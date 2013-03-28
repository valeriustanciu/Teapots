IDP - Tema 1

Echipa Teapots 
	- Stanciu Valeriu-Daniel, 342C3
	- Mindroiu Andreea-Gabriela, 342C5
	
	
	Arhitectura descrisa in enunt este respectata in implementarea temei. Clasa Mediator realizeaza legatura intre componentele aplicatiei, folosind trei interfete: IMediatorGui, IMediatorNetwork, IMediatorWeb. Reteaua este simulata printr-un MockNetwork si trateaza doua cazuri simpliste (unul pentru buyer si unul pentru seller). Userii care au acces la aplicatie sunt extrasi din fisierul config.txt. Clasa Web se asigura ca userii care se logheaza la aplicatie exista in baza de date si extrage tipul fiecaruia. In meniul de logare trebuiesc introduse atat un username existent, cat si parola corecta (a se consulta fisierul config.txt), pentru a avea acces la sistemul de licitatie. Dupa logare, in fereastra aplicatiei sunt afisate serviciile oferite/dorite de utilizator si, alaturat, starea fiecaruia - initial inactiva. Combobox-urile se populeaza pe parcurs, in functie de disponibilitatea serviciilor. Pentru implementarea ProgressBar-ului, ne-am folosit de sursele din laboratorul 4, pe care le-am adaptat cerintelor temei. 

	Pentru a retine informatiile necesare unui utilizator, am creat clasa ServiceUserStatus, care contine un camp cu numele serviciului si o lista de perechi (utilizator, stare serviciu). Fiecare instanta corespunzatoare unui user va avea un obiect de tipul ArrayList<ServiceUserStatus>. De asemenea, in lipsa unei retele care sa asigure comunicarea directa intre seller si buyer, am implementat atat metode care au rolul unor actiuni (ex: makeOffer, acceptOffer), cat si metode care au rolul unor notificari (ex: sellerMadeOffer, buyerAcceptedOffer).

	Avand in vedere ca in enunt nu au fost detaliate anumite lucruri, ne-am permis urmatoarele in implementare:
		- cand un buyer accepta o oferta, transferul serviciului incepe instant, neputand fi observata starea "Offer accepted". Considerand ca progress bar-ul are un interval de [0, 10], am impartit afisarea starilor astfel: Offer accepted = [0, 1); Transfer started = [1, 2); Transfer in progress = [2, 10); Transfer completed = 10;
		- in cazul in care un utilizator (buyer) se delogheaza in timpul transferului, progress bar-ul inca se incarca pentru userul care a ramas logat, urmand ca atunci cand ajunge la 100% sa fie notificat ca transferul a esuat.