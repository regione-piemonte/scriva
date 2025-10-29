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
import java.util.Objects;

/**
 * The type Istanza attore extended dto.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IstanzaAttoreExtendedDTO extends IstanzaAttoreDTO implements Serializable {

    @JsonProperty("tipo_abilitazione")
    private TipoAbilitazioneDTO tipoAbilitazione;

    @JsonProperty("compilante")
    private CompilanteDTO compilante;

    @JsonProperty("profilo_app")
    private ProfiloAppExtendedDTO profiloApp;

    /**
     * Gets tipo abilitazione.
     *
     * @return TipoAbilitazioneDTO tipo abilitazione
     */
    public TipoAbilitazioneDTO getTipoAbilitazione() {
        return tipoAbilitazione;
    }

    /**
     * Sets tipo abilitazione.
     *
     * @param tipoAbilitazione TipoAbilitazioneDTO
     */
    public void setTipoAbilitazione(TipoAbilitazioneDTO tipoAbilitazione) {
        this.tipoAbilitazione = tipoAbilitazione;
    }

    /**
     * Gets compilante.
     *
     * @return CompilanteDTO compilante
     */
    public CompilanteDTO getCompilante() {
        return compilante;
    }

    /**
     * Sets compilante.
     *
     * @param compilante CompilanteDTO
     */
    public void setCompilante(CompilanteDTO compilante) {
        this.compilante = compilante;
    }

    /**
     * Gets profilo app.
     *
     * @return ProfiloAppExtendedDTO profilo app
     */
    public ProfiloAppExtendedDTO getProfiloApp() {
        return profiloApp;
    }

    /**
     * Sets profilo app.
     *
     * @param profiloApp ProfiloAppExtendedDTO
     */
    public void setProfiloApp(ProfiloAppExtendedDTO profiloApp) {
        this.profiloApp = profiloApp;
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
        IstanzaAttoreExtendedDTO that = (IstanzaAttoreExtendedDTO) o;
        return Objects.equals(tipoAbilitazione, that.tipoAbilitazione) && Objects.equals(compilante, that.compilante) && Objects.equals(profiloApp, that.profiloApp);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tipoAbilitazione, compilante, profiloApp);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IstanzaAttoreExtendedDTO {");
        sb.append(super.toString());
        sb.append(",         tipoAbilitazione:").append(tipoAbilitazione);
        sb.append(",         compilante:").append(compilante);
        sb.append(",         profiloApp:").append(profiloApp);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return the dto
     */
    @JsonIgnore
    public IstanzaAttoreDTO getDTO() {
        IstanzaAttoreDTO dto = new IstanzaAttoreDTO();
        dto.setIdIstanzaAttore(this.getIdIstanzaAttore());
        dto.setIdIstanza(this.getIdIstanza());
        if (null != this.getTipoAbilitazione()) {
            dto.setIdTipoAbilitazione(this.getTipoAbilitazione().getIdTipoAbilitazione());
        }
        if (null != this.getCompilante()) {
            dto.setIdCompilante(this.getCompilante().getIdCompilante());
        }
        if (null != this.getProfiloApp()) {
            dto.setIdProfiloApp(this.getProfiloApp().getIdProfiloApp());
        }
        dto.setCfAttore(this.getCfAttore());
        dto.setCfAbilitanteDelegante(this.getCfAbilitanteDelegante());
        dto.setDataInizio(this.getDataInizio());
        dto.setDataRevoca(this.getDataRevoca());
        dto.setDataDelegaConFirma(this.getDataDelegaConFirma());
        dto.setGestAttoreIns(this.getGestAttoreIns());
        dto.setGestDataIns(this.getGestDataIns());
        dto.setGestAttoreUpd(this.getGestAttoreUpd());
        dto.setGestDataUpd(this.getGestDataUpd());
        dto.setGestUID(this.getGestUID());
        return dto;
    }
}