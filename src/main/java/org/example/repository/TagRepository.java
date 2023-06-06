package org.example.repository;

import jakarta.persistence.LockModeType;
import org.example.entity.Question;
import org.example.entity.Tag;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
    List<Tag> findByNameIn(List<String> tagNames);

    Tag findByName(String tagName);
}
