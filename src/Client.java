import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clasa modeleaza un client. Clasa contine informatiile despre client si ofera
 * functionalitatea clientului de a oferi un pret intr-o licitatie.
 */
public class Client {
    private int id;
    private String nume;
    private String adresa;
    private int nrParticipari;
    private int nrLicitatiiCastigate;
    private List<Licitatie> licitatiiCurente;
    private List<Double> preturiMaximeOferite;
    private List<Double> ultimelePreturiOferite;

    public Client() { }

    public Client(int id, String nume, String adresa) {
        this.id = id;
        this.nume = nume;
        this.adresa = adresa;
        this.nrParticipari = 0;
        this.nrLicitatiiCastigate = 0;
        preturiMaximeOferite = Collections.synchronizedList(new ArrayList<>());
        licitatiiCurente = Collections.synchronizedList(new ArrayList<>());
        ultimelePreturiOferite = Collections.synchronizedList(new ArrayList<>());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public int getNrParticipari() {
        return nrParticipari;
    }

    public void setNrParticipari(int nrParticipari) {
        this.nrParticipari = nrParticipari;
    }

    public int getNrLicitatiiCastigate() {
        return nrLicitatiiCastigate;
    }

    public void setNrLicitatiiCastigate(int nrLicitatiiCastigate) {
        this.nrLicitatiiCastigate = nrLicitatiiCastigate;
    }

    public List<Licitatie> getLicitatiiCurente() {
        return licitatiiCurente;
    }

    public void setLicitatiiCurente(List<Licitatie> licitatiiCurente) {
        this.licitatiiCurente = licitatiiCurente;
    }

    public List<Double> getPreturiMaximeOferite() {
        return preturiMaximeOferite;
    }

    public void setPreturiMaximeOferite(List<Double> preturiMaximeOferite) {
        this.preturiMaximeOferite = preturiMaximeOferite;
    }

    public List<Double> getUltimelePreturiOferite() {
        return ultimelePreturiOferite;
    }

    public void setUltimelePreturiOferite(List<Double> ultimelePreturiOferite) {
        this.ultimelePreturiOferite = ultimelePreturiOferite;
    }

    /**
     * Functia calculeaza pretul oferit de client intr-o licitatie in functie
     * de pretul maxim oferit la pasul trecut.
     * @param licitatie licitatia pentru care se ofera pretul
     * @param pretulMaxim pretul maxim precedent
     * @return pretul oferit de client
     */
    public synchronized double OferaPret(Licitatie licitatie, double pretulMaxim)
    {
        int indiceLicitatie = licitatiiCurente.indexOf(licitatie);
        double pretMaximClient = preturiMaximeOferite.get(indiceLicitatie);

        double pretMinimProdus = licitatie.getProdus().getPretMinim();

        try {
            Thread.sleep(Constants.DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(pretMinimProdus > pretMaximClient || pretulMaxim > pretMaximClient)
        {
            System.out.println(toString() + " s-a retras din:\n\t" + licitatie);

            licitatiiCurente.remove(indiceLicitatie);
            preturiMaximeOferite.remove(indiceLicitatie);
            ultimelePreturiOferite.remove(indiceLicitatie);
            return -1;
        }

        double pretOferit = pretulMaxim + (pretMaximClient - pretulMaxim) *
                Math.random() * 0.5d;

        ultimelePreturiOferite.set(indiceLicitatie, pretOferit);

        System.out.println(toString() + " a oferit pretul " + pretOferit +
                " in:\n\t" + licitatie);
        return pretOferit;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", adresa='" + adresa + '\'' +
                '}';
    }
}
