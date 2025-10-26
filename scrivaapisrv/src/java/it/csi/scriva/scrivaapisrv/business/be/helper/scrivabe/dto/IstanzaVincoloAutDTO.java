/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/*
 *  SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */

package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Istanza vincolo aut dto.
 */
/*
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IstanzaVincoloAutDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_istanza_vincolo_aut")
    private Long idIstanzaVincoloAut;

    @JsonProperty("id_istanza")
    private Long idIstanza;

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

    @JsonProperty("des_motivo_richiesta_port")
    private String desMotivoRichiestaPort;

    @JsonProperty("id_istanza_attore")
    private Long idIstanzaAttore;

    /**
     * Gets id istanza vincolo aut.
     *
     * @return the id istanza vincolo aut
     */
    public Long getIdIstanzaVincoloAut() {
        return idIstanzaVincoloAut;
    }

    /**
     * Sets id istanza vincolo aut.
     *
     * @param idIstanzaVincoloAut the id istanza vincolo aut
     */
    public void setIdIstanzaVincoloAut(Long idIstanzaVincoloAut) {
        this.idIstanzaVincoloAut = idIstanzaVincoloAut;
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
     * Gets des vincolo utente.
     *
     * @return the des vincolo utente
     */
    public String getDesVincoloCalcolato() {
        return desVincoloCalcolato;
    }

    /**
     * Sets des vincolo utente.
     *
     * @param desVincoloCalcolato the des vincolo utente
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
     * Gets des motivo richiesta port.
     *
     * @return the des motivo richiesta port
     */
    public String getDesMotivoRichiestaPort() {
        return desMotivoRichiestaPort;
    }

    /**
     * Sets des motivo richiesta port.
     *
     * @param desMotivoRichiestaPort the des motivo richiesta port
     */
    public void setDesMotivoRichiestaPort(String desMotivoRichiestaPort) {
        this.desMotivoRichiestaPort = desMotivoRichiestaPort;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        IstanzaVincoloAutDTO that = (IstanzaVincoloAutDTO) o;
        return Objects.equals(idIstanzaVincoloAut, that.idIstanzaVincoloAut) && Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idVincoloAutorizza, that.idVincoloAutorizza) && Objects.equals(desVincoloCalcolato, that.desVincoloCalcolato) && Objects.equals(desEnteUtente, that.desEnteUtente) && Objects.equals(desEmailPecEnteUtente, that.desEmailPecEnteUtente) && Objects.equals(flgRichiestaPost, that.flgRichiestaPost) && Objects.equals(desMotivoRichiestaPort, that.desMotivoRichiestaPort) && Objects.equals(idIstanzaAttore, that.idIstanzaAttore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idIstanzaVincoloAut, idIstanza, idVincoloAutorizza, desVincoloCalcolato, desEnteUtente, desEmailPecEnteUtente, flgRichiestaPost, desMotivoRichiestaPort, idIstanzaAttore);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IstanzaVincoloAutDTO {");
        sb.append(super.toString());
        sb.append("         idIstanzaVincoloAut:").append(idIstanzaVincoloAut);
        sb.append(",         idIstanza:").append(idIstanza);
        sb.append(",         idVincoloAutorizza:").append(idVincoloAutorizza);
        sb.append(",         desVincoloUtente:'").append(desVincoloCalcolato).append("'");
        sb.append(",         desEnteUtente:'").append(desEnteUtente).append("'");
        sb.append(",         desEmailPecEnteUtente:'").append(desEmailPecEnteUtente).append("'");
        sb.append(",         flgRichiestaPost:").append(flgRichiestaPost);
        sb.append(",         desMotivoRichiestaPort:'").append(desMotivoRichiestaPort).append("'");
        sb.append(",         idIstanzaAttore:").append(idIstanzaAttore);
        sb.append("}");
        return sb.toString();
    }
}