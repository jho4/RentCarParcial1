package grupo5.controlador;

import grupo5.modelo.Empresa;
import grupo5.modelo.Reserva;
import java.util.List;

/**
 * Controlador encargado de la coordinacion y registro de las reservas.
 */
public class GestorReservas {

    private Empresa empresa;

    public GestorReservas() {
        this.empresa = Empresa.obtenerInstancia(); // Acceso al Singleton de Miguel
    }

    public boolean registrarReserva(Reserva reserva) {
        if (reserva == null) {
            return false;
        }
        return empresa.agregarReserva(reserva);
    }

    /**
     * Cancela una reserva cambiando su estado a CANCELADA.
     */
    public boolean cancelarReserva(String codigo) {
        Reserva reserva = buscarReservaPorCodigo(codigo);
        if (reserva != null) {
            reserva.setEstado("CANCELADA");
            return true;
        }
        return false;
    }

    /**
     * Clona una reserva existente utilizando el patrón PROTOTYPE y la registra.
     */
    public Reserva clonarReserva(String codigoOriginal) {
        Reserva original = buscarReservaPorCodigo(codigoOriginal);
        if (original != null) {
            Reserva clon = original.clone();
            empresa.agregarReserva(clon);
            return clon;
        }
        return null;
    }

    public Reserva buscarReservaPorCodigo(String codigo) {
        if (codigo == null) return null;
        for (Reserva r : empresa.getReservas()) {
            if (r.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return r;
            }
        }
        return null;
    }

    public List<Reserva> obtenerTodasLasReservas() {
        return empresa.getReservas();
    }
}