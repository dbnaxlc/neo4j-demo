package com.xzq.neo4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Neo4jJdbcDriverTest {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Class.forName("org.neo4j.jdbc.bolt.impl.BoltNeo4jDriverImpl");
		Connection connection = DriverManager.getConnection("jdbc:neo4j:bolt://172.16.5.20:7687", "neo4j", "admin1234");
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("MATCH (n) RETURN n");
		while(resultSet.next()){
			System.out.println(resultSet.getString(1));
		}
//			StatementResult result = tx.run("match(a:A1) WHERE a.NAME = {NAME} RETURN a.NAME AS NAME,a.TITLE AS TITLE",
//					Values.parameters("NAME","james"));
		connection.close();
	}

}
