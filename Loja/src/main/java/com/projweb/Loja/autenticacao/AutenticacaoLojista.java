package com.projweb.Loja.autenticacao;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = { "/homeCliente.html", "/verCarrinho" })
public class AutenticacaoLojista implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        
        // Convertendo os objetos de ServletRequest e ServletResponse para objetos HttpServletRequest e HttpServletResponse
        HttpServletResponse response = ((HttpServletResponse) servletResponse);
        HttpServletRequest request = ((HttpServletRequest) servletRequest);

        // Mensagem de log para indicar que o filtro foi ativado
        System.out.println("Filtro ativado");

        // Obtendo a sessão do usuário
        HttpSession session = request.getSession(false);

        // Verificando se a sessão existe
        if (session == null) {
            // Redirecionando para a página de login caso não haja sessão
            response.sendRedirect("index.html?msg= Faça login para continuar");
        } else {
            // Verificando se o cliente está logado (o atributo da sessão pode variar dependendo da implementação)
            Boolean clienteLogado = (Boolean) session.getAttribute("clienteLogado");
            if (clienteLogado != null && clienteLogado.booleanValue()) {
                // Redirecionando para a página do cliente caso esteja logado como cliente
                response.sendRedirect("homeCliente.html?msg= Faça logout como lojista antes");
            }
        }

        // Continuando o fluxo do filtro
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
