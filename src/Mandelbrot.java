
public class Mandelbrot {

    final static int WIDTH = 500;
    final static int HEIGHT = 500;
    final static int MANDELBROT_ORDER = 2;
    final static int MAX_ITERATIONS = 64;
    final static double ITERATIONS_MULTIPILER = 1;
    final static int ITERATIONS_ADDER = 0;
    final static double ZOOM_MULTIPLIER = 1.5;
    final static byte dm = 2;
    static MandelbrotCanvas canvas;

    public static void main(String[] args) {
        canvas = new MandelbrotCanvas(WIDTH, HEIGHT);
        canvas.setOrder(MANDELBROT_ORDER);
        canvas.setIterations(MAX_ITERATIONS);
        canvas.setIterationMultiplier(ITERATIONS_MULTIPILER);
        canvas.setIterationAdder(ITERATIONS_ADDER);
        canvas.setZoom(0.1);
        canvas.setZoomMultiplier(ZOOM_MULTIPLIER);
        canvas.setDrawMode(dm);
        canvas.setSave(true);
        canvas.setOpen(true);
        canvas.setCrosshair(false);
        canvas.setXShift(-1.74006238257933990);
        canvas.setYShift(-0.02817533977921104);
//        canvas.setXShift(-0.16);
//        canvas.setYShift(1.0405);
//        canvas.setXShift(-1.75);
//        canvas.setYShift(0);
        int render = 0;
        while (render < 6000) {
            canvas.drawBrot();
            render++;
        }
    }
}
