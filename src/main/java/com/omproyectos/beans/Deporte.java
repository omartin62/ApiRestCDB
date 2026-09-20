
package com.omproyectos.beans;

public class Deporte {
    
    private int idDeporte;
    private String descripcion;
    private int totalDeporte;

    public Deporte() {
        
    }

    /**
     * @return the idDeporte
     */
    public int getIdDeporte() {
        return idDeporte;
    }

    /**
     * @param idDeporte the idDeporte to set
     */
    public void setIdDeporte(int idDeporte) {
        this.idDeporte = idDeporte;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the totalDeporte
     */
    public int getTotalDeporte() {
        return totalDeporte;
    }

    /**
     * @param totalDeporte the totalDeporte to set
     */
    public void setTotalDeporte(int totalDeporte) {
        this.totalDeporte = totalDeporte;
    }
}
