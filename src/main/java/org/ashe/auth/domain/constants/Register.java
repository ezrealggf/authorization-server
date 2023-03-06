package org.ashe.auth.domain.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Register {
    /**
     * 账号密码注册
     */
    ACCOUNT,
    /**
     * 电话注册
     */
    TEL,
    /**
     * 微信号注册
     */
    WECHAT
}
