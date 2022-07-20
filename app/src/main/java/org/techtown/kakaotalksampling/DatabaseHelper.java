package org.techtown.kakaotalksampling;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String NAME = "Friend.db";
    public static int VERSION = 1;
    public DatabaseHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists Friend(" + " name text, " +
                " stateMessage text, " +
                " mobile text, " +
                " lastMessage text, " +
                " lastDate text)";

        db.execSQL(sql);
    }

    public void onOpen(SQLiteDatabase db) {
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion>1) {
            db.execSQL("DROP TABLE IF EXISTS Friend");
        }
    }

    private void insertRecord(SQLiteDatabase db, String nm, String stateM, String m, String lastM,
                              String lastD) {

        db.execSQL("insert into Friend"+"(name, stateMessage, mobile, lastMessage, lastDate) "+
                " values "+
                "( nm, stateM, m, lastM, lastD)");
    }

    //pubic void println(String data)
}
