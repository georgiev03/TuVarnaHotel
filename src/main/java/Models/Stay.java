package src.main.java.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stay
{
    private LocalDate fromDate;
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
