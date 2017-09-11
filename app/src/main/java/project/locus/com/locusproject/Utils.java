package project.locus.com.locusproject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import static android.content.Context.ACCOUNT_SERVICE;

/**
 * Created by Dipesh on 11-09-2017.
 */

public class Utils {
    public static final String TAG = Utils.class.getSimpleName();

    public static boolean createSyncAccount(Context context, String name, String accoutType) {
        Account newAccount = new Account(name, accoutType);
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(ACCOUNT_SERVICE);
        return accountManager.addAccountExplicitly(newAccount, null, null);
    }

    public static boolean checkIfAccountExists(Context context, String name, String accoutType) {
        AccountManager accountManager =
                (AccountManager) context.getSystemService(ACCOUNT_SERVICE);
        Account[] accounts = accountManager.getAccountsByType(accoutType);
        if (accounts != null) {
            for (Account account: accounts) {
                if (name.equals(account.name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkAndCreateAccount(Context context, String name, String accountType) {
        if (!checkIfAccountExists(context, name, accountType)) {
             if(createSyncAccount(context, name, accountType)) {
                 Log.d(TAG, "account created");
                 createPeriodicSync(name, accountType);
                 return true;
             }
             else {
                 return false;
             }
        }
        Log.d(TAG, "account exists");
        return true;
    }


    public static void createPeriodicSync(String accountName, String accountType) {
        Log.d(TAG, "periodicsync created");
        Account account = new Account(accountName, accountType);
        ContentResolver.addPeriodicSync(
                account,
                Constants.ACCOUNT_AUTHORITY,
                Bundle.EMPTY,
                30*60);

    }

    public static void requestSync(Context context) {
        Account account = new Account(Constants.ACCOUNT_NAME, Constants.ACCOUNT_TYPE);
        ContentResolver.requestSync(account, Constants.ACCOUNT_AUTHORITY, null);
    }
}
