package jantardosfilosofos;

public class Iniciar
{
    public static Controlador controle = new Controlador(1);

    public static Controlador[] controleFilosofo = new Controlador[5];
    
    public static int estado[] = new int[5];

    static Filosofo filosofo[] = new Filosofo[5];

    public Iniciar ()
    {   
        init();
    }

    public void init ()
    {
        for (int i = 0; i < estado.length; i++)
        {
            estado[i] = 0;
        }

        Thread.currentThread().setPriority(1);

        filosofo[0] = new Filosofo("Filósofo 0", 0);
        filosofo[1] = new Filosofo("Filósofo 1", 1);
        filosofo[2] = new Filosofo("Filósofo 2", 2);
        filosofo[3] = new Filosofo("Filósofo 3", 3);
        filosofo[4] = new Filosofo("Filósofo 4", 4);

        controleFilosofo[0] = new Controlador(0);
        controleFilosofo[1] = new Controlador(0);
        controleFilosofo[2] = new Controlador(0);
        controleFilosofo[3] = new Controlador(0);
        controleFilosofo[4] = new Controlador(0);

        filosofo[0].start();
        filosofo[1].start();
        filosofo[2].start();
        filosofo[3].start();
        filosofo[4].start();
    }
}
