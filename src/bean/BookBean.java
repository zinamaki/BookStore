package bean;

public class BookBean {
	private String bid;
	private String title;
	private String price;
	private String category;
	
	public BookBean(String bid, String title, String price, String category) {
		super();
		this.bid = bid;
		this.title = title;
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
	public String getPrice() {
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
