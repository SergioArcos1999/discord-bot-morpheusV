package commons.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

public class CustomFileReader {
    public String read(String filename) {
        try{
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            if(myReader.hasNextLine()) {
                String data = myReader.nextLine();
                return data;
            }
        }catch(FileNotFoundException e) {
            System.out.println("An error ocurred while trying to read");
            e.printStackTrace();
        }
        return null;
    }
}
