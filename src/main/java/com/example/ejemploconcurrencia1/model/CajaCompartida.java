package com.example.ejemploconcurrencia1.model;

public class CajaCompartida {
    private int cantidadVentas = 0;
    private int totalRecaudado = 0;

    public synchronized void registrarVenta(int monto) {
        cantidadVentas++;
        totalRecaudado += monto;
    }

    /**
     * Permite que los hilos se organicen al momento de modificar los elementos
     */


    public synchronized int getCantidadVentas() {
        return cantidadVentas;
    }

    public  synchronized int getTotalRecaudado() {
        return totalRecaudado;
    }

    public void setCantidadVentas(int cantidadVentas) {
        this.cantidadVentas = cantidadVentas;
    }

    public void setTotalRecaudado(int totalRecaudado) {
        this.totalRecaudado = totalRecaudado;
    }
}
