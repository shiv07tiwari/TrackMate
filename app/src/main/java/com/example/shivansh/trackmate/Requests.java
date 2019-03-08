package com.example.shivansh.trackmate;

public class Requests {
    private String send, reieve, professor, student, roll, status,regarding, sendDate, recieveDate, sendTime,recieveTime;

    public String getRegarding() {
        return regarding;
    }

    public void setRegarding(String regarding) {
        this.regarding = regarding;
    }

    public Requests(String send, String reieve, String professor, String student, String roll, String status, String sendDate, String sendTime, String recieveDate, String recieveTime, String regarding) {
        this.send = send;
        this.regarding = regarding;
        this.sendDate=sendDate;
        this.recieveDate=recieveDate;
        this.sendTime=sendTime;
        this.recieveTime=recieveTime;
        this.reieve = reieve;
        this.professor = professor;
        this.student = student;
        this.roll = roll;
        this.status = status;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public String getReieve() {
        return reieve;
    }

    public void setReieve(String reieve) {
        this.reieve = reieve;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getRecieveDate() {
        return recieveDate;
    }

    public void setRecieveDate(String recieveDate) {
        this.recieveDate = recieveDate;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getRecieveTime() {
        return recieveTime;
    }

    public void setRecieveTime(String recieveTime) {
        this.recieveTime = recieveTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
