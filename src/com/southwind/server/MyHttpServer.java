package com.southwind.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MyHttpServer {
	public static String WebContent = System.getProperty("user.dir") + File.separator + "WebContent";
	private int port = 8080;
	private boolean isShutdown = false;
	public void receiving() {
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}

		// 接收请求
		while (!isShutdown) {
			Socket socket = null;
			InputStream is = null;
			OutputStream os = null;
			try {
				//获取连接
				socket = serverSocket.accept();
				is = socket.getInputStream();
				os = socket.getOutputStream();
				//解析请求
				MyHttpRequest request = new MyHttpRequest(is);
				request.parse();
				//响应
				MyHttpResponse response = new MyHttpResponse(os);
				response.sendStaticResource(request);

			} catch (Exception ex) {
				ex.printStackTrace();
			}finally {
				// 关闭
				try {
					socket.close();
					is.close();
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}