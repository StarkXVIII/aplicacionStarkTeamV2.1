package cl.ubiobio.nicolas.ssbiobiobeta;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/*Clase llamada AtencionUrgencia donde se mandan las instrucciones a un botón dinamico que muestre la información correspondiente
* a la cartera de Atención Urgencia del SSBIOBIO*/
public class AtencionUrgencia extends AppCompatActivity {

    // atributo texto que se muestra al apretar AtencionUrgencia
    TextView consulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atencion_urgencia);


        //declaracion para que muestre el contenido del textocartera
        consulta=(TextView) findViewById(R.id.consulta);
    }
}
