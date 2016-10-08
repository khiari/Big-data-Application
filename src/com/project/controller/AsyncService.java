package com.project.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Vector;

import javax.servlet.AsyncContext;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import com.project.cassandra.DbAcces;
import com.project.cassandra.Post;
import com.project.mapReduce.PostsMapper;
import com.project.mapReduce.PostsReducer;
/**
 * Long running task which is to be invoked by our servlet.
 */
public class AsyncService implements Runnable {

  /**
   * The AsyncContext object for getting the request and response
   * objects. Provided by the servlet.
   */
  private AsyncContext aContext;
  private String inputFilePath="hdfs://quickstart.cloudera:8020/bigDataProject/post";
  private String lineToAdd; 
  String jsonObject = "{ \"hours\" : [";
  
  BufferedReader br ; 
  BufferedWriter bw ; 
  
  
  public AsyncService(AsyncContext aContext, String lineToAdd) {
    this.aContext = aContext;
    this.lineToAdd=lineToAdd;
  }

  @Override
  public void run() {
    PrintWriter out = null;
    String key_value[] = new String[2];

    
  

      try {
		out = aContext.getResponse().getWriter();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
			
	 Configuration conf = new Configuration();
		FileSystem fs = null;
		
			try {
				fs = FileSystem.get(new URI("hdfs://quickstart.cloudera:8020"), conf );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		Path path = new Path(inputFilePath );
		try {
			if( fs.exists( path ) ) 
			{
			    Path tmpPath = new Path( "tmp.txt" );
			     br = new BufferedReader( new InputStreamReader( fs.open( path ) ) );  // Open old file for reading
			     bw = new BufferedWriter( new OutputStreamWriter( fs.create( tmpPath , true ) ) ); 
			    
			    getFromCassandra();
			    
			    br.close(); // Closes the stream and releases any system resources associated with it.
			    bw.close(); // Closes the stream and releases any system resources associated with it.
			    
			    fs.delete( path, true ); // Delete old file
			    fs.rename( tmpPath , path );  // Rename new file to old file name
			    
			    
			    mapReduce();
			    inputFilePath="hdfs://quickstart.cloudera:8020/bigDataProject/result/part-r-00000";
			     path = new Path(inputFilePath );
				if( fs.exists( path ) ) {
					
					
					   br = new BufferedReader( new InputStreamReader( fs.open( path ) ) );  // Open old file for reading
					   // bw = new BufferedWriter( new OutputStreamWriter( fs.create( tmpPath , true ) ) ); 
					    
					    String line = br.readLine(); // Read first line of file
					    while ( line != null )
					    {
					    	System.out.println(jsonObject);
					    	
							key_value = line.split("\t"); // hadoop tabulation

							jsonObject += "{ \"label\" : \"" + key_value[0] + "\", \"y\" :" + key_value[1] + "},";
							
					       // bw.write( line ); // Write line from old file to new file
					       // bw.newLine();  // Writes a line separator.
					        line = br.readLine(); // Read next line of the file
					     }
					    jsonObject = jsonObject.substring(0, jsonObject.length() - 1);
						jsonObject += "]}";
					   // bw.write( lineToAdd );  // Append new line into new file
					    //bw.newLine(); // Writes a line separator
					      

					    br.close(); // Closes the stream and releases any system resources associated with it.
					   // bw.close(); // Closes the stream and releases any system resources associated with it.
					    //fs.delete( path, true ); // Delete old file
					    //fs.rename( tmpPath , path );  // Rename new file to old file name
					}
					fs.close(); // No more filesystem operations are needed. Will release any held locks

					 out.println(jsonObject);

					
				
			    
			    
			    
			 

     //Sleeping the thread so as to mimic long running task.
    //Thread.sleep(5000);

     /**
      * Doing some operation based on the type parameter.
      */
//      switch (typeParam) {
//        case "1":
//          out.println("This process invoked for "+ counter +" times.");
//          break;
//        case "2":
//          out.println("Some other process invoked for "+ counter +" times.");
//          break;
//        default:
//          out.println("Ok... nothing asked of.");
//          break;
//      }

   //  System.out.println("Done processing the long running task: Type="+typeParam+", Counter: "+counter);

     /**
      * Intimating the Web server that the asynchronous task is complete and the
      * response to be sent to the client.
      */
     aContext.complete();

   }
		} catch (IllegalArgumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
  }
  
  public void getFromCassandra() throws IOException{


	  DbAcces dbAcces = new DbAcces();
	  Vector<Post> posts = dbAcces.GetAllPosts();
	  int num, year,month,hour;
	  String line="";
	  for (Post post : posts) {
		  
		  line=post.getYear()+","+post.getMonth()+","+post.getHour()+","+post.getBody();
		  bw.write( line ); // Write line from old file to new file
	      bw.newLine();  // Writes a line separator.
	
	}
	  
}
  
  public void mapReduce() throws IOException{
	  
	  Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec("hadoop fs -rm -r hdfs://quickstart.cloudera:8020/bigDataProject/result");
		try {
			proc.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	  
		Configuration conf = new Configuration();
		// Replace CallJobFromServlet.class name with your servlet class
		Job job = new Job(conf, "AsyncService.class");
		job.setJarByClass(AsyncService.class);
		job.setJobName("Job Name");
		job.setOutputKeyClass(FloatWritable.class);
		job.setOutputValueClass(FloatWritable.class);
		job.setMapperClass(PostsMapper.class); // Replace Map.class name with
													// your Mapper class
		// job.setNumReduceTasks(30);
		job.setReducerClass(PostsReducer.class); // Replace Reduce.class name
													// with your Reducer class
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(FloatWritable.class);
		job.setInputFormatClass(TextInputFormat.class);
		// job.setOutputFormatClass(TextOutputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		// Job Input path
		FileInputFormat.addInputPath(job, new Path(
				"hdfs://quickstart.cloudera:8020/bigDataProject/post"));
		// Job Output path
		FileOutputFormat.setOutputPath(job, new Path(
				"hdfs://quickstart.cloudera:8020/bigDataProject/result"));

		try {
			job.waitForCompletion(true);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
	  
	  
  }
}
    
    



