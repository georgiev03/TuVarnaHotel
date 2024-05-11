package src.main.java.Models;

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

    public boolean isOccupied(LocalDate fromDate, LocalDate toDate) {
        for (Stay stay : stays) {
            LocalDate stayFromDate = LocalDate.parse(stay.getFromDate());
            LocalDate stayToDate = LocalDate.parse(stay.getToDate());

            if ((stayFromDate.isAfter(fromDate) && stayFromDate.isBefore(toDate)) ||
                    (stayToDate.isAfter(fromDate) && stayToDate.isBefore(toDate)) ||
            stayFromDate.isEqual(fromDate) || stayToDate.isEqual(toDate)) {
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
