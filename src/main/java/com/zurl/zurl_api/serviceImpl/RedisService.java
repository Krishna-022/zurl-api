package com.zurl.zurl_api.serviceImpl;

import java.util.concurrent.TimeUnit;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisService {
	
	private final RedisTemplate<String, Object> redisTemplate;

	public <T> T get(String key, Class<T> valueClass) throws Exception {
		Object valueObject = null;
		try {
			valueObject = redisTemplate.opsForValue().get(key);
		} catch (DataAccessException dataAccessException) {
			throw new Exception();
		}
		return valueClass.cast(valueObject);
	}

	public void set(String key, Object value, Long ttl) throws Exception {
		try {
			redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
		} catch (DataAccessException dataAccessException) {
			throw new Exception();
		}
	}

	public void set(String key, Object value) throws Exception {
		try {
			redisTemplate.opsForValue().set(key, value, 1, TimeUnit.HOURS);
		} catch (DataAccessException dataAccessException) {
			throw new Exception();
		}
	}

	public RedisService(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
}
