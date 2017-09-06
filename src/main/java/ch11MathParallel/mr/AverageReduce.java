package ch11MathParallel.mr;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class AverageReduce extends Reducer<Text, IntWritable, Text, FloatWritable> {
    private final FloatWritable finalAvg = new FloatWritable();
    Float average = 0f;
    Float count = 0f;
    int sum = 0;

    @Override
    protected void reduce(Text key, Iterable<IntWritable> pageCnts, Context context)
            throws IOException, InterruptedException {
        for (IntWritable cnt : pageCnts) {
            sum += cnt.get();
        }
        count += 1;
        average = sum/count;
        finalAvg.set(average);
        context.write(new Text("Average Page Count = "), finalAvg);
    }
}
