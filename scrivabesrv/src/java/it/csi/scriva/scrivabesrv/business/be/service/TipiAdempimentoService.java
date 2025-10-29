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

import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoExtendedDTO;

import java.util.List;

/**
 * The interface Tipi adempimento service.
 *
 * @author CSI PIEMONTE
 */
public interface TipiAdempimentoService {

    /**
     * Load ambito list.
     *
     * @param idAmbito           the id ambito
     * @param idCompilante       the id compilante
     * @param codTipoAdempimento the cod tipo adempimento
     * @param codAmbito          the cod ambito
     * @return the list
     */
    List<TipoAdempimentoExtendedDTO> loadTipiAdempimento(Long idAmbito, Long idCompilante, String codTipoAdempimento, String codAmbito, Boolean flgAttivo);

    /**
     * Load ambito list.
     *
     * @param idTipoAdempimento the id tipo adempimento
     * @return the list
     */
    List<TipoAdempimentoExtendedDTO> loadTipoAdempimento(Long idTipoAdempimento);

    /**
     * Load tipi adempimento by cf funzionario list.
     *
     * @param cfFunzionario the cf funzionario
     * @return the list
     */
    List<TipoAdempimentoExtendedDTO> loadTipiAdempimentoByCfFunzionario(String cfFunzionario, Long idAmbito, String codTipoAdempimento, String codAmbito, Boolean flgAttivo);

}