package com.first.family.module1;

import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description: 阻塞IO示例
 * @author: cuiweiman
 * @date: 2024/10/8 14:26
 */
public class BioDemo {

    @Test
    public void testSocketServer() throws IOException {
        // 创建一个新的ServerSocket，监听端口的连接请求;
        ServerSocket serverSocket = new ServerSocket(9091);
        // accept() 将阻塞等待连接请求（不向下执行，直到连接到达）
        Socket clientSocket = serverSocket.accept();
        // 通过 套接字的流对象，派生处理过程中使用的流对象
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        String request, response;
        while ((request = in.readLine()) != null) {
            if ("Done".equals(request)) {
                break;
            }
            // 简单模拟 response
            response = request;
            out.println(response);
        }
    }

    @Test
    public void testSocketClient() throws IOException {
        Socket socket = new Socket("172.18.66.82", 9091);
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
        toServer.println("Hello Socket Server\r");
        toServer.println("Nice to meet you, bye bye\r");
        toServer.println("Done\r");
        String read;
        while ((read = fromServer.readLine()) != null) {
            System.out.println(read);
        }
    }
}
