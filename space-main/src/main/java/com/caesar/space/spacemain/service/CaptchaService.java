package com.caesar.space.spacemain.service;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;

public interface CaptchaService {
    BufferedImage getCachedCaptcha(HttpServletRequest request);
    boolean captchaLimit(HttpServletRequest request);
}
