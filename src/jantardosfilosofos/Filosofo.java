package jantardosfilosofos;

import java.util.Random;

public class Filosofo extends Thread {

    private int ID;

    private int ComeuNVezes = 0;
    
    private static Random random = new Random();

    public int getComeuNVezes() {
        return ComeuNVezes;
    }

    public void setComeuNVezes(int ComeuNVezes) {
        this.ComeuNVezes = ComeuNVezes;
    }

    public Filosofo(String nome, int ID) {
        super(nome);
        this.ID = ID;
    }

    // Método para definir que o filósofo está com fome
    public void ComFome() {
        // COM_FOME
        Iniciar.estado[this.ID] = 1;
    }

    // Filósofo está comendo
    public void Come() {
        // COMENDO
        Iniciar.estado[this.ID] = 2;

        // Seta quantas vezes o filosofo ja comeu
        Iniciar.filosofo[this.ID].setComeuNVezes(Iniciar.filosofo[this.ID].getComeuNVezes() + 1);
        
        for (int i = 0; i < 5; i++) {          
            // Compara o número de vezes que os filósofo U, V, X, Y e Z comeram e atualiza a prioridade deles
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
                            Iniciar.filosofo[j].setPriority(1);
                            Iniciar.filosofo[i].setPriority(1);
                        }
                    }
                }
            }
        }
        System.out.println("\n");

        try {
            Thread.sleep(random.nextInt(1500)+500);
        } catch (InterruptedException ex) {
            System.out.println("ERROR>" + ex.getMessage());
        }
    }

    // Defini que o filósofo está pensando
    public void Pensa() {
        // Seta o estado deste filósofo na classe Iniciar para PENSANDO
        Iniciar.estado[this.ID] = 0;
        
        try {
            Thread.sleep(random.nextInt(500)+500);
        } catch (InterruptedException ex) {
            System.out.println("ERROR>" + ex.getMessage());
        }
    }

    // Método para o filósofo soltar um garfo
    public void LargarGarfo() {

        Iniciar.controle.decrementar();
    
        if(Iniciar.controle.contador < 0){
            Iniciar.controle.contador = 0;
        }
        
        Pensa();    
        
        //Informa os vizinhos
        Iniciar.filosofo[VizinhoEsquerda()].GarfoEsquerdo();
        Iniciar.filosofo[VizinhoDireita()].GarfoEsquerdo();
  
        Iniciar.controle.incrementar();
        
        if(Iniciar.controle.getContador() > 2){
           Iniciar.controle.contador = 1; 
        }
    }

    // Filósofo pega um garfo na mesa
    public void PegarGarfo() {
        Iniciar.controle.decrementar();
 
        if(Iniciar.controle.contador < 0){
           Iniciar.controle.contador = 0;
        }
        
        ComFome();

        // Após o filósofo o período de fome, ele vai verificar com seus
        // vizinhos se ele pode pegar os garfos
        GarfoEsquerdo();

        Iniciar.controle.incrementar();

        if(Iniciar.controle.getContador() > 2){
           Iniciar.controle.contador = 1; 
        }
        
        // Decrementa seu controleFilosofo
        Iniciar.controleFilosofo[this.ID].decrementar();
        //Segundo "decrementar" para colocar o controleFilosofo em estado 0 (Pensando)
        Iniciar.controleFilosofo[this.ID].decrementar();
    }
    
    public synchronized void GarfoEsquerdo(){
    
        if(Iniciar.estado[this.ID] == 1 && Iniciar.estado[VizinhoEsquerda()] != 2 
                && Iniciar.controleFilosofo[VizinhoEsquerda()].getContador() != 2){
            
            Iniciar.controleFilosofo[this.ID].incrementar();
            
            GarfoDireito();
            notifyAll();
            
        }   
    }

    public synchronized void GarfoDireito(){
        if(Iniciar.estado[this.ID] == 1 && Iniciar.estado[VizinhoDireita()] != 2 
                && Iniciar.controleFilosofo[VizinhoDireita()].getContador() ==0){
            
            Iniciar.controleFilosofo[this.ID].incrementar();
            notify();
            Come();
            
        }else{
            Iniciar.controleFilosofo[this.ID].decrementar();
            ComFome();
            }
    }
    
    // Método de execução
    @Override
    public void run() {

        try {
            Pensa();
            do {
                Imprime();
                PegarGarfo();
                Thread.sleep(random.nextInt(500)+1500);
                LargarGarfo();
            } while (true);
        } catch (InterruptedException ex) {
            System.out.println("ERROR>" + ex.getMessage());
            return;
        }

    }

    // Obter o da direita
    public int VizinhoDireita() {
        return (this.ID + 1) % 5;
    }

    // Obter o vizinho da esquerda
    public int VizinhoEsquerda() {
        if (this.ID == 0) {
            return 4;
        } else {
            return (this.ID - 1) % 5;
        }
    }
 
    public void Imprime(){
        for(int i=0; i<5; i++){
            System.out.println("----> O Filósofo " + Iniciar.filosofo[i].getName() + 
                        " comeu " + Iniciar.filosofo[i].getComeuNVezes() + " vezes!!! ");
                        
            if(Iniciar.estado[i] == 0){
                System.out.println("O Filósofo " + Iniciar.filosofo[i].getName() + " está PENSANDO!");
            }else{
                if(Iniciar.estado[i] == 1){
                    System.out.println("O Filosofo " + Iniciar.filosofo[i].getName() + " está COM FOME!");
                }else{
                    System.out.println("O Filosofo " + Iniciar.filosofo[i].getName() + " está COMENDO!");
                }
            }
        }
        System.out.println("\n");
    }

}
