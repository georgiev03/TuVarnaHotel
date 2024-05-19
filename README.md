# Hotel Management System

This project is a hotel management system that allows users to manage hotel bookings and operations. It includes
functionalities for checking in guests, checking availability, generating reports, and more.

## Libraries Used

This project uses the following external libraries:

- **Lombok**: Used for reducing boilerplate code with annotations like `@Getter`, `@Setter`, `@NoArgsConstructor`,
  and `@AllArgsConstructor`.
    - Website: [Project Lombok](https://projectlombok.org/)
- **JAXB (Java Architecture for XML Binding)**: Used for converting Java objects to XML and vice versa.
    - Website: [JAXB](https://javaee.github.io/jaxb-v2/)

## How to Use

1. **Open a Hotel Data File**: open <"filename.xml">
2. **Check In a Guest**: checkin <"room number"> <"from date"> <"to date"> <"note"> [<"guests">]
3. **Other Commands**:

- `close`: Close the currently opened data file.
- `save`: Save changes to the current data file.
- `saveas <filename>`: Save changes to a new data file.
- `availability [<date>]`: Display available rooms for a given date.
- `checkout <room>`: Check out a guest from a room.
- `report <from> <to>`: Generate a room usage report.
- `find <beds> <from> <to>`: Find available rooms with specified beds.
- `unavailable <room> <from> <to> <note>`: Mark a room as unavailable.
- `help`: Display available commands.
- `exit`: Exit the program.

