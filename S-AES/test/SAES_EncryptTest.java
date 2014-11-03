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
import org.junit.Test;
import static org.junit.Assert.*;

/** S-AES_Encrypt Tests.
 *
 * Various functions used to encrypt by S-AES
 * 
 * @author Noel Niles
 * @version 1.0
 * @since 2014-10-28
 ******************************************************************************/

public class SAES_EncryptTest {
    
    @Test
    public void substituteNibblesTest(){
        //System.out.println("Testing substituteNibbles() from SAES_Encrypt");
        final byte[][] nibArr = {{0x8, 0x1}, {0xa, 0xc}};
        final byte[][] expResult = {{0x6,0x4}, {0x0, 0xc}};
        final byte[][] result = SAES_Encrypt.substituteNibbles(nibArr);
        assertArrayEquals(expResult, result);
    }
    @Test
    public void shiftRowsTest(){
        //System.out.println("Testing shiftRows() from SAES_Encrypt");
        final byte[][] state = {{6, 4}, {0, 0xc}};
        final byte[][] expResult ={{6, 4}, {0xc, 0}};
        final byte[][] result = SAES_Encrypt.shiftRows(state);
        assertArrayEquals(expResult, result);
        
    }
    @Test
    public void mixColumnsTest(){
        //System.out.println("Testing mixColumns from SAES_Encrypt");
        final byte[][] state ={{6, 4}, {0xc, 0}};
        final byte[][] expResult ={{3, 4}, {0x7, 0x3}};
        final byte[][] result = SAES_Encrypt.mixColumns(state);
        assertArrayEquals(expResult, result);
    }
    @Test
    /** As far as I'm concerned this is the only test that really matters.
     * 
     * And it passes.
     * 
     * Given plaintext = 0x6f6b and key = 0xa73b the 
     * result should be 0x0738
     **************************************************************************/
    public void SAES_EncryptionRoundTest(){
        short plainText = 0x6f6b;
        byte[][] key = {{0xa, 0x7}, {0x3, 0xb}};
        short expResult = 0x0738;
        short result = SAES_Encrypt.SAES_EncryptionRound(plainText, key);
        assertEquals(expResult, result);
    }
}
