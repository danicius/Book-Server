package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class server {

    public static void main(String[] args) throws IOException {
        //list of books/**////*/*/*/**/*/*/*/
        List<String> books = new ArrayList<String>();
        books.add("RomeoJuliet.txt");
        books.add("TheGreatGatsby.txt");
        books.add("Dracula.txt");
        books.add("PeterPan.txt");

        // Create Server.server socket
        try {
            ServerSocket serverSocket = new ServerSocket(9127);
            System.out.println("Server started. Waiting for client to connect...\n");


            // Accept client connection
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outToClient = new PrintWriter(socket.getOutputStream(), true);
            // DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
            String bookselection = " ";
            // Send menu to client
            outToClient.println("Welcome to the Book Server!\n");
            outToClient.println("Showing you our classic and free books\n");
            // outToClient.writeBytes("Choose a book by entering its number:\n");
            for (int i = 0; i < books.size(); i++) {
                outToClient.println((i + 1) + ". " + books.get(i) + "\n");
            }
            outToClient.println("Enter the number of the book you want to read or type 'exit' to quit:\n");


            int pageSize = 100;
            //int charCount = 100;
            // List<String> bookToPages = new ArrayList<>();

            while (true) {
                bookselection = fromClient.readLine();
                System.out.println("FROM CLIENT: " + bookselection);

                if (bookselection.equalsIgnoreCase("exit")) {
                    System.out.println("Client disconnected.");
                    socket.close();
                    break;
                }
//                if (bookselection.equalsIgnoreCase("back")) {//
//                    outToClient.println("Showing you our classic and free books\n");
//                    for (int i = 0; i < books.size(); i++) {
//                        outToClient.println((i + 1) + ". " + books.get(i) + "\n");
//                    }
//                    outToClient.println("Enter the number of the book you want to read or type 'exit' to quit:\n");
//                    continue;
//                }
                try {
                    int selection = Integer.parseInt(bookselection);
                    if (!bookselection.equalsIgnoreCase("back")) {
                        if (selection < 1 || selection > books.size() || bookselection.isEmpty() || bookselection == null) {
                            outToClient.println("ERROR: Not a valid book number." + '\n');
                            continue;
                        } else {
                            String bookFileName = books.get(selection - 1);

                            BufferedReader reader = new BufferedReader(new FileReader(bookFileName));
                            String line;
                            StringBuilder page = new StringBuilder();
                            int lineCount = 0;
                            int pageCount = 0;

                            while ((line = reader.readLine()) != null) {
                                if (line.trim().isEmpty()) {
                                    // skip empty lines
                                    continue;
                                }
                                page.append(line).append('\n');
                                lineCount++;
                                if (lineCount == pageSize || pageCount == 0) {
                                    outToClient.println("Page " + (pageCount + 1) + ":");
                                    outToClient.println("Press Enter to contiune book or type 'exit' to quit: " + '\n');
                                    System.out.println(page);
                                    outToClient.println(page);
                                    pageCount++;
                                    page.setLength(0);
                                    lineCount = 0;
                                    fromClient.readLine(); // wait for client to press enter
                                }
                            }
                            if (lineCount > 0) {

                                outToClient.println("Press Enter to contiune book or type 'exit' to quit: " + '\n');

                                //System.out.println(page);
                                outToClient.println(page);
                            }
                            if (fromClient.readLine().equalsIgnoreCase("back")) {
                                outToClient.println("Showing you our classic and free books\n");
                                for (int i = 0; i < books.size(); i++) {
                                    outToClient.println((i + 1) + ". " + books.get(i) + "\n");
                                }
                                break;
                            }
                            reader.close();
                            outToClient.println("Press Enter to contiune book or type 'exit' to quit: " + '\n');
                        }
                    }

//
//                        if(lineCount > 0) {
//                            outToClient.writeBytes(page.toString());
//                            System.out.println(page.toString());
//                        }
//                        reader.close();
//                        outToClient.writeBytes("Enter the number of the book you want to read or type 'exit' to quit: " + '\n');
//                    }

//                } catch (NumberFormatException e) {
//                    outToClient.writeBytes("ERROR: Not a valid command." + '\n');
//                    outToClient.writeBytes("Enter the number of the book you want to read or type 'exit' to quit: " + '\n');
//                }
//            }
//
//

                } catch (IOException e) {
                    System.out.println("Exception caught when trying to listen on port 9127 or listening for a connection");
                    System.out.println(e.getMessage());
                }


            }
        } finally {
            System.out.println("Server stopped.");
        }
    }
}