package com.southwind.server;

import java.io.IOException;
import java.io.InputStream;

public class MyHttpRequest {

    private InputStream input;
    private String uri;
    
    public MyHttpRequest(InputStream input) {
        this.input = input;
    }
    
    public void parse() {
        StringBuffer requestStr = new StringBuffer(2048);
        int i;
        byte[] buffer = new byte[2048];
        
        try {
            i = input.read(buffer);
        } catch (IOException ex) {
            ex.printStackTrace();
            i = -1;
        }
        
        for (int j = 0; j < i; j++) {
            requestStr.append((char) buffer[j]);
        }
        
        System.out.println(requestStr.toString());
        uri = parseUri(requestStr.toString());
    }
    
    private String parseUri(String requestStr) {
        int index1, index2;
        index1 = requestStr.indexOf(' ');
        
        if (index1 != -1) {
            index2 = requestStr.indexOf(' ', index1 + 1);
            if (index2 > index1) {
                return requestStr.substring(index1 + 1, index2);
            }
        }
        
        return null;
    }
    
    public String getUri() {
        return uri;
    }
}