package SubscriptionAPI.presistence;

import SubscriptionAPI.sketch.Customers;
import SubscriptionAPI.sketch.Cards;
import SubscriptionAPI.sketch.Subscriptions;
import SubscriptionAPI.sketch.Items;

import java.sql.*;
import java.util.ArrayList;
public class ItemsDO {
    // SELECT ALL ITEMS
    public ArrayList<Items> selectAllItems() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;
        ArrayList<Items> itemList = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            // Establish connection to SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM items");

            while(result.next()) {
                Items item = new Items();
                item.setId(result.getInt("id"));
                item.setName(result.getString("name"));
                item.setPrice(result.getInt("price"));
                item.setType(result.getString("type"));
                item.setIs_active(result.getInt("is_active"));
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

    // SELECT ITEM BY ID
    public Items selectItemById(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        Items item = null;

        try {
            Class.forName("org.sqlite.JDBC");
            // Establish connection to SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");
            statement = connection.prepareStatement("SELECT * FROM items WHERE id = ?");
            statement.setInt(1, id);
            result = statement.executeQuery();

            if (result.next()) {
                item = new Items();
                item.setId(result.getInt("id"));
                item.setName(result.getString("name"));
                item.setPrice(result.getInt("price"));
                item.setType(result.getString("type"));
                item.setIs_active(result.getInt("is_active"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (result != null) result.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return item;
    }

    // INSERT NEW ITEM
    public String addNewItms(Items item) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String response;

        try {
            Class.forName("org.sqlite.JDBC");
            // Establish connection to SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");
            statement = connection.prepareStatement("INSERT INTO items (id, name, price, type, is_active) VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, item.getId());
            statement.setString(2, item.getName());
            statement.setInt(3, item.getPrice());
            statement.setString(4, item.getType().toString());
            statement.setInt(5, item.getIs_active());

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

    // UPDATE ITEM BY ID
    public String updateItms(Items item, int id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String response;

        try {
            Class.forName("org.sqlite.JDBC");
            // Establish connection to SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");
            statement = connection.prepareStatement("UPDATE items SET name = ?, price = ?, type = ?, is_active = ? WHERE id = ?");
            statement.setString(1, item.getName());
            statement.setInt(2, item.getPrice());
            statement.setString(3, item.getType().toString());
            statement.setInt(4, item.getIs_active());
            statement.setInt(5, id);

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

    public ArrayList<Items> selectItemsByActiveStatus() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<Items> itemList = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            // Establish connection to SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");

            // SQL query based on isActive parameter
            String sql = "SELECT * FROM items WHERE is_active = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, 1); // 1 for true (active), 0 for false (inactive)

            result = statement.executeQuery();

            while(result.next()) {
                Items item = new Items();
                item.setId(result.getInt("id"));
                item.setName(result.getString("name"));
                item.setPrice(result.getInt("price"));
                item.setType(result.getString("type"));
                item.setIs_active(result.getInt("is_active"));
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

    // DELETE ITEM BY ID
    public String deleteItms(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String response;

        try {
            Class.forName("org.sqlite.JDBC");
            // Establish connection to SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");
            statement = connection.prepareStatement("UPDATE items SET is_active = 0 WHERE id = " + id );


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
}
