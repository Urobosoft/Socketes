package myserversocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyServerSocket {
    private static List<ClientHandler> connectedClients = new ArrayList<>();
    private static int clientCounter = 0;

    public static void main(String[] args) {
        int port = 9991;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Logger.getLogger(MyServerSocket.class.getName()).log(Level.INFO, "Waiting...");

            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(socket);
                connectedClients.add(client);
                Thread clientThread = new Thread(client);
                clientThread.start();
                clientCounter++; 
            }
        } catch (IOException ex) {
            Logger.getLogger(MyServerSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter printWriter;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))
            ) {
                String clientName = "Cliente " + clientCounter; 

                while (true) {
                    String msgRead = bufferedReader.readLine();
                    if (msgRead == null) {
                        break; // Si el cliente se desconecta, sal del bucle
                    }
                    String mensajeDescifrado = Descifrar.descifrarMensaje(msgRead);
                    String mensajeConNombre = clientName + ": " + mensajeDescifrado;
                    Logger.getLogger(MyServerSocket.class.getName()).log(Level.INFO, mensajeConNombre);

                    // Envia el mensaje a todos los clientes
                    for (ClientHandler client : connectedClients) {
                        if (client != this) {
                            client.sendMessage(mensajeConNombre);
                        }
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(MyServerSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void sendMessage(String message) {
            printWriter.println(message);
        }
    }
}
