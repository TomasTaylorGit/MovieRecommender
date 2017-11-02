package models;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable{

	//Serial for serialization
	private static final long serialVersionUID = 3468803324366831145L;
	private int userID;
	private String firstName;
	private String lastName;
	private int age;
	private char gender;
	private List<Rating> ratings;

	public User(int userID, String firstName, String lastName, int age, char gender, List<Rating> ratings){
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.ratings = ratings;
	}

	// GETTERS
	
	public int getUserID() {
		return userID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

	public char getGender() {
		return gender;
	}

	public List<Rating> getRatings(){
		return ratings;
	}
	
	public void addRating(int movieID, int rating){
		ratings.add(new Rating(userID, movieID, rating));
	}
	
	public void setRating(int index,int movieID, int rating){
		ratings.set(index, new Rating(userID, movieID, rating));
	}
	
	public void removeRating(int index){
		ratings.remove(index);
	}
	
	@Override
	public String toString(){
		return userID+"  -  "+firstName+" "+lastName+"   Age: "+age+"   Gender: "+gender;
	}


}
