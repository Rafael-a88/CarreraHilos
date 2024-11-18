package Micarrera;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class PanelCarrera extends JFrame {
    private Carrera carrera;

    public PanelCarrera(int distanciaTotal) {
        setTitle("Carrera de Motos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Establecer el tamaño inicial

        setIconImage(new ImageIcon("moto1.png").getImage());

        // Panel de fondo
        FondoPanel panelFondo = new FondoPanel("fondo.png");
        panelFondo.setLayout(null);
        setContentPane(panelFondo);

        carrera = new Carrera(distanciaTotal);

        for (int i = 0; i < 4; i++) {
            // Cargar y escalar la imagen de la moto
            ImageIcon iconoOriginal = new ImageIcon("moto" + (i + 1) + ".png");
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(100, 80, Image.SCALE_REPLICATE);
            ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);

            JLabel etiquetaMoto = new JLabel(iconoEscalado);

            // Ajustar la posición vertical de las motos
            int yPos = i * 150;
            if (i == 0 || i == 1) {
                yPos += 90; // Posición para motos 1 y 2
            } else {
                yPos += 100; // Posición para motos 3 y 4
            }

            int xPos = 30;
            etiquetaMoto.setBounds(xPos, yPos, 100, 80); // Ajustar el tamaño del JLabel al tamaño de la imagen
            panelFondo.add(etiquetaMoto);

            Moto moto = new Moto("Moto " + (i + 1), (int) (Math.random() * 10) + 1, distanciaTotal, etiquetaMoto);
            carrera.agregarMoto(moto);
        }

        JButton botonIniciar = new JButton("Iniciar Carrera");
        botonIniciar.setBounds(500, 700, 150, 30); // Ajustar la posición del botón de inicio
        botonIniciar.addActionListener(e -> carrera.iniciarCarrera());
        panelFondo.add(botonIniciar);

        // Botón para reiniciar la carrera
        JButton botonReiniciar = new JButton("Reiniciar Carrera");
        botonReiniciar.setBounds(670, 700, 150, 30); // Posición justo al lado del botón de iniciar
        botonReiniciar.addActionListener(e -> carrera.reiniciarCarrera());
        panelFondo.add(botonReiniciar);


    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PanelCarrera frame = new PanelCarrera(1150); // Distancia a recorrer las motos en total
            frame.setVisible(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        });
    }
}


// Clase para el panel de fondo
class FondoPanel extends JPanel {
    private Image imagenFondo;

    public FondoPanel(String rutaImagen) {
        try {
            BufferedImage imagenOriginal = ImageIO.read(new File(rutaImagen));
            // Escalar la imagen al tamaño del JFrame
            imagenFondo = imagenOriginal.getScaledInstance(Toolkit.getDefaultToolkit().getScreenSize().width,
                    Toolkit.getDefaultToolkit().getScreenSize().height, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, this); // Dibujar la imagen de fondo
        }
    }
}