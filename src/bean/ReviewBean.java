package bean;

public class ReviewBean {

	/*********Attributes************/
	private String bid;
	private int uid;
	private int rating;
	private String review;
	
	/*********Constructor************/
	public ReviewBean(String bid, int uid, int rating, String review) {
		this.bid = bid;
		this.uid = uid;
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

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
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
