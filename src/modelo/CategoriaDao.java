package modelo;

import servicio.general.ConexionMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoriaDao extends ConexionMySQL {
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private final String SQL_INSERTAR_CATEGORIA = "INSERT INTO categoria (nombre, descripcion) VALUES(?, ?)";
    private final String SQL_MODIFICAR_CATEGORIA = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE id= ?";
    private final String SQL_ELIMINAR_CATEGORIA = "DELETE FROM categoria WHERE id = ?";
    private final String SQL_CONSULTAR_CATEGORIA = "SELECT * FROM categoria ORDER BY nombre";
    private final String SQL_CONSULTAR_CATEGORIA_UNO_NOMBRE = "SELECT * FROM categoria WHERE nombre = ? ";
    private final String SQL_CONSULTAR_CATEGORIA_UNO_ID = "SELECT * FROM categoria WHERE id = ? ";
    private final String SQL_CANTIDAD_CATEGORIA = "SELECT count(nombre) as cantidad FROM categoria";
    public boolean add(CategoriaBean bean)  {
        boolean resultado = false;

        try{
            ps = getConexion().prepareStatement(SQL_INSERTAR_CATEGORIA);
            ps.setString(1, bean.getNombre());
            ps.setString(2, bean.getDescripcion());
            resultado = ps.executeUpdate() == 1;

        }catch (SQLException e){
            System.out.println("Error en CategoriaDao().add(CategoriaBean bean) :(" );
            System.out.println(e);

        }finally {
            try{
                ps.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return resultado;
    }


    public boolean update(CategoriaBean bean){
        boolean resultado = false;

        try{
            ps = getConexion().prepareStatement(SQL_MODIFICAR_CATEGORIA);

            ps.setString(1, bean.getNombre());
            ps.setString(2,bean.getDescripcion());
            ps.setInt(3,bean.getId());
            resultado = ps.executeUpdate() == 1;
        }catch (SQLException e){
            System.out.println("Error en CategoriaDao().update(CategoriaBean bean) :(");
            System.out.println(e);
        }finally {
            try{
                ps.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return resultado;
    }


    public boolean delete(int id){
        boolean resultado = false;

        try{
            ps = getConexion().prepareStatement(SQL_ELIMINAR_CATEGORIA);
            ps.setInt(1, id);
            resultado = ps.executeUpdate() == 1;

        }catch (SQLException e){
            System.out.println("Error en CategoriaDao().delete(CategoriaBean bean) :(");
            System.out.println(e);

        }finally {
            try{
                ps.close();

            }catch (Exception e){
                System.out.println("Error al cerrar las conexiones :(");

            }
        }
        return resultado;
    }

    public CategoriaBean queryOne(String nombre){
        CategoriaBean bean = null ;

        try{
            ps = getConexion().prepareStatement(SQL_CONSULTAR_CATEGORIA_UNO_NOMBRE);
            ps.setString(1, nombre);
            rs = ps.executeQuery();

            if(rs.next())
                bean = new CategoriaBean( rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion") );

        }catch (SQLException e) {
            System.out.println("Error al consultar si la categoría existe :(");
            System.out.println(e);
        }finally {
            try{
                rs.close();
                ps.close();
            }catch (Exception e){
                System.out.println("Error al cerrar las conexiones :(");
                System.out.println(e);

            }
        }

        return bean ;
    }

    public CategoriaBean queryOne(int id){
        CategoriaBean bean = null ;

        try{
            ps = getConexion().prepareStatement(SQL_CONSULTAR_CATEGORIA_UNO_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if(rs.next())
                bean = new CategoriaBean( rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion") );

        }catch (SQLException e) {
            System.out.println("Error al consultar si la categoría existe :(");
            System.out.println(e);
        }finally {
            try{
                rs.close();
                ps.close();
            }catch (Exception e){
                System.out.println("Error al cerrar las conexiones :(");
                System.out.println(e);

            }
        }

        return bean ;
    }

    public CategoriaBean [] query(){
        CategoriaBean[] categoria = null;

        try{
            ps = getConexion().prepareStatement(SQL_CANTIDAD_CATEGORIA);
            rs = ps.executeQuery();

            if(rs.next()){
                categoria = new CategoriaBean[rs.getInt("cantidad")];
            }

        }catch (SQLException e) {
            System.out.println("Error al consultar la cantidad en categoria :( ");
            System.out.println(e);
        }
        if(categoria != null){
            try{
                ps = getConexion().prepareStatement(SQL_CONSULTAR_CATEGORIA);
                rs = ps.executeQuery();

                for(int i=0; rs.next(); i++)
                    categoria[i] = new CategoriaBean(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("descripcion")
                    );

            }catch (Exception e){
                System.out.println("Error en MarcaDao().query()");
                System.out.println(e);
            }finally {
                try{
                    rs.close();
                    ps.close();
                }catch (Exception e){
                    System.out.println("Error al cerrar las conexiones :(");
                    System.out.println(e);

                }
            }
        }
        return categoria;

    }

}
