package model;

import java.util.LinkedList;
import java.util.List;

import util.Pair;

/**
 * Enumeration containing all the supported models for recognition.
 */
public enum RefModels {
    
    VERTICAL("vertical", new Integer[] {2}, false, new Double[] {1.}),
    HORIZONTAL("horizontal", new Integer[] {4}, false, new Double[] {1.}),
    GRATERTHAN("greaterthan", new Integer[] {5,7}, false, new Double[] {1.,1.}),
    SMALLERTHAN("smallerthan", new Integer[] {7,5}, false, new Double[] {1.,1.}),
    SINGLEV("singlev", new Integer[] {5, 3}, false, new Double[] {1.,1.}),
    CIRCLE("circle", new Integer[] {0,1,2,3,4,5,6,7}, true, new Double[] {1.,1.,1.,1.,1.,1.,1.,1.}),
    TRIANGLE("triangle", new Integer[] {3,5,0}, true, new Double[] {1.,1.,1.}),
    DOUBLEW("doublew", new Integer[] {5,3,5,3}, false, new Double[] {1.,.7,.7,1.}),
    ZETA("zeta", new Integer[] {4,7,4}, false, new Double[] {1.,1.4,1.});
    
    private final String name;
    private final Integer[] seq; 
    private final Double[] prop; 
    private final List<Pair<Integer, Double>> map;
    private final boolean circular;

    RefModels(final String name, final Integer[] seq, final boolean circular, final Double[] prop) {
        this.name = name;
        this.seq = seq;
        this.prop = prop;
        this.circular = circular;
        this.map = getFeaturesMapList();
    }

    /**
     * 
     * @return
     *          weather or not this model can be rotated in space without changing its meaning
     */
    public boolean isCircular() {
        return this.circular;
    }

    /**
     * 
     * @return
     *          directions sequence for this model
     */
    public Integer[] getSeq() {
        return this.seq.clone();
    }

    /**
     * 
     * @return
     *          proportions for this model (not implemented in this version)
     */
    public Double[] getProp() {
        return prop.clone();
    }

    /**
     * 
     * @return
     *          direction-proportion map (not implemented in this version)
     */
    public List<Pair<Integer, Double>> getMap() {
        return map;
    }
    
    /**
     * 
     * @return
     *          name of this model
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return
     *          stores direction-proportion map
     */
    public List<Pair<Integer, Double>> getFeaturesMapList() {
        final List<Pair<Integer, Double>> ret = new LinkedList<Pair<Integer, Double>>();
        for (int i = 0; i < this.seq.length; i++) {
            ret.add(new Pair<>(this.seq[i], this.prop[i]));
        }
        return ret;
    }

}
