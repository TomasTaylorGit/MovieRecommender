package controllers;

import java.io.File;
import java.util.Scanner;

import models.Movie;
import models.Rating;
import models.User;

public class Driver {

	private RecommenderAPI recommender;
	private Scanner input;
	
	public static void main(String[] args){
		new Driver().run();
	}
	
	public void run(){
		File saveFile = new File("save.xml");
		recommender = new MovieRecommender(saveFile);
		input = new Scanner(System.in);
		
		if(saveFile.exists()){
			recommender.load(saveFile);
		}else{
			recommender.initialLoad();
		}
		
		runMenu();
		
	}
	
	private int displayMenu(){
		System.out.println("\n\nMOVIE RECOMMENDER! - PLEASE INPUT CHOICE");
		System.out.println("1) Get All Users");
		System.out.println("2) Get All Movies");
		System.out.println("3) Get All Ratings");
		System.out.println("4) Remove a User");
		System.out.println("5) Add a User");
		System.out.println("6) Add Rating");
		System.out.println("7) Get Top Ten Movies");
		System.out.println("0) Exit");
		System.out.print("=> ");
		int response = -1;
		
		try{
			response = input.nextInt();
		}catch(Exception e){
			System.out.println("Please put in an integer");
		}
		
		return response;
	}
	
	private void runMenu(){
		
		int choice = displayMenu();
		
		while (choice != 0){
			switch(choice){
			case 1:
				displayUsers();
				break;
			case 2:
				displayMovies();
				break;
			case 3:
				displayRatings();
				break;
			case 4:
				removeUser();
				break;
			case 5:
				addUser();
				break;
			case 6:
				addRating();
				break;
			case 7:
				getTopTen();
				break;
			default:
				System.out.println("Error");
				break;
			}
			
			input.nextLine(); //Scanner bug fix
			choice = displayMenu();
		}
		
		recommender.write();
		System.out.println("THE MENU HAS BEEN EXITED, DATA HAS BEEN SAVED TO FILE!");
		
	}
	
	private void displayUsers(){
		for(User user : recommender.getUsers()){
			System.out.println(user);
		}
	}
	
	private void displayMovies(){
		for(Movie movie : recommender.getMovies()){
			System.out.println(movie);
		}
	}
	
	private void displayRatings(){
		String ratingString = "";
		for(User user : recommender.getUsers()){
			for(Rating rating : user.getRatings()){
				ratingString = "User: "+user.getFirstName()+" "+user.getLastName()+"\t "+recommender.getMovie(rating.getMovieID()).getTitle()+
						"\t -> "+rating.getRating();
				System.out.println(ratingString);
			}
		}
		
	}
	
	private void getTopTen(){
		for(Movie movie : recommender.getTopTenMovies()){
			System.out.println(movie);
		}
	}
	
	private void removeUser(){
		int userID = -1;
		try{
			System.out.print("User ID: ");
			userID = input.nextInt();
			if(userID < 0) throw new Exception("UserID cannot be negative"); 
		}catch(Exception e){
			System.out.println("Please enter a valid Integer");
			return;
		}
		if(!recommender.removeUser(userID)){
			System.out.println("Could not remove the user");
			return;
		}
		System.out.println("User removed");
	}

	private void addUser(){
		String firstName, lastName;
		char gender;
		int age;
		
		System.out.print("Enter First Name: ");
		firstName = input.next();
		System.out.print("Enter Last Name: ");
		lastName = input.next();
		System.out.print("Gender: ");
		gender = input.next().toUpperCase().charAt(0);
		
		if(gender != 'F' && gender != 'M'){
			System.out.println("Please enter a valid Gender");
			return;
		}
		
		try{
			System.out.print("Age: ");
			age = input.nextInt();
			if(age < 0 || age > 100) throw new Exception("Age cannot be negative or over 100"); 
		}catch(Exception e){
			System.out.println("Please enter a valid Integer");
			return;
		}
		
		recommender.addUser(firstName, lastName, age, gender);
		System.out.println("User added");
	}

	private void addRating(){
		int userID, movieID, rating;
		
		try{
			System.out.print("UserID: ");
			userID = input.nextInt();
			if(userID < 0) throw new Exception("UserID cannot be negative"); 
			System.out.print("MovieID: ");
			movieID = input.nextInt();
			if(movieID < 0) throw new Exception("MovieID cannot be negative"); 
			System.out.print("Rating: ");
			rating = input.nextInt();
			if(rating < -5 || rating > 5) throw new Exception("Rating cannot be outside of -5 to 5"); 
		}catch(Exception e){
			System.out.println("Please enter a valid Integer");
			return;
		}
		
		if(recommender.addRating(userID, movieID, rating)){
			System.out.println("Rating added successfully");
			return;
		}
		
		System.out.println("Could not add Rating - check your UserID or MovieID is valid");
		
	}
	
}
