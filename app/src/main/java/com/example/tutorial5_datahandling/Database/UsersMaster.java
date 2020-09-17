package com.example.tutorial5_datahandling.Database;

import android.provider.BaseColumns;

public class UsersMaster {
    public UsersMaster() {
    }

    /*Inner Class that defines the table contens*/
    public static class Users implements BaseColumns{
        public static final String TABLE_NAME ="users";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";
    }
}
