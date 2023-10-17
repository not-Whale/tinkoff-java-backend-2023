package edu.homework2.calculator;

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

    public record Negate(Expr value) implements Expr {
        public Negate(double doubleValue) {
            this(new Constant(doubleValue));
        }

        @Override
        public double evaluate() {
            return (-1) * this.value.evaluate();
        }
    }

    public record Exponent(Expr base, Expr power) implements Expr {
        public Exponent(double doubleBase, Expr power) {
            this(new Constant(doubleBase), power);
        }

        public Exponent(Expr base, double doublePower) {
            this(base, new Constant(doublePower));
        }

        public Exponent(double doubleBase, double doublePower) {
            this(new Constant(doubleBase), new Constant(doublePower));
        }

        @Override
        public double evaluate() {
            return Math.pow(this.base.evaluate(), this.power.evaluate());
        }
    }

    public record Addition(Expr leftTerm, Expr rightTerm) implements Expr {
        public Addition(double doubleLeftTerm, Expr rightTerm) {
            this(new Constant(doubleLeftTerm), rightTerm);
        }

        public Addition(Expr leftTerm, double doubleRightTerm) {
            this(leftTerm, new Constant(doubleRightTerm));
        }

        public Addition(double doubleLeftTerm, double doubleRightTerm) {
            this(new Constant(doubleLeftTerm), new Constant(doubleRightTerm));
        }

        @Override
        public double evaluate() {
            return this.leftTerm.evaluate() + this.rightTerm.evaluate();
        }
    }

    public record Subtraction(Expr leftTerm, Expr rightTerm) implements Expr {
        public Subtraction(double doubleLeftTerm, Expr rightTerm) {
            this(new Constant(doubleLeftTerm), rightTerm);
        }

        public Subtraction(Expr leftTerm, double doubleRightTerm) {
            this(leftTerm, new Constant(doubleRightTerm));
        }

        public Subtraction(double doubleLeftTerm, double doubleRightTerm) {
            this(new Constant(doubleLeftTerm), new Constant(doubleRightTerm));
        }

        @Override
        public double evaluate() {
            return this.leftTerm.evaluate() - this.rightTerm.evaluate();
        }
    }

    public record Multiplication(Expr leftMul, Expr rightMul) implements Expr {
        public Multiplication(double doubleLeftMul, Expr rightMul) {
            this(new Constant(doubleLeftMul), rightMul);
        }

        public Multiplication(Expr leftMul, double doubleRightMul) {
            this(leftMul, new Constant(doubleRightMul));
        }

        public Multiplication(double doubleLeftMul, double doubleRightMul) {
            this(new Constant(doubleLeftMul), new Constant(doubleRightMul));
        }

        @Override
        public double evaluate() {
            return this.leftMul.evaluate() * this.rightMul.evaluate();
        }
    }

    public record Division(Expr leftMul, Expr rightMul) implements Expr {
        public Division(double doubleLeftMul, Expr rightMul) {
            this(new Constant(doubleLeftMul), rightMul);
        }

        public Division(Expr leftMul, double doubleRightMul) {
            this(leftMul, new Constant(doubleRightMul));
        }

        public Division(double doubleLeftMul, double doubleRightMul) {
            this(new Constant(doubleLeftMul), new Constant(doubleRightMul));
        }

        @Override
        public double evaluate() {
            return this.leftMul.evaluate() / this.rightMul.evaluate();
        }
    }
}
