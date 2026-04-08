package com.zurl.zurl_api.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import org.hibernate.annotations.CreationTimestamp;

import com.zurl.zurl_api.constant.DatabaseConstraintNames;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(name = DatabaseConstraintNames.UNIQUE_ENCODED_KEY, columnNames = "encoded_key")})
public class UrlMap {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	private String encodedKey;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String longUrl;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	public String getEncodedKey() {
		return encodedKey;
	}
	
	public Long getId() {
		return id;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdAt, encodedKey, id, longUrl);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UrlMap other = (UrlMap) obj;
		return Objects.equals(createdAt, other.createdAt) && Objects.equals(encodedKey, other.encodedKey)
				&& Objects.equals(id, other.id) && Objects.equals(longUrl, other.longUrl);
	}

	@Override
	public String toString() {
		return "UrlMap [encodedKey=" + encodedKey + ", longUrl=" + longUrl + ", createdAt=" + createdAt + "]";
	}

	public UrlMap(String encodedKey, String longUrl, LocalDateTime createdAt) {
		this.encodedKey = encodedKey;
		this.longUrl = longUrl;
		this.createdAt = createdAt;
	}
	
	public UrlMap(String longUrl) {
		this.longUrl = longUrl;
	}

	public UrlMap() {}
}
