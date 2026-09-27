package grupo5.vista;

import grupo5.controlador.GestorClientes;
import grupo5.controlador.GestorFacturacion;
import grupo5.controlador.GestorReservas;
import grupo5.fabrica.ModalidadFabrica;
import grupo5.modelo.*;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class MainApp extends Application {

    private GestorReservas gestorReservas;
    private GestorClientes gestorClientes;
    private GestorFacturacion gestorFacturacion;

    // Listas y Tablas
    private ComboBox<String> cbClientesReserva;
    private ComboBox<Vehiculo> cbVehiculosReserva;

    private TableView<Cliente> tablaClientes;
    private ObservableList<Cliente> listaClientesObservable;

    private TableView<Vehiculo> tablaVehiculos;
    private ObservableList<Vehiculo> listaVehiculosObservable;

    private TableView<ServicioAdicional> tablaServicios;
    private ObservableList<ServicioAdicional> listaServiciosObservable;

    private TableView<Reserva> tablaReservas;
    private ObservableList<Reserva> listaReservasObservable;

    @Override
    public void start(Stage primaryStage) {
        gestorReservas = new GestorReservas();
        gestorClientes = new GestorClientes();
        gestorFacturacion = new GestorFacturacion();

        cargarDatosSemilla();

        primaryStage.setTitle("RentCar - Sistema de Alquiler de Vehículos");

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // 6 Pestañas Especializadas
        Tab tabClientes = new Tab("👤 Clientes", crearPanelClientes());
        Tab tabVehiculos = new Tab("🚗 Vehículos & Prototype", crearPanelVehiculos());
        Tab tabServicios = new Tab("🧰 Servicios Adicionales", crearPanelServicios());
        Tab tabRegistro = new Tab("📝 Nueva Reserva", crearPanelRegistroReserva());
        Tab tabGestion = new Tab("📋 Gestión Reservas", crearPanelGestionReservas());
        Tab tabConsultas = new Tab("🔍 Búsqueda & Finanzas", crearPanelConsultas());

        tabPane.getTabs().addAll(tabClientes, tabVehiculos, tabServicios, tabRegistro, tabGestion, tabConsultas);

        Scene scene = new Scene(tabPane, 1080, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void cargarDatosSemilla() {
        Empresa emp = Empresa.obtenerInstancia();
        if (emp.getVehiculos().isEmpty()) {
            emp.getVehiculos().add(new Vehiculo("ABC-123", "Toyota", "Corolla Cross", 2024, "SUV", 120000.0));
            emp.getVehiculos().add(new Vehiculo("XYZ-789", "Mazda", "CX-30", 2025, "SUV", 140000.0));
            emp.getVehiculos().add(new Vehiculo("UQ-2026", "Chevrolet", "Onix", 2023, "Automóvil", 90000.0));
        }

        if (emp.getClientes().isEmpty()) {
            Cliente c1 = new Cliente("Carlos Pérez", "1098765432", "6", "carlos@correo.com", 28);
            Cliente c2 = new Cliente("María Gómez", "1098111222", "3009876543", "maria@correo.com", 32);
            gestorClientes.registrarCliente(c1);
            gestorClientes.registrarCliente(c2);
        }

        if (emp.getServicios().isEmpty()) {
            emp.getServicios().add(new ServicioAdicional("SERV-01", "GPS Satelital", "Navegador GPS con mapas en tiempo real", 25000.0, true));
            emp.getServicios().add(new ServicioAdicional("SERV-02", "Silla para Bebé", "Silla homologada de seguridad infantil", 30000.0, true));
            emp.getServicios().add(new ServicioAdicional("SERV-03", "Conductor Adicional", "Permiso legal para un segundo chofer certificado", 50000.0, true));
            emp.getServicios().add(new ServicioAdicional("SERV-04", "Seguro Complementario", "Cobertura contra daños a terceros y colisión", 40000.0, true));
        }
    }

    /**
     * PESTAÑA 1: Clientes (Incluye Columna Edad y Fecha Automática)
     */
    private VBox crearPanelClientes() {
        VBox contenedor = new VBox(15);
        contenedor.setPadding(new Insets(20));

        Label lblTitulo = new Label("👤 Registro y Consulta de Clientes");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #1a237e;");

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);

        TextField txtNombre = new TextField(); txtNombre.setPromptText("Nombre Completo");
        TextField txtDoc = new TextField(); txtDoc.setPromptText("Documento ID");
        TextField txtTel = new TextField(); txtTel.setPromptText("Teléfono (Ej: 6)");
        TextField txtCorreo = new TextField(); txtCorreo.setPromptText("Correo Electrónico");
        TextField txtEdad = new TextField(); txtEdad.setPromptText("Edad");

        TextField txtFechaAuto = new TextField(LocalDate.now().toString());
        txtFechaAuto.setEditable(false);
        txtFechaAuto.setStyle("-fx-background-color: #e0e0e0; -fx-font-weight: bold;");

        grid.add(new Label("Nombre:"), 0, 0); grid.add(txtNombre, 1, 0);
        grid.add(new Label("Documento:"), 2, 0); grid.add(txtDoc, 3, 0);
        grid.add(new Label("Teléfono:"), 0, 1); grid.add(txtTel, 1, 1);
        grid.add(new Label("Correo:"), 2, 1); grid.add(txtCorreo, 3, 1);
        grid.add(new Label("Edad:"), 0, 2); grid.add(txtEdad, 1, 2);
        grid.add(new Label("Fecha Registro (Sistema):"), 2, 2); grid.add(txtFechaAuto, 3, 2);

        Button btnGuardar = new Button("➕ Registrar Cliente");
        btnGuardar.setStyle("-fx-background-color: #1a237e; -fx-text-fill: white; -fx-font-weight: bold;");

        tablaClientes = new TableView<>();
        listaClientesObservable = FXCollections.observableArrayList();

        TableColumn<Cliente, String> colDoc = new TableColumn<>("Documento");
        colDoc.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDocumento()));

        TableColumn<Cliente, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombreCompleto()));

        TableColumn<Cliente, String> colTel = new TableColumn<>("Teléfono");
        colTel.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTelefono()));

        TableColumn<Cliente, String> colCorreo = new TableColumn<>("Correo");
        colCorreo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCorreo()));

        TableColumn<Cliente, String> colEdad = new TableColumn<>("Edad");
        colEdad.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getEdad())));

        TableColumn<Cliente, String> colFecha = new TableColumn<>("Fecha Registro");
        colFecha.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getFechaRegistro().toString()));

        tablaClientes.getColumns().addAll(colDoc, colNombre, colTel, colCorreo, colEdad, colFecha);
        tablaClientes.setItems(listaClientesObservable);

        btnGuardar.setOnAction(e -> {
            try {
                int edad = Integer.parseInt(txtEdad.getText().trim());
                Cliente c = new Cliente(txtNombre.getText(), txtDoc.getText(), txtTel.getText(), txtCorreo.getText(), edad);
                gestorClientes.registrarCliente(c);
                actualizarTablaClientes();
                actualizarCombosReserva();
                txtNombre.clear(); txtDoc.clear(); txtTel.clear(); txtCorreo.clear(); txtEdad.clear();
                mostrarInfo("Cliente " + c.getNombreCompleto() + " registrado con éxito.");
            } catch (Exception ex) {
                mostrarError("Verifique la edad del cliente.");
            }
        });

        actualizarTablaClientes();
        contenedor.getChildren().addAll(lblTitulo, grid, btnGuardar, new Separator(), new Label("Lista de Clientes Registrados:"), tablaClientes);
        return contenedor;
    }

    /**
     * PESTAÑA 2: Vehículos
     */
    private VBox crearPanelVehiculos() {
        VBox contenedor = new VBox(15);
        contenedor.setPadding(new Insets(20));

        Label lblTitulo = new Label("🚗 Catálogo de Vehículos y Patrón Prototype");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #1b5e20;");

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);

        TextField txtPlaca = new TextField(); txtPlaca.setPromptText("Placa (Ej: ABC-123)");
        TextField txtMarca = new TextField(); txtMarca.setPromptText("Marca (Ej: Toyota)");
        TextField txtModelo = new TextField(); txtModelo.setPromptText("Modelo (Ej: Corolla)");
        TextField txtAnio = new TextField("2024");

        ComboBox<String> cbTipoV = new ComboBox<>();
        cbTipoV.getItems().addAll("Automóvil", "SUV", "Camioneta", "Deportivo");
        cbTipoV.setValue("Automóvil");

        TextField txtTarifaV = new TextField("120000");

        grid.add(new Label("Placa:"), 0, 0); grid.add(txtPlaca, 1, 0);
        grid.add(new Label("Marca:"), 2, 0); grid.add(txtMarca, 3, 0);
        grid.add(new Label("Modelo:"), 0, 1); grid.add(txtModelo, 1, 1);
        grid.add(new Label("Año:"), 2, 1); grid.add(txtAnio, 3, 1);
        grid.add(new Label("Tipo:"), 0, 2); grid.add(cbTipoV, 1, 2);
        grid.add(new Label("Tarifa/Día ($):"), 2, 2); grid.add(txtTarifaV, 3, 2);

        Button btnGuardarV = new Button("➕ Registrar Vehículo");
        btnGuardarV.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white; -fx-font-weight: bold;");

        tablaVehiculos = new TableView<>();
        listaVehiculosObservable = FXCollections.observableArrayList();

        TableColumn<Vehiculo, String> colPlaca = new TableColumn<>("Placa");
        colPlaca.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getPlaca()));

        TableColumn<Vehiculo, String> colMarca = new TableColumn<>("Marca");
        colMarca.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getMarca()));

        TableColumn<Vehiculo, String> colModelo = new TableColumn<>("Modelo");
        colModelo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getModelo()));

        TableColumn<Vehiculo, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTipo()));

        TableColumn<Vehiculo, String> colTarifa = new TableColumn<>("Tarifa Diaria");
        colTarifa.setCellValueFactory(d -> new SimpleStringProperty(String.format("$%.2f", d.getValue().getTarifaDiaria())));

        TableColumn<Vehiculo, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().isDisponible() ? "🟢 DISPONIBLE" : "🔴 ALQUILADO"));

        tablaVehiculos.getColumns().addAll(colPlaca, colMarca, colModelo, colTipo, colTarifa, colEstado);
        tablaVehiculos.setItems(listaVehiculosObservable);

        Button btnClonarVehiculo = new Button("♻️ Clonar Vehículo Seleccionado (Prototype)");
        btnClonarVehiculo.setStyle("-fx-background-color: #0288d1; -fx-text-fill: white; -fx-font-weight: bold;");

        btnGuardarV.setOnAction(e -> {
            try {
                int anio = Integer.parseInt(txtAnio.getText().trim());
                double tarifa = Double.parseDouble(txtTarifaV.getText().trim());
                Vehiculo v = new Vehiculo(txtPlaca.getText(), txtMarca.getText(), txtModelo.getText(), anio, cbTipoV.getValue(), tarifa);
                Empresa.obtenerInstancia().getVehiculos().add(v);
                actualizarTablaVehiculos();
                actualizarCombosReserva();
                txtPlaca.clear(); txtMarca.clear(); txtModelo.clear();
                mostrarInfo("Vehículo " + v.getPlaca() + " registrado con éxito.");
            } catch (Exception ex) {
                mostrarError("Error en año o tarifa.");
            }
        });

        btnClonarVehiculo.setOnAction(e -> {
            Vehiculo sel = tablaVehiculos.getSelectionModel().getSelectedItem();
            if (sel != null) {
                Vehiculo clon = sel.clone();
                Empresa.obtenerInstancia().getVehiculos().add(clon);
                actualizarTablaVehiculos();
                actualizarCombosReserva();
                mostrarInfo("Vehículo clonado mediante Prototype. Nueva Placa: " + clon.getPlaca());
            } else {
                mostrarError("Seleccione un vehículo para clonar.");
            }
        });

        actualizarTablaVehiculos();
        contenedor.getChildren().addAll(lblTitulo, grid, btnGuardarV, new Separator(), new Label("Inventario de Vehículos:"), tablaVehiculos, btnClonarVehiculo);
        return contenedor;
    }

    /**
     * PESTAÑA 3: Servicios Adicionales
     */
    private VBox crearPanelServicios() {
        VBox contenedor = new VBox(15);
        contenedor.setPadding(new Insets(20));

        Label lblTitulo = new Label("🧰 Catálogo de Servicios Adicionales y Control de Inventario");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #e65100;");

        tablaServicios = new TableView<>();
        listaServiciosObservable = FXCollections.observableArrayList();

        TableColumn<ServicioAdicional, String> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCodigo()));

        TableColumn<ServicioAdicional, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));

        TableColumn<ServicioAdicional, String> colDesc = new TableColumn<>("Descripción Completa");
        colDesc.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDescripcion()));

        TableColumn<ServicioAdicional, String> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(d -> new SimpleStringProperty(String.format("$%.2f", d.getValue().getPrecio())));

        TableColumn<ServicioAdicional, String> colTotal = new TableColumn<>("Total Inventario");
        colTotal.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getCantidadTotal())));

        TableColumn<ServicioAdicional, String> colDisponibles = new TableColumn<>("Disponibles");
        colDisponibles.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getCantidadDisponible())));

        TableColumn<ServicioAdicional, String> colAlquilados = new TableColumn<>("Alquilados");
        colAlquilados.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getCantidadAlquilados())));

        tablaServicios.getColumns().addAll(colCodigo, colNombre, colDesc, colPrecio, colTotal, colDisponibles, colAlquilados);
        tablaServicios.setItems(listaServiciosObservable);

        Button btnRefrescarServicios = new Button("🔄 Actualizar Tabla de Servicios");
        btnRefrescarServicios.setOnAction(e -> actualizarTablaServicios());

        actualizarTablaServicios();
        contenedor.getChildren().addAll(lblTitulo, tablaServicios, btnRefrescarServicios);
        return contenedor;
    }

    /**
     * PESTAÑA 4: Crear Nueva Reserva
     */
    private VBox crearPanelRegistroReserva() {
        VBox contenedor = new VBox(15);
        contenedor.setPadding(new Insets(20));

        Label lblTitulo = new Label("📝 Crear Nueva Reserva (Builder & Factory Method)");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #1a237e;");

        GridPane grid = new GridPane();
        grid.setHgap(15); grid.setVgap(12);

        cbClientesReserva = new ComboBox<>();
        cbVehiculosReserva = new ComboBox<>();

        ComboBox<String> cbModalidad = new ComboBox<>();
        cbModalidad.getItems().addAll("ECONOMICA", "EJECUTIVA", "PREMIUM");
        cbModalidad.setValue("ECONOMICA");

        DatePicker dpInicio = new DatePicker(LocalDate.now());
        DatePicker dpFin = new DatePicker(LocalDate.now().plusDays(3));

        CheckBox chkGPS = new CheckBox("GPS Satelital ($25.000)"); chkGPS.setSelected(true);
        CheckBox chkSilla = new CheckBox("Silla para Bebé ($30.000)");
        CheckBox chkCond = new CheckBox("Conductor Adicional ($50.000)");
        CheckBox chkSeg = new CheckBox("Seguro Complementario ($40.000)");

        grid.add(new Label("Cliente:"), 0, 0); grid.add(cbClientesReserva, 1, 0, 2, 1);
        grid.add(new Label("Vehículo Disponible:"), 0, 1); grid.add(cbVehiculosReserva, 1, 1, 2, 1);
        grid.add(new Label("Modalidad:"), 0, 2); grid.add(cbModalidad, 1, 2);
        grid.add(new Label("Fecha Inicio:"), 0, 3); grid.add(dpInicio, 1, 3);
        grid.add(new Label("Fecha Fin:"), 2, 3); grid.add(dpFin, 3, 3);

        grid.add(new Label("Servicios Adicionales Opcionales:"), 0, 4);
        VBox boxServicios = new VBox(8, chkGPS, chkSilla, chkCond, chkSeg);
        grid.add(boxServicios, 1, 4, 3, 1);

        Button btnRegistrar = new Button("🛠️ Construir y Registrar Reserva");
        btnRegistrar.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

        btnRegistrar.setOnAction(e -> {
            try {
                if (cbClientesReserva.getValue() == null || cbVehiculosReserva.getValue() == null) {
                    mostrarError("Seleccione un cliente y un vehículo disponible.");
                    return;
                }

                String docSel = cbClientesReserva.getValue().split(" - ")[0];
                Cliente clienteSel = null;
                for (Cliente c : gestorClientes.getClientes()) {
                    if (c.getDocumento().equals(docSel)) { clienteSel = c; break; }
                }

                Vehiculo vehiculoSel = cbVehiculosReserva.getValue();

                ModalidadAlquiler modalidad = ModalidadFabrica.crearModalidad(
                        cbModalidad.getValue(), "MOD-" + cbModalidad.getValue(), cbModalidad.getValue(),
                        "Modalidad seleccionada", 1, 100000.0, EstadoModalidad.DISPONIBLE, "Ninguna", 0, "Estándar"
                );

                double descuento = 0.0;
                if (clienteSel != null && gestorClientes.esTelefonoPerfecto(clienteSel.getTelefono())) {
                    descuento = 20000.0;
                }

                ReservaBuilder builder = new ReservaBuilder();
                builder.conCliente(clienteSel)
                        .conVehiculo(vehiculoSel)
                        .conModalidad(modalidad)
                        .conFechas(dpInicio.getValue(), dpFin.getValue())
                        .conDescuento(descuento);

                for (ServicioAdicional s : Empresa.obtenerInstancia().getServicios()) {
                    if (s.getCodigo().equals("SERV-01") && chkGPS.isSelected()) {
                        builder.agregarServicio(s); s.alquilar();
                    } else if (s.getCodigo().equals("SERV-02") && chkSilla.isSelected()) {
                        builder.agregarServicio(s); s.alquilar();
                    } else if (s.getCodigo().equals("SERV-03") && chkCond.isSelected()) {
                        builder.agregarServicio(s); s.alquilar();
                    } else if (s.getCodigo().equals("SERV-04") && chkSeg.isSelected()) {
                        builder.agregarServicio(s); s.alquilar();
                    }
                }

                Reserva reserva = builder.construir();

                if (gestorReservas.registrarReserva(reserva)) {
                    vehiculoSel.setDisponible(false);
                    actualizarTablaVehiculos();
                    actualizarTablaServicios();
                    actualizarCombosReserva();
                    actualizarTablaReservas();

                    mostrarAlertaFactura(reserva, descuento);
                }

            } catch (Exception ex) {
                mostrarError("Error procesando reserva: " + ex.getMessage());
            }
        });

        actualizarCombosReserva();
        contenedor.getChildren().addAll(lblTitulo, grid, btnRegistrar);
        return contenedor;
    }

    /**
     * PESTAÑA 5: Gestión de Reservas
     */
    private VBox crearPanelGestionReservas() {
        VBox contenedor = new VBox(15);
        contenedor.setPadding(new Insets(20));

        Label lblTitulo = new Label("📋 Cancelación y Clonación de Reservas (Prototype)");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #1a237e;");

        tablaReservas = new TableView<>();
        listaReservasObservable = FXCollections.observableArrayList();

        TableColumn<Reserva, String> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCodigo()));

        TableColumn<Reserva, String> colCliente = new TableColumn<>("Cliente");
        colCliente.setCellValueFactory(d -> new SimpleStringProperty(
                d.getValue().getCliente() != null ? d.getValue().getCliente().getNombreCompleto() : "N/A"));

        TableColumn<Reserva, String> colVehiculo = new TableColumn<>("Vehículo");
        colVehiculo.setCellValueFactory(d -> new SimpleStringProperty(
                d.getValue().getVehiculo() != null ? d.getValue().getVehiculo().getPlaca() : "N/A"));

        TableColumn<Reserva, String> colInicio = new TableColumn<>("Inicio");
        colInicio.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getFechaInicio().toString()));

        TableColumn<Reserva, String> colFin = new TableColumn<>("Fin");
        colFin.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getFechaFin().toString()));

        TableColumn<Reserva, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEstado()));

        TableColumn<Reserva, String> colValor = new TableColumn<>("Valor Total");
        colValor.setCellValueFactory(d -> new SimpleStringProperty(
                String.format("$%.2f", d.getValue().calcularValorTotal())));

        tablaReservas.getColumns().addAll(colCodigo, colCliente, colVehiculo, colInicio, colFin, colEstado, colValor);
        tablaReservas.setItems(listaReservasObservable);

        Button btnClonarReserva = new Button("♻️ Clonar Reserva (Prototype)");
        btnClonarReserva.setStyle("-fx-background-color: #0288d1; -fx-text-fill: white; -fx-font-weight: bold;");

        Button btnCancelarReserva = new Button("🚫 Cancelar Reserva");
        btnCancelarReserva.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-weight: bold;");

        HBox boxBotones = new HBox(15, btnClonarReserva, btnCancelarReserva);

        btnClonarReserva.setOnAction(e -> {
            Reserva sel = tablaReservas.getSelectionModel().getSelectedItem();
            if (sel != null) {
                Reserva clon = gestorReservas.clonarReserva(sel.getCodigo());
                if (clon != null) {
                    actualizarTablaReservas();
                    mostrarInfo("¡Reserva Clonada!\nCódigo: " + clon.getCodigo());
                }
            } else {
                mostrarError("Seleccione una reserva de la tabla.");
            }
        });

        btnCancelarReserva.setOnAction(e -> {
            Reserva sel = tablaReservas.getSelectionModel().getSelectedItem();
            if (sel != null) {
                boolean exito = gestorReservas.cancelarReserva(sel.getCodigo());
                if (exito) {
                    if (sel.getVehiculo() != null) {
                        sel.getVehiculo().setDisponible(true);
                    }
                    for (ServicioAdicional sa : sel.getServiciosAdicionales()) {
                        sa.liberar();
                    }
                    actualizarTablaReservas();
                    actualizarTablaVehiculos();
                    actualizarTablaServicios();
                    actualizarCombosReserva();
                    mostrarInfo("Reserva CANCELADA. El vehículo y los servicios adicionales han sido liberados.");
                }
            } else {
                mostrarError("Seleccione una reserva.");
            }
        });

        contenedor.getChildren().addAll(lblTitulo, tablaReservas, boxBotones);
        actualizarTablaReservas();
        return contenedor;
    }

    /**
     * PESTAÑA 6: Búsqueda de Cliente & Finanzas
     */
    private VBox crearPanelConsultas() {
        VBox contenedor = new VBox(20);
        contenedor.setPadding(new Insets(20));

        Label lblUniquindio = new Label("1. Búsqueda de Cliente por Teléfono & Evaluación de Número Perfecto");
        lblUniquindio.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1565c0;");

        TextField txtBuscarTel = new TextField(); txtBuscarTel.setPromptText("Ej: 6");
        Button btnBuscarTel = new Button("🔍 Buscar y Evaluar");

        HBox boxBusqueda = new HBox(10, new Label("Teléfono:"), txtBuscarTel, btnBuscarTel);
        Label lblResultadoCliente = new Label("Ingrese un teléfono para consultar.");

        btnBuscarTel.setOnAction(e -> {
            String tel = txtBuscarTel.getText().trim();
            if (tel.isEmpty()) return;

            Cliente cliente = gestorClientes.buscarPorTelefono(tel);
            boolean esPerfecto = gestorClientes.esTelefonoPerfecto(tel);

            StringBuilder sb = new StringBuilder();
            if (cliente != null) {
                sb.append("👤 Cliente: ").append(cliente.getNombreCompleto())
                        .append(" | Cédula: ").append(cliente.getDocumento())
                        .append(" | Edad: ").append(cliente.getEdad())
                        .append(" | Registrado: ").append(cliente.getFechaRegistro()).append("\n");
            } else {
                sb.append("⚠️ No hay cliente registrado con ese teléfono.\n");
            }

            if (esPerfecto) {
                sb.append("✨ ¡EL TELÉFONO (" + tel + ") ES UN NÚMERO PERFECTO! (Aplica Descuento Especial)");
            } else {
                sb.append("ℹ️ El número (" + tel + ") no es un Número Perfecto.");
            }
            lblResultadoCliente.setText(sb.toString());
        });

        VBox box1 = new VBox(10, lblUniquindio, boxBusqueda, lblResultadoCliente);
        box1.setStyle("-fx-background-color: #e3f2fd; -fx-padding: 15; -fx-background-radius: 8;");

        Label lblFacturacion = new Label("2. Reporte de Ingresos Financieros por Período");
        lblFacturacion.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;");

        DatePicker dpDesde = new DatePicker(LocalDate.now().minusDays(30));
        DatePicker dpHasta = new DatePicker(LocalDate.now().plusDays(30));
        Button btnCalcular = new Button("📊 Calcular Ingresos");
        Label lblTotal = new Label("Total Ingresos: $0.0");
        lblTotal.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;");

        btnCalcular.setOnAction(e -> {
            double total = gestorFacturacion.calcularIngresosPorPeriodo(dpDesde.getValue(), dpHasta.getValue());
            lblTotal.setText("💰 Ingresos Totales Activos en el período: $" + String.format("%.2f", total));
        });

        HBox box2Filtro = new HBox(10, new Label("Desde:"), dpDesde, new Label("Hasta:"), dpHasta, btnCalcular);
        VBox box2 = new VBox(10, lblFacturacion, box2Filtro, lblTotal);
        box2.setStyle("-fx-background-color: #e8f5e9; -fx-padding: 15; -fx-background-radius: 8;");

        contenedor.getChildren().addAll(box1, box2);
        return contenedor;
    }

    // --- MÉTODOS AUXILIARES ---
    private void actualizarTablaClientes() {
        if (listaClientesObservable != null) {
            listaClientesObservable.clear();
            listaClientesObservable.addAll(gestorClientes.getClientes());
        }
    }

    private void actualizarTablaVehiculos() {
        if (listaVehiculosObservable != null) {
            listaVehiculosObservable.clear();
            listaVehiculosObservable.addAll(Empresa.obtenerInstancia().getVehiculos());
        }
    }

    private void actualizarTablaServicios() {
        if (listaServiciosObservable != null) {
            listaServiciosObservable.clear();
            listaServiciosObservable.addAll(Empresa.obtenerInstancia().getServicios());
        }
    }

    private void actualizarCombosReserva() {
        if (cbClientesReserva != null) {
            cbClientesReserva.getItems().clear();
            for (Cliente c : gestorClientes.getClientes()) {
                cbClientesReserva.getItems().add(c.getDocumento() + " - " + c.getNombreCompleto());
            }
            if (!cbClientesReserva.getItems().isEmpty()) cbClientesReserva.setValue(cbClientesReserva.getItems().get(0));
        }

        if (cbVehiculosReserva != null) {
            cbVehiculosReserva.getItems().clear();
            for (Vehiculo v : Empresa.obtenerInstancia().getVehiculos()) {
                if (v.isDisponible()) {
                    cbVehiculosReserva.getItems().add(v);
                }
            }
            if (!cbVehiculosReserva.getItems().isEmpty()) cbVehiculosReserva.setValue(cbVehiculosReserva.getItems().get(0));
        }
    }

    private void actualizarTablaReservas() {
        if (listaReservasObservable != null) {
            listaReservasObservable.clear();
            listaReservasObservable.addAll(gestorReservas.obtenerTodasLasReservas());
        }
    }

    private void mostrarInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg);
        alert.setHeaderText("Información");
        alert.showAndWait();
    }

    private void mostrarError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
        alert.setHeaderText("Error");
        alert.showAndWait();
    }

    private void mostrarAlertaFactura(Reserva reserva, double descuento) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Resumen de Reserva Registrada");
        alert.setHeaderText("Factura / Desglose de Cobro");
        alert.setContentText(
                "Código: " + reserva.getCodigo() + "\n" +
                        "Cliente: " + reserva.getCliente().getNombreCompleto() + "\n" +
                        "Vehículo: " + reserva.getVehiculo().getMarca() + " " + reserva.getVehiculo().getModelo() + " (" + reserva.getVehiculo().getPlaca() + ")\n" +
                        "Servicios Adicionales: " + reserva.getServiciosAdicionales().size() + "\n" +
                        "Descuento Número Perfecto: $" + descuento + "\n" +
                        "----------------------------------------\n" +
                        "VALOR TOTAL FACTURADO: $" + String.format("%.2f", reserva.calcularValorTotal())
        );
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}