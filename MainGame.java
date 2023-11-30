package com.prakhar;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class MainGame extends JFrame implements ActionListener{
    static int ar[][] = new int[9][9];
    JButton solve = new JButton();
    JButton reset = new JButton();
    JButton generate = new JButton();
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
        solve.setBounds(85,425,100, 30);
        reset.setBounds(200,385,100, 30);
        check.setBounds(200,425,100, 30);
        generate.setBounds(85,385,100,30);
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
        generate.setText("Generate");
        solve.addActionListener(this);
        reset.addActionListener(this);
        check.addActionListener(this);
        generate.addActionListener(this);
        this.setSize(400,500);
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
        this.add(generate);
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
        int ch;
            for (fields_y =0; fields_y < 9; fields_y++) {
                for (fields_x =0; fields_x < 9; fields_x++) {
                    if(fields[fields_x][fields_y].getText().equalsIgnoreCase("") ||
                            fields[fields_x][fields_y].getText().equalsIgnoreCase("."))
                        matrixP[fields_x][fields_y] = 0;
                    else {
                        ch = Integer.parseInt(fields[fields_x][fields_y].getText());
                            matrixP[fields_x][fields_y] = ch;
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
                    congrats.setVisible(false);
                    allcell.setVisible(false);
                    Failed.setVisible(false);
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
            if(e.getSource() == generate)
            {
                generateSudoku();
                for (fields_y =0; fields_y < 9; fields_y++) {
                    for (fields_x =0; fields_x < 9; fields_x++) {
                        if(ar[fields_x][fields_y] == 0)
                            fields[fields_x][fields_y].setText("");
                        else
                            fields[fields_x][fields_y].setText(""+ar[fields_x][fields_y]);
                    }
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
    public static void generateSudoku(){
        int N = 3;
        for(int i = 0;i<N*N;i++)
        {
            for(int j = 0;j<N*N;j++)
                ar[i][j] = 1;
        }
        int k=0;
        int fillCount =1;
        int subGrid=1;
        for (int i=0;i<N*N;i++) {
            if (k == N) {
                k = 1;
                subGrid++;
                fillCount = subGrid;
            } else {
                k++;
                if (i != 0)
                    fillCount = fillCount + N;
            }
            for (int j = 0; j < N * N; j++) {
                if (fillCount == N * N) {
                    ar[i][j] = fillCount;
                    fillCount = 1;
                } else {
                    ar[i][j] = fillCount++;
                }
            }
        }
        for(int i = 0;i < 9;i++)
        {
            for(int j = 0;j<9;j++)
            {
                Random rand = new Random();
                int a = rand.nextInt(100);
                if(a % 2 == 0)
                    ar[i][j] = 0;
                System.out.print(ar[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        MainGame ob = new MainGame();
        generateSudoku();
    }
}
