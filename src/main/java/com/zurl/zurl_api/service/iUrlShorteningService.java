package com.zurl.zurl_api.service;

public interface iUrlShorteningService{
	
	String encode(Long id);
	
	Long decode(String key);
}
