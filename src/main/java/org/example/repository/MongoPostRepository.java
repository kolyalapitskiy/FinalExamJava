package org.example.repository;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.example.model.DiscussionPost;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class MongoPostRepository implements PostRepository {

    private final MongoCollection<Document> collection;

    public MongoPostRepository() {
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = client.getDatabase("lms_forum");
        collection = database.getCollection("discussion_posts");
    }

    @Override
    public void create(DiscussionPost post) {
        Document doc = new Document("title", post.getTitle())
                .append("author", post.getAuthor())
                .append("content", post.getContent());

        collection.insertOne(doc);
    }

    @Override
    public List<DiscussionPost> getAll() {
        List<DiscussionPost> list = new ArrayList<>();

        for (Document doc : collection.find()) {
            list.add(map(doc));
        }
        return list;
    }

    @Override
    public DiscussionPost getById(int id) {
        // MongoDB использует ObjectId → для консоли берём первый
        Document doc = collection.find().first();
        return doc != null ? map(doc) : null;
    }

    @Override
    public void update(DiscussionPost post) {
        collection.updateOne(
                Filters.eq("_id", new ObjectId()),
                Updates.combine(
                        Updates.set("title", post.getTitle()),
                        Updates.set("author", post.getAuthor()),
                        Updates.set("content", post.getContent())
                )
        );
    }

    @Override
    public void delete(int id) {
        collection.deleteOne(Filters.eq("_id", new ObjectId()));
    }

    private DiscussionPost map(Document doc) {
        return new DiscussionPost(
                0,
                doc.getString("title"),
                doc.getString("author"),
                doc.getString("content")
        );
    }
}
