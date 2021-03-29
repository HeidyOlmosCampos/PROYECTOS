package com.example.colores;

public class Item {
    String color;
    String pueblo;
    String peso;

    public Item(String color, String pueblo) {
        this.color = color;
        this.pueblo = pueblo;
        this.peso = "";
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPueblo() {
        return pueblo;
    }

    public void setPueblo(String pueblo) {
        this.pueblo = pueblo;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }
}
