package jantardosfilosofos;

import java.util.Random;

/**
 *
 * @author 0196611 Vinícius Daniel N. B.
 */
public class Filosofo extends Thread {

    // Cria um código privado para o filósofo
    private int ID;

    // Variável para contar o número de vezes que determinado filósofo comeu
    private int ComeuNVezes = 0;
    
    //Random
    private static Random random = new Random();

    public int getComeuNVezes() {
        return ComeuNVezes;
    }

    public void setComeuNVezes(int ComeuNVezes) {
        this.ComeuNVezes = ComeuNVezes;
    }

    // Método construtor que recebe um nome para a classe e um código de
    // identificação do filósofo
    public Filosofo(String nome, int ID) {
        super(nome);
        this.ID = ID;
    }

    // Método para definir que o filósofo está com fome
    public void ComFome() {
        // Seta o estado deste filósofo na classe Iniciar para COM_FOME
        Iniciar.estado[this.ID] = 1;

        //-*-*-*- Exibe uma mensagem de controle na tela
        //System.out.println("O Filósofo " + getName() + " está COM FOME!");
        //-*-*-*-
        // ------ Tentativa de forçar rodar o filosofo com maior prioridade
        //for (int i = 0; i < 5; i++) {
            if (Iniciar.filosofo[this.ID].getPriority() == 2) {
                Iniciar.controle.contador = 1;
                //Iniciar.filosofo[VizinhoEsquerda()].Pensa();
                Iniciar.filosofo[VizinhoDireita()].Pensa();
                
                //>>>>>
                System.out.println("------>" + Iniciar.controle.getContador());
                
                Iniciar.filosofo[VizinhoEsquerda()].LargarGarfo();
                //Iniciar.filosofo[VizinhoDireita()].LargarGarfo();
                //>>>>>>
            }
        //}
    }

    // Método para definir que o filósofo está comendo
    public void Come() {
        // Seta o estado deste filósofo na classe Iniciar para COMENDO
        Iniciar.estado[this.ID] = 2;
        // Exibe uma mensagem de controle na tela
        //-*-*-*-*-
        //System.out.println("O Filósofo " + getName() + " está COMENDO!");

        // Seta quantas vezes o filosofo ja comeu
        Iniciar.filosofo[this.ID].setComeuNVezes(Iniciar.filosofo[this.ID].getComeuNVezes() + 1);
        

        // Roda um for dentro de outro comparando todos os Filosofos entre si
        // Para setar a prioridade entre eles
        for (int i = 0; i < 5; i++) {
            //-*-*-*-Exibe uma mensagem com o numero de vezes que o filosofo comeu atualizado
            //System.out.println("----> O Filósofo " + Iniciar.filosofo[i].getName() + " tem prioridade " + Iniciar.filosofo[i].getPriority() + "\n");

            // Compara o número de vezes que os filósofo U, V, X, Y e Z comeram e atualiza a prioridade deles--
            for (int j = i + 1; j < 5; j++) {
                if (Iniciar.filosofo[i].getComeuNVezes() - Iniciar.filosofo[j].getComeuNVezes() >= 2) {
                    Iniciar.filosofo[i].setPriority(1);
                    Iniciar.filosofo[j].setPriority(2);
                    System.out.println("----> O Filósofo " + Iniciar.filosofo[j].getName() + " tem prioridade " + Iniciar.filosofo[j].getPriority() + "\n");
                } else {
                    if (Iniciar.filosofo[j].getComeuNVezes() - Iniciar.filosofo[i].getComeuNVezes() >= 2) {
                        Iniciar.filosofo[j].setPriority(1);
                        Iniciar.filosofo[i].setPriority(2);
                        System.out.println("----> O Filósofo " + Iniciar.filosofo[i].getName() + " tem prioridade " + Iniciar.filosofo[i].getPriority() + "\n");
                    } else {
                        if (Iniciar.filosofo[i].getComeuNVezes() - Iniciar.filosofo[j].getComeuNVezes() < 2 && Iniciar.filosofo[j].getComeuNVezes() - Iniciar.filosofo[i].getComeuNVezes() < 2) {
                            //Volta para prioridade 1
                            Iniciar.filosofo[j].setPriority(1);
                            Iniciar.filosofo[i].setPriority(1);
                        }
                    }
                }
            }
            //---
            //Exibe uma mensagem com o numero de vezes que o filosofo comeu atualizado
            //-*-*-*-
            //System.out.println("----> O Filósofo " + Iniciar.filosofo[i].getName() + 
                    //" comeu " + Iniciar.filosofo[i].getComeuNVezes() + " vezes!!! ");  
            //System.out.println("----> O Filósofo " + Iniciar.filosofo[i].getName() + " tem prioridade " + Iniciar.filosofo[i].getPriority() + "\n");
            //-*-*-*-
        }
        //-----
        System.out.println("\n");

        // Será criado um controle para o filósofo permanecer comendo
        // durante certo período de tempo
        try {
            // Fica parado neste estado por 1000 milisegundos
            Thread.sleep(random.nextInt(1000)+500);
        } catch (InterruptedException ex) {
            // Exibe uma mensagem de controle de erro
            System.out.println("ERROR>" + ex.getMessage());
        }
    }

    // Método para definir que o filósofo está pensando
    public void Pensa() {
        // Seta o estado deste filósofo na classe Iniciar para PENSANDO
        Iniciar.estado[this.ID] = 0;
        
        for(int i=0; i<5; i++){
        System.out.println("----> O Filósofo " + Iniciar.filosofo[i].getName() + 
                        " comeu " + Iniciar.filosofo[i].getComeuNVezes() + " vezes!!! ");
        }
        System.out.println("\n");
        // Exibe uma mensagem de controle na tela
        //-*-*-*-
        //System.out.println("O Filósofo " + getName() + " está PENSANDO!");
        //-*-*-*-
        // Será criado um controle para o filósofo permanecer pensando
        // durante certo período de tempo
        try {
            // Fica parado neste estado por 1000 milisegundos
            Thread.sleep(random.nextInt(500)+700);
        } catch (InterruptedException ex) {
            // Exibe uma mensagem de controle de erro
            System.out.println("ERROR>" + ex.getMessage());
        }
    }

    // Método para o filósofo soltar um garfo que ele pegou
    public void LargarGarfo() {
        // Decrementa o Controlador controle principal da classe, isso permite
        // informar que o atual método está operando na mesa dos filósofos
        Iniciar.controle.decrementar();
        //>>>>>
        if(Iniciar.controle.contador < 0){
            Iniciar.controle.contador = 0;
        }//>>>>>
        
        // Coloca o filósofo para pensar determinado tempo
        Pensa();
        //Iniciar.controle.incrementar();
        // Após o filósofo pensar, ele vai informar para os seus vizinhos
        // que podem tentar pegar os garfos que já estão disponíveis
        //<<<<<Iniciar.filosofo[VizinhoEsquerda()].TentarObterGarfos();
        //<<<<<Iniciar.filosofo[VizinhoDireita()].TentarObterGarfos();
        
        //>>>>>
        Iniciar.filosofo[VizinhoEsquerda()].GarfoEsquerdo();
        Iniciar.filosofo[VizinhoDireita()].GarfoEsquerdo();
        //>>>>>
        // Após operar, volta o Controlador controle para o estado normal
        // indicando que já realizou todos procedimentos na mesa
        Iniciar.controle.incrementar();
        
        //Controle de contador
        //>>>>>
        if(Iniciar.controle.getContador() > 2){
           Iniciar.controle.contador = 1; 
        }//>>>>>
    }

    // Método para o filósofo pegar um garfo na mesa
    public void PegarGarfo() {
        // Decrementa o Controlador controle principal da classe, isso permite
        // informar que o atual método está operando na mesa dos filósofos
        Iniciar.controle.decrementar();
        //>>>>>
        if(Iniciar.controle.contador < 0){
           Iniciar.controle.contador = 0;
        }//>>>>>
        
        // Deixa o filósofo faminto por determinado tempo
        ComFome();

        // Após o filósofo o período de fome, ele vai verificar com seus
        // vizinhos se ele pode pegar os garfos
        //<<<<<TentarObterGarfos();
        //>>>>>Chamada na versão 2 garfos
        GarfoEsquerdo();

        // Após operar, volta o Controlador controle para o estado normal
        // indicando que já realizou todos procedimentos na mesa
        Iniciar.controle.incrementar();
        //>>>>>
        if(Iniciar.controle.getContador() > 2){
           Iniciar.controle.contador = 1; 
        }//>>>>>
        
        // Decrementa seu controleFilosofo
        //----
        //System.out.println("O Filósofo " + getName() + " SEMAFORO" + Iniciar.controleFilosofo[this.ID].getContador());
        Iniciar.controleFilosofo[this.ID].decrementar();
        //>>>>>Segundo "decrementar" para colocar o controleFilosofo em estado 0
        //>>>>>Na Versão com 2 garfos, controleFilosofo 0 = sem garfo, 1 = garfo esquerdo e 2 = garfo esquerdo e direito
        Iniciar.controleFilosofo[this.ID].decrementar();
    }

    // Método para verificar se o filósofo pode pegar um garfo disposto na mesa
    /*<<<<<public void TentarObterGarfos() {
        // Verifica se este filósofo está com fome, e se o vizinho da esquerda
        // e da direita não estão comendo
        if (Iniciar.estado[this.ID] == 1
                && Iniciar.estado[VizinhoEsquerda()] != 2
                && Iniciar.estado[VizinhoDireita()] != 2) {
            // Então este filósofo pode comer
            Come();
            // E incrementa o seu controleFilosofo
            Iniciar.controleFilosofo[this.ID].incrementar();
        }
    }*///<<<<<
    
    
    //>>>>>
    
    public void GarfoEsquerdo(){
    
        if(Iniciar.estado[this.ID] == 1 && Iniciar.estado[VizinhoEsquerda()] != 2 
                && Iniciar.controleFilosofo[VizinhoEsquerda()].getContador() != 2){
            
            Iniciar.controleFilosofo[this.ID].incrementar();
            
            GarfoDireito();
            
        }
    }
    
    //>>>>>
    public void GarfoDireito(){
        if(Iniciar.estado[this.ID] == 1 && Iniciar.estado[VizinhoDireita()] != 2 
                && Iniciar.controleFilosofo[VizinhoDireita()].getContador() ==0){
            
            Iniciar.controleFilosofo[this.ID].incrementar();
    
            Come();
            
            //Foi trocado para cima
            //Iniciar.controleFilosofo[this.ID].incrementar();
        }else{
            Iniciar.controleFilosofo[this.ID].decrementar();
            }
    }
    //>>>>>
    
    // Método de execução da classe, onde o ambiente do filósofo será rodado
    @Override
    public void run() {

        try {
            // Coloca o filósofo para pensar
            Pensa();

            // Então realiza uma vida infinita para o filósofo onde inicialmente
            // ele executa os procedimentos de pergar os garfos da mesa, posteriormente
            // ele descansa um pouco, e por fim, ele largar os garfos que ele pegou
            do {
                PegarGarfo();
                Thread.sleep(random.nextInt(500)+1500);
                LargarGarfo();
            } while (true);
        } catch (InterruptedException ex) {
            // Exibe uma mensagem de controle de erro
            System.out.println("ERROR>" + ex.getMessage());
            // E da um retorno de cancelamento
            return;
        }

    }

    // Método para obter o filósofo vizinho da direita
    public int VizinhoDireita() {
        // Rationa o valor em 5 posições, ou seja, se o ID deste filósofo acrescentado
        // de um for maior que quatro, passa a ser zero
        return (this.ID + 1) % 5;
    }

    // Método para obter o filósofo vizinho da esquerda
    public int VizinhoEsquerda() {
        if (this.ID == 0) {
            // Retorna a ultima posição
            return 4;
        } else {
            // Rationa o valor em 5 posições, ou seja, se o ID deste filósofo decrescido
            // de um for menor que zero, passa a ser quatro
            return (this.ID - 1) % 5;
        }
    }

}
