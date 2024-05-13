package src.main.java.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    private transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void setRooms(List<Room> rooms)
    {
        this.rooms = rooms;
    }

    public String checkIn(String roomNumberStr, String fromDateStr, String toDateStr, String note, int guestsCount)
    {
        if (!isValidDate(fromDateStr) || !isValidDate(toDateStr) || !isEndDateAfterStartDate(fromDateStr, toDateStr))
        {
            return "Invalid dates. Please enter valid future dates where end date is after start date.";
        }

        int roomNumber = Integer.parseInt(roomNumberStr);
        LocalDate fromDate = LocalDate.parse(fromDateStr, formatter);
        LocalDate toDate = LocalDate.parse(toDateStr, formatter);

        Room roomToCheckIn = findRoom(roomNumber);
        if (roomToCheckIn == null)
        {
            return "Room " + roomNumber + " does not exist.";
        }

        if (roomToCheckIn.isOccupied(fromDate, toDate))
        {
            return "Room " + roomNumber + " is already occupied for the specified period.";
        }

        Stay stay = new Stay(fromDate, toDate, note);
        if (guestsCount != -1)
        {
            stay.setGuestsCount(guestsCount);
        } else
        {
            stay.setGuestsCount(roomToCheckIn.getBeds());
        }

        roomToCheckIn.addStay(stay);

        return "Checked in successfully.";
    }

    public List<Integer> checkAvailability(String dateStr)
    {
        LocalDate date = LocalDate.parse(dateStr);

        List<Integer> availableRooms = new ArrayList<>();


        for (Room room : rooms)
        {
            if (!room.isOccupied(date, date)) //TODO: Check working correctly
            {
                availableRooms.add(room.getNumber());
            }
        }

        return availableRooms;
    }

    public void checkOut(String roomNumberStr)
    {
        int roomNumber = Integer.parseInt(roomNumberStr);
        Room roomToCheckout = findRoom(roomNumber);

        if (roomToCheckout == null)
        {
            System.out.println("Room " + roomNumber + " does not exist.");
            return;
        }

        LocalDate dateToday = LocalDate.now();
        if (!roomToCheckout.isOccupied(dateToday, dateToday))
        {
            System.out.println("Room " + roomNumber + " is not occupied.");
            return;
        }

        var stay = roomToCheckout.findStay(dateToday);
        if (stay != null)
        {
            System.out.println("Stay from: " + stay.getFromDate() + ", to: " + stay.getToDate() + " has successfully checkout.");
            roomToCheckout.removeStay(stay);
            return;
        }
        System.out.println("Couldn't checkout room " + roomNumberStr);
    }

    public void report(String startDateStr, String endDateStr)
    {
        if (!isValidDate(startDateStr) || !isValidDate(endDateStr) || !isEndDateAfterStartDate(startDateStr, endDateStr))
        {
            System.out.println("Invalid dates. Please enter valid dates where end date is after start date.");
            return;
        }

        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        Map<Integer, Integer> roomUsage = new HashMap<>();

        for (Room room : rooms)
        {
            int daysUsed = 0;
            if (room.getStays().isEmpty())
            {
                continue;
            }

            for (Stay stay : room.getStays())
            {
                LocalDate stayStartDate = stay.getFromDate();
                LocalDate stayEndDate = stay.getToDate();

                if (!stayEndDate.isBefore(startDate) && !stayStartDate.isAfter(endDate))
                {
                    LocalDate stayStart = stayStartDate.isBefore(startDate) ? startDate : stayStartDate;
                    LocalDate stayEnd = stayEndDate.isAfter(endDate) ? endDate : stayEndDate;

                    daysUsed += 1 + stayStart.until(stayEnd).getDays();
                }
            }

            roomUsage.put(room.getNumber(), daysUsed);
        }

        System.out.println("Room Usage Report from " + startDateStr + " to " + endDateStr + ":");
        for (Map.Entry<Integer, Integer> entry : roomUsage.entrySet())
        {
            System.out.println("Room " + entry.getKey() + " was used for " + entry.getValue() + " days.");
        }
    }

    private Room findRoom(int roomNumber)
    {
        for (Room room : rooms)
        {
            if (room.getNumber() == roomNumber)
            {
                return room;
            }
        }
        return null;
    }

    private boolean isValidDate(String dateStr)
    {
        try
        {
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }

    private boolean isFutureDate(String dateStr)
    {
        LocalDate date = LocalDate.parse(dateStr, formatter);
        return !LocalDate.now().isBefore(date);
    }

    private boolean isEndDateAfterStartDate(String startDateStr, String endDateStr)
    {
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        LocalDate endDate = LocalDate.parse(endDateStr, formatter);
        return !endDate.isBefore(startDate);
    }


}
