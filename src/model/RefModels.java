package model;

import java.util.LinkedList;
import java.util.List;

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

    private RefModels(final String name,final Integer[] seq, final boolean circular, final Double[] prop) {
        this.name = name;
        this.seq = seq;
        this.prop = prop;
        this.circular = circular;
        this.map = getFeaturesMapList();
    }

    public boolean isCircular() {
        return this.circular;
    }

    public Integer[] getSeq() {
        return this.seq;
    }

    public Double[] getProp() {
        return prop;
    }

    public List<Pair<Integer, Double>> getMap() {
        return map;
    }
    
    public String getName() {
        return name;
    }

    public List<Pair<Integer, Double>> getFeaturesMapList() {
        final List<Pair<Integer, Double>> ret = new LinkedList<Pair<Integer,Double>>();
        for (int i = 0; i < this.seq.length; i++) {
            ret.add(new Pair<>(this.seq[i], this.prop[i]));
        }
        return ret;
    }

}
