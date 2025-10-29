/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * The type Soggetto istanza dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SoggettoIstanzaDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_soggetto_istanza")
    private Long idSoggettoIstanza;

    @JsonProperty("id_soggetto_padre")
    private Long idSoggettoPadre;

    @JsonProperty("id_soggetto")
    private Long idSoggetto;

    @JsonProperty("id_tipo_soggetto")
    private Long idTipoSoggetto;

    @JsonProperty("id_ruolo_soggetto")
    private Long idRuoloSoggetto;

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_tipo_natura_giuridica")
    private Long idTipoNaturaGiuridica;

    @JsonProperty("id_ruolo_compilante")
    private Long idRuoloCompilante;

    @JsonProperty("id_comune_residenza")
    private Long idComuneResidenza;

    @JsonProperty("id_comune_nascita")
    private Long idComuneNascita;

    @JsonProperty("id_comune_sede_legale")
    private Long idComuneSedeLegale;

    @JsonProperty("den_soggetto")
    private String denSoggetto;

    @JsonProperty("data_cessazione_soggetto")
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
    private String partitaIvaSoggetto;

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
    //@JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataNascitaSoggetto;

    @JsonProperty("citta_estera_nascita")
    private String cittaEsteraNascita;

    @JsonProperty("stato_estero_nascita")
    private String statoEsteroNascita;

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

    @JsonIgnore
    private Long idIstanzaAttore;

    @JsonIgnore
    private Long idFunzionario;

    /**
     * Gets id soggetto istanza.
     *
     * @return idSoggettoIstanza id soggetto istanza
     */
    public Long getIdSoggettoIstanza() {
        return idSoggettoIstanza;
    }

    /**
     * Sets id soggetto istanza.
     *
     * @param idSoggettoIstanza idSoggettoIstanza
     */
    public void setIdSoggettoIstanza(Long idSoggettoIstanza) {
        this.idSoggettoIstanza = idSoggettoIstanza;
    }

    /**
     * Gets id soggetto padre.
     *
     * @return idSoggettoPadre id soggetto padre
     */
    public Long getIdSoggettoPadre() {
        return idSoggettoPadre;
    }

    /**
     * Sets id soggetto padre.
     *
     * @param idSoggettoPadre idSoggettoPadre
     */
    public void setIdSoggettoPadre(Long idSoggettoPadre) {
        this.idSoggettoPadre = idSoggettoPadre;
    }

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
     * Gets id ruolo soggetto.
     *
     * @return idRuoloSoggetto id ruolo soggetto
     */
    public Long getIdRuoloSoggetto() {
        return idRuoloSoggetto;
    }

    /**
     * Sets id ruolo soggetto.
     *
     * @param idRuoloSoggetto idRuoloSoggetto
     */
    public void setIdRuoloSoggetto(Long idRuoloSoggetto) {
        this.idRuoloSoggetto = idRuoloSoggetto;
    }

    /**
     * Gets id istanza.
     *
     * @return idIstanza id istanza
     */
    public Long getIdIstanza() {
        return idIstanza;
    }

    /**
     * Sets id istanza.
     *
     * @param idIstanza idIstanza
     */
    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
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
     * Gets id ruolo compilante.
     *
     * @return idRuoloCompilante id ruolo compilante
     */
    public Long getIdRuoloCompilante() {
        return idRuoloCompilante;
    }

    /**
     * Sets id ruolo compilante.
     *
     * @param idRuoloCompilante idRuoloCompilante
     */
    public void setIdRuoloCompilante(Long idRuoloCompilante) {
        this.idRuoloCompilante = idRuoloCompilante;
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
     * Gets partita iva soggetto.
     *
     * @return partitaIvaSoggetto partita iva soggetto
     */
    public String getPartitaIvaSoggetto() {
        return partitaIvaSoggetto;
    }

    /**
     * Sets partita iva soggetto.
     *
     * @param partitaIvaSoggetto partitaIvaSoggetto
     */
    public void setPartitaIvaSoggetto(String partitaIvaSoggetto) {
        this.partitaIvaSoggetto = partitaIvaSoggetto;
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
     * Gets stato estero nascita.
     *
     * @return statoEsteroNascita stato estero nascita
     */
    public String getStatoEsteroNascita() {
        return statoEsteroNascita;
    }

    /**
     * Sets stato estero nascita.
     *
     * @param statoEsteroNascita statoEsteroNascita
     */
    public void setStatoEsteroNascita(String statoEsteroNascita) {
        this.statoEsteroNascita = statoEsteroNascita;
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
     * Gets id istanza attore.
     *
     * @return idIstanzaAttore id istanza attore
     */
    public Long getIdIstanzaAttore() {
        return idIstanzaAttore;
    }

    /**
     * Sets id istanza attore.
     *
     * @param idIstanzaAttore idIstanzaAttore
     */
    public void setIdIstanzaAttore(Long idIstanzaAttore) {
        this.idIstanzaAttore = idIstanzaAttore;
    }

    /**
     * Gets id funzionario.
     *
     * @return the id funzionario
     */
    public Long getIdFunzionario() {
        return idFunzionario;
    }

    /**
     * Sets id funzionario.
     *
     * @param idFunzionario the id funzionario
     */
    public void setIdFunzionario(Long idFunzionario) {
        this.idFunzionario = idFunzionario;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SoggettoIstanzaDTO that = (SoggettoIstanzaDTO) o;
        return Objects.equals(idSoggettoIstanza, that.idSoggettoIstanza) && Objects.equals(idSoggettoPadre, that.idSoggettoPadre) && Objects.equals(idSoggetto, that.idSoggetto) && Objects.equals(idTipoSoggetto, that.idTipoSoggetto) && Objects.equals(idRuoloSoggetto, that.idRuoloSoggetto) && Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idTipoNaturaGiuridica, that.idTipoNaturaGiuridica) && Objects.equals(idRuoloCompilante, that.idRuoloCompilante) && Objects.equals(idComuneResidenza, that.idComuneResidenza) && Objects.equals(idComuneNascita, that.idComuneNascita) && Objects.equals(idComuneSedeLegale, that.idComuneSedeLegale) && Objects.equals(denSoggetto, that.denSoggetto) && Objects.equals(dataCessazioneSoggetto, that.dataCessazioneSoggetto) && Objects.equals(nome, that.nome) && Objects.equals(cognome, that.cognome) && Objects.equals(indirizzoSoggetto, that.indirizzoSoggetto) && Objects.equals(desEmail, that.desEmail) && Objects.equals(desPec, that.desPec) && Objects.equals(cfSoggetto, that.cfSoggetto) && Objects.equals(partitaIvaSoggetto, that.partitaIvaSoggetto) && Objects.equals(numCivicoIndirizzo, that.numCivicoIndirizzo) && Objects.equals(numTelefono, that.numTelefono) && Objects.equals(denProvinciaCCIAA, that.denProvinciaCCIAA) && Objects.equals(denAnnoCCIAA, that.denAnnoCCIAA) && Objects.equals(denNumeroCCIAA, that.denNumeroCCIAA) && Objects.equals(dataNascitaSoggetto, that.dataNascitaSoggetto) && Objects.equals(cittaEsteraNascita, that.cittaEsteraNascita) && Objects.equals(statoEsteroNascita, that.statoEsteroNascita) && Objects.equals(cittaEsteraResidenza, that.cittaEsteraResidenza) && Objects.equals(idMasterdata, that.idMasterdata) && Objects.equals(idMasterdataOrigine, that.idMasterdataOrigine) && Objects.equals(numCellulare, that.numCellulare) && Objects.equals(idNazioneResidenza, that.idNazioneResidenza) && Objects.equals(idNazioneNascita, that.idNazioneNascita) && Objects.equals(desLocalita, that.desLocalita) && Objects.equals(cittaEsteraSedeLegale, that.cittaEsteraSedeLegale) && Objects.equals(idNazioneSedeLegale, that.idNazioneSedeLegale) && Objects.equals(capResidenza, that.capResidenza) && Objects.equals(capSedeLegale, that.capSedeLegale) && Objects.equals(idIstanzaAttore, that.idIstanzaAttore) && Objects.equals(idFunzionario, that.idFunzionario);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idSoggettoIstanza, idSoggettoPadre, idSoggetto, idTipoSoggetto, idRuoloSoggetto, idIstanza, idTipoNaturaGiuridica, idRuoloCompilante, idComuneResidenza, idComuneNascita, idComuneSedeLegale, denSoggetto, dataCessazioneSoggetto, nome, cognome, indirizzoSoggetto, desEmail, desPec, cfSoggetto, partitaIvaSoggetto, numCivicoIndirizzo, numTelefono, denProvinciaCCIAA, denAnnoCCIAA, denNumeroCCIAA, dataNascitaSoggetto, cittaEsteraNascita, statoEsteroNascita, cittaEsteraResidenza, idMasterdata, idMasterdataOrigine, numCellulare, idNazioneResidenza, idNazioneNascita, desLocalita, cittaEsteraSedeLegale, idNazioneSedeLegale, capResidenza, capSedeLegale, idIstanzaAttore, idFunzionario);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "SoggettoIstanzaDTO {\n" +
                "         gestDataIns:" + gestDataIns +
                ",\n         gestAttoreIns:'" + gestAttoreIns + "'" +
                ",\n         gestDataUpd:" + gestDataUpd +
                ",\n         gestAttoreUpd:'" + gestAttoreUpd + "'" +
                ",\n         gestUID:'" + gestUID + "'" +
                ",\n         idSoggettoIstanza:" + idSoggettoIstanza +
                ",\n         idSoggettoPadre:" + idSoggettoPadre +
                ",\n         idSoggetto:" + idSoggetto +
                ",\n         idTipoSoggetto:" + idTipoSoggetto +
                ",\n         idRuoloSoggetto:" + idRuoloSoggetto +
                ",\n         idIstanza:" + idIstanza +
                ",\n         idTipoNaturaGiuridica:" + idTipoNaturaGiuridica +
                ",\n         idRuoloCompilante:" + idRuoloCompilante +
                ",\n         idComuneResidenza:" + idComuneResidenza +
                ",\n         idComuneNascita:" + idComuneNascita +
                ",\n         idComuneSedeLegale:" + idComuneSedeLegale +
                ",\n         denSoggetto:'" + denSoggetto + "'" +
                ",\n         dataCessazioneSoggetto:" + dataCessazioneSoggetto +
                ",\n         nome:'" + nome + "'" +
                ",\n         cognome:'" + cognome + "'" +
                ",\n         indirizzoSoggetto:'" + indirizzoSoggetto + "'" +
                ",\n         desEmail:'" + desEmail + "'" +
                ",\n         desPec:'" + desPec + "'" +
                ",\n         cfSoggetto:'" + cfSoggetto + "'" +
                ",\n         partitaIvaSoggetto:'" + partitaIvaSoggetto + "'" +
                ",\n         numCivicoIndirizzo:'" + numCivicoIndirizzo + "'" +
                ",\n         numTelefono:'" + numTelefono + "'" +
                ",\n         denProvinciaCCIAA:'" + denProvinciaCCIAA + "'" +
                ",\n         denAnnoCCIAA:" + denAnnoCCIAA +
                ",\n         denNumeroCCIAA:'" + denNumeroCCIAA + "'" +
                ",\n         dataNascitaSoggetto:" + dataNascitaSoggetto +
                ",\n         cittaEsteraNascita:'" + cittaEsteraNascita + "'" +
                ",\n         statoEsteroNascita:'" + statoEsteroNascita + "'" +
                ",\n         cittaEsteraResidenza:'" + cittaEsteraResidenza + "'" +
                ",\n         idMasterdata:" + idMasterdata +
                ",\n         idMasterdataOrigine:" + idMasterdataOrigine +
                ",\n         numCellulare:'" + numCellulare + "'" +
                ",\n         idNazioneResidenza:" + idNazioneResidenza +
                ",\n         idNazioneNascita:" + idNazioneNascita +
                ",\n         desLocalita:'" + desLocalita + "'" +
                ",\n         cittaEsteraSedeLegale:'" + cittaEsteraSedeLegale + "'" +
                ",\n         idNazioneSedeLegale:" + idNazioneSedeLegale +
                ",\n         capResidenza:'" + capResidenza + "'" +
                ",\n         capSedeLegale:'" + capSedeLegale + "'" +
                ",\n         idIstanzaAttore:" + idIstanzaAttore +
                ",\n         idFunzionario:" + idFunzionario +
                "}\n";
    }
}