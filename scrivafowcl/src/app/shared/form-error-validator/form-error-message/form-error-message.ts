/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, Input } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-form-error-message',
  templateUrl: './form-error-message.html',
  styleUrls: ['./form-error-message.scss'],
})
export class FormErrorMessageComponent {
  @Input() public control!: FormControl;
  public params: any;

  public get errorMessage(): string | null {
    if (this.control && this.control.errors) {
      for (const propertyName in this.control.errors) {
        if (
          this.control.errors.hasOwnProperty(propertyName) &&
          this.control.touched
        ) {
          // Passo i parametri alla stringa di traduzione
          this.params = this.control.errors[propertyName];

          let text;
          switch (propertyName) {
            case 'required':
              text = `Campo obbligatorio`;
              break;
            case 'max':
              text = 'Formato non corretto';
              break;
            case 'maxlength':
              text = `Il campo non rispetta la lunghezza massima (${this.params.requiredLength})`;
              break;
            case 'minlength':
              text = `Il campo non rispetta la lunghezza minima (${this.params.requiredLength})`;
              break;
            case 'minimumAge':
              text = `Il soggetto deve essere maggiorenne`;
              break;
            case 'email':
              text = `Formato email non corretto`;
              break;
            case 'pattern':
              text = `Formato non corretto`;
              break;
            case '_controlloCf':
              text = `Codice fiscale non valido`;
              break;
            case '_controlloPIVA':
              text = `P.Iva non valida`;
              break;
            case 'maxDateExceededToday':
              text = 'La data non può essere successiva alla data odierna'
              break;
            case 'dateFineDataInizio':
              text = 'La data di fine non può precedere la data di inizio'
              break;
            default:
              text = `Errore ${propertyName}`;
          }

          return text;
        }
      }
    }

    return null;
  }
}
