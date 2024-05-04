package src.main.java.Models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class HotelSystem
{
    @XmlElement
    private List<Room> rooms = new ArrayList<>();
    @XmlElement
    private List<Guest> guests = new ArrayList<>();

    public void setRooms(List<Room> rooms)
    {
        this.rooms = rooms;
    }
}
