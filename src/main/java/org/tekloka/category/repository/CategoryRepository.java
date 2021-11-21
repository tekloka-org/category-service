package org.tekloka.category.repository;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tekloka.category.document.Category;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String>{
	
	Set<Category> findByActive(boolean active);

	Optional<Category> findByUrlPathAndActive(String urlPath, boolean active);
}

	
	