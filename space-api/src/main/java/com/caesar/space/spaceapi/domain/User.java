/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.caesar.space.spaceapi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("idea_user_t")
public class User {
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long userId;

    private String userName;

    private String password;

    private String nickName;

    private Integer invalidFlag;

    private Integer deleteFlag;

    private String createdTime;

    private String updatedTime;

    private SpaceFile avatarFile;

}
