package com.paulawaite.foodchain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Bitmap character;
    Bitmap arrowControl;
    Bitmap target;
    int score;

    float x = 0, y = 0;
    boolean moveLeft;
    boolean moveRight;
    boolean moveUp;
    boolean moveDown;


    int CHARACTER_MOVE_DISTANCE = 25;


    // from sprite canvas

    GameView gameView;
    Paint drawPaint = new Paint();
    int backgroundColor = Color.BLUE;


    int characterx = 300;
    int charactery = 400;
    //int graphic1xSpeed = 10;
    //int graphic1ySpeed = 10;

    int arrowControlx = 400;
    int arrowControly = 1200;
    //int graphic2xSpeed = 10;
    //int graphic2ySpeed = 10;

    int targetControlx; //set to randomly generated number
    int targetControly = -25;

    // use frames to detect collisions

    Rect characterRect;
    Rect targetRect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        gameView = new GameView(this);
        setContentView(gameView);

        character = BitmapFactory.decodeResource(getResources(), R.drawable.rubythroatedfemale100);
        arrowControl = BitmapFactory.decodeResource(getResources(), R.drawable.fourwayarrow100);
        target =  BitmapFactory.decodeResource(getResources(), R.drawable.beebalm);  // swap hard-coded for other value later

    }

    @Override
    protected void onPause(){
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        gameView.resume();
    }

    // create method to handle touches
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                moveCharacter(event);
            }
            break;
        }
        return true;

    }

    private void moveCharacter(MotionEvent event) {

        x = event.getRawX();
        y = event.getRawY() - 245;  // TODO can i programmatically get the -245 for the ribbon

        switch (GraphicTools.determineImagePortionSelected(arrowControl, arrowControlx, arrowControly, x, y)) {
            case "left": {
                characterx -= CHARACTER_MOVE_DISTANCE;
            }
            break;
            case "right": {
                characterx += CHARACTER_MOVE_DISTANCE;
            }
            break;
            case "up": {
                charactery -= CHARACTER_MOVE_DISTANCE;
            }
            break;
            case "down": {
                charactery += CHARACTER_MOVE_DISTANCE;
            }
            break;
        }
    }

    public class GameView extends SurfaceView implements Runnable {
        boolean threadOK = true;
        Thread ViewThread = null;
        SurfaceHolder surfaceHolder;

        public GameView(Context context) {
            super(context);
            surfaceHolder = this.getHolder();
        }

        @Override
        public void run() {
            while (threadOK) {
                if (!surfaceHolder.getSurface().isValid()) {
                    continue;
                } else {
                    Canvas gameCanvas = surfaceHolder.lockCanvas(); // create a secondary view in background
                    // draw invisible squares around graphic and then see if they intersect
                    characterRect = new Rect(characterx, charactery, character.getWidth() + characterx, character.getHeight() + charactery);
                    // graphic2Rect = new Rect(graphic2x, graphic2y, graphic2.getWidth() + graphic2x, graphic2.getHeight() + graphic2y);

                    onMyDraw(gameCanvas);
                    surfaceHolder.unlockCanvasAndPost(gameCanvas);  //post to primary canvas
                }
            }
        }

        protected void onMyDraw(Canvas canvas) {
            drawPaint.setAlpha(255);
            drawPaint.setTextSize(70);

            if (characterx < 0) {
                characterx += CHARACTER_MOVE_DISTANCE;
            }
            //canvas size reported to be 1080 x 1536, actual seems to be 875 and 1275

            //if (characterx > canvas.getWidth()) {
            if (characterx > 875) {
                characterx -= CHARACTER_MOVE_DISTANCE;
            }
            if (charactery < 0) {
                 charactery += CHARACTER_MOVE_DISTANCE;
            }
           // if (charactery > canvas.getHeight()) {
            if (charactery > 1275) {
                charactery -= CHARACTER_MOVE_DISTANCE;
            }
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),
                    R.drawable.blueskybackgroundfaded), 0, 0, null);
            canvas.drawBitmap(character, characterx, charactery, drawPaint );
            canvas.drawBitmap(arrowControl, arrowControlx, arrowControly, drawPaint );
            canvas.drawText("Score: " + score, 16, canvas.getHeight() - 20, drawPaint);




        }


        // boilerplate pause code from lecture
        public void pause() {
            threadOK = false;
            while (true) {
                try {
                    ViewThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
            ViewThread = null;
        }

        // boilerplate pause code from lecture
        public void resume() {
            threadOK = true;
            ViewThread = new Thread(this);
            ViewThread.start();

        }
    }
}
