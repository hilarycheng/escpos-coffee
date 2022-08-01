/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */
package com.github.anastaciocintra.escpos.image;


import com.github.anastaciocintra.escpos.EscPosConst;

public interface ImageWrapperInterface<T> {

    byte[] getBytes(EscPosImage image);

    T setJustification(EscPosConst.Justification justification);

}
