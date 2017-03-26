package bean;

public class BookBean {
	private String title;
	private String price;
	private String category;
	private String author;
	
	public BookBean(String title, String author, String price, String category) {

		this.title = title;
		this.author = author;
		this.price = price;
		this.category = category;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrice() {
		return this.price;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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
