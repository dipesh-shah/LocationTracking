package project.locus.com.locusproject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Dipesh on 11-09-2017.
 */

public class AuthenticatorService extends Service{
    @Override
    public IBinder onBind(Intent intent) {
        CustomAuthenticator authenticator = new CustomAuthenticator(this);
        return authenticator.getIBinder();
    }
}
