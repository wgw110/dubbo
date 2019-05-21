package mobi.mixiong.cache;

/**
 * -------------------------------------
 * -------------------------------------
 */
public class ValueScorePair<V> {
    public ValueScorePair(V value, Double score) {
        this.value = value;
        this.score = score;
    }
    private V value;
    private Double score;

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}

