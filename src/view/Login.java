package view;

import modelo.AdministradorBean;
import modelo.AdministradorDao;

import java.sql.SQLException;
import java.util.Scanner;

public class Login {

    private final AdministradorDao administradorDao = new AdministradorDao();
    private AdministradorBean administradorBean ;
    private final Scanner cin = new Scanner(System.in);

    public boolean login() throws SQLException {
        String usuario, password ;

        System.out.print("Ingresa tu usuario: ");
        usuario = cin.nextLine();
        System.out.print("Ingresa tu contraseña: ");
        password = cin.nextLine();

        administradorBean = new AdministradorBean(usuario, password);

        return administradorDao.login(administradorBean);
    }
}
