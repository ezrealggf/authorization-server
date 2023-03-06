//package org.ashe.auth.controller;
//
//import org.ashe.auth.domain.constants.Gender;
//import org.ashe.auth.domain.model.User;
//import org.ashe.auth.domain.vo.exc.BusinessException;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Optional;
//
//@SpringBootTest
//class OptionalTest {
//
//    /**
//     * Optional<T t>学习
//     */
//    @Test
//    void optionalStudy() {
//        User jack = getUser();
//        Optional<User> user = Optional.ofNullable(jack);
//        Integer gender = user.map(User::getGender)
//                .orElseThrow(() -> new BusinessException("不存在该用户"));
//        System.out.println(gender);
//    }
//
//    User getUser(){
//        User user = new User();
//        user.setGender(Gender.FEMALE.getCode());
//        return user;
////        return null;
//    }
//
//}
