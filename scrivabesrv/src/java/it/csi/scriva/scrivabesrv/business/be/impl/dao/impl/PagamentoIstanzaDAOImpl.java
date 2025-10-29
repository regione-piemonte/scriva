/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.impl.dao.impl;

import it.csi.scriva.scrivabesrv.business.be.impl.dao.PagamentoIstanzaDAO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoTipoPagamentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AllegatoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;
import it.csi.scriva.scrivabesrv.dto.CompetenzaTerritorioDTO;
import it.csi.scriva.scrivabesrv.dto.EnteCreditoreDTO;
import it.csi.scriva.scrivabesrv.dto.GruppoPagamentoDTO;
import it.csi.scriva.scrivabesrv.dto.PagamentoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.PagamentoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.RiscossioneEnteExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.StatoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.StatoPagamentoDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoPagamentoExtendedDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * The type Pagamento istanza dao.
 */
public class PagamentoIstanzaDAOImpl extends ScrivaBeSrvGenericDAO<PagamentoIstanzaDTO> implements PagamentoIstanzaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String ORDER_BY_ORDINAMENTO_RISCOSSIONE_ENTE = "ORDER BY COALESCE(NULLIF(srre.ordinamento_riscossione_ente,0) , 0) ASC ";

    private static final String WHERE_COMPONENTE_APP = "AND stct.ind_visibile LIKE '%'||:componenteApp||'%' \n";

    private static final String WHERE_ID_ISTANZA = "AND sti.id_istanza = :idIstanza \n";

    private static final String WHERE_ID = "AND srpi.id_pagamento_istanza = :idPagamentoIstanza \n";

    private static final String WHERE_ID_ONERE_PADRE = " srpi.id_onere_padre = :idPagamentoIstanza \n";

    private static final String QUERY_PAGAMENTO_ISTANZA = "SELECT * FROM _replaceTableName_ srpi WHERE 1=1 \n";

    private static final String QUERY_PAGAMENTO_ISTANZA_PK = QUERY_PAGAMENTO_ISTANZA + WHERE_ID;

    private static final String QUERY_PAGAMENTO_ISTANZA_BY_ID_ISTANZA_ENTE_GRUPPI_PAGAMENTO = "SELECT srpi.* \n" +
            "FROM scriva_r_pagamento_istanza srpi \n" +
            "INNER JOIN scriva_t_istanza sti ON sti.id_istanza = srpi.id_istanza \n" +
            "INNER JOIN scriva_r_riscossione_ente srre ON srre.id_riscossione_ente = srpi.id_riscossione_ente \n" +
            "INNER JOIN scriva_d_stato_pagamento sdsp ON sdsp.id_stato_pagamento = srpi.id_stato_pagamento \n" +
            "INNER JOIN scriva_r_istanza_competenza sric ON sric.id_istanza = sti.id_istanza \n" +
            "INNER JOIN scriva_r_adempi_competenza srac ON srac.id_competenza_territorio = sric.id_competenza_territorio \n" +
            "INNER JOIN scriva_t_competenza_territorio stct ON stct.id_competenza_territorio = sric.id_competenza_territorio \n" +
            "INNER JOIN scriva_d_ente_creditore sdec ON sdec.id_ente_creditore = stct.id_ente_creditore \n" +
            "INNER JOIN scriva_d_gruppo_pagamento sdgp ON sdgp.id_gruppo_pagamento = srre.id_gruppo_pagamento \n" +
            "WHERE 1 = 1 \n" +
            "AND srac.id_adempimento = sti.id_adempimento \n" +
            "AND (stct.data_fine_validita IS NULL OR stct.data_fine_validita < now()) \n" +
            "AND sdec.flg_attivo = 1 \n" +
            "AND srre.flg_attivo = 1 \n" +
            //"AND sdatp.flg_attivo = 1 \n" +
            "AND srac.ind_adesione_adempimento != 0 \n" +
            "AND srpi.id_istanza = :idIstanza \n" +
            "AND sdec.id_ente_creditore = :idEnteCreditore \n" +
            "AND sdgp.cod_gruppo_pagamento IN (:codeGruppiPagamento) \n" +
            "AND srpi.id_stato_pagamento = 1 \n";

//    private static final String QUERY_PAGAMENTI_ISTANZA = "SELECT srpi.* \n" +
//            ", srre.*,  srre.id_riscossione_ente AS riscossione_ente_id \n" +
//            ", srrsi.flg_attiva_pagamento \n" +
//            ", sdsp.*, sdsp.id_stato_pagamento AS stato_pagamento_id \n" +
//            ", sdatp.*, sdatp.id_adempi_tipo_pagamento AS adempi_tipo_pagamento_id \n" +
//            ", sdec.*, sdec.id_ente_creditore AS ente_creditore_id \n" +
//            ", sdtp.*, sdtp.id_tipo_pagamento AS tipo_pagamento_id \n" +
//            ", sdgp.*, sdgp.id_gruppo_pagamento AS gruppo_pagamento_id \n" +
//            ", sdsi_blocco.*, sdsi_blocco.id_stato_istanza AS stato_istanza_id \n" +
//            ", sdad.*, sdad.id_adempimento AS adempimento_id \n" +
//            ", sdta.*, sdta.id_tipo_adempimento AS tipo_adempimento_id \n" +
//            ", sda.*, sda.id_ambito AS ambito_id \n" +
//            ", srac.url_oneri_previsti \n" +
//            ", stai.*, stai.id_allegato_istanza AS allegato_istanza_id, stai.uuid_index AS uuid_index_ai \n" +
//            "FROM _replaceTableName_ srpi \n" +
//            "INNER JOIN scriva_t_istanza sti ON sti.id_istanza = srpi.id_istanza \n" +
//            "INNER JOIN scriva_r_riscossione_ente srre ON srre.id_riscossione_ente = srpi.id_riscossione_ente \n" +
//            "INNER JOIN scriva_d_stato_pagamento sdsp ON sdsp.id_stato_pagamento = srpi.id_stato_pagamento \n" +
//            "INNER JOIN scriva_r_istanza_competenza sric ON sric.id_istanza = sti.id_istanza \n" +
//            "INNER JOIN scriva_r_adempi_competenza srac ON srac.id_competenza_territorio = sric.id_competenza_territorio \n" +
//            "INNER JOIN scriva_t_competenza_territorio stct ON stct.id_competenza_territorio = sric.id_competenza_territorio \n" +
//            "INNER JOIN scriva_d_adempi_tipo_pagamento sdatp ON sdatp.id_adempi_tipo_pagamento = srre.id_adempi_tipo_pagamento \n" +
//            "INNER JOIN scriva_d_ente_creditore sdec ON sdec.id_ente_creditore = sdatp.id_ente_creditore \n" +
//            "INNER JOIN scriva_r_risco_stato_istanza srrsi ON srrsi.id_riscossione_ente = srre.id_riscossione_ente \n" +
//            "LEFT JOIN scriva_d_stato_istanza sdsi_blocco ON sdsi_blocco.id_stato_istanza = srre.id_stato_istanza_blocco \n" +
//            "INNER JOIN scriva_d_tipo_pagamento sdtp ON sdtp.id_tipo_pagamento = sdatp.id_tipo_pagamento \n" +
//            "INNER JOIN scriva_d_gruppo_pagamento sdgp ON sdgp.id_gruppo_pagamento = sdtp.id_gruppo_pagamento \n" +
//            "INNER JOIN scriva_d_adempimento sdad ON sdad.id_adempimento = sti.id_adempimento \n" +
//            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdta.id_tipo_adempimento = sdad.id_tipo_adempimento \n" +
//            "INNER JOIN scriva_d_ambito sda ON sda.id_ambito = sdta.id_ambito \n" +
//            "LEFT JOIN scriva_t_allegato_istanza stai ON stai.id_allegato_istanza = srpi.id_allegato_istanza \n" +
//            "WHERE 1 = 1 \n" +
//            "AND srac.id_adempimento = sti.id_adempimento \n" +
//            "AND srrsi.id_stato_istanza = sti.id_stato_istanza \n" +
//            "AND (stct.data_fine_validita IS NULL OR stct.data_fine_validita < now()) \n" +
//            "AND sdec.flg_attivo = 1 \n" +
//            "AND srre.flg_attivo = 1 \n" +
//            "AND sdatp.flg_attivo = 1 \n" +
//            "AND srac.flg_adesione_adempimento = 1 \n" +
//            "AND sric.flg_autorita_principale = 1 \n"; // SCRIVA-367:  mostrare i soli pagamenti afferenti all'autorità principale

    private static final String QUERY_PAGAMENTI_ISTANZA = "SELECT srpi.* \n" +
            ", srre.*,  srre.id_riscossione_ente AS riscossione_ente_id \n" +
            ", srrsi.flg_attiva_pagamento \n" +
            ", sdsp.*, sdsp.id_stato_pagamento AS stato_pagamento_id \n" +
            ", sdatp.*, sdatp.id_adempi_tipo_pagamento AS adempi_tipo_pagamento_id \n" +
            ", sdec.*, sdec.id_ente_creditore AS ente_creditore_id \n" +
            ", sdtp.*, sdtp.id_tipo_pagamento AS tipo_pagamento_id \n" +
            ", sdgp.*, sdgp.id_gruppo_pagamento AS gruppo_pagamento_id \n" +
            ", sdsi_blocco.*, sdsi_blocco.id_stato_istanza AS stato_istanza_id \n" +
            ", sdad.*, sdad.id_adempimento AS adempimento_id \n" +
            ", sdta.*, sdta.id_tipo_adempimento AS tipo_adempimento_id \n" +
            ", sda.*, sda.id_ambito AS ambito_id \n" +
            ", srac.url_oneri_previsti \n" +
            ", stai.*, stai.id_allegato_istanza AS allegato_istanza_id, stai.uuid_index AS uuid_index_ai \n" +
            ", stct.id_competenza_territorio, stct.cod_competenza_territorio, stct.des_competenza_territorio, stct.des_competenza_territorio_estesa \n" +
            "FROM scriva_r_pagamento_istanza srpi \n" +
            "INNER JOIN scriva_t_istanza sti ON sti.id_istanza = srpi.id_istanza\n" +
            "INNER JOIN scriva_d_stato_pagamento sdsp ON sdsp.id_stato_pagamento = srpi.id_stato_pagamento\n" +
            //"INNER JOIN scriva_r_stato_istanza_adempi srsia ON srsia.id_adempimento = sti.id_adempimento AND srsia.id_stato_istanza = sti.id_stato_istanza\n" +
            "INNER JOIN scriva_r_istanza_competenza sric ON sric.id_istanza = sti.id_istanza AND sric.flg_autorita_principale = 1 AND sric.data_fine_validita IS NULL\n" +
            "INNER JOIN scriva_r_adempi_competenza srac ON srac.id_competenza_territorio = sric.id_competenza_territorio AND srac.id_adempimento = sti.id_adempimento AND srac.ind_adesione_adempimento != 0\n" +
            "INNER JOIN scriva_t_competenza_territorio stct ON stct.id_competenza_territorio = srac.id_competenza_territorio AND (stct.data_fine_validita IS NULL OR stct.data_fine_validita > now()) AND stct.ind_visibile LIKE '%FO%'\n" +
            "INNER JOIN scriva_d_ente_creditore sdec ON sdec.id_ente_creditore = stct.id_ente_creditore AND sdec.flg_attivo = 1\n" +
            "INNER JOIN scriva_r_riscossione_ente srre ON srre.id_riscossione_ente = srpi.id_riscossione_ente\n" +
            "INNER JOIN scriva_d_adempi_tipo_pagamento sdatp ON sdatp.id_competenza_territorio = sric.id_competenza_territorio AND sdatp.id_adempimento = sti.id_adempimento AND sdatp.id_adempi_tipo_pagamento = srre.id_adempi_tipo_pagamento \n" +
            "INNER JOIN scriva_r_risco_stato_istanza srrsi ON srrsi.id_riscossione_ente = srre.id_riscossione_ente AND srrsi.id_stato_istanza = sti.id_stato_istanza\n" +
            "INNER JOIN scriva_d_tipo_pagamento sdtp ON sdtp.id_tipo_pagamento = sdatp.id_tipo_pagamento\n" +
            "INNER JOIN scriva_d_gruppo_pagamento sdgp ON sdgp.id_gruppo_pagamento = sdtp.id_gruppo_pagamento \n" +
            "LEFT JOIN scriva_d_stato_istanza sdsi_blocco ON sdsi_blocco.id_stato_istanza = srre.id_stato_istanza_blocco \n" +
            "INNER JOIN scriva_d_adempimento sdad ON sdad.id_adempimento = sti.id_adempimento \n" +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdta.id_tipo_adempimento = sdad.id_tipo_adempimento \n" +
            "INNER JOIN scriva_d_ambito sda ON sda.id_ambito = sdta.id_ambito \n" +
            "LEFT JOIN scriva_t_allegato_istanza stai ON stai.id_allegato_istanza = srpi.id_allegato_istanza\n" +
            "WHERE 1 = 1\n";

    private static final String QUERY_PAGAMENTO_ISTANZA_BY_ID_ISTANZA_STATI_PAGAMENTO = "SELECT srpi.* \n" +
            "FROM scriva_r_pagamento_istanza srpi\n" +
            "INNER JOIN scriva_d_stato_pagamento sdsp ON sdsp.id_stato_pagamento = srpi.id_stato_pagamento \n" +
            "WHERE srpi.id_istanza = :idIstanza";

    private static final String WHERE_CODE_STATO_PAGAMENTO_IN = "AND sdsp.cod_stato_pagamento IN (:codeStatiPagamentoList)\n";

    private static final String WHERE_CODE_STATO_PAGAMENTO_NOT_IN = "AND sdsp.cod_stato_pagamento NOT IN (:codeStatiPagamentoList)\n";

    private static final String QUERY_PAGAMENTI_ISTANZA_BY_ID = QUERY_PAGAMENTI_ISTANZA + WHERE_ID;

    private static final String QUERY_PAGAMENTI_ISTANZA_HIERARCHY_BY_ID = QUERY_PAGAMENTI_ISTANZA +
            "AND (srpi.id_pagamento_istanza = :idPagamentoIstanza OR srpi.id_onere_padre = :idPagamentoIstanza) \n" +
            "AND srpi.flg_non_dovuto = 0 \n";

    private static final String QUERY_PAGAMENTI_ISTANZA_BY_ID_ISTANZA = QUERY_PAGAMENTI_ISTANZA + WHERE_COMPONENTE_APP + WHERE_ID_ISTANZA;

    private static final String QUERY_INSERT_PAGAMENTO_ISTANZA = "INSERT INTO _replaceTableName_" +
            "(id_pagamento_istanza, id_istanza, id_riscossione_ente, id_stato_pagamento, id_onere_padre, data_inserimento_pagamento, importo_dovuto, iuv, data_effettivo_pagamento, importo_pagato, iubd, flg_non_dovuto, rt_xml, id_allegato_istanza, ind_tipo_inserimento, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)" +
            "VALUES(nextval('seq_scriva_r_pagamento_istanza'), :idIstanza, :idRiscossioneEnte, :idStatoPagamento, :idOnerePadre, :dataInserimentoPagamento, :importoDovuto, :iuv, :dataEffettivoPagamento, :importoPagato, :iubd, :flgNonDovuto, :rtXml, :idAllegatoIstanza, :indTipoInserimento, :gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_UPDATE_PAGAMENTO_ISTANZA = "UPDATE _replaceTableName_ \n" +
            "SET id_istanza=:idIstanza, id_riscossione_ente=:idRiscossioneEnte, id_stato_pagamento=:idStatoPagamento, id_onere_padre=:idOnerePadre, \n" +
            "data_inserimento_pagamento=:dataInserimentoPagamento, importo_dovuto=:importoDovuto, iuv=:iuv, data_effettivo_pagamento=:dataEffettivoPagamento, \n" +
            "importo_pagato=:importoPagato, iubd=:iubd, rt_xml=xmlcomment(:rtXml), id_allegato_istanza=:idAllegatoIstanza, ind_tipo_inserimento=:indTipoInserimento, \n" +
            "gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd, flg_non_dovuto=:flgNonDovuto \n" +
            "WHERE id_pagamento_istanza=:idPagamentoIstanza";

    private static final String QUERY_DELETE_PAGAMENTO_ISTANZA = "DELETE FROM _replaceTableName_ WHERE gest_uid=:gestUid";
    
    private static final String WHERE_DATA_INIZIO_FINE  =  " AND (DATE(srac.data_inizio_validita) <= DATE(NOW()) AND (srac.data_fine_validita IS NULL OR DATE(srac.data_fine_validita)>= DATE(NOW())))\n " +
    		"AND (DATE(sdatp.data_inizio_validita) <= DATE(NOW()) AND (sdatp.data_fine_validita IS NULL OR DATE(sdatp.data_fine_validita)>= DATE(NOW())))\\n ";


    /**
     * Load pagamenti istanza list.
     *
     * @return the list
     */
    @Override
    public List<PagamentoIstanzaExtendedDTO> loadPagamentiIstanza() {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        return findListByQuery(className, QUERY_PAGAMENTI_ISTANZA + WHERE_DATA_INIZIO_FINE, map);
    }

    /**
     * Load pagamento istanza by id list.
     *
     * @param idPagamentoIstanza the id pagamento istanza
     * @return the list
     */
    @Override
    public List<PagamentoIstanzaExtendedDTO> loadPagamentoIstanzaById(Long idPagamentoIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idPagamentoIstanza", idPagamentoIstanza);
        return findListByQuery(className, QUERY_PAGAMENTI_ISTANZA_BY_ID, map);
    }

    /**
     * Find by pk pagamento istanza dto.
     *
     * @param idPagamentoIstanza the id pagamento istanza
     * @return the pagamento istanza dto
     */
    @Override
    public PagamentoIstanzaDTO findByPK(Long idPagamentoIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idPagamentoIstanza", idPagamentoIstanza);
        try {
            return this.findByPK(map);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Load pagamento istanza h hierarchy by id list.
     *
     * @param idPagamentoIstanza the id pagamento istanza
     * @return the list
     */
    @Override
    public List<PagamentoIstanzaExtendedDTO> loadPagamentoIstanzaHierarchyById(Long idPagamentoIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idPagamentoIstanza", idPagamentoIstanza);
        return findListByQuery(className, QUERY_PAGAMENTI_ISTANZA_HIERARCHY_BY_ID, map);
    }

    /**
     * Load pagamento istanza id istanza list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    @Override
    public List<PagamentoIstanzaExtendedDTO> loadPagamentoIstanzaByIdIstanza(Long idIstanza, String componenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        map.put("componenteApp", componenteApp);
        String query = QUERY_PAGAMENTI_ISTANZA + WHERE_ID_ISTANZA;
        if (StringUtils.isNotBlank(componenteApp)){
            query += WHERE_COMPONENTE_APP;
        }
        return findListByQuery(className, query, map);
    }

    /**
     * Load pagamento istanza by id istanza ente cred cod versamento list.
     *
     * @param idIstanza           the id istanza
     * @param idEnteCreditore     the id ente creditore
     * @param codeGruppiPagamento the code gruppi pagamento
     * @return the list
     */
    @Override
    public List<PagamentoIstanzaDTO> loadPagamentoIstanzaByIdIstanzaEnteCredCodGruppoPagamento(Long idIstanza, Long idEnteCreditore, List<String> codeGruppiPagamento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        map.put("idEnteCreditore", idEnteCreditore);
        map.put("codeGruppiPagamento", codeGruppiPagamento);
        return findSimpleDTOListByQuery(className, QUERY_PAGAMENTO_ISTANZA_BY_ID_ISTANZA_ENTE_GRUPPI_PAGAMENTO + ORDER_BY_ORDINAMENTO_RISCOSSIONE_ENTE, map);
    }

    /**
     * Load pagamento istanza by id istanza stato pagamento list.
     *
     * @param idIstanza              the id istanza
     * @param codeStatiPagamentoList the code stati pagamento list
     * @param notIn                  the not in
     * @return the list
     */
    @Override
    public List<PagamentoIstanzaDTO> loadPagamentoIstanzaByIdIstanzaStatoPagamento(Long idIstanza, List<String> codeStatiPagamentoList, boolean notIn) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_PAGAMENTO_ISTANZA_BY_ID_ISTANZA_STATI_PAGAMENTO;
        map.put("idIstanza", idIstanza);
        if (codeStatiPagamentoList != null && !codeStatiPagamentoList.isEmpty()) {
            map.put("codeStatiPagamentoList", codeStatiPagamentoList);
            query = notIn ? query + WHERE_CODE_STATO_PAGAMENTO_NOT_IN : query + WHERE_CODE_STATO_PAGAMENTO_IN;
        }
        return findSimpleDTOListByQuery(className, query, map);
    }

    /**
     * Save pagamento istanza long.
     *
     * @param dto the pagamento istanza
     * @return the long
     */
    @Override
    public Long savePagamentoIstanza(PagamentoIstanzaDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            map.put("idIstanza", dto.getIdIstanza());
            map.put("idRiscossioneEnte", dto.getIdRiscossioneEnte());
            map.put("idStatoPagamento", dto.getIdStatoPagamento());
            map.put("idOnerePadre", dto.getIdOnerePadre());
            map.put("dataInserimentoPagamento", now);
            map.put("importoDovuto", dto.getImportoDovuto());
            map.put("iuv", dto.getIuv());
            map.put("dataEffettivoPagamento", dto.getDataEffettivoPagamento());
            map.put("importoPagato", dto.getImportoPagato());
            map.put("iubd", dto.getIubd());
            map.put("rtXml", dto.getRtXml());
            map.put("idAllegatoIstanza", dto.getIdAllegatoIstanza());
            map.put("indTipoInserimento", dto.getIndTipoInserimento());
            map.put("flgNonDovuto", Boolean.TRUE.equals(dto.getFlgNonDovuto()) ? 1 : 0);
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());
            map.put("gestUid", generateGestUID(dto.getIdIstanza().toString() + dto.getIdRiscossioneEnte().toString() + now));

            MapSqlParameterSource params = getParameterValue(map);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            int returnValue = template.update(getQuery(QUERY_INSERT_PAGAMENTO_ISTANZA, null, null), params, keyHolder, new String[]{"id_pagamento_istanza"});
            Number key = keyHolder.getKey();
            return key.longValue();
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update pagamento istanza integer.
     *
     * @param dto the pagamento istanza
     * @return the integer
     */
    @Override
    public Integer updatePagamentoIstanza(PagamentoIstanzaDTO dto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idPagamentoIstanza", dto.getIdPagamentoIstanza());

        try {
            PagamentoIstanzaDTO pagamentoIstanzaOld = this.findByPK(map);
            if (null == pagamentoIstanzaOld) {
                logError(className, "Record non trovato con id [" + dto.getIdPagamentoIstanza() + "]");
                return -1;
            }

            Date now = Calendar.getInstance().getTime();
            map.put("idIstanza", pagamentoIstanzaOld.getIdIstanza());
            map.put("idRiscossioneEnte", dto.getIdRiscossioneEnte());
            map.put("idStatoPagamento", dto.getIdStatoPagamento());
            map.put("idOnerePadre", dto.getIdOnerePadre());
            map.put("dataInserimentoPagamento", pagamentoIstanzaOld.getDataInserimentoPagamento());
            map.put("importoDovuto", dto.getImportoDovuto());
            map.put("iuv", dto.getIuv());
            map.put("dataEffettivoPagamento", dto.getDataEffettivoPagamento());
            map.put("importoPagato", dto.getImportoPagato());
            map.put("iubd", dto.getIubd());
            map.put("rtXml", dto.getRtXml());
            map.put("idAllegatoIstanza", dto.getIdAllegatoIstanza());
            map.put("indTipoInserimento", dto.getIndTipoInserimento());
            map.put("flgNonDovuto", Boolean.TRUE.equals(dto.getFlgNonDovuto()) ? 1 : 0);
            map.put("gestDataIns", pagamentoIstanzaOld.getGestDataIns());
            map.put("gestAttoreIns", pagamentoIstanzaOld.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreUpd());
            map.put("gestUid", pagamentoIstanzaOld.getGestUID());

            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_UPDATE_PAGAMENTO_ISTANZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete pagamento istanza integer.
     *
     * @param uid the gest uid
     * @return the integer
     */
    @Override
    public Integer deletePagamentoIstanza(String uid) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("gestUid", uid);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_PAGAMENTO_ISTANZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_PAGAMENTO_ISTANZA_PK, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>  row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<PagamentoIstanzaDTO> getRowMapper() throws SQLException {
        return new PagamentoIstanzaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>  extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<PagamentoIstanzaExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new PagamentoIstanzaExtendedRowMapper();
    }

    /**
     * The type Pagamento istanza row mapper.
     */
    public static class PagamentoIstanzaRowMapper implements RowMapper<PagamentoIstanzaDTO> {

        /**
         * Instantiates a new Competenza territorio row mapper.
         *
         * @throws SQLException the sql exception
         */
        public PagamentoIstanzaRowMapper() throws SQLException {
            // Instantiate class
        }

        /**
         * Implementations must implement this method to map each row of data
         * in the ResultSet. This method should not call {@code next()} on
         * the ResultSet; it is only supposed to map values of the current row.
         *
         * @param rs     the ResultSet to map (pre-initialized for the current row)
         * @param rowNum the number of the current row
         * @return the result object for the current row (may be {@code null})
         * @throws SQLException if a SQLException is encountered getting
         *                      column values (that is, there's no need to catch SQLException)
         */
        @Override
        public PagamentoIstanzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            PagamentoIstanzaDTO bean = new PagamentoIstanzaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, PagamentoIstanzaDTO bean) throws SQLException {
            bean.setIdPagamentoIstanza(rs.getLong("id_pagamento_istanza"));
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdRiscossioneEnte(rs.getLong("id_riscossione_ente"));
            bean.setIdStatoPagamento(rs.getLong("id_stato_pagamento"));
            bean.setIdOnerePadre(rs.getLong("id_onere_padre"));
            bean.setDataInserimentoPagamento(rs.getTimestamp("data_inserimento_pagamento"));
            bean.setImportoDovuto(rs.getBigDecimal("importo_dovuto"));
            bean.setIuv(rs.getString("iuv"));
            bean.setDataEffettivoPagamento(rs.getTimestamp("data_effettivo_pagamento"));
            bean.setImportoPagato(rs.getBigDecimal("importo_pagato"));
            bean.setIubd(rs.getString("iubd"));
            bean.setRtXml(rs.getString("rt_xml"));
            bean.setIdAllegatoIstanza(rs.getLong("id_allegato_istanza") != 0 ? rs.getLong("id_allegato_istanza") : null);
            bean.setIndTipoInserimento(rs.getString("ind_tipo_inserimento"));
            bean.setFlgNonDovuto(rs.getInt("flg_non_dovuto") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }

    }

    /**
     * The type Riscossione ente extended row mapper.
     */
    public static class PagamentoIstanzaExtendedRowMapper implements RowMapper<PagamentoIstanzaExtendedDTO> {

        /**
         * Instantiates a new Competenza territorio row mapper.
         *
         * @throws SQLException the sql exception
         */
        public PagamentoIstanzaExtendedRowMapper() throws SQLException {
            // Instantiate class
        }

        /**
         * Implementations must implement this method to map each row of data
         * in the ResultSet. This method should not call {@code next()} on
         * the ResultSet; it is only supposed to map values of the current row.
         *
         * @param rs     the ResultSet to map (pre-initialized for the current row)
         * @param rowNum the number of the current row
         * @return the result object for the current row (may be {@code null})
         * @throws SQLException if a SQLException is encountered getting
         *                      column values (that is, there's no need to catch SQLException)
         */
        @Override
        public PagamentoIstanzaExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            PagamentoIstanzaExtendedDTO bean = new PagamentoIstanzaExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, PagamentoIstanzaExtendedDTO bean) throws SQLException {
            bean.setIdPagamentoIstanza(rs.getLong("id_pagamento_istanza"));
            bean.setIdIstanza(rs.getLong("id_istanza"));

            RiscossioneEnteExtendedDTO riscossioneEnte = new RiscossioneEnteExtendedDTO();
            populateBeanRiscossioneEnte(rs, riscossioneEnte);
            bean.setRiscossioneEnte(riscossioneEnte);

            StatoPagamentoDTO statoPagamento = new StatoPagamentoDTO();
            populateBeanStatoPagamento(rs, statoPagamento);
            bean.setStatoPagamento(statoPagamento);

            AllegatoIstanzaDTO allegatoIstanza = new AllegatoIstanzaDTO();
            populateBeanAllegatoIstanza(rs, allegatoIstanza);
            bean.setAllegatoIstanza(allegatoIstanza.getIdAllegatoIstanza() != 0 ? allegatoIstanza : null);

            bean.setIdOnerePadre(rs.getLong("id_onere_padre") == 0L ? null : rs.getLong("id_onere_padre"));
            bean.setDataInserimentoPagamento(rs.getTimestamp("data_inserimento_pagamento"));
            bean.setImportoDovuto(rs.getBigDecimal("importo_dovuto"));
            bean.setIuv(rs.getString("iuv"));
            bean.setDataEffettivoPagamento(rs.getTimestamp("data_effettivo_pagamento"));
            bean.setImportoPagato(rs.getBigDecimal("importo_pagato"));
            bean.setIubd(rs.getString("iubd"));
            bean.setRtXml(rs.getString("rt_xml"));
            bean.setIndTipoInserimento(rs.getString("ind_tipo_inserimento"));
            bean.setFlgNonDovuto(rs.getInt("flg_non_dovuto") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setGestUID(rs.getString("gest_uid"));
        }

        private void populateBeanRiscossioneEnte(ResultSet rs, RiscossioneEnteExtendedDTO bean) throws SQLException {
            bean.setIdRiscossioneEnte(rs.getLong("riscossione_ente_id"));

            AdempimentoTipoPagamentoExtendedDTO adempimentoTipoPagamento = new AdempimentoTipoPagamentoExtendedDTO();
            populateBeanAdempimentoTipoPagamento(rs, adempimentoTipoPagamento);
            bean.setAdempimentoTipoPagamento(adempimentoTipoPagamento);

            GruppoPagamentoDTO gruppoPagamento = new GruppoPagamentoDTO();
            populateBeanGruppoPagamento(rs, gruppoPagamento);
            bean.setGruppoPagamento(gruppoPagamento);

            StatoIstanzaDTO statoIstanzaBlocco = new StatoIstanzaDTO();
            populateBeanStatoIstanzaBlocco(rs, statoIstanzaBlocco);

            bean.setStatoIstanzaBlocco(statoIstanzaBlocco.getCodiceStatoIstanza() != null ? statoIstanzaBlocco : null);

            bean.setDatiSpecificiRiscossione(rs.getString("dati_specifici_riscossione"));
            bean.setAccertamentoAnno(rs.getInt("accertamento_anno"));
            bean.setNumeroAccertamento(rs.getLong("numero_accertamento"));
            bean.setDesPagamentoVersoCittadino(rs.getString("des_pagamento_verso_cittadino"));
            bean.setOrdinamentoRiscossioneEnte(rs.getInt("ordinamento_riscossione_ente"));
            bean.setUrloOneriPrevisti(rs.getString("url_oneri_previsti"));
            bean.setFlgAttivaPagamento(rs.getInt("flg_attiva_pagamento") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgAttivaImportoNonDovuto(rs.getInt("flg_attiva_importo_non_dovuto") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgAttivo(rs.getInt("flg_attivo") == 1 ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateBeanAdempimentoTipoPagamento(ResultSet rs, AdempimentoTipoPagamentoExtendedDTO bean) throws SQLException {
            bean.setIdAdempiTipoPagamento(rs.getLong("adempi_tipo_pagamento_id"));

            AdempimentoExtendedDTO adempimento = new AdempimentoExtendedDTO();
            populateBeanAdempimento(rs, adempimento);
            bean.setAdempimento(adempimento);

            TipoPagamentoExtendedDTO tipoPagamento = new TipoPagamentoExtendedDTO();
            populateBeanTipoPagamento(rs, tipoPagamento);
            bean.setTipoPagamento(tipoPagamento);

            EnteCreditoreDTO enteCreditore = new EnteCreditoreDTO();
            populateBeanEnteCreditore(rs, enteCreditore);
            bean.setEnteCreditore(enteCreditore);

            CompetenzaTerritorioDTO competenzaTerritorio = new CompetenzaTerritorioDTO();
            populateBeanCompetenzaTerritorio(rs, competenzaTerritorio);
            bean.setCompetenzaTerritorio(competenzaTerritorio);

            bean.setCodiceVersamento(rs.getString("codice_versamento"));
            bean.setImportoPrevisto(rs.getBigDecimal("importo_previsto"));
            bean.setGiorniMaxAttesaRt(rs.getInt("giorni_max_attesa_rt"));
            bean.setIndImportoPagamento(rs.getString("ind_importo_pagamento"));
        }

        private void populateBeanAdempimento(ResultSet rs, AdempimentoExtendedDTO bean) throws SQLException {
            bean.setIdAdempimento(rs.getLong("adempimento_id"));

            TipoAdempimentoExtendedDTO tipoAdempimento = new TipoAdempimentoExtendedDTO();
            pupulateBeanTipoAdempimento(rs, tipoAdempimento);
            bean.setTipoAdempimento(tipoAdempimento);

            bean.setCodAdempimento(rs.getString("cod_adempimento"));
            bean.setDesAdempimento(rs.getString("des_adempimento"));
            bean.setDesEstesaAdempimento(rs.getString("des_estesa_adempimento"));
        }

        private void pupulateBeanTipoAdempimento(ResultSet rs, TipoAdempimentoExtendedDTO bean) throws SQLException {
            bean.setIdTipoAdempimento(rs.getLong("tipo_adempimento_id"));

            AmbitoDTO ambito = new AmbitoDTO();
            populateBeanAmbito(rs, ambito);
            bean.setAmbito(ambito);

            bean.setDesTipoAdempimento(rs.getString("des_tipo_adempimento"));
            bean.setDesEstesaTipoAdempimento(rs.getString("des_estesa_tipo_adempimento"));
            bean.setCodTipoAdempimento(rs.getString("cod_tipo_adempimento"));
        }

        private void populateBeanAmbito(ResultSet rs, AmbitoDTO bean) throws SQLException {
            bean.setIdAmbito(rs.getLong("ambito_id"));
            bean.setCodAmbito(rs.getString("cod_ambito"));
            bean.setDesAmbito(rs.getString("des_ambito"));
            bean.setDesEstesaAmbito(rs.getString("des_estesa_ambito"));
        }

        private void populateBeanTipoPagamento(ResultSet rs, TipoPagamentoExtendedDTO bean) throws SQLException {
            bean.setIdTipoPagamento(rs.getLong("tipo_pagamento_id"));

            GruppoPagamentoDTO gruppoPagamento = new GruppoPagamentoDTO();
            populateBeanGruppoPagamento(rs, gruppoPagamento);
            bean.setGruppoPagamento(gruppoPagamento);

            bean.setCodiceTipoPagamento(rs.getString("cod_tipo_pagamento"));
            bean.setDescrizioneTipoPagamento(rs.getString("des_tipo_pagamento"));
        }

        private void populateBeanGruppoPagamento(ResultSet rs, GruppoPagamentoDTO bean) throws SQLException {
            bean.setIdGruppoPagamento(rs.getLong("gruppo_pagamento_id"));
            bean.setCodiceGruppoPagamento(rs.getString("cod_gruppo_pagamento"));
            bean.setDescrizioneGruppoPagamento(rs.getString("des_gruppo_pagamento"));
        }

        private void populateBeanStatoIstanzaBlocco(ResultSet rs, StatoIstanzaDTO bean) throws SQLException {
            bean.setIdStatoIstanza(rs.getLong("stato_istanza_id"));
            bean.setCodiceStatoIstanza(rs.getString("cod_stato_istanza"));
            bean.setDescrizioneStatoIstanza(rs.getString("des_stato_istanza"));
            bean.setDesEstesaStatoIstanza(rs.getString("des_estesa_stato_istanza"));
            bean.setIndRicercaOggetto(rs.getString("ind_ricerca_oggetto"));
            bean.setIndAggiornaOggetto(rs.getString("ind_aggiorna_oggetto"));
            bean.setFlgAggiornaOggetto(rs.getBoolean("flg_aggiorna_oggetto") ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateBeanEnteCreditore(ResultSet rs, EnteCreditoreDTO bean) throws SQLException {
            bean.setIdEnteCreditore(rs.getLong("ente_creditore_id"));
            bean.setCfEnteCreditore(rs.getString("cf_ente_creditore"));
            bean.setDenominazioneEnteCreditore(rs.getString("denominazione_ente_creditore"));
            bean.setIndirizzoTesoreria(rs.getString("indirizzo_tesoreria"));
            bean.setIbanAccredito(rs.getString("iban_accredito"));
            bean.setBicAccredito(rs.getString("bic_accredito"));
            bean.setFlgAderiscePiemontepay(rs.getInt("flg_aderisce_piemontepay") == 1 ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateBeanStatoPagamento(ResultSet rs, StatoPagamentoDTO bean) throws SQLException {
            bean.setIdStatoPagamento(rs.getLong("stato_pagamento_id"));
            bean.setCodiceStatoPagamento(rs.getString("cod_stato_pagamento"));
            bean.setDescrizioneStatoPagamento(rs.getString("des_stato_pagamento"));
        }

        private void populateBeanAllegatoIstanza(ResultSet rs, AllegatoIstanzaDTO bean) throws SQLException {
            Calendar timezone = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"));
            bean.setIdAllegatoIstanza(rs.getLong("allegato_istanza_id"));
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdTipologiaAllegato(rs.getLong("id_tipologia_allegato"));
            bean.setIdTipoIntegraAllegato(rs.getLong("id_tipo_integrazione"));
            bean.setUuidIndex(rs.getString("uuid_index_ai"));
            bean.setFlgRiservato(1 == rs.getInt("flg_riservato") ? Boolean.TRUE : Boolean.FALSE);
            bean.setCodAllegato(rs.getString("cod_allegato"));
            bean.setNomeAllegato(rs.getString("nome_allegato"));
            bean.setDimensioneUpload(rs.getLong("dimensione_upload"));
            bean.setDataUpload(rs.getTimestamp("data_upload", timezone));
            bean.setDataIntegrazione(rs.getTimestamp("data_integrazione", timezone));
            bean.setDataCancellazione(rs.getTimestamp("data_cancellazione", timezone));
            bean.setFlgCancellato(1 == rs.getInt("flg_cancellato") ? Boolean.TRUE : Boolean.FALSE);
            bean.setIndFirma(rs.getInt("ind_firma"));
            bean.setNote(rs.getString("note"));
        }

        private void populateBeanCompetenzaTerritorio(ResultSet rs, CompetenzaTerritorioDTO bean) throws SQLException {
            bean.setIdCompetenzaTerritorio(rs.getLong("id_competenza_territorio"));
            bean.setCodCompetenzaTerritorio(rs.getString("cod_competenza_territorio"));
            bean.setDesCompetenzaTerritorio(rs.getString("des_competenza_territorio"));
            //bean.setDesCompetenzaTerritorioEstesa(rs.getString("des_competenza_territorio_estesa"));
        }

    }


}