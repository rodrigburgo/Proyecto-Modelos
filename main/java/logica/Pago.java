package logica;

public class Pago {
    private static Pago instancia;

    private Pago() {}

    public static Pago getInstancia() {
        if (instancia == null) instancia = new Pago();
        return instancia;
    }

    public boolean procesarPago(double monto, String tarjeta) {
        // Simulación: tarjeta válida si tiene 16 dígitos
        return tarjeta != null && tarjeta.replaceAll("\\s", "").length() == 16;
    }
}
