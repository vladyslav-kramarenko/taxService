package com.my.kramarenko.taxService.web;

import com.my.kramarenko.taxService.db.PasswordCreator;
import com.my.kramarenko.taxService.web.listener.ContextListener;
import org.apache.log4j.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.naming.NamingException;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

public class VerifyRecaptcha {
    private static final Logger LOG = Logger.getLogger(VerifyRecaptcha.class);
    public static final String url = "https://www.google.com/recaptcha/api/siteverify";
    public static final String secret = "6LeCWdkiAAAAAKDvXI2foWNQpW1ZVSwGQIpzkaS_";

    private final static String USER_AGENT = "Mozilla/5.0";

    public static boolean verify(String gRecaptchaResponse) throws IOException {
        if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
            return false;
        }

        try {
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            // add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            String postParams = "secret=" + secret + "&response="
                    + gRecaptchaResponse;

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            LOG.trace("\nSending 'POST' request to URL : " + url);
            LOG.trace("Post parameters : " + postParams);
            LOG.trace("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //parse JSON response and return 'success' value
            JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            return jsonObject.getBoolean("success");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }
}
