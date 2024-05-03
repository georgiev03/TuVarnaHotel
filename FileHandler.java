import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler
{
    private File file;
    private FileWriter fileWriter;

    public void openFile(String filePath)
    {
        this.file = new File(filePath);
        try
        {
            if (file.createNewFile())
            {
                System.out.println("File created: " + file.getName());
            } else
            {
                System.out.println("File successfully opened.");
            }
            fileWriter = new FileWriter(file);
        } catch (IOException e)
        {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
        }

    }

    public void closeFile()
    {
        if (fileWriter != null)
        {
            try
            {
                fileWriter.close();
                System.out.println("File closed successfully.");
            } catch (IOException e)
            {
                System.out.println("Error occurred while closing the file: " + e.getMessage());
            }
        } else
        {
            System.out.println("No file is currently open.");
        }
    }

    public void saveFile()
    {
        if (file == null)
        {
            System.out.println("No file is currently open.");
            return;
        }

        try
        {
            // Write data to the file

            // ...

            System.out.println("File saved successfully.");
        } catch (Exception e)
        {
            System.out.println("An error occurred while saving the file.");

        }
    }

    public void saveFileAs(String filePath)
    {
        // Логика за запис на файл като
    }

    // Други методи за работа с файлове
}
