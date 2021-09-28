package mainPackage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class LocationGenerator
{
    public static void main(String[] args) {

        Random random = new Random();

        int sampleSize = 10;

        try
        {
            FileWriter fileWriter = new FileWriter("sampleLocations.txt");

            for(int i=0;i<sampleSize;i++)
            {
                double x = random.doubles(-180,180).findFirst().getAsDouble();
                double y = random.doubles(-180,180).findFirst().getAsDouble();

                fileWriter.write(x + " " + y + "\n");
            }

            fileWriter.close();

        }catch (IOException e)
        {

        }



    }
}
