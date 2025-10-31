/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { CdkStepperModule } from '@angular/cdk/stepper';
import {
  CommonModule,
  DatePipe,
  DecimalPipe,
  registerLocaleData,
} from '@angular/common';
import localeItExtra from '@angular/common/locales/extra/it';
import localeIt from '@angular/common/locales/it';
import { CUSTOM_ELEMENTS_SCHEMA, LOCALE_ID, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { FormioAppConfig } from 'angular-formio';
import { FormioModule } from 'angular-formio/formio.module';
import {
  ConfirmModalComponent,
  EmptyRouteComponent,
  FormioComponent,
  FormioDatiGeneraliComponent,
  InfoProtocolloAttoModalComponent,
  RecapTableComponent,
  StepperComponent,
  StepperFooterComponent,
} from './components';
import { CardRiepilogoAllegatiComponent } from './components/card-riepilogo-allegati/card-riepilogo-allegati.component';
import { EditAllegatoModalComponent } from './components/edit-allegato-modal/edit-allegato-modal.component';
import { EditNotaModalComponent } from './components/edit-nota-modal/edit-nota-modal.component';
import { FormInputComponent } from './components/form-inputs/form-input/form-input.component';
import { ScrivaCheckboxComponent } from './components/form-inputs/scriva-checkbox/scriva-checkbox.component';
import { ScrivaDatepickerComponent } from './components/form-inputs/scriva-datepicker/scriva-datepicker.component';
import { ScrivaEmailComponent } from './components/form-inputs/scriva-email/scriva-email.component';
import { ScrivaFormControlErrorComponent } from './components/form-inputs/scriva-form-control-error/scriva-form-control-error.component';
import { ScrivaFormGroupErrorComponent } from './components/form-inputs/scriva-form-group-error/scriva-form-group-error.component';
import { ScrivaInputLabelComponent } from './components/form-inputs/scriva-input-label/scriva-input-label.component';
import { ScrivaNumberFormattedComponent } from './components/form-inputs/scriva-number-formatted/scriva-number-formatted.component';
import { ScrivaNumberComponent } from './components/form-inputs/scriva-number/scriva-number.component';
import { ScrivaRadioComponent } from './components/form-inputs/scriva-radio/scriva-radio.component';
import { ScrivaSelectComponent } from './components/form-inputs/scriva-select/scriva-select.component';
import { ScrivaTextFakeComponent } from './components/form-inputs/scriva-text-fake/scriva-text-fake.component';
import { ScrivaTextComponent } from './components/form-inputs/scriva-text/scriva-text.component';
import { ScrivaTextareaComponent } from './components/form-inputs/scriva-textarea/scriva-textarea.component';
import { ScrivaTypeaheadComponent } from './components/form-inputs/scriva-typeahead/scriva-typeahead.component';
import { FormioConfirmModalComponent } from './components/formio/modals/formio-confirm-modal/formio-confirm-modal.component';
import { HeaderQuadroComponent } from './components/header-quadro/header-quadro.component';
import { IntegrazioneAllegatiComponent } from './components/integrazione-allegati/integrazione-allegati.component';
import { ProgressModalComponent } from './components/progress-modal/progress-modal.component';
import { ScrivaAlertComponent } from './components/scriva-alert/scriva-alert.component';
import { ScrivaBreadCrumbComponent } from './components/scriva-bread-crumb/scriva-bread-crumb.component';
import { ScrivaButtonComponent } from './components/scriva-buttons/scriva-button/scriva-button.component';
import { ScrivaDefaultButtonComponent } from './components/scriva-buttons/scriva-default-button/scriva-default-button.component';
import { ScrivaDropdownButtonComponent } from './components/scriva-buttons/scriva-dropdown-button/scriva-dropdown-button.component';
import { ScrivaIconTextButtonComponent } from './components/scriva-buttons/scriva-icon-text-button/scriva-icon-text-button.component';
import { ScrivaLinkButtonComponent } from './components/scriva-buttons/scriva-link-button/scriva-link-button.component';
import { ScrivaPrimaryButtonComponent } from './components/scriva-buttons/scriva-primary-button/scriva-primary-button.component';
import { ScrivaIconComponent } from './components/scriva-icon/scriva-icon.component';
import { ScrivaNavBarComponent } from './components/scriva-nav-bar/scriva-nav-bar.component';
import { ScrivaNoteComponent } from './components/scriva-note/scriva-note.component';
import { ScrivaPageTitleComponent } from './components/scriva-page-title/scriva-page-title.component';
import { ScrivaPaginatoreComponent } from './components/scriva-paginatore/scriva-paginatore.component';
import { TipoAdempimentoCardComponent } from './components/tipo-adempimento-card/tipo-adempimento-card.component';
import { CONFIG } from './config.injectiontoken';
import { ScrivaClickStopPropagation } from './directives/click-stop-propagation';
import { ScrivaNumbersOnly } from './directives/input-only-number.directive';
import { ScrivaNoTab } from './directives/no-tab.directive';
import {
  ScrivaMaintainDomDirective,
  ScrivaRemoveDomDirective,
} from './directives/remove-dom.directive';
import {
  ErrorValidatorService,
  FormErrorMessageComponent,
} from './form-error-validator';
import { AuthGuard } from './guards/auth.guard';
import { ProfiloGuard } from './guards/profilo/profilo.guard';
import { ScrivaModalHeaderComponent } from './modals/scriva-modal-header/scriva-modal-header.component';
import { ScrivaModalUtilitiesComponent } from './modals/scriva-modal-utilities/scriva-modal-utilities.component';
import {
  ScrivaFormErrorPipe,
  ScrivaTypeaheadDataValiditaPipe,
  ScrivaTypeaheadMapPipe,
} from './pipes/form-inputs/form-input.pipe';
import { ScrivaOptionValuePipe } from './pipes/form-inputs/scriva-select.pipe';
import {
  ScrivaAlertBoxIconPipe,
  ScrivaAlertBoxPipe,
} from './pipes/scriva-alert/scriva-alert.pipe';
import { ScrivaNavBarTabSelectedPipe } from './pipes/scriva-nav-bar/scriva-nav-bar.pipe';
import {
  ScrivaAttrDisablePipe,
  ScrivaCssHandlerPipe,
  ScrivaExecutePipe,
  ScrivaSanitizePipe,
  ScrivaStylesHandlerPipe,
  ScrivaTableTitlePipe,
} from './pipes/scriva-utilities/scriva-utilities.pipe';
import {
  AmbitiService,
  AppConfigService,
  LocationService,
  StepManagerService,
} from './services';
import { AppConfig } from './services/formio/configs/formio-config';
import { StepperIstanzaComponent } from './components/stepper-istanza/stepper-istanza.component';
import { ScrivaNumberItFormatComponent } from './components/form-inputs/scriva-number-it-format/scriva-number-it-format.component';
import { ScrivaSearchComponent } from './components/form-inputs/scriva-search/scriva-search.component';
import { TestInputGenericiComponent } from './components/form-inputs/test-input-generici/test-input-generici.component';

registerLocaleData(localeIt, 'it', localeItExtra);

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    CdkStepperModule,
    FormioModule,
    NgbModule,
    NgxDatatableModule,
  ],
  providers: [
    AppConfigService,
    ErrorValidatorService,
    AmbitiService,
    StepManagerService,
    LocationService,
    AuthGuard,
    ProfiloGuard,
    DatePipe,
    DecimalPipe,
    { provide: LOCALE_ID, useValue: 'it-IT' },
    { provide: CONFIG, useValue: '' },
    { provide: FormioAppConfig, useValue: AppConfig },
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  declarations: [
    // COMPONENTS
    EmptyRouteComponent,
    StepperComponent,
    StepperFooterComponent,
    RecapTableComponent,
    FormErrorMessageComponent,
    ConfirmModalComponent,
    FormioComponent,
    FormioConfirmModalComponent,
    FormioDatiGeneraliComponent,
    TipoAdempimentoCardComponent,
    HeaderQuadroComponent,
    InfoProtocolloAttoModalComponent,
    EditAllegatoModalComponent,
    ProgressModalComponent,
    CardRiepilogoAllegatiComponent,
    IntegrazioneAllegatiComponent,
    ScrivaPageTitleComponent,
    ScrivaNavBarComponent,
    ScrivaIconComponent,
    FormInputComponent,
    ScrivaModalUtilitiesComponent,
    ScrivaModalHeaderComponent,
    ScrivaButtonComponent,
    ScrivaDefaultButtonComponent,
    ScrivaDropdownButtonComponent,
    ScrivaLinkButtonComponent,
    ScrivaPrimaryButtonComponent,
    ScrivaIconTextButtonComponent,
    ScrivaCheckboxComponent,
    ScrivaEmailComponent,
    ScrivaTypeaheadComponent,
    ScrivaTextareaComponent,
    ScrivaTextFakeComponent,
    ScrivaNumberFormattedComponent,
    ScrivaRadioComponent,
    ScrivaNumberComponent,
    ScrivaTextComponent,
    ScrivaDatepickerComponent,
    ScrivaSelectComponent,
    ScrivaInputLabelComponent,
    ScrivaFormControlErrorComponent,
    ScrivaFormGroupErrorComponent,
    ScrivaPaginatoreComponent,
    ScrivaAlertComponent,
    ScrivaBreadCrumbComponent,
    ScrivaNoteComponent,
    EditNotaModalComponent,
    StepperIstanzaComponent,
    ScrivaNumberItFormatComponent,
    ScrivaSearchComponent,
    TestInputGenericiComponent,
    // PIPES
    ScrivaNavBarTabSelectedPipe,
    ScrivaExecutePipe,
    ScrivaCssHandlerPipe,
    ScrivaStylesHandlerPipe,
    ScrivaSanitizePipe,
    ScrivaAttrDisablePipe,
    ScrivaFormErrorPipe,
    ScrivaTypeaheadMapPipe,
    ScrivaTypeaheadDataValiditaPipe,
    ScrivaOptionValuePipe,
    ScrivaAlertBoxPipe,
    ScrivaAlertBoxIconPipe,
    ScrivaTableTitlePipe,
    // Directives
    ScrivaMaintainDomDirective,
    ScrivaRemoveDomDirective,
    ScrivaNumbersOnly,
    ScrivaNoTab,
    ScrivaClickStopPropagation,
  
  ],
  exports: [
    // COMPONENTS
    EmptyRouteComponent,
    StepperComponent,
    StepperFooterComponent,
    RecapTableComponent,
    CdkStepperModule,
    ReactiveFormsModule,
    FormsModule,
    FormErrorMessageComponent,
    FormioComponent,
    FormioConfirmModalComponent,
    FormioDatiGeneraliComponent,
    TipoAdempimentoCardComponent,
    HeaderQuadroComponent,
    InfoProtocolloAttoModalComponent,
    EditAllegatoModalComponent,
    ProgressModalComponent,
    CardRiepilogoAllegatiComponent,
    IntegrazioneAllegatiComponent,
    EditNotaModalComponent,
    ScrivaPageTitleComponent,
    ScrivaNavBarComponent,
    ScrivaIconComponent,
    FormInputComponent,
    ScrivaModalUtilitiesComponent,
    ScrivaModalHeaderComponent,
    ScrivaButtonComponent,
    ScrivaDefaultButtonComponent,
    ScrivaDropdownButtonComponent,
    ScrivaLinkButtonComponent,
    ScrivaPrimaryButtonComponent,
    ScrivaIconTextButtonComponent,
    ScrivaCheckboxComponent,
    ScrivaEmailComponent,
    ScrivaTypeaheadComponent,
    ScrivaTextareaComponent,
    ScrivaTextFakeComponent,
    ScrivaNumberFormattedComponent,
    ScrivaRadioComponent,
    ScrivaTextComponent,
    ScrivaNumberComponent,
    ScrivaDatepickerComponent,
    ScrivaSelectComponent,
    ScrivaInputLabelComponent,
    ScrivaFormControlErrorComponent,
    ScrivaFormGroupErrorComponent,
    ScrivaPaginatoreComponent,
    ScrivaAlertComponent,
    ScrivaBreadCrumbComponent,
    ScrivaNoteComponent,
    ScrivaNumberItFormatComponent,
    ScrivaSearchComponent,
    TestInputGenericiComponent,
    // PIPES
    ScrivaNavBarTabSelectedPipe,
    ScrivaExecutePipe,
    ScrivaCssHandlerPipe,
    ScrivaStylesHandlerPipe,
    ScrivaSanitizePipe,
    ScrivaAttrDisablePipe,
    ScrivaFormErrorPipe,
    ScrivaTypeaheadMapPipe,
    ScrivaTypeaheadDataValiditaPipe,
    ScrivaOptionValuePipe,
    ScrivaAlertBoxPipe,
    ScrivaAlertBoxIconPipe,
    ScrivaTableTitlePipe,
    // Directives
    ScrivaMaintainDomDirective,
    ScrivaRemoveDomDirective,
    ScrivaNumbersOnly,
    ScrivaNoTab,
    ScrivaClickStopPropagation,
  ],
  entryComponents: [
    EmptyRouteComponent,
    ConfirmModalComponent,
    FormioComponent,
    FormioDatiGeneraliComponent,
    InfoProtocolloAttoModalComponent,
    EditAllegatoModalComponent,
    ProgressModalComponent,
    ScrivaModalUtilitiesComponent,
    EditNotaModalComponent,
    FormioConfirmModalComponent
  ],
})
export class SharedModule {}
