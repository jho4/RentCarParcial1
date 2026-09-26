package grupo5.controlador;

import grupo5.modelo.Empresa;
import grupo5.modelo.Reserva;
import java.time.LocalDate;

/**
 * Controlador especializado en consultas financieras y reporte de ingresos.
 */
public class GestorFacturacion {

    private Empresa empresa;

    public GestorFacturacion() {
        this.empresa = Empresa.obtenerInstancia();
    }

    /**
     * Requisito funcional del parcial:
     * Recorre las reservas registradas, identifica aquellas realizadas dentro
     * del periodo consultado y acumula el valor total generado.
     *
     * @param fechaInicio Fecha inicial del periodo a consultar.
     * @param fechaFin Fecha final del periodo a consultar.
     * @return Acumulado total de ingresos en ese rango de fechas.
     */
    public double calcularIngresosPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null || fechaFin == null || fechaInicio.isAfter(fechaFin)) {
            return 0.0;
        }

        double ingresosTotales = 0.0;

        for (Reserva reserva : empresa.getReservas()) {
            LocalDate inicioReserva = reserva.getFechaInicio();
            LocalDate finReserva = reserva.getFechaFin();

            // Evalúa si la reserva está dentro del rango solicitado
            boolean estaEnPeriodo = (!inicioReserva.isBefore(fechaInicio)) && (!finReserva.isAfter(fechaFin));

            if (estaEnPeriodo) {
                ingresosTotales += reserva.calcularValorTotal();
            }
        }

        return ingresosTotales;
    }
}