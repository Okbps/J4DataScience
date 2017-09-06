package ch11MathParallel;

import ch11MathParallel.mr.AverageReduce;
import ch11MathParallel.mr.TextMapper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

import static util.Globals.RESOURCE_FOLDER;

public class MapReduceExample {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration con = new Configuration();
        Job bookJob = Job.getInstance(con, "Average Page Count");

        bookJob.setJarByClass(MapReduceExample.class);
        bookJob.setMapperClass(TextMapper.class);
        bookJob.setReducerClass(AverageReduce.class);
        bookJob.setOutputKeyClass(Text.class);
        bookJob.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(bookJob, new Path(RESOURCE_FOLDER + "txt/books.txt"));
        FileOutputFormat.setOutputPath(bookJob, new Path(RESOURCE_FOLDER + "txt/booksOutput"));

        if(bookJob.waitForCompletion(true)){
            System.exit(0);
        }
    }
}
