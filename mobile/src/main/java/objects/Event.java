package objects;

import java.sql.Date;

/**
 * Created by Malte on 22.06.2016.
 */
public class Event {

    private int id;
    private Date datum;
    private String title;

    public Event(Date datum, String title) {
        this.datum = datum;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", datum=" + datum +
                ", title='" + title + '\'' +
                '}';
    }
}
