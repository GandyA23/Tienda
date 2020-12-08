package servicio.general;

public class Validacion {

    private String ACENTOS = "áéíóúÁÉÍÓÚñÑ";
    private String ALLOWED_CHARACTERS = " .,:;\"\'()-";
    private final Cin cin = new Cin();

    public boolean doYouWantRead(String campo){
        boolean c ;
        int opcF = 0;

        do{
            opcF = cin.cInt("Este campo (" + campo + ") no es obligatorio, ¿Desea ingresarlo?\n1.-Si\n2.-No\nIngrese una opción: ");

            if(c = opcF < 1 || opcF > 2)
                System.out.println("Error: Ingrese una opción valida, vuelva a intentarlo.");

            System.out.println("\n\n");
        }while (c);

        return opcF == 1;
    }

    public String nullMessage(String dato){
        if(dato == null) return "No hay registro guardado" ;
        return dato ;
    }

    public boolean haveCharNotAllowed(String str){
        int n = str.length();

        for(int i=0; i<n; i++){
            char c = str.charAt(i);
            if( isCharNotAllowed(c) ) return true ;
        }

        return false ;
    }

    public boolean onlyLettersWithoutSpaces(String str){
        int n = str.length();
        for(int i=0; i<n; i++){
            char c = str.charAt(i);

            if( !isLetter(c) ) return false ;
        }

        return true ;
    }

    public boolean onlyLetters(String str){
        int n = str.length();
        for(int i=0; i<n; i++){
            char c = str.charAt(i);

            if( !isLetter(c) && c != 32 ) return false ;
        }

        return true ;
    }

    public boolean isPositive(int n){
        return n > 0 ;
    }

    public boolean isPositive(double n){
        return n > 0.0 ;
    }

    private boolean isLetter(char c){
        return c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || ACENTOS.indexOf(c) != -1 ;
    }

    private boolean isNumber(char c){
        return c >= '0' && c <= '9' ;
    }

    private boolean isAllowedChar(char c){
        return ALLOWED_CHARACTERS.indexOf(c) != -1 ;
    }

    private boolean isCharNotAllowed(char c){
        return !isLetter(c) && !isNumber(c) && !isAllowedChar(c) ;
    }
}
