
package beans;


public class Equipos {
    private int id;
    private String descripcion;
    private String tipo;
    private String marca;
    private int equipos_disponibles;
    private boolean novedad;

    public Equipos(int id, String descripcion, String tipo, String marca, int equipos_disponibles, boolean novedad) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.marca = marca;
        this.equipos_disponibles = equipos_disponibles;
        this.novedad = novedad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getEquipos_disponibles() {
        return equipos_disponibles;
    }

    public void setEquipos_disponibles(int equipos_disponibles) {
        this.equipos_disponibles = equipos_disponibles;
    }

    public boolean isNovedad() {
        return novedad;
    }

    public void setNovedad(boolean novedad) {
        this.novedad = novedad;
    }

    @Override
    public String toString() {
        return "Equipos{" + "id=" + id + ", descripcion=" + descripcion + ", tipo=" + tipo + ", marca=" + marca + ", equipos_disponibles=" + equipos_disponibles + ", novedad=" + novedad + '}';
    }

}