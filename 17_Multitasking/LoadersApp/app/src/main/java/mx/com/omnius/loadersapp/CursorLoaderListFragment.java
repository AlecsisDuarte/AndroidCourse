package mx.com.omnius.loadersapp;


import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleCursorAdapter;


public class CursorLoaderListFragment extends ListFragment implements OnQueryTextListener, LoaderCallbacks<Cursor>{

    static final String[] COLUMNAS_TABLA_CONTACTOS = new String[]{
            Contacts._ID,
            Contacts.DISPLAY_NAME,
            Contacts.CONTACT_STATUS
    };
    SimpleCursorAdapter cursorAdapter;
    String cadenaFiltro;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setEmptyText("No existen contactos en el dispositivo");
        setHasOptionsMenu(true);

        cursorAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.activity_list_item,
                null, new String[] {Contacts.DISPLAY_NAME, Contacts.CONTACT_STATUS},
                new int[] {android.R.id.text1, android.R.id.text2}, 0);

        setListAdapter(cursorAdapter);
        setListShown(false);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItem = menu.add("Buscar");
        menuItem.setIcon(android.R.drawable.ic_menu_search);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SearchView searchView = new SearchView(getActivity());
        searchView.setOnQueryTextListener(this);

        menuItem.setActionView(searchView);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri;
        if(cadenaFiltro != null){
            uri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI, Uri.encode(cadenaFiltro));
        }else{
            uri = Contacts.CONTENT_URI;
        }

        String select = "((" + Contacts.DISPLAY_NAME + " NOTNULL) AND (" + Contacts.HAS_PHONE_NUMBER + "=1 ))";
        return new CursorLoader(getActivity(),
                uri,
                COLUMNAS_TABLA_CONTACTOS,
                select, null,
                Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
        setListShown(true);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        cadenaFiltro = !TextUtils.isEmpty(newText) ? newText : null;
        getLoaderManager().restartLoader(0, null, this);
        return false;
    }
}
