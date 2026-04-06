package com.zurl.zurl_api.serviceImpl;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.InvalidUrlException;

import com.zurl.zurl_api.constant.ExceptionConstants;
import com.zurl.zurl_api.exception.ServiceUnavailableException;
import com.zurl.zurl_api.service.iUrlShorteningService;

@Service
public class UrlShorteningService implements iUrlShorteningService {
	
	private static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_".toCharArray();
    private static final int LENGTH = 7;
    private static final int TOTAL_BITS = LENGTH * 6;   // 42
    private static final int HALF_BITS = TOTAL_BITS / 2; // 21
    private static final long MASK21 = (1L << HALF_BITS) - 1L;
    private static final int ROUNDS = 8;
    private final SecretKeySpec hmacKey;

    public UrlShorteningService(@Value("${app.shortener-secret}") String secret) {
        this.hmacKey = new SecretKeySpec(normalizeKey(secret), "HmacSHA256");
    }

    @Override
	public String encode(Long id) {
        if (id < 0 || id >= (1L << TOTAL_BITS)) {
            throw new ServiceUnavailableException(ExceptionConstants.SERVICE_UNAVAILABLE) ;
        }
        long permuted = feistelEncrypt(id);
        return toBase64Url7(permuted);
    }
    
	@Override
	public Long decode(String key) {
        if (key == null || key.length() != LENGTH) {
            throw new InvalidUrlException(ExceptionConstants.INVALID_URL);
        }
        long value = fromBase64Url7(key);
        return feistelDecrypt(value);
    }

    private long feistelEncrypt(long id) {
        int left = (int) ((id >>> HALF_BITS) & MASK21);
        int right = (int) (id & MASK21);

        for (int round = 0; round < ROUNDS; round++) {
            int newLeft = right;
            int f = roundFunction(right, round);
            int newRight = (left ^ f) & (int) MASK21;
            left = newLeft;
            right = newRight;
        }
        return (((long) left) << HALF_BITS) | (right & MASK21);
    }

    private long feistelDecrypt(long feistelCode) {
        int left = (int) ((feistelCode >>> HALF_BITS) & MASK21);
        int right = (int) (feistelCode & MASK21);

        for (int round = ROUNDS - 1; round >= 0; round--) {
            int newRight = left;
            int f = roundFunction(left, round);
            int newLeft = (right ^ f) & (int) MASK21;
            left = newLeft;
            right = newRight;
        }
        return (((long) left) << HALF_BITS) | (right & MASK21);
    }

    private int roundFunction(int half, int round) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(hmacKey);

            ByteBuffer input = ByteBuffer.allocate(8);
            input.putInt(round);
            input.putInt(half);

            byte[] digest = mac.doFinal(input.array());

            // Take 21 bits from the HMAC output
            int v = ((digest[0] & 0xFF) << 16)
                  | ((digest[1] & 0xFF) << 8)
                  |  (digest[2] & 0xFF);

            return v & (int) MASK21;
        } catch (Exception exception) {
            throw new ServiceUnavailableException(ExceptionConstants.SERVICE_UNAVAILABLE);
        }
    }

    private String toBase64Url7(long value) {
        char[] out = new char[LENGTH];
        for (int i = LENGTH - 1; i >= 0; i--) {
            out[i] = ALPHABET[(int) (value & 63L)];
            value >>>= 6;
        }
        return new String(out);
    }

    private long fromBase64Url7(String code) {
        long value = 0L;
        for (int i = 0; i < code.length(); i++) {
            int idx = alphabetIndex(code.charAt(i));
            if (idx < 0) {
                throw new InvalidUrlException(ExceptionConstants.INVALID_URL);
            }
            value = (value << 6) | idx;
        }
        return value;
    }

    private int alphabetIndex(char c) {
        if (c >= INDEX.length) return -1;
        return INDEX[c];
    }

    private static final int[] INDEX = buildIndex();

    private static int[] buildIndex() {
        int[] idx = new int[128];
        Arrays.fill(idx, -1);
        for (int i = 0; i < ALPHABET.length; i++) {
            idx[ALPHABET[i]] = i;
        }
        return idx;
    }

    private byte[] normalizeKey(String secret) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            return sha256.digest(secret.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceUnavailableException(ExceptionConstants.SERVICE_UNAVAILABLE);
        }
    }

}
