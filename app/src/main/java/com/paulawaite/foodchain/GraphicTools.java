package com.paulawaite.foodchain;

import android.widget.ImageView;

/**
 * Created by paulawaite on 7/15/16.
 */
public class GraphicTools {

    public static String determineImagePortionSelected(ImageView inGraphic, float x, float y) {

        // LEFT is image left bounds to 1/3 of image width
        // Left if image top bounds -1/3 image height to image top bounds -2/3 image height

        if (isLeftArrowSelected(inGraphic, x, y)) {
            return "left";
        }

        if (isRightArrowSelected(inGraphic, x, y)) {
            return "right";
        }

        if (isUpArrowSelected(inGraphic, x, y)) {
            return "up";
        }

        if (isDownArrowSelected(inGraphic, x, y)) {
            return "down";
        }



        return "";
    }

    private static boolean isLeftArrowSelected(ImageView inGraphic, float x, float y) {
        float leftArrowLeftX = inGraphic.getX();
        float leftArrowRightX = inGraphic.getX() + (inGraphic.getWidth() / 3);
        float leftArrowTopY = inGraphic.getY() + (inGraphic.getHeight() / 3);
        float leftArrowBottomY = inGraphic.getY() + (inGraphic.getHeight() * 2 / 3.0f);

        if (x > leftArrowLeftX // pointer is on left arrow
                && x < leftArrowRightX
                && y > leftArrowTopY
                && y < leftArrowBottomY) {
            return true;
        }
        return false;
    }

    private static boolean isRightArrowSelected(ImageView inGraphic, float x, float y) {
        float rightArrowLeftX = inGraphic.getX() + (inGraphic.getWidth() * 2 / 3.0f);
        float rightArrowRightX = inGraphic.getX() + (inGraphic.getWidth());
        float rightArrowTopY = inGraphic.getY() + (inGraphic.getHeight() / 3);
        float rightArrowBottomY = inGraphic.getY() + (inGraphic.getHeight() * 2 / 3.0f);

        if (x > rightArrowLeftX // pointer is on left arrow
                && x < rightArrowRightX
                && y > rightArrowTopY
                && y < rightArrowBottomY) {
            return true;
        }
        return false;
    }

    private static boolean isUpArrowSelected(ImageView inGraphic, float x, float y) {
        float leftArrowLeftX = inGraphic.getX() + (inGraphic.getWidth() / 3);
        float leftArrowRightX = inGraphic.getX() + (inGraphic.getWidth() * 2 / 3.0f);
        float leftArrowTopY = inGraphic.getY();
        float leftArrowBottomY = inGraphic.getY() + (inGraphic.getHeight() / 3);

        if (x > leftArrowLeftX // pointer is on left arrow
                && x < leftArrowRightX
                && y > leftArrowTopY
                && y < leftArrowBottomY) {
            return true;
        }
        return false;
    }

    private static boolean isDownArrowSelected(ImageView inGraphic, float x, float y) {
        float leftArrowLeftX = inGraphic.getX() + (inGraphic.getWidth() / 3);
        float leftArrowRightX = inGraphic.getX() + (inGraphic.getWidth() * 2 / 3.0f);
        float leftArrowTopY = inGraphic.getY() + (inGraphic.getHeight() * 2 / 3.0f);
        float leftArrowBottomY = inGraphic.getY() + (inGraphic.getHeight());

        if (x > leftArrowLeftX // pointer is on left arrow
                && x < leftArrowRightX
                && y > leftArrowTopY
                && y < leftArrowBottomY) {
            return true;
        }
        return false;
    }
}
