package Consultas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Consulta8 {
    Scanner leer = new Scanner(System.in);    
    Bases.Conexion sq = new Bases.Conexion();
    int id_territorio, empleado,ordenes,id;
    
    public void consulta(){
        System.out.println("\nIngresa algun ID de territorio (1-10)");
        id_territorio=Integer.parseInt(leer.nextLine());
        try {
            sq.estableceConnectionString();
            sq.conectar();

            ResultSet rsUsr;
            
            System.out.println("\nEmpleado      Ordenes     TerritoryID");
            rsUsr = sq.consulta("exec consulta_h '"+id_territorio+"'");
            if (rsUsr.next()) {
                empleado = rsUsr.getInt("Empleado");
                ordenes = rsUsr.getInt("Ordenes");
                id = rsUsr.getInt("TerritoryID");
                System.out.println(empleado+"           "+ordenes+"             "+id);
            }

            rsUsr.close();
            sq.cierraConexion();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
}
