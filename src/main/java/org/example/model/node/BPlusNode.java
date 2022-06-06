package org.example.model.node;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BPlusNode {
    public BPlusInternal parent;
}