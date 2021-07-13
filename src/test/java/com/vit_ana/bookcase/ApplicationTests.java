package com.vit_ana.bookcase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.vit_ana.bookcase.entities.Author;
import com.vit_ana.bookcase.entities.Book;
import com.vit_ana.bookcase.repositories.AuthorRepository;
import com.vit_ana.bookcase.repositories.BookRepository;

@Testcontainers
@SpringBootTest
class ApplicationTests {
	@Container
	public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:13.1").withPassword("sa")
			.withUsername("sa");

	@DynamicPropertySource
	static void postgresqlProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
		registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
		registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
	}

	@Test
	void contextLoads() {
	}

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Test
	public void testBookcaseWithDb() {

		Book book = new Book();
		book.setTitle("Test book");
		Author john = new Author();
		john.setName("John Doe");
		john = authorRepository.save(john);
		Author jane = new Author();
		jane.setName("Jane Doe");
		jane = authorRepository.save(jane);
		book.addAuthor(john);
		book.addAuthor(jane);
		bookRepository.save(book);

		assertEquals(book.getAuthors().size(), 2);

		assertEquals(bookRepository.count(), 1);
		assertEquals(authorRepository.count(), 2);

		assertNotNull(authorRepository.findAll().get(0));
		assertNotNull(bookRepository.findAll().get(0));

		// TODO More tests!
	}

}
