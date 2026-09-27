package grupo5.modelo;

public class Vehiculo implements Cloneable {
    private String placa;
    private String marca;
    private String modelo;
    private int anio;
    private String tipo; // Automóvil, SUV, Camioneta, Deportivo
    private double tarifaDiaria;
    private boolean disponible; // Se agrega para la lógica del sistema

    public Vehiculo(String placa, String marca, String modelo, int anio, String tipo, double tarifaDiaria) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.tipo = tipo;
        this.tarifaDiaria = tarifaDiaria;
        this.disponible = true; // Por defecto nace disponible
    }

    // Patrón PROTOTYPE para duplicar un vehículo en el catálogo
    @Override
    public Vehiculo clone() {
        try {
            Vehiculo clon = (Vehiculo) super.clone();
            clon.placa = this.placa + "-C"; // Placa temporal para el clon
            clon.disponible = true;
            return clon;
        } catch (CloneNotSupportedException e) {
            Vehiculo clon = new Vehiculo(this.placa + "-C", this.marca, this.modelo, this.anio, this.tipo, this.tarifaDiaria);
            clon.setDisponible(true);
            return clon;
        }
    }
    // Getters y Setters
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getTarifaDiaria() {
        return tarifaDiaria;
    }

    public void setTarifaDiaria(double tarifaDiaria) {
        this.tarifaDiaria = tarifaDiaria;
    }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    @Override
    public String toString() {
        return placa + " - " + marca + " " + modelo + " ($" + tarifaDiaria + "/día)";
    }

}