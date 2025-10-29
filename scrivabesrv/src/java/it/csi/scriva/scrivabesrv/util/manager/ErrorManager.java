/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.manager;

import it.csi.scriva.scrivabesrv.business.be.impl.dao.MessaggioDAO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.MessaggioExtendedDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

/**
 * The type Error manager.
 *
 * @author CSI PIEMONTE
 */
@Component
public class ErrorManager {

    @Autowired
    private MessaggioDAO messaggioDAO;

    /**
     * Gets error.
     *
     * @param status  the status
     * @param code    the code
     * @param title   the title
     * @param details the details
     * @param links   the links
     * @return the error
     */
    public ErrorDTO getError(String status, String code, String title, Map<String, String> details, List<String> links) {
        List<MessaggioExtendedDTO> messaggi = StringUtils.isNotBlank(code) ? messaggioDAO.loadMessaggioByCode(code) : null;
        MessaggioExtendedDTO msg = null != messaggi && !messaggi.isEmpty() ? messaggi.get(0) : null;
        title = null != msg ? msg.getDesTestoMessaggio() : title;
        return new ErrorDTO(status, code, null != title ? title : "Errore inaspettato nella gestione della richiesta. Riprova a breve", details, links);
    }

    /**
     * Gets generic error response.
     *
     * @return the generic error response
     */
    public Response getGenericErrorResponse() {
        ErrorDTO error = getError("500", "E100", null, null, null);
        return Response.serverError().status(500).entity(error).build();
    }
}