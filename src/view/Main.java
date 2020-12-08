package view;

import servicio.general.Cin;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Categoria categoria = new Categoria();
        Login login = new Login();
        Marca marca = new Marca();
        Producto producto = new Producto();
        Cin cin = new Cin();
        int opc ;

        do{
            if( login.login() ){

                do{
                    System.out.println("HOLA ADMINISTRADOR!!");
                    opc = cin.cInt("¿Qué desea administrar?\n1.- Categorías\n2.- Marcas\n3.- Productos\n4.- Cerrar Sesión\nIngrese una opción[1/2/3/4]: ");

                    System.out.println("\n\n");
                    switch (opc){
                        case 1:
                            categoria.menu();
                            break ;

                        case 2:
                            marca.menu();
                            break ;

                        case 3:
                            producto.menu();
                            break ;

                        case 4:
                            System.out.println("Se ha cerrado sesión");
                            break;

                        default:
                            System.out.println("Error: Ingrese una opción valida, vuelva a intentarlo");
                    }
                }while(opc != 4);
            }else
                System.out.println("Usuario o contraseña incorrectos");
            System.out.println("\n\n");
        }while(true);
    }
}
