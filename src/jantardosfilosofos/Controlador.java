package jantardosfilosofos;

public class Controlador
{
    protected int contador;

    public Controlador ()
    {
        this.contador = 0;
    }

    public Controlador (int valor){
        this.contador = valor;
    }

    public synchronized void decrementar (){

        while (this.contador == 0)
        {
            try
            {
                wait();
            }
            catch (InterruptedException ex)
            {
                System.out.println("ERROR>" + ex.getMessage());
            }
        }
        this.contador--;

    }

    public synchronized void incrementar (){
        this.contador++;
        notify();
    }
    public int getContador(){
        return contador;
    }
}
