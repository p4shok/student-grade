package com.student_grade.util.messages;

public class LoggerMessage {
    private String _status;
    private String _message;

    public LoggerMessage(String status, String message){
        this._message = message;
        this._status=status;
    }
    public LoggerMessage(){
    }

    @Override
    public String toString() {
        return _status + ":" + _message;
    }

    public LoggerMessage message(String message){
        _message=message;
        return this;
    }

    public LoggerMessage status(String status){
        _status=status;
        return this;
    }
}
