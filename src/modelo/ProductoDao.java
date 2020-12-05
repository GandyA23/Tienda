package modelo;

import servicio.general.ConexionMySQL;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductoDao extends ConexionMySQL {

    private final String SQL_COUNT = "SELECT count(codigo) AS cantidad FROM producto";
    private final String SQL_ADD = "INSERT INTO producto VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final String SQL_QUERY = "SELECT * FROM producto ORDER BY nombre" ;
    private final String SQL_DELETE = "DELETE FROM producto WHERE codigo = ? ";
    private final String SQL_UPDATE = "UPDATE producto SET `nombre` = ?, `idCategoria` = ?, `idMarca` = ?, `existencia` = ?, `precio` = ?, `descripcion` = ? WHERE codigo = ? ";

    private PreparedStatement pstm ;
    private ResultSet rs ;


    public boolean add(ProductoBean bean){
        boolean flag = false ;
        try{
            pstm = getConexion().prepareStatement(SQL_ADD);
            pstm.setString(1, bean.getCodigo());
            pstm.setString(2, bean.getNombre());
            pstm.setInt(3, bean.getCategoria().getId());
            pstm.setInt(4, bean.getMarca().getId());
            pstm.setInt(5, bean.getExistencia());
            pstm.setDouble(6, bean.getPrecio());
            pstm.setString(7, bean.getDescripcion());

            flag = pstm.executeUpdate() == 1 ;

        }catch (Exception e){
            System.out.println("Error en ProductoDao().add(ProductoBean bean)");
            System.out.println(e);
        }finally {
            try {
                pstm.close();
            }catch (Exception e){
                System.out.println("Error al cerrar las conexiones en ProductoDao().add(ProductoBean bean)");
                System.out.println(e);
            }
        }

        return flag ;
    }

    public ProductoBean[] query(){
        ProductoBean[] productos = null ;

        try{
            pstm = getConexion().prepareStatement(SQL_COUNT);
            rs = pstm.executeQuery();

            if(rs.next())
                productos = new ProductoBean[ rs.getInt("cantidad") ];
        } catch (Exception e) {
            System.out.println("Error al consultar la cantidad en ProductoDao().query()");
            System.out.println(e);
        }

        if(productos != null){
            try{
                pstm = getConexion().prepareStatement(SQL_QUERY);
                rs = pstm.executeQuery();

                for(int i=0; rs.next(); i++)
                    productos[i] = new ProductoBean(
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            new CategoriaBean( rs.getInt("idCategoria") ),
                            new MarcaBean( rs.getInt("idMarca") ),
                            rs.getInt("existencia"),
                            rs.getDouble("precio"),
                            rs.getString("descripcion")
                    );


            } catch (Exception e) {
                System.out.println("Error en ProductoDao().query()");
                System.out.println(e);
            }
        }

        try {
            rs.close();
            pstm.close();
        }catch (Exception e){
            System.out.println("Error al cerrar cerrar conexiones en ProductoDao().add(ProductoBean bean)");
            System.out.println(e);
        }

        return productos;
    }

    public boolean delete(String codigo){
        boolean flag = false ;

        try{
            pstm = getConexion().prepareStatement(SQL_DELETE);
            pstm.setString(1, codigo);
            flag = pstm.executeUpdate() == 1;

        }catch (Exception e){
            System.out.println("Error en ProductoDao().delete(String codigo)");
            System.out.println(e);
        }finally {
            try{
                pstm.close();
            }catch (Exception e){
                System.out.println("Error al cerrar conexiones de DB en ProductoDao().delete(String codigo)");
            }
        }

        return flag;
    }

    public boolean update(ProductoBean bean){
        boolean flag = false ;

        try{
            pstm = getConexion().prepareStatement(SQL_UPDATE);

            pstm.setString(1, bean.getNombre());
            pstm.setInt(2, bean.getCategoria().getId());
            pstm.setInt(3, bean.getMarca().getId());
            pstm.setInt(4, bean.getExistencia());
            pstm.setDouble(5, bean.getPrecio());
            pstm.setString(6, bean.getDescripcion());
            pstm.setString(7, bean.getCodigo());

            flag = pstm.executeUpdate() == 1;

        }catch (Exception e){
            System.out.println("Error en ProductoDao().update(ProductoBean bean)");
            System.out.println(e);
        }finally {
            try{
                pstm.close();
            }catch (Exception e){
                System.out.println("Error al cerrar conexiones de DB en ProductoDao().update(ProductoBean bean)");
            }
        }

        return flag;
    }
}
