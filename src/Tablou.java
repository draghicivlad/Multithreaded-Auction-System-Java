public class Tablou extends Produs{
    private String numePictor;
    private Culori culori;

    public Tablou() { }

    public String getNumePictor() {
        return numePictor;
    }

    public void setNumePictor(String numePictor) {
        this.numePictor = numePictor;
    }

    public Culori getCulori() {
        return culori;
    }

    public void setCulori(Culori culori) {
        this.culori = culori;
    }

    @Override
    public String toString() {
        return super.toString() +  "Tablou{" +
                "numePictor='" + numePictor + '\'' +
                ", culori=" + culori +
                '}';
    }
}
