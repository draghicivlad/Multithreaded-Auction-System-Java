import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Clasa modeleaza o Casa de Licitatii. Clasa foloseste Design Pattern-ul
 * Singleton.
 * Aceasta contine listele produselor, clientilor, broker-ilor si a licitatiilor
 * curente in Casa de Licitatii.
 */
public class CasaDeLicitatii {
    private static CasaDeLicitatii instance;

    private final List<Produs> produseVanzare;
    private final List<Client> clienti;
    private final List<Broker> brokers;
    private final List<Licitatie> licitatii;
    private int numarLicitatie = 1000;

    private CasaDeLicitatii()
    {
        produseVanzare = Collections.synchronizedList(new ArrayList<>());
        clienti = Collections.synchronizedList(new ArrayList<>());
        brokers = Collections.synchronizedList(new ArrayList<>());
        licitatii = Collections.synchronizedList(new ArrayList<>());
    }

    public static CasaDeLicitatii getInstance()
    {
        if (instance == null)
            instance = new CasaDeLicitatii();
        return instance;
    }

    public List<Produs> getProduseVanzare() {
        return produseVanzare;
    }

    public List<Client> getClienti() {
        return clienti;
    }

    public List<Broker> getBrokers() {
        return brokers;
    }

    public List<Licitatie> getLicitatii() {
        return licitatii;
    }

    /**
     * Functia afiseaza produsele disponibile pentru un client
     * @param client clientul care doreste sa vizualizeze produsele disponibile
     * @throws ClientNegasitException exceptie in cazul in care clientul nu exista
     */
    public void AfisareProduseClient(Client client) throws ClientNegasitException
    {
        if(!clienti.contains(client))
            throw new ClientNegasitException();

        System.out.println(client + " a vazut produsele:");

        for(Produs produs : produseVanzare)
            System.out.println("\t" + produs);
    }

    /**
     * Functia gestioneaza o solicitare de la un client pentru a participa intr-o
     * licitatie pentru un produs anume.
     * Functia cauta daca exista vreo licitatie in asteptare pentru produsul
     * cerut si in cazut in care nu gaseste niciuna creeaza o noua licitatie.
     * @param client clientul care doreste sa intre in licitatie
     * @param produs produsul care este dorit
     * @param pretMaxim pretul maxim pe care il poate oferi clientul
     * @throws ProdusInvalidException exceptie in cazul in care produsul solicitat
     * nu exista
     */
    public synchronized void SolicitareLicitatie(Client client, Produs produs, int pretMaxim)
            throws ProdusInvalidException {
        if(produs == null)
            throw new ProdusInvalidException();

        Licitatie licitatie;
        Optional<Licitatie> optionalLicitatie = licitatii.stream().filter(e ->
                e.getProdus().getId() == produs.getId()).findAny();

        if(optionalLicitatie.isEmpty()) {
            licitatie = new Licitatie(numarLicitatie++, 2 +
                    (int)(Math.random() * 3), produs, 3 + (int)(Math.random() * 4));
            licitatii.add(licitatie);

            for(Broker broker : brokers)
                broker.getClienti().add(Collections.synchronizedList(new ArrayList<>()));

            new Thread(licitatie).start();
            System.out.println(licitatie + " a fost creata.\n");
        }
        else {
            licitatie = optionalLicitatie.get();
            if(licitatie.getNrParticipanti() >= licitatie.getNrDoritParticipanti()) {
                System.out.println(client + " nu participa in licitatie!\n\t"
                        + licitatie + " este in desfasurare!");
                return;
            }
        }

        int indexOfBroker = ThreadLocalRandom.current().nextInt(0, brokers
                    .size());
        List<List<Client>> clienti = brokers.get(indexOfBroker).getClienti();

        int indiceLicitatie = licitatii.indexOf(licitatie);

        clienti.get(indiceLicitatie).add(client);

        System.out.println(client + " a fost asociat cu Broker " + (indexOfBroker + 1)
                + ".\n\tParticipa la " + licitatie + ".\n");

        client.getLicitatiiCurente().add(licitatie);
        client.getPreturiMaximeOferite().add((double) pretMaxim);
        client.getUltimelePreturiOferite().add(0d);

        licitatie.setNrParticipanti(licitatie.getNrParticipanti() + 1);
    }

    /**
     * Functia cere preturile oferita intr-o licitatie de la toti clientii prin
     * intermediul broker-ilor asociati.
     * @param licitatieActuala licitatia in cauza
     * @param pretulMaximCurent pretul maxim oferit in pasul precedent al
     *                          licitatie
     * @return lista de preturi oferite
     */
    public synchronized List<Double> SolicitarePreturi(Licitatie licitatieActuala,
                                                       Double pretulMaximCurent)
    {
        List<Double> preturiOferiteTotal = new ArrayList<>();
        for(Broker broker : brokers)
        {
            List<Double> preturiOferite = broker.AnuntaClienti(licitatieActuala, pretulMaximCurent);
            if(preturiOferite != null)
                preturiOferiteTotal.addAll(preturiOferite);
        }
        return  preturiOferiteTotal;
    }
}