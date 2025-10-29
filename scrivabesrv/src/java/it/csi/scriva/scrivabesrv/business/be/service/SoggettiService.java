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

import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.ComuneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.NazioneDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoNaturaGiuridicaDTO;
import it.csi.scriva.scrivabesrv.dto.TipoSoggettoDTO;

/**
 * The interface Soggetti service.
 *
 * @author CSI PIEMONTE
 */
public interface SoggettiService {

    /**
     * Save soggetto long.
     *
     * @param soggetto     the soggetto
     * @param attoreScriva the attore scriva
     * @return the long
     */
    Long saveSoggetto(SoggettoExtendedDTO soggetto, AttoreScriva attoreScriva);

    /**
     * Update soggetto integer.
     *
     * @param soggetto     the soggetto
     * @param attoreScriva the attore scriva
     * @return the integer
     */
    Integer updateSoggetto(SoggettoExtendedDTO soggetto, AttoreScriva attoreScriva);

    /**
     * Gets comune by cod istat.
     *
     * @param codIstatComune the cod istat comune
     * @return the comune by cod istat
     */
    ComuneExtendedDTO getComuneByCodIstat(String codIstatComune);

    /**
     * Gets nazione by cod istat.
     *
     * @param codIstatNazione the cod istat nazione
     * @return the nazione by cod istat
     */
    NazioneDTO getNazioneByCodIstat(String codIstatNazione);

    /**
     * Gets tipo soggetto by code.
     *
     * @param codTipoSoggetto the cod tipo soggetto
     * @return the tipo soggetto by code
     */
    TipoSoggettoDTO getTipoSoggettoByCode(String codTipoSoggetto);

    /**
     * Gets tipo natura giuridica by code.
     *
     * @param codTipoNaturaGiuridica the cod tipo natura giuridica
     * @return the tipo natura giuridica by code
     */
    TipoNaturaGiuridicaDTO getTipoNaturaGiuridicaByCode(String codTipoNaturaGiuridica);

    /**
     * Validate dto error dto.
     *
     * @param soggetto the soggetto
     * @return the error dto
     */
    ErrorDTO validateDTO(SoggettoExtendedDTO soggetto);

}