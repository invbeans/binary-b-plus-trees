package org.example.model.node;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BPlusInternal extends BPlusNode {
    int maxLevel;
    int minLevel;
    int curLevel;
    boolean isLeaf;
    int keySize;
    Integer[] key;
    BPlusNode[] pointers;
    BPlusInternal right;
    BPlusInternal left;
    public BPlusInternal(int level, Integer[] key){
        this.maxLevel = level;
        this.minLevel = (int)Math.ceil(level/2.0);
        this.curLevel = 0;
        this.key = key;
        this.pointers = new BPlusNode[this.maxLevel+1];
    }
    public BPlusInternal(int level, Integer[] key, BPlusNode[] pointers){
        this.maxLevel = level;
        this.minLevel = (int)Math.ceil(level/2.0);
        this.curLevel = linearNullSearch(pointers);
        this.key = key;
        this.pointers = pointers;
    }

    public int linearNullSearch(BPlusNode[] arr){
        for(int i = 0; i < arr.length; i++){
            if(arr[i] == null) return i;
        }
        return -1;
    }

    public void removePointer(int index){
        this.pointers[index] = null;
        this.curLevel--;
    }

    public int findIndexOfPointer(BPlusNode pointer){
        for(int i = 0; i < this.pointers.length; i++){
            if(this.pointers[i] == pointer) return i;
        }
        return -1;
    }

    public void appendChildPointer(BPlusNode pointer){
        this.pointers[curLevel] = pointer;
        this.curLevel++;
    }

    public void insertChildPointer(BPlusNode pointer, int index){
        for(int i = curLevel - 1; i >= index; i--){
            this.pointers[i + 1] = this.pointers[i];
        }
        this.pointers[index] = pointer;
        this.curLevel++;
    }

}
