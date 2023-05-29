import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.net.*;
import java.io.*;
import java.util.Scanner;


public class client {
    public static void main(String[] args) throws IOException {
        // Connect to Server.server
        Socket socket = new Socket(args[0], 9127);


        System.out.println("Connected to Server");// bookserver list of books

        // Create input and output streams
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in);


        // Get book menu from Server.server
        String response;
        while ((response = in.readLine()) != null) {
            System.out.println(response);
            if (response.endsWith(":")) {
                break;
            }
        }


        //Send message to Server.server from user input to request a book
        BufferedReader fromClient = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        while ((userInput = fromClient.readLine()) != null) {
            // Send request to Server.server
            out.println(userInput);

            // Get response from Server.server
            while ((response = in.readLine()) != null) {
                System.out.println(response);
                if (response.endsWith(":")) {
                    break;
                }
            }
//            }
            // Prompt for another book or exit
           // System.out.println("Enter the number of the book you want to read next, type 'close' to close book or  type 'exit' to quit:");
            userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("exit")) {
                break;
            }
            if (userInput.equalsIgnoreCase("back")) {


            }
        }

        // Close streams and socket
        in.close();
        out.close();
        socket.close();


    }
}
