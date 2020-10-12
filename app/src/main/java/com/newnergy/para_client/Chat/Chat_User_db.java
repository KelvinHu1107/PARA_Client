package com.newnergy.para_client.Chat;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by G7 on 15/09/2016.
 */
public class Chat_User_db extends SQLiteOpenHelper {
    private static final String TAG = "Chat_User_db";
    public static final String DB_NAME = "Chat_User.db";
    public static final int DB_VERSION = 1;
    public Context mContext;

    public Chat_User_db(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
    }

    public Chat_User_db(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    //create tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Chat_Table_Info.UserTable.CREATETABLESQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Chat_Table_Info.UserTable.DELETETABLESQL);
        onCreate(db);
    }

    public void updataNewMessage(String fromID,String toID,String message,int messageType,String data )
    {
        SQLiteDatabase db = getReadableDatabase();
        //boolean value=true;
        String update="UPDATE "+Chat_Table_Info.UserTable.TABLENAME +" SET "+
                Chat_Table_Info.UserTable.MESSAGE+" = '"+message+"', "+
                Chat_Table_Info.UserTable.MESSAGE_TYPE+" = "+messageType+", "+
                Chat_Table_Info.UserTable.DATA+"= '"+data+"' "+
                "WHERE "+Chat_Table_Info.UserTable.FROM_EMAIL+" = '"+fromID+"'"
                +" AND "+
                Chat_Table_Info.UserTable.TO_EMAIL+" = '"+toID+"'";

        db.execSQL(update);
    }

    public boolean isUserExist(String fromID,String toID) {
        //if there is same user(email=id), return true;
        // List<ChatMessage> list = new LinkedList<ChatMessage>();

        List<String> list=new ArrayList<String>();

        SQLiteDatabase db = null;
        db = getReadableDatabase();

        String query ="SELECT "+Chat_Table_Info.UserTable.FROM_EMAIL
                + " FROM "+Chat_Table_Info.UserTable.TABLENAME+" WHERE "+
                Chat_Table_Info.UserTable.FROM_EMAIL+" = '"+fromID+"'"+" AND "+
                Chat_Table_Info.UserTable.TO_EMAIL+" = '"+toID+"'";

        Cursor  cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            do {

                list.add(cursor.getString(cursor.getColumnIndex(Chat_Table_Info.UserTable.FROM_EMAIL)));

            }while (cursor.moveToNext());
        }

//        Cursor cursor = null;
//
//        try {
//            db = getReadableDatabase();
//            //(Chat_Table_Info.SingleChatTable.FROMEmail = toID )
//
//            String selection="( " + Chat_Table_Info.SingleChatTable.TO_EMAIL + "=\""
//                    + toID  + "\")";
////            String selection="select Chat_Table_Info.SingleChatTable.FROM_EMAIL " +
////                    "from Single_Chat_Table " +
////                    "where select Chat_Table_Info.SingleChatTable.FROM_EMAIL =" +
////                    "'" + fromID + "'" ;
//            cursor =db.query(true,
//                    Chat_Table_Info.SingleChatTable.TABLENAME,//table name
//                    null,
//                    selection,
//                    null,
//                    null,
//                    null,
//                    Chat_Table_Info.SingleChatTable.DATA,//order
//                    null);//number of item
//            if (cursor.moveToFirst()) {
//                do {
//                    list.add(cursor.getInt(cursor.getColumnIndex(Chat_Table_Info.SingleChatTable.IS_READ)));
//
//                }while (cursor.moveToNext());
//            }
//
//        }
//        catch (Exception e) {
//            System.out.println("chat Test fail");
//        }
//        finally {
//            if(cursor!=null)
//            {
//                try {
//                    cursor.close();
//                    cursor = null;
//                }
//                catch (Exception e) {
//                }
//            }
//            if (db != null) {
//                try {
//                    db.close();
//                    db = null;
//                }
//                catch (Exception e) {
//                }
//            }
//
//        }
//
        if(list.size()==0)
            return false;
        return true;
        //return list;

    }



    public List<ProviderUserDate> getUserData(String fromID,String toID) {
        List<ProviderUserDate> list = new LinkedList<ProviderUserDate>();

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = getReadableDatabase();

//            String selection="( " + Chat_Table_Info.SingleChatTable.FROM_EMAIL + "=\""
//                    + fromID + "\" and "
//                    + Chat_Table_Info.SingleChatTable.TO_EMAIL + "=\""
//                    + toID + "\")";

            String selection="( " + Chat_Table_Info.SingleChatTable.FROM_EMAIL + "=\""
                    + fromID + "\" and "
                    + Chat_Table_Info.SingleChatTable.TO_EMAIL + "=\""
                    + toID + "\") or ("
                    + Chat_Table_Info.SingleChatTable.TO_EMAIL + "=\""
                    + fromID + "\" and "
                    + Chat_Table_Info.SingleChatTable.FROM_EMAIL + "=\""
                    + fromID + "\")";

            cursor =db.query(true,
                    Chat_Table_Info.UserTable.TABLENAME,//table name
                    null,
                    selection,
                    null,
                    null,
                    null,
                    Chat_Table_Info.UserTable.DATA,//order
                    null);//number of item
            if (cursor.moveToFirst()) {
                do {
                    ProviderUserDate pud = new ProviderUserDate();
                    pud.set_toEmail(cursor.getString(cursor.getColumnIndex(Chat_Table_Info.UserTable.TO_EMAIL)));
                    pud.set_fromEmail(cursor.getString(cursor.getColumnIndex(Chat_Table_Info.UserTable.FROM_EMAIL)));
                    pud.set_firstName(cursor.getString(cursor.getColumnIndex(Chat_Table_Info.UserTable.FIRStNAME)));
                    pud.set_lastName(cursor.getString(cursor.getColumnIndex(Chat_Table_Info.UserTable.LASTNAME)));
                    pud.set_profileUrl(cursor.getString(cursor.getColumnIndex(Chat_Table_Info.UserTable.PORTRAIT_URL)));
                    pud.set_message(cursor.getString(cursor.getColumnIndex(Chat_Table_Info.UserTable.MESSAGE)));
                    pud.set_messageType(cursor.getInt(cursor.getColumnIndex(Chat_Table_Info.UserTable.MESSAGE_TYPE)));
                    pud.set_date(cursor.getString(cursor.getColumnIndex(Chat_Table_Info.UserTable.DATA)));
                    list.add(pud);
                }while (cursor.moveToNext());
            }
            System.out.println("chat Test number of single chat: "+list.size());

            }
            catch (Exception e) {
                System.out.println("chat Test fail");
            }
            finally {
                if(cursor!=null)
                {
                    try {
                        cursor.close();
                        cursor = null;
                    }
                    catch (Exception e) {
                    }
                }
                if (db != null) {
                    try {
                        db.close();
                        db = null;
                    }
                    catch (Exception e) {
                    }
                }

            }

                return list;

    }

   // private ContentValues getContentValuesByChatItem(SingleChatItem item,boolean isRead,boolean side){


//    public boolean insertSingleChat(SingleChatItem item, boolean isRead,boolean side){
    public void insertUserData(ProviderUserDate item) {
        SQLiteDatabase db=getWritableDatabase();
        String sql = "insert into "+ Chat_Table_Info.UserTable.TABLENAME +
                    "("+ Chat_Table_Info.UserTable.FROM_EMAIL + "," +
                    Chat_Table_Info.UserTable.TO_EMAIL + "," +
                    Chat_Table_Info.UserTable.FIRStNAME + "," +
                    Chat_Table_Info.UserTable.LASTNAME + "," +
                    Chat_Table_Info.UserTable.PORTRAIT_URL + "," +
                    Chat_Table_Info.UserTable.MESSAGE + "," +
                    Chat_Table_Info.UserTable.MESSAGE_TYPE + "," +
                    Chat_Table_Info.UserTable.DATA + ") " +
                    "values" +
                    "('" +item.get_fromEmail() + "'," +
                    "'" + item.get_toEmail()+"',"+
                    "'" + item.get_firstName()+"',"+
                    "'" + item.get_lastName()+"',"+
                    "'" + item.get_profileUrl()+"',"+
                    "'" + item.get_message()+"',"+
                    "'" + item.get_messageType() +"',"+
                    "'" + item.get_date()+"')";

        db.execSQL(sql);

    }

    public void deleteAllChat()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Chat_Table_Info.UserTable.TABLENAME,null,null);
        db.close();
    }







}
