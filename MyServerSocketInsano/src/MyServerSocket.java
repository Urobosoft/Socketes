
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
    private static int clientCounter = 1;

    public static void main(String[] args) {
        int port = 9991;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Logger.getLogger(MyServerSocket.class.getName()).log(Level.INFO, "Waiting...");

            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(socket);
                connectedClients.add(client);

                // Notificar al servidor que un cliente se ha conectado
                Logger.getLogger(MyServerSocket.class.getName()).log(Level.INFO, client.getClientName() + " se ha conectado");

                // Notificar a todos los clientes que un nuevo cliente se ha conectado
                for (ClientHandler c : connectedClients) {
                    if (c != client) {
                        c.sendMessage("El invocador: " + client.getClientName() + " se ha conectado");
                    }
                }

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
        private String clientName;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                this.printWriter = new PrintWriter(socket.getOutputStream(), true);
                this.clientName = "Cliente " + clientCounter;
            } catch (IOException e) {
                Logger.getLogger(MyServerSocket.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        public String getClientName() {
            return clientName;
        }

        @Override
        public void run() {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                while (true) {
                    String msgRead = bufferedReader.readLine();
                    if (msgRead == null) {
                        break; // Si el cliente se desconecta, sal del bucle
                    }
                    String mensajeCifrado = clientName + ": Mensaje cifrado: " + msgRead;
                    String mensajeDescifrado = Descifrar.descifrarMensaje(msgRead);
                    String mensajeConNombre = clientName + ": " + mensajeDescifrado;
                    Logger.getLogger(MyServerSocket.class.getName()).log(Level.INFO, mensajeCifrado);
                    Logger.getLogger(MyServerSocket.class.getName()).log(Level.INFO, mensajeConNombre);

                    // Envia el mensaje a todos los clientes
                    for (ClientHandler client : connectedClients) {
                        if (client != this) {
                            client.sendMessage(mensajeConNombre);
                        }
                    }
                }

                // Notificar al servidor cuando un cliente se desconecta
                connectedClients.remove(this);
                Logger.getLogger(MyServerSocket.class.getName()).log(Level.INFO, "Cliente desconectado unu: " + clientName);

                // Notificar a todos los clientes cuando un cliente se desconecta
                for (ClientHandler c : connectedClients) {
                    c.sendMessage("Cliente desconectado: " + clientName);
                }

                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(MyServerSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void sendMessage(String message) {
            printWriter.println(message);
        }
    }
}
