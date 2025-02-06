import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class ConexaoMySQL {


    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://wagnerweinert.com.br:3306/tads24_giovanna";
        String username = "tads24_giovanna";
        String password = "tads24_giovanna";

        
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("Conexao estabelecida com sucesso!");
           
            Scanner scanner = new Scanner(System.in);
            int opcao;


            do {
                System.out.println("Menu:");
                System.out.println("1. Inserir Registro");
                System.out.println("2. Alterar Registro");
                System.out.println("3. Excluir Registro");
                System.out.println("4. Listar Registros");
                System.out.println("5. Sair");
                System.out.print("Escolha uma opcao: ");
                opcao = scanner.nextInt();
                scanner.nextLine();


                switch (opcao) {
                    case 1:
                        inserirRegistro(connection, scanner);
                        break;
                    case 2:
                        alterarRegistro(connection, scanner);
                        break;
                    case 3:
                        excluirRegistro(connection, scanner);
                        break;
                    case 4:
                        listarRegistros(connection);
                        break;
                    case 5:
                        System.out.println("Encerrando o programa.");
                        break;
                    default:
                        System.out.println("Opcao inválida. Tente novamente.");
                        break;
                }
            } while (opcao != 5);


            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void inserirRegistro(Connection connection, Scanner scanner) {


        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        System.out.print("Data de Cadastro (yyyy-mm-dd): ");
        String dataCadastro = scanner.nextLine();
        
        scanner.nextLine();


        String sql = "INSERT INTO java_conta (nome, telefone, cpf, senha, data_cadastro) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, telefone);
            pstmt.setString(3, cpf);
            pstmt.setString(4, senha);
            pstmt.setString(5, dataCadastro);
            pstmt.executeUpdate();
            System.out.println("Registro inserido com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void alterarRegistro(Connection connection, Scanner scanner) {
        System.out.print("ID do registro a alterar: ");
        int idConta = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Novo Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Novo Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Novo CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Nova Senha: ");
        String senha = scanner.nextLine();
        System.out.print("Nova Data de Cadastro (yyyy-mm-dd): ");
        String dataCadastro = scanner.nextLine();
        System.out.print("Novo ID do Endereço: ");
        int idEndereco = scanner.nextInt();
        scanner.nextLine();


        String sql = "UPDATE java_conta SET nome = ?, telefone = ?, cpf = ?, senha = ?, data_cadastro = ?, id_endereco = ? WHERE id_conta = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, telefone);
            pstmt.setString(3, cpf);
            pstmt.setString(4, senha);
            pstmt.setString(5, dataCadastro);
            pstmt.setInt(6, idEndereco);
            pstmt.setInt(7, idConta);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Registro atualizado com sucesso!");
            } else {
                System.out.println("Nenhum registro encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void excluirRegistro(Connection connection, Scanner scanner) {
        System.out.print("ID do registro a excluir: ");
        int idConta = scanner.nextInt();
        scanner.nextLine();


        String sql = "DELETE FROM java_conta WHERE id_conta = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idConta);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Registro excluído com sucesso!");
            } else {
                System.out.println("Nenhum registro encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void listarRegistros(Connection connection) {
        String sql = "SELECT id_conta, nome, telefone, cpf FROM java_conta";
        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {
            while (resultSet.next()) {
                int idConta = resultSet.getInt("id_conta");
                String nome = resultSet.getString("nome");
                String telefone = resultSet.getString("telefone");
                String cpf = resultSet.getString("cpf");
                System.out.println("ID Conta: " + idConta + ", Nome: " + nome + ", Telefone: " + telefone + ", CPF: " + cpf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


