package org.tekloka.category.repository;
import java.util.Optional;

import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.tekloka.category.document.Page;
import org.tekloka.category.dto.PageLinkDTO;

@Repository
public interface PageRepository extends MongoRepository<Page, String>{
	
	Set<Page> findByCategoryUrlPathAndActive(String categoryUrlPath, boolean active);

	@Query(value = "{'categoryUrlPath' : ?0 , 'active' : ?1}", fields="{_id:1, title: 1, shortTitle: 1, urlPath: 1, index: 1}")
	Set<PageLinkDTO> findCategoryPages(String categoryUrlPath, boolean active);
	
	Optional<Page> findByCategoryUrlPathAndUrlPathAndActive(String categoryUrlPath, String pageUrlPath, boolean active);
	
}

	
	