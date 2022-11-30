package Consultas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Consulta5 {
    Bases.Conexion sq = new Bases.Conexion();
    Scanner leer = new Scanner(System.in);
    char res='S';
    int IDProd;
    int Cantidad;
    String Nombre;
    
    public void consulta() {
        int ordenid;
        int productoid;
        int nueva_can;
        
        System.out.println("\nActualizar productos por orden");

        System.out.println("Ingresa el ID de la orden");
        ordenid=Integer.parseInt(leer.nextLine());
        verpororden(ordenid);
                
        do{
            System.out.println("\nIngresa el ID del producto que desea modificar");
            productoid=Integer.parseInt(leer.nextLine());
            System.out.println("Ingresa la cantidad");
            nueva_can=Integer.parseInt(leer.nextLine());
                    
            actualizar(ordenid, productoid, nueva_can);
                    
            System.out.println("Desea actualizar otro producto de esta orden? (S/N)");
            res=leer.nextLine().charAt(0);
        }while(res=='S');
    }

    private void verpororden(int ordenid) {
        
        try {
            sq.estableceConnectionString();
            sq.conectar();
            System.out.println("\nIDProducto\t   Nombre\t\t\t\t   Cantidad");
            ResultSet rsUsr;
            rsUsr = sq.consulta("select * from vista_e where Orden="+ordenid);
            while (rsUsr.next()) {
                IDProd = rsUsr.getInt("IDProd");
                Cantidad = rsUsr.getInt("Cantidad");
                Nombre = rsUsr.getString("Nombre");
                
                System.out.println(IDProd+"  \t\t  "+Nombre+"  \t\t\t  "+Cantidad);           
            }

            rsUsr.close();
            sq.cierraConexion();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void actualizar(int ordenid, int productoid, int nueva_can) {
        int prodc;
        
        try {
            sq.estableceConnectionString();
            sq.conectar();
            ResultSet rsUsr;
            
            rsUsr = sq.consulta("exec consulta_e '"+nueva_can+"', '"+productoid+"', '"+ordenid+"'");
            if (rsUsr.next()) {
                prodc = rsUsr.getInt("ProductosAumentados");
                System.out.println("\nSe cambio el stock de "+prodc+" producto(s)");
            }
            rsUsr.close();
            sq.cierraConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        System.out.println("El nuevo stock es el siguiente");
        verpororden(ordenid);
    }    
}
