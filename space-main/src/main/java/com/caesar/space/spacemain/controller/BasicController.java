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

package com.caesar.space.spacemain.controller;


import com.caesar.space.spaceapi.tools.MqUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@RestController
@RequestMapping("basic")
public class BasicController {

    @Autowired
    private MqUtil mqUtil;

    @RequestMapping("/hello")
    public String hello() {

        return mqUtil.sendUploadMessage(null);
    }
    @RequestMapping(value = "upload")
    public String upload(@RequestPart("file") MultipartFile multipartFile) {
        if (multipartFile == null) {
            return "file is required";
        }
        return mqUtil.sendUploadMessage(multipartFile);
    }

}
