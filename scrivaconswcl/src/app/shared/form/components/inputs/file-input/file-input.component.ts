/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import {
  Component,
  ElementRef,
  HostListener,
  Input,
  OnInit,
  ViewChild,
} from '@angular/core';
import { FileInput } from '../../../models/inputs/file-input';

@Component({
  selector: 'app-file-input',
  templateUrl: './file-input.component.html',
})
export class FileInputComponent implements OnInit {
  @ViewChild('fileTag') fileTag: ElementRef<HTMLInputElement>;
  @Input() name: string;
  @Input() control: FileInput;
  onChange: () => any;
  file: File | null = null;

  ngOnInit(): void {
    this.control.statusChanges.subscribe((x) => {
      if (!this.hasFile) {
        this.fileTag.nativeElement.value = null;
        this.file = null;
      }
    });
  }

  get hasFile(): boolean {
    return !!this.control.value;
  }

  @HostListener('change', ['$event.target.files']) emitFiles(event: FileList): void {
    const file = event && event.item(0);
    this.file = file;
    this.control.setValue(this.file);
  }

  btnClick(): void {
    if (!this.file) {
      this.fileTag.nativeElement.click();
    } else {
      this.control.reset();
      this.fileTag.nativeElement.value = null;
      this.file = null;
    }
  }
}
