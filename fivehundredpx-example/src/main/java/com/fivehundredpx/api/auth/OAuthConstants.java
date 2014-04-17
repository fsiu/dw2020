package com.fivehundredpx.api.auth;

final public class OAuthConstants {
	/**
	 * <p>
	 * TIMESTAMP.
	 * </p>
	 */
	public static final String TIMESTAMP = "oauth_timestamp";

	/**
	 * <p>
	 * SIGN_METHOD.
	 * </p>
	 */
	public static final String SIGN_METHOD = "oauth_signature_method";

	/**
	 * <p>
	 * SIGNATURE.
	 * </p>
	 */
	public static final String SIGNATURE = "oauth_signature";

	/**
	 * <p>
	 * CONSUMER_KEY.
	 * </p>
	 */
	public static final String CONSUMER_KEY = "oauth_consumer_key";

	/**
	 * <p>
	 * VERSION.
	 * </p>
	 */
	public static final String VERSION = "oauth_version";

	/**
	 * <p>
	 * NONCE.
	 * </p>
	 */
	public static final String NONCE = "oauth_nonce";

	/**
	 * <p>
	 * PARAM_PREFIX.
	 * </p>
	 */
	public static final String PARAM_PREFIX = "oauth_";

	/**
	 * <p>
	 * TOKEN.
	 * </p>
	 */
	public static final String TOKEN = "oauth_token";

	/**
	 * <p>
	 * EMPTY_TOKEN_SECRET.
	 * </p>
	 */
	public static final String EMPTY_TOKEN_SECRET = "";

	/**
	 * <p>
	 * HEADER.
	 * </p>
	 */
	public static final String HEADER = "Authorization";

	/**
	 * <p>
	 * MODE.
	 * </p>
	 */
	public static final String MODE = "x_auth_mode";

	/**
	 * <p>
	 * USERNAME.
	 * </p>
	 */
	public static final String USERNAME = "x_auth_username";

	/**
	 * <p>
	 * PASSWORD.
	 * </p>
	 */
	public static final String PASSWORD = "x_auth_password";


	public static final String X_TOKEN = "x_auth_token";
	
	public static final String X_SECRET = "x_auth_secret";

	
	/**
	 * <p>
	 * CALLBACK.
	 * </p>
	 */
	public static final String CALLBACK = "oauth_callback";

	/**
	 * <p>
	 * VERIFIER.
	 * </p>
	 */
	public static final String VERIFIER = "oauth_verifier";

	/**
	 * <p>
	 * Private constructor to avoid object instantiation.
	 * </p>
	 */
	private OAuthConstants() {
	}
}
