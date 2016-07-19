package modell;

/**
 * Created by Malte on 15.07.2016.
 */
public class Schulden {

    private long id;
    private float betrag;
    private User schuldenVon;
    private User schuldenAn;

    public Schulden(long id, float betrag, User schuldenVon, User schuldenAn) {
        this.id = id;
        this.betrag = betrag;
        this.schuldenVon = schuldenVon;
        this.schuldenAn = schuldenAn;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getBetrag() {
        return betrag;
    }

    public void setBetrag(float betrag) {
        this.betrag = betrag;
    }

    public User getSchuldenVon() {
        return schuldenVon;
    }

    public void setSchuldenVon(User schuldenVon) {
        this.schuldenVon = schuldenVon;
    }

    public User getSchuldenAn() {
        return schuldenAn;
    }

    public void setSchuldenAn(User schuldenAn) {
        this.schuldenAn = schuldenAn;
    }

    @Override
    public String toString() {
        return "Schulden{" +
                "id=" + id +
                ", betrag=" + betrag +
                ", schuldenVon=" + schuldenVon +
                ", schuldenAn=" + schuldenAn +
                '}';
    }
}
