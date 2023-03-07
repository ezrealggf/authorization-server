package org.ashe.auth.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.ashe.auth.domain.constants.Gender;
import org.ashe.auth.domain.constants.Register;
import org.ashe.auth.domain.dto.LoginDTO;
import org.ashe.auth.domain.dto.LogoutDTO;
import org.ashe.auth.domain.dto.RegisterDTO;
import org.ashe.auth.domain.model.User;
import org.ashe.auth.domain.vo.exc.BusinessException;
import org.ashe.auth.mapper.UserMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 注册
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterDTO dto) {
        // 判断账户是否已注册，若存在则响应提示
        User user = verifyAccount(dto);
        if (!ObjectUtils.isEmpty(user)) {
            throw new BusinessException("该账户已被注册");
        }
        // 持久化
        registerUser(dto);
    }

    public void registerUser(RegisterDTO dto) {
        User user = new User();
        user.setAccount(dto.getAccount());
        // 密码加密
        String encodePassword = new BCryptPasswordEncoder().encode(dto.getPassword());
        user.setPassword(encodePassword);
        user.setGender(Gender.UN_KNOWN.getCode());
        this.save(user);
    }

    /**
     * 验证账户是否已注册
     */
    @SuppressWarnings("all")
    private User verifyAccount(RegisterDTO dto) {
        // 获取注册类型枚举
        Register registerEnum = Register.valueOf(dto.getType());
        switch (registerEnum) {
            case ACCOUNT -> {
                if (ObjectUtils.isEmpty(dto.getAccount()) || ObjectUtils.isEmpty(dto.getPassword())) {
                    throw new BusinessException("账户或密码未填写");
                }
                return getUserByAccount(dto.getAccount());
            }
            case TEL, WECHAT -> throw new BusinessException("暂不支持的注册方式");
        }
        return null;
    }

    @Override
    public User getUserByAccount(String account) {
        return baseMapper.getUserByAccount(account);
    }

    /**
     * 登录
     */
    @SuppressWarnings("all")
    @Override
    public void login(LoginDTO dto, HttpServletRequest request) {
        // 先判断是否已登录？
        System.out.println("Here's Johnny!");
    }

    /**
     * 注销登录
     */
    @SuppressWarnings("all")
    @Override
    public void logout(LogoutDTO dto, HttpServletRequest request) {
        // 先判断请求是否为用户本人，确认无误方执行注销登录行为
    }

}
