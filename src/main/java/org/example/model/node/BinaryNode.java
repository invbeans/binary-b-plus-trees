package org.example.model.node;

public class BinaryNode {
    public int key;
    public BinaryNode left;
    public BinaryNode right;

    public BinaryNode(int key, BinaryNode left, BinaryNode right) {
        this.key = key;
        this.left = left;
        this.right = right;
    }

    // поиск по близости сверху
    public BinaryNode searchBigger(int num) {
        if (this.key == num) return this;
        if (this.left == null && this.right == null) return this;

        BinaryNode tmpLeft, tmpRight;

        // если у узла есть только правый ребенок
        if (this.left == null) {
            tmpRight = this.right.searchBigger(num);

            if (tmpRight.key == num) return right;
            int dif1 = num - tmpRight.key;
            int dif2 = num - this.key;

            if (Math.abs(dif1) < Math.abs(dif2)) {
                if (dif1 < 0) return tmpRight;
                else {
                    if (dif2 < 0) return this;
                    else return tmpRight;
                }
            } else {
                if (dif2 < 0) return this;
                else {
                    if (dif1 < 0) return tmpRight;
                    else return this;
                }
            }
        }

        // если у узла есть только левый ребенок
        if (this.right == null) {
            tmpLeft = this.left.searchBigger(num);

            if (tmpLeft.key == num) return right;
            int dif1 = num - tmpLeft.key;
            int dif2 = num - this.key;
            if (Math.abs(dif1) < Math.abs(dif2)) {
                if (dif1 < 0) return tmpLeft;
                else {
                    if (dif2 < 0) return this;
                    else return tmpLeft;
                }
            } else {
                if (dif2 < 0) return this;
                else {
                    if (dif1 < 0) return tmpLeft;
                    else return this;
                }
            }
        }

        // если у узла есть оба ребенка
        tmpLeft = this.left.searchBigger(num);
        if (tmpLeft.key == num) return tmpLeft;
        tmpRight = this.right.searchBigger(num);
        if (tmpRight.key == num) return tmpRight;

        int dif1 = num - tmpLeft.key;
        int dif2 = num - tmpRight.key;
        int dif3 = num - this.key;
        BinaryNode tmpMin = null;
        if (Math.abs(dif1) < Math.abs(dif2) && Math.abs(dif1) < Math.abs(dif3)) {
            tmpMin = tmpLeft;
            if (dif1 < 0) return tmpLeft;
            else {
                if (dif2 < 0) {
                    if (dif3 < 0) {
                        if (dif2 > dif3) return tmpRight;
                        else return this;
                    } else return tmpRight;
                } else {
                    if (dif3 < 0) return this;
                    else return tmpLeft;
                }
            }
        } else if (Math.abs(dif2) < Math.abs(dif1) && Math.abs(dif2) < Math.abs(dif3)) {
            tmpMin = tmpRight;
            if (dif2 < 0) return tmpRight;
            else {
                if (dif1 < 0) {
                    if (dif3 < 0) {
                        if (dif1 > dif3) return tmpLeft;
                        else return this;
                    } else return tmpLeft;
                } else {
                    if (dif3 < 0) return this;
                    else return tmpRight;
                }
            }
        } else if (Math.abs(dif3) < Math.abs(dif1) && Math.abs(dif3) < Math.abs(dif2)) {
            tmpMin = this;
            if (dif3 < 0) return this;
            else {
                if (dif1 < 0) {
                    if (dif2 < 0) {
                        if (dif1 > dif2) return tmpLeft;
                        else return tmpRight;
                    } else return tmpLeft;
                } else {
                    if (dif2 < 0) return tmpRight;
                    else return this;
                }
            }
        }
        return tmpMin;
    }
}
