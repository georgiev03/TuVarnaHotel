package src.main.java.service;

import src.main.java.model.HotelSystem;
import src.main.java.model.Room;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The DefaultXmlGenerator class provides a method to generate a default XML file for the HotelSystem.
 */
public class DefaultXmlGenerator
{
    /**
     * Generates a default XML file with sample hotel data.
     *
     * @param defaultHotelSystem the HotelSystem instance to populate with default data
     * @param filePath           the path of the XML file to create
     * @return the HotelSystem with default data
     */
    public static HotelSystem generateDefaultXml(HotelSystem defaultHotelSystem, String filePath)
    {
        try
        {
            defaultHotelSystem = getDefaultHotelSystem();

            JAXBContext context = JAXBContext.newInstance(HotelSystem.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            marshaller.marshal(defaultHotelSystem, new File(filePath));

            System.out.println("Default XML file created successfully.");
            return defaultHotelSystem;

        } catch (JAXBException e)
        {
            System.out.println("An error occurred while generating the default XML file: " + e.getMessage());
            return null;
        }
    }

    /**
     * Creates and returns a HotelSystem instance populated with default room data.
     *
     * @return a HotelSystem instance with default room data
     */
    private static HotelSystem getDefaultHotelSystem()
    {
        HotelSystem hotelSystem = new HotelSystem();
        List<Room> rooms = new ArrayList<>();
        int beds = 2;
        for (int i = 100; i <= 410; i++)
        {
            rooms.add(new Room(i, beds));
            if (i % 100 >= 5)
            {
                beds = 3;
            }

            if (i % 100 == 10)
            {
                i += 90;
                beds = 2;
            }
        }

        hotelSystem.setRooms(rooms);

        return hotelSystem;
    }
}
