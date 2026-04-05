package com.zurl.zurl_api.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.zurl.zurl_api.entity.UrlMap;

public interface UrlMapRepo extends JpaRepository<UrlMap, Long> {
	
	Optional<UrlMap> findByEncodedKey(String key);
}
