package view;

import modelo.*;
import servicio.general.Cin;
import servicio.general.Validacion;

public class Producto {
    private final ProductoDao productoDao = new ProductoDao();
    private final CategoriaDao categoriaDao = new CategoriaDao();
    private final MarcaDao marcaDao = new MarcaDao();
    private final Cin cin = new Cin();
    private final Validacion validacion = new Validacion();

    private ProductoBean productoBean ;
    private MarcaBean marcaBean ;
    private CategoriaBean categoriaBean ;

    private CategoriaBean[] categoriaBeans ;
    private MarcaBean[] marcaBeans ;

    public void menu() {
        int opc;

        do {
            opc = cin.cInt("Menú de Productos\n" +
                    "1.- Añadir\n" +
                    "2.- Consultar\n" +
                    "3.- Modificar\n" +
                    "4.- Eliminar\n" +
                    "5.- Salir\n" +
                    "Ingrese una opción [1/2/3/4/5]: ");

            System.out.println("\n\n");

            switch (opc) {
                case 1:
                    switch (add()) {
                        case 0:
                            System.out.println("Hubo un error al guardar, intente nuevamente");
                            break;

                        case 1:
                            System.out.println("Producto registrado con éxito");
                            break;

                        case 2:
                            System.out.println("Este producto ya está registrada, intente nuevamente con un código distinto");
                            break;

                        case 3:
                            System.out.println("Error: Fallo al consultar las categorías");
                            break;

                        case 4:
                            System.out.println("No hay categorias guardadas, no es posible registrar un producto sin categorias");
                            break;

                        case 5:
                            System.out.println("Error: Fallo al consultar las marcas");
                            break;

                        case 6:
                            System.out.println("No hay marcas guardadas, no es posible registrar un producto sin marcas");
                            break;
                    }
                    break;
                case 2:
                    switch (query()) {
                        case 0:
                            System.out.println("Hubo un error al consultar, intente nuevamente");
                            break;

                        case 2:
                            System.out.println("No hay productos registrados");
                            break;
                    }
                    break;

                case 3:
                    switch (update()) {
                        case 0:
                            System.out.println("No se ha actualizado el producto");
                            break;

                        case 1:
                            System.out.println("Producto actualizado con éxito");
                            break;

                        case 2:
                            System.out.println("El producto no existe");
                            break;
                    }
                    break;

                case 4:
                    switch (delete()) {
                        case 0:
                            System.out.println("Hubo un error al eliminar, intente nuevamente");
                            break;

                        case 1:
                            System.out.println("Producto eliminado con éxito");
                            break;

                        case 2:
                            System.out.println("No hay producto con ese código");
                            break;
                    }
                    break;
            }

            System.out.println("\n\n");

        } while (opc != 5);
    }

    private int add(){
        boolean c ;
        String codigo, nombre, descripcion = null, categoria, marca ;
        int flag, existencia ;
        double precio ;

        System.out.println("Añadir Producto");


        categoriaBeans = categoriaDao.query() ;
        marcaBeans = marcaDao.query();
        if( categoriaBeans == null ) return 3 ;         //Fallo al consultar las categorias
        if( categoriaBeans.length == 0 ) return 4 ;     //No hay categorias en ls DB
        if( marcaBeans == null ) return 5 ;             //Fallo al consultar las marcas
        if( marcaBeans.length == 0 ) return 6 ;         //No hay marcas en la DB

        do{
            System.out.print("Ingrese el código del producto: ");
            codigo = cin.cString();

            if( c = !validacion.onlyLettersWithoutSpaces(codigo) )
                System.out.println("Error: Solo se admiten letras en el código, intentelo de nuevo");
            else if(productoDao.queryOne(codigo) != null) return 2 ;    //Ya existe el producto con ese código

        }while (c);

        System.out.println("\n\n");

        do{
            System.out.print("Ingrese el nombre del producto: ");
            nombre = cin.cLine();

            if( c = !validacion.onlyLetters(nombre) )
                System.out.println("Error: Solo se admiten letras y espacios en el nombre, intentelo de nuevo\n");

        }while (c);

        System.out.println("\n\n");

        do{
            printCategorias(categoriaBeans);
            System.out.print("Ingrese el nombre de la categoría del Producto: ");
            categoria = cin.cLine();

            if( c = !validacion.onlyLetters(categoria) )
                System.out.println("Error: Solo se admiten letras y espacios en el nombre, intentelo de nuevo\n");
            else {
                categoriaBean = categoriaDao.queryOne(categoria) ;

                if( c = categoriaBean == null )
                    System.out.println("Error: Esa Categoría no existe, intentelo de nuevo\n");
            }
        }while (c);

        System.out.println("\n\n");

        do{
            printMarcas(marcaBeans);
            System.out.print("Ingrese el nombre de la marca del Producto: ");
            marca = cin.cLine();

            if( c = !validacion.onlyLetters(marca) )
                System.out.println("Error: Solo se admiten letras y espacios en el nombre, intentelo de nuevo\n");
            else {
                marcaBean = marcaDao.queryOne(marca) ;

                if( c = marcaBean == null )
                    System.out.println("Error: Esa Marca no existe, intentelo de nuevo\n");
            }
        }while (c);

        System.out.println("\n\n");

        do{
            existencia = cin.cInt("Ingrese la existencia del producto: ");

            if( c = existencia <= 0 )
                System.out.println("Error: La existencia debe de ser mayor a cero, intentelo de nuevo");

        }while (c);

        System.out.println("\n\n");

        do{
            precio = cin.cDouble("Ingrese el precio del producto: ");

            if( c = precio <= 0D )
                System.out.println("Error: El precio debe de ser mayor a cero, intentelo de nuevo");

        }while (c);

        System.out.println("\n\n");

        if(validacion.doYouWantRead("descripción")){

            do{
                System.out.print("Ingrese la descripción de la categoría: ");
                descripcion = cin.cLine();

                if( c = validacion.haveCharNotAllowed(descripcion) )
                    System.out.println("Error: Hay caracteres no permitidos, intentelo de nuevo\n");

            }while (c);

        }

        System.out.println("\n\n");

        productoBean = new ProductoBean(codigo, nombre, categoriaBean, marcaBean, existencia, precio, descripcion);
        flag = productoDao.add(productoBean) ? 1 : 0 ;

        return flag;
    }

    private int query(){
        System.out.println("Consultar Productos");
        ProductoBean[] productoBeans = productoDao.query();

        if( productoBeans == null ) return 0 ;

        int n = productoBeans.length;
        if( n == 0 ) return 2 ;

        for (ProductoBean p: productoBeans){

            categoriaBean = categoriaDao.queryOne( p.getCategoria().getId() );
            marcaBean = marcaDao.queryOne( p.getMarca().getId() );

            System.out.println("Código: " + p.getCodigo());
            System.out.println("Nombre: " + p.getNombre());
            System.out.println("Categoría: " + categoriaBean.getNombre());
            System.out.println("Marca: " + marcaBean.getNombre());
            System.out.println("Existencia: " + p.getExistencia());
            System.out.println("Precio: " + p.getPrecio());
            System.out.println("Descripción: " + validacion.nullMessage(p.getDescripcion()) + "\n");
        }

        return 1 ;
    }

    private int delete(){
        String codigo ;
        int opc ;
        boolean c ;
        System.out.println("Eliminar Producto");

        do{
            System.out.print("Ingrese el codigo del Producto que desea eliminar: ");
            codigo = cin.cString();

            if( c = !validacion.onlyLettersWithoutSpaces(codigo) )
                System.out.println("Error: Solo se admiten letras en el código, intentelo de nuevo");

        }while (c);

        System.out.println("\n\n");

        productoBean = productoDao.queryOne(codigo);

        if( productoBean == null ) return 2 ;

        categoriaBean = categoriaDao.queryOne( productoBean.getCategoria().getId() );
        marcaBean = marcaDao.queryOne( productoBean.getMarca().getId() );

        do{
            opc = cin.cInt("Código: " + productoBean.getCodigo() + "\n" +
                    "Nombre: " + productoBean.getNombre() + "\n" +
                    "Categoría: " + categoriaBean.getNombre() + "\n" +
                    "Marca: " + marcaBean.getNombre() + "\n" +
                    "Existencia: " + productoBean.getExistencia() + "\n" +
                    "Precio: " + productoBean.getPrecio() + "\n" +
                    "Descripción: " + validacion.nullMessage(productoBean.getDescripcion()) + "\n" +
                    "¿Desea eliminarlo?\n" +
                    "1.- Sí\n" +
                    "2.- No\n" +
                    "Ingrese una opción [1/2]: "
            );

            if( c = opc < 1 || opc > 2 )
                System.out.println("Error: Ingrese una opción valida, vuelva a intentarlo.");

            System.out.println("\n\n");

        }while (c);

        if( opc == 1 && productoDao.delete(productoBean.getCodigo())) return 1 ;

        if(opc == 2){
            System.out.println("No se ha borrado el producto");
            return -1 ;
        }

        return 0 ;
    }

    private int update(){
        boolean c ;
        String codigo, nombre, descripcion = null, categoria, marca ;
        int opc, existencia ;
        double precio ;
        System.out.println("Actualizar Producto");

        System.out.print("Ingrese el código del Producto que desea actualizar: ");
        codigo = cin.cString();

        productoBean = productoDao.queryOne(codigo);

        if( productoBean == null ) return 2 ;
        categoriaBean = categoriaDao.queryOne( productoBean.getCategoria().getId() );
        marcaBean = marcaDao.queryOne( productoBean.getMarca().getId() );

        categoriaBeans = categoriaDao.query() ;
        marcaBeans = marcaDao.query();
        do{
            opc = cin.cInt("Código: " + productoBean.getCodigo() + "\n" +
                    "Nombre: " + productoBean.getNombre() + "\n" +
                    "Categoría: " + categoriaBean.getNombre() + "\n" +
                    "Marca: " + marcaBean.getNombre() + "\n" +
                    "Existencia: " + productoBean.getExistencia() + "\n" +
                    "Precio: " + productoBean.getPrecio() + "\n" +
                    "Descripción: " + validacion.nullMessage(productoBean.getDescripcion()) + "\n" +
                    "¿Que desea actualizar?\n" +
                    "1.- Nombre\n" +
                    "2.- Categoria\n" +
                    "3.- Marca\n" +
                    "4.- Existencia\n" +
                    "5.- Precio\n" +
                    "6.- Descripción\n" +
                    "7.- Guardar cambios\n" +
                    "8.- Cancelar\n" +
                    "Ingrese una opción [1/2/3/4/5/6/7/8]: ");

            System.out.println("\n\n");

            switch (opc){
                case 1:
                    do{
                        System.out.print("Ingrese el nombre del producto: ");
                        nombre = cin.cLine();

                        if( c = !validacion.onlyLetters(nombre) )
                            System.out.println("Error: Solo se admiten letras y espacios en el nombre, intentelo de nuevo\n");

                    }while (c);

                    productoBean.setNombre(nombre);
                    break;

                case 2:
                    do{
                        printCategorias(categoriaBeans);
                        System.out.print("Ingrese el nombre de la categoría del Producto: ");
                        categoria = cin.cLine();

                        if( c = !validacion.onlyLetters(categoria) )
                            System.out.println("Error: Solo se admiten letras y espacios en el nombre, intentelo de nuevo\n");
                        else {
                            categoriaBean = categoriaDao.queryOne(categoria) ;

                            if( c = categoriaBean == null )
                                System.out.println("Error: Esa Categoría no existe, intentelo de nuevo\n");
                        }
                    }while (c);

                    categoriaBean.setNombre(categoria);
                    productoBean.setCategoria(categoriaBean);
                    break;

                case 3:
                    do{
                        printMarcas(marcaBeans);
                        System.out.print("Ingrese el nombre de la marca del Producto: ");
                        marca = cin.cLine();

                        if( c = !validacion.onlyLetters(marca) )
                            System.out.println("Error: Solo se admiten letras y espacios en el nombre, intentelo de nuevo\n");
                        else {
                            marcaBean = marcaDao.queryOne(marca) ;

                            if( c = marcaBean == null )
                                System.out.println("Error: Esa Marca no existe, intentelo de nuevo\n");
                        }
                    }while (c);

                    marcaBean.setNombre(marca);
                    productoBean.setMarca(marcaBean);
                    break;

                case 4:
                    do{
                        existencia = cin.cInt("Ingrese la existencia del producto: ");

                        if( c = existencia <= 0 )
                            System.out.println("Error: La existencia debe de ser mayor a cero, intentelo de nuevo");

                    }while (c);

                    productoBean.setExistencia(existencia);
                    break;

                case 5:
                    do{
                        precio = cin.cDouble("Ingrese el precio del producto: ");

                        if( c = precio <= 0D )
                            System.out.println("Error: El precio debe de ser mayor a cero, intentelo de nuevo");

                    }while (c);

                    productoBean.setPrecio(precio);
                    break;

                case 6:
                    do{
                        System.out.print("Ingrese la descripción de la categoría: ");
                        descripcion = cin.cLine();

                        if( c = validacion.haveCharNotAllowed(descripcion) )
                            System.out.println("Error: Hay caracteres no permitidos, intentelo de nuevo\n");

                    }while (c);
                    productoBean.setDescripcion(descripcion);
                    break ;

                case 7:
                    if( productoDao.update(productoBean) ) return 1 ;
                    else return 0 ;

                case 8:
                    return 0 ;

                default:
                    System.out.println("Error: Ingrese una opción valida, vuelva a intentarlo.");
            }

            System.out.println("\n\n");
        }while (true);
    }

    private void printCategorias(CategoriaBean[] categoriaBeans){
        for (CategoriaBean c: categoriaBeans)
            System.out.println("Id: " + c.getId() + " -- Nombre: " + c.getNombre() + " -- Descripción: " + validacion.nullMessage( c.getDescripcion() ));
    }

    private void printMarcas(MarcaBean[] marcaBeans){
        for (MarcaBean m: marcaBeans)
            System.out.println("Id: " + m.getId() + " -- Nombre: " + m.getNombre() + " -- Descripción: " + validacion.nullMessage( m.getDescripcion() ));
    }

}
