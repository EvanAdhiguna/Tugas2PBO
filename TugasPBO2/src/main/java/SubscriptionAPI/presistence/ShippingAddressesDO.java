package SubscriptionAPI.presistence;

import SubscriptionAPI.sketch.Customers;
import SubscriptionAPI.sketch.Cards;
import SubscriptionAPI.sketch.Subscriptions;
import SubscriptionAPI.sketch.ShippingAddresses;

import java.sql.*;
import java.util.ArrayList;
public class ShippingAddressesDO {

    // SELECT SHIPPING ADDRESSES BY CUSTOMER ID
    public ArrayList<ShippingAddresses> selectAddressesByCustomer(int idCustomer) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<ShippingAddresses> addressList = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            // Establish connection to SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:ecommerce.db");
            System.out.println("Connected to database");
            statement = connection.prepareStatement("SELECT * FROM shipping_addresses WHERE customer = ?");
            statement.setInt(1, idCustomer);
            result = statement.executeQuery();

            while(result.next()) {
                ShippingAddresses address = new ShippingAddresses();
                address.setId(result.getInt("id"));
                address.setCustomer(result.getInt("customer"));
                address.setTitle(result.getString("title"));
                address.setLine1(result.getString("line1"));
                address.setLine2(result.getString("line2"));
                address.setCity(result.getString("city"));
                address.setProvince(result.getString("province"));
                address.setPostcode(result.getString("postcode"));
                addressList.add(address);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (result != null) result.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return addressList;
    }

    // INSERT NEW SHIPPING ADDRESS
    public String addNewAddress(ShippingAddresses address) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String response;

        try {
            Class.forName("org.sqlite.JDBC");
            // Establish connection to SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:ecommerce.db");
            System.out.println("Connected to database");
            statement = connection.prepareStatement("INSERT INTO shipping_addresses (customer, title, line1, line2, city, province, postcode) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, address.getCustomer());
            statement.setString(2, address.getTitle());
            statement.setString(3, address.getLine1());
            statement.setString(4, address.getLine2());
            statement.setString(5, address.getCity());
            statement.setString(6, address.getProvince());
            statement.setString(7, address.getPostcode());

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

    // UPDATE SHIPPING ADDRESS BASED ON ID
    public String updateAddress(ShippingAddresses address, int idAddress) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String response;

        try {
            Class.forName("org.sqlite.JDBC");
            // Establish connection to SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:ecommerce.db");
            System.out.println("Connected to database");
            statement = connection.prepareStatement("UPDATE shipping_addresses SET title = ?, line1 = ?, line2 = ?, " +
                    "city = ?, province = ?, postcode = ? WHERE id = ?");
            statement.setString(1, address.getTitle());
            statement.setString(2, address.getLine1());
            statement.setString(3, address.getLine2());
            statement.setString(4, address.getCity());
            statement.setString(5, address.getProvince());
            statement.setString(6, address.getPostcode());
            statement.setInt(7, idAddress);

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

    // DELETE SHIPPING ADDRESS
    public String deleteAddress(int idAddress) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        String response;

        try {
            Class.forName("org.sqlite.JDBC");
            // Establish connection to SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:ecommerce.db");
            System.out.println("Connected to database");
            statement = connection.prepareStatement("DELETE FROM shipping_addresses WHERE id = ?");
            statement.setInt(1, idAddress);
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
