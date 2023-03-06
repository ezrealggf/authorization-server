package org.ashe.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import org.ashe.auth.domain.dto.LoginDTO;
import org.ashe.auth.domain.dto.LogoutDTO;
import org.ashe.auth.domain.dto.RegisterDTO;
import org.ashe.auth.domain.model.User;

public interface UserService extends IService<User> {
    void register(RegisterDTO dto);

    void login(LoginDTO dto, HttpServletRequest request);

    void logout(LogoutDTO dto, HttpServletRequest request);
}
