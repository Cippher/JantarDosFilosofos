package jantardosfilosofos;

/**
 *
 * @author 0196611 Vinícius Daniel N. B.
 */
public class Filosofo extends Thread
{
    // Cria um código privado para o filósofo
    private int ID;

    // Cria padrões de comportamento do filósofo
    final int PENSANDO = 0;
    final int FAMINTO  = 1;
    final int COMENDO  = 2;
    
    // Variável para contar o número de vezes que determinado filósofo comeu
    private int ComeuNVezes = 0;
    
    public int getComeuNVezes(){
        return ComeuNVezes;
    }
    public void setComeuNVezes(int ComeuNVezes){
        this.ComeuNVezes = ComeuNVezes;
    }
    //-------

    // Método construtor que recebe um nome para a classe e um código de
    // identificação do filósofo
    public Filosofo (String nome, int ID)
    {
        super(nome);
        this.ID = ID;
    }

    // Método para definir que o filósofo está com fome
    public void ComFome ()
    {
        // Seta o estado deste filósofo na classe Grade para FAMINTO
        Grade.estado[this.ID] = 1;
        
        // Exibe uma mensagem de controle na tela
        // System.out.println("O Filósofo " + getName() + " está FAMINTO!");
        
        // ------ Tentativa de forçar rodar o filosofo com maior prioridade
        for(int i=0; i<5; i++){
            if(Grade.filosofo[this.ID].getPriority() == 2){
                return;
            }
        }
        //------
    }

    // Método para definir que o filósofo está comendo
    public void Come ()
    {
        // Seta o estado deste filósofo na classe Grade para COMENDO
        Grade.estado[this.ID] = 2;
        // Exibe uma mensagem de controle na tela
        //-----
        //--System.out.println("O Filósofo " + getName() + " está COMENDO!");
        
        // Seta quantas vezes o filosofo ja comeu
        Grade.filosofo[this.ID].setComeuNVezes(Grade.filosofo[this.ID].getComeuNVezes() + 1);
        
        // Roda um for dentro de outro comparando todos os Filosofos entre si
        // Para setar a prioridade entre eles
        //-----
        for(int i=0; i<5; i++){
            //Exibe uma mensagem com o numero de vezes que o filosofo comeu atualizado
            //--System.out.println("----> O Filósofo " + Grade.filosofo[i].getName() + " comeu " + Grade.filosofo[i].getComeuNVezes() + " vezes!!! ");  
            //--System.out.println("----> O Filósofo " + Grade.filosofo[i].getName() + " tem prioridade " + Grade.filosofo[i].getPriority() + "\n");
            
            // Compara o número de vezes que os filósofo U, V, X, Y e Z comeram e atualiza a prioridade deles--
            for(int j=i+1; j<5; j++){
                if(Grade.filosofo[i].getComeuNVezes() - Grade.filosofo[j].getComeuNVezes() >= 2){
                   Grade.filosofo[i].setPriority(1);
                   Grade.filosofo[j].setPriority(2);
                   System.out.println("----> O Filósofo " + Grade.filosofo[j].getName() + " tem prioridade " + Grade.filosofo[j].getPriority() + "\n");
                }else{
                    if(Grade.filosofo[j].getComeuNVezes() - Grade.filosofo[i].getComeuNVezes() >= 2){
                       Grade.filosofo[j].setPriority(1);
                       Grade.filosofo[i].setPriority(2);
                       System.out.println("----> O Filósofo " + Grade.filosofo[i].getName() + " tem prioridade " + Grade.filosofo[i].getPriority() + "\n");
                    }else{
                        if(Grade.filosofo[i].getComeuNVezes() - Grade.filosofo[j].getComeuNVezes() < 2 && Grade.filosofo[j].getComeuNVezes() - Grade.filosofo[i].getComeuNVezes() < 2){
                           Grade.filosofo[j].setPriority(1);
                           Grade.filosofo[i].setPriority(1);
                        }
                    }
                }
            }
            //---
            //Exibe uma mensagem com o numero de vezes que o filosofo comeu atualizado
            System.out.println("----> O Filósofo " + Grade.filosofo[i].getName() + " comeu " + Grade.filosofo[i].getComeuNVezes() + " vezes!!! ");  
            //System.out.println("----> O Filósofo " + Grade.filosofo[i].getName() + " tem prioridade " + Grade.filosofo[i].getPriority() + "\n");
            //---
        }
        //-----
        System.out.println("\n");
        
        // Será criado um controle para o filósofo permanecer comendo
        // durante certo período de tempo
        try
        {
            // Fica parado neste estado por 1000 milisegundos
            Thread.sleep(1000L);
        }
        catch (InterruptedException ex)
        {
            // Exibe uma mensagem de controle de erro
            System.out.println("ERROR>" + ex.getMessage());
        }
    }

    // Método para definir que o filósofo está pensando
    public void Pensa ()
    {
        // Seta o estado deste filósofo na classe Grade para PENSANDO
        Grade.estado[this.ID] = 0;
        // Exibe uma mensagem de controle na tela
        //--------
        //--System.out.println("O Filósofo " + getName() + " está PENSANDO!");

        // Será criado um controle para o filósofo permanecer pensando
        // durante certo período de tempo
        try
        {
            // Fica parado neste estado por 1000 milisegundos
            Thread.sleep(1000L);
        }
        catch (InterruptedException ex)
        {
            // Exibe uma mensagem de controle de erro
            System.out.println("ERROR>" + ex.getMessage());
        }
    }

    // Método para o filósofo soltar um garfo que ele pegou
    public void LargarGarfo ()
    {
        // Decrementa o semáforo mutex principal da classe, isso permite
        // informar que o atual método está operando na mesa dos filósofos
        Grade.mutex.decrementar();

        // Coloca o filósofo para pensar determinado tempo
        Pensa();

        // Após o filósofo pensar, ele vai informar para os seus vizinhos
        // que podem tentar pegar os garfos que já estão disponíveis
        Grade.filosofo[VizinhoEsquerda()].TentarObterGarfos();
        Grade.filosofo[VizinhoDireita()].TentarObterGarfos();

        // Após operar, volta o semáforo mutex para o estado normal
        // indicando que já realizou todos procedimentos na mesa
        Grade.mutex.incrementar();
    }

    // Método para o filósofo pegar um garfo na mesa
    public void PegarGarfo ()
    {
        // Decrementa o semáforo mutex principal da classe, isso permite
        // informar que o atual método está operando na mesa dos filósofos
        Grade.mutex.decrementar();

        // Deixa o filósofo faminto por determinado tempo
        ComFome();

        // Após o filósofo o período de fome, ele vai verificar com seus
        // vizinhos se ele pode pegar os garfos
        TentarObterGarfos();

        // Após operar, volta o semáforo mutex para o estado normal
        // indicando que já realizou todos procedimentos na mesa
        Grade.mutex.incrementar();
        // Decrementa seu semáforo
        Grade.semaforos[this.ID].decrementar();
    }

    // Método para verificar se o filósofo pode pegar um garfo disposto na mesa
    public void TentarObterGarfos()
    {

        // Verifica se este filósofo está com fome, e se o vizinho da esquerda
        // e da direita não estão comendo
        if (Grade.estado[this.ID] == 1 &&
            Grade.estado[VizinhoEsquerda()] != 2 && 
            Grade.estado[VizinhoDireita()] != 2)
        {
            // Então este filósofo pode comer
            Come();
            // E incrementa o seu semáforo
            Grade.semaforos[this.ID].incrementar();
        }

    }

    // Método de execução da classe, onde o ambiente do filósofo será rodado
    @Override
    public void run ()
    {

        try
        {
            // Coloca o filósofo para pensar
            Pensa();

            // Então realiza uma vida infinita para o filósofo onde inicialmente
            // ele executa os procedimentos de pergar os garfos da mesa, posteriormente
            // ele descansa um pouco, e por fim, ele largar os garfos que ele pegou
            do
            {
                PegarGarfo();
                Thread.sleep(1000L);
                LargarGarfo();
            }
            while (true);
        }
        catch (InterruptedException ex)
        {
            // Exibe uma mensagem de controle de erro
            System.out.println("ERROR>" + ex.getMessage());
            // E da um retorno de cancelamento
            return;
        }

    }

    // Método para obter o filósofo vizinho da direita
    public int VizinhoDireita ()
    {
        // Rationa o valor em 5 posições, ou seja, se o ID deste filósofo acrescentado
        // de um for maior que quatro, passa a ser zero
        return (this.ID + 1) % 5;
    }

    // Método para obter o filósofo vizinho da esquerda
    public int VizinhoEsquerda ()
    {
        if (this.ID == 0)
        {
            // Retorna a ultima posição
            return 4;
        }
        else
        {
            // Rationa o valor em 5 posições, ou seja, se o ID deste filósofo decrescido
            // de um for menor que zero, passa a ser quatro
            return (this.ID - 1) % 5;
        }
    }

}
