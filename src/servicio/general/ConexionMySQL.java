package servicio.general;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {
    public static Connection getConexion() throws SQLException {
        Connection con;
        String driver = "com.mysql.jdbc.Driver";

        //Conexión a localhost
        String url = "jdbc:mysql://localhost/tienda?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user = "root";
        String pass = "Avila-23";

        System.setProperty(driver,"");
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        }catch (Exception e){
            System.out.println(e);
        }
        con = DriverManager.getConnection(url,user,pass);
        return  con;
    }

    public static void main(String[] args) {
        try {
            ConexionMySQL c = new ConexionMySQL();
            Connection con = c.getConexion();
            System.out.println(con);
            System.out.println("(^_^) Connection successful ... ");
        } catch (Exception e) {
            System.out.println("(o_O) Conection error ... " + e);
        }
    }

}
