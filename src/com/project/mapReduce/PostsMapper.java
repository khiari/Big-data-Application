package com.project.mapReduce;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
//import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
//import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class PostsMapper extends
		Mapper<LongWritable, Text, Text, FloatWritable> {

	@Override
	public void map(LongWritable key, Text value, Context context)

	throws IOException, InterruptedException {

		String line = value.toString();
		String[] data = line.split(",");
		context.write(new Text(data[2]), new FloatWritable(1));

	}

}