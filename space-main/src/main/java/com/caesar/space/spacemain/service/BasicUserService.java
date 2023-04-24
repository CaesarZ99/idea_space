package com.caesar.space.spacemain.service;

import com.caesar.space.spacemain.domain.User;

public interface BasicUserService {

    /**
     * 通过userId 获取单个用户信息
     * @param userId userId
     * @return User
     */
    User gerSingleUserInfo(Long userId);
}
