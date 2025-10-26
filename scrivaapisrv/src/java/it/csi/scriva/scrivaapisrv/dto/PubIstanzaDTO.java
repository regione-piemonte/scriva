/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.OrganoTecnicoDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ProcedimentoStataleDTO;
import org.dozer.Mapping;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * The type Pub istanza dto.
 *
 * @author CSI PIEMONTE
 */
public class PubIstanzaDTO implements Serializable {

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("cod_istanza")
    private String codIstanza;

    @JsonProperty("stato_istanza")
    private PubStatoIstanzaDTO statoIstanza;

    @JsonProperty("adempimento")
    private PubAdempimentoDTO adempimento;

    @Mapping("dataProtocolloIstanza")
    @JsonProperty("data_protocollo")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataProtocollo;

    @Mapping("numProtocolloIstanza")
    @JsonProperty("numero_protocollo")
    private String numeroProtocollo;

    @JsonProperty("data_inserimento_istanza")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataInserimentoIstanza;

    @JsonProperty("data_modifica_istanza")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataModificaIstanza;

    @JsonProperty("data_pubblicazione")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataPubblicazione;

    @JsonProperty("cod_pratica")
    private String codPratica;

    @JsonProperty("data_inserimento_pratica")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataInserimentoPratica;

    @JsonProperty("data_modifica_pratica")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataModificaPratica;

    @JsonProperty("des_stato_sintesi_pagamento")
    private String desStatoSintesiPagamento;

    @Mapping("dataInizioOsservazioni")
    @JsonProperty("data_inizio_osservazione")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataInizioOsservazione;

    @Mapping("dataFineOsservazioni")
    @JsonProperty("data_fine_osservazione")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataFineOsservazione;

    @JsonProperty("competenza_territorio")
    private List<PubCompetenzaTerritorioDTO> competenzaTerritorio;

    @JsonProperty("oggetto_istanza")
    private List<PubOggettoIstanzaUbicazioneDTO> oggettoIstanza;

    @JsonProperty("soggetto_istanza")
    private List<PubSoggettoIstanzaDTO> soggettoIstanza;

    @JsonProperty("flg_osservazione")
    private Boolean flgOsservazione;

    @JsonProperty("data_pubblicazione_esterna")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataPubblicazioneEsterna;

    @JsonProperty("data_scadenza_procedimento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataScadenzaProcedimento;

    @JsonProperty("des_esito_procedimento_statale")
    private String desEsitoProcedimentoStatale;

    @JsonProperty("des_stato_procedimento_statale")
    private String desStatoProcedimentoStatale;

    @Mapping("esitoProcedimento.desEsitoProcedimento")
    @JsonProperty("des_esito_procedimento")
    private String desEsitoProcedimento;

    @JsonProperty("organo_tecnico")
    private OrganoTecnicoDTO organoTecnico;

    @JsonProperty("procedimento_statale")
    private ProcedimentoStataleDTO procedimentoStatale;

    /**
     * Gets des esito procedimento.
     *
     * @return the des esito procedimento
     */
    public String getDesEsitoProcedimento() {
        return desEsitoProcedimento;
    }

    /**
     * Sets des esito procedimento.
     *
     * @param desEsitoProcedimento the des esito procedimento
     */
    public void setDesEsitoProcedimento(String desEsitoProcedimento) {
        this.desEsitoProcedimento = desEsitoProcedimento;
    }

    /**
     * Gets id istanza.
     *
     * @return the id istanza
     */
    public Long getIdIstanza() {
        return idIstanza;
    }


    /**
     * Sets id istanza.
     *
     * @param idIstanza the id istanza
     */
    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    /**
     * Gets cod istanza.
     *
     * @return the cod istanza
     */
    public String getCodIstanza() {
        return codIstanza;
    }

    /**
     * Sets cod istanza.
     *
     * @param codIstanza the cod istanza
     */
    public void setCodIstanza(String codIstanza) {
        this.codIstanza = codIstanza;
    }

    /**
     * Gets stato istanza.
     *
     * @return the stato istanza
     */
    public PubStatoIstanzaDTO getStatoIstanza() {
        return statoIstanza;
    }

    /**
     * Sets stato istanza.
     *
     * @param statoIstanza the stato istanza
     */
    public void setStatoIstanza(PubStatoIstanzaDTO statoIstanza) {
        this.statoIstanza = statoIstanza;
    }

    /**
     * Gets adempimento.
     *
     * @return the adempimento
     */
    public PubAdempimentoDTO getAdempimento() {
        return adempimento;
    }

    /**
     * Sets adempimento.
     *
     * @param adempimento the adempimento
     */
    public void setAdempimento(PubAdempimentoDTO adempimento) {
        this.adempimento = adempimento;
    }

    /**
     * Gets data protocollo.
     *
     * @return the data protocollo
     */
    public Timestamp getDataProtocollo() {
        return dataProtocollo;
    }

    /**
     * Sets data protocollo.
     *
     * @param dataProtocollo the data protocollo
     */
    public void setDataProtocollo(Timestamp dataProtocollo) {
        this.dataProtocollo = dataProtocollo;
    }

    /**
     * Gets numero protocollo.
     *
     * @return the numero protocollo
     */
    public String getNumeroProtocollo() {
        return numeroProtocollo;
    }

    /**
     * Sets numero protocollo.
     *
     * @param numeroProtocollo the numero protocollo
     */
    public void setNumeroProtocollo(String numeroProtocollo) {
        this.numeroProtocollo = numeroProtocollo;
    }

    /**
     * Gets data inserimento istanza.
     *
     * @return the data inserimento istanza
     */
    public Timestamp getDataInserimentoIstanza() {
        return dataInserimentoIstanza;
    }

    /**
     * Sets data inserimento istanza.
     *
     * @param dataInserimentoIstanza the data inserimento istanza
     */
    public void setDataInserimentoIstanza(Timestamp dataInserimentoIstanza) {
        this.dataInserimentoIstanza = dataInserimentoIstanza;
    }

    /**
     * Gets data modifica istanza.
     *
     * @return the data modifica istanza
     */
    public Timestamp getDataModificaIstanza() {
        return dataModificaIstanza;
    }

    /**
     * Sets data modifica istanza.
     *
     * @param dataModificaIstanza the data modifica istanza
     */
    public void setDataModificaIstanza(Timestamp dataModificaIstanza) {
        this.dataModificaIstanza = dataModificaIstanza;
    }

    /**
     * Gets data pubblicazione.
     *
     * @return the data pubblicazione
     */
    public Timestamp getDataPubblicazione() {
        return dataPubblicazione;
    }

    /**
     * Sets data pubblicazione.
     *
     * @param dataPubblicazione the data pubblicazione
     */
    public void setDataPubblicazione(Timestamp dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }

    /**
     * Gets cod pratica.
     *
     * @return the cod pratica
     */
    public String getCodPratica() {
        return codPratica;
    }

    /**
     * Sets cod pratica.
     *
     * @param codPratica the cod pratica
     */
    public void setCodPratica(String codPratica) {
        this.codPratica = codPratica;
    }

    /**
     * Gets data inserimento pratica.
     *
     * @return the data inserimento pratica
     */
    public Timestamp getDataInserimentoPratica() {
        return dataInserimentoPratica;
    }

    /**
     * Sets data inserimento pratica.
     *
     * @param dataInserimentoPratica the data inserimento pratica
     */
    public void setDataInserimentoPratica(Timestamp dataInserimentoPratica) {
        this.dataInserimentoPratica = dataInserimentoPratica;
    }

    /**
     * Gets data modifica pratica.
     *
     * @return the data modifica pratica
     */
    public Timestamp getDataModificaPratica() {
        return dataModificaPratica;
    }

    /**
     * Sets data modifica pratica.
     *
     * @param dataModificaPratica the data modifica pratica
     */
    public void setDataModificaPratica(Timestamp dataModificaPratica) {
        this.dataModificaPratica = dataModificaPratica;
    }

    /**
     * Gets des stato sintesi pagamento.
     *
     * @return the des stato sintesi pagamento
     */
    public String getDesStatoSintesiPagamento() {
        return desStatoSintesiPagamento;
    }

    /**
     * Sets des stato sintesi pagamento.
     *
     * @param desStatoSintesiPagamento the des stato sintesi pagamento
     */
    public void setDesStatoSintesiPagamento(String desStatoSintesiPagamento) {
        this.desStatoSintesiPagamento = desStatoSintesiPagamento;
    }

    /**
     * Gets data inizio osservazione.
     *
     * @return the data inizio osservazione
     */
    public Timestamp getDataInizioOsservazione() {
        return dataInizioOsservazione;
    }

    /**
     * Sets data inizio osservazione.
     *
     * @param dataInizioOsservazione the data inizio osservazione
     */
    public void setDataInizioOsservazione(Timestamp dataInizioOsservazione) {
        this.dataInizioOsservazione = dataInizioOsservazione;
    }

    /**
     * Gets data fine osservazione.
     *
     * @return the data fine osservazione
     */
    public Timestamp getDataFineOsservazione() {
        return dataFineOsservazione;
    }

    /**
     * Sets data fine osservazione.
     *
     * @param dataFineOsservazione the data fine osservazione
     */
    public void setDataFineOsservazione(Timestamp dataFineOsservazione) {
        this.dataFineOsservazione = dataFineOsservazione;
    }

    /**
     * Gets competenza territorio.
     *
     * @return the competenza territorio
     */
    public List<PubCompetenzaTerritorioDTO> getCompetenzaTerritorio() {
        return competenzaTerritorio;
    }

    /**
     * Sets competenza territorio.
     *
     * @param competenzaTerritorio the competenza territorio
     */
    public void setCompetenzaTerritorio(List<PubCompetenzaTerritorioDTO> competenzaTerritorio) {
        this.competenzaTerritorio = competenzaTerritorio;
    }

    /**
     * Gets oggetto istanza.
     *
     * @return the oggetto istanza
     */
    public List<PubOggettoIstanzaUbicazioneDTO> getOggettoIstanza() {
        return oggettoIstanza;
    }

    /**
     * Sets oggetto istanza.
     *
     * @param oggettoIstanza the oggetto istanza
     */
    public void setOggettoIstanza(List<PubOggettoIstanzaUbicazioneDTO> oggettoIstanza) {
        this.oggettoIstanza = oggettoIstanza;
    }

    /**
     * Gets soggetto istanza.
     *
     * @return the soggetto istanza
     */
    public List<PubSoggettoIstanzaDTO> getSoggettoIstanza() {
        return soggettoIstanza;
    }

    /**
     * Sets soggetto istanza.
     *
     * @param soggettoIstanza the soggetto istanza
     */
    public void setSoggettoIstanza(List<PubSoggettoIstanzaDTO> soggettoIstanza) {
        this.soggettoIstanza = soggettoIstanza;
    }

    /**
     * Gets flg osservazione.
     *
     * @return the flg osservazione
     */
    public Boolean getFlgOsservazione() {
        return flgOsservazione;
    }

    /**
     * Sets flg osservazione.
     *
     * @param flgOsservazione the flg osservazione
     */
    public void setFlgOsservazione(Boolean flgOsservazione) {
        this.flgOsservazione = flgOsservazione;
    }


    /**
     * Gets data pubblicazione esterna.
     *
     * @return the data pubblicazione esterna
     */
    public Timestamp getDataPubblicazioneEsterna() {
        return dataPubblicazioneEsterna;
    }

    /**
     * Sets data pubblicazione esterna.
     *
     * @param dataPubblicazioneEsterna the data pubblicazione esterna
     */
    public void setDataPubblicazioneEsterna(Timestamp dataPubblicazioneEsterna) {
        this.dataPubblicazioneEsterna = dataPubblicazioneEsterna;
    }

    /**
     * Gets data scadenza procedimento.
     *
     * @return the data scadenza procedimento
     */
    public Timestamp getDataScadenzaProcedimento() {
        return dataScadenzaProcedimento;
    }

    /**
     * Sets data scadenza procedimento.
     *
     * @param dataScadenzaProcedimento the data scadenza procedimento
     */
    public void setDataScadenzaProcedimento(Timestamp dataScadenzaProcedimento) {
        this.dataScadenzaProcedimento = dataScadenzaProcedimento;
    }

    /**
     * Gets des esito procedimento statale.
     *
     * @return the des esito procedimento statale
     */
    public String getDesEsitoProcedimentoStatale() {
        return desEsitoProcedimentoStatale;
    }

    /**
     * Sets des esito procedimento statale.
     *
     * @param desEsitoProcedimentoStatale the des esito procedimento statale
     */
    public void setDesEsitoProcedimentoStatale(String desEsitoProcedimentoStatale) {
        this.desEsitoProcedimentoStatale = desEsitoProcedimentoStatale;
    }

    /**
     * Gets des stato procedimento statale.
     *
     * @return the des stato procedimento statale
     */
    public String getDesStatoProcedimentoStatale() {
        return desStatoProcedimentoStatale;
    }

    /**
     * Sets des stato procedimento statale.
     *
     * @param desStatoProcedimentoStatale the des stato procedimento statale
     */
    public void setDesStatoProcedimentoStatale(String desStatoProcedimentoStatale) {
        this.desStatoProcedimentoStatale = desStatoProcedimentoStatale;
    }

    /**
     * Gets organo tecnico.
     *
     * @return the organo tecnico
     */
    public OrganoTecnicoDTO getOrganoTecnico() {
        return organoTecnico;
    }

    /**
     * Sets organo tecnico.
     *
     * @param organoTecnico the organo tecnico
     */
    public void setOrganoTecnico(OrganoTecnicoDTO organoTecnico) {
        this.organoTecnico = organoTecnico;
    }

    /**
     * Gets procedimento statale.
     *
     * @return the procedimento statale
     */
    public ProcedimentoStataleDTO getProcedimentoStatale() {
        return procedimentoStatale;
    }

    /**
     * Sets procedimento statale.
     *
     * @param procedimentoStatale the procedimento statale
     */
    public void setProcedimentoStatale(ProcedimentoStataleDTO procedimentoStatale) {
        this.procedimentoStatale = procedimentoStatale;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PubIstanzaDTO that = (PubIstanzaDTO) o;
        return Objects.equals(idIstanza, that.idIstanza) && Objects.equals(codIstanza, that.codIstanza) && Objects.equals(statoIstanza, that.statoIstanza) && Objects.equals(adempimento, that.adempimento) && Objects.equals(dataProtocollo, that.dataProtocollo) && Objects.equals(numeroProtocollo, that.numeroProtocollo) && Objects.equals(dataInserimentoIstanza, that.dataInserimentoIstanza) && Objects.equals(dataModificaIstanza, that.dataModificaIstanza) && Objects.equals(dataPubblicazione, that.dataPubblicazione) && Objects.equals(codPratica, that.codPratica) && Objects.equals(dataInserimentoPratica, that.dataInserimentoPratica) && Objects.equals(dataModificaPratica, that.dataModificaPratica) && Objects.equals(desStatoSintesiPagamento, that.desStatoSintesiPagamento) && Objects.equals(dataInizioOsservazione, that.dataInizioOsservazione) && Objects.equals(dataFineOsservazione, that.dataFineOsservazione) && Objects.equals(competenzaTerritorio, that.competenzaTerritorio) && Objects.equals(oggettoIstanza, that.oggettoIstanza) && Objects.equals(soggettoIstanza, that.soggettoIstanza) && Objects.equals(flgOsservazione, that.flgOsservazione) && Objects.equals(dataPubblicazioneEsterna, that.dataPubblicazioneEsterna) && Objects.equals(dataScadenzaProcedimento, that.dataScadenzaProcedimento) && Objects.equals(desEsitoProcedimentoStatale, that.desEsitoProcedimentoStatale) && Objects.equals(desStatoProcedimentoStatale, that.desStatoProcedimentoStatale) && Objects.equals(desEsitoProcedimento, that.desEsitoProcedimento) && Objects.equals(organoTecnico, that.organoTecnico) && Objects.equals(procedimentoStatale, that.procedimentoStatale);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idIstanza, codIstanza, statoIstanza, adempimento, dataProtocollo, numeroProtocollo, dataInserimentoIstanza, dataModificaIstanza, dataPubblicazione, codPratica, dataInserimentoPratica, dataModificaPratica, desStatoSintesiPagamento, dataInizioOsservazione, dataFineOsservazione, competenzaTerritorio, oggettoIstanza, soggettoIstanza, flgOsservazione, dataPubblicazioneEsterna, dataScadenzaProcedimento, desEsitoProcedimentoStatale, desStatoProcedimentoStatale, desEsitoProcedimento, organoTecnico, procedimentoStatale);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "PubIstanzaDTO {\n" +
                "         idIstanza:" + idIstanza +
                ",\n         codIstanza:'" + codIstanza + "'" +
                ",\n         statoIstanza:" + statoIstanza +
                ",\n         adempimento:" + adempimento +
                ",\n         dataProtocollo:" + dataProtocollo +
                ",\n         numeroProtocollo:" + numeroProtocollo +
                ",\n         dataInserimentoIstanza:" + dataInserimentoIstanza +
                ",\n         dataModificaIstanza:" + dataModificaIstanza +
                ",\n         dataPubblicazione:" + dataPubblicazione +
                ",\n         codPratica:'" + codPratica + "'" +
                ",\n         dataInserimentoPratica:" + dataInserimentoPratica +
                ",\n         dataModificaPratica:" + dataModificaPratica +
                ",\n         desStatoSintesiPagamento:'" + desStatoSintesiPagamento + "'" +
                ",\n         dataInizioOsservazione:" + dataInizioOsservazione +
                ",\n         dataFineOsservazione:" + dataFineOsservazione +
                ",\n         competenzaTerritorio:" + competenzaTerritorio +
                ",\n         oggettoIstanza:" + oggettoIstanza +
                ",\n         soggettoIstanza:" + soggettoIstanza +
                ",\n         flgOsservazione:" + flgOsservazione +
                ",\n         dataPubblicazioneEsterna:" + dataPubblicazioneEsterna +
                ",\n         dataScadenzaProcedimento:" + dataScadenzaProcedimento +
                ",\n         desEsitoProcedimentoStatale:'" + desEsitoProcedimentoStatale + "'" +
                ",\n         desStatoProcedimentoStatale:'" + desStatoProcedimentoStatale + "'" +
                ",\n         desEsitoProcedimento:'" + desEsitoProcedimento + "'" +
                ",\n         organoTecnico:" + organoTecnico +
                ",\n         procedimentoStatale:" + procedimentoStatale +
                "}\n";
    }

}