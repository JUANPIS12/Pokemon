public class Pokemon
{
    private String nombre;
    private String tipo;
    private int hp;
    private int hpActual;
    private int atk;
    private int def;
    private int speed;
    private String urlImagen;

    public Pokemon(
            String nombre,
            String tipo,
            int hp,
            int atk,
            int def,
            int speed,
            String urlImagen
    )
    {
        this.nombre = nombre;
        this.tipo = tipo;
        this.hp = hp;
        this.hpActual = hp;
        this.atk = atk;
        this.def = def;
        this.speed = speed;
        this.urlImagen = urlImagen;
    }

    public String getNombre()
    {
        return nombre;
    }

    public String getTipo()
    {
        return tipo;
    }

    public int getHp()
    {
        return hp;
    }

    public int getHpActual()
    {
        return hpActual;
    }

    public int getAtk()
    {
        return atk;
    }

    public int getDef()
    {
        return def;
    }

    public int getSpeed()
    {
        return speed;
    }

    public String getUrlImagen()
    {
        return urlImagen;
    }

    public void recibirDanio(int danio)
    {
        hpActual = hpActual - danio;

        if (hpActual < 0)
        {
            hpActual = 0;
        }
    }

    public boolean estaDerrotado()
    {
        return hpActual <= 0;
    }

    public void restaurarHp()
    {
        hpActual = hp;
    }
}