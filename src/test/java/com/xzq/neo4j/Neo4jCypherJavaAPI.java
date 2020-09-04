package com.xzq.neo4j;

import java.io.File;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Neo4jCypherJavaAPI {

	public static void main(String[] args) {
		File file = new File("/home/admin/db/graph.db");
        GraphDatabaseService graphDB = new GraphDatabaseFactory().newEmbeddedDatabase(file);
        System.out.println("Server is up and Running");

        try(Transaction tx = graphDB.beginTx()){
            //通过Cypher查询获得结果
            StringBuilder sb = new StringBuilder();
            sb.append("MATCH (xzq)-[:IS_FRIEND_OF]->(USER)-[:HAS_SEEN]->(movie) ");
            sb.append("RETURN movie");
            Result result = graphDB.execute(sb.toString());
            //遍历结果
            while(result.hasNext()){
                //get("movie")和查询语句的return movie相匹配
                Node movie = (Node) result.next().get("movie");
                System.out.println(movie.getId() + " : " + movie.getProperty("name"));
            }

            tx.success();
            System.out.println("Done successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            graphDB.shutdown();
        }

	}

}
