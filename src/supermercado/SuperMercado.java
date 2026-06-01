package supermercado;
//@author starmoon 
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class SuperMercado {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // cargamos las listas desde los archivos binarios al arrancar
        ArrayList<Producto> inventario = ManejadorArchivos.cargarInventario();
        ArrayList<Venta> ventas = ManejadorArchivos.cargarVentas();
        
        int opcion = 0;
        System.out.printf("=== BIENVENIDOS A SUPERMERCADO 'STARMOON' ===\n");
        do {
            
            System.out.println("\n=== SECCION DE ARCHIVOS E INVENTARIO ===");
            System.out.println("1. Agregar producto");
            System.out.println("2. Mostrar inventario");
            System.out.println("3. Registrar venta");
            System.out.println("4. Consultar ventas realizadas");
            System.out.println("5. Eliminar producto del inventario"); 
            System.out.println("6. Salir"); 
            System.out.print("Seleccione una opcion: ");
            
            try {
                // leemos todo como texto y lo convertimos a int 
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) { //atrapamos la excepcion por si se ingreso algo diferente a un numero 
                System.out.println("Error: Debe ingresar un numero entero.");
                opcion = 0;
                continue;// reinicia el ciclo para volver a mostrar el menu
            }

            switch (opcion) {
              case 1:
                    try {
                        System.out.println("\n=== REGISTRAR NUEVO PRODUCTO ===");
                        System.out.print("Ingrese ID (Clave alfanumerica): ");
                        String id = scanner.nextLine().trim();//utilizamo .trim para eliminar espacios 

                        //utilizamos el metodo de nuestra clase validador para validar el id 
                        ManejadorArchivos.Validador.validarClave(id);

                        //validar que el id no se repita 
                        boolean idExiste = false;
                        // pasamos todo a minusculas para que A12 y a12 cuenten como el mismo ID
                        String idNuevo = id.toLowerCase();
                        for (int i = 0; i < inventario.size(); i++) {
                            // extraemos el ID del producto en esta posición del inventario y lo pasamos a minúsculas
                            String idGuardado = inventario.get(i).getId().toLowerCase();
                            // ahora comparamos ambos textos en minúsculas usando el equals normal
                            if (idGuardado.equals(idNuevo)) {
                                idExiste = true;
                                break; // ya encontramos que existe, no tiene caso seguir buscando
                            }
                        }

                        // si el ID ya existe, lanzamos la excepción para interrumpir el flujo
                        if (idExiste) {
                            throw new IdInvalidaException("El ID [" + id + "] ya esta registrado con otro producto.");
                        }
        
                        // si llegamos hasta aqui, el ID es valido y unico
                        
                        //registramos los datos del producto 
                        System.out.print("Ingrese nombre del producto: ");
                        String nombre = scanner.nextLine().trim();
                        if (nombre.length() < 3) {
                            throw new IdInvalidaException("El nombre debe tener minimo 3 caracteres.");
                        }

                        System.out.print("Ingrese marca: ");
                        String marca = scanner.nextLine().trim();

                        System.out.print("Ingrese precio base: ");
                        double precio = Double.parseDouble(scanner.nextLine());
                        ManejadorArchivos.Validador.validarPrecio(precio);

                        System.out.print("Ingrese stock inicial: ");
                        int stock = Integer.parseInt(scanner.nextLine());
                        ManejadorArchivos.Validador.validarStockInicial(stock);
                        
                        //mostramos un menú para poder registrar el producto de acuerdo a su categoría 
                        System.out.println("\nQue tipo de producto es?");
                        System.out.println("1. Fritura");
                        System.out.println("2. Lacteo");
                        System.out.println("3. Verduras");
                        System.out.println("4. Cereal");         
                        System.out.println("5. Carne Fria");     
                        System.out.print("Seleccione una opcion: ");
                        
                        // leemos que categoria de producto quiere registrar
                        int tipoProd = Integer.parseInt(scanner.nextLine());
                        // creamos una variable vacia usando la clase padre Producto tiene la capacidad de guardar cualquiera de sus clases hijas  mas adelante en el switch.
                        Producto nuevoProducto = null; 
                       
                        switch (tipoProd) {
                            case 1: //fritura
                                //leemos los atributsos especificos de la clase 
                                System.out.print("Ingrese tipo de fritura: ");
                                String tipoF = scanner.nextLine();
                                System.out.print("Ingrese sabor: ");
                                String sabor = scanner.nextLine();
                                System.out.print("Ingrese contenido en gramos: ");
                                double gramos = Double.parseDouble(scanner.nextLine());
                                
                                //instanciamos la clase hija 
                                nuevoProducto = new Fritura(tipoF, sabor, gramos, precio, nombre, marca, "Frituras", id, stock, 0);
                                break;

                            

                            case 2://lacteos 
                                // pedimos la fecha indicando el formato exacto
                                System.out.print("Ingrese fecha de caducidad (AAAA-MM-DD): ");
                                // leemos la fecha como texto (String) y le quitamos los espacios basura con trim()
                                String fechaInput = scanner.nextLine().trim();
                                // usamos LocalDate.parse() para convertir ese texto en formato date
                                LocalDate fechaC = LocalDate.parse(fechaInput);

                                //submenu interno para separar lacteos generales de queso
                                System.out.println("\n--- SUBCLASIFICACION DE LACTEO ---");
                                System.out.println("1. Queso");
                                System.out.println("2. Otro lacteo");
                                System.out.print("Seleccione una opcion: ");
                                int subTipoLacteo = Integer.parseInt(scanner.nextLine());

                                switch (subTipoLacteo) {
                                    case 1: // Queso (No pide tipo de leche)
                                        System.out.println("\n--- DETALLES ESPECIFICOS DEL QUESO ---");
                                        System.out.print("Ingrese tipo de Queso: ");
                                        String tipoQ = scanner.nextLine().trim();
                                        System.out.print("Ingrese peso en Kg: ");
                                        double pesoQ = Double.parseDouble(scanner.nextLine());
                                        System.out.print("¿Es importado? (true/false): ");
                                        boolean esImportado = Boolean.parseBoolean(scanner.nextLine());

                                        // LLAMAMOS AL NUEVO CONSTRUCTOR (Tiene 11 parametros, sin tipoL)
                                        nuevoProducto = new Queso(precio, nombre, marca, "Lacteos", id, stock, 0, fechaC, tipoQ, pesoQ, esImportado);
                                        break;

                                    case 2: // Lácteo Base
                                        System.out.print("Ingrese que tipo de lacteo es: ");
                                        String tipoL = scanner.nextLine().trim();

                                        // Instanciamos el Lacteo normal pasando el tipo que capturamos
                                        nuevoProducto = new Lacteo(precio, nombre, marca, "Lacteos", id, stock, 0, tipoL, fechaC);
                                        break;

                                    default:
                                        System.out.println("Opcion de subclasificacion invalida. Registro de lacteo cancelado.");
                                        break;
                                }
                                break;

                            case 3: // verduras
                                System.out.print("Ingrese el peso en Kg de la muestra/empaque: ");
                                double pesoV = Double.parseDouble(scanner.nextLine());
                                //instanciamos 
                                nuevoProducto = new Verduras(pesoV, precio, nombre, marca, "Verduras", id, stock, 0);
                                break;

                            case 4: //cereal
                                System.out.print("Ingrese contenido neto en gramos: ");
                               
                                double gramosCereal = Double.parseDouble(scanner.nextLine());
                              
                                System.out.print("Ingrese el tipo de cereal: ");
                                String tipoCereal = scanner.nextLine().trim();
                                //instanciamos 
                                nuevoProducto = new Cereal(precio, nombre, marca, "Cereales", id, stock, 0, gramosCereal, tipoCereal);
                                break;

                            case 5: //carne frias 
                                System.out.print("Ingrese el peso en Kg: ");
                                double pesoCarne = Double.parseDouble(scanner.nextLine());
                                
                                System.out.print("Ingrese el tipo de carne: ");
                                String tipoCarne = scanner.nextLine().trim();
                               //instanciamos 
                                nuevoProducto = new CarneFria(precio, nombre, marca, "Carnes Frias", id, stock, 0, pesoCarne, tipoCarne);
                                break;

                            default:
                                System.out.println("Opcion de tipo de producto invalida. Registro cancelado.");
                                break;
                        }

                        // si se logro instanciar algun objeto del submenú, lo guardamos
                        if (nuevoProducto != null) {
                            inventario.add(nuevoProducto); // agregamos al ArrayList
                            ManejadorArchivos.guardarInventario(inventario); // guardamos permanentemente en el archivo .dat
                            // getSimpleName() obtiene el nombre de la clase hija para imprimirlo
                            System.out.println( nuevoProducto.getClass().getSimpleName() + " guardado con exito en el inventario");
                        }
                        // cachamos cualquiera de nuestras excepciones
                        } catch (IdInvalidaException | PrecioInvalidoException | StockInvalidoException e) {
                            System.out.println("Validacion: " + e.getMessage());
                        } catch (NumberFormatException e) {
                            System.out.println(" Error: Formato de numero incorrecto.");
                        } catch (java.time.format.DateTimeParseException e) {
                            System.out.println(" Error: Formato de fecha invalido. Recuerde usar el formato AAAA-MM-DD.");
                        }
                        break;
                case 2:
                    int subOpcion = 0;
                    do {
                        //creamos un submenu para poder visulaizar el inventario de manera más ordenada 
                       System.out.println("\n=== VISUALIZAR INVENTARIO ===");
                        System.out.println("1. Inventario general");
                        System.out.println("2. Ver detalles de frituras");
                        System.out.println("3. Ver detalles de lacteos y quesos");
                        System.out.println("4. Ver detalles de verduras");
                        System.out.println("5. Ver detalles de cereales");      
                        System.out.println("6. Ver detalles de carnes frias");  
                        System.out.println("7. Regresar al Menu Principal");    
                        System.out.print("Seleccione una opcion: ");
                        try {
                            subOpcion = Integer.parseInt(scanner.nextLine());
                            
                            //revisa que la lista tenga productos y que la opcion sea diferente a la salida 
                            if (inventario.isEmpty() && subOpcion != 7) {
                                System.out.println("El inventario esta completamente vacio.");
                                continue;
                            }

                            switch (subOpcion) {
                                case 1:
                                    // definimos una estructura para la tabla general
                                    System.out.println("\n---------------------------------------------------------");
                                    System.out.printf("%-6s | %-18s | %-6s | %-12s\n", "ID", "NOMBRE", "STOCK", "PRECIO BASE");
                                    System.out.println("---------------------------------------------------------");
                                    for (int i = 0; i < inventario.size(); i++) {
                                        Producto p = inventario.get(i);
                                        System.out.println(String.format("%-6s | %-18.18s | %-6d | $%10.2f", 
                                                p.getId(), p.getNombre(), p.getStock(), p.getPrecioBase()));
                                    }
                                    System.out.println("---------------------------------------------------------");
                                    break;

                                case 2: // DETALLES DE FRITURAS
                                case 3: // DETALLES DE LÁCTEOS
                                case 4: // DETALLES DE VERDURAS
                                case 5: // DETALLES DE CEREALES 
                                case 6: // DETALLES DE CARNE FRIA 
                                    System.out.println("\n--- DETALLES DE CATEGORIA ---");
                                    boolean hayProductos = false;
                                
                                    // recorremos el inventario evaluando a qué clase hija pertenece cada objeto
                                    for (Producto p : inventario) {
                                        if (subOpcion == 2 && p instanceof Fritura) {
                                            System.out.println(p.toString());
                                            hayProductos = true;
                                        } else if (subOpcion == 3 && p instanceof Lacteo) {
                                            System.out.println(p.toString());
                                            hayProductos = true;
                                        } else if (subOpcion == 4 && p instanceof Verduras) {
                                            System.out.println(p.toString());
                                            hayProductos = true;
                                        } else if (subOpcion == 5 && p instanceof Cereal) {    
                                            System.out.println(p.toString());
                                            hayProductos = true;
                                        } else if (subOpcion == 6 && p instanceof CarneFria) {   
                                            System.out.println(p.toString());
                                            hayProductos = true;
                                        }
                                    }

                                    // si terminó el ciclo y no entramos a ningun IF, mostramos que esa sección está vacía
                                    if (!hayProductos) {
                                        System.out.println("No hay productos registrados en esta categoria especifica.");
                                    }
                                    break;

                            case 7: 
                                System.out.println("Regresando al menu principal...");
                                break;
                                

                            default:
                                System.out.println("Opcion invalida.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Debe ingresar un numero entero.");
                        }
                    } while (subOpcion != 7);
                    break;

                case 3:
                    System.out.println("\n=== INICIAR NUEVA VENTA ===");

                    // se instancia un objeto de la clase Caja para la venta
                    Caja cajaActual = new Caja(); 
                    String continuarCaja = "s";

                    // ciclo para agregar multiples productos al carrito de la caja
                    do {
                        System.out.print("Ingrese el ID del producto que quiere vender: ");
                        String idBuscar = scanner.nextLine().trim();
                        Producto encontrado = null;

                        // buscamos el producto en el inventario
                        String idBuscarMin = idBuscar.toLowerCase(); // lo estandarizamos a minusculas antes del ciclo
                        for (int i = 0; i < inventario.size(); i++) {
                            String idGuardado = inventario.get(i).getId().toLowerCase(); // extraemos en minúsculas
                            if (idGuardado.equals(idBuscarMin)) { // comparamos con equals
                                encontrado = inventario.get(i);
                                break; // cortamos el ciclo al encontrar la coincidencia exacta
                            }
                        }

                        if (encontrado == null) {
                            System.out.println("El producto no existe en el inventario.");
                        } else {
                            // validacion de caducidad
                            boolean puedeVenderse = true;
                            
                            // Aplicamos polimorfismo para ver si el objeto tiene fecha de caducidad
                            if (encontrado instanceof Lacteo) {
                                Lacteo productoLacteo = (Lacteo) encontrado;
                                
                                // comparamos la fecha de caducidad con la fecha del día de hoy
                                if (productoLacteo.getFechaCaducidad().isBefore(LocalDate.now())) {
                                    System.out.println("ALERTA DE SANIDAD: El producto '" + encontrado.getNombre() + 
                                                       "' caduco el " + productoLacteo.getFechaCaducidad() + " y NO puede ser vendido.");
                                    puedeVenderse = false; // bloqueamos el flujo de venta por seguridad
                                }
                            }

                            // si el producto está en buen estado (o es una fritura/verdura)procedemos a pedir la cantidad
                            if (puedeVenderse) {
                                System.out.print("Ingrese la cantidad a vender: ");
                                try {
                                    int cantidad = Integer.parseInt(scanner.nextLine());
                                    
                                    if (cantidad <= 0) {
                                        System.out.println("La cantidad debe ser mayor a 0.");
                                    } else if (cantidad > encontrado.getStock()) {
                                        
                                        // lanzamos la excepcion si no hay existencias suficientes en el inventario
                                        throw new InsuficienteStockException("No hay suficiente stock. Disponible: " + encontrado.getStock());
                                        
                                    } else {
                                        // restamos el stock del inventario
                                        encontrado.setStock(encontrado.getStock() - cantidad);

                                        // asignamos la cantidad que compra este cliente
                                        encontrado.setCantidad(encontrado.getCantidad() + cantidad); 

                                        // agregamos el producto directamente al carrito de compra
                                        cajaActual.getCarrito().add(encontrado);

                                        System.out.println(encontrado.getNombre() + " agregado al carrito de la caja.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Error: Ingrese un dato numerico valido."); 
                                    
                                // atrapamos la excepcion de stock para evitar que se cierre el programa
                                } catch (InsuficienteStockException e) {
                                    System.out.println("Error de Venta: " + e.getMessage());
                                }
                            }
                        }
                        

                        System.out.print("Desea agregar otro producto al carrito? (s/n): ");
                        continuarCaja = scanner.nextLine().trim().toLowerCase();

                    } while (continuarCaja.equals("s"));

                    // procesamiento del ticket
                    if (!cajaActual.getCarrito().isEmpty()) {
                        // aqui usamos polimorfismo y encapsulamiento 
                        // imprimimos el ticket completo usando el metodo toString de la clase Caja
                        System.out.println(cajaActual.toString());

                        // registramos los movimientos en el historial global de ventas
                        for (int i = 0; i < cajaActual.getCarrito().size(); i++) {
                            Producto prodVendido = cajaActual.getCarrito().get(i);
                            int cant = prodVendido.getCantidad();
                            double importeProd = cant * prodVendido.calcularPrecioFinal();

                            // creamos el objeto Venta para el registro
                            Venta registroVenta = new Venta(prodVendido.getNombre(), cant, importeProd);
                            ventas.add(registroVenta);

                            // limpiamos la cantidad asignada al producto para que quede listo en el inventario para futuras acciones
                            prodVendido.setCantidad(0);
                        }

                        // actualizamos ambos archivos binarios
                        ManejadorArchivos.guardarInventario(inventario);
                        ManejadorArchivos.guardarVentas(ventas);
                        System.out.println("-- Venta finalizada y base de datos guardada con exito. --");
                    } else {
                        System.out.println("-- Venta cancelada. El carrito de la caja esta vacio. --");
                    }
                    break;

                case 4:
                    System.out.println("\n--- LISTA DE VENTAS DESDE ARCHIVO ---");
                    
                    // validacion de existencia de registros
                    if (ventas.isEmpty()) {
                        System.out.println("No se han hecho ventas aun.");
                    } else {
                        // recorrido secuencial del ArrayList de ventas
                        for (int i = 0; i < ventas.size(); i++) {
                            // llamamos al metodo sobreescrito toString de la clase Venta
                            System.out.println(ventas.get(i).toString());
                        }
                    }
                    break;
                case 5:
                    System.out.println("\n=== ELIMINAR PRODUCTO DEL INVENTARIO ===");
                    System.out.print("Ingrese el ID del producto que desea borrar: ");
                    String idBorrar = scanner.nextLine().trim();

                    // bandera logica para controlar el exito de la operacion de busqueda
                    boolean encontradoParaBorrar = false;

                    // buscamos en el ArrayList 
                    String idBorrarMin = idBorrar.toLowerCase(); // lo estandarizamos antes del ciclo
                    for (int i = 0; i < inventario.size(); i++) {
                        String idGuardado = inventario.get(i).getId().toLowerCase(); // extraemos en minúsculas
                        if (idGuardado.equals(idBorrarMin)) { // comparamos con equals 
                            
                            // guardamos el nombre antes de borrarlo para avisarle al usuario
                            String nombreEliminado = inventario.get(i).getNombre();

                            // lo removemos del ArrayList en memoria RAM
                            inventario.remove(i); 

                            // sobrescribimos el archivo binario para actualizar el disco duro
                            ManejadorArchivos.guardarInventario(inventario); 

                            System.out.println("¡Exito! El producto '" + nombreEliminado + "' con ID [" + idBorrar + "] fue eliminado.");
                            encontradoParaBorrar = true;
                             // salimos del ciclo porque los IDs son únicos
                        }
                    }
                    // control en caso de que el id no coincida con ningun registro en el archivo binario
                    if (!encontradoParaBorrar) 
                        System.out.println("No se encontro ningun producto con el ID [" + idBorrar + "].");
                    break;
                case 6:
                    System.out.println("Saliendo del programa persistente...");
                    break;

                // manejo para entradas fuera del rango del menu de opciones
                default:
                    System.out.println("Opcion invalida.");
                }
            
            // condicion para cerrar el ciclo principal del menu
            } while (opcion != 6);
        }
}