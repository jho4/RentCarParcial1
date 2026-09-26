package com.rentcar.modelo;

import java.util.ArrayList;
import java.util.List;

public class Empresa {
    private static Empresa instancia;

    private String nombreComercial;
    private String nit;
    private String direccion;
    private String telefono;
    private String correo;
    private String paginaWeb;
    private List<Cliente> clientes;
    private List<Vehiculo> vehiculos;
    private List<ModalidadAlquiler> modalidades;
    private List<ServicioAdicional> servicios;
    private List<Reserva> reservas;

    private Empresa() {
        this.clientes = new ArrayList<>();
        this.vehiculos = new ArrayList<>();
        this.modalidades = new ArrayList<>();
        this.servicios = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    public static Empresa obtenerInstancia() {
        if (instancia == null) {
            instancia = new Empresa();
        }
        return instancia;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public List<ModalidadAlquiler> getModalidades() {
        return modalidades;
    }

    public List<ServicioAdicional> getServicios() {
        return servicios;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }
}