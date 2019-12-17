package com.xzq.neo4j;

import java.io.File;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.schema.IndexDefinition;

public class Neo4jTest {
	
	private static void registerShutdownHook(final GraphDatabaseService graphDB) {
        Runtime.getRuntime().addShutdownHook(
                new Thread() {
                    public void run() {
                        System.out.println("Server is shutting down");
                        graphDB.shutdown();
                    }
                }
        );
    }

	public static void main(String[] args) {
		File file = new File("/home/admin/db/graph.db");
		GraphDatabaseService dbService = new GraphDatabaseFactory().newEmbeddedDatabase(file);
		System.out.println("Server is up and Running");
		try(Transaction tx = dbService.beginTx()) {
			Node user1 = dbService.createNode(MyLabels.USERS);
			user1.setProperty("name", "xzq");
			Node user2 = dbService.createNode(MyLabels.USERS);
			user2.setProperty("name", "yh");
			user1.createRelationshipTo(user2, MyRelationshipTypes.IS_FRIEND_OF);
			
			Node movie1 = dbService.createNode(MyLabels.MOVIES);
            movie1.setProperty("name", "F战狼");
            Node movie2 = dbService.createNode(MyLabels.MOVIES);
            movie2.setProperty("name", "Alien");
            Node movie3 = dbService.createNode(MyLabels.MOVIES);
            movie3.setProperty("name", "Heat");
            
            Relationship relationship1 = user1.createRelationshipTo(movie1, MyRelationshipTypes.HAS_SEEN);
            relationship1.setProperty("score", 5);
            relationship1.setProperty("stars", 5);
            
            Relationship relationship2 = user1.createRelationshipTo(movie2, MyRelationshipTypes.HAS_SEEN);
            relationship2.setProperty("stars", 4);
            relationship2.setProperty("score", 4);
            
            Relationship relationship3 = user2.createRelationshipTo(movie1, MyRelationshipTypes.HAS_SEEN);
            relationship3.setProperty("stars", 5);
            relationship3.setProperty("score", 4);
            
            Relationship relationship4 = user2.createRelationshipTo(movie3, MyRelationshipTypes.HAS_SEEN);
            relationship4.setProperty("stars", 5);
            relationship4.setProperty("score", 5);
           
            /**用Java代码创建索引要在Neo4j服务关闭的情况下，索引创建完之后，再启动服务，这时候索引状态初始为POPULATING 。
             * 不是执行完创建索引命令就会生成索引，而是要先启动服务，等待索引状态由POPULATING变为ONLINE，然后才可以使用索引
             * 在浏览器执行:schema命令显示如下：
             * Indexes
             *    	ON :User(name) POPULATING 
             *      No constraints
             * 接下来开始生成索引，需要花一点时间。索引生成之后的状态为ONLINE：
             * Indexes
             *    	ON :User(name) ONLINE 
             * 如果在索引正在创建而还未完成的过程中，重启了服务，索引状态会变为FAILED：
             * Indexes
             *    	ON :User(name) FAILED 
             * 
             */
            dbService.schema().indexFor(MyLabels.USERS).on("name").create();
            //遍历users节点的所有索引并删除
		    for (IndexDefinition indexDefinition : dbService.schema().getIndexes(Label.label("users"))) {
		        indexDefinition.drop();
		     }
            tx.success();
		}
		System.out.println("done");
		registerShutdownHook(dbService);
	}

}

/**
 * Label类型枚举类
 */
enum MyLabels implements Label {
    MOVIES, USERS
}

enum MyRelationshipTypes implements RelationshipType {
    IS_FRIEND_OF, HAS_SEEN
}
