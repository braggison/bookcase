package com.vit_ana.bookcase;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;

import graphql.schema.DataFetcher;

@Component
public class GraphQLDataFetchers {
	private static List<Map<String, String>> books = Arrays.asList(
			ImmutableMap.of("id", "book-1", "title", "One", "authorId", "author-1"),
			ImmutableMap.of("id", "book-2", "title", "Two", "authorId", "author-2"),
			ImmutableMap.of("id", "book-3", "title", "Three", "authorId", "author-2"));

	private static List<Map<String, String>> authors = Arrays.asList(
			ImmutableMap.of("id", "author-1", "name", "John Doe"),
			ImmutableMap.of("id", "author-2", "name", "Jane Doe"));

	public DataFetcher getAllBooksDataFetcher() {
		return dataFetchingEnvironment -> {
			return books;
		};
	}

	public DataFetcher getBooksByAuthorDataFetcher() {
		return dataFetchingEnvironment -> {
			String authorId = dataFetchingEnvironment.getArgument("authorId");
			return books.stream().filter(book -> book.get("authorId").equals(authorId)).collect(Collectors.toList());
		};
	}

	public DataFetcher getAuthorDataFetcher() {
		return dataFetchingEnvironment -> {
			String name = dataFetchingEnvironment.getArgument("name");
			List filteredAuthors = authors.stream().filter(author -> author.get("name").equals(name))
					.collect(Collectors.toList());
			return filteredAuthors;
		};
	}
}
