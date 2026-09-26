package grupo5.fabrica;

import grupo5.modelo.*;

/**
 * Fabrica que implementa el Patron Creacional FACTORY METHOD.
 * Se encarga de instanciar la subclase adecuada de ModalidadAlquiler.
 */
public class ModalidadFabrica {

    /**
     * Metodo de fabricacion estatico.
     *
     * @param tipo Tipo de modalidad ("ECONOMICA", "EJECUTIVA", "PREMIUM").
     * @return Instancia concreta de ModalidadAlquiler.
     */
    public static ModalidadAlquiler crearModalidad(
            String tipo,
            String codigo,
            String nombre,
            String descripcion,
            int duracionMinima,
            double valorDiario,
            EstadoModalidad estado,
            String tipoCobertura,
            int conductoresAdicionales,
            String caracteristicasEspeciales) {

        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de modalidad no puede estar vacio.");
        }

        switch (tipo.toUpperCase().trim()) {
            case "ECONOMICA":
                return new ModalidadEconomica(codigo, nombre, descripcion, duracionMinima, valorDiario, estado);

            case "EJECUTIVA":
                return new ModalidadEjecutiva(codigo, nombre, descripcion, duracionMinima, valorDiario, estado);

            case "PREMIUM":
                return new ModalidadPremium(codigo, nombre, descripcion, duracionMinima, valorDiario, estado,
                        tipoCobertura, conductoresAdicionales, caracteristicasEspeciales);

            default:
                throw new IllegalArgumentException("El tipo de modalidad ingresado no es valido: " + tipo);
        }
    }
}