package bean;

public class ReviewBean {

	/*********Attributes************/
	private String bid;
	private String fullName;
	private int rating;
	private String review;
	private int uid;

	/*********Constructor************/
	public ReviewBean(String bid, int uid, String fullName, int rating, String review) {
		this.bid = bid;
		this.uid = uid;
		this.fullName = fullName;
		this.rating = rating;
		this.review = review;
	}
	
	public ReviewBean(String bid, int rating, String review) {
		this.bid = bid;
		this.rating = rating;
		this.review = review;
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
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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
