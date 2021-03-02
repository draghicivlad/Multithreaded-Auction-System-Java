Draghici Vlad Matei 322CB

Programul contine urmatoarele clase:
1. Main - contine functia main
	- in functia main se face parsarea comenzilor de la input

	- dupa identificarea comenzii curente se apeleaza functia asociata din clasa
		Facade

2. Facade - Facade Design Pattern
	- clasa Facade contine cate o metoda asociata fiecarei comenzi de la input

	- Facade se asigura de apelarea functiilor din celelalte clase implementate
		in program si din main trebuie doar sa se apeleze metodele din main
		facand folosirea aplicatiei foarte usoara pentru o alta persoana
		care vrea sa foloseasca aplicatia

3. CasaDeLicitatii - Singleton Design Pattern
	- contine listele produselor, clientilor, broker-ilor si a licitatiilor
		curente in Casa de Licitatii

	- functii:	* AfisareProduseClient: Functia afiseaza produsele disponibile
					pentru un client

				* SolicitareLicitatie: Functia gestioneaza o solicitare de la 
					un client pentru a participa intr-o licitatie pentru un produs anume

				* SolicitarePreturi: Functia cere preturile oferita intr-o licitatie
					de la toti clientii prin intermediul broker-ilor asociati

4. Licitatie - implementeaza Runnable
	- modeleaza o licitatie si contine informatiile despre aceasta

	- implementeaza interfata Runnable pentru a putea rula mai multe licitatii
		in paralel

	- functia run: Functia pune thread-ul in asteptare(cu Thread.yield()) pana
		cand numarul de participanti atinge pragul minim. Apoi functia pentru fiecare
		pas al licitatiei apeleaza functia ProceseazaPas.

	- functia ProceseazaPas: Functia obtine preturile oferite de clienti, calculeaza
		pretul maxim si anunta celelalte entitati daca s-a ajuns intr-o stare finala
		a licitatiei.

5. GenericBuilder - Builder Design Pattern; Generic
	- implementeaza Design Pattern-ul Builder pentru a usura crearea de obiecte
		de tip produs

	- este generica pentru a permite crearea de obiecte de tip derivat din clasa
		Produs(Tablou, Mobila, Bijuterie)

6. ClientFactory - Factory Design Pattern
	- Clasa implementeaza Design Pattern-ul Factory pentru a usura crearea de
		obiecte de tip derivat din Client

7. Client
	- contine informatiile despre client si ofera functionalitatea clientului
		de a oferi un pret intr-o licitatie

	- functia OferaPret: calculeaza pretul oferit de client intr-o licitatie in
		functie de pretul maxim oferit la pasul trecut

8. Broker
	- face legatura dintre Casa de Licitatii si clientii acesteia

	- contine o lista de liste de clienti: cate o lista de clienti pentru fiecare
		licitatie in desfasurare

	- functia AnuntaClienti: aduna preturile oferite de clientii broker-ului curent
		pentru o licitatie

	- functia CautaClientDupaPret: cauta un client care este asociat broker-ului
		curent dupa ultimul pret pe care l-a oferit intr-o licitatie