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
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * The type Stato osservazione dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatoOsservazioneContatoreExtDTO implements Serializable {

    @JsonProperty("val")
    private List<StatoOsservazioneContatoreDTO> stato;

    /**
     * Gets stato.
     *
     * @return the stato
     */
    public List<StatoOsservazioneContatoreDTO> getStato() {
        return stato;
    }

    /**
     * Sets stato.
     *
     * @param stato the stato
     */
    public void setStato(List<StatoOsservazioneContatoreDTO> stato) {
        this.stato = stato;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((stato == null) ? 0 : stato.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StatoOsservazioneContatoreExtDTO other = (StatoOsservazioneContatoreExtDTO) obj;
        if (stato == null) {
            if (other.stato != null)
                return false;
        } else if (!stato.equals(other.stato))
            return false;
        return true;
    }


    /**
     * Gets map.
     *
     * @return the map
     */
    public HashMap<String, String> getMap() {
        HashMap<String, String> map = new HashMap<>();
        List<StatoOsservazioneContatoreDTO> stato = this.stato;
        int count = 0;
        for (StatoOsservazioneContatoreDTO statoOsservazioneContatoreDTO : stato) {
            count++;
            map.put(count + "_StatoOsservazione", statoOsservazioneContatoreDTO.getStatoOsservazione());
            map.put(count + "_NumeroOsservazione", statoOsservazioneContatoreDTO.getNumeroOsservazione().toString());
        }

        return map;
    }


    /**
     * Gets map tree.
     *
     * @return the map tree
     */
    public TreeMap<String, String> getMapTree() {
        TreeMap<String, String> map = new TreeMap<>();
        List<StatoOsservazioneContatoreDTO> stato = this.stato;
        int count = 0;
        for (StatoOsservazioneContatoreDTO statoOsservazioneContatoreDTO : stato) {
            count++;
            map.put(count + "_StatoOsservazione", statoOsservazioneContatoreDTO.getStatoOsservazione());
            map.put(count + "_NumeroOsservazione", statoOsservazioneContatoreDTO.getNumeroOsservazione().toString());
        }

        return map;
    }


}