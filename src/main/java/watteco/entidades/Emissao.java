package watteco.entidades;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class Emissao {
    private int id;
    private double valor;
    private String tipo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    public Emissao() {}

    public Emissao(int id, double valor, String tipo, LocalDate data) {
        this.id = id;
        this.valor = valor;
        this.tipo = tipo;
        this.data = data;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
