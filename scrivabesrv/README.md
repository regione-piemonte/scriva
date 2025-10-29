# Prodotto
SCRIVA componente SCRIVABESRV (componente di backend trasversale)


# Descrizione della componente
Questa cartella rappresenta una "unità di installazione" corrispondente alla componente "logica" di backend tasversale che si integra con altri servizi del sistema informativo regionale, espone a sua volta servizi di integrazione  verso altre compnenti applicative del prodotto o di altri applicativi o altre "piattaforme" (all'interno del sistema informativo regionale), ed accede al DB di SCRIVA; in particolare espone API utili all'integrazione con il sistema di GIS Web Editing.

In generale, è predisposta per l'esposizione di API REST in modalità "point to point".

Questa componente si basa su:
- Java 11 --> AdoptOpenJDK 11
- Application server WILDFLY 17
- Librerie per la realizzazione della persistenza su DB

# Configurazioni iniziali
Fondamentale avere a disposzione un ambiente (Virtual Machine o "container") in cui è installato un JDK ver.11, preferibilmente Adopt OpenJDK.
Occorre poi avere a disposizione un'installazione dell'application server WildFly, ver. 17 o sup.
Da un punto di vista generale, nella fase iniziale occorre adattare i file di properties alla propria configurazione.
I file di properties, suddivisi per ambiente, saranno usati per il build tramite AMT ed Ivy.
	

# Getting Started
Una volta prelevata e portata in locale dal repository la componente ("git clone"), procedere con la modifica dei file di configurazione in base al proprio ambiente di deploy e quindi procedere al build.

In generale, ogni integrazione con moduli di terze parti deve corrispondere ad un modulo separato in `/integ-<name>`.

Per ogni nuova integrazione, ci deve essere una corrispondenza dei moduli nelle directory `/ear` e `/tar`.

Le indicazioni principali riportate nel seguito sono quelle seguite in fase di realizzazione della componente.
## implementazoni di default e modalità di sviluppo ##

### sviluppo contract first ###
Il progetto è impostato per lo sviluppo secondo la modalità contract-first. per quanto riguarda l'esposizione di API REST:
* nella cartella ```src/main/resources/META-INF``` sono presenti i file derivati dallo scheletro base "openapi.yaml" basati sulla specifica openapi 3, contenenti le definizioni di base e nei quali è possibile aggiungere le definizioni delle risorse specfiche dell'applicativo
* si prevede l'utilizzo del plugin swagger per la generazione delle sole interfacce JAX-RS a partire da tale file di specifiche. La generazione avviene in fase di compilazione 
* il generatore non genera invece le classi di implementazione delle API, che devono essere definite manualmente ed implementare le interfacce JAX-RS (vedere esempio ```StatusApiServiceImpl```)

### altre implementazioni predefinite ###

Sono predefiniti:
* il filtro per il recepimento dell'header Shibboleth (se autenticazione prevista - di default è disattivato: se necessario scommentare la annotation ```@Provider```)
* i filtri per la protezione cross site request forgery (XSRF)

### api strumentali ###

Il progetto è impostato per esporre una implementazione minimale di default delle API strumentali per:
* health check:
  * liveness: ```/q/health/live```
  * readyness: ```/q/health/ready```
* metrics:  ```/q/metrics```

E' possibile estendere l'implementazione di default a seconda delle esigenze specifiche.

# Prerequisiti di sistema

Nella directory "csi-lib" sono disponibili le librerie sviluppate da CSI e rese disponibili con le licenze indicate nel BOM.csv .

Occorre inoltre prevedere le opportune sostituzioni dei servizi esterni richiamati.

Per il "build" si è previsto di utilizzare Apache ANT.

# Installazione - Deployment

Installare il file "ear" generato con il build sul proprio ambiente WildFly.

# Esecuzione dei test

Questa componente è stata sottoposta a vulnerability assessment.

# Versioning

Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

# Copyrights

© Copyright Regione Piemonte – 2025\

Questo stesso elenco dei titolari del software è anche riportato in Copyrights.txt .

# License
Il prodotto software è sottoposto alla licenza EUPL-1.2 o versioni successive.
SPDX-License-Identifier: EUPL-1.2-or-later

