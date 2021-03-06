package com.paulawaite.foodchain;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by paulawaite on 7/15/16.
 */
public class GraphicTools {

    public static String determineImagePortionSelected(Bitmap inGraphic, int inGraphicX, int inGraphicY, float x, float y) {

        // LEFT is image left bounds to 1/3 of image width
        // Left if image top bounds -1/3 image height to image top bounds -2/3 image height

        if (isLeftArrowSelected(inGraphic, inGraphicX, inGraphicY, x, y)) {
            return "left";
        }

        if (isRightArrowSelected(inGraphic, inGraphicX, inGraphicY, x, y)) {
            return "right";
        }

        if (isUpArrowSelected(inGraphic, inGraphicX, inGraphicY, x, y)) {
            return "up";
        }

        if (isDownArrowSelected(inGraphic, inGraphicX, inGraphicY, x, y)) {
            return "down";
        }



        return "";
    }

    private static boolean isLeftArrowSelected(Bitmap inGraphic, int inGraphicX, int inGraphicY, float x, float y) {
        float leftArrowLeftX = inGraphicX;
        float leftArrowRightX = inGraphicX + (inGraphic.getWidth() / 3);
        float leftArrowTopY = inGraphicY + (inGraphic.getHeight() / 3);
        float leftArrowBottomY = inGraphicY + (inGraphic.getHeight() * 2 / 3.0f);

        if (x > leftArrowLeftX // pointer is on left arrow
                && x < leftArrowRightX
                && y > leftArrowTopY
                && y < leftArrowBottomY) {
            return true;
        }
        return false;
    }

    private static boolean isRightArrowSelected(Bitmap inGraphic, int inGraphicX, int inGraphicY, float x, float y) {
        float rightArrowLeftX = inGraphicX + (inGraphic.getWidth() * 2 / 3.0f);
        float rightArrowRightX = inGraphicX + (inGraphic.getWidth());
        float rightArrowTopY = inGraphicY + (inGraphic.getHeight() / 3);
        float rightArrowBottomY = inGraphicY + (inGraphic.getHeight() * 2 / 3.0f);

        if (x > rightArrowLeftX // pointer is on left arrow
                && x < rightArrowRightX
                && y > rightArrowTopY
                && y < rightArrowBottomY) {
            return true;
        }
        return false;
    }

    private static boolean isUpArrowSelected(Bitmap inGraphic, int inGraphicX, int inGraphicY, float x, float y) {
        float leftArrowLeftX = inGraphicX + (inGraphic.getWidth() / 3);
        float leftArrowRightX = inGraphicX + (inGraphic.getWidth() * 2 / 3.0f);
        float leftArrowTopY = inGraphicY;
        float leftArrowBottomY = inGraphicY + (inGraphic.getHeight() / 3);

        if (x > leftArrowLeftX // pointer is on left arrow
                && x < leftArrowRightX
                && y > leftArrowTopY
                && y < leftArrowBottomY) {
            return true;
        }
        return false;
    }

    private static boolean isDownArrowSelected(Bitmap inGraphic, int inGraphicX, int inGraphicY, float x, float y) {
        float leftArrowLeftX = inGraphicX + (inGraphic.getWidth() / 3);
        float leftArrowRightX = inGraphicX + (inGraphic.getWidth() * 2 / 3.0f);
        float leftArrowTopY = inGraphicY + (inGraphic.getHeight() * 2 / 3.0f);
        float leftArrowBottomY = inGraphicY  + (inGraphic.getHeight());

        if (x > leftArrowLeftX // pointer is on left arrow
                && x < leftArrowRightX
                && y > leftArrowTopY
                && y < leftArrowBottomY) {
            return true;
        }
        return false;
    }
}
