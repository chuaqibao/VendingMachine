package com.pairprogramming;

import com.pairprogramming.vendingmachine.VendingMachine;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // initialise the vm
        
        Scanner in = new Scanner(System.in);
        VM vm = new VM(10, 10, 10);

        while(true) {
            try {
                // vm to display it's inventory
                System.out.println(vm.displayMenu());
                String input = in.nextLine(); // select item e.g. COKE
                // vm to prompt to insert coin until sufficient amount
                System.out.println("Please insert " + vm.getPriceByItem(input) + " cents.");
                int balance = vm.insertCoin(in.nextLine()); // insert coin e.g. 10
                while(balance > 0) {
                    System.out.println("Please insert " + balance + " cents or enter R for refund");
                    input = in.nextLine();
                    if(input.equalsIgnoreCase("R")) {
                        System.out.println(vm.refund());
                        return;
                    } else {
                        balance = vm.insertCoin(input);
                    }
                }
                // vm display item, and change
                System.out.println(vm.collectItemAndChange());
                vm.reset();
                vm.printStats();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }
}

class VM implements VendingMachine {
    String drink_request;
//    int balance;
    int amt_inserted;

    int five_cent_coins = 10;
    int ten_cent_coins = 10;
    int twenty_cent_coins = 10;

    int coke_price = 25;
    int pepsi_price = 35;
    int soda_price = 45;

    int num_of_coke;
    int num_of_pepsi;
    int num_of_soda;

    VM(int num_of_coke, int num_of_pepsi, int num_of_soda) {
        this.num_of_coke = num_of_coke;
        this.num_of_pepsi = num_of_pepsi;
        this.num_of_soda = num_of_soda;
    }

    @Override
    public int getPriceByItem(String inItem) throws Exception {
        drink_request = inItem;
        if (inItem.equals("c")) {
            return coke_price;
        } else if (inItem.equals("p")) {
            return pepsi_price;
        } else {
            return soda_price;
        }
    }

    @Override
    public int insertCoin(String coin) throws Exception {
        int c = Integer.parseInt(coin);
        System.out.println("coin: " + c);
        if (c == 5 || c == 10 || c == 20) {
            amt_inserted += c;
        }
        return getPriceByItem(drink_request) - amt_inserted;
    }

    @Override
    public String refund() {
        // give back the coins
        try {
            return "Refunded " + dispenseRefund();
        } catch (Exception e) {
            return "An error has occurred: " + e;
        }
    }

    @Override
    public String collectItemAndChange() throws Exception {
        if (drink_request.equals("c")) {
            num_of_coke--;
        } else if (drink_request.equals("p")) {
            num_of_pepsi--;
        } else {
            num_of_soda--;
        }
        return "Dispensed " + drink_request + ", Dispensed coins " + dispenseChange();
    }

    public String dispenseChange() throws Exception {
        int five_cents_to_refund = 0;
        int ten_cents_to_refund = 0;
        int twenty_cents_to_refund = 0;

        int change = amt_inserted - getPriceByItem(drink_request);
        System.out.println("Change: " + change);
        System.out.println("Drink Price: " + getPriceByItem(drink_request));
        System.out.println("Amount inserted: " + amt_inserted);

        while (change > 0) {
            if (change >= 20) {
                change -= 20;
                twenty_cents_to_refund++;
            } else if (change >= 10) {
                change -= 10;
                ten_cents_to_refund++;
            } else {
                change -= 5;
                five_cents_to_refund++;
            }
        }
        return five_cents_to_refund + " 5 cents, " + ten_cents_to_refund + " 10 cents, " + twenty_cents_to_refund + " 20 cents";
    }

    public String dispenseRefund() {
        int five_cents_to_refund = 0;
        int ten_cents_to_refund = 0;
        int twenty_cents_to_refund = 0;

        while (amt_inserted > 0) {
            if (amt_inserted >= 20) {
                amt_inserted -= 20;
                twenty_cents_to_refund++;
            } else if (amt_inserted >= 10) {
                amt_inserted -= 10;
                ten_cents_to_refund++;
            } else {
                amt_inserted -= 5;
                five_cents_to_refund++;
            }
        }
        return five_cents_to_refund + " 5 cents, " + ten_cents_to_refund + " 10 cents, " + twenty_cents_to_refund + " 20 cents";
    }

    @Override
    public void printStats() {

    }

    public void reset() {
        amt_inserted = 0;
    }

    @Override
    public String displayMenu() {
        System.out.println("Coke(25), Pepsi(35), Soda(45)");
        return null;
    }
}
