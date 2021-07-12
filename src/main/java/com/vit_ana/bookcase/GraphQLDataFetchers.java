package com.vit_ana.bookcase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vit_ana.bookcase.entities.Author;
import com.vit_ana.bookcase.entities.Book;
import com.vit_ana.bookcase.repositories.AuthorRepository;
import com.vit_ana.bookcase.repositories.BookRepository;

import graphql.schema.DataFetcher;

@Component
public class GraphQLDataFetchers {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	AuthorRepository authorRepository;

	public DataFetcher<List<Book>> getAllBooksDataFetcher() {
		return dataFetchingEnvironment -> {
			return bookRepository.findAll();
		};
	}

	public DataFetcher<List<Book>> getBooksByAuthorDataFetcher() {
		return dataFetchingEnvironment -> {
			String authorName = dataFetchingEnvironment.getArgument("authorName");
			return bookRepository.findBookByAuthors_Name(authorName);
		};
	}

	public DataFetcher<List<Author>> getAuthorDataFetcher() {
		return dataFetchingEnvironment -> {
			String name = dataFetchingEnvironment.getArgument("name");
			return authorRepository.findAuthorsByName(name);
		};
	}
}
