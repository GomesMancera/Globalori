package watteco.entidades;

public class Relatorio {
    private int id;
    private int usuarioId;
    private double consumoTotal;
    private double emissaoTotal;

    public Relatorio() {}

    public Relatorio(int id, int usuarioId, double consumoTotal, double emissaoTotal) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.consumoTotal = consumoTotal;
        this.emissaoTotal = emissaoTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public double getConsumoTotal() {
        return consumoTotal;
    }

    public void setConsumoTotal(double consumoTotal) {
        this.consumoTotal = consumoTotal;
    }

    public double getEmissaoTotal() {
        return emissaoTotal;
    }

    public void setEmissaoTotal(double emissaoTotal) {
        this.emissaoTotal = emissaoTotal;
    }
}
