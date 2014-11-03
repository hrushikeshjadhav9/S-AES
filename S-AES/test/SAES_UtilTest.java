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
import static org.junit.Assert.*;
import org.junit.Test;

/** S-AES Utility Tests.
 *
 * Various functions used to encrypt by S-AES
 * 
 * @author Noel Niles
 * @version 1.0
 * @since 2014-10-28
 ******************************************************************************/
public class SAES_UtilTest {
    
    public SAES_UtilTest() {
    }

     @Test
    public void shortToMatrix(){
        //System.out.println("Testing shortToMatrix");
        final short input = 0x2d55;
        final byte[][] expResult = {{0x02, 0x05}, {0x0d, 0x05}};
        final byte[][] result = SAES_Util.shortToMatrix(input);
        assertArrayEquals(expResult, result);
    }
    
}
