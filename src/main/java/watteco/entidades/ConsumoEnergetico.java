package watteco.entidades;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class ConsumoEnergetico {
    private int id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    private double valor;
    private int usuarioId;

    public ConsumoEnergetico() {
    }

    public ConsumoEnergetico(int id, LocalDate data, double valor, int usuarioId) {
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.usuarioId = usuarioId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
}
