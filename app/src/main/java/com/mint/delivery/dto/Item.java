package com.mint.delivery.dto;

public class Item {
    private Integer id;
    private Integer id_pedido;
    private Integer id_producto;
    private String nombres;
    private Integer existencias;
    private Integer iva;
    private Integer valor;
    private Double total;
    private Integer cantidad;

    public Item(Integer id, Integer id_pedido, Integer id_producto, String nombres, Integer existencias, Integer iva, Integer valor, Double total, Integer cantidad) {
        this.id = id;
        this.id_pedido = id_pedido;
        this.id_producto = id_producto;
        this.nombres = nombres;
        this.existencias = existencias;
        this.iva = iva;
        this.valor = valor;
        this.total = total;
        this.cantidad = cantidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(Integer id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Integer getId_producto() {
        return id_producto;
    }

    public void setId_producto(Integer id_producto) {
        this.id_producto = id_producto;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
