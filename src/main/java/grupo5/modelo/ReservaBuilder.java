package grupo5.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservaBuilder {
    /*
    Se agrega private String codigo;
    */
    private String codigo;
    private Cliente cliente;
    private Vehiculo vehiculo;
    private ModalidadAlquiler modalidad;
    private List<ServicioAdicional> servicios;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double descuento;

    /*
    Se agrega método conCodigo, que debido funcionamiento
    */

    public ReservaBuilder conCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public ReservaBuilder() {
        this.servicios = new ArrayList<>();
        this.descuento = 0.0;
    }

    public ReservaBuilder conCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public ReservaBuilder conVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
        return this;
    }

    public ReservaBuilder conModalidad(ModalidadAlquiler modalidad) {
        this.modalidad = modalidad;
        return this;
    }

    public ReservaBuilder agregarServicio(ServicioAdicional servicio) {
        this.servicios.add(servicio);
        return this;
    }

    public ReservaBuilder conFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        return this;
    }

    public ReservaBuilder conDescuento(double descuento) {
        this.descuento = descuento;
        return this;
    }

    public Reserva construir() {
        // Genera un código automático de reserva si Miguel no definió el campo 'codigo'
        String codigoReserva = "RES-" + System.currentTimeMillis();

        // Instanciamos la Reserva con el orden correcto de parámetros
        Reserva reserva = new Reserva(codigoReserva, this.cliente, this.vehiculo, this.modalidad, this.fechaInicio, this.fechaFin, this.descuento);

        // Agregamos los servicios adicionales si existen
        if (this.servicios != null) {
            for (ServicioAdicional servicio : this.servicios) {
                reserva.agregarServicioAdicional(servicio);
            }
        }

        return reserva;
    }
    }
