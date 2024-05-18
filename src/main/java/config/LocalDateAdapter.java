package src.main.java.config;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The LocalDateAdapter class is an XML adapter for converting between LocalDate objects and their string representations.
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate>
{
    /**
     * The date formatter for parsing and formatting LocalDate objects.
     */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Converts a string representation of a date to a LocalDate object.
     *
     * @param s the string representation of the date
     * @return the LocalDate object
     * @throws Exception if an error occurs during parsing
     */
    @Override
    public LocalDate unmarshal(String s) throws Exception
    {
        if (s != null && !s.isEmpty())
        {
            return LocalDate.parse(s, formatter);
        }
        return null;
    }

    /**
     * Converts a LocalDate object to its string representation.
     *
     * @param localDate the LocalDate object
     * @return the string representation of the date
     * @throws Exception if an error occurs during formatting
     */
    @Override
    public String marshal(LocalDate localDate) throws Exception
    {
        if (localDate != null)
        {
            return localDate.format(formatter);
        }
        return null;
    }
}
