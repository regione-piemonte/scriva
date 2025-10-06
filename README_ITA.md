# Prodotto

SCRIVA : Sistema delle Scrivanie del Richiedente e del Funzionario per i procedimenti ambientali

# Descrizione del prodotto
Il prodotto nasce dall'esigenza di disporre di una soluzione di riferimento trasversale per la gestione dei procedimenti ambientali coerente col modello generale per l’ICT della PA italiana promosso da AgID, rispondendo a una duplice esigenza:

- offrire un punto di vista inter-tematico, raccordando, anche in ottica di user centered design, i procedimenti dei diversi business afferenti allo stesso richiedente o allo stesso intervento/opera/attività sul territorio;

- gestire trasversalmente ai diversi procedimenti logiche comuni rispetto a funzioni di:
-- scrivania (query & reporting, gestione stati/eventi, bacheca notifiche, scadenziario);
-- processo (deleghe/procure, anagrafiche soggetti/oggetti);
-- integrazione con le altre piattaforme (georeferenziazione, protocollazione & gestione documentale, pagamenti);
-- interazione tra gli stakeholder e i sistemi coinvolti, al fine di permettere che le PA dialoghino su un procedimento all’interno della soluzione, oppure ingaggiando sistemi esterni (es: SUAP).


E' stato realizzato con l'obiettivo di semplificare, dematerializzare e armonizzare i servizi pubblici della Regione relativi alle procedure ambientali rivolti a imprese, professionisti e cittadini attraverso la realizzazione di una nuova modalità di interazione e il potenziamento del livello di integrazione dei servizi regionali con la P.A.

La soluzione trasversale è costituita a livello funzionale dalle componenti di:

- Front Office, o scrivania del Richiedente, inteso come soggetto privato, persona fisica o giuridica, diretto o mediato da un professionista, o anche Pubblica Amministrazione, che interagisce con la P.A. per la presentazione di un’istanza;
- Back Office, o scrivania del Funzionario, inteso come soggetto pubblico cui è destinata l’istanza, che prende in carico la pratica e la gestisce coinvolgendo gli Enti Terzi interessati allo svolgimento delle attività istruttorie.


Complessivamente l’applicativo prevede moduli di front-end web che interagiscono, tramite API, con moduli di back-end dove risiede la logica di business e che accedono al DB.

Il prodotto segue quindi il paradigma “SPA – Single Page Application” : la componente di interfaccia (Angular oppure Form.io) ha una corrispondente componente di “BackEnd”, realizzata nel linguaggio Java, e che espone API REST per la componente Angular; le componenti di back-end  sono quelle che accedono al DB (data access).

Il prodotto è strutturato nelle seguenti componenti specifiche:
- [scrivadb]( https://github.com/regione-piemonte/scriva/tree/main/scrivadb ) : script per la creazione del DB (istanza DBMS Postgresql);
- [scrivafowcl]( https://github.com/regione-piemonte/scriva/tree/main/scrivafowcl ) : Client Web (Angular 9), front-end applicativo canale "autenticato";
- [scrivafoweb]( https://github.com/regione-piemonte/scriva/tree/main/scrivafoweb ) : Backend per la componente Angular di frontend scrivafowcl;
- [scrivaconswcl]( https://github.com/regione-piemonte/scriva/tree/main/scrivaconswcl ) : Client Web (Angular 11), front-end applicativo canale di consultazione;
- [scrivaconsweb]( https://github.com/regione-piemonte/scriva/tree/main/scrivaconsweb ) : Backend per la componente Angular di frontend scrivaconswcl;				;
- [scrivabesrv]( https://github.com/regione-piemonte/scriva/tree/main/scrivabesrv ) : Servizi di backend trasversali del prodotto scriva;
- [scrivaapisrv]( https://github.com/regione-piemonte/scriva/tree/main/scrivaapisrv ) : componente di esposizione API REST per il prodotto scriva.


In ciascuna di queste cartelle di componente si trovano ulteriori informazioni specifiche, incluso il BOM della componente di prodotto.

Nella directory [csi-lib]( https://github.com/regione-piemonte/scriva/csi-lib ) si trovano le librerie sviluppate da CSI-Piemonte con licenza OSS, come indicato nei BOM delle singole componenti, ed usate trasversalmente nel prodotto.

## Architettura Tecnologica

Le tecnologie adottate sono conformi agli attuali standard adottati da CSI per lo sviluppo del Sistema infermativo di Regione Piemonte, ed in particolare sono orientate alla possibilità di installare il prodotto sw su infrastruttura “a container”, orientata alle moderne architetture a “mini/microservizi”, prediligendo sostanzialmente gli strumenti open-source consolidati a livello internazionale (Linux, Java, Apache…); nel dettaglio tali pile prevedono:

- JDK 11
- WildFly - 17.0
- WS Apache 2.4
- DBMS Postgresql 12.4
- S.O. Linux CentOS 7
- Angular 9 e 11 (lato client web)
- Form.io (lato client web).

## Linguaggi di programmazione utilizzati

I principali linguaggi utilizzati sono:
•	Java v. 11
•	HTML5
•	Typescript/Javascript
•	XML/JSON
•	SQL

## DB di riferimento

A seguito di valutazione sull’utilizzo di DBMS open-source si è ritenuto che Postgresql garantisca adeguata robustezza e affidabilità tendo conto delle dimensioni previste per il DB e dei volumi annui gestiti.
La versione scelta è la 12.4, con possibilità di aggiornamento a versioni successive (15.x o 17.x). Inoltre deve essere configurata l'opzione PostGIS.

Per quanto riguarda la struttura del DB, si è optato per un “partizionamento” fra entità di carattere trasversale ed entità specifiche per ciascun adempimento. Tale strutturazione è coerente con la progettazione di piattaforme sw orientate ai "micro-servizi".

## Tecnologie framework e standard individuati
Le tecnologie individuate sono Open Source e lo "stack applicativo" utilizzato rispetta gli standard del SIRe Regione Piemonte. Si basa quindi sull’utilizzo di:

- JDK 11
- Angular 9.x ed 11.x
- Form.io
- librerie sviluppate da CSI e mantenute trasversalmente per la cooperazione applicativa


# Prerequisiti di sistema

Una istanza DBMS Postgresql (consigliata la verione 15) con utenza avente privilegi per la creazione tabelle ed altri oggetti DB  ed una ulteriore utenza separata non proprietaria dello schama, per l'esecuzione di istruzioni DML di Insert, Read, Update e Delete sui dati.

Una istanza di web server, consigliato apache web server ( https://httpd.apache.org/ ).\
Per il build è previsto l'uso di apache Ant (https://ant.apache.org/)

Infine, anche per quanto concerne l'autenticazione e la profilazione degli utenti del sistema, scriva è integrato con servizi trasversali del sistema informativo regionale ("Shibboleth"), di conseguenza per un utilizzo in un altro contesto occorre avere a disposizione servizi analoghi o integrare moduli opportuni che svolgano analoghe funzionalità.
 

# Installazione

Creare lo schema del DB, tramite gli script della componente scrivadb.
 
Configurare il datasource nel file application.properties 

Configurare i web server e definire gli opportuni Virtual Host e "location" - per utilizzare il protocollo https occorre munirsi di adeguati certificati SSL.

Nel caso si vogliano sfruttare le funzionalità di invio mail, occorre anche configurare un mail-server.


# Deployment

Dopo aver seguito le indicazioni del paragrafo relativo all'installazione, si può procedere al build dei pacchetti ed al deploy sull'infrastruttura prescelta.


# Versioning
Per la gestione del codice sorgente viene utilizzato Git, ma non vi sono vincoli per l'utilizzo di altri strumenti analoghi.\
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).


# Copyrights
© Copyright Regione Piemonte – 2025\


# License

SPDX-License-Identifier: EUPL-1.2-or-later .\
Questo software è distribuito con licenza EUPL-1.2 .\
Consultare il file LICENSE.txt per i dettagli sulla licenza.