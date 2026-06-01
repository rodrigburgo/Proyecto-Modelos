package logica.patrones.estructurales.decorator;

import logica.Boleta;

public class SeguroDecorator extends BeneficioDecorator {
    public SeguroDecorator(Boleta boleta) { super(boleta); }

    @Override
    public String getDescripcion() {
        return boleta.getDescripcion() + " + Seguro de reembolso";
    }

    @Override
    public double getPrecio() { return boleta.getPrecio() + 10.0; }
}
