import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 12345;

        try (Socket socket = new Socket(hostname, port)) {
            new Thread(new ReadTask(socket)).start();
            new Thread(new WriteTask(socket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ReadTask implements Runnable {
        private Socket socket;

        public ReadTask(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class WriteTask implements Runnable {
        private Socket socket;

        public WriteTask(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 Scanner scanner = new Scanner(System.in)) {
                String message;
                while (true) {
                    message = scanner.nextLine();
                    out.println(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
