package com.thoughtworks.androidtrain.data.model;


public class Sender {
    private String username;
    private String nick;
    private String avatar;

    public Sender() {
    }

    public Sender(String username, String nick, String avatar) {
        this.username = username;
        this.nick = nick;
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public Sender setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getNick() {
        return nick;
    }

    public Sender setNick(String nick) {
        this.nick = nick;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public Sender setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }
}
