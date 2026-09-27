package grupo5;

import grupo5.controlador.GestorFacturacion;
import grupo5.controlador.GestorReservas;
import grupo5.fabrica.ModalidadFabrica;
import grupo5.modelo.*;

import java.time.LocalDate;

/**
 * Clase ejecutable de prueba ajustada a los constructores después de combinar códigos.
 */
public class PruebaBackend {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("    RENT CAR - PRUEBA DE QUE FUNCIONE EL PROGRAMA EN CONSOLA     ");
        System.out.println("==================================================");

        try {
            // 1. Verificación del Singleton Empresa

            Empresa empresa = Empresa.obtenerInstancia();
            System.out.println("1. [SINGLETON] Instancia de Empresa obtenida con éxito.");

            // 2. Creación de Cliente (5 parámetros según el constructor de Miguel)

            Cliente cliente = new Cliente("1098765432", "Jhoan Esteban", "3001234567", "jhoan@ejemplo.com", 22);

            // Creación de Vehículo

            Vehiculo vehiculo = new Vehiculo("ABC-123", "Toyota", "Corolla", 2024, "Sedán", 150000.0);

            System.out.println("2. [MODELO] Cliente creado: " + cliente.getNombreCompleto());
            System.out.println("   [MODELO] Vehículo creado: " + vehiculo.getMarca() + " " + vehiculo.getModelo() + " (" + vehiculo.getPlaca() + ")");

            // 3. Verificación de Modalidad usando la fábrica

            ModalidadAlquiler modalidadEjecutiva = ModalidadFabrica.crearModalidad(
                    "EJECUTIVA", "MOD-01", "Ejecutiva", "Modalidad para viajes ejecutivos",
                    500, 120000.0, EstadoModalidad.DISPONIBLE, "Conductor opcional", 0, "VIP"
            );
            System.out.println("3. [FACTORY METHOD] Modalidad creada con éxito: " + modalidadEjecutiva.getClass().getSimpleName());

            // 4. Servicio Adicional (5 parámetros: id, nombre, descripcion, precio, disponible)
            ServicioAdicional gps = new ServicioAdicional("SERV-01", "Navegador GPS", "Sistema de navegación satelital", 25000.0, true);
            System.out.println("4. [MODELO] Servicio adicional instanciado: " + gps.getNombre() + " (Disponible: " + gps.isDisponible() + ")");

            // 5. Verificación del Patrón Builder (ReservaBuilder)

            LocalDate inicio = LocalDate.now();
            LocalDate fin = inicio.plusDays(5);

            ReservaBuilder builder = new ReservaBuilder();
            Reserva reserva = builder.conCodigo("RES-2026-001")
                    .conCliente(cliente)
                    .conVehiculo(vehiculo)
                    .conModalidad(modalidadEjecutiva)
                    .conFechas(inicio, fin)
                    .agregarServicio(gps)
                    .conDescuento(20000.0)
                    .construir();

            System.out.println("5. [BUILDER] Reserva construida correctamente:");
            System.out.println("   - Código: " + reserva.getCodigo());
            System.out.println("   - Período: " + reserva.getFechaInicio() + " al " + reserva.getFechaFin());

            // 6. Verificación del Gestor de Reservas

            GestorReservas gestorReservas = new GestorReservas();
            boolean registroExitoso = gestorReservas.registrarReserva(reserva);
            System.out.println("6. [GESTOR RESERVAS] Registro en el sistema: " + (registroExitoso ? "ÉXITO" : "FALLO"));

            // 7. Verificación del Gestor de Facturación y Reporte Financiero

            GestorFacturacion gestorFacturacion = new GestorFacturacion();
            double ingresos = gestorFacturacion.calcularIngresosPorPeriodo(inicio.minusDays(1), fin.plusDays(1));
            System.out.println("7. [GESTOR FACTURACIÓN] Ingresos calculados para el período: $" + ingresos);

            System.out.println("==================================================");
            System.out.println("  ¡TODAS LAS PRUEBAS PASARON PAPUS!");
            System.out.println("==================================================");

        } catch (Exception e) {
            System.err.println("\n❌ ERROR DETECTADO DURANTE LA EJECUCIÓN:");
            e.printStackTrace();
        }
    }
}