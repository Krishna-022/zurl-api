package com.zurl.zurl_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.zurl.zurl_api.constant.ApiPathConstants;
import com.zurl.zurl_api.dto.in.LongUrlInDto;
import com.zurl.zurl_api.serviceImpl.UrlMapService;
import com.zurl.zurl_api.validator.UrlValidator;

import jakarta.validation.Valid;

@RestController
public class UrlMapController {
	
	private final UrlMapService urlMapService;
	
	@PostMapping(ApiPathConstants.URL)
	public ResponseEntity<String> shortTheUrl(@RequestBody @Valid LongUrlInDto longUrlInDto) {
		UrlValidator.validateUrl(longUrlInDto.getLongUrl());
		String shortUrl = urlMapService.shortTheUrl(longUrlInDto.getLongUrl());
		return ResponseEntity.status(HttpStatus.CREATED).body(shortUrl);
	}

	public UrlMapController(UrlMapService urlMapService) {
		this.urlMapService = urlMapService;
	}
}
