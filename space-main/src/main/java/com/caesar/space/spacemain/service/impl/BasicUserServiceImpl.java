package com.caesar.space.spacemain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caesar.space.spacemain.domain.User;
import com.caesar.space.spacemain.mapper.BasicUserMapper;
import com.caesar.space.spacemain.service.BasicUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <h3>BasicUserServiceImpl</h3>
 * <p>基础用户接口的实现类</p>
 *
 * @author : Caesar·Liu
 * @date : 2023-04-20 17:11
 **/
@Service
public class BasicUserServiceImpl extends ServiceImpl<BasicUserMapper, User> implements BasicUserService {

    @Autowired
    BasicUserMapper basicUserMapper;

    @Override
    public User gerSingleUserInfo(Long userId) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        return basicUserMapper.selectOne(wrapper);
    }
}
