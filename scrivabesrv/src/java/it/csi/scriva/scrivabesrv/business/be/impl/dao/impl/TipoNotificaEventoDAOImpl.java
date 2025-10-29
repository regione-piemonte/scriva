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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoNotificaEventoDAO;
import it.csi.scriva.scrivabesrv.dto.TipoEventoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoNotificaDTO;
import it.csi.scriva.scrivabesrv.dto.TipoNotificaEventoDTO;
import it.csi.scriva.scrivabesrv.dto.TipoNotificaEventoExtendedDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Tipo notifica evento dao.
 */
public class TipoNotificaEventoDAOImpl extends ScrivaBeSrvGenericDAO<TipoNotificaEventoDTO> implements TipoNotificaEventoDAO {

    private final String className = this.getClass().getSimpleName();
    													  
    private static final String WHERE_ATTIVI = "AND (DATE(srtne.data_inizio) <= DATE(NOW()) AND (srtne.data_fine IS NULL OR DATE(srtne.data_fine)>= DATE(NOW())))\n" +
            								   "AND (DATE(sdtn.data_inizio) <= DATE(NOW()) AND (sdtn.data_fine IS NULL OR DATE(sdtn.data_fine)>= DATE(NOW())))\n";

    private static final String WHERE_ID_TIPO_NOTIFICA_EVENTO = "AND srtne.id_tipo_notifica_evento = :idTipoNotificaEvento\n";

    private static final String WHERE_COD_TIPO_NOTIFICA = "AND UPPER(sdtn.cod_tipo_notifica) = UPPER(:codTipoNotifica)\n";

    private static final String WHERE_COD_TIPO_EVENTO = "AND UPPER(sdte.cod_tipo_evento) = UPPER(:codTipoEvento)\n";

   //  private static final String WHERE_COD_ADEMPIMENTO = "AND UPPER(sda.cod_adempimento) = UPPER(:codAdempimento)\n";

   //  private static final String WHERE_ID_ISTANZA = "AND sti.id_istanza = :idIstanza\n";
/*
    private static final String QUERY_LOAD_TIPI_NOTIFICA_EVENTO = "SELECT srtne.*\n" +
            ", sdtn.cod_tipo_notifica, sdtn.des_tipo_notifica, sdtn.data_inizio AS data_inizio_tn, sdtn.data_fine AS data_fine_tn\n" +
            ", sdte.cod_tipo_evento, sdte.id_stato_istanza_evento, sdte.cod_tipo_evento, sdte.des_tipo_evento, sdte.ind_visibile, sdte.id_componente_gestore_processo, sdte.flg_genera_ricevuta\n" +
            "FROM _replaceTableName_ srtne\n" + //scriva_r_tipo_notifica_evento
            "LEFT JOIN scriva_r_tipo_notifica_evento_adempi srtnea ON srtnea.id_tipo_notifica_evento = srtne.id_tipo_notifica_evento\n" +
            "LEFT JOIN scriva_d_adempimento sda ON sda.id_adempimento = srtnea.id_adempimento\n" +
            "LEFT JOIN scriva_t_istanza sti ON sti.id_adempimento = srtnea.id_adempimento\n" +
            "INNER JOIN scriva_d_tipo_notifica sdtn ON sdtn.id_tipo_notifica = srtne.id_tipo_notifica\n" +
            "INNER JOIN scriva_d_tipo_evento sdte ON sdte.id_tipo_evento = srtne.id_tipo_evento\n" +
            "WHERE 1 = 1";
   */
    private static final  String QUERY_LOAD_TIPI_NOTIFICA_EVENTO ="select\r\n"
    		+ "sdtn.*,\r\n"
    		+ "sdte.*,\r\n"
    		+ "sdsi.*,\r\n"
    		+ "srtne.*\r\n"
    		+ "FROM _replaceTableName_ sdte\r\n"
    		+ "INNER JOIN scriva_r_tipo_notifica_evento srtne ON srtne.id_tipo_evento = sdte.id_tipo_evento \r\n"
    		+ "INNER JOIN scriva_d_tipo_notifica sdtn on sdtn.id_tipo_notifica = srtne.id_tipo_notifica\r\n"
    		+ "LEFT JOIN scriva_d_stato_istanza sdsi ON sdsi.id_stato_istanza = sdte.id_stato_istanza_evento \r\n"
    		+ "WHERE 1 = 1";

    		

    /**
     * Load tipi messaggio list.
     *
     * @param flgAttivi the flg attivi
     * @return List<TipoNotificaDTO>         list
     */
    @Override
    public List<TipoNotificaEventoExtendedDTO> loadTipiNotificaEvento(Boolean flgAttivi) {
        logBegin(className);
        return loadTipoNotificaEvento(null, null, null, null, null, flgAttivi);
    }

    /**
     * Load tipo messaggio list.
     *
     * @param idTipoNotificaEvento the id tipo notifica evento
     * @return List<TipoNotificaDTO>      list
     */
    @Override
    public List<TipoNotificaEventoExtendedDTO> loadTipoNotificaEvento(Long idTipoNotificaEvento) {
        logBegin(className);
        return loadTipoNotificaEvento(idTipoNotificaEvento, null, null, null, null, null);
    }

    /**
     * Load tipo messaggio by code list.
     *
     * @param codTipoNotifica the cod tipo notifica
     * @param codTipoEvento   the cod tipo evento
     * @return List<TipoNotificaDTO>      list
     */
    @Override
    public List<TipoNotificaEventoExtendedDTO> loadTipoNotificaEvento(String codTipoNotifica, String codTipoEvento) {
        logBegin(className);
        return loadTipoNotificaEvento(null, codTipoNotifica, codTipoEvento, null, null, null);
    }
    
    @Override
    public List<TipoNotificaEventoExtendedDTO> loadTipoNotificaEvento( String codTipoEvento) {
        logBegin(className);
        return loadTipoNotificaEvento(null, null, codTipoEvento, null, null, true);
    }

    /**
     * Load tipo notifica evento by cod adempimento list.
     *
     * @param codAdempimento the cod adempimento
     * @param flgAttivi      the flg attivi
     * @return the list
     */
    @Override
    public List<TipoNotificaEventoExtendedDTO> loadTipoNotificaEventoByCodAdempimento(String codAdempimento, Boolean flgAttivi) {
        logBegin(className);
        return loadTipoNotificaEvento(null, null, null, codAdempimento, null, flgAttivi);
    }

    /**
     * Load tipo notifica evento by id istanza list.
     *
     * @param idIstanza the id istanza
     * @param flgAttivi the flg attivi
     * @return the list
     */
    @Override
    public List<TipoNotificaEventoExtendedDTO> loadTipoNotificaEventoByIdIstanza(Long idIstanza, Boolean flgAttivi) {
        logBegin(className);
        return loadTipoNotificaEvento(null, null, null, null, idIstanza, flgAttivi);
    }

    /**
     * Load tipo notifica evento list.
     *
     * @param idTipoNotificaEvento the id tipo notifica evento
     * @param codTipoNotifica      the cod tipo notifica
     * @param codTipoEvento        the cod tipo evento
     * @param codAdempimento       the cod adempimento
     * @param idIstanza            the id istanza
     * @param flgAttivi            the flg attivi
     * @return the list
     */
    private List<TipoNotificaEventoExtendedDTO> loadTipoNotificaEvento(Long idTipoNotificaEvento, String codTipoNotifica, String codTipoEvento, String codAdempimento, Long idIstanza, Boolean flgAttivi) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_TIPI_NOTIFICA_EVENTO + (Boolean.TRUE.equals(flgAttivi) ? WHERE_ATTIVI : "");
        if (idTipoNotificaEvento != null) {
            map.put("idTipoNotificaEvento", idTipoNotificaEvento);
            query += WHERE_ID_TIPO_NOTIFICA_EVENTO;
        }
        if (StringUtils.isNotBlank(codTipoNotifica)) {
            map.put("codTipoNotifica", codTipoNotifica);
            query += WHERE_COD_TIPO_NOTIFICA;
        }
        if (StringUtils.isNotBlank(codTipoEvento)) {
            map.put("codTipoEvento", codTipoEvento);
            query += WHERE_COD_TIPO_EVENTO;
        }
      /*  if (StringUtils.isNotBlank(codAdempimento)) {
            map.put("codAdempimento", codAdempimento);
            query += WHERE_COD_ADEMPIMENTO;
        }
        if (idIstanza != null) {
            map.put("idIstanza", idIstanza);
            query += WHERE_ID_ISTANZA;
        }*/
        logEnd(className);
        return findListByQuery(className, query, map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_LOAD_TIPI_NOTIFICA_EVENTO + WHERE_ID_TIPO_NOTIFICA_EVENTO, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<TipoNotificaEventoDTO> getRowMapper() throws SQLException {
        return new TipoNotificaEventoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<TipoNotificaEventoExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new TipoNotificaEventoExtendedRowMapper();
    }


    /**
     * The type Tipo notifica evento row mapper.
     */
    public static class TipoNotificaEventoRowMapper implements RowMapper<TipoNotificaEventoDTO> {

        /**
         * Instantiates a new Tipo notifica evento row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TipoNotificaEventoRowMapper() throws SQLException {
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
        public TipoNotificaEventoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoNotificaEventoDTO bean = new TipoNotificaEventoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TipoNotificaEventoDTO bean) throws SQLException {
            bean.setIdTipoNotificaEvento(rs.getLong("id_tipo_notifica_evento"));
            bean.setIdTipoNotifica(rs.getLong("id_tipo_notifica"));
            bean.setIdTipoEvento(rs.getLong("id_tipo_evento"));
            bean.setDataInizio(rs.getDate("data_inizio"));
            bean.setDataFine(rs.getDate("data_fine"));
        }
    }

    /**
     * The type Tipo notifica evento extended row mapper.
     */
    public static class TipoNotificaEventoExtendedRowMapper implements RowMapper<TipoNotificaEventoExtendedDTO> {

        /**
         * Instantiates a new Tipo notifica evento row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TipoNotificaEventoExtendedRowMapper() throws SQLException {
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
        public TipoNotificaEventoExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoNotificaEventoExtendedDTO bean = new TipoNotificaEventoExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        /**
         * Populate bean.
         *
         * @param rs   the rs
         * @param bean the bean
         * @throws SQLException the sql exception
         */
        public void populateBean(ResultSet rs, TipoNotificaEventoExtendedDTO bean) throws SQLException {
            bean.setIdTipoNotificaEvento(rs.getLong("id_tipo_notifica_evento"));

            TipoNotificaDTO tipoNotifica = new TipoNotificaDTO();
            populateTipoNotificaBean(rs, tipoNotifica);
            bean.setTipoNotifica(tipoNotifica);

            TipoEventoExtendedDTO tipoEvento = new TipoEventoExtendedDTO();
            populateTipoEventoBean(rs, tipoEvento);
            bean.setTipoEvento(tipoEvento);

            //bean.setDataInizio(rs.getDate("data_inizio"));
            //bean.setDataFine(rs.getDate("data_fine"));
        }

        private void populateTipoNotificaBean(ResultSet rs, TipoNotificaDTO bean) throws SQLException {
            bean.setIdTipoNotifica(rs.getLong("id_tipo_notifica"));
            bean.setCodTipoNotifica(rs.getString("cod_tipo_notifica"));
            bean.setDesTipoNotifica(rs.getString("des_tipo_notifica"));
            //bean.setDataInizio(rs.getDate("data_inizio"));
            //bean.setDataFine(rs.getDate("data_fine"));
        }

        private void populateTipoEventoBean(ResultSet rs, TipoEventoExtendedDTO bean) throws SQLException {
            bean.setIdTipoEvento(rs.getLong("id_tipo_evento"));
            bean.setIdStatoIstanzaEvento(rs.getLong("id_stato_istanza_evento"));
            bean.setCodTipoEvento(rs.getString("cod_tipo_evento"));
            bean.setDesTipoEvento(rs.getString("des_tipo_evento"));
            bean.setIndVisibile(rs.getString("ind_visibile"));
            bean.setIdComponenteGestoreProcesso(rs.getLong("id_componente_gestore_processo"));
            bean.setFlgGeneraRicevuta(rs.getInt("flg_genera_ricevuta") == 1 ? Boolean.TRUE : Boolean.FALSE);
        }

    }

}