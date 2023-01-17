package pt.ipleiria.estg.dei.gymmethodmobile.listeners;

import android.content.Context;

public interface LoginListener {
    void onValidateLogin(final String token, final Integer user_id, final String username, final Context context);
}
