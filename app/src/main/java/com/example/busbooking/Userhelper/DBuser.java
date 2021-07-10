package com.example.busbooking.Userhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBuser {
    public static final String KEY_MOBILE ="MOBILE";
    public static final String KEY_NAME ="NAME";
    public static final String KEY_CNIC ="CNIC";
    public static final String KEY_EMAIL ="EMAIL";
    public static final String KEY_PASSWORD ="PASSWORD";
    public static final String KEY_DOB ="DOB";
    public static final String KEY_GENDER ="GENDER";
    public static final String KEY_MONEY ="MONEY";
    public static final String KEY_TICKET ="TICKET";

    public static final String DATABASE_NAME="Recordsdb";
    public static final String DATABASE_TABLE="USERS";

    private final int DATABASE_VERSION=1;

    private DBhelper myhelper;
    private SQLiteDatabase mysql;
    private final Context mycontext;

    public DBuser(Context context){
        mycontext=context;
    }
    private class  DBhelper extends SQLiteOpenHelper{

        public DBhelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);

        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String sqlcode= "CREATE TABLE "+DATABASE_TABLE+" ("
                    +KEY_MOBILE+" TEXT PRIMARY KEY, "
                    +KEY_NAME+" TEXT NOT NULL, "
                    +KEY_CNIC+" TEXT NOT NULL, "
                    +KEY_DOB+" TEXT NOT NULL, "
                    +KEY_EMAIL+" TEXT NOT NULL, "
                    +KEY_GENDER+" TEXT NOT NULL, "
                    +KEY_PASSWORD+" TEXT NOT NULL, "
                    +KEY_MONEY+" TEXT, "
                    +KEY_TICKET+" TEXT);";
            db.execSQL(sqlcode);


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(db);
        }
    }

    public DBuser open(){
        myhelper = new DBhelper(mycontext);
        mysql=myhelper.getWritableDatabase();
        return this;
    }
    public void close(){
        myhelper.close();
    }
    public long createEntry(String mobile,String name,String cnic,String dob,String email,
                            String gender,String password,String money,String ticket){



        ContentValues cv = new ContentValues();
        cv.put(KEY_MOBILE,mobile);
        cv.put(KEY_NAME,name);
        cv.put(KEY_CNIC,cnic);
        cv.put(KEY_DOB,dob);
        cv.put(KEY_EMAIL,email);
        cv.put(KEY_GENDER,gender);
        cv.put(KEY_PASSWORD,password);
        cv.put(KEY_MONEY,money);
        cv.put(KEY_TICKET,ticket);
        long result=mysql.insert(DATABASE_TABLE,null,cv);
        if(result==-1){
            Toast.makeText(mycontext, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(mycontext, "Registration Successful", Toast.LENGTH_SHORT).show();
        }
        return result;

    }
    public void updateRecord(String mobile,String newName,String newEmail,String newCNIc){
        ContentValues cv= new ContentValues();
        cv.put(KEY_NAME,newName);
        cv.put(KEY_EMAIL,newEmail);
        cv.put(KEY_CNIC,newCNIc);
        long resultupdate = mysql.update(DATABASE_TABLE,cv,KEY_MOBILE+" =? ",new String[]{mobile});

        if(resultupdate == -1){
            Toast.makeText(mycontext, "Something wrong", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(mycontext, "Updated successfully", Toast.LENGTH_SHORT).show();
        }

    }
    public void addTicket(String mobile,String ticketid){
        ContentValues cv= new ContentValues();
        cv.put(KEY_TICKET,ticketid);
        long resultupdate = mysql.update(DATABASE_TABLE,cv,KEY_MOBILE+" =? ",new String[]{mobile});

    }
    public void deleteTicket(String mobile){
        ContentValues cv= new ContentValues();
        cv.put(KEY_TICKET, (String) null);
       long result = mysql.update(DATABASE_TABLE,cv,KEY_MOBILE+"=?",new String[]{mobile});

    }

}
