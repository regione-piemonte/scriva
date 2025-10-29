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

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;



import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * The type Competenza territorio extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompetenzaTerritorioExtendedDTO extends CompetenzaTerritorioDTO implements Serializable {

    @JsonProperty("tipo_competenza")
    private TipoCompetenzaDTO tipoCompetenza;

    @JsonProperty("ente_creditore")
    private EnteCreditoreDTO enteCreditore;

    @JsonProperty("comune_competenza")
    private ComuneExtendedDTO comuneCompetenza;

    @JsonProperty("flg_principale")
    private Boolean flgPrincipale;

    @JsonProperty("url_oneri_previsti")
    private String urlOneriPrevisti;

    @JsonProperty("ind_adesione_adempimento")
    private Integer indAdesioneAdempimento;

    @JsonProperty("id_componente_gestore_processo")
    private Long idComponenteGestoreProcesso;

    @JsonIgnore
    private String numCellulareAdempi;

    @JsonIgnore
    private String desEmailAdempi;

    @JsonIgnore
    private String desPecAdempi;

    @JsonIgnore
    private String desServizioApplicativoAdempi;

    @JsonIgnore
    private String numDimensionePecAdempi;
    
    @JsonProperty("responsabili_competenza_territorio")
    private List<CompetenzaTerritorioResponsabileDTO> responsabiliCompetenzaTerritorio;


	/**
     * Gets tipo competenza.
     *
     * @return tipoCompetenza tipo competenza
     */
    public TipoCompetenzaDTO getTipoCompetenza() {
        return tipoCompetenza;
    }

    /**
     * Sets tipo competenza.
     *
     * @param tipoCompetenza tipoCompetenza
     */
    public void setTipoCompetenza(TipoCompetenzaDTO tipoCompetenza) {
        this.tipoCompetenza = tipoCompetenza;
    }

    /**
     * Gets ente creditore.
     *
     * @return enteCreditore ente creditore
     */
    public EnteCreditoreDTO getEnteCreditore() {
        return enteCreditore;
    }

    /**
     * Sets ente creditore.
     *
     * @param enteCreditore enteCreditore
     */
    public void setEnteCreditore(EnteCreditoreDTO enteCreditore) {
        this.enteCreditore = enteCreditore;
    }

    /**
     * Gets comune competenza.
     *
     * @return comuneCompetenza comune competenza
     */
    public ComuneExtendedDTO getComuneCompetenza() {
        return comuneCompetenza;
    }

    /**
     * Sets comune competenza.
     *
     * @param comuneCompetenza comuneCompetenza
     */
    public void setComuneCompetenza(ComuneExtendedDTO comuneCompetenza) {
        this.comuneCompetenza = comuneCompetenza;
    }

    /**
     * Gets flg principale.
     *
     * @return flgPrincipale flg principale
     */
    public Boolean getFlgPrincipale() {
        return flgPrincipale;
    }

    /**
     * Sets flg principale.
     *
     * @param flgPrincipale flgPrincipale
     */
    public void setFlgPrincipale(Boolean flgPrincipale) {
        this.flgPrincipale = flgPrincipale;
    }

    /**
     * Gets url oneri previsti.
     *
     * @return urlOneriPrevisti url oneri previsti
     */
    public String getUrlOneriPrevisti() {
        return urlOneriPrevisti;
    }

    /**
     * Sets url oneri previsti.
     *
     * @param urlOneriPrevisti urlOneriPrevisti
     */
    public void setUrlOneriPrevisti(String urlOneriPrevisti) {
        this.urlOneriPrevisti = urlOneriPrevisti;
    }

    /**
     * Gets ind adesione adempimento.
     *
     * @return the ind adesione adempimento
     */
    public Integer getIndAdesioneAdempimento() {
        return indAdesioneAdempimento;
    }

    /**
     * Sets ind adesione adempimento.
     *
     * @param indAdesioneAdempimento the ind adesione adempimento
     */
    public void setIndAdesioneAdempimento(Integer indAdesioneAdempimento) {
        this.indAdesioneAdempimento = indAdesioneAdempimento;
    }

    /**
     * Gets id componente gestore processo.
     *
     * @return the id componente gestore processo
     */
    public Long getIdComponenteGestoreProcesso() {
        return idComponenteGestoreProcesso;
    }

    /**
     * Sets id componente gestore processo.
     *
     * @param idComponenteGestoreProcesso the id componente gestore processo
     */
    public void setIdComponenteGestoreProcesso(Long idComponenteGestoreProcesso) {
        this.idComponenteGestoreProcesso = idComponenteGestoreProcesso;
    }

    /**
     * Gets num cellulare adempi.
     *
     * @return the num cellulare adempi
     */
    public String getNumCellulareAdempi() {
        return numCellulareAdempi;
    }

    /**
     * Sets num cellulare adempi.
     *
     * @param numCellulareAdempi the num cellulare adempi
     */
    public void setNumCellulareAdempi(String numCellulareAdempi) {
        this.numCellulareAdempi = numCellulareAdempi;
    }

    /**
     * Gets des email adempi.
     *
     * @return the des email adempi
     */
    public String getDesEmailAdempi() {
        return desEmailAdempi;
    }

    /**
     * Sets des email adempi.
     *
     * @param desEmailAdempi the des email adempi
     */
    public void setDesEmailAdempi(String desEmailAdempi) {
        this.desEmailAdempi = desEmailAdempi;
    }

    /**
     * Gets des pec adempi.
     *
     * @return the des pec adempi
     */
    public String getDesPecAdempi() {
        return desPecAdempi;
    }

    /**
     * Sets des pec adempi.
     *
     * @param desPecAdempi the des pec adempi
     */
    public void setDesPecAdempi(String desPecAdempi) {
        this.desPecAdempi = desPecAdempi;
    }

    /**
     * Gets des servizio applicativo adempi.
     *
     * @return the des servizio applicativo adempi
     */
    public String getDesServizioApplicativoAdempi() {
        return desServizioApplicativoAdempi;
    }

    /**
     * Sets des servizio applicativo adempi.
     *
     * @param desServizioApplicativoAdempi the des servizio applicativo adempi
     */
    public void setDesServizioApplicativoAdempi(String desServizioApplicativoAdempi) {
        this.desServizioApplicativoAdempi = desServizioApplicativoAdempi;
    }

    /**
     * Gets num dimensione pec adempi.
     *
     * @return the num dimensione pec adempi
     */
    public String getNumDimensionePecAdempi() {
        return numDimensionePecAdempi;
    }

    /**
     * Sets num dimensione pec adempi.
     *
     * @param numDimensionePecAdempi the num dimensione pec adempi
     */
    public void setNumDimensionePecAdempi(String numDimensionePecAdempi) {
        this.numDimensionePecAdempi = numDimensionePecAdempi;
    }
    
    /**
     * Gets list competenza territorio responsabile.
     *
     * @return list competenza territorio responsabile
     */
    public List<CompetenzaTerritorioResponsabileDTO> getResponsabiliCompetenzaTerritorio() {
		return responsabiliCompetenzaTerritorio;
	}

    /**
     * Sets list competenza territorio responsabile.
     *
     * @param list competenza territorio responsabile
     */
	public void setResponsabiliCompetenzaTerritorio(
			List<CompetenzaTerritorioResponsabileDTO> responsabiliCompetenzaTerritorio) {
		this.responsabiliCompetenzaTerritorio = responsabiliCompetenzaTerritorio;
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
        return Objects.equals(tipoCompetenza, that.tipoCompetenza) && Objects.equals(enteCreditore, that.enteCreditore) && Objects.equals(comuneCompetenza, that.comuneCompetenza) && Objects.equals(flgPrincipale, that.flgPrincipale) && Objects.equals(urlOneriPrevisti, that.urlOneriPrevisti) && Objects.equals(indAdesioneAdempimento, that.indAdesioneAdempimento) && Objects.equals(idComponenteGestoreProcesso, that.idComponenteGestoreProcesso) && Objects.equals(numCellulareAdempi, that.numCellulareAdempi) && Objects.equals(desEmailAdempi, that.desEmailAdempi) && Objects.equals(desPecAdempi, that.desPecAdempi) && Objects.equals(desServizioApplicativoAdempi, that.desServizioApplicativoAdempi) && Objects.equals(numDimensionePecAdempi, that.numDimensionePecAdempi) && Objects.equals(responsabiliCompetenzaTerritorio, that.responsabiliCompetenzaTerritorio);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tipoCompetenza, enteCreditore, comuneCompetenza, flgPrincipale, urlOneriPrevisti, indAdesioneAdempimento, idComponenteGestoreProcesso, numCellulareAdempi, desEmailAdempi, desPecAdempi, desServizioApplicativoAdempi, numDimensionePecAdempi, responsabiliCompetenzaTerritorio);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "CompetenzaTerritorioExtendedDTO {\n" +
                "         gestDataIns:" + gestDataIns +
                ",\n         gestAttoreIns:'" + gestAttoreIns + "'" +
                ",\n         gestDataUpd:" + gestDataUpd +
                ",\n         gestAttoreUpd:'" + gestAttoreUpd + "'" +
                ",\n         gestUID:'" + gestUID + "'" +
                ",\n         tipoCompetenza:" + tipoCompetenza +
                ",\n         enteCreditore:" + enteCreditore +
                ",\n         comuneCompetenza:" + comuneCompetenza +
                ",\n         flgPrincipale:" + flgPrincipale +
                ",\n         urlOneriPrevisti:'" + urlOneriPrevisti + "'" +
                ",\n         indAdesioneAdempimento:" + indAdesioneAdempimento +
                ",\n         idComponenteGestoreProcesso:" + idComponenteGestoreProcesso +
                ",\n         numCellulareAdempi:'" + numCellulareAdempi + "'" +
                ",\n         desEmailAdempi:'" + desEmailAdempi + "'" +
                ",\n         desPecAdempi:'" + desPecAdempi + "'" +
                ",\n         desServizioApplicativoAdempi:'" + desServizioApplicativoAdempi + "'" +
                ",\n         numDimensionePecAdempi:'" + numDimensionePecAdempi + "'" +
                ",\n         competenzaTerritorioResponsabile:'" + responsabiliCompetenzaTerritorio + "'" +
                "}\n";
    }
}