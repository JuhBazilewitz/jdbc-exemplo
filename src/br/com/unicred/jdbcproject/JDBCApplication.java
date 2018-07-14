package br.com.unicred.jdbcproject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JOptionPane;

public class JDBCApplication {

    public static void main(String[] args) throws SQLException {

        System.out.println("Preparando conexão...");
        String conexao = "jdbc:sqlserver://UBR3144\\SQLEXPRESS; databaseName=db_curso_unicred; user=sa;password=xongas_1234;";
        Connection con = DriverManager.getConnection(conexao);

//        executaProcedure(con);
        executaSelect(con);

        con.close();
    }
    
    private static void executaSelect(Connection con) throws SQLException {
        System.out.println("Criando consulta sql...");
        String sql = "select * from dbo.cliente";
        PreparedStatement ps = con.prepareStatement(sql);
        
        System.out.println("Executanto consulta sql...");
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()) {
            System.out.println("Nome: " + rs.getString("nm_cliente"));
        }
        
        System.out.println("Fechando conexão...");
        rs.close();
        ps.close();
    }
    
    private static void executaProcedure(Connection con) throws SQLException {
        System.out.println("Preparando procedure...");
        String sql = "{call dbo.PR_INS_CLIENTE (?, ?, ?, ?)}";
        CallableStatement statement = con.prepareCall(sql);
        statement.setInt(1, 321);
        statement.setString(2, "Marcio Andrade");
        statement.setString(3, "Cristóvão Colombo");
        statement.registerOutParameter(4, Types.VARCHAR); //Parâmetro de saída.
        
        System.out.println("Executanto procedure...");
        statement.executeUpdate();
        String mensagem = statement.getString(4);
        JOptionPane jPane = new JOptionPane();
        jPane.setMessage(mensagem);
        
        System.out.println("Fechando conexão...");
        statement.close();
    }
}
