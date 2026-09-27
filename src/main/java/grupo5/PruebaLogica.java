package grupo5;

import grupo5.controlador.GestorClientes;
import grupo5.controlador.GestorFacturacion;
import grupo5.controlador.GestorReservas;
import grupo5.fabrica.ModalidadFabrica;
import grupo5.modelo.*;

import java.time.LocalDate;

public class PruebaLogica {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   Prueba por consola del proyecto       ");
        System.out.println("==================================================");

        // 1. Prueba SINGLETON (Empresa)
        Empresa empresa = Empresa.obtenerInstancia();
        System.out.println("1. [SINGLETON] Instancia de Empresa obtenida con éxito.");

        // 2. Prueba Gestor Clientes
        GestorClientes gestorClientes = new GestorClientes();
        Cliente cliente1 = new Cliente("Carlos Pérez", "1098765432", "6", "carlos@uniquindio.edu.co", 28); // "6" es Número Perfecto
        gestorClientes.registrarCliente(cliente1);

        Cliente clienteEncontrado = gestorClientes.buscarPorTelefono("6");
        boolean esPerfecto = gestorClientes.esTelefonoPerfecto("6");

        System.out.println("2. [GESTOR CLIENTES & UNIQUINDIANIDAD]");
        System.out.println("   - Cliente registrado y buscado por teléfono: " + (clienteEncontrado != null ? clienteEncontrado.getNombreCompleto() : "NO ENCONTRADO"));
        System.out.println("   - ¿El teléfono es Número Perfecto? " + (esPerfecto ? "SI (Aplica Descuento Uniquindianidad)" : "NO"));

        // 3. Prueba FACTORY METHOD (Modalidad Premium)
        ModalidadAlquiler modalidadPremium = ModalidadFabrica.crearModalidad(
                "PREMIUM", "MOD-PREM", "Ejecutiva Premium",
                "Cobertura total con conductor adicional", 1, 150000.0,
                EstadoModalidad.DISPONIBLE, "Todo Riesgo", 2, "Asistencia 24/7 VIP"
        );
        System.out.println("3. [FACTORY METHOD] Modalidad Premium creada con éxito.");

        // 4. Prueba vehiculo y servicios adicionales (Facturable)

        Vehiculo vehiculo = new Vehiculo("ABC-123", "Toyota", "Corolla Cross", 2024, "SUV", 120000.0);
        ServicioAdicional gps = new ServicioAdicional("SERV-01", "GPS Satelital", "Navegador", 25000.0, true);
        ServicioAdicional seguro = new ServicioAdicional("SERV-02", "Seguro Extra", "Cobertura amplia", 40000.0, true);

        // 5. Prueba BUILDER (Reserva)
        ReservaBuilder builder = new ReservaBuilder();
        Reserva reservaOriginal = builder.conCliente(cliente1)
                .conVehiculo(vehiculo)
                .conModalidad(modalidadPremium)
                .conFechas(LocalDate.now(), LocalDate.now().plusDays(3))
                .agregarServicio(gps)
                .agregarServicio(seguro)
                .conDescuento(15000.0)
                .construir();

        GestorReservas gestorReservas = new GestorReservas();
        gestorReservas.registrarReserva(reservaOriginal);

        System.out.println("4. [BUILDER] Reserva construida y registrada:");
        System.out.println("   - Código: " + reservaOriginal.getCodigo());
        System.out.println("   - Valor Calculado: $" + reservaOriginal.calcularValorTotal());

        // 6. Prueba PATRÓN PROTOTYPE (Clonación)
        Reserva reservaClonada = gestorReservas.clonarReserva(reservaOriginal.getCodigo());
        System.out.println("5. [PROTOTYPE] Reserva clonada exitosamente:");
        System.out.println("   - Nuevo Código Clon: " + (reservaClonada != null ? reservaClonada.getCodigo() : "ERROR AL CLONAR"));
        System.out.println("   - Cliente Clonado: " + reservaClonada.getCliente().getNombreCompleto());

        // 7. Prueba Cancelación reserva
        boolean cancelada = gestorReservas.cancelarReserva(reservaOriginal.getCodigo());
        System.out.println("6. [CANCELACIÓN] Estado de cancelación de reserva original: " + (cancelada ? "CANCELADA (" + reservaOriginal.getEstado() + ")" : "ERROR"));
        System.out.println("   - Valor tras cancelación: $" + reservaOriginal.calcularValorTotal());

        // 8. Prueba Gestor facturación
        GestorFacturacion gestorFacturacion = new GestorFacturacion();
        double ingresosPeriodo = gestorFacturacion.calcularIngresosPorPeriodo(LocalDate.now().minusDays(1), LocalDate.now().plusDays(10));
        System.out.println("7. [GESTOR FACTURACIÓN] Ingresos activos calculados en el período: $" + ingresosPeriodo);

        System.out.println("==================================================");
        System.out.println(" ¡Todo okis Papu!");
        System.out.println("==================================================");
    }
}