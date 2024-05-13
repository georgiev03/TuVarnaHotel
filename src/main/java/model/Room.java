package src.main.java.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room
{
    private int number;
    private int beds;
    private List<Stay> stays = new ArrayList<>();

    public Room(int number, int beds)
    {
        this.number = number;
        this.beds = beds;
    }

    public boolean isOccupied(LocalDate fromDate, LocalDate toDate)
    {
        for (Stay stay : stays)
        {
            LocalDate stayFromDate = stay.getFromDate();
            LocalDate stayToDate = stay.getToDate();

            if (!((fromDate.isBefore(stayFromDate) && toDate.isBefore(stayFromDate)) ||
                    (toDate.isAfter(stayToDate) && fromDate.isAfter(stayFromDate))))
            {
                return true;
            }
        }
        return false;
    }

    public void addStay(Stay stay)
    {
        stays.add(stay);
    }
}
