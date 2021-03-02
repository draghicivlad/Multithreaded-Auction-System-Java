/**
 * Clasa modeleaza o persoana juridica cu atributele sale specifice.
 */
public class PersoanaJuridica extends Client{
    private Companie companie;
    private double capitalSocial;

    public PersoanaJuridica() { }

    public PersoanaJuridica(int id, String nume, String adresa, Companie companie,
                            double capitalSocial) {
        super(id, nume, adresa);
        this.companie = companie;
        this.capitalSocial = capitalSocial;
    }

    public Companie getCompanie() {
        return companie;
    }

    public void setCompanie(Companie companie) {
        this.companie = companie;
    }

    public double getCapitalSocial() {
        return capitalSocial;
    }

    public void setCapitalSocial(double capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    @Override
    public String toString() {
        return super.toString() + "PersoanaJuridica{" +
                "companie=" + companie +
                ", capitalSocial=" + capitalSocial +
                '}';
    }
}
