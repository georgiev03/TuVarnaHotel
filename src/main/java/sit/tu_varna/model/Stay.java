package sit.tu_varna.model;

import sit.tu_varna.config.LocalDateAdapter;
import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

/**
 * The Stay class represents a stay in a hotel room with details about the duration, notes, and guest count.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Stay
{
    /**
     * The start date of the stay.
     */
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fromDate;

    /**
     * The end date of the stay.
     */
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate toDate;

    /**
     * A note associated with the stay.
     */
    private String note;
    /**
     * The number of guests staying.
     */
    private int guestsCount;

    /**
     * Constructs a Stay with the specified from date, to date, and note.
     *
     * @param fromDate the start date of the stay
     * @param toDate   the end date of the stay
     * @param note     the note associated with the stay
     */
    public Stay(LocalDate fromDate, LocalDate toDate, String note)
    {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.note = note;
    }
}
