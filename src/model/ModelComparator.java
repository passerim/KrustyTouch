package model;

import java.util.Arrays;

public class ModelComparator {
    
    private final Integer[] pointsSeq;
    private final Integer[] refModelSeq;
    private Integer result;

    public ModelComparator( final Integer[] pointsSeq, final RefModels refModel) {
        this.pointsSeq = pointsSeq;
        this.refModelSeq = refModel.getSeq();
        this.result = ModelUtils.DTWDistance(this.pointsSeq, this.refModelSeq);
        Integer res = ModelUtils.DTWDistance(this.pointsSeq, invert(this.refModelSeq));
        if (res < this.result) {
            this.result = res;
        }
        if (refModel.isCircular()) {
            Integer[] rotated = rotateBy(this.refModelSeq, 1);
            while (!Arrays.equals(this.refModelSeq, rotated)) {
                res = ModelUtils.DTWDistance(this.pointsSeq, rotated);
                if (res < this.result) {
                    this.result = res;
                }
                rotated = rotateBy(rotated, 1);
            }
            rotated = invert(rotated);
            Integer[] inverted = rotated.clone();
            rotated = rotateBy(rotated, 1);
            while (!Arrays.equals(inverted, rotated)) {
                res = ModelUtils.DTWDistance(this.pointsSeq, rotated);
                if (res < this.result) {
                    this.result = res;
                }
                rotated = rotateBy(rotated, 1);
            }
        }
    }
    
    public Integer getResult() {
        return this.result;
    }
    
    private Integer[] invert(final Integer[] src) {
        Integer[] dest = new Integer[src.length];
        int j = src.length;
        for (int i = 0; i < src.length; i++) { 
            dest[j - 1] = src[i]; 
            j = j - 1; 
        }
        for(int x = 0; x <= src.length-1; x++) {
            dest[x] = ModelUtils.invertMod8(dest[x]);
        }
        
        return dest;
    }

    private Integer[] rotateBy(final Integer[] src, final int n) {
        Integer[] dest = new Integer[src.length];
        for(int x = 0; x <= src.length-1; x++) {
            dest[(x+n) % src.length ] = src[x];
        }
        return dest;
    }
    

}
