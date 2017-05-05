package com.epam.jmp.chat;

public class Response {
    private String log;
    private String users;

    public Response(String log, String users) {
        this.log = log;
        this.users = users;
    }

    public String getLog() {
        return log;
    }

    public String getUsers() {
        return users;
    }
}
