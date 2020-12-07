package servicio.general;

public class AES {
    private final String KEY = "XA2j0s^u1^2%krB";

    public String decrypt(String campo){
        return "AES_DECRYPT(`" + campo + "`, '" + KEY + "')";
    }

    public String encrypt(){
        return "AES_ENCRYPT(?, '" + KEY + "')";
    }
}
