package com.rh.util;

public class SmsUtil {
    
    public static void sendSms(String phoneNumber, String message) {
        System.out.println("╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                         📱 SMS SIMULATION                          ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Destinataire : " + formatPhoneNumber(phoneNumber));
        System.out.println("║ Message      : " + message);
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");
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