package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Elements
    EditText et_name,et_email,et_number;
    TextView tv_display;
    Button btn_write,btn_read,btn_update,btn_remove;
    MyHelper myhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        myhelper = new MyHelper( this );
        myhelper.getWritableDatabase();

        et_name = findViewById( R.id.et_name );
        et_email = findViewById( R.id.et_email );
        et_number = findViewById( R.id.et_number );
        tv_display = findViewById( R.id.tv_display );
        btn_write = findViewById( R.id.btn_write );
        btn_read = findViewById( R.id.btn_read );
        btn_update = findViewById( R.id.btn_update );
        btn_remove = findViewById( R.id.btn_remove );

        refresh();

        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                write();
                refresh();
            }
        });
        btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                read();
                refresh();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
                refresh();
            }
        });
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove();
                refresh();
            }
        });



    }

    void refresh(){
        String s = "------------\n- No Records -\n------------";
        tv_display.setText( s );
        SQLiteDatabase db = myhelper.getReadableDatabase();
        Cursor cursor = db.query( "info_user",null,null,null,null,null,null );
        if(cursor.getCount() == 0)
            tv_display.setText(s);
        else{
            cursor.moveToFirst();
            tv_display.setText( "NAME:" + cursor.getString( 0 ) + "\nEMAIL:" + cursor.getString( 1 ) + "\nNUMBER:" + cursor.getString( 2 ) );
        }
        while(cursor.moveToNext()){
            tv_display.append( "\n\n" + "NAME:" + cursor.getString( 0 ) + "\nEMAIL:" + cursor.getString( 1 ) + "\nNUMBER:" + cursor.getString( 2 ) );
        }
        db.close();

    }

    void write(){
        String name = et_name.getText().toString();
        String email = et_email.getText().toString();
        String number = et_number.getText().toString();
        if(name.isEmpty()||email.isEmpty()||number.isEmpty())
            Toast.makeText(MainActivity.this, "Infomation isn't complete!", Toast.LENGTH_SHORT).show();
        else{
            SQLiteDatabase db = myhelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put( "name",name );
            values.put( "email",email );
            values.put( "number",number );
            long i = db.insert( "info_user",null,values );
            db.close();
            Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT).show();
        }

    }

    void read(){
        String number = et_number.getText().toString();
        if(number.isEmpty())
            Toast.makeText(MainActivity.this, "Need Number!", Toast.LENGTH_SHORT).show();
        else{
            SQLiteDatabase db = myhelper.getReadableDatabase();
            Cursor cursor = db.query( "info_user",null,"number=?",new String[]{number},null,null,null );
            db.close();
            Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT).show();
        }
    }

    void update(){
        String name = et_name.getText().toString();
        String email = et_email.getText().toString();
        String number = et_number.getText().toString();
        if(name.isEmpty()||email.isEmpty()||number.isEmpty())
            Toast.makeText(MainActivity.this, "Infomation isn't complete!", Toast.LENGTH_SHORT).show();
        else{
            SQLiteDatabase db = myhelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put( "name",name );
            values.put( "email",email );
            db.update( "info_user",values,"number=?",new String[]{number} );
            db.close();
            Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT).show();
        }
    }

    void remove(){
        String number = et_number.getText().toString();
        if(number.isEmpty())
            Toast.makeText(MainActivity.this, "Need Number!", Toast.LENGTH_SHORT).show();
        else{
            SQLiteDatabase db = myhelper.getWritableDatabase();
            db.delete( "info_user","number=?",new String[]{number} );
            db.close();
            Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT).show();
        }
    }
}
