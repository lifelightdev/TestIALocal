package life.light;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class Main {
    static void main() {
        try {
            URL url = new URL( "http://127.0.0.1:1234/v1/chat/completions" );
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/json" );
            conn.setDoOutput( true );

            String jsonInput = """
                    {
                      "model": "mistralai/mistral-7b-instruct-v0.3:2",
                      "messages": [
                        { "role": "user", "content": "Dis-moi simplement bonjour !" }
                      ],
                      "temperature": 0.7,
                      "max_tokens": 200
                    }
                    """;
            try (OutputStream os = conn.getOutputStream()) {
                os.write( jsonInput.getBytes( StandardCharsets.UTF_8 ) );
            }
            BufferedReader br = new BufferedReader(
                    new InputStreamReader( conn.getInputStream(), StandardCharsets.UTF_8 ) );
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append( line.trim() );
            }
            System.out.println( "Réponse de l'IA :" );
            System.out.println( response );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
