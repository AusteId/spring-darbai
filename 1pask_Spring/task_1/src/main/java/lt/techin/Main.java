package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
  public static void main(String[] args) {

    while (true) {
      System.out.println("Yes or No?");
      Scanner scanner = new Scanner(System.in);
      String input = scanner.nextLine();

      if (input.equalsIgnoreCase("No")) {
        break;
      }
    }
  }
}