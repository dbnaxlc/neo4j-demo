package com.xzq.neo4j.ogm;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Movie {
	@Id
	@GeneratedValue
	private Long id;
	private String title;
	private int released;

	@Relationship(type = "ACTS_IN", direction = Relationship.INCOMING)
	Set<Actor> actors = new HashSet<Actor>();

	public Movie() {
	}

	public Movie(String title, int year) {
		this.title = title;
		this.released = year;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getReleased() {
		return released;
	}

	public void setReleased(int released) {
		this.released = released;
	}

	public Set<Actor> getActors() {
		return actors;
	}

	public void setActors(Set<Actor> actors) {
		this.actors = actors;
	}

}
