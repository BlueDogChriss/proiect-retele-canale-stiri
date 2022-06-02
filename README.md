# proiect-retele-canale-stiri
## Subscrierea si notificarea pentru canale de stiri:
###○ Server-ul gestioneaza o serie de canale identificate prin nume si descriere;
###○ Clientii se conecteaza la server si primesc lista canalelor;
###○ Un client poate publica un canal, caz in care server-ul notifica toti clientii
conectati pentru a putea subscrie la el;
###○ Un client poate sterge un canal publicat de el, caz in care server-ul notifica toti
clientii conectati;
###○ Un client poate subscrie la un canal pentru a fi notificat;
###○ Un client poate renunta la subscrierea la un anumit canal, caz in care nu va
mai primi stirile publicate pe canalul respectiv;
###○ Clientul care a publicat un canal poate trimite stiri pe acel canal, caz in care
toti clientii care au subscris la el vor primi stirile respective;
###○ Server-ul filtreaza continutul stirilor pentru a le bloca pe acelea care contin
anumite cuvinte predefinite care nu sunt permise, caz in care clientii care au
subscris la canalul pe care este livrata stirea nu vor mai fi notificati.
