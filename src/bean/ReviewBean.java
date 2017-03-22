package bean;

public class ReviewBean {

	/*********Attributes************/
	private String bid;
	private String username;
	private int rating;
	private String review;
	
	/*********Constructor************/
	public ReviewBean(String bid, String username, int rating, String review) {
		this.bid = bid;
		this.username = username;
		this.rating = rating;
		this.review = review;
	}
	
	public ReviewBean(int rating, String review) { 
		this.rating = rating;
		this.review = review;
	}
	
	public ReviewBean(String bid, int rating) {
		this.bid = bid;
		this.rating = rating;
	}

	/*********Methods************/
	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}	
}
