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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.sql.Timestamp;


/**
 * The type Istanza extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IstanzaExtendedDTO extends IstanzaDTO implements Serializable {

    @JsonProperty("stato_istanza")
    private StatoIstanzaDTO statoIstanza;

    @JsonProperty("adempimento")
    private AdempimentoExtendedDTO adempimento;

    @JsonProperty("esito_procedimento")
    private EsitoProcedimentoDTO esitoProcedimento;

    @JsonProperty("esito_procedimento_statale")
    private EsitoProcedimentoDTO esitoProcedimentoStatale;

    @JsonProperty("stato_procedimento_statale")
    private StatoProcedimentoStataleDTO statoProcedimentoStatale;

    @JsonProperty("responsabili_istanza")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<IstanzaResponsabileExtendedDTO> istanzaResponsabile;
    
	@JsonProperty("tipi_adempimento_ogg_app")
    private List<TipoAdempimentoOggettoAppExtendedDTO> tipiAdempimentoOggettoApp;

    @JsonProperty("profili_app")
    private List<ProfiloOggettoAppExtendedDTO> profiloOggettoAppList;
    
    @JsonProperty("data_invio_istanza")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataInvioIstanza;


    /**
     * Gets stato istanza.
     *
     * @return statoIstanza stato istanza
     */
    public StatoIstanzaDTO getStatoIstanza() {
        return statoIstanza;
    }

    /**
     * Sets stato istanza.
     *
     * @param statoIstanza statoIstanza
     */
    public void setStatoIstanza(StatoIstanzaDTO statoIstanza) {
        this.statoIstanza = statoIstanza;
    }

    /**
     * Gets adempimento.
     *
     * @return adempimento adempimento
     */
    public AdempimentoExtendedDTO getAdempimento() {
        return adempimento;
    }

    /**
     * Sets adempimento.
     *
     * @param adempimento adempimento
     */
    public void setAdempimento(AdempimentoExtendedDTO adempimento) {
        this.adempimento = adempimento;
    }

    /**
     * Gets esito procedimento.
     *
     * @return the esito procedimento
     */
    public EsitoProcedimentoDTO getEsitoProcedimento() {
        return esitoProcedimento;
    }

    /**
     * Sets esito procedimento.
     *
     * @param esitoProcedimento the esito procedimento
     */
    public void setEsitoProcedimento(EsitoProcedimentoDTO esitoProcedimento) {
        this.esitoProcedimento = esitoProcedimento;
    }

    /**
     * Gets esito procedimento statale.
     *
     * @return the esito procedimento statale
     */
    public EsitoProcedimentoDTO getEsitoProcedimentoStatale() {
        return esitoProcedimentoStatale;
    }

    /**
     * Sets esito procedimento statale.
     *
     * @param esitoProcedimentoStatale the esito procedimento statale
     */
    public void setEsitoProcedimentoStatale(EsitoProcedimentoDTO esitoProcedimentoStatale) {
        this.esitoProcedimentoStatale = esitoProcedimentoStatale;
    }

    /**
     * Gets stato procedimento statale.
     *
     * @return the stato procedimento statale
     */
    public StatoProcedimentoStataleDTO getStatoProcedimentoStatale() {
        return statoProcedimentoStatale;
    }

    /**
     * Sets stato procedimento statale.
     *
     * @param statoProcedimentoStatale the stato procedimento statale
     */
    public void setStatoProcedimentoStatale(StatoProcedimentoStataleDTO statoProcedimentoStatale) {
        this.statoProcedimentoStatale = statoProcedimentoStatale;
    }

    /**
     * Gets istanza responsabile.
     *
     * @return the istanza responsabile list
     */
    public List<IstanzaResponsabileExtendedDTO> getIstanzaResponsabile() {
  		return istanzaResponsabile;
  	}

    /**
     * Sets istanza responsabile.
     *
     * @param istanzaResponsabile the istanza responsabile
     */
    public void setIstanzaResponsabile(ArrayList<IstanzaResponsabileExtendedDTO> istanzaResponsabile) {
  		this.istanzaResponsabile = istanzaResponsabile;
  	}
    
    /**
	 * @return the tipiAdempimentoOggettoApp
	 */
	public List<TipoAdempimentoOggettoAppExtendedDTO> getTipiAdempimentoOggettoApp() {
		return tipiAdempimentoOggettoApp;
	}

	/**
	 * @param tipiAdempimentoOggettoApp the tipiAdempimentoOggettoApp to set
	 */
	public void setTipiAdempimentoOggettoApp(List<TipoAdempimentoOggettoAppExtendedDTO> tipiAdempimentoOggettoApp) {
		this.tipiAdempimentoOggettoApp = tipiAdempimentoOggettoApp;
	}

	/**
	 * @return the profiloOggettoAppList
	 */
	public List<ProfiloOggettoAppExtendedDTO> getProfiloOggettoAppList() {
		return profiloOggettoAppList;
	}

	/**
	 * @param profiloOggettoAppList the profiloOggettoAppList to set
	 */
	public void setProfiloOggettoAppList(List<ProfiloOggettoAppExtendedDTO> profiloOggettoAppList) {
		this.profiloOggettoAppList = profiloOggettoAppList;
	}
	
    /**
     * Gets data invio istanza.
     *
     * @return the data invio istanza
     */
    public Timestamp getDataInvioIstanza() {
		return dataInvioIstanza;
	}

    /**
     * Sets data invio istanza.
     *
     * @param dataInvioIstanza the data invio istanza
     */
	public void setDataInvioIstanza(Timestamp dataInvioIstanza) {
		this.dataInvioIstanza = dataInvioIstanza;
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
        IstanzaExtendedDTO that = (IstanzaExtendedDTO) o;
        return Objects.equals(statoIstanza, that.statoIstanza) && Objects.equals(adempimento, that.adempimento) && Objects.equals(esitoProcedimento, that.esitoProcedimento) && Objects.equals(esitoProcedimentoStatale, that.esitoProcedimentoStatale) && Objects.equals(statoProcedimentoStatale, that.statoProcedimentoStatale) && Objects.equals(istanzaResponsabile, that.istanzaResponsabile)  && Objects.equals(tipiAdempimentoOggettoApp, that.tipiAdempimentoOggettoApp)  && Objects.equals(profiloOggettoAppList, that.profiloOggettoAppList) && Objects.equals(dataInvioIstanza, that.dataInvioIstanza);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), statoIstanza, adempimento, esitoProcedimento, esitoProcedimentoStatale, statoProcedimentoStatale, istanzaResponsabile, tipiAdempimentoOggettoApp, profiloOggettoAppList, dataInvioIstanza);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "IstanzaExtendedDTO {\n" +
                "         statoIstanza:" + statoIstanza +
                ",\n         adempimento:" + adempimento +
                ",\n         esitoProcedimento:" + esitoProcedimento +
                ",\n         esitoProcedimentoStatale:" + esitoProcedimentoStatale +
                ",\n         statoProcedimentoStatale:" + statoProcedimentoStatale +
                ",\n         istanzaResponsabile:" + istanzaResponsabile +
                ",\n         profiloOggettoAppList:" + profiloOggettoAppList +
                ",\n         tipiAdempimentoOggettoApp:" + tipiAdempimentoOggettoApp +
                ",\n         dataInvioIstanza:" + dataInvioIstanza +
                super.toString() +
                "}\n";
    }

    /**
     * Gets dto.
     *
     * @return IstanzaDTO dto
     */
    @JsonIgnore
    public IstanzaDTO getDTO() {
        IstanzaDTO dto = new IstanzaDTO();
        dto.setIdIstanza(this.getIdIstanza());
        if (null != this.getStatoIstanza()) {
            dto.setIdStatoIstanza(this.getStatoIstanza().getIdStatoIstanza());
        }
        if (null != this.getAdempimento()) {
            dto.setIdAdempimento(this.getAdempimento().getIdAdempimento());
        }
        if (null != this.getEsitoProcedimento()) {
            dto.setIdEsitoProcedimento(this.getEsitoProcedimento().getIdEsitoProcedimento());
        }
        if (null != this.getEsitoProcedimentoStatale()) {
            dto.setIdEsitoProcedStatale(this.getEsitoProcedimentoStatale().getIdEsitoProcedimento());
        }
        if (null != this.getStatoProcedimentoStatale()) {
            dto.setIdStatoProcedStatale(this.getStatoProcedimentoStatale().getIdStatoProcedStatale());
        }
        dto.setCodIstanza(this.getCodIstanza());
        dto.setDataInserimentoIstanza(this.getDataInserimentoIstanza());
        dto.setDataModificaIstanza(this.getDataModificaIstanza());
        dto.setJsonData(this.getJsonData());
        dto.setGestAttoreIns(this.getGestAttoreIns());
        dto.setGestAttoreUpd(this.getGestAttoreUpd());
        dto.setGestUID(this.getGestUID());
        dto.setUuidIndex(this.getUuidIndex());
        dto.setCodPratica(this.getCodPratica());
        dto.setIdTemplate(this.getIdTemplate());
        dto.setIdIstanzaAttoreOwner(this.getIdIstanzaAttoreOwner());
        dto.setDataInserimentoPratica(this.getDataInserimentoPratica());
        dto.setDataModificaPratica(this.getDataModificaPratica());
        dto.setDesStatoSintesiPagamento(this.getDesStatoSintesiPagamento());
        dto.setIdIstanzaAttore(this.getIdIstanzaAttore());
        dto.setIdFunzionario(this.getIdFunzionario());
        dto.setDataPubblicazione(this.getDataPubblicazione());
        dto.setNumProtocolloIstanza(this.getNumProtocolloIstanza());
        dto.setDataProtocolloIstanza(this.getDataProtocolloIstanza());
        dto.setDataInizioOsservazioni(this.getDataInizioOsservazioni());
        dto.setDataFineOsservazioni(this.getDataFineOsservazioni());
        dto.setDataConclusioneProcedimento(this.getDataConclusioneProcedimento());
        dto.setDataScadenzaProcedimento(this.getDataScadenzaProcedimento());
        dto.setAnnoRegistro(this.getAnnoRegistro());
        return dto;
    }

    /**
     * To json string string.
     *
     * @return string string
     */
    @JsonIgnore
    public String toJsonString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(df);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}