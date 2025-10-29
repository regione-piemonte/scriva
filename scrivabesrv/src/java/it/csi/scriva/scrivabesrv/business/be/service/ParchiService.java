/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.service;

import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaAreaProtettaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaNatura2000ExtendedDTO;

import java.util.List;

/**
 * The interface Parchi service.
 *
 * @author CSI PIEMONTE
 */
public interface ParchiService {

    /**
     * Gets aree protette.
     *
     * @return the aree protette
     * @throws GenericException the generic exception
     */
    List<OggettoIstanzaAreaProtettaExtendedDTO> getAreeProtette() throws GenericException;

    /**
     * Gets aree protette by cod istat comuni.
     *
     * @param codIstatComuni the cod istat comuni
     * @return the aree protette by cod istat comuni
     * @throws GenericException the generic exception
     */
    List<OggettoIstanzaAreaProtettaExtendedDTO> getAreeProtetteByCodIstatComuni(String codIstatComuni) throws GenericException;

    /**
     * Gets aree protette.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param checkComuni      the check comuni
     * @return the aree protette
     * @throws GenericException the generic exception
     */
    List<OggettoIstanzaAreaProtettaExtendedDTO> getAreeProtette(Long idOggettoIstanza, Boolean checkComuni) throws GenericException;

    /**
     * Gets aree protette by geometry.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the aree protette by geometry
     * @throws GenericException the generic exception
     */
    List<OggettoIstanzaAreaProtettaExtendedDTO> getAreeProtetteByGeometry(Long idOggettoIstanza) throws GenericException;

    /**
     * Gets aree protette by ogg ist cod istat comuni.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the aree protette by cod istat comuni
     * @throws GenericException the generic exception
     */
    List<OggettoIstanzaAreaProtettaExtendedDTO> getAreeProtetteByOggIstCodIstatComuni(Long idOggettoIstanza) throws GenericException;


    /**
     * Gets siti natura 2000.
     *
     * @return the siti natura 2000
     * @throws GenericException the generic exception
     */
    List<OggettoIstanzaNatura2000ExtendedDTO> getSitiNatura2000() throws GenericException;

    /**
     * Gets siti natura 2000 by cod istat comuni.
     *
     * @param codIstatComuni the cod istat comuni
     * @return the siti natura 2000 by cod istat comuni
     * @throws GenericException the generic exception
     */
    List<OggettoIstanzaNatura2000ExtendedDTO> getSitiNatura2000ByCodIstatComuni(String codIstatComuni) throws GenericException;

    /**
     * Gets siti natura 2000.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param checkComuni      the check comuni
     * @return the siti natura 2000
     * @throws GenericException the generic exception
     */
    List<OggettoIstanzaNatura2000ExtendedDTO> getSitiNatura2000(Long idOggettoIstanza, Boolean checkComuni) throws GenericException;

    /**
     * Gets siti natura 2000 by cod istat comuni.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the siti natura 2000 by cod istat comuni
     * @throws GenericException the generic exception
     */
    List<OggettoIstanzaNatura2000ExtendedDTO> getSitiNatura2000ByCodIstatComuni(Long idOggettoIstanza) throws GenericException;

    /**
     * Gets siti natura 2000 by geometry.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the siti natura 2000 by geometry
     * @throws GenericException the generic exception
     */
    List<OggettoIstanzaNatura2000ExtendedDTO> getSitiNatura2000ByGeometry(Long idOggettoIstanza) throws GenericException;

}