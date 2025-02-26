package com.example.demo;

import com.example.demo.applications.LancheApplication;
import com.example.demo.entities.Lanche;
import com.example.demo.facade.LancheFacade;
import com.example.demo.repositories.LancheRepository;
import com.example.demo.services.LancheService;

import java.util.List;
import java.util.Scanner;

public class DemoApplication {
    private static LancheRepository lancheRepository;
    private static LancheService lancheService;
    private static LancheApplication lancheApplication;
    private static LancheFacade lancheFacade;

    private static void injetarDependencias() {
        lancheRepository = new LancheRepository();
        lancheService = new LancheService();
        lancheApplication = new LancheApplication(lancheService, lancheRepository);
        lancheFacade = new LancheFacade(lancheApplication);
    }

    public static void main(String[] args) {
        injetarDependencias();

        int codigo = 1;
        boolean quit = false;
        Scanner scanner = new Scanner(System.in);
        while (!quit) {
            System.out.println("""
                    1 - Cadastrar lanche
                    2 - Atualizar lanche
                    3 - Excluir lanche
                    4 - Listar lanches
                    5 - Comprar lanche
                    6 - Sair""");
            int op = scanner.nextInt();
            scanner.nextLine();

            switch (op) {
                case 1 -> {
                    System.out.println("Nome do lanche:");
                    String nome = scanner.nextLine();
                    System.out.println("Preço:");
                    double preco = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println("URL da imagem:");
                    String img_url = scanner.nextLine();

                    lancheFacade.cadastrar(new Lanche(codigo++, nome, preco, img_url));
                    System.out.println("Lanche cadastrado com sucesso\n");
                }
                case 2 -> {
                    System.out.println("Digite o código do lanche que deseja atualizar:");
                    int codAtualizar = scanner.nextInt();
                    scanner.nextLine();
                    Lanche lancheAtualizar = lancheFacade.buscarPorCodigo(codAtualizar);

                    if (lancheAtualizar != null) {
                        System.out.println("Novo nome do lanche:");
                        String novoNome = scanner.nextLine();
                        System.out.println("Novo preço:");
                        double novoPreco = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.println("Nova URL da imagem:");
                        String novaImgUrl = scanner.nextLine();

                        Lanche lancheAtualizado = new Lanche(codAtualizar, novoNome, novoPreco, novaImgUrl);
                        if (lancheFacade.atualizar(codAtualizar, lancheAtualizado)) {
                            System.out.println("Lanche atualizado com sucesso\n");
                        } else {
                            System.out.println("Erro ao atualizar o lanche\n");
                        }
                    } else {
                        System.out.println("Lanche não encontrado\n");
                    }
                }
                case 3 -> {
                    System.out.println("Digite o código do lanche que deseja excluir:");
                    int codExcluir = scanner.nextInt();
                    scanner.nextLine();
                    if (lancheFacade.excluir(codExcluir)) {
                        System.out.println("Lanche excluído com sucesso\n");
                    } else {
                        System.out.println("Erro ao excluir o lanche\n");
                    }
                }
                case 4 -> {
                    System.out.println("CÓDIGO\tNOME\t\tPRECO\n");
                    List<Lanche> lanches = lancheFacade.buscar();
                    for (Lanche l : lanches) {
                        System.out.println(l.getCodigo() + "\t\t" + l.getNome() + "\t\t" + l.getPreco() + "\n");
                    }
                }
                case 5 -> {
                    System.out.println("Digite o código do lanche:");
                    int cod = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Digite a quantidade:");
                    int qtd = scanner.nextInt();
                    scanner.nextLine();
                    Lanche lanche = lancheFacade.buscarPorCodigo(cod);

                    if (lanche != null) {
                        String total = String.format("%.2f", lancheFacade.calcularLanche(lanche, qtd));
                        System.out.println("Total: R$ " + total);
                    } else {
                        System.out.println("Lanche não encontrado\n");
                    }
                }
                case 6 -> quit = true;
                default -> System.out.println("Opção inválida\n");
            }
        }
        scanner.close();
    }
}