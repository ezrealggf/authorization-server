package org.ashe.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.ashe.auth.domain.model.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    User getUserByAccount(@Param("account") String account);
}
