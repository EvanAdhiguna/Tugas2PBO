package SubscriptionAPI.presistence;

import SubscriptionAPI.sketch.Customers;
import SubscriptionAPI.sketch.Cards;
import SubscriptionAPI.sketch.SubscriptionItems;
import SubscriptionAPI.sketch.ShippingAddresses;

import java.sql.*;
import java.util.ArrayList;
public class SubscriptionsItemsDO {

    // SELECT SUBSCRIPTION ITEMS BY SUBSCRIPTION ID
    public ArrayList<SubscriptionItems> selectItemsBySubscription(int idSubscription) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<SubscriptionItems> itemList = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            // Establish connection to SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:ecommerce.db");
            System.out.println("Connected to database");
            statement = connection.prepareStatement("SELECT * FROM subscription_items WHERE subscription = ?");
            statement.setInt(1, idSubscription);
            result = statement.executeQuery();

            while(result.next()) {
                SubscriptionItems item = new SubscriptionItems();
                item.setSubscription(result.getInt("subscription"));
                item.setItem(result.getInt("item"));
                item.setQuantity(result.getInt("quantity"));
                item.setPrice(result.getInt("price"));
                item.setAmount(result.getInt("amount"));
                itemList.add(item);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (result != null) result.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return itemList;
    }

    // INSERT NEW SUBSCRIPTION ITEM
    public String addNewSubscriptionItem(SubscriptionItems item) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String response;

        try {
            Class.forName("org.sqlite.JDBC");
            // Establish connection to SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:ecommerce.db");
            System.out.println("Connected to database");
            statement = connection.prepareStatement("INSERT INTO subscription_items (subscription, item, quantity, price, amount) VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, item.getSubscription());
            statement.setInt(2, item.getItem());
            statement.setInt(3, item.getQuantity());
            statement.setInt(4, item.getPrice());
            statement.setInt(5, item.getAmount());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                response = rowsAffected + " row(s) has been affected";
                System.out.println(response);
            } else {
                response = "No rows have been affected";
                System.out.println(response);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return response;
    }

    // UPDATE SUBSCRIPTION ITEM BASED ON SUBSCRIPTION AND ITEM ID
    public String updateSubscriptionItem(SubscriptionItems item, int idSubscription, int idItem) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String response;

        try {
            Class.forName("org.sqlite.JDBC");
            // Establish connection to SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:ecommerce.db");
            System.out.println("Connected to database");
            statement = connection.prepareStatement("UPDATE subscription_items SET quantity = ?, price = ?, amount = ? WHERE subscription = ? AND item = ?");
            statement.setInt(1, item.getQuantity());
            statement.setInt(2, item.getPrice());
            statement.setInt(3, item.getAmount());
            statement.setInt(4, idSubscription);
            statement.setInt(5, idItem);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                response = rowsAffected + " row(s) has been affected";
                System.out.println(response);
            } else {
                response = "No rows have been affected";
                System.out.println(response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return response;
    }

    // DELETE SUBSCRIPTION ITEM
    public String deleteSubscriptionItem(int idSubscription, int idItem) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        String response;

        try {
            Class.forName("org.sqlite.JDBC");
            // Establish connection to SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:ecommerce.db");
            System.out.println("Connected to database");
            statement = connection.prepareStatement("DELETE FROM subscription_items WHERE subscription = ? AND item = ?");
            statement.setInt(1, idSubscription);
            statement.setInt(2, idItem);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                response = rowsAffected + " row(s) have been affected";
                System.out.println(response);
            } else {
                response = "No rows have been affected";
                System.out.println(response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return response;
    }

}
