package cl.ubiobio.nicolas.ssbiobiobeta;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/*Clase correspondiente al botón siete de "Salud responde", donde se administra el contacto y/o la llamada
hacia el servicio Salud Responde*/
public class BotonSaludResponde extends AppCompatActivity {

    @Override
    /*Función on create donde se integran las funciones que ejecutará el botónSiete*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boton_salud_responde);

    /*Se crea un botón llamado btn el cual se activa mediante un setOnClickListener que redirecciona a la clase VisorLlamar */
        CardView btn = (CardView) findViewById(R.id.llamenme);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), VisorLlamar.class);
                startActivityForResult(intent, 0);
            }
        });

    /*Se crea una imagen tipo botón la cual se activa mediante un setOnClickListener
     que enlaza la app con el marcador del telefono y escribe el numero de contacto para llamar*/
        ImageButton btn2 = (ImageButton) findViewById(R.id.imageButton);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:6003607777"));
                startActivity(intent);
            }
        });
    }
}
