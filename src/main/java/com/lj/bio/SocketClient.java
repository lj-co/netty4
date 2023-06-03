package com.lj.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;

@Slf4j
public class SocketClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("127.0.0.1", 9000);
        //向服务端发送数据
        socket.getOutputStream().write("刘杰，怎么样呢？".getBytes());
        socket.getOutputStream().flush();
        log.info("向服务端发送数据结束");
        byte[] bytes = new byte[1024];
        //接收服务端回传的数据
        int read = socket.getInputStream().read(bytes);
        log.info("接收到服务端的数据:{}", new String(bytes, 0, read));
        socket.close();
    }
}