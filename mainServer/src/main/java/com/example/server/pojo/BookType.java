package com.example.server.pojo;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Node
public class BookType {
	@Id
	@GeneratedValue
	private Long id;

	private String typeName;

	private List<Integer> bookIDs;

	private BookType(){}

	public BookType(String typeName){this.typeName = typeName;}

	@Relationship(type = "relateBooks")
	public Set<BookType> relateBookTypes;


	public void addRelateBookType(BookType bookType){
		if(relateBookTypes == null)
			relateBookTypes = new HashSet<>();
		relateBookTypes.add(bookType);
	}

	public void addBookID(int id){
		if(bookIDs == null)
			bookIDs = new ArrayList<>();
		for (Integer bookID : bookIDs) {
			if (bookID == id)
				return;
		}
		bookIDs.add(id);
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setBookIDs(List<Integer> bookIDs) {
		this.bookIDs = bookIDs;
	}

	public List<Integer> getBookIDs() {
		return bookIDs;
	}


}
