// repository/PostRepository.java
package org.example.repository;

import org.example.model.DiscussionPost;
import java.util.List;

public interface PostRepository {
    void create(DiscussionPost post);
    List<DiscussionPost> getAll();
    DiscussionPost getById(int id);
    void update(DiscussionPost post);
    void delete(int id);
}
