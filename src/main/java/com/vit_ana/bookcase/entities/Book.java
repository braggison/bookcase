package com.vit_ana.bookcase.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {
	@Id
	private UUID id;
	private String title;

	public Book() {
		id = UUID.randomUUID();
	}

	@ManyToMany
	@JoinTable(name = "book_authors", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
	private List<Author> authors;

	public Author addAuthor(Author author) {
		getAuthors().add(author);
		if (!author.getBooks().contains(this))
			author.addBook(this);

		return author;
	}

	public List<Author> getAuthors() {
		if (authors == null)
			authors = new ArrayList<>();
		return authors;
	}

}
