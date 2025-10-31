/**
 * Interfaccia specifica che definisce le configurazioni dati per associare, mediante proprietà [rowClass] della componente <ngx-datatable>, la colorazione ad una riga selezionata.
 */
export interface INgxDataTableRowSelected {
  /** La proprietà definisce la classe di stile globale per la colorazione della riga, mentre il valore definisce se la classe di stile è d'attivare effettivamente. */
  'riga-selezionata': boolean;
}
