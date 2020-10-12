package com.newnergy.para_client.Chat;

/**
 * Created by G7 on 16/09/2016.
 */
public class Chat_Table_Info {
    public class SingleChatTable{
        //table name
        public static final String TABLENAME= "Single_Chat_Table";
        //from email
        public static final String FROM_EMAIL ="from_email";
        //to email
        public static final String TO_EMAIL ="to_email";
        //message
        public static final String MESSAGE= "message";
        //date
        public static final String DATA="date";
        //type
        public static final String MESSAGE_TYPE="message_type";
        //side
        public static final String SIDE="side";
        //is read
        public static final String IS_READ = "is_read";


        //create
        public static final String CREATETABLESQL = "create table "+ TABLENAME +"("
                //+ FROMEMAIL + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + FROM_EMAIL + " TEXT,"
                + TO_EMAIL + " TEXT,"
                + MESSAGE +" TEXT,"
                + DATA +" TEXT,"
                + MESSAGE_TYPE+" INTEGER,"
                + SIDE +" INTEGER,"
                + IS_READ +" INTEGER"
                +" )";
        //delete
        public static final String DELETETABLESQL = "DROP TABLE IF EXISTS "+ TABLENAME;

    }

    public class UserTable{
        //table name
        public static final String TABLENAME= "User_Table";
        //from message's email
        public static final String FROM_EMAIL ="from_email";
        //to email
        public static final String TO_EMAIL ="to_email";
        //first name
        public static final String FIRStNAME="first_names";
        //last name
        public static final String LASTNAME="last_names";
        //portrait url
        public static final String PORTRAIT_URL="portrait_url";
        //message
        public static final String MESSAGE= "message";
        //date
        public static final String DATA="date";
        //type
        public static final String MESSAGE_TYPE="message_type";



        //create
        public static final String CREATETABLESQL = "create table "+ TABLENAME +" ("
              //  + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + FROM_EMAIL + " TEXT,"
                + TO_EMAIL + " TEXT,"
                + FIRStNAME +" TEXT,"
                + LASTNAME +" TEXT,"
                + PORTRAIT_URL +" TEXT,"
                + MESSAGE +" TEXT,"
                + DATA +" TEXT,"
                + MESSAGE_TYPE+" INTEGER"
                +" )";
        //delete
        public static final String DELETETABLESQL = "DROP TABLE IF EXISTS "+ TABLENAME;

    }

    public class MessageTable{
        //table name
        public static final String TABLENAME= "Message_Table";
        //type
        public static final String MESSAGE_TYPE="message_type";
        //portrait url
        public static final String PORTRAIT_URL="portrait_url";
        //dlc
        //public static final String PORTRAIT_URL="portrait_url";


        //create
        public static final String CREATETABLESQL = "CREATE TABLE IF NOT EXISTS "+ TABLENAME +" ("
                //  + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MESSAGE_TYPE +" TEXT,"
                + PORTRAIT_URL +" TEXT"
//               // + TYPE +" TEXT,"
//               // + IS_READ +" INTEGER"
                +" )";
        //delete
        public static final String DELETETABLESQL = "DROP TABLE IF EXISTS "+ TABLENAME;

    }

}
