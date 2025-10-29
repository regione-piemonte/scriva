/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.piemontepay.dto;

import java.util.List;
import java.util.Objects;

/**
 * The type P pay pagamento marca bollo out dto.
 *
 * @author CSI PIEMONTE
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class PPayPagamentoMarcaBolloOutDTO {

    private String identificativoPagamento;

    private String codiceEsito;

    private String descrizioneEsito;

    private String urlWisp;

    private String iuvDocumento;

    private List<String> elencoIuvMarcaBollo;

    /**
     * È l’identificativo fornito dal chiamante durante l’invocazione
     *
     * @return identificativoPagamento identificativo pagamento
     */
    public String getIdentificativoPagamento() {
        return identificativoPagamento;
    }

    /**
     * Sets identificativo pagamento.
     *
     * @param identificativoPagamento identificativoPagamento
     */
    public void setIdentificativoPagamento(String identificativoPagamento) {
        this.identificativoPagamento = identificativoPagamento;
    }

    /**
     * 000 - L’invocazione del servizio si è conclusa correttamente.
     * 100 - Errore applicativo generico – nella descrizione esito si avranno maggiori dettagli sull’errore.
     *
     * @return codiceEsito codice esito
     */
    public String getCodiceEsito() {
        return codiceEsito;
    }

    /**
     * Sets codice esito.
     *
     * @param codiceEsito codiceEsito
     */
    public void setCodiceEsito(String codiceEsito) {
        this.codiceEsito = codiceEsito;
    }

    /**
     * Esito descrittivo che dettaglia il codice esito
     *
     * @return descrizioneEsito descrizione esito
     */
    public String getDescrizioneEsito() {
        return descrizioneEsito;
    }

    /**
     * Sets descrizione esito.
     *
     * @param descrizioneEsito descrizioneEsito
     */
    public void setDescrizioneEsito(String descrizioneEsito) {
        this.descrizioneEsito = descrizioneEsito;
    }

    /**
     * URL del WISP 2.0 su cui il cittadino verrà reindirizzato per il pagamento
     *
     * @return urlWisp url wisp
     */
    public String getUrlWisp() {
        return urlWisp;
    }

    /**
     * Sets url wisp.
     *
     * @param urlWisp urlWisp
     */
    public void setUrlWisp(String urlWisp) {
        this.urlWisp = urlWisp;
    }

    /**
     * IUV (di Modello 1 o 3 a seconda che venga o meno specificato in input).
     * Se pago solo la marca non sarà presente, se pago solo la marca ma essa è associata ad uno IUV di modello 3 esistente ritroverò quello stesso che ho inserito nella chiamata,
     * se pago contestualmente marca e tassa on-line, troverò qui lo IUV di modello 1 relativo alla tassa e staccato con la chiamata stessa.
     * Lo IUV dovrà essere memorizzato perché verrà riproposto nella RT che attesta il pagamento, nelle notifiche e nei flussi di rendicontazione.
     *
     * @return iuvDocumento iuv documento
     */
    public String getIuvDocumento() {
        return iuvDocumento;
    }

    /**
     * Sets iuv documento.
     *
     * @param iuvDocumento iuvDocumento
     */
    public void setIuvDocumento(String iuvDocumento) {
        this.iuvDocumento = iuvDocumento;
    }

    /**
     * Si tratta di un array di IUV con la marca da bollo (o le marche da bollo se più di 1) associate al documento di istanza che si deve gestire.
     * Tale IUV dovrà essere memorizzato perché verrà riproposto nella RT che attesta il pagamento, nelle notifiche e nei flussi di rendicontazione.
     *
     * @return elencoIuvMarcaBollo elenco iuv marca bollo
     */
    public List<String> getElencoIuvMarcaBollo() {
        return elencoIuvMarcaBollo;
    }

    /**
     * Sets elenco iuv marca bollo.
     *
     * @param elencoIuvMarcaBollo elencoIuvMarcaBollo
     */
    public void setElencoIuvMarcaBollo(List<String> elencoIuvMarcaBollo) {
        this.elencoIuvMarcaBollo = elencoIuvMarcaBollo;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PPayPagamentoMarcaBolloOutDTO that = (PPayPagamentoMarcaBolloOutDTO) o;
        return Objects.equals(identificativoPagamento, that.identificativoPagamento) && Objects.equals(codiceEsito, that.codiceEsito) && Objects.equals(descrizioneEsito, that.descrizioneEsito) && Objects.equals(urlWisp, that.urlWisp) && Objects.equals(iuvDocumento, that.iuvDocumento) && Objects.equals(elencoIuvMarcaBollo, that.elencoIuvMarcaBollo);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(identificativoPagamento, codiceEsito, descrizioneEsito, urlWisp, iuvDocumento, elencoIuvMarcaBollo);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PPayPagamentoMarcaBolloOutDTO {");
        sb.append("         identificativoPagamento:'").append(identificativoPagamento).append("'");
        sb.append(",         codiceEsito:'").append(codiceEsito).append("'");
        sb.append(",         descrizioneEsito:'").append(descrizioneEsito).append("'");
        sb.append(",         urlWisp:'").append(urlWisp).append("'");
        sb.append(",         iuvDocumento:'").append(iuvDocumento).append("'");
        sb.append(",         elencoIuvMarcaBollo:").append(elencoIuvMarcaBollo);
        sb.append("}");
        return sb.toString();
    }
}