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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Oggetto istanza vincolo autorizza dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OggettoIstanzaVincoloAutorizzaDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_oggetto_vincolo_aut")
    private Long idOggettoVincoloAut;

    @JsonProperty("id_oggetto_istanza")
    private Long idOggettoIstanza;

    @JsonProperty("id_vincolo_autorizza")
    private Long idVincoloAutorizza;

    @JsonProperty("des_vincolo_calcolato")
    private String desVincoloCalcolato;

    @JsonProperty("des_ente_utente")
    private String desEnteUtente;

    @JsonProperty("des_email_pec_ente_utente")
    private String desEmailPecEnteUtente;

    @JsonProperty("flg_richiesta_post")
    private Boolean flgRichiestaPost;

    @JsonProperty("des_motivo_richiesta_post")
    private String desMotivoRichiestaPost;

    /**
     * Gets id oggetto vincolo aut.
     *
     * @return the id oggetto vincolo aut
     */
    public Long getIdOggettoVincoloAut() {
        return idOggettoVincoloAut;
    }

    /**
     * Sets id oggetto vincolo aut.
     *
     * @param idOggettoVincoloAut the id oggetto vincolo aut
     */
    public void setIdOggettoVincoloAut(Long idOggettoVincoloAut) {
        this.idOggettoVincoloAut = idOggettoVincoloAut;
    }

    /**
     * Gets id oggetto istanza.
     *
     * @return the id oggetto istanza
     */
    public Long getIdOggettoIstanza() {
        return idOggettoIstanza;
    }

    /**
     * Sets id oggetto istanza.
     *
     * @param idOggettoIstanza the id oggetto istanza
     */
    public void setIdOggettoIstanza(Long idOggettoIstanza) {
        this.idOggettoIstanza = idOggettoIstanza;
    }

    /**
     * Gets id vincolo autorizza.
     *
     * @return the id vincolo autorizza
     */
    public Long getIdVincoloAutorizza() {
        return idVincoloAutorizza;
    }

    /**
     * Sets id vincolo autorizza.
     *
     * @param idVincoloAutorizza the id vincolo autorizza
     */
    public void setIdVincoloAutorizza(Long idVincoloAutorizza) {
        this.idVincoloAutorizza = idVincoloAutorizza;
    }

    /**
     * Gets des vincolo calcolato.
     *
     * @return the des vincolo calcolato
     */
    public String getDesVincoloCalcolato() {
        return desVincoloCalcolato;
    }

    /**
     * Sets des vincolo calcolato.
     *
     * @param desVincoloCalcolato the des vincolo calcolato
     */
    public void setDesVincoloCalcolato(String desVincoloCalcolato) {
        this.desVincoloCalcolato = desVincoloCalcolato;
    }

    /**
     * Gets des ente utente.
     *
     * @return the des ente utente
     */
    public String getDesEnteUtente() {
        return desEnteUtente;
    }

    /**
     * Sets des ente utente.
     *
     * @param desEnteUtente the des ente utente
     */
    public void setDesEnteUtente(String desEnteUtente) {
        this.desEnteUtente = desEnteUtente;
    }

    /**
     * Gets des email pec ente utente.
     *
     * @return the des email pec ente utente
     */
    public String getDesEmailPecEnteUtente() {
        return desEmailPecEnteUtente;
    }

    /**
     * Sets des email pec ente utente.
     *
     * @param desEmailPecEnteUtente the des email pec ente utente
     */
    public void setDesEmailPecEnteUtente(String desEmailPecEnteUtente) {
        this.desEmailPecEnteUtente = desEmailPecEnteUtente;
    }

    /**
     * Gets flg richiesta post.
     *
     * @return the flg richiesta post
     */
    public Boolean getFlgRichiestaPost() {
        return flgRichiestaPost;
    }

    /**
     * Sets flg richiesta post.
     *
     * @param flgRichiestaPost the flg richiesta post
     */
    public void setFlgRichiestaPost(Boolean flgRichiestaPost) {
        this.flgRichiestaPost = flgRichiestaPost;
    }

    /**
     * Gets des motivo richiesta post.
     *
     * @return the des motivo richiesta post
     */
    public String getDesMotivoRichiestaPost() {
        return desMotivoRichiestaPost;
    }

    /**
     * Sets des motivo richiesta post.
     *
     * @param desMotivoRichiestaPost the des motivo richiesta post
     */
    public void setDesMotivoRichiestaPost(String desMotivoRichiestaPost) {
        this.desMotivoRichiestaPost = desMotivoRichiestaPost;
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
        OggettoIstanzaVincoloAutorizzaDTO that = (OggettoIstanzaVincoloAutorizzaDTO) o;
        return Objects.equals(idOggettoVincoloAut, that.idOggettoVincoloAut) && Objects.equals(idOggettoIstanza, that.idOggettoIstanza) && Objects.equals(idVincoloAutorizza, that.idVincoloAutorizza) && Objects.equals(desVincoloCalcolato, that.desVincoloCalcolato) && Objects.equals(desEnteUtente, that.desEnteUtente) && Objects.equals(desEmailPecEnteUtente, that.desEmailPecEnteUtente) && Objects.equals(flgRichiestaPost, that.flgRichiestaPost) && Objects.equals(desMotivoRichiestaPost, that.desMotivoRichiestaPost);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idOggettoVincoloAut, idOggettoIstanza, idVincoloAutorizza, desVincoloCalcolato, desEnteUtente, desEmailPecEnteUtente, flgRichiestaPost, desMotivoRichiestaPost);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OggettoIstanzaVincoloAutorizzaDTO {\n");
        sb.append("         gestDataIns:").append(gestDataIns);
        sb.append(",\n         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",\n         gestDataUpd:").append(gestDataUpd);
        sb.append(",\n         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",\n         gestUID:'").append(gestUID).append("'");
        sb.append(",\n         idOggettoVincoloAut:").append(idOggettoVincoloAut);
        sb.append(",\n         idOggettoIstanza:").append(idOggettoIstanza);
        sb.append(",\n         idVincoloAutorizza:").append(idVincoloAutorizza);
        sb.append(",\n         desVincoloCalcolato:'").append(desVincoloCalcolato).append("'");
        sb.append(",\n         desEnteUtente:'").append(desEnteUtente).append("'");
        sb.append(",\n         desEmailPecEnteUtente:'").append(desEmailPecEnteUtente).append("'");
        sb.append(",\n         flgRichiestaPost:").append(flgRichiestaPost);
        sb.append(",\n         desMotivoRichiestaPost:'").append(desMotivoRichiestaPost).append("'");
        sb.append("}\n");
        return sb.toString();
    }
}