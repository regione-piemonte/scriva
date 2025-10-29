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

import it.csi.scriva.scrivabesrv.dto.TentativoDettaglioDTO;
import it.csi.scriva.scrivabesrv.dto.TentativoDettaglioExtendedDTO;

import java.util.List;

/**
 * The interface Tentativo dettaglio dao.
 *
 * @author CSI PIEMONTE
 */
public interface TentativoDettaglioDAO {

    /**
     * Load tentativi dettaglio list.
     *
     * @return the list
     */
    List<TentativoDettaglioExtendedDTO> loadTentativiDettaglio();

    /**
     * Load tentativo dettaglio by id pagamento istanza list.
     *
     * @param idPagamentoIstanza the id pagamento istanza
     * @return the list
     */
    List<TentativoDettaglioExtendedDTO> loadTentativoDettaglioByIdPagamentoIstanza(Long idPagamentoIstanza);

    /**
     * Load tentativo dettaglio by id pagamento istanza for pag ist list.
     *
     * @param idPagamentoIstanza the id pagamento istanza
     * @return the list
     */
    List<TentativoDettaglioExtendedDTO> loadTentativoDettaglioByIdPagamentoIstanzaForPagIst(Long idPagamentoIstanza);


    /**
     * Load tentativo dettaglio by id tentativo pagamento list.
     *
     * @param idTentativoPagamento the id tentativo pagamento
     * @return the list
     */
    List<TentativoDettaglioExtendedDTO> loadTentativoDettaglioByIdTentativoPagamento(Long idTentativoPagamento);

    /**
     * Load tentativo dettaglio by id tentativo pagamento list.
     *
     * @param idTentativoPagamento the id tentativo pagamento
     * @return the list
     */
    List<TentativoDettaglioExtendedDTO> loadTentativoDettaglioExtendedByIdTentativoPagamento(Long idTentativoPagamento);

    /**
     * Save tentativo dettaglio long.
     *
     * @param tentativoDettaglio the tentativo dettaglio
     * @return the long
     */
    Long saveTentativoDettaglio(TentativoDettaglioDTO tentativoDettaglio);

    /**
     * Update tentativo dettaglio integer.
     *
     * @param tentativoDettaglio the tentativo dettaglio
     * @return the integer
     */
    Integer updateTentativoDettaglio(TentativoDettaglioDTO tentativoDettaglio);

    /**
     * Delete tentativo dettaglio integer.
     *
     * @param uid the uid
     * @return the integer
     */
    Integer deleteTentativoDettaglio(String uid);

}