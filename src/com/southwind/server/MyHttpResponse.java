package com.southwind.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MyHttpResponse {
	private OutputStream output;

	public MyHttpResponse(OutputStream output) {
		this.output = output;
	}

	public void sendStaticResource(MyHttpRequest request) throws IOException {
		byte[] bytes = new byte[1024];
		FileInputStream fis = null;
		String filePath = request.getUri() == null ? "" : request.getUri().trim();
		if (filePath.equals("/")) {
			filePath = "/index.html";
		}

		try {
			String result = null;
			File file = new File(MyHttpServer.WebContent, filePath);
			byte[] fileByte = new byte[(int) file.length()];

			if (file.exists()) {
				fis = new FileInputStream(file);
				fis.read(fileByte);
				fis.close();

				result = new String(fileByte);
				result = warpMessage("200", result);
				output.write(result.getBytes());

			} else {
				String errorMessage = warpMessage("404", "404 File Not Found!  The requested URL /404/ was not found on this server. ");
				output.write(errorMessage.getBytes());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private String warpMessage(String statusCode, String message) {
		return "HTTP/1.1 " + statusCode + "\r\n" + "Content-Type: text/html\r\n" + "Content-Length: " + message.length()
				+ "\r\n" + "\r\n" + message;
	}
}