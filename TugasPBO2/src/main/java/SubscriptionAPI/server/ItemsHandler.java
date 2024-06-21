package SubscriptionAPI.server;

import SubscriptionAPI.presistence.*;
import SubscriptionAPI.sketch.Customers;
import SubscriptionAPI.sketch.Items;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemsHandler {
    CardsDO cardsDO = new CardsDO();
    ItemsDO itemsDO = new ItemsDO();
    CustomersDO customersDO = new CustomersDO();
    ShippingAddressesDO shippingAddressesDO = new ShippingAddressesDO();
    SubscriptionsDO subscriptionsDO = new SubscriptionsDO();
    SubscriptionsItemsDO subscriptionsItemsDO = new SubscriptionsItemsDO();

    public JSONObject getItemStatus() throws SQLException{
        JSONObject jsonItem = new JSONObject();
        JSONArray jsonItemArray = new JSONArray();
        ArrayList<Items> listItems = itemsDO.selectItemsByActiveStatus();
        for(Items items : listItems){
            JSONObject jsonItemRecord = new JSONObject();
            jsonItemRecord.put("id", items.getId());
            jsonItemRecord.put("name", items.getName());
            jsonItemRecord.put("price", items.getPrice());
            jsonItemRecord.put("type", items.getType());
            jsonItemRecord.put("is_active", items.getIs_active());
            jsonItemArray.put(jsonItemRecord);
        }
        jsonItem.put("Item Record", jsonItemArray);
        return jsonItem;
    }

    // GET ITEM(Select in Database)
    public JSONObject getItem(String[] path) throws SQLException {
        int idItem = 0;
        JSONObject jsonItem = null;
        if (path.length == 2) {
            jsonItem = new JSONObject();
            JSONArray jsonItemArray = new JSONArray();
            ArrayList<Items> listItems = itemsDO.selectAllItems();
            for (Items items : listItems) {
                JSONObject jsonItemRecord = new JSONObject();
                jsonItemRecord.put("id", items.getId());
                jsonItemRecord.put("name", items.getName());
                jsonItemRecord.put("price", items.getPrice());
                jsonItemRecord.put("type", items.getType());
                jsonItemRecord.put("is_active", items.getIs_active());
                jsonItemArray.put(jsonItemRecord);
            }
            jsonItem.put("Item Record", jsonItemArray);
        } else if (path.length == 3) {
            jsonItem = new JSONObject();
            idItem = Integer.valueOf(path[2]);
            Items items = itemsDO.selectItemById(idItem);
            if (items.getId() != 0) {
                JSONObject jsonItemRecord = new JSONObject();
                jsonItemRecord.put("id", items.getId());
                jsonItemRecord.put("name", items.getName());
                jsonItemRecord.put("price", items.getPrice());
                jsonItemRecord.put("type", items.getType());
                jsonItemRecord.put("is_active", items.getIs_active());
                jsonItem.put("Item Record", jsonItemRecord);
            }
        }
        return jsonItem;
    }
    // POST  ITEMS (INSERT in Database)
    public String postingItem(JSONObject jsonReqBody) throws SQLException {
        Items items = itemsParseJSONData(jsonReqBody);
        return itemsDO.addNewItms(items);
    }

    // PUT ITEMS (UPDATE in database)
    public String puttingItem(JSONObject jsonReqBody, String[] path) throws SQLException {
        Items items = itemsParseJSONData(jsonReqBody);
        int idItem = Integer.valueOf(path[2]);
        return itemsDO.updateItms(items, idItem);
    }
    private Items itemsParseJSONData (JSONObject jsonReqBody) {
        Items items = new Items();
        System.out.println("Getting data from request");
        items.setId(jsonReqBody.optInt("id"));
        items.setName(jsonReqBody.optString("name"));
        items.setPrice(jsonReqBody.optInt("price"));
        items.setType(jsonReqBody.optString("type"));
        items.setIs_active(jsonReqBody.optInt("is_active"));

        return items;
    }
    // DELETE USER
    public String deletingItem(String[] path) throws SQLException, ClassNotFoundException {
        int idItems = Integer.parseInt(path[2]);
        return itemsDO.deleteItms(idItems);
    }
}
