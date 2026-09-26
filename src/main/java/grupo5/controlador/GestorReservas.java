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

    public List<Reserva> obtenerTodasLasReservas() {
        return empresa.getReservas();
    }
}