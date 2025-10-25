/* *****************************************************
 * Copyright Regione Piemonte - 2025
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************/

-- DROP FUNCTION fnc_delete_istanza(int4);

CREATE OR REPLACE FUNCTION fnc_delete_istanza(p_idistanza integer)
 RETURNS integer
 LANGUAGE plpgsql
AS $function$
declare

/*********************************************************************************************************************
   NOME:     fnc_delete_istanza
   SCOPO:    La funzione permette di cancellare un istanza e tutte le tabelle 'figlie' ad essa legate
   UTILIZZO: La funzione sara' richiamata dal servizio : /istanze/id/{idIstanza} (DELETE)
   
             Il servizio effettua quanto segue:
                         1. Estrazione uuindex allegati istanza
                         2. Esecuzione fnc_delete_istanza
                         3. In caso di presenza di allegati richiama il servizio di delete allegati su index 
                            per gli uuindex salvati in precedenza

                         Questo per permettere la pulizia totale sia su scriva che su index.            

   REVISIONE:
   Ver        Date        Author           Description
   ---------  ----------  ---------------  ------------------------------------
   1.0        01/06/2021                   Creazione della funzione iniziale
   1.1        10/09/2021  Simona R.        Cancellazione dati : scriva_r_istanza_evento
   1.2        18/11/2021  Beppe I.         Cancellazione dati : scriva_r_istanza_osservazione e aggiornata update scriva_t_istanza
   1.3        23/12/2021  Simona R.        Cancellazione dati : scriva_r_oggetto_istanza, scriva_r_ogg_natura_2000, scriva_r_ogg_area_protetta, 
                                                                scriva_r_ogg_vincolo_autorizza, scriva_r_ogg_istanza_categoria
   1.4        21/01/2022  Beppe I.         Cancellazione dati : scriva_r_nota_istanza, scriva_r_catasto_ubi_ogg_ist
   1.5        03/02/2022  Beppe I.         Cancellazione dati : scriva_r_oggetto_ist_figlio e storici : 
                                                                         scriva_s_gruppo_soggetto, scriva_s_soggetto_gruppo, scriva_s_recapito_alternativo
                                                                         scriva_s_oggetto_ist_figlio, scriva_s_ogg_area_protetta, scriva_s_ogg_natura_2000
                                                                         scriva_s_ogg_vincolo_autorizza, scriva_s_catasto_ubi_ogg_ist
   1.6        13/05/2022  Beppe I.         Cancellazione dati : spostata cancellazione allegati dopo pagamenti
   1.7        26/10/2022  Beppe I.         Cancellazione dati : notifiche - scriva_t_notifica e scriva_r_notifica_applicativa
   1.8        14/03/2023  Beppe I.         Cancellazione dati : scriva_r_istanza_competenza_oggetto
   1.9        08/05/2023  Beppe I.         Cancellazione dati : scriva_r_istanza_responsabile
   2.0        13/09/2023  Beppe I.         Cancellazione dati : tabelle scriva_t_integrazione_istanza e scriva_r_integra_istanza_allegato
   2.1        15/11/2023  Beppe I.         Cancellazione dati : tabella scriva_r_notifica_allegato
**********************************************************************************************************************/

ctr int4 := 0;
ctr_allegati int4 := 0;
esito int4 := 1;
t_msg_exc text;
t_dett_exc text;
t_hint_exc text;

BEGIN
   begin
      select count(1) into ctr from scriva_t_istanza where id_istanza = p_idistanza;

      if ctr = 1 then 

         raise notice '% -Start  delete istanza : %', now(), p_idistanza;

         -- -----------------------------------------------------
         -- Soggetti Istanza
         -- -----------------------------------------------------
         DELETE FROM scriva_s_soggetto_gruppo ra where exists (select 1 from scriva_t_soggetto_istanza stsi   -- (1.5 del 03/02/2022)
                                    where 
                                    stsi.id_istanza = p_idistanza
                                    and stsi.id_soggetto_istanza = ra.id_soggetto_istanza
                                    );
         DELETE FROM scriva_r_soggetto_gruppo ra where exists (select 1 from scriva_t_soggetto_istanza stsi 
                                    where 
                                    stsi.id_istanza = p_idistanza
                                    and stsi.id_soggetto_istanza = ra.id_soggetto_istanza
                                    );

         DELETE FROM scriva_s_recapito_alternativo ra where exists (select 1 from scriva_t_soggetto_istanza stsi -- (1.5 del 03/02/2022)
                                    where 
                                    stsi.id_istanza = p_idistanza
                                    and stsi.id_soggetto_istanza = ra.id_soggetto_istanza
                                    );
         DELETE FROM scriva_r_recapito_alternativo ra where exists (select 1 from scriva_t_soggetto_istanza stsi 
                                    where 
                                    stsi.id_istanza = p_idistanza
                                    and stsi.id_soggetto_istanza = ra.id_soggetto_istanza
                                    );

         DELETE FROM scriva_s_gruppo_soggetto ra  where not exists (select 1 from scriva_r_soggetto_gruppo sg -- (1.5 del 03/02/2022) 
                                  where sg.id_gruppo_soggetto = ra.id_gruppo_soggetto );
         DELETE FROM scriva_t_gruppo_soggetto ra  where not exists (select 1 from scriva_r_soggetto_gruppo sg 
                                  where sg.id_gruppo_soggetto = ra.id_gruppo_soggetto );

         DELETE FROM scriva_s_soggetto_istanza where id_istanza = p_idistanza;
         DELETE FROM scriva_t_soggetto_istanza where id_istanza = p_idistanza;


         -- -----------------------------------------------------
         -- Oggetti Istanza
         -- -----------------------------------------------------

         -- Competenze Istanza (Oggetti Istanza)
         DELETE FROM scriva_r_istanza_competenza_oggetto where id_istanza = p_idistanza; -- (1.8 del 14/03/2023)

         -- Ubicazioni (Oggetti Istanza)

         -- Dati Catastali (Ubicazioni-Oggetti Istanza)
         DELETE FROM scriva_s_catasto_ubi_ogg_ist u  -- (1.5 del 03/02/2022)
               where exists ( select 1 from scriva_r_ubica_oggetto_istanza ub,
                                            scriva_t_oggetto_istanza oi
                                      where ub.id_oggetto_istanza= oi.id_oggetto_istanza
                                        and id_istanza = p_idistanza
                                        and ub.id_ubica_oggetto_istanza = u.id_ubica_oggetto_istanza
                             );
         DELETE FROM scriva_r_catasto_ubi_ogg_ist u  -- (1.4 del 21/01/2022)
               where exists ( select 1 from scriva_r_ubica_oggetto_istanza ub,
                                            scriva_t_oggetto_istanza oi
                                      where ub.id_oggetto_istanza= oi.id_oggetto_istanza
                                        and id_istanza = p_idistanza
                                        and ub.id_ubica_oggetto_istanza = u.id_ubica_oggetto_istanza
                             );

         DELETE FROM scriva_s_ubica_oggetto_istanza u where exists (select 1 from scriva_t_oggetto_istanza oi
                                    where 
                                    id_istanza = p_idistanza
                                    and oi.id_oggetto_istanza= u.id_oggetto_istanza
                                    );

         DELETE FROM scriva_r_ubica_oggetto_istanza u where exists (select 1 from scriva_t_oggetto_istanza oi
                                    where 
                                    id_istanza = p_idistanza
                                    and oi.id_oggetto_istanza= u.id_oggetto_istanza
                                    );

          -- Geometrie (Oggetti Istanza)
         DELETE FROM scriva_geo_area_ogg_istanza u where exists (select 1 from scriva_t_oggetto_istanza oi
                                    where 
                                    id_istanza = p_idistanza
                                    and oi.id_oggetto_istanza= u.id_oggetto_istanza
                                    );
         DELETE FROM scriva_geo_linea_ogg_istanza u where exists (select 1 from scriva_t_oggetto_istanza oi
                                    where 
                                    id_istanza = p_idistanza
                                    and oi.id_oggetto_istanza= u.id_oggetto_istanza
                                    );
         DELETE FROM scriva_geo_punto_ogg_istanza u where exists (select 1 from scriva_t_oggetto_istanza oi
                                    where 
                                    id_istanza = p_idistanza
                                    and oi.id_oggetto_istanza= u.id_oggetto_istanza
                                    );

         -- Asociazioni padre figli (Oggetto Istanza)
         -- Asociazione figlio  
         DELETE FROM scriva_s_oggetto_ist_figlio u   -- (1.5 del 03/02/2022)
               where exists (select 1 from scriva_t_oggetto_istanza oi
                                     where id_istanza = p_idistanza
                                       and u.id_oggetto_istanza_figlio= oi.id_oggetto_istanza
                            );
         DELETE FROM scriva_r_oggetto_ist_figlio u 
               where exists (select 1 from scriva_t_oggetto_istanza oi
                                     where id_istanza = p_idistanza
                                       and u.id_oggetto_istanza_figlio= oi.id_oggetto_istanza
                            );

         -- Asociazione padre
         DELETE FROM scriva_s_oggetto_ist_figlio u -- (1.5 del 03/02/2022)
               where exists (select 1 from scriva_t_oggetto_istanza oi
                                     where id_istanza = p_idistanza
                                       and u.id_oggetto_istanza_padre= oi.id_oggetto_istanza
                            );
         DELETE FROM scriva_r_oggetto_ist_figlio u 
               where exists (select 1 from scriva_t_oggetto_istanza oi
                                     where id_istanza = p_idistanza
                                       and u.id_oggetto_istanza_padre= oi.id_oggetto_istanza
                            );
         
         -- Siti natura 2000 istanza (Oggetto Istanza)
         DELETE FROM scriva_s_ogg_natura_2000 u where exists (select 1 from scriva_t_oggetto_istanza oi -- (1.5 del 03/02/2022)
                                    where 
                                    id_istanza = p_idistanza
                                    and oi.id_oggetto_istanza= u.id_oggetto_istanza 
                                    );
         DELETE FROM scriva_r_ogg_natura_2000 u where exists (select 1 from scriva_t_oggetto_istanza oi
                                    where 
                                    id_istanza = p_idistanza
                                    and oi.id_oggetto_istanza= u.id_oggetto_istanza 
                                    );

         -- Aree protette oggetti istanza (Oggetto Istanza)
         DELETE FROM scriva_s_ogg_area_protetta u where exists (select 1 from scriva_t_oggetto_istanza oi -- (1.5 del 03/02/2022)
                                    where 
                                    id_istanza = p_idistanza
                                    and oi.id_oggetto_istanza= u.id_oggetto_istanza 
                                    );
         DELETE FROM scriva_r_ogg_area_protetta u where exists (select 1 from scriva_t_oggetto_istanza oi
                                    where 
                                    id_istanza = p_idistanza
                                    and oi.id_oggetto_istanza= u.id_oggetto_istanza 
                                    );

         -- Vincolo autorizzativo (Oggetto Istanza)
         DELETE FROM scriva_s_ogg_vincolo_autorizza u where exists (select 1 from scriva_t_oggetto_istanza oi -- (1.5 del 03/02/2022)
                                    where 
                                    id_istanza = p_idistanza
                                    and oi.id_oggetto_istanza= u.id_oggetto_istanza 
                                    );
         DELETE FROM scriva_r_ogg_vincolo_autorizza u where exists (select 1 from scriva_t_oggetto_istanza oi
                                    where 
                                    id_istanza = p_idistanza
                                    and oi.id_oggetto_istanza= u.id_oggetto_istanza 
                                    );

          -- Categorie (Oggetto Istanza)
         DELETE FROM scriva_s_ogg_istanza_categoria u where exists (select 1 from scriva_t_oggetto_istanza oi
                                    where 
                                    id_istanza = p_idistanza
                                    and oi.id_oggetto_istanza= u.id_oggetto_istanza
                                    );
         DELETE FROM scriva_r_ogg_istanza_categoria u where exists (select 1 from scriva_t_oggetto_istanza oi
                                    where 
                                    id_istanza = p_idistanza
                                    and oi.id_oggetto_istanza= u.id_oggetto_istanza
                                    );

         -- Oggetto Istanza 
         DELETE FROM scriva_s_oggetto_istanza u where exists (select 1 from scriva_t_oggetto_istanza oi
                                    where 
                                    id_istanza = p_idistanza
                                    and oi.id_oggetto_istanza= u.id_oggetto_istanza
                                    );
         DELETE FROM scriva_t_oggetto_istanza u where exists (select 1 from scriva_t_oggetto_istanza oi
                                    where 
                                    id_istanza = p_idistanza
                                    and oi.id_oggetto_istanza= u.id_oggetto_istanza
                                    );


         -- -----------------------------------------------------       
         -- Pagamenti Istanza
         -- -----------------------------------------------------

         DELETE FROM scriva_r_tentativo_dettaglio u where exists (select 1 from scriva_r_pagamento_istanza srpi
                                    where 
                                    id_istanza = p_idistanza
                                    and srpi.id_pagamento_istanza = u.id_pagamento_istanza 
                                    );

         DELETE FROM scriva_t_tentativo_pagamento u where not exists (select 1 from  scriva_r_tentativo_dettaglio dp
                                   where dp.id_tentativo_pagamento  = u.id_tentativo_pagamento 
                                   );

         DELETE FROM scriva_r_pagamento_istanza where id_onere_padre is not null and id_istanza = p_idistanza;
         DELETE FROM scriva_r_pagamento_istanza where id_istanza = p_idistanza;

         -- -----------------------------------------------------       
         -- Allegati Istanza
         -- -----------------------------------------------------

         -- Relazione Allegato Istanza - Integrazione Istanza -- (2.0 del 13/09/2023)
         DELETE FROM scriva_r_integra_istanza_allegato u where exists (select 1 from scriva_t_allegato_istanza oi
                                    where 
                                    id_istanza = p_idistanza
                                    and oi.id_allegato_istanza= u.id_allegato_istanza
                                    );

         DELETE FROM scriva_t_integrazione_istanza where id_istanza = p_idistanza;    -- (2.0 del 13/09/2023)

         -- ATTENZIONE : CANCELLARE FILE SU INDEX!!!!
         ctr_allegati := 0;
         DELETE FROM scriva_t_allegato_istanza where id_istanza = p_idistanza;

         GET DIAGNOSTICS ctr_allegati = ROW_COUNT;

         if ctr_allegati >0 then
            raise notice 'Ricordarsi di eliminare su INDEX gli allegati presenti nella cartella: %', p_idistanza;
         end if;

         -- -----------------------------------------------------       
         -- Notifiche (1.7 del 26/10/2022)
         -- -----------------------------------------------------

         -- Relazione Notifica Allegato -- (2.1 del 14/11/2023)
         DELETE FROM scriva_r_notifica_allegato u where exists (select 1 from scriva_t_notifica oi
                                                                 where id_istanza = p_idistanza
                                                                   and oi.id_notifica= u.id_notifica
                                                               );

         DELETE FROM scriva_r_notifica_applicativa where id_istanza = p_idistanza;
         DELETE FROM scriva_t_notifica where id_istanza = p_idistanza;


         -- -----------------------------------------------------       
         -- Istanza
         -- -----------------------------------------------------

         -- Competenza territorio (Istanza)
         DELETE FROM scriva_s_istanza_competenza where id_istanza = p_idistanza;
         DELETE FROM scriva_r_istanza_competenza where id_istanza = p_idistanza;

         -- Vincoli e autorizzazioni (Istanza)
         DELETE FROM scriva_s_istanza_vincolo_aut where id_istanza = p_idistanza;
         DELETE FROM scriva_r_istanza_vincolo_aut where id_istanza = p_idistanza;


         -- Eventi istanza (Istanza) -- (1.1 del 10/09/2021)
         DELETE FROM scriva_r_istanza_evento where id_istanza = p_idistanza;

         -- Osservazioni (Istanza) -- (1.2 del 18/11/2021)
         DELETE FROM scriva_r_istanza_osservazione where id_istanza = p_idistanza;

         -- Referenti e Attori (Istanza)
         DELETE FROM scriva_r_referente_istanza where id_istanza = p_idistanza;
         DELETE FROM scriva_r_istanza_stato where id_istanza = p_idistanza;
         DELETE FROM scriva_r_istanza_storico where id_istanza = p_idistanza;

         -- Note (Istanza) -- (1.4 del 21/01/2022)
         DELETE FROM scriva_r_nota_istanza where id_istanza = p_idistanza;

         -- Responsabili (Istanza)
         DELETE FROM scriva_r_istanza_responsabile where id_istanza = p_idistanza; -- (1.9 del 08/05/2023)

         -- Istanza
         -- update scriva_t_istanza set id_istanza_attore_owner  = null where id_istanza = p_idistanza; -- Correttiva istanza --	(1.2 del 18/11/2021)
         update scriva_t_istanza 
            set id_istanza_attore_owner = null, 
                id_istanza_attore = null,
                id_funzionario = null
          where id_istanza = p_idistanza;

         DELETE FROM scriva_r_istanza_attore where id_istanza = p_idistanza;
         DELETE FROM scriva_t_istanza where id_istanza = p_idistanza;

         raise notice '% -End  delete istanza : %', now(), p_idistanza;

         esito := 0;
      else 

         RAISE NOTICE 'Istanza % non esiste', p_idistanza; 
         esito := 2;
      end if;
   end;
   return esito;

EXCEPTION WHEN OTHERS then
   esito := 1;
   GET STACKED DIAGNOSTICS 
   t_msg_exc = MESSAGE_TEXT,
   t_dett_exc = PG_EXCEPTION_DETAIL,
   t_hint_exc = PG_EXCEPTION_HINT;
   RAISE NOTICE '% -Errore durante l''esecuzione della funzione', now(); 
   RAISE NOTICE '%', t_msg_exc; 
   RAISE NOTICE '%', t_dett_exc;  
   RAISE NOTICE '%', t_hint_exc;  
   return esito;
END;

$function$
;

ALTER FUNCTION fnc_delete_istanza(p_idistanza integer)                     OWNER TO scriva;

GRANT ALL ON FUNCTION fnc_delete_istanza(p_idistanza integer)              TO public;

GRANT ALL ON FUNCTION fnc_delete_istanza(p_idistanza integer))             TO scriva;

GRANT ALL ON FUNCTION fnc_delete_istanza(p_idistanza integer))             TO scriva_rw;
