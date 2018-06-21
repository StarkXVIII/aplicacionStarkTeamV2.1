package cl.ubiobio.nicolas.ssbiobiobeta;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.View;
import android.widget.GridLayout;

/*Clase MainNormalActivity, corresponde a la interfaz del modo "Normal" contenida en empty Activity
 donde se integran las funciones requeridas mediante una lista de botones en formato Grid de 2x4, donde se contiene
 tambien el boton cambio de modo*/
public class MainNormalActivity extends AppCompatActivity {

    /*Se crea el grid que contiene la lista de botones*/
    GridLayout mainGrid;

    /*Instancia de la clase actual y declaración de un sharedPre para el cambio de modo*/
    private MainNormalActivity _this = this;
    private SharedPreferences sharedPre;
    private SharedPreferences.Editor editorSP;

    /*Declaración de los modos en formato int para su eventual selección y cambio*/
    private int NO_PREFERENCES = 0;
    private int MODO_AVANZADO = 1;
    private int MODO_NORMAL = 2;

    @Override
    /*Función on create donde se integran las funciones que ejecutará el "Modo Normal" junto con el inicio respectivo del
    * Grid y el sharedpre para el cambio de modo*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_normal);

        sharedPre = getSharedPreferences(getString(R.string.sharedPreID), MODE_PRIVATE);
        editorSP = sharedPre.edit();

        mainGrid = (GridLayout)findViewById(R.id.mainGrid);
        setSingleEvent(mainGrid);
    }
    //metodo para cuando uno apreta el boton "Atrás", salir de la aplicación
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    /*Función setSingleEvent que identifica las opciones listadas en el Grid mediante los botones
    * y redirige a la clase correspondiente al momento de seleccionar alguna opción de los botones*/
    private void setSingleEvent(GridLayout mainGrid) {
        for (int i = 0; i <mainGrid.getChildCount(); i++)
        {
            //la funcion grid es para que los botones sean dinamicos y se vean mejor
            CardView cardView = (CardView)mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (finalI ==0){
                        Intent intent = new Intent(MainNormalActivity.this,BotonConsultaHora.class);
                        intent.putExtra("info","Click en boton 1  "+finalI);
                        startActivity(intent);
                    }if (finalI ==1){
                        Intent intent = new Intent(MainNormalActivity.this,BotonConsultaPaciente.class);
                        intent.putExtra("info","Click en boton 1  "+finalI);
                        startActivity(intent);
                    }
                    if (finalI ==2){
                        Intent intent = new Intent(MainNormalActivity.this,BotonFarmaciaTurno.class);
                        intent.putExtra("info","Click en boton 1  "+finalI);
                        startActivity(intent);
                    }
                    if (finalI ==3){
                        Intent intent = new Intent(MainNormalActivity.this,BotonHorarioVisita.class);
                        intent.putExtra("info","Click en boton 1  "+finalI);
                        startActivity(intent);
                    }
                    if (finalI ==4){
                        Intent intent = new Intent(MainNormalActivity.this,BotonDonarSangre.class);
                        intent.putExtra("info","Click en boton 1  "+finalI);
                        startActivity(intent);
                    }
                    if (finalI ==5){
                        Intent intent = new Intent(MainNormalActivity.this,BotonCarteraServicios.class);
                        intent.putExtra("info","Click en boton 1  "+finalI);
                        startActivity(intent);
                    }
                    if (finalI ==6){
                        Intent intent = new Intent(MainNormalActivity.this,BotonSaludResponde.class);
                        intent.putExtra("info","Click en boton 1  "+finalI);
                        startActivity(intent);
                    }

                    /*Confirmación del cambio de modo que se despliega una vez seleccionado el botón "Cambiar Modo" */
                    if (finalI ==7){
                        Intent iniciar = null;
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(_this);
                        alertBuilder.setMessage("¿Está Seguro?")
                                .setTitle("Cambiar a Modo Avanzado")
                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        editorSP.putInt("MODO",MODO_AVANZADO);
                                        editorSP.commit();
                                        Intent iniciar = new Intent(_this, MainAvanzadoActivity.class);
                                        startActivity(iniciar);
                                        finish();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
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


                    }
                }
            });
        }
    }

    @Override
    //Despliega el menu para el modo normal
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_avanzado, menu);
        return true;
    }

}
