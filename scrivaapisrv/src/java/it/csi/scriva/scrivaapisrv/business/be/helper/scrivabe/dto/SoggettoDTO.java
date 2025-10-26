/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * The type Soggetto dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SoggettoDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_soggetto")
    private Long idSoggetto;

    @JsonProperty("id_tipo_soggetto")
    private Long idTipoSoggetto;

    @JsonProperty("id_tipo_natura_giuridica")
    private Long idTipoNaturaGiuridica;

    @JsonProperty("id_comune_residenza")
    private Long idComuneResidenza;

    @JsonProperty("id_comune_nascita")
    private Long idComuneNascita;

    @JsonProperty("id_comune_sede_legale")
    private Long idComuneSedeLegale;

    @JsonProperty("den_soggetto")
    private String denSoggetto;

    @JsonProperty("data_cessazione_soggetto")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Rome")
    private Date dataCessazioneSoggetto;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("cognome")
    private String cognome;

    @JsonProperty("indirizzo_soggetto")
    private String indirizzoSoggetto;

    @JsonProperty("des_email")
    private String desEmail;

    @JsonProperty("des_pec")
    private String desPec;

    @JsonProperty("cf_soggetto")
    private String cfSoggetto;

    @JsonProperty("partita_iva_soggetto")
    private String pivaSoggetto;

    @JsonProperty("num_civico_indirizzo")
    private String numCivicoIndirizzo;

    @JsonProperty("num_telefono")
    private String numTelefono;

    @JsonProperty("den_provincia_cciaa")
    private String denProvinciaCCIAA;

    @JsonProperty("den_anno_cciaa")
    private Integer denAnnoCCIAA;

    @JsonProperty("den_numero_cciaa")
    private String denNumeroCCIAA;

    @JsonProperty("data_nascita_soggetto")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Rome")
    private Date dataNascitaSoggetto;

    @JsonProperty("citta_estera_nascita")
    private String cittaEsteraNascita;

    @JsonProperty("citta_estera_residenza")
    private String cittaEsteraResidenza;

    @JsonProperty("id_masterdata")
    private Long idMasterdata;

    @JsonProperty("id_masterdata_origine")
    private Long idMasterdataOrigine;

    @JsonProperty("num_cellulare")
    private String numCellulare;

    @JsonProperty("id_nazione_residenza")
    private Long idNazioneResidenza;

    @JsonProperty("id_nazione_nascita")
    private Long idNazioneNascita;

    @JsonProperty("des_localita")
    private String desLocalita;

    @JsonProperty("citta_estera_sede_legale")
    private String cittaEsteraSedeLegale;

    @JsonProperty("id_nazione_sede_legale")
    private Long idNazioneSedeLegale;

    @JsonProperty("cap_residenza")
    private String capResidenza;

    @JsonProperty("cap_sede_legale")
    private String capSedeLegale;

    @JsonProperty("data_aggiornamento")
    private Timestamp dataAggiornamento;

    /**
     * Gets id soggetto.
     *
     * @return idSoggetto id soggetto
     */
    public Long getIdSoggetto() {
        return idSoggetto;
    }

    /**
     * Sets id soggetto.
     *
     * @param idSoggetto idSoggetto
     */
    public void setIdSoggetto(Long idSoggetto) {
        this.idSoggetto = idSoggetto;
    }

    /**
     * Gets id tipo soggetto.
     *
     * @return idTipoSoggetto id tipo soggetto
     */
    public Long getIdTipoSoggetto() {
        return idTipoSoggetto;
    }

    /**
     * Sets id tipo soggetto.
     *
     * @param idTipoSoggetto idTipoSoggetto
     */
    public void setIdTipoSoggetto(Long idTipoSoggetto) {
        this.idTipoSoggetto = idTipoSoggetto;
    }

    /**
     * Gets id tipo natura giuridica.
     *
     * @return idTipoNaturaGiuridica id tipo natura giuridica
     */
    public Long getIdTipoNaturaGiuridica() {
        return idTipoNaturaGiuridica;
    }

    /**
     * Sets id tipo natura giuridica.
     *
     * @param idTipoNaturaGiuridica idTipoNaturaGiuridica
     */
    public void setIdTipoNaturaGiuridica(Long idTipoNaturaGiuridica) {
        this.idTipoNaturaGiuridica = idTipoNaturaGiuridica;
    }

    /**
     * Gets id comune residenza.
     *
     * @return idComuneResidenza id comune residenza
     */
    public Long getIdComuneResidenza() {
        return idComuneResidenza;
    }

    /**
     * Sets id comune residenza.
     *
     * @param idComuneResidenza idComuneResidenza
     */
    public void setIdComuneResidenza(Long idComuneResidenza) {
        this.idComuneResidenza = idComuneResidenza;
    }

    /**
     * Gets id comune nascita.
     *
     * @return idComuneNascita id comune nascita
     */
    public Long getIdComuneNascita() {
        return idComuneNascita;
    }

    /**
     * Sets id comune nascita.
     *
     * @param idComuneNascita idComuneNascita
     */
    public void setIdComuneNascita(Long idComuneNascita) {
        this.idComuneNascita = idComuneNascita;
    }

    /**
     * Gets id comune sede legale.
     *
     * @return idComuneSedeLegale id comune sede legale
     */
    public Long getIdComuneSedeLegale() {
        return idComuneSedeLegale;
    }

    /**
     * Sets id comune sede legale.
     *
     * @param idComuneSedeLegale idComuneSedeLegale
     */
    public void setIdComuneSedeLegale(Long idComuneSedeLegale) {
        this.idComuneSedeLegale = idComuneSedeLegale;
    }

    /**
     * Gets den soggetto.
     *
     * @return denSoggetto den soggetto
     */
    public String getDenSoggetto() {
        return denSoggetto;
    }

    /**
     * Sets den soggetto.
     *
     * @param denSoggetto denSoggetto
     */
    public void setDenSoggetto(String denSoggetto) {
        this.denSoggetto = denSoggetto;
    }

    /**
     * Gets data cessazione soggetto.
     *
     * @return dataCessazioneSoggetto data cessazione soggetto
     */
    public Date getDataCessazioneSoggetto() {
        return dataCessazioneSoggetto;
    }

    /**
     * Sets data cessazione soggetto.
     *
     * @param dataCessazioneSoggetto dataCessazioneSoggetto
     */
    public void setDataCessazioneSoggetto(Date dataCessazioneSoggetto) {
        this.dataCessazioneSoggetto = dataCessazioneSoggetto;
    }

    /**
     * Gets nome.
     *
     * @return nome nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Sets nome.
     *
     * @param nome nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Gets cognome.
     *
     * @return cognome cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Sets cognome.
     *
     * @param cognome cognome
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Gets indirizzo soggetto.
     *
     * @return indirizzoSoggetto indirizzo soggetto
     */
    public String getIndirizzoSoggetto() {
        return indirizzoSoggetto;
    }

    /**
     * Sets indirizzo soggetto.
     *
     * @param indirizzoSoggetto indirizzoSoggetto
     */
    public void setIndirizzoSoggetto(String indirizzoSoggetto) {
        this.indirizzoSoggetto = indirizzoSoggetto;
    }

    /**
     * Gets des email.
     *
     * @return desEmail des email
     */
    public String getDesEmail() {
        return desEmail;
    }

    /**
     * Sets des email.
     *
     * @param desEmail desEmail
     */
    public void setDesEmail(String desEmail) {
        this.desEmail = desEmail;
    }

    /**
     * Gets des pec.
     *
     * @return desPec des pec
     */
    public String getDesPec() {
        return desPec;
    }

    /**
     * Sets des pec.
     *
     * @param desPec desPec
     */
    public void setDesPec(String desPec) {
        this.desPec = desPec;
    }

    /**
     * Gets cf soggetto.
     *
     * @return cfSoggetto cf soggetto
     */
    public String getCfSoggetto() {
        return cfSoggetto;
    }

    /**
     * Sets cf soggetto.
     *
     * @param cfSoggetto cfSoggetto
     */
    public void setCfSoggetto(String cfSoggetto) {
        this.cfSoggetto = cfSoggetto;
    }

    /**
     * Gets piva soggetto.
     *
     * @return pivaSoggetto piva soggetto
     */
    public String getPivaSoggetto() {
        return pivaSoggetto;
    }

    /**
     * Sets piva soggetto.
     *
     * @param pivaSoggetto pivaSoggetto
     */
    public void setPivaSoggetto(String pivaSoggetto) {
        this.pivaSoggetto = pivaSoggetto;
    }

    /**
     * Gets num civico indirizzo.
     *
     * @return numCivicoIndirizzo num civico indirizzo
     */
    public String getNumCivicoIndirizzo() {
        return numCivicoIndirizzo;
    }

    /**
     * Sets num civico indirizzo.
     *
     * @param numCivicoIndirizzo numCivicoIndirizzo
     */
    public void setNumCivicoIndirizzo(String numCivicoIndirizzo) {
        this.numCivicoIndirizzo = numCivicoIndirizzo;
    }

    /**
     * Gets num telefono.
     *
     * @return numTelefono num telefono
     */
    public String getNumTelefono() {
        return numTelefono;
    }

    /**
     * Sets num telefono.
     *
     * @param numTelefono numTelefono
     */
    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }

    /**
     * Gets den provincia cciaa.
     *
     * @return denProvinciaCCIAA den provincia cciaa
     */
    public String getDenProvinciaCCIAA() {
        return denProvinciaCCIAA;
    }

    /**
     * Sets den provincia cciaa.
     *
     * @param denProvinciaCCIAA denProvinciaCCIAA
     */
    public void setDenProvinciaCCIAA(String denProvinciaCCIAA) {
        this.denProvinciaCCIAA = denProvinciaCCIAA;
    }

    /**
     * Gets den anno cciaa.
     *
     * @return denAnnoCCIAA den anno cciaa
     */
    public Integer getDenAnnoCCIAA() {
        return denAnnoCCIAA;
    }

    /**
     * Sets den anno cciaa.
     *
     * @param denAnnoCCIAA denAnnoCCIAA
     */
    public void setDenAnnoCCIAA(Integer denAnnoCCIAA) {
        this.denAnnoCCIAA = denAnnoCCIAA;
    }

    /**
     * Gets den numero cciaa.
     *
     * @return denNumeroCCIAA den numero cciaa
     */
    public String getDenNumeroCCIAA() {
        return denNumeroCCIAA;
    }

    /**
     * Sets den numero cciaa.
     *
     * @param denNumeroCCIAA denNumeroCCIAA
     */
    public void setDenNumeroCCIAA(String denNumeroCCIAA) {
        this.denNumeroCCIAA = denNumeroCCIAA;
    }

    /**
     * Gets data nascita soggetto.
     *
     * @return dataNascitaSoggetto data nascita soggetto
     */
    public Date getDataNascitaSoggetto() {
        return dataNascitaSoggetto;
    }

    /**
     * Sets data nascita soggetto.
     *
     * @param dataNascitaSoggetto dataNascitaSoggetto
     */
    public void setDataNascitaSoggetto(Date dataNascitaSoggetto) {
        this.dataNascitaSoggetto = dataNascitaSoggetto;
    }

    /**
     * Gets citta estera nascita.
     *
     * @return cittaEsteraNascita citta estera nascita
     */
    public String getCittaEsteraNascita() {
        return cittaEsteraNascita;
    }

    /**
     * Sets citta estera nascita.
     *
     * @param cittaEsteraNascita cittaEsteraNascita
     */
    public void setCittaEsteraNascita(String cittaEsteraNascita) {
        this.cittaEsteraNascita = cittaEsteraNascita;
    }

    /**
     * Gets citta estera residenza.
     *
     * @return cittaEsteraResidenza citta estera residenza
     */
    public String getCittaEsteraResidenza() {
        return cittaEsteraResidenza;
    }

    /**
     * Sets citta estera residenza.
     *
     * @param cittaEsteraResidenza cittaEsteraResidenza
     */
    public void setCittaEsteraResidenza(String cittaEsteraResidenza) {
        this.cittaEsteraResidenza = cittaEsteraResidenza;
    }

    /**
     * Gets id masterdata.
     *
     * @return idMasterdata id masterdata
     */
    public Long getIdMasterdata() {
        return idMasterdata;
    }

    /**
     * Sets id masterdata.
     *
     * @param idMasterdata idMasterdata
     */
    public void setIdMasterdata(Long idMasterdata) {
        this.idMasterdata = idMasterdata;
    }

    /**
     * Gets id masterdata origine.
     *
     * @return idMasterdataOrigine id masterdata origine
     */
    public Long getIdMasterdataOrigine() {
        return idMasterdataOrigine;
    }

    /**
     * Sets id masterdata origine.
     *
     * @param idMasterdataOrigine idMasterdataOrigine
     */
    public void setIdMasterdataOrigine(Long idMasterdataOrigine) {
        this.idMasterdataOrigine = idMasterdataOrigine;
    }

    /**
     * Gets num cellulare.
     *
     * @return the num cellulare
     */
    public String getNumCellulare() {
        return numCellulare;
    }

    /**
     * Sets num cellulare.
     *
     * @param numCellulare the num cellulare
     */
    public void setNumCellulare(String numCellulare) {
        this.numCellulare = numCellulare;
    }

    /**
     * Gets id nazione residenza.
     *
     * @return the id nazione residenza
     */
    public Long getIdNazioneResidenza() {
        return idNazioneResidenza;
    }

    /**
     * Sets id nazione residenza.
     *
     * @param idNazioneResidenza the id nazione residenza
     */
    public void setIdNazioneResidenza(Long idNazioneResidenza) {
        this.idNazioneResidenza = idNazioneResidenza;
    }

    /**
     * Gets id nazione nascita.
     *
     * @return the id nazione nascita
     */
    public Long getIdNazioneNascita() {
        return idNazioneNascita;
    }

    /**
     * Sets id nazione nascita.
     *
     * @param idNazioneNascita the id nazione nascita
     */
    public void setIdNazioneNascita(Long idNazioneNascita) {
        this.idNazioneNascita = idNazioneNascita;
    }

    /**
     * Gets des localita.
     *
     * @return the des localita
     */
    public String getDesLocalita() {
        return desLocalita;
    }

    /**
     * Sets des localita.
     *
     * @param desLocalita the des localita
     */
    public void setDesLocalita(String desLocalita) {
        this.desLocalita = desLocalita;
    }

    /**
     * Gets citta estera sede legale.
     *
     * @return the citta estera sede legale
     */
    public String getCittaEsteraSedeLegale() {
        return cittaEsteraSedeLegale;
    }

    /**
     * Sets citta estera sede legale.
     *
     * @param cittaEsteraSedeLegale the citta estera sede legale
     */
    public void setCittaEsteraSedeLegale(String cittaEsteraSedeLegale) {
        this.cittaEsteraSedeLegale = cittaEsteraSedeLegale;
    }

    /**
     * Gets id nazione sede legale.
     *
     * @return the id nazione sede legale
     */
    public Long getIdNazioneSedeLegale() {
        return idNazioneSedeLegale;
    }

    /**
     * Sets id nazione sede legale.
     *
     * @param idNazioneSedeLegale the id nazione sede legale
     */
    public void setIdNazioneSedeLegale(Long idNazioneSedeLegale) {
        this.idNazioneSedeLegale = idNazioneSedeLegale;
    }

    /**
     * Gets cap residenza.
     *
     * @return the cap residenza
     */
    public String getCapResidenza() {
        return capResidenza;
    }

    /**
     * Sets cap residenza.
     *
     * @param capResidenza the cap residenza
     */
    public void setCapResidenza(String capResidenza) {
        this.capResidenza = capResidenza;
    }

    /**
     * Gets cap sede legale.
     *
     * @return the cap sede legale
     */
    public String getCapSedeLegale() {
        return capSedeLegale;
    }

    /**
     * Sets cap sede legale.
     *
     * @param capSedeLegale the cap sede legale
     */
    public void setCapSedeLegale(String capSedeLegale) {
        this.capSedeLegale = capSedeLegale;
    }

    /**
     * Gets data aggiornamento.
     *
     * @return the data aggiornamento
     */
    public Timestamp getDataAggiornamento() {
        return dataAggiornamento;
    }

    /**
     * Sets data aggiornamento.
     *
     * @param dataAggiornamento the data aggiornamento
     */
    public void setDataAggiornamento(Timestamp dataAggiornamento) {
        this.dataAggiornamento = dataAggiornamento;
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idSoggetto, idTipoSoggetto, idTipoNaturaGiuridica, idComuneResidenza, idComuneNascita, idComuneSedeLegale, denSoggetto, dataCessazioneSoggetto, nome, cognome, indirizzoSoggetto, desEmail, desPec, cfSoggetto, pivaSoggetto, numCivicoIndirizzo, numTelefono, denProvinciaCCIAA, denAnnoCCIAA, denNumeroCCIAA, dataNascitaSoggetto, cittaEsteraNascita, cittaEsteraResidenza, idMasterdata, idMasterdataOrigine, numCellulare, idNazioneResidenza, idNazioneNascita, desLocalita, cittaEsteraSedeLegale, idNazioneSedeLegale, capResidenza, capSedeLegale, dataAggiornamento);
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        SoggettoDTO that = (SoggettoDTO) o;
        return Objects.equals(idSoggetto, that.idSoggetto) && Objects.equals(idTipoSoggetto, that.idTipoSoggetto) && Objects.equals(idTipoNaturaGiuridica, that.idTipoNaturaGiuridica) && Objects.equals(idComuneResidenza, that.idComuneResidenza) && Objects.equals(idComuneNascita, that.idComuneNascita) && Objects.equals(idComuneSedeLegale, that.idComuneSedeLegale) && Objects.equals(denSoggetto, that.denSoggetto) && Objects.equals(dataCessazioneSoggetto, that.dataCessazioneSoggetto) && Objects.equals(nome, that.nome) && Objects.equals(cognome, that.cognome) && Objects.equals(indirizzoSoggetto, that.indirizzoSoggetto) && Objects.equals(desEmail, that.desEmail) && Objects.equals(desPec, that.desPec) && Objects.equals(cfSoggetto, that.cfSoggetto) && Objects.equals(pivaSoggetto, that.pivaSoggetto) && Objects.equals(numCivicoIndirizzo, that.numCivicoIndirizzo) && Objects.equals(numTelefono, that.numTelefono) && Objects.equals(denProvinciaCCIAA, that.denProvinciaCCIAA) && Objects.equals(denAnnoCCIAA, that.denAnnoCCIAA) && Objects.equals(denNumeroCCIAA, that.denNumeroCCIAA) && Objects.equals(dataNascitaSoggetto, that.dataNascitaSoggetto) && Objects.equals(cittaEsteraNascita, that.cittaEsteraNascita) && Objects.equals(cittaEsteraResidenza, that.cittaEsteraResidenza) && Objects.equals(idMasterdata, that.idMasterdata) && Objects.equals(idMasterdataOrigine, that.idMasterdataOrigine) && Objects.equals(numCellulare, that.numCellulare) && Objects.equals(idNazioneResidenza, that.idNazioneResidenza) && Objects.equals(idNazioneNascita, that.idNazioneNascita) && Objects.equals(desLocalita, that.desLocalita) && Objects.equals(cittaEsteraSedeLegale, that.cittaEsteraSedeLegale) && Objects.equals(idNazioneSedeLegale, that.idNazioneSedeLegale) && Objects.equals(capResidenza, that.capResidenza) && Objects.equals(capSedeLegale, that.capSedeLegale) && Objects.equals(dataAggiornamento, that.dataAggiornamento);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SoggettoDTO {\n");
        sb.append("         gestDataIns:").append(gestDataIns);
        sb.append(",\n         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",\n         gestDataUpd:").append(gestDataUpd);
        sb.append(",\n         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",\n         gestUID:'").append(gestUID).append("'");
        sb.append(",\n         idSoggetto:").append(idSoggetto);
        sb.append(",\n         idTipoSoggetto:").append(idTipoSoggetto);
        sb.append(",\n         idTipoNaturaGiuridica:").append(idTipoNaturaGiuridica);
        sb.append(",\n         idComuneResidenza:").append(idComuneResidenza);
        sb.append(",\n         idComuneNascita:").append(idComuneNascita);
        sb.append(",\n         idComuneSedeLegale:").append(idComuneSedeLegale);
        sb.append(",\n         denSoggetto:'").append(denSoggetto).append("'");
        sb.append(",\n         dataCessazioneSoggetto:").append(dataCessazioneSoggetto);
        sb.append(",\n         nome:'").append(nome).append("'");
        sb.append(",\n         cognome:'").append(cognome).append("'");
        sb.append(",\n         indirizzoSoggetto:'").append(indirizzoSoggetto).append("'");
        sb.append(",\n         desEmail:'").append(desEmail).append("'");
        sb.append(",\n         desPec:'").append(desPec).append("'");
        sb.append(",\n         cfSoggetto:'").append(cfSoggetto).append("'");
        sb.append(",\n         pivaSoggetto:'").append(pivaSoggetto).append("'");
        sb.append(",\n         numCivicoIndirizzo:'").append(numCivicoIndirizzo).append("'");
        sb.append(",\n         numTelefono:'").append(numTelefono).append("'");
        sb.append(",\n         denProvinciaCCIAA:'").append(denProvinciaCCIAA).append("'");
        sb.append(",\n         denAnnoCCIAA:").append(denAnnoCCIAA);
        sb.append(",\n         denNumeroCCIAA:'").append(denNumeroCCIAA).append("'");
        sb.append(",\n         dataNascitaSoggetto:").append(dataNascitaSoggetto);
        sb.append(",\n         cittaEsteraNascita:'").append(cittaEsteraNascita).append("'");
        sb.append(",\n         cittaEsteraResidenza:'").append(cittaEsteraResidenza).append("'");
        sb.append(",\n         idMasterdata:").append(idMasterdata);
        sb.append(",\n         idMasterdataOrigine:").append(idMasterdataOrigine);
        sb.append(",\n         numCellulare:'").append(numCellulare).append("'");
        sb.append(",\n         idNazioneResidenza:").append(idNazioneResidenza);
        sb.append(",\n         idNazioneNascita:").append(idNazioneNascita);
        sb.append(",\n         desLocalita:'").append(desLocalita).append("'");
        sb.append(",\n         cittaEsteraSedeLegale:'").append(cittaEsteraSedeLegale).append("'");
        sb.append(",\n         idNazioneSedeLegale:").append(idNazioneSedeLegale);
        sb.append(",\n         capResidenza:'").append(capResidenza).append("'");
        sb.append(",\n         capSedeLegale:'").append(capSedeLegale).append("'");
        sb.append(",\n         dataAggiornamento:").append(dataAggiornamento);
        sb.append("}\n");
        return sb.toString();
    }

}