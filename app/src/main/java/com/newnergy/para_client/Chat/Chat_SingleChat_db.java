package com.newnergy.para_client.Chat;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by G7 on 15/09/2016.
 */
public class Chat_SingleChat_db extends SQLiteOpenHelper {
    private static final String TAG = "Chat_SingleChat_db";
    public static final String DB_NAME = "Chat_History.db";
    public static final int DB_VERSION = 1;
    public Context mContext;

    public Chat_SingleChat_db(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
    }

    public Chat_SingleChat_db(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    //create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Chat_Table_Info.SingleChatTable.CREATETABLESQL);
        //db.execSQL(Chat_Table_Info.UserTable.CREATETABLESQL);
       // db.execSQL(Chat_Table_Info.MessageTable.CREATETABLESQL);
        //System.out.println("chat test create successful");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Chat_Table_Info.SingleChatTable.DELETETABLESQL);
        //db.execSQL(Chat_Table_Info.UserTable.DELETETABLESQL);
        //db.execSQL(Chat_Table_Info.MessageTable.DELETETABLESQL);
        onCreate(db);
    }


    public List<String> getProviderEmail(String myEmail)
    {
        List<String> list=new ArrayList<String>();

        SQLiteDatabase db = null;
        db = getReadableDatabase();

        String query ="SELECT "+Chat_Table_Info.SingleChatTable.FROM_EMAIL
                + " FROM "+Chat_Table_Info.SingleChatTable.TABLENAME+" WHERE "+
                Chat_Table_Info.SingleChatTable.TO_EMAIL+" = '"+myEmail+"'";

        Cursor  cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            do {
                //System.out.println("iiiiii"+cursor.getString(cursor.getColumnIndex("is_read")));
                list.add(cursor.getString(cursor.getColumnIndex(Chat_Table_Info.SingleChatTable.FROM_EMAIL)));

            }while (cursor.moveToNext());
        }


        return removeDuplicate(list);
    }

    public List<String> getSelfEmail(String myEmail)
    {
        List<String> list=new ArrayList<String>();

        SQLiteDatabase db = null;
        db = getReadableDatabase();

        String query ="SELECT "+Chat_Table_Info.SingleChatTable.TO_EMAIL
                + " FROM "+Chat_Table_Info.SingleChatTable.TABLENAME+" WHERE "+
                Chat_Table_Info.SingleChatTable.FROM_EMAIL+" = '"+myEmail+"'";

//        String query ="SELECT "+Chat_Table_Info.SingleChatTable.FROM_EMAIL+" = '"+myEmail+"'"
//                + " FROM "+Chat_Table_Info.SingleChatTable.TABLENAME+" WHERE "+
//                Chat_Table_Info.SingleChatTable.TO_EMAIL;

        Cursor  cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndex(Chat_Table_Info.SingleChatTable.TO_EMAIL)));

            }while (cursor.moveToNext());
        }


        return removeDuplicate(list);
    }


    private static List<String> removeDuplicate(List<String> list)
    {
        Set set = new LinkedHashSet<String>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }

    public void setAlreadyRead(String toID)
    {
        SQLiteDatabase db = getReadableDatabase();
        //boolean value=true;
        String update="UPDATE "+Chat_Table_Info.SingleChatTable.TABLENAME +" SET "+
                Chat_Table_Info.SingleChatTable.IS_READ+" = 'true'";// WHERE "

//                +
//                Chat_Table_Info.SingleChatTable.TO_EMAIL+" = '"+toID+"'";
        db.execSQL(update);
    }


    public boolean isRead(String toID) {
       // List<ChatMessage> list = new LinkedList<ChatMessage>();

        List<String> list=new ArrayList<String>();

        SQLiteDatabase db = null;
        db = getReadableDatabase();

        String query ="SELECT "+Chat_Table_Info.SingleChatTable.IS_READ
                + " FROM "+Chat_Table_Info.SingleChatTable.TABLENAME+" WHERE "+
                Chat_Table_Info.SingleChatTable.TO_EMAIL+" = '"+toID+"'";

        Cursor  cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()) {
                do {
                    //System.out.println("iiiiii"+cursor.getString(cursor.getColumnIndex("is_read")));
                    list.add(cursor.getString(cursor.getColumnIndex(Chat_Table_Info.SingleChatTable.IS_READ)));

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
        for(int i=0;i<list.size();i++)
        {
            if (list.get(i).equals("false"))
                return false;

        }
        return true;
        //return list;

    }


    public List<ChatMessage> obtainSingleChatList(String fromID, String toID) {
        List<ChatMessage> list = new LinkedList<ChatMessage>();

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = getReadableDatabase();
            //(Chat_Table_Info.SingleChatTable.FROMEmail = fromid and TableInfo.SingleChatTable.TOEmail = toid)
            //            or
            // (Chat_Table_Info.SingleChatTable.TOEmail = fromid and TableInfo.SingleChatTable.FROMEmail +toid)
            String selection="( " + Chat_Table_Info.SingleChatTable.FROM_EMAIL + "=\""
                    + fromID + "\" and "
                    + Chat_Table_Info.SingleChatTable.TO_EMAIL + "=\""
                    + toID + "\") or ("
                    + Chat_Table_Info.SingleChatTable.TO_EMAIL + "=\""
                    + fromID + "\" and "
                    + Chat_Table_Info.SingleChatTable.FROM_EMAIL + "=\""
                    + fromID + "\")";
//            String selection="select Chat_Table_Info.SingleChatTable.FROM_EMAIL " +
//                    "from Single_Chat_Table " +
//                    "where select Chat_Table_Info.SingleChatTable.FROM_EMAIL =" +
//                    "'" + fromID + "'" ;
            cursor =db.query(true,
                    Chat_Table_Info.SingleChatTable.TABLENAME,//table name
                    null,
                    selection,
                    null,
                    null,
                    null,
                    Chat_Table_Info.SingleChatTable.DATA,//order
                    null);//number of item
            if (cursor.moveToFirst()) {
                do {
                    ChatMessage msg = new ChatMessage();
                    msg.set_from_email(cursor.getString(cursor.getColumnIndex(Chat_Table_Info.SingleChatTable.FROM_EMAIL)));
                    msg.set_to_email(cursor.getString(cursor.getColumnIndex(Chat_Table_Info.SingleChatTable.TO_EMAIL)));
                    msg.set_message(cursor.getString(cursor.getColumnIndex(Chat_Table_Info.SingleChatTable.MESSAGE)));
                    msg.set_Message_type(cursor.getInt(cursor.getColumnIndex(Chat_Table_Info.SingleChatTable.MESSAGE_TYPE)));
                    msg.set_data(cursor.getString(cursor.getColumnIndex(Chat_Table_Info.SingleChatTable.DATA)));
                    msg.set_side(cursor.getInt(cursor.getColumnIndex(Chat_Table_Info.SingleChatTable.SIDE))); //set_Side(cursor.getString(cursor.getColumnIndex(Chat_Table_Info.SingleChatTable.SIDE)));
                    //System.out.println("ddddddddddddd: "+cursor.getInt(cursor.getColumnIndex(Chat_Table_Info.SingleChatTable.IS_READ)));
                    msg.set_isRead(cursor.getInt(cursor.getColumnIndex(Chat_Table_Info.SingleChatTable.IS_READ)));
                    list.add(msg);
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

        //
//        if(fromID.equals(cursor.getString(cursor.getColumnIndex(Chat_Table_Info.SingleChatTable.FROM_EMAIL))) && toID.equals(cursor.getString(cursor.getColumnIndex(Chat_Table_Info.SingleChatTable.TO_EMAIL))) ){
//            return list;
//        }else{
//            return null;
//        }

        return list;
    }

   // private ContentValues getContentValuesByChatItem(SingleChatItem item,boolean isRead,boolean side){


//    public boolean insertSingleChat(SingleChatItem item, boolean isRead,boolean side){
    public void insertSingleChat(ChatMessage item) {
        SQLiteDatabase db=getWritableDatabase();
        String sql = "insert into "+ Chat_Table_Info.SingleChatTable.TABLENAME +
                    "("+ Chat_Table_Info.SingleChatTable.FROM_EMAIL + "," +
                    Chat_Table_Info.SingleChatTable.TO_EMAIL + "," +
                    Chat_Table_Info.SingleChatTable.MESSAGE + "," +
                    Chat_Table_Info.SingleChatTable.DATA + "," +
                    Chat_Table_Info.SingleChatTable.MESSAGE_TYPE + "," +
                    Chat_Table_Info.SingleChatTable.IS_READ + "," +
                    Chat_Table_Info.SingleChatTable.SIDE + ") " +
                    "values" +
                    "('" +item.get_from_email() + "'," +
                    "'" + item.get_to_email()+"',"+
                    "'" + item.get_message()+"',"+
                    "'" + item.get_data()+"',"+
                    "'" + item.get_Message_type()+"',"+
                    "'" + item.get_isRead() +"',"+
                    "'" + item.get_side()+"')";

        db.execSQL(sql);

    }

    public void deleteAllChat()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Chat_Table_Info.SingleChatTable.TABLENAME,null,null);
        db.close();
    }







}
