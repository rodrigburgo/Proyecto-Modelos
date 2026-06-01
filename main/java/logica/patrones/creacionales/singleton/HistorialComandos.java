package logica.patrones.creacionales.singleton;

import logica.patrones.comportamiento.command.Comando;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class HistorialComandos {
    private Stack<Comando> historial = new Stack<>();

    public void ejecutar(Comando cmd) {
        cmd.ejecutar();
        historial.push(cmd);
    }

    public void deshacerUltimo() {
        if (!historial.isEmpty()) historial.pop().deshacer();
    }

    public List<String> getHistorial() {
        List<String> lista = new ArrayList<>();
        for (Comando c : historial) lista.add(c.getDescripcion());
        return lista;
    }
}
