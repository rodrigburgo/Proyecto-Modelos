package logica.patrones.estructurales.decorator;

import logica.Boleta;

public abstract class BeneficioDecorator {
    protected Boleta boleta;

    public BeneficioDecorator(Boleta boleta) { this.boleta = boleta; }

    public abstract String getDescripcion();
    public abstract double getPrecio();
}
