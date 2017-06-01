import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
// Server Class
public class server {
	public static void main(String[] args) throws Exception {
        System.out.println("The server is running.");
        ServerSocket listener = new ServerSocket(9898);
        try {
            while (true) {
                new Capitalizer(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    }
	
    private static class Capitalizer extends Thread {
        private Socket socket;

        public Capitalizer(Socket socket) {
            this.socket = socket;
            System.out.println("Connected to a Client");
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                out.println("Enter a line with only a period to quit\n");

                while (true) {
                    String input = in.readLine();
                    if (input == null || input.equals(".")) {
                        break;
                    }
                    out.println(input.toUpperCase());
                }
            } catch (IOException e) {
                System.out.println("Error handling client# " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                	System.out.println("Socket Closing Error");
                }
                System.out.println("Connection with client# " +" closed");
            }
        }

    }
}
