/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.impl.dao;

import it.csi.scriva.scrivabesrv.dto.ClasseAllegatoDTO;

import java.util.List;

/**
 * The interface Classe allegato dao.
 *
 * @author CSI PIEMONTE
 */
public interface ClasseAllegatoDAO {

    /**
     * Load classe allegato list.
     *
     * @return the list
     */
    List<ClasseAllegatoDTO> loadClasseAllegato();

    /**
     * Load classe allegato list.
     *
     * @param codClasseAllegato the cod classe allegato
     * @return the list
     */
    List<ClasseAllegatoDTO> loadClasseAllegatoByCode(String codClasseAllegato);

    /**
     * Load classe allegato by id adempimento list.
     *
     * @param idAdempimento    the id adempimento
     * @param codComponenteApp the cod componente app
     * @return the list
     */
    List<ClasseAllegatoDTO> loadClasseAllegatoByIdAdempimento(Long idAdempimento, String codComponenteApp);

    /**
     * Load classe allegato by id adempimento stato istanza list.
     *
     * @param idAdempimento    the id adempimento
     * @param idStatoIstanza   the id stato istanza
     * @param codComponenteApp the cod componente app
     * @return the list
     */
    List<ClasseAllegatoDTO> loadClasseAllegatoByIdAdempimentoStatoIstanza(Long idAdempimento, Long idStatoIstanza, String codComponenteApp);

    /**
     * Load classe allegato by id istanza list.
     *
     * @param idIstanza        the id istanza
     * @param codComponenteApp the cod componente app
     * @return the list
     */
    List<ClasseAllegatoDTO> loadClasseAllegatoByIdIstanza(Long idIstanza, String codComponenteApp);

    /**
     * Load classe allegato by id istanza config list.
     *
     * @param idIstanza             the id istanza
     * @param codInformazioneScriva the cod informazione scriva
     * @param codTipologiaAllegato  the cod tipologia allegato
     * @return the list
     */
    List<ClasseAllegatoDTO> loadClasseAllegatoByIdIstanzaConfig(Long idIstanza, String codInformazioneScriva, String codTipologiaAllegato);

}