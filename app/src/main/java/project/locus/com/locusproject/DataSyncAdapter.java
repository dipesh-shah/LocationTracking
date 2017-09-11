package project.locus.com.locusproject;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

/**
 * Created by Dipesh on 11-09-2017.
 */

public class DataSyncAdapter extends AbstractThreadedSyncAdapter {
    public DataSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult) {
        LocationSyncClient.synclocation();
    }


}
