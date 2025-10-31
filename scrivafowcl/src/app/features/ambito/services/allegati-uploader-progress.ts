/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Injectable } from '@angular/core';
import { Allegato } from '../models';
import { AppConfigService, MessageService } from 'src/app/shared/services';
import { NgxSpinnerService } from 'ngx-spinner';
import { AllegatiService } from './allegati.service';
import { HttpClient } from '@angular/common/http';
import { AllegatiIntegrazioniService } from './allegati-integrazioni.service';
import { AllegatiUploadOptions } from '../models/allegati/allegati-upload-options';
import { Observable, Subject, Subscription, forkJoin, from, of, timer } from 'rxjs';
import { catchError, concatMap, delay, tap } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class AllegatiUploaderProgressService extends AllegatiService {
  private _allegatiUploadOptions: AllegatiUploadOptions;
  // default per il progress
  private _allegatiUploadOptionsDefault: Partial<AllegatiUploadOptions> = {
    enableProgress: true,
    enableProgressMultipleUpload: true,
    maxSizeFileSmall: 1048576,  // 1MEGA
    minProgressMega: 7,
    secondsProgressForMega: 2.3,
    maxProgressMegaSmall: 1,
    secondsProgressForMegaSmall: 12,
    parallelFileSmall: 3,
    timerMilliseconds: 1500,
    maxPercentProgressPending: 95
  };
  private _status$: Subject<any> = new Subject<any>();
  /** Subscription per il recupero del progress temporizzato */
  private progressTimer$: Subscription;
  private _progress$: Subject<ProgressFile[]> = new Subject<ProgressFile[]>();
  private _progress: ProgressFile[] = [];

  constructor(
    private allegatiIntegrazioniService: AllegatiIntegrazioniService,
    private messageService: MessageService,
    private spinner: NgxSpinnerService,
    protected http: HttpClient, 
    protected config: AppConfigService
  ) {
    super(http,config);
  }

  /**
   * Funzione che recupera obervable dello status per istanza corrente
  */
  getStatus(): Observable<any> {
    return this._status$.asObservable();
  }

  /**
   * Funzione che recupera obervable dei progress per istanza corrente
  */
  getProgress(): Observable<ProgressFile[]> {
    return this._progress$.asObservable();
  }

  /**
   * Emetto lo stato azione verso chi è in ascolto
   * @param action string
   */
  private _emitStatus(action: string) {
    const statusUpload: any = {
      idIstanza: this.allegatiUploadOptions.idIstanza,
      action: action
    };
    this._status$.next(statusUpload);
  }

  // setter per opzioni upload corrente
  set allegatiUploadOptions(allegatiUploadOptions: AllegatiUploadOptions) {
    this._allegatiUploadOptions = {...this._allegatiUploadOptionsDefault, ...allegatiUploadOptions};
  }

  // getter per opzioni upload corrente
  get allegatiUploadOptions(): AllegatiUploadOptions {
    return this._allegatiUploadOptions;
  }

  /**
   * Controlla se si mostra la progress
   * @param files array di files
   * @param totalFileSize somma totale size dei files
   * @returns boolean
   */
  isProgress(files,totalFileSize):boolean{
    if(!this.allegatiUploadOptions.enableProgress) {
      return false;
    }

    if(totalFileSize/1048576 >=  this.allegatiUploadOptions.minProgressMega) {
      return true;
    }
    if(this.allegatiUploadOptions.enableProgressMultipleUpload && files.length>1) {
      return true;
    }
    return false;
  }

  inserisciAllegato(files) {    
    if (files?.length > 0) {
      files = Array.from(files);

      let totalFileSize = 0;
      files.forEach((file) => (totalFileSize += file.size));
      if (totalFileSize / 1048576 > this.allegatiUploadOptions.maxFileSize) {
        this.messageService.showMessage(
          'I010',
          this.allegatiUploadOptions.targetMessage,
          true
        );
        return;
      }
      
      const showProgress: boolean = this.isProgress(files,totalFileSize);
      if(!showProgress) {
        this.spinner.show();
      }
      
      // costruisco il set di metadata che servono per uploadare il file
      const data: Partial<Allegato> = {
        id_istanza: this.allegatiUploadOptions.idIstanza,
        tipologia_allegato: this.allegatiUploadOptions.tipologiaSelezionata.tipologia_allegato,
        flg_riservato: this.allegatiUploadOptions.flgRiservato,
        nome_allegato: '',
        num_protocollo_allegato:  this.allegatiUploadOptions.numProtocolloAllegato,
        data_protocollo_allegato:  this.allegatiUploadOptions.dataProtocolloAllegato
          ? this.allegatiUploadOptions.dataProtocolloAllegato + ' 00:00:00'
          : undefined,
        num_atto: this.allegatiUploadOptions.allegatoAdditionalInfo?.atto?.numero,
        data_atto: this.allegatiUploadOptions.allegatoAdditionalInfo?.atto?.data,
        titolo_allegato: this.allegatiUploadOptions.allegatoAdditionalInfo?.atto?.titolo,
        autore_allegato: this.allegatiUploadOptions.allegatoAdditionalInfo?.atto?.autore,
        note: this.allegatiUploadOptions.nota,
      };

      if (this.allegatiUploadOptions.tipoIntegrazione) {
        data.tipo_integra_allegato = {
          cod_tipo_integra_allegato: this.allegatiUploadOptions.tipoIntegrazione,
        };
      }
      // chiamo il metodo che si occupa di uploadare i files
      this.uploadFiles(data, files, showProgress);
    }
  }

  /**
   * Upload per tutti i files in input in ordine ed in gruppi in base alla size
   * @param data metadata allegati
   * @param files array di files input utente 
   * @param showProgress boolean che indica se devo mostrare o meno il progress
   */
  uploadFiles(data, files, showProgress: boolean = false){
    // divido i files in base alla size e li riordino
    const smallFiles = files.filter(file=>file.size<=this.allegatiUploadOptions.maxSizeFileSmall).sort((a,b) => a.size - b.size);
    const bigFiles = files.filter(file=>file.size>this.allegatiUploadOptions.maxSizeFileSmall).sort((a,b) => a.size - b.size);
    // le richieste possono essere singole o forkjoin per file piccoli
    const requests  = [];
    const filesRequest  = [];
    if(showProgress) {
      // in caso di showProgress parte il primo step
      this.startProgress([...smallFiles,...bigFiles]);
    }
    // recupero quanti file piccoli partono in parallelo
    const chunkSize = this.allegatiUploadOptions.parallelFileSmall;
    for (let i = 0; i < smallFiles.length; i += chunkSize) {
      const chunk = smallFiles.slice(i, i + chunkSize);
      const calls = chunk.map((x) => this.uploadFile(data,x,showProgress));
      requests.push(forkJoin(calls));
      filesRequest.push(chunk);
    }
    bigFiles.forEach(file=>{
      requests.push(this.uploadFile(data,file,showProgress));
      filesRequest.push(file);
    });
    let i=0;
    if(showProgress) {
      // in caso di showProgress parte il primo step
      this.startProgressStep(filesRequest[i]);
    }
    let allegatiAdded: Allegato[] = [];
    from(requests).pipe(    
      concatMap((request) => request.pipe(delay(100)))
    ).subscribe((res: Allegato| Allegato[]) => {
      if(showProgress) {
        // in caso di showProgress parte lo step successivo 
        i++;
        if(filesRequest[i]) {
          this.startProgressStep(filesRequest[i]);
        }
      }
      // le risposte potrebbero non essere un allegato, ma un caso di errore
      if(Array.isArray(res)) {
        res.forEach(a=>{
          if(a) {
            allegatiAdded.push(a);
          }
        })
      } else if(res) {
        allegatiAdded.push(res);
      }
    }, 
    err => {
      console.log(err);
    },
    ()=> {          
      this._callbackAddedAllegati(allegatiAdded);
      if(!showProgress) {
        this.spinner.hide();
        this._emitStatus('endUpload');
      } else {
        this.stopTimerProgress();
        this._emitStatus('hideProgressActive');
        this._emitStatus('endUploadProgress');
      }
    });
  }

  /**
   * Upload per singolo file
   * @param data  metadata per allegato
   * @param file singolo file allegato
   * @returns 
   */
  uploadFile(data, file, showProgress: boolean = false): Observable<Allegato>{
    const split = file.name.split('.');
    const checkExtensions = this.allegatiUploadOptions.fileExtensionsArray.some(
      (el) => el === split[split.length - 1].toLowerCase()
    );
    if (!checkExtensions) {
      if(!showProgress) {
        this.messageService.showMessage(
          'E019',
          this.allegatiUploadOptions.targetMessage,
          false
        );
      } else {
        this.errorProgressFile(file,this.messageService.getDescrizioneMessaggio( 'E019'));
      }
      return of(undefined);
    }
    data.nome_allegato = file.name;
    /// starto il timestamp del progress del file se necessario
    return this.postAllegati(JSON.stringify(data), file).pipe(
      tap((res:Allegato) => {
        if (res.ind_firma === 1 || res.ind_firma === 3) {
          if(!showProgress) {
            this.messageService.showMessage(
              'I009',
              this.allegatiUploadOptions.targetMessage,
              false
            );
          } else {
            this.doneProgressFile(file, this.messageService.getDescrizioneMessaggio('I009'));
          }
        } else {
          this.doneProgressFile(file);
        }
      })
    ).pipe(
      catchError((err) => {
        if (err.error?.code) {                    
          if (err.error.code === 'E015') {                      
            const swapPh = {
              ph: '{PH_NOME_FILE}',
              swap: data.nome_allegato,                        
            };
            if(!showProgress) {
              this.messageService.showMessage(
                err.error.code,
                this.allegatiUploadOptions.targetMessage,
                false,
                swapPh,
                err.error.title ? err.error.title : null
              );
            } else {
              this.errorProgressFile(file,this.messageService.getMessaggioPlaceholders( err.error.code, swapPh).des_testo_messaggio);
            }
          } else {
            if(!showProgress) {
              this.messageService.showMessage(
                err.error.code,
                this.allegatiUploadOptions.targetMessage,
                false,
                null,
                err.error.title ? err.error.title : null
              );
            } else {
              this.errorProgressFile(file,this.messageService.getDescrizioneMessaggio( err.error.code));
            }
          }
        } else {
          if(!showProgress) {
            this.messageService.showMessage(
              'E100',
              this.allegatiUploadOptions.targetMessage,
              true,
              null,
              err.error?.title? err.error.title : null
            );
          } else {
            this.errorProgressFile(file,this.messageService.getDescrizioneMessaggio( 'E100'));
          }
        }
        return of(undefined)
      })
    );
  }

  /**
   * Faccio una partire il progress per uno step di caricamento
   * @param files files o file per cui deve essere startato il progress
   */
  startProgressStep(files){
    if(Array.isArray(files)) {
      files.forEach(f=>{
        this.startProgressFile(f);
      })
    } else  {
      // file singolo
      this.startProgressFile(files);
    }
  }

  /**
   * Faccio una partire il progress per un singolo file
   * @param file singolo file 
   */
  startProgressFile(file){
    const fp: ProgressFile = this.getFileProgress(file.name);
    // se esiste già un errore non starto il progress
    // per es. il caso errore estensione
    if(fp.status==='wait') {
      this.setFileProgress(file.name, {
        status: 'started',
        startDate: new Date().getTime()/1000,
      });
    }
  } 

  /**
   * file uploadato con esito positivo
   * @param file singolo file 
   * @param error stringa errore opzionale
   */
  doneProgressFile(file, error:string = null){
    if(this._progress.findIndex(p=>p.name === file.name)>-1) {
      const nowDate = new Date();
      const now = nowDate.getTime()/1000;
      const seconds = Math.round(Math.abs(now-this._progress[this._progress.findIndex(p=>p.name === file.name)].startDate));
      console.log('progress: done file: ', file.name);
      console.log('progress: done date: ', nowDate);
      console.log('progress: done seconds: ', seconds);
    }
    this.setFileProgress(file.name, {
      status: 'done',
      progress: 100,
      error,
      typeMessage:'info'
    } as Partial<ProgressFile>);
  }
  
  /**
   * file uploadato con esito negativo
   * @param file singolo file 
   * @param error stringa errore
   */
  errorProgressFile(file, error: string){
    this.setFileProgress(file.name, {
      status: 'error',
      progress: 100,
      error,
      typeMessage: 'error'
    } as Partial<ProgressFile>);
  }

  /**
   * Agggiorno un ProgressFile
   * @param fileName stringa con il filename
   * @param partialProgress Partial<ProgressFile> che aggiorna il file con il filename in input
   */
  setFileProgress(fileName, partialProgress:Partial<ProgressFile>){
    if(this._progress.findIndex(p=>p.name === fileName)>-1) {
      this._progress[this._progress.findIndex(p=>p.name === fileName)] = {...this._progress.find(p=>p.name === fileName),...partialProgress};
    }
  }

  /**
   * Recupero il ProgressFile in base a fileName
   * @param fileName stringa con il filename
   * @returns ProgressFile
   */
  getFileProgress(fileName: string): ProgressFile{
    if(this._progress.findIndex(p=>p.name === fileName)>-1) {
      return this._progress[this._progress.findIndex(p=>p.name === fileName)];
    }
    return null;
  }

  emitProgress() {
    this._progress$.next(this._progress);
  }

  /**
   * 
   * @param size size file 
   * @returns numero di secondi che si si stimano per caricare un Mega
   */
  private _getSecondsForMega(size: number): number {
    let secondsForMega =(size<this.allegatiUploadOptions.maxProgressMegaSmall)?this.allegatiUploadOptions.secondsProgressForMegaSmall:this.allegatiUploadOptions.secondsProgressForMega;
    //const coeff= size<this.allegatiUploadOptions.maxProgressMegaSmall ? 1:size/100;
    //secondsForMega = secondsForMega*coeff;
    return secondsForMega;
  }

  /**
   * Funzione che effettua lo start del Timer per il recupero dei progress 
   * @param files elenco di files
   */
  private startProgress(files) {
    this._progress = [];
    files.forEach(f => {
        this._progress.push({
          name: f.name,
          size: f.size/1048576,
          status: 'wait'
        })
    });
    this._emitStatus('showProgress');
    this.emitProgress();
    // Sottoscrivo un ascoltatore al polling e gestisco la propagazione delle modifiche
    this.progressTimer$ = timer(
      this.allegatiUploadOptions.timerMilliseconds,
      this.allegatiUploadOptions.timerMilliseconds
    ).subscribe({
        next: (response: any) => {
          const nowDate = new Date();
          const now = nowDate.getTime()/1000;
          this._progress.forEach(
            p=> {
              const seconds = Math.round(Math.abs(now-p.startDate));
              if(p.status==='started') {
                // sotto una certa soglia di mega il numero di secopndi per mega va modificato
                const secondsForMega = this._getSecondsForMega(p.size);
                let percent = (seconds/(p.size*secondsForMega))*100;
                // imposto un limite massimo solo il done supera maxPercentProgressPending
                if(percent>this.allegatiUploadOptions.maxPercentProgressPending) {
                  percent = this.allegatiUploadOptions.maxPercentProgressPending;
                }
                this.setFileProgress(p.name, { progress: percent});
              }
            }
          )
          this.emitProgress();
          // #
        },
        error: (e: any) => {
          console.error('ERROR - [TimerGetPtogress]', e);
        },
      });
  }

  /**
   * Funzione che interrompe il timer per aggiornare il progress.
   * Una volta interrotto lo riaggancia facendo partire da zero il timer.
   */
  private stopTimerProgress() {
    // Interrompo il timer per il progress
    this.progressTimer$.unsubscribe();
  }

  /**
   * 
   * @param allegati elenco allegati aggiunti
   * @param getAllegatiQuadro da rimuovere 
   */
  private _callbackAddedAllegati(allegati: Allegato[]){ 
    this.allegatiIntegrazioniService.callbackMultiplePostAllegati(allegati, this.allegatiUploadOptions.idIstanza).subscribe(
      resAllegatiIstanza => {
        if(resAllegatiIstanza) {
         this._emitStatus('reloadAllegati');
        }
      }
    );
  }
}
