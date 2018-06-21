
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class MandelbrotCanvas extends JPanel {

    private BufferedImage canvas;
    private JFrame frame;
    private Color color;
    private int MANDELBROT_ORDER;
    private int MAX_ITERATIONS;
    private double ITERATIONS_MULTIPLIER;
    private int ITERATIONS_ADDER;
    private double ZOOM_MULTIPLIER;
    private byte DRAW_MODE;
    private boolean GRID;
    private boolean CROSSHAIR;
    private boolean SAVE_FILE;
    private boolean OPEN_FRAME;
    private double xc;
    private double yc;
    private double zoom;

    public MandelbrotCanvas(int width, int height) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        color = new Color(51, 51, 51);
        MANDELBROT_ORDER = 2;
        MAX_ITERATIONS = 32;
        ITERATIONS_MULTIPLIER = 1;
        ITERATIONS_ADDER = 0;
        ZOOM_MULTIPLIER = 1.0;
        DRAW_MODE = 0;
        GRID = false;
        CROSSHAIR = false;
        SAVE_FILE = true;
        OPEN_FRAME = true;
        xc = 0;
        yc = 0;
        zoom = 0.25;
        background(color);
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
////                System.out.println("Click Received: x=" + e.getX() + " y=" + e.getY());
//                x = 0;
//                y = 0;
//                color = new Color(51, 51, 51);
//                int button = e.getButton();
//                switch (button) {
//                    case MouseEvent.BUTTON1:
//                        zoomIn(e.getX(), e.getY());
//                        background(color);
//                        repaint();
//                        drawBrot();
//                        break;
//                    case MouseEvent.BUTTON2:
////                        zoomOut(e.getX(), e.getY());
//                        background(color);
//                        repaint();
//                        drawBrot();
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
        frame = new JFrame();
        frame.add(this);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Dimension getPreferredSize() {
        return new Dimension(canvas.getWidth(), canvas.getHeight());
    }

    public boolean getGrid() {
        return GRID;
    }

    public boolean getCrosshair() {
        return CROSSHAIR;
    }

    public boolean getSave() {
        return SAVE_FILE;
    }

    public boolean getOpen() {
        return OPEN_FRAME;
    }

    public int getOrder() {
        return MANDELBROT_ORDER;
    }

    public int getIterations() {
        return MAX_ITERATIONS;
    }

    public double getIterationMultiplier() {
        return ITERATIONS_MULTIPLIER;
    }
    
    public int getIterationAdder(){
        return ITERATIONS_ADDER;
    }

    public double getZoomMultiplier() {
        return ZOOM_MULTIPLIER;
    }

    public byte getDrawMode() {
        return DRAW_MODE;
    }

    public double getXShift() {
        return xc;
    }

    public double getYShift() {
        return yc;
    }

    public double getZoom() {
        return zoom;
    }

    public void setGrid(boolean g) {
        GRID = g;
    }

    public void setCrosshair(boolean c) {
        CROSSHAIR = c;
    }

    public void setSave(boolean s) {
        SAVE_FILE = s;
    }

    public void setOpen(boolean o) {
        OPEN_FRAME = o;
    }

    public void setOrder(int o) {
        MANDELBROT_ORDER = o;
    }

    public void setIterations(int iter) {
        MAX_ITERATIONS = iter;
    }

    public void setIterationMultiplier(double im) {
        ITERATIONS_MULTIPLIER = im;
    }
    
    public void setIterationAdder(int ia){
        ITERATIONS_ADDER = ia;
    }

    public void setZoomMultiplier(double zm) {
        ZOOM_MULTIPLIER = zm;
    }

    public void setDrawMode(byte dm) {
        DRAW_MODE = dm;
    }

    public void setXShift(double x) {
        xc = x;
    }

    public void setYShift(double y) {
        yc = y;
    }

    public void setZoom(double z) {
        zoom = z;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(canvas, null, null);
    }

    public void save(String filename) {
        File outputfile = new File(filename);
        try {
            boolean saved = ImageIO.write(canvas, "png", outputfile);
            if (saved) {
//                System.out.println("File Saved.");
            } else {
//                System.out.println("File NOT Saved.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Color getFill() {
        return color;
    }

    public void fill(Color c) {
        color = c;
    }

    public void background(Color c) {
        int color = c.getRGB();
        for (int x = 0; x < canvas.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight(); y++) {
                canvas.setRGB(x, y, color);
            }
        }
        repaint();
    }

    public void drawPoint(int x, int y) {
        int c = color.getRGB();
        if (x < canvas.getWidth() && y < canvas.getHeight()) {
            canvas.setRGB(x, y, c);
        }
    }

    public void drawBrot() {
        String filename = "Order=" + (MANDELBROT_ORDER / 2.0) + " Res=" + canvas.getWidth() + "x" + canvas.getHeight() + " Pos=(" + xc + ", " + yc + ") Zoom=" + zoom + "x.png";
        for (double y = 0; y < canvas.getHeight(); y++) {
            for (double x = 0; x < canvas.getWidth(); x++) {
                double x0 = xc + ((x / canvas.getWidth()) - 0.5) / zoom;
                double y0 = yc + ((y / canvas.getHeight())- 0.5) / zoom;
                if (((CROSSHAIR) && ((x == getWidth() / 2) || (y == getHeight() / 2))) || ((GRID) && ((x0 == 0) || (y0 == 0)))) {
                    this.fill(Color.BLACK);
                    this.drawPoint((int) x, (int) y);
                } else {
                    ComplexNumber z0 = new ComplexNumber(x0, y0);
                    double h = 0;
                    h = map(mand(z0), 0, MAX_ITERATIONS, 0, 0.6);
                    double s = map(h, 0, 1, 0.8, 1);
                    double l = map(h, 0, 1, 0.5, 0.75);
                    int color = Color.HSBtoRGB((float) h, (float) s, (float) l);
                    this.fill(new Color(color));
                    this.drawPoint((int) x, (int) y);
                }
                if (DRAW_MODE == 0) {
                    repaint();
                }
            }
            if ((100 * y / canvas.getHeight()) == (100 * (int) y / canvas.getHeight())) {
                frame.setTitle((100 * (int) y / getHeight()) + "% Complete - " + filename);
            }
            if (DRAW_MODE == 1) {
                repaint();
            }
        }
        if (DRAW_MODE == 2) {
            repaint();
        }
        if (SAVE_FILE) {
//            System.out.println("Saving File: \"" + filename + "\"");
            this.save(filename);
        }
        if (OPEN_FRAME) {
            frame.add(this);
            frame.pack();
            frame.setVisible(true);
        }
        zoomIn(getWidth() / 2, getHeight() / 2);

    }

    private int mand(ComplexNumber z0) {
        ComplexNumber z = new ComplexNumber(z0);
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            if (z.abs() > 2.0) {
                return i;
            }
            z.pow(MANDELBROT_ORDER);
            z.plus(z0);
        }
        return MAX_ITERATIONS;
    }

    private double map(double v, double a1, double a2, double b1, double b2) {
        return b1 + (v - a1) * (b2 - b1) / (a2 - a1);
    }

    private void zoomIn(int xm, int ym) {
//        xc = xc - (1.0 / zoom) / 2 + (1.0 / zoom) * xm / getWidth();
//        yc = yc - (1.0 / zoom) / 2 + (1.0 / zoom) * ym / getHeight();
        xc = xc + ((double) (xm - getWidth() / 2.0) / (double) getWidth()) / zoom;
        yc = yc + ((double) (ym - getHeight() / 2.0) / (double) getHeight()) / zoom;
        zoom = zoom * ZOOM_MULTIPLIER;
        MAX_ITERATIONS = (int) (MAX_ITERATIONS * ITERATIONS_MULTIPLIER) + ITERATIONS_ADDER;
    }

//    private void zoomOut(int xm, int ym){
//        xc = xc + (xm - getWidth() / 2) / zoom;
//        yc = yc + (ym - getHeight() / 2) / zoom;
//        zoom = zoom / ZOOM_MULTIPLIER;
//        MAX_ITERATIONS = MAX_ITERATIONS / ITERATIONS_MULTIPLIER;
//    }
}
