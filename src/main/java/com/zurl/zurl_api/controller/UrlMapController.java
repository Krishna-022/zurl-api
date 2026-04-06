package com.zurl.zurl_api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.zurl.zurl_api.constant.ApiPathConstants;
import com.zurl.zurl_api.dto.in.LongUrlInDto;
import com.zurl.zurl_api.dto.out.ShortUrlOutDto;
import com.zurl.zurl_api.serviceImpl.UrlMapService;
import com.zurl.zurl_api.validator.UrlValidator;
import jakarta.validation.Valid;

@RestController
public class UrlMapController {
	
	private final UrlMapService urlMapService;
	
	@PostMapping(ApiPathConstants.URL)
	public ShortUrlOutDto shortTheUrl(@RequestBody @Valid LongUrlInDto longUrlInDto) {
		UrlValidator.validateUrl(longUrlInDto.getLongUrl());
		String shortUrl = urlMapService.shortTheUrl(longUrlInDto.getLongUrl());
		return new ShortUrlOutDto(shortUrl);
	}

	public UrlMapController(UrlMapService urlMapService) {
		this.urlMapService = urlMapService;
	}
}
