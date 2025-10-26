/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.impl;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.scriva.scrivaapisrv.business.be.FilesApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.FilesApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.dto.FileFoDTO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;


/**
 * The type Files api service.
 */
@Component
public class FilesApiServiceImpl extends AbstractApiServiceImpl implements FilesApi {

    private final String CLASSNAME = this.getClass().getSimpleName();

    @Autowired
    private FilesApiServiceHelper filesApiServiceHelper;

    /**
     * Upload file old response.
     *
     * @param input           the input
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
//@Override
    public Response uploadFileOLD(MultipartFormDataInput input, SecurityContext securityContext, HttpHeaders httpHeaders,
                                  HttpServletRequest httpRequest) {

        if (input.getParts().size() > 0) {
            String s = "";
            for (int i = 0; i < input.getParts().size(); i++) {
                String bodyAsString = "";
                try {
                    bodyAsString = "[[" + input.getParts().get(i).getBodyAsString() + "]]";
                } catch (IOException e) {
                    bodyAsString = "[[error wile parsing body: " + e + "]]";
                }
                s += "part[" + i + "]-media: " + input.getParts().get(i).getMediaType() + ", body as string:" + bodyAsString;
            }
            return Response.ok(s).build();
        } else {
            ErrorDTO err = new ErrorDTO("400", "", "elenco di file vuoto", null, null);
            return Response.serverError().entity(err).status(400).build();
        }
    }

    @Override
    public Response uploadFile(MultipartFormDataInput input, String uidIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[FilesApiServiceImpl::uploadFileNew] BEGIN");
//        byte[] dto = {};
        try {
//            dto = filesApiServiceHelper.uploadFile(input, uidIstanza);
            filesApiServiceHelper.uploadFile(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), input, uidIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[FilesApiServiceImpl::uploadFileNew] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[FilesApiServiceImpl::uploadFileNew] ERROR : " + errorMessage);
            //return Response.serverError().entity(err).status(500).build();
            return Response.serverError().status(500).build();
        } finally {
            LOGGER.debug("[FilesApiServiceImpl::uploadFileNew] END");
        }
//        return Response.ok(dto).status(201).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
        return Response.noContent().build();
    }

    @Override
    public Response deleteFile(String uidIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return Response.noContent().build();
    }

    @Override
    public Response downloadFiles(String type, String name, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        FileFoDTO fileFoDTO = new FileFoDTO();
        try {
            fileFoDTO = filesApiServiceHelper.downloadFile(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), type, name);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return Response.ok(fileFoDTO.getFile(), MediaType.APPLICATION_OCTET_STREAM).header(HttpHeaders.CONTENT_ENCODING, "identity").header("Content-Disposition", fileFoDTO.getContentDispositionHeader()).build();
    }

}