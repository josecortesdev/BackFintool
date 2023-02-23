package com.tutorial.crud.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ProductoDto {

    @NotBlank
    private String nombre;
    @NotBlank
    private String isin;
    @NotBlank
    private String ticker;
    @Min(0)
    private Float Ter;

    @NotBlank
    private String idcartera;

    public ProductoDto() {
    }

    public ProductoDto(@NotBlank String nombre, @NotBlank String isin, @NotBlank String ticker, @Min(0) Float Ter,
                       @NotBlank String idcartera) {
        this.nombre = nombre;
        this.isin = isin;
        this.ticker = ticker;
        this.Ter = Ter;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getTer() {
        return Ter;
    }

    public void setTer(Float Ter) {
        this.Ter = Ter;
    }


    public String getIdcartera() {
        return idcartera;
    }

    public void setIdcartera(String idcartera) {
        this.idcartera = idcartera;
    }
}
