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

import it.csi.scriva.scrivabesrv.dto.AdempimentoTipoAllegatoExtendedDTO;

import java.util.List;

/**
 * The interface Adempimento tipo allegato dao.
 *
 * @author CSI PIEMONTE
 */
public interface AdempimentoTipoAllegatoDAO {

    /**
     * Load tipologie allegato list.
     *
     * @return the list
     */
    List<AdempimentoTipoAllegatoExtendedDTO> loadTipologieAllegato();

    /**
     * Lista AdempimentiTipoAllegato per idAdempimento
     *
     * @param idAdempimento    idAdempimento
     * @param codComponenteApp the cod componente app
     * @return List<AdempimentoTipoAllegatoExtendedDTO>    list
     */
    List<AdempimentoTipoAllegatoExtendedDTO> loadTipologieAllegatoByIdAdempimento(Long idAdempimento, String codComponenteApp);

    /**
     * Lista AdempimentiTipoAllegato per codAdempimento
     *
     * @param codAdempimento   codAdempimento
     * @param codComponenteApp the cod componente app
     * @return List<AdempimentoTipoAllegatoExtendedDTO>    list
     */
    List<AdempimentoTipoAllegatoExtendedDTO> loadTipologieAllegatoByCodeAdempimento(String codAdempimento, String codComponenteApp);

    /**
     * Loads a list of AdempimentoTipoAllegatoExtendedDTO based on the provided 
     * adempimento code, tipologia allegato code, and componente.
     * no filter for  ind_modifica
     *
     * @param codAdempimento the code of the adempimento
     * @param codTipologiaAllegato the code of the tipologia allegato
     * @param componente the component App
     * @return a list of AdempimentoTipoAllegatoExtendedDTO matching the specified criteria
     */
    List<AdempimentoTipoAllegatoExtendedDTO> loadTipologiaAllegatoByCodeAdempimentoAndCodeTipologiaAllegato(String codAdempimento, String codTipologiaAllegato, String componente);


    /**
     * Lista AdempimentiTipoAllegato per codAdempimento e codCategoria
     *
     * @param codAdempimento   codAdempimento
     * @param codCategoria     codCategoria
     * @param codComponenteApp the cod componente app
     * @return List<AdempimentoTipoAllegatoExtendedDTO>    list
     */
    List<AdempimentoTipoAllegatoExtendedDTO> loadTipologieAllegatoByCodeAdempimentoAndCodeCategoria(String codAdempimento, String codCategoria, String codComponenteApp);

    /**
     * Lista AdempimentiTipoAllegato per idAdempimento e idTipologiaAllegato
     *
     * @param idAdempimento       idAdempimento
     * @param idTipologiaAllegato idTipologiaAllegato
     * @param codComponenteApp    the cod componente app
     * @return List<AdempimentoTipoAllegatoExtendedDTO>    list
     */
    List<AdempimentoTipoAllegatoExtendedDTO> loadTipologieAllegatoByIdAdempimentoAndIdTipologiaAllegato(Long idAdempimento, Long idTipologiaAllegato, String codComponenteApp);

    /**
     * Load tipologie allegato by istanza vincolo aut list.
     *
     * @param idIstanza        the id istanza
     * @param codComponenteApp the cod componente app
     * @return the list
     */
    List<AdempimentoTipoAllegatoExtendedDTO> loadTipologieAllegatoByIstanzaVincoloAut(Long idIstanza, String codComponenteApp);

    /**
     * Load tipologie allegato by cod allegato list.
     *
     * @param codAllegato the cod allegato
     * @return the list
     */
    List<AdempimentoTipoAllegatoExtendedDTO> loadTipologieAllegatoByCodAllegato(String codAllegato, Long idAdempimento);

    /**
     * Load cod tipologia allegato by id istanza string.
     *
     * @param idIstanza the id istanza
     * @return the string
     */
    String loadCodTipologiaAllegatoByIdIstanza(Long idIstanza);


}