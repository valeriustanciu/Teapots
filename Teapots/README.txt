- Tema 1 IDP
- Echipa Teapots
	- Valeriu - Daniel Stanciu, 342C3
	- Andreea - Gabriela Mindroiu, 342C5



- link github: https://github.com/valeriustanciu/Teapots



- am implementat toate cerintele specificate in enunt, am testat toate functionalitatile cerute,
totul functioneaza



- clasa Mediator realizeaza legatura intre componentele aplicatiei, folosind trei interfete: IMediatorGui,
IMediatorNetwork, IMediatorWeb
- in etapa aceasta a proiectului, modulul network este simulat printr-o clasa MockNetwork
- userii care au acces la aplicatie sunt extrasi din fisierul config.txt
- clasa Web se asigura ca userii care se logheaza la aplicatie exista in baza de date si extrage tipul fiecaruia
- in meniul de logare trebuiesc introduse atat un username existent, cat si parola corecta (ATENTIE: a se
consulta fisierul config.txt), pentru a avea acces la sistemul de licitatie
- dupa logare, in fereastra aplicatiei sunt afisate serviciile oferite/dorite de utilizator si, alaturat,
starea fiecaruia - initial inactiva
- combobox-urile se populeaza pe parcurs, in functie de disponibilitatea serviciilor (in combobox apar userii
ce ofera / doresc serviciul respectiv)
- pentru implementarea progressbar-ului ne-am folosit de sursele din laboratorul 4, pe care le-am adaptat
cerintelor temei



- pentru a retine informatiile necesare unui utilizator, am creat clasa ServiceUserStatus, care contine un
camp cu numele serviciului si o lista de perechi (utilizator, stare serviciu)
- fiecare instanta corespunzatoare unui user va avea un obiect de tipul ArrayList<ServiceUserStatus>
- de asemenea, in lipsa unei retele care sa asigure comunicarea directa intre seller si buyer, am implementat
atat metode care au rolul unor actiuni (ex: makeOffer, acceptOffer), cat si metode care au rolul unor notificari
(ex: sellerMadeOffer, buyerAcceptedOffer)



- avand in vedere ca in enunt nu au fost detaliate anumite lucruri, ne-am permis urmatoarele in implementare:
	- cand un buyer accepta o oferta, transferul serviciului incepe instant, neputand fi observata starea
	"Offer accepted"; considerand ca progress bar-ul are un interval de [0, 10], am impartit afisarea starilor
	astfel: Offer accepted = [0, 1); Transfer started = [1, 2); Transfer in progress = [2, 10); Transfer
	completed = 10
	- in cazul in care un utilizator (buyer) se delogheaza in timpul transferului, progress bar-ul inca se
	incarca pentru userul care a ramas logat, urmand ca atunci cand ajunge la 100% sa fie notificat ca
	transferul a esuat.



- RULARE:
	- se poate folosi Eclipse (Import project, din folderul Teapots)
	- se poate rula si folosind comanda "ant" atat in Windows, cat si in Linux



- TESTARE:
	- avem doua scenarii: unul pentru buyer si unul pentru seller
	- scenariu pentru buyer:
		- login cu username valeriu, password hispass
		- se activeaza "scaun" (click dreapta pe "scaun", "Launch offer request")
		- la 20 de secunde dupa pornirea aplicatiei, seller-ul Gigi va face o oferta (simulare)
		- se selecteaza Gigi din lista si se accepta oferta (click dreapta, "Accept offer")
		- se observa cum se efectueaza transferul
		- dupa 20 de secunde de la acceptarea ofertei, seller-ul Gigi se va deloga (simulare)
		- se observa ca el dispare din lista de useri pentru "scaun"

		- in cazul in care pasii mentionati nu sunt executati in timp util, reies urmatoarele scenarii:
			- daca nu se va activa serviciul in primele 20 de secunde, Gigi nu va mai face nicio oferta
			- daca nu se accepta oferta facuta de Gigi, acesta se va deloga si va disparea din lista 
			userilor pentru "scaun"
			- daca se va intarzia acceptarea ofertei, transferul poate esua, dar userul va ramane in lista,
			cu starea "Transfer failed" asociata

	- scenariu pentru seller:
		- login cu username andreea, password herpass
		- se observa ca, initial, nu exista nici un serviciu activ (presupunem ca nu este nici un buyer
		ce doreste vreun serviciu din lista)
		- la 20 de secunde dupa pornirea aplicatiei, buyerul Grigore va activa serviciul "imprimare"
		(simulare); ca efect, serviciul "imprimare" va aparea activ si se va popula combobox-ul cu useri
		- se selecteaza Grigore din lista si i se face acestuia o oferta (click dreapta, "Make offer")
		- se observa modificarea statusului in "Offer made"
		- dupa 20 de secunde de la aparitia lui Grigore in lista, el va accepta oferta facuta de noi;
		ca efect, vom vedea inceperea transferului

		- in cazul in care pasii mentionati nu sunt executati in timp util, reiese doar urmatorul scenariu:
			- daca in 20 de secunde de la aparitia lui Grigore in lista nu ii vom face o oferta,
			acesta nu o va accepta