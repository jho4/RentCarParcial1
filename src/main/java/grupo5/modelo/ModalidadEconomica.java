package grupo5.modelo;

/**
 * Representa la modalidad de alquiler económica.
 */
public class ModalidadEconomica extends ModalidadAlquiler {

    public ModalidadEconomica(String codigo, String nombre, String descripcion,
                              int duracionMinimaDias, double valorDiario, EstadoModalidad estado) {
        super(codigo, nombre, descripcion, duracionMinimaDias, valorDiario, estado);
    }

    /**
     * Constructor de copia para el patrón PROTOTYPE.
     */
    public ModalidadEconomica(ModalidadEconomica prototipo) {
        super(prototipo);
    }

    @Override
    public double calcularCostoPorDias(int dias) {
        return valorDiario * dias;
    }

    @Override
    public double calcularValor() {
        return valorDiario;
    }

    @Override
    public ModalidadAlquiler clonar() {
        return new ModalidadEconomica(this);
    }
}