package org.ashe.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
        // 参数校验
        dto.verifyParams();
        // 判断账户是否已注册，若存在则响应提示
        User user = verifyAccount(dto);
        if (!ObjectUtils.isEmpty(user)) {
            throw new BusinessException("该账户名已注册");
        }
        // 持久化
        buildUser(dto);
    }

    private void buildUser(RegisterDTO dto) {
        User user = new User();
        user.setAccount(dto.getAccount());
        user.setPassword(dto.getPassword());
        user.setGender(Gender.UN_KNOWN.getCode());
        this.save(user);
    }

    /**
     * 验证账户是否已注册
     */
    private User verifyAccount(RegisterDTO dto) {
        // 获取注册类型枚举
        Register registerEnum = Register.valueOf(dto.getType());
        return switch (registerEnum) {
            case ACCOUNT -> this.getOne(new LambdaQueryWrapper<User>().eq(User::getAccount, dto.getAccount()));
            case TEL, WECHAT -> throw new BusinessException("暂不支持的注册方式");
        };
    }

    /**
     * 登录
     */
    @SuppressWarnings("all")
    @Override
    public void login(LoginDTO dto, HttpServletRequest request) {
        // 先判断是否已登录？

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
