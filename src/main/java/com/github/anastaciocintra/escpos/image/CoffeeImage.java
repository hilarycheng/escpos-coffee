package com.github.anastaciocintra.escpos.image;

/**
 * Specify the interface to work with image
 * @see CoffeeImageImpl
 * @see <a href="https://github.com/anastaciocintra/AdroidEscposCoffee">EscPosCoffee image on Android</a>
 */
public interface CoffeeImage {

    /**
     * Returns the width of the image
     */
    int getWidth();

    /**
     * Returns the height of the image
     */
    int getHeight();

    /**
     * Returns a Sub Image defined by a specified rectangular region.
     * The returned CoffeeImage shares the same data array as the original image.
     * @param x - the X coordinate of the upper-left corner of the specified rectangular region
     * @param y - the Y coordinate of the upper-left corner of the specified rectangular region
     * @param w - the width of the specified rectangular region
     * @param h - the height of the specified rectangular region
     * @return a CoffeeImage that is the Sub Image of this CoffeeImage.
     */
    CoffeeImage getSubImage(int x, int y, int w, int h);

    /**
     * Returns an integer pixel in the default RGB color model (TYPE_INT_ARGB) and default sRGB color-space.
     * Color conversion takes place if this default model does not match the image ColorModel.
     * There are only 8-bits of precision for each color component in the returned data when using this method.
     * @param x - the X coordinate of the pixel from which to get the pixel in the default RGB color model and sRGB color space
     * @param y - the Y coordinate of the pixel from which to get the pixel in the default RGB color model and sRGB color space
     * @return an integer pixel in the default RGB color model and default sRGB color-space.
     * @see getRGB(int, int)
     */
    int getRGB(int x, int y);

}
