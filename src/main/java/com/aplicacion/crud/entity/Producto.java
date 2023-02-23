package com.tutorial.crud.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String isin;
    private String ticker;
    private float Ter;
    private String idcartera;

    public Producto() {
    }

    public Producto(String nombre, String isin, String ticker, float ter, String idcartera) {
        this.nombre = nombre;
        this.isin = isin;
        this.ticker = ticker;
        Ter = ter;
        this.idcartera = idcartera;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getTer() {
        return Ter;
    }

    public void setTer(float Ter) {
        this.Ter = Ter;
    }


    public String getIdcartera() {
        return idcartera;
    }

    public void setIdcartera(String idcartera) {
        this.idcartera = idcartera;
    }
}
