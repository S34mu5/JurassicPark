# Manejo de Dependencias Circulares en el Sistema de Jurassic Park

## Dependencia circular entre Entrada y Reserva: Explicación detallada

### Primero, entendamos qué es una dependencia circular:

Una dependencia circular ocurre cuando la clase A depende de la clase B, y a su vez, la clase B depende de la clase A.

En nuestro caso:

- `Entrada` tiene un atributo de tipo `Reserva`
- `Reserva` tiene un atributo que es una lista de `Entrada`s

```
Entrada ──────► Reserva
   ▲              │
   │              │
   └──────────────┘
```

### Veamos el código exacto que crea esta dependencia:

**En Entrada.java:**

```java
public class Entrada {
    // Esta línea crea la dependencia de Entrada hacia Reserva
    private Reserva reserva;

    public Entrada(int id, Reserva reserva, double precio) {
        // Aquí necesitamos una Reserva ya existente
        this.reserva = reserva;
        // ...
    }
}
```

**En Reserva.java:**

```java
public class Reserva {
    // Esta línea crea la dependencia de Reserva hacia Entrada
    private List<Entrada> entradas;

    public Reserva(int id, Cliente cliente, Date fechaVisita) {
        // Inicializamos la lista vacía
        this.entradas = new ArrayList<>();
    }

    public void agregarEntrada(Entrada entrada) {
        // Aquí añadimos una Entrada a la Reserva
        if (!entradas.contains(entrada)) {
            entradas.add(entrada);
        }
    }
}
```

### Ahora, simulemos paso a paso el proceso con un ejemplo concreto:

**Paso 1: Creamos un Cliente (esto es previo a la dependencia circular)**

```java
Cliente juan = new Cliente(101, "Juan", "Pérez", "juan@email.com", "555-1234");
```

**Paso 2: Creamos una Reserva para ese Cliente**

```java
Reserva reservaJuan = new Reserva(201, juan, new Date());
```

En este momento, la situación es:

- `reservaJuan` existe como objeto completo
- `reservaJuan.entradas` es una lista vacía (ArrayList) inicializada
- No hay entradas todavía

Representación en memoria:

```
reservaJuan = {
    id: 201,
    cliente: [referencia a juan],
    fechaReserva: [fecha actual],
    fechaVisita: [fecha proporcionada],
    estado: PENDIENTE,
    entradas: [] (lista vacía),
    pago: null,
    factura: null
}
```

**Paso 3: Creamos una Entrada para esa Reserva**

```java
Entrada entradaAdulto = new Entrada(301, reservaJuan, 50.0);
```

Aquí ocurre lo importante:

- Al crear `entradaAdulto`, le pasamos `reservaJuan` como parámetro
- `entradaAdulto.reserva` ahora apunta a `reservaJuan`
- PERO `reservaJuan.entradas` aún NO contiene a `entradaAdulto`
- **La dependencia circular no está completa, es unidireccional**

Representación en memoria:

```
entradaAdulto = {
    id: 301,
    reserva: [referencia a reservaJuan],
    visitante: null,
    precio: 50.0,
    fechaEmision: [fecha actual],
    utilizada: false
}

reservaJuan = {
    // ...igual que antes...
    entradas: [] (sigue vacía),
    // ...
}
```

Diagrama de la situación actual:

```
entradaAdulto ───► reservaJuan
                      │
                      ╰─── entradas: []
```

**Paso 4: Completamos la relación bidireccional**

```java
reservaJuan.agregarEntrada(entradaAdulto);
```

Este es el paso clave:

- Ahora añadimos `entradaAdulto` a la lista `reservaJuan.entradas`
- La dependencia circular ahora está completa
- La referencia va en ambas direcciones

Representación en memoria después de este paso:

```
reservaJuan = {
    // ...igual que antes...
    entradas: [entradaAdulto], // Ahora contiene la entrada
    // ...
}
```

Diagrama de la situación final (dependencia circular completa):

```
entradaAdulto ───► reservaJuan
      ▲                │
      │                │
      └────────────────┘
      reservaJuan.entradas[0]
```

### ¿Por qué es importante este orden?

Si intentáramos hacer todo a la vez, nos encontraríamos con problemas:

1. **¿Cómo crear una Entrada sin Reserva?**

   - No se puede, porque el constructor de `Entrada` requiere una `Reserva`

2. **¿Cómo añadir una Entrada a una Reserva si la Entrada no existe?**
   - No se puede añadir algo que no existe

Por eso seguimos este orden específico:

1. Crear primero la `Reserva` (completamente inicializada)
2. Crear la `Entrada` pasándole la `Reserva` existente
3. Actualizar la `Reserva` para que incluya la `Entrada` creada

### Ejemplo de código completo en el servicio:

```java
// En EntradaServicio.java
public List<Entrada> generarEntradasParaReserva(Reserva reserva, int cantidad, double precio) {
    List<Entrada> entradasCreadas = new ArrayList<>();

    for (int i = 0; i < cantidad; i++) {
        // PASO 1: Generar ID único
        int idEntrada = obtenerSiguienteIdEntrada();

        // PASO 2: Crear Entrada con referencia a la Reserva
        // Aquí establecemos la primera dirección de la dependencia: Entrada → Reserva
        Entrada nuevaEntrada = new Entrada(idEntrada, reserva, precio);

        // PASO 3: Añadir la Entrada a la Reserva
        // Aquí completamos la dependencia circular: Reserva → Entrada
        reserva.agregarEntrada(nuevaEntrada);

        entradasCreadas.add(nuevaEntrada);
    }

    return entradasCreadas;
}
```

### Verificación de que funciona:

Una vez completados estos pasos, podemos navegar en ambas direcciones:

```java
// Podemos obtener la reserva desde una entrada
Reserva r = entradaAdulto.getReserva();  // Devuelve reservaJuan

// Podemos obtener las entradas desde una reserva
List<Entrada> entradas = reservaJuan.getEntradas();  // Contiene entradaAdulto
Entrada e = entradas.get(0);  // Devuelve entradaAdulto
```

Esta técnica de "establecer referencias en pasos secuenciales" es el patrón que se utiliza en todo el sistema para manejar las dependencias circulares, garantizando que:

1. Cada objeto esté correctamente inicializado antes de ser referenciado
2. Las relaciones bidireccionales se establezcan de manera controlada
3. El sistema mantenga su integridad y consistencia

## Aplicación del mismo patrón a otras dependencias circulares

Este mismo enfoque se aplica a las otras dependencias circulares del sistema:

### Reserva ↔ Pago

```java
// 1. Primero creamos el Pago con referencia a la Reserva
Pago pago = new Pago(idPago, reserva, total);

// 2. Luego actualizamos la Reserva para referenciar al Pago
reserva.setPago(pago);
```

### Reserva ↔ Factura

```java
// 1. Primero creamos la Factura con referencia a la Reserva
Factura factura = new Factura(idFactura, reserva, total);

// 2. Luego actualizamos la Reserva para referenciar a la Factura
reserva.setFactura(factura);
```

## Beneficios de este enfoque

1. **Evita problemas de inicialización circular**: Nunca nos encontramos con la situación de "el huevo o la gallina"
2. **Mantiene la integridad referencial**: Todas las referencias apuntan a objetos válidos
3. **Facilita la navegación bidireccional**: Podemos ir de una entidad a otra en ambas direcciones
4. **Previene inconsistencias**: Al establecer las relaciones en pasos controlados, evitamos estados inconsistentes
5. **Simplifica el código cliente**: Los servicios pueden encapsular esta lógica, haciendo que el código cliente sea más limpio

## Conclusión

Las dependencias circulares son inevitables en muchos modelos de dominio complejos. En lugar de tratar de eliminarlas (lo que a menudo resulta en diseños artificiales), es mejor gestionarlas adecuadamente mediante un proceso de inicialización y establecimiento de relaciones controlado y secuencial.

El patrón utilizado en el sistema de Jurassic Park demuestra cómo se pueden manejar estas dependencias de manera efectiva, manteniendo la integridad del modelo y permitiendo una navegación natural entre entidades relacionadas.
