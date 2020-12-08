package view;

import modelo.CategoriaBean;
import modelo.CategoriaDao;
import servicio.general.Cin;
import servicio.general.Validacion;

//0 Error interno o no se pudo consultar
//1 Se realizo la operación
//2 Ya existe (añadir y editar) o no hay (consultas y eliminar)

public class Categoria {

    private final CategoriaDao categoriaDao = new CategoriaDao();
    private final Cin cin = new Cin();
    private final Validacion validacion = new Validacion();
    private CategoriaBean categoriaBean ;

    public void menu(){
        int opc ;

        do{
            opc = cin.cInt("Menú de Categoría\n" +
                    "1.- Añadir\n" +
                    "2.- Consultar\n" +
                    "3.- Modificar\n" +
                    "4.- Eliminar\n" +
                    "5.- Salir\n" +
                    "Ingrese una opción [1/2/3/4/5]: ");

            System.out.println("\n\n");

            switch(opc){
                case 1:
                    switch (add()){
                        case 0:
                            System.out.println("Hubo un error al guardar, intente nuevamente");
                            break ;

                        case 1:
                            System.out.println("Categoría registrada con éxito");
                            break ;

                        case 2:
                            System.out.println("Esta categoría ya está registrada, intente nuevamente con un nombre distinto");
                            break ;
                    }
                    break ;
                case 2:
                    switch (query()){
                        case 0:
                            System.out.println("Hubo un error al consultar, intente nuevamente");
                            break ;

                        case 2:
                            System.out.println("No hay categorías registradas");
                            break ;
                    }
                    break ;

                case 3:
                    switch (update()){
                        case 0:
                            System.out.println("No se ha actualizado la categoría");
                            break ;

                        case 1:
                            System.out.println("Categoría actualizada con éxito");
                            break ;

                        case 2:
                            System.out.println("La categoría no existe");
                            break ;
                    }
                    break ;

                case 4:
                    switch (delete()){
                        case 0:
                            System.out.println("Hubo un error al eliminar, intente nuevamente");
                            break ;

                        case 1:
                            System.out.println("Categoría eliminada con éxito");
                            break ;

                        case 2:
                            System.out.println("No hay categoría con ese nombre");
                            break ;
                    }
                    break ;
            }
            System.out.println("\n\n");
        }while(opc != 5);

    }

    private int add(){
        boolean c ;
        String nombre, descripcion = null ;
        int flag ;

        System.out.println("Añadir Categoría");

        do{
            System.out.print("Ingrese el nombre de la categoría: ");
            nombre = cin.cLine();

            if( c = !validacion.onlyLetters(nombre) )
                System.out.println("Error: Solo se admiten letras y espacios en el nombre, intentelo de nuevo\n");

        }while (c);

        System.out.println("\n\n");

        if(categoriaDao.queryOne(nombre) != null) return 2 ;

        if(validacion.doYouWantRead("descripción")){

            do{
                System.out.print("Ingrese la descripción de la categoría: ");
                descripcion = cin.cLine();

                if( c = validacion.haveCharNotAllowed(descripcion) )
                    System.out.println("Error: Hay caracteres no permitidos, intentelo de nuevo\n");

            }while (c);

        }

        System.out.println("\n\n");

        categoriaBean = new CategoriaBean(nombre, descripcion);

        flag = categoriaDao.add(categoriaBean) ? 1 : 0 ;

        return flag;
    }

    protected int query(){
        System.out.println("Consultar Categorías");
        CategoriaBean[] categoriaBeans = categoriaDao.query();

        if( categoriaBeans == null ) return 0 ;

        int n = categoriaBeans.length;
        if( n == 0 ) return 2 ;

        for (CategoriaBean c: categoriaBeans)
            System.out.println("Id: " + c.getId() + " -- Nombre: " + c.getNombre() + " -- Descripción: " + validacion.nullMessage( c.getDescripcion() ));

        return 1 ;
    }

    private int delete(){
        String nombre ;
        int opc ;
        boolean c ;
        System.out.println("Eliminar Categoría");

        do{
            System.out.print("Ingrese el nombre de la Categoría que desea eliminar: ");
            nombre = cin.cLine();

            if( c = !validacion.onlyLetters(nombre) )
                System.out.println("Error: Solo se admiten letras y espacios en el nombre, intentelo de nuevo\n");

        }while (c);

        System.out.println("\n\n");

        categoriaBean = categoriaDao.queryOne(nombre);

        if( categoriaBean == null ) return 2 ;

        do{
            opc = cin.cInt("Nombre: " + categoriaBean.getNombre() + "\n" +
                    "Descripción: " + validacion.nullMessage(categoriaBean.getDescripcion()) + "\n" +
                    "¿Desea eliminarlo?\n" +
                    "1.- Sí\n" +
                    "2.- No\n" +
                    "Ingrese una opción [1/2]: "
            );

            if( c = opc < 1 || opc > 2 )
                System.out.println("Error: Ingrese una opción valida, vuelva a intentarlo.");

        }while (c);

        System.out.println("\n\n");

        if( opc == 1 && categoriaDao.delete(categoriaBean.getId())) return 1 ;

        if(opc == 2){
            System.out.println("No se ha borrado la categoría");
            return -1 ;
        }

        return 0 ;
    }

    private int update(){
        String nombre, descripcion ;
        int opc ;
        boolean c = false ;
        System.out.println("Actualizar Categoría");

        System.out.print("Ingrese el nombre de la Categoría que desea actualizar: ");
        nombre = cin.cLine();

        categoriaBean = categoriaDao.queryOne(nombre);

        System.out.println("\n\n");

        if( categoriaBean == null ) return 2 ;

        do{
            opc = cin.cInt("Nombre: " + categoriaBean.getNombre() + "\n" +
                    "Descripción: " + validacion.nullMessage(categoriaBean.getDescripcion()) + "\n" +
                    "¿Que desea actualizar?\n" +
                    "1.- Nombre\n" +
                    "2.- Descripción\n" +
                    "3.- Guardar cambios\n" +
                    "4.- Cancelar\n" +
                    "Ingrese una opción [1/2/3/4]: ");

            switch (opc){
                case 1:
                    do{
                        System.out.print("Ingrese el nombre de la categoría: ");
                        nombre = cin.cLine();

                        if( c = !validacion.onlyLetters(nombre) )
                            System.out.println("Error: Solo se admiten letras y espacios en el nombre, intentelo de nuevo\n");

                    }while (c);

                    categoriaBean.setNombre(nombre);
                    break;

                case 2:
                    do{
                        System.out.print("Ingrese la descripción de la categoría: ");
                        descripcion = cin.cLine();

                        if( c = validacion.haveCharNotAllowed(descripcion) )
                            System.out.println("Error: Hay caracteres no permitidos, intentelo de nuevo\n");

                    }while (c);

                    categoriaBean.setDescripcion(descripcion);
                    break;

                case 3:
                    System.out.println("\n\n");
                    if( categoriaDao.update(categoriaBean) ) return 1 ;
                    else return 0 ;

                case 4:
                    System.out.println("\n\n");
                    return 0 ;

                default:
                    System.out.println("Error: Ingrese una opción valida, vuelva a intentarlo.");
            }


        }while (true);

    }
}
