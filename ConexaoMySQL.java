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
                        System.out.println("Opcao invalida. Tente novamente.");
                        break;
                }
            } while (opcao != 5);

            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void inserirRegistro(Connection connection, Scanner scanner) {
        int idEndereco = -1;

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


        System.out.println("Nome da Rua: ");
        String nomeDaRua = scanner.nextLine();
        System.out.println("Numero: ");
        int numero = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Bairro: ");
        String bairro = scanner.nextLine();
        System.out.println("Complemento: ");
        String complemento = scanner.nextLine();
        System.out.println("CEP: ");
        String cep = scanner.nextLine();
        System.out.println("Cidade: ");
        String cidade = scanner.nextLine();

        String sqlEndereco = "INSERT INTO java_endereco (rua, numero, bairro, complemento, cep, cidade) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sqlEndereco, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, nomeDaRua);
            pstmt.setInt(2, numero);
            pstmt.setString(3, bairro);
            pstmt.setString(4, complemento);
            pstmt.setString(5, cep);
            pstmt.setString(6, cidade);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    idEndereco = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (idEndereco == -1) {
            System.out.println("Erro ao inserir endereco.");
            return;
        }

        String sqlConta = "INSERT INTO java_conta (nome, telefone, cpf, senha, data_cadastro, id_endereco) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sqlConta)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, telefone);
            pstmt.setString(3, cpf);
            pstmt.setString(4, senha);
            pstmt.setString(5, dataCadastro);
            pstmt.setInt(6, idEndereco);
            pstmt.executeUpdate();
            System.out.println("Registro inserido com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void alterarRegistro(Connection connection, Scanner scanner) {
        System.out.print("Qual o CPF da conta que deseja alterar: ");
        String alterarCpf = scanner.nextLine();
    
        
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
    

        System.out.println("Novo Nome da Rua: ");
        String nomeDaRua = scanner.nextLine();
        System.out.println("Novo Numero: ");
        int numero = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Novo Bairro: ");
        String bairro = scanner.nextLine();
        System.out.println("Novo Complemento: ");
        String complemento = scanner.nextLine();
        System.out.println("Novo CEP: ");
        String cep = scanner.nextLine();
        System.out.println("Nova Cidade: ");
        String cidade = scanner.nextLine();
    

        String sqlEndereco = "UPDATE java_endereco SET rua = ?, numero = ?, bairro = ?, complemento = ?, cep = ?, cidade = ? WHERE id_endereco = (SELECT id_endereco FROM java_conta WHERE cpf = ?)";
        try (PreparedStatement pstmtEndereco = connection.prepareStatement(sqlEndereco)) {
            pstmtEndereco.setString(1, nomeDaRua);
            pstmtEndereco.setInt(2, numero);
            pstmtEndereco.setString(3, bairro);
            pstmtEndereco.setString(4, complemento);
            pstmtEndereco.setString(5, cep);
            pstmtEndereco.setString(6, cidade);
            pstmtEndereco.setString(7, alterarCpf);
            pstmtEndereco.executeUpdate();
    
            int ColunasNovas = pstmtEndereco.executeUpdate();
        
        if (ColunasNovas == 0) {
            System.out.println("Erro ao atualizar endereço (CPF nao encontrado ou sem vinculo)");
            return;
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    

        String sqlConta = "UPDATE java_conta SET nome = ?, telefone = ?, cpf = ?, senha = ?, data_cadastro = ? WHERE cpf = ?";
        try (PreparedStatement pstmtConta = connection.prepareStatement(sqlConta)) {
            pstmtConta.setString(1, nome);
            pstmtConta.setString(2, telefone);
            pstmtConta.setString(3, cpf);
            pstmtConta.setString(4, senha);
            pstmtConta.setString(5, dataCadastro);
            pstmtConta.setString(6, alterarCpf);

            int colunasAtulizadas = pstmtConta.executeUpdate();
            if (colunasAtulizadas > 0) {
                System.out.println("Registro atualizado com sucesso!");
            } else {
                System.out.println("Nenhum registro encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void excluirRegistro(Connection connection, Scanner scanner) {
        System.out.print("Digite o CPF da conta que deseja excluir: ");
        String alterarCpf = scanner.nextLine();


        String sql = "DELETE FROM java_conta WHERE cpf = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, alterarCpf);
            int colunasDeletadas = pstmt.executeUpdate();
            if (colunasDeletadas > 0) {
                System.out.println("Registro excluido com sucesso!");
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

