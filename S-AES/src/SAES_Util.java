
/**
 *
 * @author noel
 */
public class SAES_Util {
        /** Converts a 2x2 matrix of nibbles to a 16-bit short.
     * 
     * Operates columnwise. Example:
     * 
     *                      | 0x01 0x03 |                     
     *                      | 0x02 0x04 | == 0x1234
     * 
     * @param b: 2x2 array.
     * @return result: a short
     **************************************************************************/
    protected static short matrixToShort(final byte[][] b){
        short result = 0; 
        result = (short)(result | b[0][0]);
        result = (short)((result << 4) | b[1][0]);
        result = (short)((result << 4) | b[0][1]);
        result = (short)((result << 4) | b[1][1]);
        return result;
    }
        
    /** Converts a 16-bits into a 2x2 matrix f nibbles columnwise.
     * 
     * @param s A short that needs to be a matrix.
     * @return 2x2 matrix of nibbles.
     **************************************************************************/
    protected static byte[][] shortToMatrix(final short s){
        byte[][] m = new byte[2][2];
        m[0][0] = (byte)((s >>> 12) & 0xf);
        m[1][0] = (byte)((s >>> 8) & 0xf);
        m[0][1] = (byte)((s >>> 4) & 0xf);
        m[1][1] = (byte)(s & 0xf);
        return m;
    }
  
    // Multiplication table for GF(2^4)
    protected static byte finMul(final byte f0, final byte f1){
        byte mulTable[][] = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0,   0,   0,   0,   0,   0},
            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf}, 
            {0, 2, 4, 6, 8, 0xa, 0xc, 0xe, 3, 1, 7, 5, 0xb, 9, 0xf, 0xd},
            {0, 3, 6, 5, 0xc, 0xf, 0xa, 9, 0xb, 8, 0xd, 0xe, 7, 4, 1, 2},
            {0, 4, 8, 0xc, 3, 7, 0xb, 0xf, 6, 2, 0xe, 0xa, 5, 1, 0xd, 9},
            {0, 5, 0xa, 0xf, 7, 2, 0xd, 8, 0xe, 0xb, 4, 1, 9, 0xc, 3, 6},
            {0, 6, 0xc, 0xa, 0xb, 0xd, 7, 1, 5, 3, 9, 0xf, 0xe, 8, 2, 4},
            {0, 7, 0xe, 9, 0xf, 8, 1, 6, 0xd, 0xa, 3, 4, 2, 5, 0xc, 0xb},
            {0, 8, 3, 0xb, 6, 0xe, 5, 0xd, 0xc, 4, 0xf, 7, 0xa, 2, 9, 1},
            {0, 9, 1, 8, 2, 0xb, 3, 0xa, 4, 0xd, 5, 0xc, 6, 0xf, 7, 0xe},
            {0, 0xa, 7, 0xd, 0xe, 4, 9, 3, 0xf, 5, 8, 2, 1, 0xb, 0xc, 6},
            {0, 0xb, 5, 0xe, 0xa, 1, 0xf, 4, 7, 0xc, 2, 9, 0xd, 6, 8, 3},
            {0, 0xc, 0xb, 7, 5, 9, 0xe, 2, 0xa, 6, 1, 0xd, 0xf, 3, 4, 8},
            {0, 0xd, 9, 4, 1, 0xc, 8, 5, 2, 0xf, 0xb, 6, 3, 0x3, 0xa, 7},
            {0, 0xe, 0xf, 1, 0xd, 3, 2, 0xc, 9, 7, 6, 8, 4, 0xa, 0xb, 5},
            {0, 0xf, 0xd, 2, 9, 6, 4, 0xb, 1, 0xe, 0xc, 3, 8, 7, 5, 0xa}
        };   
        byte product = mulTable[f0][f1];
        return product;
    }
 
}
