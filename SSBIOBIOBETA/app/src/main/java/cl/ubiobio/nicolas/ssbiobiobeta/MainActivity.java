package cl.ubiobio.nicolas.ssbiobiobeta;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/*Clase MainActivity correspoondiente a la interfaz inicial de la app donde se elije el modo de uso en cada  nuevo inicio*/
public class MainActivity extends AppCompatActivity {

    private MainActivity _this = this;
    private SharedPreferences sharedPre;
    private SharedPreferences.Editor editorSP;

    private int NO_PREFERENCES = 0;
    private int MODO_AVANZADO = 1;
    private int MODO_NORMAL = 2;

    @Override
    //Metodo que al iniciar la aplicacion te pregunta que modo usar y esta a su vez la guarda para que cuando
    //uno abra nuevamente la aplicacion, este se abra con el modo ya seleccionado.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPre = getSharedPreferences(getString(R.string.sharedPreID), MODE_PRIVATE);
        editorSP = sharedPre.edit();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent iniciar = null;
                if(sharedPre.getInt("MODO", NO_PREFERENCES) == NO_PREFERENCES){
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(_this);
                    alertBuilder.setMessage("ELIJA EL MODO DE INTERFAZ")
                            .setTitle("App Salud Biobío")
                            .setPositiveButton("Modo Avanzado", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    editorSP.putInt("MODO",MODO_AVANZADO);
                                    editorSP.commit();
                                    Intent iniciar = new Intent(_this, MainAvanzadoActivity.class);
                                    startActivity(iniciar);
                                    finish();
                                }
                            })
                            .setNegativeButton("Modo Normal", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    editorSP.putInt("MODO",MODO_NORMAL);
                                    editorSP.commit();
                                    Intent iniciar = new Intent(_this, MainNormalActivity.class);
                                    startActivity(iniciar);
                                    finish();
                                }
                            });
                    AlertDialog dialog = alertBuilder.create();
                    dialog.show();
                }else{
                    if(sharedPre.getInt("MODO", NO_PREFERENCES) == MODO_AVANZADO){
                        iniciar = new Intent(_this, MainAvanzadoActivity.class);
                    }else{
                        iniciar = new Intent(_this, MainNormalActivity.class);
                    }
                    startActivity(iniciar);
                    finish();
                }
            }
        }, 2000);//Milisegundos
    }
}