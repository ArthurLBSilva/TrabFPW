package com.projweb.Loja.controller;

import com.projweb.Loja.dominio.ProdutoDAO;
import com.projweb.Loja.aplicacao.Produto;
import org.springframework.stereotype.Controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.text.DecimalFormat;

@Controller
public class ClienteController {

    @RequestMapping(value = "/listarProdutosCliente", method = RequestMethod.GET)
    public void getAllProdutosCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        List<Produto> produtos = produtoDAO.listarProdutos();

        response.setContentType("text/html");
        var writer = response.getWriter();
        writer.println("<html> <head> <title> LISTA </title> <style>" +
                "table { border-collapse: collapse; width: 80%; margin: 0 auto; }" + 
                "th, td { border: 1px solid black; padding: 8px;}" +
                "th { background-color: #f2f2f2; }" +
                "h2 { text-align: center; }" +
                "button { background-color: #4CAF50; color: white; padding: 10px 20px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px; margin: 4px 2px; cursor: pointer; border-radius: 12px;}" +
                "</style></head> <body> <h2>LISTA</h2> <table>");

        writer.println("<tr>");
        writer.println("<th>Nome</th>");
        writer.println("<th>Descrição</th>");
        writer.println("<th>Preço</th>");
        writer.println("<th>Estoque</th>");
        writer.println("<th>Carrinho</th>");
        writer.println("</tr>");

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        for (Produto produto : produtos) {
            writer.println("<tr>");
            writer.println("<td>" + produto.getNome() + "</td>");
            writer.println("<td>" + produto.getDescricao() + "</td>");
            writer.println("<td>R$ " + decimalFormat.format(produto.getPreco()) + "</td>");
            if (produto.getQuantidade() == 0){
                writer.println("<td>Sem Estoque</td>");
            } else {
                writer.println("<td>" + produto.getQuantidade() + "</td>");
            }
            writer.println("<td><a href='/carrinhoServlet?id=" + produto.getId() + "&comando=add' class='button'>Adicionar</a></td>");

           
            writer.println("</tr>");
        }

        writer.println("<tr>");
        writer.println("<td colspan=\"5\" style=\"text-align: center;\">");
        writer.println("<button onclick=\"window.location.href='verCarrinho'\" class='button'>Carrinho</button>");
        writer.println("<button onclick=\"window.location.href='homeCliente.html'\" class='button'>Voltar</button></td>");
        writer.println("</tr>");

        writer.println("</body> </html>");

    }

}
