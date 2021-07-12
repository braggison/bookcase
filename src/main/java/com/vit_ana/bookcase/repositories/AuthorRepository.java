package com.vit_ana.bookcase.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vit_ana.bookcase.entities.Author;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

}
