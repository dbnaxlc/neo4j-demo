package com.xzq.neo4j.movies.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class MovieService {
	
	public Map<String, Object> graph(Integer limit) {
		return new HashMap<String, Object>();
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Class.forName("org.neo4j.jdbc.bolt.impl.BoltNeo4jDriverImpl");
		Connection connection = DriverManager.getConnection("jdbc:neo4j:bolt://172.16.5.20:7687", "neo4j", "admin1234");
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("MATCH n =((n1:Person{name:'Keanu Reeves'})-[r]->(n2)) RETURN n LIMIT 25");
		while(resultSet.next()){
			List<Map<String, Object>> list = (List<Map<String, Object>>)resultSet.getObject("n");
			wrapNode(list.get(0));
			wrapRelationship(list.get(1));
			wrapNode(list.get(2));
		}
//		resultSet = statement.executeQuery("MATCH (n) RETURN n");
//		while(resultSet.next()){
//			System.out.println(resultSet.getObject("n"));
//		}
//			StatementResult result = tx.run("match(a:A1) WHERE a.NAME = {NAME} RETURN a.NAME AS NAME,a.TITLE AS TITLE",
//					Values.parameters("NAME","james"));
		connection.close();
	}

	private static void wrapNode(Map<String, Object> node) {
		System.out.println(node);
	}
	
	private static void wrapRelationship(Map<String, Object> relationship) {
		System.out.println(relationship);
	}
}
