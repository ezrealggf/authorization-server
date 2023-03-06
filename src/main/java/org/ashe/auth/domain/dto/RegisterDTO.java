package org.ashe.auth.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.ashe.auth.domain.constants.Register;
import org.ashe.auth.domain.vo.exc.BusinessException;
import org.springframework.util.ObjectUtils;

@Getter
@Setter
public class RegisterDTO {
    /**
     * 注册类型
     *      账号密码注册
     */
    @NotBlank(message = "请选择注册类型")
    private String type;
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;

    public void verifyParams() {
        Register register = Register.valueOf(type);
        switch (register) {
            case ACCOUNT :
                if (ObjectUtils.isEmpty(account) || ObjectUtils.isEmpty(password)) {
                    throw new BusinessException("账户或密码未填写");
                }
                break;
            case TEL, WECHAT :
                throw new BusinessException("暂不支持的注册方式");
            default:
                throw new BusinessException("you are silly B");
        }
    }
}
