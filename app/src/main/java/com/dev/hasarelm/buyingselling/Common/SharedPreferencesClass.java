package com.dev.hasarelm.buyingselling.Common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public  class SharedPreferencesClass extends Activity {

    public static String SETTINGS = "SETTINGS";

    public static void setLocalSharedPreference(final Context con, String localSPKey, String localSPValue) {
        SharedPreferences localSP = con.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE+ Context.MODE_PRIVATE);
        SharedPreferences.Editor localBackupEditor = localSP.edit();
        localBackupEditor.putString(localSPKey, localSPValue);
        localBackupEditor.commit();
    }
    public static void setLocalSharedPreference_forint(final Context con, String localSPKey, int localSPValue) {
        SharedPreferences localSP = con.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE+ Context.MODE_PRIVATE);
        SharedPreferences.Editor localBackupEditor = localSP.edit();
        localBackupEditor.putInt(localSPKey, localSPValue);
        localBackupEditor.commit();
    }
    public static void ClearSharedPreference(final Context con, String localSPKey) {
        SharedPreferences localSP = con.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE+ Context.MODE_PRIVATE);
        SharedPreferences.Editor localBackupEditor = localSP.edit();
        //localBackupEditor.putString(localSPKey, localSPValue);

        //SharedPreferences settings = context.getSharedPreferences("PreferencesName", Context.MODE_PRIVATE);
        localSP.edit().remove(localSPKey).commit();

        //localBackupEditor.commit();
    }


}
