package org.example.model;

import org.example.model.node.BPlusInternal;
import org.example.model.node.BPlusLeaf;
import org.example.model.node.BPlusNode;

import java.util.Arrays;
import java.util.Comparator;

public class BPlusTree {
    int level;
    BPlusInternal root;
    BPlusLeaf firstLeaf;

    public BPlusTree(int level){
        this.level = level;
        this.root = null;
    }

    public void createTree(int[] arr){
        this.addElem(arr[arr.length / 2]);
        for(int i = 0; i < arr.length / 2; i++){
            this.addElem(arr[i]);
        }
        for(int i = arr.length / 2 + 1; i < arr.length; i++){
            this.addElem(arr[i]);
        }
    }

    public int findClosestBigLeaf(int elem, BPlusLeaf leaf){
        int result = 0;
        Integer[] tempKey = leaf.getKey();
        int amount = linearNullSearch(tempKey);
        int[] dif = new int[amount];
        int k = 0;
        for(int i = 0; i < tempKey.length; i++){
            if(tempKey[i] != null){
                dif[k] = tempKey[i] - elem;
                k++;
            }
        }
        int smallestDif = 1000;
        for(int i = 0; i < amount; i++){
            if(Math.abs(smallestDif) >= Math.abs(dif[i]) && dif[i] > 0){
                smallestDif = dif[i];
            }
        }
        result = elem + smallestDif;
        return result;
    }

    public int findClosestBigInternal(int elem, BPlusInternal internal){
        BPlusNode[] tempPointers = internal.getPointers();
        int amountPointers = linearNullSearch(tempPointers);
        Integer[] tempKey = internal.getKey();
        int amountDif = linearNullSearch(tempKey);
        int[] dif = new int[amountDif];
        int k = 0;
        for(int i = 0; i < tempKey.length; i++){
            if(tempKey[i] != null){
                dif[k] = tempKey[i] - elem;
                k++;
            }
        }
        int smallestDif = 1000;
        for(int i = 0; i < amountDif; i++){
            if(Math.abs(smallestDif) >= Math.abs(dif[i])){
                smallestDif = dif[i];
            }
        }
        int result = 0;
        if(smallestDif < 0){
            if(tempPointers[amountPointers - 1] instanceof BPlusInternal){
                result = findClosestBigInternal(elem, (BPlusInternal) tempPointers[amountPointers - 1]);
            }
            else{
                result = findClosestBigLeaf(elem, (BPlusLeaf) tempPointers[amountPointers - 1]);
            }
            return result;
        }
        for(int i = 0; i < amountDif; i++){
            if(smallestDif == dif[i]){
                if(tempPointers[i] instanceof BPlusInternal){
                    result = findClosestBigInternal(elem, (BPlusInternal) tempPointers[i]);
                }
                else{
                    result = findClosestBigLeaf(elem, (BPlusLeaf) tempPointers[i]);
                }
                break;
            }
        }
        return result;
    }

    public int findClosestBig(int elem){
        if(this.root == null) return -1;
        int found = findClosestBigInternal(elem, this.root);
        return found;
    }

    public void inOrder(BPlusNode node){
        if(node instanceof BPlusInternal){
            BPlusInternal internal = (BPlusInternal)node;
            BPlusNode[] temp = internal.getPointers();
            Integer[] keys = internal.getKey();
            for(int i = 0; i < temp.length; i++){
                if(temp[i] != null){
                    inOrder(temp[i]);
                }
                if(i < temp.length - 2 && keys[i] != null){
                    System.out.print(keys[i] + " ");
                }
            }
        }
        if(node instanceof BPlusLeaf){
            BPlusLeaf leaf = (BPlusLeaf) node;
            Integer[] temp = leaf.getKey();
            for(int i = 0; i < temp.length; i++){
                if(temp[i] != null){
                    System.out.print(temp[i] + " ");
                }
            }
        }
    }

    public void inOrder(){
        if(this.root == null) {
            System.out.println("Дерево пустое");
            return;
        }
        inOrder(this.root);
    }

    public void printLeaves(BPlusLeaf leaves[]){
        for(int i = 0; i < leaves.length; i++){
            if(leaves[i] != null){
                Integer[] tempKey = leaves[i].getKey();
                for(int j = 0; j < tempKey.length; j++){
                    if(tempKey[j] != null){
                        System.out.print(tempKey[j] + " ");
                    }
                }
                System.out.print("   ");
            }
        }
        System.out.println();
        System.out.println("Конец дерева");
    }

    public void printInternals(BPlusInternal[] nodes){
        int countNextNodes = 0;
        for(int i = 0; i < nodes.length; i++){
            if(nodes[i] != null){
                Integer[] tempKey = nodes[i].getKey();
                BPlusNode[] tempPointers = nodes[i].getPointers();
                for(int j = 0; j < tempKey.length; j++){
                    if(tempKey[j] != null){
                        System.out.print(tempKey[j] + " ");
                    }
                }
                for(int j = 0; j < tempPointers.length; j++){
                    if(tempPointers[j] != null){
                        countNextNodes++;
                    }
                }
                System.out.print("   ");
            }
        }
        System.out.println();
        BPlusNode[] nextNodes;
        if(nodes[0].getPointers()[0] instanceof BPlusInternal){
            nextNodes = new BPlusInternal[countNextNodes];
        }
        else {
            nextNodes = new BPlusLeaf[countNextNodes];
        }
        for(int i = 0; i < nodes.length; i++){
            int k = 0;
            if(nodes[i] != null){
                BPlusNode[] tempPointers = nodes[i].getPointers();
                for(int j = 0; j < tempPointers.length; j++){
                    if(tempPointers[j] != null){
                        nextNodes[k] = tempPointers[j];
                        k++;
                    }
                }
            }
        }
        if(nextNodes[0] instanceof BPlusInternal){
            printInternals((BPlusInternal[]) nextNodes);
        }
        else printLeaves((BPlusLeaf[]) nextNodes);
    }

    public void printTree(){
        if(this.root == null) {
            System.out.println("Дерево пустое");
            return;
        }
        BPlusInternal[] rootArr = new BPlusInternal[1];
        rootArr[0] = this.root;
        printInternals(rootArr);
    }

    public boolean isEmpty(){ return firstLeaf == null; }

    public BPlusInternal getRoot() {
        return this.root;
    }

    public BPlusLeaf findLeafNode(int elem){
        Integer[] key = this.root.getKey();
        int i;
        for(i = 0; i < this.root.getCurLevel() - 1; i++){
            if(elem < key[i]){ break; }
        }
        BPlusNode child = this.root.getPointers()[i];
        if(child instanceof BPlusLeaf) return (BPlusLeaf)child;
        else return findLeafNode((BPlusInternal)child, elem);
    }

    public BPlusLeaf findLeafNode(BPlusInternal node, int elem){
        Integer[] key = node.getKey();
        int i;
        for(i = 0; i < node.getCurLevel() - 1; i++){
            if(elem < key[i]){ break; }
        }
        BPlusNode child = node.getPointers()[i];
        if(child instanceof BPlusLeaf) return (BPlusLeaf)child;
        else return findLeafNode((BPlusInternal)node.getPointers()[i], elem);
    }

    public void sortKey(Integer[] key){
        Arrays.sort(key, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if(o1 == null && o2 == null){ return 0; }
                if(o1 == null){ return 1; }
                if(o2 == null){ return -1; }
                return o1.compareTo(o2);
            }
        });
    }

    public Integer[] splitNodeKey(BPlusLeaf leaf, int midPoint){
        Integer[] key = leaf.getKey();
        Integer[] halfKey = new Integer[this.level];
        for(int i = midPoint; i < key.length; i++){
            halfKey[i - midPoint] = key[i];
            leaf.deleteElem(i);
        }
        return halfKey;
    }

    public Integer[] splitInternalKey(Integer[] key, int midPoint){
        Integer[] halfKey = new Integer[this.level];
        key[midPoint] = null;
        for(int i = midPoint + 1; i < key.length; i++){
            halfKey[i - midPoint - 1] = key[i];
            key[i] = null;
        }
        return halfKey;
    }

    public BPlusNode[] splitChildPointers(BPlusInternal internal, int midPoint){
        BPlusNode[] pointers = internal.getPointers();
        BPlusNode[] halfPointers = new BPlusNode[this.level + 1];
        for(int i = midPoint + 1; i < pointers.length; i++){
            halfPointers[i - midPoint - 1] = pointers[i];
            internal.removePointer(i);
        }
        return halfPointers;
    }

    public int linearNullSearch(BPlusNode[] arr){
        for(int i = 0; i < arr.length; i++){
            if(arr[i] == null) return i;
        }
        return -1;
    }

    public int linearNullSearch(Integer[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) return i;
        }
        return -1;
    }

    public void splitInternalNode(BPlusInternal internal){
        BPlusInternal parent = internal.parent;
        int midPoint = (int)Math.ceil((this.level + 1) / 2.0) - 1;
        int newParentKey = internal.getKey()[midPoint];
        Integer[] halfKeys = splitInternalKey(internal.getKey(), midPoint);
        BPlusNode[] halfPointers = splitChildPointers(internal, midPoint);
        internal.setCurLevel(linearNullSearch(internal.getPointers()));
        BPlusInternal sibling = new BPlusInternal(this.level, halfKeys, halfPointers);
        for(BPlusNode pointer: halfPointers){
            if(pointer != null) pointer.parent = sibling;
        }
        sibling.setRight(internal.getRight());
        if(sibling.getRight() != null){
            sibling.getRight().setLeft(sibling);
        }
        internal.setRight(sibling);
        sibling.setLeft(internal);
        if(parent == null){
            Integer[] key = new Integer[this.level];
            key[0] = newParentKey;
            BPlusInternal newRoot = new BPlusInternal(this.level, key);
            newRoot.appendChildPointer(internal);
            newRoot.appendChildPointer(sibling);
            this.root = newRoot;
        } else {
            Integer[] temp = parent.getKey();
            temp[parent.getCurLevel() - 1] = newParentKey;
            Arrays.sort(temp, 0, parent.getCurLevel());
            parent.setKey(temp);
            int pointerIndex = parent.findIndexOfPointer(internal) + 1;
            parent.insertChildPointer(sibling, pointerIndex);
            sibling.parent = parent;
        }
    }

    public void addElem(int elem){
        if(isEmpty()){
            BPlusLeaf leaf = new BPlusLeaf(this.level, elem);
            this.firstLeaf = leaf;
        }
        else{
            BPlusLeaf leaf = (this.root == null) ? this.firstLeaf : findLeafNode(elem);
            if(!leaf.addElem(elem)){
                Integer[] tempKey = leaf.getKey();
                tempKey[leaf.getCurNum()] = elem;
                leaf.setKey(tempKey);
                this.sortKey(leaf.getKey());
                leaf.setCurNum(leaf.getCurNum() + 1);
                int midPoint = (int)Math.ceil((this.level + 1) / 2.0) - 1;
                Integer[] halfKey = splitNodeKey(leaf, midPoint);
                if(leaf.parent == null){
                    Integer[] parentKey = new Integer[this.level];
                    parentKey[0] = halfKey[0];
                    BPlusInternal parent = new BPlusInternal(this.level, parentKey);
                    leaf.parent = parent;
                    parent.appendChildPointer(leaf);
                }
                else {
                    int newParentKey = halfKey[0];
                    Integer[] tempParentKey = leaf.parent.getKey();
                    tempParentKey[leaf.parent.getCurLevel() - 1] = newParentKey;
                    leaf.parent.setKey(tempParentKey);
                    Arrays.sort(leaf.parent.getKey(), 0, leaf.parent.getCurLevel());

                }
                BPlusLeaf newLeafNode = new BPlusLeaf(this.level, halfKey, leaf.parent);
                int pointerIndex = leaf.parent.findIndexOfPointer(leaf) + 1;
                leaf.parent.insertChildPointer(newLeafNode, pointerIndex);
                newLeafNode.setRight(leaf.getRight());
                if(newLeafNode.getRight() != null){
                    newLeafNode.getRight().setLeft(newLeafNode);
                }
                leaf.setRight(newLeafNode);
                newLeafNode.setLeft(leaf);

                if(this.root == null){
                    this.root = leaf.parent;
                }
                else{
                    BPlusInternal internal = leaf.parent;
                    while (internal != null){
                        if(internal.getCurLevel() == internal.getMaxLevel() + 1){
                            splitInternalNode(internal);
                        }
                        else break;
                        internal = internal.parent;
                    }
                }
            }
        }
    }
}
