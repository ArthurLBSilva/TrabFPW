package com.projweb.Loja.dominio;

import com.projweb.Loja.aplicacao.Cliente;

import org.springframework.stereotype.Repository;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ClienteDAO {

    private Connection conexao;

    public ClienteDAO() {
        try {
            conexao = Conexao.getConnection();
        } catch (SQLException | URISyntaxException ex) {
            ex.printStackTrace();
        }
    }

    public Cliente verificarLogin(String email, String senha) {
        ResultSet rs = null;
        Cliente c = null;
    
        try {
            String sql = "SELECT * FROM \"cliente\" WHERE email = ? AND senha = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, senha);
    
            rs = ps.executeQuery();
            if (rs.next()) {
                c = new Cliente(rs.getString("nome"), rs.getString("email"), rs.getString("senha"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    
        return c;
    }
    

    public boolean verificarEmailExistente(String email) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT COUNT(*) FROM \"cliente\" WHERE email = ?";
            ps = conexao.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void registrarCliente(Cliente cliente) {
        PreparedStatement ps = null;

        try {
            String sql = "INSERT INTO \"cliente\" (nome, email, senha) VALUES (?, ?, ?)";
            ps = conexao.prepareStatement(sql);
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getSenha());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Cliente verificarCredenciais(String email, String senha) {
        throw new UnsupportedOperationException("Unimplemented method 'verificarCredenciais'");
    }

}