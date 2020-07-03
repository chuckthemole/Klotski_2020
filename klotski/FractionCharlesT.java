package klotski;
/**
 * Program name:    FractionCharlesT.java
 * Discussion:      Final
 * Written by:      Charles T
 * Due Date:        
 */

import java.util.Scanner;

/*
FractionCharlesT defines a fraction with an integer
numerator, num, and integer denominator, denom.
*/

class FractionCharlesT {
    private int num;
    private int denom;

    //Constructors
    public FractionCharlesT() {      
        num = 0;
        denom = 1;
    }

    public FractionCharlesT(int integer) {
        num = integer;
        denom = 1;
    }

    public FractionCharlesT(int n, int d) {
        int gcd;

        if (d == 0) {
            System.out.print("\n0 is not acceptable for " +
                    "denominator!");
            do {
                System.out.print("\nEnter denominator: ");
                d = scanner();
                if (d == 0) {
                    System.out.print("0 is not " +
                            "acceptable for " +
                            " denominator!");
                }
            } while (d == 0);
        }

        gcd = gcdRecur(n, d);
        n /= gcd;
        d /= gcd;
        num = (d < 0) ? -n : n;
        denom = (d < 0) ? -d : d;
    }

    public FractionCharlesT(FractionCharlesT f) {
        num = f.num;
        denom = f.denom;
    }

    //Member Methods
    public void print(FractionCharlesT f) {
        System.out.print(f);
    }

    @Override
    public String toString() {
        return "      num : " + num + "\n      denom : " + denom;
    }

    public void print(FractionCharlesT f1, FractionCharlesT f2,
            FractionCharlesT result) {
        System.out.print("      (" + f1.num + "/" + f1.denom +
                ", " + f2.num + "/" + f2.denom + ", " +
                result.num  + "/" +result.denom + ")\n");
    }

    public void print(FractionCharlesT f1, FractionCharlesT f2) {
        System.out.print("      (" + f1.num + "/" + f1.denom +
                ", " + f2.num + "/" + f2.denom + ", " +
                "no result)\n");
    }

    public int getNum() {
        return num;
    }
    
    public int getDenom() {
        return denom;
    }

    public void setNum(int n) {
        int d = denom;
        int gcd;

        gcd = gcdRecur(n, d);
        n /= gcd;
        d /= gcd;
        num = (d < 0) ? -n : n;
        denom = (d < 0) ? -d : d;
    }

    public void setDenom(int d) {
        int n = num;
        int gcd;

        while (d == 0) {
            System.out.print("\nCan't have denominator equal to zero." +
                    "\nEnter a denominator: ");
            d = scanner();
        }

        gcd = gcdRecur(n, d);
        n /= gcd;
        d /= gcd;
        num = (d < 0) ? -n : n;
        denom = (d < 0) ? -d : d;
    }

    public static int gcdRecur(int arg1, int arg2) {
        if (arg1 % arg2 == 0)
            return arg2;
        else
            return gcdRecur(arg2, arg1 % arg2);
    }

    public FractionCharlesT add(FractionCharlesT f) {
        return new FractionCharlesT((num * f.denom) + (f.num * denom),
                denom * f.denom);
    }

    public FractionCharlesT add(int del) {
        return new FractionCharlesT(num + denom * del, denom);
    }
    
    public void update(int del) {
        num = del;
        denom = 1;
    }
    
    public void update(FractionCharlesT f) {
        num = f.num;
        denom = f.denom;
    }
    
    public void updateBy(int del) {
        int n, d, gcd;
        
        d = denom;
        n = num + denom * del;
        gcd = gcdRecur(n, d);
        n /= gcd;
        d /= gcd;
        num = (d < 0) ? -n : n;
        denom = (d < 0) ? -d : d;
    }
    
    public void updateBy(FractionCharlesT f) {
        int n, d, gcd;
        
        n = num * f.denom + denom * f.num;
        d = denom * f.denom;
        gcd = gcdRecur(n, d);
        n /= gcd;
        d /= gcd;
        num = (d < 0) ? -n : n;
        denom = (d < 0) ? -d : d;
    }
    
    public FractionCharlesT subtract(FractionCharlesT f) {
        return new FractionCharlesT((num * f.denom) - (f.num * denom),
                denom * f.denom);
    }

    public FractionCharlesT multiply(FractionCharlesT f) {
        return new FractionCharlesT(num * f.num, denom * f.denom);
    }
    
    public FractionCharlesT multiply(int i) {
        return new FractionCharlesT(i * num, denom);
    }

    public FractionCharlesT divide(FractionCharlesT f) {
        return new FractionCharlesT(num * f.denom, denom * f.num);
    }

    public boolean compare(FractionCharlesT f) {
        return false;
    }
    
    public static int scanner() {
        Scanner scanner;
        scanner = new Scanner(System.in);
        int output;

        output = scanner.nextInt();
        scanner.close();
        return output;
    }
}