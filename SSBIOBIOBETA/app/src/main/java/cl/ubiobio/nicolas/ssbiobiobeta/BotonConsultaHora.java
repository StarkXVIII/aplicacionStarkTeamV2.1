package cl.ubiobio.nicolas.ssbiobiobeta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*Clase correspondiente al botón uno de "Consulta hora médica", donde se listan los datos de una hora médica
* de algun usuario del servicio de salud mediante su rut, los datos son extraidos mediante un xml y una conexión vpn*/
public class BotonConsultaHora extends AppCompatActivity implements View.OnClickListener {
    public static EditText rutParte1,rutParte2;
    public static String rutText,digitoVerificador;
 /*
        En esta funcion hacemos la asigancion del boton del layout a las variables,
        tambien se realiza la integracion del boton "consultar el cual sera un boton co onClick.
         */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boton_consulta_hora);

        CardView botonConsultar = findViewById(R.id.cardView);

        botonConsultar.setOnClickListener(this);
        rutParte1 = findViewById(R.id.rutParteUno);
        rutParte2 = findViewById(R.id.rutParteDos);
    }

    /*
   En esta funcion creamos un Intent el cual una vez ingresados los datos
   en los cuadros editables, se asignaran a las variables String para depues enviarse al
   servidor para realizar la consulta, se creo un intent para lanzar otra activity la cual
   mostrara en pantalla los datos recogidos de la consulta.
    */
    @Override
    public void onClick(View v) {
        rutText= rutParte1.getText().toString();
        digitoVerificador=rutParte2.getText().toString();
        if(!rutText.equals("")||!digitoVerificador.equals("")){
            if (analizar(digitoVerificador)){
                if (!rutText.equals("")){
                    if(rutText.length()==7||rutText.length()==8){
                        Intent intent = new Intent(BotonConsultaHora.this, ResultadoConsulta.class);
                        startActivity(intent);
                    }else{
                        generateToast("Rut erróneo.");
                    }

                }else{
                    generateToast("Necesita llenar todos los campos.");
                }
            }else{
                generateToast("Código verificador erróneo.");
            }
        }else{
            generateToast("Necesita llenar todos los campos.");
        }
    }

    private boolean analizar(String dv){
        boolean result = false;
        String [] arr = {"0","1","2","3","4","5","6","7","8","9","k","K"};
        for(String a : arr){
            if(dv.equals(a)){
                result = true;
                break;
            }
        }
        return result;
    }

    /*Función generateToast para mostrar mensajes temporales al momento de ejecutar el serviceFarmacias*/
    private void generateToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }
}
