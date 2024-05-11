package src.main.java.config;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate>
{
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate unmarshal(String s) throws Exception
    {
        if (s != null && !s.isEmpty())
        {
            return LocalDate.parse(s, formatter);
        }
        return null;
    }

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
