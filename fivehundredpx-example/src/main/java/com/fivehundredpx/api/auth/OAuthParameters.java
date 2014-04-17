package com.fivehundredpx.api.auth;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import android.net.Uri;

final public class OAuthParameters {
	/**
	 * <p>
	 * Parameters.
	 * </p>
	 */
	private HashMap<String, String> params;

	/**
	 * <p>
	 * Create an instance of OAuthParameters class.
	 * </p>
	 * @param consumerSecret Consumer secret.
	 * @throws IllegalArgumentException If consumer key/secret is empty or null.
	 */
	public OAuthParameters(String consumerKey) {
		if (consumerKey == null
				|| (consumerKey = consumerKey.trim()).length() == 0) {
			throw new IllegalArgumentException(
				"Consumer key must not be empty/null");
		}
		//
		params = new HashMap<String,String>();
		params.put(OAuthConstants.TIMESTAMP, getTimestampInSeconds());
		params.put(OAuthConstants.SIGN_METHOD, "HMAC-SHA1");
		params.put(OAuthConstants.VERSION, "1.0");
		params.put(OAuthConstants.NONCE, getTimestampInSeconds());
		params.put(OAuthConstants.CONSUMER_KEY, consumerKey);
	}

	/**
	 * <p>
	 * Get a string with all the parameters sorted.
	 * </p>
	 * @return Sorted string.
	 */
	public String getSortedEncodedParamsAsString() {
		StringBuffer buffer = new StringBuffer();
		String[] sKeys = sortedKeys();
		//
		for (int i = 0; i < sKeys.length; i++) {
			buffer.append(HttpParameterUtil.encode(sKeys[i]));
			buffer.append('=');
			buffer.append(HttpParameterUtil.encode((String)params.get(sKeys[i])));
			
			//
			if (i +1 < sKeys.length) {
				buffer.append('&');
			}
		}
		//
		return buffer.toString();
	}

	/**
	 * 
	 * OAuth key="value",
	 * 
	 * 
	 * <p>
	 * Get the Authorization header value.
	 * </p>
	 * @return Value.
	 */
	public String getAuthorizationHeaderValue() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("OAuth ");
		//
		String[] sKeys = sortedKeys();
		for (int i = 0; i < sKeys.length; i++) {
			if (sKeys[i].startsWith(OAuthConstants.PARAM_PREFIX)) {
				buffer.append(sKeys[i]);
				buffer.append('=');
				buffer.append('"');
				buffer.append(
						Uri.encode((String)params.get(sKeys[i])));
				buffer.append("\", ");
			}
		}
		//
		return buffer.toString().substring(0, buffer.length() - 2);
	}

	/**
	 * <p>
	 * Put a given parameter.
	 * </p>
	 * @param key Parameter key.
	 * @param value Parameter value.
	 */
	public void put(String key, String value) {
		params.put(key, value);
	}

	/**
	 * <p>
	 * Get an array with all parameter keys sorted.
	 * </p>
	 * @return Sorted keys.
	 */
	private String[] sortedKeys() {
		final Set<String> keys = params.keySet();
		Object[] oKeys =  keys.toArray();
		Arrays.sort(oKeys);
		
		String[] sKeys = new String[oKeys.length];
		int i = 0;
		for (Object object : oKeys) {
			sKeys[i++] = object.toString();
		}
		
		return sKeys;
	}

	/**
	 * <p>
	 * Get the current time in seconds.
	 * </p>
	 * @return Timestamp.
	 */
	private String getTimestampInSeconds() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
}