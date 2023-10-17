package main.caixaatacado;

public class CompraItem {
    private ItemDeCompra item;
    private int quantidade;

    public CompraItem(ItemDeCompra item, int quantidade) {
        this.item = item;
        this.quantidade = quantidade;
    }

    public ItemDeCompra getItem() {
        return item;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
