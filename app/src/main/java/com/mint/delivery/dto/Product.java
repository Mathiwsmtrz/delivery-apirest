package com.mint.delivery.dto;

public class Product {
    private Integer id;
    private String nombres;
    private Integer existencias;
    private Integer iva;
    private Integer valor;

    public Product(Integer id, String nombres, Integer existencias, Integer iva, Integer valor) {
        this.id = id;
        this.nombres = nombres;
        this.existencias = existencias;
        this.iva = iva;
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Integer getExistencias() {
        return existencias;
    }

    public void setExistencias(Integer existencias) {
        this.existencias = existencias;
    }

    public Integer getIva() {
        return iva;
    }

    public void setIva(Integer iva) {
        this.iva = iva;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }
}
