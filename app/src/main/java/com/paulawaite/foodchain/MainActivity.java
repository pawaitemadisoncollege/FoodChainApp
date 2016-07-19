package com.paulawaite.foodchain;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

   // ImageView character;
   // ImageView arrowControl;
    float x=0, y=0;
    boolean moveLeft;
    boolean moveRight;
    boolean moveUp;
    boolean moveDown;
    int screenX;
    int screenY;


    // from sprite canvas

    GameView gameView;
    Paint drawPaint = new Paint();
    int backgroundColor = Color.RED;
    Bitmap character;
    Bitmap arrowControl;

    int graphic1x = 100;
    int graphic1y = 100;
    int graphic1xSpeed = 10;
    int graphic1ySpeed = 10;

    int graphic2x = 200;
    int graphic2y = 500;
    int graphic2xSpeed = 10;
    int graphic2ySpeed = 10;

    // use frames to detect collisions

    Rect graphic1Rect;
    Rect graphic2Rect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        character = (ImageView) findViewById(R.id.character);
        arrowControl = (ImageView) findViewById(R.id.arrow);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenX = size.x; // TODO size seems too wide - goes off right hand side
        screenY = size.y; // Y also seems to large - goes off bottom

    }
    // create method to handle touches
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN :
            {
                moveCharacter(event);
            }
            break;

        }
        return true;

    }

    private void moveCharacter(MotionEvent event) {

        x = event.getRawX();
        y = event.getRawY() - 245;  // TODO can i programmatically get the -245 for the ribbon? OR if we use canvas, ribbon goes away!

        switch (GraphicTools.determineImagePortionSelected(arrowControl, x, y)) {
            case "left" :
            {
                float newX = character.getX() - 20;
                if (newX > 0) {
                    character.setX(newX);
                }
            }
            break;
            case "right" :
            {
                float newX = character.getX() + 20;
                if (newX < screenX) {
                    character.setX(newX);
                }
            }
            break;
            case "up" :
            {
                float newY = character.getY() - 20;
                if (newY > 0) {
                    character.setY(newY);
                }
            }
            break;
            case "down" :
            {
                float newY = character.getY() + 20;
                if (newY < screenY) {
                    character.setY(newY);
                }
            }
            break;
        }
    }
}
