/**
 * Clasa Bijuterie modeleaza o bijuterie.
 */
public class Bijuterie extends Produs{
    private String material;
    private boolean piatraPretioasa;

    public Bijuterie() { }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public boolean isPiatraPretioasa() {
        return piatraPretioasa;
    }

    public void setPiatraPretioasa(boolean piatraPretioasa) {
        this.piatraPretioasa = piatraPretioasa;
    }

    @Override
    public String toString() {
        return super.toString() + "Bijuterie{" +
                "material='" + material + '\'' +
                ", piatraPretioasa=" + piatraPretioasa +
                '}';
    }
}
