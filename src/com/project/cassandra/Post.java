package com.project.cassandra;



public class Post{
	
	private String body;
	private int Hour,year,month;
	
	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Post(String body, int hour, int year, int month) {
		super();
		this.body = body;
		Hour = hour;
		this.year = year;
		this.month = month;
	}



	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getHour() {
		return Hour;
	}

	public void setHour(int hour) {
		Hour = hour;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	
	
	
}