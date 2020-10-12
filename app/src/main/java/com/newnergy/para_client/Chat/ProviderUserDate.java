package com.newnergy.para_client.Chat;

/**
 * Created by G7 on 29/09/2016.
 */
public class ProviderUserDate {
    private String _fromEmail;
    private String _toEmail;
    private String _firstName;
    private String _lastName;
    private String _profileUrl;
    private String _message;
    private int _messageType;
    private String _date;

    public ProviderUserDate()
    {}

    public ProviderUserDate(String fromEmail,String toEmail,String firstName,String lastName,
                            String profileUrl,String message,int messageType,
                            String date)
    {
        this._fromEmail=fromEmail;
        this._toEmail=toEmail;
        this._firstName=firstName;
        this._lastName=lastName;
        this._profileUrl=profileUrl;
        this._message=message;
        this._messageType=messageType;
        this._date=date;
    }


    public void set_fromEmail(String _email) {
        this._fromEmail = _email;
    }
    public void set_toEmail(String _toEmail) {
        this._toEmail = _toEmail;
    }
    public void set_firstName(String _firstName) {
        this._firstName = _firstName;
    }
    public void set_lastName(String _lastName) {
        this._lastName = _lastName;
    }
    public void set_profileUrl(String _profileUrl) {
        this._profileUrl = _profileUrl;
    }
    public void set_message(String _message) {
        this._message = _message;
    }
    public void set_messageType(int _messageType) {
        this._messageType = _messageType;
    }
    public void set_date(String _date) {
        this._date = _date;
    }

    public String get_fromEmail() {
        return _fromEmail;
    }
    public String get_firstName() {
        return _firstName;
    }
    public String get_lastName() {
        return _lastName;
    }
    public String get_profileUrl() {
        return _profileUrl;
    }
    public String get_message() {
        return _message;
    }
    public int get_messageType() {
        return _messageType;
    }
    public String get_date() {
        return _date;
    }
    public String get_toEmail() {
        return _toEmail;
    }


}
