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

import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.enumeration.StatoSintesiPagamentoEnum;

/**
 * The interface Pagamenti service.
 *
 * @author CSI PIEMONTE
 */
public interface PagamentiService {

    /**
     * Gets stato sintesi pagamento by id istanza.
     *
     * @param idIstanza     the id istanza
     * @param componenteApp the componente app
     * @return the stato sintesi pagamento by id istanza
     */
    StatoSintesiPagamentoEnum getStatoSintesiPagamentoByIdIstanza(Long idIstanza, String componenteApp);

    /**
     * Sets stato sintesi pagamento.
     *
     * @param idIstanza             the id istanza
     * @param statoSintesiPagamento the stato sintesi pagamento
     * @param attoreScriva          the attore scriva
     * @return the stato sintesi pagamento
     */
    Integer setStatoSintesiPagamento(Long idIstanza, StatoSintesiPagamentoEnum statoSintesiPagamento, AttoreScriva attoreScriva);

}