package modell;

/**
 * Created by Malte on 22.06.2016.
 */
public class Ausgabe {

    private long id;
    private float betrag;
    private String title;
    private Event event;
    private User user;

    public Ausgabe(long id, float betrag, String title, Event event) {
        this.id = id;
        this.betrag = betrag;
        this.title = title;
        this.event = event;
    }

    public Ausgabe(long id, float betrag, String title, Event event, User user) {
        this.id = id;
        this.betrag = betrag;
        this.title = title;
        this.event = event;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getBetrag() {
        return betrag;
    }

    public void setBetrag(float betrag) {
        this.betrag = betrag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
