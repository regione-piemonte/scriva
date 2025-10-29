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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.AllegatoIstanzaDAO;
import it.csi.scriva.scrivabesrv.dto.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.CollectionUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

/**
 * The type Allegato istanza dao.
 *
 * @author CSI PIEMONTE
 */
public class AllegatoIstanzaDAOImpl extends ScrivaBeSrvGenericDAO<AllegatoIstanzaDTO> implements AllegatoIstanzaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_PRIMARY_KEY = "SELECT * FROM _replaceTableName_ WHERE id_allegato_istanza = :id";

    private static final String QUERY_LOAD_ALLEGATI_ISTANZA = "SELECT stai.*, \n" +
            "sdtal.*, sdtal.id_tipologia_allegato AS tipologia_allegato_id, sdtal.flg_attivo AS tipologia_allegato_attivo, \n" +
            "sdca.*, sdca.id_categoria_allegato AS categoria_allegato_id, sdca.flg_attivo AS categoria_allegato_attivo, \n" +
            "sdtia.*, sdtia.id_tipo_integrazione AS tipo_integra_allegato_id, \n" +
            "sdcla.*, sdcla.id_classe_allegato AS classe_allegato_id, sdcla.flg_attivo AS flg_attivo_classe_allegato, \n" +
            "sdso.cod_stato_osservazione \n" +
            "FROM _replaceTableName_ stai \n" +
            "INNER JOIN scriva_t_istanza sti ON sti.id_istanza = stai.id_istanza \n" +
            "INNER JOIN scriva_d_tipologia_allegato sdtal ON stai.id_tipologia_allegato = sdtal.id_tipologia_allegato \n" +
            "INNER JOIN scriva_d_categoria_allegato sdca ON sdtal.id_categoria_allegato = sdca.id_categoria_allegato \n" +
            "INNER JOIN scriva_r_adempi_tipo_allegato srata ON srata.id_tipologia_allegato = sdtal.id_tipologia_allegato \n" +
            "LEFT JOIN scriva_d_tipo_integrazione sdtia ON stai.id_tipo_integrazione = sdtia.id_tipo_integrazione \n" +
            "LEFT JOIN scriva_d_classe_allegato sdcla ON sdcla.id_classe_allegato = stai.id_classe_allegato \n" +
            "LEFT JOIN scriva_r_istanza_osservazione srio ON srio.id_istanza_osservazione = stai.id_istanza_osservazione\n" +
            "LEFT JOIN scriva_d_stato_osservazione sdso ON sdso.id_stato_osservazione = srio.id_stato_osservazione\n" +
            "" +
            "WHERE sti.id_adempimento = srata.id_adempimento \n";

    private static final String ORDER_BY_CLAUSE = "ORDER BY stai.data_upload,  sdca.cod_categoria_allegato, sdtal.cod_tipologia_allegato, sdcla.ordinamento_classe_allegato DESC";

    private static final String ORDER_BY_CLAUSE_ID_PADRE = "ORDER BY stai.id_allegato_istanza_padre DESC";

    private static final String QUERY_LOAD_ALLEGATI_ISTANZA_BY_ID = QUERY_LOAD_ALLEGATI_ISTANZA
            + "AND stai.id_allegato_istanza = :id \n";

    private static final String WHERE_IND_VISIBILE = "AND srata.ind_visibile LIKE '%'||:codComponenteApp||'%' \n";

    private static final String WHERE_COD_ALLEGATO = "AND stai.cod_allegato = :codAllegato\n";

    private static final String WHERE_COD_CLASSE_ALLEGATO = "AND sdcla.cod_classe_allegato = :codClasseAllegato\n";

    private static final String WHERE_COD_CATEGORIA_ALLEGATO = "AND sdca.cod_categoria_allegato = :codCategoriaAllegato\n";

    private static final String WHERE_COD_TIPOLOGIA_ALLEGATO = "AND sdtal.cod_tipologia_allegato = :codTipologiaAllegato\n";

    private static final String WHERE_FLG_CANC_LOGICA = "AND stai.flg_cancellato IN (:flgCancLogica)\n";

    private static final String WHERE_ID_ISTANZA = "AND stai.id_istanza = :idIstanza\n";

    private static final String WHERE_ID_ISTANZA_OSSERVAZIONE = " AND stai.id_istanza_osservazione = :idIstanzaOsservazione \n";

    private static final String WHERE_DATA_PUBBLICAZIONE_NOT_NULL = " AND stai.data_pubblicazione IS NOT NULL\n" +
            "AND stai.data_pubblicazione <= now()\n"; //JIRA-860

    private static final String WHERE_DATA_INVIO_NULL = "AND stai.data_invio_esterno IS NULL\n";

    private static final String WHERE_TIPOLOGIA_ALLEG_SISTEMA = "AND ((stai.flg_cancellato = 1 and sdtal.flg_sistema <> 1) or (stai.flg_cancellato = 0))\n";
    
    //private static final String WHERE_DATA_CANCELLAZIONE_NOT_NULL = "AND stai.data_cancellazione IS NOT NULL\n";

    private static final String QUERY_LOAD_ALLEGATI_ISTANZA_BY_ID_ISTANZA = QUERY_LOAD_ALLEGATI_ISTANZA + WHERE_ID_ISTANZA;

    private static final String QUERY_LOAD_ALLEGATI_ISTANZA_BY_ID_ISTANZA_AND_COD_TIPO_ALLEGATO = QUERY_LOAD_ALLEGATI_ISTANZA_BY_ID_ISTANZA
            + WHERE_COD_TIPOLOGIA_ALLEGATO;

    private static final String QUERY_LOAD_ALLEGATI_ISTANZA_BY_ID_ISTANZA_AND_NOME_ALLEGATO = QUERY_LOAD_ALLEGATI_ISTANZA_BY_ID_ISTANZA
            + "AND stai.nome_allegato = :nomeAllegato \n";

    private static final String QUERY_LOAD_ALLEGATI_ISTANZA_BY_UUID_INDEX = QUERY_LOAD_ALLEGATI_ISTANZA
            + "AND stai.uuid_index = :uuidIndex \n";

    private static final String QUERY_LOAD_ALLEGATI_ISTANZA_BY_ID_ALLEGATO_ISTANZA_PADRE = QUERY_LOAD_ALLEGATI_ISTANZA
            + "AND stai.id_allegato_istanza_padre = :idAllegatoIstanzaPadre\n";

    private static final String COD_ALLEGATO_CALC = ":codAllegato || '-' || nextval('seq_scriva_cod_allegato')";

    private static final String QUERY_INSERT_ALLEGATO_ISTANZA = "INSERT INTO _replaceTableName_ \n"
            + "(id_allegato_istanza, id_istanza, id_tipologia_allegato, id_tipo_integrazione, uuid_index, flg_riservato, \n"
            + "cod_allegato, nome_allegato, dimensione_upload, data_upload, data_integrazione, data_cancellazione, flg_cancellato, \n"
            + "ind_firma, note, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid, \n"
            + "id_istanza_attore, id_funzionario, id_classe_allegato, id_allegato_istanza_padre, flg_da_pubblicare, data_pubblicazione, \n"
            + "num_protocollo_allegato, data_protocollo_allegato, url_doc, id_istanza_osservazione,\n"
            + "num_atto, data_atto, titolo_allegato, autore_allegato, data_invio_esterno) \n"
            + "VALUES(nextval('seq_scriva_t_allegato_istanza'), :idIstanza, :idTipologiaAllegato, :idTipoIntegraAllegato, :uuidIndex, :flgRiservato,\n"
            + "__codAllegato__,\n"
            + ":nomeAllegato, :dimensioneUpload, :dataUpload, :dataIntegrazione, :dataCancellazione, :flgCancellato, \n"
            + ":indFirma, :note, :gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid, \n"
            + ":idIstanzaAttore, :idFunzionario, :idClasseAllegato, :idAllegatoIstanzaPadre, :flgDaPubblicare, :dataPubblicazione, \n"
            + ":numProtocolloAllegato, :dataProtocolloAllegato, :urlDoc, :idIstanzaOsservazione,\n"
            + ":numAtto, :dataAtto, :titoloAllegato, :autoreAllegato, :dataInvioEsterno)";


    public static final String UPDATE_TABLE_NAME = "UPDATE _replaceTableName_ \n";
    private static final String QUERY_UPDATE_ALLEGATO_ISTANZA = UPDATE_TABLE_NAME
            + "SET id_istanza=:idIstanza, id_tipologia_allegato=:idTipologiaAllegato, id_tipo_integrazione=:idTipoIntegraAllegato, uuid_index=:uuidIndex, \n"
            + "flg_riservato=:flgRiservato, cod_allegato=:codAllegato, nome_allegato=:nomeAllegato, dimensione_upload=:dimensioneUpload, \n"
            + "data_upload=:dataUpload, data_integrazione=:dataIntegrazione, data_cancellazione=:dataCancellazione, flg_cancellato=:flgCancellato, \n"
            + "ind_firma=:indFirma, note=:note, gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd, id_istanza_attore=:idIstanzaAttore, \n"
            + "id_funzionario=:idFunzionario, id_classe_allegato=:idClasseAllegato, id_allegato_istanza_padre=:idAllegatoIstanzaPadre, flg_da_pubblicare=:flgDaPubblicare, \n"
            + "data_pubblicazione=:dataPubblicazione, num_protocollo_allegato=:numProtocolloAllegato, data_protocollo_allegato=:dataProtocolloAllegato, url_doc=:urlDoc, id_istanza_osservazione=:idIstanzaOsservazione,\n"
            + "num_atto=:numAtto, data_atto=:dataAtto, titolo_allegato=:titoloAllegato, autore_allegato=:autoreAllegato, data_invio_esterno=:dataInvioEsterno\n"
            + "WHERE id_allegato_istanza=:idAllegatoIstanza";

    private static final String QUERY_DELETE_ALLEGATO_ISTANZA_BY_UUID_INDEX = "DELETE FROM _replaceTableName_ WHERE uuid_index = :uuidIndex";

    private static final String QUERY_UPDATE_DATA_INVIO_ESTERNO = UPDATE_TABLE_NAME +
            "SET data_invio_esterno = :dataInvioEsterno \n" +
            "WHERE id_allegato_istanza IN (:idAllegatoIstanzaList)";

    private static final String QUERY_UPDATE_ID_ALLEGATO_PADRE = UPDATE_TABLE_NAME +
            "SET id_allegato_istanza_padre = :idAllegatoIstanzaPadre \n" +
            "WHERE id_istanza = :idIstanza \n" +
            "AND id_allegato_istanza != :idAllegatoIstanzaPadre\n" +
            "AND data_invio_esterno IS NULL";

    private static final String QUERY_UPDATE_ID_ALLEGATO_PADRE_INTEG = UPDATE_TABLE_NAME +
            "SET id_allegato_istanza_padre = :idAllegatoIstanzaPadre\n" +
            "WHERE id_istanza = :idIstanza\n" +
            "AND id_allegato_istanza != :idAllegatoIstanzaPadre\n" +
            "AND id_allegato_istanza IN (\n" +
            "    SELECT id_allegato_istanza\n" +
            "    FROM scriva_r_integra_istanza_allegato\n" +
            "    WHERE id_integrazione_istanza IN (\n" +
            "        SELECT MAX(stii.id_integrazione_istanza)\n" +
            "        FROM scriva_t_integrazione_istanza stii\n" +
            "        INNER JOIN scriva_r_integra_istanza_allegato sriia ON sriia.id_integrazione_istanza = stii.id_integrazione_istanza\n" +
            "        WHERE id_istanza = :idIstanza\n" +
            "    )\n" +
            ")";

    private static final String QUERY_UPDATE_ID_ALLEGATO_PADRE_ELENCO_ALL = UPDATE_TABLE_NAME +
            "SET id_allegato_istanza_padre = :idAllegatoIstanzaPadre\n" +
            "WHERE id_istanza = :idIstanza\n" +
            "AND id_allegato_istanza != :idAllegatoIstanzaPadre\n" +
            "AND id_allegato_istanza IN (\n" +
            "    SELECT stai.id_allegato_istanza\n" +
            "    FROM scriva_t_allegato_istanza stai\n" +
            "    INNER JOIN scriva_d_tipologia_allegato sdta ON sdta.id_tipologia_allegato = stai.id_tipologia_allegato\n" +
            "    INNER JOIN scriva_d_categoria_allegato sdca ON sdca.id_categoria_allegato = sdta.id_categoria_allegato\n" +
            "    INNER JOIN scriva_d_classe_allegato sdcla ON sdcla.id_classe_allegato = stai.id_classe_allegato\n" +
            "    WHERE stai.id_istanza = :idIstanza\n" +
            "    AND stai.gest_attore_ins <> 'SYSTEM'\n" +
            "    AND sdcla.cod_classe_allegato = 'ELAB_PROGETTUALI'\n" +
            "    AND cod_categoria_allegato NOT IN ('SYS', 'INTEG')\n" +
            ")";

    private static final String QUERY_DELETE_ID_ALLEGATO_PADRE = UPDATE_TABLE_NAME +
            "SET id_allegato_istanza_padre = NULL \n" +
            "WHERE id_allegato_istanza_padre = :idAllegatoIstanzaPadre";

    private static final String QUERY_UPDATE_DATA_PUBBLICAZIONE = UPDATE_TABLE_NAME +
            "SET data_pubblicazione = :dataPubblicazione, gest_data_upd = :dateUpd, gest_attore_upd = :attoreUpd\n" +
            "WHERE id_istanza = :idIstanza\n" +
            "AND flg_da_pubblicare = 1\n" +
            "AND flg_cancellato = 0\n" +
            "AND data_pubblicazione IS NULL\n";

    private static final String QUERY_UPDATE_PADRE_FROM_FIGLIO = UPDATE_TABLE_NAME +
            "SET id_tipo_integrazione = son.id_tipo_integrazione,\n" +
            "data_integrazione = son.data_integrazione \n" +
            "FROM (\n" +
            "    SELECT id_tipo_integrazione, data_integrazione\n" +
            "    FROM scriva_t_allegato_istanza\n" +
            "    WHERE id_allegato_istanza_padre = :idAllegatoIstanzaPadre\n" +
            "    AND id_tipo_integrazione IS NOT NULL\n" +
            "    ORDER BY data_upload desc  \n"+
            "    LIMIT 1\n" +
            ") AS son\n" +
            "WHERE id_allegato_istanza = :idAllegatoIstanzaPadre";

    private static final String QUERY_UPDATE_PADRE_FROM_MOD_OSS = "UPDATE _replaceTableName_ stai\n" +
            "SET gest_data_upd = now(), gest_attore_upd = :attoreUpd,\n" +
            "id_allegato_istanza_padre = allegato_padre.id_allegato_istanza\n" +
            "FROM (\n" +
            "        SELECT stai2.*\n" +
            "        FROM scriva_t_allegato_istanza stai2\n" +
            "        INNER JOIN scriva_d_tipologia_allegato sdta ON sdta.id_tipologia_allegato = stai2.id_tipologia_allegato\n" +
            "        WHERE sdta.cod_tipologia_allegato = 'MOD_OSS' \n" +
            "        AND id_istanza_osservazione = :idAllegatoIstanzaOsservazione\n" +
            "        AND data_cancellazione IS NULL\n" +
            "        ORDER BY stai2.gest_data_ins ASC\n" +
            "        LIMIT 1\n" +
            ") allegato_padre\n" +
            "WHERE stai.id_istanza_osservazione = :idAllegatoIstanzaOsservazione\n" +
            "AND stai.id_allegato_istanza != allegato_padre.id_allegato_istanza\n" +
            "AND stai.id_allegato_istanza_padre IS NULL\n" +
            "AND stai.data_cancellazione IS NULL;";

    private static final String SET_UPDATE_PROTOC = "UPDATE _replaceTableName_ stai\n" +
            "SET data_protocollo_allegato = :dataProtocollo\n" +
            ", num_protocollo_allegato = :numProtocollo\n" +
            ", gest_attore_upd = :attoreUpd\n" +
            ", gest_data_upd = :dateUpd\n" +
            "WHERE stai.data_cancellazione IS NULL\n";

    private static final String QUERY_UPDATE_DATI_INTEGRAZIONE = SET_UPDATE_PROTOC +
            "AND stai.id_allegato_istanza_padre = :idAllegatoIstanzaPadre\n" +
            "AND stai.data_integrazione = (SELECT data_integrazione FROM scriva_t_allegato_istanza WHERE id_allegato_istanza = :idAllegatoIstanzaPadre)\n" +
            "AND stai.id_classe_allegato = (\n" +
            "    SELECT sdcla.id_classe_allegato\n" +
            "    FROM scriva_d_classe_allegato sdcla\n" +
            "    WHERE sdcla.cod_classe_allegato = 'INTEGRAZIONI'\n" +
            ")";

    private static final String QUERY_UPDATE_PROTOC_MODULI = SET_UPDATE_PROTOC +
            "AND id_tipologia_allegato IN\n" +
            "    (SELECT id_tipologia_allegato FROM scriva_d_tipologia_allegato sdta WHERE sdta.cod_tipologia_allegato IN ('MOD_IST', 'ELENCO_ALLEGATI','LETT_TRASM'))\n" +
            "AND id_classe_allegato =\n" +
            "    (SELECT id_classe_allegato FROM scriva_d_classe_allegato sdca WHERE cod_classe_allegato = 'ELAB_PROGETTUALI')\n" +
            "AND data_cancellazione IS NULL\n" +
            "AND id_istanza = :idIstanza\n";
    
    private static final String QUERY_UPDATE_PROTOC_MODULI_ALL = SET_UPDATE_PROTOC +
            "AND id_classe_allegato =(SELECT id_classe_allegato FROM scriva_d_classe_allegato sdca WHERE cod_classe_allegato = 'ELAB_PROGETTUALI')\n" +
            "AND id_istanza = :idIstanza\n";



    private static final String QUERY_UPDATE_PROTOC_FIGLI_ELENCO_ALL = SET_UPDATE_PROTOC +
            "AND id_allegato_istanza_padre IN\n" +
            "    (\n" +
            "        SELECT id_allegato_istanza\n" +
            "        FROM scriva_t_allegato_istanza stai2\n" +
            "        WHERE data_cancellazione IS NULL\n" +
            "        AND stai2.id_tipologia_allegato IN \n" +
            "            (SELECT id_tipologia_allegato FROM scriva_d_tipologia_allegato sdta WHERE sdta.cod_tipologia_allegato IN ('ELENCO_ALLEGATI'))\n" +
            "        AND stai2.id_classe_allegato =\n" +
            "            (SELECT id_classe_allegato FROM scriva_d_classe_allegato sdca WHERE cod_classe_allegato = 'ELAB_PROGETTUALI')\n" +
            "        AND id_istanza = :idIstanza\n" +
            "    )\n" +
            "AND id_istanza = :idIstanza\n";

    private static final String WHERE_DATA_INIZIO_FINE = " AND (DATE(srata.data_inizio_validita) <= DATE(NOW()) AND (srata.data_fine_validita IS NULL OR DATE(srata.data_fine_validita)>= DATE(NOW())))\n ";

    private static final String UPDATE_ALLEGATO_PADRE = "update scriva_t_allegato_istanza set id_allegato_istanza_padre = null\n" +
            "WHERE id_allegato_istanza_padre = ( \n" +
            "               SELECT id_allegato_istanza\n" +
            "               FROM scriva_t_allegato_istanza\n" +
            "               WHERE uuid_index = :uuidIndex" +
            "            )";

    private static final String QUERY_LOAD_ALLEGATI_ISTANZA_FOR_NOTIFICHE = "SELECT stai.*\n" +
            "FROM _replaceTableName_ stai\n" +
            "INNER JOIN scriva_d_tipologia_allegato sdta ON sdta.id_tipologia_allegato = stai.id_tipologia_allegato\n" +
            "INNER JOIN scriva_d_categoria_allegato sdca ON sdca.id_categoria_allegato = sdta.id_categoria_allegato\n" +
            "INNER JOIN scriva_d_classe_allegato sdcla ON sdcla.id_classe_allegato = stai.id_classe_allegato\n" +
            //"INNER JOIN scriva_r_notifica_configurazione srnc ON srnc.id_notifica_configurazione = :idNotificaConfigurazione\n" +
            "INNER JOIN scriva_r_notifica_config_allegato srnca ON srnca.id_notifica_config_allegato = :idNotificaConfigurazioneAllegato\n" +
            "LEFT JOIN scriva_r_istanza_osservazione srio on srio.id_istanza_osservazione = stai.id_istanza_osservazione\n" +
            "LEFT JOIN scriva_r_integra_istanza_allegato sriia on sriia.id_allegato_istanza = stai.id_allegato_istanza\n" +
            "LEFT JOIN scriva_t_integrazione_istanza sria ON sria.id_integrazione_istanza =sriia.id_integrazione_istanza\n" +
            "WHERE stai.id_istanza = :idIstanza\n" +
            "AND stai.data_cancellazione IS NULL\n" +
            "AND (srio.id_stato_osservazione <> 1 OR srio.id_stato_osservazione IS NULL)\n";

    private static final String WHERE_TIPO_CATEG_CLASSE_ALLEG = "AND (\n" +
            "\tsdta.cod_tipologia_allegato = COALESCE(srnca.notifica_cod_tipologia_allegato, sdta.cod_tipologia_allegato) AND\n" +
            "\tsdca.cod_categoria_allegato = COALESCE(srnca.notifica_cod_categoria_allegato, sdca.cod_categoria_allegato) AND\n" +
            "\tsdcla.cod_classe_allegato = COALESCE(srnca.notifica_cod_classe_allegato, sdcla.cod_classe_allegato)\n" +
            ")\n";

    private static final String WHERE_UID_RICHIESTA = "AND (\n" +
            "\tsrio.gest_uid = :uidRichiesta -- Tipo richiesta = osservazione\n" +
            "\tOR sria.gest_uid = :uidRichiesta -- Tipo richiesta = integrazione o perfezionamento\n" +
            ")\n";

    private static final String WHERE_ID_ALLEGATO_PADRE = "AND stai.id_allegato_istanza_padre IN (:idAllegatoPadre)\n";

    private static final String WHERE_ID_ALLEGATO_PADRE_NULL = "AND stai.id_allegato_istanza_padre IS NULL\n";

    private static final String WHERE_FLG_PUBBLICABILI = "AND stai.data_pubblicazione IS NOT NULL\n";

    private static final String WHERE_FLG_RIF_PROTOCOLLO = "AND sriia.flg_allegato_rif_protocollo = 1\n";

    private static final String WHERE_FLG_ULTIMO_DOC = "ORDER BY stai.data_upload DESC LIMIT 1";
    
    private static final String ORDER_BY_CLAUSE_REV = " ORDER BY sdcla.ordinamento_classe_allegato, srata.ordinamento_adem_tipo_allega, stai.data_upload ";

    /**
     * @return List<AllegatoIstanzaExtendedDTO>
     */
    @Override
    public List<AllegatoIstanzaExtendedDTO> loadAllegatiIstanza(Long idIstanzaOsservazione, AttoreScriva attoreScriva, boolean flgOnlyPubblicati, String codComponenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_ALLEGATI_ISTANZA;
        if (idIstanzaOsservazione != null) {
            map.put("idIstanzaOsservazione", idIstanzaOsservazione);
            query += WHERE_ID_ISTANZA_OSSERVAZIONE;
        }
        if (Boolean.TRUE.equals(flgOnlyPubblicati)) {
            query += WHERE_DATA_PUBBLICAZIONE_NOT_NULL;
        }
        if (StringUtils.isNotBlank(codComponenteApp)) {
            map.put("codComponenteApp", attoreScriva.getComponente());
            query += WHERE_IND_VISIBILE;
        }

        if (idIstanzaOsservazione == null && Boolean.FALSE.equals(flgOnlyPubblicati) && !StringUtils.isNotBlank(codComponenteApp)) {
            query += WHERE_DATA_INIZIO_FINE;
        }

        return findListByQuery(className, query + ORDER_BY_CLAUSE, map);
    }

    /**
     * @param id id
     * @return List<AllegatoIstanzaExtendedDTO>
     */
    @Override
    public List<AllegatoIstanzaExtendedDTO> loadAllegatoIstanzaById(Long id) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return findListByQuery(className, QUERY_LOAD_ALLEGATI_ISTANZA_BY_ID, map);
    }

    /**
     * Load allegato istanza by id istanza list.
     *
     * @param idIstanza             idIstanza
     * @param codAllegato           the cod allegato
     * @param codClasseAllegato     the cod classe allegato
     * @param codCategoriaAllegato  the cod categoria allegato
     * @param codTipologiaAllegato  the cod tipologia allegato
     * @param idIstanzaOsservazione the id istanza osservazione
     * @param flgCancLogica         the flg canc logica
     * @param flgOnlyPubblicati     the flg only pubblicati
     * @param offset                the offset
     * @param limit                 the limit
     * @param sort                  the sort
     * @param system                the system
     * @param codComponenteApp      the cod componente app
     * @return List<AllegatoIstanzaExtendedDTO>            list
     */
    @Override
    public List<AllegatoIstanzaExtendedDTO> loadAllegatoIstanzaByIdIstanza(Long idIstanza, String codAllegato, String codClasseAllegato,
                                                                           String codCategoriaAllegato, String codTipologiaAllegato,
                                                                           Long idIstanzaOsservazione,
                                                                           String flgCancLogica, boolean flgOnlyPubblicati,
                                                                           String offset, String limit, String sort, Boolean system, String codComponenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        List<Integer> flgCancellato = new ArrayList<>();

        String query = QUERY_LOAD_ALLEGATI_ISTANZA;

        if (idIstanza != null) {
            map.put("idIstanza", idIstanza);
            query += WHERE_ID_ISTANZA;
        }

        flgCancellato.add(0);
        if (StringUtils.isNotBlank(flgCancLogica) && flgCancLogica.equalsIgnoreCase("TRUE")) {
            flgCancellato.add(1);
            // TODO: (JIRA-1379) Aggiungere condizione per non restituire i documenti cancellati logicamente
            // che hanno flg_sistema=1 come tipologia allegati 
            map.put("flgSistema", 1);
            query += WHERE_TIPOLOGIA_ALLEG_SISTEMA;  //+ WHERE_DATA_CANCELLAZIONE_NOT_NULL;
        }
        map.put("flgCancLogica", flgCancellato);
        query += WHERE_FLG_CANC_LOGICA;

        if (StringUtils.isNotBlank(codAllegato)) {
            map.put("codAllegato", codAllegato);
            query += WHERE_COD_ALLEGATO;
        }

        if (StringUtils.isNotBlank(codClasseAllegato)) {
            map.put("codClasseAllegato", codClasseAllegato);
            query += WHERE_COD_CLASSE_ALLEGATO;
        }

        if (StringUtils.isNotBlank(codCategoriaAllegato)) {
            map.put("codCategoriaAllegato", codCategoriaAllegato);
            query += WHERE_COD_CATEGORIA_ALLEGATO;
        }

        if (StringUtils.isNotBlank(codTipologiaAllegato)) {
            map.put("codTipologiaAllegato", codTipologiaAllegato);
            query += WHERE_COD_TIPOLOGIA_ALLEGATO;
        }

        if (idIstanzaOsservazione != null) {
            map.put("idIstanzaOsservazione", idIstanzaOsservazione);
            query += WHERE_ID_ISTANZA_OSSERVAZIONE;
        }

        if (StringUtils.isNotBlank(codComponenteApp)) { //&& Boolean.FALSE.equals(system)) { 
        	/* A seguito della call con Simona e Barbara, si e' deciso di rimuovere questo controllo in quanto non dovrebbe essere piu' necessario*/
            map.put("codComponenteApp", codComponenteApp);
            query += WHERE_IND_VISIBILE;
        }

        if (Boolean.TRUE.equals(flgOnlyPubblicati)) {
            query += WHERE_DATA_PUBBLICAZIONE_NOT_NULL;
        }

        if (StringUtils.isNotBlank(sort)) {
            query += getQuerySortingSegment(sort);
        } else {
            query += ORDER_BY_CLAUSE_REV;
        }

        return findListByQuery(className, query, map, offset, limit, Boolean.FALSE);
    }

    /**
     * @param uuidIndex uuidIndex
     * @return List<AllegatoIstanzaExtendedDTO>
     */
    @Override
    public List<AllegatoIstanzaExtendedDTO> loadAllegatoIstanzaByUuidIndex(String uuidIndex) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("uuidIndex", uuidIndex);
        return findListByQuery(className, QUERY_LOAD_ALLEGATI_ISTANZA_BY_UUID_INDEX, map);
    }

    /**
     * @param idIstanza            idIstanza
     * @param codTipologiaAllegato codice tipologia allegato
     * @return List<AllegatoIstanzaExtendedDTO>
     */
    @Override
    public List<AllegatoIstanzaExtendedDTO> loadAllegatoIstanzaByIdIstanzaAndCodTipologia(Long idIstanza, String codTipologiaAllegato) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        map.put("codTipologiaAllegato", codTipologiaAllegato);
        return findListByQuery(className, QUERY_LOAD_ALLEGATI_ISTANZA_BY_ID_ISTANZA_AND_COD_TIPO_ALLEGATO + ORDER_BY_CLAUSE, map);
    }

    /**
     * @param idIstanza    idIstanza
     * @param nomeAllegato nome allegato
     * @return List<AllegatoIstanzaExtendedDTO>
     */
    @Override
    public List<AllegatoIstanzaExtendedDTO> loadAllegatoIstanzaByIdIstanzaAndNome(Long idIstanza, String nomeAllegato) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        map.put("nomeAllegato", nomeAllegato);
        return findListByQuery(className, QUERY_LOAD_ALLEGATI_ISTANZA_BY_ID_ISTANZA_AND_NOME_ALLEGATO, map);
    }

    /**
     * Load allegato istanza non inviato by id istanza list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    @Override
    public List<AllegatoIstanzaExtendedDTO> loadAllegatoIstanzaNonInviatoByIdIstanza(Long idIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_ALLEGATI_ISTANZA + WHERE_DATA_INVIO_NULL;

        if (idIstanza != null) {
            map.put("idIstanza", idIstanza);
            query += WHERE_ID_ISTANZA;
        }
        query += ORDER_BY_CLAUSE_ID_PADRE;

        return findListByQuery(className, query, map, null, null, Boolean.FALSE);
    }

    @Override
    public List<AllegatoIstanzaExtendedDTO> loadAllegatoIstanzaFigliByIdAllegatoPadre(Long idAllegatoIstanzaPadre) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAllegatoIstanzaPadre", idAllegatoIstanzaPadre);
        return findListByQuery(className, QUERY_LOAD_ALLEGATI_ISTANZA_BY_ID_ALLEGATO_ISTANZA_PADRE, map, null, null, Boolean.FALSE);
    }

    /**
     * @param allegatoIstanza AllegatoIstanzaExtendedDTO
     * @return id record inserito
     */
    @Override
    public Long saveAllegatoIstanza(AllegatoIstanzaDTO allegatoIstanza, String codCategoriaAllegato) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            map.put("idIstanza", allegatoIstanza.getIdIstanza());
            map.put("idTipologiaAllegato", allegatoIstanza.getIdTipologiaAllegato());
            map.put("idTipoIntegraAllegato", allegatoIstanza.getIdTipoIntegraAllegato());
            map.put("uuidIndex", allegatoIstanza.getUuidIndex());
            map.put("flgRiservato", (allegatoIstanza.getFlgRiservato() == null || !allegatoIstanza.getFlgRiservato()) ? 0 : 1);
            map.put("codAllegato", StringUtils.isNotBlank(allegatoIstanza.getCodAllegato()) ? allegatoIstanza.getCodAllegato() : codCategoriaAllegato);
            map.put("nomeAllegato", allegatoIstanza.getNomeAllegato());
            map.put("dimensioneUpload", allegatoIstanza.getDimensioneUpload());
            map.put("dataUpload", allegatoIstanza.getDataUpload());
            map.put("dataIntegrazione", allegatoIstanza.getDataIntegrazione());
            map.put("dataCancellazione", allegatoIstanza.getDataCancellazione());
            map.put("flgCancellato", Boolean.TRUE.equals(allegatoIstanza.getFlgCancellato()) ? 1 : 0);
            map.put("indFirma", allegatoIstanza.getIndFirma() != null ? allegatoIstanza.getIndFirma() : 0);
            map.put("note", allegatoIstanza.getNote());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", allegatoIstanza.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", allegatoIstanza.getGestAttoreIns());
            map.put("idIstanzaAttore", allegatoIstanza.getIdIstanzaAttore());
            map.put("idFunzionario", allegatoIstanza.getIdFunzionario());
            map.put("idClasseAllegato", allegatoIstanza.getIdClasseAllegato());
            map.put("idAllegatoIstanzaPadre", allegatoIstanza.getIdAllegatoIstanzaPadre());
            map.put("flgDaPubblicare", allegatoIstanza.getFlgDaPubblicare() != null && Boolean.TRUE.equals(allegatoIstanza.getFlgDaPubblicare()) ? 1 : 0);
            map.put("dataPubblicazione", allegatoIstanza.getDataPubblicazione());
            map.put("numProtocolloAllegato", StringUtils.isNotBlank(allegatoIstanza.getNumProtocolloAllegato()) ? allegatoIstanza.getNumProtocolloAllegato() : null);
            map.put("dataProtocolloAllegato", allegatoIstanza.getDataProtocolloAllegato());
            map.put("urlDoc", allegatoIstanza.getUrlDoc());
            map.put("idIstanzaOsservazione", allegatoIstanza.getIdIstanzaOsservazione());
            map.put("numAtto", allegatoIstanza.getNumAtto());
            map.put("dataAtto", allegatoIstanza.getDataAtto());
            map.put("titoloAllegato", allegatoIstanza.getTitoloAllegato());
            map.put("autoreAllegato", allegatoIstanza.getAutoreAllegato());
            map.put("dataInvioEsterno", allegatoIstanza.getDataInvioEsterno());
            map.put("gestUid", generateGestUID(allegatoIstanza.getIdIstanza() + allegatoIstanza.getUuidIndex() + now));
            MapSqlParameterSource params = getParameterValue(map);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(getQuery(QUERY_INSERT_ALLEGATO_ISTANZA.replace("__codAllegato__", StringUtils.isNotBlank(allegatoIstanza.getCodAllegato()) ? ":codAllegato" : COD_ALLEGATO_CALC), null, null), params, keyHolder, new String[]{"id_allegato_istanza"});
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
     * @param dto AllegatoIstanzaDTO
     * @return numero record aggiornati
     */
    @Override
    public Integer updateAllegatoIstanza(AllegatoIstanzaDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            AllegatoIstanzaDTO allegatoIstanzaOld = this.findByPK(dto.getIdAllegatoIstanza());
            if (null == allegatoIstanzaOld) {
                logError(className, "Record non trovato con id [" + dto.getIdAllegatoIstanza() + "]");
                return -1;
            }
            Long idTipologiaAllegatoOld = allegatoIstanzaOld.getIdTipologiaAllegato() != null && allegatoIstanzaOld.getIdTipologiaAllegato() > 0 ? allegatoIstanzaOld.getIdTipologiaAllegato() : null;
            Long idTipoIntegraAllegatoOld = allegatoIstanzaOld.getIdTipoIntegraAllegato() != null && allegatoIstanzaOld.getIdTipoIntegraAllegato() > 0 ? allegatoIstanzaOld.getIdTipoIntegraAllegato() : null;
            Long idIstanzaAttoreOld = allegatoIstanzaOld.getIdIstanzaAttore() != null && allegatoIstanzaOld.getIdIstanzaAttore() > 0 ? allegatoIstanzaOld.getIdIstanzaAttore() : null;
            Long idFunzionarioOld = allegatoIstanzaOld.getIdFunzionario() != null && allegatoIstanzaOld.getIdFunzionario() > 0 ? allegatoIstanzaOld.getIdFunzionario() : null;
            Long idClasseAllegatoOld = allegatoIstanzaOld.getIdClasseAllegato() != null && allegatoIstanzaOld.getIdClasseAllegato() > 0 ? allegatoIstanzaOld.getIdClasseAllegato() : null;
            Long idAllegatoIstanzaPadreOld = allegatoIstanzaOld.getIdAllegatoIstanzaPadre() != null && allegatoIstanzaOld.getIdAllegatoIstanzaPadre() > 0 ? allegatoIstanzaOld.getIdAllegatoIstanzaPadre() : null;
            Long idIstanzaOsservazioneOld = allegatoIstanzaOld.getIdIstanzaOsservazione() != null && allegatoIstanzaOld.getIdIstanzaOsservazione() > 0 ? allegatoIstanzaOld.getIdIstanzaOsservazione() : null;

            map.put("idAllegatoIstanza", allegatoIstanzaOld.getIdAllegatoIstanza());
            map.put("idIstanza", allegatoIstanzaOld.getIdIstanza());
            map.put("idTipologiaAllegato", dto.getIdTipologiaAllegato() != null && dto.getIdTipologiaAllegato() != 0 ? dto.getIdTipologiaAllegato() : idTipologiaAllegatoOld);
            map.put("idTipoIntegraAllegato", dto.getIdTipoIntegraAllegato() != null && dto.getIdTipoIntegraAllegato() != 0 ? dto.getIdTipoIntegraAllegato() : idTipoIntegraAllegatoOld);
            map.put("uuidIndex", dto.getUuidIndex() != null ? dto.getUuidIndex() : allegatoIstanzaOld.getUuidIndex());
            map.put("flgRiservato", dto.getFlgRiservato() != null ? (dto.getFlgRiservato() ? 1 : 0) : (allegatoIstanzaOld.getFlgRiservato() ? 1 : 0));
            map.put("codAllegato", allegatoIstanzaOld.getCodAllegato());
            map.put("nomeAllegato", dto.getNomeAllegato() != null ? dto.getNomeAllegato() : allegatoIstanzaOld.getNomeAllegato());
            map.put("dimensioneUpload", dto.getDimensioneUpload() != null ? dto.getDimensioneUpload() : allegatoIstanzaOld.getDimensioneUpload());
            map.put("dataUpload", dto.getDataUpload() != null ? dto.getDataUpload() : allegatoIstanzaOld.getDataUpload());
            map.put("dataIntegrazione", dto.getDataIntegrazione() != null ? dto.getDataIntegrazione() : allegatoIstanzaOld.getDataIntegrazione());
            map.put("dataCancellazione", dto.getDataCancellazione() != null ? dto.getDataCancellazione() : allegatoIstanzaOld.getDataCancellazione());
            map.put("flgCancellato", dto.getFlgCancellato() != null ? (Boolean.TRUE.equals(dto.getFlgCancellato()) ? 1 : 0) : (Boolean.TRUE.equals(allegatoIstanzaOld.getFlgCancellato()) ? 1 : 0));
            map.put("indFirma", dto.getIndFirma() != null ? dto.getIndFirma() : allegatoIstanzaOld.getIndFirma());
            map.put("note", dto.getNote());
            map.put("gestDataIns", allegatoIstanzaOld.getGestDataIns());
            map.put("gestAttoreIns", allegatoIstanzaOld.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreUpd());
            map.put("idIstanzaAttore", dto.getIdIstanzaAttore() != null && dto.getIdIstanzaAttore() > 0 ? dto.getIdIstanzaAttore() : idIstanzaAttoreOld);
            map.put("idFunzionario", dto.getIdFunzionario() != null && dto.getIdFunzionario() != 0 ? dto.getIdFunzionario() : idFunzionarioOld);
            map.put("idClasseAllegato", dto.getIdClasseAllegato() != null && dto.getIdClasseAllegato() != 0 ? dto.getIdClasseAllegato() : idClasseAllegatoOld);
            map.put("idAllegatoIstanzaPadre", dto.getIdAllegatoIstanzaPadre() != null && dto.getIdAllegatoIstanzaPadre() != 0 ? dto.getIdAllegatoIstanzaPadre() : idAllegatoIstanzaPadreOld);
            
            
            Boolean flgCanc = dto.getFlgCancellato() != null ? (Boolean.TRUE.equals(dto.getFlgCancellato()) ? true : false) : (Boolean.TRUE.equals(allegatoIstanzaOld.getFlgCancellato()) ? true : false);

            if(!flgCanc) {
            	map.put("flgDaPubblicare", dto.getFlgDaPubblicare() != null ? (Boolean.TRUE.equals(dto.getFlgDaPubblicare()) ? 1 : 0) : (Boolean.TRUE.equals(allegatoIstanzaOld.getFlgDaPubblicare()) ? 1 : 0));
            	map.put("dataPubblicazione", dto.getDataPubblicazione());
            }
            else {
            	map.put("flgDaPubblicare", 0);
            	map.put("dataPubblicazione", null);
            }
            	
            map.put("numProtocolloAllegato", dto.getNumProtocolloAllegato());
            map.put("dataProtocolloAllegato", dto.getDataProtocolloAllegato());
            map.put("urlDoc", dto.getUrlDoc());
            map.put("idIstanzaOsservazione", dto.getIdIstanzaOsservazione() != null && dto.getIdIstanzaOsservazione() != 0 ? dto.getIdIstanzaOsservazione() : idIstanzaOsservazioneOld);
            map.put("numAtto", dto.getNumAtto() != null ? dto.getNumAtto() : allegatoIstanzaOld.getNumAtto());
            map.put("dataAtto", dto.getDataAtto() != null ? dto.getDataAtto() : null); // allegatoIstanzaOld.getDataAtto());
            map.put("titoloAllegato", dto.getTitoloAllegato() != null ? dto.getTitoloAllegato() : allegatoIstanzaOld.getTitoloAllegato());
            map.put("autoreAllegato", dto.getAutoreAllegato() != null ? dto.getAutoreAllegato() : allegatoIstanzaOld.getAutoreAllegato());
            map.put("dataInvioEsterno", dto.getDataInvioEsterno() != null ? dto.getDataInvioEsterno() : allegatoIstanzaOld.getDataInvioEsterno());

            
            MapSqlParameterSource params = getParameterValue(map);
            String query = getQuery(QUERY_UPDATE_ALLEGATO_ISTANZA, null, null);
            return template.update(query, params);

        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }

    }

    /**
     * @param uuidIndex uuidIndex
     */
    @Override
    public Integer deleteAllegatoIstanzaByUuidIndex(String uuidIndex) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("uuidIndex", uuidIndex);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_ALLEGATO_ISTANZA_BY_UUID_INDEX, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update id allegato istanza padre
     *
     * @param uuidIndex the uuidIndex
     */
    @Override
    public void updateIdAllegatoIstanzaPadre(String uuidIndex) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("uuidIndex", uuidIndex);
            template.update(getQuery(UPDATE_ALLEGATO_PADRE, null, null), getParameterValue(map));
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }

    @Override
    public List<AllegatoIstanzaDTO> loadAllegatoIstanzaForNotifiche(NotificaAllegatoSearchDTO notificaAllegatoSearch) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        List<Integer> flgCancellato = new ArrayList<>();

        String query = QUERY_LOAD_ALLEGATI_ISTANZA_FOR_NOTIFICHE;

        map.put("idIstanza", notificaAllegatoSearch.getIdIstanza());
        map.put("idNotificaConfigurazione", notificaAllegatoSearch.getIdNotificaConfigurazione());
        map.put("idNotificaConfigurazioneAllegato", notificaAllegatoSearch.getIdNotificaConfigurazioneAllegato());

        if (CollectionUtils.isEmpty(notificaAllegatoSearch.getIdAllegatoPadreList())) {
            query += WHERE_ID_ALLEGATO_PADRE_NULL;
            //Parametri da valutare solo per il documento padre
            query += WHERE_TIPO_CATEG_CLASSE_ALLEG;
            if (notificaAllegatoSearch.getFlgRifAllegatoProtocollo() == 1) {
                query += WHERE_FLG_RIF_PROTOCOLLO;
            }

            if (notificaAllegatoSearch.getFlgUltimoDoc() == 1) {
                query += WHERE_FLG_ULTIMO_DOC;
            }
        } else {
            map.put("idAllegatoPadre", notificaAllegatoSearch.getIdAllegatoPadreList());
            query += WHERE_ID_ALLEGATO_PADRE;
        }

        if (StringUtils.isNotBlank(notificaAllegatoSearch.getTipoRichiesta()) && StringUtils.isNotBlank(notificaAllegatoSearch.getGestUidRichiesta())) {
            map.put("uidRichiesta", notificaAllegatoSearch.getGestUidRichiesta());
            query += WHERE_UID_RICHIESTA;
        }

        if (notificaAllegatoSearch.getFlgPubblicabili() == 1) {
            query += WHERE_FLG_PUBBLICABILI;
        }


        return findListByQuery(className, query, map, null, null, Boolean.TRUE);

    }


    /**
     * Update data invio esterno integer.
     *
     * @param idAllegatoIstanzaList the id allegato istanza list
     * @return the integer
     */
    @Override
    public Integer updateDataInvioEsterno(List<Long> idAllegatoIstanzaList) {
        logBegin(className);
        try {
            Date now = Calendar.getInstance().getTime();
            Map<String, Object> map = new HashMap<>();
            map.put("idAllegatoIstanzaList", idAllegatoIstanzaList);
            map.put("dataInvioEsterno", now);
            return template.update(getQuery(QUERY_UPDATE_DATA_INVIO_ESTERNO, null, null), getParameterValue(map));
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update id padre figlio.
     *
     * @param idIstanza              the id istanza
     * @param idAllegatoIstanzaPadre the id allegato istanza padre
     */
    @Override
    public void updateIdPadreFiglio(Long idIstanza, Long idAllegatoIstanzaPadre) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            map.put("idAllegatoIstanzaPadre", idAllegatoIstanzaPadre);
            template.update(getQuery(QUERY_UPDATE_ID_ALLEGATO_PADRE, null, null), getParameterValue(map));
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update id padre figlio elenco integrazioni.
     *
     * @param idIstanza              the id istanza
     * @param idAllegatoIstanzaPadre the id allegato istanza padre
     */
    @Override
    public void updateIdPadreFiglioElencoIntegrazioni(Long idIstanza, Long idAllegatoIstanzaPadre) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            map.put("idAllegatoIstanzaPadre", idAllegatoIstanzaPadre);
            template.update(getQuery(QUERY_UPDATE_ID_ALLEGATO_PADRE_INTEG, null, null), getParameterValue(map));
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update id padre figlio elenco allegati.
     *
     * @param idIstanza              the id istanza
     * @param idAllegatoIstanzaPadre the id allegato istanza padre
     */
    @Override
    public void updateIdPadreFiglioElencoAllegati(Long idIstanza, Long idAllegatoIstanzaPadre) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            map.put("idAllegatoIstanzaPadre", idAllegatoIstanzaPadre);
            template.update(getQuery(QUERY_UPDATE_ID_ALLEGATO_PADRE_ELENCO_ALL, null, null), getParameterValue(map));
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete id padre integer.
     *
     * @param idAllegatoIstanzaPadre the id padre
     * @return the integer
     */
    @Override
    public Integer deleteIdPadre(Long idAllegatoIstanzaPadre) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idAllegatoIstanzaPadre", idAllegatoIstanzaPadre);
            return template.update(getQuery(QUERY_DELETE_ID_ALLEGATO_PADRE, null, null), getParameterValue(map));
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return 0;
    }

    /**
     * Update data pubblicazione integer.
     *
     * @param idIstanza         the id istanza
     * @param dataPubblicazione the data pubblicazione
     * @param attoreUpd         the attore upd
     * @return the integer
     */
    @Override
    public Integer updateDataPubblicazione(Long idIstanza, Date dataPubblicazione, String attoreUpd) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            map.put("dataPubblicazione", dataPubblicazione);
            map.put("dateUpd", Calendar.getInstance().getTime());
            map.put("attoreUpd", attoreUpd);
            return template.update(getQuery(QUERY_UPDATE_DATA_PUBBLICAZIONE, null, null), getParameterValue(map));
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update data padre from figlio integer.
     *
     * @param idAllegatoIstanzaPadre the id allegato istanza padre
     * @return the integer
     */
    @Override
    public Integer updateDataPadreFromFiglio(Long idAllegatoIstanzaPadre) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idAllegatoIstanzaPadre", idAllegatoIstanzaPadre);
            return template.update(getQuery(QUERY_UPDATE_PADRE_FROM_FIGLIO, null, null), getParameterValue(map));
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update id padre osservazioni integer.
     *
     * @param idAllegatoIstanzaOsservazione the id allegato istanza osservazione
     * @param attoreScriva                  the attore scriva
     * @return the integer
     */
    @Override
    public Integer updateIdPadreOsservazioni(Long idAllegatoIstanzaOsservazione, AttoreScriva attoreScriva) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idAllegatoIstanzaOsservazione", idAllegatoIstanzaOsservazione);
            map.put("dateUpd", Calendar.getInstance().getTime());
            map.put("attoreUpd", attoreScriva.getCodiceFiscale());
            return template.update(getQuery(QUERY_UPDATE_PADRE_FROM_MOD_OSS, null, null), getParameterValue(map));
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update dati integrazione integer.
     *
     * @param idAllegatoIstanzaPadre the id allegato istanza padre
     * @param numProtocollo          the num protocollo
     * @param dataProtocollo         the data protocollo
     * @param attoreScriva           the attore scriva
     * @return the integer
     */
    @Override
    public Integer updateDatiIntegrazione(Long idAllegatoIstanzaPadre, String numProtocollo, Timestamp dataProtocollo, AttoreScriva attoreScriva) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idAllegatoIstanzaPadre", idAllegatoIstanzaPadre);
            map.put("numProtocollo", numProtocollo);
            map.put("dataProtocollo", dataProtocollo);
            map.put("dateUpd", Calendar.getInstance().getTime());
            map.put("attoreUpd", attoreScriva.getCodiceFiscale());
            return template.update(getQuery(QUERY_UPDATE_DATI_INTEGRAZIONE, null, null), getParameterValue(map));
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update protocollo mod istanza elenco allegati integer.
     *
     * @param idIstanza      the id istanza
     * @param numProtocollo  the num protocollo
     * @param dataProtocollo the data protocollo
     * @param attoreScriva   the attore scriva
     * @return the integer
     */
    @Override
    public Integer updateProtocolloModIstanzaElencoAllegati(Long idIstanza, String numProtocollo, Timestamp dataProtocollo, AttoreScriva attoreScriva) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            map.put("numProtocollo", numProtocollo);
            map.put("dataProtocollo", dataProtocollo);
            map.put("dateUpd", Calendar.getInstance().getTime());
            map.put("attoreUpd", attoreScriva.getCodiceFiscale());
            return template.update(getQuery(QUERY_UPDATE_PROTOC_MODULI, null, null), getParameterValue(map));
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update protocollo mod istanza elenco allegati integer.
     *
     * @param idIstanza      the id istanza
     * @param numProtocollo  the num protocollo
     * @param dataProtocollo the data protocollo
     * @param attoreScriva   the attore scriva
     * @return the integer
     */
    @Override
    public Integer updateProtocolloModIstanzaElencoAllegatiAll(Long idIstanza, String numProtocollo, Timestamp dataProtocollo, AttoreScriva attoreScriva) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            map.put("numProtocollo", numProtocollo);
            map.put("dataProtocollo", dataProtocollo);
            map.put("dateUpd", Calendar.getInstance().getTime());
            map.put("attoreUpd", attoreScriva.getCodiceFiscale());
            return template.update(getQuery(QUERY_UPDATE_PROTOC_MODULI_ALL, null, null), getParameterValue(map));
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update protocollo figli elenco allegati integer.
     *
     * @param idIstanza      the id istanza
     * @param numProtocollo  the num protocollo
     * @param dataProtocollo the data protocollo
     * @param attoreScriva   the attore scriva
     * @return the integer
     */
    @Override
    public Integer updateProtocolloFigliElencoAllegati(Long idIstanza, String numProtocollo, Timestamp dataProtocollo, AttoreScriva attoreScriva) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            map.put("numProtocollo", numProtocollo);
            map.put("dataProtocollo", dataProtocollo);
            map.put("dateUpd", Calendar.getInstance().getTime());
            map.put("attoreUpd", attoreScriva.getCodiceFiscale());
            return template.update(getQuery(QUERY_UPDATE_PROTOC_FIGLI_ELENCO_ALL, null, null), getParameterValue(map));
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }


    /**
     * Find by pk allegato istanza dto.
     *
     * @param idAllegatoIstanza the id allegato istanza
     * @return the allegato istanza dto
     */
    public AllegatoIstanzaDTO findByPK(Long idAllegatoIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("id", idAllegatoIstanza);
        return findByPK(className, map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_PRIMARY_KEY, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<AllegatoIstanzaDTO>
     */
    @Override
    public RowMapper<AllegatoIstanzaDTO> getRowMapper() throws SQLException {
        return new AllegatoIstanzaRowMapper();
    }

    /**
     * Returns a RowMapper extended for a new bean instance
     *
     * @return RowMapper<AllegatoIstanzaExtendedDTO>
     */
    @Override
    public RowMapper<AllegatoIstanzaExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new AllegatoIstanzaExtendedRowMapper();
    }

    private static class AllegatoIstanzaRowMapper implements RowMapper<AllegatoIstanzaDTO> {

        /**
         * Costruttore
         *
         * @throws SQLException SQLException
         */
        public AllegatoIstanzaRowMapper() throws SQLException {
            // Instantiate class
        }

        @Override
        public AllegatoIstanzaDTO mapRow(ResultSet rs, int i) throws SQLException {
            AllegatoIstanzaDTO bean = new AllegatoIstanzaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, AllegatoIstanzaDTO bean) throws SQLException {
            bean.setIdAllegatoIstanza(rs.getLong("id_allegato_istanza"));
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdTipologiaAllegato(rs.getLong("id_tipologia_allegato"));
            bean.setIdTipoIntegraAllegato(rs.getLong("id_tipo_integrazione"));
            bean.setUuidIndex(rs.getString("uuid_index"));
            bean.setFlgRiservato(1 == rs.getInt("flg_riservato") ? Boolean.TRUE : Boolean.FALSE);
            bean.setCodAllegato(rs.getString("cod_allegato"));
            bean.setNomeAllegato(rs.getString("nome_allegato"));
            bean.setDimensioneUpload(rs.getLong("dimensione_upload"));
            bean.setDataUpload(rs.getTimestamp("data_upload"));
            bean.setDataIntegrazione(rs.getTimestamp("data_integrazione"));
            bean.setDataCancellazione(rs.getTimestamp("data_cancellazione"));
            bean.setFlgCancellato(1 == rs.getInt("flg_cancellato") ? Boolean.TRUE : Boolean.FALSE);
            bean.setIndFirma(rs.getInt("ind_firma"));
            bean.setNote(rs.getString("note"));
            bean.setIdIstanzaAttore(rs.getLong("id_istanza_attore"));
            bean.setIdFunzionario(rs.getLong("id_funzionario"));
            bean.setIdClasseAllegato(rs.getLong("id_classe_allegato"));
            bean.setIdAllegatoIstanzaPadre(rs.getLong("id_allegato_istanza_padre") == 0 ? null : rs.getLong("id_allegato_istanza_padre"));
            bean.setFlgDaPubblicare(1 == rs.getInt("flg_da_pubblicare") ? Boolean.TRUE : Boolean.FALSE);
            bean.setDataPubblicazione(rs.getTimestamp("data_pubblicazione"));
            bean.setNumProtocolloAllegato(rs.getString("num_protocollo_allegato"));
            bean.setDataProtocolloAllegato(rs.getTimestamp("data_protocollo_allegato"));
            bean.setUrlDoc(rs.getString("url_doc"));
            bean.setIdIstanzaOsservazione(rs.getLong("id_istanza_osservazione"));
            bean.setNumAtto(rs.getString("num_atto"));
            bean.setDataAtto(rs.getDate("data_atto"));
            bean.setTitoloAllegato(rs.getString("titolo_allegato"));
            bean.setAutoreAllegato(rs.getString("autore_allegato"));
            bean.setDataInvioEsterno(rs.getTimestamp("data_invio_esterno"));
            //bean.setFlgSistema(1 == rs.getInt("flg_sistema") ? Boolean.TRUE : Boolean.FALSE);
            try {
                bean.setCodStatoOsservazione(rs.getString("cod_stato_osservazione"));
            } catch (Exception e) {
                LOGGER.info("[AllegatoIstanzaRowMapper::populateBean]\n" + e);
            }
        }

    }

    private static class AllegatoIstanzaExtendedRowMapper implements RowMapper<AllegatoIstanzaExtendedDTO> {

        /**
         * Costruttore
         *
         * @throws SQLException SQLException
         */
        public AllegatoIstanzaExtendedRowMapper() throws SQLException {
            // Instantiate class
        }

        @Override
        public AllegatoIstanzaExtendedDTO mapRow(ResultSet rs, int i) throws SQLException {
            AllegatoIstanzaExtendedDTO bean = new AllegatoIstanzaExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, AllegatoIstanzaExtendedDTO bean) throws SQLException {
            Calendar timezone = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"));
            bean.setIdAllegatoIstanza(rs.getLong("id_allegato_istanza"));
            bean.setIdIstanza(rs.getLong("id_istanza"));

            TipologiaAllegatoExtendedDTO tipologiaAllegato = new TipologiaAllegatoExtendedDTO();
            populateBeanTipologiaAllegato(rs, tipologiaAllegato);
            bean.setTipologiaAllegato(tipologiaAllegato);

            TipoIntegraAllegatoDTO tipoIntegraAllegato = new TipoIntegraAllegatoDTO();
            populateBeanTipoIntegraAllegato(rs, tipoIntegraAllegato);
            bean.setTipoIntegraAllegato(tipoIntegraAllegato.getIdTipoIntegraAllegato() != 0 ? tipoIntegraAllegato : null);

            bean.setUuidIndex(rs.getString("uuid_index"));
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
            bean.setIdIstanzaAttore(rs.getLong("id_istanza_attore"));
            bean.setIdFunzionario(rs.getLong("id_funzionario"));

            ClasseAllegatoDTO classeAllegato = new ClasseAllegatoDTO();
            populateBeanClasseAllegato(rs, classeAllegato);
            bean.setClasseAllegato(classeAllegato.getIdClasseAllegato() != 0 ? classeAllegato : null);

            bean.setIdAllegatoIstanzaPadre(rs.getLong("id_allegato_istanza_padre") == 0 ? null : rs.getLong("id_allegato_istanza_padre"));
            bean.setFlgDaPubblicare(1 == rs.getInt("flg_da_pubblicare") ? Boolean.TRUE : Boolean.FALSE);
            bean.setDataPubblicazione(rs.getTimestamp("data_pubblicazione"));
            bean.setNumProtocolloAllegato(rs.getString("num_protocollo_allegato"));
            bean.setDataProtocolloAllegato(rs.getTimestamp("data_protocollo_allegato"));
            bean.setUrlDoc(rs.getString("url_doc"));
            bean.setIdIstanzaOsservazione(rs.getLong("id_istanza_osservazione"));
            bean.setNumAtto(rs.getString("num_atto"));
            bean.setDataAtto(rs.getDate("data_atto"));
            bean.setTitoloAllegato(rs.getString("titolo_allegato"));
            bean.setAutoreAllegato(rs.getString("autore_allegato"));
            bean.setDataInvioEsterno(rs.getTimestamp("data_invio_esterno"));
            bean.setCodStatoOsservazione(rs.getString("cod_stato_osservazione"));
            //bean.setFlgSistema(1 == rs.getInt("flg_sistema") ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateBeanTipologiaAllegato(ResultSet rs, TipologiaAllegatoExtendedDTO bean) throws SQLException {
            bean.setIdTipologiaAllegato(rs.getLong("tipologia_allegato_id"));

            CategoriaAllegatoDTO categoriaAllegato = new CategoriaAllegatoDTO();
            populateBeanCategoriaAllegato(rs, categoriaAllegato);
            bean.setCategoriaAllegato(categoriaAllegato);

            bean.setCodTipologiaAllegato(rs.getString("cod_tipologia_allegato"));
            bean.setDesTipologiaAllegato(rs.getString("des_tipologia_allegato"));
            bean.setFlgAttivo(rs.getInt("tipologia_allegato_attivo") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgAttivo(rs.getInt("flg_atto") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgSistema(1 == rs.getInt("flg_sistema") ? Boolean.TRUE : Boolean.FALSE);
            bean.setOrdinamentoTipologiaAllegato(rs.getInt("ordinamento_tipologia_allegato"));
        }

        private void populateBeanCategoriaAllegato(ResultSet rs, CategoriaAllegatoDTO bean) throws SQLException {
            bean.setIdCategoriaAllegato(rs.getLong("categoria_allegato_id"));
            bean.setCodCategoriaAllegato(rs.getString("cod_categoria_allegato"));
            bean.setDesCategoriaAllegato(rs.getString("des_categoria_allegato"));
            bean.setFlgAttivo(1 == rs.getInt("categoria_allegato_attivo") ? Boolean.TRUE : Boolean.FALSE);
            bean.setOrdinamentoCategoriaAllegato(rs.getInt("ordinamento_categoria_allegato"));
        }

        private void populateBeanTipoIntegraAllegato(ResultSet rs, TipoIntegraAllegatoDTO bean) throws SQLException {
            bean.setIdTipoIntegraAllegato(rs.getLong("tipo_integra_allegato_id"));
            bean.setCodTipoIntegraAllegato(rs.getString("cod_tipo_integrazione"));
            bean.setDesTipoIntegraAllegato(rs.getString("des_tipo_integrazione"));
        }

        private void populateBeanClasseAllegato(ResultSet rs, ClasseAllegatoDTO bean) throws SQLException {
            bean.setIdClasseAllegato(rs.getLong("classe_allegato_id"));
            bean.setCodClasseAllegato(rs.getString("cod_classe_allegato"));
            bean.setDesClasseAllegato(rs.getString("des_classe_allegato"));
            bean.setFlgAttivo(rs.getInt("flg_attivo_classe_allegato") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setOrdinamentoClasseAllegato(rs.getLong("ordinamento_classe_allegato"));
        }
    }

}