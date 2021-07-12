package com.vit_ana.bookcase;

import java.util.List;

import javax.transaction.Transactional;

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

	@Transactional
	public DataFetcher<Book> saveBookDataFetcher() {
		return dataFetchingEnvironment -> {
			Book book = new Book();
			// book.setTitle(saveBookInput.getTitle());
			return bookRepository.saveAndFlush(book);
		};
	}

	@Transactional
	public DataFetcher<Author> saveAuthorDataFetcher() {
		return dataFetchnigEnvironment -> {
			Author author = new Author();
			// author.setName(saveAuthorInput.getName());
			return authorRepository.saveAndFlush(author);
		};
	}
}
