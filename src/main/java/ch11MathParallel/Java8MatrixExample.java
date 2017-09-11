package ch11MathParallel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Java8MatrixExample {
    public static void main(String[] args) {
        Java8MatrixExample example = new Java8MatrixExample();
//        example.streamMultiply();
        example.streamMapReduce();
    }

    void streamMultiply(){
        int n = 4, p = 3;

        double A[][] = {
                {0.1950, 0.0311},
                {0.3588, 0.2203},
                {0.1716, 0.5931},
                {0.2105, 0.3242}};
        double B[][] = {
                {0.0502, 0.9823, 0.9472},
                {0.5732, 0.2694, 0.916}};
        double C[][] = new double[n][p];

        C = Arrays.stream(A)
                .parallel()
                .map(AMatrixRow  -> IntStream.range(0, B[0].length)
                        .mapToDouble(i -> IntStream.range(0, B.length)
                                .mapToDouble(j -> AMatrixRow [j] * B[j][i])
                                .sum())
                        .toArray())
                .toArray(double[][]::new);

        System.out.println("Result");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < p; j++) {
                System.out.printf("%.4f ", C[i][j]);
            }
            System.out.println();
        }
    }

    void streamMapReduce(){
        List<Book>books = new ArrayList<>();
        double average;
        int totalPg = 0;

        books.add(new Book("Moby Dick", "Herman Melville", 822));
        books.add(new Book("Charlotte's Web", "E.B. White", 189));
        books.add(new Book("The Grapes of Wrath", "John Steinbeck", 212));
        books.add(new Book("Jane Eyre", "Charlotte Bronte", 299));
        books.add(new Book("A Tale of Two Cities", "Charles Dickens", 673));
        books.add(new Book("War and Peace", "Leo Tolstoy", 1032));
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald",    275));

        totalPg = books.stream()
                .parallel()
                .map(Book::getPgCnt)
                .reduce(totalPg, (accumulator, _item) -> {
                    System.out.println(accumulator + " " + _item);
                    return  accumulator + _item;
                });

        average = books.parallelStream()
                .map(Book::getPgCnt)
                .mapToDouble(value -> value)
                .average()
                .getAsDouble();

        System.out.printf("Total Page Count: %d\n", totalPg);
        System.out.printf("Average Page Count: %.4f\n", average);
    }

    private class Book{
        String author, name;
        int pgCnt;

        public Book(String name, String author, int pageCnt) {
            this.author = author;
            this.name = name;
            this.pgCnt = pageCnt;
        }

        public int getPgCnt() {
            return pgCnt;
        }
    }
}
