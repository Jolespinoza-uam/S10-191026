package com.example.ejemploconcurrencia1;

import com.example.ejemploconcurrencia1.model.Caja;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Caja caja = new Caja();

        /*Runnable representa la tarea que ejecutara cada vendedor*/

        Runnable vendedor1 = () -> procesarVentas("Vendedor 1", caja);
        Runnable vendedor2 = () -> procesarVentas("Vendedor 2", caja);
        Runnable vendedor3 = () -> procesarVentas("Vendedor 3", caja);

        Thread hilo1 = new Thread(vendedor1);
        Thread hilo2 = new Thread(vendedor2);
        Thread hilo3 = new Thread(vendedor3);

        System.out.println("--- Inicio del proceso de ventas ---");

        /*hilo.start() inicia cada Thread
        * Los 3 vendedores comenzaran a procesar las ventas de manera concurrente
        * */

        /*vendedor 1*/
        hilo1.start();

        /*Vendedor 2*/
        hilo2.start();

        /*Vendedor 3*/
        hilo3.start();

        /*join() hace que el hilo main espere hasta que los demas terminen*/

        hilo1.join();
        hilo2.join();
        hilo3.join();

        System.out.println("--- Resultados finales ---");
        System.out.println("Cantidad de ventas: " + caja.getCantidadVentas());
        System.out.println("Total recaudado: " + caja.getTotalRecaudado());
    }

    public static void procesarVentas(String vendedor, Caja caja){
        for(int i = 1; i <= 200; i++){
            try{
                /*Simular que procesamos una venta que tarda aproximadamente 500 milisegundos*/

                Thread.sleep(500);
                caja.registrarVentas(vendedor, 100);


            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(vendedor + " fue interrumpido");
                return;
            }
        }

    }
}
