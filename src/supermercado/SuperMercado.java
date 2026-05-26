package supermercado;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class SuperMercado {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Cargamos las listas desde los archivos binarios al arrancar
        ArrayList<Producto> inventario = ManejadorArchivos.cargarInventario();
        ArrayList<Venta> ventas = ManejadorArchivos.cargarVentas();
        
        int opcion = 0;
        System.out.printf("=== BIENVENIDOS A SUPERMERCADO 'STARMOON' ===\n");
        do {
            
            System.out.println("\n=== SECCION DE ARCHIVOS E INVENTARIO ===");
            System.out.println("1. Agregar Producto");
            System.out.println("2. Mostrar Inventario");
            System.out.println("3. Registrar Venta");
            System.out.println("4. Consultar Ventas Realizadas");
            System.out.println("5. Eliminar Producto del Inventario"); 
            System.out.println("6. Salir"); 
            System.out.print("Seleccione una opcion: ");
            
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un numero entero.");
                opcion = 0;
                continue;
            }

            switch (opcion) {
              case 1:
                    try {
                        System.out.println("\n=== REGISTRAR NUEVO PRODUCTO ===");
                        System.out.print("Ingrese ID (Clave alfanumerica): ");
                        String id = scanner.nextLine().trim();

                        // 1. Validación de formato básica de tu validador
                        ManejadorArchivos.Validador.validarClave(id);

                        // 2. CANDADO: Validar que el ID no esté repetido en el inventario actual
                        boolean idExiste = false;
                        for (int i = 0; i < inventario.size(); i++) {
                            if (inventario.get(i).getId().equalsIgnoreCase(id)) {
                                idExiste = true;
                                break; // Ya encontramos que existe, no tiene caso seguir buscando
                            }
                        }

                        // Si el ID ya existe, lanzamos la excepción para interrumpir el flujo
                        if (idExiste) {
                            throw new IdInvalidaException("El ID [" + id + "] ya esta registrado con otro producto.");
                        }
        
                        // ------------------------------------------------------------------
                        // Si el programa llega aquí, significa que el ID es único y legal.
                        // Continúa el flujo normal de captura de datos...
                        // ------------------------------------------------------------------

                        System.out.print("Ingrese Nombre del producto: ");
                        String nombre = scanner.nextLine().trim();
                        if (nombre.length() < 3) {
                            throw new IdInvalidaException("El nombre debe tener minimo 3 caracteres.");
                        }

                        System.out.print("Ingrese Marca: ");
                        String marca = scanner.nextLine().trim();

                        System.out.print("Ingrese Precio Base: ");
                        double precio = Double.parseDouble(scanner.nextLine());
                        ManejadorArchivos.Validador.validarPrecio(precio);

                        System.out.print("Ingrese Stock Inicial: ");
                        int stock = Integer.parseInt(scanner.nextLine());
                        ManejadorArchivos.Validador.validarStockInicial(stock);

                        // [Aquí continúa el submenú switch(tipoProd) que ya tienes de la fritura, lácteo, etc.]

                        // SUBMENÚ: Seleccionar el tipo específico de producto
                        System.out.println("\n¿Que tipo de producto es?");
                        System.out.println("1. Fritura");
                        System.out.println("2. Lacteo");
                        System.out.println("3. Queso (Lacteo Especializado)");
                        System.out.println("4. Verduras");
                        System.out.print("Seleccione una opcion: ");
                        int tipoProd = Integer.parseInt(scanner.nextLine());

                        Producto nuevoProducto = null; // Variable abstracta contenedora (Polimorfismo)
        
                        switch (tipoProd) {
                            case 1: // FRITURA
                                System.out.print("Ingrese Tipo de Fritura: ");
                                String tipoF = scanner.nextLine();
                                System.out.print("Ingrese Sabor: ");
                                String sabor = scanner.nextLine();
                                System.out.print("Ingrese Contenido en Gramos: ");
                                double gramos = Double.parseDouble(scanner.nextLine());

                                // Instanciamos la Fritura con sus datos específicos y generales
                                nuevoProducto = new Fritura(tipoF, sabor, gramos, precio, nombre, marca, "Frituras", id, stock, 0);
                                break;

                            case 2: // LACTEO
                                System.out.print("Ingrese Tipo de Leche: ");
                                String tipoL = scanner.nextLine();
                                System.out.print("Ingrese Fecha de Caducidad (AAAA-MM-DD): ");
                                LocalDate fechaC = LocalDate.parse(scanner.nextLine()); // Requiere formato estricto: 2026-05-25

                                nuevoProducto = new Lacteo(precio, nombre, marca, "Lacteos", id, stock, 0, tipoL, fechaC);
                                break;

                            case 3: // QUESO (Hereda de Lacteo)
                                System.out.print("Ingrese Tipo de Leche: ");
                                String tipoLecheQ = scanner.nextLine();
                                System.out.print("Ingrese Fecha de Caducidad: ");
                                LocalDate fechaCadQ = LocalDate.parse(scanner.nextLine());
                                System.out.print("Ingrese Tipo de Queso: ");
                                String tipoQ = scanner.nextLine();
                                System.out.print("Ingrese Peso en Kg: ");
                                double pesoQ = Double.parseDouble(scanner.nextLine());
                                System.out.print("¿Es importado? (true/false): ");
                                boolean esImportado = Boolean.parseBoolean(scanner.nextLine());

                                nuevoProducto = new Queso(precio, nombre, marca, "Lacteos", id, stock, 0, tipoLecheQ, fechaCadQ, tipoQ, pesoQ, esImportado);
                                break;

                            case 4: // VERDURAS
                                System.out.print("Ingrese el Peso en Kg de la muestra/empaque: ");
                                double pesoV = Double.parseDouble(scanner.nextLine());

                                nuevoProducto = new Verduras(pesoV, precio, nombre, marca, "Verduras", id, stock, 0);
                                break;

                            default:
                                System.out.println("Opcion de tipo de producto invalida. Registro cancelado.");
                                break;
                        }

                        // Si se logro instanciar algun objeto del submenú, lo guardamos
                        if (nuevoProducto != null) {
                            inventario.add(nuevoProducto); // Agregamos al ArrayList polimorfico
                            ManejadorArchivos.guardarInventario(inventario); // Guardamos permanentemente en el archivo .dat
                            System.out.println( nuevoProducto.getClass().getSimpleName() + " guardado con exito en el inventario");
                        }

                        } catch (IdInvalidaException | PrecioInvalidoException | StockInvalidoException e) {
                            System.out.println("Validacion: " + e.getMessage());
                        } catch (NumberFormatException e) {
                            System.out.println(" Error: Formato de numero incorrecto.");
                        } catch (java.time.format.DateTimeParseException e) {
                            System.out.println(" Error: Formato de fecha invalido. Recuerde usar el formato AAAA-MM-DD.");
                        }
                        break;
                case 2:
                    System.out.println("\n---------------------------------------------------------");
                    System.out.printf("%-6s | %-18s | %-6s | %-12s\n", "ID", "NOMBRE", "STOCK", "PRECIO BASE");
                    System.out.println("---------------------------------------------------------");

                    if (inventario.isEmpty()) {
                        System.out.println("         El inventario esta vacio.");
                    } else {
                        for (int i = 0; i < inventario.size(); i++) {
                            Producto p = inventario.get(i);
                            // CORRECCIÓN 1: Formateador %-6s para p.getId() que es String
                            System.out.println(String.format("%-6s | %-18.18s | %-6d | $%10.2f", 
                                    p.getId(), p.getNombre(), p.getStock(), p.getPrecioBase()));
                        }
                    }
                    System.out.println("---------------------------------------------------------");
                break;

                case 3:
                    System.out.println("\n=== INICIAR NUEVA VENTA ===");

                    // 1. Instanciamos un objeto de tu clase Caja para esta venta
                    Caja cajaActual = new Caja(); 
                    String continuarCaja = "s";

                    // CICLO: Captura de productos y llenado del carrito de la Caja
                    do {
                        System.out.print("Ingrese el ID del producto que quiere vender: ");
                        String idBuscar = scanner.nextLine().trim();
                        Producto encontrado = null;

                        // Buscamos el producto en el inventario global
                        for (int i = 0; i < inventario.size(); i++) {
                            if (inventario.get(i).getId().equalsIgnoreCase(idBuscar)) {
                                encontrado = inventario.get(i);
                                break;
                            }
                        }

                        if (encontrado == null) {
                            System.out.println("El producto no existe en el inventario.");
                        } else {
                            System.out.print("Ingrese la cantidad a vender: ");
                            try {
                                int cantidad = Integer.parseInt(scanner.nextLine());

                                if (cantidad <= 0) {
                                    System.out.println("La cantidad debe ser mayor a 0.");
                                } else if (cantidad > encontrado.getStock()) {
                                    System.out.println("No hay suficiente stock. Disponible: " + encontrado.getStock());
                                } else {
                                    // Restamos el stock del inventario global
                                    encontrado.setStock(encontrado.getStock() - cantidad);

                                    // Asignamos la cantidad que compra este cliente
                                    encontrado.setCantidad(encontrado.getCantidad() + cantidad); 

                                    // 2. Agregamos el producto directamente al carrito de la caja
                                    // (Asegúrate de que tu clase Caja tenga el método para añadir, o añade a su lista)
                                    cajaActual.getCarrito().add(encontrado);

                                    System.out.println(encontrado.getNombre() + " agregado al carrito de la caja.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Error: Ingrese un dato numerico valido.");
                            }
                        }

                        System.out.print("Desea agregar otro producto al carrito? (s/n): ");
                        continuarCaja = scanner.nextLine().trim().toLowerCase();

                    } while (continuarCaja.equals("s"));

                    // PROCESAMIENTO DEL TICKET Y PERSISTENCIA
                    if (!cajaActual.getCarrito().isEmpty()) {
                        // 3. ¡Polimorfismo y encapsulamiento! 
                        // Usamos el toString() de tu clase Caja para imprimir todo el ticket formateado
                        System.out.println(cajaActual.toString());

                        // 4. Registramos de forma persistente las ventas en el historial global
                        for (int i = 0; i < cajaActual.getCarrito().size(); i++) {
                            Producto prodVendido = cajaActual.getCarrito().get(i);
                            int cant = prodVendido.getCantidad();
                            double importeProd = cant * prodVendido.calcularPrecioFinal();

                            // Creamos el registro para tu archivo ventas.dat
                            Venta registroVenta = new Venta(prodVendido.getNombre(), cant, importeProd);
                            ventas.add(registroVenta);

                            // Limpiamos la cantidad asignada al producto para que quede listo en el inventario
                            prodVendido.setCantidad(0);
                        }

                        // 5. Guardado permanente de los archivos binarios actualizados
                        ManejadorArchivos.guardarInventario(inventario);
                        ManejadorArchivos.guardarVentas(ventas);
                        System.out.println("-- Venta finalizada y base de datos guardada con exito. --");
                    } else {
                        System.out.println("-- Venta cancelada. El carrito de la caja esta vacio. --");
                    }
                    break;

                case 4:
                    System.out.println("\n--- LISTA DE VENTAS DESDE ARCHIVO ---");
                    if (ventas.isEmpty()) {
                        System.out.println("No se han hecho ventas aun.");
                    } else {
                        for (int i = 0; i < ventas.size(); i++) {
                            System.out.println(ventas.get(i).toString());
                        }
                    }
                    break;
                case 5:
                    System.out.println("\n=== ELIMINAR PRODUCTO DEL INVENTARIO ===");
                    System.out.print("Ingrese el ID del producto que desea borrar: ");
                    String idBorrar = scanner.nextLine().trim();

                    boolean encontradoParaBorrar = false;

                    // Buscamos de forma clásica en el ArrayList
                    for (int i = 0; i < inventario.size(); i++) {
                        if (inventario.get(i).getId().equalsIgnoreCase(idBorrar)) {

                            // Guardamos el nombre antes de borrarlo para avisarle al usuario
                            String nombreEliminado = inventario.get(i).getNombre();

                            // 1. Lo removemos del ArrayList en memoria RAM
                            inventario.remove(i); 

                            // 2. Sobrescribimos el archivo binario para actualizar el disco duro
                            ManejadorArchivos.guardarInventario(inventario); 

                            System.out.println("¡Éxito! El producto '" + nombreEliminado + "' con ID [" + idBorrar + "] fue eliminado.");
                            encontradoParaBorrar = true;
                             // Salimos del ciclo porque los IDs son únicos
                        }
                    }
                    if (!encontradoParaBorrar) 
                        System.out.println("No se encontró ningún producto con el ID [" + idBorrar + "].");
                    break;
                case 6:
                    System.out.println("Saliendo del programa persistente...");
                    break;

                default:
                    System.out.println("Opcion invalida.");
                }
            
            } while (opcion != 6);
        }
}