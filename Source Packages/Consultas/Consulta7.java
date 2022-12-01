package Consultas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Consulta7 {
    Bases.Conexion sq = new Bases.Conexion();
    Scanner leer = new Scanner(System.in);
    
    int idcliente;
    String nuevo_correo;
    
    public void consulta() {
        System.out.println("\nActualizar correo de clientes");
        System.out.println("Ingresa el ID del cliente:");
        idcliente=Integer.parseInt(leer.nextLine());
        System.out.println("Ingresa el nuevo correo del cliente:");
        nuevo_correo=leer.nextLine();
       
        try {
            sq.estableceConnectionString();
            sq.conectar();
            ResultSet rsUsr;
            
            rsUsr = sq.consulta("exec consulta_g '"+idcliente+"', '"+nuevo_correo+"'");
            if (rsUsr.next()) {
                System.out.println("\nCorreo actualizado correctamente");
            }
            rsUsr.close();
            sq.cierraConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
          System.out.println("\nCorreo actualizado correctamente");        
    }
}
