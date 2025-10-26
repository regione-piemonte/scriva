/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/**
 *  SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Recapito alternativo dto.
 *
 * @author CSI PIEMONTE
 */
public class RecapitoAlternativoDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_recapito_alternativo")
    private Long idRecapitoAlternativo;

    @JsonProperty("cod_recapito_alternativo")
    private String codRecapitoAlternativo;

    @JsonProperty("id_soggetto_istanza")
    private Long idSoggettoIstanza;

    @JsonProperty("id_comune_residenza")
    private Long idComuneResidenza;

    @JsonProperty("id_comune_sede_legale")
    private Long idComuneSedeLegale;

    @JsonProperty("indirizzo_soggetto")
    private String indirizzoSoggetto;

    @JsonProperty("num_civico_indirizzo")
    private String numCivicoIndirizzo;

    @JsonProperty("citta_estera_residenza")
    private String cittaEsteraResidenza;

    @JsonProperty("id_nazione_residenza")
    private Long idNazioneResidenza;

    @JsonProperty("presso")
    private String presso;

    @JsonProperty("num_telefono")
    private String numTelefono;

    @JsonProperty("num_cellulare")
    private String numCellulare;

    @JsonProperty("des_localita")
    private String desLocalita;

    @JsonProperty("des_email")
    private String desEmail;

    @JsonProperty("des_pec")
    private String desPec;

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

    /**
     * Gets id recapito alternativo.
     *
     * @return the id recapito alternativo
     */
    public Long getIdRecapitoAlternativo() {
        return idRecapitoAlternativo;
    }

    /**
     * Sets id recapito alternativo.
     *
     * @param idRecapitoAlternativo the id recapito alternativo
     */
    public void setIdRecapitoAlternativo(Long idRecapitoAlternativo) {
        this.idRecapitoAlternativo = idRecapitoAlternativo;
    }

    /**
     * Gets cod recapito alternativo.
     *
     * @return the cod recapito alternativo
     */
    public String getCodRecapitoAlternativo() {
        return codRecapitoAlternativo;
    }

    /**
     * Sets cod recapito alternativo.
     *
     * @param codRecapitoAlternativo the cod recapito alternativo
     */
    public void setCodRecapitoAlternativo(String codRecapitoAlternativo) {
        this.codRecapitoAlternativo = codRecapitoAlternativo;
    }

    /**
     * Gets id soggetto istanza.
     *
     * @return the id soggetto istanza
     */
    public Long getIdSoggettoIstanza() {
        return idSoggettoIstanza;
    }

    /**
     * Sets id soggetto istanza.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     */
    public void setIdSoggettoIstanza(Long idSoggettoIstanza) {
        this.idSoggettoIstanza = idSoggettoIstanza;
    }

    /**
     * Gets id comune residenza.
     *
     * @return the id comune residenza
     */
    public Long getIdComuneResidenza() {
        return idComuneResidenza;
    }

    /**
     * Sets id comune residenza.
     *
     * @param idComuneResidenza the id comune residenza
     */
    public void setIdComuneResidenza(Long idComuneResidenza) {
        this.idComuneResidenza = idComuneResidenza;
    }

    /**
     * Gets id comune sede legale.
     *
     * @return the id comune sede legale
     */
    public Long getIdComuneSedeLegale() {
        return idComuneSedeLegale;
    }

    /**
     * Sets id comune sede legale.
     *
     * @param idComuneSedeLegale the id comune sede legale
     */
    public void setIdComuneSedeLegale(Long idComuneSedeLegale) {
        this.idComuneSedeLegale = idComuneSedeLegale;
    }

    /**
     * Gets indirizzo soggetto.
     *
     * @return the indirizzo soggetto
     */
    public String getIndirizzoSoggetto() {
        return indirizzoSoggetto;
    }

    /**
     * Sets indirizzo soggetto.
     *
     * @param indirizzoSoggetto the indirizzo soggetto
     */
    public void setIndirizzoSoggetto(String indirizzoSoggetto) {
        this.indirizzoSoggetto = indirizzoSoggetto;
    }

    /**
     * Gets num civico indirizzo.
     *
     * @return the num civico indirizzo
     */
    public String getNumCivicoIndirizzo() {
        return numCivicoIndirizzo;
    }

    /**
     * Sets num civico indirizzo.
     *
     * @param numCivicoIndirizzo the num civico indirizzo
     */
    public void setNumCivicoIndirizzo(String numCivicoIndirizzo) {
        this.numCivicoIndirizzo = numCivicoIndirizzo;
    }

    /**
     * Gets citta estera residenza.
     *
     * @return the citta estera residenza
     */
    public String getCittaEsteraResidenza() {
        return cittaEsteraResidenza;
    }

    /**
     * Sets citta estera residenza.
     *
     * @param cittaEsteraResidenza the citta estera residenza
     */
    public void setCittaEsteraResidenza(String cittaEsteraResidenza) {
        this.cittaEsteraResidenza = cittaEsteraResidenza;
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
     * Gets presso.
     *
     * @return the presso
     */
    public String getPresso() {
        return presso;
    }

    /**
     * Sets presso.
     *
     * @param presso the presso
     */
    public void setPresso(String presso) {
        this.presso = presso;
    }

    /**
     * Gets num telefono.
     *
     * @return the num telefono
     */
    public String getNumTelefono() {
        return numTelefono;
    }

    /**
     * Sets num telefono.
     *
     * @param numTelefono the num telefono
     */
    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
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
     * Gets des email.
     *
     * @return the des email
     */
    public String getDesEmail() {
        return desEmail;
    }

    /**
     * Sets des email.
     *
     * @param desEmail the des email
     */
    public void setDesEmail(String desEmail) {
        this.desEmail = desEmail;
    }

    /**
     * Gets des pec.
     *
     * @return the des pec
     */
    public String getDesPec() {
        return desPec;
    }

    /**
     * Sets des pec.
     *
     * @param desPec the des pec
     */
    public void setDesPec(String desPec) {
        this.desPec = desPec;
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
     * @return the id istanza attore
     */
    public Long getIdIstanzaAttore() {
        return idIstanzaAttore;
    }

    /**
     * Sets id istanza attore.
     *
     * @param idIstanzaAttore the id istanza attore
     */
    public void setIdIstanzaAttore(Long idIstanzaAttore) {
        this.idIstanzaAttore = idIstanzaAttore;
    }

    /**
     * equals
     *
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RecapitoAlternativoDTO that = (RecapitoAlternativoDTO) o;
        return Objects.equals(idRecapitoAlternativo, that.idRecapitoAlternativo) && Objects.equals(codRecapitoAlternativo, that.codRecapitoAlternativo) && Objects.equals(idSoggettoIstanza, that.idSoggettoIstanza) && Objects.equals(idComuneResidenza, that.idComuneResidenza) && Objects.equals(idComuneSedeLegale, that.idComuneSedeLegale) && Objects.equals(indirizzoSoggetto, that.indirizzoSoggetto) && Objects.equals(numCivicoIndirizzo, that.numCivicoIndirizzo) && Objects.equals(cittaEsteraResidenza, that.cittaEsteraResidenza) && Objects.equals(idNazioneResidenza, that.idNazioneResidenza) && Objects.equals(presso, that.presso) && Objects.equals(numTelefono, that.numTelefono) && Objects.equals(numCellulare, that.numCellulare) && Objects.equals(desLocalita, that.desLocalita) && Objects.equals(desEmail, that.desEmail) && Objects.equals(desPec, that.desPec) && Objects.equals(cittaEsteraSedeLegale, that.cittaEsteraSedeLegale) && Objects.equals(idNazioneSedeLegale, that.idNazioneSedeLegale) && Objects.equals(capResidenza, that.capResidenza) && Objects.equals(capSedeLegale, that.capSedeLegale) && Objects.equals(idIstanzaAttore, that.idIstanzaAttore);
    }

    /**
     * hashCode
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idRecapitoAlternativo, codRecapitoAlternativo, idSoggettoIstanza, idComuneResidenza, idComuneSedeLegale, indirizzoSoggetto, numCivicoIndirizzo, cittaEsteraResidenza, idNazioneResidenza, presso, numTelefono, numCellulare, desLocalita, desEmail, desPec, cittaEsteraSedeLegale, idNazioneSedeLegale, capResidenza, capSedeLegale, idIstanzaAttore);
    }

    /**
     * toString
     *
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RecapitoAlternativoDTO {\n");
        sb.append("         gestDataIns:").append(gestDataIns);
        sb.append(",\n         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",\n         gestDataUpd:").append(gestDataUpd);
        sb.append(",\n         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",\n         gestUID:'").append(gestUID).append("'");
        sb.append(",\n         idRecapitoAlternativo:").append(idRecapitoAlternativo);
        sb.append(",\n         codRecapitoAlternativo:'").append(codRecapitoAlternativo).append("'");
        sb.append(",\n         idSoggettoIstanza:").append(idSoggettoIstanza);
        sb.append(",\n         idComuneResidenza:").append(idComuneResidenza);
        sb.append(",\n         idComuneSedeLegale:").append(idComuneSedeLegale);
        sb.append(",\n         indirizzoSoggetto:'").append(indirizzoSoggetto).append("'");
        sb.append(",\n         numCivicoIndirizzo:'").append(numCivicoIndirizzo).append("'");
        sb.append(",\n         cittaEsteraResidenza:'").append(cittaEsteraResidenza).append("'");
        sb.append(",\n         idNazioneResidenza:").append(idNazioneResidenza);
        sb.append(",\n         presso:'").append(presso).append("'");
        sb.append(",\n         numTelefono:'").append(numTelefono).append("'");
        sb.append(",\n         numCellulare:'").append(numCellulare).append("'");
        sb.append(",\n         desLocalita:'").append(desLocalita).append("'");
        sb.append(",\n         desEmail:'").append(desEmail).append("'");
        sb.append(",\n         desPec:'").append(desPec).append("'");
        sb.append(",\n         cittaEsteraSedeLegale:'").append(cittaEsteraSedeLegale).append("'");
        sb.append(",\n         idNazioneSedeLegale:").append(idNazioneSedeLegale);
        sb.append(",\n         capResidenza:'").append(capResidenza).append("'");
        sb.append(",\n         capSedeLegale:'").append(capSedeLegale).append("'");
        sb.append(",\n         idIstanzaAttore:").append(idIstanzaAttore);
        sb.append("}\n");
        return sb.toString();
    }
}