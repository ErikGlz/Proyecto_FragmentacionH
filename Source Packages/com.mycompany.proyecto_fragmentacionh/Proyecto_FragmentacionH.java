package com.mycompany.proyecto_fragmentacionh;

import java.util.Scanner;

public class Proyecto_FragmentacionH {

    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        int opc=1;
        
        do{
            System.out.println("\nSelecciona una consulta");
            System.out.println("1. Total de las ventas de los productos de una categoría");
            System.out.println("2. Producto mas solicitado de una región");
            System.out.println("3. Actualizar stock disponible de una categoria");
            System.out.println("4. Ver clientes de un territorio que realizan ordenes en territorios diferentes al que se encuentran");
            System.out.println("5. Actualizar  la  cantidad  de  productos  de  una  orden");
            System.out.println("6. Actualizar el método de envío de una orden ");
            System.out.println("7. Actualizar correo electronico de cliente");
            System.out.println("8. Determinar el empleado que atendió más ordenes por territorio/región");
            System.out.println("9. Determinar el total de las ventas en cada una de las regiones para un rango de fechas");            
            System.out.println("10. Determinar los 5 productos menos vendidos en un rango de fecha");
            System.out.println("0. Salir");
            opc=Integer.parseInt(leer.nextLine());
            switch(opc){
                case 1:
                    Consultas.Consulta1 c1= new Consultas.Consulta1();
                    c1.consulta();
                    break;
                case 2:                    
                    Consultas.Consulta2 c2= new Consultas.Consulta2();
                    c2.consulta();
                    break;
                case 3:
                    Consultas.Consulta3 c3= new Consultas.Consulta3();
                    c3.consulta();
                    break;
                case 4:
                    Consultas.Consulta4 c4= new Consultas.Consulta4();
                    c4.consulta();
                    break;
                case 5:                    
                    Consultas.Consulta5 c5= new Consultas.Consulta5();
                    c5.consulta();
                    break;
                case 6:
                    Consultas.Consulta6 c6= new Consultas.Consulta6();
                    c6.consulta();
                    break;
                case 7:
                    Consultas.Consulta7 c7= new Consultas.Consulta7();
                    c7.consulta();
                    break;
                case 8:
                    Consultas.Consulta8 c8= new Consultas.Consulta8();
                    c8.consulta();
                    break;
                case 9:
                    Consultas.Consulta9 c9= new Consultas.Consulta9();
                    c9.consulta();
                    break;                    
                case 10:
                    Consultas.Consulta10 c10= new Consultas.Consulta10();
                    c10.consulta();
                    break;                    
            }
        }while(opc!=0);
    }
}
