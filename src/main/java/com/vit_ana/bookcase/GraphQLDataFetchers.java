package com.vit_ana.bookcase;

import java.util.List;
import java.util.Map;

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
			Map<String, Object> arguments = dataFetchingEnvironment.getArguments();
			if (arguments.containsKey("input")) {
				Map input = (Map) arguments.get("input");
				if (input.containsKey("title"))
					book.setTitle((String) input.get("title"));
				if (input.containsKey("authors")) {
					for (Object authorName : (List) input.get("authors")) {
						String name = (String) authorName;
						Author author;
						if (authorRepository.countAuthorsByName(name) == 0) {
							author = new Author();
							author.setName((String) authorName);
							author = authorRepository.saveAndFlush(author);
						} else {
							author = authorRepository.findAuthorsByName(name).get(0);
						}
						book.addAuthor(author);
					}
				}
			}
			return bookRepository.saveAndFlush(book);
		};
	}

	@Transactional
	public DataFetcher<Author> saveAuthorDataFetcher() {
		return dataFetchingEnvironment -> {
			Author author = new Author();
			Map<String, Object> arguments = dataFetchingEnvironment.getArguments();
			if (arguments.containsKey("input")) {
				Map input = (Map) arguments.get("input");
				if (input.containsKey("name"))
					author.setName((String) input.get("name"));
				if (input.containsKey("books")) {
					for (Object bookTitle : (List) input.get("books")) {
						Book book = new Book();
						book.setTitle((String) bookTitle);
						book = bookRepository.saveAndFlush(book);
						author.addBook(book);
					}
				}
			}
			return authorRepository.saveAndFlush(author);
		};
	}
}
