/** Copyright 2014 Noel Niles
 * 
 * This file is part of SAES
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
 *along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/** S-AES_KeyTest
 * 
 * Simple implementation of Advanced Encryption Standard. The main difference
 * between standard AES and S-AES is that AES does 10/12/14 rounds while S-AES 
 * only does 3 rounds. Also the block size for S-AES is 16-bits compared to AES
 * which uses 128-bit block sizes.
 *
 * @author Noel Niles
 * @version 1.0
 * @since 2014-10-28
 */
public class SAES_KeyTest {
    
    public SAES_KeyTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    /**
     * Test of genKey method, of class SAES_Keys.
     * 
     * Only tests length of array. Is not very useful.
     */
    @Test
    public void testGenKey() {
        // System.out.println("genKey");
        int expResult = 2;
        byte[][] result = SAES_Key.genKey();
        assertEquals(expResult, result.length);
        assertEquals(expResult, result[0].length);
        assertEquals(expResult, result[1].length);
    }
    /**
     * Test of subNib method, of class SAES_Keys.
     */
    @Test
    public void testSubNib() {
        byte[] nib = {0x05, 0x05};
        byte expResult = 0x11;
        byte result = SAES_Key.subNib(nib);
        assertEquals(expResult, result);
    }

    /**
     * Test of rotNib method, of class SAES_Keys.
     */
    @Test
    public void testRotNib() {
        //System.out.println("rotNib");
        byte w = 0x2d;
        byte[] expResult = {0x0d,0x02};
        byte[] result = SAES_Key.rotNib(w);
        assertArrayEquals(expResult, result);
    }
    
    /**
     * Test of keyExpansion method, of class SAES_Keys.
     */
    @Test
    public void testKeyExpansion() {
        // System.out.println("keyExpansion");
        byte[][] cipherKey = {{0x02, 0x0d}, {0x05, 0x05}};
        short[] expResult = {(short)0x2d55, (short)0xbce9, (short)0xa34a};
        short[] result = SAES_Key.keyExpansion(cipherKey);
        assertArrayEquals(expResult, result);
    } 
}
