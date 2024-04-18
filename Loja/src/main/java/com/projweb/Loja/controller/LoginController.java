package com.projweb.Loja.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.projweb.Loja.aplicacao.Cliente;
import com.projweb.Loja.aplicacao.Lojista;
import com.projweb.Loja.dominio.ClienteDAO;
import com.projweb.Loja.dominio.LojistaDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void autenticar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var email = request.getParameter("email");
        var senha = request.getParameter("senha");

        ClienteDAO clienteDAO = new ClienteDAO();
        LojistaDAO lojistaDAO = new LojistaDAO();

        Cliente cliente = clienteDAO.verificarCredenciais(email, senha);
        Lojista lojista = lojistaDAO.verificarCredenciais(email, senha);

        if (cliente != null || lojista != null) {
            HttpSession session = request.getSession(true);

            System.out.println("Sessão iniciada para: " + email);

            if (cliente != null) {
                session.setAttribute("clienteLogado", true);
                email = email.replaceAll("@", "");
                response.sendRedirect("homeCliente.html");
            } else {
                session.setAttribute("lojistaLogado", true);
                response.sendRedirect("/homeLojista.html");
            }
        } else {
            response.sendRedirect("index.html?msg=Login falhou");
        }
    }

    @RequestMapping(value="/logout")
    public void encerrarSessao(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (session != null) {
            System.out.println("Sessão encerrada para: " + session.getAttribute("email"));
            session.invalidate();
        }
        response.sendRedirect("index.html?msg=Usuário desconectado");
    }
}
