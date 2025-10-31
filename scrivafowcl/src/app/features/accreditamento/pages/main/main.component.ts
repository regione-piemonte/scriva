/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { filter, takeUntil } from 'rxjs/operators';
import { AutoUnsubscribe } from 'src/app/core/components';
import { ErrorValidatorService } from 'src/app/shared/form-error-validator';
import { Compilante } from 'src/app/shared/models';
import {
  AuthStoreService,
  HelpService,
  MessageService,
} from 'src/app/shared/services';
import { RegexUtil } from 'src/app/shared/utils';
import { Accreditamento } from '../../models';
import { AccreditamentoService } from '../../services';
import { environment } from '../../../../../environments/environment';
import { ScrivaComponenteApp } from 'src/app/shared/enums/scriva-utilities/scriva-utilities.enums';


@Component({
  selector: 'app-accreditamento',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss'],
})
export class MainComponent extends AutoUnsubscribe implements OnInit {
  codMaschera = '.MSCR007D';
  compilante: Compilante;

  formAccreditamento: FormGroup;

  accreditamento: Accreditamento;

  constructor(
    private router: Router,
    private spinner: NgxSpinnerService,
    private fb: FormBuilder,
    private ms: MessageService,
    private errorValidatorService: ErrorValidatorService,
    private service: AccreditamentoService,
    private authStoreService: AuthStoreService,
    private helpService: HelpService
  ) {
    super();
    this.spinner.hide();
  }

  ngOnInit(): void {
    this.spinner.hide();
    this.compilante = this.authStoreService.getCompilante();
    this.helpService.setCodMaschera(this.codMaschera);

    this._buildFormAccreditamento();
  }

  private _buildFormAccreditamento() {
    this.formAccreditamento = this.fb.group(
      {
        email: [
          '',
          {
            validators: [
              Validators.required,
              Validators.pattern(RegexUtil.Email),
            ],
          },
        ],
        confermaEmail: [
          '',
          {
            validators: [
              Validators.required,
              Validators.pattern(RegexUtil.Email),
            ],
          },
        ],
        otpCode: [
          '',
          {
            validators: [Validators.required],
          },
        ],
        checkAutorizza: [
          '',
          {
            validators: [Validators.requiredTrue],
          },
        ],
      },
      {
        validator: this.errorValidatorService.stringMustMatchCaseInsensitive(
          'email',
          'confermaEmail'
        ),
      }
    );
  }

  confermaEmail() {
    this.spinner.show();
    this.formAccreditamento.get('email').markAsTouched();
    this.formAccreditamento.get('confermaEmail').markAsTouched();

    if (
      !this.formAccreditamento.get('email')?.value ||
      !this.formAccreditamento.get('confermaEmail')?.value
    ) {
      this.ms.showMessage('E001', 'formAccreditamento', true);
    } else if (
      !this.formAccreditamento.get('email')?.valid ||
      !this.formAccreditamento.get('confermaEmail')?.valid
    ) {
      this.ms.showMessage('E004', 'formAccreditamento', true);
    } else if (
      (<string>this.formAccreditamento.get('email')?.value)
        .toLocaleLowerCase()
        .includes('@pec')
    ) {
      this.ms.showMessage('E010', 'formAccreditamento', true);
    } else {
      this.service
        .sendMailOtp({
          cf_accredito: this.compilante.cf_compilante,
          cognome_accredito: this.compilante.cognome_compilante,
          nome_accredito: this.compilante.nome_compilante,
          des_email_accredito: this.formAccreditamento.get('email')?.value,
          flg_autorizza_dati_personali:
            this.formAccreditamento.get('checkAutorizza')?.value,
        })
        .pipe(
          takeUntil(this.destroy$),
          filter((accreditamento) => !!accreditamento)
        )
        .subscribe(
          (accreditamento) => {
            this.accreditamento = accreditamento;
            this.ms.showMessage('I002', 'formAccreditamento', true);
          },
          (error) => {
            if (error.error?.code) {
              // SCRIVA-1413 
              const keyserror = Object.keys(error.error.detail);
              if(keyserror?.length==1) {
                this.ms.showMessageStatic('formAccreditamento',false, error.error.title+': '+error.error.detail[keyserror[0]]);
              } else {
                this.ms.showMessageStatic('formAccreditamento',false, error.error.title);
              }
            } else {
              this.ms.showMessage('E100', 'formAccreditamento', true);
            }
          }
        );
    }
  }

  annulla() {
    // todo : check what to do here
    // this.router.navigate(['/login']);
    // window.history.back();
    const annullaUrl = (this.authStoreService.getComponente() as ScrivaComponenteApp ===ScrivaComponenteApp.backOffice )? environment.annullaUrlBO : environment.annullaUrlFO ;
    window.open(annullaUrl, '_self');
  }

  accredita() {
    this.spinner.show();
    this.formAccreditamento.markAllAsTouched();

    if (this.formAccreditamento.valid && this.accreditamento) {
      this.service
        .getAccreditamento(
          this.accreditamento?.gestUID,
          this.formAccreditamento.get('otpCode')?.value
        )
        .pipe(takeUntil(this.destroy$))
        .subscribe(
          (compilante) => {
            if (compilante) {
              this.compilante = compilante;
              this.authStoreService.setCompilante(compilante);
              this.ms.showMessage('P003', 'formAccreditamento', true);
              this.router.navigate(['/home']);
            } else {
              // verifica OTP non andata a buon fine
              this.ms.showMessage('E009', 'formAccreditamento', true);
            }
          },
          (error) => {
            if (error.error?.code) {
              this.ms.showMessage(
                error.error.code,
                'formAccreditamento',
                false
              );
            } else {
              this.ms.showMessage('E100', 'formAccreditamento', true);
            }
          }
        );
    }
  }
}
