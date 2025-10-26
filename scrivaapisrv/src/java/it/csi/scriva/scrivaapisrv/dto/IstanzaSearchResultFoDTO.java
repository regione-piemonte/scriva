/*

========================LICENSE_START=================================
Copyright (C) 2025 Regione Piemonte
SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
SPDX-License-Identifier: EUPL-1.2
=========================LICENSE_END==================================
*/
package it.csi.scriva.scrivaapisrv.dto;

import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.IstanzaSearchResultDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * The type Istanza search result fo dto.
 *
 * @author CSI PIEMONTE
 */
public class IstanzaSearchResultFoDTO implements Serializable {

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

    @Override
    public int hashCode() {
        return Objects.hash(countIstanzeHeader, istanzaSearchResultDTOList, paginationInfoHeader);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        IstanzaSearchResultFoDTO other = (IstanzaSearchResultFoDTO) obj;
        return Objects.equals(countIstanzeHeader, other.countIstanzeHeader) && Objects.equals(istanzaSearchResultDTOList, other.istanzaSearchResultDTOList) && Objects.equals(paginationInfoHeader, other.paginationInfoHeader);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SearchFODTO {\n    istanzaSearchResultDTOList: ").append(istanzaSearchResultDTOList).append("\n    paginationInfoHeader: ").append(paginationInfoHeader).append("\n    countIstanzeHeader: ").append(countIstanzeHeader).append("\n}");
        return builder.toString();
    }

}