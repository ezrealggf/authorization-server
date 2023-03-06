package org.ashe.auth.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {

    /**
     * 登录类型
     *      账号登录
     *      手机验证码登录
     */
    @NotBlank(message = "请选择登录类型")
    private String type;
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
}
