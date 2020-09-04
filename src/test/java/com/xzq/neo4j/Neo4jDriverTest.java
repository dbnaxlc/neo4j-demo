package com.xzq.neo4j;

import java.util.concurrent.TimeUnit;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import org.neo4j.driver.v1.Values;

public class Neo4jDriverTest {

	public static void main(String[] args) {
		Config config = Config.builder()
				.withMaxConnectionLifetime(30, TimeUnit.MINUTES)
				.withMaxConnectionPoolSize(50)
				.withConnectionAcquisitionTimeout(30, TimeUnit.SECONDS)
				.build();
		Driver driver = GraphDatabase.driver("bolt://172.16.5.20:7687", AuthTokens.basic("neo4j", "admin1234"), config);
		Session session = driver.session();
		try (Transaction tx = session.beginTransaction()) {
			tx.run("create(n:A1{NAME:{NAME},TITLE:{TITLE}})", Values.parameters("NAME", "james", "TITLE", "King"));
			tx.success();
		}
		String res = session.writeTransaction(new TransactionWork<String>() {

			@Override
			public String execute(Transaction tx) {
				StatementResult result = tx.run("create (test:A1) SET test.message = $message RETURN test.message + ', from node ' + id(test) ",
						Values.parameters("message", "wahaha"));
				return result.single().get(0).asString();
			}
		});
		System.out.println(res);
		try (Transaction tx = session.beginTransaction()) {
			StatementResult result = tx.run("match(a:A1) WHERE a.NAME = {NAME} RETURN a.NAME AS NAME,a.TITLE AS TITLE",
					Values.parameters("NAME","james"));
			while (result.hasNext()) {
				Record record = result.next();
				System.out
						.println(String.format("%s %s", record.get("TITLE").asString(), record.get("NAME").asString()));
			}
		}
		session.close();
		driver.close();
	}

}
