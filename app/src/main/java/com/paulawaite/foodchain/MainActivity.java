package com.paulawaite.foodchain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Handler handler;
    Context context;
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
    int TARGET_MOVE_DISTANCE = 15;


    // from sprite canvas

    GameView gameView;
    Paint drawPaint = new Paint();
    int backgroundColor = Color.BLUE;


    int characterx = 400;
    int charactery = 1000;
    //int graphic1xSpeed = 10;
    //int graphic1ySpeed = 10;

    int arrowControlx = 400;
    int arrowControly = 1200;


    int targetx;
    int targety = -25; // start target off screen so it scrolls down the screen from off screen

    // use frames to detect collisions

    Rect characterRect;
    Rect targetRect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        context = this;

        gameView = new GameView(this);

        handler = new Handler();

        setContentView(gameView);

        character = BitmapFactory.decodeResource(getResources(), R.drawable.rubythroatedfemale);
        arrowControl = BitmapFactory.decodeResource(getResources(), R.drawable.fourwayarrow);

        int targetId = getResources().getIdentifier("beebalm", "drawable", getPackageName());
        target =  BitmapFactory.decodeResource(getResources(), targetId);
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
        boolean showTarget = true;
        //canvas size reported to be 1080 x 1536, actual seems to be 875 and 1275
        int canvasX = 875;
        int canvasY = 1275;


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

                    //adjust for invisible edges in graphic
                    int characterXadjusted = (int)(characterx * 1.20);
                    int characterYadjusted = (int)(charactery * 1.20);

                    characterRect = new Rect(characterXadjusted, characterYadjusted, (int)(character.getWidth() *.80) +
                            characterXadjusted, (int)(character.getHeight() * .80) + characterYadjusted);
                    targetRect = new Rect(targetx, targety, target.getWidth() + targetx, target.getHeight() + targety);

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

            if (characterx > canvasX) {
                characterx -= CHARACTER_MOVE_DISTANCE;
            }

            if (charactery < 0) {
                charactery += CHARACTER_MOVE_DISTANCE;
            }

            if (charactery > canvasY) {
                charactery -= CHARACTER_MOVE_DISTANCE;
            }

            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),
                    R.drawable.blueskybackgroundfaded), 0, 0, null);
            canvas.drawBitmap(character, characterx, charactery, drawPaint);
            canvas.drawBitmap(arrowControl, arrowControlx, arrowControly, drawPaint);
            if (showTarget) {
                canvas.drawBitmap(target, targetx, targety, drawPaint);

                if (Rect.intersects(characterRect, targetRect)) {

                    score += 10;
                    showTarget = false;
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context, "+10", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                createNewTarget();

            }

            canvas.drawText("Score: " + score, 16, canvas.getHeight() - 20, drawPaint);

            targety += TARGET_MOVE_DISTANCE;

        }

        private void createNewTarget() {
            Random random = new Random();
            int leftEdge = 0;
            int rightEdge = canvasX;
            targetx = (random.nextInt(rightEdge + 1));
            targety = -100;
            showTarget = true;
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
