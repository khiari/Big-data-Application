package com.project.mapReduce;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
//import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Reducer;
 
public class PostsReducer<KEY> extends Reducer<KEY, FloatWritable,
                                                 KEY,FloatWritable> {
 
  private FloatWritable result = new FloatWritable();
 
  public void reduce(KEY key, Iterable<FloatWritable> values,
                     Context context) throws IOException, InterruptedException {
    float sum = 0;
    for (FloatWritable val : values) {
      sum += val.get();
    }
    result.set(sum);
    context.write(key, result);
  
    
  }
  
  
 
}