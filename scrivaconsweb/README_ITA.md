# Prodotto
SCRIVA componente SCRIVACONSWEB

# Descrizione della componente
Questa componente è una web application che segue il paradigma "Single Page Application (SPA)", espone servizi REST alla componente SCRIVACONSWCL (Angular) e si connette alla componente SCRIVABESRV per l'accesso ai dati (operazioni CRUD).

Al fine di permettere la fruizione delle API REST da parte del webclient angular, deve essere "esposta" su internet.
Si collega ad altri servizi del Sistema Informativo Regionale tramite la componente di backend scrivabesrv.

I servizi fruiti usano protocollo HTTP, e sono per lo più REST API.

# Configurazioni iniziali
Da un punto di vista generale, nella fase iniziale occorre adattare i file di properties nella directory buildfiles alla propria configurazione. 


# Getting Started
Una volta prelevata e portata in locale dal repository la componente ("git clone"), procedere con la modifica dei file di configurazione in base al proprio ambiente di deploy e quindi procedere al build.

# Prerequisiti di sistema
Occorre per prima cosa predisporre il DB Schema utilizzato da questa componente tramite la componente di beckend trasversae, e popolarlo con i dati iniziali: si deve quindi prima aver completato installazione e configurazione della componente scrivadb.

Nella directory "csi-lib" sono disponibili le librerie sviluppate da CSI e rese disponibili con le licenze indicate nel BOM.csv .

Occorre inoltre prevedere le opportune sostituzioni dei servizi esterni richiamati.

Per il "build" si è previsto di utilizzare Apache ANT. 

# Installazione - Deployment

Installare il file "ear" generato con il build sul proprio ambiente WildFly.

# Esecuzione dei test

Questa componente è stata sottoposta a vulnerability assessment prima del rilascio.

# Versioning

Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

# Copyrights

© Copyright Regione Piemonte – 2025

Questo stesso elenco dei titolari del software è anche riportato in Copyrights.txt .

# License
Il prodotto software è sottoposto alla licenza EUPL-1.2 o versioni successive.
SPDX-License-Identifier: EUPL-1.2-or-later

