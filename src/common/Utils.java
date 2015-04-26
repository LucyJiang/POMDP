/** ------------------------------------------------------------------------- *
 * libpomdp
 * ========
 * File: Utils.java
 * Description: useful general routines - everything in this class is static
 * Copyright (c) 2009, 2010 Diego Maniloff
 * Copyright (c) 2010 Mauricio Araya
 --------------------------------------------------------------------------- */

package common;

import java.util.Random;
public class Utils {

    //  set the random only once for every instance
    public static final Random random = new Random(System.currentTimeMillis());

} // Utils
