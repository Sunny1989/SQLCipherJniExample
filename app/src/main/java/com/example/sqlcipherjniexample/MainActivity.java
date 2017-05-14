package com.example.sqlcipherjniexample;

import android.content.ContentValues;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.sqlcipherjniexample.databinding.MainActivityBinding;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    // JNI part!
    static {
        System.loadLibrary("native-lib");
    }

    private MainActivityBinding mainActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //Loading Sqlcipher Library
        SQLiteDatabase.loadLibs(this);

        //Inserting data in Sqlcipher Sqlite DB!
        insertDataToDb();

        //Reading from DB!
        showDataFromDb();
    }


    private void insertDataToDb() {
        //Note : Wherever we use Sqlite classes, its all from net.sqlite.database.
        SQLiteDatabase db = DatabaseHelper.getInstance(this).getWritableDatabase(stringFromJNI());

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.WORD, "Word 1");
        values.put(DatabaseHelper.DEFINITION, "First Word");

        db.insert(DatabaseHelper.TABLE_NAME, null, values);

        db.close();
    }


    private void showDataFromDb() {
        SQLiteDatabase db = DatabaseHelper.getInstance(this).getWritableDatabase(stringFromJNI());
        Cursor cursor = db.rawQuery("SELECT * FROM '" + DatabaseHelper.TABLE_NAME + "';", null);
        Log.d(MainActivity.class.getSimpleName(), "Rows count: " + cursor.getCount());

        String dbValues = "";

        if (cursor.moveToFirst()) {
            do {
                dbValues = dbValues + "\n" + cursor.getString(0) + " , " + cursor.getString(1);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        mainActivityBinding.sampleText.setText(dbValues);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
