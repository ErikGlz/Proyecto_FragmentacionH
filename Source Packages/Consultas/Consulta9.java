package Consultas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Consulta9 {
    Scanner leer = new Scanner(System.in);    
    Bases.Conexion sq = new Bases.Conexion();
    
    String fecha1;
    String fecha2;
    String grupo;
    float ventas;
    
    public void consulta(){
        System.out.println("\nIngresa una fecha con el formato aaaa-mm-dd");
        fecha1=leer.nextLine();
        System.out.println("\nIngresa otra fecha posterior a la primera con el formato aaaa-mm-dd");
        fecha2=leer.nextLine();
        try{
            sq.estableceConnectionString();
            sq.conectar();

            ResultSet rsUsr;
            
            System.out.println("\nGroup             Ventas");
            rsUsr = sq.consulta("exec consulta_i '"+fecha1+"', '"+fecha2+"'");
            while (rsUsr.next()) {
                grupo = rsUsr.getString("Group");
                ventas = rsUsr.getFloat("Ventas");
                System.out.println(grupo+"            "+ventas);
            }

            rsUsr.close();
            sq.cierraConexion();            
            
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }    
}
