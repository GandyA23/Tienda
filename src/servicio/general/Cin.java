package servicio.general;

import java.util.Scanner;

public class Cin {
    private Scanner cin ;

    private void init(){
        cin = new Scanner(System.in);
    }

    public int cInt(String mensaje){
        init();
        int aux = 0 ;
        boolean c = true ;

        do{
            init();
            try{
                System.out.print(mensaje);
                aux = cin.nextInt();

                c = false ;
            }catch (Exception e){
                System.out.println("Error: No ingrese caracteres, vuelva a intentarlo.");
            }
        }while(c);

        return aux;
    }

    public double cDouble(String mensaje){
        init();
        double aux = 0 ;
        boolean c = true ;

        do{
            init();
            try{
                System.out.print(mensaje);
                aux = cin.nextDouble();

                c = false ;
            }catch (Exception e){
                System.out.println("Error: No ingrese caracteres, vuelva a intentarlo.");
            }
        }while(c);

        return aux;
    }

    public String cString(){
        init();
        return cin.next().trim();
    }

    public String cLine(){
        init();
        return cin.nextLine().trim();
    }
}
