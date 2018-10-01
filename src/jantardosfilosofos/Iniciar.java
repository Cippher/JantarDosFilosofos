package jantardosfilosofos;

/**
 *
 * @author 0196611 Vinícius Daniel N. B.
 */
public class Iniciar
{

    // Criação dos controladores da aplicação

    // O Controlador controle que recebe o valor incial 1 para o contador
    // e é o controlador principal
    public static Controlador controle = new Controlador(1);

    // O vetor semáforos são normais e existe um semáforo para cada filósofo
    // que será criado, esses semafóros não recebem valores de inicialização
    // portanto iniciando o contador em 0
    //----Alteração para testar contador e controlador
    //----Alteração na classe filosofo onde tem controlador
    //----Printar o semaforo na classe filosofo para testar o controlador----
    public static Controlador[] controleFilosofo = new Controlador[5];
    //----

    // Define um vetor para o estado de cada um dos filósofos presentes
    // na aplicação
    public static int estado[] = new int[5];

    // Cria 5 filósofos em um vetor para a aplicação
    static Filosofo filosofo[] = new Filosofo[5];

    // Método construtor da Iniciar da aplicação
    public Iniciar ()
    {   
        init();
    }

    // Método para inicializar tudo o que é preciso dentro da classe
    public void init ()
    {
        // Inicializa todos os estados para zero
        for (int i = 0; i < estado.length; i++)
        {
            estado[i] = 0;
        }

        // Define a prioridade para a Thread
        Thread.currentThread().setPriority(1);

        // Inicializa todos filósofos
        filosofo[0] = new Filosofo("Goku", 0);
        filosofo[1] = new Filosofo("Vegeta", 1);
        filosofo[2] = new Filosofo("Gohan", 2);
        filosofo[3] = new Filosofo("Goten", 3);
        filosofo[4] = new Filosofo("Bardock", 4);

        // Inicializa todos semáforos
        controleFilosofo[0] = new Controlador(0);
        controleFilosofo[1] = new Controlador(0);
        controleFilosofo[2] = new Controlador(0);
        controleFilosofo[3] = new Controlador(0);
        controleFilosofo[4] = new Controlador(0);

        // Inicia a execução de todos filósofos (fazer um FOR)
        filosofo[0].start();
        filosofo[1].start();
        filosofo[2].start();
        filosofo[3].start();
        filosofo[4].start();
        
        // Seta prioridade 1
        //filosofo[0].setPriority(2);
        //filosofo[1].setPriority(1);
        //filosofo[2].setPriority(1);
        //filosofo[3].setPriority(1);
        //filosofo[4].setPriority(1);

    }
}
