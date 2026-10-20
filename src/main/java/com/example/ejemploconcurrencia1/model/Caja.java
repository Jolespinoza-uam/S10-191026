package com.example.ejemploconcurrencia1.model;

public class Caja {
    public int getCantidadVentas() {
        return cantidadVentas;
    }

    public int getTotalRecaudado() {
        return totalRecaudado;
    }

    private int cantidadVentas = 0;
    private int totalRecaudado = 0;

    public synchronized void registrarVentas(String vendedor, int monto){
        cantidadVentas++;
        totalRecaudado += monto;

        System.out.println(
                vendedor + " registro de una venta de C$"
                + monto + "| Ventas acumuladas" + cantidadVentas +
                "| Total recaudado' " + totalRecaudado
        );
    }


}
