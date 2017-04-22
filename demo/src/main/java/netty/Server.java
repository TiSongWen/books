package netty;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;

/**
 * Created by tisong on 4/5/17.
 */
public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);

        Socket socket = serverSocket.accept();

        Writer out = new OutputStreamWriter(socket.getOutputStream());

        out.write("test" + "\r\n");

        out.flush();
    }
}
