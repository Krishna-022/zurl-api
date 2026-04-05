package com.zurl.zurl_api.validator;

import java.net.URI;
import org.springframework.web.util.InvalidUrlException;
import com.zurl.zurl_api.constant.ExceptionConstants;

public class UrlValidator {
	
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
