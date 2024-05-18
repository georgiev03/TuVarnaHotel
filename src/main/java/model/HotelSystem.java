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

/**
 * The HotelSystem class manages the operations and state of the hotel, including room management and bookings.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class HotelSystem
{
    /**
     * The list of rooms in the hotel.
     */
    @XmlElement
    private List<Room> rooms = new ArrayList<>();

    /**
     * The list of guests in the hotel.
     */
    @XmlElement
    private List<Guest> guests = new ArrayList<>();

    /**
     * A DateTimeFormatter instance for formatting date strings.
     */
    private transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Sets the list of rooms in the hotel.
     *
     * @param rooms the list of rooms
     */
    public void setRooms(List<Room> rooms)
    {
        this.rooms = rooms;
    }

    /**
     * Registers a guest in a specified room for a given period with a note.
     *
     * @param roomNumberStr the room number
     * @param fromDateStr   the start date
     * @param toDateStr     the end date
     * @param note          the note for the booking
     * @param guestsCount   the number of guests (optional)
     * @return a message indicating the result of the check-in
     */
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

        if (roomToCheckIn.isUnavailable(fromDate, toDate))
        {
            return "Room " + roomNumber + " is already unavailable for the specified period.";
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

    /**
     * Displays the availability of rooms for a given date.
     *
     * @param dateStr the date to check availability
     */
    public void checkAvailability(String dateStr)
    {
        LocalDate date = LocalDate.parse(dateStr);

        List<Integer> availableRooms = new ArrayList<>();


        for (Room room : rooms)
        {
            if (!room.isUnavailable(date, date))
            {
                availableRooms.add(room.getNumber());
            }
        }

        if (availableRooms.isEmpty())
        {
            System.out.println("No available rooms on " + date);
        } else
        {
            System.out.println("Available rooms on " + date + ": " + String.join(", ", availableRooms.toString()));
        }
    }

    /**
     * Checks out a guest from a specified room for today.
     *
     * @param roomNumberStr the room number
     */
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
        if (!roomToCheckout.isUnavailable(dateToday, dateToday))
        {
            System.out.println("Room " + roomNumber + " is not unavailable for today " + dateToday + ".");
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

    /**
     * Generates a report of room usage for a given period.
     *
     * @param startDateStr the start date of the period
     * @param endDateStr the end date of the period
     */
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

    /**
     * Finds available rooms with a specified number of beds for a given period.
     *
     * @param requiredBeds the number of beds required
     * @param startDateStr the start date of the period
     * @param endDateStr the end date of the period
     */
    public void find(int requiredBeds, String startDateStr, String endDateStr)
    {
        if (!isValidDate(startDateStr) || !isValidDate(endDateStr) || !isEndDateAfterStartDate(startDateStr, endDateStr))
        {
            System.out.println("Invalid dates. Please enter valid dates where end date is after start date.");
            return;
        }
        if (requiredBeds <= 0)
        {
            System.out.println("Invalid number of required beds. Please enter a positive number of beds.");
            return;
        }

        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        Room selectedRoom = null;

        for (Room room : rooms)
        {
            if (room.getBeds() >= requiredBeds && !room.isUnavailable(startDate, endDate))
            {
                if (selectedRoom == null || room.getBeds() < selectedRoom.getBeds())
                {
                    selectedRoom = room;
                }
            }
        }

        if (selectedRoom == null)
        {
            System.out.println("No available room with at least " + requiredBeds + " beds for the specified dates.");
        } else
        {
            System.out.println("Available room with at least " + requiredBeds + " beds for the specified dates:");
            System.out.println(selectedRoom.getNumber() + " - " + selectedRoom.getBeds() + " beds");
        }
    }

    /**
     * Marks a specified room as unavailable for a given period with a note.
     *
     * @param roomNum the room number
     * @param startDateStr the start date of the period
     * @param endDateStr the end date of the period
     * @param note the note for the unavailability
     */
    public void markUnavailable(int roomNum, String startDateStr, String endDateStr, String note)
    {
        if (!isValidDate(startDateStr) || !isValidDate(endDateStr) || !isEndDateAfterStartDate(startDateStr, endDateStr))
        {
            System.out.println("Invalid dates. Please enter valid dates where end date is after start date.");
            return;
        }

        Room roomToMarkUnavailable = findRoom(roomNum);
        if (roomToMarkUnavailable == null)
        {
            System.out.println("Room " + roomNum + " does not exist.");
            return;
        }

        LocalDate fromDate = LocalDate.parse(startDateStr);
        LocalDate toDate = LocalDate.parse(endDateStr);

        Stay overlappingStay = roomToMarkUnavailable.isUnavailable(fromDate, toDate) ? roomToMarkUnavailable.findStay(fromDate) : null;
        if (overlappingStay != null)
        {
            System.out.println("Room " + roomNum + " is already unavailable from " + overlappingStay.getFromDate() + " to " + overlappingStay.getToDate() + ". Please select different dates.");
            return;
        }

        roomToMarkUnavailable.addUnavailablePeriod(fromDate, toDate, note);
        System.out.println("Room " + roomNum + " is marked as unavailable from " + fromDate + " to " + toDate + " for the reason: " + note);
    }

    /**
     * Finds a room by its number.
     *
     * @param roomNumber the room number to find
     * @return the Room object if found, else null
     */
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

    /**
     * Checks if a given date string is valid.
     *
     * @param dateStr the date string to validate
     * @return true if the date string is valid, otherwise false
     */
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

    /**
     * Checks if the end date is after the start date.
     *
     * @param startDateStr the start date string
     * @param endDateStr the end date string
     * @return true if end date is after start date, otherwise false
     */
    private boolean isEndDateAfterStartDate(String startDateStr, String endDateStr)
    {
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        LocalDate endDate = LocalDate.parse(endDateStr, formatter);
        return !endDate.isBefore(startDate);
    }

}
