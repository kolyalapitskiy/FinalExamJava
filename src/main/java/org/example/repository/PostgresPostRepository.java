// repository/PostgresPostRepository.java
package org.example.repository;

import org.example.model.DiscussionPost;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresPostRepository implements PostRepository {

    private final Connection connection;

    public PostgresPostRepository() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/lms_forum",
                "postgres",
                "1234"
        );
    }

    @Override
    public void create(DiscussionPost post) {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO discussion_posts(title, author, content) VALUES (?, ?, ?)")) {
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getAuthor());
            ps.setString(3, post.getContent());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<DiscussionPost> getAll() {
        List<DiscussionPost> list = new ArrayList<>();
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM discussion_posts");
            while (rs.next()) {
                list.add(new DiscussionPost(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("content")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public DiscussionPost getById(int id) {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM discussion_posts WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new DiscussionPost(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("content")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(DiscussionPost post) {
        try (PreparedStatement ps = connection.prepareStatement(
                "UPDATE discussion_posts SET title=?, author=?, content=? WHERE id=?")) {
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getAuthor());
            ps.setString(3, post.getContent());
            ps.setInt(4, post.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM discussion_posts WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
