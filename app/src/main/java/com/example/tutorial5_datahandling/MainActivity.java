package com.example.tutorial5_datahandling;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tutorial5_datahandling.Database.DBHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etusername,etpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etusername = findViewById(R.id.etUsername);
        etpassword = findViewById(R.id.etPassword);

    }

    public void addData(View view){
        DBHelper dbHelper = new DBHelper(this);

       long val = dbHelper.addInfo(etusername.getText().toString(),etpassword.getText().toString());

       if(val>0){
           Toast.makeText(this, "Data Successfully inserted", Toast.LENGTH_SHORT).show();
       }else{
           Toast.makeText(this, "Data Not inserted", Toast.LENGTH_SHORT).show();
       }
    }

    public void viewAll(View view){
        DBHelper dbHelper = new DBHelper(this);

        List unames = dbHelper.readAllInfo("user");

        Toast.makeText(this, unames.toString(), Toast.LENGTH_SHORT).show();
    }

    public void deleteData(View view){
        DBHelper dbHelper = new DBHelper(this);

        dbHelper.deleteInfo(etusername.getText().toString());

        Toast.makeText(this, etusername.getText().toString() + "delete successfully", Toast.LENGTH_SHORT).show();
    }

    public void updateData(View view){
        DBHelper dbHelper = new DBHelper(this);

        int val = dbHelper.updateInfo(etusername.getText().toString(),etpassword.getText().toString());

        if(val>0){
            Toast.makeText(this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Data not Updated", Toast.LENGTH_SHORT).show();
        }
    }

    public void signIn(View view){

        DBHelper dbHelper = new DBHelper(this);

        List usernames = dbHelper.readAllInfo("user");
        List passwords = dbHelper.readAllInfo("password");

        String user = etusername.getText().toString();
        String password = etpassword.getText().toString();

        if (usernames.indexOf(user)>=0){

            if (passwords.get(usernames.indexOf(user)).equals(password)){
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

}