package com.example.colores;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Personal
 */
public class GrafoPesado {


    protected List<List<AdyacenteConPeso>> listaDeAdyacencias;


    public GrafoPesado(){
        this.listaDeAdyacencias = new ArrayList<>();
    }


    public GrafoPesado(int nroDeVerticesInicial) throws ExcepcionNroVerticesInvalido{
        if(nroDeVerticesInicial < 0){
            throw new ExcepcionNroVerticesInvalido();
        }
        this.listaDeAdyacencias = new ArrayList<>();
        for (int i = 0; i < nroDeVerticesInicial; i++){
            this.listaDeAdyacencias.add(new ArrayList<AdyacenteConPeso>());
        }
    }


    public void insertarDato() {
        this.listaDeAdyacencias.add(new ArrayList<AdyacenteConPeso>());
    }


    public int cantidadDeAristas() {
        int cantAristas = 0;
        int cantLazos = 0;
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++){
            List<AdyacenteConPeso> adyacentesDeUnVertice = this.listaDeAdyacencias.get(i);
            for (AdyacenteConPeso  adyacenteConPeso : adyacentesDeUnVertice){
                int posAdyacente = adyacenteConPeso.getIndiceVertice();
                if(i == posAdyacente){
                    cantLazos++;
                }else{
                    cantAristas++;
                }
            }
        }
        cantAristas = (cantAristas/2) + cantLazos;
        return cantAristas;
    }


    public int cantidadDeVertices(){
        return listaDeAdyacencias.size();
    }

    public  void validarVertice(int posicionDeVertice){
        if (posicionDeVertice < 0 || posicionDeVertice >= cantidadDeVertices()){
            throw new IllegalArgumentException("El vértice " + posicionDeVertice + " no pertenece al grafo");
        }
    }

    public void insertarArista(int posVerticeOrigen, int posVerticeDestino, double peso) throws ExcepcionAristaYaExiste{
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);
        if(this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)){
            throw new ExcepcionAristaYaExiste();
        }

        List<AdyacenteConPeso> adyacenciasDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        AdyacenteConPeso nuevaAdyacenciaOrigen = new AdyacenteConPeso(posVerticeDestino, peso);
        adyacenciasDelOrigen.add(nuevaAdyacenciaOrigen);
        if (posVerticeOrigen != posVerticeDestino){
            List<AdyacenteConPeso> nuevaAdyacenciaDestino = this.listaDeAdyacencias.get(posVerticeDestino);
            AdyacenteConPeso nuevaAristaDestino = new AdyacenteConPeso(posVerticeOrigen, peso);
            nuevaAdyacenciaDestino.add(nuevaAristaDestino);
        }
    }


    public boolean existeAdyacencia(int posVerticeOrigen, int posVerticeDestino){
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);
        List<AdyacenteConPeso> adyacenciasDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        AdyacenteConPeso destino = new AdyacenteConPeso(posVerticeDestino);
        return adyacenciasDelOrigen.contains(destino);
    }

    public void eliminarVertice(int posVerticeAEliminar){
        validarVertice(posVerticeAEliminar);
        this.listaDeAdyacencias.remove(posVerticeAEliminar);

        for (List<AdyacenteConPeso> adyacentesDeUnVertice : this.listaDeAdyacencias){
            AdyacenteConPeso adyacenteConPeso = new AdyacenteConPeso(posVerticeAEliminar);
            int posicionDeVerticeEnAdy = adyacentesDeUnVertice.indexOf(adyacenteConPeso);
            if (posicionDeVerticeEnAdy >= 0){
                adyacentesDeUnVertice.remove(posicionDeVerticeEnAdy);
            }

            for (int i = 0; i < adyacentesDeUnVertice.size(); i++){
                AdyacenteConPeso posicionAdyacente = adyacentesDeUnVertice.get(i);
                if ( posicionAdyacente.getIndiceVertice() > posVerticeAEliminar){
                    posicionAdyacente.setIndiceVertice(posicionAdyacente.getIndiceVertice()-1);
                }
            }
        }
    }


    public int gradoDeVertice(int posDeVertice){
        validarVertice(posDeVertice);
        List<AdyacenteConPeso> adyacenciasDelVertice = this.listaDeAdyacencias.get(posDeVertice);
        return adyacenciasDelVertice.size();
    }


    public Iterable<Integer> adyacentesDeVertice(int posDeVertice){
        validarVertice(posDeVertice);
        List<AdyacenteConPeso> adyacenciasDelVertice = this.listaDeAdyacencias.get(posDeVertice);
        List<Integer> adyacentesDelVertice = new ArrayList<>();
        for(AdyacenteConPeso posAdyacente : adyacenciasDelVertice){
            adyacentesDelVertice.add(posAdyacente.getIndiceVertice());
        }
        Iterable<Integer> it = adyacentesDelVertice;
        return it;
    }





    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino){
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);
        if(!existeAdyacencia(posVerticeOrigen, posVerticeDestino)){
            throw new IllegalArgumentException("La arista no existe");
        }

        List<AdyacenteConPeso> adyacenciasDelOrigen = listaDeAdyacencias.get(posVerticeOrigen);
        AdyacenteConPeso posDestino = new AdyacenteConPeso(posVerticeDestino);
        int p = adyacenciasDelOrigen.indexOf(posDestino);
        adyacenciasDelOrigen.remove(p);
        if(posVerticeOrigen != posVerticeDestino){
            List<AdyacenteConPeso> adyacenciasDelDestino = listaDeAdyacencias.get(posVerticeDestino);
            AdyacenteConPeso posOrigen = new AdyacenteConPeso(posVerticeOrigen);
            p = adyacenciasDelDestino.indexOf(posOrigen);
            adyacenciasDelDestino.remove(p);
        }
    }


    public List<AdyacenteConPeso> getAdyacentesDeVertice(int posVertice){
        validarVertice(posVertice);
        return listaDeAdyacencias.get(posVertice);
    }

    public void mostrar(){
        if(cantidadDeVertices()>0){
            int i = 0;
            System.out.println("Lista de adyacencias: ");
            for (List<AdyacenteConPeso> adyacencias : this.listaDeAdyacencias){
                System.out.println(i + ": " + adyacencias);
                i++;
            }
        }else{
            System.out.println("El grafo no tiene ningún vértice");
        }
    }

}


