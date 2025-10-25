# Prodotto - Componente
SCRIVA - SCRIVADB

# Descrizione della componente
SCRIVADB è la componente DB del prodotto SCRIVA.\
Il DBMS di riferimento è Postgresql 12.\
Tramite gli script messi a disposizione è possibile creare gli schemi dei dati del DB, usati dalle altre componenti che accedono ai dati per le operazioni CRUD. \
Per il popolamento iniziale delle tabelle possono essere forniti degli script "DML" su richiesta, con i valori utili per le tabelle di decodifica per esempio, nel caso di Regione Piemonte.\
Questa componente include quindi:
- script DDL per la creazione delle tabelle dello schema dati;
- script DDL per la definizione delle sequence e dei vincoli;
- script per la definizione delle viste (ove applicabile);

# Configurazioni iniziali
Definire un DB "SCRIVA" (per esempio) su una istanza DBMS Postgresql (consigliata versione 12 o superiore).
Per il DB di SCRIVA occorre prevedere uno "schema" con relativi utenti correlati:
 - utente proprietario dello schema;
 - utente "rw" per l'accesso ai dati da parte dell'applicativo.;
 - utente "ro" per l'accesso in sola lettura ai dati da parte di eventuali altre piattaforme di reportistica o similari.

L'utente "scriva" è il proprietario dello schema corrispondente, e l'utente "scriva_rw" serve per accedere ai dati da applicativo ed effettuare le operazioni CRUD (questo utente non ha la possibilità di modificare lo schema dati).

# Getting Started
Una volta prelevato ("git clone") e portato in locale dal repository il progetto che include la componente DB, predisporsi per poter eseguire gli script nella sequenza indicata nel seguito.

# Prerequisiti di sistema
DBMS Postgresql versione 12 o sup.; DB, schema ed utenti con permessi adeguati ad eseguire istruzioni di creazione tabelle.

# Installazione
Lanciare tutti gli script nella sequenza indicata, seguendo le note riportate.

Presupponendo di dover creare l'ambiente database da zero gli script da eseguire in sequenza sono:

--------------------


1. CONNETTERSI ALLO USER DI AMMINISTRAZIONE:


iniziale-1.0.0-01-drops_schemas_roles.sql   ==> Serve per la riesecuzione, elimina tutto l'ambiente database.
                                                Può essere quindi saltato la prima volta o comunque quando non è ancora stato creato nulla

iniziale-1.0.0-02-create_roles.sql          ==> Crea tutti i ruoli ( scriva, scriva_rw)

iniziale-1.0.0-03-create_schemas.sql        ==> Crea tutti gli schemi ( scriva)


----------------------
2. CONNETTERSI ALLO USER DI SCRIVA:

scrivadb-1.0.0-01-drops_objects.sql    ==> Serve per la riesecuzione, elimina tutti gli oggetti dello schema scriva
                                        Può essere quindi saltato la prima volta o comunque quando non è ancora stato creato nulla

scrivadb-1.0.0-02-set_search_path.sql  ==> Setta opportunamente il parametro di sistema SEARCH_PATH

scrivadb-1.0.0-03-create_tables.sql    ==> Crea tutte le tabelle dello schema scriva

scrivadb-1.0.0-04-create_sequences.sql ==> Crea tutte le sequence dello schema scriva

scrivadb-1.0.0-05-create_functions.sql ==> Crea tutte le function dello schema scriva

scrivadb-1.0.0-06-create_views.sql     ==> Crea tutte le viste dello schema scriva

scrivadb-1.0.0-07-insert_data.sql      ==> Inserisce tutti i dati necessari in alcune tabelle dello schema scriva (es. tabelle di decodifica)

scrivadb-1.0.0-08-insert_limiti_amministrativi.sql      ==> Inserisce tutti i dati necessari alla gestione dei limiti amministrativi (es. comuni,regioni, etc...)

----------------------------

Nella directory "doc" saranno disponibili documenti descrittivi ed eventuali ulteriori script di popolamento tabelle di esempio.

# Versioning
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

# Authors
Fare riferimento a quanto riportato nel file AUTHORS.txt.

# Copyrights

© Copyright Regione Piemonte – 2025

Vedere anche il file Copyrights.txt .

# License
Il prodotto software è sottoposto alla licenza EUPL-1.2 o versioni successive.\
SPDX-License-Identifier: EUPL-1.2-or-later.\

Vedere il file EUPL v1_2 IT-LICENSE.txt per i dettagli.

