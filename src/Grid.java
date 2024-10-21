
import java.awt.Color;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
public class Grid {
    private Color[][] grid;
    
    Grid(int height, int width){
        int rows = height / Shape.SIZE;
        int cols = width / Shape.SIZE;
        grid = new Color[rows][cols];
    }
    
    public int getHeight() {
        return grid.length;
    }
    
    public int getWidth(){
        return grid[0].length;
    }

    void setCell(int row, int col, Color color) {
        grid[row][col] = color;
    }

    boolean isCell(int row, int col) {
        return grid[row][col] != null;
    }
    
    public Color getColor(int row, int col){
        return grid[row][col];
    }
}
