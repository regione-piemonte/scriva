/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { AppConfigService } from "src/app/shared/services";



@Injectable({ providedIn: 'root' })
export class HttpErrorService {
    // chiave per registrare errore nella sessionStorage
    private ERRORSERVICEKEY = 'error302';
    // path di routing per accesso negato
    private ROUTINGACCESSONEGATO = '/accesso-negato';
    // base url delle api del nostro BE 
    private _configBeUrl: string;

    constructor(
        private _appConfig: AppConfigService,
        private _router: Router
    ) {
        /** recupero la base path delle chiamate api */
        this._configBeUrl = this._appConfig.getBEUrl();
    }
    
    /**
     * Gestione errore che ci viene inoltrato da intercptor
     * @param err HttpErrorResponse
     */
    handleError(err: HttpErrorResponse) {
        // Verifico se la riposta è un 302 redirect (login scaduta su sso csi piemente )
        if(this._checkIs302(err)) {
            // verifico se ho errore in sessionStorage
            if(sessionStorage.getItem(this.ERRORSERVICEKEY)){
                // navigo verso la pagina opportuna per non incorrere in loop di reload
                this._router.navigate([this.ROUTINGACCESSONEGATO]);
            }else {
                // salvo nella session storage item in modo opportuno
                sessionStorage.setItem(this.ERRORSERVICEKEY,'1');
                // richiamo il metodo privato che gestisce la reload
                this._reload();
            }
        }
    }

    /**
     * Funzione pubblica che resetta item che ci dice che abbiamo appena effettuato una reload da 302
     */
    resetError() {
        // rimuovo nella session storage item in modo opportuno
        sessionStorage.removeItem(this.ERRORSERVICEKEY);
    }

    /**
     * Metodo che verifica in modo indiretto se la chiamata api ha effettuato una redirect
     * (login scaduta su sso csi piemente Shibbolet)
     * @param err HttpErrorResponse
     * @returns boolean
     */
    private _checkIs302(err: HttpErrorResponse): boolean {
        /** 
         * Se la url di errore non è contenuta non contiene il path delle api
         * e non è un 500
         * deduco che e' un 302
         */
        if(
            err.url.indexOf(this._configBeUrl) == -1 && 
            err.status !== 500 
        ) {
            return true;
        }
        return false;
    }

    /**
     *  Metodo che esegue la reload della pagina corrente del browser
     */
    private _reload() {
        window.location.reload();
    }

}