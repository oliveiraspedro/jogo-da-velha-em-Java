import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Game extends JFrame implements ActionListener {

    JLabel text;
    JPanel header;
    JPanel buttonPanel;
    JButton[] buttons;
    char player = 'X';
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
        text.setText("Jogo da velha");
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

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(850,850);
        this.setLayout(new BorderLayout());
        this.setTitle("Tic Tac Toe game");
        this.setVisible(true);
        this.setResizable(false);
        this.add(header, BorderLayout.NORTH);
        this.add(buttonPanel);
    }

    public void makeAMove(JButton clickedButton){
        if (!moveIsValid(clickedButton)){
            System.out.println("Movimento inválido");
        }
        clickedButton.setText(String.valueOf(player));

        if (checkWinCondition()){
            text.setText(player + " Venceu");
            disableAllButtons();
            return;
        }

        changePlayer();
        text.setText("Vez do Jogador " + player);
    }

    public boolean moveIsValid(JButton clickedButton){
        if (Objects.equals(clickedButton.getText(), "X") || Objects.equals(clickedButton.getText(), "O")){
            System.out.println("Movimento inválido");
            return false;
        } else {
            return true;
        }
    }

    public void changePlayer(){
        if (player == 'X'){
            player = 'O';
        } else {
            player = 'X';
        }
    }

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

    public void disableAllButtons(){
        for (JButton button : buttons){
            button.setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
