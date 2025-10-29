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

import it.csi.scriva.scrivabesrv.dto.AdempimentoEstensioneAllegatoDTO;

import java.util.List;

/**
 * The interface Adempimento estensione allegato dao.
 *
 * @author CSI PIEMONTE
 */
public interface AdempimentoEstensioneAllegatoDAO {

    /**
     * Load adempimenti estensioni allegati list.
     *
     * @return List<AdempimentoEstensioneAllegatoDTO> list
     */
    List<AdempimentoEstensioneAllegatoDTO> loadAdempimentiEstensioniAllegati();

    /**
     * Load adempimento estensione allegato by id adempimento list.
     *
     * @param idAdempimento idAdempimento
     * @return List<AdempimentoEstensioneAllegatoDTO> list
     */
    List<AdempimentoEstensioneAllegatoDTO> loadAdempimentoEstensioneAllegatoByIdAdempimento(Long idAdempimento);

    /**
     * Load adempimento estensione allegato by cod adempimento list.
     *
     * @param codAdempimento codice adempimento
     * @return List<AdempimentoEstensioneAllegatoDTO> list
     */
    List<AdempimentoEstensioneAllegatoDTO> loadAdempimentoEstensioneAllegatoByCodAdempimento(String codAdempimento);


}