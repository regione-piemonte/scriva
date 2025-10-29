/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be;

import it.csi.scriva.scrivabesrv.dto.CaptchaChallangeRequestDTO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;


/**
 * The interface Captcha api.
 *
 * @author CSI PIEMONTE
 */
@Path("/captcha")
@Produces(MediaType.APPLICATION_JSON)
public interface CaptchaApi {


    /**
     * Gets captcha image.
     *
     * @param xRequestAuth      the x request auth
     * @param xRequestId        the x request id
     * @param previousCaptchaId the previous captcha id
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the captcha image
     */
    @GET
    Response getCaptchaImage(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                             @QueryParam("previous_captcha_code") String previousCaptchaId,
                             @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Challange captcha response.
     *
     * @param xRequestAuth               the x request auth
     * @param xRequestId                 the x request id
     * @param captchaChallangeRequestDTO the captcha challange request dto
     * @param securityContext            the security context
     * @param httpHeaders                the http headers
     * @param httpRequest                the http request
     * @return the response
     */
    @POST
    @Path("/_validate")
    @Consumes(MediaType.APPLICATION_JSON)
    Response challangeCaptcha(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                              @RequestBody CaptchaChallangeRequestDTO captchaChallangeRequestDTO,
                              @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


}