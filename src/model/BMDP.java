/** ------------------------------------------------------------------------- *
 * libpomdp
 --------------------------------------------------------------------------- */


package model;

import common.AlphaVector;
import org.apache.commons.math3.linear.RealMatrix;

public interface BMDP extends POMDP {

    public POMDP getPomdp();
    
    public RealMatrix getTau(int a, int o);

    public AlphaVector projection(AlphaVector alpha, int a, int o);
}
