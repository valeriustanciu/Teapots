- Tema 2 IDP
- Echipa Teapots
	- Valeriu - Daniel Stanciu, 342C3
	- Andreea - Gabriela Mindroiu, 342C5



- link github: https://github.com/valeriustanciu/Teapots



- am implementat toate cerintele specificate in enunt, am si testat, de asemenea,
totul functioneaza conform specificatiilor



- IMPLEMENTARE:

- la initializarea aplicatiei, modulul de retea afla de existenta celorlalti useri,
adresele lor ip si porturile pe care asculta (un obiect de tip NetworkInfo, care
mentine informatii despre toti utilizatorii logati)
- aceste informatii se retin dinamic intr-un fisier loggedUsers.txt (in functie de
ce useri sunt logati)
- o rulare porneste 4 instante ale aplicatiei, pe care se logheaza urmatorii useri:
	- username: andreea; password: herpass;
	- username: valeriu; password: hispass;
	- username: student; password: nopass;
	- username: unchiasu; password: smecherpass


- pentru comunicarea peste retea am folosit Java NIO, cu 2 tipuri de threaduri:
ReadThread si WriteThread
- pentru partea de receptionare avem un thread Read care asculta pe un port, accepta
conexiuni si primeste mesaje
- procesarea mesajelor se face intr-o clasa MessageHandler, specializata pe
interpretarea mesajelor
- pentru partea de scriere, avem mai multe threaduri Write care trimit mesaje /
transfera documente (se conecteaza la ReadThread-uri)


- am creeat o structura "Message", pe care o serializam si o transmitem pe retea
- aceasta structura contine informatii despre expeditor, destinatar, tipul actiunii
si mesajul / parametrii actiunii


- avem cate un fisier .txt pentru fiecare tip de serviciu (acestea sunt de forma
"nume_serviciu.txt", de dimensiuni variabile); aceste fisiere reprezinta "bunul"
tranzactionat intre useri; ele sunt transmite pe retea, in cazul unei tranzactii
reusite; la destinatie sunt salvate ca "downloaded_nume_serviciu.txt"


- pentru logare am folosit log4j
- logurile se salveaza in fisiere de forma "username_log.txt"
- de asemenea, am afisat si la consola unele informatii



- RULARE:
- se poate folosi Eclipse (Import project, din folderul Teapots)
- se poate rula si folosind comanda "ant" atat in Windows, cat si in Linux