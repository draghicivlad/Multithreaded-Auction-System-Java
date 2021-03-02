/**
 * Clasa Mobila modeleaza o mobila.
 */
public class Mobila extends Produs{
    private String tip;
    private String material;

    public Mobila() { }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return super.toString() + "Mobila{" +
                "tip='" + tip + '\'' +
                ", material='" + material + '\'' +
                '}';
    }
}
