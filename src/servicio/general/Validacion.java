package servicio.general;

import sun.security.krb5.internal.crypto.Aes128;

public class Validacion {

    public boolean validarUserPass(String user, String pass) {
        boolean resultado=false;
        AES aes= new AES();
        if (aes.decrypt(user).equals(user) && aes.decrypt(pass).equals(pass)) {
            return resultado;
        }
        return resultado;
    }

    public boolean emptyNomCategoria(String categoria){
        if(!(categoria.isEmpty())){
            return true;
        }
        return false;
    }
    public boolean caracterEspecial(String contra) {
        String[] caracterEsp = new String[]{"/", "*", "+", ".", "_", "-", "?", "$", "%", "]", "(", ")","!","¡","°","#","&","="};
        for (int i = 0; i < caracterEsp.length; i++) {
            if (contra.contains(caracterEsp[i])) {
                return true;
            }
        }
        return false;
    }
    public boolean caracterEspecialStock(String contra) {
        String[] caracterEsp = new String[]{"/", "*", "_", "?", "$", "%", "]", "(", ")","!","¡","°","#","&","="};
        for (int i = 0; i < caracterEsp.length; i++) {
            if (contra.contains(caracterEsp[i])) {
                return true;
            }
        }
        return false;
    }
    public boolean numeros(String contra) {
        String[] num = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        for (int j = 0; j < num.length; j++) {
            if (contra.contains(num[j])) {
                return true;
            }
        }
        return false;
    }
}


