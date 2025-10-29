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

import it.csi.scriva.scrivabesrv.dto.CategoriaAllegatoDTO;

import java.util.List;

/**
 * The interface Categoria allegato dao.
 *
 * @author CSI PIEMONTE
 */
public interface CategoriaAllegatoDAO {

    /**
     * Load tipologie allegato list.
     *
     * @return the list
     */
    List<CategoriaAllegatoDTO> loadTipologieAllegato(String codCategAllegato);

    /**
     * Lista AdempimentiTipoAllegato per idAdempimento
     *
     * @param idAdempimento idAdempimento
     * @return List<AdempimentoTipoAllegatoExtendedDTO> list
     */
    List<CategoriaAllegatoDTO> loadCategoriaAllegatoByIdAdempimento(Long idAdempimento, String codComponenteApp);

    /**
     * Lista AdempimentiTipoAllegato per codAdempimento
     *
     * @param codAdempimento codAdempimento
     * @return List<AdempimentoTipoAllegatoExtendedDTO> list
     */
    List<CategoriaAllegatoDTO> loadCategoriaAllegatoByCodeAdempimento(String codAdempimento, String codComponenteApp);


    /**
     * Verifica presenza degli allegati obbligatori
     *
     * @param idIstanza idIstanza
     * @return List<CategoriaAllegatoDTO> list
     */
    List<CategoriaAllegatoDTO> loadCategoriaAllegatoMandatoryByIdIstanza(Long idIstanza, String codComponenteApp);

    /**
     * Controllo su allegati assegnati a categorie non legate all'adempimento dell'istanza
     *
     * @param idIstanza idIstanza
     * @return List<CategoriaAllegatoDTO> list
     */
    List<CategoriaAllegatoDTO> loadCategoriaAllegatoMandatoryAdempimentoByIdIstanza(Long idIstanza);

}