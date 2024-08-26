import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class Game extends JFrame implements ActionListener {

    JLabel text;
    JPanel header;
    JPanel buttonPanel;
    JButton[] buttons;
    char player = 'X';
    int drawPlaceholder = 0;
    String title = "Tic Tac Toe";

    // 2D Array com as condições de vitória
    int[][] winningCombinations = {
            {0, 1, 2}, // linha 1
            {3, 4, 5}, // linha 2
            {6, 7, 8}, // linha 3
            {0, 3, 6}, // coluna 1
            {1, 4, 7}, // coluna 2
            {2, 5, 8}, // coluna 3
            {0, 4, 8}, // diagonal 1
            {2, 4, 6}  // diagonal 2
    };

    public Game(){

        //Label
        this.text = new JLabel();
        text.setForeground(Color.white);
        text.setText(title);
        text.setFont(new Font("MV Boli", Font.PLAIN, 50));

        //Header
        this.header = new JPanel();
        header.setBounds(0, 0, 850,100);
        header.setBackground(Color.black);
        header.add(text);

        //Button Panel and Buttons
        this.buttonPanel = new JPanel();
        this.buttons = new JButton[9];

        buttonPanel.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++){
            buttons[i] = new JButton();
            buttonPanel.add(buttons[i]);
            buttons[i].setText(" ");
            buttons[i].setFocusable(false);
            buttons[i].setFont(new Font("MV Boli", Font.PLAIN, 80));

            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton clickedButton = (JButton) e.getSource();
                    makeAMove(clickedButton);
                }
            });
        }
    }

    public void frame(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(850,850);
        this.setLayout(new BorderLayout());
        this.setTitle("Tic Tac Toe Game");
        this.setVisible(true);
        this.setResizable(false);
        this.add(header, BorderLayout.NORTH);
        this.add(buttonPanel);
    }

    // Jogador faz uma jogada
    public void makeAMove(JButton clickedButton){
        // Antes de realizar a jogada, verifica se ela é valida
        if (!moveIsValid(clickedButton)){
            System.out.println("Movimento inválido");
        } else{
            clickedButton.setText(String.valueOf(player)); // Coloca o simbolo do jogador no botão que ele clicou

            // Verifica se o jogador ganhou a partida
            if (checkWinCondition()){
                text.setText(player + " Venceu");
                disableAllButtons();

                if (playAgain() == 0){
                    clearBoard();
                    enableAllButtons();

                    text.setText(title);
                    return;
                }

                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                return;
            }

            // Verifica se a partida empatou
            if (checkDraw(buttons)) {
                text.setText("Empate");
                disableAllButtons();

                if (playAgain() == 0){
                    clearBoard();
                    enableAllButtons();

                    text.setText(title);
                    return;
                }

                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                return;
            }

            changePlayer(); // Se o jogador não ganhou a partida, muda a vez do jogador
            text.setText("Vez do Jogador " + player);
        }
    }

    // Checa se a jogada é valida
    public boolean moveIsValid(JButton clickedButton){
        return Objects.equals(clickedButton.getText(), " ");
    }

    // Muda a vez do jogador
    public void changePlayer(){
        if (player == 'X'){
            player = 'O';
        } else {
            player = 'X';
        }
    }

    // Verifica se algum jogador ganhou o jogo
    public boolean checkWinCondition(){
        for (int[] combination : winningCombinations) {
            if (Objects.equals(buttons[combination[0]].getText(), String.valueOf(player)) &&
                    Objects.equals(buttons[combination[1]].getText(), String.valueOf(player)) &&
                    Objects.equals(buttons[combination[2]].getText(), String.valueOf(player))) {
                return true; // Retorna verdadeiro se uma condição de vitória for encontrada
            }
        }
        return false;
    }

    public boolean checkDraw(JButton[] buttons){
        for (JButton button : buttons) {
            if (button.getText().trim().isEmpty()) {
                return false;
            }
        }

        return true;
    }

    // Pergunta se o jogador quer jogar novamente
    public int playAgain(){
        JOptionPane pane = new JOptionPane();
        int answer = JOptionPane.showConfirmDialog(null, "Do you want to continue?", "Question", JOptionPane.YES_NO_OPTION);
        return answer;
    }

    // Limpa o tabuleiro
    public void clearBoard(){
        for (int i=0; i < buttons.length; i++){
            buttons[i].setText(" ");
        }
    }

    // Habilita os botões
    public void enableAllButtons(){
        for (JButton button : buttons){
            button.setEnabled(true);
        }
    }

    // Desabilita os botões
    public void disableAllButtons(){
        for (JButton button : buttons){
            button.setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}

