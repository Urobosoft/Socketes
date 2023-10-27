package myclientsocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyClientSocket {

    public static void main(String[] args) {
        String ip = "192.168.1.117"; 
        int port = 9991; 

        Cifrar cifrador = new Cifrar(); 

        try (Socket socket = new Socket(ip, port);
             PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in))
        ) {
            Thread readThread = new Thread(() -> {
                try {
                    while (true) {
                        String msgRead = bufferedReader.readLine();
                        if (msgRead == null) {
                            break;
                        }
                        Logger.getLogger(MyClientSocket.class.getName()).log(Level.INFO, msgRead);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MyClientSocket.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            readThread.start();

            while (true) {
                System.out.println("Escribe un mensaje (o 'exit' para salir): ");
                String msgWrite = userInputReader.readLine();
                String mensajeCifrado = cifrador.cifrarMensaje(msgWrite);

                if ("exit".equalsIgnoreCase(msgWrite)) {
                    break;
                }

                printWriter.println(mensajeCifrado);
            }

            socket.close();

            Logger.getLogger(MyClientSocket.class.getName()).log(Level.INFO, "End");
        } catch (IOException ex) {
            Logger.getLogger(MyClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
