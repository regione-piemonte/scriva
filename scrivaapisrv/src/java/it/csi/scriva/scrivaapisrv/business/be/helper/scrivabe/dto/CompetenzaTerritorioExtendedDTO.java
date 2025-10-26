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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author CSI PIEMONTE
 */
public class CompetenzaTerritorioExtendedDTO extends CompetenzaTerritorioDTO implements Serializable {

    @JsonProperty("tipo_competenza")
    private TipoCompetenzaDTO tipoCompetenza;

    @JsonProperty("ente_creditore")
    private EnteCreditoreDTO enteCreditore;

    @JsonProperty("comune_competenza")
    private ComuneExtendedDTO comuneCompetenza;

    @JsonProperty("flg_adesione_adempimento")
    private Boolean flgAdesioneAdempimento;

    @JsonProperty("flg_principale")
    private Boolean flgPrincipale;

    @JsonProperty("url_oneri_previsti")
    private String urlOneriPrevisti;

    /**
     * @return tipoCompetenza
     */
    public TipoCompetenzaDTO getTipoCompetenza() {
        return tipoCompetenza;
    }

    /**
     * @param tipoCompetenza tipoCompetenza
     */
    public void setTipoCompetenza(TipoCompetenzaDTO tipoCompetenza) {
        this.tipoCompetenza = tipoCompetenza;
    }

    /**
     * @return enteCreditore
     */
    public EnteCreditoreDTO getEnteCreditore() {
        return enteCreditore;
    }

    /**
     * @param enteCreditore enteCreditore
     */
    public void setEnteCreditore(EnteCreditoreDTO enteCreditore) {
        this.enteCreditore = enteCreditore;
    }

    /**
     * @return comuneCompetenza
     */
    public ComuneExtendedDTO getComuneCompetenza() {
        return comuneCompetenza;
    }

    /**
     * @param comuneCompetenza comuneCompetenza
     */
    public void setComuneCompetenza(ComuneExtendedDTO comuneCompetenza) {
        this.comuneCompetenza = comuneCompetenza;
    }

    /**
     * @return flgAdesioneAdempimento
     */
    public Boolean getFlgAdesioneAdempimento() {
        return flgAdesioneAdempimento;
    }

    /**
     * @param flgAdesioneAdempimento flgAdesioneAdempimento
     */
    public void setFlgAdesioneAdempimento(Boolean flgAdesioneAdempimento) {
        this.flgAdesioneAdempimento = flgAdesioneAdempimento;
    }

    /**
     * @return flgPrincipale
     */
    public Boolean getFlgPrincipale() {
        return flgPrincipale;
    }

    /**
     * @param flgPrincipale flgPrincipale
     */
    public void setFlgPrincipale(Boolean flgPrincipale) {
        this.flgPrincipale = flgPrincipale;
    }

    /**
     * @return urlOneriPrevisti
     */
    public String getUrlOneriPrevisti() {
        return urlOneriPrevisti;
    }

    /**
     * @param urlOneriPrevisti urlOneriPrevisti
     */
    public void setUrlOneriPrevisti(String urlOneriPrevisti) {
        this.urlOneriPrevisti = urlOneriPrevisti;
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
        CompetenzaTerritorioExtendedDTO that = (CompetenzaTerritorioExtendedDTO) o;
        return Objects.equals(tipoCompetenza, that.tipoCompetenza) && Objects.equals(enteCreditore, that.enteCreditore) && Objects.equals(comuneCompetenza, that.comuneCompetenza) && Objects.equals(flgAdesioneAdempimento, that.flgAdesioneAdempimento) && Objects.equals(flgPrincipale, that.flgPrincipale) && Objects.equals(urlOneriPrevisti, that.urlOneriPrevisti);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tipoCompetenza, enteCreditore, comuneCompetenza, flgAdesioneAdempimento, flgPrincipale, urlOneriPrevisti);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CompetenzaTerritorioExtendedDTO {");
        sb.append(super.toString());
        sb.append("         tipoCompetenza:").append(tipoCompetenza);
        sb.append(",         enteCreditore:").append(enteCreditore);
        sb.append(",         comuneCompetenza:").append(comuneCompetenza);
        sb.append(",         flgAdesioneAdempimento:").append(flgAdesioneAdempimento);
        sb.append(",         flgPrincipale:").append(flgPrincipale);
        sb.append(",         urlOneriPrevisti:'").append(urlOneriPrevisti).append("'");
        sb.append("}");
        return sb.toString();
    }
}