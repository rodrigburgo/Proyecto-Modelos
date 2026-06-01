package logica.patrones.estructurales.adapter;

public class PagoExterno {
    public boolean pagarConTarjeta(double monto, String tarjeta) {
        return tarjeta != null && tarjeta.replaceAll("\\s", "").length() == 16;
    }
}
