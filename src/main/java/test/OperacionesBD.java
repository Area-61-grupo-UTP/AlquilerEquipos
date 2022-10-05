
package test;
// importar clases  
import beans.Equipos;
import connection.DBConnection;
// ejecutara resultados
import java.sql.ResultSet;
//acceder a la base de datos entender las setencias sql
import java.sql.Statement;

public class OperacionesBD {
    public static void main(String[] args) {
        listarEquipos();
        //actualizarEquipo(1,"table");
    }
    public static void actualizarEquipo(int id, String tipo){
        //crear objeto
        DBConnection con=new DBConnection();
        //crear atributo
        String sql="UPDATE equipos SET tipo= '"+ tipo + "'WHERE id ="+ id;
               
        try {
            Statement st= con.getConnection().createStatement();
            st.executeUpdate(sql);
        }catch( Exception ex){
            System.out.println(ex.getMessage());
        
        }
        finally {
            con.desconectar();
            
        }
    }
    public static void listarEquipos(){
       DBConnection con=new DBConnection();
       String sql="SELECT * FROM equipos";
       try {
            Statement st= con.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                int id=rs.getInt("id");
                String descripcion=rs.getString("descripcion");
                String tipo=rs.getString("tipo");
                String marca=rs.getString("marca");
                int equipos_disponibles=rs.getInt("equipos_disponibles");
                boolean novedad = rs.getBoolean("novedad");
                Equipos equipos= new Equipos(id,descripcion,tipo,marca,equipos_disponibles,novedad);
                System.out.println(equipos.toString());
            }
          
        }catch( Exception ex){
            System.out.println(ex.getMessage());
        
        }
        finally { 
            con.desconectar();
            
        }
    }
    
    
}
