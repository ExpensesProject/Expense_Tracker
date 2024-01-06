package DEV;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Headers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class ExpenseTrackerBackend {

    private static final moneyManager manager = new moneyManager();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/addExpense", new AddExpenseHandler());
        server.createContext("/addIncome", new AddIncomeHandler());
        server.createContext("/viewExpenses", new ViewExpensesHandler());
        server.createContext("/viewIncomes", new ViewIncomesHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("Server started on port 8080");
    }

    static class AddExpenseHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                try {
                    // Read data from the request body
                    String requestBody = new String(exchange.getRequestBody().readAllBytes());

                    // Parse data and add Expense
                    String[] data = requestBody.split("&");
                    double amount = Double.parseDouble(data[0].split("=")[1]);
                    String category = data[1].split("=")[1];
                    String date = data[2].split("=")[1];

                    // Log received data to the console
                    System.out.println("Received Expense Data:");
                    System.out.println("Amount: " + amount);
                    System.out.println("Category: " + category);
                    System.out.println("Date: " + date);

                    Expense expense = new Expense(amount, category, date);
                    manager.addExpense(expense);

                    // Send a success response
                    sendResponse(exchange, "Expense added successfully");
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    // Handle parsing errors
                    sendErrorResponse(exchange, "Invalid data format");
                }
            } else {
                // Handle unsupported methods
                sendErrorResponse(exchange, "Method not allowed");
            }
        }
    }

    static class AddIncomeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                try {
                    // Read data from the request body
                    String requestBody = new String(exchange.getRequestBody().readAllBytes());

                    // Parse data and add Income
                    String[] data = requestBody.split("&");
                    double amount = Double.parseDouble(data[0].split("=")[1]);
                    String category = data[1].split("=")[1];
                    String date = data[2].split("=")[1];

                    // Log received data to the console
                    System.out.println("Received Income Data:");
                    System.out.println("Amount: " + amount);
                    System.out.println("Category: " + category);
                    System.out.println("Date: " + date);

                    income income = new income(amount, category, date);
                    manager.addIncome(income);

                    // Send a success response
                    sendResponse(exchange, "Income added successfully");
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    // Handle parsing errors
                    sendErrorResponse(exchange, "Invalid data format");
                }
            } else {
                // Handle unsupported methods
                sendErrorResponse(exchange, "Method not allowed");
            }
        }
    }

    static class ViewExpensesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
           
            if ("GET".equals(exchange.getRequestMethod())) {
                // Build a response with expense details
                StringBuilder response = new StringBuilder("Expenses:\n");
                for (Expense expense : manager.getExpenses()) {
                    response.append("Amount: ").append(expense.getAmount()).append("\n");
                    response.append("Category: ").append(expense.getCategory()).append("\n");
                    response.append("Date: ").append(expense.getDate()).append("\n");
                    response.append("---------------------\n");
                }

                sendResponse(exchange, response.toString());
            } else {
                // Handle unsupported methods
                sendErrorResponse(exchange, "Method not allowed");
            }
        }
    }

    static class ViewIncomesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            
            if ("GET".equals(exchange.getRequestMethod())) {
                // Build a response with income details
                StringBuilder response = new StringBuilder("Incomes:\n");
                for (income income : manager.getIncomes()) {
                    response.append("Amount: ").append(income.getAmount()).append("\n");
                    response.append("Category: ").append(income.getCategory()).append("\n");
                    response.append("Date: ").append(income.getDate()).append("\n");
                    response.append("---------------------\n");
                }

                sendResponse(exchange, response.toString());
            } else {
                // Handle unsupported methods
                sendErrorResponse(exchange, "Method not allowed");
            }
        }
    }

    private static void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private static void sendErrorResponse(HttpExchange exchange, String message) throws IOException {
        exchange.sendResponseHeaders(400, message.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(message.getBytes());
        }
    }
}
