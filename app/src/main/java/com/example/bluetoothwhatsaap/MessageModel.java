package com.example.bluetoothwhatsaap;

public class MessageModel {
    String message;
    Long timestamp;
    int uId;
    String time;

    public MessageModel() {

    }
    public MessageModel(Long timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;

    }
    public MessageModel(Long timestamp, String message,int uId) {
        this.timestamp = timestamp;
        this.message = message;
        this.uId = uId;

    }

    public void setTime(String time) {
        time = time.replaceAll("-"," ");
        String arr[] = time.split(" ");

        if(arr.length==5) {
            this.time = arr[3].substring(0,5) + " " + arr[4];
        }else if(arr.length==4){
            this.time = arr[3].substring(0,5);
        }else if(arr.length==3){
            this.time = arr[2].substring(0,5);
        }else if(arr.length==2){
            this.time = arr[1].substring(0,5);
        }
    }

    public String getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public int getuId() {
        return uId;
    }

    public Long getTimestamp() {
        return timestamp;
    }



    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setuId(int uId){
        this.uId= uId;
    }
}
