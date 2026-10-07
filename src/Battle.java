import java.util.Random;

public class Battle
{
    private Pokemon pokemon1;
    private Pokemon pokemon2;

    private Pokemon atacante;
    private Pokemon defensor;

    private Random random;

    public Battle(Pokemon pokemon1, Pokemon pokemon2)
    {
        this.pokemon1 = pokemon1;
        this.pokemon2 = pokemon2;

        random = new Random();

        determinarPrimerTurno();
    }

    private void determinarPrimerTurno()
    {
        if (pokemon1.getSpeed() > pokemon2.getSpeed())
        {
            atacante = pokemon1;
            defensor = pokemon2;
        }
        else if (pokemon2.getSpeed() > pokemon1.getSpeed())
        {
            atacante = pokemon2;
            defensor = pokemon1;
        }
        else
        {
            if (random.nextBoolean())
            {
                atacante = pokemon1;
                defensor = pokemon2;
            }
            else
            {
                atacante = pokemon2;
                defensor = pokemon1;
            }
        }
    }

    public Pokemon getAtacante()
    {
        return atacante;
    }

    public Pokemon getDefensor()
    {
        return defensor;
    }

    public int realizarAtaque()
    {
        int ataque =
                atacante.getAtk();

        int defensa =
                defensor.getDef();

        int danio =
                ataque - (defensa / 2);

        if (danio < 1)
        {
            danio = 1;
        }

        double variacion =
                0.85 + (random.nextDouble() * 0.15);

        danio =
                (int) (danio * variacion);

        if (danio < 1)
        {
            danio = 1;
        }

        defensor.recibirDanio(danio);

        return danio;
    }

    public void cambiarTurno()
    {
        Pokemon temporal =
                atacante;

        atacante =
                defensor;

        defensor =
                temporal;
    }

    public boolean termino()
    {
        return pokemon1.estaDerrotado()
                || pokemon2.estaDerrotado();
    }

    public Pokemon getGanador()
    {
        if (pokemon1.estaDerrotado())
        {
            return pokemon2;
        }

        if (pokemon2.estaDerrotado())
        {
            return pokemon1;
        }

        return null;
    }
}