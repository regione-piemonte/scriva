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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * The type Date crit.
 *
 * @author CSI PIEMONTE
 */
public class DateCrit {
    // verra' utilizzata la seguente strategia serializzazione degli attributi: [explicit-as-modeled]

    private Date eq = null;
    private Date ne = null;
    private Date lt = null;
    private Date lte = null;
    private Date gt = null;
    private Date gte = null;
    private List<Date> in = new ArrayList<>();
    private List<Date> nin = new ArrayList<>();

    /**
     * Gets eq.
     *
     * @return Date equal
     */
    @JsonProperty("eq")
    public Date getEq() {
        return eq;
    }

    /**
     * Sets eq.
     *
     * @param eq Date equal
     */
    public void setEq(Date eq) {
        this.eq = eq;
    }

    /**
     * Gets ne.
     *
     * @return Date not equal
     */
    @JsonProperty("ne")
    public Date getNe() {
        return ne;
    }

    /**
     * Sets ne.
     *
     * @param ne Date not equal
     */
    public void setNe(Date ne) {
        this.ne = ne;
    }

    /**
     * Gets lt.
     *
     * @return Date lighter
     */
    @JsonProperty("lt")
    public Date getLt() {
        return lt;
    }

    /**
     * Sets lt.
     *
     * @param lt Date lighter
     */
    public void setLt(Date lt) {
        this.lt = lt;
    }

    /**
     * Gets lte.
     *
     * @return Date light than equal
     */
    @JsonProperty("lte")
    public Date getLte() {
        return lte;
    }

    /**
     * Sets lte.
     *
     * @param lte Date light than equal
     */
    public void setLte(Date lte) {
        this.lte = lte;
    }

    /**
     * Gets gt.
     *
     * @return Date great than
     */
    @JsonProperty("gt")
    public Date getGt() {
        return gt;
    }

    /**
     * Sets gt.
     *
     * @param gt the gt
     */
    public void setGt(Date gt) {
        this.gt = gt;
    }

    /**
     * Gets gte.
     *
     * @return Date great than equal
     */
    @JsonProperty("gte")
    public Date getGte() {
        return gte;
    }

    /**
     * Sets gte.
     *
     * @param gte Date greater than equal
     */
    public void setGte(Date gte) {
        this.gte = gte;
    }

    /**
     * Gets in.
     *
     * @return List<Date>  IN
     */
    @JsonProperty("in")
    public List<Date> getIn() {
        return in;
    }

    /**
     * Sets in.
     *
     * @param in List<Date> IN
     */
    public void setIn(List<Date> in) {
        this.in = in;
    }

    /**
     * Gets nin.
     *
     * @return List<Date>  NOT IN
     */
    @JsonProperty("nin")
    public List<Date> getNin() {
        return nin;
    }

    /**
     * Sets nin.
     *
     * @param nin List<Date> NOT IN
     */
    public void setNin(List<Date> nin) {
        this.nin = nin;
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DateCrit {");
        sb.append("         eq:").append(eq);
        sb.append(",         ne:").append(ne);
        sb.append(",         lt:").append(lt);
        sb.append(",         lte:").append(lte);
        sb.append(",         gt:").append(gt);
        sb.append(",         gte:").append(gte);
        sb.append(",         in:").append(in);
        sb.append(",         nin:").append(nin);
        sb.append("}");
        return sb.toString();
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DateCrit dateCrit = (DateCrit) o;
        return Objects.equals(eq, dateCrit.eq) && Objects.equals(ne, dateCrit.ne) && Objects.equals(lt, dateCrit.lt) && Objects.equals(lte, dateCrit.lte) && Objects.equals(gt, dateCrit.gt) && Objects.equals(gte, dateCrit.gte) && Objects.equals(in, dateCrit.in) && Objects.equals(nin, dateCrit.nin);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(eq, ne, lt, lte, gt, gte, in, nin);
    }
}