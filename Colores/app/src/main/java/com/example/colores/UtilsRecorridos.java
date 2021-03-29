package com.example.colores;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Personal
 */
public class UtilsRecorridos {
    private List<Boolean> marcados;
    private int nroVertices;

    public UtilsRecorridos(int nroVertices){
        this.nroVertices = nroVertices;
    }

    public void desmarcarTodos() {
        marcados = new ArrayList<>();
        for (int i = 0; i < this.nroVertices; i++){
            marcados.add(Boolean.FALSE);
        }
    }


    public void marcarVertice(int posVertice){
        marcados.set(posVertice, Boolean.TRUE);
    }


    public boolean estaMarcado(int posVertice){
        return marcados.get(posVertice);
    }


    public boolean estanTodosMarcados(){
        for(Boolean marcado : this.marcados){
            if(!marcado){
                return false;
            }
        }
        return true;
    }

    public Iterable<Integer> noMarcados(){
        List<Integer> lista = new ArrayList<>();
        int i = 0;
        for(Boolean marcado : this.marcados){
            if(!marcado)
                lista.add(i);
            i++;
        }
        Iterable<Integer> it = lista;
        return it;
    }

}
