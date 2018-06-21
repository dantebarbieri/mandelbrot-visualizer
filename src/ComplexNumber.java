public class ComplexNumber {

    protected double re;
    protected double im;

    public ComplexNumber() {
        re = 0;
        im = 0;
    }

    public ComplexNumber(double real) {
        re = real;
    }

    public ComplexNumber(double real, double imaginary) {
        re = real;
        im = imaginary;
    }

    public ComplexNumber(String number) {
        number = number.replace(" ", "");
        number = number.toLowerCase();
        number = number.replace("i", "");
        re = Double.parseDouble(number.substring(0, number.indexOf("+")));
        im = Double.parseDouble(number.substring(number.indexOf("+") + 1));
    }

    public ComplexNumber(ComplexNumber other) {
        this.re = other.re;
        this.im = other.im;
    }

    public ComplexNumber copy() {
        return new ComplexNumber(this);
    }

    public ComplexNumber conjugate() {
        return new ComplexNumber(this.re, -1 * this.im);
    }

    public double abs() {
        return Math.sqrt(Math.pow(re, 2) + Math.pow(im, 2));
    }

    public void pow(double power) {
        double r = abs();
        double theta = Math.atan(this.im / this.re);
        this.re = Math.pow(r, power) * Math.cos(theta * power);
        this.im = Math.pow(r, power) * Math.sin(theta * power);
    }

    public void plus(ComplexNumber other) {
        this.re = this.re + other.re;
        this.im = this.im + other.im;
    }

    public void times(ComplexNumber other) {
        this.re = this.re * other.re - this.im * other.im;
        this.im = this.re * other.im + this.im * other.re;
    }

}
