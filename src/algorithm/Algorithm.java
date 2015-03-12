package algorithm;


/**
 * Algorithm interface, involving input, execute and output
 * Created by LeoDong on 12/03/2015.
 */
public interface Algorithm<C extends Configuration, O extends Result>
        extends Runnable {

    public static final short STAGE_INITIAL = 0;
    public static final short STAGE_INPUT_FINISH = 1;
    public static final short STAGE_EXECUTE_FINISH = 2;

    public boolean input(C config);

    public void forward();

    public O getResult();

    public short getStage();
}
