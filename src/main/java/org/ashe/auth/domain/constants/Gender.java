package org.ashe.auth.domain.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    /**
     * 男
     */
    MALE(1,"男"),
    /**
     * 女
     */
    FEMALE(2,"女"),
    /**
     * 未知
     */
    UN_KNOWN(0,"未知");


    /**
     * 性别编号
     */
    private final Integer code;
    /**
     * 性别对象
     */
    private final String object;




}
