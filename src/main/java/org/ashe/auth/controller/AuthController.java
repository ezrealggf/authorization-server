package org.ashe.auth.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.ashe.auth.domain.dto.LogoutDTO;
import org.ashe.auth.domain.vo.resp.RespBody;
import org.ashe.auth.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/deny")
public class AuthController {


    @Resource
    private UserService userService;

    /**
     * 注销登录
     */
    @PostMapping("/logout")
    public RespBody<Void> logout(@RequestBody @Valid LogoutDTO dto, HttpServletRequest request) {
        log.info("Little pigs, little pigs, let me come in.");
        userService.logout(dto, request);
        return RespBody.ok();
    }
}
