package cl.ubiobio.nicolas.ssbiobiobeta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*Clase correspondiente al botón siete de "Cartera de servicios", donde se muestra la información de todas
* las carteras de servicios disponibles en el SSBIOBIO*/
public class BotonCarteraServicios extends AppCompatActivity {

    // atributo texto que se muestra al apretar el boton seis
    TextView textocartera;

    //se carga el contenido del boton seis
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boton_cartera_servicios);

        //declaracion para que muestre el contenido del textocartera
        textocartera = (TextView) findViewById(R.id.textocartera);


        //metodos para que cuando aprete cierto boton abra el contenido del boton apretado
        Button btn = (Button) findViewById(R.id.abierta);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AtencionAbierta.class);
                startActivityForResult(intent, 0);
            }
        });

        Button btn2 = (Button) findViewById(R.id.urgencia);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AtencionUrgencia.class);
                startActivityForResult(intent, 0);
            }
        });

        Button btn3 = (Button) findViewById(R.id.cerrada);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AtencionCerrada.class);
                startActivityForResult(intent, 0);
            }
        });

        Button btn4 = (Button) findViewById(R.id.apoyo);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ApoyoClinico.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}
