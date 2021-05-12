package com.song.sunset.design.structural.facade;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/12 8:46
 */
public class ShapeFacade {
    private Shape circle;
    private Shape triangle;
    private Shape square;

    public ShapeFacade() {
        circle = new Circle();
        triangle = new Triangle();
        square = new Square();
    }

    public void drawCircle(){
        circle.draw();
    }
    public void drawTriangle(){
        triangle.draw();
    }
    public void drawSquare(){
        square.draw();
    }
}
