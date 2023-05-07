package com.caesar.space.spacemain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.caesar.space.spaceapi.domain.User;
import org.apache.ibatis.annotations.Param;

/**
 * <h3>BasicUserMapper</h3>
 * <p></p>
 *
 * @author : Caesar·Liu
 * @date : 2023-04-20 17:19
 **/
public interface BasicUserMapper extends BaseMapper<User> {

    User selectUserById(@Param("userId") Long userId);
}
