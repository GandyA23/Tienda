package modelo;

import servicio.general.AES;
import servicio.general.ConexionMySQL;
import servicio.general.Validacion;
import view.Menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministradorDao extends ConexionMySQL {
    private PreparedStatement pstm;
    private ResultSet rs;
    Validacion val = new Validacion();
    AES aes= new AES();
    private final String SQL_CONSULTAR_USUARIO = "SELECT * FROM usuario = ? AND "+aes.decrypt("password")+"=?;";

    public boolean login(AdministradorBean bean) throws SQLException {
        boolean resultado = false;
        try {
            pstm = getConexion().prepareStatement(SQL_CONSULTAR_USUARIO);
            pstm.setString(1, bean.getUsuario());
            pstm.setString(2, bean.getContra());
            rs = pstm.executeQuery();
            if (rs.next())
                resultado = true;

        } catch (Exception e) {
            System.out.println("Error en AdministradorDao().login(AdministradorBean bean)");
        } finally {
            try {
                rs.close();
                pstm.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar las conexiones en AdministradorDao().consulta(AdministradorBean bean)");
            }
        }
        return resultado;
    }


}
