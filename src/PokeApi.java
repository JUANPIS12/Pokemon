import org.json.JSONObject;

import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PokeApi
{
    public void consultarPokemon()
    {
        try
        {
            String nombrePokemon = JOptionPane.showInputDialog("ingrese el nombre del pokemon");
            HttpClient cliente  = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://pokeapi.co/api/v2/pokemon/" + nombrePokemon))
                    .build();


            //ejecutamos la solicitud

            HttpResponse<String> response = cliente.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200)
            {
                System.out.println("El pokemon se ha consultado");

                JSONObject json  = new JSONObject(response.body());

                System.out.println("ID pokemon: " + json.getInt("id"));
                System.out.println("Nombre: " + json.getString("name"));
                System.out.println("peso: " + json.getInt("weight"));
                System.out.println("altura: " + json.getInt("height"));

                //obtenemos las habilidades pero estas estan contenidas en un array
                System.out.println("Habilidades:");

                json.getJSONArray("abilities").forEach(objetoHabilidad ->
                {
                    JSONObject habilidad = (JSONObject) objetoHabilidad;

                    JSONObject ability = habilidad.getJSONObject("ability");

                    System.out.println("Nombre habilidad: " + ability.getString("name"));
                });

                System.out.println("ESTADISTICAS ***");

                json.getJSONArray("stats").forEach(estadistica ->
                {
                    // Accedemos al objeto
                    JSONObject estadisticaJson = (JSONObject) estadistica;

                    JSONObject estadisticaName = estadisticaJson.getJSONObject("stat");

                    System.out.println(
                            estadisticaName.getString("name") + ": "
                                    + estadisticaJson.getInt("base_stat")
                    );
                });

                System.out.println("\nImagen:");

                JSONObject foto = json.getJSONObject("sprites");
                System.out.println(foto.getString("front_default"));


                System.out.println("\nSonido:");
                System.out.println(json.getJSONObject("cries").getString("latest"));


            }
            else
            {
                JOptionPane.showInputDialog(null, "el pokemon no existe");
            }

        }
        catch(IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    //psvm
    // Punto de entrada
    public static void main(String[] args)
    {
        PokeApi pokeApi = new PokeApi();
        pokeApi.consultarPokemon();
    }
}