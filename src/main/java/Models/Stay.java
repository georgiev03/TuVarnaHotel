package src.main.java.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stay
{

    private String fromDate;
    private String toDate;
    private String note;
    private int guestsCount;

    public Stay(String fromDate, String toDate, String note)
    {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.note = note;
    }
}
