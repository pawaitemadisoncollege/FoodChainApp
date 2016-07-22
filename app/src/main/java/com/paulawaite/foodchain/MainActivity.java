package com.paulawaite.foodchain;

import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.paulawaite.foodchain.entity.Animal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    GlobalVariables globals;

    SoundPool soundPool;
    int characterSound = -11; // no sound at current time

    Handler handler;
    Context context;
    Bitmap characterImage;
    Bitmap arrowControlImage;
    Bitmap targetImage;
    int targetId;
    int score;

    float x = 0, y = 0;
    boolean moveLeft;
    boolean moveRight;
    boolean moveUp;
    boolean moveDown;


    int characterMoveDistance = 25;
    int targetMoveDistance = 40;


    // from sprite canvas

    GameView gameView;
    Paint drawPaint = new Paint();
    int backgroundColor = Color.BLUE;


    int characterx = 400;
    int charactery = 900;
    //int graphic1xSpeed = 10;
    //int graphic1ySpeed = 10;

    int arrowControlx = 400;
    int arrowControly = 1200;


    int targetx;
    int targety = -25; // start target off screen so it scrolls down the screen from off screen

    // use frames to detect collisions

    Rect characterRect;
    Rect targetRect;

    Animal userCharacter;
    ArrayList<Animal> targets;

    Animal target;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create data for application - this would likely use a db at some point
        globals = GlobalVariables.getInstance();
        globals.setEventData(new DataSetUp());

        setContentView(R.layout.activity_main);

        context = this;

        gameView = new GameView(this);

        handler = new Handler();

        setContentView(gameView);

        userCharacter = globals.getEventData().getUserCharacter();
        targets = globals.getEventData().getAnimals();

        int characterId = getResources().getIdentifier(userCharacter.getImage(), "drawable", getPackageName());
        characterImage = BitmapFactory.decodeResource(getResources(), characterId);

        arrowControlImage = BitmapFactory.decodeResource(getResources(), R.drawable.fourwayarrow);

        setUpCharacterSound(userCharacter.getSound());

        gameView.createNewTarget();

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

        switch (GraphicTools.determineImagePortionSelected(arrowControlImage, arrowControlx, arrowControly, x, y)) {
            case "left": {
                characterx -= characterMoveDistance;
            }
            break;
            case "right": {
                characterx += characterMoveDistance;
            }
            break;
            case "up": {
                charactery -= characterMoveDistance;
            }
            break;
            case "down": {
                charactery += characterMoveDistance;
            }
            break;
        }
    }

        public void setUpCharacterSound(String file){
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0); // how many sounds in the pool, source, quality
            AssetManager assetManager = getAssets();

            try {
                AssetFileDescriptor descriptor = assetManager.openFd(file);
                characterSound = soundPool.load(descriptor, 1); // priority
            } catch (IOException ioe){
                ioe.printStackTrace();
            }
        }



    public class GameView extends SurfaceView implements Runnable {

        boolean gameOver = false;
        boolean threadOK = true;
        Thread ViewThread = null;
        SurfaceHolder surfaceHolder;
        boolean showTarget = true;
        boolean showCharacter = true;
        //canvas size reported to be 1080 x 1536, actual seems to be 875 and 1275
        int canvasX = 875;
        int canvasY = 1275;


        public GameView(Context context) {
            super(context);
            surfaceHolder = this.getHolder();
        }

        @Override
        public void run() {
            while (threadOK && !gameOver) {
                if (!surfaceHolder.getSurface().isValid()) {
                    continue;
                } else {
                    Canvas gameCanvas = surfaceHolder.lockCanvas(); // create a secondary view in background
                    // draw invisible squares around graphic and then see if they intersect

                    //adjust for invisible edges in graphic
                    int characterXadjusted = (int)(characterx * 1.20);
                    int characterYadjusted = (int)(charactery * 1.20);

                    characterRect = new Rect(characterXadjusted, characterYadjusted, (int)(characterImage.getWidth() *.80) +
                            characterx, (int)(characterImage.getHeight() * .80) + charactery);
                    targetRect = new Rect(targetx, targety, targetImage.getWidth() + targetx, targetImage.getHeight() + targety);

                    onMyDraw(gameCanvas);
                    surfaceHolder.unlockCanvasAndPost(gameCanvas);  //post to primary canvas
                }
            }
        }

        protected void onMyDraw(Canvas canvas) {
            drawPaint.setAlpha(255);
            drawPaint.setTextSize(70);


            if (characterx < 0) {
                characterx += characterMoveDistance;
            }

            if (characterx > canvasX) {
                characterx -= characterMoveDistance;
            }

            if (charactery < 0) {
                charactery += characterMoveDistance;
            }

            if (charactery > canvasY) {
                charactery -= characterMoveDistance;
            }

            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),
                    R.drawable.blueskybackgroundfaded), 0, 0, null);

            if (!gameOver){
                canvas.drawBitmap(characterImage, characterx, charactery, drawPaint);
            }

            canvas.drawBitmap(arrowControlImage, arrowControlx, arrowControly, drawPaint);

            if (showTarget) {
                canvas.drawBitmap(targetImage, targetx, targety, drawPaint);

                if (Rect.intersects(characterRect, targetRect)) {

                    if (userCharacter.isPrey(target.getId())) {

                        score += 10;
                        showTarget = false;
                        soundPool.play(characterSound, 1, 1, 0, 0, 1);
                        handler.post(new Runnable() {
                            public void run() {
                                Toast.makeText(context, "+10", Toast.LENGTH_SHORT).show();
                            }
                        });
                        //speed up the targets if the user has > 50
                        if (score > 50) {
                            targetMoveDistance += 10;
                        }
                    } else if (userCharacter.isPredator(target.getId())) {
                        gameOver = true;
                        handler.post(new Runnable() {
                            public void run() {
                                Toast.makeText(context, "You were eaten!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        score -= 10;
                        showTarget = false;
                        handler.post(new Runnable() {
                            public void run() {
                                Toast.makeText(context, "You can't eat that! -10", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                else if (targety > canvasY) {
                    createNewTarget();
                }
            } else {
                createNewTarget();

            }

            canvas.drawText("Score: " + score, 16, canvas.getHeight() - 20, drawPaint);




            targety += targetMoveDistance;

            if (gameOver) {
                // canvas.drawText("GAME OVER", 300, canvas.getHeight()/2, drawPaint);

                handler.post(new Runnable() {
                    public void run() {
                        showQuitAlert();
                    }
                });

            }

        }

        private void createNewTarget() {

            // randomly choose an animal to be the target
            Random random = new Random();
            int low = 0;
            int high = targets.size() - 1;
            int randomTarget = (random.nextInt(high + 1));

            target = targets.get(randomTarget);

            int targetIdentifier = getResources().getIdentifier(target.getImage(), "drawable", getPackageName());
            Log.w("moving", "Image is: " + target.getImage());
            targetImage =  BitmapFactory.decodeResource(getResources(), targetIdentifier);



            // place image randomly along X axis at top
            random = new Random();
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


    public void showQuitAlert() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.gameover);
        final TextView txt = (TextView) dialog.findViewById(R.id.gameoverscoreText);
        txt.setText("Score: " + score);
        Button playAgainButton = (Button) dialog.findViewById(R.id.gameoverbutton);

        playAgainButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {

                dialog.dismiss();
                recreate();
            }
        });

        dialog.show();
    }

}
