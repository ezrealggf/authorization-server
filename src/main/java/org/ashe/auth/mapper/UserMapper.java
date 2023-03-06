package org.ashe.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.ashe.auth.domain.model.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
