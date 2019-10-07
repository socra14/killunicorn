package com.socra.socra.killunicorn.clases;

public class usuarios {
    private String nick;
    private int patos;

    public usuarios() {
    }

    public usuarios(String nick, int patos) {
        this.nick = nick;
        this.patos = patos;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getPatos() {
        return patos;
    }

    public void setPatos(int patos) {
        this.patos = patos;
    }
}
