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
    protected static byte[][] addKey(byte[][] state, byte[][] key){               
        byte[][] newState = new byte[2][2];
        newState[0][0] = (byte) (state[0][0] ^ key[0][0]);
        newState[0][1] = (byte) (state[0][1] ^ key[1][0]);     
        newState[1][0] = (byte) (state[1][0] ^ key[0][1]);       
        newState[1][1] = (byte) (state[1][1] ^ key[1][1]);
        return newState;
    }
}
