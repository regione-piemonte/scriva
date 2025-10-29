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

import it.csi.scriva.scrivabesrv.business.be.IstanzaApi;
import it.csi.scriva.scrivabesrv.business.be.TipoResponsabileApi;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoConfigDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AllegatoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaAttoreDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaResponsabiliDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaStatoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaTemplateQuadroDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ReferenteIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.StatoIstanzaAdempimentoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.StatoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoResponsabileDAO;
import it.csi.scriva.scrivabesrv.business.be.service.IstanzaEventoService;
import it.csi.scriva.scrivabesrv.business.be.service.IstanzaService;
import it.csi.scriva.scrivabesrv.business.be.service.TemplateService;
import it.csi.scriva.scrivabesrv.dto.AdempimentoConfigDTO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AllegatoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaResponsabileDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaStatoDTO;
import it.csi.scriva.scrivabesrv.dto.ReferenteIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.StatoIstanzaAdempimentoDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.TipoEventoEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.TipologiaAllegatoEnum;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.manager.AllegatiManager;
import it.csi.scriva.scrivabesrv.util.manager.IstanzaAttoreManager;
import it.csi.scriva.scrivabesrv.util.manager.JsonDataManager;
import it.csi.scriva.scrivabesrv.util.manager.MandatoryInfoIstanzaManager;
import it.csi.scriva.scrivabesrv.util.templating.TemplateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


/**
 * The type Istanza api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TipoResponsabileApiServiceImpl extends BaseApiServiceImpl implements TipoResponsabileApi {

    private final String className = this.getClass().getSimpleName();


    @Autowired
    private TipoResponsabileDAO tipoResponsabileDAO;
    
    @Autowired
    private IstanzaDAO istanzaDAO;
    
    @Autowired
    private AdempimentoConfigDAO adempimentoConfigDAO;


    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadTipoResponsabile(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        List<String> codTipoResponsabile = new ArrayList<String>();

        if(idIstanza != null) {
        	List<IstanzaExtendedDTO> istanzaRes = istanzaDAO.loadIstanza(idIstanza);
        	IstanzaExtendedDTO istanza = istanzaRes.get(0);
        	Long idAdemp = istanza.getAdempimento().getIdAdempimento();
        	String chiave = "TIPO_RESP_ITER";
        	List<AdempimentoConfigDTO> adempConfList = adempimentoConfigDAO.loadAdempimentoConfigByIdAdempimentoChiave(idAdemp, chiave);
        	AdempimentoConfigDTO adempiConfig = adempConfList.get(0);
        	String valore = adempiConfig.getValore();
        	codTipoResponsabile = Arrays.asList(valore.split(Pattern.quote("|")));
        	
        }
        
        return getResponseList(tipoResponsabileDAO.loadTipoResponsabile(idIstanza, codTipoResponsabile), className);
    }


}