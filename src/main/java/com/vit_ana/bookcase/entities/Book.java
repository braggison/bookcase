package com.vit_ana.bookcase.entities;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {
	private String id;
	private String title;
	private List<Author> authors;

}
