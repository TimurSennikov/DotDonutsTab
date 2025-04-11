package senntools.dotdonuts_tab;

public class Pair<F, S>{
    F first;
    S second;

    public Pair(F first, S second){
        this.first = first;
        this.second = second;
    }

    public F getFirst(){return first;}
    public S getSecond(){return second;}
} // При попытке импорта в плагин Pair тот при выполнении выдаёт ошибку, поэтому делаем свой.