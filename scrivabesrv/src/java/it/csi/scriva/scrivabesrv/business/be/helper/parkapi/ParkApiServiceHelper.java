/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.parkapi;

import it.csi.scriva.scrivabesrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.areeprotette.AreaProtettaFiltriEstesi;
import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.areeprotette.ParkapiAreeprotetteServiceLocator;
import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.areeprotette.ParkapiAreeprotette_PortType;
import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.rn2000.ParkapiRn2000ServiceLocator;
import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.rn2000.ParkapiRn2000_PortType;
import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.rn2000.Rn2000;
import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.sic.ParkapiSicServiceLocator;
import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.sic.ParkapiSic_PortType;
import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.zps.ParkapiZpsServiceLocator;
import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.zps.ParkapiZps_PortType;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigGeecoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoOggettoIstanzaDAO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.rpc.ServiceException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The type Territorio service helper.
 *
 * @author CSI PIEMONTE
 */
public class ParkApiServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    private final String areeProtetteEndPoint;
    private final String sicEndPoint;
    private final String zpsEndPoint;
    private final String rn2000EndPoint;

    private ParkapiAreeprotetteServiceLocator apiAreeprotetteServiceLocator;
    private ParkapiSicServiceLocator apiSicServiceLocator;
    private ParkapiZpsServiceLocator apiZpsServiceLocator;
    private ParkapiRn2000ServiceLocator apiRn2000ServiceLocator;

    private ParkapiAreeprotette_PortType apiAreeProtette;
    private ParkapiSic_PortType apiSic;
    private ParkapiZps_PortType apiZps;
    private ParkapiRn2000_PortType apiRn2000;

    private List<AreaProtettaFiltriEstesi> areaProtettaFiltriEstesiList = Collections.emptyList(); //lista aree protette
    private List<Rn2000> rn2000List = Collections.emptyList(); //Lista rete natura 2000 

    @Autowired
    private ConfigGeecoDAO configGeecoDAO;

    @Autowired
    private GeoOggettoIstanzaDAO geoOggettoIstanzaDAO;


    /**
     * Class constructor.
     * Instantiates a new Territorio service helper.
     *
     * @param areeProtetteEndPoint the aree protette end point
     * @param zicEndPoint          the zic end point
     * @param zpsEndPoint          the zps end point
     */
    public ParkApiServiceHelper(String areeProtetteEndPoint, String rn2000EndPoint, String zicEndPoint, String zpsEndPoint) {
       
        super();
        logInfo(className, "Inizializzazione ParkApiServiceHelper con endpoint");
        this.areeProtetteEndPoint = areeProtetteEndPoint;
        this.rn2000EndPoint = rn2000EndPoint;
        this.sicEndPoint = zicEndPoint;
        this.zpsEndPoint = zpsEndPoint;
        this.getAreaProtetta();
        this.getRn2000();
    }



    /**
     * Gets api areeprotette service locator.
     *
     * @return the api areeprotette service locator
     */
    private ParkapiAreeprotetteServiceLocator getApiAreeprotetteServiceLocator() {
        if (this.apiAreeprotetteServiceLocator == null) {
            this.apiAreeprotetteServiceLocator = new ParkapiAreeprotetteServiceLocator();
            this.apiAreeprotetteServiceLocator.setparkapiAreeprotetteEndpointAddress(this.areeProtetteEndPoint);
        }
        return this.apiAreeprotetteServiceLocator;
    }

    /**
     * Gets api sic service locator.
     *
     * @return the api sic service locator
     */
    private ParkapiSicServiceLocator getApiSicServiceLocator() {
        if (this.apiSicServiceLocator == null) {
            this.apiSicServiceLocator = new ParkapiSicServiceLocator();
            this.apiSicServiceLocator.setparkapiSicEndpointAddress(this.sicEndPoint);
        }
        return this.apiSicServiceLocator;
    }

    /**
     * Gets api zps service locator.
     *
     * @return the api zps service locator
     */
    private ParkapiZpsServiceLocator getApiZpsServiceLocator() {
        if (this.apiZpsServiceLocator == null) {
            this.apiZpsServiceLocator = new ParkapiZpsServiceLocator();
            this.apiZpsServiceLocator.setparkapiZpsEndpointAddress(zpsEndPoint);
        }
        return this.apiZpsServiceLocator;
    }

    /**
     * Gets api aree protette.
     *
     * @return the api aree protette
     * @throws ServiceException the service exception
     */
    private ParkapiAreeprotette_PortType getApiAreeProtette() throws ServiceException {
        if (this.apiAreeProtette == null) {
            this.apiAreeProtette = getApiAreeprotetteServiceLocator().getparkapiAreeprotette();
        }
        return this.apiAreeProtette;
    }

    /**
     * Gets api sic.
     *
     * @return the api sic
     * @throws ServiceException the service exception
     */
    private ParkapiSic_PortType getApiSic() throws ServiceException {
        if (this.apiSic == null) {
            this.apiSic = getApiSicServiceLocator().getparkapiSic();
        }
        return this.apiSic;
    }

    /**
     * Gets api zps.
     *
     * @return the api zps
     * @throws ServiceException the service exception
     */
    private ParkapiZps_PortType getApiZps() throws ServiceException {
        if (this.apiZps == null) {
            this.apiZps = getApiZpsServiceLocator().getparkapiZps();
        }
        return this.apiZps;
    }



    /**
     * Gets area protetta.
     *
     * @return the area protetta
     */
    public List<AreaProtettaFiltriEstesi> getAreaProtetta() {
        logBegin(className);
        try {
            if (CollectionUtils.isEmpty(areaProtettaFiltriEstesiList)) {
                areaProtettaFiltriEstesiList = this.getAreaProtetta(null, null, null, null, null, null, null, null, null);
            }
        } catch (Exception e) {
            logError(className, e);
        }
        logEnd(className);
        return areaProtettaFiltriEstesiList;
    }

    /**
     * Gets area protetta.
     *
     * @param gmlGeometry the gml geometry
     * @return the area protetta
     */
    public List<AreaProtettaFiltriEstesi> getAreaProtetta(String gmlGeometry) {
        logBegin(className);
        try {
            //Escape non necessario
            //String escapedGML = StringEscapeUtils.escapeXml(gmlGeometry);
            //logDebug(className, "escapedGeoGML :\n" + escapedGML);
            return this.getAreaProtetta(gmlGeometry, null, null, null, null, null, null, null, null);
        } catch (Exception e) {
            logError(className, e);
        }
        logEnd(className);
        return Collections.emptyList();
    }

    /**
     * Gets area protetta.
     *
     * @param codIstatComuneList the cod istat comune list
     * @return the area protetta
     */
    public List<AreaProtettaFiltriEstesi> getAreaProtetta(List<String> codIstatComuneList) {
        logBegin(className);
        try {
            String codIstatComuni = String.join(",", codIstatComuneList);
            logDebug(className, "codIstatComuni : " + codIstatComuni);
            return this.getAreaProtetta(null, null, null, null, null, null, null, null, codIstatComuni);
        } catch (Exception e) {
            logError(className, e);
        }
        logEnd(className);
        return Collections.emptyList();
    }

    /**
     * Gets area protetta.
     *
     * @param gmlGeometry                  the gml geometry
     * @param nomeAreaProtetta             the nome area protetta
     * @param codAmmAreaProtetta           the cod amm area protetta
     * @param codEnteGestore               the cod ente gestore
     * @param nomeEnteGestore              the nome ente gestore
     * @param codTipoEnteGestore           the cod tipo ente gestore
     * @param codPatrimonialitaEnteGestore the cod patrimonialita ente gestore
     * @param codTipoAreaProtetta          the cod tipo area protetta
     * @param codIstatComuni               the cod istat comuni
     * @return the area protetta
     */
    public List<AreaProtettaFiltriEstesi> getAreaProtetta(String gmlGeometry,
                                                          String nomeAreaProtetta,
                                                          String codAmmAreaProtetta,
                                                          String codEnteGestore,
                                                          String nomeEnteGestore,
                                                          String codTipoEnteGestore,
                                                          String codPatrimonialitaEnteGestore,
                                                          String codTipoAreaProtetta,
                                                          String codIstatComuni) {
        logBegin(className);
        logInfo(className, "chiamata soap per ricerca aree protette con filtri estesi");
        try {
            AreaProtettaFiltriEstesi[] areaProtettaFiltriEstesi = getApiAreeProtette().cercaAreeProtetteConFiltriEstesi(gmlGeometry,
                    nomeAreaProtetta,
                    codAmmAreaProtetta,
                    codEnteGestore,
                    nomeEnteGestore,
                    codTipoEnteGestore,
                    codPatrimonialitaEnteGestore,
                    codTipoAreaProtetta,
                    codIstatComuni
            );
            return areaProtettaFiltriEstesi != null ? Arrays.asList(areaProtettaFiltriEstesi) : Collections.emptyList();
        } catch (Exception e) {
            logError(className, e);
        }
        logEnd(className);
        return Collections.emptyList();
    }


/***************************************************************
 *
 *
 *     Rete natura 2000
 *
 *
 *************************************************************/

    /**
     * Gets api rete natura 2000 service locator.
     *
     * @return the api rete natura 2000 service locator
     */
    private ParkapiRn2000ServiceLocator getApiRn2000ServiceLocator() {
        if (this.apiRn2000ServiceLocator == null) {
            this.apiRn2000ServiceLocator = new ParkapiRn2000ServiceLocator();
            this.apiRn2000ServiceLocator.setparkapiRn2000EndpointAddress(this.rn2000EndPoint);
        }
        return this.apiRn2000ServiceLocator;
    }

// DA CAPIRE SE E' DA FARE SIC E ZPS
//    /**
//     * Gets api sic service locator.
//     *
//     * @return the api sic service locator
//     */
//    private ParkapiSicServiceLocator getApiSicServiceLocator() {
//        if (this.apiSicServiceLocator == null) {
//            this.apiSicServiceLocator = new ParkapiSicServiceLocator();
//            this.apiSicServiceLocator.setparkapiSicEndpointAddress(this.sicEndPoint);
//        }
//        return this.apiSicServiceLocator;
//    }
//
//    /**
//     * Gets api zps service locator.
//     *
//     * @return the api zps service locator
//     */
//    private ParkapiZpsServiceLocator getApiZpsServiceLocator() {
//        if (this.apiZpsServiceLocator == null) {
//            this.apiZpsServiceLocator = new ParkapiZpsServiceLocator();
//            this.apiZpsServiceLocator.setparkapiZpsEndpointAddress(zpsEndPoint);
//        }
//        return this.apiZpsServiceLocator;
//    }

    /**
     * Gets api rete natura 2000.
     *
     * @return the api rete natura 2000
     * @throws ServiceException the service exception
     */
    private ParkapiRn2000_PortType getApiRn2000() throws ServiceException {
        if (this.apiRn2000 == null) {
            this.apiRn2000 = getApiRn2000ServiceLocator().getparkapiRn2000();
        }
        return this.apiRn2000;
    }

// DA CAPIRE SE E' DA FARE
//    /**
//     * Gets api sic.
//     *
//     * @return the api sic
//     * @throws ServiceException the service exception
//     */
//    private ParkapiSic_PortType getApiSic() throws ServiceException {
//        if (this.apiSic == null) {
//            this.apiSic = getApiSicServiceLocator().getparkapiSic();
//        }
//        return this.apiSic;
//    }
//
//    /**
//     * Gets api zps.
//     *
//     * @return the api zps
//     * @throws ServiceException the service exception
//     */
//    private ParkapiZps_PortType getApiZps() throws ServiceException {
//        if (this.apiZps == null) {
//            this.apiZps = getApiZpsServiceLocator().getparkapiZps();
//        }
//        return this.apiZps;
//    }

    /**
     * Instantiates a new Territorio service helper.
     *
     * @param rn2000EndPoint       the rete natura 2000 end point
     * @param zicEndPoint          the zic end point
     * @param zpsEndPoint          the zps end point
     */
//    public ParkApiServiceHelper(String rn2000EndPoint, String zicEndPoint, String zpsEndPoint) {
//        super();
//        this.rn2000EndPoint = rn2000EndPoint;
//        this.sicEndPoint = zicEndPoint;
//        this.zpsEndPoint = zpsEndPoint;
//    }

    /**
     * Gets rete natura 2000.
     *
     * @return the rete natura 2000
     */
    public List<Rn2000> getRn2000() {
        logBegin(className);
        try {
            if (CollectionUtils.isEmpty(rn2000List)) {
                rn2000List = this.getRn2000(null, null, null, null, null, null, null, null);
            }
        } catch (Exception e) {
            logError(className, e);
        }
        logEnd(className);
        return rn2000List;
    }

    /**
     * Gets rete natura 2000.
     *
     * @param gmlGeometry the gml geometry
     * @return the rete natura 2000.
     */
    public List<Rn2000> getRn2000(String gmlGeometry) {
        logBegin(className);
        try {
            //Escape non necessario
            //String escapedGML = StringEscapeUtils.escapeXml(gmlGeometry);
            //logDebug(className, "escapedGeoGML :\n" + escapedGML);
            return this.getRn2000(gmlGeometry, null, null, null, null, null, null, null);
        } catch (Exception e) {
            logError(className, e);
        }
        logEnd(className);
        return Collections.emptyList();
    }

    /**
     * Gets rete natura 2000.
     *
     * @param codIstatComuneList the cod istat comune list
     * @return the rete natura 2000.
     */
    public List<Rn2000> getRn2000(List<String> codIstatComuneList) {
        logBegin(className);
        try {
            String codIstatComuni = String.join(",", codIstatComuneList);
            logDebug(className, "codIstatComuni : " + codIstatComuni);
            return this.getRn2000(null, null, null, null, null, null, null, codIstatComuni);
        } catch (Exception e) {
            logError(className, e);
        }
        logEnd(className);
        return Collections.emptyList();
    }

    /**
     * Gets area protetta.
     *
     * @param gmlGeometry           the gml geometry
     * @param nomeRn2000            the nome rete natura 2000
     * @param codAmmRn2000          the cod amm rete natura 2000
     * @param codEnteGestore        the cod ente gestore
     * @param nomeEnteGestore       the nome ente gestore
     * @param codTipoEnteGestore    the cod tipo ente gestore
     * @param codTipoReteNatura2000 the cod tipo rete natura 2000
     * @param codIstatComuni        the cod istat comuni
     * @return the area protetta
     */
    public List<Rn2000> getRn2000(String gmlGeometry,
                                  String nomeRn2000,
                                  String codAmmRn2000,
                                  String codEnteGestore,
                                  String nomeEnteGestore,
                                  String codTipoEnteGestore,
                                  String codTipoReteNatura2000,
                                  String codIstatComuni) {
        logBegin(className);
        logInfo(className, "chiamata soap per ricerca siti RN2000 con filtri estesi");
        try {
            Rn2000[] rn2000 = getApiRn2000().cercaRN2000ConFiltriEstesi(gmlGeometry,
                    codAmmRn2000,
                    codEnteGestore,
                    codEnteGestore,
                    nomeEnteGestore,
                    codTipoEnteGestore,
                    codTipoReteNatura2000,
                    codIstatComuni
            );
            return rn2000 != null ? Arrays.asList(rn2000) : Collections.emptyList();
        } catch (Exception e) {
            logError(className, e);
        }
        logEnd(className);
        return Collections.emptyList();
    }


}