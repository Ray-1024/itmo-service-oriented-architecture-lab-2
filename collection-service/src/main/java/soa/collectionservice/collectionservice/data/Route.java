package soa.collectionservice.collectionservice.data;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Date;
import java.util.Objects;

@XmlRootElement(name = "route")
@XmlAccessorType(XmlAccessType.FIELD)
public class Route {

    @XmlElement(name = "id")
    private final long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @XmlElement(name = "name", required = true)
    private final String name; //Поле не может быть null, Строка не может быть пустой

    @XmlElement(name = "coordinates", required = true)
    private final Coordinates coordinates; //Поле не может быть null

    @XmlElement(name = "creationDate")
    private final Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @XmlElement(name = "from", required = true)
    private final Location from; //Поле может быть null

    @XmlElement(name = "to", required = true)
    private final Location to; //Поле не может быть null

    @XmlElement(name = "distance", required = true)
    private final int distance; //Значение поля должно быть больше 1

    public Route(long id, String name, Coordinates coordinates, Date creationDate, Location from, Location to, int distance) {
        if (id < 0) throw new IllegalArgumentException("Id cannot be negative");
        if (Objects.isNull(name) || name.isEmpty()) throw new IllegalArgumentException("Name cannot be null or empty");
        if (Objects.isNull(coordinates)) throw new IllegalArgumentException("Coordinates cannot be null");
        if (Objects.isNull(creationDate)) throw new IllegalArgumentException("CreationDate cannot be null");
        if (Objects.isNull(to)) throw new IllegalArgumentException("ToLocation cannot be null");
        if (distance <= 1) throw new IllegalArgumentException("Distance cannot be less than 1 or equal to 1");

        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

    public int getDistance() {
        return distance;
    }
}
