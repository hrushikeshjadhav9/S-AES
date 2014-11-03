/** Copyright 2014 Noel Niles
 * 
 * This file is part of SAES.
 *
 * S-AES is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

/** S-AES Decryption.
 *
 * Various functions used to encrypt by S-AES
 * 
 * @author Noel Niles
 * @version 1.0
 * @since 2014-10-28
 ******************************************************************************/
public class SAES_Decrypt {

    public SAES_Decrypt(){
        
    }
    /** Inverse S-Box
     * 
     * Inverses the transformation performed in the S-Box. Used during 
     * decryption.
     **************************************************************************/
    private static final byte[][] invSBox = 
        {{0x0a, 0x05, 0x09, 0x0b}, 
         {0x01, 0x07, 0x08, 0x0f}, 
         {0x06, 0x00, 0x02, 0x03}, 
         {0x0c, 0x04, 0x0d, 0x0e}};
       
    /** Inverse mix columns.
     * 
     * @param state: 2x2 matrix representing the current state of the message.
     * @return mixedState: A new 2x2 matrix. 
     **************************************************************************/
    protected static byte[][] invMixColumns(final byte[][] state){
        final byte[][] mixedState = new byte[2][2];
        final byte[][] mixMatrix = {{9,2},{2,9}};
        
        mixedState[0][0] = (byte)(state[0][0]^(SAES_Util.finMul((byte)0x4, state[1][0])));
        mixedState[1][0] = (byte)(SAES_Util.finMul((byte)0x4, state[0][0])^state[1][0]);
        mixedState[0][1] = (byte)(state[0][1]^(SAES_Util.finMul((byte)0x4, state[1][1])));
        mixedState[1][1] = (byte)(SAES_Util.finMul((byte)0x4, state[0][1])^state[1][1]);
        return mixedState;
    }
        
    /** Substitutes nibbles using a table look in S-AES S-Box.
     * 
     * @param  nibArr: 2x2 matrix of nibbles
     * @return matrix: a new 2x2 matrix with the nibbles substituted
     **************************************************************************/
        /** Substitutes nibbles during key expansion.
     * 
     * @param nibArr: array with 2 nibbles
     * @return 
     **************************************************************************/
    protected static byte invSubNib(final byte[] nibArr) {
        // x, y, xx, yy are indexes into the SBox
        int x, y;
        x = (nibArr[0] >>> 0x02) & 0x03;
        y = nibArr[0] & 0x03;
        byte subNib1 = invSBox[x][y];

        int xx, yy;
        xx = (nibArr[1] >>> 0x02) & 0x03;
        yy = nibArr[1] & 0x03;

        byte subNib2 = invSBox[xx][yy];
        byte subbedByte = (byte) (((subNib1 & 0x0f) << 0x04) | (subNib2));        
        return subbedByte;
    }
    
    
    protected static byte[][] invSubNib(final byte[][] nibArr) {

        byte[][] matrix = new byte[2][2];
        byte[] w1 = {nibArr[0][0], nibArr[1][0]};
        byte[] w2 = {nibArr[0][1], nibArr[1][1]};
        matrix[0][0] = (byte)((invSubNib(w1) >>> 4) & 0xf);
        matrix[1][0] = (byte)(invSubNib(w1) & 0xf);
        matrix[0][1] = (byte)((invSubNib(w2) >>> 4) & 0xf);
        matrix[1][1] = (byte)(invSubNib(w2) & 0xf);      
        return matrix;
    }
    protected static byte[][] invSubstituteNibbles(final byte[][] nibArr){
        return invSubNib(nibArr);
    }
    protected static byte[][] invShiftRows(final byte[][] state){
        return SAES_Encrypt.shiftRows(state);
    }
    
    protected static short SAES_DecryptionRound(final short cipherText, 
        final byte[][] key){
           
        byte[][] state = SAES_Util.shortToMatrix(cipherText);
        //System.out.printf("Decipher state before addKey: %x%x%x%x\n", state[0][0], state[1][0],
         //       state[0][1], state[1][1]);
        short plainText;  
        
        short[] keyArray = SAES_Key.keyExpansion(key);
        
        byte[][] k1 = SAES_Util.shortToMatrix(keyArray[0]);
        byte[][] k2 = SAES_Util.shortToMatrix(keyArray[1]);
        byte[][] k3 = SAES_Util.shortToMatrix(keyArray[2]);
        
        System.out.printf("***Begin Decryption Keys***\n");
        System.out.printf("Decryption key1: %x%x%x%x\n", k1[0][0], k1[1][0],
                k1[0][1], k1[1][1]);
        System.out.printf("Decryption key2: %x%x%x%x\n", k2[0][0], k2[1][0],
                k1[0][1], k1[1][1]);
        System.out.printf("Decryption key3: %x%x%x%x\n\n", k2[0][0], k2[1][0],
                k1[0][1], k1[1][1]);
        
        // Round 0
        state = SAES_Encrypt.addKey(state, k3);
        System.out.printf("Decipher state0: %x%x%x%x\n", state[0][0], state[1][0],
                state[0][1], state[1][1]);
        
        // Round 1
        // Fight!
        state = invShiftRows(state);
        state = invSubstituteNibbles(state);
        state = SAES_Encrypt.addKey(state, k2);
        System.out.printf("Decipher state1: %x%x%x%x\n", state[0][0], state[1][0],
                state[0][1], state[1][1]);
        state = invMixColumns(state);
        
              
        // Round 2
        state = invShiftRows(state);
        state = invSubstituteNibbles(state);
        state = SAES_Encrypt.addKey(state, k1);
        
        System.out.printf("Decipher state2: %x%x%x%x\n", state[0][0], state[1][0],
                state[0][1], state[1][1]);
        
        System.out.printf("Deciphered text = %x%x%x%x\n\n", state[0][0], state[1][0], 
            state[0][1], state[1][1]);
        
        plainText = SAES_Util.matrixToShort(state);
        return plainText;
    }
}


