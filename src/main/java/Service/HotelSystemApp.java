package src.main.java.Service;

import src.main.java.Models.HotelSystem;

import java.util.Scanner;

public class HotelSystemApp
{

    public static void Start()
    {
        HotelSystem hotelSystem = new HotelSystem();
        XmlFileHandler fileHandler = new XmlFileHandler();
        Scanner scanner = new Scanner(System.in);
        boolean fileOpened = false;

        while (true) {
            System.out.println("Enter a command: ");
            String command = scanner.nextLine();

            switch (command.split(" ")[0]) {
                case "open":
                    if (!fileOpened) {
                        hotelSystem = fileHandler.openFile(hotelSystem, command.split(" ")[1]);
                        fileOpened = true;
                    } else {
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
                    if (command.split(" ").length < 5) {
                        System.out.println("Invalid command format. Please try again.");
                        break;
                    }

                    String roomNumber = command.split(" ")[1];
                    String fromDate = command.split(" ")[2];
                    String toDate = command.split(" ")[3];
                    String note = command.split(" ")[4];
                    int guestsCount = command.split(" ").length > 5 ? Integer.parseInt(command.split(" ")[5]) : -1;

                    String checkInResult = hotelSystem.checkIn(roomNumber, fromDate, toDate, note, guestsCount);
                    System.out.println(checkInResult);
                    break;
                case "availability":
                    // Логика за извеждане на свободни стаи
                    break;
                case "checkout":
                    // Логика за освобождаване на стая
                    break;
                case "report":
                    // Логика за извеждане на справка
                    break;
                case "find":
                    // Логика за намиране на свободна стая
                    break;
                case "find!":
                    // Логика за спешно намиране на стая
                    break;
                case "unavailable":
                    // Логика за обявяване на стая за недостъпна
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
