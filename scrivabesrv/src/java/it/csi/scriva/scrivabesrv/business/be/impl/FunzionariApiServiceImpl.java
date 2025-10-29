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

import it.csi.scriva.scrivabesrv.business.be.FunzionariApi;
import it.csi.scriva.scrivabesrv.business.be.exception.UserNotAllowedException;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.FunzionarioCompetenzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.FunzionarioDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.FunzionarioProfiloDAO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.FunzionarioAutorizzatoDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import it.csi.scriva.scrivabesrv.util.manager.FunzionarioManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * The type Funzionari api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FunzionariApiServiceImpl extends BaseApiServiceImpl implements FunzionariApi {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Funzionario dao.
     */
    @Autowired
    FunzionarioDAO funzionarioDAO;

    /**
     * The Funzionario competenza dao.
     */
    @Autowired
    FunzionarioCompetenzaDAO funzionarioCompetenzaDAO;

    /**
     * The Funzionario profilo dao.
     */
    @Autowired
    FunzionarioProfiloDAO funzionarioProfiloDAO;

    @Autowired
    FunzionarioManager funzionarioManager;


    /**
     * Load ambiti response.
     *
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @Override
    public Response loadFunzionario(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        FunzionarioAutorizzatoDTO funzionarioAutorizzato = null;
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
            logDebug(className, "Header parameter Attore-Scriva :\n" + attoreScriva);

            if (!ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
                ErrorDTO error = getErrorManager().getError("400", null, "Componente applicativa non valida.", null, null);
                throw new UserNotAllowedException(error);
            }
            funzionarioAutorizzato = funzionarioManager.getFunzionarioAutorizzatoByCF(attoreScriva.getCodiceFiscale());
            if (funzionarioAutorizzato == null) {
                ErrorDTO error = getErrorManager().getError("404", "E036", "Utente non abilitato ad accedere alla scrivania del funzionario. Contattare assistenza.", null, null);
                logError(className, error);
                return Response.serverError().entity(error).status(404).build();
            }

        } catch (UserNotAllowedException e) {
            logError(className, e);
            return Response.serverError().entity(e.getError()).status(404).build();
        } catch (Exception e) {
            logError(className, e);
        }
        return getResponseSaveUpdate(funzionarioAutorizzato, className);
    }

}