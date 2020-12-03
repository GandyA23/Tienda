package modelo;

import servicio.general.ConexionMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MarcaDao extends ConexionMySQL{
    private PreparedStatement ps;
    private ResultSet rs;
    private Connection con;
    private final String SQL_INSERTAR_MARCA = "INSERT INTO marca (nombre, descripcion) VALUES(?,?);";
    private final  String SQL_MODIFICAR_MARCA = "UPDATE marca SET nombre = ?, descripcion = ? WHERE id = ?;";
    private  final  String SQL_ELIMINAR_MARCA = "DELETE FROM marca WHERE id = ?;";
    private final String SQL_CONSULTAR_MARCA = "SELECT * FROM marca ORDER BY nombre";
    private final String SQL_CANTIDAD_MARCAS = "SELECT count(nombre) as cantidad FROM marca;";



    public boolean add(MarcaBean bean)  {
        boolean resultado = false;

        try{
            ps = getConexion().prepareStatement(SQL_INSERTAR_MARCA);
            ps.setString(1, bean.getNombre());
            ps.setString(2, bean.getDescripcion());
            resultado = ps.executeUpdate() == 1;

        }catch (SQLException e){
            System.out.println("Error en MarcaDao().add(MarcaBean bean)" );
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


    public boolean update(MarcaBean bean){
        boolean resultado = false;

        try{
            ps = getConexion().prepareStatement(SQL_MODIFICAR_MARCA);

            ps.setString(1, bean.getNombre());
            ps.setString(2,bean.getDescripcion());
            ps.setInt(3,bean.getId());
            resultado = ps.executeUpdate() == 1;
        }catch (SQLException e){
            System.out.println("Error en MarcaDao().update(MarcaBean bean)");
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
            ps = getConexion().prepareStatement(SQL_ELIMINAR_MARCA);
            ps.setInt(1, id);
            resultado = ps.executeUpdate() == 1;

        }catch (SQLException e){
            System.out.println("Error en MarcaDao().delete(MarcaBean bean)");
            System.out.println(e);

        }finally {
            try{
                ps.close();

            }catch (Exception e){
                System.out.println("Error al cerrar las conexiones ");

            }
        }
        return resultado;
    }

    public MarcaBean [] query(){
        MarcaBean[] marcas = null;

        try{
            ps = getConexion().prepareStatement(SQL_CANTIDAD_MARCAS);
            rs = ps.executeQuery();

            if(rs.next()){
                marcas = new MarcaBean[rs.getInt("cantidad")];
            }

        }catch (SQLException e) {
            System.out.println("Error al consultar la cantidad en Marca ");
            System.out.println(e);
        }
        if(marcas != null){
            try{
                ps = getConexion().prepareStatement(SQL_CONSULTAR_MARCA);
                rs = ps.executeQuery();

                for(int i=0; rs.next(); i++)
                    marcas[i] = new MarcaBean(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("descripcion")
                    );

        }catch (Exception e){
                System.out.println("Error en MarcaDao().query()");
                System.out.println(e);
            }finally {
                try{
                    ps.close();
                }catch (Exception e){
                    System.out.println("Error al cerrar las conexiones");
                    System.out.println(e);

                }
            }
        }
        return marcas;

    }






}
