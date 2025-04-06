# Sistema de Gestión de Jurassic Park

## Descripción General

Este sistema permite gestionar diversos aspectos de un parque temático inspirado en Jurassic Park, incluyendo clientes, reservas, entradas, visitantes, pagos y facturación. La aplicación está construida con Java y sigue una arquitectura en capas con componentes de modelo, servicios, controladores y vistas.

## Estructura del Proyecto

El proyecto está organizado en varios paquetes:

- **modelo**: Contiene las clases que representan las entidades del dominio (Cliente, Reserva, Entrada, etc.)
- **servicio**: Contiene las clases de servicio que gestionan la lógica de negocio
- **controller**: Contiene los controladores que manejan la interacción con el usuario
- **vista**: Contiene las clases para la interfaz de usuario
- **dinosaurios**: Contiene las clases relacionadas con los diferentes tipos de dinosaurios

## Modelo de Dominio y Dependencias

### Entidades Principales

- **Cliente**: Representa a una persona que realiza reservas
- **Reserva**: Representa una reserva para visitar el parque
- **Entrada**: Representa un ticket de entrada para la reserva
- **Visitante**: Representa a una persona que visita el parque
- **Pago**: Representa un pago asociado a una reserva
- **Factura**: Representa un documento fiscal por el pago de una reserva

### Relaciones entre Entidades

El sistema tiene varias relaciones bidireccionales entre entidades, lo que podría considerarse como "dependencias circulares" a nivel de modelo:

1. **Reserva ↔ Entrada**:

   - Una `Reserva` contiene una lista de `Entrada`s
   - Cada `Entrada` tiene una referencia a su `Reserva`

2. **Reserva ↔ Pago**:

   - Una `Reserva` tiene una referencia a su `Pago`
   - Un `Pago` tiene una referencia a la `Reserva` asociada

3. **Reserva ↔ Factura**:

   - Una `Reserva` tiene una referencia a su `Factura`
   - Una `Factura` tiene una referencia a la `Reserva` asociada

4. **Relaciones unidireccionales**:
   - `Reserva` → `Cliente`: Una reserva está asociada a un cliente
   - `Entrada` → `Visitante`: Una entrada puede estar asignada a un visitante

## Manejo de Dependencias Circulares

A pesar de estas dependencias bidireccionales en el modelo, el sistema funciona correctamente gracias a varias estrategias de diseño e implementación:

### 1. Inicialización Secuencial Controlada

Las dependencias circulares se establecen en pasos secuenciales y controlados:

1. Primero se crea un objeto con referencia al otro
2. Después se actualiza el segundo objeto para referenciar al primero

Por ejemplo, para la relación `Reserva` ↔ `Entrada`:

```java
// Primero: Crear entrada con referencia a la reserva
Entrada entrada = new Entrada(id, reserva, precio);

// Segundo: Actualizar la reserva para incluir la entrada
reserva.agregarEntrada(entrada);
```

### 2. Servicios como Intermediarios

Cada entidad tiene un servicio asociado que gestiona su ciclo de vida y relaciones:

- **ClienteServicio**: Administra los clientes
- **ReservaServicio**: Administra las reservas
- **EntradaServicio**: Administra las entradas
- **VisitanteServicio**: Administra los visitantes
- **PagoServicio**: Administra los pagos
- **FacturaServicio**: Administra las facturas

Estos servicios implementan métodos para:

- Crear entidades
- Buscar entidades por ID
- Filtrar entidades según criterios
- Establecer relaciones entre entidades de forma segura

### 3. Selección Explícita en Cada Paso del Flujo

El sistema no asume que se trabaja con la última entidad creada:

- En cada operación, se muestran las entidades disponibles
- El usuario selecciona explícitamente con qué entidad trabajar
- La entidad seleccionada se recupera desde el servicio correspondiente

Esto permite un flujo flexible donde se puede:

- Crear múltiples clientes
- Crear múltiples reservas para diferentes clientes
- Seleccionar cualquier reserva existente para generar entradas
- Seleccionar cualquier reserva con entradas para procesar pagos

### 4. Gestión de Referencias en Memoria

Java maneja adecuadamente las referencias circulares en memoria:

- El recolector de basura de Java puede manejar ciclos de referencias
- Las entidades pueden ser liberadas cuando no hay referencias externas a ellas
- No se producen fugas de memoria debido a estas dependencias circulares

### 5. Filtrado Contextual

Las listas de entidades mostradas al usuario son filtradas según el contexto de la operación:

- Al generar entradas: solo se muestran reservas sin entradas
- Al procesar pagos: solo se muestran reservas sin pagos
- Al confirmar pagos: solo se muestran reservas con pagos pendientes
- Al crear visitantes: solo se muestran reservas con entradas sin visitantes

Esto asegura que el usuario interactúe solo con las entidades relevantes para cada operación.

## Flujo de Trabajo Detallado

A continuación, se detalla paso a paso el flujo de trabajo del sistema y cómo se manejan las dependencias en cada operación:

### 1. Crear Cliente

**Proceso:**

- El usuario ingresa datos del cliente (nombre, apellido, email, teléfono)
- `clienteServicio.crearCliente()` crea una instancia de `Cliente`
- Se almacena en la colección de clientes del servicio

**Dependencias:**

- `Cliente` es una entidad independiente sin referencias a otras entidades
- No existen dependencias circulares en este punto

**Ejemplo de código:**

```java
Cliente cliente = clienteServicio.crearCliente(nombre, apellido, email, telefono);
```

### 2. Crear Reserva

**Proceso:**

- Se muestran todos los clientes disponibles
- El usuario selecciona un cliente específico por ID
- El usuario ingresa la fecha de visita
- `reservaServicio.crearReserva(clienteSeleccionado, fechaVisita)` crea la reserva

**Manejo de dependencias:**

- `Reserva` mantiene una referencia unidireccional a `Cliente`
- La referencia se establece en el momento de creación de la reserva
- No hay dependencia circular, solo una referencia en una dirección

**Ejemplo de código:**

```java
// Mostrar y seleccionar cliente
List<Cliente> clientes = clienteServicio.obtenerTodosLosClientes();
// [Código para mostrar y seleccionar cliente]
Cliente clienteSeleccionado = clienteServicio.buscarClientePorId(idCliente);

// Crear reserva con el cliente seleccionado
Reserva reserva = reservaServicio.crearReserva(clienteSeleccionado, fechaVisita);
```

### 3. Generar Entradas

**Proceso:**

- Se muestran todas las reservas disponibles
- El usuario selecciona una reserva específica por ID
- El usuario especifica cantidad de entradas y precio unitario
- `entradaServicio.generarEntradas(reservaSeleccionada, cantidad, precioUnitario)` crea las entradas

**Manejo de dependencias circulares:**

- **Aquí aparece la primera dependencia circular:** `Reserva` ↔ `Entrada`
- Gestión:
  1. Se crea cada `Entrada` con referencia a la `Reserva` seleccionada
  2. La `Entrada` almacena internamente esta referencia
  3. Se actualiza la `Reserva` para incluir la `Entrada` en su lista

**Ejemplo de código:**

```java
// Seleccionar reserva
Reserva reservaSeleccionada = reservaServicio.buscarReservaPorId(idReserva);

// Generar entradas (implementación interna)
List<Entrada> entradas = entradaServicio.generarEntradas(reservaSeleccionada, cantidad, precioUnitario);

// En EntradaServicio.generarEntradas:
for (int i = 0; i < cantidad; i++) {
    // Crear entrada con referencia a la reserva
    Entrada entrada = new Entrada(entradaId, reserva, precioUnitario);

    // Establecer la referencia inversa
    reserva.agregarEntrada(entrada);

    entradas.add(entrada);
}
```

### 4. Procesar Pago

**Proceso:**

- Se muestran las reservas disponibles (que no tienen pago asociado)
- El usuario selecciona una reserva específica
- `pagoServicio.crearPago(reservaSeleccionada, total)` crea el pago

**Manejo de dependencias circulares:**

- **Segunda dependencia circular:** `Reserva` ↔ `Pago`
- Gestión:
  1. Se crea el `Pago` con referencia a la `Reserva` seleccionada
  2. Se actualiza la `Reserva` con la referencia al `Pago`

**Ejemplo de código:**

```java
// Seleccionar reserva
Reserva reservaSeleccionada = reservaServicio.buscarReservaPorId(idReserva);

// Procesar pago
double total = entradaServicio.calcularPrecioTotal(reservaSeleccionada.getEntradas());
Pago pago = pagoServicio.crearPago(reservaSeleccionada, total);

// En PagoServicio.crearPago:
Pago pago = new Pago(id, reserva, total);
reserva.setPago(pago);
```

### 5. Confirmar Pago

**Proceso:**

- Se muestran reservas con pagos pendientes
- El usuario selecciona una reserva específica
- `pagoServicio.confirmarPago(pagoSeleccionado)` actualiza el estado del pago
- `reservaServicio.actualizarEstadoReserva(reservaSeleccionada, pagoSeleccionado)` actualiza el estado de la reserva

**Manejo de dependencias:**

- Se utilizan las referencias existentes para acceder a los objetos relacionados
- No se crean nuevas dependencias circulares

**Ejemplo de código:**

```java
// Filtrar y mostrar reservas con pagos pendientes
List<Reserva> reservasConPagoPendiente = new ArrayList<>();
for (Reserva reserva : reservas) {
    if (reserva.getPago() != null && reserva.getPago().getEstado() == Pago.EstadoPago.PENDIENTE) {
        reservasConPagoPendiente.add(reserva);
    }
}

// Seleccionar reserva
Reserva reservaSeleccionada = reservaServicio.buscarReservaPorId(idReserva);

// Confirmar pago
Pago pagoSeleccionado = reservaSeleccionada.getPago();
pagoServicio.confirmarPago(pagoSeleccionado);
reservaServicio.actualizarEstadoReserva(reservaSeleccionada, pagoSeleccionado);
```

### 6. Generar Factura

**Proceso:**

- Se muestran reservas con pagos confirmados
- El usuario selecciona una reserva específica
- `facturaServicio.generarFactura(reservaSeleccionada, total)` crea la factura

**Manejo de dependencias circulares:**

- **Tercera dependencia circular:** `Reserva` ↔ `Factura`
- Gestión:
  1. Se crea la `Factura` con referencia a la `Reserva` seleccionada
  2. Se actualiza la `Reserva` con la referencia a la `Factura`

**Ejemplo de código:**

```java
// Seleccionar reserva con pago confirmado
Reserva reservaSeleccionada = reservaServicio.buscarReservaPorId(idReserva);

// Generar factura
Factura factura = facturaServicio.generarFactura(reservaSeleccionada, reservaSeleccionada.getPago().getTotal());

// En FacturaServicio.generarFactura:
Factura factura = new Factura(id, reserva, total);
reserva.setFactura(factura);
```

### 7. Crear Visitantes

**Proceso:**

- Se muestran reservas con entradas sin visitantes asignados
- El usuario selecciona una reserva específica
- Para cada entrada necesaria, se solicitan datos del visitante
- Para cada visitante, el usuario puede optar por reutilizar datos de un cliente existente
- `visitanteServicio.crearVisitante(nombre, apellido, edad)` crea cada visitante

**Manejo de dependencias:**

- Los `Visitante`s se crean como entidades independientes
- No se establecen dependencias circulares en este punto

**Ejemplo de código:**

```java
// Filtrar reservas con entradas sin visitantes
List<Reserva> reservasDisponibles = new ArrayList<>();
for (Reserva reserva : reservas) {
    // [Código de filtrado]
}

// Seleccionar reserva
Reserva reservaSeleccionada = reservaServicio.buscarReservaPorId(idReserva);

// Crear visitantes
List<Visitante> visitantes = new ArrayList<>();
for (int i = 0; i < cantidadEntradas; i++) {
    // [Código para obtener datos del visitante]
    Visitante visitante = visitanteServicio.crearVisitante(nombre, apellido, edad);
    visitantes.add(visitante);
}
```

### 8. Asignar Visitantes

**Proceso:**

- Se muestran reservas con entradas sin visitantes asignados
- El usuario selecciona una reserva específica
- Se muestran visitantes disponibles
- Para cada entrada, el usuario selecciona qué visitante asignar
- `entrada.asignarVisitante(visitanteSeleccionado)` establece la relación

**Manejo de dependencias:**

- Se establece una relación unidireccional `Entrada` → `Visitante`
- No hay dependencia circular en este caso

**Ejemplo de código:**

```java
// Seleccionar reserva
Reserva reservaSeleccionada = reservaServicio.buscarReservaPorId(idReserva);

// Obtener entradas sin visitantes
List<Entrada> entradasSinVisitantes = new ArrayList<>();
for (Entrada entrada : reservaSeleccionada.getEntradas()) {
    if (!entrada.tieneVisitanteAsignado()) {
        entradasSinVisitantes.add(entrada);
    }
}

// Asignar visitantes
for (int i = 0; i < entradasSinVisitantes.size(); i++) {
    // [Código para seleccionar visitante]
    Entrada entrada = entradasSinVisitantes.get(i);
    Visitante visitanteSeleccionado = visitantesDisponibles.get(seleccion - 1);

    // Establecer relación unidireccional
    entrada.asignarVisitante(visitanteSeleccionado);
}
```

### 9. Marcar Entradas como Utilizadas

**Proceso:**

- Se muestran reservas con entradas
- El usuario selecciona una reserva específica
- `entradaServicio.marcarEntradaComoUtilizada(entrada)` actualiza cada entrada

**Manejo de dependencias:**

- Solo se actualizan estados, no se crean nuevas dependencias

**Ejemplo de código:**

```java
// Seleccionar reserva
Reserva reservaSeleccionada = reservaServicio.buscarReservaPorId(idReserva);

// Marcar entradas
for (Entrada entrada : reservaSeleccionada.getEntradas()) {
    entradaServicio.marcarEntradaComoUtilizada(entrada);
}
```

### 10. Mostrar Estadísticas

**Proceso:**

- Se muestran reservas con facturas
- El usuario selecciona una reserva específica
- `entradaServicio.generarEstadisticas(entradas, factura)` genera las estadísticas
- Se muestran las estadísticas al usuario

**Manejo de dependencias:**

- Se utiliza un objeto `EntradaEstadisticas` que no forma parte de las dependencias circulares
- Solo se accede a información existente

## Conclusiones

El sistema de Jurassic Park demuestra cómo se pueden manejar efectivamente dependencias bidireccionales (o "circulares") en un modelo de dominio, a través de:

1. **Diseño cuidadoso de la inicialización de objetos**
2. **Servicios que actúan como intermediarios**
3. **Selección explícita de entidades en cada paso del flujo**
4. **Filtrado contextual para mostrar solo entidades relevantes**
5. **Establecimiento secuencial y controlado de referencias bidireccionales**

Estas estrategias permiten que un sistema con dependencias circulares a nivel de modelo funcione correctamente y mantenga la integridad de sus datos, siempre que el flujo de trabajo sea claro, secuencial y controlado.

## Tecnologías Utilizadas

- Java
- Arquitectura en capas (Modelo-Vista-Controlador)
- Programación orientada a objetos
- Colecciones Java (ArrayList, List)

## Requisitos del Sistema

- Java JDK 8 o superior
- Entorno de desarrollo integrado (IDE) como NetBeans o IntelliJ IDEA
- No requiere bases de datos externas (almacenamiento en memoria)
