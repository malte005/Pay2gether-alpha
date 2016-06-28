package objects;

/**
 * Created by Malte on 22.06.2016.
 */
public class Ausgabe {

    private int id;
    private double betrag;
    private String name;

    public Ausgabe(double betrag, String name) {
        this.betrag = betrag;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBetrag() {
        return betrag;
    }

    public void setBetrag(double betrag) {
        this.betrag = betrag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Ausgabe{" +
                "id=" + id +
                ", betrag=" + betrag +
                ", name='" + name + '\'' +
                '}';
    }
}
