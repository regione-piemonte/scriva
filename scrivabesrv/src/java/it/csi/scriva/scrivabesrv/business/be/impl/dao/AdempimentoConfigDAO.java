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

import it.csi.scriva.scrivabesrv.dto.AdempimentoConfigDTO;

import java.util.List;

/**
 * The interface Adempimento config dao.
 *
 * @author CSI PIEMONTE
 */
public interface AdempimentoConfigDAO {

    /**
     * Load adempimenti config list.
     *
     * @param codTipoAdempimento the cod tipo adempimento
     * @param info               the info
     * @return List<AdempimentoConfigDTO>   list
     */
    List<AdempimentoConfigDTO> loadAdempimentiConfig(String codTipoAdempimento, String info);

    /**
     * Load adempimento config by adempimento list.
     *
     * @param codAdempimento codAdempimento
     * @return List<AdempimentoConfigDTO>   list
     */
    List<AdempimentoConfigDTO> loadAdempimentoConfigByAdempimento(String codAdempimento);

    /**
     * Load adempimento config by adempimento informazione list.
     *
     * @param codAdempimento codAdempimento
     * @param infoScriva     infoScriva
     * @return List<AdempimentoConfigDTO>   list
     */
    List<AdempimentoConfigDTO> loadAdempimentoConfigByAdempimentoInformazione(String codAdempimento, String infoScriva);

    /**
     * Load adempimento config by adempimento informazione chiave list.
     *
     * @param codAdempimento codAdempimento
     * @param infoScriva     infoScriva
     * @param chiave         chiave
     * @return List<AdempimentoConfigDTO>   list
     */
    List<AdempimentoConfigDTO> loadAdempimentoConfigByAdempimentoInformazioneChiave(String codAdempimento, String infoScriva, String chiave);

        /**
     * Load adempimento config by adempimento informazione chiave list.
     *
     * @param codAdempimento codAdempimento
     * @param infoScriva     infoScriva
     * @param chiave         chiave
     * @return List<AdempimentoConfigDTO>   list
     */
    List<AdempimentoConfigDTO> loadAdempimentoConfigByAdempimentoComponenteChiave(String codAdempimento, String componenteApp, String chiave);

    /**
     * Load adempimento config by id istanza informazione chiave list.
     *
     * @param idIstanza  the id istanza
     * @param infoScriva the info scriva
     * @param chiave     the chiave
     * @return the list
     */
    List<AdempimentoConfigDTO> loadAdempimentoConfigByIdIstanzaInformazioneChiave(Long idIstanza, String infoScriva, String chiave);

	/**
	*
	* @param idAdempimento
	* @param chiave
	* @return
	*
	*/
	List<AdempimentoConfigDTO> loadAdempimentoConfigByIdAdempimentoChiave(Long idAdempimento, String chiave);

}