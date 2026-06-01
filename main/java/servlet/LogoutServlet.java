package servlet;

import controladorEventos.EventosController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        EventosController controller =
                (EventosController) getServletContext().getAttribute("controller");
        controller.logout();
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/index.html");
    }
}
