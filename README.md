# RentCar - Sistema de Alquiler de Vehículos

**Asignatura:** Programación II / Parcial I

**Grupo:** Grupo 5

**Integrantes:**

* **Jhoan Esteban Avilés Sánchez**
* **Miguel Angel Bocanegra Villanueva**
---

## 📋 Descripción del Proyecto

**RentCar** es un sistema integral de gestión y alquiler de vehículos desarrollado en **Java 21**, estructurado bajo el patrón arquitectónico **MVC (Modelo-Vista-Controlador)**, además pensado con los patrones de diseño visto en clase **(Singleton, Factory Method, Builder, Prototype)** y potenciado con una interfaz gráfica moderna desarrollada en **JavaFX**.
Asímismo, tienen los principios **SOLID**, como ejes rectores del código. 

El sistema administra de forma centralizada los clientes, inventario de vehículos, servicios adicionales, modalidades de alquiler y el ciclo completo de las reservas (creación, clonación, cálculo de cobros y cancelación con liberación automática de vehículo).

---

## 📐 Arquitectura del Sistema (MVC)

El proyecto está organizado en paquetes claramente delimitados para mantener una separación estricta de responsabilidades:

```
grupo5/
 ├── modelo/               # Clases del dominio, interfaces (Facturable) y Entidad Singleton
 ├── fabrica/              # Patrón Factory Method para crear Modalidades de Alquiler
 ├── controlador/          # Lógica de negocio, algoritmos matemáticos y gestión de colecciones
 ├── vista/                # Interfaz Gráfica en JavaFX (AplicacionPrincipal)
 ├── MetodoPrincipal.java  # Punto de entrada y lanzador principal
 └── PruebaBackend.java    # Script de auditoría y verificación por consola

```

### 1. Modelo (`grupo5.modelo`)

* `Cliente`: Representa a los usuarios del sistema. Almacena nombre completo, documento, teléfono, correo, edad y **fecha de registro asignada automáticamente por el sistema (`LocalDate.now()`)**.
* `Vehiculo`: Representa los automóviles e implemementa la interfaz `Cloneable` para el **Patrón Prototype**. Mantiene placa, marca, modelo, año, tipo (*Automóvil, SUV, Camioneta, Deportivo*), tarifa diaria y **estado de disponibilidad** (`isDisponible()`).
* `ServicioAdicional`: Implementa `Facturable`. Gestiona código, nombre, descripción completa, precio, inventario total, unidades disponibles y alquiladas.
* `ModalidadAlquiler`: Clase base abstracta para los tipos de alquiler.
* `Reserva`: Entidad principal que vincula Cliente, Vehículo, Modalidad, Servicios Adicionales, fechas de inicio/fin y calcula el valor facturado. Soporta **Patrón Prototype** (`clone()`).
* `Empresa`: Clase central que aplica el **Patrón Singleton**.

### 2. Controlador (`grupo5.controlador`)

* `GestorClientes`: Encargado del registro, búsqueda por teléfono y de la evaluación matemática del **Número Perfecto** para aplicar descuentos.
* `GestorReservas`: Administra el registro de alquileres, la generación de duplicados mediante Prototype (`clonarReserva`) y la cancelación con liberación automática de recursos (`cancelarReserva`).
* `GestorFacturacion`: Consolida reportes financieros evaluando los ingresos totales en rangos de fechas específicos.

### 3. Vista (`grupo5.vista`)

* `AplicacionPrincipal`: Aplicación JavaFX con un panel estructurado en **6 Pestañas** interactivas.

---

## 🏗️ Patrones de Diseño Implementados

1. **Singleton (`Empresa`)**
* Garantiza una **única fuente de verdad** (*Single Source of Truth*) para la gestión global de clientes, inventario de vehículos, catálogo de servicios adicionales y reservas activas.


2. **Factory Method (`ModalidadFabrica` / `ModalidadAlquiler`)**
* Desencadena la instanciación de modalidades de alquiler (`ECONOMICA`, `EJECUTIVA`, `PREMIUM`) parametrizando coberturas, límites de kilometraje y costos base sin acoplar la vista a las clases concretas.


3. **Builder (`ReservaBuilder`)**
* Facilita la construcción paso a paso de objetos complejos `Reserva`, agregando de forma fluida el cliente, vehículo, fechas, servicios adicionales opcionales y cálculo de descuentos.


4. **Prototype (`Reserva` & `Vehiculo`)**
* Permite la duplicación rápida de vehículos y reservas existentes mediante el método `.clone()`, agilizando el proceso administrativo de crear plantillas de alquileres repetitivos.



---

## ⚙️ Reglas de Negocio y Características Especiales

* **Algoritmo de Número Perfecto (Descuento Especial):**
  Evalúa si la suma de los divisores propios de un número es igual al mismo número (ej: $6, 28$). Si el teléfono del cliente es un Número Perfecto (o si la suma de sus dígitos lo es para cadenas de 10 dígitos), el sistema otorga de forma automática un **descuento en el valor de la reserva**, - Se agregó descuento si llegado el caso, era número perfecto, el valor es de 20000.0".


* **Gestión de Fechas Automática:**
La fecha de registro del cliente es capturada directamente de la fecha actual del sistema al momento de guardarlo.


* **Control de Inventario y Ciclo de Vida del Vehículo:*** Al confirmar una reserva, el vehículo pasa a estado `🔴 ALQUILADO` y los servicios seleccionados descuentan stock.* 
Al ejecutar `cancelarReserva()`, el vehículo retorna automáticamente a estado `🟢 DISPONIBLE` y los servicios restauran su inventario.



---

## 🖥️ Estructura de la Interfaz Gráfica (JavaFX)

La aplicación `AplicacionPrincipal.java` organiza todas las funcionalidades requeridas en 6 pestañas principales:

1. **👤 Clientes:** Formulario de registro con captura de edad, fecha automática del sistema y `TableView` con la lista completa de clientes.
2. **🚗 Vehículos & Prototype:** Gestión del inventario (incluyendo tipo *Automóvil*), visualización del estado de disponibilidad (`DISPONIBLE` / `ALQUILADO`) y botón para **Clonar Vehículos con el Patrón Prototype**.
3. **🧰 Servicios Adicionales:** Catálogo con código, nombre, descripción detallada, precio y control estricto de unidades totales, disponibles y alquiladas.
4. **📝 Nueva Reserva:** Ensamblado con `ReservaBuilder`. Selección dinámica mediante `ComboBox` de clientes y **vehículos actualmente disponibles**, checkboxes de servicios adicionales, cálculo de fechas y facturación final.
5. **📋 Gestión Reservas:** Tabla de reservas activas con opciones para **Cancelar Reserva** (liberando vehículo e inventario) y **Clonar Reserva (Prototype)**.
6. **🔍 Búsqueda & Finanzas:** Búsqueda individual de cliente por teléfono con evaluación automática de **Número Perfecto** y módulo de cálculo de ingresos totales por período.

---

## 🚀 Instrucciones de Compilación y Ejecución

### Prerrequisitos

* **Java Development Kit (JDK):** Versión 21 o superior.
* **Apache Maven:** Versión 3.8+

### 1. Compilación del Proyecto

Desde la terminal en la raíz del proyecto, ejecuta:

```bash
mvn clean compile

```

### 2. Ejecutar la Aplicación Gráfica (JavaFX)

Para desplegar la interfaz gráfica principal:

```bash
mvn javafx:run

también puedes usar, 

mvn clean javafx:run

Con el clean, podemos evitar errores de ejecución

También, profe, lo podemos ejectuar de la siguiente forma, dado a que colocamos un método principal:

Ejecutando la clase MetodoPrincipal ubicado en src/main/java/grupo5, haces clic derecho sobre el archivo o dentro del método main y seleccionas Run 'MetodoPrincipal.main()'.

```

### 3. Ejecutar las Pruebas Backend por Consola

Profe, hicimos una prueba en consola, aunque creo que faltó agregar unas cosillas, que luego fueron agregadas en funcionalidad para la interfaz gráfica, sin embargo puedes probar funcionamiento de los patrones de diseño y la lógica interna desde la consola sin abrir la interfaz gráfica:

```bash
java -classpath target/classes grupo5.PruebaLogica

```

---

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java 21
* **Gestor de Dependencias:** Apache Maven
* **Interfaz Gráfica:** JavaFX 21
* **Control de Versiones:** Git & GitHub