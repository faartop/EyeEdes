package com.eyeedes.API;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApiConnection {

    private static final Logger LOGGER = Logger.getLogger(ApiConnection.class.getName());

    public static JSONObject validaUrl(String cep) {
        String apiUrl = "https://viacep.com.br/ws/" + cep + "/json/";
        JSONObject responseJson = null;

        try {
            responseJson = apiCEP(apiUrl);
            return filtraResponse(responseJson);
        } catch (MalformedURLException e) {
            LOGGER.log(Level.SEVERE, "URL inválida! Verifique o CEP e tente novamente.", e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro de I/O ocorreu ao tentar conectar com a API.", e);
        }

        return null;
    }

    public static JSONObject apiCEP(String apiUrl) throws IOException {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(apiUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();

            if (responseCode == 400) {
                LOGGER.log(Level.WARNING, "CEP inválido, verifique se não há:\n- Espaços\n- Letras\n- Dígitos a mais");
                return null;
            } else if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Falha: Erro HTTP: " + responseCode);
            }

            try (BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = input.readLine()) != null) {
                    response.append(line);
                }
                return new JSONObject(response.toString());
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private static JSONObject filtraResponse(JSONObject responseJson) {
        if (responseJson == null) {
            return null;
        }

        if (responseJson.has("cep") && responseJson.has("logradouro") &&
                responseJson.has("bairro") && responseJson.has("localidade") &&
                responseJson.has("uf")) {

            JSONObject filteredJson = new JSONObject();
            filteredJson.put("cep", responseJson.getString("cep"));
            filteredJson.put("logradouro", responseJson.getString("logradouro"));
            filteredJson.put("bairro", responseJson.getString("bairro"));
            filteredJson.put("localidade", responseJson.getString("localidade"));
            filteredJson.put("uf", responseJson.getString("uf"));

            return filteredJson;
        } else {
            LOGGER.log(Level.WARNING, "Resposta da API não contém todos os campos necessários.");
            return null;
        }
    }

    public static void printCepData(String cep) {
        JSONObject resultado = validaUrl(cep);
        if (resultado != null) {
            System.out.println("Dados do CEP:");
            System.out.println("CEP: " + resultado.optString("cep"));
            System.out.println("Logradouro: " + resultado.optString("logradouro"));
            System.out.println("Bairro: " + resultado.optString("bairro"));
            System.out.println("Localidade: " + resultado.optString("localidade"));
            System.out.println("UF: " + resultado.optString("uf"));
        } else {
            System.out.println("Não foi possível obter os dados do CEP ou os dados retornados são incompletos.");
        }
    }

}
