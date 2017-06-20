package ch03Cleaning;

import java.util.Optional;

/**
 * Created by Aspire on 20.06.2017.
 */
public class Imputation {
    public static void main(String[] args) {
        Imputation imputation = new Imputation();
//        imputation.avgTemperature();
        imputation.defaultName();
    }

    void avgTemperature(){
        double[]tempList = {50,56,65,70,74,80,82,90,83,78,64,52};
        tempList[0] = 0;
        double sum = 0;
        for(double d: tempList){
            sum += d;
        }
        System.out.printf("The average temperature is %1$,.2f", sum/12);
    }

    void defaultName(){
        String useName = "";
        String[]nameList = {"Amy","Bob","Sally","Sue","Don","Rick",null,"Betsy"};
        Optional<String> tempName;
        for(String name: nameList){
            tempName = Optional.ofNullable(name);
            useName = tempName.orElse("DEFAULT");
            System.out.println("Name to use = " + useName);
        }
    }
}
