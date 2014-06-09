package de.hpi.glaed.nlp;

public class ParserConfig {
    private float _testSetPercentage = 0.1f;
    private int _testSetSize;
    private int _collectionSize = -1;

    public float getTestSetPercentage() {
        return _testSetPercentage;
    }

    public int getCollectionSize() {
        assertCollectionSizeSet();
        return _collectionSize;
    }

    public void setCollectionSize(int collectionSize) {
        this._collectionSize = collectionSize;
        this._testSetSize = _collectionSize / 10;
    }

    public int getTestSetSize() {
        assertCollectionSizeSet();
        return _testSetSize;
    }

    public int getTrainingSetSize(){
        assertCollectionSizeSet();
        return _collectionSize - _testSetSize;
    }

    private void assertCollectionSizeSet() {
        if (_collectionSize == -1){
            System.err.println("Collection size must be configured first.");
            Thread.dumpStack();
            System.exit(-1);
        }
    }

}
