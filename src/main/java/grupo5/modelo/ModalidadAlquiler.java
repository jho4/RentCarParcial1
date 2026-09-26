package grupo5.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta que representa la base de cualquier modalidad de alquiler.
 * Aplica el patrón creacional PROTOTYPE mediante el método abstracto clonar().
 */
public abstract class ModalidadAlquiler implements Facturable, Cloneable {

    protected String codigo;
    protected String nombre;
    protected String descripcion;
    protected int duracionMinimaDias;
    protected double valorDiario;
    protected EstadoModalidad estado;
    protected List<Beneficio> beneficios;

    public ModalidadAlquiler(String codigo, String nombre, String descripcion,
                             int duracionMinimaDias, double valorDiario, EstadoModalidad estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracionMinimaDias = duracionMinimaDias;
        this.valorDiario = valorDiario;
        this.estado = estado;
        this.beneficios = new ArrayList<>();
    }

    /**
     * Constructor de copia para el patrón PROTOTYPE.
     */
    protected ModalidadAlquiler(ModalidadAlquiler prototipo) {
        this.codigo = prototipo.codigo;
        this.nombre = prototipo.nombre;
        this.descripcion = prototipo.descripcion;
        this.duracionMinimaDias = prototipo.duracionMinimaDias;
        this.valorDiario = prototipo.valorDiario;
        this.estado = prototipo.estado;
        this.beneficios = new ArrayList<>(prototipo.beneficios);
    }

    /**
     * Método del Patrón PROTOTYPE.
     */
    public abstract ModalidadAlquiler clonar();

    /**
     * Calcula el costo total según los días de alquiler contratados.
     */
    public abstract double calcularCostoPorDias(int dias);

    public void agregarBeneficio(Beneficio beneficio) {
        if (beneficio != null) {
            this.beneficios.add(beneficio);
        }
    }

    // Métodos de acceso (Getters y Setters)
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getDuracionMinimaDias() { return duracionMinimaDias; }
    public void setDuracionMinimaDias(int duracionMinimaDias) { this.duracionMinimaDias = duracionMinimaDias; }

    public double getValorDiario() { return valorDiario; }
    public void setValorDiario(double valorDiario) { this.valorDiario = valorDiario; }

    public EstadoModalidad getEstado() { return estado; }
    public void setEstado(EstadoModalidad estado) { this.estado = estado; }

    public List<Beneficio> getBeneficios() { return new ArrayList<>(beneficios); }
}