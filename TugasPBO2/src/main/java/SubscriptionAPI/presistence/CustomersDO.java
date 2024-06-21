package SubscriptionAPI.presistence;


import SubscriptionAPI.sketch.Customers;
import SubscriptionAPI.sketch.Cards;
import SubscriptionAPI.sketch.Subscriptions;

import java.sql.*;
import java.util.ArrayList;

public class CustomersDO {
    // SELECT ALL CUSTOMERS
    public static ArrayList<Customers> selectAll() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<Customers> customers = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");

            statement = connection.prepareStatement("SELECT * FROM customers");
            result = statement.executeQuery();

            while (result.next()) {
                Customers customer = new Customers();
                customer.setId(result.getInt("id"));
                customer.setEmail(result.getString("email"));
                customer.setFirst_name(result.getString("first_name"));
                customer.setLast_name(result.getString("last_name"));
                customer.setPhone_number(result.getString("phone_number"));
                customers.add(customer);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (result != null) result.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return customers;
    }

    // SELECT CUSTOMER BY ID
    public Customers selectCustomerById(int idCustomer) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        Customers customer = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");

            statement = connection.prepareStatement("SELECT * FROM customers WHERE id = ?");
            statement.setInt(1, idCustomer);
            result = statement.executeQuery();

            if (result.next()) {
                customer = new Customers();
                customer.setId(result.getInt("id"));
                customer.setEmail(result.getString("email"));
                customer.setFirst_name(result.getString("first_name"));
                customer.setLast_name(result.getString("last_name"));
                customer.setPhone_number(result.getString("phone_number"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (result != null) result.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return customer;
    }

    // SELECT CARDS OF A CUSTOMER
    public ArrayList<Cards> selectCardsByCustomer(int idCustomer) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<Cards> cards = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");

            statement = connection.prepareStatement("SELECT * FROM cards WHERE customer = ?");
            statement.setInt(1, idCustomer);
            result = statement.executeQuery();

            while (result.next()) {
                Cards card = new Cards();
                card.setId(result.getInt("id"));
                card.setCustomer(result.getInt("customer"));
                card.setCard_type(result.getString("card_type"));
                card.setMasked_number(result.getString("masked_number"));
                card.setExpiry_month(result.getInt("expiry_month"));
                card.setExpiry_year(result.getInt("expiry_year"));
                card.setStatus(result.getString("status"));
                card.setIs_primary(result.getInt("is_primary"));
                cards.add(card);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (result != null) result.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return cards;
    }

    // SELECT SUBSCRIPTIONS OF A CUSTOMER
    public ArrayList<Subscriptions> selectSubscriptionsByCustomer(int idCustomer) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<Subscriptions> subscriptions = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");

            statement = connection.prepareStatement("SELECT * FROM subscriptions WHERE customer = ?");
            statement.setInt(1, idCustomer);
            result = statement.executeQuery();

            while (result.next()) {
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
                subscriptions.add(subscription);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (result != null) result.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return subscriptions;
    }

    // SELECT SUBSCRIPTIONS OF A CUSTOMER BY STATUS
    public ArrayList<Subscriptions> selectSubscriptionsByStatus(int idCustomer, String status) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<Subscriptions> subscriptions = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");

            statement = connection.prepareStatement("SELECT * FROM subscriptions WHERE customer = ? AND status = ?");
            statement.setInt(1, idCustomer);
            statement.setString(2, status);
            result = statement.executeQuery();

            while (result.next()) {
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
                subscriptions.add(subscription);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (result != null) result.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return subscriptions;
    }

    // INSERT NEW CUSTOMER
    public String addNewCust(Customers customer) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String response;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");

            statement = connection.prepareStatement("INSERT INTO customers (email, first_name, last_name, phone_number) VALUES (?, ?, ?, ?)");
            statement.setString(1, customer.getEmail());
            statement.setString(2, customer.getFirst_name());
            statement.setString(3, customer.getLast_name());
            statement.setString(4, customer.getPhone_number());

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

    // UPDATE CUSTOMER BY ID
    public String updateCust(Customers customer, int idCustomer) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String response;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");

            statement = connection.prepareStatement("UPDATE customers SET email = ?, first_name = ?, last_name = ?, phone_number = ? WHERE id = ?");
            statement.setString(1, customer.getEmail());
            statement.setString(2, customer.getFirst_name());
            statement.setString(3, customer.getLast_name());
            statement.setString(4, customer.getPhone_number());
            statement.setInt(5, customer.getId());

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

    // DELETE CUSTOMER BY ID
    public String deleteCust(int idCustomer) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String response;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");

            // Delete related cards first (assuming there is a foreign key constraint with CASCADE delete)
            statement = connection.prepareStatement("DELETE FROM cards WHERE customer = ?");
            statement.setInt(1, idCustomer);
            statement.executeUpdate();

            // Delete related subscriptions first (assuming there is a foreign key constraint with CASCADE delete)
            statement = connection.prepareStatement("DELETE FROM subscriptions WHERE customer = ?");
            statement.setInt(1, idCustomer);
            statement.executeUpdate();

            // Now delete the customer record
            statement = connection.prepareStatement("DELETE FROM customers WHERE id = ?");
            statement.setInt(1, idCustomer);

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
