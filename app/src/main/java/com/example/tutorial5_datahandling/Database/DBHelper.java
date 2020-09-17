package com.example.tutorial5_datahandling.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserInfo.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
                String SQL_CREATE_ENTRIES =
                        "CREATE TABLE " + UsersMaster.Users.TABLE_NAME +"(" +
                                UsersMaster.Users._ID + " INTEGER PRIMARY KEY," +
                                UsersMaster.Users.COLUMN_NAME_USERNAME+ " TEXT," +
                                UsersMaster.Users.COLUMN_NAME_PASSWORD+ " TEXT)";

                //Use the details from the UserMaster and Users Classes we created.Specify the primery key from the BaseColomns.

        db.execSQL(SQL_CREATE_ENTRIES);    //This will execute the contents of SQL_CREATE_ENTRIES
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long addInfo(String userName, String password) {
        //Gets the data repository in the write mode
        SQLiteDatabase db = getWritableDatabase();

        //Create a new map of values, where column names the keys
        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.COLUMN_NAME_USERNAME,userName);
        values.put(UsersMaster.Users.COLUMN_NAME_PASSWORD,password);

        //Insert the new row, returning the primery key value of the new row
        long newRowId = db.insert(UsersMaster.Users.TABLE_NAME,null,values);
        return newRowId;
    }

    public List readAllInfo(String req){
        SQLiteDatabase db = getReadableDatabase();

        //define a projection that specifies which columns from the database
        //you will actually use after this query
        String[] projection = {
                UsersMaster.Users._ID,
                UsersMaster.Users.COLUMN_NAME_USERNAME,
                UsersMaster.Users.COLUMN_NAME_PASSWORD
        };

        //Filter results Where "userName" = 'SLIIT USER'
        //String selection = Users.COLUMN_NAME_USERNAME + "= ? ";
        //String[] selectionArgs = {""};

        //How you want the results sorted in the resulting cursor
        String sortOrder = UsersMaster.Users.COLUMN_NAME_USERNAME + " DESC";

        Cursor cursor = db.query(
                UsersMaster.Users.TABLE_NAME,           //the table to query
                projection,                             //the columns to return
                null,                          //the columns for the WHERE clause
                null,                      //the values for the WHERE clause
                null,                          //don't group the rows
                null,                           //don't filter by row groups
                sortOrder                               //the sort order
        );

        List userNames = new ArrayList<>();
        List passwords = new ArrayList<>();

        while(cursor.moveToNext()){
            String username = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USERNAME));
            String password =  cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_PASSWORD));
            userNames.add(username);
            passwords.add(password);
        }
        cursor.close();
        Log.i(TAG,"readAllInfo: " + userNames);

        if(req =="user"){
            return  userNames;
        }else if(req=="password"){
            return passwords;
        }else{
            return null;
        }
    }

    public void deleteInfo(String userName){
        SQLiteDatabase db = getReadableDatabase();
        //Define 'Where' part of query
        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + " LIKE ?";
        //Specify arguments n spaceholder order
        String[] selectionArgs = { userName };
        //Issue SQL statement
        db.delete(UsersMaster.Users.TABLE_NAME,selection,selectionArgs);
    }

    public int updateInfo(String userName, String password) {
        SQLiteDatabase db = getReadableDatabase();

        //New value for one column
        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.COLUMN_NAME_PASSWORD,password);

        //Which row to update, based on the title
        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + " LIKE ?";
        String[] selectionArgs = { userName };

        int count = db.update(
                UsersMaster.Users.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );

        return count;
    }

}
