package SubscriptionAPI.server;
import java.sql.SQLException;
import org.json.JSONObject;
import SubscriptionAPI.sketch.Cards;
import SubscriptionAPI.presistence.CardsDO;
public class CardsHandler {
    CardsDO cardsDO = new CardsDO();
    private Cards parseCardsFromJSON(JSONObject jsonObject) {
        Cards cards = new Cards();
        cards.setId(jsonObject.optInt("id"));
        cards.setCustomer(jsonObject.optInt("customer"));
        cards.setCard_type(jsonObject.optString("card_type"));
        cards.setMasked_number(jsonObject.optString("masked_number"));
        cards.setExpiry_month(jsonObject.optInt("expiry_month"));
        cards.setExpiry_year(jsonObject.optInt("expiry_yeaar"));
        cards.setStatus(jsonObject.optString("status"));
        cards.setIs_primary(jsonObject.optInt("is_primary"));
        return cards;
    }

    public String deleteCardsIfNotPrimary(String[] path) throws SQLException {
        // Parse customer ID and card ID from the path
        int customerId = Integer.parseInt(path[2]); // Assuming /cards/customerId/cardId
        int cardId = Integer.parseInt(path[4]);

        return cardsDO.deleteCardIfNotPrimary(customerId, cardId);
    }
}
