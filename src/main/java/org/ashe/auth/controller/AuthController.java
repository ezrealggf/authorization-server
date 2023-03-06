package org.ashe.auth.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.ashe.auth.domain.dto.LoginDTO;
import org.ashe.auth.domain.dto.LogoutDTO;
import org.ashe.auth.domain.dto.RegisterDTO;
import org.ashe.auth.domain.vo.resp.RespBody;
import org.ashe.auth.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {


    @Resource
    private UserService userService;

    /**
     * 注册
     */
    @PostMapping("/register")
    public RespBody<Void> register(@RequestBody @Valid RegisterDTO dto) {
        userService.register(dto);
        return RespBody.ok();
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public RespBody<Void> login(@RequestBody @Valid LoginDTO dto, HttpServletRequest request) {
        userService.login(dto, request);
        return RespBody.ok();
    }

    /**
     * 注销登录
     */
    @PostMapping("/logout")
    public RespBody<Void> logout(@RequestBody @Valid LogoutDTO dto, HttpServletRequest request) {
        userService.logout(dto, request);
        return RespBody.ok();
    }
}
