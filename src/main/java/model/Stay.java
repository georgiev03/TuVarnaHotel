package src.main.java.model;

import lombok.*;
import src.main.java.config.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Stay
{
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fromDate;
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate toDate;
    private String note;
    private int guestsCount;

    public Stay(LocalDate fromDate, LocalDate toDate, String note)
    {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.note = note;
    }
}
