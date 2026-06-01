package logica.patrones.estructurales.adapter;

public class PagoAdapter {
    private PagoExterno pagoExterno = new PagoExterno();

    public boolean procesarPagoConTarjeta(double monto, String tarjeta) {
        return pagoExterno.pagarConTarjeta(monto, tarjeta);
    }
}
