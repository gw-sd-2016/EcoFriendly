package tunca.tom.ecofriendlyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;


public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.main_preferences);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        if (key.equals("service_enabled")) {
            boolean serviceStatus = sharedPreferences.getBoolean("service_enabled", false);
            Intent mIntent = new Intent(getContext(), TripMonitor.class);
            if(serviceStatus){
                getContext().startService(mIntent);
            }else{
                getContext().stopService(mIntent);
            }
        }
        else if(key.equals("demo_mode")) {
            Log.d("success","success");
            exportDatabase();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }


    private void exportDatabase(){
        try
        {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()){
                File folder = new File(Environment.getExternalStorageDirectory() +
                        File.separator + "EcoFriendlyApp");

                if (!folder.exists()) {
                    folder.mkdir();
                }
                String currentDBPath = "/data/tunca.tom.ecofriendlyapp/databases/LocationData.db";
                String backupDBPath = "/EcoFriendlyApp/LocationData.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
                Toast.makeText(getActivity(), "Export Successful", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e) {
            Toast.makeText(getActivity(), "Export failed", Toast.LENGTH_SHORT).show();
        }
    }
}