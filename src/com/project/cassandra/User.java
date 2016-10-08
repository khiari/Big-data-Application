package com.project.cassandra;




import java.util.Vector;

public class User{
	
	private String Nom;
	private String Prenom;
	private String Login;
	private String Password;
	
	private Vector<String> Emails ;
	
	
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}


	




	@Override
	public String toString() {
		return "User [Nom=" + Nom + ", Prenom=" + Prenom + ", Login=" + Login
				+ ", Password=" + Password + ", Emails=" + Emails + "]";
	}







	public User(String nom, String prenom, String login, String password,
			Vector<String> emails) {
		super();
		Nom = nom;
		Prenom = prenom;
		Login = login;
		Password = password;
		Emails = emails;
	}







	public String getLogin() {
		return Login;
	}







	public void setLogin(String login) {
		Login = login;
	}







	public String getPassword() {
		return Password;
	}







	public void setPassword(String password) {
		Password = password;
	}







	public String getNom() {
		return Nom;
	}




	public void setNom(String nom) {
		Nom = nom;
	}




	public String getPrenom() {
		return Prenom;
	}




	public void setPrenom(String prenom) {
		Prenom = prenom;
	}




	public Vector<String> getEmails() {
		return Emails;
	}




	public void setEmails(Vector<String> emails) {
		Emails = emails;
	}

}