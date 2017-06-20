package ch03Cleaning.sort;

/**
 * Created by Aspire on 20.06.2017.
 */
public class Dog {
    private String name;
    private int age;

    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return this.getName() + " " + this.getAge();
    }
}
