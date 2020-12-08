package view;

import modelo.MarcaBean;
import modelo.MarcaDao;
import servicio.general.Cin;
import servicio.general.Validacion;

public class Marca {
    private final MarcaDao marcaDao = new MarcaDao();
    private MarcaBean marcaBean = new MarcaBean();
    private final Cin cin = new Cin();
    private final Validacion validacion = new Validacion();

    public void menu() {
        int opc;

        do {
            opc = cin.cInt("Menú de Marca\n" +
                    "1.- Añadir\n" +
                    "2.- Consultar\n" +
                    "3.- Modificar\n" +
                    "4.- Eliminar\n" +
                    "5.- Salir\n" +
                    "Ingrese una opción [1/2/3/4/5]: ");

            System.out.println("\n\n");

            switch (opc) {
                case 1:
                    switch (add()){
                        case 0:
                            System.out.println("Hubo un error al guardar, intente nuevamente");
                            break ;

                        case 1:
                            System.out.println("Marca registrada con éxito");
                            break ;

                        case 2:
                            System.out.println("Esta marca ya está registrada, intente nuevamente con un nombre distinto");
                            break ;
                    }
                    break;
                case 2:
                    switch (query()){
                        case 0:
                            System.out.println("Hubo un error al consultar, intente nuevamente");
                            break ;

                        case 2:
                            System.out.println("No hay marcas registradas");
                            break ;
                    }
                    break;
                case 3:
                    switch (update()) {
                        case 0:
                            System.out.println("No se ha actualizado la marca");
                            break;

                        case 1:
                            System.out.println("Marca actualizada con éxito");
                            break;

                        case 2:
                            System.out.println("La marca no existe");
                            break;
                    }
                    break;
                case 4:
                    switch (delete()){
                        case 0:
                            System.out.println("Hubo un error al eliminar, intente nuevamente");
                            break ;

                        case 1:
                            System.out.println("Marca eliminada con éxito");
                            break ;

                        case 2:
                            System.out.println("No hay marca con ese nombre");
                            break ;
                    }
                    break;
            }

            System.out.println("\n\n");
        } while (opc != 5);
    }

    private int add() {
        boolean c;
        String nombre, descripcion = null;
        int flag;
        System.out.println("Añadir Marca");
        do {
            System.out.print("Ingresar el nombre de la marca: ");
            nombre = cin.cLine();
            if (c = !validacion.onlyLetters(nombre))
                System.out.println("Error: Solo se admiten letras y espacios en el nombre, intentelo de nuevo\n");

        } while (c);

        System.out.println("\n\n");

        if (marcaDao.queryOne(nombre) != null) return 2;

        if (validacion.doYouWantRead("descripción")) {
            do {
                System.out.print("Ingrese la descripción de la categoría: ");
                descripcion = cin.cLine();

                if (c = validacion.haveCharNotAllowed(descripcion))
                    System.out.println("Error: Hay caracteres no permitidos, intentelo de nuevo\n");

            } while (c);
        }

        System.out.println("\n\n");

        marcaBean = new MarcaBean(nombre, descripcion);
        flag = marcaDao.add(marcaBean) ? 1 : 0;
        return flag;
    }

    protected int query() {
        System.out.println("Consultar Marcas");
        MarcaBean[] marcaBeans = marcaDao.query();

        if (marcaBeans == null) return 0;

        int n = marcaBeans.length;
        if (n == 0) return 2;

        for (MarcaBean c : marcaBeans)
            System.out.println("Id: " + c.getId() + " -- Nombre: " + c.getNombre() + " -- Descripción: " + validacion.nullMessage(c.getDescripcion()));

        System.out.println("\n\n");

        return 1;
    }

    private int delete() {
        String nombre;
        int opc;
        boolean c;
        System.out.println("Eliminar Marca");

        do {
            System.out.print("Ingrese el nombre de la Marca que desea eliminar: ");
            nombre = cin.cLine();

            if (c = !validacion.onlyLetters(nombre))
                System.out.println("Error: Solo se admiten letras y espacios en el nombre, intentelo de nuevo\n");

        } while (c);

        marcaBean = marcaDao.queryOne(nombre);

        if (marcaBean == null) return 2;

        do {
            opc = cin.cInt("Nombre: " + marcaBean.getNombre() + "\n" +
                    "Descripción: " + validacion.nullMessage(marcaBean.getDescripcion()) + "\n" +
                    "¿Desea eliminarlo?\n" +
                    "1.- Sí\n" +
                    "2.- No\n" +
                    "Ingrese una opción [1/2]: "
            );

            System.out.println("\n\n");

            if (c = opc < 1 || opc > 2)
                System.out.println("Error: Ingrese una opción valida, vuelva a intentarlo.");

        } while (c);

        if (opc == 1 && marcaDao.delete(marcaBean.getId())) return 1;

        if (opc == 2) {
            System.out.println("No se ha borrado la categoría");
            return -1;
        }

        return 0;
    }
    private int update(){
        String nombre, descripcion ;
        int opc ;
        boolean c = false ;
        System.out.println("Actualizar Categoría");

        System.out.print("Ingrese el nombre de la Categoría que desea actualizar: ");
        nombre = cin.cLine();

        marcaBean = marcaDao.queryOne(nombre);

        System.out.println("\n\n");

        if( marcaBean == null ) return 2 ;

        do{
            opc = cin.cInt("Nombre: " + marcaBean.getNombre() + "\n" +
                    "Descripción: " + marcaBean.getDescripcion() + "\n" +
                    "¿Que desea actualizar?\n" +
                    "1.- Nombre\n" +
                    "2.- Descripción\n" +
                    "3.- Guardar cambios\n" +
                    "4.- Cancelar\n" +
                    "Ingrese una opción [1/2/3/4]: ");

            System.out.println("\n\n");

            switch (opc){
                case 1:
                    do{
                        System.out.print("Ingrese el nombre de la categoría: ");
                        nombre = cin.cLine();

                        if( c = !validacion.onlyLetters(nombre) )
                            System.out.println("Error: Solo se admiten letras y espacios en el nombre, intentelo de nuevo\n");

                    }while (c);

                    marcaBean.setNombre(nombre);
                    break;

                case 2:
                    do{
                        System.out.print("Ingrese la descripción de la categoría: ");
                        descripcion = cin.cLine();

                        if( c = validacion.haveCharNotAllowed(descripcion) )
                            System.out.println("Error: Hay caracteres no permitidos, intentelo de nuevo\n");

                    }while (c);

                    marcaBean.setDescripcion(descripcion);
                    break;

                case 3:
                    if( marcaDao.update(marcaBean) ) return 1 ;
                    else return 0 ;

                case 4:
                    return 0 ;

                default:
                    System.out.println("Error: Ingrese una opción valida, vuelva a intentarlo.");
            }

            System.out.println("\n\n");
        }while (true);

    }

}
