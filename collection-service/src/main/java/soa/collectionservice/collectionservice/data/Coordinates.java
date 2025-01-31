package soa.collectionservice.collectionservice.data;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Objects;

@XmlRootElement(name = "coordinates")
@XmlAccessorType(XmlAccessType.FIELD)
public class Coordinates {

    @XmlElement(name = "x", required = true)
    private final long x; //Максимальное значение поля: 510

    @XmlElement(name = "y", required = true)
    private final Long y; //Поле не может быть null

    public Coordinates(long x, Long y) {
        if (x > 510) throw new IllegalArgumentException("x value cannot be greater than 510");
        if (Objects.isNull(y)) throw new IllegalArgumentException("y value cannot be null");
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return x;
    }

    public Long getY() {
        return y;
    }
}
