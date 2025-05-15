package jurassicpark;


import jurassicpark.controller.AppController;


/**
 * Clase principal que inicia la aplicación
 */
public class Main {

    /**
     * Método principal que inicia la aplicación
     *
     * @param args Argumentos de la línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        AppController controlador = new AppController();
        controlador.iniciar();


    }
}
