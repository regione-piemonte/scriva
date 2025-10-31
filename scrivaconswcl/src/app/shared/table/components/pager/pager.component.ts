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
  EventEmitter,
  Input,
  OnChanges,
  Output
} from '@angular/core';
import { environment } from '@env/environment';

@Component({
  selector: 'app-pager',
  templateUrl: './pager.component.html',
  styleUrls: ['./pager.component.scss']
})
export class AgidPagerComponent implements OnChanges {
  @Input() page: number;
  @Input() count: number;
  @Input() perPage: number;
  @Input() showSize = false;

  @Output() changePage = new EventEmitter<number>();
  @Output() changeSize = new EventEmitter<number>();

  iconBaseUrl = environment.iconBaseUrl;

  currentPerPage: any;
  sizes = [5, 10, 20, 50, 100];
  selectedSize = this.sizes[0];

  pages: any[];

  ngOnChanges(): void {
    this.selectedSize = this.sizes.find((size) => size === this.perPage);
    this.initPages();
  }

  shouldShow(): boolean {
    return +this.count > +this.perPage;
  }

  paginate(page: number): boolean {
    this.page = page;
    this.changePage.emit(page);
    return false;
  }

  next(): boolean {
    if (!this.isLastPage()) {
      return this.paginate(this.getPage() + 1);
    }
  }

  prev(): boolean {
    if (!this.isFirstPage()) {
      return this.paginate(this.getPage() - 1);
    }
  }

  getPage(): number {
    return this.page;
  }

  getPages(): any[] {
    return this.pages;
  }

  getLast(): number {
    return Math.ceil(this.count / this.perPage);
  }

  isPageOutOfBounce(): boolean {
    return (
      this.page * this.perPage >= this.count + this.perPage && this.page > 1
    );
  }

  isFirstPage(): boolean {
    if (this.page === 1) {
      return true;
    } else {
      return false;
    }
  }

  isLastPage(): boolean {
    if (this.page === this.getLast()) {
      return true;
    } else {
      return false;
    }
  }

  initPages() {
    const pagesCount = this.getLast();
    let showPagesCount = 4;
    showPagesCount = pagesCount < showPagesCount ? pagesCount : showPagesCount;
    this.pages = [];

    if (this.shouldShow()) {
      let middleOne = Math.ceil(showPagesCount / 2);
      middleOne = this.page >= middleOne ? this.page : middleOne;

      let lastOne = middleOne + Math.floor(showPagesCount / 2);
      lastOne = lastOne >= pagesCount ? pagesCount : lastOne;

      const firstOne = lastOne - showPagesCount + 1;

      for (let i = firstOne; i <= lastOne; i++) {
        this.pages.push(i);
      }
    }
  }

  onChangeSize(size): void {
    this.changeSize.emit(size);
  }

  onChangePage(page: number): void {
    this.changePage.emit(page);
  }
}
