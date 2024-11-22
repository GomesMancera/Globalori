package watteco.entidades;

public class Dispositivo {
    private int id;
    private String nome;
    private String tipo;
    private String status;

    public Dispositivo() {}

    public Dispositivo(int id, String nome, String tipo, String status) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
