package com.vit_ana.bookcase.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vit_ana.bookcase.entities.Book;

public interface BookRepository extends JpaRepository<Book, UUID> {
	public List<Book> findBookByAuthors_Name(String name);
}
