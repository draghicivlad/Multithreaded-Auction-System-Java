import java.util.Optional;

/**
 * Clasa Facade ofera functiile asociate comenzilor de la input.
 * Clasa implementeaza Design Pattern-ul Facade pentru a usura utilizarea
 * programului.
 */
public class Facade {

    Administrator administrator;

    public Facade()
    {
        administrator = new Administrator();
    }


    /**
     * @param args argumentele unui client care sunt folosite de ClientFactory
     * pentru a crea un nou client.
     */
    public void NewClient(String []args)
    {
        ClientFactory clientFactory = new ClientFactory();

        Client client = clientFactory.getClient(args);

        administrator.AdaugaClient(client);
    }

    /**
     * @param args argumentele unui produs care sunt folosite de GenericBuilder
     * pentru a crea un nou produs.
     */
    public void NewProdus(String []args)
    {
        Produs produs;

        int id = Integer.parseInt(args[2]);
        String nume = args[3];
        double pretMinim = Double.parseDouble(args[4]);
        int an = Integer.parseInt(args[5]);

        switch (args[1])
        {
            case "Tablou":
                String numePictor = args[6];
                Culori culori = Culori.valueOf(args[7]);

                produs = new GenericBuilder<>(new Tablou())
                        .withId(id)
                        .withNume(nume)
                        .withPretMinim(pretMinim)
                        .withAn(an)
                        .withNumePictor(numePictor)
                        .withCulori(culori)
                        .build();
                break;
            case "Mobila":
                String tip = args[6];
                String material = args[7];

                produs = new GenericBuilder<>(new Mobila())
                        .withId(id)
                        .withNume(nume)
                        .withPretMinim(pretMinim)
                        .withAn(an)
                        .withTip(tip)
                        .withMaterial(material)
                        .build();
                break;
            case "Bijuterie":
                material = args[6];
                boolean piatraPretioasa = Boolean.parseBoolean(args[7]);

                produs = new GenericBuilder<>(new Bijuterie())
                        .withId(id)
                        .withNume(nume)
                        .withPretMinim(pretMinim)
                        .withAn(an)
                        .withPiatraPretioasa(piatraPretioasa)
                        .withMaterial(material)
                        .build();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + args[1]);
        }

        administrator.AdaugaProdus(produs);
    }

    public void newBroker()
    {
        administrator.AdaugaBroker(new Broker());
    }

    /**
     * @param args argumentele unei solicitari pentru o licitatie
     * @throws ProdusInvalidException se arunca in cazul in care produsul pentru
     * care se face licitatia nu exista
     */
    public void SolicitareLicitatie(String []args) throws ProdusInvalidException {
        CasaDeLicitatii casaDeLicitatii = CasaDeLicitatii.getInstance();
        int idClient = Integer.parseInt(args[1]);
        int idProdus = Integer.parseInt(args[2]);
        int pretMaxim = Integer.parseInt(args[3]);

        Client client = null;
        Optional<Client> optionalClient = casaDeLicitatii.getClienti().stream()
                .filter(e -> e.getId() == idClient).findAny();

        if(optionalClient.isPresent())
            client = optionalClient.get();

        Produs produs;
        Optional<Produs> optionalProdus = casaDeLicitatii.getProduseVanzare()
                .stream().filter(e -> e.getId() == idProdus).findAny();

        if(optionalProdus.isPresent()) {
            produs = optionalProdus.get();
            casaDeLicitatii.SolicitareLicitatie(client, produs, pretMaxim);
        }
    }

    public void AfisareProduseClient(String []args)
    {
        CasaDeLicitatii casaDeLicitatii = CasaDeLicitatii.getInstance();
        int idClient = Integer.parseInt(args[1]);

        Optional<Client> optionalClient = casaDeLicitatii.getClienti().stream()
                .filter(e -> e.getId() == idClient).findAny();

        Client client = null;

        if(optionalClient.isPresent())
            client = optionalClient.get();

        casaDeLicitatii.AfisareProduseClient(client);
    }
}