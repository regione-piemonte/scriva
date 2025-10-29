/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.impl;

import it.csi.scriva.scrivabesrv.business.be.CaptchaApi;
import it.csi.scriva.scrivabesrv.business.be.service.CaptchaService;
import it.csi.scriva.scrivabesrv.dto.CaptchaChallangeDTO;
import it.csi.scriva.scrivabesrv.dto.CaptchaChallangeRequestDTO;
import it.csi.scriva.scrivabesrv.dto.CaptchaDTO;
import it.csi.scriva.scrivabesrv.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.NoSuchAlgorithmException;

/**
 * The type Captcha api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CaptchaApiServiceImpl extends BaseApiServiceImpl implements CaptchaApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private CaptchaService captchaService;

    @Override
    public Response getCaptchaImage(String xRequestAuth, String xRequestId, String previousCaptchaId, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, previousCaptchaId);
        String[] captchaData= {};
		try {
			captchaData = captchaService.generateCaptchaImage(previousCaptchaId);
		} catch (NoSuchAlgorithmException e) {
            logError(className, e);
		}
        CaptchaDTO captchaDTO = new CaptchaDTO();
        captchaDTO.setCaptchaId(captchaData[1]);
        captchaDTO.setCaptchaImg(captchaData[0]);
        logEnd(className);
        return Response.ok(captchaDTO).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).build();
    }

    @Override
    public Response challangeCaptcha(String xRequestAuth, String xRequestId, CaptchaChallangeRequestDTO captchaChallangeRequest, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, captchaChallangeRequest);
        boolean challange=false;
		try {
			challange = (captchaChallangeRequest.getCaptchaAnswer() != null && captchaChallangeRequest.getCaptchaAnswer().trim().length() != 0) && (captchaChallangeRequest.getCaptchaId() != null && captchaChallangeRequest.getCaptchaId().trim().length() != 0) ?
			        captchaService.validateCaptcha(captchaChallangeRequest.getCaptchaId(), captchaChallangeRequest.getCaptchaAnswer()) :
			        Boolean.FALSE;
		} catch (NoSuchAlgorithmException e) {
            logError(className, e);
		}
        CaptchaChallangeDTO captchaChallangeDTO = new CaptchaChallangeDTO();
        captchaChallangeDTO.setChallange(challange ? "success" : "fail");
        logEnd(className);
        return Response.ok(captchaChallangeDTO).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).build();
    }

}