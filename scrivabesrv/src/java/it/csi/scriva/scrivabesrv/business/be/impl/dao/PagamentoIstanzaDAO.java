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

import it.csi.scriva.scrivabesrv.dto.PagamentoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.PagamentoIstanzaExtendedDTO;

import java.util.List;

/**
 * The interface Pagamento istanza dao.
 */
public interface PagamentoIstanzaDAO {

    /**
     * Load pagamenti istanza list.
     *
     * @return the list
     */
    List<PagamentoIstanzaExtendedDTO> loadPagamentiIstanza();

    /**
     * Load pagamento istanza by id list.
     *
     * @param idPagamentoIstanza the id pagamento istanza
     * @return the list
     */
    List<PagamentoIstanzaExtendedDTO> loadPagamentoIstanzaById(Long idPagamentoIstanza);

    /**
     * Find by pk pagamento istanza dto.
     *
     * @param idPagamentoIstanza the id pagamento istanza
     * @return the pagamento istanza dto
     */
    PagamentoIstanzaDTO findByPK(Long idPagamentoIstanza);

    /**
     * Load pagamento istanza hierarchy by id list.
     *
     * @param idPagamentoIstanza the id pagamento istanza
     * @return the list
     */
    List<PagamentoIstanzaExtendedDTO> loadPagamentoIstanzaHierarchyById(Long idPagamentoIstanza);

    /**
     * Load pagamento istanza id istanza list.
     *
     * @param idIstanza     the id istanza
     * @param componenteApp the componente app
     * @return the list
     */
    List<PagamentoIstanzaExtendedDTO> loadPagamentoIstanzaByIdIstanza(Long idIstanza, String componenteApp);

    /**
     * Load pagamento istanza by id istanza ente cred cod versamento list.
     *
     * @param idIstanza           the id istanza
     * @param idEnteCreditore     the id ente creditore
     * @param codeGruppiPagamento the code gruppi pagamento
     * @return the list
     */
    List<PagamentoIstanzaDTO> loadPagamentoIstanzaByIdIstanzaEnteCredCodGruppoPagamento(Long idIstanza, Long idEnteCreditore, List<String> codeGruppiPagamento);

    /**
     * Load pagamento istanza by id istanza stato pagamento list.
     *
     * @param idIstanza              the id istanza
     * @param codeStatiPagamentoList the code stati pagamento list
     * @param notIn                  the not in
     * @return the list
     */
    List<PagamentoIstanzaDTO> loadPagamentoIstanzaByIdIstanzaStatoPagamento(Long idIstanza, List<String> codeStatiPagamentoList, boolean notIn);

    /**
     * Save pagamento istanza long.
     *
     * @param pagamentoIstanza the pagamento istanza
     * @return the long
     */
    Long savePagamentoIstanza(PagamentoIstanzaDTO pagamentoIstanza);

    /**
     * Update pagamento istanza integer.
     *
     * @param pagamentoIstanza the pagamento istanza
     * @return the integer
     */
    Integer updatePagamentoIstanza(PagamentoIstanzaDTO pagamentoIstanza);

    /**
     * Delete pagamento istanza integer.
     *
     * @param uid the gest uid
     * @return the integer
     */
    Integer deletePagamentoIstanza(String uid);

}