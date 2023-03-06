import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class Manecillas extends JFrame implements Runnable {
    private Image fondo;
    private Image buffer;
    private Thread hilo;
    int min;
    int hora;
    int sec;
    int manecillaSegundos = 30;
    int manecillaMinutos = 25;
    int manecillaHora = 16;

    public Manecillas() {
        setTitle("Reloj Analógico");
        setResizable(false);
        setSize(650,650);
        setLayout(null);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hilo = new Thread(this);
        hilo.start();
    }
    public static void main(String[] args) { new Manecillas(); }

    public void paint(Graphics g) {
        if(fondo == null) {
            fondo = createImage(getWidth(), getHeight());
            //Pintar el circulo del reloj
            Graphics gfondo = fondo.getGraphics();
            //Bing bang
            bingBang(gfondo,222, 580);
        }
        update(g);
    }

    public void update(Graphics g) {
        double xHora, yHora, anguloHora;
        double xMinuto, yMinuto, anguloMinuto;
        double xSegundo, ySegundo, anguloSegundo;
        g.setClip(0,0,getWidth(),getHeight());

        //Hora actual
        Calendar calendar = Calendar.getInstance();

        if(calendar.get(Calendar.SECOND) != sec) {
            //Regenerar la imagen de fondo
            hora = calendar.get(Calendar.HOUR);
            min = calendar.get(Calendar.MINUTE);
            sec = calendar.get(Calendar.SECOND);

            //Crear la imagen estatica
            buffer = createImage(getWidth(), getHeight());
            Graphics2D gbuffer = (Graphics2D) buffer.getGraphics();
            gbuffer.setClip(0,0,getWidth(),getHeight());
            gbuffer.drawImage(fondo,0,0,this);

            //Horas
            System.out.println(hora);
            anguloHora = calculoAnguloHora(hora);
            System.out.println("Angulo hora: " + anguloHora);
            xHora = getX(anguloHora, manecillaHora);
            yHora = getY(anguloHora, manecillaHora);
            gbuffer.setColor(new Color(62, 40, 13));
            gbuffer.setStroke(new BasicStroke(3));
            gbuffer.drawLine(776/2, 570/2, (776/2) + (int) xHora, (570/2) + (int) yHora);
            gbuffer.drawLine(523/2, 570/2, (523/2) + (int) xHora, (570/2) + (int) yHora);

            //Minutos
            System.out.println(min);
            anguloMinuto = calculoAnguloMinuto(min);
            System.out.println("Angulo minuto: " + anguloMinuto);
            xMinuto = getX(anguloMinuto, manecillaMinutos);
            yMinuto = getY(anguloMinuto, manecillaMinutos);
            gbuffer.setColor(new Color(62, 40, 13));
            gbuffer.setStroke(new BasicStroke(2));
            gbuffer.drawLine(776/2, 570/2, (776/2) + (int) xMinuto, (570/2) + (int) yMinuto);
            gbuffer.drawLine(523/2, 570/2, (523/2) + (int) xMinuto, (570/2) + (int) yMinuto);

            //Segundos
            System.out.println(sec);
            anguloSegundo = calculoAnguloSegundo(sec);
            System.out.println("Angulo segundo: " + anguloSegundo);
            xSegundo = getX(anguloSegundo, manecillaSegundos);
            ySegundo = getY(anguloSegundo, manecillaSegundos);
            gbuffer.setColor(new Color(242, 150, 150));
            gbuffer.setStroke(new BasicStroke(1));
            gbuffer.drawLine(776/2, 570/2, (776/2) + (int) xSegundo, (570/2) + (int) ySegundo);
            gbuffer.drawLine(523/2, 570/2, (523/2) + (int) xSegundo, (570/2) + (int) ySegundo);
            g.drawImage(buffer,0,0,this);
        }
    }

    public void run() {
        while(true) {
            try {
                repaint();
                hilo.sleep(1000);
            } catch (InterruptedException ex) {}
        }
    }
    private double getX(double angulo, int radio) {
        double x = (double) radio * (Math.cos(Math.toRadians(angulo)));
        return x;
    }
    private double getY(double angulo, int radio) {
        double y = (double) radio * (Math.sin(Math.toRadians(angulo)));
        return y;
    }
    public int calculoAnguloHora(int hora) {
        int anguloH = -90;
        for(int i = 1; i <= hora; i++) {
            anguloH += 30;
        }
        return anguloH;
    }
    public int calculoAnguloMinuto(int minuto) {
        int anguloM = -90;
        for(int i = 1; i <= minuto; i++) {
            anguloM += 6;
        }
        return anguloM;
    }
    public int calculoAnguloSegundo(int segundo) {
        int anguloS = -90;
        for(int i = 1; i <= segundo; i++) {
            anguloS += 6;
        }
        return anguloS;
    }
    public void bingBang(Graphics graphics, int x1, int y1) {
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setStroke(new BasicStroke(1));

        //Base lado 2
        g2d.setColor(new Color(99,72,63,255));
        g2d.fillRect(x1 + 100, y1 - 230, 112, 310);

            //Cuadro externo
            g2d.setColor(new Color(246,205,138,255));
            g2d.fillRect(x1 + 100, y1 - 360, 130, 130);

                //Exterior
                g2d.setColor(new Color(99,72,63,255));
                g2d.fillRect(x1 + 100, y1 - 360, 4, 130);
                g2d.fillRect(x1 + 230, y1 - 360, 4, 130);
                g2d.fillRect(x1 + 100, y1 - 362, 134, 4);
                g2d.fillRect(x1 + 210, y1 - 232, 23, 4);
                g2d.setColor(new Color(153,123,113,255));
                g2d.fillRect(x1 + 103, y1 - 232, 107, 4);

                //Cuadro interno
                g2d.setColor(new Color(225,186,122,255));
                g2d.fillRect(x1 + 109, y1 - 353, 115, 115);

            //Relleno reloj
            g2d.setColor(new Color(255,255,255,255));
            g2d.fillRect(x1 + 125, y1 - 326, 80, 57);
            g2d.fillRect(x1 + 140, y1 - 290, 53, 33);
            g2d.fillRect(x1 + 155, y1 - 260, 22, 5);
            g2d.fillRect(x1 + 155, y1 - 336, 22, 5);
            g2d.fillRect(x1 + 140, y1 - 332, 53, 33);
            g2d.fillRect(x1 + 122, y1 - 309, 6, 30);
            g2d.fillRect(x1 + 203, y1 - 309, 6, 30);

                //Reloj contorno
                g2d.setColor(new Color(181,143,82,255));
                g2d.fillRect(x1 + 153, y1 - 340, 25, 4);
                g2d.fillRect(x1 + 153, y1 - 255, 25, 4);
                g2d.fillRect(x1 + 135, y1 - 336, 20, 4);
                g2d.fillRect(x1 + 135, y1 - 259, 20, 4);
                g2d.fillRect(x1 + 177, y1 - 336, 20, 4);
                g2d.fillRect(x1 + 177, y1 - 259, 20, 4);
                g2d.fillRect(x1 + 135, y1 - 336, 4, 10);
                g2d.fillRect(x1 + 135, y1 - 265, 4, 10);
                g2d.fillRect(x1 + 193, y1 - 336, 4, 10);
                g2d.fillRect(x1 + 193, y1 - 265, 4, 10);
                g2d.fillRect(x1 + 125, y1 - 330, 13, 4);
                g2d.fillRect(x1 + 125, y1 - 269, 14, 4);
                g2d.fillRect(x1 + 195, y1 - 330, 13, 4);
                g2d.fillRect(x1 + 193, y1 - 269, 17, 4);
                g2d.fillRect(x1 + 118, y1 - 310, 4, 30);
                g2d.fillRect(x1 + 210, y1 - 310, 4, 30);
                g2d.fillRect(x1 + 121, y1 - 330, 4, 20);
                g2d.fillRect(x1 + 121, y1 - 280, 4, 15);
                g2d.fillRect(x1 + 205, y1 - 330, 4, 20);
                g2d.fillRect(x1 + 206, y1 - 280, 4, 15);

            //Detalles de la base arriba
            g2d.setColor(new Color(79,60,54,255));
            g2d.fillRect(x1 + 115, y1 - 220, 10, 120);
            g2d.fillRect(x1 + 135, y1 - 220, 10, 120);
            g2d.fillRect(x1 + 155, y1 - 220, 10, 120);
            g2d.fillRect(x1 + 175, y1 - 220, 10, 120);
            g2d.fillRect(x1 + 195, y1 - 220, 10, 120);
            g2d.fillRect(x1 + 100, y1 - 85, 112, 10);
            g2d.fillRect(x1 + 100, y1 - 65, 112, 3);

                //Sombra superior
                g2d.setColor(new Color(153,123,113,255));
                g2d.fillRect(x1 + 115, y1 - 220, 10, 5);
                g2d.fillRect(x1 + 135, y1 - 220, 10, 5);
                g2d.fillRect(x1 + 155, y1 - 220, 10, 5);
                g2d.fillRect(x1 + 175, y1 - 220, 10, 5);
                g2d.fillRect(x1 + 195, y1 - 220, 10, 5);
                g2d.fillRect(x1 + 100, y1 - 90, 112, 5);

            //Detalles de la base abajo
            g2d.setColor(new Color(79,60,54,255));
            g2d.fillRect(x1 + 115, y1 - 50, 10, 120);
            g2d.fillRect(x1 + 135, y1 - 50, 10, 120);
            g2d.fillRect(x1 + 155, y1 - 50, 10, 120);
            g2d.fillRect(x1 + 175, y1 - 50, 10, 120);
            g2d.fillRect(x1 + 195, y1 - 50, 10, 120);

                //Sombra superior
                g2d.setColor(new Color(153,123,113,255));
                g2d.fillRect(x1 + 115, y1 - 50, 10, 5);
                g2d.fillRect(x1 + 135, y1 - 50, 10, 5);
                g2d.fillRect(x1 + 155, y1 - 50, 10, 5);
                g2d.fillRect(x1 + 175, y1 - 50, 10, 5);
                g2d.fillRect(x1 + 195, y1 - 50, 10, 5);


        //Base lado 1
        g2d.setColor(new Color(186,162,154,255));
        g2d.fillRect(x1, y1 - 230, 100, 310);

            //Rectangulo
            g2d.setColor(new Color(153,123,113,255));
            g2d.fillRect(x1 + 10, y1 - 230, 83, 310);

            //Cuadro externo
            g2d.setColor(new Color(249,226,189,255));
            g2d.fillRect(x1 - 20, y1 - 360, 120, 130);

                //Exterior
                g2d.setColor(new Color(186,162,154,255));
                g2d.fillRect(x1 + 95, y1 - 360, 5, 130);
                g2d.setColor(new Color(99,72,63,255));
                g2d.fillRect(x1 - 18, y1 - 362, 120, 4);
                g2d.setColor(new Color(153,123,113,255));
                g2d.fillRect(x1 - 22, y1 - 362, 4, 133);
                g2d.setColor(new Color(225,202,194,255));
                g2d.fillRect(x1 - 18, y1 - 232, 113, 4);

                //Cuadro interno
                g2d.setColor(new Color(246,205,138,255));
                g2d.fillRect(x1 - 13, y1 - 353, 103, 115);

            //Relleno reloj
            g2d.setColor(new Color(255,255,255,255));
            g2d.fillRect(x1 + 7, y1 - 330, 62, 70);
            g2d.fillRect(x1 + 30, y1 - 336, 20, 4);
            g2d.fillRect(x1 + 30, y1 - 259, 20, 4);
            g2d.fillRect(x1 + 16, y1 - 333, 48, 4);
            g2d.fillRect(x1 + 16, y1 - 263, 48, 4);
            g2d.fillRect(x1 + 4, y1 - 310, 4, 30);
            g2d.fillRect(x1 + 69, y1 - 310, 4, 30);

                //Reloj contorno
                g2d.setColor(new Color(210,172,109,255));
                g2d.fillRect(x1 + 30, y1 - 340, 20, 4);
                g2d.fillRect(x1 + 30, y1 - 255, 20, 4);
                g2d.fillRect(x1, y1 - 310, 4, 30);
                g2d.fillRect(x1 + 73, y1 - 310, 4, 30);
                g2d.fillRect(x1 + 15, y1 - 337, 15, 4);
                g2d.fillRect(x1 + 15, y1 - 259, 15, 4);
                g2d.fillRect(x1 + 50, y1 - 337, 15, 4);
                g2d.fillRect(x1 + 50, y1 - 259, 15, 4);
                g2d.fillRect(x1 + 3, y1 - 330, 4, 20);
                g2d.fillRect(x1 + 69, y1 - 330, 4, 20);
                g2d.fillRect(x1 + 3, y1 - 280, 4, 20);
                g2d.fillRect(x1 + 69, y1 - 280, 4, 20);
                g2d.fillRect(x1 + 3, y1 - 334, 12, 4);
                g2d.fillRect(x1 + 3, y1 - 262, 12, 4);
                g2d.fillRect(x1 + 64, y1 - 334, 9, 4);
                g2d.fillRect(x1 + 64, y1 - 262, 9, 4);

            //Detalles de la base arriba
            g2d.setColor(new Color(99,72,63,255));
            g2d.fillRect(x1 + 18, y1 - 220, 12, 120);
            g2d.fillRect(x1 + 38, y1 - 220, 12, 120);
            g2d.fillRect(x1 + 58, y1 - 220, 12, 120);
            g2d.fillRect(x1 + 78, y1 - 220, 12, 120);
            g2d.fillRect(x1 + 10, y1 - 85, 83, 10);
            g2d.fillRect(x1 + 10, y1 - 65, 83, 3);

                //Sombra superior
                g2d.setColor(new Color(225,202,194,255));
                g2d.fillRect(x1 + 18, y1 - 220, 12, 5);
                g2d.fillRect(x1 + 38, y1 - 220, 12, 5);
                g2d.fillRect(x1 + 58, y1 - 220, 12, 5);
                g2d.fillRect(x1 + 78, y1 - 220, 12, 5);
                g2d.fillRect(x1 + 10, y1 - 90, 83, 5);

            //Detalles de la base arriba
            g2d.setColor(new Color(99,72,63,255));
            g2d.fillRect(x1 + 18, y1 - 50, 12, 120);
            g2d.fillRect(x1 + 38, y1 - 50, 12, 120);
            g2d.fillRect(x1 + 58, y1 - 50, 12, 120);
            g2d.fillRect(x1 + 78, y1 - 50, 12, 120);

                //Sombra superior
                g2d.setColor(new Color(225,202,194,255));
                g2d.fillRect(x1 + 18, y1 - 50, 12, 5);
                g2d.fillRect(x1 + 38, y1 - 50, 12, 5);
                g2d.fillRect(x1 + 58, y1 - 50, 12, 5);
                g2d.fillRect(x1 + 78, y1 - 50, 12, 5);

        //Numeros lado 2
        Font font = new Font("Trajan",Font.BOLD,10);
        g2d.setFont(font);
        g2d.setColor(Color.black);
        g2d.drawString( "XII", 382, 255);
        g2d.drawString( "III", 420, 288);
        g2d.drawString( "VI", 383, 323);
        g2d.drawString( "IX", 348, 288);

        //Numeros lado 1
        Font font1 = new Font("Trajan",Font.BOLD,10);
        g2d.setFont(font1);
        g2d.setColor(Color.black);
        g2d.drawString( "XII", 255, 255);
        g2d.drawString( "III", 283, 288);
        g2d.drawString( "VI", 257, 323);
        g2d.drawString( "IX", 228, 288);

        //Punta
        g2d.setColor(new Color(153,123,113,255));
        g2d.fillRect(x1 + 103, y1 - 372, 96, 10);
        g2d.setColor(new Color(225,202,194,255));
        g2d.fillRect(x1 + 16, y1 - 372, 87, 10);
        g2d.setColor(new Color(99,72,63,255));
        g2d.fillRect(x1 + 100, y1 - 375, 95, 4);
        g2d.fillRect(x1 + 195, y1 - 375, 4, 13);
        g2d.fillRect(x1 + 16, y1 - 375, 4, 13);
        g2d.setColor(new Color(150,122,114,255));
        g2d.fillRect(x1 + 20, y1 - 375, 84, 4);

            //Parte gris abajo
            g2d.setColor(new Color(63,67,78,255));
            g2d.fillRect(x1 + 103, y1 - 394, 96, 20);
            g2d.setColor(new Color(114,120,138,255));
            g2d.fillRect(x1 + 16, y1 - 394, 87, 20);
            g2d.setColor(new Color(63,67,78,255));
            g2d.fillRect(x1 + 100, y1 - 410, 91, 20);
            g2d.setColor(new Color(114,120,138,255));
            g2d.fillRect(x1 + 22, y1 - 410, 81, 20);
            g2d.setColor(new Color(63,67,78,255));
            g2d.fillRect(x1 + 100, y1 - 425, 81, 20);
            g2d.setColor(new Color(114,120,138,255));
            g2d.fillRect(x1 + 29, y1 - 425, 74, 20);
            g2d.setColor(new Color(63,67,78,255));
            g2d.fillRect(x1 + 105, y1 - 445, 68, 20);
            g2d.setColor(new Color(114,120,138,255));
            g2d.fillRect(x1 + 36, y1 - 445, 74, 20);
            g2d.setColor(new Color(63,67,78,255));
            g2d.fillRect(x1 + 105, y1 - 460, 60, 20);
            g2d.setColor(new Color(114,120,138,255));
            g2d.fillRect(x1 + 45, y1 - 460, 65, 20);

                //Contorno
                g2d.setColor(new Color(63,67,78,255));
                g2d.fillRect(x1 + 16, y1 - 394, 4, 20);
                g2d.fillRect(x1 + 22, y1 - 410, 4, 18);
                g2d.fillRect(x1 + 29, y1 - 425, 4, 17);
                g2d.fillRect(x1 + 36, y1 - 445, 4, 18);
                g2d.fillRect(x1 + 45, y1 - 460, 4, 17);
                g2d.setColor(new Color(45,49,60,255));
                g2d.fillRect(x1 + 195, y1 - 394, 4, 20);
                g2d.fillRect(x1 + 190, y1 - 410, 4, 18);
                g2d.fillRect(x1 + 180, y1 - 425, 4, 17);
                g2d.fillRect(x1 + 173, y1 - 445, 4, 20);
                g2d.fillRect(x1 + 164, y1 - 460, 4, 17);

            //Parte amarilla
            g2d.setColor(new Color(225,186,122,255));
            g2d.fillRect(x1 + 105, y1 - 507, 60, 47);
            g2d.setColor(new Color(246,205,138,255));
            g2d.fillRect(x1 + 45, y1 - 507, 65, 47);
            g2d.setColor(new Color(249,226,189,255));
            g2d.fillRect(x1 + 45, y1 - 507, 65, 6);
            g2d.fillRect(x1 + 45, y1 - 465, 65, 6);
            g2d.fillRect(x1 + 45, y1 - 507, 6, 47);

            //Parte gris arriba
            g2d.setColor(new Color(63,67,78,255));
            g2d.fillRect(x1 + 106, y1 - 527, 50, 20);
            g2d.setColor(new Color(114,120,138,255));
            g2d.fillRect(x1 + 55, y1 - 527, 55, 20);
            g2d.setColor(new Color(63,67,78,255));
            g2d.fillRect(x1 + 109, y1 - 547, 43, 20);
            g2d.setColor(new Color(114,120,138,255));
            g2d.fillRect(x1 + 60, y1 - 547, 50, 20);

                //Contorno
                g2d.setColor(new Color(63,67,78,255));
                g2d.fillRect(x1 + 55, y1 - 527, 4, 20);
                g2d.fillRect(x1 + 60, y1 - 547, 4, 20);
                g2d.setColor(new Color(45,49,60,255));
                g2d.fillRect(x1 + 155, y1 - 527, 4, 20);
                g2d.fillRect(x1 + 148, y1 - 547, 4, 20);

            //Raya del centro
            g2d.setColor(new Color(141,146,161,255));
            g2d.fillRect(x1 + 103, 120, 6, 42);
            g2d.fillRect(x1 + 103, 34, 6, 40);
            g2d.fillRect(x1 + 97, 160, 6, 58);
            g2d.setColor(new Color(248,217,165,255));
            g2d.fillRect(x1 + 103, 75, 6, 40);
            g2d.setColor(new Color(225,186,122,255));
            g2d.fillRect(x1 + 103, 73, 8, 8);
            g2d.fillRect(x1 + 103, 113, 8, 8);

        //Ventanas en lo gris
        ventanasLado2(graphics,x1 + 120,185);
        ventanasLado2(graphics,x1 + 145,185);
        ventanasLado2(graphics,x1 + 170,185);
        ventanasLado2(graphics,x1 + 130,150);
        ventanasLado2(graphics,x1 + 155,150);
        simpleVentanaLado2(graphics,x1 + 120, 60);
        simpleVentanaLado2(graphics,x1 + 130, 60);
        simpleVentanaLado2(graphics,x1 + 140, 60);
        simpleVentanaLado2(graphics,x1 + 125, 45);
        simpleVentanaLado2(graphics,x1 + 135, 45);
        ventanasLado1(graphics,x1 + 33, 185);
        ventanasLado1(graphics,x1 + 57, 185);
        ventanasLado1(graphics,x1 + 81, 185);
        ventanasLado1(graphics,x1 + 50, 150);
        ventanasLado1(graphics,x1 + 77, 150);
        simpleVentanaLado1(graphics,x1 + 69,60);
        simpleVentanaLado1(graphics,x1 + 79,60);
        simpleVentanaLado1(graphics,x1 + 89,60);
        simpleVentanaLado1(graphics,x1 + 77,45);
        simpleVentanaLado1(graphics,x1 + 87,45);

        //Ventanas en lo amarillo
        ventanasAmarilloLado2(graphics,x1 + 120,85);
        ventanasAmarilloLado2(graphics,x1 + 145,85);
        ventanasAmarilloLado1(graphics,x1 + 57,85);
        ventanasAmarilloLado1(graphics,x1 + 82,85);

        //Decoracion lado 2
        decoracionLado2(graphics,x1 + 109,y1 - 340);
        decoracionLado2(graphics,x1 + 114,y1 - 345);
        decoracionLado2(graphics,x1 + 119,y1 - 340);
        decoracionLado2(graphics,x1 + 124,y1 - 345);
        decoracionLado2(graphics,x1 + 129,y1 - 350);
        decoracionLado2(graphics,x1 + 200,y1 - 350);
        decoracionLado2(graphics,x1 + 205,y1 - 345);
        decoracionLado2(graphics,x1 + 210,y1 - 340);
        decoracionLado2(graphics,x1 + 215,y1 - 345);
        decoracionLado2(graphics,x1 + 220,y1 - 340);
        decoracionLado2(graphics,x1 + 109,y1 - 255);
        decoracionLado2(graphics,x1 + 114,y1 - 250);
        decoracionLado2(graphics,x1 + 119,y1 - 255);
        decoracionLado2(graphics,x1 + 124,y1 - 250);
        decoracionLado2(graphics,x1 + 129,y1 - 245);
        decoracionLado2(graphics,x1 + 200,y1 - 245);
        decoracionLado2(graphics,x1 + 205,y1 - 250);
        decoracionLado2(graphics,x1 + 210,y1 - 255);
        decoracionLado2(graphics,x1 + 215,y1 - 250);
        decoracionLado2(graphics,x1 + 220,y1 - 255);

        //Decoracion lado 1
        decoracionLado1(graphics,x1 - 13, y1 - 345);
        decoracionLado1(graphics,x1 - 8, y1 - 340);
        decoracionLado1(graphics,x1 - 3, y1 - 345);
        decoracionLado1(graphics,x1 + 2, y1 - 350);
        decoracionLado1(graphics,x1 + 65,y1 - 350);
        decoracionLado1(graphics,x1 + 70,y1 - 345);
        decoracionLado1(graphics,x1 + 75,y1 - 345);
        decoracionLado1(graphics,x1 + 80,y1 - 345);
        decoracionLado1(graphics,x1 + 85,y1 - 345);
        decoracionLado1(graphics,x1 + 85,y1 - 250);
        decoracionLado1(graphics,x1 + 80,y1 - 250);
        decoracionLado1(graphics,x1 + 75,y1 - 250);
        decoracionLado1(graphics,x1 + 70,y1 - 250);
        decoracionLado1(graphics,x1 + 65,y1 - 245);
        decoracionLado1(graphics,x1 - 13, y1 - 250);
        decoracionLado1(graphics,x1 - 8, y1 - 250);
        decoracionLado1(graphics,x1 - 3, y1 - 250);
        decoracionLado1(graphics,x1 + 2, y1 - 250);
        decoracionLado1(graphics,x1 + 7, y1 - 245);
    }
    public void ventanasLado2(Graphics graphics, int x1, int y1) {
        Graphics2D v1 = (Graphics2D) graphics;
        v1.setColor(new Color(45,49,60,255));
        v1.fillRect(x1,y1,8,15);
        v1.setColor(new Color(99,72,63,255));
        v1.fillRect(x1 - 4,y1,5,15);
        v1.fillRect(x1 + 8,y1,5,15);
        v1.fillRect(x1 + 1,y1 - 5,7,5);
    }
    public void ventanasLado1(Graphics graphics, int x1, int y1) {
        Graphics2D v2 = (Graphics2D) graphics;
        v2.setColor(new Color(63,67,78,255));
        v2.fillRect(x1,y1,8,15);
        v2.setColor(new Color(153,123,113,255));
        v2.fillRect(x1 - 4,y1,5,15);
        v2.fillRect(x1 + 8,y1,5,15);
        v2.fillRect(x1 + 1,y1 - 5,7,5);
    }
    public void simpleVentanaLado2(Graphics graphics, int x1, int y1) {
        Graphics2D sv1 = (Graphics2D) graphics;
        sv1.setColor(new Color(99,72,63,255));
        sv1.fillRect(x1,y1,5,5);
    }
    public void simpleVentanaLado1(Graphics graphics, int x1, int y1) {
        Graphics2D sv2 = (Graphics2D) graphics;
        sv2.setColor(new Color(153,123,113,255));
        sv2.fillRect(x1,y1,5,5);
    }
    public void ventanasAmarilloLado2(Graphics graphics, int x1, int y1) {
        Graphics2D va1 = (Graphics2D) graphics;
        va1.setColor(new Color(185,148,89,255));
        va1.fillRect(x1,y1,13,25);
        va1.setColor(new Color(246,205,138,255));
        va1.fillRect(x1,y1,13,5);
    }
    public void ventanasAmarilloLado1(Graphics graphics, int x1, int y1) {
        Graphics2D va1 = (Graphics2D) graphics;
        va1.setColor(new Color(225,186,122,255));
        va1.fillRect(x1,y1,13,25);
        va1.setColor(new Color(249,226,189,255));
        va1.fillRect(x1,y1,13,5);
    }
    public void decoracionLado2(Graphics graphics, int x1, int y1) {
        Graphics2D sv2 = (Graphics2D) graphics;
        sv2.setColor(new Color(249,226,189,255));
        sv2.fillRect(x1,y1,5,5);
    }
    public void decoracionLado1(Graphics graphics, int x1, int y1) {
        Graphics2D sv2 = (Graphics2D) graphics;
        sv2.setColor(new Color(250,236,214,255));
        sv2.fillRect(x1,y1,5,5);
    }
}