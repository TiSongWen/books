package netty;

import java.io.*;
import java.net.Inet4Address;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Created by tisong on 4/4/17.
 */
public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8080);


        while (true) {
            //读取服务器端数据
            InputStreamReader reader = new InputStreamReader(socket.getInputStream());
            //向服务器端发送数据
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            System.out.print("请输入: \t");
            String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
            out.writeUTF(str);
            out.flush();

            BufferedReader reader1 = new BufferedReader(reader);

            String s = reader1.readLine();
//            StringBuilder sb = new StringBuilder();
//            for(int c = reader.read(); c != -1; c = reader.read()) {
//                sb.append(c);
//            }

            System.out.println("服务器端返回过来的是: " + s);
        }
    }
}
