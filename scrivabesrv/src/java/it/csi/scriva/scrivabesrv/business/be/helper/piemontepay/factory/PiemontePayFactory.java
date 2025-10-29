/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.piemontepay.factory;

import it.csi.scriva.scrivabesrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.helper.piemontepay.dto.PPayComponentePagamentoDTO;
import it.csi.scriva.scrivabesrv.business.be.helper.piemontepay.dto.PPayPagamentoMarcaBolloInDTO;
import it.csi.scriva.scrivabesrv.business.be.helper.piemontepay.dto.PPayRtInDTO;
import it.csi.scriva.scrivabesrv.dto.CompilanteDTO;
import it.csi.scriva.scrivabesrv.dto.PagamentoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TentativoDettaglioExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TentativoPagamentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.GruppoPagamentoEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Piemonte pay factory.
 */
public class PiemontePayFactory extends AbstractServiceHelper {

    /**
     * Create p pay pagamento marca bollo input p pay pagamento marca bollo in dto.
     *
     * @param compilante              the compilante
     * @param tentativoPagamento      the tentativo pagamento
     * @param provincia               the provincia
     * @param importoBollo            the importo bollo
     * @param marcaFittizia           the marca fittizia
     * @param iuvAssociata            the iuv associata
     * @param importoTotaleComponenti the importo totale componenti
     * @return the p pay pagamento marca bollo in dto
     */
    public static PPayPagamentoMarcaBolloInDTO createPPayPagamentoMarcaBolloInput(CompilanteDTO compilante, TentativoPagamentoExtendedDTO tentativoPagamento, String provincia, BigDecimal importoBollo, int marcaFittizia, String iuvAssociata, BigDecimal importoTotaleComponenti) {
        List<TentativoDettaglioExtendedDTO> tentativoDettaglioList = tentativoPagamento.getTentativoDettaglio();
        List<TentativoDettaglioExtendedDTO> tentativoDettaglioBolloList = tentativoDettaglioList != null ?
                tentativoDettaglioList.stream()
                        .filter(tentativoDett -> tentativoDett.getPagamentoIstanza()
                                .getRiscossioneEnte()
                                .getAdempimentoTipoPagamento()
                                .getTipoPagamento()
                                .getGruppoPagamento()
                                .getCodiceGruppoPagamento().equalsIgnoreCase(GruppoPagamentoEnum.MARCA_BOLLO.name()))
                        .collect(Collectors.toList())
                : new ArrayList<>();

        List<TentativoDettaglioExtendedDTO> tentativoDettaglioNoBolloList = tentativoDettaglioList != null ?
                tentativoDettaglioList.stream()
                        .filter(tentativoDett -> !tentativoDett.getPagamentoIstanza()
                                .getRiscossioneEnte()
                                .getAdempimentoTipoPagamento()
                                .getTipoPagamento()
                                .getGruppoPagamento()
                                .getCodiceGruppoPagamento().equalsIgnoreCase(GruppoPagamentoEnum.MARCA_BOLLO.name()))
                        .collect(Collectors.toList())
                : new ArrayList<>();

        List<TentativoDettaglioExtendedDTO> tentativoDettaglioOnereList = tentativoDettaglioList != null ?
                tentativoDettaglioList.stream()
                        .filter(tentativoDett -> tentativoDett.getPagamentoIstanza()
                                .getRiscossioneEnte()
                                .getAdempimentoTipoPagamento()
                                .getTipoPagamento()
                                .getGruppoPagamento()
                                .getCodiceGruppoPagamento().equalsIgnoreCase(GruppoPagamentoEnum.ONERE.name()))
                        .collect(Collectors.toList())
                : new ArrayList<>();

        List<TentativoDettaglioExtendedDTO> tentativoDettaglioComponentiList = tentativoDettaglioList != null ?
                tentativoDettaglioList.stream()
                        .filter(tentativoDett ->
                                !tentativoDett.getPagamentoIstanza()
                                        .getRiscossioneEnte()
                                        .getAdempimentoTipoPagamento()
                                        .getTipoPagamento()
                                        .getGruppoPagamento()
                                        .getCodiceGruppoPagamento().equalsIgnoreCase(GruppoPagamentoEnum.ONERE.name())
                                        &&
                                        !tentativoDett.getPagamentoIstanza()
                                                .getRiscossioneEnte()
                                                .getAdempimentoTipoPagamento()
                                                .getTipoPagamento()
                                                .getGruppoPagamento()
                                                .getCodiceGruppoPagamento().equalsIgnoreCase(GruppoPagamentoEnum.MARCA_BOLLO.name()))
                        .collect(Collectors.toList())
                : new ArrayList<>();


        PPayPagamentoMarcaBolloInDTO pPayPagamentoMarcaBolloInDTO = new PPayPagamentoMarcaBolloInDTO();
        pPayPagamentoMarcaBolloInDTO.setIdentificativoPagamento(tentativoPagamento.getIdentificativoPagamentoPpay());
        pPayPagamentoMarcaBolloInDTO.setImporto(importoTotaleComponenti);
        pPayPagamentoMarcaBolloInDTO.setNome(compilante.getNomeCompilante());
        pPayPagamentoMarcaBolloInDTO.setCognome(compilante.getCognomeCompilante());
        pPayPagamentoMarcaBolloInDTO.setRagioneSociale(null);
        pPayPagamentoMarcaBolloInDTO.setEmail(compilante.getDesEmailCompilante());
        pPayPagamentoMarcaBolloInDTO.setCodiceFiscalePartitaIVAPagatore(compilante.getCodiceFiscaleCompilante());
        pPayPagamentoMarcaBolloInDTO.setHashDocumento(tentativoPagamento.getHashPagamento());
        pPayPagamentoMarcaBolloInDTO.setFlagSoloMarca(tentativoPagamento.getFlgSoloMarca());
        pPayPagamentoMarcaBolloInDTO.setProvincia(provincia); // parametro configurato
        pPayPagamentoMarcaBolloInDTO.setTipoBollo(tentativoPagamento.getTipoBollo());
        pPayPagamentoMarcaBolloInDTO.setQuantita(tentativoDettaglioBolloList.size()); // se Marca Fittizia allora = 0; diversamente sempre 1
        //pPayPagamentoMarcaBolloInDTO.setQuantita(marcaFittizia); // se Marca Fittizia allora = 0; diversamente sempre 1
        /*
        Si tratta dello iuv (staccato in precedenza) a cui fa riferimento il pagamento (la parte di tassa per così dire).
        Se indico null ma il flag solo marca è a false significa che sto facendo un pagamento contestuale di tassa e marca: in questo caso staccherò due iuv di
        modello 1, uno per il documento di istanza e uno per la marca.
        Se indico null ma il flag solo marca è a true significa che voglio pagare solo la marca da bollo e che non vi è una tassa. In questo caso anche il tipo
        pagamento sarà vuoto.
        */
        pPayPagamentoMarcaBolloInDTO.setIuvIstanzaAssociata(iuvAssociata);

        if (!tentativoDettaglioBolloList.isEmpty() && tentativoDettaglioOnereList.isEmpty() && tentativoDettaglioComponentiList.isEmpty()) { // SOLO MARCA DA BOLLO
            pPayPagamentoMarcaBolloInDTO.setCodiceFiscaleEnte(tentativoDettaglioBolloList.get(0).getPagamentoIstanza().getRiscossioneEnte().getAdempimentoTipoPagamento().getEnteCreditore().getCfEnteCreditore());
            pPayPagamentoMarcaBolloInDTO.setCausale(tentativoDettaglioBolloList.get(0).getPagamentoIstanza().getRiscossioneEnte().getDesPagamentoVersoCittadino());
            pPayPagamentoMarcaBolloInDTO.setTipoPagamento(tentativoDettaglioBolloList.get(0).getPagamentoIstanza().getRiscossioneEnte().getAdempimentoTipoPagamento().getCodiceVersamento());
            pPayPagamentoMarcaBolloInDTO.setImportoBollo(importoBollo); // parametro configurato
        } else if (!tentativoDettaglioBolloList.isEmpty() && !tentativoDettaglioOnereList.isEmpty() && tentativoDettaglioComponentiList.isEmpty()) { // MARCA DA BOLLO + ONERE
            pPayPagamentoMarcaBolloInDTO.setCodiceFiscaleEnte(tentativoDettaglioOnereList.get(0).getPagamentoIstanza().getRiscossioneEnte().getAdempimentoTipoPagamento().getEnteCreditore().getCfEnteCreditore());
            pPayPagamentoMarcaBolloInDTO.setCausale(tentativoDettaglioOnereList.get(0).getPagamentoIstanza().getRiscossioneEnte().getDesPagamentoVersoCittadino());
            pPayPagamentoMarcaBolloInDTO.setTipoPagamento(tentativoDettaglioOnereList.get(0).getPagamentoIstanza().getRiscossioneEnte().getAdempimentoTipoPagamento().getCodiceVersamento());
            pPayPagamentoMarcaBolloInDTO.setImportoBollo(importoBollo); // parametro configurato
        } else if (tentativoDettaglioBolloList.isEmpty() && !tentativoDettaglioOnereList.isEmpty() && tentativoDettaglioComponentiList.isEmpty()) { // SOLO ONERE
            pPayPagamentoMarcaBolloInDTO.setCodiceFiscaleEnte(tentativoDettaglioOnereList.get(0).getPagamentoIstanza().getRiscossioneEnte().getAdempimentoTipoPagamento().getEnteCreditore().getCfEnteCreditore());
            pPayPagamentoMarcaBolloInDTO.setCausale(tentativoDettaglioOnereList.get(0).getPagamentoIstanza().getRiscossioneEnte().getDesPagamentoVersoCittadino());
            pPayPagamentoMarcaBolloInDTO.setTipoPagamento(tentativoDettaglioOnereList.get(0).getPagamentoIstanza().getRiscossioneEnte().getAdempimentoTipoPagamento().getCodiceVersamento());
            pPayPagamentoMarcaBolloInDTO.setImportoBollo(new BigDecimal(0)); // parametro configurato
        } else if (tentativoDettaglioBolloList.isEmpty() && !tentativoDettaglioOnereList.isEmpty() && !tentativoDettaglioComponentiList.isEmpty()) { // ONERE + INTERESSI/SANZIONI
            pPayPagamentoMarcaBolloInDTO.setCodiceFiscaleEnte(tentativoDettaglioOnereList.get(0).getPagamentoIstanza().getRiscossioneEnte().getAdempimentoTipoPagamento().getEnteCreditore().getCfEnteCreditore());
            pPayPagamentoMarcaBolloInDTO.setCausale(tentativoDettaglioOnereList.get(0).getPagamentoIstanza().getRiscossioneEnte().getDesPagamentoVersoCittadino());
            pPayPagamentoMarcaBolloInDTO.setTipoPagamento(tentativoDettaglioOnereList.get(0).getPagamentoIstanza().getRiscossioneEnte().getAdempimentoTipoPagamento().getCodiceVersamento());
            pPayPagamentoMarcaBolloInDTO.setImportoBollo(new BigDecimal(0));
            pPayPagamentoMarcaBolloInDTO.setComponentiPagamento(createPPayComponentePagamentoListByTentativoDettaglio(tentativoDettaglioOnereList, tentativoDettaglioComponentiList));
        } else if (!tentativoDettaglioBolloList.isEmpty() && !tentativoDettaglioOnereList.isEmpty() && !tentativoDettaglioComponentiList.isEmpty()) { // MARCA DA BOLLO + ONERE + INTERESSI/SANZIONI
            pPayPagamentoMarcaBolloInDTO.setCodiceFiscaleEnte(tentativoDettaglioOnereList.get(0).getPagamentoIstanza().getRiscossioneEnte().getAdempimentoTipoPagamento().getEnteCreditore().getCfEnteCreditore());
            pPayPagamentoMarcaBolloInDTO.setCausale(tentativoDettaglioOnereList.get(0).getPagamentoIstanza().getRiscossioneEnte().getDesPagamentoVersoCittadino());
            pPayPagamentoMarcaBolloInDTO.setTipoPagamento(tentativoDettaglioOnereList.get(0).getPagamentoIstanza().getRiscossioneEnte().getAdempimentoTipoPagamento().getCodiceVersamento());
            pPayPagamentoMarcaBolloInDTO.setImportoBollo(importoBollo); // parametro configurato
            pPayPagamentoMarcaBolloInDTO.setComponentiPagamento(createPPayComponentePagamentoListByTentativoDettaglio(tentativoDettaglioOnereList, tentativoDettaglioComponentiList));
        }

        return pPayPagamentoMarcaBolloInDTO;
    }

    /**
     * Create p pay componente pagamento list by pagamento istanza list.
     *
     * @param pagamentoIstanzaList the pagamento istanza list
     * @return the list
     */
    public static List<PPayComponentePagamentoDTO> createPPayComponentePagamentoListByPagamentoIstanza(List<PagamentoIstanzaExtendedDTO> pagamentoIstanzaList) {
        List<PPayComponentePagamentoDTO> pPayComponentePagamentoList = new ArrayList<>();
        long progressivo = 0;
        if (pagamentoIstanzaList != null && !pagamentoIstanzaList.isEmpty()) {
            for (PagamentoIstanzaExtendedDTO pagamentoIstanza : pagamentoIstanzaList) {
                PPayComponentePagamentoDTO pPayComponentePagamento = new PPayComponentePagamentoDTO();
                pPayComponentePagamento.setProgressivo(++progressivo);
                pPayComponentePagamento.setImporto(pagamentoIstanza.getImportoDovuto());
                pPayComponentePagamento.setCausale(pagamentoIstanza.getRiscossioneEnte().getDesPagamentoVersoCittadino());
                pPayComponentePagamento.setDatiSpecificiRiscossione(pagamentoIstanza.getRiscossioneEnte().getDatiSpecificiRiscossione());
                pPayComponentePagamento.setAnnoAccertamento(pagamentoIstanza.getRiscossioneEnte().getAccertamentoAnno());
                pPayComponentePagamento.setNumeroAccertamento(String.valueOf(pagamentoIstanza.getRiscossioneEnte().getNumeroAccertamento()));
                pPayComponentePagamentoList.add(pPayComponentePagamento);
            }
        }
        return pPayComponentePagamentoList;
    }

    /**
     * Create p pay componente pagamento list by tentativo dettaglio list.
     *
     * @param tentativoDettaglioOnereList      the tentativo dettaglio onere list
     * @param tentativoDettaglioComponentiList the tentativo dettaglio componenti list
     * @return the list
     */
    public static List<PPayComponentePagamentoDTO> createPPayComponentePagamentoListByTentativoDettaglio(List<TentativoDettaglioExtendedDTO> tentativoDettaglioOnereList, List<TentativoDettaglioExtendedDTO> tentativoDettaglioComponentiList) {
        long progressivo = 0;
        List<PPayComponentePagamentoDTO> pPayComponentePagamentoList = new ArrayList<>();

        //aggiungo oneri (tassa)
        progressivo = getPPayComponentePagamentoByTentativoDettaglio(tentativoDettaglioOnereList, progressivo, pPayComponentePagamentoList);
        //aggiungo sanzioni/interessi (componenti)
        progressivo = getPPayComponentePagamentoByTentativoDettaglio(tentativoDettaglioComponentiList, progressivo, pPayComponentePagamentoList);

        return pPayComponentePagamentoList;
    }

    /**
     * Create p pay rt input p pay rt in dto.
     *
     * @param tentativoPagamento    the tentativo pagamento
     * @param tentativoDettaglio    the tentativo dettaglio
     * @param codiceFiscalePagatore the codice fiscale pagatore
     * @param codiceFiscaleEnte     the codice fiscale ente
     * @param formatoRT             the formato rt
     * @return the p pay rt in dto
     */
    public static PPayRtInDTO createPPayRTInput(TentativoPagamentoExtendedDTO tentativoPagamento, TentativoDettaglioExtendedDTO tentativoDettaglio, String codiceFiscalePagatore, String codiceFiscaleEnte, String formatoRT) {
        PPayRtInDTO pPayRtInput = new PPayRtInDTO();
        pPayRtInput.setIuv(tentativoDettaglio.getIuvTentativoPagamento());
        pPayRtInput.setCodiceFiscaleEnte(codiceFiscaleEnte);
        pPayRtInput.setCodiceFiscale(codiceFiscalePagatore);
        pPayRtInput.setIdentificativoPagamento(tentativoPagamento.getIdentificativoPagamentoPpay());
        pPayRtInput.setFormatoRT(formatoRT);
        return pPayRtInput;
    }

    /**
     * Create p pay rt xml input p pay rt in dto.
     *
     * @param tentativoPagamento    the tentativo pagamento
     * @param tentativoDettaglio    the tentativo dettaglio
     * @param codiceFiscalePagatore the codice fiscale pagatore
     * @param codiceFiscaleEnte     the codice fiscale ente
     * @return the p pay rt in dto
     */
    public static PPayRtInDTO createPPayRTXmlInput(TentativoPagamentoExtendedDTO tentativoPagamento, TentativoDettaglioExtendedDTO tentativoDettaglio, String codiceFiscalePagatore, String codiceFiscaleEnte) {
        return createPPayRTInput(tentativoPagamento, tentativoDettaglio, codiceFiscalePagatore, codiceFiscaleEnte, "xml");
    }

    /**
     * Create p pay rt pdf input p pay rt in dto.
     *
     * @param tentativoPagamento    the tentativo pagamento
     * @param tentativoDettaglio    the tentativo dettaglio
     * @param codiceFiscalePagatore the codice fiscale pagatore
     * @param codiceFiscaleEnte     the codice fiscale ente
     * @return the p pay rt in dto
     */
    public static PPayRtInDTO createPPayRTPdfInput(TentativoPagamentoExtendedDTO tentativoPagamento, TentativoDettaglioExtendedDTO tentativoDettaglio, String codiceFiscalePagatore, String codiceFiscaleEnte) {
        return createPPayRTInput(tentativoPagamento, tentativoDettaglio, codiceFiscalePagatore, codiceFiscaleEnte, "pdf");
    }

    private static Long getPPayComponentePagamentoByTentativoDettaglio(List<TentativoDettaglioExtendedDTO> tentativoDettaglioList, long progressivo, List<PPayComponentePagamentoDTO> pPayComponentePagamentoList) {
        if (!tentativoDettaglioList.isEmpty()) {
            for (TentativoDettaglioExtendedDTO tentativoDettaglio : tentativoDettaglioList) {
                PPayComponentePagamentoDTO pPayComponentePagamento = new PPayComponentePagamentoDTO();
                PagamentoIstanzaExtendedDTO pagamentoIstanza = tentativoDettaglio.getPagamentoIstanza();
                pPayComponentePagamento.setProgressivo(++progressivo);
                pPayComponentePagamento.setImporto(pagamentoIstanza.getImportoDovuto());
                pPayComponentePagamento.setCausale(pagamentoIstanza.getRiscossioneEnte().getDesPagamentoVersoCittadino());
                pPayComponentePagamento.setDatiSpecificiRiscossione(pagamentoIstanza.getRiscossioneEnte().getDatiSpecificiRiscossione());
                pPayComponentePagamento.setAnnoAccertamento(pagamentoIstanza.getRiscossioneEnte().getAccertamentoAnno());
                pPayComponentePagamento.setNumeroAccertamento(String.valueOf(pagamentoIstanza.getRiscossioneEnte().getNumeroAccertamento()));
                pPayComponentePagamentoList.add(pPayComponentePagamento);
            }
        }
        return progressivo;
    }

}