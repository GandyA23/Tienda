package servicio.general;

import java.util.Scanner;

public class Cin {
    private Scanner cin ;

    public void init(){
        cin = new Scanner(System.in);
    }

    public int cInt(){
        return cin.nextInt();
    }

    public double cDouble(){
        return cin.nextDouble();
    }

    public String cString(){
        return cin.next();
    }

    public String cLine(){
        return cin.nextLine();
    }
}
