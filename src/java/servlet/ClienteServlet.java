/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import com.google.gson.Gson;
import dao.DaoCliente;
import dao.impl.DaoClienteImpl;
import dto.Cliente;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author carlr
 */
@WebServlet(name = "ClienteServlet", urlPatterns = {"/cliente"})
public class ClienteServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Gson myGson = new Gson();
        DaoCliente myDaoCliente = new DaoClienteImpl();
        // URI ?dni=4567890
        String dni = request.getParameter("holaMundo");
        // int codigo = Integer.parseInt(request.getParameter("dniParametro"));
        String respuesta;
        if (dni != null) {
            Cliente myCliente = myDaoCliente.obtenerCliente(dni);
            respuesta = myGson.toJson(myCliente);
        } else {
            List<Cliente> myListaCliente = myDaoCliente.obtenerClientes();
            respuesta = myGson.toJson(myListaCliente);
        }

        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println(respuesta);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Gson myGson = new Gson();
        /*
        {
            "nombre": "Luisito",
            "apellido": "Rodriguez",
            "dni": "45671890",
            "direccion": "Av. Olo"
        }
         */
        Cliente myCliente = myGson.fromJson(request.getReader(), Cliente.class);

        DaoCliente myDaoCliente = new DaoClienteImpl();

        boolean resultado = myDaoCliente.insertarCliente(myCliente);

        String resultadoString;

        if (resultado) {
            resultadoString = "{ \"msg\": \"Se registró correctamente\" }";
        } else {
            resultadoString = "{ \"msg\": \"Error\" }";
        }

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println(resultadoString);
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson myGson = new Gson();
        /*
        {
            "nombre": "Luisito",
            "apellido": "Rodriguez",
            "dni": "45671890",
            "direccion": "Av. Olo"
        }
         */
        Cliente myCliente = myGson.fromJson(req.getReader(), Cliente.class);

        DaoCliente myDaoCliente = new DaoClienteImpl();

        boolean resultado = myDaoCliente.actualizarCliente(myCliente);

        String resultadoString;

        if (resultado) {
            resultadoString = "{ \"msg\": \"Se registró correctamente\" }";
        } else {
            resultadoString = "{ \"msg\": \"Error\" }";
        }

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println(resultadoString);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson myGson = new Gson();
        DaoCliente myDaoCliente = new DaoClienteImpl();
        String my_dni = req.getParameter("dni");
        String my_estado = req.getParameter("estado");

        boolean resultado = myDaoCliente.habilitarCliente(my_estado, my_dni);

        String resultadoString;

        if (resultado) {
            resultadoString = "{ \"msg\": \"Se cambio de correctamente\" }";
        } else {
            resultadoString = "{ \"msg\": \"Error\" }";
        }

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println(resultadoString);
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
