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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * The type Notifica allegato dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificaAllegatoDTO extends BaseDTO implements Serializable {

    private Long idNotificaAllegato;
    private Long idNotifica;
    private String desSegnalazione;
    private Long idAllegatoIstanza;
    private String codAllegato;
    private String nomeAllegato;
    private Boolean flgAllegatoFallito;
    private String gestUidRichiesta;
    private String desTipoRichiesta;
    private String uuidIndex;
    private File fileAllegato;

    /**
     * Gets id notifica allegato.
     *
     * @return the id notifica allegato
     */
    public Long getIdNotificaAllegato() {
        return idNotificaAllegato;
    }

    /**
     * Sets id notifica allegato.
     *
     * @param idNotificaAllegato the id notifica allegato
     */
    public void setIdNotificaAllegato(Long idNotificaAllegato) {
        this.idNotificaAllegato = idNotificaAllegato;
    }

    /**
     * Gets id notifica.
     *
     * @return the id notifica
     */
    public Long getIdNotifica() {
        return idNotifica;
    }

    /**
     * Sets id notifica.
     *
     * @param idNotifica the id notifica
     */
    public void setIdNotifica(Long idNotifica) {
        this.idNotifica = idNotifica;
    }

    /**
     * Gets des segnalazione.
     *
     * @return the des segnalazione
     */
    public String getDesSegnalazione() {
        return desSegnalazione;
    }

    /**
     * Sets des segnalazione.
     *
     * @param desSegnalazione the des segnalazione
     */
    public void setDesSegnalazione(String desSegnalazione) {
        this.desSegnalazione = desSegnalazione;
    }

    /**
     * Gets id allegato istanza.
     *
     * @return the id allegato istanza
     */
    public Long getIdAllegatoIstanza() {
        return idAllegatoIstanza;
    }

    /**
     * Sets id allegato istanza.
     *
     * @param idAllegatoIstanza the id allegato istanza
     */
    public void setIdAllegatoIstanza(Long idAllegatoIstanza) {
        this.idAllegatoIstanza = idAllegatoIstanza;
    }

    /**
     * Gets cod allegato.
     *
     * @return the cod allegato
     */
    public String getCodAllegato() {
        return codAllegato;
    }

    /**
     * Sets cod allegato.
     *
     * @param codAllegato the cod allegato
     */
    public void setCodAllegato(String codAllegato) {
        this.codAllegato = codAllegato;
    }

    /**
     * Gets nome allegato.
     *
     * @return the nome allegato
     */
    public String getNomeAllegato() {
        return nomeAllegato;
    }

    /**
     * Sets nome allegato.
     *
     * @param nomeAllegato the nome allegato
     */
    public void setNomeAllegato(String nomeAllegato) {
        this.nomeAllegato = nomeAllegato;
    }

    /**
     * Gets flg allegato fallito.
     *
     * @return the flg allegato fallito
     */
    public Boolean getFlgAllegatoFallito() {
        return flgAllegatoFallito;
    }

    /**
     * Sets flg allegato fallito.
     *
     * @param flgAllegatoFallito the flg allegato fallito
     */
    public void setFlgAllegatoFallito(Boolean flgAllegatoFallito) {
        this.flgAllegatoFallito = flgAllegatoFallito;
    }

    /**
     * Gets gest uid richiesta.
     *
     * @return the gest uid richiesta
     */
    public String getGestUidRichiesta() {
        return gestUidRichiesta;
    }

    /**
     * Sets gest uid richiesta.
     *
     * @param gestUidRichiesta the gest uid richiesta
     */
    public void setGestUidRichiesta(String gestUidRichiesta) {
        this.gestUidRichiesta = gestUidRichiesta;
    }

    /**
     * Gets des tipo richiesta.
     *
     * @return the des tipo richiesta
     */
    public String getDesTipoRichiesta() {
        return desTipoRichiesta;
    }

    /**
     * Sets des tipo richiesta.
     *
     * @param desTipoRichiesta the des tipo richiesta
     */
    public void setDesTipoRichiesta(String desTipoRichiesta) {
        this.desTipoRichiesta = desTipoRichiesta;
    }

    

    /**
     * @return the uuidIndex
     */
    public String getUuidIndex() {
      return uuidIndex;
    }

    /**
     * @param uuidIndex the uuidIndex to set
     */
    public void setUuidIndex(String uuidIndex) {
      this.uuidIndex = uuidIndex;
    }

    /**
     * @return the fileAllegato
     */
    public File getFileAllegato() {
      return fileAllegato;
    }

    /**
     * @param fileAllegato the fileAllegato to set
     */
    public void setFileAllegato(File fileAllegato) {
      this.fileAllegato = fileAllegato;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (!super.equals(obj))
        return false;
      if (getClass() != obj.getClass())
        return false;
      NotificaAllegatoDTO other = (NotificaAllegatoDTO) obj;
      return Objects.equals(codAllegato, other.codAllegato)
              && Objects.equals(desSegnalazione, other.desSegnalazione)
              && Objects.equals(desTipoRichiesta, other.desTipoRichiesta)
              && Objects.equals(fileAllegato, other.fileAllegato)
              && Objects.equals(flgAllegatoFallito, other.flgAllegatoFallito)
              && Objects.equals(gestUidRichiesta, other.gestUidRichiesta)
              && Objects.equals(idAllegatoIstanza, other.idAllegatoIstanza)
              && Objects.equals(idNotifica, other.idNotifica)
              && Objects.equals(idNotificaAllegato, other.idNotificaAllegato)
              && Objects.equals(nomeAllegato, other.nomeAllegato)
              && Objects.equals(uuidIndex, other.uuidIndex);
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + Objects.hash(codAllegato, desSegnalazione, desTipoRichiesta,
              fileAllegato, flgAllegatoFallito, gestUidRichiesta, idAllegatoIstanza, idNotifica,
              idNotificaAllegato, nomeAllegato, uuidIndex);
      return result;
    }

    @Override
    public String toString() {
        return "NotificaAllegatoDTO {\n" +
                "         gestDataIns:" + gestDataIns +
                ",\n         gestAttoreIns:'" + gestAttoreIns + "'" +
                ",\n         gestDataUpd:" + gestDataUpd +
                ",\n         gestAttoreUpd:'" + gestAttoreUpd + "'" +
                ",\n         gestUID:'" + gestUID + "'" +
                ",\n         idNotificaAllegato:" + idNotificaAllegato +
                ",\n         idNotifica:" + idNotifica +
                ",\n         desSegnalazione:'" + desSegnalazione + "'" +
                ",\n         idAllegatoIstanza:" + idAllegatoIstanza +
                ",\n         codAllegato:'" + codAllegato + "'" +
                ",\n         nomeAllegato:'" + nomeAllegato + "'" +
                ",\n         flgAllegatoFallito:" + flgAllegatoFallito +
                ",\n         gestUidRichiesta:'" + gestUidRichiesta + "'" +
                ",\n         desTipoRichiesta:'" + desTipoRichiesta + "'" +
                ",\n         uuidIndex:'" + uuidIndex + "'" +
                "}\n";
    }
    
    
}