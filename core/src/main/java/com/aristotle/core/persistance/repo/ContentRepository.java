package com.aristotle.core.persistance.repo;

import com.aristotle.core.enums.ContentType;
import com.aristotle.core.persistance.Content;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ContentRepository extends JpaRepository<Content, Long> {

    //Web
    @Query("select content from Content content where content.contentType=?1 and content.global=true order by dateCreated desc")
    public List<Content> getGlobalContent(String contentType, Pageable pageable);

    @Query("select content from Content content join content.locations locations where locations.id in ?2 and content.contentStatus='Published' and content.contentType=?1 order by content.dateCreated desc")
    public List<Content> getLocationPublishedContent(String contentType, Set<Long> locationIds, Pageable pageable);

    @Query("select count(content) from Content content join content.locations locations where content.contentType=?1 and locations.id in ?2 and content.contentStatus='Published'")
    public long getLocationPublishedContentCount(ContentType contentType, Set<Long> locationIds);

    @Query("select content from Content content where content.contentType=?1 and content.global=true and content.contentStatus='Published' order by content.dateCreated desc")
    public List<Content> getGlobalPublishedContent(ContentType contentType, Pageable pageable);

    @Query("select count(content) from Content content where content.contentType=?1 and content.global=true  and content.contentStatus='Published'")
    public long getGlobalPublishedContentCount(ContentType contentType);

    @Query("select content from Content content join content.locations locations where locations.id in ?1 and content.contentStatus='Published' and content.contentType in ('News', 'PressRelease') order by content.dateCreated desc")
    public List<Content> getLatestLocationPublishedContent(Set<Long> locationIds, Pageable pageable);

    @Query("select content from Content content where content.contentStatus='Published' and content.contentType in ('News', 'PressRelease') order by content.dateCreated desc")
    public List<Content> getLatestGlobalPublishedContent(Pageable pageable);

    //Admin
    @Query("select content from Content content where content.contentType=?1 order by content.dateCreated desc")
    public List<Content> getContent(ContentType contentType, Pageable pageable);

}
