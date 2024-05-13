package src.main.java.Service;

import src.main.java.model.HotelSystem;

import java.time.LocalDate;
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
            String input = scanner.nextLine();
            String command = input.split(" ")[0];

            if (!fileOpened && !command.equals("open") && !command.equals("help") && !command.equals("exit"))
            {
                System.out.println("You need to open a file first.");
                continue;
            }

            switch (input.split(" ")[0])
            {
                case "open":
                    if (!fileOpened)
                    {
                        hotelSystem = fileHandler.openFile(hotelSystem, input.split(" ")[1]);
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
                    fileHandler.saveAs(hotelSystem, input.split(" \"")[1].replace("\"", ""));
                    break;
                case "checkin":
                    if (input.split(" ").length < 5)
                    {
                        System.out.println("Invalid command format. Please try again.");
                        break;
                    }

                    String roomNumber = input.split(" ")[1];
                    startDateStr = input.split(" ")[2];
                    endDateStr = input.split(" ")[3];
                    note = input.split(" ")[4];
                    int guestsCount = input.split(" ").length > 5 ? Integer.parseInt(input.split(" ")[5]) : -1;

                    String checkInResult = hotelSystem.checkIn(roomNumber, startDateStr, endDateStr, note, guestsCount);
                    System.out.println(checkInResult);
                    break;
                case "availability":
                    startDateStr = input.split(" ").length > 1 ? input.split(" ")[1] : String.valueOf(LocalDate.now());

                    hotelSystem.checkAvailability(startDateStr);
                    break;
                case "checkout":
                    if (input.split(" ").length < 2)
                    {
                        System.out.println("Invalid command format. Please specify the room number.");
                        break;
                    }
                    hotelSystem.checkOut(input.split(" ")[1]);
                    break;
                case "report":
                    if (input.split(" ").length < 3)
                    {
                        System.out.println("Invalid command format. Please specify the start and end dates.");
                        break;
                    }

                    startDateStr = input.split(" ")[1];
                    endDateStr = input.split(" ")[2];

                    hotelSystem.report(startDateStr, endDateStr);
                    break;
                case "find":
                    if (input.split(" ").length < 4)
                    {
                        System.out.println("Invalid command format. Please specify the number of beds, start date and end date.");
                        break;
                    }

                    int requiredBeds = Integer.parseInt(input.split(" ")[1]);
                    startDateStr = input.split(" ")[2];
                    endDateStr = input.split(" ")[3];

                    hotelSystem.find(requiredBeds, startDateStr, endDateStr);
                    break;
                case "unavailable":
                    if (input.split(" ").length < 5)
                    {
                        System.out.println("Invalid command format. Please specify the number of beds, start date and end date.");
                        break;
                    }

                    int roomNum = Integer.parseInt(input.split(" ")[1]);
                    startDateStr = input.split(" ")[2];
                    endDateStr = input.split(" ")[3];
                    note = input.split(" ")[4];

                    hotelSystem.markUnavailable(roomNum, startDateStr, endDateStr, note);
                    break;
                case "help":
                    help();
                    break;
                case "exit":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid command. Please try again.");
            }
        }
    }

    private static void help()
    {
        System.out.println("Available commands:");
        System.out.println("open <filename> - Opens a hotel data file.");
        System.out.println("close - Closes the currently opened data file.");
        System.out.println("save - Saves the changes to the currently opened data file.");
        System.out.println("saveas <filename> - Saves the changes to a new hotel data file.");
        System.out.println("checkin <room> <from> <to> <note> [<guests>] - Registers a guest in a room.");
        System.out.println("availability [<date>] - Displays available rooms for a given date or today.");
        System.out.println("checkout <room> - Checks out a guest from a room for today.");
        System.out.println("report <from> <to> - Displays room usage report for a given period.");
        System.out.println("find <beds> <from> <to> - Finds available room with specified beds for a given period.");
        System.out.println("unavailable <room> <from> <to> <note> - Marks a room as unavailable for a given period.");
        System.out.println("help - Displays this help message.");
        System.out.println("exit - Exits the program.");
    }

}
