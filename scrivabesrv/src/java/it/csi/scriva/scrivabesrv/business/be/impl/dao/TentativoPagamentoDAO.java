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

import it.csi.scriva.scrivabesrv.dto.TentativoPagamentoDTO;
import it.csi.scriva.scrivabesrv.dto.TentativoPagamentoExtendedDTO;

import java.util.List;

/**
 * The interface Tentativo pagamento dao.
 */
public interface TentativoPagamentoDAO {

    /**
     * Load tentativi pagamento list.
     *
     * @return the list
     */
    List<TentativoPagamentoExtendedDTO> loadTentativiPagamento();

    /**
     * Load tentativo pagamento by id list.
     *
     * @param idTentativoPagamento the id pagamento istanza
     * @return the list
     */
    List<TentativoPagamentoExtendedDTO> loadTentativoPagamentoById(Long idTentativoPagamento);

    /**
     * Load tentativo pagamento by identificativo pagamento p pay list.
     *
     * @param identificativoPagamentoPPay the identificativo pagamento p pay
     * @return the list
     */
    List<TentativoPagamentoExtendedDTO> loadTentativoPagamentoByIdentificativoPagamentoPPay(String identificativoPagamentoPPay);

    /**
     * Load tentativo pagamento by id pagamento istanza list.
     *
     * @param idPagamentoIstanza the id pagamento istanza
     * @return the list
     */
    List<TentativoPagamentoExtendedDTO> loadTentativoPagamentoByIdPagamentoIstanza(Long idPagamentoIstanza);

    /**
     * Load tentativo pagamento by id istanza list.
     *
     * @param idIstanza      the id istanza
     * @param idStatoTentivo the id stato tentivo
     * @return the list
     */
    List<TentativoPagamentoExtendedDTO> loadTentativoPagamentoByIdIstanza(Long idIstanza, Long idStatoTentivo);

    /**
     * Save tentativo pagaemnto long.
     *
     * @param dto the dto
     * @return the long
     */
    Long saveTentativoPagamento(TentativoPagamentoDTO dto);

    /**
     * Update tentativo pagaemnto integer.
     *
     * @param dto the dto
     * @return the integer
     */
    Integer updateTentativoPagamento(TentativoPagamentoDTO dto);

    /**
     * Delete tentativo pagaemnto integer.
     *
     * @param uid the uid
     * @return the integer
     */
    Integer deleteTentativoPagaemnto(String uid);

}