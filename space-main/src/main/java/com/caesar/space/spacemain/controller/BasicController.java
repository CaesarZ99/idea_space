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


import com.caesar.space.spaceapi.domain.User;
import com.caesar.space.spaceapi.responce.JsonResponse;
import com.caesar.space.spaceapi.util.CaptchaGenerator;
import com.caesar.space.spaceapi.util.LogUtil;
import com.caesar.space.spacemain.service.BasicUserService;
import com.caesar.space.spacemain.service.CaptchaService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@RestController
@RequestMapping("basic")
public class BasicController {

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private BasicUserService basicUserService;

    @RequestMapping(value = "upload")
    @HystrixCommand(fallbackMethod = "uploadFail",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000" )
            })
    public Object upload(@RequestPart("file") MultipartFile multipartFile, @RequestParam("captcha") String captcha, HttpServletRequest request) throws InterruptedException {
        if (multipartFile == null) {
            return JsonResponse.Builder.buildFailure("file is required");
        }
        return basicUserService.uploadFileBySpaceFile(multipartFile,captcha,request);
    }

    /**
     * 上传文件basic/upload的熔断接口
     * 熔断参数和返回值都需要一样
     *
     * @param multipartFile multipartFile
     * @return JsonResponse
     */
    public JsonResponse<?> uploadFail(MultipartFile multipartFile,String captcha, HttpServletRequest request) {
        // 触发熔断打印日志
        LogUtil.logInfo("调用space-file服务失败/异常，请检查space-file服务日志: ",this.getClass());
        return JsonResponse.Builder.buildFailure("上传失败咯");
    }

    @RequestMapping("userInfo")
    public JsonResponse<?> getUserInfo(@RequestParam("userId") Long userId) {
        User user = basicUserService.gerSingleUserInfo(userId);
        return JsonResponse.Builder.buildSuccess(user);
    }

    @GetMapping("getCaptCha")
    public Object getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean limit = captchaService.captchaLimit(request);
        if (limit) {
            return null;
        }
        BufferedImage cachedCaptcha = captchaService.getCachedCaptcha(request);
        if (cachedCaptcha != null) {
            return ImageIO.write(cachedCaptcha, "JPEG", response.getOutputStream());
        }
        return null;
    }
}
