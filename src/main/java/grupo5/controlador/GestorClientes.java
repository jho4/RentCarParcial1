package grupo5.controlador;

import grupo5.modelo.Cliente;
// Importación nueva
import grupo5.modelo.Empresa;
import java.util.ArrayList;
import java.util.List;

public class GestorClientes {
    private List<Cliente> clientes;

    // Se ajusta GestorClientes
    public GestorClientes() {
        this.clientes = Empresa.obtenerInstancia().getClientes();
    }

    public void registrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public Cliente buscarPorTelefono(String telefono) {
        for (Cliente cliente : clientes) {
            if (cliente.getTelefono().equals(telefono)) {
                return cliente;
            }
        }
        return null;
    }

    public boolean esNumeroPerfecto(int numero) {
        if (numero <= 0) {
            return false;
        }
        int suma = 0;
        for (int i = 1; i <= numero / 2; i++) {
            if (numero % i == 0) {
                suma += i;
            }
        }
        return suma == numero;
    }

    // Se agrega método
    // Permite validar el teléfono ingresado en el formulario usando String

    public boolean esTelefonoPerfecto(String telefono) {
        if (telefono == null) return false;
        String soloNumeros = telefono.replaceAll("\\D+", "");
        if (soloNumeros.isEmpty()) return false;

        try {
            long val = Long.parseLong(soloNumeros);
            if (val <= Integer.MAX_VALUE) {
                return esNumeroPerfecto((int) val);
            }
        } catch (NumberFormatException ignored) {}

        // Si el número de celular supera Integer.MAX_VALUE, se evalúa la suma de sus dígitos
        int sumaDigitos = 0;
        for (char c : soloNumeros.toCharArray()) {
            sumaDigitos += Character.getNumericValue(c);
        }
        return esNumeroPerfecto(sumaDigitos);
    }
    public List<Cliente> getClientes() {
        return clientes;
    }
}