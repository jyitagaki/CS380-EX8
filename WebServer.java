import com.sun.deploy.util.SessionState;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    public static void main(String[] args) throws Exception{
        try(ServerSocket ssocket = new ServerSocket(8080)){
    while(true) {
        Socket socket = ssocket.accept();
        InputStream is = socket.getInputStream();
        BufferedReader buff = new BufferedReader(new InputStreamReader(is));
        OutputStream os = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(os, true);
        Runnable run = () -> {
            try {
                String read = buff.readLine();
                String fileName = "www" + read.split(" ")[1];
                System.out.println(fileName);
                File file = new File(fileName);
                BufferedReader br = new BufferedReader(new FileReader(file));
                if (fileName.equals("www/hello.html")) {
                    pw.print("HTTP/1.1 200 OK \n Content-type: text/html \n Content-length: " + file.length());
                    read = br.readLine();
                    while (read != null) {
                        pw.print(read + "\n");
                        read = br.readLine();
                    }
                    br.close();
                    pw.close();
                } else {
                    pw.print("HTTP/1.1 404 Not Found \n Content-type: text/html \n Content-length: " + file.length());
                    read = br.readLine();
                    while (read != null) {
                        pw.print(read + "\n");
                        read = br.readLine();
                    }
                    br.close();
                    pw.close();
                }
            } catch(Exception e) {
                System.out.println(e);
            }
        };
    }
        }
    }
}
