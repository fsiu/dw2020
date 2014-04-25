package net.darkwire.example.tasks;

import net.darkwire.example.exception.AuthenticationError;
import net.darkwire.example.model.FiveHundredPxConfiguration;
import com.fivehundredpx.api.FiveHundredException;
import com.fivehundredpx.api.auth.AccessToken;
import com.fivehundredpx.api.auth.OAuthAuthorization;
import com.fivehundredpx.api.auth.XAuthProvider;

/**
 * Created by fsiu on 4/19/14.
 */
public class FiveHundredPxAccessToken {

    public static AccessToken getAccessToken(final FiveHundredPxConfiguration config) throws AuthenticationError {
        final AccessToken result;
        try {
            final OAuthAuthorization oauth = new OAuthAuthorization.Builder()
                    .consumerKey(config.getConsumerKey())
                    .consumerSecret(config.getConsumerSecret())
                    .build();
            result = oauth.getAccessToken(new XAuthProvider(config.getUsername(), config.getPassword()));
        } catch (FiveHundredException fhe) {
            throw new AuthenticationError(fhe);
        }
        return result;
    }

}
