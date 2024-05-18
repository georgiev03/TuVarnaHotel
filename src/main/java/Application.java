package src.main.java;

import src.main.java.service.HotelSystemApp;

import java.io.IOException;

/**
 * The Application class serves as the entry point for the hotel management system application.
 */
public class Application
{
    /**
     * The main method which starts the Hotel System Application.
     */
    public static void main(String[] args) throws IOException
    {
        try
        {
            HotelSystemApp.start();
        } catch (Exception e)
        {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
