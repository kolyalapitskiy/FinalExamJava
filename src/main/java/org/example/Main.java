package org.example;// Main.java
import org.example.model.DiscussionPost;
import org.example.repository.*;
import org.example.util.DatabaseConfig;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        PostRepository repository =
                DatabaseConfig.USE_MONGO
                        ? new MongoPostRepository()
                        : new PostgresPostRepository();

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("""
                1. Create post
                2. View all posts
                3. Update post
                4. Delete post
                0. Exit
                """);

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Title: ");
                    String title = sc.nextLine();
                    System.out.print("Author: ");
                    String author = sc.nextLine();
                    System.out.print("Content: ");
                    String content = sc.nextLine();
                    repository.create(new DiscussionPost(title, author, content));
                }
                case 2 -> repository.getAll().forEach(p ->
                        System.out.println(p.getId() + ": " + p.getTitle()));
                case 3 -> {
                    System.out.print("ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("New title: ");
                    String t = sc.nextLine();
                    System.out.print("New author: ");
                    String a = sc.nextLine();
                    System.out.print("New content: ");
                    String c = sc.nextLine();
                    repository.update(new DiscussionPost(id, t, a, c));
                }
                case 4 -> {
                    System.out.print("ID: ");
                    repository.delete(sc.nextInt());
                }
                case 0 -> System.exit(0);
            }
        }
    }
}
