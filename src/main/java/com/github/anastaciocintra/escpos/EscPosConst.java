/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */
package com.github.anastaciocintra.escpos;

public interface EscPosConst {

    /**
     *
     */
    int NUL = 0;
    int LF = 10;
    int ESC = 27;
    int GS = 29;

    /**
     * Values for print justification.
     *
     * @see Style#setJustification(Justification)
     */
    enum Justification {
        Left_Default(48),
        Center(49),
        Right(50);
        public int value;

        Justification(int value) {
            this.value = value;
        }
    }

}
