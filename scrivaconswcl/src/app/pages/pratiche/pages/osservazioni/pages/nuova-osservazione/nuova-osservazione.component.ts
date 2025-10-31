/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Location } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { BaseComponent, Status, UtilityService, ValueMapperService } from '@app/core';
import { I18nService } from '@app/core/translate/public-api';
import {
  CompetenzaTerritorio,
  Pratica,
  TipologiaAllegato
} from '@app/pages/pratiche/model';
import {
  AllegatoInsert,
  AllegatoPost
} from '@app/pages/pratiche/model/allegato.model';
import {
  OsservazionePost,
  OsservazionePut
} from '@app/pages/pratiche/model/osservazione.model';
import { PraticheService } from '@app/pages/pratiche/services/pratiche.service';
import {
  AutocompleteInput,
  CheckboxInput,
  Divider,
  Form,
  TextareaInput
} from '@app/shared/form';
import { NotificationService } from '@app/shared/notification/notification.service';
import { LoadingService } from '@app/theme/layouts/loading.services';
import { forkJoin, of, throwError } from 'rxjs';
import { catchError, switchMap, takeUntil, tap } from 'rxjs/operators';
import { AllegatoFile } from '../../enums/allegato-file';
import {
  IstanzaOss,
  Osservazione
} from '@pages/osservazioni/interfaces/osservazione';
import { HelpService } from '@app/shared/services/help/help.service';
import { MessageService } from '@app/shared/services/message.service';
import { ConswebCodesMesseges } from '@app/core/enums/codes-messagges.enums';
import { NotificationModel } from '@shared/notification/model/notification.model';
import { NotificationIcons } from '@app/shared/notification/components/notification/utilities/notification.enum';

interface TipoAllegato {
  id_adempimento: number;
  estensione_allegato: string;
  des_estensione_allegato: string;
}

@Component({
  selector: 'app-nuova-osservazione',
  templateUrl: './nuova-osservazione.component.html',
  styleUrls: ['./nuova-osservazione.component.scss']
})
export class NuovaOsservazioneComponent
  extends BaseComponent
  implements OnInit
{
  codMaschera: string = 'PUBWEB.MSCR007P';

  @ViewChild('allegatoFile') allegatoFile: any;
  breadcrumbs = [];

  pratica: Pratica;
  competenzaPrincipale: CompetenzaTerritorio[];
  inserisciOssForm: Form;
  idOsservazione: number = 0;
  navigatedObs: Osservazione;
  osservazione: OsservazionePost;
  putBodyOsservazione: OsservazionePut | Osservazione;
  postBodyAllegatoForm: AllegatoPost;
  refreshList: boolean;
  tipiAllegati: TipoAllegato[];
  isSubmitted: boolean = false;
  canCreateObs: boolean = true;
  isBozza: boolean = true;
  proponente: string;

  notificationErFileNamingConfig: NotificationModel;
  notificationErTypeFileConfig: NotificationModel;
  notificationErFileObbligConfig: NotificationModel;
  notificationSuccInvioConfig: NotificationModel;

  constructor(
    private loadingService: LoadingService,
    private route: ActivatedRoute,
    private mapper: ValueMapperService,
    private utilityService: UtilityService,
    private praticheService: PraticheService,
    public notification: NotificationService,
    private conswebMessages: MessageService,
    private i18n: I18nService,
    private helpService: HelpService,
    private location: Location
  ) {
    super();
    this.loadingService.hide();
  }

  ngOnInit(): void {
    this.helpService.setCodMaschera(this.codMaschera);
    this.route.queryParams.subscribe((parameter) => {
      if (parameter.id) {
        this.idOsservazione = parameter.id;
      }
      if (parameter.obs) {
        this.navigatedObs = JSON.parse(atob(parameter.obs));

        this.canCreateObs =
          this.checkDataFineOsservazione(this.navigatedObs.istanza) &&
          this.checkIsBozza(this.navigatedObs);
        // this.isBozza = this.checkIsBozza(this.navigatedObs);
      }
    });
    this.route.data
      .pipe(
        catchError((e: HttpErrorResponse) => {
          this.loadingService.hide();
          return of(null);
        }),
        takeUntil(this.destroy$)
      )
      .subscribe((data: any) => {
        this.loadingService.hide();
        this.pratica = data.pratica[0];

        // handle proponente
        if (
          this.pratica.soggetto_istanza &&
          this.pratica.soggetto_istanza.length > 0
        ) {
          if (
            this.pratica.soggetto_istanza[0].tipo_soggetto.cod_tipo_soggetto ===
            'PF'
          ) {
            this.proponente = this.pratica.soggetto_istanza[0].cognome + ' ';
            this.pratica.soggetto_istanza[0].nome;
          } else {
            this.proponente = this.pratica.soggetto_istanza[0].denominazione;
          }
        }

        this._initBreadcrumbs();
        this.osservazione = {
          istanza: {
            id_istanza: this.pratica.id_istanza
          }
        };
        this.competenzaPrincipale = this.getCompetenzaPrincipale();
        this.getTipiAllegati().subscribe((list: TipoAllegato[]) => {
          this.tipiAllegati = list;
          this._initForm();
        });
      });
  }

  private _initBreadcrumbs() {
    const snapshot = this.route.snapshot.parent.parent.params;
    const ambito = this.mapper.getAmbitoFromAdempimento(
      snapshot['id_tipo_adempimento']
    );

    const fromSearch =
      this.route.snapshot.parent.parent.queryParams['fromSearch'];

    const fromMyObs =
      this.route.snapshot.parent.parent.queryParams['fromMyObs'];

    if (fromSearch) {
      this.breadcrumbs = [
        {
          label: 'PRACTICE.SEARCH.TITLE',
          href: '/procedimenti/ricerca'
        },
        {
          label: `${this.pratica.cod_pratica.replace('/', ' - ')}`,
          href: null
        }
      ];
      return;
    }

    if (fromMyObs) {
      this.breadcrumbs = [
        {
          label: 'OBSERVATIONS.TITLE',
          href: '/osservazioni'
        },
        {
          label: `${this.pratica.cod_pratica.replace('/', ' - ')}`,
          href: null
        }
      ];
      return;
    }

    this.breadcrumbs = [
      {
        label: `${ambito.des_ambito}`,
        href: `/ambito/${ambito.cod_ambito}`
      },
      {
        label: `${snapshot['id_tipo_adempimento']}`,
        href: `/procedimenti/${ambito.cod_ambito}/${snapshot['id_tipo_adempimento']}`
      },
      {
        label: `${this.pratica.cod_pratica.replace('/', ' - ')}`,
        href: null
      }
    ];
  }

  getCompetenzaPrincipale(): CompetenzaTerritorio[] {
    const competenzaPrincipale = this.pratica.competenza_territorio
      ? this.pratica.competenza_territorio.filter(
          (competenza) => competenza.flg_autorita_principale
        )
      : [];

    if (competenzaPrincipale.length === 0) {
      return this.pratica.competenza_territorio;
    }
    return competenzaPrincipale;
  }

  getTipiAllegati() {
    return this.utilityService.getTipiAllegatiByAdempimento(
      this.pratica.adempimento.cod_adempimento
    );
  }

  private _initForm() {
    this.inserisciOssForm = new Form({
      header: { show: false },
      controls: {
        categoria_allegato: new AutocompleteInput<string, string>({
          options: this.utilityService.getCategoriaAllegato(),
          label: 'OSSERVAZIONI.FIELDS.CATEGORIA_ALLEGATO',
          size: '12|12|6|4|4',
          placeholder: 'OSSERVAZIONI.FIELDS.CATEGORIA_ALLEGATO',
          required: true,
          readonly: !this.canCreateObs,
          prepopulate: true
        }),
        divider_1: new Divider({ size: '6|6|6|6|6' }),
        tipologia_allegato: new AutocompleteInput<string, string>({
          options: this.utilityService.getTipologieAllegatoSelect(
            this.pratica.adempimento.cod_adempimento
          ),
          label: 'OSSERVAZIONI.FIELDS.TIPOLOGIA_ALLEGATO',
          placeholder: 'OSSERVAZIONI.FIELDS.TIPOLOGIA_ALLEGATO',
          size: '12|12|6|4|4',
          required: true,
          readonly: !this.canCreateObs,
          valueChange: (value) => {
            if (!value) {
              return;
            }
            const flg_riservato: CheckboxInput = this.inserisciOssForm.get(
              'flg_riservato'
            ) as unknown as CheckboxInput;
            const tipologiaAllegato =
              this.utilityService.tipologieAllegato.find(
                (tipologia) =>
                  tipologia.tipologia_allegato.id_tipologia_allegato === +value
              );
            flg_riservato.setValue(tipologiaAllegato.flg_riservato);

            const note: TextareaInput = this.inserisciOssForm.get(
              'note'
            ) as unknown as TextareaInput;
            if (tipologiaAllegato.flg_nota) {
              note.setValidators([
                Validators.required,
                Validators.maxLength(500)
              ]);
              note.required = true;
              note.updateValueAndValidity();
            } else {
              note.setValidators([Validators.maxLength(500)]);
              note.required = false;
              note.updateValueAndValidity();
            }
          }
        }),
        flg_riservato: new CheckboxInput({
          label: 'OSSERVAZIONI.FIELDS.FLG_PUBBLICO',
          size: '6|6|6|6|6',
          readonly: true
        }),
        note: new TextareaInput({
          label: 'OSSERVAZIONI.FIELDS.NOTE',
          placeholder: 'OSSERVAZIONI.FIELDS.NOTE',
          size: '12|12|12|12|12',
          maxLenght: 500,
          readonly: !this.canCreateObs
        })
      }
    });
  }

  clearForm() {
    this.inserisciOssForm.reset();
  }

  async onFileChange(event) {
    if (!this.canCreateObs) {
      return;
    }
    this.loadingService.show();
    if (this.idOsservazione) {
      this.insertAllegati(event);
    } else {
      this.praticheService
        .postOsservazioni(this.osservazione)
        .pipe(
          catchError((e: HttpErrorResponse) => {
            this.loadingService.hide();
            this.notification.error({
              title: this.i18n.translate(
                'OSSERVAZIONI.NOTIFICATION.ERROR.CREATE.TITLE'
              ),
              text:
                e.error.title ||
                this.i18n.translate(
                  'OSSERVAZIONI.NOTIFICATION.ERROR.CREATE.TEXT'
                )
            });
            return throwError(e);
          }),
          takeUntil(this.destroy$)
        )
        .subscribe((response: OsservazionePut) => {
          this.putBodyOsservazione = response;
          this.idOsservazione = response.id_istanza_osservazione;
          this.trackEvent('INS_OSS');
          this.insertAllegati(event);
        });
    }
  }

  trackEvent(ev: string, uuid_richiesta?: string, tipo_richiesta?: string) {
    this.utilityService
      .trackEvent(
        this.osservazione.istanza.id_istanza + '',
        ev,
        uuid_richiesta,
        tipo_richiesta
      )
      .pipe(
        catchError((e: HttpErrorResponse) => {
          this.loadingService.hide();
          // this.notification.error({
          //   title: this.i18n.translate(
          //     'OSSERVAZIONI.NOTIFICATION.ERROR.TRACK.TITLE'
          //   ),
          //   text: this.i18n.translate(
          //     'OSSERVAZIONI.NOTIFICATION.ERROR.TRACK.TEXT'
          //   )
          // });
          return throwError(e);
        }),
        takeUntil(this.destroy$)
      )
      .subscribe();
  }

  onPutOsservazione() {
    let modOssExist: boolean = false;

    this.praticheService
      .getCheckAllegati(this.idOsservazione)
      .subscribe((res) => {
        res.forEach((r) => {
          //verifico la presenza di tutti gli obbligatori
          if (r?.tipologia_allegato.cod_tipologia_allegato == 'MOD_OSS') {
            modOssExist = true;
          }
        });

        if (modOssExist) {
          if (this.navigatedObs) {
            this.putBodyOsservazione = {
              ...this.putBodyOsservazione,
              ...this.navigatedObs
            };
          }
          this.putBodyOsservazione.stato_osservazione = {
            cod_stato_osservazione: 'Inviata'
          };

          this.praticheService
            .sendOsservazione(this.putBodyOsservazione)
            .pipe(
              catchError((e: HttpErrorResponse) => {
                this.loadingService.hide();
                this.isSubmitted = false;

                this.notification.error({
                  title: this.i18n.translate(
                    'OSSERVAZIONI.NOTIFICATION.ERROR.EDIT.TITLE'
                  ),
                  text: this.i18n.translate(
                    'OSSERVAZIONI.NOTIFICATION.ERROR.EDIT.TEXT'
                  )
                });
                return throwError(e);
              }),
              takeUntil(this.destroy$)
            )
            .subscribe(() => {
              //traccio l'evento
              this.trackEvent('INVIO_OSS', this.putBodyOsservazione.gestUID ,'OSSERVAZIONE');

              this.notificationSuccInvioConfig = {
                title: this.i18n.translate(
                  'OSSERVAZIONI.NOTIFICATION.SUCCESS.EDIT.TITLE'
                ),
                text: this.i18n.translate(
                  'OSSERVAZIONI.NOTIFICATION.SUCCESS.EDIT.TEXT'
                ), // ConswebCodesMesseges.P013
                status: Status.success,
                icon: NotificationIcons.SUCCESS,
                dismissable: false,
                time: 7000
              };
              this.canCreateObs = false;
              this._initForm();
            });
        } else {
          const desAlert = this.conswebMessages.getDesMessaggio(
            ConswebCodesMesseges.E074
          );
          this.notificationErFileObbligConfig = {
            title: this.i18n.translate(
              'OSSERVAZIONI.NOTIFICATION.ERROR.EDIT.TITLE'
            ),
            text: desAlert,
            status: Status.error,
            icon: NotificationIcons.ERROR,
            dismissable: false
          };
        }
      });
  }

  checkIsBozza(obs: Osservazione) {
    return (
      obs.stato_osservazione.cod_stato_osservazione.toLowerCase() === 'bozza'
    );
  }

  checkDataFineOsservazione(istanza: IstanzaOss) {
    return !(
      !istanza.data_fine_osservazione ||
      new Date(
        new Date(istanza.data_fine_osservazione).setHours(0, 0, 0, 0)
      ).getTime() < new Date(new Date().setHours(0, 0, 0, 0)).getTime()
    );
  }

  async insertAllegati(event) {
    this.refreshList = false;
    const obs = [];
    for (let i = 0; i < event.target.files.length; i++) {
      const file: File = event.target.files[i];
      const isSizeOk = this.checkSize(file);
      // TODO controllare valore da BE
      // const isTypeOk = true
      const isTypeOk = this.checkType(file);

      if (isSizeOk && isTypeOk) {
        const tipologia_allegato = this.utilityService.tipologieAllegato.find(
          (tipologia) =>
            tipologia.tipologia_allegato.id_tipologia_allegato ===
            this.inserisciOssForm.value.tipologia_allegato
        ).tipologia_allegato;
        const metadata = {
          id_istanza_osservazione: this.idOsservazione,
          id_istanza: this.pratica.id_istanza,
          tipologia_allegato,
          // flg_riservato: this.inserisciOssForm.value.flg_riservato,
          note: this.inserisciOssForm.value.note,
          classe_allegato: {
            id_classe_allegato: 5,
            cod_classe_allegato: 'OSSERVAZIONI'
          }
        };
        obs.push(
          this.praticheService.postAllegati(metadata, file).pipe(
            catchError(({ error }: HttpErrorResponse) => {
              this.clearInputFile(this.allegatoFile.nativeElement);
              this.loadingService.hide();

              const desAlert = this.conswebMessages.getDesMessaggio(
                ConswebCodesMesseges.E021
              );
              this.notificationErFileNamingConfig = {
                title: this.i18n.translate(
                  'OSSERVAZIONI.NOTIFICATION.ERROR.ALLEGATI.TITLE'
                ),
                text: desAlert,
                status: Status.warning,
                icon: NotificationIcons.WARNING,
                dismissable: true,
                time: 7000
              };
              return throwError(error);
            }),
            takeUntil(this.destroy$)
          )
        );
      } else {
        this.loadingService.hide();

        this.notificationErFileNamingConfig = {
          title: this.i18n.translate(
            'OSSERVAZIONI.NOTIFICATION.ERROR.ALLEGATI.TITLE'
          ),
          text: this.i18n.translate(
            'OSSERVAZIONI.ALLEGATI.NOTIFICATION.ERROR.DIMENSIONE.TEXT'
          ),
          status: Status.warning,
          icon: NotificationIcons.WARNING,
          dismissable: true,
          time: 7000
        };

        /*         this.notification.error({
          title: this.i18n.translate(
            'OSSERVAZIONI.ALLEGATI.NOTIFICATION.ERROR.DIMENSIONE.TITLE'
          ),
          text: this.i18n.translate(
            'OSSERVAZIONI.ALLEGATI.NOTIFICATION.ERROR.DIMENSIONE.TEXT' //code non presente a db
          )
        }); */
      }
    }
    forkJoin(obs).subscribe(() => {
      this.refreshList = true;
      this.clearErrorAlertObbligatori();

      this.clearInputFile(this.allegatoFile.nativeElement);
    });
  }

  clearErrorAlertObbligatori() {
    if (!this.notificationErFileObbligConfig) {
      return;
    }

    let modOssExist = false;
    this.praticheService
      .getCheckAllegati(this.idOsservazione)
      .subscribe((res) => {
        res.forEach((r) => {
          //verifico la presenza di tutti gli obbligatori
          if (r?.tipologia_allegato.cod_tipologia_allegato == 'MOD_OSS') {
            modOssExist = true;
          }
        });

        if (modOssExist) {
          this.notificationErFileObbligConfig = null;
        }
      });
  }

  clearInputFile(f) {
    if (f.value) {
      try {
        f.value = '';
      } catch (err) {
        console.error('Error during cleaning input file');
      }
    }
  }

  private checkType(file: File) {
    return !!this.tipiAllegati.find((tipo) =>
      file.name.endsWith(tipo.estensione_allegato)
    );
  }

  private checkSize(file: File) {
    return file.size / 1024 / 1024 <= AllegatoFile.MaxDimensionMb;
  }

  toBase64(file: File) {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result);
      reader.onerror = (error) => reject(error);
    });
  }

  downloadPrivacy() {
    this.praticheService
      .downloadModuloPrivacy(this.pratica.id_istanza)
      .pipe(
        catchError((e: HttpErrorResponse) => {
          this.loadingService.hide();
          this.notification.error({
            title: this.i18n.translate(
              'OSSERVAZIONI.ALLEGATI.NOTIFICATION.ERROR.DOWNLOAD.TITLE'
            ),
            text: this.i18n.translate(
              'OSSERVAZIONI.ALLEGATI.NOTIFICATION.ERROR.DOWNLOAD.TEXT'
            )
          });
          return throwError(e);
        }),
        takeUntil(this.destroy$)
      )
      .subscribe();
  }

  downloadOsservazione() {
    this.praticheService
      .downloadModuloOsservazioni(this.pratica.id_istanza)
      .pipe(
        catchError((e: HttpErrorResponse) => {
          this.loadingService.hide();
          this.notification.error({
            title: this.i18n.translate(
              'OSSERVAZIONI.ALLEGATI.NOTIFICATION.ERROR.DOWNLOAD.TITLE'
            ),
            text: this.i18n.translate(
              'OSSERVAZIONI.ALLEGATI.NOTIFICATION.ERROR.DOWNLOAD.TEXT'
            )
          });
          return throwError(e);
        }),
        takeUntil(this.destroy$)
      )
      .subscribe();
  }
}
