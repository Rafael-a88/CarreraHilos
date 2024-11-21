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
        FondoPanel panelFondo = new FondoPanel("Circuito.png");
        panelFondo.setLayout(null);
        setContentPane(panelFondo);

        carrera = new Carrera(distanciaTotal);

        for (int i = 0; i < 4; i++) {
            // Cargar y escalar la imagen de la moto
            ImageIcon iconoOriginal = new ImageIcon("moto" + (i + 1) + ".png");
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(100, 80, Image.SCALE_REPLICATE);
            ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);

            JLabel etiquetaMoto = new JLabel(iconoEscalado);

            // Ajustar la posición vertical de las motos manualmente
            int yPos;
            switch (i) {
                case 0: yPos = 250; break; // Posición para la moto 1
                case 1: yPos = 350; break; // Posición para la moto 2
                case 2: yPos = 450; break; // Posición para la moto 3
                case 3: yPos = 550; break; // Posición para la moto 4
                default: yPos = 0; // Valor por defecto (no debería alcanzarse)
            }

            int xPos = 30; // Puedes ajustar este valor si deseas mover las motos horizontalmente
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

            // Obtener el tamaño de la pantalla
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            // Calcular nuevas dimensiones
            int nuevoAncho = (int)(screenSize.width * 1.05);  // 105% del ancho de pantalla
            int nuevoAlto = (int)(screenSize.height * 0.7);  // 70% del alto de pantalla

            // Escalar la imagen a las nuevas dimensiones
            imagenFondo = imagenOriginal.getScaledInstance(
                    nuevoAncho,
                    nuevoAlto,
                    Image.SCALE_SMOOTH
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            // Centrar la imagen
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (screenSize.width - imagenFondo.getWidth(this)) / 2;
            int y = (screenSize.height - imagenFondo.getHeight(this)) / 2 - 50;

            g.drawImage(imagenFondo, x, y, this);
        }
    }
}