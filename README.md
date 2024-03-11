# Web Application Scanner

## Description

The Web Application Scanner is a Java program designed to scan a web application for potential security vulnerabilities, including SQL injection, Cross-Site Scripting (XSS), and Cross-Site Request Forgery (CSRF) attacks. It allows users to input the URL of a web application and performs a series of checks on the response from the server to identify any vulnerabilities present.

## Features

- **Vulnerability Detection:** The scanner identifies potential SQL injection vulnerabilities by checking for common SQL injection vectors in the server response.
- **Cross-Site Scripting (XSS) Detection:** It also identifies potential XSS vulnerabilities by searching for XSS attack vectors in the server response.
- **Cross-Site Request Forgery (CSRF) Detection:** Additionally, the scanner detects potential CSRF vulnerabilities by looking for CSRF attack vectors in the server response.
- **User Input:** Users can input the URL of the web application to be scanned, making the tool flexible and easy to use.

## Usage

1. **Input URL:** Run the program and provide the URL of the web application when prompted.
2. **Scan:** The program will send a GET request to the specified URL and analyze the server response.
3. **View Results:** Any detected vulnerabilities will be displayed, if present.

## Requirements

- Java Development Kit (JDK) 8 or higher

## How to Run

1. Compile the Java source file `WebAppScanner.java`.
   ```
   javac WebAppScanner.java
   ```

2. Run the compiled program.
   ```
   java WebAppScanner
   ```

## Disclaimer

This tool is intended for educational purposes only. Use it responsibly and only on web applications where you have explicit permission to perform security testing.
