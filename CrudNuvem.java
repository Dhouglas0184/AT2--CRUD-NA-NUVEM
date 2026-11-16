
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

// Aluno: Dhouglas Rafael Gomes Pereira
// RA: 0024112
// CRUD de produtos com banco de dados MySQL no Railway

public class CrudNuvem {

    public static void main(String[] args) {

        // CONEXAO COM MYSQL NO RAILWAY

    	String url = "jdbc:mysql://iriguchi.proxy.rlwy.net:53485/railway";
    	String usuariobanco = "root";
    	String bancosenha = "rGTmIEWerMkXEVPMHHKItpYJudzdLOQl";

        Scanner sc = new Scanner(System.in);

        try {

            // Carrega o driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Conecta ao banco de dados
            Connection conexao = DriverManager.getConnection(
                url, usuariobanco, bancosenha
            );

            System.out.println("Conectado ao banco na nuvem!");

            // Cria a tabela automaticamente
            String sqlTabela = "CREATE TABLE IF NOT EXISTS PRODUTO ("
                    + "CODIGO INT PRIMARY KEY, "
                    + "NOME VARCHAR(100), "
                    + "PRECO DECIMAL(10,2))";

            conexao.createStatement().executeUpdate(sqlTabela);

            int opcao = -1;

            while (opcao != 0) {

                System.out.println("\n===== CRUD DE PRODUTOS =====");
                System.out.println("1 - Inserir");
                System.out.println("2 - Listar");
                System.out.println("3 - Atualizar");
                System.out.println("4 - Deletar");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opcao: ");

                opcao = sc.nextInt();

                switch (opcao) {

                    // INSERIR
                    case 1:

                        System.out.print("Codigo do produto: ");
                        int codigo = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Nome do produto: ");
                        String nome = sc.nextLine();

                        System.out.print("Preco do produto: ");
                        double preco = sc.nextDouble();

                        String sql = "INSERT INTO PRODUTO "
                                + "(CODIGO, NOME, PRECO) VALUES (?, ?, ?)";

                        PreparedStatement stmt =
                                conexao.prepareStatement(sql);

                        stmt.setInt(1, codigo);
                        stmt.setString(2, nome);
                        stmt.setDouble(3, preco);

                        stmt.executeUpdate();

                        System.out.println("Produto inserido com sucesso!");

                        stmt.close();

                        break;

                    // LISTAR
                    case 2:

                        System.out.print("Digite o codigo do produto: ");
                        int codigo2 = sc.nextInt();

                        String sql2 =
                                "SELECT * FROM PRODUTO WHERE CODIGO = ?";

                        PreparedStatement stmt2 =
                                conexao.prepareStatement(sql2);

                        stmt2.setInt(1, codigo2);

                        ResultSet rs = stmt2.executeQuery();

                        if (rs.next()) {

                            System.out.println("\n===== DADOS DO PRODUTO =====");
                            System.out.println("Codigo: "
                                    + rs.getInt("CODIGO"));

                            System.out.println("Nome: "
                                    + rs.getString("NOME"));

                            System.out.println("Preco: "
                                    + rs.getBigDecimal("PRECO"));

                        } else {

                            System.out.println("Produto nao localizado!");

                        }

                        rs.close();
                        stmt2.close();

                        break;

                    // ATUALIZAR
                    case 3:

                        System.out.print(
                                "Codigo do produto para atualizar: ");

                        int codPesquisa = sc.nextInt();

                        System.out.print("Digite o novo preco: ");
                        double novoPreco = sc.nextDouble();

                        String sqlUpd =
                                "UPDATE PRODUTO SET PRECO = ? WHERE CODIGO = ?";

                        PreparedStatement stmtUp =
                                conexao.prepareStatement(sqlUpd);

                        stmtUp.setDouble(1, novoPreco);
                        stmtUp.setInt(2, codPesquisa);

                        int alterados = stmtUp.executeUpdate();

                        if (alterados > 0) {

                            System.out.println(
                                    "Preco atualizado com sucesso!");

                        } else {

                            System.out.println("Produto nao localizado!");

                        }

                        stmtUp.close();

                        break;

                    // DELETAR
                    case 4:

                        System.out.print(
                                "Codigo do produto para deletar: ");

                        int codDelete = sc.nextInt();

                        String sqlDel =
                                "DELETE FROM PRODUTO WHERE CODIGO = ?";

                        PreparedStatement stmtDel =
                                conexao.prepareStatement(sqlDel);

                        stmtDel.setInt(1, codDelete);

                        int deletados = stmtDel.executeUpdate();

                        if (deletados > 0) {

                            System.out.println(
                                    "Produto deletado com sucesso!");

                        } else {

                            System.out.println("Produto nao localizado!");

                        }

                        stmtDel.close();

                        break;

                    // SAIR
                    case 0:

                        System.out.println("Saindo do sistema...");

                        break;

                    default:

                        System.out.println("Opcao invalida!");

                }

            }

            conexao.close();
            sc.close();

        } catch (Exception e) {

            System.out.println("Erro: " + e.getMessage());

        }

    }

}
