/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.Set;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class GameArea extends JPanel {
    
    private final Color[] availableColors = new Color[]{
            Color.red, Color.blue, Color.green
    };
    private final Random rnd = new Random();
    
    private Grid grid;
    private Shape shape;
    
    GameArea(JPanel placeholder){
//      placeholder.setVisible(false);
        setBounds(placeholder.getBounds());
        setBorder(placeholder.getBorder());
        setBackground(placeholder.getBackground());
        setVisible(true);
        
        grid = new Grid(getBounds().height, getBounds().width);
    }
    
    public void spawnShape() {
        shape = createShape();
        repaint();
    }
    
    public int freezeShape(){
        freezeShape(shape, grid);
        return clearLines(grid);
    }
    
    public boolean shapeOutOfGrid(){
        return shape.getY() < 0;
    }
    
    public boolean moveShapeDown(){
        if (hitBottom()) {
            freezeShape(shape, grid);
            return true;
        }
        
        shape.moveDown();
        repaint();
        
        return false;
    }
    
    public void dropShapeDown(){
        while (!hitBottom()){
            shape.moveDown();
        }
        repaint();
    }
    
    public void moveShapeLeft(){
        if(!hitLeft()){
            shape.moveLeft();
            repaint();
        }
    }
    
    public void moveShapeRight(){
        if(!hitRight()){
            shape.moveRight();
            repaint();
        }
    }
    
    public void rotateShape(){
        shape.rotate();
        
        if (shape.getLeft() < 0) {
            shape.setX(0);
        }
        
        if (shape.getRight() > grid.getWidth()) {
            shape.setX(grid.getWidth() - shape.getWidth());
        }
        
        if(shape.getBottom() > grid.getHeight()) {
            shape.setY(grid.getHeight() - shape.getHeight());
        }
        
        repaint();
    }
    
    private void clearLine(int row, Grid grid){
        for (int col = 0; col < grid.getWidth(); ++col){
            grid.setCell(row, col, null);
        }
    }
    
    private void shiftLinesAbove(int bottomRow, Grid grid){
        for (int row = bottomRow; row > 0; --row){
            for (int col = 0; col < grid.getWidth(); ++col){
            grid.setCell(row, col, grid.getColor(row - 1, col));
                
            }
        }
    }
    
    private int clearLines(Grid grid){
        int clearedLines = 0;
        for (int row = grid.getHeight() - 1; row >= 0; --row){
            boolean fullLine = true;
            for (int col = 0; col < grid.getWidth(); ++col){
                if (!grid.isCell(row, col)){
                    fullLine = false;
                    break;
                }
            }
            if (fullLine){
                clearedLines++;
                clearLine(row, grid);
                shiftLinesAbove(row, grid);
                clearLine(0, grid);
                row++;
            }
        }
        return clearedLines;
    }
    
    private boolean hitBottom(){
        if(shape.getBottom() == grid.getHeight()){
            return true;
        }
        
        int rows = shape.getHeight();
        int cols = shape.getWidth();
        
        for(int col = 0; col < cols; ++col){
            for(int row = rows - 1; row >= 0; --row){
                if (shape.isCell(row, col)){
                    int x = shape.getX() + col;
                    int y = shape.getY() + row + 1;
                    if(y < 0){
                        break;
                    }
                    if(grid.isCell(y, x)){
                        return true;
                    }
                    break;
                }
            }
        }
        
        return false;
    }
    
    private boolean hitLeft(){
        if(shape.getLeft() == 0){
            return true;
        }
        
        int rows = shape.getHeight();
        int cols = shape.getWidth();
        
        for(int row = 0; row < rows; ++row){
            for(int col = 0; col < cols; ++col){
                if (shape.isCell(row, col)){
                    int x = shape.getX() + col - 1;
                    int y = shape.getY() + row;
                    if(y < 0){
                        break;
                    }
                    if(grid.isCell(y, x)){
                        return true;
                    }
                    break;
                }
            }
        }
        
        return false;
    }
    
    private boolean hitRight(){
        if(shape.getRight() == grid.getWidth()){
            return true;
        }
        
        int rows = shape.getHeight();
        int cols = shape.getWidth();
        
        for(int row = 0; row < rows; ++row){
            for(int col = cols - 1; col >= 0; --col){
                if (shape.isCell(row, col)){
                    int x = shape.getX() + col + 1;
                    int y = shape.getY() + row;
                    if(y < 0){
                        break;
                    }
                    if(grid.isCell(y, x)){
                        return true;
                    }
                    break;
                }
            }
        }
        
        return false;
    }
    
    private void freezeShape(Shape shape, Grid grid){
        for (int row = 0; row < shape.getHeight(); ++row){
            for (int col = 0; col < shape.getWidth(); ++col){
                if (shape.isCell(row, col)){
                    grid.setCell(row + shape.getY(), col + shape.getX(), shape.getColor());
                }
            }
        }
    }
    
    private void drawShape(Graphics g, Shape shape){
        for (int row = 0; row < shape.getHeight(); ++row){
            for (int col = 0; col < shape.getWidth(); ++col){
                if (shape.isCell(row, col)){
                    int x = (shape.getX() + col) * Shape.SIZE;
                    int y = (shape.getY() + row) * Shape.SIZE;
                    drawCell(g, x, y, shape.getColor());
                }
            }
        }
    }
    
    private void drawGrid(Graphics g, Grid grid){
        for (int row = 0; row < grid.getHeight(); ++row){
            for (int col = 0; col < grid.getWidth(); ++col){
                if (grid.isCell(row, col)){
                    int x = col * Shape.SIZE;
                    int y = row * Shape.SIZE;
                    drawCell(g, x, y, grid.getColor(row, col));
                }
            }
        }
    }
    
    private void drawCell(Graphics g, int x, int y, Color color){
        g.setColor(color);
        g.fillRect(x, y, Shape.SIZE, Shape.SIZE);
        g.setColor(color.black);
        g.drawRect(x, y, Shape.SIZE, Shape.SIZE);
    }
        
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        drawGrid(g, grid);
        drawShape(g, shape);
    }

    private Shape createShape() {
        int colorChoice = rnd.nextInt(availableColors.length);
        Color color = availableColors[colorChoice];
        int shapeChoice = rnd.nextInt(7);
        switch(shapeChoice){
            case 0:
                return new Shape(new boolean[][]{
                    {true, false},
                    {true, false},
                    {true, true}
                }, grid.getWidth(), color);
            case 1:
                return new Shape(new boolean[][]{
                    {false, true},
                    {false, true},
                    {true, true}
                }, grid.getWidth(), color);
            case 2:
                return new Shape(new boolean[][]{
                    {true, true},
                    {true, true}
                }, grid.getWidth(), color);
            case 3:
                return new Shape(new boolean[][]{
                    {true, true, true},
                    {false, true, false}
                }, grid.getWidth(), color);
            case 4:
                return new Shape(new boolean[][]{
                    {true, true, false},
                    {false, true, true}
                }, grid.getWidth(), color);
            case 5:
                return new Shape(new boolean[][]{
                    {false, true, true},
                    {true, true, false}
                }, grid.getWidth(), color);
            case 6:
                return new Shape(new boolean[][]{
                    {true, true, true, true},
                }, grid.getWidth(), color);
            default: throw new IllegalArgumentException("Choice invalid");
        }
    }
}