package ch03Cleaning;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static util.Globals.RESOURCE_FOLDER;

/**
 * Created by Aspire on 20.06.2017.
 */
public class Subsetting {
    public static void main(String[] args) {
        Integer[]nums = {12,46,52,34,87,123,14,44};
        List<Integer>numsList = Arrays.asList(nums);

        Subsetting subsetting = new Subsetting();
//        subsetting.subsetTree(numsList);
//        subsetting.subsetStream(numsList);
        subsetting.blankLines();
    }

    void subsetTree(List<Integer>numsList){
        TreeSet<Integer>fullNumsList = new TreeSet<Integer>(numsList);

        SortedSet<Integer> partNumList;
        System.out.println("Original List: " + fullNumsList + " " + fullNumsList.last());

        partNumList = fullNumsList.subSet(fullNumsList.first(), 46);
        System.out.println("Subset of List: " + partNumList + " " + partNumList.size());

    }

    void subsetStream(List<Integer>numsList){
        Set<Integer> fullNumsList = new TreeSet<Integer>(numsList);

        Set<Integer> partNumsList = fullNumsList
                .stream()
                .skip(5)
                .collect(Collectors.toSet());

        System.out.println("Original List: " + fullNumsList);
        System.out.println("Subset of List: " + partNumsList);
    }

    void blankLines(){
        try(BufferedReader br = new BufferedReader(new FileReader(RESOURCE_FOLDER+"txt/stopwords.txt"))){
            br
                    .lines()
                    .filter(s -> !s.equals(""))
                    .forEach(System.out::println);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
