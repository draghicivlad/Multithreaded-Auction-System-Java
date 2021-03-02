import java.util.*;

/**
 * Clasa modeleaza o licitatie. Aceasta contine informatiile despre o licitatie.
 * Clasa implementeaza interfata Runnable pentru a putea rula mai multe licitatii
 * in paralel.
 */
public class Licitatie implements Runnable{
    private int id;
    private int nrDoritParticipanti;
    private int nrParticipanti;
    private Produs produs;
    private int nrPasiActual;
    private int nrPasiMaxim;
    private double pretulMaximCurent;
    private boolean aInceput = false;

    public Licitatie() { }

    public Licitatie(int id, int nrDoritParticipanti, Produs produs, int nrPasiMaxim) {
        this.id = id;
        this.nrDoritParticipanti = nrDoritParticipanti;
        this.produs = produs;
        this.nrPasiMaxim = nrPasiMaxim;
        this.nrParticipanti = 0;
        this.nrPasiActual = 0;
        this.pretulMaximCurent = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNrParticipanti() {
        return nrParticipanti;
    }

    public void setNrParticipanti(int nrParticipanti) {
        this.nrParticipanti = nrParticipanti;
    }

    public Produs getProdus() {
        return produs;
    }

    public void setIdProdus(Produs Produs) {
        this.produs = Produs;
    }

    public int getNrPasiMaxim() {
        return nrPasiMaxim;
    }

    public void setNrPasiMaxim(int nrPasiMaxim) {
        this.nrPasiMaxim = nrPasiMaxim;
    }

    public int getNrDoritParticipanti() {
        return nrDoritParticipanti;
    }

    public void setNrDoritParticipanti(int nrDoritParticipanti) {
        this.nrDoritParticipanti = nrDoritParticipanti;
    }

    @Override
    public String toString() {
        return "Licitatie{" +
                "id=" + id +
                ", nrDoritParticipanti=" + nrDoritParticipanti +
                ", idProdus=" + produs.getId() +
                ", nrPasiMaxim=" + nrPasiMaxim +
                ", nrPasiActual=" + nrPasiActual +
                '}';
    }

    /**
     * Functia pune thread-ul in asteptare(cu Thread.yield()) pana cand numarul
     * de participanti atinge pragul minim. Apoi functia pentru fiecare pas
     * al licitatiei apeleaza functia ProceseazaPas.
     */
    @Override
    public synchronized void run() {
        CasaDeLicitatii casaDeLicitatii = CasaDeLicitatii.getInstance();

        while(nrPasiActual < nrPasiMaxim)
        {
            if(nrParticipanti == nrDoritParticipanti)
            {
                if(!aInceput)
                {
                    aInceput = true;
                    System.out.println(this + " a inceput!\n");
                }
                ProceseazaPas();

            } else { Thread.yield(); }
        }
        try {
            Thread.sleep(Constants.DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int indiceLicitatie = casaDeLicitatii.getLicitatii().indexOf(this);

        for(Broker broker : casaDeLicitatii.getBrokers()) {
            for(Client client : broker.getClienti().get(indiceLicitatie))
            {
                client.setNrParticipari(client.getNrParticipari() + 1);
            }
            broker.getClienti().remove(indiceLicitatie);
        }

        casaDeLicitatii.getBrokers().get(0).StergeProdus(produs);
        casaDeLicitatii.getLicitatii().remove(indiceLicitatie);
    }

    /**
     * Functia obtine preturile oferite de clienti, calculeaza pretul maxim si
     * anunta celelalte entitati daca s-a ajuns intr-o stare finala a licitatiei.
     */
    public synchronized void ProceseazaPas()
    {
        nrPasiActual++;
        System.out.println("Pasul " + nrPasiActual + " din: " + this + "\n");

        CasaDeLicitatii casaDeLicitatii = CasaDeLicitatii.getInstance();
        List<Double> preturiOferiteTotal = casaDeLicitatii.SolicitarePreturi(this,
                pretulMaximCurent);

        if(preturiOferiteTotal.size() == 0)
        {
            this.nrPasiActual = this.nrPasiMaxim + 1;
            System.out.println(this + " s-a incheiat! Rezultat: \n\tLicitatia " +
                    "nu a fost castigata de nimeni!\n");
            try {
                Thread.sleep(Constants.DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }

        Collections.sort(preturiOferiteTotal);
        pretulMaximCurent = preturiOferiteTotal.get(preturiOferiteTotal.size() - 1);

        System.out.println("Pretul maxim din pasul " + nrPasiActual + ": "
                + pretulMaximCurent + " din: " + this + "\n");

        if(preturiOferiteTotal.size() == 1 || nrPasiActual == nrPasiMaxim)
        {
            this.nrPasiActual = this.nrPasiMaxim + 1;
            Client client = null;

            for(Broker broker : casaDeLicitatii.getBrokers())
            {
                client = broker.CautaClientDupaPret(this, pretulMaximCurent);
                if(client != null)
                    break;
            }
            if (client != null) {
                client.setNrLicitatiiCastigate(client.getNrLicitatiiCastigate() + 1);
            }
            System.out.println(this + " s-a incheiat! Rezultat: \n\tLicitatia " +
                    "a fost castigata de: " + client + "\n\tS-a vandut " + produs
                    + " la pretul " + pretulMaximCurent);
            try {
                Thread.sleep(Constants.DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
