/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Component, Input, OnInit } from '@angular/core';
import { I18nService } from '@eng-ds/translate';
import { Status } from '@core/enums';
import { UtilsService } from '@shared/components/utils.service';
import { AlertModel } from '@shared/alert/model/alert.model';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.scss']
})
export class AlertComponent implements OnInit {

  @Input() alert: AlertModel;
  closeText: string;
  alertId = 'alert_' + Math.random().toFixed(5).substring(2);
  h5Id = 'h5_' + this.alertId;
  classes;
  alertIconFill = '#000000de';

  constructor(private i18n: I18nService,
    private utils: UtilsService) {
  }

  ngOnInit(): void {
    this.classes = {
      alert: true,
      dismissable: this.alert.dismissable,
      success: this.utils.isDefined(this.alert.status) && this.alert.status === Status.success,
      error: this.utils.isDefined(this.alert.status) && this.alert.status === Status.error,
      info: this.utils.isDefined(this.alert.status) && this.alert.status === Status.info,
      warning: this.utils.isDefined(this.alert.status) && this.alert.status === Status.warning,
      'with-icon': this.utils.isDefined(this.alert.icon) && this.utils.isNotNull(this.alert.icon)
    };
    this.closeText = this.i18n.translate('UTILS.ALERT.CLOSE', { titleAlert: this.alert.title });
    this.alertIconFill = this.alert.status === 'success'  ? '#61b87a'
      : this.alert.status === 'error'  ? '#f41622'
      : this.alert.status === 'info'  ? '#0058b1'
      : this.alert.status === 'warning'  ? '#f8e60d'
      : '#000000de';
  }


}
