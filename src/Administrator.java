/**
 * Clasa Administrator se ocupa cu adaugarea Clientilor, Produselor si a Broker-ilor
 * in listele corespunzatoare din casa de licitatii.
 */
public class Administrator extends Angajat{
    public Administrator() { }

    public void AdaugaProdus(Produs produs)
    {
        CasaDeLicitatii casaDeLicitatii = CasaDeLicitatii.getInstance();

        casaDeLicitatii.getProduseVanzare().add(produs);
        System.out.println(produs + " a fost adaugat ca PRODUS.\n");
    }

    public void AdaugaClient(Client client)
    {
        CasaDeLicitatii casaDeLicitatii = CasaDeLicitatii.getInstance();

        casaDeLicitatii.getClienti().add(client);
        System.out.println(client + " a fost adaugat ca si CLIENT.\n");
    }

    public void AdaugaBroker(Broker broker)
    {
        CasaDeLicitatii casaDeLicitatii = CasaDeLicitatii.getInstance();

        casaDeLicitatii.getBrokers().add(broker);
        System.out.println("Broker " + casaDeLicitatii.getBrokers().size() + " a fost adaugat\n");
    }
}
