/* *****************************************************
 * Copyright Regione Piemonte - 2025
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************/

-- DROP SEQUENCE seq_scriva_cod_allegato;

CREATE SEQUENCE seq_scriva_cod_allegato
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
COMMENT ON SEQUENCE seq_scriva_cod_allegato IS 'applicativa, utilizzata nel campo : scriva_t_allegato_istanza.cod_allegato.
Regola : <COD CATEGORIA ALLEGATO>-<progressivo assoluto>

<COD CATEGORIA ALLEGATO>= scriva_d_categoria_allegato.cod_categoria_allegato
<progressivo assoluto> = seq_scriva_cod_allegato.nextval

es: DATRICH-1234
';
-- DROP SEQUENCE seq_scriva_cod_gruppo_soggetto;

CREATE SEQUENCE seq_scriva_cod_gruppo_soggetto
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
COMMENT ON SEQUENCE seq_scriva_cod_gruppo_soggetto IS 'applicativa, utilizzata nel campo : scriva_t_gruppo_soggetto.cod_gruppo_soggetto.
Regola : <SIGLA>-<progressivo assoluto>

<SIGLA>= ''GRP''
<progressivo assoluto> = seq_scriva_cod_gruppo_soggetto.nextval

es: GRP1234
';
-- DROP SEQUENCE seq_scriva_cod_istanza;

CREATE SEQUENCE seq_scriva_cod_istanza
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
COMMENT ON SEQUENCE seq_scriva_cod_istanza IS 'applicativa, utilizzata nel campo : scriva_t_istanza.cod_istanza.
Regola : <CODICE TIPO ADEMPIMENTO>-<COD ADEMPIMENTO>-<seq_scriva_cod_istanza >

<CODICE TIPO ADEMPIMENTO>= specifico es. VIA
<CODICE ADEMPIMENTO>= specifico es. SPE
<progressivo assoluto> = seq_scriva_cod_istanza.nextval

es: VIA-VER-1234
';
-- DROP SEQUENCE seq_scriva_cod_recapito_alternativo;

CREATE SEQUENCE seq_scriva_cod_recapito_alternativo
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
COMMENT ON SEQUENCE seq_scriva_cod_recapito_alternativo IS 'applicativa, utilizzata nel campo : scriva_r_recapito_alternativo.cod_recapito_alternativo.
Regola : <progressivo assoluto>

<progressivo assoluto> = seq_scriva_cod_recapito_alternativo.nextval

es: 1234
';
-- DROP SEQUENCE seq_scriva_cod_scriva;

CREATE SEQUENCE seq_scriva_cod_scriva
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
COMMENT ON SEQUENCE seq_scriva_cod_scriva IS 'applicativa, utilizzata nel campo : scriva_t_oggetto.cod_scriva.
Regola : <SISLA><seq_scriva_cod_istanza >

<SIGLA>= ''SCRV''
<progressivo assoluto> = seq_scriva_cod_scriva.nextval

es: SCRV1234
';
-- DROP SEQUENCE seq_scriva_geo_area_ogg_istanza;

CREATE SEQUENCE seq_scriva_geo_area_ogg_istanza
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_geo_id_geometrico;

CREATE SEQUENCE seq_scriva_geo_id_geometrico
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
COMMENT ON SEQUENCE seq_scriva_geo_id_geometrico IS 'applicativa, utilizzata nel campo : scriva_geo_***_ogg_istanza.id_geometrico.
Regola : <progressivo assoluto>

<progressivo assoluto> = seq_scriva_geo_id_geometrico.nextval

es: 1234
';
-- DROP SEQUENCE seq_scriva_geo_linea_ogg_istanza;

CREATE SEQUENCE seq_scriva_geo_linea_ogg_istanza
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_geo_punto_ogg_istanza;

CREATE SEQUENCE seq_scriva_geo_punto_ogg_istanza
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_log_acq_allegato;

CREATE SEQUENCE seq_scriva_log_acq_allegato
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_log_acq_istanza;

CREATE SEQUENCE seq_scriva_log_acq_istanza
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_log_acq_soggetto;

CREATE SEQUENCE seq_scriva_log_acq_soggetto
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_r_catasto_ubi_ogg_ist;

CREATE SEQUENCE seq_scriva_r_catasto_ubi_ogg_ist
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_r_competenza_responsabile;

CREATE SEQUENCE seq_scriva_r_competenza_responsabile
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_r_compilante_preferenza;

CREATE SEQUENCE seq_scriva_r_compilante_preferenza
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_r_compilante_preferenza_cn;

CREATE SEQUENCE seq_scriva_r_compilante_preferenza_cn
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_r_integra_istanza_allegato;

CREATE SEQUENCE seq_scriva_r_integra_istanza_allegato
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_r_istanza_attore;

CREATE SEQUENCE seq_scriva_r_istanza_attore
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_r_istanza_evento;

CREATE SEQUENCE seq_scriva_r_istanza_evento
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_r_istanza_osservazione;

CREATE SEQUENCE seq_scriva_r_istanza_osservazione
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_r_istanza_responsabile;

CREATE SEQUENCE seq_scriva_r_istanza_responsabile
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_r_istanza_storico;

CREATE SEQUENCE seq_scriva_r_istanza_storico
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_r_istanza_vincolo_aut;

CREATE SEQUENCE seq_scriva_r_istanza_vincolo_aut
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_r_nota_istanza;

CREATE SEQUENCE seq_scriva_r_nota_istanza
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_r_notifica_allegato;

CREATE SEQUENCE seq_scriva_r_notifica_allegato
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_r_notifica_applicativa;

CREATE SEQUENCE seq_scriva_r_notifica_applicativa
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_r_ogg_area_protetta;

CREATE SEQUENCE seq_scriva_r_ogg_area_protetta
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_r_ogg_natura_2000;

CREATE SEQUENCE seq_scriva_r_ogg_natura_2000
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_r_ogg_vincolo_autorizza;

CREATE SEQUENCE seq_scriva_r_ogg_vincolo_autorizza
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_r_pagamento_istanza;

CREATE SEQUENCE seq_scriva_r_pagamento_istanza
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_r_recapito_alternativo;

CREATE SEQUENCE seq_scriva_r_recapito_alternativo
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_r_referente_istanza;

CREATE SEQUENCE seq_scriva_r_referente_istanza
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_r_registro_elabora;

CREATE SEQUENCE seq_scriva_r_registro_elabora
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_r_richiesta_accredito;

CREATE SEQUENCE seq_scriva_r_richiesta_accredito
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_r_ubica_oggetto;

CREATE SEQUENCE seq_scriva_r_ubica_oggetto
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_r_ubica_oggetto_istanza;

CREATE SEQUENCE seq_scriva_r_ubica_oggetto_istanza
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_s_catasto_ubi_ogg_ist;

CREATE SEQUENCE seq_scriva_s_catasto_ubi_ogg_ist
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_s_gruppo_soggetto;

CREATE SEQUENCE seq_scriva_s_gruppo_soggetto
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_s_istanza_competenza;

CREATE SEQUENCE seq_scriva_s_istanza_competenza
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_s_istanza_vincolo_aut;

CREATE SEQUENCE seq_scriva_s_istanza_vincolo_aut
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_s_ogg_area_protetta;

CREATE SEQUENCE seq_scriva_s_ogg_area_protetta
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_s_ogg_istanza_categoria;

CREATE SEQUENCE seq_scriva_s_ogg_istanza_categoria
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_s_ogg_natura_2000;

CREATE SEQUENCE seq_scriva_s_ogg_natura_2000
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_s_ogg_vincolo_autorizza;

CREATE SEQUENCE seq_scriva_s_ogg_vincolo_autorizza
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_s_oggetto;

CREATE SEQUENCE seq_scriva_s_oggetto
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_s_oggetto_ist_figlio;

CREATE SEQUENCE seq_scriva_s_oggetto_ist_figlio
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_s_oggetto_istanza;

CREATE SEQUENCE seq_scriva_s_oggetto_istanza
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_s_recapito_alternativo;

CREATE SEQUENCE seq_scriva_s_recapito_alternativo
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_s_soggetto;

CREATE SEQUENCE seq_scriva_s_soggetto
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_s_soggetto_gruppo;

CREATE SEQUENCE seq_scriva_s_soggetto_gruppo
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_s_soggetto_istanza;

CREATE SEQUENCE seq_scriva_s_soggetto_istanza
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_s_ubica_oggetto;

CREATE SEQUENCE seq_scriva_s_ubica_oggetto
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_s_ubica_oggetto_istanza;

CREATE SEQUENCE seq_scriva_s_ubica_oggetto_istanza
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_t_allegato_istanza;

CREATE SEQUENCE seq_scriva_t_allegato_istanza
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_t_compilante;

CREATE SEQUENCE seq_scriva_t_compilante
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_t_elabora;

CREATE SEQUENCE seq_scriva_t_elabora
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_t_gruppo_soggetto;

CREATE SEQUENCE seq_scriva_t_gruppo_soggetto
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_t_integrazione_istanza;

CREATE SEQUENCE seq_scriva_t_integrazione_istanza
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_t_istanza;

CREATE SEQUENCE seq_scriva_t_istanza
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_t_notifica;

CREATE SEQUENCE seq_scriva_t_notifica
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_t_oggetto;

CREATE SEQUENCE seq_scriva_t_oggetto
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_t_oggetto_istanza;

CREATE SEQUENCE seq_scriva_t_oggetto_istanza
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_t_soggetto;

CREATE SEQUENCE seq_scriva_t_soggetto
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_t_soggetto_istanza;

CREATE SEQUENCE seq_scriva_t_soggetto_istanza
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;
-- DROP SEQUENCE seq_scriva_t_tentativo_pagamento;

CREATE SEQUENCE seq_scriva_t_tentativo_pagamento
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	NO CYCLE;-- scriva.bck_scriva_w_acq_soggetto_mc definition


ALTER SEQUENCE seq_scriva_cod_allegato                 OWNER TO scriva;
ALTER SEQUENCE seq_scriva_cod_gruppo_soggetto          OWNER TO scriva;
ALTER SEQUENCE seq_scriva_cod_istanza                  OWNER TO scriva;
ALTER SEQUENCE seq_scriva_cod_recapito_alternativo     OWNER TO scriva;
ALTER SEQUENCE seq_scriva_cod_scriva                   OWNER TO scriva;
ALTER SEQUENCE seq_scriva_geo_area_ogg_istanza         OWNER TO scriva;
ALTER SEQUENCE seq_scriva_geo_id_geometrico            OWNER TO scriva;
ALTER SEQUENCE seq_scriva_geo_linea_ogg_istanza        OWNER TO scriva;
ALTER SEQUENCE seq_scriva_geo_punto_ogg_istanza        OWNER TO scriva;
ALTER SEQUENCE seq_scriva_log_acq_allegato             OWNER TO scriva;
ALTER SEQUENCE seq_scriva_log_acq_istanza              OWNER TO scriva;
ALTER SEQUENCE seq_scriva_log_acq_soggetto             OWNER TO scriva;
ALTER SEQUENCE seq_scriva_r_catasto_ubi_ogg_ist        OWNER TO scriva;
ALTER SEQUENCE seq_scriva_r_competenza_responsabile    OWNER TO scriva;
ALTER SEQUENCE seq_scriva_r_compilante_preferenza      OWNER TO scriva;
ALTER SEQUENCE seq_scriva_r_compilante_preferenza_cn   OWNER TO scriva;
ALTER SEQUENCE seq_scriva_r_integra_istanza_allegato   OWNER TO scriva;
ALTER SEQUENCE seq_scriva_r_istanza_attore             OWNER TO scriva;
ALTER SEQUENCE seq_scriva_r_istanza_evento             OWNER TO scriva;
ALTER SEQUENCE seq_scriva_r_istanza_osservazione       OWNER TO scriva;
ALTER SEQUENCE seq_scriva_r_istanza_responsabile       OWNER TO scriva;
ALTER SEQUENCE seq_scriva_r_istanza_storico            OWNER TO scriva;
ALTER SEQUENCE seq_scriva_r_istanza_vincolo_aut        OWNER TO scriva;
ALTER SEQUENCE seq_scriva_r_nota_istanza               OWNER TO scriva;
ALTER SEQUENCE seq_scriva_r_notifica_allegato          OWNER TO scriva;
ALTER SEQUENCE seq_scriva_r_notifica_applicativa       OWNER TO scriva;
ALTER SEQUENCE seq_scriva_r_ogg_area_protetta          OWNER TO scriva;
ALTER SEQUENCE seq_scriva_r_ogg_natura_2000            OWNER TO scriva;
ALTER SEQUENCE seq_scriva_r_ogg_vincolo_autorizza      OWNER TO scriva;
ALTER SEQUENCE seq_scriva_r_pagamento_istanza          OWNER TO scriva;
ALTER SEQUENCE seq_scriva_r_recapito_alternativo       OWNER TO scriva;
ALTER SEQUENCE seq_scriva_r_referente_istanza          OWNER TO scriva;
ALTER SEQUENCE seq_scriva_r_registro_elabora           OWNER TO scriva;
ALTER SEQUENCE seq_scriva_r_richiesta_accredito        OWNER TO scriva;
ALTER SEQUENCE seq_scriva_r_ubica_oggetto              OWNER TO scriva;
ALTER SEQUENCE seq_scriva_r_ubica_oggetto_istanza      OWNER TO scriva;
ALTER SEQUENCE seq_scriva_s_catasto_ubi_ogg_ist        OWNER TO scriva;
ALTER SEQUENCE seq_scriva_s_gruppo_soggetto            OWNER TO scriva;
ALTER SEQUENCE seq_scriva_s_istanza_competenza         OWNER TO scriva;
ALTER SEQUENCE seq_scriva_s_istanza_vincolo_aut        OWNER TO scriva;
ALTER SEQUENCE seq_scriva_s_ogg_area_protetta          OWNER TO scriva;
ALTER SEQUENCE seq_scriva_s_ogg_istanza_categoria      OWNER TO scriva;
ALTER SEQUENCE seq_scriva_s_ogg_natura_2000            OWNER TO scriva;
ALTER SEQUENCE seq_scriva_s_ogg_vincolo_autorizza      OWNER TO scriva;
ALTER SEQUENCE seq_scriva_s_oggetto                    OWNER TO scriva;
ALTER SEQUENCE seq_scriva_s_oggetto_ist_figlio         OWNER TO scriva;
ALTER SEQUENCE seq_scriva_s_oggetto_istanza            OWNER TO scriva;
ALTER SEQUENCE seq_scriva_s_recapito_alternativo       OWNER TO scriva;
ALTER SEQUENCE seq_scriva_s_soggetto                   OWNER TO scriva;
ALTER SEQUENCE seq_scriva_s_soggetto_gruppo            OWNER TO scriva;
ALTER SEQUENCE seq_scriva_s_soggetto_istanza           OWNER TO scriva;
ALTER SEQUENCE seq_scriva_s_ubica_oggetto              OWNER TO scriva;
ALTER SEQUENCE seq_scriva_s_ubica_oggetto_istanza      OWNER TO scriva;
ALTER SEQUENCE seq_scriva_t_allegato_istanza           OWNER TO scriva;
ALTER SEQUENCE seq_scriva_t_compilante                 OWNER TO scriva;
ALTER SEQUENCE seq_scriva_t_elabora                    OWNER TO scriva;
ALTER SEQUENCE seq_scriva_t_gruppo_soggetto            OWNER TO scriva;
ALTER SEQUENCE seq_scriva_t_integrazione_istanza       OWNER TO scriva;
ALTER SEQUENCE seq_scriva_t_istanza                    OWNER TO scriva;
ALTER SEQUENCE seq_scriva_t_notifica                   OWNER TO scriva;
ALTER SEQUENCE seq_scriva_t_oggetto                    OWNER TO scriva;
ALTER SEQUENCE seq_scriva_t_oggetto_istanza            OWNER TO scriva;
ALTER SEQUENCE seq_scriva_t_soggetto                   OWNER TO scriva;
ALTER SEQUENCE seq_scriva_t_soggetto_istanza           OWNER TO scriva;
ALTER SEQUENCE seq_scriva_t_tentativo_pagamento        OWNER TO scriva;

GRANT ALL ON SEQUENCE seq_scriva_cod_allegato                   TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_cod_gruppo_soggetto            TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_cod_istanza                    TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_cod_recapito_alternativo       TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_cod_scriva                     TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_geo_area_ogg_istanza           TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_geo_id_geometrico              TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_geo_linea_ogg_istanza          TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_geo_punto_ogg_istanza          TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_log_acq_allegato               TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_log_acq_istanza                TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_log_acq_soggetto               TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_r_catasto_ubi_ogg_ist          TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_r_competenza_responsabile      TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_r_compilante_preferenza        TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_r_compilante_preferenza_cn     TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_r_integra_istanza_allegato     TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_r_istanza_attore               TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_r_istanza_evento               TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_r_istanza_osservazione         TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_r_istanza_responsabile         TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_r_istanza_storico              TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_r_istanza_vincolo_aut          TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_r_nota_istanza                 TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_r_notifica_allegato            TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_r_notifica_applicativa         TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_r_ogg_area_protetta            TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_r_ogg_natura_2000              TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_r_ogg_vincolo_autorizza        TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_r_pagamento_istanza            TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_r_recapito_alternativo         TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_r_referente_istanza            TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_r_registro_elabora             TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_r_richiesta_accredito          TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_r_ubica_oggetto                TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_r_ubica_oggetto_istanza        TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_s_catasto_ubi_ogg_ist          TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_s_gruppo_soggetto              TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_s_istanza_competenza           TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_s_istanza_vincolo_aut          TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_s_ogg_area_protetta            TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_s_ogg_istanza_categoria        TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_s_ogg_natura_2000              TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_s_ogg_vincolo_autorizza        TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_s_oggetto                      TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_s_oggetto_ist_figlio           TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_s_oggetto_istanza              TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_s_recapito_alternativo         TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_s_soggetto                     TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_s_soggetto_gruppo              TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_s_soggetto_istanza             TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_s_ubica_oggetto                TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_s_ubica_oggetto_istanza        TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_t_allegato_istanza             TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_t_compilante                   TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_t_elabora                      TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_t_gruppo_soggetto              TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_t_integrazione_istanza         TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_t_istanza                      TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_t_notifica                     TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_t_oggetto                      TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_t_oggetto_istanza              TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_t_soggetto                     TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_t_soggetto_istanza             TO scriva;
GRANT ALL ON SEQUENCE seq_scriva_t_tentativo_pagamento          TO scriva;

GRANT ALL ON SEQUENCE seq_scriva_cod_allegato                   TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_cod_gruppo_soggetto            TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_cod_istanza                    TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_cod_recapito_alternativo       TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_cod_scriva                     TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_geo_area_ogg_istanza           TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_geo_id_geometrico              TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_geo_linea_ogg_istanza          TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_geo_punto_ogg_istanza          TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_log_acq_allegato               TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_log_acq_istanza                TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_log_acq_soggetto               TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_r_catasto_ubi_ogg_ist          TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_r_competenza_responsabile      TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_r_compilante_preferenza        TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_r_compilante_preferenza_cn     TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_r_integra_istanza_allegato     TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_r_istanza_attore               TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_r_istanza_evento               TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_r_istanza_osservazione         TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_r_istanza_responsabile         TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_r_istanza_storico              TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_r_istanza_vincolo_aut          TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_r_nota_istanza                 TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_r_notifica_allegato            TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_r_notifica_applicativa         TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_r_ogg_area_protetta            TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_r_ogg_natura_2000              TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_r_ogg_vincolo_autorizza        TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_r_pagamento_istanza            TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_r_recapito_alternativo         TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_r_referente_istanza            TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_r_registro_elabora             TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_r_richiesta_accredito          TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_r_ubica_oggetto                TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_r_ubica_oggetto_istanza        TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_s_catasto_ubi_ogg_ist          TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_s_gruppo_soggetto              TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_s_istanza_competenza           TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_s_istanza_vincolo_aut          TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_s_ogg_area_protetta            TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_s_ogg_istanza_categoria        TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_s_ogg_natura_2000              TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_s_ogg_vincolo_autorizza        TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_s_oggetto                      TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_s_oggetto_ist_figlio           TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_s_oggetto_istanza              TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_s_recapito_alternativo         TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_s_soggetto                     TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_s_soggetto_gruppo              TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_s_soggetto_istanza             TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_s_ubica_oggetto                TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_s_ubica_oggetto_istanza        TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_t_allegato_istanza             TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_t_compilante                   TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_t_elabora                      TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_t_gruppo_soggetto              TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_t_integrazione_istanza         TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_t_istanza                      TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_t_notifica                     TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_t_oggetto                      TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_t_oggetto_istanza              TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_t_soggetto                     TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_t_soggetto_istanza             TO scriva_rw;
GRANT ALL ON SEQUENCE seq_scriva_t_tentativo_pagamento          TO scriva_rw;