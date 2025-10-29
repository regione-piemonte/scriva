/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.dto;

import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.IstanzaSearchResultDTO;

import java.util.List;
import java.util.Objects;

/**
 * The type Istanza search result fo dto.
 */
public class IstanzaSearchResultFoDTO {

    private List<IstanzaSearchResultDTO> istanzaSearchResultDTOList;

    private String paginationInfoHeader;

    private String countIstanzeHeader;

    /**
     * Gets istanza search result dto list.
     *
     * @return the istanza search result dto list
     */
    public List<IstanzaSearchResultDTO> getIstanzaSearchResultDTOList() {
        return istanzaSearchResultDTOList;
    }

    /**
     * Sets istanza search result dto list.
     *
     * @param istanzaSearchResultDTOList the istanza search result dto list
     */
    public void setIstanzaSearchResultDTOList(List<IstanzaSearchResultDTO> istanzaSearchResultDTOList) {
        this.istanzaSearchResultDTOList = istanzaSearchResultDTOList;
    }

    /**
     * Gets pagination info header.
     *
     * @return the pagination info header
     */
    public String getPaginationInfoHeader() {
        return paginationInfoHeader;
    }

    /**
     * Sets pagination info header.
     *
     * @param paginationInfoHeader the pagination info header
     */
    public void setPaginationInfoHeader(String paginationInfoHeader) {
        this.paginationInfoHeader = paginationInfoHeader;
    }

    /**
     * Gets count istanze header.
     *
     * @return the count istanze header
     */
    public String getCountIstanzeHeader() {
        return countIstanzeHeader;
    }

    /**
     * Sets count istanze header.
     *
     * @param countIstanzeHeader the count istanze header
     */
    public void setCountIstanzeHeader(String countIstanzeHeader) {
        this.countIstanzeHeader = countIstanzeHeader;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IstanzaSearchResultFoDTO that = (IstanzaSearchResultFoDTO) o;
        return Objects.equals(istanzaSearchResultDTOList, that.istanzaSearchResultDTOList) && Objects.equals(paginationInfoHeader, that.paginationInfoHeader) && Objects.equals(countIstanzeHeader, that.countIstanzeHeader);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(istanzaSearchResultDTOList, paginationInfoHeader, countIstanzeHeader);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "IstanzaSearchResultFoDTO {\n" +
                "         istanzaSearchResultDTOList:" + istanzaSearchResultDTOList +
                ",\n         paginationInfoHeader:'" + paginationInfoHeader + "'" +
                ",\n         countIstanzeHeader:'" + countIstanzeHeader + "'" +
                "}\n";
    }
}