/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author noel
 */
public class SAES_Encrypt {
    
    public SAES_Encrypt(){
        // S-AES state matrix.
        byte state[][] = new byte[2][2];
        
        // Multiplication table for GF(2^4)
        
    }
    
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
        byte product = 0;
        product = mulTable[f0][f1];
        return product;
    }
    
    /** Adds key to state.
     * 
     * State is by column. Key is by row. Probably should fix that because it's
     * awkward to traverse the two matrices differently.
     * @param state 2x2 array of nibbles.
     * @param key 2x2 random nibbles
     * @return newState: state with the key added
     * 
     * @TODO fix the key expansion so that the loop is nicer.
     */
    protected static byte[][] addKey(final byte[][] state, final byte[][] key){               
        byte[][] newState = new byte[2][2];
        newState[0][0] = (byte) (state[0][0] ^ key[0][0]);
        newState[0][1] = (byte) (state[0][1] ^ key[1][0]);     
        newState[1][0] = (byte) (state[1][0] ^ key[0][1]);       
        newState[1][1] = (byte) (state[1][1] ^ key[1][1]);
        return newState;
    }
    
    /** S-Box lookup.
     * 
     * @param nibArr 
     * @return
     * 
     * @TODO consolidate these function into a utility maybe?
     */
    protected static byte substituteNibbles(final byte[] nibArr){
        return SAES_Key.subNib(nibArr);
    }
    
    protected static byte[][] shiftRows(final byte[][] state){
        final byte[][] rotatedState = new byte[2][2];
        final byte w0 = state[0][0];
        final byte w1 = state[0][1];
        final byte w2 = state[1][0];
        final byte w3 = state[1][1];        
        
        rotatedState[0][0] = w0;
        rotatedState[0][1] = w1;
        rotatedState[1][0] = w3;
        rotatedState[1][1] = w2;
        
        return rotatedState;     
    }
    
    protected static byte[][] mixColumns(final byte[][] state){
        final byte[][] mixedState = new byte[2][2];
        final byte[][] mixMatrix = {{1,4},{4,1}};
        
        mixedState[0][0] = (byte)(state[0][0] ^ (finMul((byte)0x4, state[1][0])));
        mixedState[1][0] = (byte)(finMul((byte)0x4, state[0][0]) ^ state[1][0]);
        mixedState[0][1] = (byte)(state[0][1] ^ (finMul((byte)0x04, state[1][1])));
        mixedState[1][1] = (byte)(finMul((byte)0x4, state[0][1]) ^ state[1][1]);
        return mixedState;
    }
}
