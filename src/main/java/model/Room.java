package src.main.java.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The Room class represents a room in the hotel and manages its bookings and availability.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room
{
    /**
     * The room number.
     */
    private int number;
    /**
     * The number of beds in the room.
     */
    private int beds;
    /**
     * The list of stays in the room.
     */
    private List<Stay> stays = new ArrayList<>();
    /**
     * The list of periods when the room is unavailable.
     */
    private List<Stay> unavailablePeriods = new ArrayList<>();

    /**
     * Constructs a Room with the specified room number and number of beds.
     *
     * @param number the room number
     * @param beds   the number of beds in the room
     */
    public Room(int number, int beds)
    {
        this.number = number;
        this.beds = beds;
    }

    /**
     * Checks if the room is unavailable for a given period.
     *
     * @param fromDate the start date of the period
     * @param toDate the end date of the period
     * @return true if the room is unavailable for the specified period, false otherwise
     */
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

    /**
     * Finds a stay for a given date.
     *
     * @param date the date to find the stay for
     * @return the stay if found, null otherwise
     */
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

    /**
     * Removes a stay from the room's list of stays.
     *
     * @param stay the stay to be removed
     */
    public void removeStay(Stay stay)
    {
        stays.remove(stay);
    }

    /**
     * Adds a stay to the room's list of stays.
     *
     * @param stay the stay to be added
     */
    public void addStay(Stay stay)
    {
        stays.add(stay);
    }

    /**
     * Adds an unavailable period for the room with a note.
     *
     * @param fromDate the start date of the unavailable period
     * @param toDate the end date of the unavailable period
     * @param note the note for the unavailable period
     */
    public void addUnavailablePeriod(LocalDate fromDate, LocalDate toDate, String note)
    {
        unavailablePeriods.add(new Stay(fromDate, toDate, note, 0));
    }
}
