package com.example.colores;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Personal
 */
public class Predecesores {
    protected List<Integer> predecesores;
    protected int nroVertices;

    public Predecesores(int nroVertices){
        this.nroVertices = nroVertices;
        predecesores = new ArrayList<>();
        for (int i = 0; i < this.nroVertices; i++){
            predecesores.add(-1);
        }
    }

    public void setPredecesor(int posVertice, int predecesor){
        predecesores.set(posVertice, predecesor);
    }


    public int getPredecesor(int posVertice){
        return predecesores.get(posVertice);
    }

}

