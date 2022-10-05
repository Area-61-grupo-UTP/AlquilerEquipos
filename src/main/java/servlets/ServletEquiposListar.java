package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.EquipoController;

@WebServlet("/ServletEquiposListar")
public class ServletEquiposListar extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ServletEquiposListar() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EquipoController equipos = new EquipoController();
        boolean ordenar = Boolean.parseBoolean(request.getParameter("ordenar"));
        String orden=request.getParameter("orden");
        
        String equipoStr = equipos.listar(ordenar, orden);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println(equipoStr);
        out.flush();
        out.close();

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            doGet(request, response);
    }

 
}
