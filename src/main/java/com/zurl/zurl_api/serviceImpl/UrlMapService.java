package com.zurl.zurl_api.serviceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zurl.zurl_api.entity.UrlMap;
import com.zurl.zurl_api.repo.UrlMapRepo;
import com.zurl.zurl_api.service.iUrlShorteningService;

@Service
public class UrlMapService {
	
	private final UrlMapRepo urlMapRepo;
	
	private final iUrlShorteningService urlShorteningService;
	
	@Value("${app.base-url}")
	private String baseUrl; 
	
	@Transactional
	public String shortTheUrl(String longUrl) {
		UrlMap newUrlMap = urlMapRepo.save(new UrlMap(longUrl));
		String key = urlShorteningService.encode(newUrlMap.getId());
		newUrlMap.setEncodedKey(key);
		urlMapRepo.save(newUrlMap);
		return getShortUrl(key);
	}
	
	private String getShortUrl(String key) {
		return baseUrl + "/" + key;
	}

	public UrlMapService(UrlMapRepo urlMapRepo, iUrlShorteningService urlShorteningService) {
		this.urlMapRepo = urlMapRepo;
		this.urlShorteningService = urlShorteningService;
	}
}
