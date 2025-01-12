public class Model {
    private int[][] tabuleiroPreenchido = new int[3][3];

    public Model() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiroPreenchido[i][j] = 2; // Inicia com tudo vazio
            }
        }
    }

    public int[][] getTabuleiroPreenchido() {
        return tabuleiroPreenchido;
    }

    public void setTabuleiroPreenchido(int[][] tabuleiroPreenchido) {
        this.tabuleiroPreenchido = tabuleiroPreenchido;
    }

    public int setPecaNaPosicao(int linha, int coluna, int valor) {
        if (linha < 0 || linha > 2 || coluna < 0 || coluna > 2 || valor < 1 || valor > 3) {
            return 0;
        }
        tabuleiroPreenchido[linha][coluna] = valor;
        return 1;
    }

    public int getPecaNaPosicao(int linha, int coluna) {
        return tabuleiroPreenchido[linha][coluna];
    }
}
