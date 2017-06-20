package ch03Cleaning.sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Aspire on 20.06.2017.
 */
public class SortingDogs {
    public static void main(String[] args) {
        List<Dog>dogs = new ArrayList<Dog>();
        dogs.add(new Dog("Zoey", 8));
        dogs.add(new Dog("Roxie", 10));
        dogs.add(new Dog("Kylie", 7));
        dogs.add(new Dog("Shorty", 14));
        dogs.add(new Dog("Ginger", 7));
        dogs.add(new Dog("Penny", 7));

        System.out.println("Name Age");

        dogs.sort(Comparator
                .comparing(Dog::getName)
                .thenComparing(Dog::getAge));

        dogs.forEach(System.out::println);

        System.out.println("Name Age");

        dogs.sort(Comparator
                .comparing(Dog::getAge)
                .thenComparing(Dog::getName));

        dogs.forEach(System.out::println);
    }
}
