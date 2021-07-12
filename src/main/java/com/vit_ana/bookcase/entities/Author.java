package com.vit_ana.bookcase.entities;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Author {

	private String id;
	private String name;
	private List<Book> books;
}
