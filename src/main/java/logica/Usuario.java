package logica;

import logica.patrones.comportamiento.observer.EventoObserver;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements EventoObserver {
    private int id;
    private String nombre;
    private String email;
    private String password;
    private List<Boleta> boletas = new ArrayList<>();
    private List<String> notificaciones = new ArrayList<>();

    public Usuario(int id, String nombre, String email, String password) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    @Override
    public void actualizar(String evento, String mensaje) {
        notificaciones.add("[" + evento + "] " + mensaje);
    }

    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    public void agregarBoleta(Boleta b) { boletas.add(b); }
    public List<Boleta> getBoletas() { return boletas; }
    public List<String> getNotificaciones() { return notificaciones; }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }

    @Override
    public String toString() { return nombre + " (" + email + ")"; }
}
