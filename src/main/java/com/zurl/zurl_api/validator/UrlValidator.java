package com.zurl.zurl_api.validator;

import java.net.URI;
import java.util.regex.Pattern;
import org.springframework.web.util.InvalidUrlException;
import com.zurl.zurl_api.constant.ExceptionConstants;

public class UrlValidator {
	
	private static final Pattern KEY_PATTERN = Pattern.compile("^[A-Za-z0-9-_]{7}$");

	public static void validateKey(String key) {
	    if (key == null || !KEY_PATTERN.matcher(key).matches()) {
	        throw new InvalidUrlException(ExceptionConstants.INVALID_URL);
	    }
	}
	
	public static void validateUrl(String url) {
	    try {
	        URI uri = new URI(url);
	        String scheme = uri.getScheme();
	        String host = uri.getHost();

	        if (url == null || url.isBlank()
	                || scheme == null
	                || !(scheme.equals("http") || scheme.equals("https"))
	                || host == null
	                || !host.contains(".")
	                || host.startsWith(".")
	                || host.endsWith(".")) {

	            throw new InvalidUrlException(ExceptionConstants.INVALID_URL);
	        }

	    } catch (Exception e) {
	        throw new InvalidUrlException(ExceptionConstants.INVALID_URL);
	    }
	}
}
