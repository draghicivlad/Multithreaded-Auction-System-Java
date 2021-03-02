/**
 * Clasa implementeaza Design Pattern-ul Factory pentru a usura crearea de obiecte
 * de tip derivat din Client.
 */
public class ClientFactory {

    public Client getClient(String []args)
    {
        Client client = null;

        int id = Integer.parseInt(args[2]);
        String nume = args[3];
        String adresa = args[4];

        if(args[1].equals("PersoanaFizica"))
        {
            String dataNastere = args[5];
            client = new PersoanaFizica(id, nume, adresa, dataNastere);
        } else if(args[1].equals("PersoanaJuridica")){
            Companie companie = Companie.valueOf(args[5]);
            double capitalSocial = Double.parseDouble(args[6]);
            client = new PersoanaJuridica(id, nume, adresa, companie, capitalSocial);
        }

        return client;
    }
}
