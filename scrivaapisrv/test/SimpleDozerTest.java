/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.AdempimentoExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.AmbitoDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.EsitoProcedimentoDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.TipoAdempimentoExtendedDTO;
import it.csi.scriva.scrivaapisrv.dto.PubIstanzaDTO;
import it.csi.scriva.scrivaapisrv.dto.StatoIstanzaDTO;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;

import java.sql.Timestamp;

public class SimpleDozerTest {

    public static void main(String[] args) {

        IstanzaExtendedDTO istanza = new IstanzaExtendedDTO();

        StatoIstanzaDTO statoIstanza = new StatoIstanzaDTO();
        statoIstanza.setCodiceStatoIstanza("codStatoIstanza");
        statoIstanza.setDescrizioneStatoIstanza("desStatoIstanza");
        statoIstanza.setLabelStato("labelStato");
        istanza.setStatoIstanza(statoIstanza);

        AdempimentoExtendedDTO adempimento = new AdempimentoExtendedDTO();
        adempimento.setIdAdempimento(0L);
        adempimento.setCodAdempimento("setCodAdempimento");
        adempimento.setDesAdempimento("setDesAdempimento");
        adempimento.setDesEstesaAdempimento("setDesEstesaAdempimento");
        adempimento.setOrdinamentoAdempimento(0);
        adempimento.setFlgAttivo(false);
        TipoAdempimentoExtendedDTO tipoAdempimento = new TipoAdempimentoExtendedDTO();
        tipoAdempimento.setAmbito(new AmbitoDTO());
        tipoAdempimento.setPreferito(0L);
        tipoAdempimento.setDesEstesaTipoAdempimento("setDesEstesaTipoAdempimento");
        tipoAdempimento.setIdTipoAdempimento(0L);
        tipoAdempimento.setIdAmbito(0L);
        tipoAdempimento.setDesTipoAdempimento("setDesTipoAdempimento");
        tipoAdempimento.setCodTipoAdempimento("setCodTipoAdempimento");
        tipoAdempimento.setOrdinamentoTipoAdempimento(0);
        tipoAdempimento.setFlgAttivo(false);
        tipoAdempimento.setUuidIndex("setUuidIndex");
        adempimento.setTipoAdempimento(tipoAdempimento);
        istanza.setAdempimento(adempimento);

        EsitoProcedimentoDTO esitoProcedimento = new EsitoProcedimentoDTO();
        esitoProcedimento.setIdEsitoProcedimento(0L);
        esitoProcedimento.setCodEsitoProcedimento("setCodEsitoProcedimento");
        esitoProcedimento.setDesEsitoProcedimento("setDesEsitoProcedimento");
        esitoProcedimento.setFlgPositivo(false);

        istanza.setEsitoProcedimento(esitoProcedimento);

        istanza.setIdIstanza(0L);
        istanza.setIdStatoIstanza(0L);
        istanza.setIdAdempimento(0L);
        istanza.setCodIstanza("setCodIstanza");
        istanza.setDataInserimentoIstanza(new Timestamp(new java.util.Date().getTime()));
        istanza.setDataModificaIstanza(new Timestamp(new java.util.Date().getTime()));
        istanza.setJsonData("setJsonData");
        istanza.setUuidIndex("setUuidIndex");
        istanza.setCodPratica("setCodPratica");
        istanza.setIdTemplate(0L);
        istanza.setIdIstanzaAttoreOwner(0L);
        istanza.setDataInserimentoPratica(new Timestamp(new java.util.Date().getTime()));
        istanza.setDataModificaPratica(new Timestamp(new java.util.Date().getTime()));
        istanza.setDesStatoSintesiPagamento("setDesStatoSintesiPagamento");
        istanza.setIdIstanzaAttore(0L);
        istanza.setIdFunzionario(0L);
        istanza.setDataPubblicazione(new Timestamp(new java.util.Date().getTime()));
        istanza.setNumProtocolloIstanza("setNumProtocolloIstanza");
        istanza.setDataProtocolloIstanza(new Timestamp(new java.util.Date().getTime()));
        istanza.setDataInizioOsservazioni(new Timestamp(new java.util.Date().getTime()));
        istanza.setDataFineOsservazioni(new Timestamp(new java.util.Date().getTime()));
        istanza.setDataConclusioneProcedimento(new Timestamp(new java.util.Date().getTime()));
        istanza.setIdEsitoProcedimento(0L);
        istanza.setDataScadenzaProcedimento(new Timestamp(new java.util.Date().getTime()));
        istanza.setAnnoRegistro(0);
        istanza.setDesEsitoProcedimentoStatale("setDesEsitoProcedimentoStatale");
        istanza.setDesStatoProcedimentoStatale("setDesStatoProcedimentoStatale");
        istanza.setGestDataIns(new Timestamp(new java.util.Date().getTime()));
        istanza.setGestAttoreIns("setGestAttoreIns");
        istanza.setGestDataUpd(new Timestamp(new java.util.Date().getTime()));
        istanza.setGestAttoreUpd("setGestAttoreUpd");
        istanza.setGestUID("setGestUID");

        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.addMapping(beanMappingBuilder(IstanzaExtendedDTO.class, PubIstanzaDTO.class));

        PubIstanzaDTO pubIstanza = new DozerBeanMapper().map(istanza, PubIstanzaDTO.class);
        System.out.println("------------------------------- pubIstanza -------------------------------");
        System.out.println(pubIstanza);

        mapper.addMapping(beanMappingBuilder(PubIstanzaDTO.class, IstanzaExtendedDTO.class));
        IstanzaExtendedDTO nuovaIstanza = new DozerBeanMapper().map(pubIstanza, IstanzaExtendedDTO.class);
        System.out.println("------------------------------- nuovaIstanza -------------------------------");
        System.out.println(nuovaIstanza);
    }

    private static BeanMappingBuilder beanMappingBuilder(Class<?> typeA, Class<?> typeB) {
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(typeA, typeB, TypeMappingOptions.mapNull(false), TypeMappingOptions.mapEmptyString(false));
            }
        };
    }
}