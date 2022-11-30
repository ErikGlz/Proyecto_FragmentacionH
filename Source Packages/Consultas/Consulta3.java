package Consultas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Consulta3 {
    Bases.Conexion sq = new Bases.Conexion();
    Bases.Servidores bdserver = new Bases.Servidores();
    Scanner leer = new Scanner(System.in);
    char opc;

    int IDProd;
    String NProd;
    int IDCat;
    String Categoria;
    int Cantidad;
    int IDLoc;
    String NLoc;
    
    String instancia2 = bdserver.ins2();
            
    public void consulta() {
        System.out.println("\nA. Ver stock por categoria y localidad");
        System.out.println("B. Actualizar stock por categoria y localidad");
        opc=leer.nextLine().charAt(0);
        
        switch(opc){
            case 'A': 
                int cat;
                System.out.println("\nIngresa el ID de la categoria");
                categorias();
                cat=Integer.parseInt(leer.nextLine());
                int loc;
                System.out.println("\nIngresa el ID de la Locacion");
                localidades();
                loc=Integer.parseInt(leer.nextLine());
                verloccat(cat, loc);
                break;
            case 'B': 
                actualizastock();
                break;
        }
    }

    private void verloccat(int cat, int loc) {        
        try {
            sq.estableceConnectionString();
            sq.conectar();
            System.out.println("\nIDProducto   Nombre   \t\t\t    Cantidad  ");
            ResultSet rsUsr;
            rsUsr = sq.consulta("select * from vista_c where IDCat="+cat+" and IDLoc="+loc);
            while (rsUsr.next()) {
                IDProd = rsUsr.getInt("IDProd");
                NProd = rsUsr.getString("NProd");
                Cantidad = rsUsr.getInt("Cantidad");
                System.out.println(IDProd +"    "+NProd+"   \t\t  "+Cantidad);           
            }

            rsUsr.close();
            sq.cierraConexion();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void actualizastock() {
        int prodau;
        System.out.println("\nActualizacion de stock");
        int cat;
        System.out.println("Ingresa el ID de la categoria");
        categorias();
        cat=Integer.parseInt(leer.nextLine());
        int loc;
        System.out.println("\nIngresa el ID de la Locacion");
        localidades();
        loc=Integer.parseInt(leer.nextLine());
        
        try {
            sq.estableceConnectionString();
            sq.conectar();
            ResultSet rsUsr;
            
            rsUsr = sq.consulta("exec consulta_c '"+cat+"', '"+loc+"'");
            if (rsUsr.next()) {
                prodau = rsUsr.getInt("ProductosAumentados");
                System.out.println("\nSe aumento el stock de "+prodau+" productos");
            }
            rsUsr.close();
            sq.cierraConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        verloccat(cat, loc);
    }

    private void categorias() {
        String nombre;
        int id; 
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
    }

    private void localidades() {
    String nombre;
        int id; 
        try {
            sq.estableceConnectionString();
            sq.conectar();

            ResultSet rsUsr;
            rsUsr = sq.consulta("select LocationID, Name from  "+instancia2+".Production.Location order by LocationID");
            while (rsUsr.next()) {
                nombre = rsUsr.getString("Name");
                id = rsUsr.getInt("LocationID");
                System.out.println(id +" => "+nombre);
            }

            rsUsr.close();
            sq.cierraConexion();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }    
}
