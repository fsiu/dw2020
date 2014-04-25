package com.fivehundredpx.api.tasks;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.fivehundredpx.api.PxApi;

public class UserDetailTask extends AsyncTask<Object, Void, JSONObject> {
	private static final String TAG = "UserDetailTask";

	private String url = "/users.json";

	public interface Delegate {
		public void onSuccess(JSONObject user);
		public void onFail();
	}

	private Delegate _d;

	public UserDetailTask(Delegate d) {
		this._d = d;
	}

	@Override
	protected JSONObject doInBackground(Object... params) {
		final PxApi api = (PxApi) params[0];
		
		final JSONObject obj = api.get(url);
		try {
			return obj.getJSONObject("user");
		} catch (JSONException e) {
			Log.e(TAG, "", e);
			_d.onFail();
			return null;
		}
	}

	@Override
	protected void onPostExecute(JSONObject result) {

		if (result != null)
			_d.onSuccess(result);
	}

}
