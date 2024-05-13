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
    private List<Stay> unavailablePeriods = new ArrayList<>();


    public Room(int number, int beds)
    {
        this.number = number;
        this.beds = beds;
    }

    public boolean isUnavailable(LocalDate fromDate, LocalDate toDate)
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

    public Stay findStay(LocalDate date)
    {

        for (Stay stay : stays)
        {
            LocalDate stayFromDate = stay.getFromDate();
            LocalDate stayToDate = stay.getToDate();

            if (!stayFromDate.isAfter(date) && !stayToDate.isBefore(date))
            {
                return stay;
            }
        }

        return null;
    }

    public void removeStay(Stay stay)
    {
        stays.remove(stay);
    }

    public void addStay(Stay stay)
    {
        stays.add(stay);
    }

    public void addUnavailablePeriod(LocalDate fromDate, LocalDate toDate, String note)
    {
        unavailablePeriods.add(new Stay(fromDate, toDate, note, 0));
    }
}
