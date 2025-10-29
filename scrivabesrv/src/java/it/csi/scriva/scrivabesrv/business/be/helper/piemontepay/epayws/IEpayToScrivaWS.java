/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.piemontepay.epayws;


import it.csi.epay.epaywso.epaywso2enti.types.TrasmettiNotifichePagamentoRequest;
import it.csi.epay.epaywso.types.ResponseType;

public interface IEpayToScrivaWS {
	
	public ResponseType TrasmettiNotifichePagamento(TrasmettiNotifichePagamentoRequest parameters);

}
