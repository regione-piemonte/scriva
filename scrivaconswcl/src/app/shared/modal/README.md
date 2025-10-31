## Modali

Per gestire l'apertura di una modale possiamo utilizzare il **modal.service**.

Tramite il metodo openDialog, che prende in input un componente, che dovrà necessariamente **estendere ModalComponent**,
e delle **options**, possiamo eseguire l'apertura di una modale.

### Step per apertura modale

+ creazione di un componente che estende ModalComponent
+ inclusione del ModalService all'interno del componente che dovrà occuparsi dell'apertura della modale
+ chiamata al metodo openDialog per l'apertura della modale con il passaggio del componente appena creato
