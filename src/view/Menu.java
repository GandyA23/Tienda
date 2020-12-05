package view;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import modelo.*;
import servicio.general.Validacion;

import java.util.Scanner;

public class Menu {
    Scanner leer = new Scanner(System.in);
    ProductoBean productoBean = new ProductoBean();
    ProductoDao productoDao = new ProductoDao();
    MarcaBean marcaBean = new MarcaBean();
    MarcaDao marcaDao = new MarcaDao();
    CategoriaBean categoriaBean = new CategoriaBean();
    CategoriaDao categoriaDao = new CategoriaDao();
    Validacion val = new Validacion();
    private int idMarca;
    private int menu;
    private int resp;
    private int stock;
    private int idCategoria;
    private double precioProducto;
    private String codProducto = null;
    private String nomProducto = null;
    private String descripProducto;
    private String nomCategoria = null;
    private String descripCategoria = null;
    private String usuario = null;
    private String contra = null;
    private String nomMarca = null;
    private String descripMarca = null;

    public void menu() {
        do {
            System.out.println("Ingresa tu usuario:");
            usuario = leer.next();
            System.out.println("Ingresa tu contraseña");
            contra = leer.next();

            //Recuerda poner la negacion si no esta mamada va a tronar !val.validarUserPass(usuario, contra)
            if (val.validarUserPass(usuario, contra)) {
                for (int i = 0; i < 10; i++) {
                    System.out.println("\n");
                }
                System.out.println("Usuario o contraseña incorrectos, favor de volver a intentarlo");
            } else {
                do {
                    System.out.println("Bienvenido");
                    System.out.println("¿A qué desea acceder?\n1.-Productos\n2.-Marcas\n3.-Categoría\n4.-Salir");
                    menu = leer.nextInt();
                    switch (menu) {
                        case 1:
                            producto();
                            break;
                        case 2:
                            //Marca
                            marca();
                            break;
                        case 3:
                            //Categorias
                            producto();
                            break;
                    }
                } while (menu != 4);
                System.out.println("\n\n\n\n\n\n");
            }
        } while (true);
    }

    public void producto() {
        do {
            System.out.println("¿Qué desea hacer?\n1.-Registrar Producto\n2.-Consultar Productos\n3.-Editar Producto\n4.-Eliminar Producto\n5.Salir");
            resp = leer.nextInt();
            switch (resp) {
                case 1:
                    //Registro
                    System.out.println("Ingrese el código del producto:");
                    codProducto = leer.next();
                    if (val.caracterEspecial(codProducto)) {
                        //Checar si el producto ya existe, si así enviar el mensaje de que ya existe
                        System.out.println("Error al registrar el producto:");
                    } else {
                        System.out.println("Ingrese el nombre del producto:");
                        nomProducto = leer.next();
                        if (val.caracterEspecial(nomProducto)) {
                            System.out.println("Error al registrar el producto:");
                        } else {
                            System.out.println("Categorías disponibles");
                            categoriaDao.query();
                            System.out.println("Ingrese la categoria del producto:");
                            nomCategoria = leer.next();
                            //Hacer consulta si existe la categoria se registra si no, manda mensaje diciendo que no existe
                            if (val.caracterEspecial(nomCategoria)) {
                                System.out.println("Error la categoría no existe:");
                            } else {
                                System.out.println("Marcas disponibles");
                                marcaDao.query();
                                System.out.println("Ingrese la marca del producto:");
                                nomMarca = leer.next();
                                //Hacer consulta si existe la marca se registra si no, manda mensaje diciendo que no existe
                                if (val.caracterEspecial(nomCategoria)) {
                                    System.out.println("Error, la marca no existe");
                                } else {
                                    try {
                                        System.out.println("Ingrese la existencia del producto:");
                                        stock = leer.nextInt();
                                        if (stock < 0) {
                                            System.out.println("Error, la existencia debe ser mayor a 0");
                                        } else {
                                            try {
                                                System.out.println("Ingrese el precio del producto:");
                                                precioProducto = leer.nextDouble();
                                                if (precioProducto < 0) {
                                                    System.out.println("Error, el precio debe ser mayor a 0");
                                                } else {
                                                    System.out.println("¿Desea agregar una descripción?\n1.-Si\n2.-No");
                                                    resp = leer.nextInt();
                                                    switch (resp) {
                                                        case 1:
                                                            System.out.println("Ingrese la descripción del producto:");
                                                            descripProducto = leer.next();
                                                            if (val.caracterEspecial(descripProducto)) {
                                                                System.out.println("Error al registrar el producto");
                                                            } else {
                                                                productoDao.add(productoBean);
                                                                System.out.println("Se registro con éxito el producto");
                                                            }
                                                            break;
                                                        case 2:
                                                            descripProducto = "";
                                                            productoDao.add(productoBean);
                                                            System.out.println("Se registro con éxito el producto");
                                                            break;
                                                        default:
                                                            System.out.println("Opción no válida");
                                                            break;
                                                    }
                                                }
                                            } catch (Exception e) {
                                                System.out.println("Error al resgistrar los productos");
                                            }
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Error al registrar el producto");
                                    }
                                }
                            }
                        }
                    }
                case 2:
                    //Consulta + opc de Eliminar y editar
                    do {
                        System.out.println("¿Desea hacer algo?\n1.-Editar Producto\n2.-Eliminar Producto\n3.Salir");
                        switch (resp) {
                            case 1:
                                do {
                                    System.out.println("¿Que campo desea editar?\n1.-Nombre\n2.-Categoría\n3.-Marca\n4.-Existencia\n5.-Precio\n.6-Descripción\7.-Salir");
                                    switch (resp) {
                                        case 1:
                                            System.out.println("Ingrese el código del producto:");
                                            codProducto = leer.next();
                                            if (val.caracterEspecial(codProducto)) {
                                                //Checar si el producto no existe, si así enviar el mensaje de que no existe
                                                System.out.println("Error el producto no existe:");
                                            } else {
                                                System.out.println("Ingrese el nombre del producto:");
                                                nomProducto = leer.next();
                                                if (val.caracterEspecial(nomProducto)) {
                                                    System.out.println("Error al registrar el producto:");
                                                } else {
                                                    productoDao.update(productoBean);
                                                }
                                            }
                                            break;
                                        case 2:
                                            System.out.println("Ingrese el código del producto:");
                                            codProducto = leer.next();
                                            if (val.caracterEspecial(codProducto)) {
                                                //Checar si el producto no existe, si así enviar el mensaje de que no existe
                                                System.out.println("Error el producto no existe:");
                                            } else {
                                                System.out.println("Categorías disponibles:");
                                                categoriaDao.query();
                                                System.out.println("Ingrese la categoría del producto:");
                                                nomCategoria = leer.next();
                                                if (val.caracterEspecial(nomCategoria)) {
                                                    System.out.println("Error al registrar el producto:");
                                                } else {
                                                    productoDao.update(productoBean);
                                                }
                                            }
                                            break;
                                        case 3:
                                            System.out.println("Ingrese el código del producto:");
                                            codProducto = leer.next();
                                            if (val.caracterEspecial(codProducto)) {
                                                //Checar si el producto no existe, si así enviar el mensaje de que no existe
                                                System.out.println("Error el producto no existe:");
                                            } else {
                                                System.out.println("Marcas disponibles:");
                                                marcaDao.query();
                                                System.out.println("Ingrese la marca del producto:");
                                                nomMarca = leer.next();
                                                if (val.caracterEspecial(nomMarca)) {
                                                    System.out.println("Error al registrar el producto:");
                                                } else {
                                                    productoDao.update(productoBean);
                                                }
                                            }
                                            break;
                                        case 4:
                                            System.out.println("Ingrese el código del producto:");
                                            codProducto = leer.next();
                                            if (val.caracterEspecial(codProducto)) {
                                                //Checar si el producto no existe, si así enviar el mensaje de que no existe
                                                System.out.println("Error el producto no existe:");
                                            } else {
                                                try {
                                                    System.out.println("Ingrese la existencia del producto:");
                                                    stock = leer.nextInt();
                                                    if (stock < 0) {
                                                        System.out.println("Error, la existencia debe ser mayor a 0");
                                                    } else {
                                                        productoDao.update(productoBean);
                                                    }
                                                }catch (Exception e){
                                                    System.out.println("Error al actualizar el producto");
                                                }
                                            }
                                            break;
                                        case 5:
                                            System.out.println("Ingrese el código del producto:");
                                            codProducto = leer.next();
                                            if (val.caracterEspecial(codProducto)) {
                                                //Checar si el producto no existe, si así enviar el mensaje de que no existe
                                                System.out.println("Error el producto no existe:");
                                            } else {
                                                try {
                                                    System.out.println("Ingrese el precio del producto:");
                                                    precioProducto = leer.nextInt();
                                                    if (precioProducto < 0) {
                                                        System.out.println("Error, el precio debe ser mayor a 0");
                                                    } else {
                                                        productoDao.update(productoBean);
                                                    }
                                                }catch (Exception e){
                                                    System.out.println("Error al actualizar el producto");
                                                }
                                            }
                                            break;
                                        case 6:
                                            System.out.println("Ingrese el código del producto:");
                                            codProducto = leer.next();
                                            if (val.caracterEspecial(codProducto)) {
                                                //Checar si el producto no existe, si así enviar el mensaje de que no existe
                                                System.out.println("Error el producto no existe:");
                                            } else {
                                                System.out.println("Ingrese la descripción del producto:");
                                                descripProducto = leer.next();
                                                if (val.caracterEspecial(descripProducto)) {
                                                    System.out.println("Error al actualizar el producto:");
                                                } else {
                                                    productoDao.update(productoBean);
                                                }
                                            }
                                            break;
                                        case 7:
                                            break;
                                        default:
                                            System.out.println("Opción no válida");
                                            break;
                                    }
                                } while (resp != 7);
                                break;
                            case 2:
                                System.out.println("Ingresa el código del producto:");
                                codProducto=leer.next();
                                if (val.caracterEspecial(codProducto)){
                                    System.out.println("Error al eliminar el producto");
                                }else{
                                    productoDao.delete(codProducto);
                                    //Enviar un mensaje si el producto no existe
                                }
                                break;
                            case 3:
                                //Salir
                                break;
                            default:
                                System.out.println("Opción no válida");
                                break;
                        }
                    } while (resp != 3);
                    break;
                case 3:
                    //Editar
                    do {
                        System.out.println("¿Que campo desea editar?\n1.-Nombre\n2.-Categoría\n3.-Marca\n4.-Existencia\n5.-Precio\n.6-Descripción\7.-Salir");
                        switch (resp) {
                            case 1:
                                System.out.println("Ingrese el código del producto:");
                                codProducto = leer.next();
                                if (val.caracterEspecial(codProducto)) {
                                    //Checar si el producto no existe, si así enviar el mensaje de que no existe
                                    System.out.println("Error el producto no existe:");
                                } else {
                                    System.out.println("Ingrese el nombre del producto:");
                                    nomProducto = leer.next();
                                    if (val.caracterEspecial(nomProducto)) {
                                        System.out.println("Error al registrar el producto:");
                                    } else {
                                        productoDao.update(productoBean);
                                    }
                                }
                                break;
                            case 2:
                                System.out.println("Ingrese el código del producto:");
                                codProducto = leer.next();
                                if (val.caracterEspecial(codProducto)) {
                                    //Checar si el producto no existe, si así enviar el mensaje de que no existe
                                    System.out.println("Error el producto no existe:");
                                } else {
                                    System.out.println("Categorías disponibles:");
                                    categoriaDao.query();
                                    System.out.println("Ingrese la categoría del producto:");
                                    nomCategoria = leer.next();
                                    if (val.caracterEspecial(nomCategoria)) {
                                        System.out.println("Error al registrar el producto:");
                                    } else {
                                        productoDao.update(productoBean);
                                    }
                                }
                                break;
                            case 3:
                                System.out.println("Ingrese el código del producto:");
                                codProducto = leer.next();
                                if (val.caracterEspecial(codProducto)) {
                                    //Checar si el producto no existe, si así enviar el mensaje de que no existe
                                    System.out.println("Error el producto no existe:");
                                } else {
                                    System.out.println("Marcas disponibles:");
                                    marcaDao.query();
                                    System.out.println("Ingrese la marca del producto:");
                                    nomMarca = leer.next();
                                    if (val.caracterEspecial(nomMarca)) {
                                        System.out.println("Error al registrar el producto:");
                                    } else {
                                        productoDao.update(productoBean);
                                    }
                                }
                                break;
                            case 4:
                                System.out.println("Ingrese el código del producto:");
                                codProducto = leer.next();
                                if (val.caracterEspecial(codProducto)) {
                                    //Checar si el producto no existe, si así enviar el mensaje de que no existe
                                    System.out.println("Error el producto no existe:");
                                } else {
                                    try {
                                        System.out.println("Ingrese la existencia del producto:");
                                        stock = leer.nextInt();
                                        if (stock < 0) {
                                            System.out.println("Error, la existencia debe ser mayor a 0");
                                        } else {
                                            productoDao.update(productoBean);
                                        }
                                    }catch (Exception e){
                                        System.out.println("Error al actualizar el producto");
                                    }
                                }
                                break;
                            case 5:
                                System.out.println("Ingrese el código del producto:");
                                codProducto = leer.next();
                                if (val.caracterEspecial(codProducto)) {
                                    //Checar si el producto no existe, si así enviar el mensaje de que no existe
                                    System.out.println("Error el producto no existe:");
                                } else {
                                    try {
                                        System.out.println("Ingrese el precio del producto:");
                                        precioProducto = leer.nextInt();
                                        if (precioProducto < 0) {
                                            System.out.println("Error, el precio debe ser mayor a 0");
                                        } else {
                                            productoDao.update(productoBean);
                                        }
                                    }catch (Exception e){
                                        System.out.println("Error al actualizar el producto");
                                    }
                                }
                                break;
                            case 6:
                                System.out.println("Ingrese el código del producto:");
                                codProducto = leer.next();
                                if (val.caracterEspecial(codProducto)) {
                                    //Checar si el producto no existe, si así enviar el mensaje de que no existe
                                    System.out.println("Error el producto no existe:");
                                } else {
                                    System.out.println("Ingrese la descripción del producto:");
                                    descripProducto = leer.next();
                                    if (val.caracterEspecial(descripProducto)) {
                                        System.out.println("Error al actualizar el producto:");
                                    } else {
                                        productoDao.update(productoBean);
                                    }
                                }
                                break;
                            case 7:
                                break;
                            default:
                                System.out.println("Opción no válida");
                                break;
                        }
                    } while (resp != 7);
                    break;
                case 4:
                    //Eliminar
                    System.out.println("Ingresa el código del producto:");
                    codProducto=leer.next();
                    if (val.caracterEspecial(codProducto)){
                        System.out.println("Error al eliminar el producto");
                    }else{
                        productoDao.delete(codProducto);
                        //Enviar un mensaje si el producto no existe
                    }
                    break;
                case 5:
                    //Salir
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        } while (resp != 5);
    }
    public  void marca(){
        do {
            System.out.println("¿Qué desea hacer?\n1.-Registrar Marca\n2.-Consultar Marca\n3.-Editar Marca\n4.-Eliminar Marca\n5.Salir");
            resp = leer.nextInt();
            switch (resp) {
                case 1:
                    //Registrar Marca
                    try {
                        System.out.println("Ingrese el id de la marca");
                        idMarca = leer.nextInt();
                        System.out.println("Ingrese el nombre de la marca");
                        nomMarca = leer.next();
                        if (val.caracterEspecial(nomMarca)) {
                            System.out.println("Error, favor de no ingresar caracteres especiales");
                        } else {
                            System.out.println("Ingrese la descripcion de la marca:");
                            descripMarca = leer.next();
                            if (val.caracterEspecial(descripMarca)) {
                                System.out.println("Error, favor de no ingresar caracteres especiales");
                            } else {
                                marcaDao.add(marcaBean);
                                System.out.println("Se ha añadido con exito");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Ingrese solo números enteros");
                    }
                    break;
                case 2:
                    //Consultar Marcas
                    System.out.println("Lista Categorias");
                    marcaDao.query();
                    try {
                        do {
                            System.out.println("¿Qué desea hacer?\n1.Editar\n2.-Eliminar\n3.-Salir");
                            resp = leer.nextInt();
                            switch (resp) {
                                case 1:
                                    System.out.println("¿Qué campo deseas editar?\n1.-Nombre Marca\n2.-Descripción Marca\n3.-Salir");
                                    resp = leer.nextInt();
                                    switch (resp) {
                                        case 1:
                                            //Nombre Marca
                                            try {
                                                System.out.println("Ingresa el id de la marca:");
                                                idMarca = leer.nextInt();

                                                System.out.println("Ingresa el nombre de la marca");
                                                nomCategoria = leer.next();
                                                do {
                                                    System.out.println("¿Estás seguro de editar el nombre?\n1.-Si\n2.-No\n3.-Salir");
                                                    resp = leer.nextInt();
                                                    switch (resp) {
                                                        case 1:
                                                            try {
                                                                marcaDao.update(marcaBean);
                                                                System.out.println("Se ha actualizado con éxito");
                                                            } catch (Exception e) {
                                                                System.out.println("No se ha podido actualizar");
                                                            }
                                                            break;
                                                        case 2:
                                                            System.out.println("Deshaciendo cambios");
                                                            break;
                                                        case 3:
                                                            break;
                                                        default:
                                                            System.out.println("Opción no válida");
                                                            break;
                                                    }
                                                } while (resp != 3);
                                            } catch (Exception e) {
                                                System.out.println("Ingrese solo números enteros");
                                            }
                                            break;
                                        case 2:
                                            //Editar Marca
                                            try {
                                                System.out.println("Ingresa el id de la categoría:");
                                                idMarca = leer.nextInt();

                                                System.out.println("Ingresa el descripción de la categoría");
                                                descripMarca = leer.next();
                                                if (val.caracterEspecial(descripMarca)) {

                                                } else {
                                                    do {
                                                        System.out.println("¿Estás seguro de editar la descripción?\n1.-Si\n2.-No\n3.-Salir");
                                                        resp = leer.nextInt();
                                                        switch (resp) {
                                                            case 1:
                                                                try {
                                                                    marcaDao.update(marcaBean);
                                                                    System.out.println("Se ha actualizado con éxito");
                                                                } catch (Exception e) {
                                                                    System.out.println("No se ha podido actualizar");
                                                                }
                                                                break;
                                                            case 2:
                                                                System.out.println("Deshaciendo cambios");
                                                                break;
                                                            case 3:
                                                                break;
                                                            default:
                                                                System.out.println("Opción no válida");
                                                                break;
                                                        }
                                                    } while (resp != 3);
                                                }

                                            } catch (Exception e) {
                                                System.out.println("Ingrese solo números enteros");
                                            }
                                            break;
                                        case 3:
                                            break;
                                        default:
                                            System.out.println("Opción no válida");
                                            break;
                                    }
                                    break;
                                case 2:
                                    //Eliminar Marca
                                    try {
                                        System.out.println("Ingresa el id de la marca:");
                                        idMarca = leer.nextInt();
                                        marcaDao.delete(idMarca);
                                        System.out.println("Se borro correctamente");
                                    } catch (Exception e) {
                                        System.out.println("Ingrese solo números enteros");
                                    }
                                    break;
                                case 3:
                                    break;
                                default:
                                    System.out.println("Opción no válida");
                                    break;
                            }
                        } while (resp != 3);
                    } catch (Exception e) {
                        System.out.println("Ingrese solo números enteros");
                    }
                    break;
                case 3:
                    //Editar Marca
                    System.out.println("¿Qué campo deseas editar?\n1.-Nombre Categoría\n2.-Descripción Categoría\n3.-Salir");
                    resp = leer.nextInt();
                    switch (resp) {
                        case 1:
                            //Nombre
                            try {
                                System.out.println("Ingresa el id de la marca:");
                                idMarca = leer.nextInt();

                                System.out.println("Ingresa el nombre de la categoría");
                                nomMarca = leer.next();
                                do {
                                    System.out.println("¿Estás seguro de editar el nombre?\n1.-Si\n2.-No\n3.-Salir");
                                    resp = leer.nextInt();
                                    switch (resp) {
                                        case 1:
                                            try {
                                                marcaDao.update(marcaBean);
                                                System.out.println("Se ha actualizado con éxito");
                                            } catch (Exception e) {
                                                System.out.println("No se ha podido actualizar");
                                            }
                                            break;
                                        case 2:
                                            System.out.println("Deshaciendo cambios");
                                            break;
                                        case 3:
                                            break;
                                        default:
                                            System.out.println("Opción no válida");
                                            break;
                                    }
                                } while (resp != 3);
                            } catch (Exception e) {
                                System.out.println("Ingrese solo números enteros");
                            }
                            break;
                        case 2:
                            //Descripcion Categoria
                            try {
                                System.out.println("Ingresa el id de la marca:");
                                idMarca = leer.nextInt();

                                System.out.println("Ingresa el descripción de la categoría");
                                descripMarca = leer.next();
                                if (val.caracterEspecial(descripMarca)) {

                                } else {
                                    do {
                                        System.out.println("¿Estás seguro de editar la descripción?\n1.-Si\n2.-No\n3.-Salir");
                                        resp = leer.nextInt();
                                        switch (resp) {
                                            case 1:
                                                try {
                                                    marcaDao.update(marcaBean);
                                                    System.out.println("Se ha actualizado con éxito");
                                                } catch (Exception e) {
                                                    System.out.println("No se ha podido actualizar");
                                                }
                                                break;
                                            case 2:
                                                System.out.println("Deshaciendo cambios");
                                                break;
                                            case 3:
                                                break;
                                            default:
                                                System.out.println("Opción no válida");
                                                break;
                                        }
                                    } while (resp != 3);
                                }

                            } catch (Exception e) {
                                System.out.println("Ingrese solo números enteros");
                            }
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println("Opción no válida");
                            break;
                    }
                    break;
                case 4:
                    //Eliminar Categoria
                    try {
                        System.out.println("Ingresa el id de la marca:");
                        idMarca = leer.nextInt();
                        marcaDao.delete(idMarca);
                        System.out.println("Se borro correctamente");
                    } catch (Exception e) {
                        System.out.println("Ingrese solo números enteros");
                    }
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        } while (resp!=5);
    }
    public  void categoria() {
        do {
            System.out.println("¿Qué desea hacer?\n1.-Registrar Categoria\n2.-Consultar Categorias\n3.-Editar Categoria\n4.-Eliminar Categoria\n5.Salir");
            resp = leer.nextInt();
            switch (resp) {
                case 1:
                    //Registrar Categoria
                    try {
                        System.out.println("Ingrese el id de la categoria");
                        idCategoria = leer.nextInt();
                        System.out.println("Ingrese el nombre de la categoria");
                        nomCategoria = leer.next();
                        if (val.caracterEspecial(nomCategoria)) {
                            System.out.println("Error, favor de no ingresar caracteres especiales");
                        } else {
                            System.out.println("Ingrese la descripcion del producto:");
                            descripProducto = leer.next();
                            if (val.caracterEspecial(descripProducto)) {
                                System.out.println("Error, favor de no ingresar caracteres especiales");
                            } else {
                                categoriaDao.add(categoriaBean);
                                System.out.println("Se ha añadido con exito");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Ingrese solo números enteros");
                    }
                    break;
                case 2:
                    //Consultar Categorias
                    System.out.println("Lista Categorias");
                    categoriaDao.query();
                    try {
                        do {
                            System.out.println("¿Qué desea hacer?\n1.Editar\n2.-Eliminar\n3.-Salir");
                            resp = leer.nextInt();
                            switch (resp) {
                                case 1:
                                    System.out.println("¿Qué campo deseas editar?\n1.-Nombre Categoría\n2.-Descripción Categoría\n3.-Salir");
                                    resp = leer.nextInt();
                                    switch (resp) {
                                        case 1:
                                            //Nombre
                                            try {
                                                System.out.println("Ingresa el id de la categoría:");
                                                idCategoria = leer.nextInt();

                                                System.out.println("Ingresa el nombre de la categoría");
                                                nomCategoria = leer.next();
                                                do {
                                                    System.out.println("¿Estás seguro de editar el nombre?\n1.-Si\n2.-No\n3.-Salir");
                                                    resp = leer.nextInt();
                                                    switch (resp) {
                                                        case 1:
                                                            try {
                                                                categoriaDao.update(categoriaBean);
                                                                System.out.println("Se ha actualizado con éxito");
                                                            } catch (Exception e) {
                                                                System.out.println("No se ha podido actualizar");
                                                            }
                                                            break;
                                                        case 2:
                                                            System.out.println("Deshaciendo cambios");
                                                            break;
                                                        case 3:
                                                            break;
                                                        default:
                                                            System.out.println("Opción no válida");
                                                            break;
                                                    }
                                                } while (resp != 3);
                                            } catch (Exception e) {
                                                System.out.println("Ingrese solo números enteros");
                                            }
                                            break;
                                        case 2:
                                            //Editar Categoria
                                            try {
                                                System.out.println("Ingresa el id de la categoría:");
                                                idCategoria = leer.nextInt();

                                                System.out.println("Ingresa el descripción de la categoría");
                                                descripCategoria = leer.next();
                                                if (val.caracterEspecial(descripCategoria)) {

                                                } else {
                                                    do {
                                                        System.out.println("¿Estás seguro de editar la descripción?\n1.-Si\n2.-No\n3.-Salir");
                                                        resp = leer.nextInt();
                                                        switch (resp) {
                                                            case 1:
                                                                try {
                                                                    categoriaDao.update(categoriaBean);
                                                                    System.out.println("Se ha actualizado con éxito");
                                                                } catch (Exception e) {
                                                                    System.out.println("No se ha podido actualizar");
                                                                }
                                                                break;
                                                            case 2:
                                                                System.out.println("Deshaciendo cambios");
                                                                break;
                                                            case 3:
                                                                break;
                                                            default:
                                                                System.out.println("Opción no válida");
                                                                break;
                                                        }
                                                    } while (resp != 3);
                                                }

                                            } catch (Exception e) {
                                                System.out.println("Ingrese solo números enteros");
                                            }
                                            break;
                                        case 3:
                                            break;
                                        default:
                                            System.out.println("Opción no válida");
                                            break;
                                    }
                                    break;
                                case 2:
                                    //Eliminar Categoria
                                    try {
                                        System.out.println("Ingresa el id de la categoría:");
                                        idCategoria = leer.nextInt();
                                        categoriaDao.delete(idCategoria);
                                        System.out.println("Se borro correctamente");
                                    } catch (Exception e) {
                                        System.out.println("Ingrese solo números enteros");
                                    }
                                    break;
                                case 3:
                                    break;
                                default:
                                    System.out.println("Opción no válida");
                                    break;
                            }
                        } while (resp != 3);
                    } catch (Exception e) {
                        System.out.println("Ingrese solo números enteros");
                    }
                    break;
                case 3:
                    //Editar Categoria
                    System.out.println("¿Qué campo deseas editar?\n1.-Nombre Categoría\n2.-Descripción Categoría\n3.-Salir");
                    resp = leer.nextInt();
                    switch (resp) {
                        case 1:
                            //Nombre
                            try {
                                System.out.println("Ingresa el id de la categoría:");
                                idCategoria = leer.nextInt();

                                System.out.println("Ingresa el nombre de la categoría");
                                nomCategoria = leer.next();
                                do {
                                    System.out.println("¿Estás seguro de editar el nombre?\n1.-Si\n2.-No\n3.-Salir");
                                    resp = leer.nextInt();
                                    switch (resp) {
                                        case 1:
                                            try {
                                                categoriaDao.update(categoriaBean);
                                                System.out.println("Se ha actualizado con éxito");
                                            } catch (Exception e) {
                                                System.out.println("No se ha podido actualizar");
                                            }
                                            break;
                                        case 2:
                                            System.out.println("Deshaciendo cambios");
                                            break;
                                        case 3:
                                            break;
                                        default:
                                            System.out.println("Opción no válida");
                                            break;
                                    }
                                } while (resp != 3);
                            } catch (Exception e) {
                                System.out.println("Ingrese solo números enteros");
                            }
                            break;
                        case 2:
                            //Descripcion Categoria
                            try {
                                System.out.println("Ingresa el id de la categoría:");
                                idCategoria = leer.nextInt();

                                System.out.println("Ingresa el descripción de la categoría");
                                descripCategoria = leer.next();
                                if (val.caracterEspecial(descripCategoria)) {

                                } else {
                                    do {
                                        System.out.println("¿Estás seguro de editar la descripción?\n1.-Si\n2.-No\n3.-Salir");
                                        resp = leer.nextInt();
                                        switch (resp) {
                                            case 1:
                                                try {
                                                    categoriaDao.update(categoriaBean);
                                                    System.out.println("Se ha actualizado con éxito");
                                                } catch (Exception e) {
                                                    System.out.println("No se ha podido actualizar");
                                                }
                                                break;
                                            case 2:
                                                System.out.println("Deshaciendo cambios");
                                                break;
                                            case 3:
                                                break;
                                            default:
                                                System.out.println("Opción no válida");
                                                break;
                                        }
                                    } while (resp != 3);
                                }

                            } catch (Exception e) {
                                System.out.println("Ingrese solo números enteros");
                            }
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println("Opción no válida");
                            break;
                    }
                    break;
                case 4:
                    //Eliminar Categoria
                    try {
                        System.out.println("Ingresa el id de la categoría:");
                        idCategoria = leer.nextInt();
                        categoriaDao.delete(idCategoria);
                        System.out.println("Se borro correctamente");
                    } catch (Exception e) {
                        System.out.println("Ingrese solo números enteros");
                    }
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        } while (resp!=5) ;

    }
}