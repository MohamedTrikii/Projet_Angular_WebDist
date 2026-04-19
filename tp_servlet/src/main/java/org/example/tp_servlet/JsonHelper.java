package org.example.tp_servlet;

import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Utilitaire pour lire et parser le JSON envoyé par le frontend Angular.
 * Parse des objets JSON plats (pas de tableaux/objets imbriqués).
 */
public class JsonHelper {

    /** Lire le corps de la requête HTTP en tant que String */
    public static String readBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString().trim();
    }

    /** Extraire la valeur d'un champ dans un JSON plat. Retourne null si absent. */
    public static String getField(String json, String key) {
        if (json == null || key == null) return null;
        String search = "\"" + key + "\"";
        int keyIdx = json.indexOf(search);
        if (keyIdx == -1) return null;

        int colonIdx = json.indexOf(':', keyIdx + search.length());
        if (colonIdx == -1) return null;

        int i = colonIdx + 1;
        while (i < json.length() && json.charAt(i) == ' ') i++;
        if (i >= json.length()) return null;

        char c = json.charAt(i);
        if (c == '"') {
            // Valeur String
            i++;
            StringBuilder value = new StringBuilder();
            while (i < json.length() && json.charAt(i) != '"') {
                if (json.charAt(i) == '\\' && i + 1 < json.length()) {
                    i++;
                    value.append(json.charAt(i));
                } else {
                    value.append(json.charAt(i));
                }
                i++;
            }
            return value.toString();
        } else if (c == 'n') {
            return null; // null
        } else {
            // Nombre ou booléen
            int start = i;
            while (i < json.length() && json.charAt(i) != ',' && json.charAt(i) != '}') i++;
            return json.substring(start, i).trim();
        }
    }

    /** Extraire un champ entier. Retourne defaultValue si absent ou non numérique. */
    public static int getInt(String json, String key, int defaultValue) {
        String val = getField(json, key);
        if (val == null || val.isEmpty()) return defaultValue;
        try {
            // Gérer les IDs envoyés en tant que String ("0") ou nombre (0)
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /** Échapper les caractères spéciaux JSON */
    public static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"")
                .replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }
}
