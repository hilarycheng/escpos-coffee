/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */
package com.github.anastaciocintra.escpos.barcode;

import com.github.anastaciocintra.escpos.EscPosConst;

import java.io.ByteArrayOutputStream;

/**
 * Supply ESC/POS BarCode commands
 */
@SuppressWarnings("unused")
public class BarCode implements EscPosConst, BarCodeWrapperInterface<BarCode> {

    /**
     * Provides bar-code system. <p>
     * Each system have one <code>regex</code> to help on validate data.
     *
     * @see #setSystem(BarCodeSystem)
     * @see java.util.regex.Pattern
     */
    @SuppressWarnings("SpellCheckingInspection")
    public enum BarCodeSystem {
        /**
         * <code>regex: "\\d{11,12}$"</code>
         */
        UPCA(0, "\\d{11,12}$"),
        /**
         * <code>regex: "^\\d{11,12}$"</code>
         */
        UPCA_B(65, "^\\d{11,12}$"),
        /**
         * <code>regex: "^\\d{6}$|^0{1}\\d{6,7}$|^0{1}\\d{10,11}$"</code>
         */
        UPCE_A(1, "^\\d{6}$|^0{1}\\d{6,7}$|^0{1}\\d{10,11}$"),
        /**
         * <code>regex: "^\\d{6}$|^0{1}\\d{6,7}$|^0{1}\\d{10,11}$"</code>
         */
        UPCE_B(66, "^\\d{6}$|^0{1}\\d{6,7}$|^0{1}\\d{10,11}$"),
        /**
         * <code>regex: "^\\d{12,13}$"</code>
         */
        JAN13_A(2, "^\\d{12,13}$"),
        /**
         * <code>regex: "^\\d{12,13}$"</code>
         */
        JAN13_B(67, "^\\d{12,13}$"),
        /**
         * <code>regex: "^\\d{7,8}$"</code>
         */
        JAN8_A(3, "^\\d{7,8}$"),
        /**
         * <code>regex: "^\\d{7,8}$"</code>
         */
        JAN8_B(68, "^\\d{7,8}$"),
        /**
         * <code>regex: "^[\\d\\p{Upper}\\ \\$\\%\\*\\+\\-\\.\\/]+$"</code>
         */
        CODE39_A(4, "^[\\d\\p{Upper}\\ \\$\\%\\*\\+\\-\\.\\/]+$"),
        /**
         * <code>regex: "^[\\d\\p{Upper}\\ \\$\\%\\*\\+\\-\\.\\/]+$"</code>
         */
        CODE39_B(69, "^[\\d\\p{Upper}\\ \\$\\%\\*\\+\\-\\.\\/]+$"),
        /**
         * <code>regex: "^([\\d]{2})+$"</code>
         */
        ITF_A(5, "^([\\d]{2})+$"),
        /**
         * <code>regex: "^([\\d]{2})+$"</code>
         */
        ITF_B(70, "^([\\d]{2})+$"),
        /**
         * <code>regex:  "^[A-Da-d][\\d\\$\\+\\-\\.\\/\\:]*[A-Da-d]$"</code>
         */
        CODABAR_A(6, "^[A-Da-d][\\d\\$\\+\\-\\.\\/\\:]*[A-Da-d]$"),
        /**
         * <code>regex:  "^[A-Da-d][\\d\\$\\+\\-\\.\\/\\:]*[A-Da-d]$"</code>
         */
        CODABAR_B(71, "^[A-Da-d][\\d\\$\\+\\-\\.\\/\\:]*[A-Da-d]$"),
        /**
         * <code>regex:  "^[\\x00-\\x7F]+$"</code>
         */
        CODE93_Default(72, "^[\\x00-\\x7F]+$"),
        /**
         * <code>regex:  "^\\{[A-C][\\x00-\\x7F]+$"</code>
         */
        CODE128(73, "^\\{[A-C][\\x00-\\x7F]+$");
        public int code;
        public String regex;

        BarCodeSystem(int code, String regex) {
            this.code = code;
            this.regex = regex;
        }
    }


    /**
     * Provides Bar Code HRI Positions.<p>
     * Human Readable Interpretation (HRI) position is the position of the text relative
     * to the position of the bar-code.
     *
     * @see #setHRIPosition(BarCodeHRIPosition)
     */
    public enum BarCodeHRIPosition {
        /**
         * Do not Print the text
         */
        NotPrinted_Default(48),
        /**
         * Print the text above the bar-code
         */
        AboveBarCode(49),
        /**
         * Print the text below the bar-code
         */
        BelowBarCode(50),
        /**
         * Print the text above and below the bar-code
         */
        AboveAndBelowBarCode(51);
        public int value;

        BarCodeHRIPosition(int value) {
            this.value = value;
        }
    }

    /**
     * Provides textHRI font for bar-code.<p>
     * Human Readable Interpretation (HRI) font is the font of the text
     * printed with bar-code.
     *
     * @see #setHRIFont(BarCodeHRIFont)
     */
    public enum BarCodeHRIFont {
        Font_A_Default(48),
        Font_B(49),
        Font_C(50);
        public int value;

        BarCodeHRIFont(int value) {
            this.value = value;
        }
    }


    protected BarCodeSystem system;
    protected int width;
    protected int height;
    protected BarCodeHRIPosition HRIPosition;
    protected BarCodeHRIFont HRIFont;
    protected Justification justification;


    /**
     * Creates object with default values.
     */
    public BarCode() {
        system = BarCodeSystem.CODE93_Default;
        width = 2;
        height = 100;
        HRIPosition = BarCodeHRIPosition.NotPrinted_Default;
        HRIFont = BarCodeHRIFont.Font_A_Default;
        justification = Justification.Left_Default;
    }

    /**
     * Set bar-code system.
     *
     * @param barCodeSystem type of bar-code system.
     * @return this object.
     * @see #getBytes(java.lang.String)
     */
    public BarCode setSystem(BarCodeSystem barCodeSystem) {
        this.system = barCodeSystem;
        return this;
    }

    /**
     * Set bar-code size.<p>
     * ASCII GS w n
     *
     * @param width  codes for widths of the module,
     *               the width of module depends of printer model.
     * @param height height of a bar code in dots.
     * @return this object.
     * @throws IllegalArgumentException when this condition is not true: 2 ≤ width ≤ 6, 68 ≤ width ≤ 76
     * @throws IllegalArgumentException when this condition is not true: 1 ≤ height ≤ 255
     * @see #getBytes(java.lang.String)
     */
    public BarCode setBarCodeSize(int width, int height) throws IllegalArgumentException {
        if ((width < 2 || width > 6) && (width < 68 || width > 76)) {
            throw new IllegalArgumentException("with must be between 1 and 255");
        }
        if (height < 1 || height > 255) {
            throw new IllegalArgumentException("height must be between 1 and 255");
        }
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Set bar-code HRI Position.<p>
     *
     * @param barCodeHRI position of the text
     * @return this object
     * @see #getBytes(java.lang.String)
     */
    public BarCode setHRIPosition(BarCodeHRIPosition barCodeHRI) {
        this.HRIPosition = barCodeHRI;
        return this;
    }

    /**
     * Set bar-code HRI Font.<p>
     *
     * @param HRIFont font of the text printed with bar-code.
     * @return this object
     * @see #getBytes(java.lang.String)
     */
    public BarCode setHRIFont(BarCodeHRIFont HRIFont) {
        this.HRIFont = HRIFont;
        return this;
    }

    /**
     * Set horizontal justification of bar-code
     *
     * @param justification left, center or right
     * @return this object
     */
    public BarCode setJustification(Justification justification) {
        this.justification = justification;
        return this;
    }


    /**
     * BarCode Assembly into ESC/POS bytes. <p>
     * <p>
     * Set bar code height <p>
     * ASCII GS h n <p>
     * <p>
     * Set bar code width <p>
     * ASCII GS w n <p>
     * <p>
     * Select print position of Human Readable Interpretation (HRI) characters <p>
     * ASCII GS H n
     * <p>
     * Select font for HRI characters <p>
     * ASCII GS f n
     * <p>
     * Select justification <p>
     * ASCII ESC a n <p>
     * <p>
     * print BarCode <p>
     * ASCII GS k m d1 ... dk <p>
     *
     * @param data to be printed in bar-code
     * @return bytes of ESC/POS commands to print the bar-code
     * @throws IllegalArgumentException when data do no match with regex.
     */
    @Override
    public byte[] getBytes(String data) throws IllegalArgumentException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        if (!data.matches(system.regex)) {
            throw new IllegalArgumentException(String.format("data must match with \"%s\"", system.regex));
        }

        //
        bytes.write(GS);
        bytes.write('h');
        bytes.write(height);
        //
        bytes.write(GS);
        bytes.write('w');
        bytes.write(width);
        //
        bytes.write(GS);
        bytes.write('H');
        bytes.write(HRIPosition.value);
        //
        bytes.write(GS);
        bytes.write('f');
        bytes.write(HRIFont.value);
        //
        bytes.write(ESC);
        bytes.write('a');
        bytes.write(justification.value);


        ////
        bytes.write(GS);
        bytes.write('k');

        bytes.write(system.code);
        if (system.code <= 6) {
            bytes.write(data.getBytes(), 0, data.length());
            bytes.write(NUL);
        } else {
            bytes.write(data.length());
            bytes.write(data.getBytes(), 0, data.length());

        }
        return bytes.toByteArray();
    }

}
