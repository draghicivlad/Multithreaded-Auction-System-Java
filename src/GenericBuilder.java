/**
 * Clasa implementeaza Design Pattern-ul Builder pentru a usura crearea de obiecte
 * de tip produs.
 * Clasa este generica pentru a permite crearea de obiecte de tip derivat din clasa
 * Produs(Tablou, Mobila, Bijuterie).
 * @param <E> produs generic
 */
public class GenericBuilder <E extends Produs>{
    E produs;

    public GenericBuilder(E produsNou)
    {
        this.produs = produsNou;
    }

    public GenericBuilder<E> withId(int id)
    {
        produs.setId(id);
        return this;
    }

    public GenericBuilder<E> withNume(String nume)
    {
        produs.setNume(nume);
        return this;
    }

    public GenericBuilder<E> withPretMinim(double pretMinim)
    {
        produs.setPretMinim(pretMinim);
        return this;
    }

    public GenericBuilder<E> withAn(int an)
    {
        produs.setAn(an);
        return this;
    }

    public GenericBuilder<E> withNumePictor(String numePictor)
    {
        if(produs instanceof Tablou)
            ((Tablou)produs).setNumePictor(numePictor);
        return this;
    }

    public GenericBuilder<E> withCulori(Culori culori)
    {
        if(produs instanceof Tablou)
            ((Tablou)produs).setCulori(culori);
        return this;
    }

    public GenericBuilder<E> withTip(String tip)
    {
        if(produs instanceof Mobila)
            ((Mobila)produs).setTip(tip);
        return this;
    }

    public GenericBuilder<E> withMaterial(String material)
    {
        if(produs instanceof Mobila)
            ((Mobila)produs).setMaterial(material);
        if(produs instanceof Bijuterie)
            ((Bijuterie)produs).setMaterial(material);
        return this;
    }

    public GenericBuilder<E> withPiatraPretioasa(boolean piatraPretioasa)
    {
        if(produs instanceof Bijuterie)
            ((Bijuterie)produs).setPiatraPretioasa(piatraPretioasa);
        return this;
    }

    public E build()
    {
        return produs;
    }
}
