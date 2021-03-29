package com.example.colores;

import java.util.ArrayList;
import java.util.List;

public class Costos {
    protected List<Double> costos;
    protected int nroVertices;

    public Costos(int nroVertices, int vInicial){
        this.nroVertices = nroVertices;
        costos = new ArrayList<>();
        for (int i = 0; i < this.nroVertices; i++){
            costos.add(Double.MAX_VALUE);
        }
        costos.set(vInicial, 0.0);
    }

    public void setCosto(int posVertice, double costo){
        costos.set(posVertice, costo);
    }


    public double getCosto(int posVertice){
        return costos.get(posVertice);
    }

}