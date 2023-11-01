package com.prakhar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class MainGame extends JFrame implements ActionListener{
    JButton solve = new JButton();
    JButton reset = new JButton();
    JButton check = new JButton();
    JLabel Failed = new JLabel();
    JLabel congrats = new JLabel();
    JLabel allcell = new JLabel();
    int fields_x,fields_y;
    int x = 35;
    int y= 35;
    static int matrixP[][]=new int[9][9];
    static int matrix[][] = new int [9][9];
    JTextField[][] fields = new JTextField[9][9]; {
        for (fields_y=0; fields_y < 9; fields_y++) {
            x=35;
            for (fields_x =0; fields_x < 9; fields_x++) {

                fields[fields_x][fields_y] = new JTextField();
                fields[fields_x][fields_y].setBounds(x, y, 32, 32);
                this.getContentPane().add(fields[fields_x][fields_y]);
                fields[fields_x][fields_y].setHorizontalAlignment(JTextField.CENTER);
                x = x+35;
                if(fields_x % 3 == 2)
                    x += 5;
            }
            y = y+35;
            if(fields_y % 3 == 2)
                y += 5;
        }
    }
    MainGame()
    {
        solve.setBounds(55,390,85, 30);
        reset.setBounds(145,390,85, 30);
        check.setBounds(235,390,85, 30);
        Failed.setText("Invalid Sudoku!!");
        congrats.setText("Congratulations! Champion");
        allcell.setText("All Cells are not Filled");
        Failed.setBounds(150,330,100,80);
        congrats.setBounds(120,330,180,80);
        allcell.setBounds(120,330,180,80);
        allcell.setVisible(false);
        Failed.setVisible(false);
        congrats.setVisible(false);
        Failed.setForeground(Color.RED.darker());
        congrats.setForeground(Color.GREEN.darker());
        allcell.setForeground(Color.PINK.darker());
        solve.setText("Solve");
        reset.setText("Reset");
        check.setText("Check");
        solve.addActionListener(this);
        reset.addActionListener(this);
        check.addActionListener(this);
        this.setSize(400,480);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setTitle("Sudoku Solver");
        this.setVisible(true);
        this.add(Failed);
        this.add(congrats);
        this.add(allcell);
        this.add(solve);
        this.add(reset);
        this.add(check);
        ImageIcon image=new ImageIcon("./logo.jpg");
        this.setIconImage(image.getImage());
        for (fields_y =0; fields_y < 9; fields_y++) {
            for (fields_x =0; fields_x < 9; fields_x++) {
                fields[fields_x][fields_y].setText("");
            }
        }
    }
    @Override
        public void actionPerformed(ActionEvent e) {
            char ch;
            for (fields_y =0; fields_y < 9; fields_y++) {
                for (fields_x =0; fields_x < 9; fields_x++) {
                    if(fields[fields_x][fields_y].getText().equalsIgnoreCase(""))
                        matrixP[fields_x][fields_y] = 0;
                    else {
                        ch = fields[fields_x][fields_y].getText().charAt(0);
                        if (ch == '.')
                            matrixP[fields_x][fields_y] = 0;
                        else
                            matrixP[fields_x][fields_y] = ch - '0';
                    }
                }
            }
            if(e.getSource()== solve)
            {
                if(validSudoku(matrixP)) {
                    if (solve(matrixP)) {
                        for (fields_y = 0; fields_y < 9; fields_y++) {
                            for (fields_x = 0; fields_x < 9; fields_x++) {
                                fields[fields_x][fields_y].setText("" + matrixP[fields_x][fields_y]);
                            }
                        }
                    }
                }
                else
                {
                    Failed.setVisible(true);
                    congrats.setVisible(false);
                    allcell.setVisible(false);
                }
            }
            if(e.getSource()==reset)
            {
                for (fields_y =0; fields_y < 9; fields_y++) {
                    for (fields_x =0; fields_x < 9; fields_x++) {
                        fields[fields_x][fields_y].setText("");
                    }
                }
                allcell.setVisible(false);
                congrats.setVisible(false);
                Failed.setVisible(false);
            }
            if(e.getSource() == check)
            {
                int c;
                int zeroCount = 0;
                for (fields_y =0; fields_y < 9; fields_y++) {
                    for (fields_x =0; fields_x < 9; fields_x++) {
                            if(fields[fields_x][fields_y].getText().equalsIgnoreCase(""))
                            {
                                zeroCount++;
                                continue;
                            }
                            c = Integer.parseInt(fields[fields_x][fields_y].getText());
                            matrix[fields_x][fields_y] = c;
                            if(c == 0)
                                zeroCount++;
                        }
                    }
                    if(zeroCount != 0)
                    {
                        allcell.setVisible(true);
                        Failed.setVisible(false);
                        congrats.setVisible(false);
                    }
                    else if(!validSudoku(matrix))
                    {
                        Failed.setVisible(true);
                        congrats.setVisible(false);
                        allcell.setVisible(false);
                    }
                    else if(validSudoku(matrix))
                    {
                        Failed.setVisible(false);
                        congrats.setVisible(true);
                        allcell.setVisible(false);
                    }
            }
        }

    public static boolean isValid(int[][] board, int row, int col, int c) {
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == c)
                return false;

            if (board[row][i] == c)
                return false;

            if (board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == c)
                return false;
        }
        return true;
    }
    public static boolean solve(int[][] board)
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (board[i][j] == 0)
                {
                    for (int c = 1; c <= 9; c++)
                    {
                        if (isValid(board, i, j, c))
                        {
                            board[i][j] = c;
                            if (solve(board))
                                return true;
                            else
                                board[i][j] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean validSudoku(int[][] board)
    {
        for(int i = 0;i<9;i++)
            for(int j = 0;j<9;j++)
                if(board[i][j] > 9)
                    return false;
        Set seen = new HashSet();
        for (int i=0; i<9; ++i) {
            for (int j=0; j<9; ++j) {
                if (board[i][j] != 0) {
                    String b = "(" + (char)board[i][j] + ")";
                    if (!seen.add(b + i) || !seen.add(j + b) || !seen.add(i/3 + b + j/3))
                        return false;
                }
            }
        }
        return true;
    }
}