package main.caixaatacado;

public class ItemDeCompra {
    private int id;
    private String nome;
    private double preco;

    public ItemDeCompra(int id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }
}
