import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Server Class creates a ServerSocket at 9898 and waits for client
 * interaction. Creates private class executeClass once client connects.
 */
public class server {
	public static void main(String[] args) throws Exception {
        System.out.println("The server is running.");
        ServerSocket listener = new ServerSocket(9898);
        try {
            while (true) {
                new executeClass(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    }
    /**
     * Private class used to facilitate the client/server connection.
     */
    private static class executeClass extends Thread {
	/**
	 * Socket used during the connection.
	 */
        private Socket socket;

	/**
	 * executeClass constructor accepts Socket and confirms the connection.
	 * @param socket the Socket used for connection
	 */
        public executeClass(Socket socket) {
            this.socket = socket;
            System.out.println("Connected to a Client");
        }

	/**
	 * Method used to accept string and convert to capitalized string.
	 * Closes socket upon completetion.
	 */
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
