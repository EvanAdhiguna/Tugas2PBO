package SubscriptionAPI.presistence;

import SubscriptionAPI.sketch.Cards;

import java.sql.*;
import java.util.ArrayList;

public class CardsDO {
    // SELECT ALL CARDS BASED ON CUSTOMER'S ID
    public ArrayList<Cards> selectCardsByCustomer(int idCustomer) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<Cards> listCards = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            // Establish connection to SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");
            statement = connection.prepareStatement("SELECT * FROM cards WHERE customer = ?");
            statement.setInt(1, idCustomer);
            result = statement.executeQuery();

            while(result.next()) {
                Cards card = new Cards();
                card.setId(result.getInt("id"));
                card.setCustomer(result.getInt("customer"));
                card.setCard_type(result.getString("card_type"));
                card.setMasked_number(result.getString("masked_number"));
                card.setExpiry_month(result.getInt("expiry_month"));
                card.setExpiry_year(result.getInt("expiry_year"));
                card.setStatus(result.getString("status"));
                card.setIs_primary(result.getInt("is_primary"));
                listCards.add(card);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (result != null) result.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return listCards;
    }

    // INSERT NEW CARD
    public String addNewCard(Cards card) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String response;

        try {
            Class.forName("org.sqlite.JDBC");
            // Establish connection to SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");
            statement = connection.prepareStatement("INSERT INTO cards (customer, card_type, masked_number, expiry_month, expiry_year, status, is_primary) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, card.getCustomer());
            statement.setString(2, card.getCard_type().toString());
            statement.setString(3, card.getMasked_number());
            statement.setInt(4, card.getExpiry_month());
            statement.setInt(5, card.getExpiry_year());
            statement.setString(6, card.getStatus().toString());
            statement.setInt(7, card.getIs_primary());

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

    // UPDATE CARD BASED ON ID
    public String updateCard(Cards card, int idCard) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String response;

        try {
            Class.forName("org.sqlite.JDBC");
            // Establish connection to SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");
            statement = connection.prepareStatement("UPDATE cards SET card_type = ?, masked_number = ?, expiry_month = ?, " +
                    "expiry_year = ?, status = ?, is_primary = ? WHERE id = ?");
            statement.setString(1, card.getCard_type().toString());
            statement.setString(2, card.getMasked_number());
            statement.setInt(3, card.getExpiry_month());
            statement.setInt(4, card.getExpiry_year());
            statement.setString(5, card.getStatus().toString());
            statement.setInt(6, card.getIs_primary());
            statement.setInt(7, idCard);

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

    // DELETE CARD
    public String deleteCard(int idCard) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        String response;

        try {
            Class.forName("org.sqlite.JDBC");
            // Establish connection to SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");
            statement = connection.prepareStatement("DELETE FROM cards WHERE id = ?");
            statement.setInt(1, idCard);
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


