package com.projweb.Loja.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.projweb.Loja.aplicacao.Cliente;
import com.projweb.Loja.dominio.ClienteDAO;

import java.io.IOException;

@Controller
public class CadastroController {

    // Método para processar o cadastro do cliente
    @RequestMapping(value = "/cadastro", method = RequestMethod.POST)
    public void doCadastro(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Obtendo os parâmetros do formulário de cadastro
        var nome = request.getParameter("nome");
        var login = request.getParameter("login");
        var senha = request.getParameter("senha");

        // Verificando se todos os campos foram preenchidos
        if (nome != null && !nome.isEmpty() && login != null && !login.isEmpty() &&
                senha != null && !senha.isEmpty()) {
            
            // Criando uma instância do ClienteDAO para interagir com o banco de dados
            ClienteDAO cDAO = new ClienteDAO();
            
            // Criando um objeto Cliente com os dados fornecidos
            Cliente c = new Cliente(nome, login, senha);
            try {
                // Registrando o cliente no banco de dados
                cDAO.registrarCliente(c);
                // Redirecionando para a página inicial com uma mensagem de sucesso
                response.sendRedirect("index.html?msg=Cadastrado com sucesso!");

            } catch (Exception e) {
                // Imprimindo qualquer exceção ocorrida no console (pode ser removido em produção)
                System.out.println(e.getMessage());
                // Redirecionando para a página de cadastro com uma mensagem de erro
                response.sendRedirect("cadastro.html?msg=Erro no cadastro");
            }
        } else {
            // Redirecionando para a página de cadastro com uma mensagem de erro caso algum campo esteja vazio
            response.sendRedirect("cadastro.html?erro=Preencha todos os campos");
        }

    }

}
