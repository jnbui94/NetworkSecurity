
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 * Client class act as a client and connects to server.
 */
public class client {
	 private BufferedReader bufferIn;
	   private PrintWriter out;
	  private JFrame frame = new JFrame("Client");
	    private JTextField dataField = new JTextField(80);
	    private JTextArea messageArea = new JTextArea(15, 40);
	/**
	 * Client constructor creates client and recieves client input.
	 */
	public client() {
		// Layout GUI
        messageArea.setEditable(false);
        frame.getContentPane().add(dataField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");

        // Add Listeners
        dataField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent theE) {
                out.println(dataField.getText());
                   String response;
                try {
                    response = bufferIn.readLine();
                    if (response == null || response.equals("")) {
                          System.exit(0);
                      }
                } catch (IOException ex) {
                       response = "Error: " + ex;
                }
                messageArea.append(response + "\n");
                dataField.selectAll();
            }
        });
    }
    /**
     * Method allows client to server connection. Creates a socket at 
     * server 9898 and closes the socket at the end of interaction.
     */
    public void connectToServer() throws IOException {
        // Get the server.
        String serverAddress = JOptionPane.showInputDialog(
            frame,
            "Enter IP Address of Server:",
            "This Program will capitalize your words",
            JOptionPane.WARNING_MESSAGE);

        // Make connection and initialize streams
        Socket socket = new Socket(serverAddress, 9898);
        bufferIn = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        for (int i = 0; i < 3; i++) {
            messageArea.append(bufferIn.readLine() + "\n");
        }
        try {
            socket.close();
        } catch (IOException e) {
        	System.out.println("Couldn't close a socket, what's going on?");
        }
    }

    /**
     * Main method creates client and frame for text input. Calls connectToServer()
     * for client/server interaction.
     */
    public static void main(String[] args) throws Exception {
        client client = new client();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.pack();
        client.frame.setVisible(true);
        client.connectToServer();
    }

}
