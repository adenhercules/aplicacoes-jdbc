    package exemplo1.conexao;

//import com.mysql.jdbc.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaMySQL {
    public static void main (String[] args) throws SQLException {
        Connection conexao = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/agenda";
            String usuario = "root";
            String senha = "adenserver";
            
            conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conectou!");
        }catch (ClassNotFoundException e){
            System.out.println ("Classe não encontrada. Erro: " + e.getMessage());
        }catch(SQLException e){
            System.out.println ("Ocorreu um erro de SQL. ERRO: " + e.getMessage());
        } finally {
            try{ 
                conexao.close();
            }catch (SQLException e){
                System.out.println("Erro ao fechar a conexão. Erro: " + e.getMessage());
                
            }
             
        }
    }
}

