package objects;

/**
 * Created by Malte on 22.06.2016.
 */
public class Ausgabe {

    private long id;
    private double betrag;
    private String title;

    public Ausgabe(long id, double betrag, String title) {
        this.id =id;
        this.betrag = betrag;
        this.title = title;
    }

    public long getId() {
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
        return title;
    }

    public void setName(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Ausgabe{" +
                "id=" + id +
                ", betrag=" + betrag +
                ", name='" + title + '\'' +
                '}';
    }
}
