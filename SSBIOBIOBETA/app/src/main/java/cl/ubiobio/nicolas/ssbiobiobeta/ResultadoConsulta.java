package cl.ubiobio.nicolas.ssbiobiobeta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cl.ubiobio.nicolas.ssbiobiobeta.BotonConsultaHora.digitoVerificador;
import static cl.ubiobio.nicolas.ssbiobiobeta.BotonConsultaHora.rutText;

public class ResultadoConsulta extends AppCompatActivity {

/*
Se crean las variables de etiqueta las cuales se utilizaran en la lectura del XML
 */
    private static final String ns = null;

    private static final String ETIQUETA_CODIGO = "codigo";
    private static final String ETIQUETA_DESCRIPCION = "descripcion";
    private static final String ETIQUETA_PACIENTE = "paciente";
    private static final String ETIQUETA_FECHA_ASIGNADA = "fecha_asignada";
    private static final String ETIQUETA_PROFESIONAL = "profesional";
    private static final String ETIQUETA_POLICLINICO = "policlinico";
    private static final String ETIQUETA_UBICACION = "ubicacion";
    private static final String ETIQUETA_TIPO_HORA = "tipo_hora";
    private static final String ETIQUETA_ITEM = "item";
    private static final String ETIQUETA_RETURN = "return";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_consulta);
        /*
Una vez lanzada la actividad desde el boton consultar, se realizara una llamada a la funcion
callWebService la cual se encargara de hacer la consulta al servidor de Minsal y obtener los
paciente que tienen hora asiganada
 */

        callWebService(rutText,digitoVerificador.toUpperCase());

    }

    private void callWebService(String ruta,String RutParteDos){
        int rut=Integer.parseInt(ruta);
        String WS_URL = "http://10.8.117.115/ws/SAC/Servicios_Usuarios/server.php";
        final String requestBody = getXMLBody(rut, RutParteDos);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(
                Request.Method.POST,
                WS_URL,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        Log.d("LOG WS", response);
                        XmlPullParser parser = Xml.newPullParser();
                        InputStream stream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
                        try {
                            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
                            parser.setInput(stream, null);
                            parser.nextTag();

                            while(!parser.getName().equals(ETIQUETA_RETURN)){
                                Log.d("LOG", "name: " + parser.getName());
                                parser.nextTag();
                            }
                            List<ResponseXML> lista = leerHoras(parser);
                            if(lista.size() == 1){
                                if(!lista.get(0).getCodigo().equals(1)){
                                    Log.e("ERROR", "DESCRIPCIÓN: " + lista.get(0).getDescripcion_codigo());
                                }
                            }
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("LOG WS", error.toString());
            }
        }
        ){
            @Override
            public String getBodyContentType() {
                return "text/xml;charset=UTF-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("SOAPAction", "urn:WS#HorasAsignadasUsuarios");
                //params.put("Content-Type","text/xml;charset=UTF-8");
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                Log.d("LOG WSSS", "body: " + requestBody);
                try {
                    return requestBody == null ? null : requestBody.getBytes("UTF-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }
    /*
    Metodo el cual crea una lista de Response XML los cuales se guardaran las horas
     */

    private List<ResponseXML> leerHoras(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        List<ResponseXML> listaHoteles = new ArrayList<>();
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_RETURN);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String nombreEtiqueta = parser.getName();
            if (nombreEtiqueta.equals(ETIQUETA_ITEM)) {
                listaHoteles.add(leerHora(parser));
            } else {
                saltarEtiqueta(parser);
            }
        }
        return listaHoteles;
    }
    /*
    Esta funcion es la encargada de realizar las lecturas a las horas del archivo XML las cuales
    mediante los switch setiamos los valores que se mostraran en los textview de la actividad
     */

    private ResponseXML leerHora(XmlPullParser parser) throws XmlPullParserException, IOException {
        TextView codigoConsulta= (TextView) findViewById(R.id.resultado_codigo);
        TextView descodigo=(TextView)findViewById(R.id.resultado_descripcionCodigo);
        TextView pacien=(TextView)findViewById(R.id.resultado_paciente);
        TextView fechaAsing=(TextView)findViewById(R.id.resultado_fechaAsiganda);
        TextView profes=(TextView)findViewById(R.id.resultado_profesional);
        TextView polici=(TextView)findViewById(R.id.resultado_policlinico);
        TextView ubic=(TextView)findViewById(R.id.resultado_ubicacion);
        TextView tipohor=(TextView)findViewById(R.id.resultado_tipoHora);

        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_ITEM);
        String codigo = null;
        String descripcion_codigo = null;
        String paciente = null;
        String fecha_asignada = null;
        String profesional = null;
        String policlinico = null;
        String ubicacion = null;
        String tipo_hora = null;

        while (parser.next() != XmlPullParser.END_TAG) {

            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case ETIQUETA_CODIGO:
                    codigo = leerCodigo(parser);
                    if(codigo!=null&&codigo!="") {
                        codigoConsulta.setText("Código: " +codigo);
                    }else {
                        codigoConsulta.setText("No hay Codigo");
                    }
                    break;
                case ETIQUETA_DESCRIPCION:
                    descripcion_codigo = leerDescripcion(parser);
                  if (descripcion_codigo!=null&& descripcion_codigo!=""){
                     descodigo.setText("Descripcion Código: "+ descripcion_codigo );
                    }else {
                        descodigo.setText("Descripción Código: No hay datos" );
                    }
                    break;
                case ETIQUETA_PACIENTE:
                    paciente = leerPaciente(parser);
                    if(paciente!=null&& paciente!="") {
                        pacien.setText("Nombre Paciente: " + paciente);
                    }else{
                        pacien.setText("Nombre Paciente: No hay Datos");
                    }
                    break;
                case ETIQUETA_FECHA_ASIGNADA:
                    fecha_asignada = leerFecha(parser);
                    if (fecha_asignada!=null&& fecha_asignada!="") {
                        fechaAsing.setText("Fecha Asignada: " + fecha_asignada);
                    }else {
                        fechaAsing.setText("Fecha Asignada: No hay datos" );
                    }
                    break;
                case ETIQUETA_PROFESIONAL:
                    profesional = leerProfesional(parser);
                    if(profesional!=null && profesional!="") {
                        profes.setText("Profesional: " + profesional);
                    }{
                        profes.setText("Profesional: No hay datos ");
                    }
                    break;
                case ETIQUETA_POLICLINICO:
                    policlinico = leerPoliclinico(parser);
                    if (policlinico!=null&& policlinico!="") {
                        polici.setText("Policlinico: " + policlinico);
                    }else{
                        polici.setText("Policlinico: No hay datos" );
                    }
                    break;
                case ETIQUETA_UBICACION:
                    ubicacion = leerUbicacion(parser);
                    if(ubicacion!=null&&ubicacion!=""){
                        ubic.setText("Ubicacion: "+ ubicacion );
                    }else{
                        ubic.setText("Ubicacion: No hay datos" );
                    }
                    break;
                case ETIQUETA_TIPO_HORA:
                    tipo_hora = leerTipoHora(parser);
                    if (tipo_hora!=null&&tipo_hora!="") {
                        tipohor.setText("Tipo Hora: " + tipo_hora);
                    }else {
                        tipohor.setText("Tipo Hora: No hay datos");
                    }
                    break;
                default:
                    saltarEtiqueta(parser);
                    break;
            }
        }
        return new ResponseXML(codigo,descripcion_codigo,paciente,fecha_asignada,profesional,policlinico,ubicacion,tipo_hora);
    }

    private String leerCodigo(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_CODIGO);
        String nombre = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_CODIGO);
        return nombre;
    }

    private String leerDescripcion(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_DESCRIPCION);
        String nombre = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_DESCRIPCION);
        return nombre;
    }

    private String leerPaciente(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_PACIENTE);
        String nombre = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_PACIENTE);
        return nombre;
    }

    private String leerFecha(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_FECHA_ASIGNADA);
        String nombre = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_FECHA_ASIGNADA);
        return nombre;
    }

    private String leerProfesional(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_PROFESIONAL);
        String nombre = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_PROFESIONAL);
        return nombre;
    }

    private String leerPoliclinico(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_POLICLINICO);
        String nombre = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_POLICLINICO);
        return nombre;
    }

    private String leerUbicacion(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_UBICACION);
        String nombre = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_UBICACION);
        return nombre;
    }

    private String leerTipoHora(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_TIPO_HORA);
        String nombre = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_TIPO_HORA);
        return nombre;
    }

    private String obtenerTexto(XmlPullParser parser) throws IOException, XmlPullParserException {
        String resultado = "";
        if (parser.next() == XmlPullParser.TEXT) {
            resultado = parser.getText();
            parser.nextTag();
        }
        return resultado;
    }

    public String getXMLBody(int rut, String dv) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:WS\">");
        stringBuilder.append("<soapenv:Header/>");
        stringBuilder.append("<soapenv:Body>");
        stringBuilder.append("<urn:HorasAsignadasUsuarios soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">");
        stringBuilder.append("<proArray xsi:type=\"urn:ArrayReq\">");
        stringBuilder.append("<rut_paciente xsi:type=\"xsd:int\">" + rut +"</rut_paciente>");
        stringBuilder.append("<dv_paciente xsi:type=\"xsd:string\">" + dv +"</dv_paciente>");
        stringBuilder.append("<token xsi:type=\"xsd:string\">qmVq2x7Yxm</token>");
        stringBuilder.append("</proArray>");
        stringBuilder.append("</urn:HorasAsignadasUsuarios>");
        stringBuilder.append("</soapenv:Body>");
        stringBuilder.append("</soapenv:Envelope>");
        String result = stringBuilder.toString();
        Log.d("LOG", result);
        return result;
    }

    private void saltarEtiqueta(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}

