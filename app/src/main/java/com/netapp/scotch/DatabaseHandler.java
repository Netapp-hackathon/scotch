package com.netapp.scotch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by jay3 on 12/10/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ops";

    // Operations table name
    private static final String TABLE_OP = "operations";

    // Operations Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_OP_NAME = "opName";
    private static final String KEY_EID = "eId";
    private static final String KEY_RESULT = "result";
    private static final String KEY_CREATED = "created";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_OP_TABLE = "CREATE TABLE " + TABLE_OP
                + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_OP_NAME + " TEXT,"
                + KEY_EID + " INTEGER,"
                + KEY_RESULT + " INTEGER,"
                + KEY_CREATED + " TEXT"
                + ")";

        db.execSQL(CREATE_OP_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OP);

        // Create tables again
        onCreate(db);
    }

    // Adding new ops
    public void addOp(OpExtended op) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, op.geteId());
        values.put(KEY_OP_NAME, op.getopName());
        values.put(KEY_EID, op.geteId());
        values.put(KEY_RESULT, op.getresult());
        values.put(KEY_CREATED, getDateTime());

        // Inserting Row
        db.insert(TABLE_OP, null, values);
        db.close(); // Closing database connection
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    // Getting single op
    public OpExtended getOp(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_OP, new String[] { KEY_ID,
                        KEY_OP_NAME, KEY_EID, KEY_RESULT, KEY_CREATED }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        OpExtended op = new OpExtended(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)), cursor.getString(4));
        // return contact
        return op;
    }

    // Getting All Ops
    public List<OpExtended> getAllOps() {
        List<OpExtended> opList = new ArrayList<OpExtended>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_OP;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                OpExtended op = new OpExtended();

                op.setopId(Integer.parseInt(cursor.getString(0)));
                op.setopName(cursor.getString(1));
                op.seteId(Integer.parseInt(cursor.getString(2)));
                op.setresult(Integer.parseInt(cursor.getString(3)));
                op.setCretaed(cursor.getString(4));

                // Adding op to list
                opList.add(op);
            } while (cursor.moveToNext());
        }

        // return op list
        return opList;
    }

    // Getting ops Count
    public int getOpsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_OP;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating single op
    public int updateContact(OpExtended op) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_OP_NAME, op.getopName());
        values.put(KEY_EID, op.geteId());
        values.put(KEY_RESULT, op.getresult());
        values.put(KEY_CREATED, getDateTime());

        // updating row
        return db.update(TABLE_OP, values, KEY_ID + " = ?",
                new String[]{String.valueOf(op.getopId())});
    }

    // Deleting single op
    public void deleteContact(OpExtended op) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_OP, KEY_ID + " = ?",
                new String[]{String.valueOf(op.getopId())});
        db.close();
    }
}
