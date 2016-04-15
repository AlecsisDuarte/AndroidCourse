package mx.com.omnius.listviewytablayoutapp.TabLayout;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;
import mx.com.omnius.listviewytablayoutapp.R;
import mx.com.omnius.listviewytablayoutapp.listas.ListaPersonalizadaActivity;
import mx.com.omnius.listviewytablayoutapp.listas.ListaSimpleActivity;

public class TabLayoutActivity extends TabActivity {
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);

        tabHost = getTabHost();

        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("lista_simple");
        tabSpec1.setIndicator("Lista Simple");
        Intent intentListaSimple = new Intent(this, ListaSimpleActivity.class);
        tabSpec1.setContent(intentListaSimple);

        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("lista_personalizada");
        tabSpec2.setIndicator("Lista Personalizada");
        Intent intentListaPerso = new Intent(this, ListaPersonalizadaActivity.class);
        tabSpec2.setContent(intentListaPerso);

        tabHost.addTab(tabSpec1);
        tabHost.addTab(tabSpec2);
    }
}
