package src.main.java.Service;

import src.main.java.Models.HotelSystem;
import src.main.java.Models.Room;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DefaultXmlGenerator
{
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
