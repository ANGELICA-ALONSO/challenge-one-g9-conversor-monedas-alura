package com.alura.model;

public class Conversor {
    public static void eleccionUserMenu() {
        ExchangeService service = new ExchangeRateApiService();
        HistoryManager history = new HistoryManager();

        System.out.println("Bienvenido al conversor de monedas de Alura!");
        System.out.println("-----------------------------------------------");

        System.out.println("======================================");
        System.out.println("     Conversor de Monedas - Menú      ");
        System.out.println("======================================");
        System.out.println("1)  Dólar (USD) a Peso Argentino (ARS)");
        System.out.println("2)  Peso Argentino (ARS) a Dólar (USD)");
        System.out.println("3)  Dólar (USD) a Real Brasileño (BRL)");
        System.out.println("4)  Real Brasileño (BRL) a Dólar (USD)");
        System.out.println("5)  Dólar (USD) a Peso Colombiano (COP)");
        System.out.println("6)  Peso Colombiano (COP) a Dólar (USD)");
        System.out.println("7)  Peso Chileno (CLP) a Dólar (USD)");
        System.out.println("8)  Dólar (USD) a Peso Chileno (CLP)");
        System.out.println("9)  Peso Chileno (CLP) a Peso Filipino (PHP)");
        System.out.println("10) Peso Filipino (PHP) a Peso Chileno (CLP)");
        System.out.println("11) Peso Filipino (PHP) a Dólar (USD)");
        System.out.println("12) Dólar (USD) a Peso Filipino (PHP)");
        System.out.println("======================================");


        boolean running = true;
        try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {
            while (running) {
                System.out.print("Ingrese codigo de moneda base (ej: ARS): ");
                String base = scanner.nextLine().trim().toUpperCase();
                System.out.print("Ingrese codigo de moneda destino (ej: MXN): ");
                String target = scanner.nextLine().trim().toUpperCase();

                double amount = 0d;
                try {
                    System.out.print("Ingrese monto a convertir: ");
                    String amt = scanner.nextLine().trim();
                    amount = Double.parseDouble(amt);
                } catch (NumberFormatException e) {
                    System.out.println("Monto inválido. Intente de nuevo.");
                    continue;
                }

                // Validación básica: códigos ISO de 3 letras
                if (!base.matches("[A-Z]{3}") || !target.matches("[A-Z]{3}")) {
                    System.out.println("Códigos inválidos. Use códigos de 3 letras (ej: ARS, USD, MXN).");
                    continue;
                }

                double result = 0d;
                try {
                    result = service.convert(base, target, amount);
                    System.out.printf("Resultado: %.4f %s\n", result, target);
                    HistoryEntry entry = new HistoryEntry(base, target, amount, result);
                    history.add(entry);
                } catch (Exception e) {
                    System.out.println("Error al convertir: " + e.getMessage());
                }

                System.out.print("Realizar otra conversión? (s/n): ");
                String cont = scanner.nextLine().trim().toLowerCase();
                if (!cont.equals("s") && !cont.equals("si")) {
                    running = false;
                }
            }
        }

        // Al salir, guardar historial
        String outPath = "conversion_history.json";
        try {
            history.saveToFile(outPath);
            System.out.println("Historial guardado en: " + outPath);
        } catch (java.io.IOException e) {
            System.out.println("No se pudo guardar el historial: " + e.getMessage());
        }
    }
}
