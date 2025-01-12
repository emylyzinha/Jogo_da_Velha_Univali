public class JogoDaVelha {
    public static void main(String[] args) {
        Model tabuleiroPreenchido = new Model();
        View tela = new View();
        Control jogo = new Control(tela, tabuleiroPreenchido);
        
        jogo.rodaJogo();
    }
}
