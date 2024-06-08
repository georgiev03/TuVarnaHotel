package sit.tu_varna.service;

import sit.tu_varna.model.HotelSystem;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The HotelSystemApp class provides a command-line interface for interacting with the HotelSystem.
 */
public class HotelSystemApp
{

    private static HotelSystem hotelSystem = new HotelSystem();
    private static XmlFileHandler fileHandler = new XmlFileHandler();
    private static Scanner scanner = new Scanner(System.in);
    private static boolean fileOpened = false;
    // Map to associate each command with its corresponding action
    private static final Map<String, Command> commands = new HashMap<>();

    static
    {
        commands.put("open", HotelSystemApp::open);
        commands.put("close", HotelSystemApp::close);
        commands.put("save", HotelSystemApp::save);
        commands.put("saveas", HotelSystemApp::saveAs);
        commands.put("checkin", HotelSystemApp::checkIn);
        commands.put("availability", HotelSystemApp::availability);
        commands.put("checkout", HotelSystemApp::checkout);
        commands.put("report", HotelSystemApp::report);
        commands.put("find", HotelSystemApp::find);
        commands.put("unavailable", HotelSystemApp::markUnavailable);
        commands.put("help", HotelSystemApp::help);
        commands.put("exit", HotelSystemApp::exit);
    }

    /**
     * Starts the Hotel System Application, processing user commands in a loop.
     */
    public static void start()
    {
        while (true)
        {
            System.out.println("Enter a command: ");
            String input = scanner.nextLine();
            String command = input.split(" ")[0];

            if (!fileOpened && !command.equals("open") && !command.equals("help") && !command.equals("exit"))
            {
                System.out.println("Invalid command.\nYou need to open a file first.");
                continue;
            }

            Command cmd = commands.get(command);
            if (cmd != null)
            {
                cmd.execute(hotelSystem, fileHandler, input, fileOpened);
            } else
            {
                System.out.println("Invalid command. Please try again.");
            }
        }
    }

    // Functional interface for commands
    @FunctionalInterface
    private interface Command
    {
        void execute(HotelSystem hotelSystem, XmlFileHandler fileHandler, String input, boolean fileOpened);
    }

    // Methods for each command
    private static void open(HotelSystem hotelSystem, XmlFileHandler fileHandler, String input, boolean fileOpened)
    {
        try
        {
            if (!fileOpened)
            {
                HotelSystemApp.hotelSystem = fileHandler.openFile(hotelSystem, input.split(" ")[1]);
                HotelSystemApp.fileOpened = true;
            } else
            {
                System.out.println("A file is already open.");
            }
        } catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Invalid input format. Please provide a valid file name.");
        }
    }

    private static void close(HotelSystem hotelSystem, XmlFileHandler fileHandler, String input, boolean fileOpened)
    {
        HotelSystemApp.fileOpened = false;
        System.out.println("File closed successfully.");
    }

    private static void save(HotelSystem hotelSystem, XmlFileHandler fileHandler, String input, boolean fileOpened)
    {
        fileHandler.saveFile(hotelSystem);
    }

    private static void saveAs(HotelSystem hotelSystem, XmlFileHandler fileHandler, String input, boolean fileOpened)
    {
        fileHandler.saveAs(hotelSystem, input.split(" \"")[1].replace("\"", ""));
    }

    private static void checkIn(HotelSystem hotelSystem, XmlFileHandler fileHandler, String input, boolean fileOpened)
    {
        String[] parts = input.split(" ");
        if (parts.length < 5)
        {
            System.out.println("Invalid command format. Please try again.");
            return;
        }

        String roomNumber = parts[1];
        String startDateStr = parts[2];
        String endDateStr = parts[3];
        int guestsCount = -1;
        StringBuilder noteBuilder = new StringBuilder();

        try
        {
            guestsCount = Integer.parseInt(parts[parts.length - 1]);
            for (int i = 4; i < parts.length - 1; i++)
            {
                noteBuilder.append(parts[i]).append(" ");
            }
        } catch (NumberFormatException e)
        {
            for (int i = 4; i < parts.length; i++)
            {
                noteBuilder.append(parts[i]).append(" ");
            }
        }

        String note = noteBuilder.toString().trim();

        String checkInResult = hotelSystem.checkIn(roomNumber, startDateStr, endDateStr, note, guestsCount);
        System.out.println(checkInResult);
    }

    private static void availability(HotelSystem hotelSystem, XmlFileHandler fileHandler, String input, boolean fileOpened)
    {
        String startDateStr = input.split(" ").length > 1 ? input.split(" ")[1] : String.valueOf(LocalDate.now());
        hotelSystem.checkAvailability(startDateStr);
    }

    private static void checkout(HotelSystem hotelSystem, XmlFileHandler fileHandler, String input, boolean fileOpened)
    {
        if (input.split(" ").length < 2)
        {
            System.out.println("Invalid command format. Please specify the room number.");
            return;
        }
        hotelSystem.checkOut(input.split(" ")[1]);
    }

    private static void report(HotelSystem hotelSystem, XmlFileHandler fileHandler, String input, boolean fileOpened)
    {
        if (input.split(" ").length < 3)
        {
            System.out.println("Invalid command format. Please specify the start and end dates.");
            return;
        }

        String startDateStr = input.split(" ")[1];
        String endDateStr = input.split(" ")[2];

        hotelSystem.report(startDateStr, endDateStr);
    }

    private static void find(HotelSystem hotelSystem, XmlFileHandler fileHandler, String input, boolean fileOpened)
    {
        if (input.split(" ").length < 4)
        {
            System.out.println("Invalid command format. Please specify the number of beds, start date, and end date.");
            return;
        }

        int requiredBeds;
        try
        {
            requiredBeds = Integer.parseInt(input.split(" ")[1]);
        } catch (NumberFormatException e)
        {
            System.out.println("Invalid number of beds. Please enter a valid integer.");
            return;
        }
        String startDateStr = input.split(" ")[2];
        String endDateStr = input.split(" ")[3];

        hotelSystem.find(requiredBeds, startDateStr, endDateStr);
    }

    private static void markUnavailable(HotelSystem hotelSystem, XmlFileHandler fileHandler, String input, boolean fileOpened)
    {
        if (input.split(" ").length < 5)
        {
            System.out.println("Invalid command format. Please specify the room number, start date, end date, and note.");
            return;
        }

        int roomNum;
        try
        {
            roomNum = Integer.parseInt(input.split(" ")[1]);
        } catch (NumberFormatException e)
        {
            System.out.println("Invalid room number. Please enter a valid integer.");
            return;
        }
        String startDateStr = input.split(" ")[2];
        String endDateStr = input.split(" ")[3];
        String note = input.split(" ")[4];

        hotelSystem.markUnavailable(roomNum, startDateStr, endDateStr, note);
    }

    private static void help(HotelSystem hotelSystem, XmlFileHandler fileHandler, String input, boolean fileOpened)
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

    private static void exit(HotelSystem hotelSystem, XmlFileHandler fileHandler, String input, boolean fileOpened)
    {
        System.exit(0);
    }
}
