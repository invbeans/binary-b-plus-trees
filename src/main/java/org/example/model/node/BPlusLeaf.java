package org.example.model.node;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class BPlusLeaf extends BPlusNode {
    int maxNum;
    int minNum;
    int curNum;
    BPlusLeaf left;
    BPlusLeaf right;
    Integer[] key;

    public BPlusLeaf(int level, int elem){
        this.maxNum = level - 1;
        this.minNum = (int)(Math.ceil(level/2.0)-1);
        this.key = new Integer[level];
        this.curNum = 0;
        this.addElem(elem);
    }

    public BPlusLeaf(int level, Integer[] key, BPlusInternal parent){
        this.maxNum = level - 1;
        this.minNum = (int)(Math.ceil(level/2.0)-1);
        this.key = key;
        this.curNum = linearNullSearch(key);
        this.parent = parent;
    }

    public int linearNullSearch(Integer[] arr){
        for(int i = 0; i < arr.length; i++){
            if(arr[i] == null) return i;
        }
        return -1;
    }

    public boolean addElem(int elem){
        if(this.curNum == this.maxNum){ return false; }
        else{
            if(this.key == null) this.key = new Integer[1];
            this.key[this.curNum] = elem;
            this.curNum++;
            Arrays.sort(this.key, 0, this.curNum);
            return true;
        }
    }

    public void deleteElem(int index){
        this.key[index] = null;
        this.curNum--;
    }
}
