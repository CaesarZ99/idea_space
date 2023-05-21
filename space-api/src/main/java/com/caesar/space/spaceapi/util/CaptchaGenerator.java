package com.caesar.space.spaceapi.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CaptchaGenerator {
    public static StringBuilder captchaCode = new StringBuilder();

    private static final int WIDTH = 120; // 验证码图片宽度
    private static final int HEIGHT = 40; // 验证码图片高度
    private static final int CODE_LENGTH = 4; // 验证码字符长度
    private static final String CODE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890"; // 验证码字符集合

    public static synchronized BufferedImage generateCaptchaImage() {
        captchaCode = new StringBuilder();
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        // 设置背景色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // 生成随机验证码
        Random random = new Random();
        for (int i = 0; i < CODE_LENGTH; i++) {
            char codeChar = CODE_CHARS.charAt(random.nextInt(CODE_CHARS.length()));
            captchaCode.append(codeChar);
        }
        // 设置字体和颜色
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.BLACK);

        // 绘制验证码
        int x = 20;
        int y = HEIGHT / 2 + 5;

        // 设置字体和颜色
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.BLACK);

        for (int i = 0; i < CODE_LENGTH; i++) {
            g.drawString(String.valueOf(captchaCode.charAt(i)), x, y);
            x += 20; // 每个字符间隔20像素
        }

        // 添加噪点干扰
        int pointCount = WIDTH * HEIGHT / 50;
        for (int i = 0; i < pointCount; i++) {
            int pointX = random.nextInt(WIDTH);
            int pointY = random.nextInt(HEIGHT);
            int rgb = getRandomRgb();
            image.setRGB(pointX, pointY, rgb);
        }

        // 添加线段干扰
        int lineCount = 5;
        for (int i = 0; i < lineCount; i++) {
            int startX = random.nextInt(WIDTH);
            int startY = random.nextInt(HEIGHT);
            int endX = random.nextInt(WIDTH);
            int endY = random.nextInt(HEIGHT);
            int rgb = getRandomRgb();
            drawLine(image, startX, startY, endX, endY, rgb);
        }
        g.dispose();
        return image;
    }

    private static int getRandomRgb() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return (r << 16) | (g << 8) | b;
    }

    private static void drawLine(BufferedImage image, int startX, int startY, int endX, int endY, int rgb) {
        Graphics g = image.getGraphics();
        g.setColor(new Color(rgb));
        g.drawLine(startX, startY, endX, endY);
    }

    private static boolean isBlack(Color color) {
        return color.getRed() == 0 && color.getGreen() == 0 && color.getBlue() == 0;
    }
}
