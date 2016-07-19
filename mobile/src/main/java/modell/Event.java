package modell;

import java.sql.Date;
import java.util.List;

/**
 * Created by Malte on 22.06.2016.
 */
public class Event {

    private long id;
    private Date datum;
    private String title;
    private List<User> user;
    private List<Ausgabe> ausgaben;

    public Event(long id, Date datum, String title) {
        this.id = id;
        this.datum = datum;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public List<Ausgabe> getAusgaben() {
        return ausgaben;
    }

    public void setAusgaben(List<Ausgabe> ausgaben) {
        this.ausgaben = ausgaben;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", datum=" + datum +
                ", title='" + title + '\'' +
                ", user=" + user +
                ", ausgaben=" + ausgaben +
                '}';
    }
}
