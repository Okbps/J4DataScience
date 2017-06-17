package ch03Cleaning;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Aspire on 17.06.2017.
 */
public class CsvHandler {
    public static void main(String[] args) {
        String pathName = "src/main/resources/Demographic.csv";

        CsvHandler handler = new CsvHandler();

        handler.readStandard(pathName);
        handler.readOpenCsv(pathName);
    }

    void readStandard(String pathName){
        try(Scanner csvData = new Scanner(new File(pathName))){
            ArrayList<String>list = new ArrayList<>();
            while(csvData.hasNext()){
                list.add(csvData.nextLine());
            }

            String[]tempArray = list.toArray(new String[1]);
            String[][]csvArray = new String[tempArray.length][];

            for (int i = 0; i < tempArray.length; i++) {
                csvArray[i] = tempArray[i].split(",");
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    void readOpenCsv(String pathName){
        try {
            CSVReader dataReader = new CSVReader(new FileReader(pathName), ',');
            String[]nextLine;

            while((nextLine = dataReader.readNext()) != null){
                for(String token: nextLine){
                    System.out.println(token);
                }
            }

            dataReader.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
