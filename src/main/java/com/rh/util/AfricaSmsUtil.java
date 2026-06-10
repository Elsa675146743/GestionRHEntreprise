package com.rh.util;

import com.africastalking.AfricasTalking;
import com.africastalking.SmsService;
import com.africastalking.sms.Recipient;

import java.util.List;

public class AfricaSmsUtil {

    // ========== CONFIGURATION ==========
    // Pour le test (Sandbox)
    private static final String USERNAME = "sandbox";
    // Remplace par ta vraie clé API (Settings → API Key)
    private static final String API_KEY = "atsk_ecb42b8254dd28bd4a30593e6600c8ce74105cebc509c92d3c0cd4f8139f927f68a0d647";
    // Pour le mode production, mets ton vrai nom d'utilisateur
    // private static final String USERNAME = "ton_nom_utilisateur";
    // ===================================

    private static SmsService smsService = null;

    // Initialisation du SDK
    static {
        try {
            AfricasTalking.initialize(USERNAME, API_KEY);
            smsService = AfricasTalking.getService(AfricasTalking.SERVICE_SMS);
            System.out.println("✅ Africa's Talking initialisé avec succès !");
        } catch (Exception e) {
            System.err.println("❌ Erreur d'initialisation Africa's Talking : " + e.getMessage());
        }
    }

    /**
     * Envoie un SMS à un destinataire
     * @param phoneNumber Numéro du destinataire (ex: 691234567 ou +237691234567)
     * @param message     Message à envoyer
     * @return true si l'envoi a réussi, false sinon
     */
    public static boolean sendSms(String phoneNumber, String message) {
        if (smsService == null) {
            System.err.println("❌ SMS non envoyé : service non initialisé");
            return false;
        }

        if (phoneNumber == null || phoneNumber.isEmpty()) {
            System.err.println("❌ SMS non envoyé : numéro manquant");
            return false;
        }

        try {
            // Formater le numéro au format international (+237XXXXXXXX)
            String formattedNumber = formatPhoneNumber(phoneNumber);
            
            System.out.println("📤 Envoi SMS via Africa's Talking...");
            System.out.println("   Destinataire : " + formattedNumber);
            System.out.println("   Message : " + message);

            // Envoi du SMS
            List<Recipient> response = smsService.send(message, new String[]{formattedNumber}, true);
            
            if (response != null && !response.isEmpty()) {
                Recipient recipient = response.get(0);
                if ("Success".equals(recipient.status)) {
                    System.out.println("✅ SMS envoyé avec succès ! ID: " + recipient.messageId);
                    return true;
                } else {
                    System.err.println("❌ Échec envoi SMS : " + recipient.status);
                    return false;
                }
            }
            return false;

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'envoi du SMS : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Formate le numéro de téléphone au format international (+237XXXXXXXX)
     */
    private static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) return "";
        
        // Supprimer les espaces et tirets
        String cleaned = phoneNumber.trim().replaceAll("\\s+", "").replaceAll("-", "");
        
        // Déjà au format international ?
        if (cleaned.startsWith("+237")) {
            return cleaned;
        }
        // Commence par 237 sans le +
        if (cleaned.startsWith("237")) {
            return "+" + cleaned;
        }
        // Commence par 0 (ex: 0691234567)
        if (cleaned.startsWith("0")) {
            return "+237" + cleaned.substring(1);
        }
        // Sinon, on suppose que c'est un numéro local camerounais
        return "+237" + cleaned;
    }
}