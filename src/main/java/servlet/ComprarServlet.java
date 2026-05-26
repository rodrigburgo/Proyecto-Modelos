package servlet;

import controladorEventos.EventosController;
import logica.Evento;
import logica.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/comprar")
public class ComprarServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        EventosController controller =
                (EventosController) getServletContext().getAttribute("controller");

        int id = Integer.parseInt(req.getParameter("id"));
        Evento evento = controller.getEventoPorId(id);
        if (evento == null) {
            resp.sendRedirect(req.getContextPath() + "/eventos");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println(generarFormCompra(evento, usuario, req));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        EventosController controller =
                (EventosController) getServletContext().getAttribute("controller");

        // Sync usuario actual en el controller con la sesión
        Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");
        controller.login(usuarioSesion.getEmail(), "123456"); // demo password

        int id = Integer.parseInt(req.getParameter("eventoId"));
        Evento evento = controller.getEventoPorId(id);

        String asiento = req.getParameter("asiento");
        String tarjeta = req.getParameter("tarjeta");
        String tipo = req.getParameter("tipo");
        boolean vip = "on".equals(req.getParameter("vip"));
        boolean seguro = "on".equals(req.getParameter("seguro"));

        String resultado = controller.comprarBoleta(evento, asiento, tarjeta, tipo, vip, seguro);

        String msgEnc = java.net.URLEncoder.encode(resultado, "UTF-8");
        resp.sendRedirect(req.getContextPath() + "/eventos?msg=" + msgEnc);
    }

    private String generarFormCompra(Evento evento, Usuario usuario, HttpServletRequest req) {
        String ctx = req.getContextPath();
        StringBuilder sb = new StringBuilder();

        sb.append("<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'>")
          .append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>")
          .append("<title>Comprar Boleta - ").append(evento.getNombre()).append("</title>")
          .append("<link rel='stylesheet' href='").append(ctx).append("/css/style.css'>")
          .append("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css'>")
          .append("</head><body>");

        // Navbar
        sb.append("<nav class='navbar'>")
          .append("<div class='nav-brand'><i class='fas fa-ticket-alt'></i> EventosMaster</div>")
          .append("<div class='nav-links'>")
          .append("<a href='").append(ctx).append("/eventos'><i class='fas fa-arrow-left'></i> Volver</a>")
          .append("<span class='nav-user'><i class='fas fa-user-circle'></i> ").append(usuario.getNombre()).append("</span>")
          .append("<a href='").append(ctx).append("/logout' class='btn-logout'><i class='fas fa-sign-out-alt'></i> Salir</a>")
          .append("</div></nav>");

        sb.append("<div class='container compra-container'>");

        // Info del evento
        String emoji = evento.getCategoria().equalsIgnoreCase("concierto") ? "🎵" :
                      evento.getCategoria().equalsIgnoreCase("deportivo") ? "⚽" : "🎭";

        sb.append("<div class='compra-grid'>")
          .append("<div class='evento-resumen'>")
          .append("<div class='evento-resumen-header'>")
          .append("<span class='emoji-grande'>").append(emoji).append("</span>")
          .append("<div>")
          .append("<h2>").append(evento.getNombre()).append("</h2>")
          .append("<span class='cat-tag'>").append(evento.getCategoria()).append("</span>")
          .append("</div></div>")
          .append("<div class='resumen-detalles'>")
          .append("<div class='detalle-item'><i class='fas fa-calendar-alt'></i><span>").append(evento.getFecha()).append("</span></div>")
          .append("<div class='detalle-item'><i class='fas fa-map-marker-alt'></i><span>").append(evento.getLugar()).append("</span></div>")
          .append("<div class='detalle-item'><i class='fas fa-users'></i><span>")
          .append(evento.getCapacidad() - evento.getEntradasVendidas()).append(" entradas disponibles</span></div>")
          .append("<div class='detalle-item precio-detalle'><i class='fas fa-tag'></i><span>Precio base: $")
          .append(String.format("%,.0f", evento.calcularPrecio())).append(" COP</span></div>")
          .append("</div>")
          .append("<p class='evento-desc-full'>").append(evento.getDescripcion()).append("</p>")
          .append("</div>");

        // Formulario
        sb.append("<div class='form-compra'>")
          .append("<h3><i class='fas fa-shopping-cart'></i> Datos de Compra</h3>")
          .append("<form action='").append(ctx).append("/comprar' method='post'>")
          .append("<input type='hidden' name='eventoId' value='").append(evento.getId()).append("'>")

          .append("<div class='form-group'>")
          .append("<label><i class='fas fa-chair'></i> Número de asiento</label>")
          .append("<input type='text' name='asiento' placeholder='Ej: A-15' required class='form-input'>")
          .append("</div>")

          .append("<div class='form-group'>")
          .append("<label><i class='fas fa-credit-card'></i> Número de tarjeta (16 dígitos)</label>")
          .append("<input type='text' name='tarjeta' placeholder='1234 5678 9012 3456' maxlength='19' class='form-input' id='tarjetaInput'>")
          .append("</div>")

          .append("<div class='form-group'>")
          .append("<label><i class='fas fa-ticket-alt'></i> Tipo de boleta</label>")
          .append("<select name='tipo' class='form-input'>")
          .append("<option value='Normal'>Normal</option>")
          .append("<option value='VIP'>VIP</option>")
          .append("</select>")
          .append("</div>")

          .append("<div class='form-group checkboxes'>")
          .append("<label class='check-label'><input type='checkbox' name='vip'> <span>🌟 Agregar paquete VIP (+$50.000)</span></label>")
          .append("<label class='check-label'><input type='checkbox' name='seguro'> <span>🛡️ Agregar seguro de reembolso (+$10.000)</span></label>")
          .append("</div>")

          .append("<div class='precio-dinamico' id='precioFinal'>")
          .append("Total estimado: <strong>$").append(String.format("%,.0f", evento.calcularPrecio())).append(" COP</strong>")
          .append("</div>")

          .append("<button type='submit' class='btn-confirmar'><i class='fas fa-lock'></i> Confirmar Compra</button>")
          .append("</form>")
          .append("</div></div></div>");

        sb.append("<script>")
          .append("var precioBase=").append(evento.calcularPrecio()).append(";")
          .append("function actualizar(){var t=document.querySelector('[name=vip]').checked;")
          .append("var s=document.querySelector('[name=seguro]').checked;")
          .append("var total=precioBase+(t?75000:0)+(s?10000:0);")
          .append("document.getElementById('precioFinal').innerHTML='Total estimado: <strong>$'+total.toLocaleString('es-CO')+' COP</strong>';}")
          .append("document.querySelectorAll('input[type=checkbox]').forEach(function(c){c.addEventListener('change',actualizar);});")
          .append("var ti=document.getElementById('tarjetaInput');")
          .append("ti.addEventListener('input',function(){this.value=this.value.replace(/[^\\d]/g,'').replace(/(\\d{4})/g,'$1 ').trim().substring(0,19);});")
          .append("</script>");

        sb.append("<script src='").append(ctx).append("/js/app.js'></script></body></html>");
        return sb.toString();
    }
}
