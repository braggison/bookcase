package com.vit_ana.bookcase.inputs;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveBookInput {
	private String title;
	private List<String> authors;
}
