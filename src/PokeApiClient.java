import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PokeApiClient
{
    private final HttpClient client;

    public PokeApiClient()
    {
        client = HttpClient.newHttpClient();
    }

    public Pokemon obtenerPokemon(String nombrePokemon)
            throws IOException, InterruptedException
    {
        nombrePokemon =
                nombrePokemon.trim().toLowerCase();

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        "https://pokeapi.co/api/v2/pokemon/"
                                                + nombrePokemon
                                )
                        )
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        if (response.statusCode() != 200)
        {
            return null;
        }

        JSONObject json =
                new JSONObject(response.body());

        String nombre =
                json.getString("name");

        StringBuilder tipos =
                new StringBuilder();

        json.getJSONArray("types")
                .forEach(objetoTipo ->
                {
                    JSONObject tipoJson =
                            (JSONObject) objetoTipo;

                    JSONObject tipo =
                            tipoJson.getJSONObject("type");

                    String nombreTipo =
                            tipo.getString("name");

                    if (tipos.length() > 0)
                    {
                        tipos.append(" / ");
                    }

                    tipos.append(nombreTipo);
                });

        int hp = 0;
        int atk = 0;
        int def = 0;
        int speed = 0;

        for (Object objetoEstadistica :
                json.getJSONArray("stats"))
        {
            JSONObject estadisticaJson =
                    (JSONObject) objetoEstadistica;

            JSONObject estadistica =
                    estadisticaJson.getJSONObject("stat");

            String nombreEstadistica =
                    estadistica.getString("name");

            int valor =
                    estadisticaJson.getInt("base_stat");

            if (nombreEstadistica.equals("hp"))
            {
                hp = valor;
            }
            else if (nombreEstadistica.equals("attack"))
            {
                atk = valor;
            }
            else if (nombreEstadistica.equals("defense"))
            {
                def = valor;
            }
            else if (nombreEstadistica.equals("speed"))
            {
                speed = valor;
            }
        }

        JSONObject sprites =
                json.getJSONObject("sprites");

        String urlImagen =
                sprites.getString("front_default");

        return new Pokemon(
                nombre,
                tipos.toString(),
                hp,
                atk,
                def,
                speed,
                urlImagen
        );
    }
}