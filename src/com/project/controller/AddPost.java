package com.project.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cassandra.DbAcces;
import com.project.cassandra.Post;

/**
 * Servlet implementation class AddPost
 */
@WebServlet("/AddPost")
public class AddPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddPost() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("AddPost ");
			String post = request.getParameter("post");
			System.out.println(post);
		
		  /////////// use this in addPost from jsp page
		
		
		  int num,year,month,hour;
		  num = 0 + (int)(Math.random() * 2); 
		  
		if(num ==0)
			  year =2015;
		  else year=2016;
		  
		  month= 0 + (int)(Math.random() * 13);
		  
		  hour= 0 + (int)(Math.random() * 24);
		  
		  
		  DbAcces dbAcces =new DbAcces();
		  
		  dbAcces.AddPost(new Post(post,hour,year,month));
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
