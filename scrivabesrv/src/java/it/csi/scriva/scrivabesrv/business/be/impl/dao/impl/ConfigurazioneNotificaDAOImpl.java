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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigurazioneNotificaDAO;
import it.csi.scriva.scrivabesrv.dto.CanaleNotificaDTO;
import it.csi.scriva.scrivabesrv.dto.CompetenzaTerritorioExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ConfigurazioneNotificaDTO;
import it.csi.scriva.scrivabesrv.dto.ConfigurazioneNotificaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.DestinatarioExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoCompetenzaDTO;
import it.csi.scriva.scrivabesrv.dto.TipoNotificaDTO;
import it.csi.scriva.scrivabesrv.dto.TipoNotificaEventoAdempiExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoNotificaEventoExtendedDTO;
import it.csi.scriva.scrivabesrv.util.notification.model.enumeration.IndChannelTypeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The interface Configurazione notifica dao.
 *
 * @author CSI PIEMONTE
 */
public class ConfigurazioneNotificaDAOImpl extends ScrivaBeSrvGenericDAO<ConfigurazioneNotificaDTO> implements ConfigurazioneNotificaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_ATTIVI = "AND (DATE(srnc.data_inizio) <= DATE(NOW()) AND (srnc.data_fine IS NULL OR DATE(srnc.data_fine)>= DATE(NOW())))\n";

    private static final String WHERE_ID_NOTIFICA_CONFIG = "AND srnc.id_notifica_configurazione = :idNotificaConfigurazione\n";

    private static final String WHERE_COD_ADEMPIMENTO = "AND UPPER(sda.cod_adempimento) = UPPER(:codAdempimento)\n";

    private static final String WHERE_COD_TIPO_EVENTO = "AND UPPER(sdte.cod_tipo_evento) = UPPER(:codTipoEvento)\n";

    private static final String WHERE_IND_ESCLUDI_APPLICATIVO = "AND srnc.ind_escludi_applicativo NOT IN (:componenteApp)\n";

    private static final String WHERE_ADEMPIMENTO_BY_ID_ISTANZA = "AND srtnea.id_adempimento = (SELECT id_adempimento FROM scriva_t_istanza WHERE id_istanza = :idIstanza)\n";

    private static final String WHERE_CODE_CANALE_NOTIFICA_LIST = "AND sdcn.cod_canale_notifica IN (:codCanaleNotificaList)\n";

    private static final String WHERE_IND_TIPO_CANALE = "AND UPPER(sdcn.ind_tipo_canale) = UPPER(:indTipoCanale)\n";

    private static final String WHERE_TIPO_NOTIFICA_EV_ADEMPI = "AND srnc.id_tipo_notifica_evento_adempi = srtnea.id_tipo_notifica_evento_adempi\nAND srtnea.data_fine IS NULL\n";

    private static final String INNER_JOIN_NOTIFICA_EVENTO_ADEMPI = "INNER JOIN scriva_r_tipo_notifica_evento_adempi srtnea ON srtnea.id_tipo_notifica_evento = srtne.id_tipo_notifica_evento\n";

    private static final String QUERY_LOAD_NOTIFICA_CONFIG = "SELECT *\n" +
            "FROM _replaceTableName_ srnc \n" +
            "INNER JOIN scriva_d_canale_notifica sdcn ON sdcn.id_canale_notifica = srnc.id_canale_notifica\n" +
            "LEFT JOIN scriva_r_tipo_notifica_evento srtne ON srtne.id_tipo_notifica_evento = srnc.id_tipo_notifica_evento\n" +
            "LEFT JOIN scriva_r_tipo_notifica_evento_adempi srtnea ON srtnea.id_tipo_notifica_evento_adempi = srnc.id_tipo_notifica_evento_adempi\n" +
            "LEFT JOIN scriva_d_adempimento sda ON sda.id_adempimento = srtnea.id_adempimento\n" +
            "LEFT JOIN scriva_d_destinatario sdd ON sdd.id_destinatario = srnc.id_destinatario\n" +
            "LEFT JOIN scriva_t_competenza_territorio stct ON stct.id_competenza_territorio = srnc.id_competenza_territorio\n" +
            "LEFT JOIN scriva_d_tipo_competenza sdtc ON sdtc.id_tipo_competenza = srnc.id_tipo_competenza\n" +
            "WHERE 1 = 1";

    private static final String QUERY_LOAD_NOTIFICA_CONFIG_EXT = "SELECT srnc.*\n" +
            ", sdcn.*\n" +
            ", srtne.*\n" +
            ", sdtn.*\n" +
            ", sdd.*\n" +
            ", sdtd.*\n" +
            ", sdca.*\n" +
            ", sdpa.*\n" +
            ", stct.*\n" +
            ", sdtc.*\n" +
            "FROM scriva_d_tipo_evento sdte\n" +
            "INNER JOIN scriva_r_tipo_notifica_evento srtne ON srtne.id_tipo_evento = sdte.id_tipo_evento \n" +
            "INNER JOIN scriva_d_tipo_notifica sdtn ON sdtn.id_tipo_notifica = srtne.id_tipo_notifica\n" +
            "__dynamic_inner_join_conditions__" +
            "INNER JOIN _replaceTableName_ srnc ON srnc.id_tipo_notifica_evento = srtne.id_tipo_notifica_evento\n" +
            "INNER JOIN scriva_d_canale_notifica sdcn ON sdcn.id_canale_notifica = srnc.id_canale_notifica\n" +
            "INNER JOIN scriva_d_destinatario sdd ON sdd.id_destinatario = srnc.id_destinatario\n" +
            "INNER JOIN scriva_d_tipo_destinatario sdtd ON sdtd.id_tipo_destinatario = sdd.id_tipo_destinatario\n" +
            "LEFT JOIN scriva_d_componente_app sdca ON sdca.id_componente_app = sdd.id_componente_app\n" +
            "LEFT JOIN scriva_d_profilo_app sdpa ON sdpa.id_profilo_app = sdd.id_profilo_app\n" +
            "LEFT JOIN scriva_t_competenza_territorio stct ON stct.id_competenza_territorio = srnc.id_competenza_territorio\n" +
            "LEFT JOIN scriva_d_tipo_competenza sdtc ON sdtc.id_tipo_competenza = stct.id_tipo_competenza\n" +
            //"LEFT JOIN scriva_r_notifica_configur al ON al.id_notifica_configurazione = srnc.id_notifica_configurazione\n" + // NON ESISTE TABELLA
            "WHERE srnc.data_fine IS NULL\n" + // notifica configurazione attiva
            "AND sdtn.data_fine IS NULL\n" + // notifica attiva
            "AND srtne.data_fine IS NULL\n"; // associazione notifica_evento_adempi attiva

    /**
     * Load configurazioni notifica list.
     *
     * @param flgAttivi the flg attivi
     * @return the list
     */
    @Override
    public List<ConfigurazioneNotificaExtendedDTO> loadConfigurazioniNotifica(Boolean flgAttivi) {
        logBegin(className);
        return loadConfigurazioneNotifica(null, null, null, null, null, null, null, flgAttivi);
    }

    /**
     * Load configurazione notifica list.
     *
     * @param idNotificaConfigurazione the id notifica configurazione
     * @param flgAttivi                the flg attivi
     * @return the list
     */
    @Override
    public List<ConfigurazioneNotificaExtendedDTO> loadConfigurazioneNotifica(Long idNotificaConfigurazione, Boolean flgAttivi) {
        logBegin(className);
        return loadConfigurazioneNotifica(null, null, null, null, null, idNotificaConfigurazione, null, flgAttivi);
    }

    /**
     * Load configurazione notifica by cod adempimento list.
     *
     * @param codAdempimento the cod adempimento
     * @param flgAttivi      the flg attivi
     * @return the list
     */
    @Override
    public List<ConfigurazioneNotificaExtendedDTO> loadConfigurazioneNotificaByCodAdempimento(String codAdempimento, Boolean flgAttivi) {
        logBegin(className);
        return loadConfigurazioneNotifica(null, null, null, null, null, null, codAdempimento, flgAttivi);
    }

    /**
     * Load configurazione notifica by code list list.
     *
     * @param idIstanza             the id istanza
     * @param codCanaleNotificaList the cod canale notifica list
     * @param indTipoCanale         the ind tipo canale
     * @param componenteApp         the componente app
     * @param flgAttivi             the flg attivi
     * @return the list
     */
    @Override
    public List<ConfigurazioneNotificaExtendedDTO> loadConfigurazioneNotificaByCodeTipoEvento(String codTipoEvento, Long idIstanza, List<String> codCanaleNotificaList, IndChannelTypeEnum indTipoCanale, String componenteApp, Boolean flgAttivi) {
        logBegin(className);
        return loadConfigurazioneNotifica(codTipoEvento, idIstanza, codCanaleNotificaList, indTipoCanale, componenteApp, null, null, flgAttivi);
    }

    /**
     * Load configurazione notifica list.
     *
     * @param idNotificaConfigurazione the id notifica configurazione
     * @param codAdempimento           the cod adempimento
     * @param flgAttivi                the flg attivi
     * @return the list
     */
    private List<ConfigurazioneNotificaExtendedDTO> loadConfigurazioneNotifica(String codTipoEvento, Long idIstanza, List<String> codCanaleNotificaList, IndChannelTypeEnum indTipoCanale, String componenteApp, Long idNotificaConfigurazione, String codAdempimento, Boolean flgAttivi) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_NOTIFICA_CONFIG_EXT + (Boolean.TRUE.equals(flgAttivi) ? WHERE_ATTIVI : "");
        if (StringUtils.isNotBlank(codTipoEvento)) {
            map.put("codTipoEvento", codTipoEvento);
            query += WHERE_COD_TIPO_EVENTO;
        }
        if (idIstanza != null) {
            map.put("idIstanza", idIstanza);
            query = query.replace("__dynamic_inner_join_conditions__", INNER_JOIN_NOTIFICA_EVENTO_ADEMPI);
            query += WHERE_TIPO_NOTIFICA_EV_ADEMPI;
            query += WHERE_ADEMPIMENTO_BY_ID_ISTANZA;

        } else {
            query = query.replace("__dynamic_inner_join_conditions__", "");
        }

        if (CollectionUtils.isNotEmpty(codCanaleNotificaList)) {
            map.put("codCanaleNotificaList", codCanaleNotificaList);
            query += WHERE_CODE_CANALE_NOTIFICA_LIST;
        }
        if (indTipoCanale != null) {
            map.put("indTipoCanale", indTipoCanale.name());
            query += WHERE_IND_TIPO_CANALE;
        }
        if (StringUtils.isNotBlank(componenteApp)) {
            map.put("componenteApp", componenteApp);
            query += WHERE_IND_ESCLUDI_APPLICATIVO;
        }
        if (idNotificaConfigurazione != null) {
            map.put("idNotificaConfigurazione", idNotificaConfigurazione);
            query += WHERE_ID_NOTIFICA_CONFIG;
        }
        if (StringUtils.isNotBlank(codAdempimento)) {
            map.put("codAdempimento", codAdempimento);
            query += WHERE_COD_ADEMPIMENTO;
        }
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
        return getQuery(QUERY_LOAD_NOTIFICA_CONFIG_EXT + WHERE_ID_NOTIFICA_CONFIG, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<ConfigurazioneNotificaDTO> getRowMapper() throws SQLException {
        return new ConfigurazioneNotificaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<ConfigurazioneNotificaExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new ConfigurazioneNotificaExtendedRowMapper();
    }

    /**
     * The type Configurazione notifica row mapper.
     */
    public static class ConfigurazioneNotificaRowMapper implements RowMapper<ConfigurazioneNotificaDTO> {

        /**
         * Instantiates a new Tipo notifica evento row mapper.
         *
         * @throws SQLException the sql exception
         */
        public ConfigurazioneNotificaRowMapper() throws SQLException {
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
        public ConfigurazioneNotificaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            ConfigurazioneNotificaDTO bean = new ConfigurazioneNotificaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, ConfigurazioneNotificaDTO bean) throws SQLException {
            bean.setIdNotificaConfigurazione(rs.getLong("id_notifica_configurazione"));
            bean.setIdCanaleNotifica(rs.getLong("id_canale_notifica"));
            bean.setIdTipoNotificaEvento(rs.getLong("id_tipo_notifica_evento") > 0 ? rs.getLong("id_tipo_notifica_evento") : null);
            bean.setIdTipoNotificaEventoAdempi(rs.getLong("id_tipo_notifica_evento_adempi") > 0 ? rs.getLong("id_tipo_notifica_evento_adempi") : null);
            bean.setIdDestinatario(rs.getLong("id_destinatario") > 0 ? rs.getLong("id_destinatario") : null);
            bean.setIdCompetenzaTerritorio(rs.getLong("id_competenza_territorio") > 0 ? rs.getLong("id_competenza_territorio") : null);
            bean.setIdTipoCompetenza(rs.getLong("id_tipo_competenza") > 0 ? rs.getLong("id_tipo_competenza") : null);
            bean.setOggettoNotifica(rs.getString("oggetto_notifica"));
            bean.setContenutoNotifica(rs.getString("contenuto_notifica"));
            bean.setDesEmail(rs.getString("des_email"));
            bean.setNumCellulare(rs.getString("num_cellulare"));
            bean.setDesServizioApplicativo(rs.getString("des_servizio_applicativo"));
            bean.setDesPec(rs.getString("des_pec"));
            bean.setDesNotificaCc(rs.getString("des_notifica_cc"));
            bean.setFlgVerificaPreferenzeSogg(rs.getInt("flg_verifica_preferenze_sogg") > 0 ? Boolean.TRUE : Boolean.FALSE);
            bean.setIndEscludiApplicativo(rs.getString("ind_escludi_applicativo"));
            bean.setDataInizio(rs.getDate("data_inizio"));
            bean.setDataFine(rs.getDate("data_fine"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

    /**
     * The type Tipo notifica evento extended row mapper.
     */
    public static class ConfigurazioneNotificaExtendedRowMapper implements RowMapper<ConfigurazioneNotificaExtendedDTO> {

        /**
         * Instantiates a new Tipo notifica evento row mapper.
         *
         * @throws SQLException the sql exception
         */
        public ConfigurazioneNotificaExtendedRowMapper() throws SQLException {
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
        public ConfigurazioneNotificaExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            ConfigurazioneNotificaExtendedDTO bean = new ConfigurazioneNotificaExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, ConfigurazioneNotificaExtendedDTO bean) throws SQLException {
            bean.setIdNotificaConfigurazione(rs.getLong("id_notifica_configurazione"));

            CanaleNotificaDTO canaleNotifica = new CanaleNotificaDTO();
            new CanaleNotificaDAOImpl.CanaleNotificaRowMapper().populateBean(rs, canaleNotifica);
            bean.setCanaleNotifica(canaleNotifica);

            if (rs.getLong("id_tipo_notifica_evento") > 0) {
                TipoNotificaEventoExtendedDTO tipoNotificaEvento = new TipoNotificaEventoExtendedDTO();
                populateTipoNotificaEventoBean(rs, tipoNotificaEvento);
                bean.setTipoNotificaEvento(tipoNotificaEvento);
            }

            if (rs.getLong("id_tipo_notifica_evento_adempi") > 0) {
                TipoNotificaEventoAdempiExtendedDTO tipoNotificaEventoAdempi = new TipoNotificaEventoAdempiExtendedDTO();
                //populateTipoNotificaEventoAdempiBean(rs, tipoNotificaEventoAdempi);
                bean.setTipoNotificaEventoAdempi(tipoNotificaEventoAdempi);
            }

            if (rs.getLong("id_destinatario") > 0) {
                DestinatarioExtendedDTO destinatario = new DestinatarioExtendedDTO();
                new DestinatarioDAOImpl.DestinatarioExtendedRowMapper().populateBean(rs, destinatario);
                bean.setDestinatario(destinatario);
            }

            if (rs.getLong("id_competenza_territorio") > 0) {
                CompetenzaTerritorioExtendedDTO competenzaTerritorio = new CompetenzaTerritorioExtendedDTO();
                populateCompetenzaTerritorioBean(rs, competenzaTerritorio);
                bean.setCompetenzaTerritorio(competenzaTerritorio);
                bean.setIdCompetenzaTerritorio(rs.getLong("id_competenza_territorio"));
            }

            if (rs.getLong("id_tipo_competenza") > 0) {
                TipoCompetenzaDTO tipoCompetenza = new TipoCompetenzaDTO();
                //new TipoCompetenzaDAOImpl.TipoCompetenzaRowMapper().populateBean(rs, tipoCompetenza);
                populateTipoCompetenzaBean(rs, tipoCompetenza);
                bean.setTipoCompetenza(tipoCompetenza);
                bean.setIdTipoCompetenza(rs.getLong("id_tipo_competenza"));
            }

            bean.setOggettoNotifica(rs.getString("oggetto_notifica"));
            bean.setContenutoNotifica(rs.getString("contenuto_notifica"));
            bean.setDesEmail(rs.getString("des_email"));
            bean.setNumCellulare(rs.getString("num_cellulare"));
            bean.setDesServizioApplicativo(rs.getString("des_servizio_applicativo"));
            bean.setDesPec(rs.getString("des_pec"));
            bean.setDesNotificaCc(rs.getString("des_notifica_cc"));
            bean.setFlgVerificaPreferenzeSogg(rs.getInt("flg_verifica_preferenze_sogg") > 0 ? Boolean.TRUE : Boolean.FALSE);
            bean.setIndEscludiApplicativo(rs.getString("ind_escludi_applicativo"));
            //bean.setDataInizio(rs.getDate("data_inizio_nc"));
            //bean.setDataFine(rs.getDate("data_fine_nc"));
        }

        
        private void populateTipoNotificaEventoBean(ResultSet rs, TipoNotificaEventoExtendedDTO bean) throws SQLException {
            bean.setIdTipoNotificaEvento(rs.getLong("id_tipo_notifica_evento"));

            TipoNotificaDTO tipoNotifica = new TipoNotificaDTO();
            populateTipoNotificaBean(rs, tipoNotifica);
            bean.setTipoNotifica(tipoNotifica);
            bean.setIdTipoEvento(rs.getLong("id_tipo_evento"));
        }

        private void populateTipoNotificaBean(ResultSet rs, TipoNotificaDTO bean) throws SQLException {
            bean.setIdTipoNotifica(rs.getLong("id_tipo_notifica"));
            bean.setCodTipoNotifica(rs.getString("cod_tipo_notifica"));
            bean.setDesTipoNotifica(rs.getString("des_tipo_notifica"));
        }

        private void populateCompetenzaTerritorioBean(ResultSet rs, CompetenzaTerritorioExtendedDTO bean) throws SQLException {
            bean.setIdCompetenzaTerritorio(rs.getLong("id_competenza_territorio"));

            TipoCompetenzaDTO tipoCompetenza = new TipoCompetenzaDTO();
            populateTipoCompetenzaBean(rs, tipoCompetenza);
            bean.setTipoCompetenza(tipoCompetenza);

            bean.setIdEnteCreditore(rs.getLong("id_ente_creditore"));
            bean.setIdComuneCompetenza(rs.getLong("id_comune_competenza"));
            bean.setCodCompetenzaTerritorio(rs.getString("cod_competenza_territorio"));
            bean.setDesCompetenzaTerritorio(rs.getString("des_competenza_territorio"));
            bean.setDesCompetenzaTerritorioEstesa(rs.getString("des_competenza_territorio_estesa"));
            bean.setCodSuap(rs.getString("cod_suap"));
            bean.setIndirizzoCompetenza(rs.getString("indirizzo_competenza"));
            bean.setNumCivicoIndirizzo(rs.getString("num_civico_indirizzo"));
            bean.setCapCompetenza(rs.getString("cap_competenza"));
            bean.setDesEmailPec(rs.getString("des_pec"));
            bean.setDesEmail(rs.getString("des_email"));
            bean.setOrario(rs.getString("orario"));
            bean.setSitoWeb(rs.getString("sito_web"));
            //bean.setResponsabile(rs.getString("responsabile"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita"));
            bean.setIndVisibile(rs.getString("ind_visibile"));
            bean.setCodIpa(rs.getString("cod_ipa"));
            bean.setNumCellulare(rs.getString("num_cellulare"));
            bean.setDesServizioApplicativo(rs.getString("des_servizio_applicativo"));
            bean.setNumDimensionePec(rs.getString("num_dimensione_pec"));
            bean.setGestUID(rs.getString("gest_uid"));
        }

        private void populateTipoCompetenzaBean(ResultSet rs, TipoCompetenzaDTO bean) throws SQLException {
            bean.setIdTipoCompetenza(rs.getLong("id_tipo_competenza"));
            bean.setCodTipoCompetenza(rs.getString("cod_tipo_competenza"));
            bean.setDesTipoCompetenza(rs.getString("des_tipo_competenza"));
        }

    }

}