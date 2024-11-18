package Micarrera;

import javax.swing.*;

public class Moto implements Runnable {

    private String nombre;
    private int velocidad;
    private int distanciaRecorrida = 0;
    private int distanciaTotal;
    private JLabel etiquetaMoto;
    private volatile boolean corriendo = true;


    public Moto(String nombre, int velocidad, int distanciaTotal, JLabel etiquetaMoto) {
        this.nombre = nombre;
        this.velocidad = Math.max(velocidad, 2); // Aseguramos que la velocidad sea al menos 2km/h
        this.distanciaTotal = distanciaTotal;
        this.etiquetaMoto = etiquetaMoto;
    }

    @Override
    public void run() {
        while (corriendo && distanciaRecorrida < distanciaTotal) {
            distanciaRecorrida += velocidad;
            etiquetaMoto.setLocation(distanciaRecorrida, etiquetaMoto.getY());
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (corriendo) { // Si aún está corriendo, significa que terminó
            etiquetaMoto.setText(nombre + " terminó!");
        }
    }

    public void reiniciar() {
        distanciaRecorrida = 0; // Restablecer la distancia recorrida
        etiquetaMoto.setLocation(0, etiquetaMoto.getY()); // Reiniciar la posición de la moto
        etiquetaMoto.setText(nombre); // Restablecer el texto de la etiqueta
        corriendo = true; // Reiniciar el estado de la moto
    }

    public void detener() {
        corriendo = false; // Detener la ejecución del hilo
    }

    public boolean isCorriendo() {
        return corriendo && distanciaRecorrida < distanciaTotal;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDistanciaRecorrida() {
        return distanciaRecorrida;
    }
}
