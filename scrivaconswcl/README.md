

# SCRIVA - Componente SCRIVACONSWCL

## Descrizione

SCRIVACONSWCL è una componente front-end Angular del prodotto SCRIVA, di consultazione per il cittadino, progettata come controparte della web application SCRIVACONSWEB. Costituisce l’interfaccia utente principale del sistema.

Il progetto è stato generato con [Angular CLI](https://github.com/angular/angular-cli) versione 11.

---

## Prerequisiti

- Node.js (versione consigliata ≥ 12)
- npm (versione consigliata ≥ 6)
- Angular CLI (inclusa come dipendenza di sviluppo)
- SCRIVACONSWEB (componente back-end)
- Servizio di autenticazione Shibboleth configurato

---

## Installazione

1. Clona il repository:
	 ```sh
	 git clone <URL del repository>
	 ```
2. Installa le dipendenze:
	 ```sh
	 npm install
	 ```
3. Configura i file di ambiente in `buildfiles/environment.<env>.ts` e aggiorna le entry in `angular.json` per ogni nuovo ambiente.

---

## Configurazione

Le principali impostazioni da configurare sono:

- `production`: ambiente di produzione o pre-produzione
- `ambiente`: nome dell’ambiente
- `shibbolethAuthentication`: abilitazione integrazione Shibboleth
- `publicPath`: URL pubblica dell’applicazione
- `appBaseHref`: valore del tag `<base>`
- `beServerPrefix`: prefisso per le URL dei servizi BackEnd
- `beService`: base URL del servizio BackEnd
- `shibbolethSSOLogoutURL`: URL di logout SSO
- `onAppExitURL`: URL di reindirizzamento all’uscita

---

## Avvio in locale

Per avviare l’ambiente di sviluppo:
```sh
ng serve
```
L’applicazione sarà disponibile su [http://localhost:4200/](http://localhost:4200/). Il reload automatico è abilitato per ogni modifica ai sorgenti.

Per generare una nuova componente:
```sh
ng generate component <nome-componente>
```
Consulta la documentazione Angular per ulteriori dettagli.

---

## Build

Per generare gli artefatti di produzione:
```sh
ng build --prod
```
I file risultanti saranno disponibili nella cartella `dist/`.

---

## Esecuzione dei test

- Test unitari:  
	```sh
	ng test
	```
	Eseguiti tramite [Karma](https://karma-runner.github.io).

- Test end-to-end:  
	```sh
	ng e2e
	```
	Eseguiti tramite [Protractor](http://www.protractortest.org/).

La componente è stata sottoposta a test di vulnerabilità prima del rilascio.

---

## Deployment

La componente Angular viene distribuita tramite un web server front-end, tipicamente configurando:

- Un virtual host e una location dedicata (es. `scriva.aaa.bbb.it/scrivafowcl`)
- Protezione Shibboleth sulla location
- Deploy dei file Angular in una posizione accessibile dal web server

---

## Versionamento

Il software segue la [Semantic Versioning](http://semver.org).

---

## Copyright

© Copyright Regione Piemonte – 2025  
Vedi anche il file `Copyrights.txt`.

---

## Licenza

Il software è distribuito secondo la licenza EUPL-1.2 o successive.  
SPDX-License-Identifier: EUPL-1.2-or-later  
Consulta il file `EUPL v1_2 IT-LICENSE.txt` per i dettagli.

---

## Risorse e supporto

Per ulteriori informazioni su Angular CLI:
- Comando: `ng help`
- [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md)
