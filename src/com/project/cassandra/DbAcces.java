package com.project.cassandra;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;




public class DbAcces {
	public Vector<Post> GetAllPosts(){
		Vector<Post> Posts=new Vector<Post>();
		Cluster cluster;
		Session session;
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("Test");
		
		//session.execute("INSERT INTO users (user_id, fname, lname)VALUES (2000, 'john111', 'smith1111');");
		
		ResultSet results = session.execute("SELECT * FROM posts ");
		for (Row row : results) {
			
		Post P =new Post(row.getString("body"),Integer.parseInt(row.getString("hour")),Integer.parseInt(row.getString("year")),Integer.parseInt(row.getString("month")));			
		Posts.add(P);
			
		}
		
		return Posts;
	}
	public void AddPost(Post P){
		
		int taille=GetAllPosts().size()+1;
		Cluster cluster;
		Session session;
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("Test");
		
		//session.execute("INSERT INTO users (user_id, fname, lname)VALUES (2000, 'john111', 'smith1111');");
		
		ResultSet results = session.execute("INSERT INTO test.posts (post_id,body,hour,year,month) VALUES ("+taille+",'" + P.getBody() + "','" + P.getHour() + "','" + P.getYear() + "','" + P.getMonth() + "') ");
		
		
		
	}	
	public Vector<User> GetAllUsers(){
		Vector<User> Users=new Vector<User>();
		Cluster cluster;
		Session session;
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("Test");
		
		//session.execute("INSERT INTO users (user_id, fname, lname)VALUES (2000, 'john111', 'smith1111');");
		
		ResultSet results = session.execute("SELECT * FROM Users ");
		
		for (Row row : results) {
			Vector<String> Emails=new Vector<String> ();
			Set<String> S=new HashSet<String>();
			S=row.getSet("emails",String.class);
			
			for (String email : S) {
				Emails.add(email);
			}
			
			User U=new User(row.getString("nom"),row.getString("prenom"),row.getString("login"),row.getString("password"),Emails);
			
			
		
			Users.add(U);
		}
		cluster.close();
		
		return Users;
	}
	
	public void AddUser(User U){
		
		
		
		int Taille =this.GetAllUsers().size()+1;
		String Emails="";
		int i=1;
		for (String email : U.getEmails()) {
			if(i!=U.getEmails().size()){
			Emails+="'"+email+"',";
			i++;
		}
			else {
			Emails+="'"+email+"'";
			}
		}
		
		
		Cluster cluster;
		Session session;
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("Test");
		
		//session.execute("INSERT INTO users (user_id, fname, lname)VALUES (2000, 'john111', 'smith1111');");
		
		 session.execute("INSERT INTO test.users (user_id,nom,prenom,emails,login,password) VALUES ("+Taille+",'" + U.getNom() + "','" + U.getPrenom() + "',{"+Emails+"},'" + U.getLogin() + "','" + U.getPassword() + "') ");
		
		
		
	}
	
//	private boolean exist(User U) {
//		Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
//		Session session = cluster.connect("Test");
//		ResultSet rs = session
//				.execute("select * from test.users where nom='" + U.getNom() + "'");
//		if (rs.isExhausted()) {
//			cluster.close();
//			return false;
//		}
//
//		cluster.close();
//		return true;
//		
//	}
	public boolean authentification(User U) {

		Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		Session session = cluster.connect("Test");
		ResultSet rs = session
				.execute("select * from test.users where login='" + U.getLogin() + "'");
		if (rs.isExhausted()) {
			cluster.close();
			return false;
		}

		cluster.close();
		return true;

	}
	public static void main(String[] args) {
		DbAcces DAO=new DbAcces();
		
	 DAO.AddPost(new Post("post0", 0,2015,1));
	 DAO.AddPost(new Post("post1", 1,2015,1));
	 DAO.AddPost(new Post("post2", 2,2015,1));
	 DAO.AddPost(new Post("post3", 3,2015,1));
	 DAO.AddPost(new Post("post4", 4,2015,1));
	 
		Vector<Post> Users=DAO.GetAllPosts();
		for (Post user : Users) {
			System.out.println(user);
		}
	}
}
