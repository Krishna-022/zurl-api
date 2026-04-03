package com.zurl.zurl_api.service;

import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisService {
	
	private final RedisTemplate<String, Object> redisTemplate;

	public <T> T get(String key, Class<T> valueClass) {
		Object valueObject = redisTemplate.opsForValue().get(key);
		return valueClass.cast(valueObject);
	}

	public void set(String key, Object value, Long ttl) {
		redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
	}

	public void set(String key, Object value) {
		redisTemplate.opsForValue().set(key, value, 1, TimeUnit.HOURS);
	}

	public RedisService(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
}
