package com.vit_ana.bookcase.entities;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="authors")
public class Author {
	@Id
	private UUID id;
	private String name;
	
	@ManyToMany(mappedBy="authors")
	private List<Book> books;
}
