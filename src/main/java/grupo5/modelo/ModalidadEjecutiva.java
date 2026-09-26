package grupo5.modelo;

/**
 * Representa la modalidad de alquiler ejecutiva.
 */
public class ModalidadEjecutiva extends ModalidadAlquiler {

    public ModalidadEjecutiva(String codigo, String nombre, String descripcion,
                              int duracionMinimaDias, double valorDiario, EstadoModalidad estado) {
        super(codigo, nombre, descripcion, duracionMinimaDias, valorDiario, estado);
    }

    /**
     * Constructor de copia para el patrón PROTOTYPE.
     */
    public ModalidadEjecutiva(ModalidadEjecutiva prototipo) {
        super(prototipo);
    }

    @Override
    public double calcularCostoPorDias(int dias) {
        double costoBase = valorDiario * dias;
        if (dias >= 5) {
            costoBase *= 0.95; // 5% de descuento para alquileres de 5 o más días
        }
        return costoBase;
    }

    @Override
    public double calcularValor() {
        return valorDiario;
    }

    @Override
    public ModalidadAlquiler clonar() {
        return new ModalidadEjecutiva(this);
    }
}