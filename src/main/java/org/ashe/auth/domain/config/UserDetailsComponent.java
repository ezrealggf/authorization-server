package org.ashe.auth.domain.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.ashe.auth.domain.model.User;
import org.ashe.auth.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

//@Component
public class UserDetailsComponent implements UserDetailsService {

    @Resource
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getAccount, username));
        if (ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException("账户或密码错误");
        }
        // "{noop}" + user.getPassword()
        return org.springframework.security.core.userdetails.User.withUsername(username).password(user.getPassword()).roles("message.read").build();
    }
}
