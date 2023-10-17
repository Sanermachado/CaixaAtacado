package main.caixaatacado;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CaixaDoAtacado {
    private List<CompraItem> compras;
    private MetodoDePagamento metodoDePagamento;
    private List<ItemDeCompra> cardapio;

    public CaixaDoAtacado() {
        this.compras = new ArrayList<>();
        this.cardapio = new ArrayList<>();
        this.cardapio.add(new ItemDeCompra(1, "Café 1kg", 5.00));
        this.cardapio.add(new ItemDeCompra(2, "Sabão em pó", 10.00));
        this.cardapio.add(new ItemDeCompra(3, "Caixa de Leite", 15.00));
        this.cardapio.add(new ItemDeCompra(4, "Refrigerante", 20.50));
    }
    public void adicionarCompra(CompraItem compraItem) {
        compras.add(compraItem);
    }

    public void setMetodoDePagamento(MetodoDePagamento metodoDePagamento) {
        this.metodoDePagamento = metodoDePagamento;
    }
    public double calcularValorTotal() {
        double valorTotal = 0;

        for (CompraItem compraItem : compras) {
            int quantidade = compraItem.getQuantidade();
            double precoUnitario = getPrecoItem(compraItem.getItem().getId());
            double precoComDesconto = calcularPrecoComDesconto(quantidade, precoUnitario);
            valorTotal += precoComDesconto * quantidade;
        }

        valorTotal = aplicarDescontoAcrescimoPorMetodoDePagamento(valorTotal);

        return valorTotal;
    }

    private double getPrecoItem(int itemId) {
        for (ItemDeCompra item : cardapio) {
            if (item.getId() == itemId) {
                return item.getPreco();
            }
        }
        return 0;
    }

    private double calcularPrecoComDesconto(int quantidade, double precoUnitario) {
        if (quantidade <= 5) {
            return precoUnitario;
        } else if (quantidade <= 15) {
            return precoUnitario * 0.9;  // 10% de desconto
        } else if (quantidade <= 25) {
            return precoUnitario * 0.8;  // 20% de desconto
        } else {
            return precoUnitario * 0.75;  // 25% de desconto
        }
    }
    private double aplicarDescontoAcrescimoPorMetodoDePagamento(double valorTotal) {
        switch (metodoDePagamento) {
            case DEBITO:
                // Não há desconto para pagamento com débito
                return valorTotal;
            case PILA:
                // 5% de desconto para pagamento em dinheiro
                return valorTotal * 0.95;
            case CREDITO:
                // 3% de acréscimo para pagamento com crédito
                return valorTotal * 1.03;
            default:
                return valorTotal;
        }
    }

    public double computarCompra(String metodoPagamento, List<String> compras) {
        MetodoDePagamento metodo = MetodoDePagamento.valueOf(metodoPagamento.toUpperCase());
        setMetodoDePagamento(metodo);

        for (String compra : compras) {
            String[] compraParts = compra.split(",");
            int itemId = Integer.parseInt(compraParts[0]);
            int quantidade = Integer.parseInt(compraParts[1]);

            ItemDeCompra item = getItemPorId(itemId);
            if (item != null) {
                adicionarCompra(new CompraItem(item, quantidade));
            } else {
                System.err.println("Item de compra inválido: " + itemId);
            }
        }
        return calcularValorTotal();
    }

    private ItemDeCompra getItemPorId(int itemId) {
        for (ItemDeCompra item : cardapio) {
            if (item.getId() == itemId) {
                return item;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("METODOS DE PAGAMENTO\nDebito : não há descontos\nPila : 5% de desconto\nCredito : 3% de acréscimo \nDigite o método de pagamento :");


        String metodoPagamento = scanner.nextLine().trim().toLowerCase();
        System.out.print("CARDAPIO\n");
        System.out.println("ID| Nome           | Preço\n" +
                "1 | Café 1kg       | 5,00\n" +
                "2 | Sabão em pó    | 10,00\n" +
                "3 | Caixa de Leite | 15,00\n" +
                "4 | Refrigerate    | 20,00\n");
        System.out.println("Digite as compras no formato 'Item , quantidade\nDigite 'fim' para terminar.");

        List<String> compras = new ArrayList<>();
        while (true) {
            System.out.print("Compra: ");
            String compra = scanner.nextLine().trim();
            if (compra.equalsIgnoreCase("fim")) {
                break;
            }
            compras.add(compra);
        }

        CaixaDoAtacado caixaDoAtacado = new CaixaDoAtacado();
        double valorTotal = caixaDoAtacado.computarCompra(metodoPagamento, compras);
        System.out.println("Valor total da compra: R$" + String.format("%.2f", valorTotal));

        scanner.close();
    }

}
