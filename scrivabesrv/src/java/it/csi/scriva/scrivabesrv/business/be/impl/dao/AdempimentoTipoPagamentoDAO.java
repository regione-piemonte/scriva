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

import it.csi.scriva.scrivabesrv.dto.AdempimentoTipoPagamentoExtendedDTO;

import java.util.List;

/**
 * The interface Riscossione ente dao.
 */
public interface AdempimentoTipoPagamentoDAO {

    /**
     * Load tipo pagamento per adempimento list.
     *
     * @return the list
     */
    List<AdempimentoTipoPagamentoExtendedDTO> loadAdempimentoTipiPagamento();

    /**
     * Load tipo pagamento per adempimento by id list.
     *
     * @param idAdempiTipoPagamento the id adempi tipo pagamento
     * @return the list
     */
    List<AdempimentoTipoPagamentoExtendedDTO> loadAdempimentoTipoPagamentoById(Long idAdempiTipoPagamento);

    /**
     * Load tipo pagamento per adempimento by id istanza list.
     *
     * @param idIstanza     the id istanza
     * @param componenteApp the componente app
     * @return the list
     */
    List<AdempimentoTipoPagamentoExtendedDTO> loadAdempimentoTipoPagamentoIdIstanza(Long idIstanza, String componenteApp);

}