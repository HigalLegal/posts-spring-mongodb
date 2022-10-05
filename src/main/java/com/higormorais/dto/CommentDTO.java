package com.higormorais.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class CommentDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String text;
	private LocalDate date;
	private AuthorDTO author;
	
	// -------------------------------------------------------------------------------------------------------------
	
	public CommentDTO() {}

	public CommentDTO(String text, LocalDate date, AuthorDTO author) {
		this.text = text;
		this.date = date;
		this.author = author;
	}
	
	// -------------------------------------------------------------------------------------------------------------

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDate getLocalDate() {
		return date;
	}

	public void setLocalDate(LocalDate date) {
		this.date = date;
	}

	public AuthorDTO getAuthor() {
		return author;
	}

	public void setAuthor(AuthorDTO author) {
		this.author = author;
	}
}
