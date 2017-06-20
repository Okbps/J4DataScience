package ch03Cleaning.sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Aspire on 20.06.2017.
 */
public class SortingComparables {
    public static void main(String[] args) {
        SortingComparables sorting = new SortingComparables();
        sorting.sortIntegers();
        sorting.sortStrings();;
    }

    void sortIntegers(){
        List<Integer> numsList = Stream
                .of(12, 46, 52, 34, 87, 123, 14, 44)
                .collect(Collectors.toList());
        System.out.println("Original Integer List: " + numsList);

        Collections.reverse(numsList);
        System.out.println("Reversed Integer list: " + numsList);

        Comparator<Integer> basicOrder = Integer::compare;
        Comparator<Integer> descentOrder = basicOrder.reversed();

        numsList.sort(basicOrder);
        System.out.println("Sorted Integer asc: " + numsList);
        
        numsList.sort(descentOrder);
        System.out.println("Sorted Integer desc: " + numsList);
        
    }

    void sortStrings(){
        List<String> wordsList = Stream
                .of("cat","dog","house","boat","road","zoo")
                .collect(Collectors.toList());
        System.out.println("Original Word List: " + wordsList);

        Collections.reverse(wordsList);
        System.out.println("Reversed Word List: " + wordsList);

        Comparator<String> basicOrder = String::compareTo;
        Comparator<String> descentOrder = basicOrder.reversed();

        wordsList.sort(basicOrder);
        System.out.println("Sorted Words asc: " + wordsList);
        
        wordsList.sort(descentOrder);
        System.out.println("Sorted Word desc: " + wordsList);
    }
}
