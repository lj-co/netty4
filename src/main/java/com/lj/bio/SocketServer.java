package com.lj.bio;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
public class SocketServer {

    private static final ExecutorService FIXED_THREAD_POOL = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);
        while (true) {
            log.info("等待连接。。");
            //阻塞方法
            Socket clientSocket = serverSocket.accept();
            log.info("有客户端连接了。。");
            FIXED_THREAD_POOL.submit(() -> {
                try {
                    // 处理客户端io
                    handler(clientSocket);
                } catch (IOException e) {
                    log.error("客户端IO处理错误，exception：{}", ExceptionUtils.getFullStackTrace(e));
                }
            });
        }
    }

    private static void handler(Socket clientSocket) throws IOException {
        byte[] bytes = new byte[1024];
        log.info("准备read。。");
        //接收客户端的数据，阻塞方法，没有数据可读时就阻塞
        int read = clientSocket.getInputStream().read(bytes);
        log.info("read完毕。。");
        if (read != -1) {
            log.info("接收到客户端的数据：{}", new String(bytes, 0, read));
        }
        clientSocket.getOutputStream().write("你好刘杰，真帅！！！".getBytes());
        clientSocket.getOutputStream().flush();
    }
}