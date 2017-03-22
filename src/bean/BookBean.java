package bean;

public class BookBean {
	private String bid;
	private String title;
<<<<<<< HEAD
	private String price;
	private String category;
	
	public BookBean(String bid, String title, String price, String category) {
=======
	private String author;
	private int price;
	private String category;
	
	public BookBean(String bid, String title, String author, int price, String category) {
>>>>>>> 12c8e014ff199811644d69bde6bcf5be1c5cdcfe
		super();
		this.bid = bid;
		this.title = title;
		this.author = author;
		this.price = price;
		this.category = category;
	}
	
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
<<<<<<< HEAD
	public String getPrice() {
=======
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	public int getPrice() {
>>>>>>> 12c8e014ff199811644d69bde6bcf5be1c5cdcfe
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	
	
}
