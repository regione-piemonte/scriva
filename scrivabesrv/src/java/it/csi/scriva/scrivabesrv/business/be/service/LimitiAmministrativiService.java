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

import it.csi.scriva.scrivabesrv.dto.ComuneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.NazioneDTO;
import it.csi.scriva.scrivabesrv.dto.ProvinciaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.RegioneExtendedDTO;

import java.util.List;

/**
 * The interface Limiti amministrativi service.
 *
 * @author CSI PIEMONTE
 */
public interface LimitiAmministrativiService {

    /**
     * Load nazioni list.
     *
     * @param codIstat the cod istat
     * @return the list
     */
    List<NazioneDTO> loadNazioni(String codIstat);

    /**
     * Load nazioni attive list.
     *
     * @return the list
     */
    List<NazioneDTO> loadNazioniAttive();

    /**
     * Load regioni list.
     *
     * @param codIstat        the cod istat
     * @param codIstatNazione the cod istat nazione
     * @return the list
     */
    List<RegioneExtendedDTO> loadRegioni(String codIstat, String codIstatNazione);

    /**
     * Load regioni attive list.
     *
     * @return the list
     */
    List<RegioneExtendedDTO> loadRegioniAttive();

    /**
     * Load province list.
     *
     * @param codIstat        the cod istat
     * @param codIstatRegione the cod istat regione
     * @param idAdempimento   the id adempimento
     * @return the list
     */
    List<ProvinciaExtendedDTO> loadProvince(String codIstat, String codIstatRegione, Long idAdempimento);

    /**
     * Load province attive list.
     *
     * @return the list
     */
    List<ProvinciaExtendedDTO> loadProvinceAttive();

    /**
     * Load comuni list.
     *
     * @param codIstat          the cod istat
     * @param codIstatProvincia the cod istat provincia
     * @param siglaProvincia    the sigla provincia
     * @return the list
     */
    List<ComuneExtendedDTO> loadComuni(String codIstat, String codIstatProvincia, String siglaProvincia);

    /**
     * Load comuni list.
     *
     * @param codIstatList the cod istat list
     * @return the list
     */
    List<ComuneExtendedDTO> loadComuni(List<String> codIstatList);

    /**
     * Load comuni attivi list.
     *
     * @return the list
     */
    List<ComuneExtendedDTO> loadComuniAttivi();

}