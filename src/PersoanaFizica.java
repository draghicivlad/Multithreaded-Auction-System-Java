/**
 * Clasa modeleaza o persoana fizica cu atributele sale specifice.
 */
public class PersoanaFizica extends Client{
    private String dataNastere;

    public PersoanaFizica() { }

    public PersoanaFizica(int id, String nume, String adresa, String dataNastere) {
        super(id, nume, adresa);
        this.dataNastere = dataNastere;
    }

    public String getDataNastere() {
        return dataNastere;
    }

    public void setDataNastere(String dataNastere) {
        this.dataNastere = dataNastere;
    }

    @Override
    public String toString() {
        return super.toString() + "PersoanaFizica{" +
                "dataNastere='" + dataNastere + '\'' +
                '}';
    }
}
