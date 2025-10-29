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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.ClasseAllegatoDAO;
import it.csi.scriva.scrivabesrv.dto.ClasseAllegatoDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Classe allegato dao.
 *
 * @author CSI PIEMONTE
 */
public class ClasseAllegatoDAOImpl extends ScrivaBeSrvGenericDAO<ClasseAllegatoDTO> implements ClasseAllegatoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_ID_ISTANZA = "AND sti.id_istanza = :idIstanza\n";

    private static final String WHERE_ID_ADEMPIMENTO = "AND srsia.id_adempimento = :idAdempimento\n";

    private static final String WHERE_ID_STATO_ISTANZA = "AND srsia.id_stato_istanza = :idStatoIstanza\n";

    private static final String WHERE_COD_CLASSE_ALLEGATO = "AND sdca.cod_classe_allegato = :codClasseAllegato\n";

    private static final String WHERE_LIKE_COMP_APP = "AND (srsia.ind_modificabile LIKE '%' || :codComponenteApp" +
            " || '%' OR UPPER(srsia.ind_modificabile)='N.A')\n";

    private static final String QUERY_CLASSI_ALLEGATO = "SELECT sdca.* FROM _replaceTableName_ sdca\n" +
            "WHERE sdca.flg_attivo = 1\n";

    private static final String QUERY_CLASSI_ALLEGATO_JOIN_STATO_ISTANZA = "SELECT sdca.*\n" +
            "FROM _replaceTableName_ sdca \n" +
            "INNER JOIN scriva_r_stato_istanza_adempi srsia ON srsia.id_classe_allegato = sdca.id_classe_allegato\n" +
            "WHERE sdca.flg_attivo = 1\n";

    private static final String QUERY_CLASSI_ALLEGATO_JOIN_ISTANZA = "SELECT sdca.*\n" +
            "FROM _replaceTableName_ sdca \n" +
            "INNER JOIN scriva_t_istanza sti ON sti.id_istanza = :idIstanza\n" +
            "INNER JOIN scriva_r_stato_istanza_adempi srsia ON srsia.id_classe_allegato = sdca.id_classe_allegato AND srsia.id_adempimento = sti.id_adempimento AND srsia.id_stato_istanza = sti.id_stato_istanza\n" +
            "WHERE sdca.flg_attivo = 1\n";

    private static final String QUERY_CLASSI_ALLEGATO_JOIN_ISTANZA_CONFIG = "SELECT sdca.*\n" +
            "FROM scriva_d_classe_allegato sdca \n" +
            "INNER JOIN scriva_t_istanza sti ON sti.id_istanza = :idIstanza\n" +
            "INNER JOIN scriva_r_adempi_config srac ON (srac.id_adempimento = sti.id_adempimento AND srac.valore = sdca.cod_classe_allegato AND srac.flg_attivo = 1)\n" +
            "INNER JOIN scriva_d_informazioni_scriva sdis ON sdis.id_informazione_scriva = srac.id_informazione_scriva\n" +
            "WHERE sdca.flg_attivo = 1\n" +
            "AND sdis.cod_informazione_scriva = :codInformazioneScriva\n" +
            "AND srac.chiave = :codTipologiaAllegato";

    /**
     * Load classe allegato list.
     *
     * @return the list
     */
    @Override
    public List<ClasseAllegatoDTO> loadClasseAllegato() {
        logBegin(className);
        return findListByQuery(className, QUERY_CLASSI_ALLEGATO, null);
    }

    /**
     * Load classe allegato list.
     *
     * @param codClasseAllegato the cod classe allegato
     * @return the list
     */
    @Override
    public List<ClasseAllegatoDTO> loadClasseAllegatoByCode(String codClasseAllegato) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_CLASSI_ALLEGATO;
        if (StringUtils.isNotBlank(codClasseAllegato)) {
            map.put("codClasseAllegato", codClasseAllegato);
            query += WHERE_COD_CLASSE_ALLEGATO;
        }
        return findListByQuery(className, query, map);
    }

    /**
     * Load classe allegato by id adempimento list.
     *
     * @param idAdempimento    the id adempimento
     * @param codComponenteApp the cod componente app
     * @return the list
     */
    @Override
    public List<ClasseAllegatoDTO> loadClasseAllegatoByIdAdempimento(Long idAdempimento, String codComponenteApp) {
        logBegin(className);
        return this.loadClasseAllegatoByIdAdempimentoStatoIstanza(idAdempimento, null, codComponenteApp);
    }

    /**
     * Load classe allegato by id adempimento stato istanza list.
     *
     * @param idAdempimento    the id adempimento
     * @param idStatoIstanza   the id stato istanza
     * @param codComponenteApp the cod componente app
     * @return the list
     */
    @Override
    public List<ClasseAllegatoDTO> loadClasseAllegatoByIdAdempimentoStatoIstanza(Long idAdempimento, Long idStatoIstanza, String codComponenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_CLASSI_ALLEGATO_JOIN_STATO_ISTANZA;
        if (idAdempimento != null) {
            map.put("idAdempimento", idAdempimento);
            query += WHERE_ID_ADEMPIMENTO;
        }
        if (idStatoIstanza != null) {
            map.put("idStatoIstanza", idStatoIstanza);
            query += WHERE_ID_STATO_ISTANZA;
        }
        if (StringUtils.isNotBlank(codComponenteApp)) {
            map.put("codComponenteApp", codComponenteApp);
            query += WHERE_LIKE_COMP_APP;
        }
        return findListByQuery(className, query, map);
    }

    /**
     * Load classe allegato by id istanza list.
     *
     * @param idIstanza        the id istanza
     * @param codComponenteApp the cod componente app
     * @return the list
     */
    @Override
    public List<ClasseAllegatoDTO> loadClasseAllegatoByIdIstanza(Long idIstanza, String codComponenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_CLASSI_ALLEGATO_JOIN_ISTANZA;
        map.put("idIstanza", idIstanza);
        if (StringUtils.isNotBlank(codComponenteApp)) {
            map.put("codComponenteApp", codComponenteApp);
            query += WHERE_LIKE_COMP_APP;
        }
        return findListByQuery(className, query, map);
    }

    /**
     * Load classe allegato by id istanza config list.
     *
     * @param idIstanza             the id istanza
     * @param codInformazioneScriva the cod informazione scriva
     * @param codTipologiaAllegato  the cod tipologia allegato
     * @return the list
     */
    @Override
    public List<ClasseAllegatoDTO> loadClasseAllegatoByIdIstanzaConfig(Long idIstanza, String codInformazioneScriva, String codTipologiaAllegato) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        map.put("codInformazioneScriva", codInformazioneScriva);
        map.put("codTipologiaAllegato", codTipologiaAllegato);
        return findListByQuery(className, QUERY_CLASSI_ALLEGATO_JOIN_ISTANZA_CONFIG, map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return null;
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>    row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<ClasseAllegatoDTO> getRowMapper() throws SQLException {
        return new ClasseAllegatoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>    extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<ClasseAllegatoDTO> getExtendedRowMapper() throws SQLException {
        return new ClasseAllegatoRowMapper();
    }

    public static class ClasseAllegatoRowMapper implements RowMapper<ClasseAllegatoDTO> {

        /**
         * Instantiates a new Classe Allegato row mapper.
         *
         * @throws SQLException the sql exception
         */
        public ClasseAllegatoRowMapper() throws SQLException {
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
        public ClasseAllegatoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            ClasseAllegatoDTO bean = new ClasseAllegatoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, ClasseAllegatoDTO bean) throws SQLException {
            bean.setIdClasseAllegato(rs.getLong("id_classe_allegato"));
            bean.setCodClasseAllegato(rs.getString("cod_classe_allegato"));
            bean.setDesClasseAllegato(rs.getString("des_classe_allegato"));
            bean.setOrdinamentoClasseAllegato(rs.getLong("ordinamento_classe_allegato"));
            bean.setFlgAttivo(1 == rs.getInt("flg_attivo") ? Boolean.TRUE : Boolean.FALSE);
        }
    }
}