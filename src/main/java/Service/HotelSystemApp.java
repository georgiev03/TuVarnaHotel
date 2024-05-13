package src.main.java.Service;

import src.main.java.model.HotelSystem;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class HotelSystemApp
{

    public static void start()
    {
        HotelSystem hotelSystem = new HotelSystem();
        XmlFileHandler fileHandler = new XmlFileHandler();
        Scanner scanner = new Scanner(System.in);
        boolean fileOpened = false;
        String startDateStr, endDateStr, note;
        while (true)
        {
            System.out.println("Enter a command: ");
            String command = scanner.nextLine();

            if (!fileOpened && !command.split(" ")[0].equals("open"))
            {
                System.out.println("You need to open a file first.");
                continue;
            }

            switch (command.split(" ")[0])
            {
                case "open":
                    if (!fileOpened)
                    {
                        hotelSystem = fileHandler.openFile(hotelSystem, command.split(" ")[1]);
                        fileOpened = true;
                    } else
                    {
                        System.out.println("A file is already open.");
                    }
                    break;
                case "close":
                    fileOpened = false;
                    System.out.println("File closed successfully.");
                    break;
                case "save":
                    fileHandler.saveFile(hotelSystem);
                    break;
                case "saveas":
                    //fileHandler.saveFileAs(command.split(" ")[1]);
                    break;
                case "checkin":
                    if (command.split(" ").length < 5)
                    {
                        System.out.println("Invalid command format. Please try again.");
                        break;
                    }

                    String roomNumber = command.split(" ")[1];
                    startDateStr = command.split(" ")[2];
                    endDateStr = command.split(" ")[3];
                    note = command.split(" ")[4];
                    int guestsCount = command.split(" ").length > 5 ? Integer.parseInt(command.split(" ")[5]) : -1;

                    String checkInResult = hotelSystem.checkIn(roomNumber, startDateStr, endDateStr, note, guestsCount);
                    System.out.println(checkInResult);
                    break;
                case "availability":
                    startDateStr = command.split(" ").length > 1 ? command.split(" ")[1] : String.valueOf(LocalDate.now());

                    List<Integer> availableRooms = hotelSystem.checkAvailability(startDateStr);

                    if (availableRooms.isEmpty())
                    {
                        System.out.println("No available rooms on " + startDateStr);
                    } else
                    {
                        System.out.println("Available rooms on " + startDateStr + ": " + String.join(", ", availableRooms.toString()));
                    }
                    break;
                case "checkout":
                    if (command.split(" ").length < 2)
                    {
                        System.out.println("Invalid command format. Please specify the room number.");
                        break;
                    }
                    hotelSystem.checkOut(command.split(" ")[1]);
                    break;
                case "report":
                    if (command.split(" ").length < 3)
                    {
                        System.out.println("Invalid command format. Please specify the start and end dates.");
                        break;
                    }

                    startDateStr = command.split(" ")[1];
                    endDateStr = command.split(" ")[2];

                    hotelSystem.report(startDateStr, endDateStr);
                    break;
                case "find":
                    if (command.split(" ").length < 4)
                    {
                        System.out.println("Invalid command format. Please specify the number of beds, start date and end date.");
                        break;
                    }

                    int requiredBeds = Integer.parseInt(command.split(" ")[1]);
                    startDateStr = command.split(" ")[2];
                    endDateStr = command.split(" ")[3];

                    hotelSystem.find(requiredBeds, startDateStr, endDateStr);
                    break;
                case "unavailable":
                    if (command.split(" ").length < 5)
                    {
                        System.out.println("Invalid command format. Please specify the number of beds, start date and end date.");
                        break;
                    }

                    int roomNum = Integer.parseInt(command.split(" ")[1]);
                    startDateStr = command.split(" ")[2];
                    endDateStr = command.split(" ")[3];
                    note = command.split(" ")[4];

                    hotelSystem.markUnavailable(roomNum, startDateStr, endDateStr, note);
                    break;
                case "help":
                    // Логика за извеждане на помощна информация
                    break;
                case "exit":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid command. Please try again.");
            }
        }
    }
}
