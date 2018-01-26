package com.example.dm2.ficheros3;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lstFavoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Favorito> datos  = new ArrayList<>();
        try {
            InputStream fraw = getResources().openRawResource(R.raw.recurso);
            BufferedReader brin = new BufferedReader(new InputStreamReader(fraw));
            String linea = brin.readLine();
            String[] s = linea.split(";");
            datos.add(new Favorito(s[0],s[1],s[2],s[3]));
            while (linea != null) {
                linea = brin.readLine();
                s = linea.split(";");
                datos.add(new Favorito(s[0],s[1],s[2],s[3]));
            }
            fraw.close();
        } catch (Exception ex) {
            Log.e("Ficheros" , "Error al leer fichero desde recurso raw");
        }
        AdaptadorFavoritos adaptador =
                new AdaptadorFavoritos(this, datos);
        lstFavoritos = (ListView)findViewById(R.id.lstFavoritos);
        lstFavoritos.setAdapter(adaptador);

        //Eventos
        lstFavoritos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id)
            {

                String url =
                        ((Favorito)a.getItemAtPosition(position)).getUrl();
                //long opcion = a.getItemIdAtPosition(position);

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });



    }

    class AdaptadorFavoritos extends ArrayAdapter<Favorito> {

        public AdaptadorFavoritos(Context context, ArrayList<Favorito> dat) {
            super(context, R.layout.listitem_favoritos,dat);

        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_favoritos, null);

            TextView lblNombre = (TextView) item.findViewById(R.id.lblNombre);
            lblNombre.setText(super.getItem(position).getNombre());

            ImageView icon=(ImageView) item.findViewById(R.id.iconWeb);
            //Con ifs
            if(super.getItem(position).getIcono().equalsIgnoreCase("yahoo"))
                icon.setImageResource(R.drawable.yahoo);
            else if(super.getItem(position).getIcono().equalsIgnoreCase("google"))
                icon.setImageResource(R.drawable.google);
            else if(super.getItem(position).getIcono().equalsIgnoreCase("bing"))
                icon.setImageResource(R.drawable.bing);


            TextView lblUrl =
                    (TextView) item.findViewById(R.id.lblUrl);
            lblUrl.setText(getItem(position).getUrl());

            TextView lblDescripcion =
                    (TextView) item.findViewById(R.id.lblId);
            lblDescripcion.setText(getItem(position)/*dat[position]*/.getDescripcion());
            return (item);
        }

    }



    class Favorito {
        private String nombre;
        private String url;
        private String icono;
        private String descripcion;
        public Favorito(String n, String u,String i,String d){
            nombre=n;
            url=u;
            icono=i;
            descripcion=d;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIcono() {
            return icono;
        }

        public void setIcono(String icono) {
            this.icono = icono;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }
    }
}
