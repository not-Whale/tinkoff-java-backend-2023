package edu.homework2;

public sealed interface Expr {
    double evaluate();

    public record Constant(double value) implements Expr {

        public Constant(Expr exprValue) {
            this(exprValue.evaluate());
        }

        @Override
        public double evaluate() {
            return this.value;
        }
    }
    public record Negate(double value) implements Expr {

        public Negate(Expr exprValue) {
            this(exprValue.evaluate());
        }

        @Override
        public double evaluate() {
            return -this.value;
        }
    }

    public record Exponent(double base, double exp) implements Expr {
        public Exponent(double base, Expr exprExp) {
            this(base, exprExp.evaluate());
        }

        public Exponent(Expr exprBase, double exp) {
            this(exprBase.evaluate(), exp);
        }

        public Exponent(Expr exprBase, Constant constantExp) {
            this(exprBase.evaluate(), constantExp.evaluate());
        }

        @Override
        public double evaluate() {
            return Math.pow(this.base, this.exp);
        }
    }

    public record Addition(double firstTerm, double secondTerm) implements Expr {
        public Addition(double firstTerm, Expr exprSecondTerm) {
            this(firstTerm, exprSecondTerm.evaluate());
        }

        public Addition(Expr exprFirstTerm, double secondTerm) {
            this(exprFirstTerm.evaluate(), secondTerm);
        }

        public Addition(Expr exprFirstTerm, Expr exprSecondTerm) {
            this(exprFirstTerm.evaluate(), exprSecondTerm.evaluate());
        }

        @Override
        public double evaluate() {
            return this.firstTerm + this.secondTerm;
        }
    }

    public record Subtraction(double firstTerm, double secondTerm) implements Expr {
        public Subtraction(double firstTerm, Expr exprSecondTerm) {
            this(firstTerm, exprSecondTerm.evaluate());
        }

        public Subtraction(Expr exprFirstTerm, double secondTerm) {
            this(exprFirstTerm.evaluate(), secondTerm);
        }

        public Subtraction(Expr exprFirstTerm, Expr exprSecondTerm) {
            this(exprFirstTerm.evaluate(), exprSecondTerm.evaluate());
        }

        @Override
        public double evaluate() {
            return this.firstTerm - this.secondTerm;
        }
    }

    public record Multiplication(double firstMul, double secondMul) implements Expr {
        public Multiplication(double firstMul, Expr exprSecondMul) {
            this(firstMul, exprSecondMul.evaluate());
        }

        public Multiplication(Expr exprFirstMul, double secondMul) {
            this(exprFirstMul.evaluate(), secondMul);
        }

        public Multiplication(Expr exprFirstMul, Expr exprSecondMul) {
            this(exprFirstMul.evaluate(), exprSecondMul.evaluate());
        }

        @Override
        public double evaluate() {
            return this.firstMul * this.secondMul;
        }
    }

    public record Division(double firstMul, double secondMul) implements Expr {
        public Division(double firstMul, Expr exprSecondMul) {
            this(firstMul, exprSecondMul.evaluate());
        }

        public Division(Expr exprFirstMul, double secondMul) {
            this(exprFirstMul.evaluate(), secondMul);
        }

        public Division(Expr exprFirstMul, Expr exprSecondMul) {
            this(exprFirstMul.evaluate(), exprSecondMul.evaluate());
        }

        @Override
        public double evaluate() {
            return this.firstMul / this.secondMul;
        }
    }
}
