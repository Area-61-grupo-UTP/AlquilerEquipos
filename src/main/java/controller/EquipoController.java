package controller;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import beans.Equipos;
import connection.DBConnection;

public class EquipoController implements IEquipoController {
    
    @Override
    public String listar(boolean ordenar, String orden) {
        Gson gson = new Gson();
        DBConnection con = new DBConnection();
        String sql="Select * from equipos";
        
        if(ordenar ==true){
            sql+= " order by tipo "+orden;
        
        }
        //equipo o equiposss
        List<String> equipo =new ArrayList<String>();
        
        try {
            Statement st = con.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
          
            while (rs.next()) {
                int id=rs.getInt("id");
                String descripcion = rs.getString("descripcion");
                String tipo = rs.getString("tipo");
                String marca = rs.getString("marca");
                int equipos_disponibles=rs.getInt("equipos_disponibles");
                boolean novedad = rs.getBoolean("novedad");
               

                Equipos equipos = new Equipos(id, descripcion, tipo, marca, equipos_disponibles, novedad);
                equipo.add(gson.toJson(equipos));

            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        } finally {

            con.desconectar();
        }
        return gson.toJson(equipo);
    }
     @Override
    public String devolver(int id, String username) {

        DBConnection con = new DBConnection();
        String sql = "Delete from alquiler where id= " + id + " and username = '" 
                + username + "' limit 1";

        try {
            Statement st = con.getConnection().createStatement();
            st.executeQuery(sql);

            this.sumarCantidad(id);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            con.desconectar();
        }

        return "false";
    }

    @Override
    public String sumarCantidad(int id) {

        DBConnection con = new DBConnection();

        String sql = "Update equipos set equipos_disponibles = (Select equipos_disponibles from equipos where id = " 
                + id + ") + 1 where id = " + id;

        try {
            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            con.desconectar();
        }

        return "false";

    }
    
    
    @Override
    public String alquilar(int id, String username) {

        Timestamp fecha = new Timestamp(new Date().getTime());
        DBConnection con = new DBConnection();
        String sql = "Insert into alquiler values ('" + id + "', '" + username + "', '" + fecha + "')";

        try {
            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql);

            String modificar = modificar(id);

            if (modificar.equals("true")) {
                return "true";
            }

        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            con.desconectar();
        }
        return "false";
    }

     @Override
    public String modificar(int id) {

        DBConnection con = new DBConnection();
        String sql = "Update equipos set equipos_disponibles = (equipos_disponibles - 1) where id = " + id;

        try {
            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            con.desconectar();
        }

        return "false";

    }
}

