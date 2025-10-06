# SCRIVA

> **Sistema delle Scrivanie del Richiedente e del Funzionario per i procedimenti ambientali**

---

## Descrizione del prodotto

SCRIVA nasce dall'esigenza di disporre di una soluzione trasversale per la gestione dei procedimenti ambientali, coerente con il modello generale per l’ICT della PA italiana promosso da AgID. Risponde a una duplice esigenza:

- Offrire un punto di vista inter-tematico, raccordando (in ottica di user centered design) i procedimenti dei diversi business afferenti allo stesso richiedente o intervento/opera/attività sul territorio.
- Gestire trasversalmente logiche comuni rispetto a funzioni di:
  - **Scrivania**: query & reporting, gestione stati/eventi, bacheca notifiche, scadenziario
  - **Processo**: deleghe/procure, anagrafiche soggetti/oggetti
  - **Integrazione**: georeferenziazione, protocollazione & gestione documentale, pagamenti
  - **Interazione**: tra stakeholder e sistemi coinvolti, permettendo alle PA di dialogare su un procedimento all’interno della soluzione o ingaggiando sistemi esterni (es: SUAP)

SCRIVA semplifica, dematerializza e armonizza i servizi pubblici regionali relativi alle procedure ambientali rivolti a imprese, professionisti e cittadini, attraverso una nuova modalità di interazione e un maggiore livello di integrazione dei servizi regionali con la PA.

### Componenti funzionali

- **Front Office**: scrivania del Richiedente (privato, persona fisica o giuridica, anche PA) che interagisce con la PA per la presentazione di un’istanza
- **Back Office**: scrivania del Funzionario (soggetto pubblico destinatario dell’istanza) che prende in carico la pratica e la gestisce coinvolgendo gli Enti Terzi

L’applicativo prevede moduli di front-end web che interagiscono tramite API con moduli di back-end dove risiede la logica di business e che accedono al DB.

SCRIVA segue il paradigma **SPA – Single Page Application**: la componente di interfaccia (Angular o Form.io) ha una corrispondente componente di back-end (Java) che espone API REST e accede al DB.

### Struttura del prodotto

- [scrivadb](https://github.com/regione-piemonte/scriva/tree/main/scrivadb): script per la creazione del DB (PostgreSQL)
- [scrivafowcl](https://github.com/regione-piemonte/scriva/tree/main/scrivafowcl): Client Web (Angular 9), front-end applicativo canale "autenticato"
- [scrivafoweb](https://github.com/regione-piemonte/scriva/tree/main/scrivafoweb): Backend per la componente Angular di frontend scrivafowcl
- [scrivaconswcl](https://github.com/regione-piemonte/scriva/tree/main/scrivaconswcl): Client Web (Angular 11), front-end applicativo canale di consultazione
- [scrivaconsweb](https://github.com/regione-piemonte/scriva/tree/main/scrivaconsweb): Backend per la componente Angular di frontend scrivaconswcl
- [scrivabesrv](https://github.com/regione-piemonte/scriva/tree/main/scrivabesrv): Servizi di backend trasversali del prodotto scriva
- [scrivaapisrv](https://github.com/regione-piemonte/scriva/tree/main/scrivaapisrv): componente di esposizione API REST per il prodotto scriva

In ciascuna di queste cartelle sono presenti ulteriori informazioni specifiche, incluso il BOM della componente.

Nella directory [csi-lib](https://github.com/regione-piemonte/scriva/csi-lib) si trovano le librerie sviluppate da CSI-Piemonte con licenza OSS, come indicato nei BOM delle singole componenti, usate trasversalmente nel prodotto.

---

## Architettura tecnologica

Le tecnologie adottate sono conformi agli standard CSI per lo sviluppo del Sistema Informativo di Regione Piemonte e orientate all’installazione su infrastruttura "a container" e architetture a "mini/microservizi". Si prediligono strumenti open-source consolidati a livello internazionale:

- **JDK 11**
- **WildFly 17.0**
- **WS Apache 2.4**
- **DBMS PostgreSQL 12.4**
- **Linux CentOS 7**
- **Angular 9 e 11** (client web)
- **Form.io** (client web)

---

## Linguaggi di programmazione utilizzati

- Java 11
- HTML5
- Typescript/Javascript
- XML/JSON
- SQL

---

## Database di riferimento

Si utilizza **PostgreSQL** (consigliata la versione 15), che garantisce robustezza e affidabilità. Deve essere configurata l'opzione **PostGIS**.

La struttura del DB prevede un partizionamento tra entità trasversali e specifiche per ciascun adempimento, coerente con piattaforme orientate ai micro-servizi.

---

## Tecnologie, framework e standard

Lo stack applicativo rispetta gli standard SIRe Regione Piemonte e si basa su:

- JDK 11
- Angular 9.x e 11.x
- Form.io
- Librerie CSI per la cooperazione applicativa

---

## Prerequisiti di sistema

1. **DBMS PostgreSQL** (consigliata versione 15) con:
   - utenza con privilegi per la creazione di tabelle e oggetti DB
   - utenza separata non proprietaria dello schema, per operazioni DML (Insert, Read, Update, Delete)
2. **Web server** (consigliato [Apache HTTP Server](https://httpd.apache.org/))
3. **Build**: [Apache Ant](https://ant.apache.org/)
4. **Autenticazione**: integrazione con servizi trasversali regionali (es. "Shibboleth"). Per altri contesti, occorre disporre di servizi analoghi o integrare moduli equivalenti.

---

## Installazione

1. Creare lo schema del DB tramite gli script della componente `scrivadb`.
2. Configurare il datasource nel file `application.properties`.
3. Configurare i web server e definire Virtual Host e location. Per HTTPS occorrono certificati SSL adeguati.
4. Per l'invio mail, configurare un mail-server.

---

## Deployment

Dopo aver seguito le indicazioni dell'installazione, procedere al build dei pacchetti e al deploy sull'infrastruttura prescelta.

---

## Versioning

- Per la gestione del codice sorgente viene utilizzato **Git** (nessun vincolo su altri strumenti analoghi)
- Per il versionamento del software si usa la tecnica [Semantic Versioning](http://semver.org)

---

## Copyright

© Regione Piemonte – 2025

---

## License

SPDX-License-Identifier: EUPL-1.2-or-later

Questo software è distribuito con licenza **EUPL-1.2**.  
Consultare il file `LICENSE.txt` per i dettagli sulla licenza.