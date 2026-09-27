package grupo5.modelo;

public class ServicioAdicional implements Facturable {
    private String codigo;
    private String nombre;
    private String descripcion;
    private double precio;
    private boolean disponibilidad;
    // Se agregan cantidad, para poder consultar disponibilidad y su respectiva oferta por cantidad
    private int cantidadTotal;
    private int cantidadAlquilados;

    public ServicioAdicional(String codigo, String nombre, String descripcion, double precio, boolean disponibilidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.disponibilidad = disponibilidad;
        this.cantidadTotal = disponibilidad ? 10 : 0;
        this.cantidadAlquilados = 0;
    }


    // Getters y Setters

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public int getCantidadTotal() { return cantidadTotal; }
    public void setCantidadTotal(int cantidadTotal) { this.cantidadTotal = cantidadTotal; }

    public int getCantidadAlquilados() { return cantidadAlquilados; }
    public void setCantidadAlquilados(int cantidadAlquilados) { this.cantidadAlquilados = cantidadAlquilados; }

    public int getCantidadDisponible() {
        return Math.max(0, cantidadTotal - cantidadAlquilados);
    }

    //Se agregan métodos para agregar lógica al sistema
    public void alquilar() {
        if (getCantidadDisponible() > 0) {
            this.cantidadAlquilados++;
            if (getCantidadDisponible() == 0) {
                this.disponibilidad = false;
            }
        }
    }

    public void liberar() {
        if (this.cantidadAlquilados > 0) {
            this.cantidadAlquilados--;
            this.disponibilidad = true;
        }
    }
    @Override
    public double calcularValor() {
        return precio;
    }

    public boolean isDisponible() {
        return this.disponibilidad && getCantidadDisponible() > 0;
    }
}