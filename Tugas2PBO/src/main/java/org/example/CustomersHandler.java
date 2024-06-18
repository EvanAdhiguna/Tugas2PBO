package org.example;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;

public class CustomersHandler {
    private Database database;

    public CustomersHandler(Database database){
        this.database = database;
    }

    public String getUsers(int usersId, String addedQuery){
        JSONArray jsonArray = new JSONArray();
        String querySQL = "";
        String queryAddresses = "SELECT * FROM addresses";
        if (usersId == 0) {
            querySQL = "SELECT * FROM users";
        }else if(addedQuery != null) {
            if(addedQuery.contains("type=buyer")){
                querySQL = "SELECT * FROM users WHERE type='Buyer'";
            }else if(addedQuery.contains("type=seller")){
                querySQL = "SELECT * FROM users WHERE type='Seller'";
            }else{
                String[] query = addedQuery.split("&");
                String queryField = "";
                String queryCondition = "";
                int queryValue = 0;
                for(int i = 0; i < query.length; i++){
                    if(query[i].contains("field")){
                        queryField = query[i].substring(query[i].lastIndexOf("=") + 1);
                    }else if(query[i].contains("val")){
                        queryValue = Integer.parseInt(query[i].substring(query[i].lastIndexOf("=") + 1));
                    }else if(query[i].contains("cond")){
                        String cond = query[i].substring(query[i].lastIndexOf("=") + 1);
                        if(cond.equals("larger")){
                            queryCondition = ">";
                        }else if(cond.equals("largerEqual")){
                            queryCondition = ">=";
                        }else if(cond.equals("smaller")){
                            queryCondition = "<";
                        }else if(cond.equals("smallerEqual")){
                            queryCondition = "<=";
                        }
                    }
                }
                querySQL = "SELECT * FROM users WHERE " + queryField + queryCondition + " " + queryValue + " ";
            }
        }else{
            querySQL = "SELECT * FROM users WHERE id=" + usersId;
        }