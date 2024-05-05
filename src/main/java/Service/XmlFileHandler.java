package src.main.java.Service;


import src.main.java.Models.HotelSystem;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlFileHandler
{
    private File file;
    private JAXBContext context;

    public HotelSystem openFile(HotelSystem hotelSystem, String filePath)
    {

        try
        {
            context = JAXBContext.newInstance(HotelSystem.class);
            Marshaller marshaller = context.createMarshaller();
            file = new File(filePath);
            if (!file.exists())
            {
                hotelSystem = DefaultXmlGenerator.generateDefaultXml(hotelSystem,filePath);
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
}
