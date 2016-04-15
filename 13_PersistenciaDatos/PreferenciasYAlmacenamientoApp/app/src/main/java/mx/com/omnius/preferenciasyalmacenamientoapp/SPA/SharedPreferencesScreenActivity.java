package mx.com.omnius.preferenciasyalmacenamientoapp.SPA;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import mx.com.omnius.preferenciasyalmacenamientoapp.R;

public class SharedPreferencesScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PreferencesFragment preferencesFragment = new PreferencesFragment();
        fragmentTransaction.replace(android.R.id.content, preferencesFragment);
        fragmentTransaction.commit();
    }

    public static class PreferencesFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
