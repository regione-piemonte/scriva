/* *****************************************************
 * Copyright Regione Piemonte - 2025
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************/


-- scriva_d_ambito definition

-- Drop table

-- DROP TABLE scriva_d_ambito;

CREATE TABLE scriva_d_ambito (
	id_ambito int4 NOT NULL,
	cod_ambito varchar(20) NOT NULL,
	des_ambito varchar(50) NOT NULL,
	des_estesa_ambito varchar(200) NULL,
	flg_attivo numeric(1) NOT NULL, -- 0 - non attivo¶1- attivo
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_ambito PRIMARY KEY (id_ambito)
);
CREATE UNIQUE INDEX ak_scriva_d_ambito_01 ON scriva_d_ambito USING btree (cod_ambito);
COMMENT ON TABLE scriva_d_ambito IS 'Si può intendere come la macro-tematica a cui un procedimento afferisce. Ad esempio AMBIENTE e'' un ambito.  ES. AMB - Ambiente AES - Attività Estrattive';

-- Column comments

COMMENT ON COLUMN scriva_d_ambito.flg_attivo IS '0 - non attivo
1- attivo';


-- scriva_d_categoria_allegato definition

-- Drop table

-- DROP TABLE scriva_d_categoria_allegato;

CREATE TABLE scriva_d_categoria_allegato (
	id_categoria_allegato int4 NOT NULL,
	cod_categoria_allegato varchar(20) NOT NULL,
	des_categoria_allegato varchar(300) NULL,
	ordinamento_categoria_allegato int4 NULL,
	flg_attivo numeric(1) NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_d_categoria_allegato_01 CHECK ((flg_attivo = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_d_categoria_allegato PRIMARY KEY (id_categoria_allegato)
);
CREATE UNIQUE INDEX ak_scriva_d_categoria_allegato_01 ON scriva_d_categoria_allegato USING btree (cod_categoria_allegato);


-- scriva_d_categoria_oggetto definition

-- Drop table

-- DROP TABLE scriva_d_categoria_oggetto;

CREATE TABLE scriva_d_categoria_oggetto (
	id_categoria_oggetto int4 NOT NULL,
	cod_categoria_oggetto varchar(20) NOT NULL,
	des_categoria_oggetto varchar(500) NULL,
	des_categoria_oggetto_estesa varchar(4000) NULL,
	ordinamento_categoria_oggetto numeric(4) NOT NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	ind_visibile varchar(20) DEFAULT 'FO'::character varying NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_categoria_oggetto PRIMARY KEY (id_categoria_oggetto)
);
CREATE UNIQUE INDEX ak_scriva_d_categoria_oggetto_01 ON scriva_d_categoria_oggetto USING btree (cod_categoria_oggetto, data_inizio_validita);
COMMENT ON TABLE scriva_d_categoria_oggetto IS 'Definisce le categorie oggetto previste nell''applicativo.';


-- scriva_d_classe_allegato definition

-- Drop table

-- DROP TABLE scriva_d_classe_allegato;

CREATE TABLE scriva_d_classe_allegato (
	id_classe_allegato int4 NOT NULL,
	cod_classe_allegato varchar(20) NOT NULL,
	des_classe_allegato varchar(100) NOT NULL,
	ordinamento_classe_allegato int4 NULL,
	flg_attivo numeric(1) DEFAULT 0 NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_sscriva_d_classe_allegato_01 CHECK ((flg_attivo = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_d_classe_allegato PRIMARY KEY (id_classe_allegato)
);
CREATE UNIQUE INDEX ak_scriva_d_classe_allegato_01 ON scriva_d_classe_allegato USING btree (cod_classe_allegato);


-- scriva_d_componente_app definition

-- Drop table

-- DROP TABLE scriva_d_componente_app;

CREATE TABLE scriva_d_componente_app (
	id_componente_app int4 NOT NULL,
	cod_componente_app varchar(20) NOT NULL,
	des_componente_app varchar(50) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_componente_app PRIMARY KEY (id_componente_app)
);
COMMENT ON TABLE scriva_d_componente_app IS 'Identifica la componente Applicativa.  ES.  FO-Front Office – si intendono le funzionalità ad uso del privato cittadino per la presentazione delle richieste di adempimento, di integrazione dei documenti.  BO-Back Office – si intendono le funzionalità ad uso del funzionario di pubblica amministrazione per la gestione dell’iter dell’adempimento a fronte della presentazione della richiesta da parte di un privato cittadino.';



-- scriva_d_config_geeco definition

-- Drop table

-- DROP TABLE scriva_d_config_geeco;

CREATE TABLE scriva_d_config_geeco (
	id_config_geeco int4 NOT NULL,
	uuid_geeco varchar(64) NOT NULL, -- E' l'identità univoca con cui l'applicativo Geeco identifica univocamente la TOC all'interno del proprio sistema.
	env_geeco varchar(20) NULL,
	version_geeco varchar(20) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_config_geeco PRIMARY KEY (id_config_geeco)
);
COMMENT ON TABLE scriva_d_config_geeco IS 'Per nuova configurazione GEECO si intendono i layout di rappresentazione grafica degli oggetti previsti per un certo adempimento e concordati con il gruppo di GEECO (chiamata ''carta di identita dell''adempimento'' in GEECO)  Per ogni configurazione, GEECO fornisce inoltre gli identificativi dei layer su cui e'' possibile disegnare le geometrie. Questi identificativi vanno censiti nella tabella scriva_r_geeco_layer_virtuali.';

-- Column comments

COMMENT ON COLUMN scriva_d_config_geeco.uuid_geeco IS 'E'' l''identità univoca con cui l''applicativo Geeco identifica univocamente la TOC all''interno del proprio sistema.';


-- scriva_d_configurazione definition

-- Drop table

-- DROP TABLE scriva_d_configurazione;

CREATE TABLE scriva_d_configurazione (
	chiave varchar(50) NOT NULL, -- Es. Modalita di accreditamento
	valore varchar(1000) NOT NULL, -- 0-Usa ACCRA¶1-Usa solo SCRIVA
	note varchar(100) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_configurazione PRIMARY KEY (chiave)
);
COMMENT ON TABLE scriva_d_configurazione IS 'Contiene tutti i parametri di sistema necessari per l’integrazione con sistemi esterni, la gestione di processi trasversali (come invio mail), la gestione file';

-- Column comments

COMMENT ON COLUMN scriva_d_configurazione.chiave IS 'Es. Modalita di accreditamento';
COMMENT ON COLUMN scriva_d_configurazione.valore IS '0-Usa ACCRA
1-Usa solo SCRIVA';


-- scriva_d_continente definition

-- Drop table

-- DROP TABLE scriva_d_continente;

CREATE TABLE scriva_d_continente (
	id_continente numeric(2) NOT NULL,
	denom_continente varchar(30) NOT NULL,
	CONSTRAINT ak_scriva_d_continente_01 UNIQUE (denom_continente),
	CONSTRAINT pk_scriva_d_continente PRIMARY KEY (id_continente)
);




-- scriva_d_ente_creditore definition

-- Drop table

-- DROP TABLE scriva_d_ente_creditore;

CREATE TABLE scriva_d_ente_creditore (
	id_ente_creditore int4 NOT NULL,
	cf_ente_creditore varchar(16) NOT NULL,
	denominazione_ente_creditore varchar(50) NULL,
	indirizzo_tesoreria varchar(100) NULL,
	iban_accredito varchar(30) NULL,
	bic_accredito varchar(12) NULL,
	flg_attivo numeric(1) NOT NULL,
	flg_aderisce_piemontepay numeric(1) DEFAULT 0 NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_d_ente_creditore_01 CHECK ((flg_attivo = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_d_ente_creditore_02 CHECK ((flg_aderisce_piemontepay = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_d_ente_creditore PRIMARY KEY (id_ente_creditore)
);
CREATE UNIQUE INDEX ak_scriva_d_ente_creditore_01 ON scriva_d_ente_creditore USING btree (cf_ente_creditore);


-- scriva_d_esito_procedimento definition

-- Drop table

-- DROP TABLE scriva_d_esito_procedimento;

CREATE TABLE scriva_d_esito_procedimento (
	id_esito_procedimento int4 NOT NULL,
	cod_esito_procedimento varchar(20) NOT NULL,
	des_esito_procedimento varchar(100) NULL,
	flg_positivo int4 NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_esito_procedimento PRIMARY KEY (id_esito_procedimento),
	CONSTRAINT valid_0_1 CHECK ((flg_positivo = ANY (ARRAY[0, 1])))
);
CREATE UNIQUE INDEX ak_scriva_d_esito_procedimento_01 ON scriva_d_esito_procedimento USING btree (cod_esito_procedimento);


-- scriva_d_funzionalita definition

-- Drop table

-- DROP TABLE scriva_d_funzionalita;

CREATE TABLE scriva_d_funzionalita (
	id_funzionalita int4 NOT NULL, -- Identificativo univoco
	cod_funzionalita varchar(3) NOT NULL,
	des_funzionalita varchar(50) NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT ak_scriva_d_funzionalita_01 UNIQUE (cod_funzionalita),
	CONSTRAINT pk_scriva_d_funzionalita PRIMARY KEY (id_funzionalita)
);
COMMENT ON TABLE scriva_d_funzionalita IS 'Permette di definire le funzionalità/contesto applicativo.  Es. Stampe, Aggiornamento Limiti ...... etc .....';

-- Column comments

COMMENT ON COLUMN scriva_d_funzionalita.id_funzionalita IS 'Identificativo univoco';


-- scriva_d_gestione_attore definition

-- Drop table

-- DROP TABLE scriva_d_gestione_attore;

CREATE TABLE scriva_d_gestione_attore (
	id_gestione_attore int4 NOT NULL,
	cod_gestione_attore varchar(20) NOT NULL,
	des_gestione_attore varchar(50) NULL,
	ordinamento_gestione_attore int4 NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_gestione_attore PRIMARY KEY (id_gestione_attore)
);
CREATE UNIQUE INDEX ak_scriva_d_gestione_attore_01 ON scriva_d_gestione_attore USING btree (cod_gestione_attore);


-- scriva_d_gruppo_pagamento definition

-- Drop table

-- DROP TABLE scriva_d_gruppo_pagamento;

CREATE TABLE scriva_d_gruppo_pagamento (
	id_gruppo_pagamento int4 NOT NULL,
	cod_gruppo_pagamento varchar(20) NOT NULL,
	des_gruppo_pagamento varchar(250) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_gruppo_pagamento PRIMARY KEY (id_gruppo_pagamento)
);
CREATE UNIQUE INDEX ak_scriva_d_gruppo_pagamento_01 ON scriva_d_gruppo_pagamento USING btree (cod_gruppo_pagamento);
COMMENT ON TABLE scriva_d_gruppo_pagamento IS 'Anagrafica dei raggruppamenti dei tipi di pagamento. Quelli attualmente previsti sono: Marca da bollo, Onere, Interesse, Sanzione';


-- scriva_d_informazioni_scriva definition

-- Drop table

-- DROP TABLE scriva_d_informazioni_scriva;

CREATE TABLE scriva_d_informazioni_scriva (
	id_informazione_scriva int4 NOT NULL,
	des_informazione_scriva varchar(50) NULL,
	cod_informazione_scriva varchar(20) NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_informazioni_scriva PRIMARY KEY (id_informazione_scriva)
);
CREATE UNIQUE INDEX ak_scriva_d_informazioni_scriva_01 ON scriva_d_informazioni_scriva USING btree (cod_informazione_scriva);


-- scriva_d_livello_help definition

-- Drop table

-- DROP TABLE scriva_d_livello_help;

CREATE TABLE scriva_d_livello_help (
	id_livello_help int4 NOT NULL,
	cod_livello_help bpchar(3) NOT NULL,
	des_livello_help varchar(50) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_livello_help PRIMARY KEY (id_livello_help)
);
CREATE UNIQUE INDEX ak_scriva_d_livello_help_01 ON scriva_d_livello_help USING btree (cod_livello_help);


-- scriva_d_mappa_fonte_esterna definition

-- Drop table

-- DROP TABLE scriva_d_mappa_fonte_esterna;

CREATE TABLE scriva_d_mappa_fonte_esterna (
	id_mappa_fonte_esterna int4 NOT NULL,
	cod_masterdata varchar(20) NOT NULL,
	info_fonte varchar(50) NOT NULL,
	cod_fonte varchar(50) NOT NULL,
	cod_scriva varchar(50) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_mappa_fonte_esterna PRIMARY KEY (id_mappa_fonte_esterna)
);
CREATE UNIQUE INDEX ak_scriva_d_mappa_fonte_est_01 ON scriva_d_mappa_fonte_esterna USING btree (cod_masterdata, info_fonte, cod_fonte);


-- scriva_d_masterdata definition

-- Drop table

-- DROP TABLE scriva_d_masterdata;

CREATE TABLE scriva_d_masterdata (
	id_masterdata int4 NOT NULL,
	des_masterdata varchar(50) NULL,
	cod_masterdata varchar(20) NOT NULL,
	ind_priorita_porting_sogg varchar(10) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_masterdata PRIMARY KEY (id_masterdata)
);
CREATE UNIQUE INDEX ak_scriva_d_masterdata_01 ON scriva_d_masterdata USING btree (cod_masterdata);


-- scriva_d_natura_oggetto definition

-- Drop table

-- DROP TABLE scriva_d_natura_oggetto;

CREATE TABLE scriva_d_natura_oggetto (
	id_natura_oggetto int4 NOT NULL,
	cod_natura_oggetto varchar(20) NOT NULL,
	des_natura_oggetto varchar(50) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_natura_oggetto PRIMARY KEY (id_natura_oggetto)
);



-- scriva_d_origine_limiti definition

-- Drop table

-- DROP TABLE scriva_d_origine_limiti;

CREATE TABLE scriva_d_origine_limiti (
	id_origine numeric(2) NOT NULL,
	cod_origine varchar(10) NOT NULL,
	desc_origine varchar(100) NOT NULL,
	CONSTRAINT pk_scriva_d_origine_limiti PRIMARY KEY (id_origine)
);
CREATE UNIQUE INDEX ak_scriva_d_origine_limiti_01 ON scriva_d_origine_limiti USING btree (cod_origine);
CREATE UNIQUE INDEX ak_scriva_d_origine_limiti_02 ON scriva_d_origine_limiti USING btree (desc_origine);


-- scriva_d_ruolo_compilante definition

-- Drop table

-- DROP TABLE scriva_d_ruolo_compilante;

CREATE TABLE scriva_d_ruolo_compilante (
	id_ruolo_compilante int4 NOT NULL,
	cod_ruolo_compilante varchar(20) NOT NULL,
	des_ruolo_compilante varchar(100) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_ruolo_compilante PRIMARY KEY (id_ruolo_compilante)
);
COMMENT ON TABLE scriva_d_ruolo_compilante IS 'Anagrafica dei ruoli compilanti contente tutti i ruoli necessari.  ES. : Titolare, Delegato, Legale rappresentante, Procuratore, Delegato con potere di firma.   Per i ruoli con potere di firma, diversi da Titolare e Legale rappresentante, e'' prevista una configurazione che permette di scaricare il modulo di delega con le informazioni anagrafiche del compilante precompilate (configurazione in tabella scriva_r_adempi_ruolo_compila)';


-- scriva_d_ruolo_soggetto definition

-- Drop table

-- DROP TABLE scriva_d_ruolo_soggetto;

CREATE TABLE scriva_d_ruolo_soggetto (
	id_ruolo_soggetto int4 NOT NULL,
	cod_ruolo_soggetto varchar(20) NOT NULL,
	des_ruolo_soggetto varchar(50) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_ruolo_soggetto PRIMARY KEY (id_ruolo_soggetto)
);
COMMENT ON TABLE scriva_d_ruolo_soggetto IS 'Anagrafica dei ruoli soggetto, contenete i ruoli previsti.  ES. 1 = Titolare;  2 = Legale Rappresentante';


-- scriva_d_stato_elabora definition

-- Drop table

-- DROP TABLE scriva_d_stato_elabora;

CREATE TABLE scriva_d_stato_elabora (
	id_stato_elabora int4 NOT NULL, -- Identificativo univoco
	cod_stato_elabora varchar(10) NOT NULL,
	des_stato_elabora varchar(50) NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT ak_scriva_d_stato_elabora_01 UNIQUE (cod_stato_elabora),
	CONSTRAINT pk_scriva_d_stato_elabora PRIMARY KEY (id_stato_elabora)
);
COMMENT ON TABLE scriva_d_stato_elabora IS 'Permette di definire gli stati di elaborazione :  - RICHIESTA  - AVVIATA - TERMINATA_KO  - TERMINATA';

-- Column comments

COMMENT ON COLUMN scriva_d_stato_elabora.id_stato_elabora IS 'Identificativo univoco';


-- scriva_d_stato_istanza definition

-- Drop table

-- DROP TABLE scriva_d_stato_istanza;

CREATE TABLE scriva_d_stato_istanza (
	id_stato_istanza int4 NOT NULL,
	cod_stato_istanza varchar(20) NOT NULL,
	des_stato_istanza varchar(50) NULL,
	flg_storico_istanza numeric(1) DEFAULT 0 NOT NULL,
	ind_visibile varchar(20) NULL,
	des_estesa_stato_istanza varchar(150) NULL,
	label_stato varchar(50) NULL,
	psw_ruolo varchar(32) NULL,
	flg_aggiorna_oggetto int4 NULL, -- Quando istanza passa in questo stato indica se occorre aggiornare anagrafica oggetto  Indica in quale stato dell’istanza occorre copiare i dati da oggetto-istanza a oggetto-anagrafica (dati generali, ubicazione, legame con altri oggetti) 
	ind_ricerca_oggetto varchar(150) DEFAULT 'N'::character varying NOT NULL, -- Indica se ricercare l'istanza quando si effettua una ricerca oggetto e con quali modalità:¶- S = da considerare nella ricerca¶- N = non considerare¶- <codice esito conclusione> = ricercare se esito corrisponde
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	ind_aggiorna_oggetto varchar(150) DEFAULT 'N'::character varying NOT NULL, -- Potra' assumere uno dei seguenti valori : N|S|E¶N (non considerare e deve essere il valore di default)¶S (SI considerare per aggiornare oggetto-anagrafica)¶E (ESITI il sistema deve verificare che l'esito dell'istanza abbia scriva_d_esito.flg_positivo=1
	CONSTRAINT chk_scriva_d_stato_istanza_01 CHECK ((flg_storico_istanza = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_d_stato_istanza PRIMARY KEY (id_stato_istanza),
	CONSTRAINT valid_0_1 CHECK ((flg_aggiorna_oggetto = ANY (ARRAY[0, 1])))
);
COMMENT ON TABLE scriva_d_stato_istanza IS 'Contiene tutti gli stati che si prevede una istanza di procedimento possa assumere.  ES. BOZZA - Bozza DA FIRMARE - Da Firmare FIRMATA - Firmata';

-- Column comments

COMMENT ON COLUMN scriva_d_stato_istanza.flg_aggiorna_oggetto IS 'Quando istanza passa in questo stato indica se occorre aggiornare anagrafica oggetto  Indica in quale stato dell’istanza occorre copiare i dati da oggetto-istanza a oggetto-anagrafica (dati generali, ubicazione, legame con altri oggetti) ';
COMMENT ON COLUMN scriva_d_stato_istanza.ind_ricerca_oggetto IS 'Indica se ricercare l''istanza quando si effettua una ricerca oggetto e con quali modalità:
- S = da considerare nella ricerca
- N = non considerare
- <codice esito conclusione> = ricercare se esito corrisponde';
COMMENT ON COLUMN scriva_d_stato_istanza.ind_aggiorna_oggetto IS 'Potra'' assumere uno dei seguenti valori : N|S|E
N (non considerare e deve essere il valore di default)
S (SI considerare per aggiornare oggetto-anagrafica)
E (ESITI il sistema deve verificare che l''esito dell''istanza abbia scriva_d_esito.flg_positivo=1';


-- scriva_d_stato_notifica definition

-- Drop table

-- DROP TABLE scriva_d_stato_notifica;

CREATE TABLE scriva_d_stato_notifica (
	id_stato_notifica int4 NOT NULL,
	cod_stato_notifica varchar(20) NOT NULL,
	des_stato_notifica varchar(150) NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT ak_scriva_d_stato_notifica_01 UNIQUE (cod_stato_notifica),
	CONSTRAINT pk_scriva_d_stato_notifica PRIMARY KEY (id_stato_notifica)
);
COMMENT ON TABLE scriva_d_stato_notifica IS 'Permette di specificare lo stato della notifica.
ES. CREATA, INVIATA, SUCCESSO,FALLITA';


-- scriva_d_stato_oggetto definition

-- Drop table

-- DROP TABLE scriva_d_stato_oggetto;

CREATE TABLE scriva_d_stato_oggetto (
	id_stato_oggetto int4 NOT NULL,
	cod_stato_oggetto varchar(20) NOT NULL,
	des_stato_oggetto varchar(50) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_stato_oggetto PRIMARY KEY (id_stato_oggetto)
);
COMMENT ON TABLE scriva_d_stato_oggetto IS 'Anagrafica di tutti gli stati possibili, a prescindere da uno specifico adempimento. Permette di specificare lo stato dell''oggetto di anagrafica, quando necessario o disponibile.  ES. NON_ESISTE - Opera non esistente/realizzata ESISTE - Opera esistente su Territorio DISMESSO - Opera dismessa o non più in esercizio RIMOSSO - Ripristinato stato naturale precedente';


-- scriva_d_stato_osservazione definition

-- Drop table

-- DROP TABLE scriva_d_stato_osservazione;

CREATE TABLE scriva_d_stato_osservazione (
	id_stato_osservazione int4 NOT NULL,
	cod_stato_osservazione varchar(20) NOT NULL,
	des_stato_osservazione varchar(100) NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_stato_osservazione PRIMARY KEY (id_stato_osservazione)
);
CREATE UNIQUE INDEX ak_scriva_d_stato_osservazione_01 ON scriva_d_stato_osservazione USING btree (cod_stato_osservazione);


-- scriva_d_stato_pagamento definition

-- Drop table

-- DROP TABLE scriva_d_stato_pagamento;

CREATE TABLE scriva_d_stato_pagamento (
	id_stato_pagamento int4 NOT NULL,
	cod_stato_pagamento varchar(20) NOT NULL,
	des_stato_pagamento varchar(100) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_stato_pagamento PRIMARY KEY (id_stato_pagamento)
);
CREATE UNIQUE INDEX ak_scriva_d_stato_pagamento_01 ON scriva_d_stato_pagamento USING btree (cod_stato_pagamento);


-- scriva_d_stato_proced_statale definition

-- Drop table

-- DROP TABLE scriva_d_stato_proced_statale;

CREATE TABLE scriva_d_stato_proced_statale (
	id_stato_proced_statale int4 NOT NULL,
	cod_stato_proced_statale varchar(20) NOT NULL,
	des_stato_proced_statale varchar(50) NULL,
	ind_visibile varchar(20) NULL,
	des_estesa_stato_proced_statale varchar(150) NULL,
	label_stato_proced_statale varchar(50) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT ak_scriva_d_stato_proced_statale_01 UNIQUE (cod_stato_proced_statale),
	CONSTRAINT pk_scriva_d_stato_proced_statale PRIMARY KEY (id_stato_proced_statale)
);
COMMENT ON TABLE scriva_d_stato_proced_statale IS 'Contiene tutti gli stati che si prevede una istanza di procedimento statale possa assumere.';


-- scriva_d_stato_tentativo_pag definition

-- Drop table

-- DROP TABLE scriva_d_stato_tentativo_pag;

CREATE TABLE scriva_d_stato_tentativo_pag (
	id_stato_tentativo_pag int4 NOT NULL,
	cod_stato_tentativo_pag varchar(20) NOT NULL,
	des_stato_tentativo_pag varchar(100) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_stato_tentativo_pag PRIMARY KEY (id_stato_tentativo_pag)
);
CREATE UNIQUE INDEX ak_scriva_d_stato_tentativo_pag_01 ON scriva_d_stato_tentativo_pag USING btree (cod_stato_tentativo_pag);
COMMENT ON TABLE scriva_d_stato_tentativo_pag IS 'Stati di tentativo di pagamento I valori attualmente previsti sono: 010 – Inviato, 020 – Fallito, 030 – Successo';


-- scriva_d_template_comunica definition

-- Drop table

-- DROP TABLE scriva_d_template_comunica;

CREATE TABLE scriva_d_template_comunica (
	id_template int4 NOT NULL,
	cod_template varchar(20) NULL,
	des_template varchar(50) NULL,
	des_oggetto varchar(150) NULL,
	des_corpo text NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_template_comunica PRIMARY KEY (id_template)
);


-- scriva_d_tipo_area_protetta definition

-- Drop table

-- DROP TABLE scriva_d_tipo_area_protetta;

CREATE TABLE scriva_d_tipo_area_protetta (
	id_tipo_area_protetta int4 NOT NULL,
	cod_tipo_area_protetta varchar(10) NOT NULL,
	des_tipo_area_protetta varchar(250) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_tipo_area_protetta PRIMARY KEY (id_tipo_area_protetta)
);
CREATE UNIQUE INDEX ak_scriva_d_tipo_area_protetta_01 ON scriva_d_tipo_area_protetta USING btree (cod_tipo_area_protetta);


-- scriva_d_tipo_competenza definition

-- Drop table

-- DROP TABLE scriva_d_tipo_competenza;

CREATE TABLE scriva_d_tipo_competenza (
	id_tipo_competenza int4 NOT NULL,
	cod_tipo_competenza varchar(20) NOT NULL,
	des_tipo_competenza varchar(100) NULL,
	des_tipo_competenza_estesa varchar(250) NULL,
	ordinamento_tipo_competenza int4 NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_tipo_competenza PRIMARY KEY (id_tipo_competenza)
);
CREATE UNIQUE INDEX ak_scriva_d_tipo_competenza_01 ON scriva_d_tipo_competenza USING btree (cod_tipo_competenza);
COMMENT ON TABLE scriva_d_tipo_competenza IS 'Definisce il perimetro di competenza dell''ente.  ES. NAZIONALE - Nazionale REGIONALE - Regionale PROVINCIALE - Provinciale COMUNALE -Comunale etc...';


-- scriva_d_tipo_destinatario definition

-- Drop table

-- DROP TABLE scriva_d_tipo_destinatario;

CREATE TABLE scriva_d_tipo_destinatario (
	id_tipo_destinatario int4 NOT NULL,
	cod_tipo_destinatario varchar(20) NOT NULL,
	des_tipo_destinatario varchar(150) NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT ak_scriva_d_tipo_destinatario_01 UNIQUE (cod_tipo_destinatario),
	CONSTRAINT pk_scriva_d_tipo_destinatario PRIMARY KEY (id_tipo_destinatario)
);
COMMENT ON TABLE scriva_d_tipo_destinatario IS 'Definisce i tipi di destinatari delle notifiche, gestiti dall''applicativo
Es.
ATTORE IN LINEA FO
ATTORE IN LINEA BO
PROFILO FO
PROFILO BO
PROFILO AC
ALTRO DESTINATARIO
ALTRO SISTEMA';


-- scriva_d_tipo_help definition

-- Drop table

-- DROP TABLE scriva_d_tipo_help;

CREATE TABLE scriva_d_tipo_help (
	id_tipo_help int4 NOT NULL,
	cod_tipo_help bpchar(3) NOT NULL,
	des_tipo_help varchar(50) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_tipo_help PRIMARY KEY (id_tipo_help)
);
CREATE UNIQUE INDEX ak_scriva_d_tipo_help_01 ON scriva_d_tipo_help USING btree (cod_tipo_help);


-- scriva_d_tipo_integrazione definition

-- Drop table

-- DROP TABLE scriva_d_tipo_integrazione;

CREATE TABLE scriva_d_tipo_integrazione (
	id_tipo_integrazione int4 NOT NULL,
	cod_tipo_integrazione varchar(20) NOT NULL,
	des_tipo_integrazione varchar(100) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_tipo_integrazione PRIMARY KEY (id_tipo_integrazione)
);
CREATE UNIQUE INDEX ak_scriva_d_tipo_integrazione_01 ON scriva_d_tipo_integrazione USING btree (cod_tipo_integrazione);
COMMENT ON TABLE scriva_d_tipo_integrazione IS 'Tipologie di integrazione istanza previste

Es.
1- P- Integrazione di perfezionamento
2- I- Integrazione post avvio';


-- scriva_d_tipo_messaggio definition

-- Drop table

-- DROP TABLE scriva_d_tipo_messaggio;

CREATE TABLE scriva_d_tipo_messaggio (
	id_tipo_messaggio int4 NOT NULL,
	cod_tipo_messaggio bpchar(1) NOT NULL,
	des_tipo_messaggio varchar(50) NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_tipo_messaggio PRIMARY KEY (id_tipo_messaggio)
);
COMMENT ON TABLE scriva_d_tipo_messaggio IS 'Configurazione dei tipi di messaggio ES. errore, alert, etc …';


-- scriva_d_tipo_natura_2000 definition

-- Drop table

-- DROP TABLE scriva_d_tipo_natura_2000;

CREATE TABLE scriva_d_tipo_natura_2000 (
	id_tipo_natura_2000 int4 NOT NULL,
	cod_tipo_natura_2000 varchar(10) NOT NULL,
	des_tipo_natura_2000 varchar(250) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_tipo_natura_2000 PRIMARY KEY (id_tipo_natura_2000)
);
CREATE UNIQUE INDEX ak_scriva_d_tipo_natura_2000_01 ON scriva_d_tipo_natura_2000 USING btree (cod_tipo_natura_2000);


-- scriva_d_tipo_natura_giuridica definition

-- Drop table

-- DROP TABLE scriva_d_tipo_natura_giuridica;

CREATE TABLE scriva_d_tipo_natura_giuridica (
	id_tipo_natura_giuridica int4 NOT NULL,
	cod_tipo_natura_giuridica varchar(20) NOT NULL,
	des_tipo_natura_giuridica varchar(80) NULL,
	sigla_tipo_natura_giuridica varchar(20) NULL,
	ordinamento_tipo_natura_giu int4 NULL,
	flg_pubblico numeric(1) DEFAULT 0 NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_d_tipo_natura_giuridica_01 CHECK ((flg_pubblico = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_d_tipo_natura_giuridica PRIMARY KEY (id_tipo_natura_giuridica)
);


-- scriva_d_tipo_notifica definition

-- Drop table

-- DROP TABLE scriva_d_tipo_notifica;

CREATE TABLE scriva_d_tipo_notifica (
	id_tipo_notifica int4 NOT NULL,
	cod_tipo_notifica varchar(20) NOT NULL,
	des_tipo_notifica varchar(150) NOT NULL,
	data_inizio date NOT NULL,
	data_fine date NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT ak_scriva_d_tipo_notifica_01 UNIQUE (cod_tipo_notifica),
	CONSTRAINT pk_scriva_d_tipo_notifica PRIMARY KEY (id_tipo_notifica)
);
COMMENT ON TABLE scriva_d_tipo_notifica IS 'Definisce i tipi di notifica gestiti dall''applicativo
Es.
Notifica di Avviso presentazione istanza
Notifica di Avviso richiesta integrazione';


-- scriva_d_tipo_oggetto_app definition

-- Drop table

-- DROP TABLE scriva_d_tipo_oggetto_app;

CREATE TABLE scriva_d_tipo_oggetto_app (
	id_tipo_ogg_app int4 NOT NULL,
	cod_tipo_ogg_app varchar(20) NOT NULL,
	des_tipo_ogg_app varchar(50) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_tipo_oggetto_app PRIMARY KEY (id_tipo_ogg_app)
);


-- scriva_d_tipo_quadro definition

-- Drop table

-- DROP TABLE scriva_d_tipo_quadro;

CREATE TABLE scriva_d_tipo_quadro (
	id_tipo_quadro int4 NOT NULL,
	cod_tipo_quadro varchar(20) NULL,
	des_tipo_quadro varchar(50) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_tipo_quadro PRIMARY KEY (id_tipo_quadro)
);
COMMENT ON TABLE scriva_d_tipo_quadro IS 'Identifica la tipologia del quadro.  - Orientamento - QDR_ORIENTAMENTO - Soggetto - QDR_SOGGETTO - Oggetto  - QDR_OGGETTO (OPERA, ATTIVITA’ , PIANO ..) - Dettaglio oggetto  - QDR_DETT_OGGETTO (categ progettuali, titoli abilitativi,..) - Dati tecnici - QDR_DATI_TECNICI  (derivazioni, pozzi, captazione, …) - Dettaglio procedimento - QDR_DETT_PROC (vincoli, autorizzazioni, info procedimento statale…) - Allegati - QDR_ALLEGATO - Pagamenti - QDR_PAGAMENTO - Dichiarazioni - QDR_DICHIARAZIONE - Conclusione procedimento – QDR_CONCLUSIONE - Riepilogo - QDR_RIEPILOGO';


-- scriva_d_tipo_responsabile definition

-- Drop table

-- DROP TABLE scriva_d_tipo_responsabile;

CREATE TABLE scriva_d_tipo_responsabile (
	id_tipo_responsabile int4 NOT NULL,
	cod_tipo_responsabile varchar(20) NOT NULL,
	des_tipo_responsabile varchar(100) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT ak_scriva_d_tipo_responsabile_01 UNIQUE (cod_tipo_responsabile),
	CONSTRAINT pk_scriva_d_tipo_responsabile PRIMARY KEY (id_tipo_responsabile)
);
COMMENT ON TABLE scriva_d_tipo_responsabile IS 'La tipologia permette definire il tipo di responsabile quando richiesto da uno specifico adempimento.
Es.
DIR_COMP - Direzione Competente
RESP_NC - Responsabile Nucleo Centrale
FUNZ_ISTR - Funzionario Istruttoria
RESP_PROC - Responsabile Procedimento';


-- scriva_d_tipo_soggetto definition

-- Drop table

-- DROP TABLE scriva_d_tipo_soggetto;

CREATE TABLE scriva_d_tipo_soggetto (
	id_tipo_soggetto int4 NOT NULL,
	cod_tipo_soggetto varchar(20) NOT NULL,
	des_tipo_soggetto varchar(50) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_tipo_soggetto PRIMARY KEY (id_tipo_soggetto)
);


-- scriva_d_tipo_vincolo_aut definition

-- Drop table

-- DROP TABLE scriva_d_tipo_vincolo_aut;

CREATE TABLE scriva_d_tipo_vincolo_aut (
	id_tipo_vincolo_aut int4 NOT NULL,
	cod_tipo_vincolo_aut bpchar(1) NOT NULL,
	des_tipo_vincolo_aut varchar(50) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_tipo_vincolo_aut PRIMARY KEY (id_tipo_vincolo_aut)
);
CREATE UNIQUE INDEX ak_scriva_d_tipo_vincolo_aut_01 ON scriva_d_tipo_vincolo_aut USING btree (cod_tipo_vincolo_aut);


-- scriva_s_catasto_ubi_ogg_ist definition

-- Drop table

-- DROP TABLE scriva_s_catasto_ubi_ogg_ist;

CREATE TABLE scriva_s_catasto_ubi_ogg_ist (
	id_catasto_ubi_ogg_ist_storico int4 NOT NULL,
	id_catasto_ubica_oggetto_ist int4 NOT NULL,
	id_ubica_oggetto_istanza int4 NOT NULL,
	sezione varchar(2) NULL,
	foglio numeric(5) NULL,
	particella varchar(10) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	ind_fonte_dato varchar(4) NULL,
	cod_belfiore varchar(4) NULL,
	CONSTRAINT pk_scriva_s_catasto_ubi_ogg_ist PRIMARY KEY (id_catasto_ubi_ogg_ist_storico)
);


-- scriva_s_funzionario definition

-- Drop table

-- DROP TABLE scriva_s_funzionario;

CREATE TABLE scriva_s_funzionario (
	id_funzionario_storico int4 NOT NULL,
	id_funzionario int4 NOT NULL,
	cf_funzionario varchar(16) NOT NULL,
	nome_funzionario varchar(100) NOT NULL,
	cognome_funzionario varchar(100) NOT NULL,
	num_telefono_funzionario varchar(25) NULL,
	des_email_funzionario varchar(100) NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_s_funzionario PRIMARY KEY (id_funzionario_storico)
);


-- scriva_s_gruppo_soggetto definition

-- Drop table

-- DROP TABLE scriva_s_gruppo_soggetto;

CREATE TABLE scriva_s_gruppo_soggetto (
	id_gruppo_soggetto_storico int4 NOT NULL,
	id_gruppo_soggetto int4 NOT NULL,
	cod_gruppo_soggetto varchar(20) NOT NULL,
	des_gruppo_soggetto varchar(250) NULL,
	flg_creazione_automatica numeric(1) NOT NULL,
	id_istanza_attore int4 NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	id_funzionario int4 NULL,
	data_aggiornamento timestamp NULL,
	CONSTRAINT pk_scriva_s_gruppo_soggetto PRIMARY KEY (id_gruppo_soggetto_storico)
);


-- scriva_s_istanza_competenza definition

-- Drop table

-- DROP TABLE scriva_s_istanza_competenza;

CREATE TABLE scriva_s_istanza_competenza (
	id_istanza_comp_storico int4 NOT NULL,
	id_istanza int4 NOT NULL,
	id_competenza_territorio int4 NOT NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	flg_autorita_principale numeric(1) NULL,
	flg_autorita_assegnata_bo numeric(1) NULL,
	ind_assegnata_da_sistema varchar(20) DEFAULT '0'::character varying NOT NULL, -- Indica la modalita' di assegnazione della competenza¶Es.¶0 – AC selezionata da utente in orientamento¶2 – AC attribuita da sistema con scriva_r_adempi_competenza.ind_adesione_adempimento = 2¶3 - AC attribuita da sistema con scriva_r_adempi_competenza.ind_adesione_adempimento = 3¶4 - AC calcolata da sistema per COMUNE¶5 - AC calcolata da sistema per SITO RETE NATURA 2000¶6 – AC calcolata da sistema per CATEGORIE PROGETTUALI
	CONSTRAINT pk_scriva_s_istanza_competenza PRIMARY KEY (id_istanza_comp_storico)
);

-- Column comments

COMMENT ON COLUMN scriva_s_istanza_competenza.ind_assegnata_da_sistema IS 'Indica la modalita'' di assegnazione della competenza
Es.
0 – AC selezionata da utente in orientamento
2 – AC attribuita da sistema con scriva_r_adempi_competenza.ind_adesione_adempimento = 2
3 - AC attribuita da sistema con scriva_r_adempi_competenza.ind_adesione_adempimento = 3
4 - AC calcolata da sistema per COMUNE
5 - AC calcolata da sistema per SITO RETE NATURA 2000
6 – AC calcolata da sistema per CATEGORIE PROGETTUALI';


-- scriva_s_istanza_vincolo_aut definition

-- Drop table

-- DROP TABLE scriva_s_istanza_vincolo_aut;

CREATE TABLE scriva_s_istanza_vincolo_aut (
	id_istanza_vincolo_aut_storico int4 NOT NULL,
	id_istanza_vincolo_aut int4 NOT NULL,
	id_istanza int4 NOT NULL,
	id_vincolo_autorizza int4 NOT NULL,
	des_vincolo_calcolato varchar(250) NULL,
	des_ente_utente varchar(150) NULL,
	des_email_pec_ente_utente varchar(100) NULL,
	flg_richiesta_post numeric(1) NOT NULL,
	des_motivo_richiesta_port varchar(150) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_r_istanza_vincolo_aut_01 CHECK ((flg_richiesta_post = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_s_istanza_vincolo_aut PRIMARY KEY (id_istanza_vincolo_aut_storico)
);


-- scriva_s_ogg_area_protetta definition

-- Drop table

-- DROP TABLE scriva_s_ogg_area_protetta;

CREATE TABLE scriva_s_ogg_area_protetta (
	id_oggetto_area_protetta_storico int4 NOT NULL,
	id_oggetto_area_protetta int4 NOT NULL,
	id_oggetto_istanza int4 NOT NULL,
	id_competenza_territorio int4 NOT NULL,
	id_tipo_area_protetta int4 NOT NULL,
	cod_amministrativo varchar(20) NOT NULL,
	cod_gestore_fonte varchar(20) NOT NULL,
	des_area_protetta varchar(300) NULL,
	flg_ricade numeric(1) NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_s_ogg_area_protetta PRIMARY KEY (id_oggetto_area_protetta_storico)
);


-- scriva_s_ogg_istanza_categoria definition

-- Drop table

-- DROP TABLE scriva_s_ogg_istanza_categoria;

CREATE TABLE scriva_s_ogg_istanza_categoria (
	id_ogg_istanza_cat_storico int4 NOT NULL,
	id_oggetto_istanza int4 NOT NULL,
	id_categoria_oggetto int4 NOT NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	flg_cat_nuovo_oggetto numeric(1) DEFAULT 0 NOT NULL,
	flg_cat_modifica_oggetto numeric(1) DEFAULT 0 NOT NULL,
	flg_cat_principale numeric(1) DEFAULT 0 NOT NULL,
	ordinamento_istanza_competenza int4 NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_r_oggis_cat_01 CHECK ((flg_cat_nuovo_oggetto = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_r_oggis_cat_02 CHECK ((flg_cat_modifica_oggetto = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_r_oggis_cat_03 CHECK ((flg_cat_principale = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_s_ogg_istanza_categoria PRIMARY KEY (id_ogg_istanza_cat_storico)
);


-- scriva_s_ogg_natura_2000 definition

-- Drop table

-- DROP TABLE scriva_s_ogg_natura_2000;

CREATE TABLE scriva_s_ogg_natura_2000 (
	id_oggetto_natura_2000_storico int4 NOT NULL,
	id_oggetto_natura_2000 int4 NOT NULL,
	id_oggetto_istanza int4 NOT NULL,
	id_competenza_territorio int4 NOT NULL,
	id_tipo_natura_2000 int4 NOT NULL,
	cod_amministrativo varchar(20) NOT NULL,
	cod_gestore_fonte varchar(20) NOT NULL,
	des_sito_natura_2000 varchar(300) NULL,
	num_distanza numeric(5) NULL, -- distanza espressa in metri
	flg_ricade numeric(1) NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	des_elemento_discontinuita varchar(300) NULL,
	CONSTRAINT pk_scriva_s_ogg_natura_2000 PRIMARY KEY (id_oggetto_natura_2000_storico)
);

-- Column comments

COMMENT ON COLUMN scriva_s_ogg_natura_2000.num_distanza IS 'distanza espressa in metri';


-- scriva_s_ogg_vincolo_autorizza definition

-- Drop table

-- DROP TABLE scriva_s_ogg_vincolo_autorizza;

CREATE TABLE scriva_s_ogg_vincolo_autorizza (
	id_oggetto_vincolo_aut_storico int4 NOT NULL,
	id_oggetto_vincolo_aut int4 NOT NULL,
	id_oggetto_istanza int4 NOT NULL,
	id_vincolo_autorizza int4 NOT NULL,
	des_vincolo_calcolato varchar(250) NULL,
	des_ente_utente varchar(150) NULL,
	des_email_pec_ente_utente varchar(100) NULL,
	flg_richiesta_post numeric(1) NOT NULL,
	des_motivo_richiesta_post varchar(150) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_s_ogg_vincolo_autorizza PRIMARY KEY (id_oggetto_vincolo_aut_storico)
);


-- scriva_s_oggetto definition

-- Drop table

-- DROP TABLE scriva_s_oggetto;

CREATE TABLE scriva_s_oggetto (
	id_oggetto_storico int4 NOT NULL,
	id_oggetto int4 NOT NULL,
	id_tipologia_oggetto int4 NOT NULL,
	id_stato_oggetto int4 NULL,
	id_masterdata int4 NOT NULL,
	id_masterdata_origine int4 NOT NULL,
	cod_scriva varchar(20) NOT NULL,
	den_oggetto varchar(500) NOT NULL,
	des_oggetto varchar(2000) NULL,
	flg_esistente numeric(1) NULL,
	coordinata_x numeric NULL,
	coordinata_y numeric NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	data_aggiornamento timestamp NULL,
	cod_oggetto_fonte varchar(50) NULL,
	id_istanza_aggiornamento int4 NULL,
	id_funzionario int4 NULL,
	CONSTRAINT pk_scriva_s_oggetto PRIMARY KEY (id_oggetto_storico)
);


-- scriva_s_oggetto_ist_figlio definition

-- Drop table

-- DROP TABLE scriva_s_oggetto_ist_figlio;

CREATE TABLE scriva_s_oggetto_ist_figlio (
	id_oggetto_ist_figlio_storico int4 NOT NULL,
	id_oggetto_istanza_padre int4 NOT NULL,
	id_oggetto_istanza_figlio int4 NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_s_oggetto_ist_figlio PRIMARY KEY (id_oggetto_ist_figlio_storico)
);


-- scriva_s_oggetto_istanza definition

-- Drop table

-- DROP TABLE scriva_s_oggetto_istanza;

CREATE TABLE scriva_s_oggetto_istanza (
	id_oggetto_istanza_storico int4 NOT NULL,
	id_oggetto_istanza int4 NOT NULL,
	id_oggetto int4 NOT NULL,
	id_istanza int4 NOT NULL,
	id_tipologia_oggetto int4 NOT NULL,
	id_masterdata int4 NOT NULL,
	id_masterdata_origine int4 NOT NULL,
	den_oggetto varchar(500) NOT NULL,
	des_oggetto varchar(2000) NULL,
	flg_esistente numeric(1) NULL,
	coordinata_x numeric NULL,
	coordinata_y numeric NULL,
	ind_geo_stato varchar(1) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	id_istanza_attore int4 NOT NULL,
	id_funzionario int4 NULL,
	cod_oggetto_fonte varchar(50) NULL,
	cod_utenza varchar(20) NULL,
	note_atto_precedente varchar(300) NULL,
	flg_geo_modificato numeric(1) NULL,
	ind_livello numeric(2) DEFAULT 0 NOT NULL,
	CONSTRAINT pk_scriva_s_oggetto_istanza PRIMARY KEY (id_oggetto_istanza_storico)
);


-- scriva_s_recapito_alternativo definition

-- Drop table

-- DROP TABLE scriva_s_recapito_alternativo;

CREATE TABLE scriva_s_recapito_alternativo (
	id_recapito_alternativo_storico int4 NOT NULL,
	id_recapito_alternativo int4 NOT NULL,
	cod_recapito_alternativo varchar(20) NOT NULL,
	id_comune_residenza numeric(6) NULL,
	id_comune_sede_legale numeric(6) NULL,
	id_istanza_attore int4 NOT NULL,
	indirizzo_soggetto varchar(100) NULL,
	num_civico_indirizzo varchar(30) NULL,
	citta_estera_residenza varchar(100) NULL,
	id_nazione_residenza numeric(6) NULL,
	presso varchar(150) NULL,
	num_telefono varchar(25) NULL,
	num_cellulare varchar(25) NULL,
	des_localita varchar(250) NULL,
	des_email varchar(100) NULL,
	des_pec varchar(100) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	id_soggetto_istanza int4 NOT NULL,
	citta_estera_sede_legale varchar(100) NULL,
	id_nazione_sede_legale numeric(3) NULL,
	cap_residenza varchar(10) NULL,
	cap_sede_legale varchar(10) NULL,
	id_funzionario int4 NULL,
	data_aggiornamento timestamp NULL,
	CONSTRAINT pk_scriva_s_recapito_alternativo PRIMARY KEY (id_recapito_alternativo_storico)
);



-- scriva_s_soggetto definition

-- Drop table

-- DROP TABLE scriva_s_soggetto;

CREATE TABLE scriva_s_soggetto (
	id_soggetto_storico int4 NOT NULL,
	id_soggetto int4 NOT NULL,
	cf_soggetto varchar(16) NOT NULL,
	id_tipo_soggetto int4 NOT NULL,
	id_tipo_natura_giuridica int4 NULL,
	id_masterdata int4 NOT NULL,
	id_masterdata_origine int4 NOT NULL,
	id_comune_nascita numeric(6) NULL,
	id_comune_residenza numeric(6) NULL,
	id_comune_sede_legale numeric(6) NULL,
	partita_iva_soggetto varchar(16) NULL,
	den_soggetto varchar(250) NULL,
	data_cessazione_soggetto date NULL,
	nome varchar(100) NULL,
	cognome varchar(100) NULL,
	data_nascita_soggetto date NULL,
	citta_estera_nascita varchar(100) NULL,
	num_telefono varchar(25) NULL,
	des_email varchar(100) NULL,
	des_pec varchar(100) NULL,
	indirizzo_soggetto varchar(100) NULL,
	num_civico_indirizzo varchar(30) NULL,
	citta_estera_residenza varchar(100) NULL,
	den_provincia_cciaa varchar(20) NULL,
	den_anno_cciaa numeric(4) NULL,
	den_numero_cciaa varchar(20) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	num_cellulare varchar(25) NULL,
	id_nazione_residenza numeric(3) NULL,
	id_nazione_nascita numeric(3) NULL,
	des_localita varchar(250) NULL,
	citta_estera_sede_legale varchar(100) NULL,
	id_nazione_sede_legale numeric(3) NULL,
	cap_residenza varchar(10) NULL,
	cap_sede_legale varchar(10) NULL,
	data_aggiornamento timestamp NULL,
	id_funzionario int4 NULL,
	CONSTRAINT pk_scriva_s_soggetto PRIMARY KEY (id_soggetto_storico)
);


-- scriva_s_soggetto_gruppo definition

-- Drop table

-- DROP TABLE scriva_s_soggetto_gruppo;

CREATE TABLE scriva_s_soggetto_gruppo (
	id_gruppo_soggetto_storico int4 NOT NULL,
	id_gruppo_soggetto int4 NOT NULL,
	id_soggetto_istanza int4 NOT NULL,
	flg_capogruppo numeric(1) NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_s_soggetto_gruppo PRIMARY KEY (id_gruppo_soggetto_storico)
);


-- scriva_s_soggetto_istanza definition

-- Drop table

-- DROP TABLE scriva_s_soggetto_istanza;

CREATE TABLE scriva_s_soggetto_istanza (
	id_soggetto_istanza_storico int4 NOT NULL,
	id_soggetto_istanza int4 NOT NULL,
	id_soggetto int4 NOT NULL,
	id_istanza int4 NOT NULL,
	id_ruolo_compilante int4 NOT NULL,
	id_soggetto_padre int4 NULL,
	id_ruolo_soggetto int4 NULL,
	cf_soggetto varchar(16) NOT NULL,
	id_tipo_soggetto int4 NOT NULL,
	id_tipo_natura_giuridica int4 NULL,
	id_masterdata int4 NOT NULL,
	id_masterdata_origine int4 NOT NULL,
	id_comune_nascita numeric(6) NULL,
	id_comune_residenza numeric(6) NULL,
	id_comune_sede_legale numeric(6) NULL,
	partita_iva_soggetto varchar(16) NULL,
	den_soggetto varchar(250) NULL,
	data_cessazione_soggetto date NULL,
	nome varchar(100) NULL,
	cognome varchar(100) NULL,
	data_nascita_soggetto date NULL,
	citta_estera_nascita varchar(100) NULL,
	num_telefono varchar(25) NULL,
	des_email varchar(100) NULL,
	des_pec varchar(100) NULL,
	indirizzo_soggetto varchar(100) NULL,
	num_civico_indirizzo varchar(30) NULL,
	citta_estera_residenza varchar(100) NULL,
	den_provincia_cciaa varchar(20) NULL,
	den_anno_cciaa numeric(4) NULL,
	den_numero_cciaa varchar(20) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	id_istanza_attore int4 NOT NULL,
	num_cellulare varchar(25) NULL,
	id_nazione_residenza numeric(3) NULL,
	id_nazione_nascita numeric(3) NULL,
	des_localita varchar(250) NULL,
	citta_estera_sede_legale varchar(100) NULL,
	id_nazione_sede_legale numeric(3) NULL,
	cap_residenza varchar(10) NULL,
	cap_sede_legale varchar(10) NULL,
	id_funzionario int4 NULL,
	data_aggiornamento timestamp NULL,
	CONSTRAINT pk_scriva_s_soggetto_istanza PRIMARY KEY (id_soggetto_istanza_storico)
);
CREATE INDEX ie_scriva_s_soggetto_istanza_01 ON scriva_s_soggetto_istanza USING btree (id_soggetto_istanza, id_soggetto, id_istanza);


-- scriva_s_ubica_oggetto definition

-- Drop table

-- DROP TABLE scriva_s_ubica_oggetto;

CREATE TABLE scriva_s_ubica_oggetto (
	id_ubica_oggetto_storico int4 NOT NULL,
	id_ubica_oggetto int4 NOT NULL,
	id_oggetto int4 NOT NULL,
	id_comune numeric(6) NOT NULL,
	den_indirizzo varchar(100) NULL,
	num_civico varchar(30) NULL,
	des_localita varchar(250) NULL,
	ind_geo_provenienza varchar(4) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_s_ubica_oggetto PRIMARY KEY (id_ubica_oggetto_storico)
);


-- scriva_s_ubica_oggetto_istanza definition

-- Drop table

-- DROP TABLE scriva_s_ubica_oggetto_istanza;

CREATE TABLE scriva_s_ubica_oggetto_istanza (
	id_ubica_ogg_istanza_storico int4 NOT NULL,
	id_ubica_oggetto_istanza int4 NOT NULL,
	id_oggetto_istanza int4 NOT NULL,
	id_comune numeric(6) NOT NULL,
	den_indirizzo varchar(100) NULL,
	num_civico varchar(30) NULL,
	des_localita varchar(250) NULL,
	ind_geo_provenienza varchar(4) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_s_ubica_oggetto_istanza PRIMARY KEY (id_ubica_ogg_istanza_storico)
);


-- scriva_t_compilante definition

-- Drop table

-- DROP TABLE scriva_t_compilante;

CREATE TABLE scriva_t_compilante (
	id_compilante int4 NOT NULL,
	cf_compilante varchar(16) NOT NULL,
	cognome_compilante varchar(50) NULL,
	nome_compilante varchar(50) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	des_email_compilante varchar(100) NOT NULL,
	CONSTRAINT ak_scriva_t_compilante_01 UNIQUE (cf_compilante),
	CONSTRAINT pk_scriva_t_compilante PRIMARY KEY (id_compilante)
);
COMMENT ON TABLE scriva_t_compilante IS 'Anagrafica dei Compilanti.  Dopo l’autenticazione avvenuta con successo il servizio verifica che l’utente sia ACCREDITATO su SCRIVA, ossia che l’attore compaia tra i compilanti di SCRIVA (scriva_t_compilante). Se l’attore e'' accreditato, potrà accedere tranquillamente alla scrivania del richiedente, diversamente gli sarà chiesto di accreditarsi attivando la procedura di accreditamento.';


-- scriva_t_funzionario definition

-- Drop table

-- DROP TABLE scriva_t_funzionario;

CREATE TABLE scriva_t_funzionario (
	id_funzionario int4 NOT NULL,
	cf_funzionario varchar(16) NOT NULL,
	nome_funzionario varchar(100) NOT NULL,
	cognome_funzionario varchar(100) NOT NULL,
	num_telefono_funzionario varchar(25) NULL,
	des_email_funzionario varchar(100) NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_t_funzionario PRIMARY KEY (id_funzionario)
);
CREATE UNIQUE INDEX ak_scriva_t_funzionario_01 ON scriva_t_funzionario USING btree (cf_funzionario);
COMMENT ON TABLE scriva_t_funzionario IS 'Anagrafica dei Funzionari PA. Alla nascita di un nuovo adempimento, occorre censire i nuovi funzionari.  Dopo l’autenticazione avvenuta con successo l’attore, funzionario di pubblica amministrazione, deve essere riconosciuto dal sistema, ossia il servizio verifica che il CODICE FISCALE dell’attore in linea sia censito nell’anagrafica funzionari e recupera il profilo e la competenza territorio a lui associati.';


-- scriva_w_acq_soggetto definition

-- Drop table

-- DROP TABLE scriva_w_acq_soggetto;

CREATE TABLE scriva_w_acq_soggetto (
	cod_acq_soggetto varchar(100) NOT NULL, -- identificativo univoco valorizzato dalla fonte ¶Es. RISCA_PORTING sequenza di record che partono da RISCA_1
	id_mig_soggetto_fonte int4 NULL,
	cf_soggetto varchar(16) NULL,
	cod_tipo_soggetto varchar(20) NULL,
	cod_tipo_natura_giuridica varchar(20) NULL,
	cod_istat_comune_nascita varchar(20) NULL,
	cod_istat_comune_res varchar(20) NULL,
	cod_istat_comune_sede_legale varchar(20) NULL,
	partita_iva_soggetto varchar(31) NULL,
	den_soggetto varchar(250) NULL,
	data_cessazione_soggetto date NULL,
	nome varchar(100) NULL,
	cognome varchar(100) NULL,
	data_nascita_soggetto date NULL,
	citta_estera_nascita varchar(100) NULL,
	num_telefono varchar(25) NULL,
	des_email varchar(100) NULL,
	des_pec varchar(100) NULL,
	indirizzo_soggetto varchar(100) NULL,
	num_civico_indirizzo varchar(30) NULL,
	citta_estera_residenza varchar(100) NULL,
	den_provincia_cciaa varchar(20) NULL,
	den_anno_cciaa numeric(4) NULL,
	den_numero_cciaa varchar(20) NULL,
	num_cellulare varchar(25) NULL,
	id_nazione_residenza int4 NULL,
	id_nazione_nascita int4 NULL,
	des_localita varchar(250) NULL,
	citta_estera_sede_legale varchar(100) NULL,
	id_nazione_sede_legale int4 NULL,
	cap_residenza varchar(10) NULL,
	cap_sede_legale varchar(10) NULL,
	data_aggiornamento date NULL,
	orig_soggetto int4 NULL,
	id_elaborazione int4 NULL,
	ind_mig int4 DEFAULT 0 NOT NULL, -- indica se il record e stato: - 1= estratto - 10= validato correttamente - 20= validato con errori bloccanti - 100 = migrato correttamente - 200 = scartato durante migrazione - 900 = fuori perimetro  - 920 = fuori perimetro con errori - 500 = le info collegate presentano anomalie bloccanti
	fonte varchar(20) NOT NULL,
	orig_tipo_sogg varchar(10) NULL,
	orig_nazione_nascita varchar(50) NULL,
	orig_nazione varchar(50) NULL,
	data_cessazione date NULL,
	cod_tipo_invio bpchar(2) NULL,
	orig_des_tipo_invio varchar(20) NULL,
	orig_presso varchar(150) NULL,
	orig_ind_postel varchar(200) NULL,
	CONSTRAINT pk_scriva_w_acq_soggetto PRIMARY KEY (cod_acq_soggetto)
);
COMMENT ON TABLE scriva_w_acq_soggetto IS 'Tabella di working. Utilizzata in RISCA per acquisire soggetti dell''Anagrafe generale da altri sistemi.';

-- Column comments

COMMENT ON COLUMN scriva_w_acq_soggetto.cod_acq_soggetto IS 'identificativo univoco valorizzato dalla fonte 
Es. RISCA_PORTING sequenza di record che partono da RISCA_1';
COMMENT ON COLUMN scriva_w_acq_soggetto.ind_mig IS 'indica se il record e stato: - 1= estratto - 10= validato correttamente - 20= validato con errori bloccanti - 100 = migrato correttamente - 200 = scartato durante migrazione - 900 = fuori perimetro  - 920 = fuori perimetro con errori - 500 = le info collegate presentano anomalie bloccanti';


-- scriva_w_del_istanza definition

-- Drop table

-- DROP TABLE scriva_w_del_istanza;

CREATE TABLE scriva_w_del_istanza (
	id_istanza int4 NOT NULL,
	flg_elimina_istanza numeric(1) DEFAULT 0 NOT NULL, -- indica se deve essere eliminata l'istanza : 1 (default=0)
	flg_elimina_file_index numeric(1) DEFAULT 0 NOT NULL -- indica se deve essere eliminata la cartella contenente gli allegati in INDEX : 1 (default=0),
	CONSTRAINT chk_scriva_w_del_istanza_01 CHECK ((flg_elimina_istanza = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_w_del_istanza_02 CHECK ((flg_elimina_file_index = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_w_del_istanza PRIMARY KEY (id_istanza)
);
COMMENT ON TABLE scriva_w_del_istanza IS 'Tabella di working contenente la lista delle istanze da eliminare (scriva e index)';

-- Column comments

COMMENT ON COLUMN scriva_w_del_istanza.flg_elimina_istanza IS 'indica se deve essere eliminata l''istanza : 1 (default=0)';
COMMENT ON COLUMN scriva_w_del_istanza.flg_elimina_file_index IS 'indica se deve essere eliminata la cartella contenente gli allegati in INDEX : 1 (default=0)';

-- scriva_d_canale_notifica definition

-- Drop table

-- DROP TABLE scriva_d_canale_notifica;

CREATE TABLE scriva_d_canale_notifica (
	id_canale_notifica int4 NOT NULL,
	id_componente_appl_push int4 NULL,
	cod_canale_notifica varchar(20) NOT NULL,
	des_canale_notifica varchar(150) NOT NULL,
	flg_campo_cc numeric(1) DEFAULT 0 NOT NULL,
	data_inizio date NOT NULL,
	data_fine date NULL,
	ind_tipo_canale varchar(10) NULL, -- E- Esterno A-Applicativo
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT ak_scriva_d_canale_notifica_01 UNIQUE (cod_canale_notifica),
	CONSTRAINT chk_scriva_d_canale_notifica_01 CHECK ((flg_campo_cc = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_d_canale_notifica PRIMARY KEY (id_canale_notifica),
	CONSTRAINT fk_scriva_d_componente_app_06 FOREIGN KEY (id_componente_appl_push) REFERENCES scriva_d_componente_app(id_componente_app)
);
COMMENT ON TABLE scriva_d_canale_notifica IS 'Definisce i canali delle notifiche, gestiti dall''applicativo
Es.
SCRIVA_FO
SCRIVA_BO
EMAIL
ALTRO SERVIZIO';

-- Column comments

COMMENT ON COLUMN scriva_d_canale_notifica.ind_tipo_canale IS 'E- Esterno A-Applicativo';


-- scriva_d_config_json_data definition

-- Drop table

-- DROP TABLE scriva_d_config_json_data;

CREATE TABLE scriva_d_config_json_data (
	id_config_json_data int4 NOT NULL,
	id_tipo_quadro int4 NOT NULL,
	flg_obbligo numeric(1) DEFAULT 0 NOT NULL,
	des_tabella varchar(255) NOT NULL,
	des_tag varchar(100) NULL,
	query_estrazione text NULL,
	json_data_sample jsonb NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_d_config_json_data_01 CHECK ((flg_obbligo = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_d_config_json_data PRIMARY KEY (id_config_json_data),
	CONSTRAINT fk_scriva_d_tipo_quadro_02 FOREIGN KEY (id_tipo_quadro) REFERENCES scriva_d_tipo_quadro(id_tipo_quadro)
);
COMMENT ON TABLE scriva_d_config_json_data IS 'Tabella per mappare le entità contenenti dati amministrativi e la sezione (tipo quadro) di json data in cui andranno salvati con indicazione degli eventuali tag specifici';


-- scriva_d_help definition

-- Drop table

-- DROP TABLE scriva_d_help;

CREATE TABLE scriva_d_help (
	id_help int4 NOT NULL,
	chiave_help varchar(100) NOT NULL,
	valore_campo_help varchar(150) NULL,
	des_testo_help text NULL,
	id_tipo_help int4 NOT NULL,
	id_livello_help int4 NOT NULL,
	note_help varchar(150) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_help PRIMARY KEY (id_help),
	CONSTRAINT fk_scriva_d_livello_help_01 FOREIGN KEY (id_livello_help) REFERENCES scriva_d_livello_help(id_livello_help),
	CONSTRAINT fk_scriva_d_tipo_help_01 FOREIGN KEY (id_tipo_help) REFERENCES scriva_d_tipo_help(id_tipo_help)
);
CREATE UNIQUE INDEX ak_scriva_d_help_01 ON scriva_d_help USING btree (chiave_help, valore_campo_help);
COMMENT ON TABLE scriva_d_help IS 'Configurazione dell’help.  I ruoli coinvolti sono l’assistenza e l’analista del procedimento. E'' possibile configurare l’help contestuale per maschere, quadri, oggetti e anche singoli valori di campi.';


-- scriva_d_messaggio definition

-- Drop table

-- DROP TABLE scriva_d_messaggio;

CREATE TABLE scriva_d_messaggio (
	id_messaggio int4 NOT NULL,
	id_tipo_messaggio int4 NOT NULL,
	cod_messaggio varchar(20) NOT NULL,
	des_testo_messaggio varchar(300) NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT ak_scriva_d_messaggio_01 UNIQUE (id_tipo_messaggio, cod_messaggio),
	CONSTRAINT pk_scriva_d_messaggio PRIMARY KEY (id_messaggio),
	CONSTRAINT fk_scriva_d_tipo_messaggio_01 FOREIGN KEY (id_tipo_messaggio) REFERENCES scriva_d_tipo_messaggio(id_tipo_messaggio)
);
COMMENT ON TABLE scriva_d_messaggio IS 'Contiene tutti i messaggi per l’applicativo SCRIVA, con per ognuno indicata la tipologia.  Qui devono essere censiti i messaggi specifici dell’adempimento in fase di configurazione.';


-- scriva_d_nazione definition

-- Drop table

-- DROP TABLE scriva_d_nazione;

CREATE TABLE scriva_d_nazione (
	id_nazione numeric(3) NOT NULL,
	cod_istat_nazione varchar(3) NULL,
	cod_belfiore_nazione varchar(4) NULL,
	denom_nazione varchar(100) NOT NULL,
	id_continente numeric(2) NOT NULL,
	unione_europea bool NULL,
	id_origine numeric(2) NOT NULL,
	cod_iso2 varchar(2) NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	dt_id_stato numeric(20) NOT NULL,
	dt_id_stato_prev numeric(20) NULL,
	dt_id_stato_next numeric(20) NULL,
	CONSTRAINT pk_scriva_d_nazione PRIMARY KEY (id_nazione),
	CONSTRAINT fk_scriva_d_continente_01 FOREIGN KEY (id_continente) REFERENCES scriva_d_continente(id_continente),
	CONSTRAINT fk_scriva_d_origine_limiti_01 FOREIGN KEY (id_origine) REFERENCES scriva_d_origine_limiti(id_origine)
);
CREATE UNIQUE INDEX ak_scriva_d_nazione_01 ON scriva_d_nazione USING btree (cod_istat_nazione, cod_belfiore_nazione, denom_nazione, data_inizio_validita);


-- scriva_d_profilo_app definition

-- Drop table

-- DROP TABLE scriva_d_profilo_app;

CREATE TABLE scriva_d_profilo_app (
	id_profilo_app int4 NOT NULL,
	id_componente_app int4 NOT NULL,
	cod_profilo_app varchar(20) NOT NULL,
	des_profilo_app varchar(50) NULL,
	flg_profilo_iride numeric(1) NOT NULL,
	flg_competenza numeric(1) NOT NULL,
	id_gestione_attore int4 NULL,
	pwd_ruolo varchar(50) NULL, -- Alcuni ruoli assegnati ad applicativi esterni avranno delle pwd in md5 da utilizzare per la chiamata ai servizi. (formato md5 ? un hash a 128bit)
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_d_profilo_app_01 CHECK ((flg_profilo_iride = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_d_profilo_app_02 CHECK ((flg_competenza = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_d_profilo_app PRIMARY KEY (id_profilo_app),
	CONSTRAINT fk_scriva_d_componente_app_01 FOREIGN KEY (id_componente_app) REFERENCES scriva_d_componente_app(id_componente_app),
	CONSTRAINT fk_scriva_d_gestione_attore_01 FOREIGN KEY (id_gestione_attore) REFERENCES scriva_d_gestione_attore(id_gestione_attore)
);
CREATE UNIQUE INDEX ak_scriva_d_profilo_app_01 ON scriva_d_profilo_app USING btree (id_componente_app, cod_profilo_app);
COMMENT ON TABLE scriva_d_profilo_app IS 'Profili applicativi previsti rispetto al componente applicativo.  ES. (FO) COMPILANTE - Compilante FO RICHIEDENTE - Richiedente istanza FO ABILITATO_CONSULTA - Abilitato alla Consultazione FO ABILITATO_GESTIONE - Abilitato alla Gestione FO';

-- Column comments

COMMENT ON COLUMN scriva_d_profilo_app.pwd_ruolo IS 'Alcuni ruoli assegnati ad applicativi esterni avranno delle pwd in md5 da utilizzare per la chiamata ai servizi. (formato md5 ? un hash a 128bit)';


-- scriva_d_quadro definition

-- Drop table

-- DROP TABLE scriva_d_quadro;

CREATE TABLE scriva_d_quadro (
	id_quadro int4 NOT NULL,
	id_tipo_quadro int4 NOT NULL,
	flg_tipo_gestione varchar(1) NOT NULL, -- R – se e' un quadro gestito con tecnologia Angular F – se e' un quadro gestito con tecnologia FORM.io
	json_configura_quadro jsonb NULL, -- Json fornito dagli sviluppatori: rappresenta il contenuto del quadro ottenuto tramite sviluppo in Angular o Form.io
	flg_attivo numeric(1) NOT NULL, -- 0 – non attivo 1 – attivo  Se impostato a 0 non e' visibile dall’applicazione e non può essere utilizzato
	json_configura_riepilogo jsonb NULL, -- Json fornito dagli sviluppatori in Form.io: rappresenta il contenuto che si visualizza per ogni quadro nell’ultima pagina di riepilogo istanza,
	cod_quadro varchar(20) NULL,
	des_quadro varchar(150) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_d_quadro_01 CHECK ((flg_attivo = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_d_quadro PRIMARY KEY (id_quadro),
	CONSTRAINT fk_scriva_d_tipo_quadro_01 FOREIGN KEY (id_tipo_quadro) REFERENCES scriva_d_tipo_quadro(id_tipo_quadro)
);
COMMENT ON TABLE scriva_d_quadro IS 'Quadro Verticale : e'' un form di gestione dei dati di un procedimento specifico per quel procedimento, con dati e logiche ad hoc   Quadro Trasversale : e'' un form di gestione dei dati di un procedimento che e'' generale, non specifico di un procedimento, ed e'' presente per ogni procedimento.  e'' possibile/probabile che un quadro trasversale sia influenzato nel suo comportamento dalla configurazione del procedimento.';

-- Column comments

COMMENT ON COLUMN scriva_d_quadro.flg_tipo_gestione IS 'R – se e'' un quadro gestito con tecnologia Angular F – se e'' un quadro gestito con tecnologia FORM.io';
COMMENT ON COLUMN scriva_d_quadro.json_configura_quadro IS 'Json fornito dagli sviluppatori: rappresenta il contenuto del quadro ottenuto tramite sviluppo in Angular o Form.io';
COMMENT ON COLUMN scriva_d_quadro.flg_attivo IS '0 – non attivo 1 – attivo  Se impostato a 0 non e'' visibile dall’applicazione e non può essere utilizzato';
COMMENT ON COLUMN scriva_d_quadro.json_configura_riepilogo IS 'Json fornito dagli sviluppatori in Form.io: rappresenta il contenuto che si visualizza per ogni quadro nell’ultima pagina di riepilogo istanza';


-- scriva_d_regione definition

-- Drop table

-- DROP TABLE scriva_d_regione;

CREATE TABLE scriva_d_regione (
	id_regione numeric(6) NOT NULL,
	cod_regione varchar(3) NOT NULL,
	denom_regione varchar(100) NOT NULL,
	id_nazione numeric(3) NOT NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	CONSTRAINT pk_scriva_d_regione PRIMARY KEY (id_regione),
	CONSTRAINT fk_scriva_d_nazione_02 FOREIGN KEY (id_nazione) REFERENCES scriva_d_nazione(id_nazione)
);
CREATE UNIQUE INDEX ak_scriva_d_regione_01 ON scriva_d_regione USING btree (cod_regione, id_nazione, data_inizio_validita);
CREATE UNIQUE INDEX ak_scriva_d_regione_02 ON scriva_d_regione USING btree (denom_regione, id_nazione, data_inizio_validita);


-- scriva_d_tipo_abilitazione definition

-- Drop table

-- DROP TABLE scriva_d_tipo_abilitazione;

CREATE TABLE scriva_d_tipo_abilitazione (
	id_tipo_abilitazione int4 NOT NULL,
	cod_tipo_abilitazione varchar(20) NOT NULL,
	des_tipo_abilitazione varchar(50) NULL,
	id_profilo_app int4 NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_tipo_abilitazione PRIMARY KEY (id_tipo_abilitazione),
	CONSTRAINT fk_scriva_d_profilo_app_07 FOREIGN KEY (id_profilo_app) REFERENCES scriva_d_profilo_app(id_profilo_app)
);


-- scriva_d_tipo_adempimento definition

-- Drop table

-- DROP TABLE scriva_d_tipo_adempimento;

CREATE TABLE scriva_d_tipo_adempimento (
	id_tipo_adempimento int4 NOT NULL,
	id_ambito int4 NOT NULL,
	cod_tipo_adempimento varchar(20) NOT NULL,
	des_tipo_adempimento varchar(100) NULL,
	des_estesa_tipo_adempimento varchar(200) NULL,
	ordinamento_tipo_adempimento int4 NULL,
	flg_attivo numeric(1) NOT NULL,
	uuid_index varchar(36) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_tipo_adempimento PRIMARY KEY (id_tipo_adempimento),
	CONSTRAINT fk_scriva_d_ambito_02 FOREIGN KEY (id_ambito) REFERENCES scriva_d_ambito(id_ambito)
);
CREATE UNIQUE INDEX ak_scriva_d_tipo_adempimneto_01 ON scriva_d_tipo_adempimento USING btree (cod_tipo_adempimento);
COMMENT ON TABLE scriva_d_tipo_adempimento IS 'E'' una classificazione di adempimenti che condividono uno stesso ambito e una stessa tipologia generale.  Ad esempio, i procedimenti relativi alle valutazioni di impatto ambientali – VIA – sono raggruppati in un tipo procedimento VIA.  ES. (per ambito = Ambiente) VIA - Valutazione di Impatto Ambientale VINCA - Valutazione di Incidenza Ambientale etc..';


-- scriva_d_tipo_elabora definition

-- Drop table

-- DROP TABLE scriva_d_tipo_elabora;

CREATE TABLE scriva_d_tipo_elabora (
	id_tipo_elabora int4 NOT NULL, -- Identificativo univoco
	id_funzionalita int4 NOT NULL,
	cod_tipo_elabora varchar(2) NOT NULL,
	des_tipo_elabora varchar(50) NOT NULL,
	ordina_tipo_elabora numeric(4) NULL,
	flg_default numeric(1) DEFAULT 0 NOT NULL,
	flg_visibile numeric(1) DEFAULT 0 NOT NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT ak_scriva_d_tipo_elabora_01 UNIQUE (cod_tipo_elabora),
	CONSTRAINT chk_scriva_d_tipo_elabora_01 CHECK ((flg_default = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_d_tipo_elabora_02 CHECK ((flg_visibile = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_d_tipo_elabora PRIMARY KEY (id_tipo_elabora),
	CONSTRAINT fk_scriva_d_funzionalita_01 FOREIGN KEY (id_funzionalita) REFERENCES scriva_d_funzionalita(id_funzionalita)
);
COMMENT ON TABLE scriva_d_tipo_elabora IS 'Permette di definire le tipologie di elaborazione previste.  Es. Aggiornamento Limiti Amministrativi      (Stampe) - Stampa-1 - Stampa-2';

-- Column comments

COMMENT ON COLUMN scriva_d_tipo_elabora.id_tipo_elabora IS 'Identificativo univoco';


-- scriva_d_tipo_evento definition

-- Drop table

-- DROP TABLE scriva_d_tipo_evento;

CREATE TABLE scriva_d_tipo_evento (
	id_tipo_evento int4 NOT NULL,
	id_stato_istanza_evento int4 NULL,
	cod_tipo_evento varchar(20) NOT NULL,
	des_tipo_evento varchar(150) NOT NULL,
	ind_visibile varchar(20) NULL,
	id_componente_gestore_processo int4 NULL,
	flg_genera_ricevuta numeric(1) DEFAULT 0 NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_d_tipo_evento_01 CHECK ((flg_genera_ricevuta = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_d_tipo_evento PRIMARY KEY (id_tipo_evento),
	CONSTRAINT fk_scriva_d_componente_app_04 FOREIGN KEY (id_componente_gestore_processo) REFERENCES scriva_d_componente_app(id_componente_app),
	CONSTRAINT fk_scriva_d_stato_istanza_06 FOREIGN KEY (id_stato_istanza_evento) REFERENCES scriva_d_stato_istanza(id_stato_istanza)
);
CREATE UNIQUE INDEX ak_scriva_d_tipo_evento_01 ON scriva_d_tipo_evento USING btree (cod_tipo_evento);


-- scriva_d_tipo_pagamento definition

-- Drop table

-- DROP TABLE scriva_d_tipo_pagamento;

CREATE TABLE scriva_d_tipo_pagamento (
	id_tipo_pagamento int4 NOT NULL,
	id_gruppo_pagamento int4 NOT NULL,
	cod_tipo_pagamento varchar(20) NOT NULL,
	des_tipo_pagamento varchar(250) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_tipo_pagamento PRIMARY KEY (id_tipo_pagamento),
	CONSTRAINT fk_scriva_d_gruppo_pagamento_01 FOREIGN KEY (id_gruppo_pagamento) REFERENCES scriva_d_gruppo_pagamento(id_gruppo_pagamento)
);
CREATE UNIQUE INDEX ak_scriva_d_tipo_pagamento_01 ON scriva_d_tipo_pagamento USING btree (cod_tipo_pagamento);
COMMENT ON TABLE scriva_d_tipo_pagamento IS 'Anagrafica dei tipi di pagamento.  Ogni tipo pagamento ha un campo (id_gruppo_pagamento, Foreign Key verso la tabella scriva_d_gruppo_pagamento) che lo lega ad un determinato gruppo pagamento: ad esempio i tipi pagamento Onere di istruttoria e Onere di segreteria hanno entrambi il legame con il gruppo pagamento Onere;   I valori attualmente previsti sono: Marca da bollo, Onere di istruttoria, Onere di segreteria, Interessi oneri istruttoria, Sanzione onere istruttoria).';


-- scriva_d_tipologia_allegato definition

-- Drop table

-- DROP TABLE scriva_d_tipologia_allegato;

CREATE TABLE scriva_d_tipologia_allegato (
	id_tipologia_allegato int4 NOT NULL,
	id_categoria_allegato int4 NOT NULL,
	cod_tipologia_allegato varchar(20) NOT NULL,
	des_tipologia_allegato varchar(300) NULL,
	ordinamento_tipologia_allegato int4 NULL,
	flg_attivo numeric(1) NOT NULL,
	flg_atto numeric(1) DEFAULT 0 NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	flg_sistema numeric(1) DEFAULT 0 NOT NULL, -- Indica che la tipologia di allegato è generata da Sistema¶Applicativamente viene inibita l'eliminazione dell'allegato da parte del 'proponente' e del 'funzionario'
	CONSTRAINT chk_scriva_d_tipologia_allegato_01 CHECK ((flg_attivo = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_d_tipologia_allegato_02 CHECK ((flg_attivo = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_d_tipologia_allegato_03 CHECK ((flg_sistema = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_d_tipologia_allegato PRIMARY KEY (id_tipologia_allegato),
	CONSTRAINT fk_scriva_d_cat_allegato_01 FOREIGN KEY (id_categoria_allegato) REFERENCES scriva_d_categoria_allegato(id_categoria_allegato)
);
CREATE UNIQUE INDEX ak_scriva_d_tipologia_allegato_01 ON scriva_d_tipologia_allegato USING btree (cod_tipologia_allegato);
COMMENT ON TABLE scriva_d_tipologia_allegato IS 'Tipi di allegato previsti';

-- Column comments

COMMENT ON COLUMN scriva_d_tipologia_allegato.flg_sistema IS 'Indica che la tipologia di allegato è generata da Sistema
Applicativamente viene inibita l''eliminazione dell''allegato da parte del ''proponente'' e del ''funzionario''';


-- scriva_d_tipologia_oggetto definition

-- Drop table

-- DROP TABLE scriva_d_tipologia_oggetto;

CREATE TABLE scriva_d_tipologia_oggetto (
	id_tipologia_oggetto int4 NOT NULL,
	id_natura_oggetto int4 NOT NULL,
	cod_tipologia_oggetto varchar(40) NOT NULL,
	des_tipologia_oggetto varchar(150) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT ak_scriva_d_tipologia_oggetto_01 UNIQUE (cod_tipologia_oggetto),
	CONSTRAINT pk_scriva_d_tipologia_oggetto PRIMARY KEY (id_tipologia_oggetto),
	CONSTRAINT fk_scriva_d_natura_oggetto_01 FOREIGN KEY (id_natura_oggetto) REFERENCES scriva_d_natura_oggetto(id_natura_oggetto)
);
COMMENT ON TABLE scriva_d_tipologia_oggetto IS 'La tipologia costituisce una classificazione/tassonomia degli oggetti. ES. Progetto, Attività';


-- scriva_d_vincolo_autorizza definition

-- Drop table

-- DROP TABLE scriva_d_vincolo_autorizza;

CREATE TABLE scriva_d_vincolo_autorizza (
	id_vincolo_autorizza int4 NOT NULL,
	id_tipo_vincolo_aut int4 NOT NULL,
	cod_vincolo_autorizza varchar(4) NOT NULL,
	des_vincolo_autorizza varchar(250) NULL,
	des_rif_normativo text NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	flg_modifica numeric(1) NOT NULL,
	ind_visibile varchar(20) NOT NULL,
	ordinamento_vincolo_aut int4 NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_d_vincolo_autorizza_01 CHECK ((flg_modifica = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_d_vincolo_autorizza PRIMARY KEY (id_vincolo_autorizza),
	CONSTRAINT fk_scriva_d_tipo_vincolo_aut_01 FOREIGN KEY (id_tipo_vincolo_aut) REFERENCES scriva_d_tipo_vincolo_aut(id_tipo_vincolo_aut)
);
CREATE UNIQUE INDEX ak_scriva_d_vincolo_autorizza_01 ON scriva_d_vincolo_autorizza USING btree (cod_vincolo_autorizza);


-- scriva_r_categoria_tipo_ogg definition

-- Drop table

-- DROP TABLE scriva_r_categoria_tipo_ogg;

CREATE TABLE scriva_r_categoria_tipo_ogg (
	id_categoria_oggetto int4 NOT NULL,
	id_tipologia_oggetto int4 NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_categoria_tipo_ogg PRIMARY KEY (id_categoria_oggetto, id_tipologia_oggetto),
	CONSTRAINT fk_scriva_d_categoria_ogget_04 FOREIGN KEY (id_categoria_oggetto) REFERENCES scriva_d_categoria_oggetto(id_categoria_oggetto),
	CONSTRAINT fk_scriva_d_tipo_oggetto_04 FOREIGN KEY (id_tipologia_oggetto) REFERENCES scriva_d_tipologia_oggetto(id_tipologia_oggetto)
);


-- scriva_r_compilante_preferenza definition

-- Drop table

-- DROP TABLE scriva_r_compilante_preferenza;

CREATE TABLE scriva_r_compilante_preferenza (
	id_preferenza int4 NOT NULL,
	id_compilante int4 NOT NULL,
	id_tipo_adempimento int4 NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_compilante_preferenza PRIMARY KEY (id_preferenza),
	CONSTRAINT fk_scriva_t_compilante_02 FOREIGN KEY (id_compilante) REFERENCES scriva_t_compilante(id_compilante)
);


-- scriva_r_compilante_preferenza_cn definition

-- Drop table

-- DROP TABLE scriva_r_compilante_preferenza_cn;

CREATE TABLE scriva_r_compilante_preferenza_cn (
	id_compilante_preferenza_cn int4 NOT NULL,
	id_compilante int4 NOT NULL,
	id_canale_notifica int4 NOT NULL,
	flg_abilitato numeric(1) DEFAULT 0 NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT ak_scriva_r_compilante_preferenza_cn_01 UNIQUE (id_compilante, id_canale_notifica),
	CONSTRAINT chk_scriva_r_compilante_preferenza_cn_01 CHECK ((flg_abilitato = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_compilante_preferenza_cn PRIMARY KEY (id_compilante_preferenza_cn),
	CONSTRAINT fk_scriva_d_canale_notifica_02 FOREIGN KEY (id_canale_notifica) REFERENCES scriva_d_canale_notifica(id_canale_notifica),
	CONSTRAINT fk_scriva_t_compilante_04 FOREIGN KEY (id_compilante) REFERENCES scriva_t_compilante(id_compilante)
);


-- scriva_r_funzionario_profilo definition

-- Drop table

-- DROP TABLE scriva_r_funzionario_profilo;

CREATE TABLE scriva_r_funzionario_profilo (
	id_funzionario int4 NOT NULL,
	id_profilo_app int4 NOT NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_funzionario_profilo PRIMARY KEY (id_funzionario, id_profilo_app),
	CONSTRAINT fk_scriva_d_profilo_app_05 FOREIGN KEY (id_profilo_app) REFERENCES scriva_d_profilo_app(id_profilo_app),
	CONSTRAINT fk_scriva_t_funzionario_02 FOREIGN KEY (id_funzionario) REFERENCES scriva_t_funzionario(id_funzionario)
);
COMMENT ON TABLE scriva_r_funzionario_profilo IS 'Serve per capire su quali TIPO ADEMPIMENTO (VIA, VINCA, AUA…) il funzionario può operare.';


-- scriva_r_geeco_layer_virtuali definition

-- Drop table

-- DROP TABLE scriva_r_geeco_layer_virtuali;

CREATE TABLE scriva_r_geeco_layer_virtuali (
	id_geeco_layer_virtuale int4 NOT NULL,
	id_config_geeco int4 NOT NULL,
	id_virtuale int4 NOT NULL, -- E' l'identificativo interno dell'applicativo Geeco con cui viene censito univocamente il layer
	layer_name varchar(50) NULL,
	geom_type varchar(50) NULL,
	label_pubblicazione varchar(100) NULL,
	type_geojson varchar(50) NULL,
	id_tipologia_oggetto int4 NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_geeco_layer_virtuali PRIMARY KEY (id_geeco_layer_virtuale),
	CONSTRAINT fk_scriva_d_geeco_uuid_01 FOREIGN KEY (id_config_geeco) REFERENCES scriva_d_config_geeco(id_config_geeco),
	CONSTRAINT fk_scriva_d_tipo_oggetto_05 FOREIGN KEY (id_tipologia_oggetto) REFERENCES scriva_d_tipologia_oggetto(id_tipologia_oggetto)
);
CREATE UNIQUE INDEX ak_scriva_r_geeco_layer_virtuali_01 ON scriva_r_geeco_layer_virtuali USING btree (id_config_geeco, id_virtuale);
COMMENT ON TABLE scriva_r_geeco_layer_virtuali IS 'Permette di definire i Layer Virtuali necessari per una specifica configurazione Geeco. (punti, linee, poligoni)';

-- Column comments

COMMENT ON COLUMN scriva_r_geeco_layer_virtuali.id_virtuale IS 'E'' l''identificativo interno dell''applicativo Geeco con cui viene censito univocamente il layer';


-- scriva_r_richiesta_accredito definition

-- Drop table

-- DROP TABLE scriva_r_richiesta_accredito;

CREATE TABLE scriva_r_richiesta_accredito (
	id_richiesta_accredito int4 NOT NULL,
	cf_accredito varchar(16) NOT NULL,
	cognome_accredito varchar(50) NULL,
	nome_accredito varchar(50) NULL,
	des_email_accredito varchar(100) NOT NULL,
	cod_verifica varchar(64) NULL,
	flg_autorizza_dati_personali numeric(1) NOT NULL,
	id_compilante int4 NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_r_richiesta_accredito_01 CHECK ((flg_autorizza_dati_personali = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_t_richiesta_accreditamento PRIMARY KEY (id_richiesta_accredito),
	CONSTRAINT fk_scriva_t_compilante_03 FOREIGN KEY (id_compilante) REFERENCES scriva_t_compilante(id_compilante) ON DELETE SET NULL
);


-- scriva_r_tipo_adempi_profilo definition

-- Drop table

-- DROP TABLE scriva_r_tipo_adempi_profilo;

CREATE TABLE scriva_r_tipo_adempi_profilo (
	id_tipo_adempi_profilo int4 NOT NULL,
	id_profilo_app int4 NOT NULL,
	id_tipo_adempimento int4 NOT NULL,
	flg_sola_lettura numeric(1) NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_r_tipo_adempi_profilo_01 CHECK ((flg_sola_lettura = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_tipo_adempi_profilo PRIMARY KEY (id_tipo_adempi_profilo),
	CONSTRAINT fk_scriva_d_profilo_app_01 FOREIGN KEY (id_profilo_app) REFERENCES scriva_d_profilo_app(id_profilo_app),
	CONSTRAINT fk_scriva_d_tipo_adempi_05 FOREIGN KEY (id_tipo_adempimento) REFERENCES scriva_d_tipo_adempimento(id_tipo_adempimento)
);
CREATE UNIQUE INDEX ak_scriva_r_tipo_adempi_profilo_01 ON scriva_r_tipo_adempi_profilo USING btree (id_profilo_app, id_tipo_adempimento);


-- scriva_r_tipo_competenza_cat definition

-- Drop table

-- DROP TABLE scriva_r_tipo_competenza_cat;

CREATE TABLE scriva_r_tipo_competenza_cat (
	id_tipo_competenza int4 NOT NULL,
	id_categoria_oggetto int4 NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_tipo_competenza_cat PRIMARY KEY (id_tipo_competenza, id_categoria_oggetto),
	CONSTRAINT fk_scriva_d_categoria_oggetto_01 FOREIGN KEY (id_categoria_oggetto) REFERENCES scriva_d_categoria_oggetto(id_categoria_oggetto),
	CONSTRAINT fk_scriva_d_tipo_competenza_02 FOREIGN KEY (id_tipo_competenza) REFERENCES scriva_d_tipo_competenza(id_tipo_competenza)
);


-- scriva_r_tipo_notifica_evento definition

-- Drop table

-- DROP TABLE scriva_r_tipo_notifica_evento;

CREATE TABLE scriva_r_tipo_notifica_evento (
	id_tipo_notifica_evento int4 NOT NULL,
	id_tipo_notifica int4 NOT NULL,
	id_tipo_evento int4 NOT NULL,
	data_inizio date NOT NULL,
	data_fine date NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	des_tipo_notifica_evento varchar(150) NULL,
	CONSTRAINT ak_scriva_r_tipo_notifica_evento_01 UNIQUE (id_tipo_notifica, id_tipo_evento),
	CONSTRAINT pk_scriva_r_tipo_notifica_evento PRIMARY KEY (id_tipo_notifica_evento),
	CONSTRAINT fk_scriva_d_tipo_evento_03 FOREIGN KEY (id_tipo_evento) REFERENCES scriva_d_tipo_evento(id_tipo_evento),
	CONSTRAINT fk_scriva_d_tipo_notifica_01 FOREIGN KEY (id_tipo_notifica) REFERENCES scriva_d_tipo_notifica(id_tipo_notifica)
);


-- scriva_s_nazione definition

-- Drop table

-- DROP TABLE scriva_s_nazione;

CREATE TABLE scriva_s_nazione (
	id_s_nazione numeric(3) NOT NULL,
	id_nazione numeric(3) NOT NULL,
	cod_istat_nazione varchar(3) NULL,
	cod_belfiore_nazione varchar(4) NULL,
	denom_nazione varchar(100) NOT NULL,
	id_continente numeric(2) NOT NULL,
	unione_europea bool NULL,
	cod_iso2 varchar(2) NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	dt_id_stato numeric(20) NOT NULL,
	dt_id_stato_prev numeric(20) NULL,
	dt_id_stato_next numeric(20) NULL,
	id_origine numeric(2) NOT NULL,
	CONSTRAINT pk_scriva_s_nazione PRIMARY KEY (id_s_nazione),
	CONSTRAINT fk_scriva_d_origine_limiti_02 FOREIGN KEY (id_origine) REFERENCES scriva_d_origine_limiti(id_origine),
	CONSTRAINT fk_scriva_s_nazione_01 FOREIGN KEY (id_nazione) REFERENCES scriva_d_nazione(id_nazione)
);
CREATE UNIQUE INDEX ak_scriva_s_nazione_01 ON scriva_s_nazione USING btree (id_nazione, data_inizio_validita);


-- scriva_s_regione definition

-- Drop table

-- DROP TABLE scriva_s_regione;

CREATE TABLE scriva_s_regione (
	id_s_regione numeric(6) NOT NULL,
	id_regione numeric(6) NOT NULL,
	cod_regione varchar(3) NOT NULL,
	denom_regione varchar(100) NOT NULL,
	id_nazione numeric(3) NOT NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	CONSTRAINT pk_scriva_s_regione PRIMARY KEY (id_s_regione),
	CONSTRAINT fk_scriva_d_regione_01 FOREIGN KEY (id_regione) REFERENCES scriva_d_regione(id_regione)
);
CREATE UNIQUE INDEX ak_scriva_s_regione_01 ON scriva_s_regione USING btree (id_regione, id_nazione, data_inizio_validita);
CREATE UNIQUE INDEX ak_scriva_s_regione_02 ON scriva_s_regione USING btree (cod_regione, id_nazione, data_inizio_validita);
CREATE UNIQUE INDEX ak_scriva_s_regione_03 ON scriva_s_regione USING btree (denom_regione, id_nazione, data_inizio_validita);


-- scriva_t_elabora definition

-- Drop table

-- DROP TABLE scriva_t_elabora;

CREATE TABLE scriva_t_elabora (
	id_elabora int4 NOT NULL, -- Identificativo univoco
	id_tipo_elabora int4 NOT NULL,
	id_stato_elabora int4 NOT NULL,
	data_richiesta date NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_t_elabora PRIMARY KEY (id_elabora),
	CONSTRAINT fk_scriva_d_stato_elabora_01 FOREIGN KEY (id_stato_elabora) REFERENCES scriva_d_stato_elabora(id_stato_elabora),
	CONSTRAINT fk_scriva_d_tipo_elabora_01 FOREIGN KEY (id_tipo_elabora) REFERENCES scriva_d_tipo_elabora(id_tipo_elabora)
);
COMMENT ON TABLE scriva_t_elabora IS 'Permette di memorizzare le elaborazioni  richieste o necessarie a funzionalità applicative.';

-- Column comments

COMMENT ON COLUMN scriva_t_elabora.id_elabora IS 'Identificativo univoco';


-- scriva_t_tentativo_pagamento definition

-- Drop table

-- DROP TABLE scriva_t_tentativo_pagamento;

CREATE TABLE scriva_t_tentativo_pagamento (
	id_tentativo_pagamento int4 NOT NULL,
	id_stato_tentativo_pag int4 NOT NULL,
	identificativo_pagamento_ppay varchar(50) NULL,
	hash_pagamento varchar(44) NULL,
	tipo_bollo varchar(2) NULL,
	flg_solo_marca numeric NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_t_tentativo_pagamento_01 CHECK ((flg_solo_marca = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_t_tentativo_pagamento PRIMARY KEY (id_tentativo_pagamento),
	CONSTRAINT fk_scriva_d_stato_tenta_pag_01 FOREIGN KEY (id_stato_tentativo_pag) REFERENCES scriva_d_stato_tentativo_pag(id_stato_tentativo_pag)
);


-- scriva_d_adempimento definition

-- Drop table

-- DROP TABLE scriva_d_adempimento;

CREATE TABLE scriva_d_adempimento (
	id_adempimento int4 NOT NULL,
	des_adempimento varchar(100) NULL,
	des_estesa_adempimento varchar(200) NULL,
	ordinamento_adempimento int4 NULL,
	flg_attivo numeric(1) NOT NULL, -- 0 - non attivo¶1 - attivo
	id_tipo_adempimento int4 DEFAULT 1 NOT NULL,
	cod_adempimento varchar(30) NOT NULL,
	ind_visibile varchar(20) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_d_adempimento PRIMARY KEY (id_adempimento),
	CONSTRAINT fk_scriva_d_tipo_adempi_01 FOREIGN KEY (id_tipo_adempimento) REFERENCES scriva_d_tipo_adempimento(id_tipo_adempimento)
);
CREATE UNIQUE INDEX ak_scriva_d_adempimneto_01 ON scriva_d_adempimento USING btree (cod_adempimento);
COMMENT ON TABLE scriva_d_adempimento IS 'E'' un iter amministrativo innescato a fronte della richiesta di un privato che richiede alla pubblica amministrazione un provvedimento autorizzativo per un oggetto (un opera, un progetto, …).';

-- Column comments

COMMENT ON COLUMN scriva_d_adempimento.flg_attivo IS '0 - non attivo
1 - attivo';


-- scriva_d_destinatario definition

-- Drop table

-- DROP TABLE scriva_d_destinatario;

CREATE TABLE scriva_d_destinatario (
	id_destinatario int4 NOT NULL,
	id_tipo_destinatario int4 NOT NULL,
	id_profilo_app int4 NULL,
	id_componente_app int4 NULL,
	cod_destinatario varchar(50) NOT NULL,
	den_destinatario varchar(100) NULL,
	nota_destinatario varchar(250) NULL,
	nome varchar(100) NULL,
	cognome varchar(100) NULL,
	des_ufficio_ente varchar(150) NULL,
	des_nota varchar(150) NULL,
	des_email varchar(150) NULL,
	num_cellulare varchar(50) NULL,
	des_servizio_applicativo varchar(150) NULL,
	flg_verifica_preferenze_notifiche numeric(1) DEFAULT 0 NOT NULL,
	data_inizio date NOT NULL,
	data_fine date NULL,
	des_pec varchar(150) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	ind_verifica_destinatario varchar(20) NULL, -- Indica se il destinatario e' obbligatorio o meno¶puo' assumere uno dei seguenti valori:¶M (mandatory)¶O (opzionale)¶PROPONENTE_PG o altro codice in base a quale occorrenza è alternativo (questo ultimo valore post-avvio)
	CONSTRAINT ak_scriva_d_destinatario_01 UNIQUE (cod_destinatario),
	CONSTRAINT chk_scriva_d_destinatario_01 CHECK ((flg_verifica_preferenze_notifiche = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_d_destinatario PRIMARY KEY (id_destinatario),
	CONSTRAINT fk_scriva_d_componente_app_05 FOREIGN KEY (id_componente_app) REFERENCES scriva_d_componente_app(id_componente_app),
	CONSTRAINT fk_scriva_d_profilo_app_06 FOREIGN KEY (id_profilo_app) REFERENCES scriva_d_profilo_app(id_profilo_app),
	CONSTRAINT fk_scriva_d_tipo_destinatario_01 FOREIGN KEY (id_tipo_destinatario) REFERENCES scriva_d_tipo_destinatario(id_tipo_destinatario)
);
COMMENT ON TABLE scriva_d_destinatario IS 'Definisce i destinatari delle notifiche, gestiti dall''applicativo
Es.
COMPILANTE
ABILITATO_CONSULTA
ABILITATO_GESTIONE
ALTRI RUOLI SPECIFICI
FUNZIONARIO';

-- Column comments

COMMENT ON COLUMN scriva_d_destinatario.ind_verifica_destinatario IS 'Indica se il destinatario e'' obbligatorio o meno
puo'' assumere uno dei seguenti valori:
M (mandatory)
O (opzionale)
PROPONENTE_PG o altro codice in base a quale occorrenza è alternativo (questo ultimo valore post-avvio)';


-- scriva_d_oggetto_app definition

-- Drop table

-- DROP TABLE scriva_d_oggetto_app;

CREATE TABLE scriva_d_oggetto_app (
	id_oggetto_app int4 NOT NULL,
	id_tipo_ogg_app int4 NOT NULL,
	cod_oggetto_app varchar(50) NOT NULL,
	des_oggetto_app varchar(100) NULL,
	id_tipo_evento int4 NULL,
	flg_previsto_da_gestore_processo numeric(1) DEFAULT 0 NOT NULL, -- L'oggetto si riferisce ad una funzionalità gestita all'interno di un eventuale processo esterno di istruttoria
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_d_oggetto_app_01 CHECK ((flg_previsto_da_gestore_processo = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_d_oggetto_app PRIMARY KEY (id_oggetto_app),
	CONSTRAINT fk_scriva_d_tipo_evento_02 FOREIGN KEY (id_tipo_evento) REFERENCES scriva_d_tipo_evento(id_tipo_evento),
	CONSTRAINT fk_scriva_d_tipo_ogg_app_01 FOREIGN KEY (id_tipo_ogg_app) REFERENCES scriva_d_tipo_oggetto_app(id_tipo_ogg_app)
);
CREATE UNIQUE INDEX ak_scriva_d_oggetto_app_01 ON scriva_d_oggetto_app USING btree (id_tipo_ogg_app, cod_oggetto_app);

-- Column comments

COMMENT ON COLUMN scriva_d_oggetto_app.flg_previsto_da_gestore_processo IS 'L''oggetto si riferisce ad una funzionalità gestita all''interno di un eventuale processo esterno di istruttoria';


-- scriva_d_provincia definition

-- Drop table

-- DROP TABLE scriva_d_provincia;

CREATE TABLE scriva_d_provincia (
	id_provincia numeric(7) NOT NULL,
	cod_provincia varchar(3) NOT NULL,
	denom_provincia varchar(100) NOT NULL,
	sigla_provincia varchar(2) NULL,
	id_regione numeric(6) NOT NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	CONSTRAINT pk_scriva_d_provincia PRIMARY KEY (id_provincia),
	CONSTRAINT fk_scriva_d_regione_02 FOREIGN KEY (id_regione) REFERENCES scriva_d_regione(id_regione)
);
CREATE UNIQUE INDEX ak_scriva_d_provincia_01 ON scriva_d_provincia USING btree (cod_provincia, id_regione);
CREATE UNIQUE INDEX ak_scriva_d_provincia_02 ON scriva_d_provincia USING btree (denom_provincia, id_regione, data_inizio_validita);
CREATE UNIQUE INDEX ak_scriva_d_provincia_03 ON scriva_d_provincia USING btree (id_regione, sigla_provincia, data_inizio_validita);


-- scriva_d_template definition

-- Drop table

-- DROP TABLE scriva_d_template;

CREATE TABLE scriva_d_template (
	id_template int4 NOT NULL,
	id_adempimento int4 NOT NULL,
	cod_template varchar(20) NULL,
	des_template varchar(50) NULL,
	pdf_template varchar(250) NULL,
	data_inizio_validita date NULL,
	data_cessazione date NULL,
	flg_attivo numeric(1) NOT NULL,
	json_configura_template jsonb NULL, -- Bisogna compilare l'attributo con gli indicatori previsti per l'adempimento.  Questi indicatori sono una configurazione che ha effetto sul comportamento del front end. Si rimanda alle specifiche di Analisi.
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_d_template_01 CHECK ((flg_attivo = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_d_template PRIMARY KEY (id_template),
	CONSTRAINT fk_scriva_d_adempimento_05 FOREIGN KEY (id_adempimento) REFERENCES scriva_d_adempimento(id_adempimento)
);
COMMENT ON TABLE scriva_d_template IS 'E'' il contenitore logico dei quadri di un procedimento.  Ne specifica l’ordine di presentazione e contiene variabili di configurazione che influenzano il comportamento dei quadri.';

-- Column comments

COMMENT ON COLUMN scriva_d_template.json_configura_template IS 'Bisogna compilare l''attributo con gli indicatori previsti per l''adempimento.  Questi indicatori sono una configurazione che ha effetto sul comportamento del front end. Si rimanda alle specifiche di Analisi.';


-- scriva_log_acq_allegato definition

-- Drop table

-- DROP TABLE scriva_log_acq_allegato;

CREATE TABLE scriva_log_acq_allegato (
	id_log_acq_allegato int4 NOT NULL,
	des_log_acq_allegato varchar(2000) NULL, -- Lista delle anomalie gestite :¶¶CODE 001: file non recuperato da SOURCE ¶Condizione : Non presente su index di SIVIA)¶Comportamento : Non carichiamo il file su scriva, Lasciamo uuid-index farlocco, nessun aggiornamento sui metadati; Tracciare Anomalia¶¶CODE 002: file recuperato da index con dimensione a 0 ¶Condizione : Quando da estrazione index Souce l'attributo file size restituito da index e' = 0¶Comportamento : Non carichiamo il file su scriva, Lasciamo uuid-index farlocco, nessun aggiornamento sui metadati; Tracciare Anomalia¶ ¶CODE 003: Impossibile ricercaresu Index Source allegato¶Condizione : Non e' possibile ricercare su index per l'allegato xxx orig id doc xxx orig id istanza docum xxx orig id atto XXX¶Comportamento : Non carichiamo il file su scriva, Lasciamo uuid-index farlocco, nessun aggiornamento sui metadati; Tracciare Anomalia¶¶Tag CODE 004: Impossibile eliminareistanza su SCRIVA¶Condizione : ---¶Comportamento : Tracciare Anomalia¶ ¶Tag CODE 005: Scatenata anomalia durante cancellazione file da Index di SCRIVA¶Condizione : -,--¶Comportamento : Tracciare Anomalia¶¶Tag CODE 006 Errore durante il caricamento dei file su index¶Condizione : Quando cerchiamo di uplodare un file (che arrivi da sivia o arrivi dal campo blob) se per qualche ragione l'uuid index mi torna null o vuoto¶Comportamento :  Tracciare Anomalia
	id_elabora int4 NOT NULL,
	fase_log varchar(200) NULL,
	operation_id varchar(100) NULL, -- metodo del servizio di scriva che ha sollevato l'eventuale eccezione
	fonte varchar(20) NOT NULL, -- fonte allegato istanza,
	id_reg_entita_porting int4 NULL, -- chiave eventuale del registro di porting
	scriva_id_istanza int4 NULL,
	scriva_id_allegato_istanza int4 NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_log_acq_allegato PRIMARY KEY (id_log_acq_allegato),
	CONSTRAINT fk_scriva_t_elabora_04 FOREIGN KEY (id_elabora) REFERENCES scriva_t_elabora(id_elabora)
);
COMMENT ON TABLE scriva_log_acq_allegato IS 'Tabella di log per tracciare acquisizione allegati legati all''istanza';

-- Column comments

COMMENT ON COLUMN scriva_log_acq_allegato.des_log_acq_allegato IS 'Lista delle anomalie gestite :

CODE 001: file non recuperato da SOURCE 
Condizione : Non presente su index di SIVIA)
Comportamento : Non carichiamo il file su scriva, Lasciamo uuid-index farlocco, nessun aggiornamento sui metadati; Tracciare Anomalia

CODE 002: file recuperato da index con dimensione a 0 
Condizione : Quando da estrazione index Souce l''attributo file size restituito da index e'' = 0
Comportamento : Non carichiamo il file su scriva, Lasciamo uuid-index farlocco, nessun aggiornamento sui metadati; Tracciare Anomalia
 
CODE 003: Impossibile ricercaresu Index Source allegato
Condizione : Non e'' possibile ricercare su index per l''allegato xxx orig id doc xxx orig id istanza docum xxx orig id atto XXX
Comportamento : Non carichiamo il file su scriva, Lasciamo uuid-index farlocco, nessun aggiornamento sui metadati; Tracciare Anomalia

Tag CODE 004: Impossibile eliminareistanza su SCRIVA
Condizione : ---
Comportamento : Tracciare Anomalia
 
Tag CODE 005: Scatenata anomalia durante cancellazione file da Index di SCRIVA
Condizione : ---
Comportamento : Tracciare Anomalia

Tag CODE 006 Errore durante il caricamento dei file su index
Condizione : Quando cerchiamo di uplodare un file (che arrivi da sivia o arrivi dal campo blob) se per qualche ragione l''uuid index mi torna null o vuoto
Comportamento :  Tracciare Anomalia';
COMMENT ON COLUMN scriva_log_acq_allegato.operation_id IS 'metodo del servizio di scriva che ha sollevato l''eventuale eccezione';
COMMENT ON COLUMN scriva_log_acq_allegato.fonte IS 'fonte allegato istanza';
COMMENT ON COLUMN scriva_log_acq_allegato.id_reg_entita_porting IS 'chiave eventuale del registro di porting';


-- scriva_log_acq_istanza definition

-- Drop table

-- DROP TABLE scriva_log_acq_istanza;

CREATE TABLE scriva_log_acq_istanza (
	id_log_acq_istanza int4 NOT NULL,
	des_log_acq_istanza varchar(2000) NULL,
	id_elabora int4 NOT NULL,
	fase_log varchar(200) NULL,
	operation_id varchar(100) NULL, -- metodo del servizio di scriva che ha sollevato l'eventuale eccezione
	fonte varchar(20) NOT NULL, -- fonte del soggetto presente nella w_soggetto,
	id_reg_entita_porting int4 NULL, -- chiave eventuale del registro di porting
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_log_acq_istanza PRIMARY KEY (id_log_acq_istanza),
	CONSTRAINT fk_scriva_t_elabora_03 FOREIGN KEY (id_elabora) REFERENCES scriva_t_elabora(id_elabora)
);
CREATE INDEX ie_scriva_log_acq_istanza ON scriva_log_acq_istanza USING btree (id_elabora);
COMMENT ON TABLE scriva_log_acq_istanza IS 'Tabella di log per tracciare inizio e fine fasi di acquisizione istanze ed eventuali anomalie riscontrate';

-- Column comments

COMMENT ON COLUMN scriva_log_acq_istanza.operation_id IS 'metodo del servizio di scriva che ha sollevato l''eventuale eccezione';
COMMENT ON COLUMN scriva_log_acq_istanza.fonte IS 'fonte del soggetto presente nella w_soggetto';
COMMENT ON COLUMN scriva_log_acq_istanza.id_reg_entita_porting IS 'chiave eventuale del registro di porting';


-- scriva_log_acq_soggetto definition

-- Drop table

-- DROP TABLE scriva_log_acq_soggetto;

CREATE TABLE scriva_log_acq_soggetto (
	id_log_acq_soggetto int4 NOT NULL,
	data_log_acq_soggetto date NOT NULL,
	des_log_acq_soggetto varchar(2000) NULL,
	id_elabora int4 NOT NULL,
	fase_log varchar(200) NULL,
	operation_id varchar(100) NULL, -- metodo del servizio di scriva che ha sollevato l'eventuale eccezione
	cod_acq_soggetto varchar(100) NULL, -- identificativo del record w_soggetto elaborato,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	fonte varchar(20) NOT NULL, -- fonte del soggetto presente nella w_soggetto
	orig_soggetto int4 NOT NULL, -- chiave originaria del soggetto presente nella w_soggetto
	target_soggetto int4 NOT NULL, -- chiave del soggetto acquisito generata su SCRIVA
	ind_porting int4 NOT NULL, -- indicatore dell'esito dell'acquisizione del soggetto
	data_ins_acq_soggetto timestamp NOT NULL,
	CONSTRAINT pk_scriva_log_acq_soggetto PRIMARY KEY (id_log_acq_soggetto),
	CONSTRAINT fk_scriva_t_elabora_02 FOREIGN KEY (id_elabora) REFERENCES scriva_t_elabora(id_elabora)
);
CREATE INDEX ie_scriva_log_acq_soggetto ON scriva_log_acq_soggetto USING btree (id_elabora);
COMMENT ON TABLE scriva_log_acq_soggetto IS 'Tabella di log per tracciare inizio e fine fasi di acquisizione soggetti ed eventuali anomalie riscontrate';

-- Column comments

COMMENT ON COLUMN scriva_log_acq_soggetto.operation_id IS 'metodo del servizio di scriva che ha sollevato l''eventuale eccezione';
COMMENT ON COLUMN scriva_log_acq_soggetto.cod_acq_soggetto IS 'identificativo del record w_soggetto elaborato';
COMMENT ON COLUMN scriva_log_acq_soggetto.fonte IS 'fonte del soggetto presente nella w_soggetto';
COMMENT ON COLUMN scriva_log_acq_soggetto.orig_soggetto IS 'chiave originaria del soggetto presente nella w_soggetto';
COMMENT ON COLUMN scriva_log_acq_soggetto.target_soggetto IS 'chiave del soggetto acquisito generata su SCRIVA';
COMMENT ON COLUMN scriva_log_acq_soggetto.ind_porting IS 'indicatore dell''esito dell''acquisizione del soggetto';


-- scriva_r_adempi_categoria_ogg definition

-- Drop table

-- DROP TABLE scriva_r_adempi_categoria_ogg;

CREATE TABLE scriva_r_adempi_categoria_ogg (
	id_adempimento int4 NOT NULL,
	id_categoria_oggetto int4 NOT NULL,
	ordinamento_adempi_cat_ogg int4 NULL,
	ind_visibile varchar(20) NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_adempi_categoria_ogg PRIMARY KEY (id_adempimento, id_categoria_oggetto),
	CONSTRAINT fk_scriva_d_adempimento_13 FOREIGN KEY (id_adempimento) REFERENCES scriva_d_adempimento(id_adempimento),
	CONSTRAINT fk_scriva_d_categoria_oggetto_03 FOREIGN KEY (id_categoria_oggetto) REFERENCES scriva_d_categoria_oggetto(id_categoria_oggetto)
);
COMMENT ON TABLE scriva_r_adempi_categoria_ogg IS 'Permette di definire le categorie oggetto previste per uno specifico adempimento.';


-- scriva_r_adempi_config definition

-- Drop table

-- DROP TABLE scriva_r_adempi_config;

CREATE TABLE scriva_r_adempi_config (
	id_adempi_config int4 NOT NULL,
	id_adempimento int4 NOT NULL,
	id_informazione_scriva int4 NOT NULL,
	chiave varchar(150) NOT NULL,
	valore varchar(1000) NOT NULL,
	ordinamento numeric(4) NOT NULL,
	flg_attivo numeric(1) NOT NULL,
	note varchar(100) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_adempi_config PRIMARY KEY (id_adempi_config),
	CONSTRAINT scriva_r_procedimento_config_flg_attivo_check CHECK ((flg_attivo = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT fk_scriva_d_adempimento_08 FOREIGN KEY (id_adempimento) REFERENCES scriva_d_adempimento(id_adempimento),
	CONSTRAINT fk_scriva_d_info_scriva_02 FOREIGN KEY (id_informazione_scriva) REFERENCES scriva_d_informazioni_scriva(id_informazione_scriva)
);
CREATE INDEX ie_scriva_r_adempi_config_01 ON scriva_r_adempi_config USING btree (id_adempimento, id_informazione_scriva, chiave);
COMMENT ON TABLE scriva_r_adempi_config IS 'Serve per definire, per un procedimento, quali sono le fonti dati di riferimento per le informazioni inerenti Persone Fisiche - PF, Persone Giuridiche – PG, Persone Giuridiche Pubbliche – PB e altri elementi   (l’insieme di questi elementi e'' contenuto nella tabella scriva_d_informazioni_scriva)';


-- scriva_r_adempi_esito_proced definition

-- Drop table

-- DROP TABLE scriva_r_adempi_esito_proced;

CREATE TABLE scriva_r_adempi_esito_proced (
	id_adempi_esito_proced int4 NOT NULL,
	id_adempimento int4 NOT NULL,
	id_esito_procedimento int4 NOT NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	ind_esito varchar(20) NULL, -- L'esito può valere solo per procedimento statale,¶solo per quello regionale o per entrambi. ¶¶Es.¶1 - valevole solo per adempimento in corso¶2 - valevole solo per esito statale¶3 - può essere utilizzato sia per adempimento in corso che adempimento statale
	ordinamento int4 NULL,
	CONSTRAINT ak_scriva_r_adempi_esito_proced_01 UNIQUE (id_adempimento, id_esito_procedimento),
	CONSTRAINT pk_scriva_r_adempi_esito_proced PRIMARY KEY (id_adempi_esito_proced),
	CONSTRAINT fk_scriva_d_adempimento_18 FOREIGN KEY (id_adempimento) REFERENCES scriva_d_adempimento(id_adempimento),
	CONSTRAINT fk_scriva_d_esito_proc_02 FOREIGN KEY (id_esito_procedimento) REFERENCES scriva_d_esito_procedimento(id_esito_procedimento)
);

-- Column comments

COMMENT ON COLUMN scriva_r_adempi_esito_proced.ind_esito IS 'L''esito può valere solo per procedimento statale,
solo per quello regionale o per entrambi. 

Es.
1 - valevole solo per adempimento in corso
2 - valevole solo per esito statale
3 - può essere utilizzato sia per adempimento in corso che adempimento statale';


-- scriva_r_adempi_est_allegato definition

-- Drop table

-- DROP TABLE scriva_r_adempi_est_allegato;

CREATE TABLE scriva_r_adempi_est_allegato (
	id_adempimento int4 NOT NULL,
	estensione_allegato varchar(20) NOT NULL,
	des_estensione_allegato varchar(100) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_adempi_est_allegato PRIMARY KEY (id_adempimento, estensione_allegato),
	CONSTRAINT fk_scriva_d_adempimento_10 FOREIGN KEY (id_adempimento) REFERENCES scriva_d_adempimento(id_adempimento)
);
COMMENT ON TABLE scriva_r_adempi_est_allegato IS 'Permette di specificare le estensioni degli allegati previste per un determinato adempimento.  ES. pdf - formato pdf jpeg - immagine jpeg';


-- scriva_r_adempi_provincia definition

-- Drop table

-- DROP TABLE scriva_r_adempi_provincia;

CREATE TABLE scriva_r_adempi_provincia (
	id_adempimento int4 NOT NULL,
	id_provincia numeric(7) NOT NULL,
	ordinamento_adempi_provincia int4 NULL, -- Definisce l’ordine con cui le province configurate per l’adempimento verranno presentate. 
	flg_limitrofa numeric(1) DEFAULT 0 NOT NULL, -- ES. Sia le province della Regione Piemonte sia le province limitrofe alla Regione Piemonte (flg_limitrofa=1). 
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_r_adempi_provincia_01 CHECK ((flg_limitrofa = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_adempi_provincia PRIMARY KEY (id_adempimento, id_provincia),
	CONSTRAINT fk_scriva_d_adempimento_16 FOREIGN KEY (id_adempimento) REFERENCES scriva_d_adempimento(id_adempimento),
	CONSTRAINT fk_scriva_d_provincia_05 FOREIGN KEY (id_provincia) REFERENCES scriva_d_provincia(id_provincia)
);
COMMENT ON TABLE scriva_r_adempi_provincia IS 'Permette di definire (se necessario) le province previste per l’adempimento.';

-- Column comments

COMMENT ON COLUMN scriva_r_adempi_provincia.ordinamento_adempi_provincia IS 'Definisce l’ordine con cui le province configurate per l’adempimento verranno presentate. ';
COMMENT ON COLUMN scriva_r_adempi_provincia.flg_limitrofa IS 'ES. Sia le province della Regione Piemonte sia le province limitrofe alla Regione Piemonte (flg_limitrofa=1). ';


-- scriva_r_adempi_ruolo_app definition

-- Drop table

-- DROP TABLE scriva_r_adempi_ruolo_app;

CREATE TABLE scriva_r_adempi_ruolo_app (
	id_adempimento int4 NOT NULL,
	id_gestione_attore int4 NOT NULL,
	id_config_geeco int4 NOT NULL,
	json_startupinfo jsonb NULL,
	json_editinglayers jsonb NULL,
	json_quitinfo jsonb NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_adempi_ruolo_app PRIMARY KEY (id_adempimento, id_gestione_attore),
	CONSTRAINT fk_scriva_d_adempimento_07 FOREIGN KEY (id_adempimento) REFERENCES scriva_d_adempimento(id_adempimento),
	CONSTRAINT fk_scriva_d_geeco_uuid_02 FOREIGN KEY (id_config_geeco) REFERENCES scriva_d_config_geeco(id_config_geeco),
	CONSTRAINT fk_scriva_d_gestione_attore_02 FOREIGN KEY (id_gestione_attore) REFERENCES scriva_d_gestione_attore(id_gestione_attore)
);
COMMENT ON TABLE scriva_r_adempi_ruolo_app IS 'Permette di associare una configurazione Geeco all''adempimento e alla specifica modalità di gestione dell''attore di gestione (write, read, etc..) indicando la configurazione json richiesta per l''adempimento  La configurazione json e'' il formato, condiviso con GEECO, che deve avere l''informazione passata al servizio di GEECO per rappresentare in mappa l''oggetto assegnato all''istanza.';


-- scriva_r_adempi_ruolo_compila definition

-- Drop table

-- DROP TABLE scriva_r_adempi_ruolo_compila;

CREATE TABLE scriva_r_adempi_ruolo_compila (
	id_ruolo_compilante int4 NOT NULL,
	id_adempimento int4 NOT NULL,
	flg_popola_richiedente numeric(1) DEFAULT 0 NOT NULL, -- Definisce se per quel ruolo compilante e quel procedimento e' prevista (il flag vale 1) o meno la precompilazione dei campi del soggetto richiedente (dati del compilante in linea); se il flag vale 1 il sistema assume che compilante e richiedente dovrebbero coincidere e precompila i dati del richiedente con quelli già inseriti per il compilante; 
	flg_modulo_delega numeric(1) DEFAULT 0 NOT NULL, -- Se per quel ruolo compilante e quel procedimento il flg_modulo_delega=1 e' necessario visualizzare nel quadro Soggetti il pulsante SCARICA MODULO DELEGA, che permette di scaricare un modulo di delega precompilato con le informazioni di soggetto delegante e soggetto delegato.,
	flg_modulo_procura numeric(1) DEFAULT 0 NOT NULL, -- Abbiamo usato questo campo per estrarre solo alcuni ruoli nel caso in cui il soggetto sia una PF.
	flg_ruolo_default numeric(1) DEFAULT 0 NOT NULL, -- Se questo flag vale 1, quando il soggetto richiedente e' persona fisica e coincide con il compilante, non deve essere richiesto il ruolo del compilante rispetto al soggetto richiedente e su DB si imposta in automatico il ruolo del compilante.
	flg_revocabile numeric(1) DEFAULT 0 NOT NULL, -- Per ruoli compilanti diversi da richiedente, il flag indica se e' previsto che il compilante dell’istanza possa essere revocato.,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	ordinamento int4 NULL,
	ind_visibile varchar(20) NULL, -- Indica la componente applicativo su cui è utilizzabile il ruolo compilante
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_r_adempi_ruolo_compila_05 CHECK ((flg_revocabile = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_adempi_ruolo_compila PRIMARY KEY (id_adempimento, id_ruolo_compilante),
	CONSTRAINT scriva_r_proced_ruolo_compila_flg_modulo_delega_check CHECK ((flg_modulo_delega = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT scriva_r_proced_ruolo_compila_flg_modulo_procura_check CHECK ((flg_modulo_procura = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT scriva_r_proced_ruolo_compila_flg_popola_richiedente_check CHECK ((flg_popola_richiedente = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT scriva_r_proced_ruolo_compila_flg_ruolo_default_check CHECK ((flg_ruolo_default = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT fk_scriva_d_adempimento_02 FOREIGN KEY (id_adempimento) REFERENCES scriva_d_adempimento(id_adempimento),
	CONSTRAINT fk_scriva_d_ruolo_compila_02 FOREIGN KEY (id_ruolo_compilante) REFERENCES scriva_d_ruolo_compilante(id_ruolo_compilante)
);
COMMENT ON TABLE scriva_r_adempi_ruolo_compila IS 'Vengono censiti tutti i ruoli abilitati alla compilazione per l’adempimento e, per ogni ruolo abilitato, vengono definite delle caratteristiche specifiche di compilazione mediante la valorizzazione di alcuni flag';

-- Column comments

COMMENT ON COLUMN scriva_r_adempi_ruolo_compila.flg_popola_richiedente IS 'Definisce se per quel ruolo compilante e quel procedimento e'' prevista (il flag vale 1) o meno la precompilazione dei campi del soggetto richiedente (dati del compilante in linea); se il flag vale 1 il sistema assume che compilante e richiedente dovrebbero coincidere e precompila i dati del richiedente con quelli già inseriti per il compilante; ';
COMMENT ON COLUMN scriva_r_adempi_ruolo_compila.flg_modulo_delega IS 'Se per quel ruolo compilante e quel procedimento il flg_modulo_delega=1 e'' necessario visualizzare nel quadro Soggetti il pulsante SCARICA MODULO DELEGA, che permette di scaricare un modulo di delega precompilato con le informazioni di soggetto delegante e soggetto delegato.';
COMMENT ON COLUMN scriva_r_adempi_ruolo_compila.flg_modulo_procura IS 'Abbiamo usato questo campo per estrarre solo alcuni ruoli nel caso in cui il soggetto sia una PF.';
COMMENT ON COLUMN scriva_r_adempi_ruolo_compila.flg_ruolo_default IS 'Se questo flag vale 1, quando il soggetto richiedente e'' persona fisica e coincide con il compilante, non deve essere richiesto il ruolo del compilante rispetto al soggetto richiedente e su DB si imposta in automatico il ruolo del compilante.';
COMMENT ON COLUMN scriva_r_adempi_ruolo_compila.flg_revocabile IS 'Per ruoli compilanti diversi da richiedente, il flag indica se e'' previsto che il compilante dell’istanza possa essere revocato.';
COMMENT ON COLUMN scriva_r_adempi_ruolo_compila.ind_visibile IS 'Indica la componente applicativo su cui è utilizzabile il ruolo compilante';


-- scriva_r_adempi_ruolo_sogg definition

-- Drop table

-- DROP TABLE scriva_r_adempi_ruolo_sogg;

CREATE TABLE scriva_r_adempi_ruolo_sogg (
	id_ruolo_soggetto int4 NOT NULL,
	id_adempimento int4 NOT NULL,
	ordinamento int4 NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_adempi_ruolo_sogg PRIMARY KEY (id_adempimento, id_ruolo_soggetto),
	CONSTRAINT fk_scriva_d_adempimento_04 FOREIGN KEY (id_adempimento) REFERENCES scriva_d_adempimento(id_adempimento),
	CONSTRAINT fk_scriva_d_ruolo_soggetto_01 FOREIGN KEY (id_ruolo_soggetto) REFERENCES scriva_d_ruolo_soggetto(id_ruolo_soggetto)
);


-- scriva_r_adempi_tipo_allegato definition

-- Drop table

-- DROP TABLE scriva_r_adempi_tipo_allegato;

CREATE TABLE scriva_r_adempi_tipo_allegato (
	id_adempimento int4 NOT NULL,
	id_tipologia_allegato int4 NOT NULL,
	flg_obbligo numeric(1) NOT NULL,
	flg_firma_digitale numeric(1) NOT NULL,
	flg_riservato numeric(1) NOT NULL,
	flg_nota numeric(1) NOT NULL,
	flg_firma_non_valida_bloccante numeric(1) NOT NULL,
	flg_integrazione numeric(1) DEFAULT 0 NOT NULL,
	ordinamento_adem_tipo_allega int4 NULL,
	ind_visibile varchar(20) NULL,
	ind_modifica varchar(20) NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_r_adempi_tipo_allegato_01 CHECK ((flg_obbligo = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_r_adempi_tipo_allegato_02 CHECK ((flg_firma_digitale = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_r_adempi_tipo_allegato_03 CHECK ((flg_riservato = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_r_adempi_tipo_allegato_05 CHECK ((flg_nota = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_r_adempi_tipo_allegato_06 CHECK ((flg_firma_non_valida_bloccante = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_r_adempi_tipo_allegato_07 CHECK ((flg_integrazione = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_adempi_tipo_allegato PRIMARY KEY (id_adempimento, id_tipologia_allegato),
	CONSTRAINT fk_scriva_d_adempimento_09 FOREIGN KEY (id_adempimento) REFERENCES scriva_d_adempimento(id_adempimento),
	CONSTRAINT fk_scriva_d_tipo_allegato_01 FOREIGN KEY (id_tipologia_allegato) REFERENCES scriva_d_tipologia_allegato(id_tipologia_allegato)
);


-- scriva_r_adempi_tipo_oggetto definition

-- Drop table

-- DROP TABLE scriva_r_adempi_tipo_oggetto;

CREATE TABLE scriva_r_adempi_tipo_oggetto (
	id_adempimento int4 NOT NULL,
	id_tipologia_oggetto int4 NOT NULL,
	flg_assegna numeric(1) DEFAULT 0 NOT NULL,
	ordinamento int4 NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_r_adempi_tipo_oggetto_01 CHECK ((flg_assegna = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_adempi_tipo_oggetto PRIMARY KEY (id_adempimento, id_tipologia_oggetto),
	CONSTRAINT fk_scriva_d_adempimento_06 FOREIGN KEY (id_adempimento) REFERENCES scriva_d_adempimento(id_adempimento),
	CONSTRAINT fk_scriva_d_tipo_oggetto_02 FOREIGN KEY (id_tipologia_oggetto) REFERENCES scriva_d_tipologia_oggetto(id_tipologia_oggetto)
);
COMMENT ON TABLE scriva_r_adempi_tipo_oggetto IS 'Mette in relazione l’adempimento con i tipi di oggetto che possono essere trattati.   La tipologia di oggetto e'' assegnata dal sistema in base alla configurazione prevista per l’adempimento oppure e'' selezionata dall’utente durante la presentazione di una istanza.';


-- scriva_r_adempi_vincolo_aut definition

-- Drop table

-- DROP TABLE scriva_r_adempi_vincolo_aut;

CREATE TABLE scriva_r_adempi_vincolo_aut (
	id_adempimento int4 NOT NULL,
	id_vincolo_autorizza int4 NOT NULL,
	ordinamento_adempi_vincolo int4 NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_adempi_vincolo_aut PRIMARY KEY (id_adempimento, id_vincolo_autorizza),
	CONSTRAINT fk_scriva_d_adempimento_15 FOREIGN KEY (id_adempimento) REFERENCES scriva_d_adempimento(id_adempimento),
	CONSTRAINT fk_scriva_d_vincolo_autorizza_01 FOREIGN KEY (id_vincolo_autorizza) REFERENCES scriva_d_vincolo_autorizza(id_vincolo_autorizza)
);


-- scriva_r_configura_ruolo_sogg definition

-- Drop table

-- DROP TABLE scriva_r_configura_ruolo_sogg;

CREATE TABLE scriva_r_configura_ruolo_sogg (
	id_ruolo_soggetto int4 NOT NULL,
	id_adempimento int4 NOT NULL,
	id_ruolo_compilante int4 NOT NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_configura_ruolo_sogg PRIMARY KEY (id_ruolo_soggetto, id_adempimento, id_ruolo_compilante)
--	CONSTRAINT fk_scriva_r_proc_ruolo_comp_01 FOREIGN KEY (id_ruolo_compilante,id_adempimento) REFERENCES <?>(),
--	CONSTRAINT fk_scriva_r_proc_ruolo_sogg_01 FOREIGN KEY (id_ruolo_soggetto,id_adempimento) REFERENCES <?>()
);


-- scriva_r_destinatario_canale definition

-- Drop table

-- DROP TABLE scriva_r_destinatario_canale;

CREATE TABLE scriva_r_destinatario_canale (
	id_destinatario_canale int4 NOT NULL,
	id_destinatario int4 NOT NULL,
	id_canale_notifica int4 NOT NULL,
	flg_canale_default numeric(1) DEFAULT 0 NOT NULL,
	data_inizio date NOT NULL,
	data_fine date NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT ak_scriva_r_destinatario_canale_01 UNIQUE (id_destinatario, id_canale_notifica),
	CONSTRAINT chk_scriva_r_destinatario_canale_01 CHECK ((flg_canale_default = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_destinatario_canale PRIMARY KEY (id_destinatario_canale),
	CONSTRAINT fk_scriva_d_canale_notifica_01 FOREIGN KEY (id_canale_notifica) REFERENCES scriva_d_canale_notifica(id_canale_notifica),
	CONSTRAINT fk_scriva_d_destinatario_01 FOREIGN KEY (id_destinatario) REFERENCES scriva_d_destinatario(id_destinatario)
);


-- scriva_r_geeco_layer_mappa definition

-- Drop table

-- DROP TABLE scriva_r_geeco_layer_mappa;

CREATE TABLE scriva_r_geeco_layer_mappa (
	id_geeco_layer_mappa int4 NOT NULL,
	id_geeco_layer_virtuale_padre int4 NOT NULL,
	id_geeco_layer_virtuale_figlio int4 NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_geeco_layer_mappa PRIMARY KEY (id_geeco_layer_mappa),
	CONSTRAINT fk_scriva_r_geeco_layer_virtuali_01 FOREIGN KEY (id_geeco_layer_virtuale_padre) REFERENCES scriva_r_geeco_layer_virtuali(id_geeco_layer_virtuale),
	CONSTRAINT fk_scriva_r_geeco_layer_virtuali_02 FOREIGN KEY (id_geeco_layer_virtuale_figlio) REFERENCES scriva_r_geeco_layer_virtuali(id_geeco_layer_virtuale)
);


-- scriva_r_profilo_ogg_app definition

-- Drop table

-- DROP TABLE scriva_r_profilo_ogg_app;

CREATE TABLE scriva_r_profilo_ogg_app (
	id_profilo_app int4 NOT NULL,
	id_oggetto_app int4 NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_profilo_ogg_app PRIMARY KEY (id_profilo_app, id_oggetto_app),
	CONSTRAINT fk_scriva_d_oggetto_app_02 FOREIGN KEY (id_oggetto_app) REFERENCES scriva_d_oggetto_app(id_oggetto_app),
	CONSTRAINT fk_scriva_d_profilo_app_03 FOREIGN KEY (id_profilo_app) REFERENCES scriva_d_profilo_app(id_profilo_app)
);
COMMENT ON TABLE scriva_r_profilo_ogg_app IS 'Il profilo applicativo avrà accesso ai soli oggetti applicativi che sono stati concordati.  Per oggetti applicativi si intende, nel caso di componente esterna, i servizi.';


-- scriva_r_registro_elabora definition

-- Drop table

-- DROP TABLE scriva_r_registro_elabora;

CREATE TABLE scriva_r_registro_elabora (
	id_registro_elabora int4 NOT NULL,
	id_elabora int4 NOT NULL,
	flg_esito_elabora numeric(1) NULL, -- Esito del passo elaborativo. Es. 0 - OK 1 - errore  
	nota_elabora varchar(500) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_r_registro_elabora_01 CHECK ((flg_esito_elabora = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_registro_elabora PRIMARY KEY (id_registro_elabora),
	CONSTRAINT fk_scriva_t_elabora_01 FOREIGN KEY (id_elabora) REFERENCES scriva_t_elabora(id_elabora)
);
COMMENT ON TABLE scriva_r_registro_elabora IS 'E'' il registro delle elaborazioni eseguite. Permette di memorizzare le azioni eseguite dalle varie fasi  previste di ogni elaborazione rispetto alla richiesta prenotata';

-- Column comments

COMMENT ON COLUMN scriva_r_registro_elabora.flg_esito_elabora IS 'Esito del passo elaborativo. Es. 0 - OK 1 - errore  ';


-- scriva_r_stato_istanza_adempi definition

-- Drop table

-- DROP TABLE scriva_r_stato_istanza_adempi;

CREATE TABLE scriva_r_stato_istanza_adempi (
	id_adempimento int4 NOT NULL,
	id_stato_istanza int4 NOT NULL, -- stato di partenza
	id_stato_ammesso int4 NOT NULL, -- possibile stato di arrivo
	ind_modificabile varchar(20) NULL, -- rappresenta la componente applicativa (ad es. ‘FO’ e' Front Office) che può modificare l’adempimento in quello stato di partenza
	id_classe_allegato int4 NULL, -- rappresenta la classe di allegati che sono previsti in un determinato stato,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_stato_istanza_adempi PRIMARY KEY (id_adempimento, id_stato_istanza, id_stato_ammesso),
	CONSTRAINT fk_scriva_d_adempimento_12 FOREIGN KEY (id_adempimento) REFERENCES scriva_d_adempimento(id_adempimento),
	CONSTRAINT fk_scriva_d_classe_allegato_02 FOREIGN KEY (id_classe_allegato) REFERENCES scriva_d_classe_allegato(id_classe_allegato),
	CONSTRAINT fk_scriva_d_stato_istanza_03 FOREIGN KEY (id_stato_istanza) REFERENCES scriva_d_stato_istanza(id_stato_istanza),
	CONSTRAINT fk_scriva_d_stato_istanza_07 FOREIGN KEY (id_stato_ammesso) REFERENCES scriva_d_stato_istanza(id_stato_istanza)
);
COMMENT ON TABLE scriva_r_stato_istanza_adempi IS 'Configurazione degli stati previsti per una istanza di adempimento (tutti gli stati, sia obbligatori che facoltativi) e delle transizioni di stato previste (stato partenza – stato arrivo).';

-- Column comments

COMMENT ON COLUMN scriva_r_stato_istanza_adempi.id_stato_istanza IS 'stato di partenza';
COMMENT ON COLUMN scriva_r_stato_istanza_adempi.id_stato_ammesso IS 'possibile stato di arrivo';
COMMENT ON COLUMN scriva_r_stato_istanza_adempi.ind_modificabile IS 'rappresenta la componente applicativa (ad es. ‘FO’ e'' Front Office) che può modificare l’adempimento in quello stato di partenza';
COMMENT ON COLUMN scriva_r_stato_istanza_adempi.id_classe_allegato IS 'rappresenta la classe di allegati che sono previsti in un determinato stato';


-- scriva_r_template_quadro definition

-- Drop table

-- DROP TABLE scriva_r_template_quadro;

CREATE TABLE scriva_r_template_quadro (
	id_template_quadro int4 NOT NULL,
	id_template int4 NOT NULL,
	id_quadro int4 NOT NULL,
	ordinamento_template_quadro int4 NULL,
	ind_visibile varchar(20) NULL, -- Permette di visualizzare il quadro in base alla componente chiamante, es. il quadro dichiarazioni sarà visibile solo da FO
	json_vestizione_quadro jsonb NULL, -- Contiene sotto forma di chiave e valore l’insieme dei placeholder che andranno sostituiti nella configurazione del quadro, es. etichette che variano in base all’adempimento
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT ak_scriva_r_template_quadro UNIQUE (id_template, id_quadro),
	CONSTRAINT pk_scriva_r_template_quadro PRIMARY KEY (id_template_quadro),
	CONSTRAINT fk_scriva_d_quadro_01 FOREIGN KEY (id_quadro) REFERENCES scriva_d_quadro(id_quadro),
	CONSTRAINT fk_scriva_d_template_01 FOREIGN KEY (id_template) REFERENCES scriva_d_template(id_template)
);
COMMENT ON TABLE scriva_r_template_quadro IS 'Tutti i quadri, trasversali e verticali, necessari per l’adempimento, definendo anche l’ordinamento di presentazione dei quadri per quel template (il quadro orientamento e'' sempre il primo, quello di riepilogo sempre l’ultimo).';

-- Column comments

COMMENT ON COLUMN scriva_r_template_quadro.ind_visibile IS 'Permette di visualizzare il quadro in base alla componente chiamante, es. il quadro dichiarazioni sarà visibile solo da FO';
COMMENT ON COLUMN scriva_r_template_quadro.json_vestizione_quadro IS 'Contiene sotto forma di chiave e valore l’insieme dei placeholder che andranno sostituiti nella configurazione del quadro, es. etichette che variano in base all’adempimento';


-- scriva_r_tipo_adempi_ogg_app definition

-- Drop table

-- DROP TABLE scriva_r_tipo_adempi_ogg_app;

CREATE TABLE scriva_r_tipo_adempi_ogg_app (
	id_tipo_adempi_ogg_app int4 NOT NULL,
	id_oggetto_app int4 NOT NULL,
	id_stato_istanza int4 NOT NULL,
	id_tipo_adempi_profilo int4 NOT NULL,
	flg_clona_istanza numeric(1) NOT NULL,
	id_adempimento_clona_istanza int4 NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_r_tipo_adempi_ogg_app_01 CHECK ((flg_clona_istanza = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_tipo_adempi_ogg_app PRIMARY KEY (id_tipo_adempi_ogg_app),
	CONSTRAINT fk_scriva_d_oggetto_app_01 FOREIGN KEY (id_oggetto_app) REFERENCES scriva_d_oggetto_app(id_oggetto_app),
	CONSTRAINT fk_scriva_d_stato_istanza_05 FOREIGN KEY (id_stato_istanza) REFERENCES scriva_d_stato_istanza(id_stato_istanza),
	CONSTRAINT fk_scriva_r_tipo_adem_prof_01 FOREIGN KEY (id_tipo_adempi_profilo) REFERENCES scriva_r_tipo_adempi_profilo(id_tipo_adempi_profilo)
);
CREATE UNIQUE INDEX ak_scriva_r_tipo_adempi_ogg_app_01 ON scriva_r_tipo_adempi_ogg_app USING btree (id_oggetto_app, id_stato_istanza, id_tipo_adempi_profilo);
COMMENT ON TABLE scriva_r_tipo_adempi_ogg_app IS 'In base al profilo l’attore potrà eseguire o meno alcune funzionalità:  queste abilitazioni vanno configurate sulla tabella SCRIVA_R_TIPO_ADEMPI_OGG_APP e dipendono, oltre che dal profilo, anche dall’adempimento e dallo stato istanza. (vedi analisi per dettagli)';


-- scriva_r_tipo_notifica_evento_adempi definition

-- Drop table

-- DROP TABLE scriva_r_tipo_notifica_evento_adempi;

CREATE TABLE scriva_r_tipo_notifica_evento_adempi (
	id_tipo_notifica_evento_adempi int4 NOT NULL,
	id_tipo_notifica_evento int4 NOT NULL,
	id_adempimento int4 NOT NULL,
	data_inizio date NOT NULL,
	data_fine date NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	des_tipo_notifica_evento_adempi varchar(150) NULL,
	CONSTRAINT ak_scriva_r_tipo_notifica_evento_adempi_01 UNIQUE (id_tipo_notifica_evento, id_adempimento),
	CONSTRAINT pk_scriva_r_tipo_notifica_evento_adempi PRIMARY KEY (id_tipo_notifica_evento_adempi),
	CONSTRAINT fk_scriva_d_adempimento_17 FOREIGN KEY (id_adempimento) REFERENCES scriva_d_adempimento(id_adempimento),
	CONSTRAINT fk_scriva_r_tipo_notifica_evento_01 FOREIGN KEY (id_tipo_notifica_evento) REFERENCES scriva_r_tipo_notifica_evento(id_tipo_notifica_evento)
);


-- scriva_r_vincolo_tipo_allegato definition

-- Drop table

-- DROP TABLE scriva_r_vincolo_tipo_allegato;

CREATE TABLE scriva_r_vincolo_tipo_allegato (
	id_adempimento int4 NOT NULL,
	id_vincolo_autorizza int4 NOT NULL,
	id_tipologia_allegato int4 NOT NULL,
	flg_obbligo numeric(1) NOT NULL,
	flg_firma_digitale numeric(1) NOT NULL,
	flg_riservato numeric(1) NOT NULL,
	flg_nota numeric(1) NOT NULL,
	flg_firma_non_valida_bloccante numeric(1) NOT NULL,
	flg_integrazione numeric(1) DEFAULT 0 NOT NULL,
	ind_visibile varchar(20) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_r_adempi_tipo_allegato_01 CHECK ((flg_obbligo = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_r_adempi_tipo_allegato_02 CHECK ((flg_firma_digitale = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_r_adempi_tipo_allegato_03 CHECK ((flg_riservato = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_r_adempi_tipo_allegato_05 CHECK ((flg_nota = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_r_adempi_tipo_allegato_06 CHECK ((flg_firma_non_valida_bloccante = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_r_adempi_tipo_allegato_07 CHECK ((flg_integrazione = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_vincolo_tipo_allegato PRIMARY KEY (id_adempimento, id_vincolo_autorizza, id_tipologia_allegato),
	CONSTRAINT fk_scriva_r_adempi_tipo_allegato_01 FOREIGN KEY (id_adempimento,id_tipologia_allegato) REFERENCES scriva_r_adempi_tipo_allegato(id_adempimento,id_tipologia_allegato),
	CONSTRAINT fk_scriva_r_adempi_vincolo_01 FOREIGN KEY (id_adempimento,id_vincolo_autorizza) REFERENCES scriva_r_adempi_vincolo_aut(id_adempimento,id_vincolo_autorizza)
);
COMMENT ON TABLE scriva_r_vincolo_tipo_allegato IS 'Per configurare la dipendenza tra tipologia allegato, adempimento e vincolo/autorizzazione su istanza.  In alcuni casi la presenza di un determinato vincolo o autorizzazione sull’istanza fa nascere la necessita di allegare dei documenti specifici.';


-- scriva_s_provincia definition

-- Drop table

-- DROP TABLE scriva_s_provincia;

CREATE TABLE scriva_s_provincia (
	id_s_provincia numeric(7) NOT NULL,
	id_provincia numeric(7) NOT NULL,
	cod_provincia varchar(3) NOT NULL,
	denom_provincia varchar(100) NOT NULL,
	sigla_provincia varchar(2) NULL,
	id_regione numeric(6) NOT NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	CONSTRAINT pk_scriva_s_provincia PRIMARY KEY (id_s_provincia),
	CONSTRAINT fk_scriva_d_provincia_01 FOREIGN KEY (id_provincia) REFERENCES scriva_d_provincia(id_provincia),
	CONSTRAINT fk_scriva_d_regione_03 FOREIGN KEY (id_regione) REFERENCES scriva_d_regione(id_regione)
);
CREATE UNIQUE INDEX ak_scriva_s_provincia_01 ON scriva_s_provincia USING btree (id_provincia, id_regione, data_inizio_validita);
CREATE UNIQUE INDEX ak_scriva_s_provincia_02 ON scriva_s_provincia USING btree (cod_provincia, id_regione, data_inizio_validita);
CREATE UNIQUE INDEX ak_scriva_s_provincia_03 ON scriva_s_provincia USING btree (denom_provincia, id_regione, data_inizio_validita);
CREATE UNIQUE INDEX ak_scriva_s_provincia_04 ON scriva_s_provincia USING btree (id_regione, sigla_provincia, data_inizio_validita);


-- scriva_d_comune definition

-- Drop table

-- DROP TABLE scriva_d_comune;

CREATE TABLE scriva_d_comune (
	id_comune numeric(8) NOT NULL,
	cod_istat_comune varchar(6) NULL,
	cod_belfiore_comune varchar(4) NULL,
	denom_comune varchar(100) NOT NULL,
	cap_comune varchar(5) NULL,
	id_provincia numeric(7) NOT NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	dt_id_comune numeric(20) NOT NULL,
	dt_id_comune_prev numeric(20) NULL,
	dt_id_comune_next numeric(20) NULL,
	CONSTRAINT pk_scriva_d_comune PRIMARY KEY (id_comune),
	CONSTRAINT fk_scriva_d_provincia_02 FOREIGN KEY (id_provincia) REFERENCES scriva_d_provincia(id_provincia)
);
CREATE UNIQUE INDEX ak_scriva_d_comune_01 ON scriva_d_comune USING btree (cod_istat_comune, id_provincia, data_inizio_validita);
CREATE UNIQUE INDEX ak_scriva_d_comune_02 ON scriva_d_comune USING btree (cod_belfiore_comune, id_provincia, data_inizio_validita);
CREATE UNIQUE INDEX ak_scriva_d_comune_03 ON scriva_d_comune USING btree (denom_comune, id_provincia, data_inizio_validita);


-- scriva_s_comune definition

-- Drop table

-- DROP TABLE scriva_s_comune;

CREATE TABLE scriva_s_comune (
	id_s_comune numeric(8) NOT NULL,
	id_comune numeric(8) NOT NULL,
	cod_istat_comune varchar(6) NULL,
	cod_belfiore_comune varchar(4) NULL,
	denom_comune varchar(100) NOT NULL,
	cap_comune varchar(5) NULL,
	id_provincia numeric(7) NOT NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	dt_id_comune numeric(20) NOT NULL,
	dt_id_comune_prev numeric(20) NULL,
	dt_id_comune_next numeric(20) NULL,
	CONSTRAINT pk_scriva_s_comune PRIMARY KEY (id_s_comune),
	CONSTRAINT fk_scriva_d_comune_01 FOREIGN KEY (id_comune) REFERENCES scriva_d_comune(id_comune),
	CONSTRAINT fk_scriva_d_provincia_03 FOREIGN KEY (id_provincia) REFERENCES scriva_d_provincia(id_provincia)
);
CREATE UNIQUE INDEX ak_scriva_s_comune_01 ON scriva_s_comune USING btree (id_comune, cod_istat_comune, cod_belfiore_comune, denom_comune, id_provincia, data_inizio_validita);


-- scriva_t_competenza_territorio definition

-- Drop table

-- DROP TABLE scriva_t_competenza_territorio;

CREATE TABLE scriva_t_competenza_territorio (
	id_competenza_territorio int4 NOT NULL,
	id_tipo_competenza int4 NOT NULL,
	id_ente_creditore int4 NOT NULL,
	id_comune_competenza numeric(8) NULL,
	cod_competenza_territorio varchar(20) NOT NULL,
	des_competenza_territorio varchar(150) NULL,
	cod_suap varchar(20) NULL,
	indirizzo_competenza varchar(100) NULL,
	num_civico_indirizzo varchar(30) NULL,
	cap_competenza varchar(5) NULL,
	des_pec varchar(100) NULL,
	des_email varchar(100) NULL,
	orario varchar(20) NULL,
	sito_web varchar(150) NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	ind_visibile varchar(20) DEFAULT 'FO'::character varying NOT NULL,
	des_competenza_territorio_estesa varchar(250) NULL,
	cod_ipa varchar(20) NULL, -- Indica se l'evento va comunicato ad un gestore processo
	num_cellulare varchar(50) NULL,
	des_servizio_applicativo varchar(150) NULL,
	num_dimensione_pec varchar(50) NULL,
	CONSTRAINT pk_scriva_t_competenza_territorio PRIMARY KEY (id_competenza_territorio),
	CONSTRAINT fk_scriva_d_comune_07 FOREIGN KEY (id_comune_competenza) REFERENCES scriva_d_comune(id_comune),
	CONSTRAINT fk_scriva_d_ente_creditore_01 FOREIGN KEY (id_ente_creditore) REFERENCES scriva_d_ente_creditore(id_ente_creditore),
	CONSTRAINT fk_scriva_d_tipo_competenza_01 FOREIGN KEY (id_tipo_competenza) REFERENCES scriva_d_tipo_competenza(id_tipo_competenza)
);
CREATE UNIQUE INDEX ak_scriva_t_competenza_territorio_01 ON scriva_t_competenza_territorio USING btree (cod_competenza_territorio);
COMMENT ON TABLE scriva_t_competenza_territorio IS 'Anagrafica degli enti competenti necessari per l’adempimento';

-- Column comments

COMMENT ON COLUMN scriva_t_competenza_territorio.cod_ipa IS 'Indica se l''evento va comunicato ad un gestore processo';


-- scriva_t_soggetto definition

-- Drop table

-- DROP TABLE scriva_t_soggetto;

CREATE TABLE scriva_t_soggetto (
	id_soggetto int4 NOT NULL,
	cf_soggetto varchar(16) NOT NULL,
	id_tipo_soggetto int4 NOT NULL,
	id_tipo_natura_giuridica int4 NULL,
	id_masterdata int4 NOT NULL,
	id_masterdata_origine int4 NOT NULL,
	id_comune_nascita numeric(8) NULL,
	id_comune_residenza numeric(8) NULL,
	id_comune_sede_legale numeric(8) NULL,
	partita_iva_soggetto varchar(16) NULL,
	den_soggetto varchar(250) NULL,
	data_cessazione_soggetto date NULL,
	nome varchar(100) NULL,
	cognome varchar(100) NULL,
	data_nascita_soggetto date NULL,
	citta_estera_nascita varchar(100) NULL,
	num_telefono varchar(25) NULL,
	des_email varchar(100) NULL,
	des_pec varchar(100) NULL,
	indirizzo_soggetto varchar(100) NULL,
	num_civico_indirizzo varchar(30) NULL,
	citta_estera_residenza varchar(100) NULL,
	den_provincia_cciaa varchar(20) NULL,
	den_anno_cciaa numeric(4) NULL,
	den_numero_cciaa varchar(20) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	num_cellulare varchar(25) NULL,
	id_nazione_residenza numeric(3) NULL,
	id_nazione_nascita numeric(3) NULL,
	des_localita varchar(250) NULL,
	citta_estera_sede_legale varchar(100) NULL,
	id_nazione_sede_legale numeric(3) NULL,
	cap_residenza varchar(10) NULL,
	cap_sede_legale varchar(10) NULL,
	data_aggiornamento timestamp NULL,
	id_funzionario int4 NULL,
	CONSTRAINT ak_scriva_t_soggetto_01 UNIQUE (cf_soggetto, id_tipo_soggetto),
	CONSTRAINT pk_scriva_t_soggetto PRIMARY KEY (id_soggetto),
	CONSTRAINT fk_scriva_d_comune_02 FOREIGN KEY (id_comune_residenza) REFERENCES scriva_d_comune(id_comune),
	CONSTRAINT fk_scriva_d_comune_03 FOREIGN KEY (id_comune_nascita) REFERENCES scriva_d_comune(id_comune),
	CONSTRAINT fk_scriva_d_comune_04 FOREIGN KEY (id_comune_sede_legale) REFERENCES scriva_d_comune(id_comune),
	CONSTRAINT fk_scriva_d_nazione_05 FOREIGN KEY (id_nazione_residenza) REFERENCES scriva_d_nazione(id_nazione),
	CONSTRAINT fk_scriva_d_nazione_06 FOREIGN KEY (id_nazione_nascita) REFERENCES scriva_d_nazione(id_nazione),
	CONSTRAINT fk_scriva_d_nazione_07 FOREIGN KEY (id_nazione_sede_legale) REFERENCES scriva_d_nazione(id_nazione),
	CONSTRAINT fk_scriva_d_tipo_natura_giu_01 FOREIGN KEY (id_tipo_natura_giuridica) REFERENCES scriva_d_tipo_natura_giuridica(id_tipo_natura_giuridica),
	CONSTRAINT fk_scriva_d_tipo_soggetto_01 FOREIGN KEY (id_tipo_soggetto) REFERENCES scriva_d_tipo_soggetto(id_tipo_soggetto),
	CONSTRAINT fk_scriva_t_funzionario_11 FOREIGN KEY (id_funzionario) REFERENCES scriva_t_funzionario(id_funzionario)
);


-- scriva_d_adempi_tipo_pagamento definition

-- Drop table

-- DROP TABLE scriva_d_adempi_tipo_pagamento;

CREATE TABLE scriva_d_adempi_tipo_pagamento (
	id_adempi_tipo_pagamento int4 NOT NULL,
	id_tipo_pagamento int4 NOT NULL,
	id_adempimento int4 NOT NULL,
	codice_versamento varchar(20) NOT NULL,
	importo_previsto numeric(13, 2) NULL,
	giorni_max_attesa_rt int4 NOT NULL,
	ind_importo_pagamento varchar(20) NOT NULL,
	flg_attivo numeric(1) NOT NULL,
	id_competenza_territorio int4 NOT NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_d_adempi_tipo_pagamento_01 CHECK ((flg_attivo = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_d_adempi_tipo_pagamento PRIMARY KEY (id_adempi_tipo_pagamento),
	CONSTRAINT fk_scriva_d_adempimento_14 FOREIGN KEY (id_adempimento) REFERENCES scriva_d_adempimento(id_adempimento),
	CONSTRAINT fk_scriva_d_tipo_pagamento_01 FOREIGN KEY (id_tipo_pagamento) REFERENCES scriva_d_tipo_pagamento(id_tipo_pagamento),
	CONSTRAINT fk_scriva_t_competenza_07 FOREIGN KEY (id_competenza_territorio) REFERENCES scriva_t_competenza_territorio(id_competenza_territorio)
);
CREATE UNIQUE INDEX ak_scriva_d_adempi_tipo_pagamento_01 ON scriva_d_adempi_tipo_pagamento USING btree (id_tipo_pagamento, id_adempimento, id_competenza_territorio, codice_versamento);


-- scriva_r_adempi_competenza definition

-- Drop table

-- DROP TABLE scriva_r_adempi_competenza;

CREATE TABLE scriva_r_adempi_competenza (
	id_adempimento int4 NOT NULL,
	id_competenza_territorio int4 NOT NULL,
	flg_principale numeric(1) DEFAULT 0 NOT NULL, -- definisce l’AC che deve essere salvata come principale per una istanza di procedimento
	url_oneri_previsti varchar(150) NULL, -- specifica l’url a cui l’ente ha pubblicato le regole di calcolo degli oneri
	ind_adesione_adempimento int4 NULL, -- Indica come interviene l'AC nell'istanza.¶¶Puo valere :¶- 0 se AC non aderisce a SCRIVA: AC non può essere selezionata da orientamento e non può essere assegnata all'istanza¶- 1 se AC aderisce: può essere selezionato in orientamento¶- 2 se AC non può essere selezionato in orientamento ma deve essere assegnata in automatico da sistema come competenza associata all'istanza¶- 3 se AC non può essere selezionato in orientamento ma deve essere assegnata in automatico da sistema se nel qdr_config è presente IndTipoCalcoloAC=AC3¶- 4 se AC non può essere selezionato in orientamento ma può essere assegnata in automatico da sistema in base ai comuni di ubicazione degli oggetti-istanza
	id_componente_gestore_processo int4 NULL, -- Indica il gestore del processo di istruttoria di quella AC per quell'adempimento
	num_cellulare varchar(50) NULL,
	des_email varchar(150) NULL,
	des_pec varchar(150) NULL,
	des_servizio_applicativo varchar(150) NULL,
	num_dimensione_pec varchar(50) NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	ordinamento int4 NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_r_adempi_competenza_02 CHECK ((flg_principale = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_adempi_competenza PRIMARY KEY (id_adempimento, id_competenza_territorio),
	CONSTRAINT fk_scriva_d_adempimento_11 FOREIGN KEY (id_adempimento) REFERENCES scriva_d_adempimento(id_adempimento),
	CONSTRAINT fk_scriva_d_componente_app_03 FOREIGN KEY (id_componente_gestore_processo) REFERENCES scriva_d_componente_app(id_componente_app),
	CONSTRAINT fk_scriva_t_competenza_01 FOREIGN KEY (id_competenza_territorio) REFERENCES scriva_t_competenza_territorio(id_competenza_territorio)
);
COMMENT ON TABLE scriva_r_adempi_competenza IS 'Tabella associativa tra scriva_d_adempimento (anagrafica adempimenti) e scriva_t_competenza_territorio (tabella enti competenti per territorio) vengono definite le Autorità Competenti (AC) per un procedimento.  Se ad un procedimento sono associate più AC, il campo “flg_principale” definisce l’AC che deve essere salvata come principale per una istanza di procedimento.';

-- Column comments

COMMENT ON COLUMN scriva_r_adempi_competenza.flg_principale IS 'definisce l’AC che deve essere salvata come principale per una istanza di procedimento';
COMMENT ON COLUMN scriva_r_adempi_competenza.url_oneri_previsti IS 'specifica l’url a cui l’ente ha pubblicato le regole di calcolo degli oneri';
COMMENT ON COLUMN scriva_r_adempi_competenza.ind_adesione_adempimento IS 'Indica come interviene l''AC nell''istanza.

Puo valere :
- 0 se AC non aderisce a SCRIVA: AC non può essere selezionata da orientamento e non può essere assegnata all''istanza
- 1 se AC aderisce: può essere selezionato in orientamento
- 2 se AC non può essere selezionato in orientamento ma deve essere assegnata in automatico da sistema come competenza associata all''istanza
- 3 se AC non può essere selezionato in orientamento ma deve essere assegnata in automatico da sistema se nel qdr_config è presente IndTipoCalcoloAC=AC3
- 4 se AC non può essere selezionato in orientamento ma può essere assegnata in automatico da sistema in base ai comuni di ubicazione degli oggetti-istanza';
COMMENT ON COLUMN scriva_r_adempi_competenza.id_componente_gestore_processo IS 'Indica il gestore del processo di istruttoria di quella AC per quell''adempimento';


-- scriva_r_competenza_comune definition

-- Drop table

-- DROP TABLE scriva_r_competenza_comune;

CREATE TABLE scriva_r_competenza_comune (
	id_competenza_territorio int4 NOT NULL,
	id_comune numeric(8) NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_competenza_comune PRIMARY KEY (id_competenza_territorio, id_comune),
	CONSTRAINT fk_scriva_d_comune_08 FOREIGN KEY (id_comune) REFERENCES scriva_d_comune(id_comune),
	CONSTRAINT fk_scriva_t_competenza_03 FOREIGN KEY (id_competenza_territorio) REFERENCES scriva_t_competenza_territorio(id_competenza_territorio)
);


-- scriva_r_competenza_provincia definition

-- Drop table

-- DROP TABLE scriva_r_competenza_provincia;

CREATE TABLE scriva_r_competenza_provincia (
	id_competenza_territorio int4 NOT NULL,
	id_provincia numeric(7) NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_competenza_provincia PRIMARY KEY (id_competenza_territorio, id_provincia),
	CONSTRAINT fk_scriva_d_provincia_04 FOREIGN KEY (id_provincia) REFERENCES scriva_d_provincia(id_provincia),
	CONSTRAINT fk_scriva_t_competenza_04 FOREIGN KEY (id_competenza_territorio) REFERENCES scriva_t_competenza_territorio(id_competenza_territorio)
);


-- scriva_r_competenza_regione definition

-- Drop table

-- DROP TABLE scriva_r_competenza_regione;

CREATE TABLE scriva_r_competenza_regione (
	id_competenza_territorio int4 NOT NULL,
	id_regione numeric(6) NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_competenza_regione PRIMARY KEY (id_competenza_territorio, id_regione),
	CONSTRAINT fk_scriva_d_regione_04 FOREIGN KEY (id_regione) REFERENCES scriva_d_regione(id_regione),
	CONSTRAINT fk_scriva_t_competenza_05 FOREIGN KEY (id_competenza_territorio) REFERENCES scriva_t_competenza_territorio(id_competenza_territorio)
);


-- scriva_r_competenza_responsabile definition

-- Drop table

-- DROP TABLE scriva_r_competenza_responsabile;

CREATE TABLE scriva_r_competenza_responsabile (
	id_competenza_responsabile int4 NOT NULL,
	id_competenza_territorio int4 NOT NULL,
	id_tipo_responsabile int4 NOT NULL,
	label_responsabile varchar(100) NOT NULL,
	nominativo_responsabile varchar(150) NOT NULL,
	recapito_responsabile varchar(150) NULL,
	flg_riservato numeric(1) DEFAULT 0 NOT NULL, -- definisce se il responsabile è riservato
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_r_competenza_responsabile_01 CHECK ((flg_riservato = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_competenza_responsabile PRIMARY KEY (id_competenza_responsabile),
	CONSTRAINT fk_scriva_d_tipo_responsabile_01 FOREIGN KEY (id_tipo_responsabile) REFERENCES scriva_d_tipo_responsabile(id_tipo_responsabile),
	CONSTRAINT fk_scriva_t_competenza_11 FOREIGN KEY (id_competenza_territorio) REFERENCES scriva_t_competenza_territorio(id_competenza_territorio)
);
COMMENT ON TABLE scriva_r_competenza_responsabile IS 'Tabella dei responsabili definiti a livello di autorità competente (principalmente per responsabile procedimento e responsabile trattamento dati), in quanto i responsabili di un’istanza vanno precompilati con questi dati';

-- Column comments

COMMENT ON COLUMN scriva_r_competenza_responsabile.flg_riservato IS 'definisce se il responsabile è riservato';


-- scriva_r_funzionario_compete definition

-- Drop table

-- DROP TABLE scriva_r_funzionario_compete;

CREATE TABLE scriva_r_funzionario_compete (
	id_funzionario int4 NOT NULL,
	id_competenza_territorio int4 NOT NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_funzionario_compete PRIMARY KEY (id_funzionario, id_competenza_territorio),
	CONSTRAINT fk_scriva_t_competenza_06 FOREIGN KEY (id_competenza_territorio) REFERENCES scriva_t_competenza_territorio(id_competenza_territorio),
	CONSTRAINT fk_scriva_t_funzionario_01 FOREIGN KEY (id_funzionario) REFERENCES scriva_t_funzionario(id_funzionario)
);
COMMENT ON TABLE scriva_r_funzionario_compete IS 'Oltre al profilo, per gli utenti di BO e'' importante individuare la competenza su territorio definita in tabella SCRIVA_R_FUNZIONARIO_COMPETE, perché il funzionario può vedere e gestire solo le pratiche degli adempimenti associati al profilo e riferite ad autorità competenti principali corrispondenti alla competenza territoriale del funzionario.   Esempio: se un utente di BO della provincia di ASTI ha associato il ruolo applicativo AUA_RW, potrà vedere e gestire tutte le pratiche legate ad adempimenti di AUA e con area di competenza principale Provincia di ASTI.';


-- scriva_r_notifica_configurazione definition

-- Drop table

-- DROP TABLE scriva_r_notifica_configurazione;

CREATE TABLE scriva_r_notifica_configurazione (
	id_notifica_configurazione int4 NOT NULL,
	id_canale_notifica int4 NOT NULL,
	id_tipo_notifica_evento int4 NULL,
	id_tipo_notifica_evento_adempi int4 NULL,
	id_destinatario int4 NULL,
	id_competenza_territorio int4 NULL,
	id_tipo_competenza int4 NULL, -- FK : livello competenza territorio
	oggetto_notifica varchar(350) NULL,
	contenuto_notifica varchar(4000) NULL,
	des_email varchar(100) NULL,
	num_cellulare varchar(25) NULL,
	des_servizio_applicativo varchar(250) NULL,
	des_pec varchar(100) NULL,
	des_notifica_cc varchar(250) NULL,
	flg_verifica_preferenze_sogg numeric(1) DEFAULT 0 NOT NULL,
	ind_escludi_applicativo varchar(50) NULL,
	data_inizio date NOT NULL,
	data_fine date NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	des_notifica_configurazione varchar(150) NULL,
	CONSTRAINT ak_scriva_r_notifica_configurazione_01 UNIQUE (id_canale_notifica, id_tipo_notifica_evento, id_tipo_notifica_evento_adempi, id_destinatario, id_competenza_territorio, id_tipo_competenza),
	CONSTRAINT chk_scriva_r_notifica_configurazione_01 CHECK ((flg_verifica_preferenze_sogg = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_notifica_configurazione PRIMARY KEY (id_notifica_configurazione),
	CONSTRAINT fk_scriva_d_canale_notifica_03 FOREIGN KEY (id_canale_notifica) REFERENCES scriva_d_canale_notifica(id_canale_notifica),
	CONSTRAINT fk_scriva_d_destinatario_02 FOREIGN KEY (id_destinatario) REFERENCES scriva_d_destinatario(id_destinatario),
	CONSTRAINT fk_scriva_d_tipo_competenza_04 FOREIGN KEY (id_tipo_competenza) REFERENCES scriva_d_tipo_competenza(id_tipo_competenza),
	CONSTRAINT fk_scriva_r_tipo_notifica_evento_02 FOREIGN KEY (id_tipo_notifica_evento) REFERENCES scriva_r_tipo_notifica_evento(id_tipo_notifica_evento),
	CONSTRAINT fk_scriva_r_tipo_notifica_evento_adempi_01 FOREIGN KEY (id_tipo_notifica_evento_adempi) REFERENCES scriva_r_tipo_notifica_evento_adempi(id_tipo_notifica_evento_adempi),
	CONSTRAINT fk_scriva_t_competenza_10 FOREIGN KEY (id_competenza_territorio) REFERENCES scriva_t_competenza_territorio(id_competenza_territorio)
);
COMMENT ON TABLE scriva_r_notifica_configurazione IS 'Permette di gestire tutte le informazioni necessarie alla definizione di una notifica.
Queste saranno utilizzate dai componenti applicativi preposte per inviarle ai relativi destinatari.';

-- Column comments

COMMENT ON COLUMN scriva_r_notifica_configurazione.id_tipo_competenza IS 'FK : livello competenza territorio';


-- scriva_r_riscossione_ente definition

-- Drop table

-- DROP TABLE scriva_r_riscossione_ente;

CREATE TABLE scriva_r_riscossione_ente (
	id_riscossione_ente int4 NOT NULL,
	id_adempi_tipo_pagamento int4 NOT NULL,
	dati_specifici_riscossione varchar(30) NOT NULL,
	accertamento_anno numeric(4) NOT NULL,
	numero_accertamento int4 NOT NULL,
	id_gruppo_pagamento int4 NULL,
	des_pagamento_verso_cittadino varchar(250) NULL,
	flg_attivo numeric(1) NOT NULL,
	id_stato_istanza_blocco int4 NULL,
	ordinamento_riscossione_ente int4 NULL,
	flg_attiva_importo_non_dovuto numeric(1) DEFAULT 0 NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_r_riscossione_ente_01 CHECK ((flg_attivo = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_r_riscossione_ente_02 CHECK ((flg_attiva_importo_non_dovuto = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_riscossione_ente PRIMARY KEY (id_riscossione_ente),
	CONSTRAINT fk_scriva_d_adempi_tipo_pag_01 FOREIGN KEY (id_adempi_tipo_pagamento) REFERENCES scriva_d_adempi_tipo_pagamento(id_adempi_tipo_pagamento),
	CONSTRAINT fk_scriva_d_gruppo_pagamento_02 FOREIGN KEY (id_gruppo_pagamento) REFERENCES scriva_d_gruppo_pagamento(id_gruppo_pagamento)
);
CREATE UNIQUE INDEX ak_sscriva_r_riscossione_ente_01 ON scriva_r_riscossione_ente USING btree (id_adempi_tipo_pagamento, dati_specifici_riscossione, accertamento_anno, numero_accertamento);


-- scriva_r_notifica_config_allegato definition

-- Drop table

-- DROP TABLE scriva_r_notifica_config_allegato;

CREATE TABLE scriva_r_notifica_config_allegato (
	id_notifica_config_allegato int4 NOT NULL, -- Identificativo univoco
	id_notifica_configurazione int4 NOT NULL,
	data_inizio date NOT NULL,
	data_fine date NULL,
	notifica_cod_tipologia_allegato varchar(20) NULL,
	notifica_cod_categoria_allegato varchar(20) NULL,
	notifica_cod_classe_allegato varchar(20) NULL,
	ind_allega_figli varchar(20) NULL, -- Specifica se è necessario allegare anche i documenti figli associati all'istanza.¶Es.¶N - non allegare documenti figli¶O - opzionale allegare documenti figli se presenti¶M - mandatory deve essere presente almeno un documento figlio altrimenti la notifica stato FALLITA e non si invia
	ind_allega_padre varchar(20) NULL, -- Specifica se è obbligatorio che sia presente l'allegato padre.¶Es.¶O - opzionale allegare il/i documento/i principale/i se presente¶M - mandatory deve essere presente obbligatoriamente il/i documento/i principale/i altrimenti la notifica stato FALLITA e non si invia,
	flg_pubblicati numeric(1) DEFAULT 0 NOT NULL, -- Indica che vanno inviati solo i file pubblicati
	flg_allegato_rif_protocollo numeric(1) DEFAULT 0 NOT NULL, -- Indica se vanno elaborati solo alcuni documenti etichettati.¶se = 0 default¶se = 1 estrarre solo i documenti etichettati con scriva_r_integra_istanza_allegato.flg_allegato_rif_protocollo=1 al¶momento informazione presente solo per le integrazioni/perfezionamenti
	flg_ultimo_doc numeric(1) DEFAULT 0 NOT NULL, -- Indica al sistema la corretta gestione del documento principale.¶se = 0 il sistema per il/i documento/i principale/i può selezionare tutti documenti trovati senza condizione sulla data di ultimo caricamento¶se = 1 il sistema per il/i documento/i principale/i deve selezionare ultimo caricamento
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_r_notifica_config_allegato_01 CHECK ((flg_pubblicati = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_r_notifica_config_allegato_02 CHECK ((flg_allegato_rif_protocollo = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_r_notifica_config_allegato_03 CHECK ((flg_ultimo_doc = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_notifica_config_allegato PRIMARY KEY (id_notifica_config_allegato),
	CONSTRAINT fk_scriva_r_notifica_configurazione_03 FOREIGN KEY (id_notifica_configurazione) REFERENCES scriva_r_notifica_configurazione(id_notifica_configurazione)
);
COMMENT ON TABLE scriva_r_notifica_config_allegato IS 'Permette di identificare le tipologie di allegati da notificare, tramite i codici specificati.';

-- Column comments

COMMENT ON COLUMN scriva_r_notifica_config_allegato.id_notifica_config_allegato IS 'Identificativo univoco';
COMMENT ON COLUMN scriva_r_notifica_config_allegato.ind_allega_figli IS 'Specifica se è necessario allegare anche i documenti figli associati all''istanza.
Es.
N - non allegare documenti figli
O - opzionale allegare documenti figli se presenti
M - mandatory deve essere presente almeno un documento figlio altrimenti la notifica stato FALLITA e non si invia';
COMMENT ON COLUMN scriva_r_notifica_config_allegato.ind_allega_padre IS 'Specifica se è obbligatorio che sia presente l''allegato padre.
Es.
O - opzionale allegare il/i documento/i principale/i se presente
M - mandatory deve essere presente obbligatoriamente il/i documento/i principale/i altrimenti la notifica stato FALLITA e non si invia';
COMMENT ON COLUMN scriva_r_notifica_config_allegato.flg_pubblicati IS 'Indica che vanno inviati solo i file pubblicati';
COMMENT ON COLUMN scriva_r_notifica_config_allegato.flg_allegato_rif_protocollo IS 'Indica se vanno elaborati solo alcuni documenti etichettati.
se = 0 default
se = 1 estrarre solo i documenti etichettati con scriva_r_integra_istanza_allegato.flg_allegato_rif_protocollo=1 al
momento informazione presente solo per le integrazioni/perfezionamenti';
COMMENT ON COLUMN scriva_r_notifica_config_allegato.flg_ultimo_doc IS 'Indica al sistema la corretta gestione del documento principale.
se = 0 il sistema per il/i documento/i principale/i può selezionare tutti documenti trovati senza condizione sulla data di ultimo caricamento
se = 1 il sistema per il/i documento/i principale/i deve selezionare ultimo caricamento';


-- scriva_r_risco_stato_istanza definition

-- Drop table

-- DROP TABLE scriva_r_risco_stato_istanza;

CREATE TABLE scriva_r_risco_stato_istanza (
	id_riscossione_ente int4 NOT NULL,
	id_stato_istanza int4 NOT NULL,
	flg_attiva_pagamento numeric(1) DEFAULT 0 NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_r_risco_stato_istanza_01 CHECK ((flg_attiva_pagamento = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_risco_stato_istanza PRIMARY KEY (id_riscossione_ente, id_stato_istanza),
	CONSTRAINT fk_scriva_d_stato_istanza_04 FOREIGN KEY (id_stato_istanza) REFERENCES scriva_d_stato_istanza(id_stato_istanza),
	CONSTRAINT fk_scriva_r_riscossione_ente_01 FOREIGN KEY (id_riscossione_ente) REFERENCES scriva_r_riscossione_ente(id_riscossione_ente)
);


-- scriva_geo_area_ogg_istanza definition

-- Drop table

-- DROP TABLE scriva_geo_area_ogg_istanza;

CREATE TABLE scriva_geo_area_ogg_istanza (
	id_geo_area_ogg_istanza int4 NOT NULL,
	id_oggetto_istanza int4 NOT NULL,
	id_geometrico int4 NOT NULL,
	id_virtuale int4 NOT NULL,
	des_geeco varchar(250) NULL,
	json_geo_feature jsonb NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	geometria public.geometry(geometry, 32632) NULL,
	CONSTRAINT pk_scriva_geo_area_ogg_istanza PRIMARY KEY (id_geo_area_ogg_istanza)
);
CREATE UNIQUE INDEX ak_scriva_geo_area_ogg_istanza_01 ON scriva_geo_area_ogg_istanza USING btree (id_oggetto_istanza, id_geometrico);
CREATE INDEX scriva_geo_area_ogg_istanza_gidx ON scriva_geo_area_ogg_istanza USING gist (geometria);


-- scriva_geo_linea_ogg_istanza definition

-- Drop table

-- DROP TABLE scriva_geo_linea_ogg_istanza;

CREATE TABLE scriva_geo_linea_ogg_istanza (
	id_geo_linea_ogg_istanza int4 NOT NULL,
	id_oggetto_istanza int4 NOT NULL,
	id_geometrico int4 NOT NULL,
	id_virtuale int4 NOT NULL,
	des_geeco varchar(250) NULL,
	json_geo_feature jsonb NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	geometria public.geometry(geometry, 32632) NULL,
	CONSTRAINT pk_scriva_geo_linea_ogg_istanza PRIMARY KEY (id_geo_linea_ogg_istanza)
);
CREATE UNIQUE INDEX ak_scriva_geo_linea_ogg_istanza_01 ON scriva_geo_linea_ogg_istanza USING btree (id_oggetto_istanza, id_geometrico);
CREATE INDEX scriva_geo_linea_ogg_istanza_gidx ON scriva_geo_linea_ogg_istanza USING gist (geometria);


-- scriva_geo_punto_ogg_istanza definition

-- Drop table

-- DROP TABLE scriva_geo_punto_ogg_istanza;

CREATE TABLE scriva_geo_punto_ogg_istanza (
	id_geo_punto_ogg_istanza int4 NOT NULL,
	id_oggetto_istanza int4 NOT NULL,
	id_geometrico int4 NOT NULL,
	id_virtuale int4 NOT NULL,
	des_geeco varchar(250) NULL,
	json_geo_feature jsonb NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	geometria public.geometry(geometry, 32632) NULL,
	CONSTRAINT pk_scriva_geo_punto_ogg_istanza PRIMARY KEY (id_geo_punto_ogg_istanza)
);
CREATE UNIQUE INDEX ak_scriva_geo_punto_ogg_istanza_01 ON scriva_geo_punto_ogg_istanza USING btree (id_oggetto_istanza, id_geometrico);
CREATE INDEX scriva_geo_punto_ogg_istanza_gidx ON scriva_geo_punto_ogg_istanza USING gist (geometria);


-- scriva_r_catasto_ubi_ogg_ist definition

-- Drop table

-- DROP TABLE scriva_r_catasto_ubi_ogg_ist;

CREATE TABLE scriva_r_catasto_ubi_ogg_ist (
	id_catasto_ubica_oggetto_ist int4 NOT NULL,
	id_ubica_oggetto_istanza int4 NOT NULL,
	sezione varchar(2) NULL,
	foglio numeric(5) NULL,
	particella varchar(10) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	ind_fonte_dato varchar(4) NULL, -- Se l'utente indica dei dati diversi da quelli proposti dal servizio dati catastali ¶mostriamo un avviso e se procede, salviamo anche ind_fonte_dato.¶ind_fonte_dato = S (servizio) oppure M (manuale)¶In modo che da BO possiamo mostrare per ogni occorrenza dei dati catastali se dato proveniente da servizio o manualmente
	cod_belfiore varchar(4) NULL, -- E' il codice Belfiore presente al momento della presentazione dell'Istanza,
	CONSTRAINT pk_scriva_r_catasto_ubi_ogg_ist PRIMARY KEY (id_catasto_ubica_oggetto_ist)
);

-- Column comments

COMMENT ON COLUMN scriva_r_catasto_ubi_ogg_ist.ind_fonte_dato IS 'Se l''utente indica dei dati diversi da quelli proposti dal servizio dati catastali 
mostriamo un avviso e se procede, salviamo anche ind_fonte_dato.
ind_fonte_dato = S (servizio) oppure M (manuale)
In modo che da BO possiamo mostrare per ogni occorrenza dei dati catastali se dato proveniente da servizio o manualmente';
COMMENT ON COLUMN scriva_r_catasto_ubi_ogg_ist.cod_belfiore IS 'E'' il codice Belfiore presente al momento della presentazione dell''Istanza';


-- scriva_r_integra_istanza_allegato definition

-- Drop table

-- DROP TABLE scriva_r_integra_istanza_allegato;

CREATE TABLE scriva_r_integra_istanza_allegato (
	id_integra_istanza_allegato int4 NOT NULL,
	id_integrazione_istanza int4 NOT NULL, -- letto dalla relativa : scriva_r_integra_istanza_allegato
	id_allegato_istanza int4 NOT NULL, -- letto dalla relativa : scriva_r_integra_istanza_allegato
	flg_allegato_rif_protocollo numeric(1) DEFAULT 0 NOT NULL, -- Identifica se impostato = 1, l'allegato di riferimento per il protocollo.
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_r_integra_istanza_allegato_01 CHECK ((flg_allegato_rif_protocollo = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_integra_istanza_allegato PRIMARY KEY (id_integra_istanza_allegato)
);
CREATE UNIQUE INDEX ak_scriva_r_integra_istanza_allegato_01 ON scriva_r_integra_istanza_allegato USING btree (id_integrazione_istanza, id_allegato_istanza);
COMMENT ON TABLE scriva_r_integra_istanza_allegato IS 'Contiene l''insieme dei documenti referenti l''integrazione. 
Alcuni di questi possono essere utilizzati (flag = 1) per la funzionalita'' di protocolla integrazione abilitata al BO.';

-- Column comments

COMMENT ON COLUMN scriva_r_integra_istanza_allegato.id_integrazione_istanza IS 'letto dalla relativa : scriva_r_integra_istanza_allegato';
COMMENT ON COLUMN scriva_r_integra_istanza_allegato.id_allegato_istanza IS 'letto dalla relativa : scriva_r_integra_istanza_allegato';
COMMENT ON COLUMN scriva_r_integra_istanza_allegato.flg_allegato_rif_protocollo IS 'Identifica se impostato = 1, l''allegato di riferimento per il protocollo.';


-- scriva_r_istanza_attore definition

-- Drop table

-- DROP TABLE scriva_r_istanza_attore;

CREATE TABLE scriva_r_istanza_attore (
	id_istanza_attore int4 NOT NULL,
	id_istanza int4 NOT NULL,
	id_tipo_abilitazione int4 NULL,
	id_compilante int4 NULL,
	id_profilo_app int4 NOT NULL,
	cf_attore varchar(16) NOT NULL,
	cf_abilitante_delegante varchar(16) NULL,
	data_inizio timestamp NOT NULL,
	data_revoca timestamp NULL,
	data_delega_con_firma timestamp NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_istanza_attore PRIMARY KEY (id_istanza_attore)
);
CREATE INDEX ie_scriva_r_istanza_attore_01 ON scriva_r_istanza_attore USING btree (id_istanza, id_tipo_abilitazione, id_compilante, id_profilo_app);


-- scriva_r_istanza_competenza definition

-- Drop table

-- DROP TABLE scriva_r_istanza_competenza;

CREATE TABLE scriva_r_istanza_competenza (
	id_istanza int4 NOT NULL,
	id_competenza_territorio int4 NOT NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	flg_autorita_principale numeric(1) NULL,
	flg_autorita_assegnata_bo numeric(1) NULL,
	ind_assegnata_da_sistema varchar(20) DEFAULT '0'::character varying NOT NULL, -- Indica la modalita' di assegnazione della competenza¶Es.¶0 – AC selezionata da utente in orientamento¶2 – AC attribuita da sistema con scriva_r_adempi_competenza.ind_adesione_adempimento = 2¶3 - AC attribuita da sistema con scriva_r_adempi_competenza.ind_adesione_adempimento = 3¶4 - AC calcolata da sistema per COMUNE¶5 - AC calcolata da sistema per SITO RETE NATURA 2000¶6 – AC calcolata da sistema per CATEGORIE PROGETTUALI
	CONSTRAINT chk_scriva_r_istanza_competenza_01 CHECK ((flg_autorita_principale = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_r_istanza_competenza_02 CHECK ((flg_autorita_assegnata_bo = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_istanza_competenza PRIMARY KEY (id_istanza, id_competenza_territorio)
);

-- Column comments

COMMENT ON COLUMN scriva_r_istanza_competenza.ind_assegnata_da_sistema IS 'Indica la modalita'' di assegnazione della competenza
Es.
0 – AC selezionata da utente in orientamento
2 – AC attribuita da sistema con scriva_r_adempi_competenza.ind_adesione_adempimento = 2
3 - AC attribuita da sistema con scriva_r_adempi_competenza.ind_adesione_adempimento = 3
4 - AC calcolata da sistema per COMUNE
5 - AC calcolata da sistema per SITO RETE NATURA 2000
6 – AC calcolata da sistema per CATEGORIE PROGETTUALI';


-- scriva_r_istanza_competenza_oggetto definition

-- Drop table

-- DROP TABLE scriva_r_istanza_competenza_oggetto;

CREATE TABLE scriva_r_istanza_competenza_oggetto (
	id_istanza int4 NOT NULL,
	id_competenza_territorio int4 NOT NULL,
	id_oggetto_istanza int4 NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_istanza_competenza_oggetto PRIMARY KEY (id_istanza, id_competenza_territorio, id_oggetto_istanza)
);
COMMENT ON TABLE scriva_r_istanza_competenza_oggetto IS 'Permette di individuare quali oggetto istanza hanno determinato l''autorità competente';


-- scriva_r_istanza_evento definition

-- Drop table

-- DROP TABLE scriva_r_istanza_evento;

CREATE TABLE scriva_r_istanza_evento (
	id_istanza_evento int4 NOT NULL,
	id_istanza int4 NOT NULL,
	id_tipo_evento int4 NOT NULL,
	data_evento timestamp NOT NULL,
	id_istanza_attore int4 NULL,
	id_funzionario int4 NULL,
	id_componente_app int4 NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	nota varchar(250) NULL,
	CONSTRAINT pk_scriva_r_istanza_evento PRIMARY KEY (id_istanza_evento)
);


-- scriva_r_istanza_osservazione definition

-- Drop table

-- DROP TABLE scriva_r_istanza_osservazione;

CREATE TABLE scriva_r_istanza_osservazione (
	id_istanza_osservazione int4 NOT NULL,
	id_istanza int4 NOT NULL,
	id_stato_osservazione int4 NOT NULL,
	id_funzionario int4 NULL,
	cf_osservazione_ins varchar(16) NOT NULL,
	data_osservazione timestamp NOT NULL,
	data_pubblicazione timestamp NULL,
	num_protocollo_osservazione varchar(20) NULL,
	data_protocollo_osservazione timestamp NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	des_sintesi_osservazione varchar(200) NULL,
	CONSTRAINT pk_scriva_r_istanza_osservazione PRIMARY KEY (id_istanza_osservazione)
);


-- scriva_r_istanza_responsabile definition

-- Drop table

-- DROP TABLE scriva_r_istanza_responsabile;

CREATE TABLE scriva_r_istanza_responsabile (
	id_istanza_responsabile int4 NOT NULL,
	id_istanza int4 NOT NULL,
	id_tipo_responsabile int4 NOT NULL,
	label_responsabile varchar(100) NOT NULL,
	nominativo_responsabile varchar(150) NOT NULL,
	recapito_responsabile varchar(150) NULL,
	flg_riservato numeric(1) DEFAULT 0 NOT NULL, -- definisce se il responsabile è riservato
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_r_istanza_responsabile_01 CHECK ((flg_riservato = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_istanza_responsabile PRIMARY KEY (id_istanza_responsabile)
);
COMMENT ON TABLE scriva_r_istanza_responsabile IS 'Tabella per censire i responsabili dell’istanza che potranno anche essere di più di quelli censiti per l’AC e anche diversi.
es. si aggiungono il responsabile nucleo operativo per VIA e funzionario istruttoria per VINCA.';

-- Column comments

COMMENT ON COLUMN scriva_r_istanza_responsabile.flg_riservato IS 'definisce se il responsabile è riservato';


-- scriva_r_istanza_stato definition

-- Drop table

-- DROP TABLE scriva_r_istanza_stato;

CREATE TABLE scriva_r_istanza_stato (
	id_istanza int4 NOT NULL,
	data_cambio_stato timestamp NOT NULL,
	id_stato_istanza int4 NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_istanza_stato PRIMARY KEY (id_istanza, data_cambio_stato)
);


-- scriva_r_istanza_storico definition

-- Drop table

-- DROP TABLE scriva_r_istanza_storico;

CREATE TABLE scriva_r_istanza_storico (
	id_istanza_storico int4 NOT NULL,
	id_istanza int4 NOT NULL,
	cod_istanza varchar(50) NULL,
	cod_pratica varchar(70) NULL,
	id_stato_istanza int4 NOT NULL,
	id_adempimento int4 NOT NULL,
	data_inserimento_istanza timestamp NULL,
	data_modifica_istanza timestamp NULL,
	data_inserimento_pratica timestamp NULL,
	data_modifica_pratica timestamp NULL,
	json_data jsonb NULL,
	uuid_index varchar(36) NULL,
	id_istanza_attore_owner int4 NULL,
	id_template int4 NOT NULL,
	id_istanza_attore int4 NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	des_stato_sintesi_pagamento varchar(50) NULL,
	id_funzionario int4 NULL,
	data_pubblicazione timestamp NULL,
	num_protocollo_istanza varchar(20) NULL,
	data_protocollo_istanza timestamp NULL,
	data_inizio_osservazioni timestamp NULL,
	data_fine_osservazioni timestamp NULL,
	data_conclusione_procedimento timestamp NULL,
	id_esito_procedimento int4 NULL,
	data_scadenza_procedimento date NULL,
	anno_registro int4 NULL,
	id_stato_proced_statale int4 NULL,
	id_esito_proced_statale int4 NULL,
	CONSTRAINT pk_scriva_r_istanza_storico PRIMARY KEY (id_istanza_storico)
);


-- scriva_r_istanza_vincolo_aut definition

-- Drop table

-- DROP TABLE scriva_r_istanza_vincolo_aut;

CREATE TABLE scriva_r_istanza_vincolo_aut (
	id_istanza_vincolo_aut int4 NOT NULL,
	id_istanza int4 NOT NULL,
	id_vincolo_autorizza int4 NOT NULL,
	des_vincolo_calcolato varchar(250) NULL,
	des_ente_utente varchar(150) NULL,
	des_email_pec_ente_utente varchar(100) NULL,
	flg_richiesta_post numeric(1) DEFAULT 0 NOT NULL,
	des_motivo_richiesta_port varchar(150) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_r_istanza_vincolo_aut_01 CHECK ((flg_richiesta_post = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_istanza_vincolo_aut PRIMARY KEY (id_istanza_vincolo_aut)
);


-- scriva_r_nota_istanza definition

-- Drop table

-- DROP TABLE scriva_r_nota_istanza;

CREATE TABLE scriva_r_nota_istanza (
	id_nota_istanza int4 NOT NULL,
	id_istanza int4 NOT NULL,
	id_funzionario int4 NOT NULL,
	data_nota timestamp NULL,
	des_nota varchar(1000) NOT NULL,
	ind_visibile varchar(20) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_nota_istanza PRIMARY KEY (id_nota_istanza)
);


-- scriva_r_notifica_allegato definition

-- Drop table

-- DROP TABLE scriva_r_notifica_allegato;

CREATE TABLE scriva_r_notifica_allegato (
	id_notifica_allegato int4 NOT NULL, -- Identificativo univoco
	id_notifica int4 NOT NULL,
	des_segnalazione varchar(2000) NULL, -- valorizzato solo in presenza di un'eccezione
	id_allegato_istanza int4 NULL, -- riferimento : scriva_r_integra_istanza_allegato,
	cod_allegato varchar(50) NULL, -- riportato il codice presente nella tabella _scriva_t_allegato_istanza
	nome_allegato varchar(200) NULL, -- riportato il nome presente nella tabella _scriva_t_allegato_istanza
	flg_allegato_fallito numeric(1) DEFAULT 0 NOT NULL, -- indica l'esito dell'operazione.¶Es.¶0 - il documento è stato allegato correttamente¶1 - il documento non è stato allegato
	gest_uid_richiesta varchar(64) NULL, -- ricevuto dal servizio
	des_tipo_richiesta varchar(2000) NULL, -- ricevuto dal servizio
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_r_notifica_allegato_01 CHECK ((flg_allegato_fallito = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_notifica_allegato PRIMARY KEY (id_notifica_allegato)
);
COMMENT ON TABLE scriva_r_notifica_allegato IS 'Permette di memorizzare a fronte di una notifica, l''eventuale notifica applicativa';

-- Column comments

COMMENT ON COLUMN scriva_r_notifica_allegato.id_notifica_allegato IS 'Identificativo univoco';
COMMENT ON COLUMN scriva_r_notifica_allegato.des_segnalazione IS 'valorizzato solo in presenza di un''eccezione';
COMMENT ON COLUMN scriva_r_notifica_allegato.id_allegato_istanza IS 'riferimento : scriva_r_integra_istanza_allegato';
COMMENT ON COLUMN scriva_r_notifica_allegato.cod_allegato IS 'riportato il codice presente nella tabella _scriva_t_allegato_istanza';
COMMENT ON COLUMN scriva_r_notifica_allegato.nome_allegato IS 'riportato il nome presente nella tabella _scriva_t_allegato_istanza';
COMMENT ON COLUMN scriva_r_notifica_allegato.flg_allegato_fallito IS 'indica l''esito dell''operazione.
Es.
0 - il documento è stato allegato correttamente
1 - il documento non è stato allegato';
COMMENT ON COLUMN scriva_r_notifica_allegato.gest_uid_richiesta IS 'ricevuto dal servizio';
COMMENT ON COLUMN scriva_r_notifica_allegato.des_tipo_richiesta IS 'ricevuto dal servizio';


-- scriva_r_notifica_applicativa definition

-- Drop table

-- DROP TABLE scriva_r_notifica_applicativa;

CREATE TABLE scriva_r_notifica_applicativa (
	id_notifica_applicativa int4 NOT NULL, -- Identificativo univoco
	id_notifica int4 NOT NULL,
	id_istanza int4 NULL,
	id_componente_app_push int4 NULL,
	cf_destinatario varchar(16) NULL,
	des_oggetto varchar(250) NULL,
	des_messaggio varchar(1000) NULL,
	data_inserimento timestamp NOT NULL,
	data_lettura timestamp NULL,
	data_cancellazione timestamp NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_notifica_applicativa PRIMARY KEY (id_notifica_applicativa)
);
COMMENT ON TABLE scriva_r_notifica_applicativa IS 'Permette di memorizzare a fronte di una notifica, l''eventuale notifica applicativa';

-- Column comments

COMMENT ON COLUMN scriva_r_notifica_applicativa.id_notifica_applicativa IS 'Identificativo univoco';


-- scriva_r_ogg_area_protetta definition

-- Drop table

-- DROP TABLE scriva_r_ogg_area_protetta;

CREATE TABLE scriva_r_ogg_area_protetta (
	id_oggetto_area_protetta int4 NOT NULL,
	id_oggetto_istanza int4 NOT NULL,
	id_competenza_territorio int4 NOT NULL,
	id_tipo_area_protetta int4 NOT NULL,
	cod_amministrativo varchar(20) NOT NULL,
	cod_gestore_fonte varchar(20) NOT NULL,
	des_area_protetta varchar(300) NULL,
	flg_ricade numeric(1) DEFAULT 0 NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_r_ogg_area_protetta_01 CHECK ((flg_ricade = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_ogg_area_protetta PRIMARY KEY (id_oggetto_area_protetta)
);
CREATE UNIQUE INDEX ak_scriva_r_ogg_area_protetta_01 ON scriva_r_ogg_area_protetta USING btree (id_oggetto_istanza, cod_amministrativo, id_tipo_area_protetta);


-- scriva_r_ogg_istanza_categoria definition

-- Drop table

-- DROP TABLE scriva_r_ogg_istanza_categoria;

CREATE TABLE scriva_r_ogg_istanza_categoria (
	id_oggetto_istanza int4 NOT NULL,
	id_categoria_oggetto int4 NOT NULL,
	data_inizio_validita date NOT NULL,
	data_fine_validita date NULL,
	flg_cat_nuovo_oggetto numeric(1) DEFAULT 0 NOT NULL,
	flg_cat_modifica_oggetto numeric(1) DEFAULT 0 NOT NULL,
	flg_cat_principale numeric(1) DEFAULT 0 NOT NULL,
	ordinamento_istanza_competenza int4 NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_r_oggis_cat_01 CHECK ((flg_cat_nuovo_oggetto = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_r_oggis_cat_02 CHECK ((flg_cat_modifica_oggetto = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_r_oggis_cat_03 CHECK ((flg_cat_principale = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_ogg_istanza_categoria PRIMARY KEY (id_oggetto_istanza, id_categoria_oggetto)
);


-- scriva_r_ogg_natura_2000 definition

-- Drop table

-- DROP TABLE scriva_r_ogg_natura_2000;

CREATE TABLE scriva_r_ogg_natura_2000 (
	id_oggetto_natura_2000 int4 NOT NULL,
	id_oggetto_istanza int4 NOT NULL,
	id_competenza_territorio int4 NOT NULL,
	id_tipo_natura_2000 int4 NOT NULL,
	cod_amministrativo varchar(20) NOT NULL,
	cod_gestore_fonte varchar(20) NOT NULL,
	des_sito_natura_2000 varchar(300) NULL,
	num_distanza numeric(5) NULL, -- distanza espressa in metri
	flg_ricade numeric(1) DEFAULT 0 NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	des_elemento_discontinuita varchar(300) NULL,
	CONSTRAINT chk_scriva_r_ogg_natura_2000_01 CHECK ((flg_ricade = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_ogg_natura_2000 PRIMARY KEY (id_oggetto_natura_2000)
);
CREATE UNIQUE INDEX ak_scriva_r_ogg_natura_2000_01 ON scriva_r_ogg_natura_2000 USING btree (id_oggetto_istanza, cod_amministrativo, id_tipo_natura_2000);

-- Column comments

COMMENT ON COLUMN scriva_r_ogg_natura_2000.num_distanza IS 'distanza espressa in metri';


-- scriva_r_ogg_vincolo_autorizza definition

-- Drop table

-- DROP TABLE scriva_r_ogg_vincolo_autorizza;

CREATE TABLE scriva_r_ogg_vincolo_autorizza (
	id_oggetto_vincolo_aut int4 NOT NULL,
	id_oggetto_istanza int4 NOT NULL,
	id_vincolo_autorizza int4 NOT NULL,
	des_vincolo_calcolato varchar(250) NULL,
	des_ente_utente varchar(150) NULL,
	des_email_pec_ente_utente varchar(100) NULL,
	flg_richiesta_post numeric(1) DEFAULT 0 NOT NULL,
	des_motivo_richiesta_post varchar(150) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_r_ogg_vincolo_aut_01 CHECK ((flg_richiesta_post = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_ogg_vincolo_autorizza PRIMARY KEY (id_oggetto_vincolo_aut)
);


-- scriva_r_oggetto_ist_figlio definition

-- Drop table

-- DROP TABLE scriva_r_oggetto_ist_figlio;

CREATE TABLE scriva_r_oggetto_ist_figlio (
	id_oggetto_istanza_padre int4 NOT NULL,
	id_oggetto_istanza_figlio int4 NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_oggetto_ist_figlio PRIMARY KEY (id_oggetto_istanza_padre, id_oggetto_istanza_figlio)
);


-- scriva_r_pagamento_istanza definition

-- Drop table

-- DROP TABLE scriva_r_pagamento_istanza;

CREATE TABLE scriva_r_pagamento_istanza (
	id_pagamento_istanza int4 NOT NULL,
	id_istanza int4 NOT NULL,
	id_riscossione_ente int4 NOT NULL,
	id_stato_pagamento int4 NOT NULL,
	id_onere_padre int4 NULL,
	data_inserimento_pagamento timestamp NOT NULL,
	importo_dovuto numeric(13, 2) NULL,
	iuv varchar(50) NULL,
	data_effettivo_pagamento timestamp NULL,
	importo_pagato numeric(13, 2) NULL,
	iubd varchar(20) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	flg_non_dovuto numeric(1) DEFAULT 0 NOT NULL,
	id_allegato_istanza int4 NULL,
	rt_xml xml NULL,
	ind_tipo_inserimento varchar(40) NULL,
	CONSTRAINT chk_scriva_r_pagamento_istanza_01 CHECK ((flg_non_dovuto = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_pagamento_istanza PRIMARY KEY (id_pagamento_istanza)
);
COMMENT ON TABLE scriva_r_pagamento_istanza IS 'Ogni pagamento e'' sempre definito (nel tipo e nell’importo) e quindi riferito ad un Ente che ha una competenza territoriale (tabella scriva_t_competenza_territorio): ogni competenza territorio ha il suo ente creditore, definito nella tabella scriva_d_ente_creditore.';


-- scriva_r_recapito_alternativo definition

-- Drop table

-- DROP TABLE scriva_r_recapito_alternativo;

CREATE TABLE scriva_r_recapito_alternativo (
	id_recapito_alternativo int4 NOT NULL,
	cod_recapito_alternativo varchar(20) NOT NULL,
	id_comune_residenza numeric(6) NULL,
	id_comune_sede_legale numeric(6) NULL,
	id_istanza_attore int4 NOT NULL,
	indirizzo_soggetto varchar(100) NULL,
	num_civico_indirizzo varchar(30) NULL,
	citta_estera_residenza varchar(100) NULL,
	id_nazione_residenza numeric(6) NULL,
	presso varchar(150) NULL,
	num_telefono varchar(25) NULL,
	num_cellulare varchar(25) NULL,
	des_localita varchar(250) NULL,
	des_email varchar(100) NULL,
	des_pec varchar(100) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	id_soggetto_istanza int4 NOT NULL,
	citta_estera_sede_legale varchar(100) NULL,
	id_nazione_sede_legale numeric(3) NULL,
	cap_residenza varchar(10) NULL,
	cap_sede_legale varchar(10) NULL,
	id_funzionario int4 NULL,
	data_aggiornamento timestamp NULL,
	CONSTRAINT pk_scriva_r_recapito_alternativo PRIMARY KEY (id_recapito_alternativo)
);


-- scriva_r_referente_istanza definition

-- Drop table

-- DROP TABLE scriva_r_referente_istanza;

CREATE TABLE scriva_r_referente_istanza (
	id_referente_istanza int4 NOT NULL,
	id_istanza int4 NOT NULL,
	cognome_referente varchar(50) NULL,
	nome_referente varchar(50) NULL,
	num_tel_referente varchar(25) NULL,
	des_email_referente varchar(100) NULL,
	des_mansione_referente varchar(50) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	num_cellulare_referente varchar(25) NULL,
	des_pec_referente varchar(100) NULL,
	CONSTRAINT pk_scriva_r_referente_istanza PRIMARY KEY (id_referente_istanza)
);


-- scriva_r_soggetto_gruppo definition

-- Drop table

-- DROP TABLE scriva_r_soggetto_gruppo;

CREATE TABLE scriva_r_soggetto_gruppo (
	id_gruppo_soggetto int4 NOT NULL,
	id_soggetto_istanza int4 NOT NULL,
	flg_capogruppo numeric(1) DEFAULT 0 NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT chk_scriva_r_soggetto_gruppo_01 CHECK ((flg_capogruppo = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_r_soggetto_gruppo PRIMARY KEY (id_gruppo_soggetto, id_soggetto_istanza)
);


-- scriva_r_tentativo_dettaglio definition

-- Drop table

-- DROP TABLE scriva_r_tentativo_dettaglio;

CREATE TABLE scriva_r_tentativo_dettaglio (
	id_pagamento_istanza int4 NOT NULL,
	id_tentativo_pagamento int4 NOT NULL,
	iuv_tentativo_pagamento varchar(50) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_tentativo_dettaglio PRIMARY KEY (id_pagamento_istanza)
);


-- scriva_r_ubica_oggetto definition

-- Drop table

-- DROP TABLE scriva_r_ubica_oggetto;

CREATE TABLE scriva_r_ubica_oggetto (
	id_ubica_oggetto int4 NOT NULL,
	id_oggetto int4 NOT NULL,
	id_comune numeric(8) NOT NULL,
	den_indirizzo varchar(100) NULL,
	num_civico varchar(30) NULL,
	des_localita varchar(250) NULL,
	ind_geo_provenienza varchar(4) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_ubica_oggetto PRIMARY KEY (id_ubica_oggetto)
);


-- scriva_r_ubica_oggetto_istanza definition

-- Drop table

-- DROP TABLE scriva_r_ubica_oggetto_istanza;

CREATE TABLE scriva_r_ubica_oggetto_istanza (
	id_ubica_oggetto_istanza int4 NOT NULL,
	id_oggetto_istanza int4 NOT NULL,
	id_comune numeric(8) NOT NULL,
	den_indirizzo varchar(100) NULL,
	num_civico varchar(30) NULL,
	des_localita varchar(250) NULL,
	ind_geo_provenienza varchar(4) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	CONSTRAINT pk_scriva_r_ubica_oggetto_istanza PRIMARY KEY (id_ubica_oggetto_istanza)
);


-- scriva_t_allegato_istanza definition

-- Drop table

-- DROP TABLE scriva_t_allegato_istanza;

CREATE TABLE scriva_t_allegato_istanza (
	id_allegato_istanza int4 NOT NULL,
	id_istanza int4 NOT NULL,
	id_tipologia_allegato int4 NOT NULL,
	id_tipo_integrazione int4 NULL,
	uuid_index varchar(36) NOT NULL,
	flg_riservato numeric(1) DEFAULT 0 NOT NULL,
	cod_allegato varchar(50) NOT NULL,
	nome_allegato varchar(200) NOT NULL,
	dimensione_upload int4 NOT NULL,
	data_upload timestamp(0) NULL,
	data_integrazione timestamp(0) NULL,
	data_cancellazione timestamp(0) NULL,
	flg_cancellato numeric(1) NOT NULL,
	ind_firma int4 DEFAULT 0 NOT NULL,
	note varchar(2000) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	id_istanza_attore int4 NULL,
	id_funzionario int4 NULL,
	id_classe_allegato int4 NOT NULL,
	id_allegato_istanza_padre int4 NULL,
	flg_da_pubblicare numeric(1) DEFAULT 0 NOT NULL,
	data_pubblicazione timestamp NULL,
	num_protocollo_allegato varchar(20) NULL,
	data_protocollo_allegato timestamp NULL,
	url_doc varchar(500) NULL,
	id_istanza_osservazione int4 NULL,
	num_atto varchar(20) NULL,
	data_atto date NULL,
	titolo_allegato varchar(1000) NULL, -- Titolo del documento
	autore_allegato varchar(300) NULL, -- Autore del documento
	data_invio_esterno timestamp NULL, -- Data trasmissione del documento al componente di gestione processo (se presente)
	nome_allegato_index varchar(200) NULL, -- Nome del file caricato su INDEX ev. formattato senza caratteri speciali
	CONSTRAINT chk_scriva_t_allegato_istanza_01 CHECK ((flg_riservato = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_t_allegato_istanza_02 CHECK ((flg_cancellato = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT chk_scriva_t_allegato_istanza_03 CHECK ((flg_da_pubblicare = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_t_allegato_istanza PRIMARY KEY (id_allegato_istanza)
);
CREATE UNIQUE INDEX ak_scriva_t_allegato_istanza_01 ON scriva_t_allegato_istanza USING btree (id_istanza, cod_allegato);

-- Column comments

COMMENT ON COLUMN scriva_t_allegato_istanza.titolo_allegato IS 'Titolo del documento';
COMMENT ON COLUMN scriva_t_allegato_istanza.autore_allegato IS 'Autore del documento';
COMMENT ON COLUMN scriva_t_allegato_istanza.data_invio_esterno IS 'Data trasmissione del documento al componente di gestione processo (se presente)';
COMMENT ON COLUMN scriva_t_allegato_istanza.nome_allegato_index IS 'Nome del file caricato su INDEX ev. formattato senza caratteri speciali';


-- scriva_t_gruppo_soggetto definition

-- Drop table

-- DROP TABLE scriva_t_gruppo_soggetto;

CREATE TABLE scriva_t_gruppo_soggetto (
	id_gruppo_soggetto int4 NOT NULL,
	cod_gruppo_soggetto varchar(20) NOT NULL,
	des_gruppo_soggetto varchar(100) NULL,
	flg_creazione_automatica numeric(1) DEFAULT 0 NOT NULL,
	id_istanza_attore int4 NOT NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	id_funzionario int4 NULL,
	data_aggiornamento timestamp NULL,
	CONSTRAINT chk_scriva_t_gruppo_soggetto_01 CHECK ((flg_creazione_automatica = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_t_gruppo_soggetto PRIMARY KEY (id_gruppo_soggetto)
);
CREATE UNIQUE INDEX ak_scriva_t_gruppo_soggetto_01 ON scriva_t_gruppo_soggetto USING btree (cod_gruppo_soggetto);


-- scriva_t_integrazione_istanza definition

-- Drop table

-- DROP TABLE scriva_t_integrazione_istanza;

CREATE TABLE scriva_t_integrazione_istanza (
	id_integrazione_istanza int4 NOT NULL,
	id_istanza int4 NOT NULL,
	id_tipo_integrazione int4 NOT NULL,
	data_inserimento timestamp NOT NULL,
	data_invio timestamp NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	num_protocollo_integrazione varchar(10) NULL,
	data_protocollo_integrazione timestamp NULL,
	CONSTRAINT pk_scriva_t_integrazione_istanza PRIMARY KEY (id_integrazione_istanza)
);
COMMENT ON TABLE scriva_t_integrazione_istanza IS 'Rappresenta l''integrazione di un''istanza, che può essere di perfezionamento, di integrazione successiva all''avvio e di altri tipi di comunicazione tra proponente e PA.';


-- scriva_t_istanza definition

-- Drop table

-- DROP TABLE scriva_t_istanza;

CREATE TABLE scriva_t_istanza (
	id_istanza int4 NOT NULL,
	cod_istanza varchar(50) NULL, -- Campo calcolato applicativamente :¶Regola : <IST>-<COD ADEMPIMENTO>-<seq_scriva_cod_istanza >¶¶<IST>= prefisso fisso concordato con utente¶<<CODICE ADEMPIMENTO>= specifico es. SPE ¶<progressivo assoluto> = seq_scriva_cod_istanza.nextval¶es: IST-VER-1234
	id_stato_istanza int4 NOT NULL,
	id_adempimento int4 NOT NULL,
	data_inserimento_istanza timestamp NULL,
	data_modifica_istanza timestamp NULL,
	json_data jsonb NULL, -- Contiene la rappresentazione in formato JSON dell'istanza.  Attenzione :  I dati tecnici degli oggetti associati ad una istanza, sono salvati solo a livello di json data .  Salvati con il seguente standard: • I dati tecnici e verticali di un determinato procedimento sono salvati solo su json_data e non in tabelle strutturate e devono essere riconoscibili tramite il prefisso “JS_”; • Eventuali informazioni salvate sul json data  che riguardano l’help devono essere riconoscibili tramite il prefisso “HELP_”  Esempio  "JS_INFO_GENERALI": {       "flg_risorse_pubbliche": true,         "flg_opera_pubblica": false,         "des_risorse_pubbliche": "des risorse"      },  La descrizione della gestione delle informazioni specifiche di ogni adempimento e' demandata ai documenti US009 specifici.
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	uuid_index varchar(36) NULL,
	cod_pratica varchar(70) NULL, -- Campo calcolato applicativamente :¶¶Regola : ANNO-PROGRESSIVO nell'anno/TIPO ADEMPIMENTO – ADEMPIMENTO¶esempio 2015-13/VIA-VER
	id_template int4 NOT NULL,
	id_istanza_attore_owner int4 NULL,
	data_inserimento_pratica timestamp NULL,
	data_modifica_pratica timestamp NULL,
	id_istanza_attore int4 NULL,
	des_stato_sintesi_pagamento varchar(50) NULL,
	id_funzionario int4 NULL,
	data_pubblicazione timestamp NULL,
	num_protocollo_istanza varchar(20) NULL,
	data_protocollo_istanza timestamp NULL,
	data_inizio_osservazioni timestamp NULL,
	data_fine_osservazioni timestamp NULL,
	data_conclusione_procedimento timestamp NULL,
	id_esito_procedimento int4 NULL,
	data_scadenza_procedimento date NULL,
	anno_registro int4 NULL,
	id_stato_proced_statale int4 NULL,
	id_esito_proced_statale int4 NULL,
	CONSTRAINT pk_scriva_t_istanza PRIMARY KEY (id_istanza)
);
CREATE INDEX ie_scriva_t_istanza_01 ON scriva_t_istanza USING btree (data_inserimento_istanza);
COMMENT ON TABLE scriva_t_istanza IS 'Si intende la richiesta, la comunicazione e la segnalazione con cui un Richiedente chiede ad un organo pubblico di avviare un procedimento.';

-- Column comments

COMMENT ON COLUMN scriva_t_istanza.cod_istanza IS 'Campo calcolato applicativamente :
Regola : <IST>-<COD ADEMPIMENTO>-<seq_scriva_cod_istanza >

<IST>= prefisso fisso concordato con utente
<<CODICE ADEMPIMENTO>= specifico es. SPE 
<progressivo assoluto> = seq_scriva_cod_istanza.nextval
es: IST-VER-1234';
COMMENT ON COLUMN scriva_t_istanza.json_data IS 'Contiene la rappresentazione in formato JSON dell''istanza.  Attenzione :  I dati tecnici degli oggetti associati ad una istanza, sono salvati solo a livello di json data .  Salvati con il seguente standard: • I dati tecnici e verticali di un determinato procedimento sono salvati solo su json_data e non in tabelle strutturate e devono essere riconoscibili tramite il prefisso “JS_”; • Eventuali informazioni salvate sul json data  che riguardano l’help devono essere riconoscibili tramite il prefisso “HELP_”  Esempio 	"JS_INFO_GENERALI": {     		"flg_risorse_pubbliche": true,       		"flg_opera_pubblica": false,       		"des_risorse_pubbliche": "des risorse"   	  },  La descrizione della gestione delle informazioni specifiche di ogni adempimento e'' demandata ai documenti US009 specifici.';
COMMENT ON COLUMN scriva_t_istanza.cod_pratica IS 'Campo calcolato applicativamente :

Regola : ANNO-PROGRESSIVO nell''anno/TIPO ADEMPIMENTO – ADEMPIMENTO
esempio 2015-13/VIA-VER';


-- scriva_t_notifica definition

-- Drop table

-- DROP TABLE scriva_t_notifica;

CREATE TABLE scriva_t_notifica (
	id_notifica int4 NOT NULL,
	id_stato_notifica int4 NOT NULL,
	id_notifica_configurazione int4 NOT NULL,
	id_istanza int4 NULL,
	id_componente_app_push int4 NULL,
	cf_destinatario varchar(16) NULL,
	rif_canale varchar(250) NULL,
	rif_canale_cc varchar(250) NULL,
	des_oggetto varchar(1000) NULL,
	des_messaggio varchar(5000) NULL,
	data_inserimento timestamp NOT NULL,
	data_invio timestamp NULL,
	n_tentativi_invio numeric(4) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	des_segnalazione varchar(2000) NULL, -- valorizzato solo in presenza di un'eccezione
	CONSTRAINT pk_scriva_t_notifica PRIMARY KEY (id_notifica)
);
COMMENT ON TABLE scriva_t_notifica IS 'Permette di memorizzare le notifiche da inviare';

-- Column comments

COMMENT ON COLUMN scriva_t_notifica.des_segnalazione IS 'valorizzato solo in presenza di un''eccezione';


-- scriva_t_oggetto definition

-- Drop table

-- DROP TABLE scriva_t_oggetto;

CREATE TABLE scriva_t_oggetto (
	id_oggetto int4 NOT NULL,
	id_tipologia_oggetto int4 NOT NULL,
	id_stato_oggetto int4 NULL,
	id_masterdata int4 NOT NULL,
	id_masterdata_origine int4 NOT NULL,
	cod_scriva varchar(20) NOT NULL,
	den_oggetto varchar(500) NOT NULL,
	des_oggetto varchar(2000) NULL,
	coordinata_x numeric NULL,
	coordinata_y numeric NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	data_aggiornamento timestamp NULL,
	cod_oggetto_fonte varchar(50) NULL,
	id_istanza_aggiornamento int4 NULL,
	id_funzionario int4 NULL,
	CONSTRAINT pk_scriva_t_oggetto PRIMARY KEY (id_oggetto)
);
CREATE UNIQUE INDEX ak_scriva_t_oggetto_01 ON scriva_t_oggetto USING btree (cod_scriva);
COMMENT ON TABLE scriva_t_oggetto IS 'E'' l’anagrafica degli oggetti (un oggetto e'' presente in questa tabella a prescindere da quali istanze trattano questo oggetto) mentre la SCRIVA_T_OGGETTO_ISTANZA e'' la copia dell’oggetto a livello di una specifica istanza.  Lo stato dell’oggetto e'' gestito solo a livello di oggetto_anagrafica ed e'' impostato manualmente dal funzionario di BackOffice.';


-- scriva_t_oggetto_istanza definition

-- Drop table

-- DROP TABLE scriva_t_oggetto_istanza;

CREATE TABLE scriva_t_oggetto_istanza (
	id_oggetto_istanza int4 NOT NULL,
	id_oggetto int4 NOT NULL,
	id_istanza int4 NOT NULL,
	id_tipologia_oggetto int4 NOT NULL,
	id_masterdata int4 NOT NULL,
	id_masterdata_origine int4 NOT NULL,
	den_oggetto varchar(500) NOT NULL,
	des_oggetto varchar(2000) NULL,
	coordinata_x numeric NULL,
	coordinata_y numeric NULL,
	ind_geo_stato varchar(1) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	id_istanza_attore int4 NOT NULL,
	id_funzionario int4 NULL,
	cod_oggetto_fonte varchar(50) NULL,
	cod_utenza varchar(20) NULL,
	note_atto_precedente varchar(300) NULL,
	flg_geo_modificato numeric(1) DEFAULT 0 NOT NULL, -- Indica se sono state modificate geometrie
	ind_livello numeric(2) DEFAULT 0 NOT NULL, -- Indica il livello di gerarchia del soggetto all'interno dell'istanza :¶- 1 = Principale¶- 2 = Figlio¶- etc...
	CONSTRAINT chk_scriva_t_oggetto_istanza_02 CHECK ((flg_geo_modificato = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT pk_scriva_t_oggetto_istanza PRIMARY KEY (id_oggetto_istanza)
);
CREATE UNIQUE INDEX ak_scriva_t_oggetto_istanza_01 ON scriva_t_oggetto_istanza USING btree (id_istanza, id_oggetto);
COMMENT ON TABLE scriva_t_oggetto_istanza IS 'Contiene l’elenco degli oggetti associati all’istanza. Diversi dati dell’istanza (es. ubicazione, stato dell’oggetto) sono salvati in questa tabella e ad essa sono collegate le tabelle che contengono le geometrie (puntuali o lineari o areali) dell’oggetto istanza (rispettivamente le tabelle scriva_geo_punto_ogg_istanza, scriva_geo_linea_ogg_istanza, scriva_geo_area_ogg_istanza), i siti Rete Natura 2000, le aree protette, i dati catastali.';

-- Column comments

COMMENT ON COLUMN scriva_t_oggetto_istanza.flg_geo_modificato IS 'Indica se sono state modificate geometrie';
COMMENT ON COLUMN scriva_t_oggetto_istanza.ind_livello IS 'Indica il livello di gerarchia del soggetto all''interno dell''istanza :
- 1 = Principale
- 2 = Figlio
- etc...';


-- scriva_t_soggetto_istanza definition

-- Drop table

-- DROP TABLE scriva_t_soggetto_istanza;

CREATE TABLE scriva_t_soggetto_istanza (
	id_soggetto_istanza int4 NOT NULL,
	id_soggetto int4 NOT NULL,
	id_istanza int4 NOT NULL,
	id_ruolo_compilante int4 NOT NULL,
	id_soggetto_padre int4 NULL,
	id_ruolo_soggetto int4 NULL,
	cf_soggetto varchar(16) NOT NULL,
	id_tipo_soggetto int4 NOT NULL,
	id_tipo_natura_giuridica int4 NULL,
	id_masterdata int4 NOT NULL,
	id_masterdata_origine int4 NOT NULL,
	id_comune_nascita numeric(6) NULL,
	id_comune_residenza numeric(6) NULL,
	id_comune_sede_legale numeric(6) NULL,
	partita_iva_soggetto varchar(16) NULL,
	den_soggetto varchar(250) NULL,
	data_cessazione_soggetto date NULL,
	nome varchar(100) NULL,
	cognome varchar(100) NULL,
	data_nascita_soggetto date NULL,
	citta_estera_nascita varchar(100) NULL,
	num_telefono varchar(25) NULL,
	des_email varchar(100) NULL,
	des_pec varchar(100) NULL,
	indirizzo_soggetto varchar(100) NULL,
	num_civico_indirizzo varchar(30) NULL,
	citta_estera_residenza varchar(100) NULL,
	den_provincia_cciaa varchar(20) NULL,
	den_anno_cciaa numeric(4) NULL,
	den_numero_cciaa varchar(20) NULL,
	gest_data_ins timestamp NOT NULL,
	gest_attore_ins varchar(30) NOT NULL,
	gest_data_upd timestamp NOT NULL,
	gest_attore_upd varchar(30) NOT NULL,
	gest_uid varchar(64) NULL,
	id_istanza_attore int4 NOT NULL,
	num_cellulare varchar(25) NULL,
	id_nazione_residenza numeric(3) NULL,
	id_nazione_nascita numeric(3) NULL,
	des_localita varchar(250) NULL,
	citta_estera_sede_legale varchar(100) NULL,
	id_nazione_sede_legale numeric(3) NULL,
	cap_residenza varchar(10) NULL,
	cap_sede_legale varchar(10) NULL,
	id_funzionario int4 NULL,
	data_aggiornamento timestamp NULL,
	CONSTRAINT pk_scriva_t_soggetto_istanza PRIMARY KEY (id_soggetto_istanza)
);
CREATE INDEX ie_scriva_t_soggetto_istanza_01 ON scriva_t_soggetto_istanza USING btree (id_istanza, id_soggetto);


-- scriva_geo_area_ogg_istanza foreign keys

ALTER TABLE scriva_geo_area_ogg_istanza ADD CONSTRAINT fk_scriva_t_oggetto_istanza_03 FOREIGN KEY (id_oggetto_istanza) REFERENCES scriva_t_oggetto_istanza(id_oggetto_istanza);


-- scriva_geo_linea_ogg_istanza foreign keys

ALTER TABLE scriva_geo_linea_ogg_istanza ADD CONSTRAINT fk_scriva_t_oggetto_istanza_02 FOREIGN KEY (id_oggetto_istanza) REFERENCES scriva_t_oggetto_istanza(id_oggetto_istanza);


-- scriva_geo_punto_ogg_istanza foreign keys

ALTER TABLE scriva_geo_punto_ogg_istanza ADD CONSTRAINT fk_scriva_t_oggetto_istanza_01 FOREIGN KEY (id_oggetto_istanza) REFERENCES scriva_t_oggetto_istanza(id_oggetto_istanza);


-- scriva_r_catasto_ubi_ogg_ist foreign keys

ALTER TABLE scriva_r_catasto_ubi_ogg_ist ADD CONSTRAINT fk_scriva_r_ubica_ogg_ist_01 FOREIGN KEY (id_ubica_oggetto_istanza) REFERENCES scriva_r_ubica_oggetto_istanza(id_ubica_oggetto_istanza);


-- scriva_r_integra_istanza_allegato foreign keys

ALTER TABLE scriva_r_integra_istanza_allegato ADD CONSTRAINT fk_scriva_t_allegato_istanza_02 FOREIGN KEY (id_allegato_istanza) REFERENCES scriva_t_allegato_istanza(id_allegato_istanza);
ALTER TABLE scriva_r_integra_istanza_allegato ADD CONSTRAINT fk_scriva_t_integrazione_istanza_01 FOREIGN KEY (id_integrazione_istanza) REFERENCES scriva_t_integrazione_istanza(id_integrazione_istanza);


-- scriva_r_istanza_attore foreign keys

ALTER TABLE scriva_r_istanza_attore ADD CONSTRAINT fk_scriva_d_profilo_app_02 FOREIGN KEY (id_profilo_app) REFERENCES scriva_d_profilo_app(id_profilo_app);
ALTER TABLE scriva_r_istanza_attore ADD CONSTRAINT fk_scriva_d_tipo_abilita_01 FOREIGN KEY (id_tipo_abilitazione) REFERENCES scriva_d_tipo_abilitazione(id_tipo_abilitazione);
ALTER TABLE scriva_r_istanza_attore ADD CONSTRAINT fk_scriva_t_compilante_01 FOREIGN KEY (id_compilante) REFERENCES scriva_t_compilante(id_compilante);
ALTER TABLE scriva_r_istanza_attore ADD CONSTRAINT fk_scriva_t_istanza_09 FOREIGN KEY (id_istanza) REFERENCES scriva_t_istanza(id_istanza);


-- scriva_r_istanza_competenza foreign keys

ALTER TABLE scriva_r_istanza_competenza ADD CONSTRAINT fk_scriva_t_competenza_02 FOREIGN KEY (id_competenza_territorio) REFERENCES scriva_t_competenza_territorio(id_competenza_territorio);
ALTER TABLE scriva_r_istanza_competenza ADD CONSTRAINT fk_scriva_t_istanza_08 FOREIGN KEY (id_istanza) REFERENCES scriva_t_istanza(id_istanza);


-- scriva_r_istanza_competenza_oggetto foreign keys

ALTER TABLE scriva_r_istanza_competenza_oggetto ADD CONSTRAINT fk_scriva_r_istanza_competenza_01 FOREIGN KEY (id_istanza,id_competenza_territorio) REFERENCES scriva_r_istanza_competenza(id_istanza,id_competenza_territorio);
ALTER TABLE scriva_r_istanza_competenza_oggetto ADD CONSTRAINT fk_scriva_t_oggetto_istanza_10 FOREIGN KEY (id_oggetto_istanza) REFERENCES scriva_t_oggetto_istanza(id_oggetto_istanza);


-- scriva_r_istanza_evento foreign keys

ALTER TABLE scriva_r_istanza_evento ADD CONSTRAINT fk_scriva_d_componente_app_02 FOREIGN KEY (id_componente_app) REFERENCES scriva_d_componente_app(id_componente_app);
ALTER TABLE scriva_r_istanza_evento ADD CONSTRAINT fk_scriva_d_tipo_evento_01 FOREIGN KEY (id_tipo_evento) REFERENCES scriva_d_tipo_evento(id_tipo_evento);
ALTER TABLE scriva_r_istanza_evento ADD CONSTRAINT fk_scriva_r_istanza_attore_08 FOREIGN KEY (id_istanza_attore) REFERENCES scriva_r_istanza_attore(id_istanza_attore);
ALTER TABLE scriva_r_istanza_evento ADD CONSTRAINT fk_scriva_t_funzionario_13 FOREIGN KEY (id_funzionario) REFERENCES scriva_t_funzionario(id_funzionario);
ALTER TABLE scriva_r_istanza_evento ADD CONSTRAINT fk_scriva_t_istanza_13 FOREIGN KEY (id_istanza) REFERENCES scriva_t_istanza(id_istanza);


-- scriva_r_istanza_osservazione foreign keys

ALTER TABLE scriva_r_istanza_osservazione ADD CONSTRAINT fk_scriva_d_stato_osserva_01 FOREIGN KEY (id_stato_osservazione) REFERENCES scriva_d_stato_osservazione(id_stato_osservazione);
ALTER TABLE scriva_r_istanza_osservazione ADD CONSTRAINT fk_scriva_t_funzionario_09 FOREIGN KEY (id_funzionario) REFERENCES scriva_t_funzionario(id_funzionario);
ALTER TABLE scriva_r_istanza_osservazione ADD CONSTRAINT fk_scriva_t_istanza_14 FOREIGN KEY (id_istanza) REFERENCES scriva_t_istanza(id_istanza);


-- scriva_r_istanza_responsabile foreign keys

ALTER TABLE scriva_r_istanza_responsabile ADD CONSTRAINT fk_scriva_d_tipo_responsabile_02 FOREIGN KEY (id_tipo_responsabile) REFERENCES scriva_d_tipo_responsabile(id_tipo_responsabile);
ALTER TABLE scriva_r_istanza_responsabile ADD CONSTRAINT fk_scriva_t_istanza_19 FOREIGN KEY (id_istanza) REFERENCES scriva_t_istanza(id_istanza);


-- scriva_r_istanza_stato foreign keys

ALTER TABLE scriva_r_istanza_stato ADD CONSTRAINT fk_scriva_d_stato_istanza_02 FOREIGN KEY (id_stato_istanza) REFERENCES scriva_d_stato_istanza(id_stato_istanza);
ALTER TABLE scriva_r_istanza_stato ADD CONSTRAINT fk_scriva_t_istanza_06 FOREIGN KEY (id_istanza) REFERENCES scriva_t_istanza(id_istanza);


-- scriva_r_istanza_storico foreign keys

ALTER TABLE scriva_r_istanza_storico ADD CONSTRAINT fk_scriva_t_istanza_12 FOREIGN KEY (id_istanza) REFERENCES scriva_t_istanza(id_istanza);


-- scriva_r_istanza_vincolo_aut foreign keys

ALTER TABLE scriva_r_istanza_vincolo_aut ADD CONSTRAINT fk_scriva_d_vincolo_autoriz_02 FOREIGN KEY (id_vincolo_autorizza) REFERENCES scriva_d_vincolo_autorizza(id_vincolo_autorizza);
ALTER TABLE scriva_r_istanza_vincolo_aut ADD CONSTRAINT fk_scriva_t_istanza_11 FOREIGN KEY (id_istanza) REFERENCES scriva_t_istanza(id_istanza);


-- scriva_r_nota_istanza foreign keys

ALTER TABLE scriva_r_nota_istanza ADD CONSTRAINT fk_scriva_t_funzionario_10 FOREIGN KEY (id_funzionario) REFERENCES scriva_t_funzionario(id_funzionario);
ALTER TABLE scriva_r_nota_istanza ADD CONSTRAINT fk_scriva_t_istanza_16 FOREIGN KEY (id_istanza) REFERENCES scriva_t_istanza(id_istanza);


-- scriva_r_notifica_allegato foreign keys

ALTER TABLE scriva_r_notifica_allegato ADD CONSTRAINT fk_scriva_t_notifica_02 FOREIGN KEY (id_notifica) REFERENCES scriva_t_notifica(id_notifica);


-- scriva_r_notifica_applicativa foreign keys

ALTER TABLE scriva_r_notifica_applicativa ADD CONSTRAINT fk_scriva_d_componente_app_08 FOREIGN KEY (id_componente_app_push) REFERENCES scriva_d_componente_app(id_componente_app);
ALTER TABLE scriva_r_notifica_applicativa ADD CONSTRAINT fk_scriva_t_istanza_18 FOREIGN KEY (id_istanza) REFERENCES scriva_t_istanza(id_istanza);
ALTER TABLE scriva_r_notifica_applicativa ADD CONSTRAINT fk_scriva_t_notifica_01 FOREIGN KEY (id_notifica) REFERENCES scriva_t_notifica(id_notifica);


-- scriva_r_ogg_area_protetta foreign keys

ALTER TABLE scriva_r_ogg_area_protetta ADD CONSTRAINT fk_scriva_d_tipo_area_prot_01 FOREIGN KEY (id_tipo_area_protetta) REFERENCES scriva_d_tipo_area_protetta(id_tipo_area_protetta);
ALTER TABLE scriva_r_ogg_area_protetta ADD CONSTRAINT fk_scriva_t_competenza_09 FOREIGN KEY (id_competenza_territorio) REFERENCES scriva_t_competenza_territorio(id_competenza_territorio);
ALTER TABLE scriva_r_ogg_area_protetta ADD CONSTRAINT fk_scriva_t_oggetto_istanza_09 FOREIGN KEY (id_oggetto_istanza) REFERENCES scriva_t_oggetto_istanza(id_oggetto_istanza);


-- scriva_r_ogg_istanza_categoria foreign keys

ALTER TABLE scriva_r_ogg_istanza_categoria ADD CONSTRAINT fk_scriva_d_categoria_oggetto_02 FOREIGN KEY (id_categoria_oggetto) REFERENCES scriva_d_categoria_oggetto(id_categoria_oggetto);
ALTER TABLE scriva_r_ogg_istanza_categoria ADD CONSTRAINT fk_scriva_t_oggetto_istanza_05 FOREIGN KEY (id_oggetto_istanza) REFERENCES scriva_t_oggetto_istanza(id_oggetto_istanza);


-- scriva_r_ogg_natura_2000 foreign keys

ALTER TABLE scriva_r_ogg_natura_2000 ADD CONSTRAINT fk_scriva_d_tiponatura_2000_01 FOREIGN KEY (id_tipo_natura_2000) REFERENCES scriva_d_tipo_natura_2000(id_tipo_natura_2000);
ALTER TABLE scriva_r_ogg_natura_2000 ADD CONSTRAINT fk_scriva_t_competenza_08 FOREIGN KEY (id_competenza_territorio) REFERENCES scriva_t_competenza_territorio(id_competenza_territorio);
ALTER TABLE scriva_r_ogg_natura_2000 ADD CONSTRAINT fk_scriva_t_oggetto_istanza_08 FOREIGN KEY (id_oggetto_istanza) REFERENCES scriva_t_oggetto_istanza(id_oggetto_istanza);


-- scriva_r_ogg_vincolo_autorizza foreign keys

ALTER TABLE scriva_r_ogg_vincolo_autorizza ADD CONSTRAINT fk_scriva_d_vincolo_autoriz_03 FOREIGN KEY (id_vincolo_autorizza) REFERENCES scriva_d_vincolo_autorizza(id_vincolo_autorizza);
ALTER TABLE scriva_r_ogg_vincolo_autorizza ADD CONSTRAINT fk_scriva_t_oggetto_istanza_04 FOREIGN KEY (id_oggetto_istanza) REFERENCES scriva_t_oggetto_istanza(id_oggetto_istanza);


-- scriva_r_oggetto_ist_figlio foreign keys

ALTER TABLE scriva_r_oggetto_ist_figlio ADD CONSTRAINT fk_scriva_t_oggetto_istanza_06 FOREIGN KEY (id_oggetto_istanza_padre) REFERENCES scriva_t_oggetto_istanza(id_oggetto_istanza);
ALTER TABLE scriva_r_oggetto_ist_figlio ADD CONSTRAINT fk_scriva_t_oggetto_istanza_07 FOREIGN KEY (id_oggetto_istanza_figlio) REFERENCES scriva_t_oggetto_istanza(id_oggetto_istanza);


-- scriva_r_pagamento_istanza foreign keys

ALTER TABLE scriva_r_pagamento_istanza ADD CONSTRAINT fk_scriva_d_stato_pagamento_01 FOREIGN KEY (id_stato_pagamento) REFERENCES scriva_d_stato_pagamento(id_stato_pagamento);
ALTER TABLE scriva_r_pagamento_istanza ADD CONSTRAINT fk_scriva_r_pagamento_istanza_01 FOREIGN KEY (id_onere_padre) REFERENCES scriva_r_pagamento_istanza(id_pagamento_istanza);
ALTER TABLE scriva_r_pagamento_istanza ADD CONSTRAINT fk_scriva_r_riscossione_ente_02 FOREIGN KEY (id_riscossione_ente) REFERENCES scriva_r_riscossione_ente(id_riscossione_ente);
ALTER TABLE scriva_r_pagamento_istanza ADD CONSTRAINT fk_scriva_t_allegato_istanza_01 FOREIGN KEY (id_allegato_istanza) REFERENCES scriva_t_allegato_istanza(id_allegato_istanza);
ALTER TABLE scriva_r_pagamento_istanza ADD CONSTRAINT fk_scriva_t_istanza_10 FOREIGN KEY (id_istanza) REFERENCES scriva_t_istanza(id_istanza);


-- scriva_r_recapito_alternativo foreign keys

ALTER TABLE scriva_r_recapito_alternativo ADD CONSTRAINT fk_scriva_d_nazione_09 FOREIGN KEY (id_nazione_sede_legale) REFERENCES scriva_d_nazione(id_nazione);
ALTER TABLE scriva_r_recapito_alternativo ADD CONSTRAINT fk_scriva_r_istanza_attore_06 FOREIGN KEY (id_istanza_attore) REFERENCES scriva_r_istanza_attore(id_istanza_attore);
ALTER TABLE scriva_r_recapito_alternativo ADD CONSTRAINT fk_scriva_t_funzionario_05 FOREIGN KEY (id_funzionario) REFERENCES scriva_t_funzionario(id_funzionario);
ALTER TABLE scriva_r_recapito_alternativo ADD CONSTRAINT fk_scriva_t_soggetto_istanza_03 FOREIGN KEY (id_soggetto_istanza) REFERENCES scriva_t_soggetto_istanza(id_soggetto_istanza);


-- scriva_r_referente_istanza foreign keys

ALTER TABLE scriva_r_referente_istanza ADD CONSTRAINT fk_scriva_t_istanza_01 FOREIGN KEY (id_istanza) REFERENCES scriva_t_istanza(id_istanza);


-- scriva_r_soggetto_gruppo foreign keys

ALTER TABLE scriva_r_soggetto_gruppo ADD CONSTRAINT fk_scriva_t_gruppo_soggetto_01 FOREIGN KEY (id_gruppo_soggetto) REFERENCES scriva_t_gruppo_soggetto(id_gruppo_soggetto);
ALTER TABLE scriva_r_soggetto_gruppo ADD CONSTRAINT fk_scriva_t_soggetto_istanza_02 FOREIGN KEY (id_soggetto_istanza) REFERENCES scriva_t_soggetto_istanza(id_soggetto_istanza);


-- scriva_r_tentativo_dettaglio foreign keys

ALTER TABLE scriva_r_tentativo_dettaglio ADD CONSTRAINT fk_scriva_r_pag_istanza_01 FOREIGN KEY (id_pagamento_istanza) REFERENCES scriva_r_pagamento_istanza(id_pagamento_istanza);
ALTER TABLE scriva_r_tentativo_dettaglio ADD CONSTRAINT fk_scriva_t_tentativo_pag_01 FOREIGN KEY (id_tentativo_pagamento) REFERENCES scriva_t_tentativo_pagamento(id_tentativo_pagamento);


-- scriva_r_ubica_oggetto foreign keys

ALTER TABLE scriva_r_ubica_oggetto ADD CONSTRAINT fk_scriva_d_comune_05 FOREIGN KEY (id_comune) REFERENCES scriva_d_comune(id_comune);
ALTER TABLE scriva_r_ubica_oggetto ADD CONSTRAINT fk_scriva_t_oggetto_02 FOREIGN KEY (id_oggetto) REFERENCES scriva_t_oggetto(id_oggetto);


-- scriva_r_ubica_oggetto_istanza foreign keys

ALTER TABLE scriva_r_ubica_oggetto_istanza ADD CONSTRAINT fk_scriva_d_comune_06 FOREIGN KEY (id_comune) REFERENCES scriva_d_comune(id_comune);
ALTER TABLE scriva_r_ubica_oggetto_istanza ADD CONSTRAINT fk_scriva_t_oggetto_istanza_11 FOREIGN KEY (id_oggetto_istanza) REFERENCES scriva_t_oggetto_istanza(id_oggetto_istanza);


-- scriva_t_allegato_istanza foreign keys

ALTER TABLE scriva_t_allegato_istanza ADD CONSTRAINT fk_scriva_d_classe_allegato_01 FOREIGN KEY (id_classe_allegato) REFERENCES scriva_d_classe_allegato(id_classe_allegato);
ALTER TABLE scriva_t_allegato_istanza ADD CONSTRAINT fk_scriva_d_tipo_allegato_02 FOREIGN KEY (id_tipologia_allegato) REFERENCES scriva_d_tipologia_allegato(id_tipologia_allegato);
ALTER TABLE scriva_t_allegato_istanza ADD CONSTRAINT fk_scriva_d_tipo_integrazione_02 FOREIGN KEY (id_tipo_integrazione) REFERENCES scriva_d_tipo_integrazione(id_tipo_integrazione);
ALTER TABLE scriva_t_allegato_istanza ADD CONSTRAINT fk_scriva_r_istanza_attore_04 FOREIGN KEY (id_istanza_attore) REFERENCES scriva_r_istanza_attore(id_istanza_attore);
ALTER TABLE scriva_t_allegato_istanza ADD CONSTRAINT fk_scriva_r_istanza_osserva_01 FOREIGN KEY (id_istanza_osservazione) REFERENCES scriva_r_istanza_osservazione(id_istanza_osservazione);
ALTER TABLE scriva_t_allegato_istanza ADD CONSTRAINT fk_scriva_t_allegato_istanza_03 FOREIGN KEY (id_allegato_istanza_padre) REFERENCES scriva_t_allegato_istanza(id_allegato_istanza);
ALTER TABLE scriva_t_allegato_istanza ADD CONSTRAINT fk_scriva_t_funzionario_08 FOREIGN KEY (id_funzionario) REFERENCES scriva_t_funzionario(id_funzionario);
ALTER TABLE scriva_t_allegato_istanza ADD CONSTRAINT fk_scriva_t_istanza_07 FOREIGN KEY (id_istanza) REFERENCES scriva_t_istanza(id_istanza);


-- scriva_t_gruppo_soggetto foreign keys

ALTER TABLE scriva_t_gruppo_soggetto ADD CONSTRAINT fk_scriva_r_istanza_attore_07 FOREIGN KEY (id_istanza_attore) REFERENCES scriva_r_istanza_attore(id_istanza_attore);
ALTER TABLE scriva_t_gruppo_soggetto ADD CONSTRAINT fk_scriva_t_funzionario_06 FOREIGN KEY (id_funzionario) REFERENCES scriva_t_funzionario(id_funzionario);


-- scriva_t_integrazione_istanza foreign keys

ALTER TABLE scriva_t_integrazione_istanza ADD CONSTRAINT fk_scriva_d_tipo_integrazione_01 FOREIGN KEY (id_tipo_integrazione) REFERENCES scriva_d_tipo_integrazione(id_tipo_integrazione);
ALTER TABLE scriva_t_integrazione_istanza ADD CONSTRAINT fk_scriva_t_istanza_20 FOREIGN KEY (id_istanza) REFERENCES scriva_t_istanza(id_istanza);


-- scriva_t_istanza foreign keys

ALTER TABLE scriva_t_istanza ADD CONSTRAINT fk_scriva_d_adempimento_03 FOREIGN KEY (id_adempimento) REFERENCES scriva_d_adempimento(id_adempimento);
ALTER TABLE scriva_t_istanza ADD CONSTRAINT fk_scriva_d_esito_proc_01 FOREIGN KEY (id_esito_procedimento) REFERENCES scriva_d_esito_procedimento(id_esito_procedimento) ON DELETE SET NULL;
ALTER TABLE scriva_t_istanza ADD CONSTRAINT fk_scriva_d_esito_proc_03 FOREIGN KEY (id_esito_proced_statale) REFERENCES scriva_d_esito_procedimento(id_esito_procedimento);
ALTER TABLE scriva_t_istanza ADD CONSTRAINT fk_scriva_d_stato_istanza_01 FOREIGN KEY (id_stato_istanza) REFERENCES scriva_d_stato_istanza(id_stato_istanza);
ALTER TABLE scriva_t_istanza ADD CONSTRAINT fk_scriva_d_stato_proced_statale_01 FOREIGN KEY (id_stato_proced_statale) REFERENCES scriva_d_stato_proced_statale(id_stato_proced_statale);
ALTER TABLE scriva_t_istanza ADD CONSTRAINT fk_scriva_d_template_02 FOREIGN KEY (id_template) REFERENCES scriva_d_template(id_template);
ALTER TABLE scriva_t_istanza ADD CONSTRAINT fk_scriva_r_istanza_attore_01 FOREIGN KEY (id_istanza_attore_owner) REFERENCES scriva_r_istanza_attore(id_istanza_attore);
ALTER TABLE scriva_t_istanza ADD CONSTRAINT fk_scriva_r_istanza_attore_05 FOREIGN KEY (id_istanza_attore) REFERENCES scriva_r_istanza_attore(id_istanza_attore);
ALTER TABLE scriva_t_istanza ADD CONSTRAINT fk_scriva_t_funzionario_03 FOREIGN KEY (id_funzionario) REFERENCES scriva_t_funzionario(id_funzionario);


-- scriva_t_notifica foreign keys

ALTER TABLE scriva_t_notifica ADD CONSTRAINT fk_scriva_d_componente_app_07 FOREIGN KEY (id_componente_app_push) REFERENCES scriva_d_componente_app(id_componente_app);
ALTER TABLE scriva_t_notifica ADD CONSTRAINT fk_scriva_d_stato_notifica_01 FOREIGN KEY (id_stato_notifica) REFERENCES scriva_d_stato_notifica(id_stato_notifica);
ALTER TABLE scriva_t_notifica ADD CONSTRAINT fk_scriva_r_notifica_configurazione_02 FOREIGN KEY (id_notifica_configurazione) REFERENCES scriva_r_notifica_configurazione(id_notifica_configurazione);
ALTER TABLE scriva_t_notifica ADD CONSTRAINT fk_scriva_t_istanza_17 FOREIGN KEY (id_istanza) REFERENCES scriva_t_istanza(id_istanza);


-- scriva_t_oggetto foreign keys

ALTER TABLE scriva_t_oggetto ADD CONSTRAINT fk_scriva_d_stato_oggetto_01 FOREIGN KEY (id_stato_oggetto) REFERENCES scriva_d_stato_oggetto(id_stato_oggetto);
ALTER TABLE scriva_t_oggetto ADD CONSTRAINT fk_scriva_d_tipo_oggetto_01 FOREIGN KEY (id_tipologia_oggetto) REFERENCES scriva_d_tipologia_oggetto(id_tipologia_oggetto);
ALTER TABLE scriva_t_oggetto ADD CONSTRAINT fk_scriva_t_funzionario_12 FOREIGN KEY (id_funzionario) REFERENCES scriva_t_funzionario(id_funzionario);
ALTER TABLE scriva_t_oggetto ADD CONSTRAINT fk_scriva_t_istanza_15 FOREIGN KEY (id_istanza_aggiornamento) REFERENCES scriva_t_istanza(id_istanza) ON DELETE SET NULL;


-- scriva_t_oggetto_istanza foreign keys

ALTER TABLE scriva_t_oggetto_istanza ADD CONSTRAINT fk_scriva_d_tipo_oggetto_03 FOREIGN KEY (id_tipologia_oggetto) REFERENCES scriva_d_tipologia_oggetto(id_tipologia_oggetto);
ALTER TABLE scriva_t_oggetto_istanza ADD CONSTRAINT fk_scriva_r_istanza_attore_03 FOREIGN KEY (id_istanza_attore) REFERENCES scriva_r_istanza_attore(id_istanza_attore);
ALTER TABLE scriva_t_oggetto_istanza ADD CONSTRAINT fk_scriva_t_funzionario_07 FOREIGN KEY (id_funzionario) REFERENCES scriva_t_funzionario(id_funzionario);
ALTER TABLE scriva_t_oggetto_istanza ADD CONSTRAINT fk_scriva_t_istanza_04 FOREIGN KEY (id_istanza) REFERENCES scriva_t_istanza(id_istanza);
ALTER TABLE scriva_t_oggetto_istanza ADD CONSTRAINT fk_scriva_t_oggetto_01 FOREIGN KEY (id_oggetto) REFERENCES scriva_t_oggetto(id_oggetto);


-- scriva_t_soggetto_istanza foreign keys

ALTER TABLE scriva_t_soggetto_istanza ADD CONSTRAINT fk_scriva_d_nazione_03 FOREIGN KEY (id_nazione_residenza) REFERENCES scriva_d_nazione(id_nazione);
ALTER TABLE scriva_t_soggetto_istanza ADD CONSTRAINT fk_scriva_d_nazione_04 FOREIGN KEY (id_nazione_nascita) REFERENCES scriva_d_nazione(id_nazione);
ALTER TABLE scriva_t_soggetto_istanza ADD CONSTRAINT fk_scriva_d_nazione_08 FOREIGN KEY (id_nazione_sede_legale) REFERENCES scriva_d_nazione(id_nazione);
ALTER TABLE scriva_t_soggetto_istanza ADD CONSTRAINT fk_scriva_d_ruolo_compila_01 FOREIGN KEY (id_ruolo_compilante) REFERENCES scriva_d_ruolo_compilante(id_ruolo_compilante);
ALTER TABLE scriva_t_soggetto_istanza ADD CONSTRAINT fk_scriva_d_ruolo_soggetto_02 FOREIGN KEY (id_ruolo_soggetto) REFERENCES scriva_d_ruolo_soggetto(id_ruolo_soggetto);
ALTER TABLE scriva_t_soggetto_istanza ADD CONSTRAINT fk_scriva_d_tipo_natura_giu_02 FOREIGN KEY (id_tipo_natura_giuridica) REFERENCES scriva_d_tipo_natura_giuridica(id_tipo_natura_giuridica);
ALTER TABLE scriva_t_soggetto_istanza ADD CONSTRAINT fk_scriva_d_tipo_soggetto_02 FOREIGN KEY (id_tipo_soggetto) REFERENCES scriva_d_tipo_soggetto(id_tipo_soggetto);
ALTER TABLE scriva_t_soggetto_istanza ADD CONSTRAINT fk_scriva_r_istanza_attore_02 FOREIGN KEY (id_istanza_attore) REFERENCES scriva_r_istanza_attore(id_istanza_attore);
ALTER TABLE scriva_t_soggetto_istanza ADD CONSTRAINT fk_scriva_r_soggetto_istanza01 FOREIGN KEY (id_soggetto_padre) REFERENCES scriva_t_soggetto_istanza(id_soggetto_istanza);
ALTER TABLE scriva_t_soggetto_istanza ADD CONSTRAINT fk_scriva_t_funzionario_04 FOREIGN KEY (id_funzionario) REFERENCES scriva_t_funzionario(id_funzionario);
ALTER TABLE scriva_t_soggetto_istanza ADD CONSTRAINT fk_scriva_t_istanza_02 FOREIGN KEY (id_istanza) REFERENCES scriva_t_istanza(id_istanza);
ALTER TABLE scriva_t_soggetto_istanza ADD CONSTRAINT fk_scriva_t_soggetto_01 FOREIGN KEY (id_soggetto) REFERENCES scriva_t_soggetto(id_soggetto);


-- FK mancanti
ALTER TABLE scriva_r_configura_ruolo_sogg ADD CONSTRAINT fk_scriva_r_proc_ruolo_comp_01 FOREIGN KEY (id_ruolo_compilante, id_adempimento) REFERENCES scriva_r_adempi_ruolo_compila(id_ruolo_compilante, id_adempimento);
ALTER TABLE scriva_r_configura_ruolo_sogg ADD CONSTRAINT fk_scriva_r_proc_ruolo_sogg_01 FOREIGN KEY (id_ruolo_soggetto, id_adempimento) REFERENCES scriva_r_adempi_ruolo_sogg(id_ruolo_soggetto, id_adempimento); 


-- --------------------------------------------------------------------------------------

ALTER TABLE scriva_d_ambito                      OWNER TO scriva;
ALTER TABLE scriva_d_categoria_allegato          OWNER TO scriva;
ALTER TABLE scriva_d_categoria_oggetto           OWNER TO scriva;
ALTER TABLE scriva_d_classe_allegato             OWNER TO scriva;
ALTER TABLE scriva_d_componente_app              OWNER TO scriva;
ALTER TABLE scriva_d_config_geeco                OWNER TO scriva;
ALTER TABLE scriva_d_configurazione              OWNER TO scriva;
ALTER TABLE scriva_d_continente                  OWNER TO scriva;
ALTER TABLE scriva_d_dizionario_placeholder      OWNER TO scriva;
ALTER TABLE scriva_d_ente_creditore              OWNER TO scriva;
ALTER TABLE scriva_d_esito_procedimento          OWNER TO scriva;
ALTER TABLE scriva_d_funzionalita                OWNER TO scriva;
ALTER TABLE scriva_d_gestione_attore             OWNER TO scriva;
ALTER TABLE scriva_d_gruppo_pagamento            OWNER TO scriva;
ALTER TABLE scriva_d_informazioni_scriva         OWNER TO scriva;
ALTER TABLE scriva_d_livello_help                OWNER TO scriva;
ALTER TABLE scriva_d_mappa_fonte_esterna         OWNER TO scriva;
ALTER TABLE scriva_d_masterdata                  OWNER TO scriva;
ALTER TABLE scriva_d_natura_oggetto              OWNER TO scriva;
ALTER TABLE scriva_d_origine_limiti              OWNER TO scriva;
ALTER TABLE scriva_d_ruolo_compilante            OWNER TO scriva;
ALTER TABLE scriva_d_ruolo_soggetto              OWNER TO scriva;
ALTER TABLE scriva_d_stato_elabora               OWNER TO scriva;
ALTER TABLE scriva_d_stato_istanza               OWNER TO scriva;
ALTER TABLE scriva_d_stato_notifica              OWNER TO scriva;
ALTER TABLE scriva_d_stato_oggetto               OWNER TO scriva;
ALTER TABLE scriva_d_stato_osservazione          OWNER TO scriva;
ALTER TABLE scriva_d_stato_pagamento             OWNER TO scriva;
ALTER TABLE scriva_d_stato_proced_statale        OWNER TO scriva;
ALTER TABLE scriva_d_stato_tentativo_pag         OWNER TO scriva;
ALTER TABLE scriva_d_template_comunica           OWNER TO scriva;
ALTER TABLE scriva_d_tipo_area_protetta          OWNER TO scriva;
ALTER TABLE scriva_d_tipo_competenza             OWNER TO scriva;
ALTER TABLE scriva_d_tipo_destinatario           OWNER TO scriva;
ALTER TABLE scriva_d_tipo_help                   OWNER TO scriva;
ALTER TABLE scriva_d_tipo_integrazione           OWNER TO scriva;
ALTER TABLE scriva_d_tipo_messaggio              OWNER TO scriva;
ALTER TABLE scriva_d_tipo_natura_2000            OWNER TO scriva;
ALTER TABLE scriva_d_tipo_natura_giuridica       OWNER TO scriva;
ALTER TABLE scriva_d_tipo_notifica               OWNER TO scriva;
ALTER TABLE scriva_d_tipo_oggetto_app            OWNER TO scriva;
ALTER TABLE scriva_d_tipo_quadro                 OWNER TO scriva;
ALTER TABLE scriva_d_tipo_responsabile           OWNER TO scriva;
ALTER TABLE scriva_d_tipo_soggetto               OWNER TO scriva;
ALTER TABLE scriva_d_tipo_vincolo_aut            OWNER TO scriva;
ALTER TABLE scriva_s_catasto_ubi_ogg_ist         OWNER TO scriva;
ALTER TABLE scriva_s_funzionario                 OWNER TO scriva;
ALTER TABLE scriva_s_gruppo_soggetto             OWNER TO scriva;
ALTER TABLE scriva_s_istanza_competenza          OWNER TO scriva;
ALTER TABLE scriva_s_istanza_vincolo_aut         OWNER TO scriva;
ALTER TABLE scriva_s_ogg_area_protetta           OWNER TO scriva;
ALTER TABLE scriva_s_ogg_istanza_categoria       OWNER TO scriva;
ALTER TABLE scriva_s_ogg_natura_2000             OWNER TO scriva;
ALTER TABLE scriva_s_ogg_vincolo_autorizza       OWNER TO scriva;
ALTER TABLE scriva_s_oggetto                     OWNER TO scriva;
ALTER TABLE scriva_s_oggetto_ist_figlio          OWNER TO scriva;
ALTER TABLE scriva_s_oggetto_istanza             OWNER TO scriva;
ALTER TABLE scriva_s_recapito_alternativo        OWNER TO scriva;
ALTER TABLE scriva_s_soggetto                    OWNER TO scriva;
ALTER TABLE scriva_s_soggetto_gruppo             OWNER TO scriva;
ALTER TABLE scriva_s_soggetto_istanza            OWNER TO scriva;
ALTER TABLE scriva_s_ubica_oggetto               OWNER TO scriva;
ALTER TABLE scriva_s_ubica_oggetto_istanza       OWNER TO scriva;
ALTER TABLE scriva_t_compilante                  OWNER TO scriva;
ALTER TABLE scriva_t_funzionario                 OWNER TO scriva;
ALTER TABLE scriva_w_acq_soggetto                OWNER TO scriva;
ALTER TABLE scriva_w_del_istanza                 OWNER TO scriva;
ALTER TABLE scriva_d_canale_notifica             OWNER TO scriva;
ALTER TABLE scriva_d_config_json_data            OWNER TO scriva;
ALTER TABLE scriva_d_help                        OWNER TO scriva;
ALTER TABLE scriva_d_messaggio                   OWNER TO scriva;
ALTER TABLE scriva_d_nazione                     OWNER TO scriva;
ALTER TABLE scriva_d_profilo_app                 OWNER TO scriva;
ALTER TABLE scriva_d_quadro                      OWNER TO scriva;
ALTER TABLE scriva_d_regione                     OWNER TO scriva;
ALTER TABLE scriva_d_tipo_abilitazione           OWNER TO scriva;
ALTER TABLE scriva_d_tipo_adempimento            OWNER TO scriva;
ALTER TABLE scriva_d_tipo_elabora                OWNER TO scriva;
ALTER TABLE scriva_d_tipo_evento                 OWNER TO scriva;
ALTER TABLE scriva_d_tipo_pagamento              OWNER TO scriva;
ALTER TABLE scriva_d_tipologia_allegato          OWNER TO scriva;
ALTER TABLE scriva_d_tipologia_oggetto           OWNER TO scriva;
ALTER TABLE scriva_d_vincolo_autorizza           OWNER TO scriva;
ALTER TABLE scriva_r_categoria_tipo_ogg          OWNER TO scriva;
ALTER TABLE scriva_r_compilante_preferenza       OWNER TO scriva;
ALTER TABLE scriva_r_compilante_preferenza_cn    OWNER TO scriva;
ALTER TABLE scriva_r_funzionario_profilo         OWNER TO scriva;
ALTER TABLE scriva_r_geeco_layer_virtuali        OWNER TO scriva;
ALTER TABLE scriva_r_richiesta_accredito         OWNER TO scriva;
ALTER TABLE scriva_r_tipo_adempi_profilo         OWNER TO scriva;
ALTER TABLE scriva_r_tipo_competenza_cat         OWNER TO scriva;
ALTER TABLE scriva_r_tipo_notifica_evento        OWNER TO scriva;
ALTER TABLE scriva_s_nazione                     OWNER TO scriva;
ALTER TABLE scriva_s_regione                     OWNER TO scriva;
ALTER TABLE scriva_t_elabora                     OWNER TO scriva;
ALTER TABLE scriva_t_tentativo_pagamento         OWNER TO scriva;
ALTER TABLE scriva_d_adempimento                 OWNER TO scriva;
ALTER TABLE scriva_d_destinatario                OWNER TO scriva;
ALTER TABLE scriva_d_oggetto_app                 OWNER TO scriva;
ALTER TABLE scriva_d_provincia                   OWNER TO scriva;
ALTER TABLE scriva_d_template                    OWNER TO scriva;
ALTER TABLE scriva_log_acq_allegato              OWNER TO scriva;
ALTER TABLE scriva_log_acq_istanza               OWNER TO scriva;
ALTER TABLE scriva_log_acq_soggetto              OWNER TO scriva;
ALTER TABLE scriva_r_adempi_categoria_ogg        OWNER TO scriva;
ALTER TABLE scriva_r_adempi_config               OWNER TO scriva;
ALTER TABLE scriva_r_adempi_esito_proced         OWNER TO scriva;
ALTER TABLE scriva_r_adempi_est_allegato         OWNER TO scriva;
ALTER TABLE scriva_r_adempi_provincia            OWNER TO scriva;
ALTER TABLE scriva_r_adempi_ruolo_app            OWNER TO scriva;
ALTER TABLE scriva_r_adempi_ruolo_compila        OWNER TO scriva;
ALTER TABLE scriva_r_adempi_ruolo_sogg           OWNER TO scriva;
ALTER TABLE scriva_r_adempi_tipo_allegato        OWNER TO scriva;
ALTER TABLE scriva_r_adempi_tipo_oggetto         OWNER TO scriva;
ALTER TABLE scriva_r_adempi_vincolo_aut          OWNER TO scriva;
ALTER TABLE scriva_r_configura_ruolo_sogg        OWNER TO scriva;
ALTER TABLE scriva_r_destinatario_canale         OWNER TO scriva;
ALTER TABLE scriva_r_geeco_layer_mappa           OWNER TO scriva;
ALTER TABLE scriva_r_profilo_ogg_app             OWNER TO scriva;
ALTER TABLE scriva_r_registro_elabora            OWNER TO scriva;
ALTER TABLE scriva_r_stato_istanza_adempi        OWNER TO scriva;
ALTER TABLE scriva_r_template_quadro             OWNER TO scriva;
ALTER TABLE scriva_r_tipo_adempi_ogg_app         OWNER TO scriva;
ALTER TABLE scriva_r_tipo_notifica_evento_adempi OWNER TO scriva;
ALTER TABLE scriva_r_vincolo_tipo_allegato       OWNER TO scriva;
ALTER TABLE scriva_s_provincia                   OWNER TO scriva;
ALTER TABLE scriva_d_comune                      OWNER TO scriva;
ALTER TABLE scriva_s_comune                      OWNER TO scriva;
ALTER TABLE scriva_t_competenza_territorio       OWNER TO scriva;
ALTER TABLE scriva_t_soggetto                    OWNER TO scriva;
ALTER TABLE scriva_d_adempi_tipo_pagamento       OWNER TO scriva;
ALTER TABLE scriva_r_adempi_competenza           OWNER TO scriva;
ALTER TABLE scriva_r_competenza_comune           OWNER TO scriva;
ALTER TABLE scriva_r_competenza_provincia        OWNER TO scriva;
ALTER TABLE scriva_r_competenza_regione          OWNER TO scriva;
ALTER TABLE scriva_r_competenza_responsabile     OWNER TO scriva;
ALTER TABLE scriva_r_funzionario_compete         OWNER TO scriva;
ALTER TABLE scriva_r_notifica_configurazione     OWNER TO scriva;
ALTER TABLE scriva_r_riscossione_ente            OWNER TO scriva;
ALTER TABLE scriva_r_notifica_config_allegato    OWNER TO scriva;
ALTER TABLE scriva_r_risco_stato_istanza         OWNER TO scriva;
ALTER TABLE scriva_geo_area_ogg_istanza          OWNER TO scriva;
ALTER TABLE scriva_geo_linea_ogg_istanza         OWNER TO scriva;
ALTER TABLE scriva_geo_punto_ogg_istanza         OWNER TO scriva;
ALTER TABLE scriva_r_catasto_ubi_ogg_ist         OWNER TO scriva;
ALTER TABLE scriva_r_integra_istanza_allegato    OWNER TO scriva;
ALTER TABLE scriva_r_istanza_attore              OWNER TO scriva;
ALTER TABLE scriva_r_istanza_competenza          OWNER TO scriva;
ALTER TABLE scriva_r_istanza_competenza_oggetto  OWNER TO scriva;
ALTER TABLE scriva_r_istanza_evento              OWNER TO scriva;
ALTER TABLE scriva_r_istanza_osservazione        OWNER TO scriva;
ALTER TABLE scriva_r_istanza_responsabile        OWNER TO scriva;
ALTER TABLE scriva_r_istanza_stato               OWNER TO scriva;
ALTER TABLE scriva_r_istanza_storico             OWNER TO scriva;
ALTER TABLE scriva_r_istanza_vincolo_aut         OWNER TO scriva;
ALTER TABLE scriva_r_nota_istanza                OWNER TO scriva;
ALTER TABLE scriva_r_notifica_allegato           OWNER TO scriva;
ALTER TABLE scriva_r_notifica_applicativa        OWNER TO scriva;
ALTER TABLE scriva_r_ogg_area_protetta           OWNER TO scriva;
ALTER TABLE scriva_r_ogg_istanza_categoria       OWNER TO scriva;
ALTER TABLE scriva_r_ogg_natura_2000             OWNER TO scriva;
ALTER TABLE scriva_r_ogg_vincolo_autorizza       OWNER TO scriva;
ALTER TABLE scriva_r_oggetto_ist_figlio          OWNER TO scriva;
ALTER TABLE scriva_r_pagamento_istanza           OWNER TO scriva;
ALTER TABLE scriva_r_recapito_alternativo        OWNER TO scriva;
ALTER TABLE scriva_r_referente_istanza           OWNER TO scriva;
ALTER TABLE scriva_r_soggetto_gruppo             OWNER TO scriva;
ALTER TABLE scriva_r_tentativo_dettaglio         OWNER TO scriva;
ALTER TABLE scriva_r_ubica_oggetto               OWNER TO scriva;
ALTER TABLE scriva_r_ubica_oggetto_istanza       OWNER TO scriva;
ALTER TABLE scriva_t_allegato_istanza            OWNER TO scriva;
ALTER TABLE scriva_t_gruppo_soggetto             OWNER TO scriva;
ALTER TABLE scriva_t_integrazione_istanza        OWNER TO scriva;
ALTER TABLE scriva_t_istanza                     OWNER TO scriva;
ALTER TABLE scriva_t_notifica                    OWNER TO scriva;
ALTER TABLE scriva_t_oggetto                     OWNER TO scriva;
ALTER TABLE scriva_t_oggetto_istanza             OWNER TO scriva;
ALTER TABLE scriva_t_soggetto_istanza            OWNER TO scriva;


GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_ambito                      TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_categoria_allegato          TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_categoria_oggetto           TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_classe_allegato             TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_componente_app              TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_config_geeco                TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_configurazione              TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_continente                  TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_dizionario_placeholder      TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_ente_creditore              TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_esito_procedimento          TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_funzionalita                TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_gestione_attore             TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_gruppo_pagamento            TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_informazioni_scriva         TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_livello_help                TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_mappa_fonte_esterna         TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_masterdata                  TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_natura_oggetto              TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_origine_limiti              TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_ruolo_compilante            TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_ruolo_soggetto              TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_stato_elabora               TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_stato_istanza               TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_stato_notifica              TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_stato_oggetto               TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_stato_osservazione          TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_stato_pagamento             TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_stato_proced_statale        TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_stato_tentativo_pag         TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_template_comunica           TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_tipo_area_protetta          TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_tipo_competenza             TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_tipo_destinatario           TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_tipo_help                   TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_tipo_integrazione           TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_tipo_messaggio              TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_tipo_natura_2000            TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_tipo_natura_giuridica       TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_tipo_notifica               TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_tipo_oggetto_app            TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_tipo_quadro                 TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_tipo_responsabile           TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_tipo_soggetto               TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_tipo_vincolo_aut            TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_s_catasto_ubi_ogg_ist         TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_s_funzionario                 TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_s_gruppo_soggetto             TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_s_istanza_competenza          TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_s_istanza_vincolo_aut         TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_s_ogg_area_protetta           TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_s_ogg_istanza_categoria       TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_s_ogg_natura_2000             TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_s_ogg_vincolo_autorizza       TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_s_oggetto                     TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_s_oggetto_ist_figlio          TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_s_oggetto_istanza             TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_s_recapito_alternativo        TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_s_soggetto                    TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_s_soggetto_gruppo             TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_s_soggetto_istanza            TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_s_ubica_oggetto               TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_s_ubica_oggetto_istanza       TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_t_compilante                  TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_t_funzionario                 TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_w_acq_soggetto                TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_w_del_istanza                 TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_canale_notifica             TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_config_json_data            TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_help                        TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_messaggio                   TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_nazione                     TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_profilo_app                 TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_quadro                      TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_regione                     TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_tipo_abilitazione           TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_tipo_adempimento            TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_tipo_elabora                TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_tipo_evento                 TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_tipo_pagamento              TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_tipologia_allegato          TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_tipologia_oggetto           TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_vincolo_autorizza           TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_categoria_tipo_ogg          TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_compilante_preferenza       TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_compilante_preferenza_cn    TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_funzionario_profilo         TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_geeco_layer_virtuali        TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_richiesta_accredito         TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_tipo_adempi_profilo         TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_tipo_competenza_cat         TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_tipo_notifica_evento        TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_s_nazione                     TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_s_regione                     TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_t_elabora                     TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_t_tentativo_pagamento         TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_adempimento                 TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_destinatario                TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_oggetto_app                 TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_provincia                   TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_template                    TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_log_acq_allegato              TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_log_acq_istanza               TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_log_acq_soggetto              TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_adempi_categoria_ogg        TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_adempi_config               TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_adempi_esito_proced         TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_adempi_est_allegato         TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_adempi_provincia            TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_adempi_ruolo_app            TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_adempi_ruolo_compila        TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_adempi_ruolo_sogg           TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_adempi_tipo_allegato        TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_adempi_tipo_oggetto         TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_adempi_vincolo_aut          TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_configura_ruolo_sogg        TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_destinatario_canale         TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_geeco_layer_mappa           TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_profilo_ogg_app             TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_registro_elabora            TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_stato_istanza_adempi        TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_template_quadro             TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_tipo_adempi_ogg_app         TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_tipo_notifica_evento_adempi TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_vincolo_tipo_allegato       TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_s_provincia                   TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_comune                      TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_s_comune                      TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_t_competenza_territorio       TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_t_soggetto                    TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_d_adempi_tipo_pagamento       TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_adempi_competenza           TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_competenza_comune           TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_competenza_provincia        TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_competenza_regione          TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_competenza_responsabile     TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_funzionario_compete         TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_notifica_configurazione     TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_riscossione_ente            TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_notifica_config_allegato    TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_risco_stato_istanza         TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_geo_area_ogg_istanza          TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_geo_linea_ogg_istanza         TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_geo_punto_ogg_istanza         TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_catasto_ubi_ogg_ist         TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_integra_istanza_allegato    TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_istanza_attore              TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_istanza_competenza          TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_istanza_competenza_oggetto  TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_istanza_evento              TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_istanza_osservazione        TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_istanza_responsabile        TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_istanza_stato               TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_istanza_storico             TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_istanza_vincolo_aut         TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_nota_istanza                TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_notifica_allegato           TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_notifica_applicativa        TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_ogg_area_protetta           TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_ogg_istanza_categoria       TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_ogg_natura_2000             TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_ogg_vincolo_autorizza       TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_oggetto_ist_figlio          TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_pagamento_istanza           TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_recapito_alternativo        TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_referente_istanza           TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_soggetto_gruppo             TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_tentativo_dettaglio         TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_ubica_oggetto               TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_r_ubica_oggetto_istanza       TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_t_allegato_istanza            TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_t_gruppo_soggetto             TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_t_integrazione_istanza        TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_t_istanza                     TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_t_notifica                    TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_t_oggetto                     TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_t_oggetto_istanza             TO scriva_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE scriva_t_soggetto_istanza            TO scriva_rw;
