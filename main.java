// created by Wuttikorn Jiraroongrojana
// created date 18-12-2024

//Currency Converter
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        System.out.println("Welcome to Currency Converter");
        System.out.print("Convert from: ");
        Scanner scanner = new Scanner(System.in);
        String BasedCurrency = scanner.next().toUpperCase();
        System.out.print("Convert to: ");
        String TargetCurrency = scanner.next().toUpperCase();;
        System.out.print("Amount: ");
        double Amount = scanner.nextDouble();
        
        // Need to call the API to get the exchange rate
        double convertedAmount = convertCurrency(BasedCurrency, TargetCurrency, Amount);

        if (convertedAmount != -1) {
            System.out.println(Amount + " " + BasedCurrency + " is " + convertedAmount + " " + TargetCurrency + ".");
        } else {
            System.out.println("Unable to fetch conversion rate. Please try again later.");
        }

        scanner.close();
    }
    
    public static double convertCurrency(String BasedCurrency, String TargetCurrency, double Amount){
        String apiKey = "API Key"; // Replace with your API key
        String urlString = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + BasedCurrency;
        try {
            // Create the URL object
            URL url = new URL(urlString);

            // Open the connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse the JSON response
            String jsonResponse = response.toString();
            double exchangeRate = parseExchangeRate(jsonResponse, TargetCurrency);

            // Perform the conversion
            return exchangeRate * Amount;

        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Indicate failure
        }
    }


    public static double parseExchangeRate(String jsonResponse, String TargetCurrency) {
        try {
            // Use a JSON parsing library (e.g., Gson or Jackson) to extract the exchange rate
            // Here's a basic placeholder:
            String key = "\"" + TargetCurrency + "\":";
            int startIndex = jsonResponse.indexOf(key) + key.length();
            int endIndex = jsonResponse.indexOf(",", startIndex);
            if (endIndex == -1) endIndex = jsonResponse.indexOf("}", startIndex);
            return Double.parseDouble(jsonResponse.substring(startIndex, endIndex));
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Indicate failure
        }
    }
}
