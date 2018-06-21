package cl.ubiobio.nicolas.ssbiobiobeta;


/*Clase ResponseXML que corresponde a un objeto con los atributos de una hora medica requeridos para listarlos en la opción
* de consulta hora médica*/
public class ResponseXML {

    /*Declaración de atributos de una hora médica presentes según los datos del gobierno*/
    private String codigo;
    private String descripcion_codigo;
    private String paciente;
    private String fecha_asignada;
    private String profesional;
    private String policlinico;
    private String ubicacion;
    private String tipo_hora;



    /*Funciones getter, setter y contructor de la clase ResponseXML de cada uno de los atributos para su eventual extración o
   * ingreso de los datos*/
    public ResponseXML(){}

    public ResponseXML(String codigo, String descripcion_codigo, String paciente, String fecha_asignada, String profesional, String policlinico, String ubicacion, String tipo_hora) {
        this.codigo = codigo;
        this.descripcion_codigo = descripcion_codigo;
        this.paciente = paciente;
        this.fecha_asignada = fecha_asignada;
        this.profesional = profesional;
        this.policlinico = policlinico;
        this.ubicacion = ubicacion;
        this.tipo_hora = tipo_hora;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion_codigo() {
        return descripcion_codigo;
    }

    public void setDescripcion_codigo(String descripcion_codigo) {
        this.descripcion_codigo = descripcion_codigo;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getFecha_asignada() {
        return fecha_asignada;
    }

    public void setFecha_asignada(String fecha_asignada) {
        this.fecha_asignada = fecha_asignada;
    }

    public String getProfesional() {
        return profesional;
    }

    public void setProfesional(String profesional) {
        this.profesional = profesional;
    }

    public String getPoliclinico() {
        return policlinico;
    }

    public void setPoliclinico(String policlinico) {
        this.policlinico = policlinico;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getTipo_hora() {
        return tipo_hora;
    }

    public void setTipo_hora(String tipo_hora) {
        this.tipo_hora = tipo_hora;
    }
}
