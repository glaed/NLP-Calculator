package de.hpi.glaed.nlp;

public class Bigram {
    private String first;
    private String second;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public Bigram(String first, String second){
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString(){
        return String.format("[%s| %s]", first, second);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bigram bigram = (Bigram) o;

        if (first != null ? !first.equals(bigram.first) : bigram.first != null) return false;
        if (second != null ? !second.equals(bigram.second) : bigram.second != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }
}
