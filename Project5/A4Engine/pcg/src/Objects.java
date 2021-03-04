public enum Objects {
    LAND_TILE(31),
    HOUSE(42),
    BIG_TREE_TILE(45),
    SMALL_TREE_TILE(46),
    ROCK(47);

    int value;

    Objects(int val) {
        this.value = val;
    }

    public int getValue() {
        return this.value;
    }
}
