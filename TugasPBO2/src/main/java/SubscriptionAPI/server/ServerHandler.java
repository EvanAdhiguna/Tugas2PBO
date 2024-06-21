package SubscriptionAPI.server;

import SubscriptionAPI.presistence.ItemsDO;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.sql.SQLException;
public class ServerHandler implements HttpHandler {
    ResponseHandler responseHandler = new ResponseHandler();
    CardsHandler cardsHandler = new CardsHandler();
    CustomersHandler customersHandler = new CustomersHandler();
    ItemsHandler itemsHandler = new ItemsHandler();
    ShippingAddressesHandler shippingAddressesHandler = new ShippingAddressesHandler();
    SubscriptionsHandler subscriptionsHandler = new SubscriptionsHandler();
    SubscriptionsItemsHandler subscriptionsItemsHandler = new SubscriptionsItemsHandler();
    String response = null;
    ItemsDO itemsDO = new ItemsDO();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String[] path = exchange.getRequestURI().getPath().split("/");
        String query = exchange.getRequestURI().getQuery();

        if ("GET".equals(exchange.getRequestMethod())) {
            if (path.length <= 1) {
                responseHandler.sendResponse(exchange, 200, "Hello there!");
            }
            if ("customers".equals(path[1])) {
                JSONObject jsonCustomer = null;

                try {
                    jsonCustomer = customersHandler.getCustomer(path);
                    if (jsonCustomer != null)
                        responseHandler.getResponse(exchange, jsonCustomer.toString(), path, "customers", 200);
                    else {
                        responseHandler.sendResponse(exchange, 404, "Not Found");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if ("subscriptions".equals(path[1])) {
                JSONObject jsonSubscription = null;
                try {
                    jsonSubscription = subscriptionsHandler.getSubscription(path);
                    if (jsonSubscription != null)
                        responseHandler.getResponse(exchange, jsonSubscription.toString(), path, "subscriptions", 200);
                    else {
                        responseHandler.sendResponse(exchange, 404, "Not Found");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if ("items".equals(path[1])) {
                JSONObject jsonItem = null;
                try {
                    jsonItem = itemsHandler.getItem(path);
                    if (jsonItem != null)
                        responseHandler.getResponse(exchange, jsonItem.toString(), path, "items", 200);
                    else {
                        responseHandler.sendResponse(exchange, 404, "Not Found");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException("Error retrieving item: " + e.getMessage(), e);
                }
            } else if ("items?is_active=true".equals(path[1])) {
                JSONObject jsonItem = null;
                try {
                    jsonItem = itemsHandler.getItemStatus();
                    if (jsonItem != null)
                        responseHandler.getResponse(exchange, jsonItem.toString(), path, "items", 200);
                    else {
                        responseHandler.sendResponse(exchange, 404, "Not Found");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }  else {
            response = "404 ENTITY NOT FOUND";
            responseHandler.sendResponse(exchange, 400, response);
        }
        }else if ("POST".equals(exchange.getRequestMethod())) {
            // POST
             if ("customers".equals(path[1])) {
                JSONObject jsonReqBody = parseRequestBody(exchange.getRequestBody());
                try {
                    response = customersHandler.postingCustomer(jsonReqBody);
                    responseHandler.sendResponse(exchange, 200, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            } else if ("items".equals(path[1])) {
                JSONObject jsonReqBody = parseRequestBody(exchange.getRequestBody());
                try {
                    response = itemsHandler.postingItem(jsonReqBody);
                    responseHandler.sendResponse(exchange, 200, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

             } else if ("subscriptions".equals(path[1])) {
                 JSONObject jsonReqBody = parseRequestBody(exchange.getRequestBody());
                 try {
                     response = subscriptionsHandler.postingSubscription(jsonReqBody);
                     responseHandler.sendResponse(exchange, 200, response);
                 } catch (SQLException e) {
                     throw new RuntimeException(e);
                 }

            }else {
                response = "404 ENTITY NOT FOUND";
                responseHandler.sendResponse(exchange, 400, response);
            }

        } else if ("PUT".equals(exchange.getRequestMethod())) {
            if ("customers".equals(path[1])) {
                JSONObject jsonReqBody = parseRequestBody(exchange.getRequestBody());
                try {
                    response = customersHandler.puttingCustomer(jsonReqBody, path);
                    responseHandler.sendResponse(exchange, 200, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            } else if ("items".equals(path[1])) {
                    JSONObject jsonReqBody = parseRequestBody(exchange.getRequestBody());
                    try {
                        response = itemsHandler.puttingItem(jsonReqBody, path);
                        responseHandler.sendResponse(exchange, 200, response);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

            } else {
                response = "404 ENTITY NOT FOUND";
                responseHandler.sendResponse(exchange, 400, response);
            }
        } else if ("DELETE".equals(exchange.getRequestMethod())) {
            // DELETE
            if ("customers".equals(path[1])) {
                try {
                    response = customersHandler.deletingCustomer(path);
                    responseHandler.sendResponse(exchange, 200, response);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }else if ("items".equals(path[1])) {
                    try {
                        response = itemsHandler.deletingItem(path);
                        responseHandler.sendResponse(exchange, 200, response);
                    } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
            } else if("cards".equals(path[3])){
                try {
                    response = cardsHandler.deleteCardsIfNotPrimary(path);
                    responseHandler.sendResponse(exchange, 200, response);
                } catch (JSONException e) {
                    responseHandler.sendResponse(exchange, 400, "Invalid Json Format");
                } catch (SQLException e){
                    throw new RuntimeException(e);
                }
            } else {
                response = "404 ENTITY NOT FOUND";
                responseHandler.sendResponse(exchange, 400, response);
            }
        } else { //untuk request method yang tidak disupport
            handleUnsupportedMethod(exchange);
        }
    }

    private void handleUnsupportedMethod(HttpExchange exchange) throws IOException {
        response = "404 ERROR NOT FOUND";
        responseHandler.sendResponse(exchange, 405, response);
    }
        private JSONObject parseRequestBody (InputStream requestBody) throws IOException {
            byte[] requestBodyBytes = requestBody.readAllBytes();
            String requestBodyString = new String(requestBodyBytes);
            return new JSONObject(requestBodyString);
        }
    }



