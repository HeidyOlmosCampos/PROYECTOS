package com.example.colores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.EditText;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Adaptador adaptador;
    ProyectoDijsktra grafoPesado;
    List<Item> listaDePueblos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //RECYCLER VIEW
        recyclerView =findViewById(R.id.recycler1);
        listaDePueblos=new ArrayList<>();
        adaptador=new Adaptador(listaDePueblos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptador);
        //INICIALIZAR EL GRAFOPESADO
         grafoPesado = new ProyectoDijsktra();
         grafoPesado.insertarDato("vallegrande");
         grafoPesado.insertarDato("postrervalle");
         grafoPesado.insertarDato("trigal");
         grafoPesado.insertarDato("piraimiri");
         grafoPesado.insertarDato("mataral");
         grafoPesado.insertarDato("pampagrande");
         grafoPesado.insertarDato("los negros");
         grafoPesado.insertarDato("tembladera");
         grafoPesado.insertarDato("mairana");
         grafoPesado.insertarDato("moro moro");
         grafoPesado.insertarDato("hierba buena");
         grafoPesado.insertarDato("algodonal");
         grafoPesado.insertarDato("san juan");
         grafoPesado.insertarDato("santa rosa");
         grafoPesado.insertarDato("becerro");
         grafoPesado.insertarDato("el pacay");

        //ARISTAS
        try {
            grafoPesado.insertarArista("vallegrande","trigal", 17.5);
            grafoPesado.insertarArista("trigal","mataral",32.5);
            grafoPesado.insertarArista("mataral","san juan", 20.8);
            grafoPesado.insertarArista("pampagrande","los negros", 4.1);
            grafoPesado.insertarArista("san juan","becerro", 25.8);
            grafoPesado.insertarArista("becerro","el pacay", 12.7);
            grafoPesado.insertarArista("el pacay","los negros", 1.8);
            grafoPesado.insertarArista("el pacay","algodonal", 3.7);
            grafoPesado.insertarArista("algodonal","santa rosa", 32.0);
            grafoPesado.insertarArista("algodonal","hierba buena", 37.1);
            grafoPesado.insertarArista("hierba buena","mairana", 16.9);
            grafoPesado.insertarArista("hierba buena","los negros", 14.5);
            grafoPesado.insertarArista("mairana","tembladera", 22.2);
            grafoPesado.insertarArista("pampagrande","tembladera", 9.3);
            grafoPesado.insertarArista("tembladera","postrervalle", 23.8);
            grafoPesado.insertarArista("postrervalle","piraimiri", 35.4);
            grafoPesado.insertarArista("vallegrande","piraimiri", 34.7);
        } catch (ExcepcionAristaYaExiste excepcionAristaYaExiste) {
            excepcionAristaYaExiste.printStackTrace();
        }
    }

    public void dijsktraBuscar() {
        EditText origen = (EditText) findViewById(R.id.campo_texto);
        EditText destino = (EditText) findViewById(R.id.campo_texto2);
        String texto1 = origen.getText().toString();
        String texto2 = destino.getText().toString();
        grafoPesado.dijsktra(texto1,texto2);
    }

    public void acceder (View view) {
        recyclerView.setVisibility(View.VISIBLE);
        listaDePueblos.clear();
        dijsktraBuscar();
        double costo = grafoPesado.costoMinimo();
        Iterable<String> caminos = grafoPesado.caminoDeCostoMinimo();
        for(String vertice : caminos){
            listaDePueblos.add(new Item("#0009ff",vertice));
        }

        TextView costito = (TextView) findViewById(R.id.textView);
        costito.setText(Double.toString(costo));

        adaptador.notifyDataSetChanged();

    }

}