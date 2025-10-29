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

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * The type Configurazione dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigurazioneNotificaExtendedDTO extends ConfigurazioneNotificaDTO implements Serializable {

    private CanaleNotificaDTO canaleNotifica;

    private TipoNotificaEventoExtendedDTO tipoNotificaEvento;

    private TipoNotificaEventoAdempiExtendedDTO tipoNotificaEventoAdempi;

    private DestinatarioExtendedDTO destinatario;

    private CompetenzaTerritorioExtendedDTO competenzaTerritorio;

    private TipoCompetenzaDTO tipoCompetenza;

    private List<ConfigurazioneNotificaAllegatoDTO> configurazioneNotificaAllegato;

    /**
     * Gets canale notifica.
     *
     * @return the canale notifica
     */
    public CanaleNotificaDTO getCanaleNotifica() {
        return canaleNotifica;
    }

    /**
     * Sets canale notifica.
     *
     * @param canaleNotifica the canale notifica
     */
    public void setCanaleNotifica(CanaleNotificaDTO canaleNotifica) {
        this.canaleNotifica = canaleNotifica;
    }

    /**
     * Gets tipo notifica evento.
     *
     * @return the tipo notifica evento
     */
    public TipoNotificaEventoExtendedDTO getTipoNotificaEvento() {
        return tipoNotificaEvento;
    }

    /**
     * Sets tipo notifica evento.
     *
     * @param tipoNotificaEvento the tipo notifica evento
     */
    public void setTipoNotificaEvento(TipoNotificaEventoExtendedDTO tipoNotificaEvento) {
        this.tipoNotificaEvento = tipoNotificaEvento;
    }

    /**
     * Gets tipo notifica evento adempi.
     *
     * @return the tipo notifica evento adempi
     */
    public TipoNotificaEventoAdempiExtendedDTO getTipoNotificaEventoAdempi() {
        return tipoNotificaEventoAdempi;
    }

    /**
     * Sets tipo notifica evento adempi.
     *
     * @param tipoNotificaEventoAdempi the tipo notifica evento adempi
     */
    public void setTipoNotificaEventoAdempi(TipoNotificaEventoAdempiExtendedDTO tipoNotificaEventoAdempi) {
        this.tipoNotificaEventoAdempi = tipoNotificaEventoAdempi;
    }

    /**
     * Gets destinatario.
     *
     * @return the destinatario
     */
    public DestinatarioExtendedDTO getDestinatario() {
        return destinatario;
    }

    /**
     * Sets destinatario.
     *
     * @param destinatario the destinatario
     */
    public void setDestinatario(DestinatarioExtendedDTO destinatario) {
        this.destinatario = destinatario;
    }

    /**
     * Gets competenza territorio.
     *
     * @return the competenza territorio
     */
    public CompetenzaTerritorioExtendedDTO getCompetenzaTerritorio() {
        return competenzaTerritorio;
    }

    /**
     * Sets competenza territorio.
     *
     * @param competenzaTerritorio the competenza territorio
     */
    public void setCompetenzaTerritorio(CompetenzaTerritorioExtendedDTO competenzaTerritorio) {
        this.competenzaTerritorio = competenzaTerritorio;
    }

    /**
     * Gets tipo competenza.
     *
     * @return the tipo competenza
     */
    public TipoCompetenzaDTO getTipoCompetenza() {
        return tipoCompetenza;
    }

    /**
     * Sets tipo competenza.
     *
     * @param tipoCompetenza the tipo competenza
     */
    public void setTipoCompetenza(TipoCompetenzaDTO tipoCompetenza) {
        this.tipoCompetenza = tipoCompetenza;
    }

    /**
     * Gets configurazione notifica allegato.
     *
     * @return the configurazione notifica allegato
     */
    public List<ConfigurazioneNotificaAllegatoDTO> getConfigurazioneNotificaAllegato() {
        return configurazioneNotificaAllegato;
    }

    /**
     * Sets configurazione notifica allegato.
     *
     * @param configurazioneNotificaAllegato the configurazione notifica allegato
     */
    public void setConfigurazioneNotificaAllegato(List<ConfigurazioneNotificaAllegatoDTO> configurazioneNotificaAllegato) {
        this.configurazioneNotificaAllegato = configurazioneNotificaAllegato;
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
        ConfigurazioneNotificaExtendedDTO that = (ConfigurazioneNotificaExtendedDTO) o;
        return Objects.equals(canaleNotifica, that.canaleNotifica) && Objects.equals(tipoNotificaEvento, that.tipoNotificaEvento) && Objects.equals(tipoNotificaEventoAdempi, that.tipoNotificaEventoAdempi) && Objects.equals(destinatario, that.destinatario) && Objects.equals(competenzaTerritorio, that.competenzaTerritorio) && Objects.equals(tipoCompetenza, that.tipoCompetenza) && Objects.equals(configurazioneNotificaAllegato, that.configurazioneNotificaAllegato);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), canaleNotifica, tipoNotificaEvento, tipoNotificaEventoAdempi, destinatario, competenzaTerritorio, tipoCompetenza, configurazioneNotificaAllegato);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "ConfigurazioneNotificaExtendedDTO {\n" +
                "         canaleNotifica:" + canaleNotifica +
                ",\n         tipoNotificaEvento:" + tipoNotificaEvento +
                ",\n         tipoNotificaEventoAdempi:" + tipoNotificaEventoAdempi +
                ",\n         destinatario:" + destinatario +
                ",\n         competenzaTerritorio:" + competenzaTerritorio +
                ",\n         tipoCompetenza:" + tipoCompetenza +
                ",\n         configurazioneNotificaAllegato:" + configurazioneNotificaAllegato +
                super.toString() +
                "}\n";
    }
}