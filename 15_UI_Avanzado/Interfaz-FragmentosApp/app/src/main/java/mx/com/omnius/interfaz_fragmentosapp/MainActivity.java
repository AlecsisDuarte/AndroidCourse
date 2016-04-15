package mx.com.omnius.interfaz_fragmentosapp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static class DetalleActivity extends AppCompatActivity{
        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);

            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                finish();
                return;
            }

            if(savedInstanceState == null){
                DetalleFragmento detalleFragmento = new DetalleFragmento();
                detalleFragmento.setArguments(getIntent().getExtras());
                getFragmentManager().beginTransaction().add(android.R.id.content, detalleFragmento).commit();
            }
        }
    }

    public static class ListaElementosFragment extends ListFragment {
        boolean dualPanel;
        int checkPosition = 0;

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, SO.sistemasOperativos));

            View detalleFrame = getActivity().findViewById(R.id.fDetalle);
            dualPanel = detalleFrame != null && detalleFrame.getVisibility() == View.VISIBLE;

            if (savedInstanceState != null) {
                checkPosition = savedInstanceState.getInt("choice", 0);
            }

            if (dualPanel) {
                getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                showDetail(checkPosition);
            } else {
                getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                getListView().setItemChecked(checkPosition, true);
            }
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            outState.putInt("choice", checkPosition);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            showDetail(position);
        }

        void showDetail(int index){
            checkPosition = index;

            if(dualPanel){
                getListView().setItemChecked(index, true);
                DetalleFragmento detalle = (DetalleFragmento) getFragmentManager().findFragmentById(R.id.fDetalle);

                if (detalle == null || detalle.getShownIndex() != index) {
                    detalle = DetalleFragmento.newInstance(index);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fDetalle, detalle);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }
            }else{
                Intent intent = new Intent();
                intent.setClass(getActivity(), DetalleActivity.class);
                intent.putExtra("index", index);
                startActivity(intent);
            }
        }
    }

    public static class DetalleFragmento extends Fragment {

        public static DetalleFragmento newInstance(int index) {
            DetalleFragmento f = new DetalleFragmento();
            Bundle args = new Bundle();
            args.putInt("index", index);
            f.setArguments(args);

            return f;
        }

        public int getShownIndex() {
            return getArguments().getInt("index", 0);
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            ScrollView scrollView = new ScrollView(getActivity());
            TextView text = new TextView(getActivity());
            int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
                    getActivity().getResources().getDisplayMetrics());
            text.setPadding(padding, padding, padding, padding);
            scrollView.addView(text);
            text.setText(SO.descripcion[getShownIndex()]);
            return scrollView;
        }

    }
}
