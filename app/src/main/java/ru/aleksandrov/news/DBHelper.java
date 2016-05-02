package ru.aleksandrov.news;

    import java.util.ArrayList;
    import java.util.List;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.database.sqlite.SQLiteStatement;
    import android.provider.BaseColumns;

public class DBHelper extends SQLiteOpenHelper implements BaseColumns{
        public static final String ID_NEWS = "id_news";
        public static final String TEXT = "text";
        public static final String SRC_SMALL = "src_small";
        public static final String SRC = "src";
        public static final String SRC_BIG = "src_big";
        public static final String COMMENTS = "comments";
        public static final String LIKES = "likes";
        public static final String REPOST = "repost";
        private static final String DATABASE_NAME = "newsData.db";
        public static final String TABLE = "wall";
        private static final String DATABASE_CREATE_SCRIPT = "create table "
            + TABLE + " (" + BaseColumns._ID
            + " integer primary key autoincrement, " + ID_NEWS
            + " text not null, " + TEXT + " text, " +SRC_SMALL + " text, " + SRC + " text, " + SRC_BIG + " text, " + COMMENTS + " text, " + LIKES
            + " text, " + REPOST +" text);";
        private static final int DATABASE_VERSION = 1;
        private Context context;
        private SQLiteDatabase db;
        //private static final String INSERT = "insert into " + TABLE
         //       + "(name,country,data_date,data_time,cloudsValue,degreeValue,humidityValue) values(?,?,?,?,?,?,?)";
        private SQLiteStatement insertStmt;

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                                  int version) {
                super(context, name, factory, version);
            }
        public long insert(String name, String country,String data_date,String data_time,String cloudsValue,String degreeValue,String humidityValue) {
            this.insertStmt.bindString(1, name);
            this.insertStmt.bindString(2, country);
            this.insertStmt.bindString(3, data_date);
            this.insertStmt.bindString(4, data_time);
            this.insertStmt.bindString(5, cloudsValue);
            this.insertStmt.bindString(6, degreeValue);
            this.insertStmt.bindString(7, humidityValue);
            return this.insertStmt.executeInsert();
        }
         public void deleteAll() {
            this.db.delete(TABLE, null, null);
        }
        public String readOne(int id) {
            List<String> list = new ArrayList<String>();
            Cursor cursor = this.db.query(TABLE, new String[] { "name" },null, null, null, null, "name desc");

            if (cursor.moveToFirst()) {
                do {
                  list.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            return list.get(id);
         }
        public List<String> selectAll() {
            List<String> list = new ArrayList<String>();
            Cursor cursor = this.db.query(TABLE, new String[] { "name" },null, null, null, null, "name desc");

            if (cursor.moveToFirst()) {
                do {
                  list.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            return list;
        }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF IT EXISTS " + TABLE);
        onCreate(db);
    }
}