package com.fivehundredpx.api.auth;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import android.net.Uri;
import android.util.Base64;

import com.fivehundredpx.api.FiveHundredException;

public class XAuthProvider implements OAuthProvider {

	private static final String HMAC_SHA1 = "HmacSHA1";

	// from xauth
	private String username;
	private String password;

	// from oauth
	private String consumerKey;
	private String consumerSecret;
	private String tokenKey;
	private String tokenSecret;

	protected XAuthProvider() {

	}

	public XAuthProvider(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public void signForAccessToken(HttpPost req) throws FiveHundredException {

		OAuthParameters params = new OAuthParameters(this.consumerKey);
		final ArrayList<NameValuePair> tuples = buildParameters(params);
		
		params.put(OAuthConstants.TOKEN, this.tokenKey);

		// generate the base string for signature.
		String str = getSignatureBaseString(req, params);
		str = getSignature(str, consumerSecret, this.tokenSecret);
		params.put(OAuthConstants.SIGNATURE, str);
		// get the authorization header
		str = params.getAuthorizationHeaderValue();
		req.addHeader(OAuthConstants.HEADER, str);

		try {
			req.setEntity(new UrlEncodedFormEntity(tuples));
		} catch (UnsupportedEncodingException e) {
			throw new FiveHundredException(e);
		}

	}

	protected ArrayList<NameValuePair> buildParameters(OAuthParameters params) {
		ArrayList<NameValuePair> tuples = new ArrayList<NameValuePair>();
		tuples.add(new BasicNameValuePair(OAuthConstants.MODE, "client_auth"));
		params.put(OAuthConstants.MODE, "client_auth");
		tuples.add(new BasicNameValuePair(OAuthConstants.USERNAME, username));
		params.put(OAuthConstants.USERNAME, username);
		tuples.add(new BasicNameValuePair(OAuthConstants.PASSWORD, password));
		params.put(OAuthConstants.PASSWORD, password);

		return tuples;
	}

	private String getSignatureBaseString(HttpPost req, OAuthParameters params) {
		final String method = req.getMethod();
		final String url = Uri.encode(req.getURI().toString());

		final String sortedParams = Uri.encode(params
				.getSortedEncodedParamsAsString());
		return method + '&' + url + '&' + sortedParams;
	}

	private String getSignature(String data, String consumerSecret,
			String tokenSecret) {
		byte[] byteHMAC = null;
		try {
			Mac mac = Mac.getInstance(HMAC_SHA1);
			String oauthSignature = HttpParameterUtil.encode(consumerSecret)
					+ "&" + HttpParameterUtil.encode(tokenSecret);
			SecretKeySpec spec = new SecretKeySpec(oauthSignature.getBytes(),
					HMAC_SHA1);
			mac.init(spec);
			byteHMAC = mac.doFinal(data.getBytes());
		} catch (InvalidKeyException ike) {
			throw new AssertionError(ike);
		} catch (NoSuchAlgorithmException nsae) {
			throw new AssertionError(nsae);
		}
		return Base64.encodeToString(byteHMAC, Base64.NO_WRAP);
	}

	@Override
	public void setOAuthConsumer(String consumerKey, String consumerSecret) {
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
	}

	@Override
	public void setOAuthRequestToken(String requestTokenKey,
			String requestTokenSecret) {
		this.tokenKey = requestTokenKey;
		this.tokenSecret = requestTokenSecret;
	}
}
