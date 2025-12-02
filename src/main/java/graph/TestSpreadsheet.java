package graph;

import java.util.Arrays;
import java.util.List;
//generado
public class TestSpreadsheet {

    public static void main(String[] args) {

        // Celdas que vamos a usar
        List<String> keys = Arrays.asList(
                "A1", "A2", "A3",   // bloque base
                "B1",               // origen para linkear A3
                "C1",               // SUM(A1..A3)
                "D1",               // AVG(A1..A3)
                "E1",               // MAX(A1..A3)
                "F1"                // MIN(A1..A3)
        );

        SpreadsheetGraph sheet = new SpreadsheetGraph(keys);

        System.out.println("==== INICIO DE PRUEBA ====\n");

        // 1️⃣ Seteamos valores iniciales en A1 y A2 (A3 queda en "")
        System.out.println(">> Seteando valores iniciales:");
        sheet.setCellContent("A1", "10");
        System.out.println("   A1 = 10");
        sheet.setCellContent("A2", "20");
        System.out.println("   A2 = 20");
        System.out.println("   A3 se mantiene como \"\" (vacía)\n");

        // 2️⃣ Linkeamos la celda faltante del bloque (A3) desde B1
        System.out.println(">> Creando enlace LINKED:");
        System.out.println("   linked: B1 -> A3   (A3 copia el valor de B1)");
        sheet.connectLinked("B1", "A3");

        // 3️⃣ Creamos SUM, AVG, MAX, MIN sobre el bloque A1..A3
        System.out.println("\n>> Creando fórmulas sobre el bloque A1..A3:");
        System.out.println("   sum : C1 = SUM(A1..A3)");
        sheet.connectSum("A1", "A3", "C1");

        System.out.println("   avg : D1 = AVG(A1..A3)");
        sheet.connectAvg("A1", "A3", "D1");

        System.out.println("   max : E1 = MAX(A1..A3)");
        sheet.connectMax("A1", "A3", "E1");

        System.out.println("   min : F1 = MIN(A1..A3)");
        sheet.connectMin("A1", "A3", "F1");

        // 4️⃣ Evaluamos para propagar valores iniciales
        System.out.println("\n>> Evaluando celdas (propagación inicial):");
        System.out.println("   evaluateCell(\"A1\")");
        sheet.evaluateCell("A1");
        System.out.println("   evaluateCell(\"A2\")");
        sheet.evaluateCell("A2");
        System.out.println("   evaluateCell(\"B1\")");
        sheet.evaluateCell("B1");

        System.out.println("\n===== ESTADO INICIAL (antes de darle valor a B1) =====");
        printCell(sheet, "A1");
        printCell(sheet, "A2");
        printCell(sheet, "A3");
        printCell(sheet, "B1");
        printCell(sheet, "C1"); // SUM
        printCell(sheet, "D1"); // AVG
        printCell(sheet, "E1"); // MAX
        printCell(sheet, "F1"); // MIN

        // En este punto esperas algo como:
        // A1 = "10"
        // A2 = "20"
        // A3 = ""        (porque B1 = "")
        // B1 = ""
        // C1 = "30"      (sum solo cuenta 10 y 20)
        // D1 = "15.0"    (avg solo sobre 10 y 20)
        // E1 = "20"
        // F1 = "10"

        // 5️⃣ Ahora recién le das valor a B1
        System.out.println("\n>> Ahora asignamos valor a B1 (afecta A3 y todas las fórmulas):");
        sheet.setCellContent("B1", "50");
        System.out.println("   B1 = 50");

        System.out.println("\n>> Evaluando desde B1 para propagar cambios:");
        System.out.println("   evaluateCell(\"B1\")");
        sheet.evaluateCell("B1");

        System.out.println("\n===== ESTADO FINAL (después de B1 = 50) =====");
        printCell(sheet, "A1");
        printCell(sheet, "A2");
        printCell(sheet, "A3"); // ahora debería ser "50" (linked desde B1)
        printCell(sheet, "B1");
        printCell(sheet, "C1"); // SUM
        printCell(sheet, "D1"); // AVG
        printCell(sheet, "E1"); // MAX
        printCell(sheet, "F1"); // MIN

        System.out.println("\n==== FIN DE PRUEBA ====");
    }

    private static void printCell(SpreadsheetGraph sheet, String key) {
        System.out.println("   " + key + " = \"" + sheet.getCellContent(key) + "\"");
    }
}