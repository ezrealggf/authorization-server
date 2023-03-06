package org.ashe.auth.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    /**
     * 注册类型
     *      账号密码注册
     */
    @NotBlank(message = "请选择注册类型: ACCOUNT/TEL/WECHAT")
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
