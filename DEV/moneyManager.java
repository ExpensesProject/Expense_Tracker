package DEV;

import java.util.ArrayList;

public class moneyManager {
    private ArrayList<Expense> expenses;
    private ArrayList<income> incomes;

    public moneyManager() {
        this.expenses = new ArrayList<>();
        this.incomes = new ArrayList<>();
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void addIncome(income income) {
        incomes.add(income);
    }

    public void viewExpenses() {
        System.out.println("Expenses:");
        for (Expense expense : expenses) {
            System.out.println("Amount: " + expense.getAmount());
            System.out.println("Category: " + expense.getCategory());
            System.out.println("Date: " + expense.getDate());
            System.out.println("---------------------");
        }
    }

    public void viewIncomes() {
        System.out.println("Incomes:");
        for (income income : incomes) {
            System.out.println("Amount: " + income.getAmount());
            System.out.println("Category: " + income.getCategory());
            System.out.println("Date: " + income.getDate());
            System.out.println("---------------------");
        }
    }

    public income[] addIncome() {
        return null;
    }

    public Expense[] addExpense() {
        return null;
    }

    public income[] getIncomes() {
        return null;
    }

    public Expense[] getExpenses() {
        return null;
    }
}

