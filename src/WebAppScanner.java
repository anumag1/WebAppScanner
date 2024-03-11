import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebAppScanner {

    // SQL injection vectors to check for potential SQL injection vulnerabilities
    private static final String[] SQL_INJECTION_VECTORS = {
            "'", "\"", "--", ";", "/*", "--", "*/",
            "'; DROP TABLE users; --", "' OR 1=1; --"
    };

    // Cross-Site Scripting (XSS) attack vectors to check for potential XSS vulnerabilities
    private static final String[] XSS_ATTACK_VECTORS = {
            "<script>", "</script>", "<img src=\"javascript:alert('XSS')\">",
            "<onload=\"alert('XSS')\">", "<body onload=\"alert('XSS')\">",
            "<form action=\"javascript:alert('XSS')\">",
            "<input type=\"text\" onfocus=\"alert('XSS')\">",
            "<a href=\"javascript:alert('XSS')\">",
            "<button onclick=\"alert('XSS')\">",
            "<marquee>alert('XSS')</marquee>"
    };

    // Cross-Site Request Forgery (CSRF) attack vectors to check for potential CSRF vulnerabilities
    private static final String[] CSRF_ATTACK_VECTORS = {
            "<form action=\"https://example.com/vulnerable-endpoint\" method=\"post\">",
            "<input type=\"text\" name=\"username\" value=\"attacker\">",
            "<input type=\"password\" name=\"password\" value=\"password\">",
            "<input type=\"submit\" value=\"Login\">",
            "</form>"
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the URL of the web application to scan: ");
        String urlString = scanner.nextLine();

        try {
            // Create URL object
            URL url = new URL(urlString);
            // Open HTTP connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Set request method to GET
            connection.setRequestMethod("GET");
            // Set User-Agent header to simulate a browser
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.5359.125 Safari/537.36");

            // Get HTTP response code
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // If response is OK, read response content
                String response = readResponse(connection.getInputStream());

                // Check for vulnerabilities in the response
                List<String> vulnerabilities = checkForVulnerabilities(response);

                // Print the vulnerabilities found, if any
                if (vulnerabilities.isEmpty()) {
                    System.out.println("No vulnerabilities detected.");
                } else {
                    System.out.println("The following vulnerabilities were detected:");
                    for (String vulnerability : vulnerabilities) {
                        System.out.println(vulnerability);
                    }
                }
            } else {
                // If response code is not OK, print error message
                System.out.println("Error retrieving response from the server. Error code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to read response from InputStream and convert to String
    private static String readResponse(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder responseBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            responseBuilder.append(line);
        }
        reader.close();
        return responseBuilder.toString();
    }

    // Method to check for vulnerabilities in the response
    private static List<String> checkForVulnerabilities(String response) {
        List<String> vulnerabilities = new ArrayList<>();

        // Check for SQL injection vulnerabilities
        for (String vector : SQL_INJECTION_VECTORS) {
            if (containsPattern(response, Pattern.quote(vector))) {
                vulnerabilities.add("Potential SQL Injection: " + vector);
            }
        }

        // Check for XSS vulnerabilities
        for (String vector : XSS_ATTACK_VECTORS) {
            if (containsPattern(response, Pattern.quote(vector))) {
                vulnerabilities.add("Potential XSS Attack: " + vector);
            }
        }

        // Check for CSRF vulnerabilities
        for (String vector : CSRF_ATTACK_VECTORS) {
            if (containsPattern(response, Pattern.quote(vector))) {
                vulnerabilities.add("Potential CSRF Attack: " + vector);
            }
        }

        return vulnerabilities;
    }

    // Method to check if the response contains a certain pattern
    private static boolean containsPattern(String text, String pattern) {
        Pattern compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = compiledPattern.matcher(text);
        return matcher.find();
    }
}
