import java.io.IOException;

public class View {
    private Model tabuleiro;

    public Model getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(Model tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    void mostraTela() {
        clearConsole();
        System.out.println("\nTABULEIRO:\n");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                switch(tabuleiro.getPecaNaPosicao(i, j)) {
                    case 1: System.out.print(" O "); break;
                    case 3: System.out.print(" X "); break;
                    default: System.out.print("   ");
                }
                if (j < 2) System.out.print("|");
            }
            System.out.println();
            if (i < 2) System.out.println("------------");
        }
    }
    
    private void clearConsole() {
    try {
        ProcessBuilder processBuilder;
        if (System.getProperty("os.name").contains("Linux")) {
            processBuilder = new ProcessBuilder("clear");
        } else {
            processBuilder = new ProcessBuilder("cmd", "/c", "cls");
        }
        processBuilder.inheritIO().start().waitFor(); // Executa o comando e espera sua finalização
    } catch (Exception e) {
        System.out.println("Erro ao limpar a tela: " + e.getMessage());
    }
}

}
