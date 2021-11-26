/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo1.crudjdbc;

//import model.Contato;
//import util.ConnectionFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ContatoCrudJDBC {
    
    
    
    public void salvar(Contato contato) {
        
        Connection conexao = this.geraConexao();
        PreparedStatement insereSt = null;
        String sql = "insert into contato(nome, cpf, telefone, email, data_cadastro, observacao, codigo) value(?,?,?,?,?,?,?)";
        try {
              
            insereSt = conexao.prepareStatement(sql);
            insereSt.setString(1, contato.getNome());
            insereSt.setString(2, contato.getCpf());
            insereSt.setString(3, contato.getTelefone());
            insereSt.setString(4, contato.getEmail());
            insereSt.setDate(5, contato.getDataCadastro());
            insereSt.setString(6, contato.getObservacao());
            insereSt.setInt(7, contato.getCodigo());
            insereSt.executeUpdate();
            
     
            
        } catch(SQLException e) {
            System.out.println("Erro ao salvar contato: " + e.getMessage());
        } finally {
           try {
               insereSt.close();
               conexao.close();
               
           } catch (Throwable e){
               System.out.println ("Erro ao fechar operações de inserir. Mensagem: " + e.getMessage());
           }
        }
    }
    
    public void atualizar(Contato contato) {
        
        Connection conexao = this.geraConexao();
         PreparedStatement atualizaSt = null;   
        String sql = "update contato set nome=?, cpf=?, telefone=?, email=?,observacao= ? where codigo=? ";
            
        
        try {
            
            atualizaSt = conexao.prepareStatement(sql);
            atualizaSt.setString(1, contato.getNome());
            atualizaSt.setString(2, contato.getCpf());
            atualizaSt.setString(3, contato.getTelefone());
            atualizaSt.setString(4, contato.getEmail());
            atualizaSt.setString(5, contato.getObservacao());
            atualizaSt.setInt(6, contato.getCodigo());
      
            atualizaSt.executeUpdate();
            
            atualizaSt.executeUpdate();
            
            atualizaSt.close();
            conexao.close();
            
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar contato: " + e.getMessage());
        }
    }
    
    public void excluir(Contato contato) {
        
        Connection conexao = this.geraConexao();
         PreparedStatement excluiSt = null;   
        String sql = "delete from contato where codigo = ?";
        
        try {
            excluiSt = conexao.prepareStatement(sql);
            excluiSt.setInt(1, contato.getCodigo().intValue());
            
            excluiSt.executeUpdate();  
        } catch (SQLException e) {
            System.out.println("Erro ao excluir contato: " + e.getMessage());
        } finally{
                try{
                    excluiSt.close();
                    conexao.close();
        } catch(Throwable e){
               System.out.println ("Erro ao fechar operaões de exclusão. Mensagem: " + e.getMessage()); 
        }
    }
 }
    
    public Contato contatoPeloId(Integer valor) {
        
             
            Connection conexao= this.geraConexao();
            PreparedStatement consulta = null;
            ResultSet resultado = null;
            Contato contato = null;
            
            String sql = "select * from contato where codigo = ?";
        try {
            consulta = conexao.prepareStatement(sql);
            consulta.setInt(1, valor);
            resultado = consulta.executeQuery();
            
            if (resultado.next()) {
                contato = new Contato();
                contato.setCodigo(resultado.getInt("codigo"));
                contato.setNome(resultado.getString("nome"));
                contato.setTelefone(resultado.getString("telefone"));
                contato.setEmail(resultado.getString("email"));
                contato.setDataCadastro(resultado.getDate("data_cadastro"));
                contato.setObservacao(resultado.getString("observacao"));
                contato.setCpf(resultado.getString("cpf"));
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao buscar contato: " + e.getMessage());
          
        }finally{
            try{
                consulta.close();
                resultado.close();
                conexao.close();
            }catch(Throwable e){
                System.out.println("Erro ao fechar operações de consulta. Mensagem: " + e.getMessage());
            }
        }
return contato;
 }
    

    
    public List<Contato> listar() {
        Connection conexao = this.geraConexao();
        List<Contato> listaContato = new ArrayList<>();
        Statement consulta = null;    
        ResultSet resultado = null;
        Contato contato = null;        
        String sql = "select * from contato";
        
        try {
       
            consulta=conexao.createStatement();
            resultado = consulta.executeQuery(sql);
            
            while (resultado.next()) {
                contato = new Contato();
                contato.setCodigo(resultado.getInt("codigo"));
                contato.setNome(resultado.getString("nome"));
                contato.setTelefone(resultado.getString("telefone"));
                contato.setEmail(resultado.getString("email"));
                contato.setDataCadastro(resultado.getDate("data_cadastro"));
                contato.setObservacao(resultado.getString("observacao"));
                contato.setCpf(resultado.getString("cpf"));
                
                listaContato.add(contato);
            }
              
        } catch (SQLException e) {
            System.out.println("Erro ao listar contatos: " + e.getMessage());
        } finally{
            try{
             consulta.close();
            resultado.close();
            conexao.close();
            } catch (Throwable e){
                System.out.println("Erro ao fechar operações de consulta. Menssagem: " + e.getMessage());
                
            }
            return listaContato;
        }
    }
    
 
    public Connection geraConexao(){
        Connection conexao = null;
        try {
            Class.forName ("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/agenda";
            String usuario = "root";
            String senha = "adenserver";
            conexao = DriverManager.getConnection(url,usuario,senha);
        }catch(ClassNotFoundException e){
            System.out.println("Classe não encontrada. Erro: " + e.getMessage());
        }catch (SQLException e){
            System.out.println("ocorrreu um erro de SQL. ERRO: " + e.getMessage());
            
        }
        return conexao;
    }
   
public static void main(String[] args) {

        ContatoCrudJDBC contatoCrudJDBC = new ContatoCrudJDBC();
        Scanner entrada = new Scanner(System.in);
        
        Contato fulano = new Contato();
        Contato beltrano = new Contato();
        System.out.println("S - Salvar");
        System.out.println("A - Alterar");
        System.out.println("E - Excluir");
        fulano.setCodigo(1);
        beltrano.setCodigo(2);
        String opcao;
        opcao = entrada.next();
        switch (opcao) {
            case "S":

                fulano.setNome("Pablo Nunes Vargas");
                fulano.setDataCadastro(new Date(System.currentTimeMillis()));
                fulano.setCpf("80032341");
                fulano.setEmail("pablo@gmail");
                fulano.setTelefone("(99)000-0000");
                fulano.setObservacao("Cliente Antigo");
                contatoCrudJDBC.salvar(fulano);

                beltrano.setNome("marcelin");
                beltrano.setDataCadastro(new Date(System.currentTimeMillis()));
                beltrano.setCpf("65742");
                beltrano.setEmail("marcellin@gmail");
                beltrano.setTelefone("(67)888-8888");
                beltrano.setObservacao("Cliente Novo");
                contatoCrudJDBC.salvar(beltrano);
                

                System.out.println("Contatos cadastrados:" + contatoCrudJDBC.listar().size());
                break;
            case "A":
                fulano.setNome("Aden Hercules");
                fulano.setCpf("000000000");
                fulano.setEmail("adenhercues@gmail.com");
                fulano.setTelefone("22222222");
                fulano.setObservacao("cliente Atualizado");
                contatoCrudJDBC.atualizar(fulano);

                break;
            case "E":
                contatoCrudJDBC.excluir(fulano);
                System.out.println("Contatos cadastrados:" + contatoCrudJDBC.listar().size());
                break;

            default: {
                System.out.println("Opção inválida");
                break;
            }

        }

    }
}