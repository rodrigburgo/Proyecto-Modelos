package logica.patrones.estructurales.proxy;

import logica.Pago;

public class PagoProxy {
    private Pago pagoReal = Pago.getInstancia();
    private boolean autenticado = false;

    public void autenticar() { autenticado = true; }

    public boolean procesarPago(double monto) {
        if (!autenticado) return false;
        return pagoReal.procesarPago(monto, "1234567890123456"); // demo
    }
}
