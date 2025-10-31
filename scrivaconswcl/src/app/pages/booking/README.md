## Rotta modello

Ogni rotta applicativa deve essere gestita come modulo LAZY. La directory corrispondente deve contenere:

* un module (areafunzionale.module) per la gestione del module
* un routing.module (areafunzionale-routing.module) per la gestione delle rotte interne
* un main (router-outlet) chiamato booking.component nell'esempio
* una directory model per gestire i modelli
* una directory pages per gestire le pagien interne
* una directory services per gestire i services necessari

## Form

Se abbiamo la necessità di generare un form complesso possiamo definire una cartella helper all'interno della quale
definiamo dei servizi (*form-helper*) che estendono la classe AbstractFormHelperService

