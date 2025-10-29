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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.CategoriaAllegatoDAO;
import it.csi.scriva.scrivabesrv.dto.CategoriaAllegatoDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Categoria allegato dao.
 *
 * @author CSI PIEMONTE
 */
public class CategoriaAllegatoDAOImpl extends ScrivaBeSrvGenericDAO<CategoriaAllegatoDTO> implements CategoriaAllegatoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_CATEGORIE_ALLEGATI = "SELECT * FROM _replaceTableName_ sdca\n";

    private static final String WHERE_COD_CATEGORIA_ALLEGATO = " WHERE UPPER(sdca.cod_categoria_allegato) = UPPER(:codCategAllegato) \n";

    private static final String QUERY_LOAD_CATEGORIA_ALLEGATI = "SELECT DISTINCT(sdca.*)\n"
            + "FROM scriva_r_adempi_tipo_allegato srata\n"
            + "INNER JOIN scriva_d_tipologia_allegato sdtal ON srata.id_tipologia_allegato = sdtal.id_tipologia_allegato\n"
            + "INNER JOIN _replaceTableName_ sdca ON sdtal.id_categoria_allegato = sdca.id_categoria_allegato\n"
            + "INNER JOIN scriva_d_adempimento sdad ON srata.id_adempimento = sdad.id_adempimento\n";

    private static final String QUERY_ORDER_BY = "ORDER BY sdca.ordinamento_categoria_allegato ASC";

    private static final String QUERY_WHERE_CONDITIONS = "WHERE sdtal.flg_attivo = 1\n"
            + "AND sdca.flg_attivo = 1\n"
            + "AND srata.data_fine_validita IS NULL\n"
            + "AND srata.ind_modifica LIKE '%'||:codComponenteApp||'%' \n";

    private static final String QUERY_LOAD_CATEGORIA_ALLEGATI_BY_ID_ADEMPIMENTO = QUERY_LOAD_CATEGORIA_ALLEGATI
            + QUERY_WHERE_CONDITIONS
            + "AND sdad.id_adempimento = :idAdempimento\n"
            + QUERY_ORDER_BY;

    private static final String QUERY_LOAD_CATEGORIA_ALLEGATI_BY_CODE_ADEMPIMENTO = QUERY_LOAD_CATEGORIA_ALLEGATI
            + QUERY_WHERE_CONDITIONS
            + "AND UPPER(sdad.cod_adempimento) = UPPER(:codAdempimento)\n"
            + QUERY_ORDER_BY;
    
//    private static final String QUERY_LOAD_CATEGORIA_ALLEGATI_BY_CODE_ADEMPIMENTO_REV = "SELECT DISTINCT(sdca.*), srata.ordinamento_adem_tipo_allega  \r\n" + 
//    		"FROM scriva_r_adempi_tipo_allegato srata\r\n" + 
//    		"INNER JOIN scriva_d_tipologia_allegato sdtal ON srata.id_tipologia_allegato = sdtal.id_tipologia_allegato\r\n" + 
//    		"INNER JOIN scriva_d_categoria_allegato  sdca ON sdtal.id_categoria_allegato = sdca.id_categoria_allegato\r\n" + 
//    		"INNER JOIN scriva_d_adempimento sdad ON srata.id_adempimento = sdad.id_adempimento\r\n" + 
//    		"WHERE sdtal.flg_attivo = 1\r\n" + 
//    		"AND sdca.flg_attivo = 1\r\n" + 
//    		"AND srata.data_inizio_validita <= DATE(now())\r\n" + 
//    		"and coalesce(srata.data_fine_validita, DATE(now())) >= DATE(now())\r\n" + 
//    		"AND srata.ind_modifica LIKE '%'||:codComponenteApp||'%' \r\n" + 
//    		"AND UPPER(sdad.cod_adempimento) = UPPER(:codAdempimento)\r\n" + 
//    		"ORDER BY srata.ordinamento_adem_tipo_allega ";
    
    private static final String QUERY_LOAD_CATEGORIA_ALLEGATI_BY_CODE_ADEMPIMENTO_REV = "SELECT sdca.*, MIN(srata.ordinamento_adem_tipo_allega)   \r\n" + 
    		"FROM scriva_r_adempi_tipo_allegato srata \r\n" + 
    		"INNER JOIN scriva_d_tipologia_allegato sdtal ON srata.id_tipologia_allegato = sdtal.id_tipologia_allegato \r\n" + 
    		"INNER JOIN scriva_d_categoria_allegato  sdca ON sdtal.id_categoria_allegato = sdca.id_categoria_allegato \r\n" + 
    		"INNER JOIN scriva_d_adempimento sdad ON srata.id_adempimento = sdad.id_adempimento \r\n" + 
    		"WHERE sdtal.flg_attivo = 1 \r\n" + 
    		"AND sdca.flg_attivo = 1 \r\n" + 
    		"AND srata.data_inizio_validita <= DATE(now()) \r\n" + 
    		"and coalesce(srata.data_fine_validita, DATE(now())) >= DATE(now()) \r\n" + 
    		"AND srata.ind_modifica LIKE '%'||:codComponenteApp||'%'  \r\n" + 
    		"AND UPPER(sdad.cod_adempimento) = UPPER(:codAdempimento) \r\n" + 
    		"group by sdca.id_categoria_allegato, sdca.cod_categoria_allegato , sdca.des_categoria_allegato \r\n" + 
    		"ORDER by 11 ";

    private static final String QUERY_LOAD_CATEGORIA_ALLEGATI_MANDATORY_BY_ID_ISTANZA = "SELECT * FROM (\n"
            + QUERY_LOAD_CATEGORIA_ALLEGATI
            + "INNER JOIN scriva_t_istanza stis ON stis.id_adempimento = srata.id_adempimento\n"
            + QUERY_WHERE_CONDITIONS
            + "AND stis.id_istanza = :idIstanza\n"
            + "AND srata.flg_obbligo = 1\n"
            + "AND srata.id_adempimento || '-' || srata.id_tipologia_allegato NOT IN (\n"
            + "SELECT sti.id_adempimento || '-' || stai.id_tipologia_allegato\n"
            + "FROM scriva_t_allegato_istanza stai\n"
            + "INNER JOIN scriva_t_istanza sti ON stai.id_istanza = sti.id_istanza\n"
            + "WHERE sti.id_istanza = :idIstanza\n"
            + "and stai.flg_cancellato = 0\n"
            + ")\n"
            + "UNION\n"
            + "SELECT DISTINCT(sdca.*)\n"
            + "FROM scriva_r_vincolo_tipo_allegato srvta\n"
            + "INNER JOIN scriva_d_tipologia_allegato sdtal ON srvta.id_tipologia_allegato = sdtal.id_tipologia_allegato\n"
            + "INNER JOIN scriva_d_categoria_allegato sdca ON sdtal.id_categoria_allegato = sdca.id_categoria_allegato\n"
            + "INNER JOIN scriva_d_adempimento sdad ON srvta.id_adempimento = sdad.id_adempimento\n"
            + "INNER JOIN scriva_t_istanza stis ON stis.id_adempimento = srvta.id_adempimento\n"
            + "INNER JOIN scriva_r_istanza_vincolo_aut sriva ON (sriva.id_vincolo_autorizza = srvta.id_vincolo_autorizza AND sriva.id_istanza = :idIstanza)\n" //SCRIVA-489
            + "WHERE sdtal.flg_attivo = 1\n"
            + "AND sdca.flg_attivo = 1\n"
            + "AND stis.id_istanza = :idIstanza\n"
            + "AND srvta.flg_obbligo = 1\n"
            + "AND srvta.id_adempimento || '-' || srvta.id_tipologia_allegato NOT IN (\n"
            + "SELECT sti.id_adempimento || '-' || stai.id_tipologia_allegato\n"
            + "FROM scriva_t_allegato_istanza stai\n"
            + "INNER JOIN scriva_t_istanza sti ON stai.id_istanza = sti.id_istanza\n"
            + "WHERE sti.id_istanza = :idIstanza)) AS sdca\n"
            + QUERY_ORDER_BY;

    private static final String QUERY_LOAD_CATEGORIA_ALLEGATI_MANDATORY_ADEMPIMENTO_BY_ID_ISTANZA = "SELECT DISTINCT(sdca.*)\n"
            + "FROM scriva_t_allegato_istanza stai\n"
            + "INNER JOIN scriva_t_istanza sti ON sti.id_istanza = stai.id_istanza\n"
            + "INNER JOIN scriva_d_adempimento sda ON sda.id_adempimento = sti.id_adempimento\n"
            + "INNER JOIN scriva_d_tipologia_allegato sdta ON sdta.id_tipologia_allegato = stai.id_tipologia_allegato\n"
            + "INNER JOIN scriva_d_categoria_allegato sdca ON sdca.id_categoria_allegato = sdta.id_categoria_allegato\n"
            + "FULL OUTER JOIN scriva_r_adempi_tipo_allegato srata ON (stai.id_tipologia_allegato || '-' || sda.id_adempimento) = (srata.id_tipologia_allegato || '-' || srata.id_adempimento)\n"
            + "WHERE sdta.flg_attivo = 1\n"
            + "AND sdca.flg_attivo = 1\n"
            + "AND (srata.id_tipologia_allegato || '-' || srata.id_adempimento) IS NULL\n"
            + "AND sti.id_istanza = :idIstanza\n"
            + QUERY_ORDER_BY;


    @Override
    public List<CategoriaAllegatoDTO> loadTipologieAllegato(String codCategAllegato) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_CATEGORIE_ALLEGATI;

        if (StringUtils.isNotBlank(codCategAllegato)) {
            map.put("codCategAllegato", codCategAllegato);
            query += WHERE_COD_CATEGORIA_ALLEGATO;
        }
        query += QUERY_ORDER_BY;

        return findSimpleDTOListByQuery(className, query, map);
    }

    /**
     * Lista AdempimentiTipoAllegato per idAdempimento
     *
     * @param idAdempimento idAdempimento
     * @return List<AdempimentoTipoAllegatoExtendedDTO>
     */
    @Override
    public List<CategoriaAllegatoDTO> loadCategoriaAllegatoByIdAdempimento(Long idAdempimento, String codComponenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempimento", idAdempimento);
        map.put("codComponenteApp", codComponenteApp);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_CATEGORIA_ALLEGATI_BY_ID_ADEMPIMENTO, map);
    }

    /**
     * Lista AdempimentiTipoAllegato per codAdempimento
     *
     * @param codAdempimento codAdempimento
     * @return List<AdempimentoTipoAllegatoExtendedDTO>
     */
    @Override
    public List<CategoriaAllegatoDTO> loadCategoriaAllegatoByCodeAdempimento(String codAdempimento, String codComponenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codAdempimento", codAdempimento);
        map.put("codComponenteApp", codComponenteApp);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_CATEGORIA_ALLEGATI_BY_CODE_ADEMPIMENTO_REV, map);
    }

    /**
     * Verifica presenza degli allegati obbligatori
     *
     * @param idIstanza idIstanza
     * @return List<CategoriaAllegatoDTO>
     */
    @Override
    public List<CategoriaAllegatoDTO> loadCategoriaAllegatoMandatoryByIdIstanza(Long idIstanza, String codComponenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        map.put("codComponenteApp", codComponenteApp);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_CATEGORIA_ALLEGATI_MANDATORY_BY_ID_ISTANZA, map);
    }

    /**
     * Controllo su allegati assegnati a categorie non legate all'adempimento dell'istanza
     *
     * @param idIstanza idIstanza
     * @return List<CategoriaAllegatoDTO>
     */
    @Override
    public List<CategoriaAllegatoDTO> loadCategoriaAllegatoMandatoryAdempimentoByIdIstanza(Long idIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_CATEGORIA_ALLEGATI_MANDATORY_ADEMPIMENTO_BY_ID_ISTANZA, map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String
     */
    @Override
    public String getPrimaryKeySelect() {
        return null;
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return Rowppar
     */
    @Override
    public RowMapper<CategoriaAllegatoDTO> getRowMapper() throws SQLException {
        return new CategoriaAllegatoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>
     */
    @Override
    public RowMapper<CategoriaAllegatoDTO> getExtendedRowMapper() throws SQLException {
        return new CategoriaAllegatoRowMapper();
    }

    private static class CategoriaAllegatoRowMapper implements RowMapper<CategoriaAllegatoDTO> {

        /**
         * Costruttore
         *
         * @throws SQLException the sql exception
         */
        public CategoriaAllegatoRowMapper() throws SQLException {
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
        public CategoriaAllegatoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            CategoriaAllegatoDTO bean = new CategoriaAllegatoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, CategoriaAllegatoDTO bean) throws SQLException {
            bean.setIdCategoriaAllegato(rs.getLong("id_categoria_allegato"));
            bean.setCodCategoriaAllegato(rs.getString("cod_categoria_allegato"));
            bean.setDesCategoriaAllegato(rs.getString("des_categoria_allegato"));
            bean.setFlgAttivo(1 == rs.getInt("flg_attivo") ? Boolean.TRUE : Boolean.FALSE);
            bean.setOrdinamentoCategoriaAllegato(rs.getInt("ordinamento_categoria_allegato"));
        }
    }
}