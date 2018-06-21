package cl.ubiobio.nicolas.ssbiobiobeta;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
/*Clase "VisorLlamada" donde se cargar mediante un webView la pagina de solicitud de llamada del servicio de Salud Responde
* junto con un a progressBar que se muestramientras se carga el visor*/
public class VisorLlamar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_llamar);

         /*Se crea un progressDialog para que se ejecute mientras se carga el formulario y desaparezca una vez
        cargado completamente */
        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        /*Se crea un webView el cual mostrará el formulario online para la solicitud de la llamada del
        servicio de Salud Responde, y se aplica el progressDialog antes
        * de mostrarlo*/
        WebView web = findViewById(R.id.visorweb);
        web.setWebViewClient(new MyWebViewClient());
        WebSettings settings =web.getSettings();
        settings.setJavaScriptEnabled(true);
        web.loadUrl("http://eccnetserver.entelcallcenter.cl/minsalc2c/index.aspx");
        web.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //elimina ProgressBar.
                progressDialog.dismiss();
            }
        });



    }
    /*Funcion MyWebViewCliente que adminitra el url via string de una web*/
    private class MyWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view,String url){
            view.loadUrl(url);
            return true;
        }
    }
}
