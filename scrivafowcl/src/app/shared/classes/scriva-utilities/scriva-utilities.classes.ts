/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { IScrivaIcon } from '../../components/scriva-icon/utilities/scriva-icon.interfaces';
import { ScrivaButtonTypes } from '../../enums/scriva-utilities/scriva-utilities.enums';
import {
  IScrivaButton,
  IScrivaButtonConfig,
  IScrivaButtonCss,
  IScrivaDDButtonConfig,
  IScrivaDDButtonCss,
  IScrivaDDItemConfig,
  IScrivaFormInputCss,
  IScrivaIconConfigs,
  IScrivaIconTextButton,
  IScrivaIconTextButtonConfig,
  IScrivaIconTextButtonCss,
} from '../../interfaces/scriva-utilities.interfaces';
import { ScrivaCss } from '../../types/scriva-utilities/scriva-utilities.types';

/**
 * Classe che definisce le configurazioni data e css per uno scriva-button.
 */
export class ScrivaButton implements IScrivaButton {
  data: ScrivaButtonConfig;
  css: ScrivaButtonCss;

  constructor(c?: IScrivaButton) {
    this.data = new ScrivaButtonConfig(c?.data);
    this.css = new ScrivaButtonCss(c?.css);
  }
}

/**
 * Classe che definisce le configurazioni data e css per uno scriva-button.
 */
export class ScrivaIconTextButton implements IScrivaIconTextButton {
  data: ScrivaIconTextButtonConfig;
  css: ScrivaIconTextButtonCss;

  constructor(c?: IScrivaIconTextButton) {
    this.data = new ScrivaIconTextButtonConfig(c?.data);
    this.css = new ScrivaIconTextButtonCss(c?.css);
  }
}

/**
 * Classe che definisce le configurazioni per gli stili dei scriva-button
 */
export class ScrivaButtonConfig implements IScrivaButtonConfig {
  label: string;

  constructor(c?: IScrivaButtonConfig) {
    this.label = c?.label;
  }
}

/**
 * Classe che definisce le configurazioni per gli stili dei scriva-icon-text-button
 */
export class ScrivaIconTextButtonConfig
  extends ScrivaButtonConfig
  implements IScrivaIconTextButtonConfig
{
  label: string;
  icon: IScrivaIcon;

  constructor(c?: IScrivaIconTextButtonConfig) {
    // Richiamo il super
    super(c);

    this.icon = c?.icon;
  }
}

/**
 * Classe che definisce le configurazioni per gli stili dei scriva-button
 */
export class ScrivaButtonCss implements IScrivaButtonCss {
  customButton?: string | any;
  typeButton?: ScrivaButtonTypes;

  constructor(c?: IScrivaButtonCss) {
    this.customButton = c?.customButton;
    this.typeButton = c?.typeButton;
  }
}

/**
 * Classe che definisce le configurazioni per gli stili dei scriva-icon-text-button
 */
export class ScrivaIconTextButtonCss
  extends ScrivaButtonCss
  implements IScrivaIconTextButtonCss
{
  customButton?: string | any;
  typeButton?: ScrivaButtonTypes;
  customLabel?: string | any;
  labelLeft?: boolean;

  constructor(c?: IScrivaIconTextButtonCss) {
    // Richiamo il super
    super(c);

    this.customLabel = c?.customLabel;
    this.labelLeft = c?.labelLeft;
  }
}

/**
 * Classe che definisce le configurazioni per gli stili dei scriva-dropdown-button
 */
export class ScrivaDDButtonCss
  extends ScrivaButtonCss
  implements IScrivaDDButtonCss
{
  customItems?: ScrivaCss;

  constructor(c?: IScrivaDDButtonCss) {
    super(c);
    this.customItems = c?.customItems;
  }
}

/**
 * Classe che definisce le configurazioni per la struttura dati del scriva-dropdown-button
 */
export class ScrivaDDButtonConfig
  extends ScrivaButtonConfig
  implements IScrivaDDButtonConfig
{
  dropdownItems: ScrivaDDItemConfig[];

  constructor(c?: IScrivaDDButtonConfig) {
    super(c);
    this.dropdownItems = c?.dropdownItems ?? [];
  }
}

/**
 * Classe che definisce le configurazioni per la struttura dati di un elemento della dropdown del componente scriva-dropdown-button.
 */
export class ScrivaDDItemConfig implements IScrivaDDItemConfig {
  id: string;
  label: string;
  disabled: boolean;
  title: string;
  data: any;
  codeAEA: string;
  itemCss: ScrivaCss;
  useIcon: boolean;
  useIconLeft: boolean;
  useIconRight: boolean;
  iconLeft: ScrivaIconConfigs;
  iconRight: ScrivaIconConfigs;

  constructor(c?: IScrivaDDItemConfig) {
    this.id = c.id;
    this.label = c.label;
    this.disabled = c?.disabled ?? false;
    this.title = c?.title ?? '';
    this.data = c?.data;
    this.codeAEA = c?.codeAEA;
    this.itemCss = c?.itemCss;
    this.useIcon = c?.useIcon ?? false;
    this.useIconLeft = c?.useIconLeft ?? false;
    this.useIconRight = c?.useIconRight ?? false;
    this.iconLeft = c?.iconLeft ?? c?.icon;
    this.iconRight = c?.iconRight ?? c?.icon;
  }
}

/**
 * Classe che rappresenta le configurazioni per il componente scriva-icon.
 */
export class ScrivaIconConfigs implements IScrivaIconConfigs {
  /** Array di string che definisce le classi da utilizzare. */
  classes: string[] = [];
  /** String che definisce l'url della risorsa dell'icona. */
  icon: string = '';
  /** Any che definisce gli css per l'icona, deve essere compatibile con NgStyle. */
  iconStyles: ScrivaCss = {};
  /** String che definisce il title dell'icona. */
  iconTitle: string = '';
  /** Boolean che definisce se l'icona è disabilitata. */
  disabled: boolean = false;
  /** Boolean che definisce se utilizzare la classe di default dell'icona. */
  useDefault: boolean = true;
  /** Boolean che definisce se attivare il click dell'icona. */
  enableClick: boolean = false;

  constructor(c?: IScrivaIconConfigs) {
    this.classes = c?.classes ?? [];
    this.icon = c?.icon ?? '';
    this.iconStyles = c?.iconStyles ?? {};
    this.iconTitle = c?.iconTitle ?? '';
    this.disabled = c?.disabled ?? false;
    this.useDefault = c?.useDefault ?? true;
    this.enableClick = c?.enableClick ?? false;
  }
}

/**
 * Classe che definisce le configurazioni CSS utilizzate dai componenti form-inputs.
 */
export class ScrivaFormInputCss {
  /** String or any, compatibile con le direttive NgClass e NgStyle, che definisce la classe CSS del contenitore del form-group. */
  customForm: string | any;
  /** String che definisce la classe CSS del contenitore del form-group. */
  customFormCheck: string;
  /** String or any, compatibile con le direttive NgClass e NgStyle, che definisce la classe CSS dell'input. */
  customInput: string | any;
  /** String or any, compatibile con le direttive NgClass e NgStyle, che definisce la classe CSS della label. */
  customLabel: string | any;
  /** String compatibile con le classi css, o oggetto compatibile con la direttiva NgStyle che definisce lo stile del container. */
  customError: string | any;
  /** Boolean che definisce se visualizzare eventuali messaggi d'errore per il form group. */
  showErrorFG: boolean;
  /** Boolean che definisce se visualizzare eventuali messaggi d'errore per il form control. */
  showErrorFC: boolean;

  /** String or any, compatibile con le direttive NgClass e NgStyle, che definisce la classe CSS per il contenitore della label di sinistra. */
  labelColLeft: string | any;
  /** String or any, compatibile con le direttive NgClass e NgStyle, che definisce la classe CSS per il contenitore della label di destra. */
  labelColRight: string | any;

  constructor(c: IScrivaFormInputCss) {
    this.customForm = c.customForm || '';
    this.customFormCheck = c.customFormCheck || '';
    this.customInput = c.customInput || '';
    this.customLabel = c.customLabel || '';
    this.customError = c.customError || '';
    this.showErrorFG = c.showErrorFG !== undefined ? c.showErrorFG : false;
    this.showErrorFC = c.showErrorFC !== undefined ? c.showErrorFC : false;
    this.labelColLeft = c.labelColLeft || '';
    this.labelColRight = c.labelColRight || '';
  }
}
