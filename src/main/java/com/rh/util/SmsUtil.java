package com.rh.util;

public class SmsUtil {
    
    public static void sendSms(String phoneNumber, String message) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            System.out.println("SMS non envoyé : numéro de téléphone manquant");
            return;
        }
        
        System.out.println("=== ENVOI SMS ===");
        System.out.println("Numéro : " + phoneNumber);
        System.out.println("Message : " + message);
        System.out.println("================");
        
        // Pour une vraie intégration, décommentez et adaptez ce code
        /*
        try {
            String encodedMessage = URLEncoder.encode(message, "UTF-8");
            String urlString = API_URL + "?api_key=" + API_KEY + "&to=" + phoneNumber + "&message=" + encodedMessage;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                System.out.println("SMS envoyé avec succès");
            } else {
                System.out.println("Erreur lors de l'envoi SMS");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }
}