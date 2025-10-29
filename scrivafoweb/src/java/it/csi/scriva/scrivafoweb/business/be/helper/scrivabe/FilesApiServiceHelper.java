/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe;

import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.dto.FileFoDTO;
import it.csi.scriva.scrivafoweb.util.Constants;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;

public class FilesApiServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    public FilesApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public /*byte[]*/ void uploadFile(MultivaluedMap<String, Object> requestHeaders, MultipartFormDataInput input, String uidIstanza) throws GenericException {
        logger.debug("[FilesApiServiceHelper::uploadFile] BEGIN");
        byte[] result = {};
        String targetUrl = this.endpointBase + "/files/uid-istanza/" + uidIstanza;
        try {
            Client client = ClientBuilder.newBuilder().build();
            WebTarget target = client.target(targetUrl);
            MultipartFormDataOutput output = new MultipartFormDataOutput();
            byte[] bytes = input.getFormDataPart("uploadedFile", byte[].class, null);
            output.addFormData("uploadedFile", bytes, MediaType.APPLICATION_OCTET_STREAM_TYPE);
            Response resp = target.request().post(Entity.entity(output, MediaType.MULTIPART_FORM_DATA));
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[FilesApiServiceHelper::uploadFile] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
//            GenericType<byte[]> file = new GenericType<>() {};
//            result = resp.readEntity(file);
        } catch (ProcessingException e) {
            logger.error("[FilesApiServiceHelper::uploadFile] ERROR : ", e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } catch (IOException e) {
            logger.error("[FilesApiServiceHelper::uploadFile] ERROR : ", e);
        } finally {
            logger.debug("[FilesApiServiceHelper::uploadFile] END");
        }
//        return result;
    }

    public FileFoDTO downloadFile(MultivaluedMap<String, Object> requestHeaders, String type, String name) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        FileFoDTO result = new FileFoDTO();
        String targetUrl = this.endpointBase + "/files/type/"+ type + "/name/" + name;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error(getClassFunctionErrorInfo(className, methodName, SERVER_EXCEPTION + err));
                throw new GenericException(err);
            }
            result.setFile(resp.readEntity(new GenericType<File>() {}));
            result.setContentDispositionHeader(resp.getHeaderString(Constants.HEADER_CONTENT_DISPOSITION));
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
        return result;
    }

}