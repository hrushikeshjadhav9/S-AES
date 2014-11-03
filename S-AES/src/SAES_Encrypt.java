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

/** S-AES_Encrypt.
 *
 * Various functions used to encrypt by S-AES
 * 
 * @author Noel Niles
 * @version 1.0
 * @since 2014-10-28
 ******************************************************************************/
public class SAES_Encrypt {
    
    public SAES_Encrypt(){

        // S-AES state matrix.
        byte state[][] = new byte[2][2];        
    }
    /** Adds key to state.
     * 
     * I suspect the problem is in this function. For some reason it doesn't 
     * seem to be reversible. Maybe something funny with the way Java is 
     * handling bitwise operations.
     * 
     * @param state 2x2 array of nibbles.
     * @param key 2x2 random nibbles
     * @return newState: state with the key added
     * 
     **************************************************************************/
    protected static byte[][] addKey(final byte[][] state, final byte[][] key){               
        byte[][] newState = new byte[2][2];
        
//        System.out.printf("Input to addKey: %x%x%x%x\n", state[0][0], state[1][0],
//                state[0][1], state[1][1]);

        newState[0][0] = (byte) (state[0][0] ^ key[0][0]);
        newState[1][0] = (byte) (state[1][0] ^ key[1][0]);     
        newState[0][1] = (byte) (state[0][1] ^ key[0][1]);       
        newState[1][1] = (byte) (state[1][1] ^ key[1][1]);
        return newState;
    }
    
    /** S-Box lookup.
     * 
     * @param nibArr 2x2 matrix of nibbles 
     * @return
     * 
     * @TODO consolidate these function into a utility maybe?
     **************************************************************************/
    protected static byte[][] substituteNibbles(final byte[][] nibArr){
        return SAES_Key.subNib(nibArr);
    }
    
    /** Shifts rows according to S-AES.
     * 
     * Takes a 2x2 matrix and circular shifts the bottom row.
     * 
     * @param state
     * @return 
     **************************************************************************/
    protected static byte[][] shiftRows(final byte[][] state){
        final byte[][] rotatedState = new byte[2][2];
        final byte w0 = state[0][0];
        final byte w1 = state[0][1];
        final byte w2 = state[1][0];
        final byte w3 = state[1][1];        
        
        rotatedState[0][0] = w0;
        rotatedState[0][1] = w1;
        
        // Shift happens.
        rotatedState[1][0] = w3;
        rotatedState[1][1] = w2;
        
        return rotatedState;     
    }
    
    /** Mixes the columns of a 2x2 matrix.
     * 
     * @param state: 2x2 matrix representing the current state of the message.
     * @return mixedState: A new 2x2 matrix. 
     **************************************************************************/
    protected static byte[][] mixColumns(final byte[][] state){
        final byte[][] mixedState = new byte[2][2];
        final byte[][] mixMatrix = {{1,4},{4,1}};
        
        mixedState[0][0] = (byte)(state[0][0]^(SAES_Util.finMul((byte)0x4, state[1][0])));
        mixedState[1][0] = (byte)(SAES_Util.finMul((byte)0x4, state[0][0])^state[1][0]);
        mixedState[0][1] = (byte)(state[0][1]^(SAES_Util.finMul((byte)0x4, state[1][1])));
        mixedState[1][1] = (byte)(SAES_Util.finMul((byte)0x4, state[0][1])^state[1][1]);
        return mixedState;
    }

    /** Performs 3 rounds of encryption.
     * 
     * The 0th round is just add the round key.
     * The 1st round subsitutesNibbles, shifts rows, mixes columns and adds key.
     * The last round does not mix columns.
     * @param plainText: 16-bit message to be encrypted
     * @param key: 16-bit key
     * @return cipherText: 16-bit S-AES encrypted message
     **************************************************************************/
    protected static short SAES_EncryptionRound(final short plainText, 
            final byte[][] key){
        byte[][] state = SAES_Util.shortToMatrix(plainText);
        short cipherText;
        
        short[] keyArray = SAES_Key.keyExpansion(key);
        
        byte[][] k1 = SAES_Util.shortToMatrix(keyArray[0]);
        byte[][] k2 = SAES_Util.shortToMatrix(keyArray[1]);
        byte[][] k3 = SAES_Util.shortToMatrix(keyArray[2]);
        
        System.out.printf("***Begin Encryption Keys***\n", k3);
        System.out.printf("Encrypt key1: %x%x%x%x\n", k1[0][0], k1[1][0],
                k1[0][1], k1[1][1]);
        System.out.printf("Encrypt key2: %x%x%x%x\n", k2[0][0], k2[1][0],
                k1[0][1], k1[1][1]);
        System.out.printf("Encrypt key3: %x%x%x%x\n\n", k2[0][0], k2[1][0],
                k1[0][1], k1[1][1]);
        
        // Round 0
        state = addKey(state, k1);
        System.out.printf("Encrypt state0: %x%x%x%x\n", state[0][0], state[1][0],
                state[0][1], state[1][1]);
        
        // Round 1
        state = substituteNibbles(state);
        state = shiftRows(state);
        state = mixColumns(state);
        state = addKey(state, k2);
        System.out.printf("Encrypt state1: %x%x%x%x\n", state[0][0], state[1][0],
                state[0][1], state[1][1]);
        
        // Round 2     
        state = substituteNibbles(state);
        state = shiftRows(state);
        state = addKey(state, k3);
        System.out.printf("Encrypt state2: %x%x%x%x\n", state[0][0], state[1][0],
                state[0][1], state[1][1]);
        
        System.out.printf("Encrypted text = %x%x%x%x\n", state[0][0], state[1][0], 
                state[0][1], state[1][1]);
        
        cipherText = SAES_Util.matrixToShort(state);
        return cipherText;
    }
}
