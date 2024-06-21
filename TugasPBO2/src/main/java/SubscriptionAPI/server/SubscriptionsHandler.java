package SubscriptionAPI.server;

import SubscriptionAPI.presistence.*;
import SubscriptionAPI.sketch.Customers;
import SubscriptionAPI.sketch.Items;
import SubscriptionAPI.sketch.Subscriptions;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;

public class SubscriptionsHandler {

    CardsDO cardsDO = new CardsDO();
    ItemsDO itemsDO = new ItemsDO();
    CustomersDO customersDO = new CustomersDO();
    ShippingAddressesDO shippingAddressesDO = new ShippingAddressesDO();
    SubscriptionsDO subscriptionsDO = new SubscriptionsDO();
    SubscriptionsItemsDO subscriptionsItemsDO = new SubscriptionsItemsDO();

    public JSONObject getSubscription(String[] path) throws SQLException {
        JSONObject jsonSubscriptions = null;
        if (path.length == 2) {
            jsonSubscriptions = new JSONObject();
            JSONArray jsonSubscriptionArray = new JSONArray();
            ArrayList<Subscriptions> listSubscriptions = subscriptionsDO.selectAllSubscriptions();
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
                jsonSubscriptionArray.put(jsonSubscriptionRecord);
            }
            jsonSubscriptions.put("Subscriptions Record", jsonSubscriptionArray);
        }
        return jsonSubscriptions;
    }


    // POST  SUBSCRIPTION (INSERT in Database)
    public String postingSubscription(JSONObject jsonReqBody) throws SQLException {
        Subscriptions subscriptions = subscriptionsParseJSONData(jsonReqBody);
        return subscriptionsDO.addNewSubs(subscriptions);
    }
    private Subscriptions subscriptionsParseJSONData (JSONObject jsonReqBody) {
        Subscriptions subscriptions = new Subscriptions();
        System.out.println("Getting data from request");
        subscriptions.setId(jsonReqBody.optInt("id"));
        subscriptions.setCustomer(jsonReqBody.optInt("customer"));
        subscriptions.setBilling_period(jsonReqBody.optInt("billing_period"));
        subscriptions.setBilling_period_unit(jsonReqBody.optString("billing_period_unit"));
        subscriptions.setTotal_due(jsonReqBody.optInt("total_due"));
        subscriptions.setActivated_at(jsonReqBody.optString("activated_at"));
        subscriptions.setCurrent_term_start(jsonReqBody.optString("current_term_start"));
        subscriptions.setCurrent_term_end(jsonReqBody.optString("current_term_end"));
        subscriptions.setStatus(jsonReqBody.optString("status"));
        return subscriptions;
    }

}