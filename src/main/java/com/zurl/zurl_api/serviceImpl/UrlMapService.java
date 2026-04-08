package com.zurl.zurl_api.serviceImpl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.InvalidUrlException;
import com.zurl.zurl_api.constant.ExceptionConstants;
import com.zurl.zurl_api.entity.UrlMap;
import com.zurl.zurl_api.repo.UrlMapRepo;
import com.zurl.zurl_api.service.iUrlShorteningService;

@Service
public class UrlMapService {
	
	private final UrlMapRepo urlMapRepo;
	
	private final iUrlShorteningService urlShorteningService;
	
	private final RedisService redisService;
	
	@Value("${app.base-url}")
	private String baseUrl; 
	
	public ResponseEntity<Void> redirectToLongUrl(String key) {
		String longUrl = null;
		try {
			longUrl = redisService.get(key, String.class);
		} catch (Exception exception) {}
		
		if (longUrl == null) {
			Optional<UrlMap> urlMapOptional =  urlMapRepo.findByEncodedKey(key);
		 	if (urlMapOptional.isEmpty()) {
		 		throw new InvalidUrlException(ExceptionConstants.INVALID_URL);
		 	} else {
		 		longUrl = urlMapOptional.get().getLongUrl();
		 		try {
					redisService.set(key, longUrl);
				} catch (Exception exception) {}
		 	}
		}
		return ResponseEntity
	            .status(302)
	            .header("Location", longUrl)
	            .build();
	}
	
	@Transactional
	public String shortTheUrl(String longUrl) {
		UrlMap newUrlMap = urlMapRepo.save(new UrlMap(longUrl));
		String key = urlShorteningService.encode(newUrlMap.getId());
		newUrlMap.setEncodedKey(key);
		urlMapRepo.save(newUrlMap);
		
		try {
			redisService.set(key, longUrl);
		} catch (Exception exception) {}
		return getShortUrl(key);
	}
	
	private String getShortUrl(String key) {
		return baseUrl + "/" + key;
	}

	public UrlMapService(UrlMapRepo urlMapRepo, iUrlShorteningService urlShorteningService, RedisService redisService) {
		this.urlMapRepo = urlMapRepo;
		this.urlShorteningService = urlShorteningService;
		this.redisService = redisService;
	}
}
