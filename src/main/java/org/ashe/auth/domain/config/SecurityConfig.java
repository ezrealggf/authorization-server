package org.ashe.auth.domain.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration
public class SecurityConfig {

    /**
     * A Spring Security filter chain for the Protocol Endpoints.
     * ???????????????Spring Security???????????????
     */
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());    // Enable OpenID Connect 1.0
        http
                // Redirect to the login page when not authenticated from the
                // authorization endpoint
                // ????????????????????????????????????????????????
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(
                                new LoginUrlAuthenticationEntryPoint("/login"))
                )
                // Accept access tokens for User Info and/or Client Registration
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

        return http.build();
    }

    /**
     * A Spring Security filter chain for authentication.
     * ?????????????????????Spring Security???????????????
     *     public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
     *         //  ??????Endpoint,?????????????????????Spring-security??????
     *         security.tokenKeyAccess("permitAll()") // ??????/oauth/token_key ???????????????????????????
     *                 .checkTokenAccess("isAuthenticated()") // ??????/oauth/check_token ??????????????????????????????
     *                 .allowFormAuthenticationForClients()// ??????????????????
     *                 .passwordEncoder(passwordEncoder);   // ??????BCrypt??????
     *     }
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {

        http
                // CSRF????????????????????????session
                .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        // ??????
                        .requestMatchers("/access/**", "/oauth2/**").permitAll()
                        .requestMatchers("/deny/**").authenticated()
                )
                // Form login handles the redirect to the login page from the
                // authorization server filter chain
//                .formLogin().disable(); // ????????????????????????
			.formLogin(Customizer.withDefaults()); // ??????????????????

        return http.build();
    }

    /**
     * An instance of UserDetailsService for retrieving users to authenticate.
     * ??????????????????????????????????????????UserDetailsService?????????
     */
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails userDetails = User.withUsername("user")
//                .password("{noop}1234")
//                .roles("user")
//                .build();
//        return new InMemoryUserDetailsManager(userDetails);
//    }

	/**
	 * An instance of RegisteredClientRepository for managing clients.
	 * ????????????????????????RegisteredClientRepository?????????
	 */
	@Bean
	public RegisteredClientRepository registeredClientRepository() {
		RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("client")
				.clientSecret(new BCryptPasswordEncoder().encode("root"))
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.redirectUri("http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc")
				.redirectUri("http://127.0.0.1:8080/authorized")
				.scope(OidcScopes.OPENID)
				.scope(OidcScopes.PROFILE)
				.scope("message.read")
				.scope("message.write")
				.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
				.build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    /**
     * An instance of com.nimbusds.jose.jwk.source.JWKSource for signing access tokens.
     * ???????????????????????????com.nimbusds.jose.jwk.source.JWKSource????????????
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    /**
     * An instance of java.security.KeyPair with keys generated on startup used to create the JWKSource above.
     * ?????????????????????????????????java.security.KeyPair???????????????????????????JWKSource???
     */
    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    /**
     * An instance of JwtDecoder for decoding signed access tokens.
     * JwtDecoder??????????????????????????????????????????????????????
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    /**
     * An instance of AuthorizationServerSettings to configure Spring Authorization Server.
     * ????????????Spring Authorization Server???AuthorizationServerSettings?????????
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    /**
     * ???????????????
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




}

