package soa.collectionservice.collectionservice.data;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Objects;

@XmlRootElement(name = "location")
@XmlAccessorType(XmlAccessType.FIELD)
public class Location {

    @XmlElement(name = "x", required = true)
    private final int x;

    @XmlElement(name = "y", required = true)
    private final int y;

    @XmlElement(name = "x", required = true)
    private final double z;

    @XmlElement(name = "name", required = true)
    private final String name; //Длина строки не должна быть больше 452, Поле не может быть null

    public Location(int x, int y, double z, String name) {
        if (Objects.isNull(name) || name.length() > 452)
            throw new IllegalArgumentException("Name cannot be null and cannot has greater length than 452");
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public String getName() {
        return name;
    }
}
