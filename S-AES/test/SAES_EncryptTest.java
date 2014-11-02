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
public class SAES_EncryptTest {
    
    public SAES_EncryptTest() {
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
    public void addKeyTest() {
        System.out.println("Testing addKey() from SAES_Encrypt");
        byte[][] state = {{0xa,4},{7,9}};
        byte[][] key = {{0x02, 0x0d}, {0x05, 0x05}};       
        byte[][] expResult = {{8,1},{0xa,0xc}};  
        byte[][] result = SAES_Encrypt.addKey(state, key);
        assertArrayEquals(expResult, result);
    }
}
