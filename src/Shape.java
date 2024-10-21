
import java.awt.Color;
import java.util.Random;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
public class Shape {
    public static int SIZE = 20;
    
    private static int ROTATIONS = 4;
    private boolean[][][] availableShapes;
    
    private boolean[][] shape;
    private int rotation = 0;
    private Color color;
    
    private int x = 0;
    private int y = 0;
    
    Shape(boolean[][] shape, int gridWidth, Color color) {
        this.shape = shape;
        this.color = color;
        
        availableShapes = new boolean[ROTATIONS][][];
        availableShapes[0] = shape;
        
        for (int i = 1; i<availableShapes.length; i++){
            int rows = availableShapes[i - 1][0].length;
            int cols = availableShapes[i - 1].length;
            availableShapes[i] = new boolean[rows][cols];
            for (int row = 0; row < rows; ++row){
                for (int col = 0; col < cols; ++col){
                    availableShapes[i][row][col] = availableShapes[i - 1][cols - col - 1][row];
                }
            }
        }
        
        Random rnd = new Random();
        
        x = rnd.nextInt(gridWidth - getWidth());
        y = -getHeight();
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public Color getColor() {
        return color;
    }
    
    public int getHeight(){
        return shape.length;
    }
    
    public int getWidth(){
        return shape[0].length;
    }
    
    public boolean isCell(int row, int col) {
        return shape[row][col];
    }
    
    public void moveDown(){
        y++;
    }
    
    public void moveLeft(){
        x--;
    }
    
    public void moveRight(){
        x++;
    }
    
    public void rotate(){
        shape = availableShapes[++rotation % ROTATIONS];
    }
    
    public int getBottom() {
        return y + getHeight();
    }
    
    public int getLeft() {
        return x;
    }
    
    public int getRight() {
        return x + getWidth();
    }

    void setX(int i) {
        x = i;
    }
    
    void setY(int i) {
        y = i;
    }
}
