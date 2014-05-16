package com.quotenote;

import java.net.URI;
import java.util.Date;

public class Quote {
	
	private boolean isPrivate;
	private String origin;
	private String content;
	private String link;
	private String createdDate;
	private String modifiedDate;
	private String user;
	
	public Quote() {}
	
	public Quote(boolean isPrivate, String origin, String content, String link,
			String createdDate, String modifiedDate, String user) {

		this.isPrivate = isPrivate;
		this.origin = origin;
		this.content = content;
		this.link = link;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.user = user;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	
	

}
