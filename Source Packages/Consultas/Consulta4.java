package Consultas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Consulta4 {
    Scanner leer = new Scanner(System.in);    
    Bases.Conexion sq = new Bases.Conexion();
        int customerID_c;
        int customerID_s;
        int territorio;
        int territorio_c;
        int territorio_s;
    public void consulta() {
        
        System.out.println("\nIngresa un ID de territorio (1-10)");
        
        territorio=Integer.parseInt(leer.nextLine());
        int clientes_tot=0;
        try {
            sq.estableceConnectionString();
            sq.conectar();

            ResultSet rsUsr;
            
            System.out.println("CustomerID_c \tTerritorio_c \tCustomerID_s \tTerritorio_s");
            rsUsr = sq.consulta("exec consulta_d '"+territorio+"'");
            while (rsUsr.next()) {
                customerID_c = rsUsr.getInt("customerID_c");
                customerID_s = rsUsr.getInt("customerID_s");
                territorio_c = rsUsr.getInt("territorio_c");
                territorio_s = rsUsr.getInt("territorio_s");
                
                System.out.println(customerID_c +"\t   "+territorio_c+" \t\t\t"+customerID_s + "\t\t\t" +territorio_s);
                
                clientes_tot=clientes_tot++;
            }
            
            rsUsr.close();
            sq.cierraConexion();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        if(clientes_tot==0){
            System.out.println("No se encontraron clientes que realicen pedidos en terriotorios difrentes");
        }else{
            System.out.println("Se encontraron "+clientes_tot+" clientes que realizan pedidos en territorios diferentes");
        }
    }    
}
