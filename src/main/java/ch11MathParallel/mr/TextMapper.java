package ch11MathParallel.mr;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class TextMapper extends Mapper<Object, Text, Text, IntWritable> {
    private final IntWritable pages = new IntWritable();
    private final Text bookTitle = new Text();

    @Override
    protected void map(Object key, Text bookInfo, Context context)
            throws IOException, InterruptedException {
        String[]book = bookInfo.toString().split("\\s{2}");
        bookTitle.set(book[0]);
        pages.set(Integer.parseInt(book[2]));
        context.write(bookTitle, pages);
    }
}
