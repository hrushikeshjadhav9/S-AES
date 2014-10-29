/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author noel
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
     * Only tests length of array. Might not be very useful.
     */
    @Test
    public void testGenKey() {
        System.out.println("genKey");
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
        System.out.println("rotNib");
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
        System.out.println("keyExpansion");
        byte[][] cipherKey = {{0x02, 0x0d}, {0x05, 0x05}};
        short[] expResult = {(short)0x2d55, (short)0xbce9, (short)0xa34a};
        short[] result = SAES_Key.keyExpansion(cipherKey);
        assertArrayEquals(expResult, result);
    } 
}
