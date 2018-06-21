package cl.ubiobio.nicolas.ssbiobiobeta;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/*Clase llamada ApoyoClinico donde se mandan las instrucciones a un botón dinamico que muestre la información correspondiente
* a la cartera de Apoyo Clinico del SSBIOBIO*/
public class ApoyoClinico extends AppCompatActivity implements View.OnClickListener{

    // atributo texto que se muestra al apretar ApoyoClinico
    TextView consulta;
    TextView consulta2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apoyo_clinico);

        //declaracion para que muestre el contenido del textocartera
        consulta=(TextView) findViewById(R.id.consulta);
        consulta2=(TextView) findViewById(R.id.consulta2);

        //declaracion para que al apretar los botones hagan X funcion
        findViewById(R.id.consultaboton).setOnClickListener(this);
        findViewById(R.id.consultaboton2).setOnClickListener(this);
    }

    //metodo para que cuando aprete un boton se vea el texto de solo ese botón y el resto se esconde
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.consultaboton:
                if(consulta.getVisibility() == View.GONE)
                {
                    consulta.setVisibility(View.VISIBLE);
                    consulta2.setVisibility(View.GONE);

                }else
                {
                    consulta.setVisibility(View.GONE);
                }
                break;
            case R.id.consultaboton2:
                if(consulta2.getVisibility() == View.GONE)
                {
                    consulta2.setVisibility(View.VISIBLE);
                    consulta.setVisibility(View.GONE);

                }else
                {
                    consulta2.setVisibility(View.GONE);
                }
                break;


        }

    }
}
