package tunca.tom.ecofriendlyapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public class SettingsActivity extends FragmentActivity {

    @Override
    protected void onCreate (Bundle savedInsteanceState){
        super.onCreate(savedInsteanceState);
        setContentView(R.layout.settings_activity);
    }

}
