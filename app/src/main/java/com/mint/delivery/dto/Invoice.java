package com.mint.delivery.dto;

public class Invoice {
    private Integer id;
    private Client client;
    private String fecha;
    private String otros;
    private Double subtotal;
    private Double iva;
    private Double total;

    public Invoice(Integer id, Client client, String fecha, String otros, Double subtotal, Double iva, Double total) {
        this.id = id;
        this.client = client;
        this.fecha = fecha;
        this.otros = otros;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getOtros() {
        return otros;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
