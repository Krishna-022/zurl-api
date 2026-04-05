package com.zurl.zurl_api.dto.in;

import java.util.Objects;
import org.hibernate.validator.constraints.URL;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LongUrlInDto {
	
	@URL
	@NotBlank(message = "Long URL must not be empty")
	@Size(max = 2048, message = "Long URL cannot exceed 2048 characters")
	private String longUrl;

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	@Override
	public int hashCode() {
		return Objects.hash(longUrl);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LongUrlInDto other = (LongUrlInDto) obj;
		return Objects.equals(longUrl, other.longUrl);
	}
}
