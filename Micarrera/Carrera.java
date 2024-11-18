package Micarrera;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Carrera {
    private List<Moto> motos = new ArrayList<>();
    private int distanciaTotal;
    private Clip clip;
    private volatile boolean carreraTerminada = false; // Indica si la carrera ha terminado
    private boolean mensajeMostrado = false; // Controla si el mensaje ya fue mostrado

    public Carrera(int distanciaTotal) {
        this.distanciaTotal = distanciaTotal;
    }

    public void agregarMoto(Moto moto) {
        motos.add(moto);
    }

    public void iniciarCarrera() {
        reproducirMusica("musicamoto.wav");
        carreraTerminada = false; // Reiniciar estado de carrera
        mensajeMostrado = false; // Reiniciar estado de mensaje

        // Iniciar hilos individualmente
        for (Moto moto : motos) {
            Thread hilo = new Thread(moto, moto.getNombre());
            hilo.start();

            // Esperar a que el hilo termine
            new Thread(() -> {
                try {
                    hilo.join(); // Espera a que el hilo termine
                    manejarFinCarrera(moto); // Manejar el fin de la carrera al terminar un hilo
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }

    public void reiniciarCarrera() {
        for (Moto moto : motos) {
            moto.reiniciar();
        }
        carreraTerminada = false; // Marcar la carrera como no terminada
        mensajeMostrado = false; // Reiniciar el estado del mensaje
    }

    private void reproducirMusica(String ruta) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(ruta));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void detenerMusica() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    private synchronized void manejarFinCarrera(Moto moto) {
        if (!carreraTerminada) {
            carreraTerminada = true; // Marcar la carrera como terminada
            detenerTodasLasMotos(); // Detener todas las motos
            mostrarGanador(moto); // Mostrar el ganador
        }
    }

    private void detenerTodasLasMotos() {
        for (Moto moto : motos) {
            moto.detener(); // Llama al método para detener la moto
        }
        detenerMusica(); // Detener la música
    }

    private void mostrarGanador(Moto moto) {
        if (!mensajeMostrado) { // Verificar si el mensaje ya fue mostrado
            StringBuilder mensaje = new StringBuilder("La carrera ha terminado!\n");
            mensaje.append(moto.getNombre()).append(" ha ganado!\n");
            for (Moto m : motos) {
                mensaje.append(m.getNombre()).append(" recorrió ").append(m.getDistanciaRecorrida()).append(" metros.\n");
            }
            JOptionPane.showMessageDialog(null, mensaje.toString(), "Resultado de la Carrera", JOptionPane.INFORMATION_MESSAGE);
            mensajeMostrado = true; // Marcar que el mensaje fue mostrado
        }
    }

}
