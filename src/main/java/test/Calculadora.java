package test;

import java.util.Scanner;

public class Calculadora {

   public int operando1;

   public int operando2;

   public String operador;

   public int resultado;

   Calculadora(int oper1, int oper2, String signo) {
      this.operando1 = oper1;
      this.operando2 = oper2;
      this.operador = signo;
   }

   public int calcular() {

      if (this.operador.equals("+")) {
         resultado = operando1 + operando2;
      }

      if (this.operador.equals("-")) {
         resultado = operando1 - operando2;
      }

      if (this.operador.equals("*")) {
         resultado = this.operando1 * this.operando2;
      }

      if (this.operador.equals("^")) {
         resultado = (int) Math.pow(this.operando1, this.operando2);
      }

      if (this.operador.equals("%")) {
         resultado = this.operando1 % this.operando2;
      }

      return resultado;
   }

   /** @param args */
   public static void main(String[] args) {
      // TODO Auto-generated method stub

      String oper1;
      String oper2;
      String operador;

      @SuppressWarnings("resource")
      Scanner in = new Scanner(System.in);

      System.out.println("INGRESE OPERANDO 1");
      oper1 = in.next();

      System.out.println("INGRESE OPERANDO 2");
      oper2 = in.next();

      System.out.println("INGRESE OPERADOR");
      operador = in.next();

      Calculadora cal = new Calculadora(Integer.parseInt(oper1), Integer.parseInt(oper2), operador);

      int resultado = cal.calcular();

      System.out.println("EL RESULTADO ES: " + resultado);

   }
}
