public class Control {
    private View tela;
    private Model tabuleiro;

    public Control(View tela, Model tabuleiro) {
        this.tela = tela;
        this.tabuleiro = tabuleiro;
        this.tela.setTabuleiro(this.tabuleiro); // Atualiza o tabuleiro no View para ser o mesmo do Control
    }

    public void rodaJogo() {
        boolean jogoAtivo = true;
        tela.mostraTela();
        while (jogoAtivo) {
            pedirNovaJogadaAoUsuario();
            tela.mostraTela();
            if (verificaVencedor()) {
                System.out.println("\n\n ***** VOCÊ VENCEU! ***** ");
                break;
            }
            if (verificaVelha()) { // Verifica se deu "Velha" após a jogada do jogador
                System.out.println("\n\n ***** DEU VELHA! ***** ");
                break;
            }
            calculaPesos();
            tela.mostraTela();
            if (verificaVencedor()) {
                System.out.println("\n\n ***** VOCÊ PERDEU! ***** ");
                break;
            }
            if (verificaVelha()) { // Verifica se deu "Velha" após a jogada da IA
                System.out.println("\n\n ***** DEU VELHA! ***** ");
                break;
            }
        }
    }

    private void calculaPesos() {
        // Verifica se existe ponto crítico onde o jogador ou a IA pode ganhar
        String acao = verificaPontoCritico();
        
        // Se não houver ponto crítico para bloquear ou vencer, faz uma jogada normal
        if (acao.equals("nenhum")) {
            int melhorLinha = -1;
            int melhorColuna = -1;
            int maxPeso = -1;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tabuleiro.getPecaNaPosicao(i, j) == 2) {
                        int pesoAtual = calculaPesoPosicao(i, j);
                        if (pesoAtual > maxPeso) {
                            maxPeso = pesoAtual;
                            melhorLinha = i;
                            melhorColuna = j;
                        }
                    }
                }
            }

            if (melhorLinha != -1 && melhorColuna != -1) {
                tabuleiro.setPecaNaPosicao(melhorLinha, melhorColuna, 1); // Computador joga 'O'
            }
        }
    }

    private String verificaPontoCritico() {
        // Verifica se existe um ponto crítico nas linhas, colunas ou diagonais
        int[][] tab = tabuleiro.getTabuleiroPreenchido();
        
        // Verificação das linhas
        for (int i = 0; i < 3; i++) {
            int produto = tab[i][0] * tab[i][1] * tab[i][2];
            if (produto == 18) { // Usuário está prestes a vencer (3*3*2)
                bloquearJogada(i, "linha");
                return "bloquear";
            } else if (produto == 2) { // IA pode vencer (1*1*2)
                vencerJogada(i, "linha");
                return "vencer";
            }
        }

        // Verificação das colunas
        for (int i = 0; i < 3; i++) {
            int produto = tab[0][i] * tab[1][i] * tab[2][i];
            if (produto == 18) {
                bloquearJogada(i, "coluna");
                return "bloquear";
            } else if (produto == 2) {
                vencerJogada(i, "coluna");
                return "vencer";
            }
        }

        // Verificação das diagonais
        int produtoDiagonal1 = tab[0][0] * tab[1][1] * tab[2][2];
        int produtoDiagonal2 = tab[0][2] * tab[1][1] * tab[2][0];
        
        if (produtoDiagonal1 == 18) {
            bloquearJogada(0, "diagonal1");
            return "bloquear";
        } else if (produtoDiagonal1 == 2) {
            vencerJogada(0, "diagonal1");
            return "vencer";
        }

        if (produtoDiagonal2 == 18) {
            bloquearJogada(0, "diagonal2");
            return "bloquear";
        } else if (produtoDiagonal2 == 2) {
            vencerJogada(0, "diagonal2");
            return "vencer";
        }

        return "nenhum";
    }

    private void bloquearJogada(int index, String tipo) {
        switch (tipo) {
            case "linha":
                for (int j = 0; j < 3; j++) {
                    if (tabuleiro.getPecaNaPosicao(index, j) == 2) {
                        tabuleiro.setPecaNaPosicao(index, j, 1); // IA joga 'O' para bloquear
                        return;
                    }
                }
                break;

            case "coluna":
                for (int i = 0; i < 3; i++) {
                    if (tabuleiro.getPecaNaPosicao(i, index) == 2) {
                        tabuleiro.setPecaNaPosicao(i, index, 1);
                        return;
                    }
                }
                break;

            case "diagonal1":
                for (int i = 0; i < 3; i++) {
                    if (tabuleiro.getPecaNaPosicao(i, i) == 2) {
                        tabuleiro.setPecaNaPosicao(i, i, 1);
                        return;
                    }
                }
                break;

            case "diagonal2":
                for (int i = 0; i < 3; i++) {
                    if (tabuleiro.getPecaNaPosicao(i, 2 - i) == 2) {
                        tabuleiro.setPecaNaPosicao(i, 2 - i, 1);
                        return;
                    }
                }
                break;
        }
    }

    private void vencerJogada(int index, String tipo) {
        // Mesma lógica do método bloquearJogada, mas prioriza a vitória
        bloquearJogada(index, tipo);
    }

    private int calculaPesoPosicao(int linha, int coluna) {
        int[] valores = { 3, 1 };
        return valores[(int) (Math.random() * 2)]; // Pontuação aleatória entre X (3) e O (1)
    }

    private void pedirNovaJogadaAoUsuario() {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        int linha, coluna;

        do {
            System.out.println("Sua vez! Escolha a linha (1-3): ");
            linha = sc.nextInt();
            linha--;
            System.out.println("Escolha a coluna (1-3): ");
            coluna = sc.nextInt();
            coluna--;
        } while (!validaJogada(linha, coluna));

        tabuleiro.setPecaNaPosicao(linha, coluna, 3); // Usuário joga 'X'
    }

    private boolean validaJogada(int linha, int coluna) {
        if (linha < 0 || linha > 2 || coluna < 0 || coluna > 2 || tabuleiro.getPecaNaPosicao(linha, coluna) != 2) {
            System.out.println("Jogada inválida! Tente novamente.");
            return false;
        }
        return true;
    }

    private boolean verificaVencedor() {
        return (verificaLinhas() || verificaColunas() || verificaDiagonais());
    }

    private boolean verificaLinhas() {
        for (int i = 0; i < 3; i++) {
            if (tabuleiro.getPecaNaPosicao(i, 0) == tabuleiro.getPecaNaPosicao(i, 1) &&
                tabuleiro.getPecaNaPosicao(i, 1) == tabuleiro.getPecaNaPosicao(i, 2) &&
                tabuleiro.getPecaNaPosicao(i, 0) != 2) {
                return true;
            }
        }
        return false;
    }

    private boolean verificaColunas() {
        for (int i = 0; i < 3; i++) {
            if (tabuleiro.getPecaNaPosicao(0, i) == tabuleiro.getPecaNaPosicao(1, i) &&
                tabuleiro.getPecaNaPosicao(1, i) == tabuleiro.getPecaNaPosicao(2, i) &&
                tabuleiro.getPecaNaPosicao(0, i) != 2) {
                return true;
            }
        }
        return false;
    }

    private boolean verificaDiagonais() {
        return (tabuleiro.getPecaNaPosicao(0, 0) == tabuleiro.getPecaNaPosicao(1, 1) &&
                tabuleiro.getPecaNaPosicao(1, 1) == tabuleiro.getPecaNaPosicao(2, 2) &&
                tabuleiro.getPecaNaPosicao(0, 0) != 2) ||
               (tabuleiro.getPecaNaPosicao(0, 2) == tabuleiro.getPecaNaPosicao(1, 1) &&
                tabuleiro.getPecaNaPosicao(1, 1) == tabuleiro.getPecaNaPosicao(2, 0) &&
                tabuleiro.getPecaNaPosicao(0, 2) != 2);
    }

    // Método para verificar se deu "Velha"
    private boolean verificaVelha() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro.getPecaNaPosicao(i, j) == 2) {
                    return false; // Ainda há espaço, o jogo continua
                }
            }
        }
        return true; // Todas as posições preenchidas e sem vencedor
    }
}
