package Consultas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Consulta10 {
    Scanner leer = new Scanner(System.in);    
    Bases.Conexion sq = new Bases.Conexion();
    
    String fecha1;
    String fecha2;
    int idproducto;
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
            
            System.out.println("\nProductID     Ventas");
            rsUsr = sq.consulta("exec consulta_j '"+fecha1+"', '"+fecha2+"'");
            while (rsUsr.next()) {
                idproducto = rsUsr.getInt("ProductID");
                ventas = rsUsr.getFloat("Ventas");
                System.out.println(idproducto+"            "+ventas);
            }

            rsUsr.close();
            sq.cierraConexion();            
            
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
