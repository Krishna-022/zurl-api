package com.zurl.zurl_api.dto.out;

import java.util.Objects;

public class ShortUrlOutDto {
	
	private String shortUrl;

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	@Override
	public int hashCode() {
		return Objects.hash(shortUrl);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShortUrlOutDto other = (ShortUrlOutDto) obj;
		return Objects.equals(shortUrl, other.shortUrl);
	}

	@Override
	public String toString() {
		return "ShortUrlOutDto [shortUrl=" + shortUrl + "]";
	}

	public ShortUrlOutDto(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public ShortUrlOutDto() {}
	
}
