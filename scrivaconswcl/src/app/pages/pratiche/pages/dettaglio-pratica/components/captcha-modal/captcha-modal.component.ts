/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { PraticheService } from '@app/pages/pratiche/services/pratiche.service';
import { ModalComponent } from '@app/shared/modal/models/modal.component';
import { LoadingService } from '@app/theme/layouts/loading.services';

@Component({
  selector: 'app-captcha-modal',
  templateUrl: './captcha-modal.component.html',
  styleUrls: ['./captcha-modal.component.scss']
})
export class CaptchaModalComponent extends ModalComponent implements OnInit {
  @ViewChild('captchaInput', { static: true })
  captchaInput: ElementRef;
  @ViewChild('reload', { static: true })
  realoadBtn: ElementRef;
  isClicked = false;
  captchaElement;
  captchaPayload = {
    captchaCode: '',
    captchaAnswer: ''
  };

  constructor(
    private praticheService: PraticheService,
    private _sanitizer: DomSanitizer,
    private loading: LoadingService) {
    super();
  }

  ngOnInit(): void {
    this.reloadCaptcha(false);
  }

  reloadCaptcha(clicked: boolean): void {
    this.isClicked = clicked;
    this.praticheService.getCaptcha().subscribe(captcha => {
      this.captchaElement = captcha;
      this.captchaElement.captchaImg = this.fromBase64ToImg(this.captchaElement.captchaImg);
    });
    setTimeout(() => {
      this.isClicked = false;
    }, 300);
  }

  downloadFile(): void {
    this.loading.show();
    this.captchaPayload.captchaCode = this.captchaElement?.captchaCode;
    this.captchaPayload.captchaAnswer = this.captchaInput.nativeElement.value;
    this.praticheService.validateCaptcha(this.captchaPayload).subscribe(value => {
      this.modalContainer.close(value);
    });
  }

  closeModal(): void {
    this.modalContainer.close();
  }

  fromBase64ToImg(base64): any {
    return this._sanitizer.bypassSecurityTrustUrl(
      'data:image/jpg;base64,' + base64
    );
  }

}
