package grupo5.modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de Reserva que implementa Facturable y Cloneable (Patrón PROTOTYPE).
 */
public class Reserva implements Facturable, Cloneable {

    private String codigo;
    private Cliente cliente;
    private Vehiculo vehiculo;
    private ModalidadAlquiler modalidad;
    private List<ServicioAdicional> serviciosAdicionales;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double descuento;
    private String estado; // "ACTIVA" o "CANCELADA"

    public Reserva(String codigo, Cliente cliente, Vehiculo vehiculo, ModalidadAlquiler modalidad,
                   LocalDate fechaInicio, LocalDate fechaFin, double descuento) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.modalidad = modalidad;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descuento = descuento;
        this.serviciosAdicionales = new ArrayList<>();
        this.estado = "ACTIVA";
    }

    public void agregarServicioAdicional(ServicioAdicional servicio) {
        if (servicio != null) {
            this.serviciosAdicionales.add(servicio);
        }
    }

    @Override
    public double calcularValor() {
        if ("CANCELADA".equalsIgnoreCase(this.estado)) {
            return 0.0;
        }

        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        if (dias <= 0) {
            dias = 1; // Mínimo 1 día de alquiler
        }

        double tarifaVehiculo = (vehiculo != null) ? vehiculo.getTarifaDiaria() : 0.0;
        double tarifaModalidad = (modalidad != null) ? modalidad.getValorDiario() : 0.0;

        double costoBase = (tarifaVehiculo + tarifaModalidad) * dias;

        double costoServicios = 0.0;
        for (ServicioAdicional servicio : serviciosAdicionales) {
            costoServicios += servicio.calcularValor();
        }

        double total = costoBase + costoServicios - descuento;
        return Math.max(0.0, total);
    }

    /**
     * Implementación del Patrón Creacional PROTOTYPE para duplicar una reserva.
     */
    @Override
    public Reserva clone() {
        try {
            Reserva clon = (Reserva) super.clone();
            // Clonación profunda de la lista de servicios adicionales
            clon.serviciosAdicionales = new ArrayList<>(this.serviciosAdicionales);
            clon.codigo = "RES-CLON-" + System.currentTimeMillis();
            clon.estado = "ACTIVA";
            return clon;
        } catch (CloneNotSupportedException e) {
            Reserva clon = new Reserva("RES-CLON-" + System.currentTimeMillis(),
                    this.cliente, this.vehiculo, this.modalidad,
                    this.fechaInicio, this.fechaFin, this.descuento);
            clon.serviciosAdicionales = new ArrayList<>(this.serviciosAdicionales);
            return clon;
        }
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Vehiculo getVehiculo() { return vehiculo; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }

    public ModalidadAlquiler getModalidad() { return modalidad; }
    public void setModalidad(ModalidadAlquiler modalidad) { this.modalidad = modalidad; }

    public List<ServicioAdicional> getServiciosAdicionales() { return serviciosAdicionales; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public double getDescuento() { return descuento; }
    public void setDescuento(double descuento) { this.descuento = descuento; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    // Puente con GestorFacturacion
    public double calcularValorTotal() {
        return calcularValor();
    }
}