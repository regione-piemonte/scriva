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

import it.csi.scriva.scrivabesrv.business.be.FilesApi;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigurazioneDAO;
import it.csi.scriva.scrivabesrv.dto.ConfigurazioneDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.FileTypePathConfigurationEnum;
import it.csi.scriva.scrivabesrv.util.updownfile.FileDTO;
import it.csi.scriva.scrivabesrv.util.updownfile.FileUtils;
import it.csi.scriva.scrivabesrv.util.updownfile.UpDownFileUtil;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * The type Files api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FilesApiServiceImpl extends BaseApiServiceImpl implements FilesApi {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Configurazione dao.
     */
    @Autowired
    ConfigurazioneDAO configurazioneDAO;

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
    public Response uploadFileOLD(MultipartFormDataInput input, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        if (input.getParts().size() > 0) {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < input.getParts().size(); i++) {
                String bodyAsString;
                try {
                    bodyAsString = "[[" + input.getParts().get(i).getBodyAsString() + "]]";
                } catch (IOException e) {
                    bodyAsString = "[[error wile parsing body: " + e + "]]";
                }
                s.append("part[").append(i).append("]-media: ").append(input.getParts().get(i).getMediaType()).append(", body as string:").append(bodyAsString);
            }
            return Response.ok(s.toString()).build();
        } else {
            ErrorDTO err = new ErrorDTO("400", "", "elenco di file vuoto", null, null);
            return Response.serverError().entity(err).status(400).build();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response uploadFile(MultipartFormDataInput input, String uidIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        if (input.getParts().size() > 0) {
            FileDTO fileDTO = FileUtils.uploadFile(input, "uploadedFile", null);
//            return Response.ok(fileDTO.getBody()).header("Content-Disposition", "attachment; filename="+ fileDTO.getFileName()).build();
            return Response.noContent().build();
        } else {
            ErrorDTO err = new ErrorDTO("400", "", "elenco di file vuoto", null, null);
            return Response.serverError().entity(err).status(400).build();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response deleteFile(String uidIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return Response.noContent().build();
    }

    /**
     * uploadFile
     *
     * @param input           MultipartFormDataInput
     * @param uidIstanza      uidIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response uploadFiles(MultipartFormDataInput input, String uidIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        if (input.getParts().size() > 0) {
            FileDTO fileDTO = UpDownFileUtil.uploadFile(input, "uploadedFile", null);
//            return Response.ok(fileDTO.getBody()).header("Content-Disposition", "attachment; filename="+ fileDTO.getFileName()).build();
            return Response.noContent().build();
        } else {
            ErrorDTO err = new ErrorDTO("400", "", "elenco di file vuoto", null, null);
            return Response.serverError().entity(err).status(400).build();
        }
    }

    @Override
    public Response downloadFiles(String type, String fileName, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input type: [" + type + "] - filename : [" + fileName + "]";
        LOGGER.debug(getClassFunctionBeginInfo(className, methodName));
        LOGGER.debug(getClassFunctionDebugString(className, methodName, inputParam));

        InputStream inputStream = null;
        String chiave = FileTypePathConfigurationEnum.valueOf(type.toUpperCase()).getChiaveConfigurazione();
        LOGGER.debug(getClassFunctionDebugString(className, methodName, "chiave : [" + chiave + "]"));
        List<ConfigurazioneDTO> configurazioneList = configurazioneDAO.loadConfigByKey(chiave);
        if (configurazioneList != null && !configurazioneList.isEmpty()) {
            String path = "templates/" + configurazioneList.get(0).getValore() + "/" + fileName; //"templates/Template_Modulo_PDF_VIA_ELENCO_ALLEGATI_V1.docx";
            LOGGER.debug(getClassFunctionDebugString(className, methodName, "path : [" + path + "]"));
            inputStream = FilesApiServiceImpl.class.getClassLoader().getResourceAsStream(path);
            LOGGER.debug(getClassFunctionEndInfo(className, methodName));
            return Response.ok(inputStream, MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", "attachment; filename=\"" + fileName + "\"").build();
        } else {
            ErrorDTO err = new ErrorDTO("400", "", "File vuoto", null, null);
            LOGGER.debug(getClassFunctionEndInfo(className, methodName));
            return Response.serverError().entity(err).status(400).build();
        }
    }

}