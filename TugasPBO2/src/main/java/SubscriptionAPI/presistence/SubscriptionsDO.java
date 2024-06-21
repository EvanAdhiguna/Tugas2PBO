package SubscriptionAPI.presistence;

import SubscriptionAPI.sketch.*;

import java.sql.*;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.management.RuntimeErrorException;
public class SubscriptionsDO {


    // SELECT SUBSCRIPTIONS BY CUSTOMER ID
    public JSONObject getCustomerAndSubscriptionsByCustomerId(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement state = null;
        ResultSet result = null;
        JSONObject customerRecord = new JSONObject();
        JSONArray listSubs = new JSONArray();

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("has connected to the database");

            String query = "SELECT s.*, cu.first_name, cu.last_name, cu.email, cu.phone_number " +
                    "FROM subscriptions s " +
                    "JOIN customers cu ON cu.id = s.customer " +
                    "WHERE cu.id = ?";
            state = conn.prepareStatement(query);
            state.setInt(1, id);
            result = state.executeQuery();

            boolean customerDetailsSet = false;
            while (result.next()) {
                if (!customerDetailsSet) {
                    customerRecord.put("id", result.getInt("customer"));
                    customerRecord.put("first_name", result.getString("first_name"));
                    customerRecord.put("last_name", result.getString("last_name"));
                    customerRecord.put("email", result.getString("email"));
                    customerRecord.put("phone_number", result.getString("phone_number"));
                    customerDetailsSet = true;
                }

                JSONObject subsJson = new JSONObject();
                subsJson.put("id", result.getInt("id"));
                subsJson.put("customer", result.getInt("customer"));
                subsJson.put("billing_period", result.getInt("billing_period"));
                subsJson.put("billing_period_unit", result.getString("billing_period_unit"));
                subsJson.put("total_due", result.getInt("total_due"));
                subsJson.put("activated_at", result.getInt("activated_at"));
                subsJson.put("current_term_start", result.getString("current_term_start"));
                subsJson.put("current_term_end", result.getString("current_term_end"));
                subsJson.put("status", result.getString("status"));

                listSubs.put(subsJson);
            }

            customerRecord.put("subscriptions", listSubs);

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (result != null) result.close();
            if (state != null) state.close();
            if (conn != null) conn.close();
        }
        return customerRecord;
    }


    public ArrayList<Subscriptions> selectAllSubscriptions() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<Subscriptions> subscriptionList = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            // Establish connection to SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");
            statement = connection.prepareStatement("SELECT * FROM subscriptions ");
            result = statement.executeQuery();

            while(result.next()) {
                Subscriptions subscription = new Subscriptions();
                subscription.setId(result.getInt("id"));
                subscription.setCustomer(result.getInt("customer"));
                subscription.setBilling_period(result.getInt("billing_period"));
                subscription.setBilling_period_unit(result.getString("billing_period_unit"));
                subscription.setTotal_due(result.getInt("total_due"));
                subscription.setActivated_at(result.getString("activated_at"));
                subscription.setCurrent_term_start(result.getString("current_term_start"));
                subscription.setCurrent_term_end(result.getString("current_term_end"));
                subscription.setStatus(result.getString("status"));
                subscriptionList.add(subscription);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (result != null) result.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return subscriptionList;
    }

    public ArrayList<Subscriptions> selectSubscriptionsByStatus(int idCustomer, String status) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<Subscriptions> subscriptionList = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            // Establish connection to SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:ecommerce.db");
            System.out.println("Connected to database");
            statement = connection.prepareStatement("SELECT * FROM subscriptions WHERE customer = ? AND status = ?");
            statement.setInt(1, idCustomer);
            statement.setString(2, status.toUpperCase());
            result = statement.executeQuery();

            while(result.next()) {
                Subscriptions subscription = new Subscriptions();
                subscription.setId(result.getInt("id"));
                subscription.setCustomer(result.getInt("customer"));
                subscription.setBilling_period(result.getInt("billing_period"));
                subscription.setBilling_period_unit(result.getString("billing_period_unit"));
                subscription.setTotal_due(result.getInt("total_due"));
                subscription.setActivated_at(result.getString("activated_at"));
                subscription.setCurrent_term_start(result.getString("current_term_start"));
                subscription.setCurrent_term_end(result.getString("current_term_end"));
                subscription.setStatus(result.getString("status"));
                subscriptionList.add(subscription);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (result != null) result.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return subscriptionList;
    }

    public ArrayList<Subscriptions> getAllSubscriptionsSortedByTermEnd() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<Subscriptions> listSubscriptions = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");
            statement = connection.prepareStatement("SELECT * FROM subscriptions ORDER BY current_term_end DESC");
            result = statement.executeQuery();

            while (result.next()) {
                Subscriptions subscription = new Subscriptions();
                subscription.setId(result.getInt("id"));

                Customers customer = new Customers();
                customer.setId(result.getInt("customer"));
                subscription.setCustomer(customer.getId());

                subscription.setBilling_period(result.getInt("billing_period"));
                subscription.setBilling_period_unit(result.getString("billing_period_unit"));
                subscription.setTotal_due(result.getInt("total_due"));
                subscription.setActivated_at(result.getString("activated_at"));
                subscription.setCurrent_term_start(result.getString("current_term_start"));
                subscription.setCurrent_term_end(result.getString("current_term_end"));
                listSubscriptions.add(subscription);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeErrorException(new Error(e));
        } finally {
            if (result != null) result.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return listSubscriptions;
    }

    // INSERT NEW SUBSCRIPTION
    public String addNewSubs(Subscriptions subscription) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String response;

        try {
            Class.forName("org.sqlite.JDBC");
            // Establish connection to SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");
            statement = connection.prepareStatement("INSERT INTO subscriptions (customer, billing_period, billing_period_unit, total_due, activated_at, current_term_start, current_term_end, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, subscription.getCustomer());
            statement.setInt(2, subscription.getBilling_period());
            statement.setString(3, subscription.getBilling_period_unit().toString());
            statement.setInt(4, subscription.getTotal_due());
            statement.setString(5, subscription.getActivated_at());
            statement.setString(6, subscription.getCurrent_term_start());
            statement.setString(7, subscription.getCurrent_term_end());
            statement.setString(8, subscription.getStatus().toString());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                response = rowsAffected + " row(s) has been affected";
                System.out.println(response);
            } else {
                response = "No rows have been affected";
                System.out.println(response);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return response;
    }

    // UPDATE SUBSCRIPTION BASED ON ID
    public String updateSubs(Subscriptions subscription, int idSubscription) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String response;

        try {
            Class.forName("org.sqlite.JDBC");
            // Establish connection to SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");
            statement = connection.prepareStatement("UPDATE subscriptions SET billing_period = ?, billing_period_unit = ?, total_due = ?, activated_at = ?, current_term_start = ?, current_term_end = ?, status = ? WHERE id = ?");
            statement.setInt(1, subscription.getBilling_period());
            statement.setString(2, subscription.getBilling_period_unit().toString());
            statement.setInt(3, subscription.getTotal_due());
            statement.setString(4, subscription.getActivated_at());
            statement.setString(5, subscription.getCurrent_term_start());
            statement.setString(6, subscription.getCurrent_term_end());
            statement.setString(7, subscription.getStatus().toString());
            statement.setInt(8, idSubscription);

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

    // DELETE SUBSCRIPTION
    public String addNew(int idSubscription) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        String response;

        try {
            Class.forName("org.sqlite.JDBC");
            // Establish connection to SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");
            statement = connection.prepareStatement("DELETE FROM subscriptions WHERE id = ?");
            statement.setInt(1, idSubscription);
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

    // SELECT SUBSCRIPTION BY ID
    public Subscriptions selectSubscriptionById(int idSubscription) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        Subscriptions subscription = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");

            statement = connection.prepareStatement("SELECT * FROM subscriptions WHERE id = ?");
            statement.setInt(1, idSubscription);
            result = statement.executeQuery();

            if (result.next()) {
                subscription = new Subscriptions();
                subscription.setId(result.getInt("id"));
                subscription.setCustomer(result.getInt("customer"));
                subscription.setBilling_period(result.getInt("billing_period"));
                subscription.setBilling_period_unit(result.getString("billing_period_unit"));
                subscription.setTotal_due(result.getInt("total_due"));
                subscription.setActivated_at(result.getString("activated_at"));
                subscription.setCurrent_term_start(result.getString("current_term_start"));
                subscription.setCurrent_term_end(result.getString("current_term_end"));
                subscription.setStatus(result.getString("status"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (result != null) result.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return subscription;
    }
}


