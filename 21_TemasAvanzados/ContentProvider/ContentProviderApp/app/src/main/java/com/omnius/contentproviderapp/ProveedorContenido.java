    package com.omnius.contentproviderapp;

    import android.content.ContentProvider;
    import android.content.ContentUris;
    import android.content.ContentValues;
    import android.content.Context;
    import android.content.UriMatcher;
    import android.database.Cursor;
    import android.database.SQLException;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteQueryBuilder;
    import android.net.Uri;
    import android.text.TextUtils;

    public class ProveedorContenido extends ContentProvider{

        public static final String NOMBRE_PROVEEDOR = "com.omnius.contentproviderapp.provider.Usuarios";
        public static final Uri CONTENT_URI = Uri.parse("content://"+ NOMBRE_PROVEEDOR + "/usuarios");

        private static final int TODOS_USUARIOS = 1;
        private static final int USUARIO_ID = 2;

        private SQLiteDatabase database;
        private DBHelper dbHelper;

        private static final UriMatcher coincideURI = new UriMatcher(UriMatcher.NO_MATCH);

        @Override
        public boolean onCreate() {
            coincideURI.addURI(NOMBRE_PROVEEDOR, "usuarios", TODOS_USUARIOS);
            coincideURI.addURI(NOMBRE_PROVEEDOR, "usuarios/#", USUARIO_ID);

            Context context = getContext();
            dbHelper = new DBHelper(context);
            return false;
        }

        @Override
        public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables("usuario");

            switch (coincideURI.match(uri)) {
                case TODOS_USUARIOS:
                    break;
                case USUARIO_ID :
                    queryBuilder.appendWhere("id" + "=" + uri.getPathSegments().get(1));
                    break;
                default:
                    throw new IllegalArgumentException("URI desconocida " + uri);
            }

            String orderBy = TextUtils.isEmpty(sortOrder) ? "id asc" : sortOrder;
            database = dbHelper.getReadableDatabase();
            Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, orderBy);
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        }

        @Override
        public String getType(Uri uri) {
            switch (coincideURI.match(uri)) {
                case TODOS_USUARIOS:
                    return "vnd.android.cursor.dir/vnd.com.omnius.contentproviderapp.provider.Usuarios.usuarios ";
                case USUARIO_ID:
                    return "vnd.android.cursor.item/vnd.com.omnius.contentproviderapp.provider.Usuarios.usuarios ";
                default:
                    throw new IllegalArgumentException("URI no admitida: " + uri);
            }
        }

        @Override
        public Uri insert(Uri uri, ContentValues values) {
            database = dbHelper.getWritableDatabase();
            long IDnuevo = database.insert("usuario", null, values);

            if (IDnuevo>0) {
                Uri _uri = ContentUris.withAppendedId(CONTENT_URI, IDnuevo);
                getContext().getContentResolver().notifyChange(_uri, null);
                database.close();
                return _uri;
            }
            throw new SQLException("Error al insertar usuario en la BD\n" + uri);
        }

        @Override
        public int delete(Uri uri, String selection, String[] selectionArgs) {
            return 0;
        }

        @Override
        public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
            return 0;
        }
    }



