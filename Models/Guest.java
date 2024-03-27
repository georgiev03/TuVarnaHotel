package Models;

import java.util.Date;

public class Guest
{
    private String name;
    private Date checkInDate;
    private Date checkOutDate;

    public Guest(String name, Date checkInDate, Date checkOutDate)
    {
        this.name = name;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }
}
