package com.fivehundredpx.api.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.fivehundredpx.api.FiveHundredException;
import com.fivehundredpx.api.auth.AccessToken;
import com.fivehundredpx.api.auth.OAuthAuthorization;
import com.fivehundredpx.api.auth.XAuthProvider;

public class XAuth500pxTask extends AsyncTask<String, Void, AccessToken> {
	private static final String TAG = "XAuth500pxTask";
	
	public interface Delegate {
		public void onSuccess(AccessToken result);
		public void onFail(FiveHundredException e);
	}

	private Delegate _d;
	public XAuth500pxTask(Delegate delegate) {
		this._d = delegate;
	}

	@Override
	protected AccessToken doInBackground(String... params) {
		final String consumerKey = params[0];
		final String consumerSecret = params[1];
		final String _user = params[2];
		final String _password = params[3];

		try {

			final OAuthAuthorization oauth = new OAuthAuthorization.Builder()
					.consumerKey(consumerKey)
					.consumerSecret(consumerSecret)
					.build();

			final AccessToken accessToken = oauth
					.getAccessToken(new XAuthProvider(_user, _password));

			return accessToken;

		} catch (FiveHundredException e) {
			final String msg = String.format("error %d for username[%s]",
					e.getStatusCode(), _user);
			Log.e(TAG, msg, e);
			_d.onFail(e);
		}

		return null;

	}

	@Override
	protected void onPostExecute(AccessToken result) {

		if (null != result)
			_d.onSuccess(result);

	}
}
