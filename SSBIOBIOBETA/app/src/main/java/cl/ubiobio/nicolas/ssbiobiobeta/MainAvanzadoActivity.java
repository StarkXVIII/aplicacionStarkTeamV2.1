package cl.ubiobio.nicolas.ssbiobiobeta;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import android.webkit.WebView;
import android.webkit.WebViewClient;
/*Clase MainAvanzadoActivity, corresponde a la interfaz del modo "Avanzado" contenida enun NavigationDrawer
* con menu lateral donde se integran las funciones requeridas, un boton superior donde se adjunta el cambio de modo
* y en la pantalla principal se carga el Timeline de twitter correspondiente al SSBIOBIO*/
public class MainAvanzadoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private WebView webView;

    /*Funciones donde se integra el url de twitter junto con la dirección del timeline de SSBIOBIO
    * para mostrarlos en la pantalla base*/
    public static final String TAG = "TimeLineActivity";
    private static final String baseURl = "http://twitter.com";
    private static final String widgetInfo = "<a class=\"twitter-timeline\" href=\"https://twitter.com/SSBiobio?ref_src=twsrc%5Etfw\">Tweets by SSBiobio</a> <script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>";

    /*Instancia de la clase actual y declaración de un sharedPre para el cambio de modo*/
    private MainAvanzadoActivity _this = this;
    private SharedPreferences sharedPre;
    private SharedPreferences.Editor editorSP;

    /*Declaración de los modos en formato int para su eventual selección y cambio*/
    private int NO_PREFERENCES = 0;
    private int MODO_AVANZADO = 1;
    private int MODO_NORMAL = 2;

    @Override
    /*Función on create donde se integran las funciones que ejecutará el "Modo Avanzado"*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_avanzado);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPre = getSharedPreferences(getString(R.string.sharedPreID), MODE_PRIVATE);
        editorSP = sharedPre.edit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /*Se crea un navigationView para mostrar las opciones del "Modo Avanzado*/
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*Se crea un progressDialog para que se ejecute mientras se carga el timeline y desaparezca una vez
        cargado completamente */
        load_background_color();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        /*Se crea un webView el cual mostrará el timeline de twitter del SSBIOBIO, y se aplica el progressDialog antes
        * de mostrarlo*/
        webView = (WebView) findViewById(R.id.timeline_webview);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadDataWithBaseURL(baseURl, widgetInfo, "text/html", "UTF-8", null);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //elimina ProgressBar.
                progressDialog.dismiss();
            }
        });
    }
    /*Se carga el color del background del webView*/
    private void load_background_color() {
        WebView webView = (WebView) findViewById(R.id.timeline_webview);
        //webView.setBackgroundColor(getResources().getColor(R.color.twitter_dark));
        webView.setBackgroundColor(0); // transparent
    }

    @Override
    //metodo para cuando uno apreta el boton "Atrás" para volver a la ventana anterior
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            webView.goBack();
            //super.onBackPressed();
        }
    }

    @Override
    //Despliega el menu contextual para el modo avanzado
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_avanzado, menu);
        return true;
    }

    @Override
    /*Confirmación del cambio de modo que se despliega una vez seleccionado el onOptionItemSelected de "Cambiar Modo" */
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent iniciar = null;
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(_this);
            alertBuilder.setMessage("¿Está Seguro?")
                    .setTitle("Cambiar a Modo Normal")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            editorSP.putInt("MODO",MODO_NORMAL);
                            editorSP.commit();
                            Intent iniciar = new Intent(_this, MainNormalActivity.class);
                            startActivity(iniciar);
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            editorSP.putInt("MODO",MODO_AVANZADO);
                            editorSP.commit();
                            Intent iniciar = new Intent(_this, MainAvanzadoActivity.class);
                            startActivity(iniciar);
                            finish();
                        }
                    });
            AlertDialog dialog = alertBuilder.create();
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*Función onNavigationItemSelected que identifica las opciones listadas en el panel lateral
    * y redirige a la clase correspondiente al momento de seleccionar alguna opción de los botones*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    //Menu en el cual se maneja los item de vista de navegacion del menu del modo avanzado
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.botonUno) {
            Intent intent = new Intent(MainAvanzadoActivity.this,BotonConsultaHora.class);
            intent.putExtra("info","Click en consulta hora");
            startActivity(intent);
        } else if (id == R.id.botonDos) {
            Intent intent = new Intent(MainAvanzadoActivity.this,BotonConsultaPaciente.class);
            intent.putExtra("info","Click en consulta paciente");
            startActivity(intent);

        } else if (id == R.id.botonTres) {
            Intent intent = new Intent(MainAvanzadoActivity.this,BotonFarmaciaTurno.class);
            intent.putExtra("info","Click en farmacia");
            startActivity(intent);

        } else if (id == R.id.botonCuatro) {
            Intent intent = new Intent(MainAvanzadoActivity.this,BotonHorarioVisita.class);
            intent.putExtra("info","Click en visita");
            startActivity(intent);

        } else if (id == R.id.botonCinco) {
            Intent intent = new Intent(MainAvanzadoActivity.this,BotonDonarSangre.class);
            intent.putExtra("info","Click en donar sangre");
            startActivity(intent);

        } else if (id == R.id.botonSeis) {
            Intent intent = new Intent(MainAvanzadoActivity.this,BotonCarteraServicios.class);
            intent.putExtra("info","Click en cartera");
            startActivity(intent);

        } else if (id == R.id.botonSiete) {
            Intent intent = new Intent(MainAvanzadoActivity.this,BotonSaludResponde.class);
            intent.putExtra("info","Click en salud responde");
            startActivity(intent);

        }else if (id == R.id.contactar) {
            Intent intent = new Intent(MainAvanzadoActivity.this,Contactar.class);
            intent.putExtra("info","Click en contactar");
            startActivity(intent);


        }else if (id == R.id.salir) {

                finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
