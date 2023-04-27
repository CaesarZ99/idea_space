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


import com.caesar.space.spaceapi.responce.JsonResponse;
import com.caesar.space.spaceapi.util.MqUtil;
import com.caesar.space.spacemain.service.BasicUserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@RestController
@RequestMapping("basic")
public class BasicController {

    @Autowired
    private BasicUserService basicUserService;

    @RequestMapping(value = "upload")
    @HystrixCommand(fallbackMethod = "uploadFail")
    public JsonResponse<?> upload(@RequestPart("file") MultipartFile multipartFile) {
        if (multipartFile == null) {
            return JsonResponse.Builder.buildFailure("file is required");
        }
        return JsonResponse.Builder.buildSuccess(basicUserService.uploadFileBySpaceFile(multipartFile));
    }

    /**
     * 上传文件basic/upload的熔断接口
     * 熔断参数和返回值都需要一样
     *
     * @param multipartFile multipartFile
     * @return JsonResponse
     */
    public JsonResponse<?> uploadFail(MultipartFile multipartFile) {
        return JsonResponse.Builder.buildFailure("上传失败咯");
    }


}
