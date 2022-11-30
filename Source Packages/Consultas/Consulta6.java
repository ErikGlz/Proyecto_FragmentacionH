package Consultas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Consulta6 {
    Bases.Conexion sq = new Bases.Conexion();
    Bases.Servidores bdserver = new Bases.Servidores();
    Scanner leer = new Scanner(System.in);
    int ordenid, nuevomet;
    int IDOrden, IDMetodo;
    String Metodo;
    
    
    String instancia3 = bdserver.ins3();
    
    public void consulta() {
        //Bases.Conexion sq = new Bases.Conexion();
               
        System.out.println("\nActualizar metodo de envio en orden");

        System.out.println("Ingresa el ID de la orden");
        ordenid=Integer.parseInt(leer.nextLine());
        verpororden(ordenid);
        System.out.println("\nLista de metodos de envío");
        metodos();
        System.out.println("Selecciona el nuevo metodo de envio");
        nuevomet=Integer.parseInt(leer.nextLine());
        actualizar(ordenid, nuevomet);
    }

    private void verpororden(int ordenid) {
        try {
            sq.estableceConnectionString();
            sq.conectar();
            ResultSet rsUsr;
            rsUsr = sq.consulta("select * from vista_f where IDOrden="+ordenid);
            while (rsUsr.next()) {
                IDOrden = rsUsr.getInt("IDOrden");
                IDMetodo = rsUsr.getInt("IDMetodo");
                Metodo = rsUsr.getString("Metodo");
                
                System.out.println("ID de la Orden: "+IDOrden);   
                System.out.println("ID del Metodo: "+IDMetodo);   
                System.out.println("Metodo: "+Metodo);
            }

            rsUsr.close();
            sq.cierraConexion();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void metodos() {
        try {
            sq.estableceConnectionString();
            sq.conectar();
            ResultSet rsUsr;
            rsUsr = sq.consulta("select ShipMethodID, Name from "+instancia3+".Purchasing.ShipMethod order by ShipMethodID");
            while (rsUsr.next()) {
                IDMetodo = rsUsr.getInt("ShipMethodID");
                Metodo = rsUsr.getString("Name");
                
                System.out.println(IDMetodo+" => "+Metodo);           
            }

            rsUsr.close();
            sq.cierraConexion();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    }

    private void actualizar(int ordenid, int nuevomet) {
        int metc;
        
        try {
            sq.estableceConnectionString();
            sq.conectar();
            ResultSet rsUsr;
            
            rsUsr = sq.consulta("exec consulta_f '"+nuevomet+"', '"+ordenid+"'");
            if (rsUsr.next()) {
                metc = rsUsr.getInt("MetodosCambiados");
                System.out.println("Se cambio el metodo de "+metc+" orden(es)");
            }
            rsUsr.close();
            sq.cierraConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        System.out.println("La informacion de la orden ahora es la siguiente:");
        verpororden(ordenid);
    }    
}
