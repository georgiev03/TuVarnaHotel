package Models;

import java.util.ArrayList;
import java.util.List;

public class HotelSystem
{
    private List<Room> rooms;
    private List<Guest> guests;

    public HotelSystem(List<Room> rooms, List<Guest> guests)
    {
        this.rooms = new ArrayList<>();
        this.guests = new ArrayList<>();
    }
}
