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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaAttoreDAO;
import it.csi.scriva.scrivabesrv.dto.CompilanteDTO;
import it.csi.scriva.scrivabesrv.dto.ComponenteAppDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaAttoreDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaAttoreExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ProfiloAppExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAbilitazioneDTO;
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
 * The type Istanza attore dao.
 */
public class IstanzaAttoreDAOImpl extends ScrivaBeSrvGenericDAO<IstanzaAttoreDTO> implements IstanzaAttoreDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String ORDER_BY_DATA_INIZIO = "ORDER BY sria.data_inizio DESC";

    private static final String QUERY_PRIMARY_KEY = "SELECT * FROM _replaceTableName_ sria WHERE sria.id_istanza_attore = :idIstanzaAttore";

    private static final String QUERY_LOAD_ISTANZA_ATTORE_BY_UID = "SELECT * FROM _replaceTableName_ sria WHERE sria.gest_uid = :uid";

    private static final String QUERY_LOAD_ISTANZA_ATTORE = "SELECT sria.*,\n" +
            "sdpa.*, sdpa.id_profilo_app AS profilo_app_id,\n" +
            "sdca.*, sdca.id_componente_app AS componente_app_id,\n" +
            "sdta.*, sdta.id_tipo_abilitazione AS tipo_abilitazione_id,\n" +
            "stc.*, stc.id_compilante AS compilante_id\n" +
            "FROM _replaceTableName_ sria\n" +
            "INNER JOIN scriva_d_profilo_app sdpa ON sria.id_profilo_app = sdpa.id_profilo_app\n" +
            "INNER JOIN scriva_d_componente_app sdca ON sdpa.id_componente_app = sdca.id_componente_app \n" +
            "LEFT JOIN scriva_d_tipo_abilitazione sdta ON sria.id_tipo_abilitazione = sdta.id_tipo_abilitazione\n" +
            "LEFT JOIN scriva_t_compilante stc ON sria.id_compilante = stc .id_compilante\n" +
            "WHERE 1=1\n";

    private static final String WHERE_DATA_REVOCA_NULL = "AND sria.data_revoca IS NULL\n";

    private static final String QUERY_LOAD_ISTANZA_ATTORE_BY_ID = QUERY_LOAD_ISTANZA_ATTORE + "AND sria.id_istanza_attore = :idIstanzaAttore\n";

    private static final String QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA = QUERY_LOAD_ISTANZA_ATTORE //+ WHERE_DATA_REVOCA_NULL RIPRISTINARE?
            + "AND sria.id_istanza = :idIstanza\n ";

    private static final String QUERY_LOAD_ISTANZA_ATTORE_BY_CF_ATTORE = QUERY_LOAD_ISTANZA_ATTORE + WHERE_DATA_REVOCA_NULL
            + "AND sria.cf_attore = :cfAttore\n ";

    private static final String QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA_CF_ATTORE = QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA + "AND sria.cf_attore = :cfAttore\n";

    private static final String QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA_CF_ATTORE_TIPO_ABIL = QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA_CF_ATTORE
            + "AND sria.id_tipo_abilitazione = :idTipoAbilitazione\n";

    private static final String QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA_CF_ATTORE_CF_ABILITANTE_TIPO_ABIL_PROF_APP = QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA_CF_ATTORE
            + "AND sria.cf_abilitante_delegante = :cfAttoreAbilitante\n"
            + "AND sria.id_tipo_abilitazione = :idTipoAbilitazione\n"
            + "AND sria.id_profilo_app = :idProfiloApp\n";

    private static final String QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA_CF_ATTORE_CF_ABILITANTE_PROF_APP = QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA_CF_ATTORE
            + "AND sria.cf_abilitante_delegante = :cfAttoreAbilitante\n"
            + "AND sria.id_profilo_app = :idProfiloApp\n";

    private static final String QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA_CF_ATTORE_ABILITANTE = "SELECT sria.*,\n" +
            "sdpa.*, sdpa.id_profilo_app AS profilo_app_id,\n" +
            "sdca.*, sdca.id_componente_app AS componente_app_id,\n" +
            "sdta.*, sdta.id_tipo_abilitazione AS tipo_abilitazione_id,\n" +
            "stc.*, stc.id_compilante AS compilante_id\n" +
            "FROM scriva_r_istanza_attore sria\n" +
            "INNER JOIN scriva_d_profilo_app sdpa ON sria.id_profilo_app = sdpa.id_profilo_app\n" +
            "INNER JOIN scriva_d_componente_app sdca ON sdpa.id_componente_app = sdca.id_componente_app \n" +
            "INNER JOIN scriva_d_tipo_abilitazione sdta ON sria.id_tipo_abilitazione = sdta.id_tipo_abilitazione\n" +
            "LEFT JOIN scriva_t_compilante stc ON sria.id_compilante = stc .id_compilante\n" +
            "WHERE sria.id_istanza = :idIstanza\n" +
            "AND sria.cf_abilitante_delegante = :cfAttore\n" +
            "AND sria.id_tipo_abilitazione IS NOT NULL\n";

    private static final String QUERY_LOAD_ISTANZA_ATTORE_REVOCABILI_BY_ID_ISTANZA = "SELECT DISTINCT sria.*,\n" +
            "sdpa.*, sdpa.id_profilo_app AS profilo_app_id,\n" +
            "sdca.*, sdca.id_componente_app AS componente_app_id,\n" +
            "sdta.*, sdta.id_tipo_abilitazione AS tipo_abilitazione_id,\n" +
            "stc.*, stc.id_compilante AS compilante_id \n" +
            "FROM scriva_t_soggetto_istanza stsi \n" +
            "INNER JOIN scriva_t_istanza sti ON sti.id_istanza = stsi.id_istanza \n" +
            "INNER JOIN scriva_r_adempi_ruolo_compila srarc ON srarc.id_ruolo_compilante = stsi.id_ruolo_compilante \n" +
            "INNER JOIN scriva_r_istanza_attore sria ON sria.id_istanza = sti.id_istanza \n" +
            "INNER JOIN scriva_d_profilo_app sdpa ON sria.id_profilo_app = sdpa.id_profilo_app \n" +
            "INNER JOIN scriva_d_componente_app sdca ON sdpa.id_componente_app = sdca.id_componente_app  \n" +
            "LEFT JOIN scriva_d_tipo_abilitazione sdta ON sria.id_tipo_abilitazione = sdta.id_tipo_abilitazione \n" +
            "LEFT JOIN scriva_t_compilante stc ON sria.id_compilante = stc .id_compilante \n" +
            "WHERE sti.id_adempimento = srarc.id_adempimento \n" +
            "AND srarc.flg_revocabile = 1 \n" +
            "AND stsi.id_istanza_attore = sria.id_istanza_attore \n" +
            "AND sti.id_istanza = :idIstanza ";

    private static final String WHERE_CONDITION_COMP_PROF_APP = "AND sdca.cod_componente_app = :codComponenteApp\n" +
            "AND sdpa.cod_profilo_app = :codProfiloApp\n";

    private static final String QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA_CF_ATTORE_COMP_PROF_APP = QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA_CF_ATTORE +
            WHERE_CONDITION_COMP_PROF_APP;

    private static final String QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA_COMP_PROF_APP = QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA +
            WHERE_CONDITION_COMP_PROF_APP;

    private static final String QUERY_INSERT_ISTANZA_ATTORE = "INSERT INTO _replaceTableName_\n" +
            "(id_istanza_attore, id_istanza, id_tipo_abilitazione, id_compilante, id_profilo_app, cf_attore, cf_abilitante_delegante,\n" +
            "data_inizio, data_delega_con_firma, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "VALUES(nextval('seq_scriva_r_istanza_attore'), :idIstanza, :idTipoAbilitazione, :idCompilante, :idProfiloApp, :cfAttore, :cfAbilitanteDelegante,\n" +
            ":dataInizio, :dataDelegaConFirma, :gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_UPDATE_ISTANZA_ATTORE = "UPDATE _replaceTableName_\n" +
            "SET id_istanza=:idIstanza, id_tipo_abilitazione=:idTipoAbilitazione, id_compilante=:idCompilante, id_profilo_app=:idProfiloApp, cf_attore=:cfAttore, cf_abilitante_delegante=:cfAbilitanteDelegante,\n" +
            "data_inizio=:dataInizio, data_revoca=:dataRevoca, data_delega_con_firma=:dataDelegaConFirma, gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd\n" +
            "WHERE id_istanza_attore=:idIstanzaAttore";

    private static final String QUERY_UPDATE_ISTANZA_ATTORE_ID_COMPILANTE_BY_CF_ATTORE = "UPDATE _replaceTableName_\n" +
            "SET id_compilante=:idCompilante, gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd\n" +
            "WHERE cf_attore=:cfAttore";

    private static final String QUERY_DELETE_ISTANZA_ATTORE = "DELETE FROM _replaceTableName_ WHERE id_istanza_attore=:idIstanzaAttore";

    private static final String QUERY_DELETE_ISTANZA_ATTORE_BY_ID_ISTANZA_CF_ATTORE_PROF_APP = "DELETE FROM _replaceTableName_ sria\n" +
            "WHERE sria.cf_attore  = :cfAttore\n" +
            "AND sria.id_istanza = :idIstanza\n" +
            "AND sria.id_profilo_app = (SELECT id_profilo_app FROM scriva_d_profilo_app sdpa WHERE sdpa.cod_profilo_app = :codProfiloApp)";

    private static final String QUERY_DELETE_ISTANZA_ATTORE_BY_UID_ISTANZA_CF_ATTORE_PROF_APP = "DELETE FROM _replaceTableName_ sria\n" +
            "WHERE sria.cf_attore  = :cfAttore\n" +
            "AND sria.id_istanza = (SELECT id_istanza FROM scriva_t_istanza WHERE gest_uid = :uidIstanza)\n" +
            "AND sria.id_profilo_app = (SELECT id_profilo_app FROM scriva_d_profilo_app sdpa WHERE sdpa.cod_profilo_app = :codProfiloApp)";

    private static final String QUERY_DELETE_REF_ISTANZA_ATTORE_BY_ID_ISTANZA_CF_ATTORE_PROF_APP = "UPDATE scriva_t_istanza \n" +
            "SET id_istanza_attore_owner = NULL\n" +
            "WHERE id_istanza = :idIstanza\n" +
            "AND id_istanza_attore_owner IN (\n" +
            "    SELECT sria.id_istanza_attore \n" +
            "    FROM scriva_r_istanza_attore sria\n" +
            "    WHERE sria.cf_attore = :cfAttore\n" +
            "    AND sria.id_istanza = :idIstanza\n" +
            "    AND sria.id_profilo_app = (SELECT id_profilo_app FROM scriva_d_profilo_app sdpa WHERE sdpa.cod_profilo_app = :codProfiloApp)\n" +
            ")";
    
        private static final String QUERY_UPDATE_ISTANZA_ATTORE_OWNER_DOPO_REVOCA_DELEGA = "UPDATE scriva_t_istanza \n" +
            "SET id_istanza_attore_owner = :id_istanza_attore_owner WHERE id_istanza = :idIstanza;";        

    private static final String QUERY_UPDATE_ISTANZA_ATTORE_BY_ID_IST_BY_CF_ATTORE = "UPDATE _replaceTableName_\n" +
            "SET cf_attore = :cfAttoreNew,\n" +
            "gest_data_upd = :gestDataUpd, gest_attore_upd = :gestAttoreUpd\n" +
            "WHERE cf_attore = :cfAttore\n" +
            "AND id_istanza = :idIstanza\n" +
            "AND data_revoca IS NULL\n" +
            "AND id_profilo_app = (SELECT id_profilo_app FROM scriva_d_profilo_app sdpa WHERE sdpa.cod_profilo_app = :codProfiloApp)\n";
    
    private static final String QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA_CF_ATTORE_COMP = "SELECT sria.*,\r\n" + 
    		"sdpa.*, sdpa.id_profilo_app AS profilo_app_id,\r\n" + 
    		"sdca.*, sdca.id_componente_app AS componente_app_id,\r\n" + 
    		"sdta.*, sdta.id_tipo_abilitazione AS tipo_abilitazione_id,\r\n" + 
    		"stc.*, stc.id_compilante AS compilante_id\r\n" + 
    		"FROM _replaceTableName_  sria\r\n" + 
    		"INNER JOIN scriva_d_profilo_app sdpa ON sria.id_profilo_app = sdpa.id_profilo_app\r\n" + 
    		"INNER JOIN scriva_d_componente_app sdca ON sdpa.id_componente_app = sdca.id_componente_app \r\n" + 
    		"LEFT JOIN scriva_d_tipo_abilitazione sdta ON sria.id_tipo_abilitazione = sdta.id_tipo_abilitazione\r\n" + 
    		"LEFT JOIN scriva_t_compilante stc ON sria.id_compilante = stc .id_compilante\r\n" + 
    		"left join scriva_d_gestione_attore sdga on sdga.id_gestione_attore  = sdpa.id_gestione_attore \r\n" + 
    		"WHERE 1=1\r\n" + 
    		"AND sria.id_istanza = :idIstanza\r\n" + 
    		" AND sria.cf_attore = :cfAttore\r\n" + 
    		"AND sdca.cod_componente_app = :codComponenteApp\r\n" + 
    		"order by sdga.ordinamento_gestione_attore ";

    /**
     * @return List<IstanzaAttoreExtendedDTO>
     */
    @Override
    public List<IstanzaAttoreExtendedDTO> loadIstanzeAttori() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_ISTANZA_ATTORE, null);
    }

    /**
     * @param idIstanzaAttore idIstanzaAttore
     * @return List<IstanzaAttoreExtendedDTO>
     */
    @Override
    public List<IstanzaAttoreExtendedDTO> loadIstanzaAttoreById(Long idIstanzaAttore) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanzaAttore", idIstanzaAttore);
        return findListByQuery(className, QUERY_LOAD_ISTANZA_ATTORE_BY_ID, map);
    }

    /**
     * @param idIstanza idIstanza
     * @return List<IstanzaAttoreExtendedDTO>
     */
    @Override
    public List<IstanzaAttoreExtendedDTO> loadIstanzaAttoreByIdIstanza(Long idIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        return findListByQuery(className, QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA + ORDER_BY_DATA_INIZIO, map);
    }

    /**
     * @param cfAttore codice fiscale attore
     * @return List<IstanzaAttoreExtendedDTO>
     */
    @Override
    public List<IstanzaAttoreExtendedDTO> loadIstanzaAttoreByCFAttore(String cfAttore) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("cfAttore", cfAttore);
        return findListByQuery(className, QUERY_LOAD_ISTANZA_ATTORE_BY_CF_ATTORE + ORDER_BY_DATA_INIZIO, map); //ATTENZIONE, MANCA ID ISTANZA NELLA QUERY
    } //E' QUESTA DA MODIFICARE!?!?

    /**
     * @param idIstanza idIstanza
     * @param cfAttore  codice fiscale attore
     * @return List<IstanzaAttoreExtendedDTO>
     */
    @Override
    public List<IstanzaAttoreExtendedDTO> loadIstanzaAttoreByIdIstanzaAndCFAttore(Long idIstanza, String cfAttore) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        map.put("cfAttore", cfAttore);
        return findListByQuery(className, QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA_CF_ATTORE + ORDER_BY_DATA_INIZIO, map);
    }

    /**
     * Load istanza attore by id istanza and cf attore list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    @Override
    public List<IstanzaAttoreExtendedDTO> loadIstanzaAttoreRevocabiliByIdIstanza(Long idIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        return findListByQuery(className, QUERY_LOAD_ISTANZA_ATTORE_REVOCABILI_BY_ID_ISTANZA + ORDER_BY_DATA_INIZIO, map);
    }

    /**
     * Load istanza attore by id istanza and cf attore cf abilitante list.
     *
     * @param idIstanza          the id istanza
     * @param cfAttore           the cf attore
     * @param idTipoAbilitazione the id tipo abilitazione
     * @return the list
     */
    @Override
    public List<IstanzaAttoreExtendedDTO> loadIstanzaAttoreByIdIstanzaCFAttoreTipoAbilitazione(Long idIstanza, String cfAttore, Long idTipoAbilitazione) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        map.put("cfAttore", cfAttore);
        map.put("idTipoAbilitazione", idTipoAbilitazione);
        return findListByQuery(className, QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA_CF_ATTORE_TIPO_ABIL + ORDER_BY_DATA_INIZIO, map);
    }

    /**
     * Load istanza attore by id istanza and cf attore cf abilitante list.
     *
     * @param idIstanza          the id istanza
     * @param cfAttore           the cf attore
     * @param cfAttoreAbilitante the cf attore abilitante
     * @param idTipoAbilitazione the id tipo abilitazione
     * @param idProfiloApp       the id profilo app
     * @return the list
     */
    @Override
    public List<IstanzaAttoreExtendedDTO> loadIstanzaAttoreByIdIstanzaAndCFAttoreCFAbilitanteTipoAbilitazioneProfiloApp(Long idIstanza, String cfAttore, String cfAttoreAbilitante, Long idTipoAbilitazione, Long idProfiloApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        map.put("cfAttore", cfAttore);
        map.put("cfAttoreAbilitante", cfAttoreAbilitante);
        map.put("idTipoAbilitazione", idTipoAbilitazione);
        map.put("idProfiloApp", idProfiloApp);
        return findListByQuery(className, idTipoAbilitazione != null && idTipoAbilitazione > 0 ? QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA_CF_ATTORE_CF_ABILITANTE_TIPO_ABIL_PROF_APP : QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA_CF_ATTORE_CF_ABILITANTE_PROF_APP, map);
    }

    /**
     * @param idIstanza idIstanza
     * @param cfAttore  codice fiscale attore
     * @return List<IstanzaAttoreExtendedDTO>
     */
    @Override
    public List<IstanzaAttoreExtendedDTO> loadIstanzaAttoreByIdIstanzaCFAttoreCodCompAppCodProfApp(Long idIstanza, String cfAttore, String codComponenteApp, String codProfiloApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        map.put("cfAttore", cfAttore);
        map.put("codComponenteApp", codComponenteApp);
        map.put("codProfiloApp", codProfiloApp);
    return findListByQuery(className, StringUtils.isBlank(cfAttore) ? QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA_COMP_PROF_APP : QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA_CF_ATTORE_COMP, map);//QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA_CF_ATTORE_COMP_PROF_APP, map);
    } // DA VEDERE DOPO

    /**
     * @param idIstanza idIstanza
     * @param cfAttore  codice fiscale attore
     * @return List<IstanzaAttoreExtendedDTO>
     */
    @Override
    public List<IstanzaAttoreExtendedDTO> loadIstanzaAttoreByIdIstanzaCFAttoreCodCompAppCodProfApp(Long idIstanza, String cfAttore, String codComponenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        map.put("cfAttore", cfAttore);
        map.put("codComponenteApp", codComponenteApp);
       
    return findListByQuery(className, QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA_CF_ATTORE_COMP, map);
    } // DA VEDERE DOPO
    
    /**
     * @param idIstanza idIstanza
     * @param cfAttore  codice fiscale attore
     * @return List<IstanzaAttoreExtendedDTO>
     */
    @Override
    public List<IstanzaAttoreExtendedDTO> loadIstanzaAttoreByIdIstanzaCFAttoreCodCompAppCodProfAppForSoggIst(Long idIstanza, String cfAttore, String codComponenteApp, String codProfiloApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        map.put("cfAttore", cfAttore);
        map.put("codComponenteApp", codComponenteApp);
        map.put("codProfiloApp", codProfiloApp);
    return findListByQuery(className, StringUtils.isBlank(cfAttore) ? QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA_COMP_PROF_APP : QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA_CF_ATTORE_COMP_PROF_APP, map);
    }
    
    /**
     * Load istanza attore by id istanza cf attore abilitante delegante list.
     *
     * @param idIstanza the id istanza
     * @param cfAttore  the cf attore
     * @return the list
     */
    @Override
    public List<IstanzaAttoreExtendedDTO> loadIstanzaAttoreByIdIstanzaCfAttoreAbilitanteDelegante(Long idIstanza, String cfAttore) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        map.put("cfAttore", cfAttore);
        return findListByQuery(className, QUERY_LOAD_ISTANZA_ATTORE_BY_ID_ISTANZA_CF_ATTORE_ABILITANTE, map);
    }

    /**
     * @param istanzaAttore istanzaAttore
     * @return id record inserito
     */
    @Override
    public Long saveIstanzaAttore(IstanzaAttoreDTO istanzaAttore) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            map.put("idIstanza", istanzaAttore.getIdIstanza());
            map.put("idTipoAbilitazione", istanzaAttore.getIdTipoAbilitazione());
            map.put("idCompilante", istanzaAttore.getIdCompilante());
            map.put("idProfiloApp", istanzaAttore.getIdProfiloApp());
            map.put("cfAttore", istanzaAttore.getCfAttore());
            map.put("cfAbilitanteDelegante", istanzaAttore.getCfAbilitanteDelegante());
            map.put("dataInizio", now);
            map.put("dataDelegaConFirma", istanzaAttore.getDataDelegaConFirma());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", istanzaAttore.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", istanzaAttore.getGestAttoreIns());
            map.put("gestUid", generateGestUID(String.valueOf(istanzaAttore.getIdIstanza()) + istanzaAttore.getIdTipoAbilitazione() + istanzaAttore.getCfAttore() + now));
            MapSqlParameterSource params = getParameterValue(map);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(getQuery(QUERY_INSERT_ISTANZA_ATTORE, null, null), params, keyHolder, new String[]{"id_istanza_attore"});
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
     * @param dto istanzaAttore
     * @return numero record aggiornati
     */
    @Override
    public Integer updateIstanzaAttore(IstanzaAttoreDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            IstanzaAttoreDTO istanzaAttoreOld = this.findByPK(dto.getIdIstanzaAttore());
            if (null == istanzaAttoreOld) {
                LOGGER.error("[IstanzaAttoreDAOImpl::updateIstanzaAttore] Record non trovato con id [" + dto.getIdIstanzaAttore() + "]");
                return -1;
            }
            map.put("idIstanzaAttore", dto.getIdIstanzaAttore());
            map.put("idIstanza", istanzaAttoreOld.getIdIstanza());
            map.put("idTipoAbilitazione", dto.getIdTipoAbilitazione());
            map.put("idCompilante", dto.getIdCompilante());
            map.put("idProfiloApp", dto.getIdProfiloApp());
            map.put("cfAttore", dto.getCfAttore());
            map.put("cfAbilitanteDelegante", dto.getCfAbilitanteDelegante());
            map.put("dataInizio", dto.getDataInizio());
            map.put("dataRevoca", dto.getDataRevoca());
            map.put("dataDelegaConFirma", dto.getDataDelegaConFirma());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreUpd());
            MapSqlParameterSource params = getParameterValue(map);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            return template.update(getQuery(QUERY_UPDATE_ISTANZA_ATTORE, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update istanza attore integer.
     *
     * @param idCompilante the id compilante
     * @param cfAttore     the cf attore
     * @return numero record aggiornati
     */
    @Override
    public Integer updateIdCompilanteByCFAttore(Long idCompilante, String cfAttore, String cfAttoreUpd) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            map.put("idCompilante", idCompilante);
            map.put("cfAttore", cfAttore);
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", cfAttoreUpd);
            MapSqlParameterSource params = getParameterValue(map);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            return template.update(getQuery(QUERY_UPDATE_ISTANZA_ATTORE_ID_COMPILANTE_BY_CF_ATTORE, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * @param idIstanzaAttore idIstanzaAttore
     */
    @Override
    public Integer deleteIstanzaAttore(Long idIstanzaAttore) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanzaAttore", idIstanzaAttore);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_ISTANZA_ATTORE, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * @param idIstanza     idIstanza
     * @param cfAttore      codice fiscale attore
     * @param codProfiloApp codice profilo app
     * @return numero record eliminati
     */
    @Override
    public Integer deleteIstanzaAttoreByIdIstanzaCFAttoreCodProfApp(Long idIstanza, String cfAttore, String codProfiloApp) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            map.put("cfAttore", cfAttore);
            map.put("codProfiloApp", codProfiloApp);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_ISTANZA_ATTORE_BY_ID_ISTANZA_CF_ATTORE_PROF_APP, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * @param uidIstanza    uidIstanza
     * @param cfAttore      codice fiscale attore
     * @param codProfiloApp codice profilo app
     * @return numero record eliminati
     */
    @Override
    public Integer deleteIstanzaAttoreByUidIstanzaCFAttoreCodProfApp(String uidIstanza, String cfAttore, String codProfiloApp) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("uidIstanza", uidIstanza);
            map.put("cfAttore", cfAttore);
            map.put("codProfiloApp", codProfiloApp);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_ISTANZA_ATTORE_BY_UID_ISTANZA_CF_ATTORE_PROF_APP, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets istanza attore by uid.
     *
     * @param uid the uid
     * @return the istanza attore by uid
     */
    @Override
    public IstanzaAttoreDTO getIstanzaAttoreByUid(String uid) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        return findSimpleDTOByQuery(className, QUERY_LOAD_ISTANZA_ATTORE_BY_UID, map);
    }

    /**
     * Delete ref istanza attore by id istanza cf attore cod prof app integer.
     *
     * @param idIstanza     the id istanza
     * @param cfAttore      the cf attore
     * @param codProfiloApp the cod profilo app
     * @return the integer
     */
    @Override
    public Integer deleteRefIstanzaAttoreByIdIstanzaCFAttoreCodProfApp(Long idIstanza, String cfAttore, String codProfiloApp) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            map.put("cfAttore", cfAttore);
            map.put("codProfiloApp", codProfiloApp);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_REF_ISTANZA_ATTORE_BY_ID_ISTANZA_CF_ATTORE_PROF_APP, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update istanza attore owner.
     *
     * @param idIstanza       the id istanza
     * @param idIstanzaAttore the id istanza attore
     * @return the integer
     */
    @Override
    public Integer updateIstanzaAttoreOwner(Long idIstanza, Long idIstanzaAttore) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            map.put("id_istanza_attore_owner", idIstanzaAttore);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_UPDATE_ISTANZA_ATTORE_OWNER_DOPO_REVOCA_DELEGA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update istanza attore by id istanza cf attore cod prof app integer.
     *
     * @param idIstanza     the id istanza
     * @param cfAttore      the cf attore
     * @param codProfiloApp the cod profilo app
     * @param cfAttoreNew   the cf attore new
     * @param cfAttoreUpd   the cf attore upd
     * @return the integer
     */
    @Override
    public Integer updateIstanzaAttoreByIdIstanzaCFAttoreCodProfApp(Long idIstanza, String cfAttore, String codProfiloApp, String cfAttoreNew, String cfAttoreUpd) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            map.put("idIstanza", idIstanza);
            map.put("cfAttore", cfAttore);
            map.put("codProfiloApp", codProfiloApp);
            map.put("cfAttoreNew", cfAttoreNew);
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", cfAttoreUpd);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_UPDATE_ISTANZA_ATTORE_BY_ID_IST_BY_CF_ATTORE, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Find by pk istanza attore dto.
     *
     * @param idIstanzaAttore the id istanza attore
     * @return the istanza attore dto
     */
    public IstanzaAttoreDTO findByPK(Long idIstanzaAttore) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanzaAttore", idIstanzaAttore);
        return findByPK(className, map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return sTRING
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_PRIMARY_KEY, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<IstanzaAttoreExtendedDTO>
     */
    @Override
    public RowMapper<IstanzaAttoreDTO> getRowMapper() throws SQLException {
        return new IstanzaAttoreRowMapper();
    }

    /**
     * Returns a RowMapper extended for a new bean instance
     *
     * @return RowMapper<AllegatoIstanzaExtendedDTO>
     */
    @Override
    public RowMapper<IstanzaAttoreExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new IstanzaAttoreExtendedRowMapper();
    }

    private static class IstanzaAttoreRowMapper implements RowMapper<IstanzaAttoreDTO> {

        /**
         * Costruttore
         *
         * @throws SQLException SQLException
         */
        public IstanzaAttoreRowMapper() throws SQLException {
            // Instantiate class
        }

        @Override
        public IstanzaAttoreDTO mapRow(ResultSet rs, int i) throws SQLException {
            IstanzaAttoreDTO bean = new IstanzaAttoreDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, IstanzaAttoreDTO bean) throws SQLException {
            bean.setIdIstanzaAttore(rs.getLong("id_istanza_attore"));
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdTipoAbilitazione(rs.getLong("id_tipo_abilitazione"));
            bean.setIdCompilante(rs.getLong("id_compilante"));
            bean.setIdProfiloApp(rs.getLong("id_profilo_app"));
            bean.setCfAttore(rs.getString("cf_attore"));
            bean.setCfAbilitanteDelegante(rs.getString("cf_abilitante_delegante"));
            bean.setDataInizio(rs.getTimestamp("data_inizio"));
            bean.setDataRevoca(rs.getTimestamp("data_revoca"));
            bean.setDataDelegaConFirma(rs.getTimestamp("data_delega_con_firma"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

    private static class IstanzaAttoreExtendedRowMapper implements RowMapper<IstanzaAttoreExtendedDTO> {

        /**
         * Costruttore
         *
         * @throws SQLException SQLException
         */
        public IstanzaAttoreExtendedRowMapper() throws SQLException {
            // Instantiate class
        }

        @Override
        public IstanzaAttoreExtendedDTO mapRow(ResultSet rs, int i) throws SQLException {
            IstanzaAttoreExtendedDTO bean = new IstanzaAttoreExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, IstanzaAttoreExtendedDTO bean) throws SQLException {
            bean.setIdIstanzaAttore(rs.getLong("id_istanza_attore"));
            bean.setIdIstanza(rs.getLong("id_istanza"));

            TipoAbilitazioneDTO tipoAbilitazione = new TipoAbilitazioneDTO();
            populateTipoAbilitazione(rs, tipoAbilitazione);
            bean.setTipoAbilitazione(tipoAbilitazione.getIdTipoAbilitazione() > 0 ? tipoAbilitazione : null);

            CompilanteDTO compilante = new CompilanteDTO();
            populateCompilante(rs, compilante);
            bean.setCompilante(compilante.getIdCompilante() > 0 ? compilante : null);

            ProfiloAppExtendedDTO profiloApp = new ProfiloAppExtendedDTO();
            populateProfiloApp(rs, profiloApp);
            bean.setProfiloApp(profiloApp.getIdProfiloApp() > 0 ? profiloApp : null);

            bean.setCfAttore(rs.getString("cf_attore"));
            bean.setCfAbilitanteDelegante(rs.getString("cf_abilitante_delegante"));

            Calendar cal = Calendar.getInstance();
            cal.setTimeZone(TimeZone.getTimeZone("UTC"));
            bean.setDataInizio(rs.getTimestamp("data_inizio", cal));
            bean.setDataRevoca(rs.getTimestamp("data_revoca", cal));
            bean.setDataDelegaConFirma(rs.getTimestamp("data_delega_con_firma", cal));
            bean.setGestUID(rs.getString("gest_uid"));
        }

        private void populateTipoAbilitazione(ResultSet rs, TipoAbilitazioneDTO bean) throws SQLException {
            bean.setIdTipoAbilitazione(rs.getLong("tipo_abilitazione_id"));
            bean.setCodTipoAbilitazione(rs.getString("cod_tipo_abilitazione"));
            bean.setDesTipoAbilitazione(rs.getString("des_tipo_abilitazione"));
        }

        private void populateCompilante(ResultSet rs, CompilanteDTO bean) throws SQLException {
            bean.setIdCompilante(rs.getLong("compilante_id"));
            bean.setCodiceFiscaleCompilante(rs.getString("cf_compilante"));
            bean.setCognomeCompilante(rs.getString("cognome_compilante"));
            bean.setNomeCompilante(rs.getString("nome_compilante"));
            bean.setDesEmailCompilante(rs.getString("des_email_compilante"));
        }

        private void populateProfiloApp(ResultSet rs, ProfiloAppExtendedDTO bean) throws SQLException {
            bean.setIdProfiloApp(rs.getLong("profilo_app_id"));

            ComponenteAppDTO componenteApp = new ComponenteAppDTO();
            populateComponenteApp(rs, componenteApp);
            bean.setComponenteApp(componenteApp);

            bean.setCodProfiloApp(rs.getString("cod_profilo_app"));
            bean.setDesProfiloApp(rs.getString("des_profilo_app"));
            bean.setFlgProfiloIride(1 == rs.getInt("flg_profilo_iride") ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgCompetenza(1 == rs.getInt("flg_competenza") ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateComponenteApp(ResultSet rs, ComponenteAppDTO bean) throws SQLException {
            bean.setIdComponenteApp(rs.getLong("componente_app_id"));
            bean.setCodComponenteApp(rs.getString("cod_componente_app"));
            bean.setDesComponenteApp(rs.getString("des_componente_app"));
        }

    }

}