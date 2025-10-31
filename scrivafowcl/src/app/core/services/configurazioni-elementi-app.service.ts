import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { clone } from 'lodash';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {
  ConfigurazioneElementoAppVo,
  IConfigurazioneElementeAppVo,
  toListConfigurazioniElementiAppVo,
} from '../vo/configurazioni-elementi-app-vo';
import { AppConfigService } from 'src/app/shared/services';

@Injectable({ providedIn: 'root' })
export class ConfigurazioniElementiAppService {
  /** Costante per il path per: /configurazioni-app. */
  private PATH_CHIAVI_CONFIGURAZIONI = '/configurazioni-app';

  /** ConfigurazioniElementiAppVo con i dati scaricati dal servizio. */
  private _configurazioniEA: ConfigurazioneElementoAppVo[];

  /**
   * Costruttore.
   */
  constructor(private config: AppConfigService, private _http: HttpClient) {}

  /**
   * ################################################
   * FUNZIONI PER LA GESTIONE DEGLI ELEMENTI DELL'APP
   * ################################################
   */

  /**
   * Funzione che recupera le configurazioni per gli elementi applicativi.
   * Le configurazioni contengono le informazioni da visualizzare per gli elementi di pagina (label, descrizioni, etc...).
   * @returns Observable<ConfigurazioniElementiAppVo[]> con i dati restituiti dal servizio.
   */
  getConfigurazioniElementiApp(): Observable<ConfigurazioneElementoAppVo[]> {
    // Costruisco l'url per la chiamata
    const url = this.config.appUrl(this.PATH_CHIAVI_CONFIGURAZIONI);

    // Effettuo la chiamata al servizio
    return this._http.get<IConfigurazioneElementeAppVo[]>(url).pipe(
      map((configurazioniEA: IConfigurazioneElementeAppVo[]) => {
        // Assegno localmente i dati
        this._configurazioniEA =
          toListConfigurazioniElementiAppVo(configurazioniEA);
        // Ritorno la lista di oggetti convertita
        return this._configurazioniEA;
        // #
      })
    );
  }

  /**
   * Funzione che cerca, all'interno della configurazione degli elementi, un oggetto ConfigurazioniElementiAppVo.
   * @param identificativo string che definisce l'identificativo per la ricerca all'interno della lista delle configurazioni.
   * @returns ConfigurazioniElementiAppVo trovato o undefined se non esiste.
   */
  getConfigurazioneElementoApp(
    identificativo: string
  ): ConfigurazioneElementoAppVo {
    // Controllo di esistenza
    if (!identificativo) {
      // Nulla da cercare
      return undefined;
    }

    // Recupero la configurazione
    const configurazioniEA: ConfigurazioneElementoAppVo[] =
      this.configurazioniElementiApp;

    // Cerco l'oggetto e lo restituisco
    return configurazioniEA?.find((cEA: ConfigurazioneElementoAppVo) => {
      // Cerco per identificativo
      return cEA.identificativo == identificativo;
    });
  }

  /**
   * ################
   * GETTER DI COMODO
   * ################
   */

  /**
   * Getter per configurazioniElementiApp.
   * @returns ConfigurazioniElementiAppVo[] con il valore di configurazioniElementiApp.
   */
  get configurazioniElementiApp(): ConfigurazioneElementoAppVo[] {
    // Ritorno una copia dei dati di configurazione
    return clone(this._configurazioniEA);
  }
}
