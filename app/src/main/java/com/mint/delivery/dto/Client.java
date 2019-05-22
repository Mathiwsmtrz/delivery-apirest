package com.mint.delivery.dto;

public class Client {
    private Integer id;
    private String cedula;
    private String nombres;

    public Client(Integer id, String cedula, String nombres) {
        this.id = id;
        this.cedula = cedula;
        this.nombres = nombres;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
}
