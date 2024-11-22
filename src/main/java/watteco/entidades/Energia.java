package watteco.entidades;

public class Energia {
    private int id;
    private String tipo;
    private double potencia;

    public Energia() {}

    public Energia(int id, String tipo, double potencia) {
        this.id = id;
        this.tipo = tipo;
        this.potencia = potencia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPotencia() {
        return potencia;
    }

    public void setPotencia(double potencia) {
        this.potencia = potencia;
    }
}
