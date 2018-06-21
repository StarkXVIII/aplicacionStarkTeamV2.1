package cl.ubiobio.nicolas.ssbiobiobeta;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nicolas on 18/06/2018.
 */
/*Clase llamada Adaptador la cual sirve para formatear los datos del array llamado "listaFarmacias", que contiene
        los objetos tipo "Farmacia" extraidos de la web a traves del JSON, donde los lista de manera organizada en el ListView designado.*/
public class Adaptador extends BaseAdapter {

    /*Array de tipo farmacia para el Adaptador y su respectivo context*/
    private ArrayList<Farmacia> listaFarmacias;
    private Context context;
    private double latitud;
    private double longitud;



    /*Constructor del Adaptador y variables que recibirá*/
    public Adaptador(ArrayList<Farmacia> listaFarmacias, Context context) {
        this.listaFarmacias = listaFarmacias;
        this.context = context;
    }
    /*Función que obtiene la cantidad de objetos en el array*/
    @Override
    public int getCount() {
        return listaFarmacias.size();
    }

    /*Obtiene la posicion de cierto objeto del array*/
    @Override
    public Object getItem(int position) {
        return listaFarmacias.get(position);
    }

    /*Obtiene el item en la posicion i del arreglo*/
    @Override
    public long getItemId(int i) {
        return 0;
    }

    /*Funcion que infla el listview para su respectivo rellenado y lo devuelve adaptado*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(
                    R.layout.adaptador,
                    parent,
                    false);
        }

        /*Objeto farmacia del cual se extraeran los atributos requeridos en los TextView*/
        Farmacia farmacia = (Farmacia) getItem(position);

        /*Seteo de los datos requeridos hacia los TextView correspoondientes*/
        TextView local =(TextView) convertView.findViewById(R.id.localnombre);
        TextView localidad =(TextView) convertView.findViewById(R.id.localidad);
        TextView direccion =(TextView) convertView.findViewById(R.id.direccion);
        TextView cierre =(TextView) convertView.findViewById(R.id.cierre);

        /*Obtención de los datos en los textview declarados mediante los metodos de la clase Farmacia*/
        local.setText(farmacia.getNombre_farmacia());
        localidad.setText(farmacia.getNombre_comuna());
        direccion.setText(farmacia.getDireccion_farmacia());
        cierre.setText("Cierre: "+farmacia.getHorario_cierre());

        /*Se guarda la latitud y longitud de las farmacias extraidas para lanzarlas como marcador en la app de
         * navegación preferida por el usuario ej:waze, maps,etc, para facilitar la navegación en caso de no saber llegar */
        latitud=farmacia.getLatitud();
        longitud=farmacia.getLongitud();
        CardView btn = (CardView) convertView.findViewById(R.id.ir);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse("geo:"+latitud+","+longitud+"?z=16&q="+latitud+","+longitud);
                context.startActivity( new Intent(Intent.ACTION_VIEW, uri));
            }
        });

        return convertView;
    }

    /*Reconoce las secuencias char y los retorna de manera individual en caso de su uso*/
    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
