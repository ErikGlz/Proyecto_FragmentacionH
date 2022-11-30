package Consultas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Consulta2 {
    Scanner leer = new Scanner(System.in);    
    Bases.Conexion sq = new Bases.Conexion();
    
        int id,id2;
        String nombre;
        String grupo;
        int cantidad_total;
        int id_territorio;
        int territorio;
        String nom_territorio;
        int cantidad_territorio;

    public void consulta() {
        System.out.println("\nIngresa algun ID de producto (1-10)");
                
        territorio=Integer.parseInt(leer.nextLine());        
        try {
            sq.estableceConnectionString();
            sq.conectar();

            ResultSet rsUsr;
            
            rsUsr = sq.consulta("exec consulta_b '"+territorio+"'");
            if (rsUsr.next()) {
                id2 = rsUsr.getInt("ID");
                nombre = rsUsr.getString("Nombre");
                cantidad_total = rsUsr.getInt("CantidadTotal");
                id_territorio = rsUsr.getInt("IDTerritorio");
                nom_territorio = rsUsr.getString("NTerritorio");
                cantidad_territorio = rsUsr.getInt("CantidadTerritorio");
            }
            System.out.println("\nEl producto más solicitado es: "+nombre +" con ID "+id2);
            System.out.println("Con "+cantidad_total+" unidades vendidas");
            System.out.println("El territorio donde más se vendio es "+nom_territorio+ " con ID "+id_territorio);
            System.out.println("Con "+cantidad_territorio + " unidades vendidas");

            rsUsr.close();
            sq.cierraConexion();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
 }    
}
