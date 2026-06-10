package com.rh.util;

public class SmsUtil {
    
    /**
     * Envoie un SMS via Africa's Talking (Sandbox ou Production)
     * @param phoneNumber Numéro du destinataire
     * @param message     Message à envoyer
     */
    public static void sendSms(String phoneNumber, String message) {
        System.out.println("╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                      📱 ENVOI SMS (Africa's Talking)               ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Destinataire : " + formatPhoneNumber(phoneNumber));
        System.out.println("║ Message      : " + message);
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");
        
        // Appel réel à l'API Africa's Talking
        boolean success = AfricaSmsUtil.sendSms(phoneNumber, message);
        
        if (success) {
            System.out.println("✅ SMS envoyé avec succès via Africa's Talking");
        } else {
            System.out.println("❌ Échec de l'envoi du SMS via Africa's Talking");
        }
        System.out.println();
    }
    
    private static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return "non renseigné";
        }
        phoneNumber = phoneNumber.trim().replaceAll("\\s+", "");
        if (phoneNumber.startsWith("0")) {
            return "+237" + phoneNumber.substring(1);
        } else if (!phoneNumber.startsWith("+")) {
            return "+237" + phoneNumber;
        }
        return phoneNumber;
    }
}