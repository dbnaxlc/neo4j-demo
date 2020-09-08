package com.xzq.neo4j.ogm;

import org.junit.Test;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

public class Neo4jOgmTest {

	@Test
	public void test() {
		//详见https://neo4j.com/docs/ogm-manual/current/reference/#reference:configuration
		Configuration configuration = new Configuration.Builder()
		        .uri("http://neo4j:admin1234@172.16.5.20:7474")
		        .build();
	    SessionFactory sessionFactory = new SessionFactory(configuration, "com.xzq.neo4j.ogm");
		Session session = sessionFactory.openSession();

		Movie movie = new Movie("The Matrix", 1999);

		Actor keanu = new Actor("Keanu Reeves");
		keanu.actsIn(movie);

		Actor carrie = new Actor("Carrie-Ann Moss");
		carrie.actsIn(movie);

		//Persist the movie. This persists the actors as well.
		session.save(movie);

		//Load a movie
		Movie matrix = session.load(Movie.class, movie.getId());
		for(Actor actor : matrix.getActors()) {
		    System.out.println("Actor: " + actor.getName());
		}
	}
}
