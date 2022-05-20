package com.pairprogramming.vendingmachine;

public interface VendingMachine {
    // return price of item
    public int getPriceByItem(String inItem) throws Exception;

    // vm should keep track of cumulative amount
    public int insertCoin(String coin) throws Exception;

    // vm should be able to refund (decide not to purchase anymore)
    public String refund();

    // calculate change and return item selected
    public String collectItemAndChange() throws Exception;

    // how much money earns + quantity of drinks left
    public void printStats();

    // print drinks + price
    public String displayMenu();
}


// display menu
// insert coins -> takes numerical input +
//