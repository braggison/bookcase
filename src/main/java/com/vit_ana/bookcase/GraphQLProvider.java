package com.vit_ana.bookcase;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring;

@Component
public class GraphQLProvider {
	@Autowired
	GraphQLDataFetchers graphQLDataFetchers;

	@Autowired
	private ResourceLoader resourceLoader;

	private GraphQL graphQL;

	@Bean
	public GraphQL graphQL() {
		return graphQL;
	}

	private GraphQLSchema buildSchema(String schema) {
		TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(schema);
		RuntimeWiring runtimeWiring = buildWiring();
		SchemaGenerator schemaGenerator = new SchemaGenerator();
		return schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
	}

	private RuntimeWiring buildWiring() {
		return RuntimeWiring.newRuntimeWiring()
				.type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getAllBooks",
						graphQLDataFetchers.getAllBooksDataFetcher()))
				.type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getBooksByAuthor",
						graphQLDataFetchers.getBooksByAuthorDataFetcher()))
				.type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getAuthor",
						graphQLDataFetchers.getAuthorDataFetcher()))
				.type(TypeRuntimeWiring.newTypeWiring("Book").dataFetcher("author",
						graphQLDataFetchers.getAuthorDataFetcher()))
				.build();

	}

	@PostConstruct
	public void init() throws IOException {
		Resource resource = resourceLoader.getResource("classpath:schema.graphqls");
		InputStream schemaInputStream = resource.getInputStream();
		byte[] bdata = FileCopyUtils.copyToByteArray(schemaInputStream);
		String schema = new String(bdata, StandardCharsets.UTF_8);
		GraphQLSchema graphQLSchema = buildSchema(schema);
		this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
	}
}