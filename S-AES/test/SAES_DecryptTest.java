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
public class SAES_DecryptTest {
    
    public SAES_DecryptTest() {
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

    @Test
    public void SAES_DecryptionRoundTest(){
        short cipherText = 0x0738;
        byte[][] key = {{0xa, 0x7}, {0x3, 0xb}};
        short expResult = 0x6f6b;
        short result = SAES_Decrypt.SAES_DecryptionRound(cipherText, key);
        assertEquals(expResult, result);
    }
}
