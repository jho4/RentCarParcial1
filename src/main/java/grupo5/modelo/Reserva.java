package grupo5.modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de dominio que representa una reserva de alquiler realizada por un cliente.
 * Asocia al cliente, vehiculo, modalidad y servicios adicionales consumidos.
 */
public class Reserva {

    private String codigo;
    private Cliente cliente;
    private Vehiculo vehiculo;
    private ModalidadAlquiler modalidad;
    private List<ServicioAdicional> serviciosAdicionales;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double descuento;

    public Reserva(String codigo, Cliente cliente, Vehiculo vehiculo,
                   ModalidadAlquiler modalidad, LocalDate fechaInicio,
                   LocalDate fechaFin, double descuento) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.modalidad = modalidad;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descuento = descuento;
        this.serviciosAdicionales = new ArrayList<>();
    }

    /**
     * Calcula la cantidad de dias transcurridos entre la fecha inicial y la final.
     * @return Dias de alquiler (minimo 1).
     */
    public int getDiasAlquiler() {
        if (fechaInicio == null || fechaFin == null) {
            return 0;
        }
        int dias = (int) ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        return Math.max(dias, 1); // Garantiza minimo 1 dia de cobro
    }

    /**
     * Calcula el valor final de la reserva:
     * (Costo de la modalidad por los dias) + (Costo de servicios adicionales) - Descuento.
     * @return Valor total a pagar.
     */
    public double calcularValorTotal() {
        int dias = getDiasAlquiler();
        double costoModalidad = modalidad.calcularCostoPorDias(dias);

        double costoServicios = 0.0;
        for (ServicioAdicional servicio : serviciosAdicionales) {
            costoServicios += servicio.calcularValor();
        }

        double subtotal = costoModalidad + costoServicios;
        double total = subtotal - descuento;
        return Math.max(total, 0.0);
    }

    public void agregarServicioAdicional(ServicioAdicional servicio) {
        if (servicio != null && servicio.isDisponible()) {
            this.serviciosAdicionales.add(servicio);
        }
    }

    // Métodos de acceso (Getters y Setters)
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Vehiculo getVehiculo() { return vehiculo; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }

    public ModalidadAlquiler getModalidad() { return modalidad; }
    public void setModalidad(ModalidadAlquiler modalidad) { this.modalidad = modalidad; }

    public List<ServicioAdicional> getServiciosAdicionales() { return new ArrayList<>(serviciosAdicionales); }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public double getDescuento() { return descuento; }
    public void setDescuento(double descuento) { this.descuento = descuento; }
}