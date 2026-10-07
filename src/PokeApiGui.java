import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class PokeApiGui
{
    private JPanel panel1;

    private JLabel textimagen1;
    private JButton random1;
    private JTextField nombre1;
    private JLabel tipo1;
    private JTextField hp1;
    private JTextField atk1;
    private JTextField def1;
    private JTextField speed1;

    private JLabel textimagen2;
    private JButton random2;
    private JTextField nombre2;
    private JLabel tipo2;
    private JTextField hp2;
    private JTextField atk2;
    private JTextField def2;
    private JTextField speed2;

    private JButton combate;

    private JTextArea areaBatalla;

    private Pokemon pokemon1;
    private Pokemon pokemon2;

    private Battle batalla;

    private final PokeApiClient apiClient;

    private final Random random;

    private Timer timerCombate;

    public PokeApiGui()
    {
        apiClient = new PokeApiClient();

        random = new Random();

        nombre1.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        consultarPokemon(
                                nombre1.getText(),
                                1
                        );
                    }
                }
        );

        nombre2.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        consultarPokemon(
                                nombre2.getText(),
                                2
                        );
                    }
                }
        );

        random1.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        int numeroPokemon =
                                random.nextInt(1025) + 1;

                        consultarPokemon(
                                String.valueOf(numeroPokemon),
                                1
                        );
                    }
                }
        );

        random2.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        int numeroPokemon =
                                random.nextInt(1025) + 1;

                        consultarPokemon(
                                String.valueOf(numeroPokemon),
                                2
                        );
                    }
                }
        );

        combate.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        iniciarCombate();
                    }
                }
        );
    }

    public void consultarPokemon(
            String nombrePokemon,
            int jugador
    )
    {
        nombrePokemon =
                nombrePokemon.trim().toLowerCase();

        if (nombrePokemon.isEmpty())
        {
            JOptionPane.showMessageDialog(
                    null,
                    "Ingrese el nombre de un Pokemon"
            );

            return;
        }

        final String pokemonBuscado = nombrePokemon;

        SwingWorker<Pokemon, Void> worker =
                new SwingWorker<Pokemon, Void>()
                {
                    @Override
                    protected Pokemon doInBackground()
                            throws Exception
                    {
                        return apiClient.obtenerPokemon(
                                pokemonBuscado
                        );
                    }

                    @Override
                    protected void done()
                    {
                        try
                        {
                            Pokemon pokemon = get();

                            if (pokemon == null)
                            {
                                JOptionPane.showMessageDialog(
                                        null,
                                        "El Pokemon no existe"
                                );

                                return;
                            }

                            mostrarPokemon(
                                    pokemon,
                                    jugador
                            );
                        }
                        catch (Exception e)
                        {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Error al consultar PokeAPI"
                            );

                            e.printStackTrace();
                        }
                    }
                };

        worker.execute();
    }

    private void mostrarPokemon(
            Pokemon pokemon,
            int jugador
    )
    {
        if (jugador == 1)
        {
            pokemon1 = pokemon;

            nombre1.setText(
                    pokemon.getNombre()
            );

            tipo1.setText(
                    pokemon.getTipo()
            );

            hp1.setText(
                    String.valueOf(
                            pokemon.getHpActual()
                    )
            );

            atk1.setText(
                    String.valueOf(
                            pokemon.getAtk()
                    )
            );

            def1.setText(
                    String.valueOf(
                            pokemon.getDef()
                    )
            );

            speed1.setText(
                    String.valueOf(
                            pokemon.getSpeed()
                    )
            );

            cargarImagen(
                    pokemon.getUrlImagen(),
                    textimagen1
            );
        }
        else
        {
            pokemon2 = pokemon;

            nombre2.setText(
                    pokemon.getNombre()
            );

            tipo2.setText(
                    pokemon.getTipo()
            );

            hp2.setText(
                    String.valueOf(
                            pokemon.getHpActual()
                    )
            );

            atk2.setText(
                    String.valueOf(
                            pokemon.getAtk()
                    )
            );

            def2.setText(
                    String.valueOf(
                            pokemon.getDef()
                    )
            );

            speed2.setText(
                    String.valueOf(
                            pokemon.getSpeed()
                    )
            );

            cargarImagen(
                    pokemon.getUrlImagen(),
                    textimagen2
            );
        }
    }

    public void cargarImagen(
            String url,
            JLabel etiquetaImagen
    )
    {
        try
        {
            java.net.URL urlImagen =
                    new java.net.URL(url);

            ImageIcon icono =
                    new ImageIcon(urlImagen);

            Image imagen =
                    icono.getImage()
                            .getScaledInstance(
                                    150,
                                    150,
                                    Image.SCALE_DEFAULT
                            );

            etiquetaImagen.setText("");

            etiquetaImagen.setIcon(
                    new ImageIcon(imagen)
            );
        }
        catch (Exception e)
        {
            etiquetaImagen.setText(
                    "No se pudo cargar"
            );

            etiquetaImagen.setIcon(null);

            e.printStackTrace();
        }
    }

    private void iniciarCombate()
    {
        if (pokemon1 == null || pokemon2 == null)
        {
            JOptionPane.showMessageDialog(
                    null,
                    "Primero debes seleccionar los dos Pokemon"
            );

            return;
        }

        if (timerCombate != null &&
                timerCombate.isRunning())
        {
            return;
        }

        pokemon1.restaurarHp();
        pokemon2.restaurarHp();

        actualizarHp();

        batalla =
                new Battle(
                        pokemon1,
                        pokemon2
                );

        areaBatalla.setText("");

        agregarLog(
                "=============================="
        );

        agregarLog(
                "       COMIENZA EL COMBATE"
        );

        agregarLog(
                "=============================="
        );

        agregarLog(
                pokemon1.getNombre()
                        + " VS "
                        + pokemon2.getNombre()
        );

        agregarLog("");

        agregarLog(
                "Comienza: "
                        + batalla
                        .getAtacante()
                        .getNombre()
        );

        agregarLog("");

        timerCombate =
                new Timer(
                        1200,
                        new ActionListener()
                        {
                            @Override
                            public void actionPerformed(
                                    ActionEvent e
                            )
                            {
                                realizarTurno();
                            }
                        }
                );

        timerCombate.start();
    }

    private void realizarTurno()
    {
        Pokemon atacante =
                batalla.getAtacante();

        Pokemon defensor =
                batalla.getDefensor();

        int danio =
                batalla.realizarAtaque();

        agregarLog(
                atacante.getNombre()
                        + " ataca a "
                        + defensor.getNombre()
        );

        agregarLog(
                "Daño causado: "
                        + danio
        );

        agregarLog(
                "HP restante de "
                        + defensor.getNombre()
                        + ": "
                        + defensor.getHpActual()
        );

        agregarLog("");

        actualizarHp();

        if (batalla.termino())
        {
            timerCombate.stop();

            Pokemon ganador =
                    batalla.getGanador();

            agregarLog(
                    "=============================="
            );

            agregarLog(
                    "¡"
                            + ganador.getNombre()
                            + " HA GANADO!"
            );

            agregarLog(
                    "=============================="
            );

            JOptionPane.showMessageDialog(
                    null,
                    "¡"
                            + ganador.getNombre()
                            + " ha ganado!"
            );

            return;
        }

        batalla.cambiarTurno();
    }

    private void actualizarHp()
    {
        hp1.setText(
                String.valueOf(
                        pokemon1.getHpActual()
                )
        );

        hp2.setText(
                String.valueOf(
                        pokemon2.getHpActual()
                )
        );
    }

    private void agregarLog(
            String mensaje
    )
    {
        areaBatalla.append(
                mensaje + "\n"
        );

        areaBatalla.setCaretPosition(
                areaBatalla.getDocument()
                        .getLength()
        );
    }

    public static void main(String[] args)
    {
        JFrame frame =
                new JFrame(
                        "Pokémon Stadium Lite"
                );

        frame.setContentPane(
                new PokeApiGui().panel1
        );

        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        frame.pack();

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

        frame.setResizable(true);
    }
}