package grupo5.modelo;

/**
 * Representa la modalidad de alquiler Premium.
 * Agrega características exclusivas como tipo de cobertura y conductores adicionales.
 */
public class ModalidadPremium extends ModalidadAlquiler {

    private String tipoCobertura;
    private int cantidadConductoresAdicionales;
    private String caracteristicasEspeciales;

    public ModalidadPremium(String codigo, String nombre, String descripcion,
                            int duracionMinimaDias, double valorDiario, EstadoModalidad estado,
                            String tipoCobertura, int cantidadConductoresAdicionales,
                            String caracteristicasEspeciales) {
        super(codigo, nombre, descripcion, duracionMinimaDias, valorDiario, estado);
        this.tipoCobertura = tipoCobertura;
        this.cantidadConductoresAdicionales = cantidadConductoresAdicionales;
        this.caracteristicasEspeciales = caracteristicasEspeciales;
    }

    /**
     * Constructor de copia para el patrón PROTOTYPE.
     */
    public ModalidadPremium(ModalidadPremium prototipo) {
        super(prototipo);
        this.tipoCobertura = prototipo.tipoCobertura;
        this.cantidadConductoresAdicionales = prototipo.cantidadConductoresAdicionales;
        this.caracteristicasEspeciales = prototipo.caracteristicasEspeciales;
    }

    @Override
    public double calcularCostoPorDias(int dias) {
        double costoBase = valorDiario * dias;
        // Recargo diario por cada conductor adicional permitido
        double recargoConductores = cantidadConductoresAdicionales * 15000.0 * dias;
        return costoBase + recargoConductores;
    }

    @Override
    public double calcularValor() {
        return valorDiario;
    }

    @Override
    public ModalidadAlquiler clonar() {
        return new ModalidadPremium(this);
    }

    // Getters y Setters de los atributos exclusivos de Premium
    public String getTipoCobertura() { return tipoCobertura; }
    public void setTipoCobertura(String tipoCobertura) { this.tipoCobertura = tipoCobertura; }

    public int getCantidadConductoresAdicionales() { return cantidadConductoresAdicionales; }
    public void setCantidadConductoresAdicionales(int cantidad) { this.cantidadConductoresAdicionales = cantidad; }

    public String getCaracteristicasEspeciales() { return caracteristicasEspeciales; }
    public void setCaracteristicasEspeciales(String caracteristicas) { this.caracteristicasEspeciales = caracteristicas; }
}