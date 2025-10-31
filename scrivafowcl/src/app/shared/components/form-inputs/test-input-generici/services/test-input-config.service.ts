import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { IScrivaAnnoSelect } from '../utilities/test-input-form.interfaces';

@Injectable({
  providedIn: 'root'
})
export class TestInputConfigService {

  PATH_ANNI:string = 'listaAnni';

  constructor() { }


  getListaAnni(): Observable<number[]>  {
    // Simulazione di una chiamata al server per ottenere la lista degli anni
    return of([2020, 2021, 2022, 2023]);
  }
}
