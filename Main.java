import java.util.*;
//Classe da conta bancária
class BankAccount {
    private String titular;
    private String numero;
    private double saldo;
    private List<String> extrato;

    public BankAccount(String titular, String numero) {
        this.titular = titular;
        this.numero = numero;
        this.saldo = 0.0;
        this.extrato = new ArrayList<>();
        extrato.add(String.format("%-20s %10s %10.2f", "Abertura da conta", "", saldo));
    }

    public String getTitular() { return titular; }
    public String getNumero() { return numero; }
    public double getSaldo() { return saldo; }

    public void depositar(double valor) {
        if (valor <= 0) throw new IllegalArgumentException("Valor de depósito deve ser positivo.");
        saldo += valor;
        extrato.add(String.format("%-20s %10.2f %10.2f", "Depósito", valor, saldo));
    }

    public void sacar(double valor) {
        if (valor <= 0) throw new IllegalArgumentException("Valor de saque deve ser positivo.");
        if (valor > saldo) throw new IllegalArgumentException("Saldo insuficiente.");
        saldo -= valor;
        extrato.add(String.format("%-20s %10.2f %10.2f", "Saque", -valor, saldo));
    }

    public void mostrarInformacoes() {
        System.out.println("=== Informações da Conta ===");
        System.out.println("Titular: " + titular);
        System.out.println("Número : " + numero);
        System.out.printf("Saldo  : R$ %.2f%n", saldo);
    }

    public void mostrarExtrato() {
        System.out.println("=== Extrato ===");
        System.out.printf("%-20s %10s %10s%n", "Movimento", "Valor", "Saldo");
        System.out.println("-----------------------------------------------");
        for (String linha : extrato) {
            System.out.println(linha);
        }
        System.out.println("-----------------------------------------------");
        System.out.printf("%-20s %10s %10.2f%n", "Saldo atual", "", saldo);
    }
}

public class Main {
    private static final Scanner scanner = new Scanner(System.in);


    private static void mostrarMenu() {
        System.out.println("====== MENU ======");
        System.out.println("1. Informações da conta");
        System.out.println("2. Extrato");
        System.out.println("3. Depósito");
        System.out.println("4. Saque");
        System.out.println("0. Sair");
    }

    public static void main(String[] args) {
        BankAccount conta = criarContaInicial();
        int opcao;
        do {
            limparTela();
            mostrarMenu();
            opcao = lerInteiro("Escolha uma opção: ");
            System.out.println();
            switch (opcao) {
                case 1:
                    conta.mostrarInformacoes();
                    pausar();
                    break;
                case 2:
                    conta.mostrarExtrato();
                    pausar();
                    break;
                case 3:
                    double dep = lerDouble("Valor do depósito: R$ ");
                    try {
                        conta.depositar(dep);
                        System.out.println("Depósito realizado com sucesso!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    pausar();
                    break;
                case 4:
                    double saque = lerDouble("Valor do saque: R$ ");
                    try {
                        conta.sacar(saque);
                        System.out.println("Saque realizado com sucesso!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    pausar();
                    break;
                case 0:
                    System.out.println("Saindo... Obrigado!");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    pausar();
            }
        } while (opcao != 0);
    }

    private static BankAccount criarContaInicial() {
        System.out.println("=== Criar Conta ===");
        System.out.print("Nome do titular: ");
        String nome = scanner.nextLine();
        if (nome.isBlank()) nome = "Titular Padrão";
        String numero = gerarNumeroConta();
        System.out.println("Conta criada! Número: " + numero);
        pausar();
        return new BankAccount(nome, numero);
    }

    private static String gerarNumeroConta() {
        Random r = new Random();
        int agencia = 1000 + r.nextInt(9000);
        int conta = 100000 + r.nextInt(900000);
        return agencia + "-" + conta;
    }

    private static int lerInteiro(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int n = Integer.parseInt(scanner.nextLine());
                return n;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    private static double lerDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double n = Double.parseDouble(scanner.nextLine().replace(",", "."));
                return n;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número (use vírgula ou ponto).");
            }
        }
    }

    private static void pausar() {
        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }

    private static void limparTela() {
        // tentativa simples de "limpar" a tela no console
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
