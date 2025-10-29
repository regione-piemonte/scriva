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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoTipoAllegatoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.impl.mapper.StringRowMapper;
import it.csi.scriva.scrivabesrv.dto.AdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoTipoAllegatoDTO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoTipoAllegatoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;
import it.csi.scriva.scrivabesrv.dto.CategoriaAllegatoDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipologiaAllegatoExtendedDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Adempimento tipo allegato dao.
 *
 * @author CSI PIEMONTE
 */
public class AdempimentoTipoAllegatoDAOImpl extends ScrivaBeSrvGenericDAO<AdempimentoTipoAllegatoDTO> implements AdempimentoTipoAllegatoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_VINCOLO_TIPO_ALLEGATO_BY_ID_ISTANZA = "SELECT srvta.*, " +
            "sdtal.*, sdtal.id_tipologia_allegato AS tipologia_allegato_id, sdtal.flg_attivo AS tipologia_allegato_attivo, " +
            "sdca.*, sdca.id_categoria_allegato AS categoria_allegato_id, sdca.flg_attivo AS categoria_allegato_attivo, " +
            "sdad.*, sdad.id_adempimento AS adempimento_id, sdad.flg_attivo AS adempimento_attivo, " +
            "sdta.*, sdta.id_tipo_adempimento AS tipo_adempimento_id, sdta.flg_attivo AS tipo_adempimento_attivo, " +
            "sda.*, sda.id_ambito AS ambito_id, sda.flg_attivo AS ambito_attivo " +
            "FROM scriva_r_vincolo_tipo_allegato srvta " +
            "INNER JOIN scriva_d_tipologia_allegato sdtal ON srvta.id_tipologia_allegato = sdtal.id_tipologia_allegato " +
            "INNER JOIN scriva_d_categoria_allegato sdca ON sdtal.id_categoria_allegato = sdca.id_categoria_allegato " +
            "INNER JOIN scriva_d_adempimento sdad ON srvta.id_adempimento = sdad.id_adempimento " +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdad.id_tipo_adempimento = sdta.id_tipo_adempimento " +
            "INNER JOIN scriva_d_ambito sda ON sdta.id_ambito = sda.id_ambito " +
            "INNER JOIN scriva_r_istanza_vincolo_aut sriva ON sriva.id_vincolo_autorizza = srvta.id_vincolo_autorizza " +
            "INNER JOIN scriva_t_istanza sti ON sti.id_adempimento = srvta.id_adempimento " +
            "WHERE sti.id_istanza = :idIstanza " +
            "AND sriva.id_istanza = :idIstanza";

    private static final String QUERY_LOAD_ADEMPIMENTI_TIPO_ALLEGATI = "SELECT srata.*,\n"
            + "sdtal.*, sdtal.id_tipologia_allegato AS tipologia_allegato_id, sdtal.flg_attivo AS tipologia_allegato_attivo,\n"
            + "sdca.*, sdca.id_categoria_allegato AS categoria_allegato_id, sdca.flg_attivo AS categoria_allegato_attivo,\n"
            + "sdad.*, sdad.id_adempimento AS adempimento_id, sdad.flg_attivo AS adempimento_attivo,\n"
            + "sdta.*, sdta.id_tipo_adempimento AS tipo_adempimento_id, sdta.flg_attivo AS tipo_adempimento_attivo,\n"
            + "sda.*, sda.id_ambito AS ambito_id, sda.flg_attivo AS ambito_attivo\n"
            + "FROM _replaceTableName_ srata\n"
            + "INNER JOIN scriva_d_tipologia_allegato sdtal ON srata.id_tipologia_allegato = sdtal.id_tipologia_allegato\n"
            + "INNER JOIN scriva_d_categoria_allegato sdca ON sdtal.id_categoria_allegato = sdca.id_categoria_allegato\n"
            + "INNER JOIN scriva_d_adempimento sdad ON srata.id_adempimento = sdad.id_adempimento\n"
            + "INNER JOIN scriva_d_tipo_adempimento sdta ON sdad.id_tipo_adempimento = sdta.id_tipo_adempimento\n"
            + "INNER JOIN scriva_d_ambito sda ON sdta.id_ambito = sda.id_ambito\n";

    private static final String QUERY_ORDER_BY = "ORDER BY sdtal.ordinamento_tipologia_allegato ASC";
    
    private static final String QUERY_ORDER_BY_MIO = "ORDER BY srata.ordinamento_adem_tipo_allega ASC";

    private static final String QUERY_WHERE_CONDITIONS = "WHERE sdtal.flg_attivo = 1 "
            + "AND sdca.flg_attivo = 1 "
            + "AND srata.ind_modifica LIKE '%'||:codComponenteApp||'%' \n"; //SCRIVA-834
            //+ "AND srata.ind_visibile LIKE '%'||:codComponenteApp||'%' \n";

    private static final String QUERY_LOAD_ADEMPIMENTI_TIPO_ALLEGATI_BY_ID_ADEMPIMENTO = QUERY_LOAD_ADEMPIMENTI_TIPO_ALLEGATI
            + QUERY_WHERE_CONDITIONS
            + "AND sdad.id_adempimento = :idAdempimento "
            + QUERY_ORDER_BY;

    private static final String QUERY_LOAD_ADEMPIMENTI_TIPO_ALLEGATI_BY_CODE_ADEMPIMENTO = QUERY_LOAD_ADEMPIMENTI_TIPO_ALLEGATI
            + QUERY_WHERE_CONDITIONS
            + "AND UPPER(sdad.cod_adempimento) = UPPER(:codAdempimento) "
            + QUERY_ORDER_BY;

    private static final String QUERY_LOAD_ADEMPIMENTI_TIPO_ALLEGATI_BY_CODE_ADEMPIMENTO_AND_CODE_CATEGORIA = QUERY_LOAD_ADEMPIMENTI_TIPO_ALLEGATI
            + "WHERE sdtal.flg_attivo = 1 "
            + "AND sdca.flg_attivo = 1 "
            + "AND UPPER(sdad.cod_adempimento) = UPPER(:codAdempimento) AND UPPER(sdca.cod_categoria_allegato) = UPPER(:codCategoria) "
            + QUERY_ORDER_BY_MIO;

    private static final String QUERY_LOAD_ADEMPIMENTI_TIPO_ALLEGATI_BY_ID_ADEMPIMENTO_AND_ID_TIPOLOGIA_ALLEGATO = QUERY_LOAD_ADEMPIMENTI_TIPO_ALLEGATI
            + "WHERE sdtal.flg_attivo = 1 "
            + "AND sdca.flg_attivo = 1 "
            + "AND srata.id_adempimento = :idAdempimento AND srata.id_tipologia_allegato = :idTipologiaAllegato "
            + QUERY_ORDER_BY;

    private static final String QUERY_LOAD_COD_TIPO_ALLEGATO_BY_ID_ISTANZA = "SELECT srac.valore\n" +
            "FROM scriva_t_istanza sti\n" +
            "INNER JOIN scriva_r_stato_istanza_adempi srsia ON (srsia.id_stato_istanza = sti.id_stato_istanza AND srsia.id_adempimento = sti.id_adempimento)\n" +
            "INNER JOIN scriva_d_classe_allegato sdca ON sdca.id_classe_allegato = srsia.id_classe_allegato\n" +
            "INNER JOIN scriva_r_adempi_config srac ON (srac.id_adempimento = sti.id_adempimento AND UPPER(srac.chiave) = UPPER(sdca.cod_classe_allegato))\n" +
            "INNER JOIN scriva_d_informazioni_scriva sdis ON (sdis.id_informazione_scriva = srac.id_informazione_scriva AND sdis.cod_informazione_scriva = 'ELENCO_DOC')\n" +
            "WHERE sti.id_istanza = :idIstanza";

    private static final String QUERY_LOAD_ADEMPIMENTI_TIPO_ALLEGATI_BY_CODE_ALLEGATO = QUERY_LOAD_ADEMPIMENTI_TIPO_ALLEGATI +
            "WHERE UPPER(sdtal.cod_tipologia_allegato) = UPPER(:codAllegato)\n" +
            "AND srata.id_adempimento = :idAdempimento";
    
    private static final String WHERE_DATA_INIZIO_FINE  =  " WHERE (DATE(srata.data_inizio_validita) <= DATE(NOW()) AND (srata.data_fine_validita IS NULL OR DATE(srata.data_fine_validita)>= DATE(NOW())))\n ";

    
    private static final String QUERY_LOAD_ADEMPIMENTI_TIPO_ALLEGATI_BY_CODE_ADEMPIMENTO_REV = "SELECT srata.*,\r\n" + 
    		"sdtal.*, sdtal.id_tipologia_allegato AS tipologia_allegato_id, sdtal.flg_attivo AS tipologia_allegato_attivo,\r\n" + 
    		"sdca.*, sdca.id_categoria_allegato AS categoria_allegato_id, sdca.flg_attivo AS categoria_allegato_attivo,\r\n" + 
    		"sdad.*, sdad.id_adempimento AS adempimento_id, sdad.flg_attivo AS adempimento_attivo,\r\n" + 
    		"sdta.*, sdta.id_tipo_adempimento AS tipo_adempimento_id, sdta.flg_attivo AS tipo_adempimento_attivo,\r\n" + 
    		"sda.*, sda.id_ambito AS ambito_id, sda.flg_attivo AS ambito_attivo\r\n" + 
    		"FROM _replaceTableName_ srata\r\n" + 
    		"INNER JOIN scriva_d_tipologia_allegato sdtal ON srata.id_tipologia_allegato = sdtal.id_tipologia_allegato\r\n" + 
    		"INNER JOIN scriva_d_categoria_allegato sdca ON sdtal.id_categoria_allegato = sdca.id_categoria_allegato\r\n" + 
    		"INNER JOIN scriva_d_adempimento sdad ON srata.id_adempimento = sdad.id_adempimento\r\n" + 
    		"INNER JOIN scriva_d_tipo_adempimento sdta ON sdad.id_tipo_adempimento = sdta.id_tipo_adempimento\r\n" + 
    		"INNER JOIN scriva_d_ambito sda ON sdta.id_ambito = sda.id_ambito\r\n" + 
    		"WHERE (DATE(srata.data_inizio_validita) <= DATE(NOW()) AND (coalesce(srata.data_fine_validita, DATE(NOW())))>= DATE(NOW())) " + 
    		"AND sdtal.flg_attivo = 1 AND sdca.flg_attivo = 1 AND srata.ind_modifica LIKE '%'||:codComponenteApp||'%' \r\n" + 
    		"AND UPPER(sdad.cod_adempimento) = UPPER(:codAdempimento) \r\n" + 
    		"ORDER BY srata.ordinamento_adem_tipo_allega  ASC";

    private static final String QUERY_LOAD_ADEMPIMENTI_TIPO_ALLEGATI_BY_CODE_ADEMPIMENTO_AND_CODE_TIPOLOGIA_ALLEGATO = "SELECT\n" +
            "    srata.*,\n" +
            "    sdtal.*,\n" +
            "    sdtal.id_tipologia_allegato AS tipologia_allegato_id,\n" +
            "    sdtal.flg_attivo AS tipologia_allegato_attivo,\n" +
            "    sdca.*,\n" +
            "    sdca.id_categoria_allegato AS categoria_allegato_id,\n" +
            "    sdca.flg_attivo AS categoria_allegato_attivo,\n" +
            "    sdad.*,\n" +
            "    sdad.id_adempimento AS adempimento_id,\n" +
            "    sdad.flg_attivo AS adempimento_attivo,\n" +
            "    sdta.*,\n" +
            "    sdta.id_tipo_adempimento AS tipo_adempimento_id,\n" +
            "    sdta.flg_attivo AS tipo_adempimento_attivo,\n" +
            "    sda.*,\n" +
            "    sda.id_ambito AS ambito_id,\n" +
            "    sda.flg_attivo AS ambito_attivo\n" +
            "FROM\n" +
            "    scriva_r_adempi_tipo_allegato srata\n" +
            "INNER JOIN scriva_d_tipologia_allegato sdtal ON\n" +
            "    srata.id_tipologia_allegato = sdtal.id_tipologia_allegato\n" +
            "INNER JOIN scriva_d_categoria_allegato sdca ON\n" +
            "    sdtal.id_categoria_allegato = sdca.id_categoria_allegato\n" +
            "INNER JOIN scriva_d_adempimento sdad ON\n" +
            "    srata.id_adempimento = sdad.id_adempimento\n" +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON\n" +
            "    sdad.id_tipo_adempimento = sdta.id_tipo_adempimento\n" +
            "INNER JOIN scriva_d_ambito sda ON\n" +
            "    sdta.id_ambito = sda.id_ambito\n" +
            "WHERE\n" +
            "    (DATE(srata.data_inizio_validita) <= DATE(NOW())\n" +
            "        AND (COALESCE(srata.data_fine_validita,\n" +
            "        DATE(NOW())))>= DATE(NOW()))\n" +
            "    AND sdtal.flg_attivo = 1\n" +
            "    AND sdca.flg_attivo = 1\n" +
            "    AND UPPER(sdad.cod_adempimento) = UPPER(:codAdempimento)\n" +
            "    AND UPPER(sdtal.cod_tipologia_allegato) = UPPER(:codTipologiaAllegato)\n" +
            "ORDER BY\n" +
            "    srata.ordinamento_adem_tipo_allega ASC";
    
    
    @Override
    public List<AdempimentoTipoAllegatoExtendedDTO> loadTipologieAllegato() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(className, methodName));
        return findListByQuery(className, methodName, QUERY_LOAD_ADEMPIMENTI_TIPO_ALLEGATI + WHERE_DATA_INIZIO_FINE + QUERY_ORDER_BY_MIO, null);
    }

    /**
     * Lista AdempimentiTipoAllegato per idAdempimento
     *
     * @param idAdempimento idAdempimento
     * @return List<AdempimentoTipoAllegatoExtendedDTO>
     */
    @Override
    public List<AdempimentoTipoAllegatoExtendedDTO> loadTipologieAllegatoByIdAdempimento(Long idAdempimento, String codComponenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempimento", idAdempimento);
        map.put("codComponenteApp", codComponenteApp);
        return findListByQuery(className, QUERY_LOAD_ADEMPIMENTI_TIPO_ALLEGATI_BY_ID_ADEMPIMENTO, map);
    }

    /**
     * Lista AdempimentiTipoAllegato per codAdempimento
     *
     * @param codAdempimento codAdempimento
     * @return List<AdempimentoTipoAllegatoExtendedDTO>
     */
    @Override
    public List<AdempimentoTipoAllegatoExtendedDTO> loadTipologieAllegatoByCodeAdempimento(String codAdempimento, String codComponenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codAdempimento", codAdempimento);
        map.put("codComponenteApp", codComponenteApp);
        return findListByQuery(className, QUERY_LOAD_ADEMPIMENTI_TIPO_ALLEGATI_BY_CODE_ADEMPIMENTO_REV, map);
    }

    /**
     * Carica una lista di AdempimentoTipoAllegatoExtendedDTO in base al codice adempimento,
     * al codice tipologia allegato e al codice componente applicativa.
     * Non filtra per ind_modifica.
     *
     * @param codAdempimento       Codice dell'adempimento.
     * @param codTipologiaAllegato Codice della tipologia di allegato.
     * @param codComponenteApp     Codice della componente applicativa.
     * @return Lista di AdempimentoTipoAllegatoExtendedDTO corrispondente ai criteri di ricerca.
     */
    @Override
    public List<AdempimentoTipoAllegatoExtendedDTO> loadTipologiaAllegatoByCodeAdempimentoAndCodeTipologiaAllegato(String codAdempimento, String codTipologiaAllegato, String codComponenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codAdempimento", codAdempimento);
        map.put("codTipologiaAllegato", codTipologiaAllegato);
        map.put("codComponenteApp", codComponenteApp);
        return findListByQuery(className, QUERY_LOAD_ADEMPIMENTI_TIPO_ALLEGATI_BY_CODE_ADEMPIMENTO_AND_CODE_TIPOLOGIA_ALLEGATO, map);
    }

    /**
     * Lista AdempimentiTipoAllegato per codAdempimento e codCategoria
     *
     * @param codAdempimento codAdempimento
     * @param codCategoria   codCategoria
     * @return List<AdempimentoTipoAllegatoExtendedDTO>
     */
    @Override
    public List<AdempimentoTipoAllegatoExtendedDTO> loadTipologieAllegatoByCodeAdempimentoAndCodeCategoria(String codAdempimento, String codCategoria, String codComponenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codAdempimento", codAdempimento);
        map.put("codCategoria", codCategoria);
        map.put("codComponenteApp", codComponenteApp);
        return findListByQuery(className, QUERY_LOAD_ADEMPIMENTI_TIPO_ALLEGATI_BY_CODE_ADEMPIMENTO_AND_CODE_CATEGORIA, map);
    }

    /**
     * Lista AdempimentiTipoAllegato per idAdempimento e idTipologiaAllegato
     *
     * @param idAdempimento       idAdempimento
     * @param idTipologiaAllegato idTipologiaAllegato
     * @return List<AdempimentoTipoAllegatoExtendedDTO>
     */
    @Override
    public List<AdempimentoTipoAllegatoExtendedDTO> loadTipologieAllegatoByIdAdempimentoAndIdTipologiaAllegato(Long idAdempimento, Long idTipologiaAllegato, String codComponenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempimento", idAdempimento);
        map.put("idTipologiaAllegato", idTipologiaAllegato);
        map.put("codComponenteApp", codComponenteApp);
        return findListByQuery(className, QUERY_LOAD_ADEMPIMENTI_TIPO_ALLEGATI_BY_ID_ADEMPIMENTO_AND_ID_TIPOLOGIA_ALLEGATO, map);
    }

    /**
     * Load tipologie allegato by istanza vincolo aut list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    @Override
    public List<AdempimentoTipoAllegatoExtendedDTO> loadTipologieAllegatoByIstanzaVincoloAut(Long idIstanza, String codComponenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        map.put("codComponenteApp", codComponenteApp);
        return findListByQuery(className, QUERY_LOAD_VINCOLO_TIPO_ALLEGATO_BY_ID_ISTANZA, map);
    }

    /**
     * Load tipologie allegato by cod allegato list.
     *
     * @param codAllegato the cod allegato
     * @return the list
     */
    @Override
    public List<AdempimentoTipoAllegatoExtendedDTO> loadTipologieAllegatoByCodAllegato(String codAllegato, Long idAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codAllegato", codAllegato);
        map.put("idAdempimento", idAdempimento);
        return findListByQuery(className, QUERY_LOAD_ADEMPIMENTI_TIPO_ALLEGATI_BY_CODE_ALLEGATO, map);
    }

    /**
     * Load cod tipologia allegato by id istanza string.
     *
     * @param idIstanza the id istanza
     * @return the string
     */
    @Override
    public String loadCodTipologiaAllegatoByIdIstanza(Long idIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        MapSqlParameterSource params = getParameterValue(map);
        List<String> result = template.query(QUERY_LOAD_COD_TIPO_ALLEGATO_BY_ID_ISTANZA, params, new StringRowMapper("valore"));
        return result != null && !result.isEmpty() ? result.get(0) : null;
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return string
     */
    @Override
    public String getPrimaryKeySelect() {
        return null;
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<AdempimentoTipoAllegatoDTO>
     */
    @Override
    public RowMapper<AdempimentoTipoAllegatoDTO> getRowMapper() throws SQLException {
        return new AdempimentoTipoAllegatoRowMapper();
    }

    /**
     * Returns a RowMapper extended for a new bean instance
     *
     * @return RowMapper<AdempimentoTipoAllegatoExtendedDTO>
     */
    @Override
    public RowMapper<AdempimentoTipoAllegatoExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new AdempimentoTipoAllegatoExtendedRowMapper();
    }

    private static class AdempimentoTipoAllegatoRowMapper implements RowMapper<AdempimentoTipoAllegatoDTO> {

        /**
         * Costruttore
         *
         * @throws SQLException SQLException
         */
        public AdempimentoTipoAllegatoRowMapper() throws SQLException {
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
        public AdempimentoTipoAllegatoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdempimentoTipoAllegatoDTO bean = new AdempimentoTipoAllegatoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, AdempimentoTipoAllegatoDTO bean) throws SQLException {
            bean.setIdAdempimento(rs.getLong("adempimento_id"));
            bean.setIdTipologiaAllegato(rs.getLong("tipologia_allegato_id"));
            bean.setFlgFirmaDigitale(1 == rs.getInt("flg_firma_digitale") ? Boolean.TRUE : Boolean.FALSE);
            //bean.setFlgNascondiFO(1 == rs.getInt("flg_nascondi_fo") ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgNota(1 == rs.getInt("flg_nota") ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgObbligo(1 == rs.getInt("flg_obbligo") ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgRiservato(1 == rs.getInt("flg_riservato") ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgFirmaNonValidaBloccante(1 == rs.getInt("flg_firma_non_valida_bloccante") ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgIntegrazione(1 == rs.getInt("flg_integrazione") ? Boolean.TRUE : Boolean.FALSE);
        }
    }

    private static class AdempimentoTipoAllegatoExtendedRowMapper implements RowMapper<AdempimentoTipoAllegatoExtendedDTO> {

        /**
         * Costruttore
         *
         * @throws SQLException SQLException
         */
        public AdempimentoTipoAllegatoExtendedRowMapper() throws SQLException {
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
        public AdempimentoTipoAllegatoExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdempimentoTipoAllegatoExtendedDTO bean = new AdempimentoTipoAllegatoExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, AdempimentoTipoAllegatoExtendedDTO bean) throws SQLException {
            AdempimentoExtendedDTO adempimento = new AdempimentoExtendedDTO();
            populateAdempimento(rs, adempimento);
            bean.setAdempimento(adempimento);

            TipologiaAllegatoExtendedDTO tipologiaAllegato = new TipologiaAllegatoExtendedDTO();
            populateTipologiaAllegato(rs, tipologiaAllegato);
            bean.setTipologiaAllegato(tipologiaAllegato);

            bean.setFlgFirmaDigitale(1 == rs.getInt("flg_firma_digitale") ? Boolean.TRUE : Boolean.FALSE);
            //bean.setFlgNascondiFO(1 == rs.getInt("flg_nascondi_fo") ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgNota(1 == rs.getInt("flg_nota") ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgObbligo(1 == rs.getInt("flg_obbligo") ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgRiservato(1 == rs.getInt("flg_riservato") ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgFirmaNonValidaBloccante(1 == rs.getInt("flg_firma_non_valida_bloccante") ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgIntegrazione(1 == rs.getInt("flg_integrazione") ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateTipologiaAllegato(ResultSet rs, TipologiaAllegatoExtendedDTO bean) throws SQLException {
            bean.setIdTipologiaAllegato(rs.getLong("tipologia_allegato_id"));

            CategoriaAllegatoDTO categoriaAllegato = new CategoriaAllegatoDTO();
            populateBeanCategoriaAllegato(rs, categoriaAllegato);
            bean.setCategoriaAllegato(categoriaAllegato);

            bean.setCodTipologiaAllegato(rs.getString("cod_tipologia_allegato"));
            bean.setDesTipologiaAllegato(rs.getString("des_tipologia_allegato"));
            bean.setFlgAttivo(rs.getInt("tipologia_allegato_attivo") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgAtto(rs.getInt("flg_atto") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setOrdinamentoTipologiaAllegato(rs.getInt("ordinamento_tipologia_allegato"));
        }

        private void populateBeanCategoriaAllegato(ResultSet rs, CategoriaAllegatoDTO bean) throws SQLException {
            bean.setIdCategoriaAllegato(rs.getLong("categoria_allegato_id"));
            bean.setCodCategoriaAllegato(rs.getString("cod_categoria_allegato"));
            bean.setDesCategoriaAllegato(rs.getString("des_categoria_allegato"));
            bean.setFlgAttivo(1 == rs.getInt("categoria_allegato_attivo") ? Boolean.TRUE : Boolean.FALSE);
            bean.setOrdinamentoCategoriaAllegato(rs.getInt("ordinamento_categoria_allegato"));
        }

        private void populateAdempimento(ResultSet rs, AdempimentoExtendedDTO bean) throws SQLException {
            bean.setIdAdempimento(rs.getLong("adempimento_id"));

            TipoAdempimentoExtendedDTO tipoAdempimento = new TipoAdempimentoExtendedDTO();
            pupulateTipoAdempimentoExtendedDTO(rs, tipoAdempimento);
            bean.setTipoAdempimento(tipoAdempimento.getIdTipoAdempimento() != null ? tipoAdempimento : null);

            bean.setCodAdempimento(rs.getString("cod_adempimento"));
            bean.setDesAdempimento(rs.getString("des_adempimento"));
            bean.setDesEstesaAdempimento(rs.getString("des_estesa_adempimento"));
            bean.setFlgAttivo(1 == rs.getInt("adempimento_attivo") ? Boolean.TRUE : Boolean.FALSE);
            bean.setOrdinamentoAdempimento(rs.getInt("ordinamento_adempimento"));
        }

        private void pupulateTipoAdempimentoExtendedDTO(ResultSet rs, TipoAdempimentoExtendedDTO tipoAdempimento) throws SQLException {
            tipoAdempimento.setIdTipoAdempimento(rs.getLong("tipo_adempimento_id"));
            AmbitoDTO ambito = new AmbitoDTO();
            populateBeanAmbito(rs, ambito);
            tipoAdempimento.setAmbito(ambito.getIdAmbito() != null ? ambito : null);
            tipoAdempimento.setDesTipoAdempimento(rs.getString("des_tipo_adempimento"));
            tipoAdempimento.setDesEstesaTipoAdempimento(rs.getString("des_estesa_tipo_adempimento"));
            tipoAdempimento.setCodTipoAdempimento(rs.getString("cod_tipo_adempimento"));
            tipoAdempimento.setFlgAttivo(1 == rs.getInt("tipo_adempimento_attivo") ? Boolean.TRUE : Boolean.FALSE);
            tipoAdempimento.setOrdinamentoTipoAdempimento(rs.getInt("ordinamento_tipo_adempimento"));
        }

        private void populateBeanAmbito(ResultSet rs, AmbitoDTO ambito) throws SQLException {
            ambito.setIdAmbito(rs.getLong("ambito_id"));
            ambito.setCodAmbito(rs.getString("cod_ambito"));
            ambito.setDesAmbito(rs.getString("des_ambito"));
            ambito.setDesEstesaAmbito(rs.getString("des_estesa_ambito"));
            ambito.setFlgAttivo(1 == rs.getInt("ambito_attivo") ? Boolean.TRUE : Boolean.FALSE);
        }
    }
}