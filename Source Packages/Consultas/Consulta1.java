package Consultas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Consulta1 {
        Scanner leer = new Scanner(System.in);
        Bases.Conexion sq = new Bases.Conexion();
        Bases.Servidores bdserver = new Bases.Servidores();
        
        String nombre = "";
        int id;
        int cat;
        double total_venta;
        
        String instancia2 = bdserver.ins2();
        
    public void consulta() {
        System.out.println("\nIngresa algun ID de categoria:"); 
        try {
            sq.estableceConnectionString();
            sq.conectar();

            ResultSet rsUsr;
            rsUsr = sq.consulta("select ProductCategoryID, Name from  "+instancia2+".Production.ProductCategory order by ProductCategoryID");
            while (rsUsr.next()) {
                nombre = rsUsr.getString("Name");
                id = rsUsr.getInt("ProductCategoryID");
                System.out.println(id +" => "+nombre);
            }

            rsUsr.close();
            sq.cierraConexion();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        cat=Integer.parseInt(leer.nextLine());
        try {
            sq.estableceConnectionString();
            sq.conectar();

            ResultSet rsUsr;
            
            System.out.println("\nID Territorio     Total Venta");
            rsUsr = sq.consulta("exec consulta_a '"+cat+"'");
            while (rsUsr.next()) {
                id = rsUsr.getInt("Territorio");
                total_venta= rsUsr.getDouble("Total_Venta");
                System.out.println("    "+id +"             "+total_venta);
            }
            

            rsUsr.close();
            sq.cierraConexion();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }    
}
