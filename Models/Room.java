package Models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Room
{
    private int number;
    private int beds;
    private boolean occupied;

    public Room(int number, int beds, boolean occupied)
    {
        this.number = number;
        this.beds = beds;
        this.occupied = occupied;
    }
}
