package com.example.ejemploconcurrencia1.controller;

import com.example.ejemploconcurrencia1.model.CajaCompartida;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;

public class VentasController {
    @FXML
    private ProgressBar pgbVendedor1;
    @FXML
    private ProgressBar pgbVendedor2;
    @FXML
    private ProgressBar pgbVendedor3;
    @FXML
    private Label lblVendedor1;
    @FXML
    private Label lblVendedor2;
    @FXML
    private Label lblVendedor3;
    @FXML
    private Label lblTotalRecaudado;
    @FXML
    private Label lblVentasRealizadas;
    @FXML
    private TextArea txtVentasProcesadas;
    @FXML
    private Button btnIniciarVentas;
    @FXML
    private Button btnLimpiar;

    /**
     * Objeto compartido por todos los hilos
     */

    private CajaCompartida cajaCompartida;

    @FXML
    private void iniciarVentas(){
        //Solo cuando se inicia una venta, el objeto de caja compartida se debe crear
        cajaCompartida = new  CajaCompartida();
        //Cada vez que se inicie el proceso de ventas, la interfaz se reinicia
        reiniciarInterfaz();

        btnIniciarVentas.setDisable(true);

        /**
         * Crear los tres hilos
         */

        Thread vendedor1 = new Thread(() -> {
            procesarVentas(
                    "Vendedor 1",
                    pgbVendedor1,
                    lblVendedor1
            );
            }
        );

        Thread vendedor2 = new Thread(() -> {
            procesarVentas(
                    "Vendedor 2",
                    pgbVendedor2,
                    lblVendedor2
            );
        });

        Thread vendedor3 = new Thread(() -> {
            procesarVentas(
                    "Vendedor 3",
                    pgbVendedor3,
                    lblVendedor3
            );
        });

        /**
         * Iniciamos la ejecución de los tres hilos (vendedores)
         */

        vendedor1.start();
        vendedor2.start();
        vendedor3.start();

        /**
         * Crear un Thread adicional encargado de esperar que los tres vendedores terminen.
         */

        Thread supervisor1 = new Thread(() -> {
            try {
                vendedor1.join();
                vendedor2.join();
                vendedor3.join();

                Platform.runLater(() -> {
                    txtVentasProcesadas.setText(" ----- Procesamiento finalizado ----- ");
                    btnIniciarVentas.setDisable(false);
                });


            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        supervisor1.start();
    }

    /**
     * Simula las ventas realizadas por los vendedores
     */
    private void procesarVentas(String vendedor, ProgressBar pgbVendedor, Label lblVendedor){
        for (int i = 0; i <= 5; i++) {
            try {
                Thread.sleep(1000);
                cajaCompartida.registrarVenta(100);

                int numeroVenta = i;
                double progreso = numeroVenta / 5.0;

                Platform.runLater(() -> {
                    pgbVendedor.setProgress(progreso);
                    lblVendedor.setText("Procesando venta " + numeroVenta + " de 5");
                    txtVentasProcesadas.appendText(vendedor + "registró una venta de C$100");
                    actualizarTotales();
                });

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            Platform.runLater(() -> {
                lblVendedor.setText("Proceso completado");
            });
        }
    }

    private void actualizarTotales(){
        lblVentasRealizadas.setText(String.valueOf(cajaCompartida.getCantidadVentas()));
        lblTotalRecaudado.setText("C$" + cajaCompartida.getTotalRecaudado());
    }

    private void reiniciarInterfaz(){
        pgbVendedor1.setProgress(0);
        pgbVendedor2.setProgress(0);
        pgbVendedor3.setProgress(0);
        lblVendedor1.setText("Iniciando...");
        lblVendedor2.setText("Iniciando...");
        lblVendedor3.setText("Iniciando...");
        lblVentasRealizadas.setText("0");
        lblTotalRecaudado.setText("C$0");
        txtVentasProcesadas.setText("");
    }

    @FXML
    private void limpiarVentas(){
        pgbVendedor1.setProgress(0);
        pgbVendedor2.setProgress(0);
        pgbVendedor3.setProgress(0);
        lblVendedor1.setText("Esperando...");
        lblVendedor2.setText("Esperando...");
        lblVendedor3.setText("Esperando...");
        lblVentasRealizadas.setText("0");
        lblTotalRecaudado.setText("C$0");
        txtVentasProcesadas.setText("");
    }
}
