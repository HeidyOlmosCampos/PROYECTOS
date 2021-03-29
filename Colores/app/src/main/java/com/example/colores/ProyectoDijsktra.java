package com.example.colores;


import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Personal
 */
public class ProyectoDijsktra {

    protected List<String> datos;
    protected GrafoPesado grafo;
    protected Dijsktra dijsktra;

    public ProyectoDijsktra(){
        this.grafo = new GrafoPesado();
        this.datos = new ArrayList();
    }

    public void insertarDato(String valor){
        grafo.insertarDato();
        String dato = valor.toUpperCase();
        dato = dato.trim();
        datos.add(dato);
    }

    public void insertarArista(String valorI, String valorF, double peso) throws ExcepcionAristaYaExiste{
        int verticeI = getVertice(valorI);
        int verticeF = getVertice(valorF);
        grafo.insertarArista(verticeI, verticeF, peso);
    }

    private int getVertice(String valor){
        String dato = valor.toUpperCase();
        dato = dato.trim();
        int vertice = datos.indexOf(dato);
        if(vertice == -1)
            throw new IllegalArgumentException("El lugar no existe");
        return vertice;
    }

    public String getDato(int vertice){
        return datos.get(vertice);
    }


    public GrafoPesado getGrafo(){
        return this.grafo;
    }


    public void dijsktra(String valorP, String valorD){
        if(grafo.cantidadDeVertices() > 0){
            int posPartida = getVertice(valorP);
            int posDestino = getVertice(valorD);
            dijsktra = new Dijsktra(grafo, posPartida, posDestino);
        }
    }

    public double costoMinimo(){
        return dijsktra.costoMinimo();
    }

    public Iterable<String> caminoDeCostoMinimo(){
        List<String> caminoString = new ArrayList<>();
        Iterable<Integer> camino = dijsktra.caminoDeCostoMinimo();
        for(Integer vertice : camino){
            caminoString.add(getDato(vertice));
        }
        return caminoString;
    }

}
