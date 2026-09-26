package com.rentcar.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservaBuilder {
    private Cliente cliente;
    private Vehiculo vehiculo;
    private ModalidadAlquiler modalidad;
    private List<ServicioAdicional> servicios;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double descuento;

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
        return new Reserva(cliente, vehiculo, modalidad, servicios, fechaInicio, fechaFin, descuento);
    }
}