/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.service.impl;

import it.csi.scriva.scrivabesrv.business.be.impl.BaseApiServiceImpl;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.PagamentoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.service.PagamentiService;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.IstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.PagamentoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.StatoPagamentoEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.StatoSintesiPagamentoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Pagamenti service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class PagamentiServiceImpl extends BaseApiServiceImpl implements PagamentiService {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Istanza dao.
     */
    @Autowired
    IstanzaDAO istanzaDAO;

    /**
     * The Pagamento istanza dao.
     */
    @Autowired
    PagamentoIstanzaDAO pagamentoIstanzaDAO;


    @Override
    public StatoSintesiPagamentoEnum getStatoSintesiPagamentoByIdIstanza(Long idIstanza, String componenteApp) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(className, methodName));
        List<PagamentoIstanzaExtendedDTO> pagamentoIstanzaList = pagamentoIstanzaDAO.loadPagamentoIstanzaByIdIstanza(idIstanza, componenteApp);
        if (pagamentoIstanzaList == null || pagamentoIstanzaList.isEmpty()) {
            LOGGER.debug(getClassFunctionEndInfo(className, methodName));
            return StatoSintesiPagamentoEnum.NON_PREVISTO;
        }

        List<PagamentoIstanzaExtendedDTO> pagamentoIstanzNOTaPagatoList = getPagamentoIstanzaFilteredByNOTCodiceStatoPagamento(pagamentoIstanzaList, StatoPagamentoEnum.PAGATO.getCodice());
        List<PagamentoIstanzaExtendedDTO> pagamentoIstanzaNOTInAttesaRicevutaList = getPagamentoIstanzaFilteredByNOTCodiceStatoPagamento(pagamentoIstanzaList, StatoPagamentoEnum.ATTESA_RICEVUTA.getCodice());
//        if ((pagamentoIstanzNOTaPagatoList != null && !pagamentoIstanzNOTaPagatoList.isEmpty()) || (pagamentoIstanzaNOTInAttesaRicevutaList != null && !pagamentoIstanzaNOTInAttesaRicevutaList.isEmpty())) {
        if (pagamentoIstanzNOTaPagatoList != null && pagamentoIstanzaNOTInAttesaRicevutaList != null && (pagamentoIstanzNOTaPagatoList.size() + pagamentoIstanzaNOTInAttesaRicevutaList.size() != pagamentoIstanzaList.size())) {
            LOGGER.debug(getClassFunctionEndInfo(className, methodName));
            return StatoSintesiPagamentoEnum.DA_EFFETTUARE;
        }

        List<PagamentoIstanzaExtendedDTO> pagamentoIstanzaNonDovutoList = getPagamentoIstanzaFilteredByFlgNonDovuto(pagamentoIstanzaList, Boolean.TRUE);
        if (pagamentoIstanzaNonDovutoList != null && !pagamentoIstanzaNonDovutoList.isEmpty() && pagamentoIstanzaNonDovutoList.size() == pagamentoIstanzaList.size()) {
            LOGGER.debug(getClassFunctionEndInfo(className, methodName));
            return StatoSintesiPagamentoEnum.NON_DOVUTO;
        }

        List<PagamentoIstanzaExtendedDTO> pagamentoIstanzaPagatoList = getPagamentoIstanzaFilteredByCodiceStatoPagamento(pagamentoIstanzaList, StatoPagamentoEnum.PAGATO.getCodice());
        List<PagamentoIstanzaExtendedDTO> pagamentoIstanzaInAttesaRicevutaList = getPagamentoIstanzaFilteredByCodiceStatoPagamento(pagamentoIstanzaList, StatoPagamentoEnum.ATTESA_RICEVUTA.getCodice());
        if (pagamentoIstanzaPagatoList != null && pagamentoIstanzaInAttesaRicevutaList != null && (pagamentoIstanzaPagatoList.size() + pagamentoIstanzaInAttesaRicevutaList.size() == pagamentoIstanzaList.size())) {
            LOGGER.debug(getClassFunctionEndInfo(className, methodName));
            return StatoSintesiPagamentoEnum.PAGATO;
        }

        LOGGER.debug(getClassFunctionEndInfo(className, methodName));
        return null;
    }

    /**
     * Sets stato sintesi pagamento.
     *
     * @param idIstanza             the id istanza
     * @param statoSintesiPagamento the stato sintesi pagamento
     * @param attoreScriva          the attore scriva
     * @return the stato sintesi pagamento
     */
    @Override
    public Integer setStatoSintesiPagamento(Long idIstanza, StatoSintesiPagamentoEnum statoSintesiPagamento, AttoreScriva attoreScriva) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(className, methodName));
        int result = 0;
        if (idIstanza != null && statoSintesiPagamento != null && attoreScriva != null) {
            try {
                IstanzaDTO istanza = istanzaDAO.findByPK(idIstanza);
                if (istanza != null) {
                    istanza.setDesStatoSintesiPagamento(statoSintesiPagamento.getDescrizione());
                    istanza.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
                    result = istanzaDAO.updateIstanza(istanza, Boolean.FALSE, Boolean.FALSE);
                }
            } catch (Exception e) {
                LOGGER.error(getClassFunctionErrorInfo(className, methodName, e));
            } finally {
                LOGGER.debug(getClassFunctionEndInfo(className, methodName));
            }
        }
        return result;
    }


    private List<PagamentoIstanzaExtendedDTO> getPagamentoIstanzaFilteredByCodiceStatoPagamento(List<PagamentoIstanzaExtendedDTO> pagamentoIstanzaList, String codiceStatoPagamento) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(className, methodName));
        return pagamentoIstanzaList.stream()
                .filter(pagamentoIstanza -> !pagamentoIstanza.getFlgNonDovuto() && codiceStatoPagamento.equalsIgnoreCase(pagamentoIstanza.getStatoPagamento().getCodiceStatoPagamento()))
                .collect(Collectors.toList());
    }

    private List<PagamentoIstanzaExtendedDTO> getPagamentoIstanzaFilteredByNOTCodiceStatoPagamento(List<PagamentoIstanzaExtendedDTO> pagamentoIstanzaList, String codiceStatoPagamento) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(className, methodName));
        return pagamentoIstanzaList.stream()
                .filter(pagamentoIstanza -> !pagamentoIstanza.getFlgNonDovuto() && !pagamentoIstanza.getStatoPagamento().getCodiceStatoPagamento().equalsIgnoreCase(codiceStatoPagamento))
                .collect(Collectors.toList());
    }

    private List<PagamentoIstanzaExtendedDTO> getPagamentoIstanzaFilteredByFlgNonDovuto(List<PagamentoIstanzaExtendedDTO> pagamentoIstanzaList, boolean flgNonDovuto) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(className, methodName));
        return pagamentoIstanzaList.stream()
                .filter(pagamentoIstanza -> pagamentoIstanza.getFlgNonDovuto() == flgNonDovuto)
                .collect(Collectors.toList());
    }

}