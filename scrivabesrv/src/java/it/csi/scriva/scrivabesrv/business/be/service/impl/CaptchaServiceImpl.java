/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.service.impl;

import it.csi.scriva.scrivabesrv.business.be.impl.BaseApiServiceImpl;
import it.csi.scriva.scrivabesrv.business.be.service.CaptchaService;
import nl.captcha.Captcha;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.gimpy.FishEyeGimpyRenderer;
import nl.captcha.gimpy.GimpyRenderer;
import nl.captcha.noise.StraightLineNoiseProducer;
import nl.captcha.text.producer.DefaultTextProducer;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * The type Captcha service.
 */
@Component
public class CaptchaServiceImpl extends BaseApiServiceImpl implements CaptchaService {

    private final String className = this.getClass().getSimpleName();


    private static final int CAPTCHA_WIDTH = 200, CAPTCHA_HEIGHT = 50;
   // private static final long CAPTCHA_EXPIRY_TIME = 5;
   // private static Map<String, String> captchaCodeMap = ExpiringMap.builder().expiration(CAPTCHA_EXPIRY_TIME, TimeUnit.MINUTES).build();
   // private SecureRandom random = new SecureRandom();

    /**
     * Next captcha id string.
     *
     * @return the string
     */
	 /*   public String nextCaptchaId() {
	        return new BigInteger(130, random).toString(32);
	    }
	  */

    @Override
    public String[] generateCaptchaImage(String previousCaptchaId) throws NoSuchAlgorithmException {
        logBeginInfo(className, "previousCaptchaId : [" + previousCaptchaId + "]");
        Captcha captcha = new Captcha.Builder(CAPTCHA_WIDTH, CAPTCHA_HEIGHT).addText()
                .addBackground(new GradiatedBackgroundProducer()).addBorder().build();
        BufferedImage buf = captcha.getImage();
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        String captchaPngImage = null;
        try {
            ImageIO.write(buf, "png", bao);
            bao.flush();
            byte[] imageBytes = bao.toByteArray();
            bao.close();
            captchaPngImage = new String(Base64.getEncoder().encode(imageBytes), "UTF-8");
        } catch (Exception e) {
            logError(className, e);
        }
       // String captchaId = this.nextCaptchaId();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashbytes = digest.digest(captcha.getAnswer().getBytes(StandardCharsets.UTF_8));
        String stringEncode=      Base64.getEncoder().encodeToString(hashbytes);
        String[] imageParams = {captchaPngImage, stringEncode};
       // addCaptcha(captchaId, captcha.getAnswer());
        logEnd(className);
        return imageParams;
    }

    @Override
    public boolean validateCaptcha(String captchaId, String captchaAnswer) throws NoSuchAlgorithmException  {
    	 MessageDigest digest = MessageDigest.getInstance("SHA-256");
         byte[] hashbytes = digest.digest(captchaAnswer.getBytes(StandardCharsets.UTF_8));
         String stringEncode=      Base64.getEncoder().encodeToString(hashbytes);
        logBeginInfo(className, "captchaId : [" + captchaId + "] - captchaAnswer : [" + captchaAnswer + "]");
       // boolean result = captchaCodeMap.containsKey(captchaId) && captchaCodeMap.get(captchaId).equals(captchaAnswer);
        boolean result = captchaId.equals(stringEncode);
      //  removeCaptcha(captchaId);
        logEnd(className);
        return result;
    }


  /*  private static void addCaptcha(String captchaId, String captchaAnswer) {
        captchaCodeMap.putIfAbsent(captchaId, captchaAnswer);
        //System.out.println("+++++ Element added to crunchifyMap:" + captchaId+"=>"+ captchaAnswer);
        //printElement();
    }

    private static void removeCaptcha(String captchaId) {
        captchaCodeMap.remove(captchaId);
    }
*/
}