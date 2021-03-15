package by.bsuir.aiprp.endpoint;

import by.bsuir.aiprp.ws.WebServiceImpl;

import javax.xml.ws.Endpoint;

public class WebServicePublisher {

    private final static String ADDRESS = "http://localhost:1986/wss/hello";

    public static void main(String[] args) {
        Endpoint.publish(ADDRESS, new WebServiceImpl());
    }
}
