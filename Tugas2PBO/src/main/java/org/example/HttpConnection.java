package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class HttpConnection {
    private CardsHandler cardsHandler;
    private CustomersHandler customersHandler;
    private ItemsHandler itemsHandler;
    private ShippingAddressesHandler shippingAddressesHandler;
    private SubscriptionsHandler subscriptionsHandler;
    private SubscriptionsItemsHandler subscriptionsItemsHandler;

    public HttpConnection(){
        Database database = new Database();
        cardsHandler = new CardsHandler(database);
        customersHandler = new CustomersHandler(database);
        itemsHandler = new ItemsHandler(database);
        shippingAddressesHandler = new ShippingAddressesHandler(database);
        subscriptionsHandler= new SubscriptionsHandler(database);
        subscriptionsItemsHandler = new SubscriptionsItemsHandler(database);
    }

    public void startServer() throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress("localhost", 8060), 0);
        httpServer.createContext("/", new Handler());
        httpServer.setExecutor(Executors.newSingleThreadExecutor());
        httpServer.start();
    }

    private class Handler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            String[] path = exchange.getRequestURI().getPath().split("/");
            String query = exchange.getRequestURI().getQuery();
            String response = "";
            if(method.equals("GET")){
                if(path[1].equals("customers")){
                    response = customersHandler.getCustomersMethod(path, query);
                }else if(path[1].equals("items")){
                    response = itemsHandler.getItemsMethod(path, query);
                }else if(path[1].equals("subscriptions")){
                    response = subscriptionsHandler.getSubscriptions(path[2]);
                }
            }else if(method.equals("DELETE")){
                if(path[1].equals("customers")){
                    response = customersHandler.deleteMethod(Integer.parseInt(path[2]));
                }else if(path[1].equals("items")){
                    response = itemsHandler.deleteMethod(path[2]);
                }else if(path[1].equals("subscriptions")){
                    response = subscriptionsHandler.deleteMethod(Integer.parseInt(path[2]));
                }
            }else if(method.equals("POST")){
                if(path[1].equals("customers")){
                    JSONObject requestBodyJson = parseRequestBody(exchange.getRequestBody());
                    response = customersHandler.postMethod(requestBodyJson);
                }else if(path[1].equals("items")){
                    JSONObject requestBodyJson = parseRequestBody(exchange.getRequestBody());
                    response = itemsHandler.postMethod(requestBodyJson);
                }else if(path[1].equals("subscriptions")){
                    JSONObject requestBodyJson = parseRequestBody(exchange.getRequestBody());
                    response = subscriptionsHandler.postMethod(requestBodyJson);
                }else if(path[1].equals("subscriptions_items")){
                    JSONObject requestBodyJson = parseRequestBody(exchange.getRequestBody());
                    response = subscriptionsItemsHandler.postMethod(requestBodyJson);
                }else if(path[1].equals("cards")){
                    JSONObject requestBodyJson = parseRequestBody(exchange.getRequestBody());
                    response = cardsHandler.postMethod(requestBodyJson);
                }else if(path[1].equals("shipping_addresses")){
                    JSONObject requestBodyJson = parseRequestBody(exchange.getRequestBody());
                    response = shippingAddressesHandler.postMethod(requestBodyJson);
                }


            }else if(method.equals("PUT")){
                if(path[1].equals("customers")){
                    JSONObject requestBodyJson = parseRequestBody(exchange.getRequestBody());
                    response = customersHandler.putMethod(path[2], requestBodyJson);
                }else if(path[1].equals("items")){
                    JSONObject requestBodyJson = parseRequestBody(exchange.getRequestBody());
                    response = itemsHandler.putMethod(path[2],requestBodyJson);
                }else if(path[1].equals("subscriptions")){
                    JSONObject requestBodyJson = parseRequestBody(exchange.getRequestBody());
                    response = subscriptionsHandler.putMethod(path[2],requestBodyJson);
                }else if(path[1].equals("subscriptions_items")){
                    JSONObject requestBodyJson = parseRequestBody(exchange.getRequestBody());
                    response = subscriptionsItemsHandler.putMethod(path[2], requestBodyJson);
                }else if(path[1].equals("cards")){
                    JSONObject requestBodyJson = parseRequestBody(exchange.getRequestBody());
                    response = cardsHandler.putMethod(path[2],requestBodyJson);
                }else if(path[1].equals("shipping_addresses")){
                    JSONObject requestBodyJson = parseRequestBody(exchange.getRequestBody());
                    response = shippingAddressesHandler.putMethod(path[2],requestBodyJson);
                }
            }
            OutputStream outputStream = exchange.getResponseBody();
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length());
            outputStream.write(response.getBytes());
            outputStream.flush();
            outputStream.close();
        }

        private JSONObject parseRequestBody(InputStream requestBody) throws IOException {
            byte[] requestBodyBytes = requestBody.readAllBytes();
            String requestBodyString = new String(requestBodyBytes);
            return new JSONObject(requestBodyString);
        }
    }
}


