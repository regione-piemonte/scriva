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

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 * The type Destinatario dto.
 *
 * @author CSI PIEMONTE
 */
public class DestinatarioDTO implements Serializable {

    private Long idDestinatario;

    private Long idTipoDestinatario;

    private Long idProfiloApp;

    private Long idComponenteApp;

    private String codDestinatario;

    private String denDestinatario;

    private String notaDestinatario;

    private String nome;

    private String cognome;

    private String desUfficioEnte;

    private String desNota;

    private String desEmail;

    private String numCellulare;

    private String desServizioApplicativo;

    private Boolean flgVerificaPreferenzeNotifiche;

    private Date dataInizio;

    private Date dataFine;

    /**
     * Gets id destinatario.
     *
     * @return the id destinatario
     */
    public Long getIdDestinatario() {
        return idDestinatario;
    }

    /**
     * Sets id destinatario.
     *
     * @param idDestinatario the id destinatario
     */
    public void setIdDestinatario(Long idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    /**
     * Gets id tipo destinatario.
     *
     * @return the id tipo destinatario
     */
    public Long getIdTipoDestinatario() {
        return idTipoDestinatario;
    }

    /**
     * Sets id tipo destinatario.
     *
     * @param idTipoDestinatario the id tipo destinatario
     */
    public void setIdTipoDestinatario(Long idTipoDestinatario) {
        this.idTipoDestinatario = idTipoDestinatario;
    }

    /**
     * Gets id profilo app.
     *
     * @return the id profilo app
     */
    public Long getIdProfiloApp() {
        return idProfiloApp;
    }

    /**
     * Sets id profilo app.
     *
     * @param idProfiloApp the id profilo app
     */
    public void setIdProfiloApp(Long idProfiloApp) {
        this.idProfiloApp = idProfiloApp;
    }

    /**
     * Gets id componente app.
     *
     * @return the id componente app
     */
    public Long getIdComponenteApp() {
        return idComponenteApp;
    }

    /**
     * Sets id componente app.
     *
     * @param idComponenteApp the id componente app
     */
    public void setIdComponenteApp(Long idComponenteApp) {
        this.idComponenteApp = idComponenteApp;
    }

    /**
     * Gets cod destinatario.
     *
     * @return the cod destinatario
     */
    public String getCodDestinatario() {
        return codDestinatario;
    }

    /**
     * Sets cod destinatario.
     *
     * @param codDestinatario the cod destinatario
     */
    public void setCodDestinatario(String codDestinatario) {
        this.codDestinatario = codDestinatario;
    }

    /**
     * Gets den destinatario.
     *
     * @return the den destinatario
     */
    public String getDenDestinatario() {
        return denDestinatario;
    }

    /**
     * Sets den destinatario.
     *
     * @param denDestinatario the den destinatario
     */
    public void setDenDestinatario(String denDestinatario) {
        this.denDestinatario = denDestinatario;
    }

    /**
     * Gets nota destinatario.
     *
     * @return the nota destinatario
     */
    public String getNotaDestinatario() {
        return notaDestinatario;
    }

    /**
     * Sets nota destinatario.
     *
     * @param notaDestinatario the nota destinatario
     */
    public void setNotaDestinatario(String notaDestinatario) {
        this.notaDestinatario = notaDestinatario;
    }

    /**
     * Gets nome.
     *
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Sets nome.
     *
     * @param nome the nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Gets cognome.
     *
     * @return the cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Sets cognome.
     *
     * @param cognome the cognome
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Gets des ufficio ente.
     *
     * @return the des ufficio ente
     */
    public String getDesUfficioEnte() {
        return desUfficioEnte;
    }

    /**
     * Sets des ufficio ente.
     *
     * @param desUfficioEnte the des ufficio ente
     */
    public void setDesUfficioEnte(String desUfficioEnte) {
        this.desUfficioEnte = desUfficioEnte;
    }

    /**
     * Gets des nota.
     *
     * @return the des nota
     */
    public String getDesNota() {
        return desNota;
    }

    /**
     * Sets des nota.
     *
     * @param desNota the des nota
     */
    public void setDesNota(String desNota) {
        this.desNota = desNota;
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
     * Gets des servizio applicativo.
     *
     * @return the des servizio applicativo
     */
    public String getDesServizioApplicativo() {
        return desServizioApplicativo;
    }

    /**
     * Sets des servizio applicativo.
     *
     * @param desServizioApplicativo the des servizio applicativo
     */
    public void setDesServizioApplicativo(String desServizioApplicativo) {
        this.desServizioApplicativo = desServizioApplicativo;
    }

    /**
     * Gets flg verifica preferenze notifiche.
     *
     * @return the flg verifica preferenze notifiche
     */
    public Boolean getFlgVerificaPreferenzeNotifiche() {
        return flgVerificaPreferenzeNotifiche;
    }

    /**
     * Sets flg verifica preferenze notifiche.
     *
     * @param flgVerificaPreferenzeNotifiche the flg verifica preferenze notifiche
     */
    public void setFlgVerificaPreferenzeNotifiche(Boolean flgVerificaPreferenzeNotifiche) {
        this.flgVerificaPreferenzeNotifiche = flgVerificaPreferenzeNotifiche;
    }

    /**
     * Gets data inizio.
     *
     * @return the data inizio
     */
    public Date getDataInizio() {
        return dataInizio;
    }

    /**
     * Sets data inizio.
     *
     * @param dataInizio the data inizio
     */
    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    /**
     * Gets data fine.
     *
     * @return the data fine
     */
    public Date getDataFine() {
        return dataFine;
    }

    /**
     * Sets data fine.
     *
     * @param dataFine the data fine
     */
    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DestinatarioDTO that = (DestinatarioDTO) o;
        return Objects.equals(idDestinatario, that.idDestinatario) && Objects.equals(idTipoDestinatario, that.idTipoDestinatario) && Objects.equals(idProfiloApp, that.idProfiloApp) && Objects.equals(idComponenteApp, that.idComponenteApp) && Objects.equals(codDestinatario, that.codDestinatario) && Objects.equals(denDestinatario, that.denDestinatario) && Objects.equals(notaDestinatario, that.notaDestinatario) && Objects.equals(nome, that.nome) && Objects.equals(cognome, that.cognome) && Objects.equals(desUfficioEnte, that.desUfficioEnte) && Objects.equals(desNota, that.desNota) && Objects.equals(desEmail, that.desEmail) && Objects.equals(numCellulare, that.numCellulare) && Objects.equals(desServizioApplicativo, that.desServizioApplicativo) && Objects.equals(flgVerificaPreferenzeNotifiche, that.flgVerificaPreferenzeNotifiche) && Objects.equals(dataInizio, that.dataInizio) && Objects.equals(dataFine, that.dataFine);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idDestinatario, idTipoDestinatario, idProfiloApp, idComponenteApp, codDestinatario, denDestinatario, notaDestinatario, nome, cognome, desUfficioEnte, desNota, desEmail, numCellulare, desServizioApplicativo, flgVerificaPreferenzeNotifiche, dataInizio, dataFine);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "DestinatarioDTO {\n" +
                "         idDestinatario:" + idDestinatario +
                ",\n         idTipoDestinatario:" + idTipoDestinatario +
                ",\n         idProfiloApp:" + idProfiloApp +
                ",\n         idComponenteApp:" + idComponenteApp +
                ",\n         codDestinatario:'" + codDestinatario + "'" +
                ",\n         denDestinatario:'" + denDestinatario + "'" +
                ",\n         notaDestinatario:'" + notaDestinatario + "'" +
                ",\n         nome:'" + nome + "'" +
                ",\n         cognome:'" + cognome + "'" +
                ",\n         desUfficioEnte:'" + desUfficioEnte + "'" +
                ",\n         desNota:'" + desNota + "'" +
                ",\n         desEmail:'" + desEmail + "'" +
                ",\n         numCellulare:'" + numCellulare + "'" +
                ",\n         desServizioApplicativo:'" + desServizioApplicativo + "'" +
                ",\n         flgVerificaPreferenzeNotifiche:" + flgVerificaPreferenzeNotifiche +
                ",\n         dataInizio:" + dataInizio +
                ",\n         dataFine:" + dataFine +
                "}\n";
    }
}