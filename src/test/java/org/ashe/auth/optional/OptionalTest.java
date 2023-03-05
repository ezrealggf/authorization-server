package org.ashe.auth.optional;

import lombok.extern.slf4j.Slf4j;
import org.ashe.auth.domain.model.User;
import org.ashe.auth.domain.vo.exc.BusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@Slf4j
@SpringBootTest
public class OptionalTest {

    /**
     * Optional<T t>学习
     */
    @Test
    void optionalStudy() {
        User jack = getUser();
        Optional<User> user = Optional.ofNullable(jack);
        Integer age = user.map(User::getAge)
                .orElseThrow(() -> new BusinessException("不存在该用户"));
        log.info("该用户的年龄为" + age + "岁");
    }

    User getUser(){
        User user = new User();
        user.setAge(69);
        return user;
//        return null;
    }

}
