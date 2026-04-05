package com.zurl.zurl_api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.zurl.zurl_api.entity.UrlMap;

public interface UrlMapRepo extends JpaRepository<UrlMap, Long> {
	
}
