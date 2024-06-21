package SubscriptionAPI.server;

import SubscriptionAPI.presistence.CardsDO;
import SubscriptionAPI.presistence.CustomersDO;
import SubscriptionAPI.presistence.ItemsDO;
import SubscriptionAPI.presistence.ShippingAddressesDO;
import SubscriptionAPI.presistence.SubscriptionsDO;
import SubscriptionAPI.presistence.SubscriptionsItemsDO;

import SubscriptionAPI.sketch.Cards;
import SubscriptionAPI.sketch.Customers;
import SubscriptionAPI.sketch.Subscriptions;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomersHandler {
    CardsDO cardsDO = new CardsDO();
    ItemsDO itemsDO = new ItemsDO();
    CustomersDO customersDO = new CustomersDO();
    ShippingAddressesDO shippingAddressesDO = new ShippingAddressesDO();
    SubscriptionsDO subscriptionsDO = new SubscriptionsDO();
    SubscriptionsItemsDO subscriptionsItemsDO = new SubscriptionsItemsDO();


    // GET CUSTOMER (Select in Database)
    public JSONObject getCustomer(String[] path) throws SQLException {
        int idCustomer = 0;
        JSONObject jsonCustomer = null;
        if (path.length == 2) {
            jsonCustomer = new JSONObject();
            JSONArray jsonCustomerArray = new JSONArray();
            ArrayList<Customers> listCustomers = CustomersDO.selectAll();
            for (Customers customer : listCustomers) {
                JSONObject jsonCustomerRecord = new JSONObject();
                jsonCustomerRecord.put("id", customer.getId());
                jsonCustomerRecord.put("first_name", customer.getFirst_name());
                jsonCustomerRecord.put("last_name", customer.getLast_name());
                jsonCustomerRecord.put("email", customer.getEmail());
                jsonCustomerRecord.put("phone_number", customer.getPhone_number());
                jsonCustomerArray.put(jsonCustomerRecord);
            }
            jsonCustomer.put("Customer Record", jsonCustomerArray);
        } else if (path.length == 3) {
            jsonCustomer = new JSONObject();
            idCustomer = Integer.valueOf(path[2]);
            Customers customer = customersDO.selectCustomerById(idCustomer);

            if (customer.getId() != 0) {
                JSONObject jsonCustomerRecord = new JSONObject();
                jsonCustomerRecord.put("id", customer.getId());
                jsonCustomerRecord.put("first_name", customer.getFirst_name());
                jsonCustomerRecord.put("last_name", customer.getLast_name());
                jsonCustomerRecord.put("email", customer.getEmail());
                jsonCustomerRecord.put("phone_number", customer.getPhone_number());
                jsonCustomer.put("Customer Record", jsonCustomerRecord);
            }
        } else if (path.length == 4) {
            if ("subscriptions".equals(path[3])) {
                jsonCustomer = new JSONObject();
                JSONObject jsonCards = new JSONObject();
                idCustomer = Integer.valueOf(path[2]);
                JSONArray jsonCardsArray = new JSONArray();

                jsonCustomer.put("Customer's subscriptions Record", getCustSubsRecord(idCustomer));

            } else if ("cards".equals(path[3])) {
                jsonCustomer = new JSONObject();
                JSONObject jsonCards = new JSONObject();
                idCustomer = Integer.valueOf(path[2]);
                JSONArray jsonCardsArray = new JSONArray();

                jsonCustomer.put("Customer's subscriptions Record", getCustCardsRecord(idCustomer));

            }
        }
        return jsonCustomer;
    }

    public JSONObject getCustSubsRecord(int customerId) throws SQLException{
        JSONObject customerAndSubscriptions = subscriptionsDO.getCustomerAndSubscriptionsByCustomerId(customerId);
        JSONObject formattedJson = new JSONObject();

        JSONArray customerSubsArray = new JSONArray();
        customerSubsArray.put(customerAndSubscriptions);

        formattedJson.put("subscriptions", customerSubsArray);
        return formattedJson;
    }

    public JSONObject getCustCardsRecord(int customerId) throws SQLException {
        JSONObject customerAndCards = cardsDO.getCustCardsByCustId(customerId);
        JSONObject formattedJson = new JSONObject();

        JSONArray customerCardArray = new JSONArray();
        customerCardArray.put(customerAndCards);

        formattedJson.put("Customer's Card Record", customerCardArray);
        return formattedJson;
    }

    private Customers customersParseJSONData(JSONObject jsonReqbody) throws SQLException {
        Customers customers = new Customers();
        customers.setId(jsonReqbody.optInt("id"));
        customers.setEmail(jsonReqbody.optString("email"));
        customers.setFirst_name(jsonReqbody.optString("first_name"));
        customers.setLast_name(jsonReqbody.optString("last_name"));
        customers.setPhone_number(jsonReqbody.optString("phone_number"));
        return customers;
    }

    // POST CUSTOMER (INSERT in Database)
    public String postingCustomer(JSONObject jsonReqBody) throws SQLException {
        Customers customers = customerParseJSONData(jsonReqBody);
        return customersDO.addNewCust(customers);
    }

    // PUT CUSTOMER (UPDATE in database)
    public String puttingCustomer (JSONObject jsonReqBody, String[] path) throws SQLException {
        Customers customers = customerParseJSONData(jsonReqBody);
        int idCustomer = Integer.valueOf(path[2]);

        return customersDO.updateCust(customers, idCustomer);
    }
    private Customers customerParseJSONData (JSONObject jsonReqBody) {
        Customers customers = new Customers();
        System.out.println("Getting data from request");
        customers.setId(jsonReqBody.optInt("id"));
        customers.setFirst_name(jsonReqBody.optString("first_name"));
        customers.setLast_name(jsonReqBody.optString("last_name"));
        customers.setEmail(jsonReqBody.optString("email"));
        customers.setPhone_number(jsonReqBody.optString("phone_number"));

        return customers;
    }
    // DELETE USER
    public String deletingCustomer(String[] path) throws SQLException, ClassNotFoundException {
        int idCustomer = Integer.valueOf(path[2]);
        return customersDO.deleteCust(idCustomer);
    }
}
