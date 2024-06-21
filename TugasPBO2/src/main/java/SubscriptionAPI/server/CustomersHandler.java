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

            if ("cards".equals(path[3])) {
                jsonCustomer = new JSONObject();
                JSONObject jsonCards = new JSONObject();
                idCustomer = Integer.valueOf(path[2]);
                JSONArray jsonCardsArray = new JSONArray();
                ArrayList<Cards> listCards = cardsDO.selectCardsByCustomer(idCustomer);
                for (Cards cards : listCards) {
                    JSONObject jsonCardsRecord = new JSONObject();
                    jsonCardsRecord.put("id", cards.getId());
                    jsonCardsRecord.put("customer", cards.getCustomer());
                    jsonCardsRecord.put("card_type", cards.getCard_type());
                    jsonCardsRecord.put("masked_number", cards.getMasked_number());
                    jsonCardsRecord.put("expiry_month", cards.getExpiry_month());
                    jsonCardsRecord.put("expiry_year", cards.getExpiry_year());
                    jsonCardsRecord.put("status", cards.getStatus());
                    jsonCardsRecord.put("is_primary", cards.getIs_primary());
                    jsonCardsArray.put(jsonCardsRecord);
                }
                jsonCards.put("Card Record", jsonCardsArray);
                jsonCustomer.put("Customer's Card Record", jsonCards);

            } else if ("subscriptions".equals(path[3])) {
                jsonCustomer = new JSONObject();
                JSONObject jsonSubscriptions = new JSONObject();
                idCustomer = Integer.valueOf(path[2]);
                JSONArray jsonSubscriptionsArray = new JSONArray();
                ArrayList<Subscriptions> listSubscriptions = customersDO.selectSubscriptionsByCustomer(idCustomer);
                for (Subscriptions subscriptions : listSubscriptions) {
                    JSONObject jsonSubscriptionRecord = new JSONObject();
                    jsonSubscriptionRecord.put("id", subscriptions.getId());
                    jsonSubscriptionRecord.put("customer", subscriptions.getCustomer());
                    jsonSubscriptionRecord.put("billing_period", subscriptions.getBilling_period());
                    jsonSubscriptionRecord.put("billing_period_unit", subscriptions.getBilling_period_unit());
                    jsonSubscriptionRecord.put("total_due", subscriptions.getTotal_due());
                    jsonSubscriptionRecord.put("activated_at", subscriptions.getActivated_at());
                    jsonSubscriptionRecord.put("current_term_start", subscriptions.getCurrent_term_start());
                    jsonSubscriptionRecord.put("current_term_end", subscriptions.getCurrent_term_end());
                    jsonSubscriptionRecord.put("status", subscriptions.getStatus());
                    jsonSubscriptionsArray.put(jsonSubscriptionRecord);
                }
                jsonSubscriptions.put("Subscriptions Record", jsonSubscriptionsArray);
                jsonCustomer.put("Customer's Subscriptions Record", jsonSubscriptions);
            }
        }
        return jsonCustomer;
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