/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * The type Accreditamento dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericInputParDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("list")
    private List<String> list;


    public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}


	@Override
	public int hashCode() {
		return Objects.hash(list);
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		GenericInputParDTO other = (GenericInputParDTO) obj;
		return Objects.equals(list, other.list);
	}


	@Override
	public String toString() {
		return "GenericInputParDTO [list=" + list + "]";
	}

    /**
     * @return string
     */


}