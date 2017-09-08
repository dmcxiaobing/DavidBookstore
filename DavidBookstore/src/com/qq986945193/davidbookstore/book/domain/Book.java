package com.qq986945193.davidbookstore.book.domain;

import com.qq986945193.davidbookstore.category.domain.Category;

/**
 * 书籍的实体类
 */
public class Book {
	private String bid;//图书ID
	private String bname;//图书名称
	private Double price;//图书价格
	private String author;//图书作者
	private String image;//图书image
	private Category category;//图书所属分类
	private Integer del;//是否被删除，1,已经删除了，0没有删除
	
	public Integer getDel() {
		return del;
	}
	public void setDel(Integer del) {
		this.del = del;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	@Override
	public String toString() {
		return "Book [bid=" + bid + ", bname=" + bname + ", price=" + price + ", author=" + author + ", image=" + image
				+ ", category=" + category + "]";
	}
	
}
