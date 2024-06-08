package sit.tu_varna.service;


import sit.tu_varna.model.HotelSystem;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * The XmlFileHandler class provides methods to handle XML files for the HotelSystem.
 */
public class XmlFileHandler
{
    /**
     * The XML file being handled.
     */
    private File file;

    /**
     * The JAXB context for XML marshalling and unmarshalling.
     */
    private JAXBContext context;

    /**
     * Opens an XML file and loads the hotel data into the HotelSystem.
     *
     * @param hotelSystem the HotelSystem instance to load data into
     * @param filePath    the path of the XML file to open
     * @return the HotelSystem with loaded data
     */
    public HotelSystem openFile(HotelSystem hotelSystem, String filePath)
    {
        try
        {
            context = JAXBContext.newInstance(HotelSystem.class);
            Marshaller marshaller = context.createMarshaller();
            file = new File(filePath);
            if (!file.exists())
            {
                hotelSystem = DefaultXmlGenerator.generateDefaultXml(hotelSystem, filePath);
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                marshaller.marshal(hotelSystem, new File(filePath));
                System.out.println("File created: " + file.getName());
            } else
            {
                Unmarshaller unmarshaller = context.createUnmarshaller();
                System.out.println("File opened successfully.");
                return (HotelSystem) unmarshaller.unmarshal(new File(filePath));
            }
        } catch (JAXBException e)
        {
            System.out.println("An error occurred while opening the file: " + e.getMessage());
        }
        return hotelSystem;
    }

    /**
     * Saves the current state of the HotelSystem to the opened XML file.
     *
     * @param hotelSystem the HotelSystem instance to save
     */
    public void saveFile(HotelSystem hotelSystem)
    {
        try
        {
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(hotelSystem, file);
            System.out.println("File saved successfully.");
        } catch (JAXBException e)
        {
            System.out.println("An error occurred while saving the file: " + e.getMessage());
        }
    }

    /**
     * Saves the current state of the HotelSystem to a specified XML file.
     *
     * @param hotelSystem the HotelSystem instance to save
     * @param filePath    the path of the XML file to save as
     */
    public void saveAs(HotelSystem hotelSystem, String filePath)
    {
        try
        {
            context = JAXBContext.newInstance(HotelSystem.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(hotelSystem, new File(filePath));
            System.out.println("File saved successfully as: " + filePath);
        } catch (JAXBException e)
        {
            System.out.println("An error occurred while saving the file: " + e.getMessage());
        }
    }
}