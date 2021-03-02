import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Clasa Broker face legatura dintre Casa de Licitatii si clientii acesteia.
 * Clasa contine o lista de liste de clienti: cate o lista de clienti pentru
 * fiecare licitatie in desfasurare.
 */
public class Broker extends Angajat{
    private final List<List<Client>> clienti;

    public Broker() {
        clienti = Collections.synchronizedList(new ArrayList<>());
    }

    public List<List<Client>> getClienti() {
        return clienti;
    }

    /**
     * @param produs produsul care trebuie sters din Casa de Licitatii
     */
    public void StergeProdus(Produs produs)
    {
        CasaDeLicitatii casaDeLicitatii = CasaDeLicitatii.getInstance();

        casaDeLicitatii.getProduseVanzare().remove(produs);
    }

    /**
     * Functia aduna preturile oferite de clientii broker-ului curent pentru o licitatie.
     * @param licitatie licitatia curenta pentru care se cer preturile de la clienti
     * @param pretulMaximRundaTrecuta pretul maxim oferit de un client in runda trecuta
     * @return lista de preturi oferite de clientii broker-ului curent
     */
    public synchronized List<Double> AnuntaClienti(Licitatie licitatie,
                                                   double pretulMaximRundaTrecuta)
    {
        CasaDeLicitatii casaDeLicitatii = CasaDeLicitatii.getInstance();

        int indiceLicitatie = casaDeLicitatii.getLicitatii().indexOf(licitatie);
        List<Client> clientiiLicitatie = clienti.get(indiceLicitatie);

        if(clientiiLicitatie.size() == 0)
            return null;

        List<Double> preturiOferite = new ArrayList<>();
        for(Iterator<Client> iter = clientiiLicitatie.iterator(); iter.hasNext();)
        {
            Client client = iter.next();

            if(!client.getLicitatiiCurente().contains(licitatie))
                continue;

            try {
                Thread.sleep(Constants.DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            double pretOferit = client.OferaPret(licitatie, pretulMaximRundaTrecuta);

            if(pretOferit > 0)
                preturiOferite.add(pretOferit);
            else
                iter.remove();
        }

        return preturiOferite;
    }

    /**
     * Functia cauta un client care este asociat broker-ului curent dupa ultimul
     * pret pe care l-a oferit intr-o licitatie.
     * @param licitatie licitatia in care s-a oferit pretul
     * @param pret ultimul pret oferit in licitatie
     * @return clientul care a oferit pretul
     */
    public synchronized Client CautaClientDupaPret(Licitatie licitatie, double pret)
    {
        CasaDeLicitatii casaDeLicitatii = CasaDeLicitatii.getInstance();

        int indiceLicitatie = casaDeLicitatii.getLicitatii().indexOf(licitatie);
        List<Client> clientiiLicitatie = clienti.get(indiceLicitatie);

        for(Client client : clientiiLicitatie)
        {
            double ultimulPret = client.getUltimelePreturiOferite().get(client
                    .getLicitatiiCurente().indexOf(licitatie));

            if(ultimulPret == pret)
                return client;
        }
        return null;
    }
}
